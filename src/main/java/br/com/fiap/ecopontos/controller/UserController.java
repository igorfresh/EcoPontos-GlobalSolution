package br.com.fiap.ecopontos.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import br.com.fiap.ecopontos.model.User;
import br.com.fiap.ecopontos.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@CacheConfig(cacheNames = "clientes")
@RequestMapping("client")
@Tag(name = "clientes", description = "Clientes cadastrados")
public class UserController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    @Cacheable
    @Operation(
            summary = "Listar Clientes",
            description = "Retorna uma página com todas os clientes cadastrados ordenada por ordem alfabética"
    )
    public Page<User> index(
            @RequestParam(required = false) String name,
            @PageableDefault(size = 3, sort = "name", direction = Direction.ASC ) Pageable pageable
    ) {
        if (name != null) {
            return userRepository.findByName(name, pageable);
        }
        return userRepository.findAll(pageable);
    }


    @PostMapping
    @ResponseStatus(CREATED)
    @CacheEvict(allEntries = true)
    @Operation(
            summary = "Cadastrar cliente",
            description = "Cadastra um novo cliente com os dados do corpo da requisição."
    )
    public User create(@RequestBody @Valid User user) {
        log.info("cadastrando cliente {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Listar cliente por ID",
            description = "Retorna um determinado cliente correspondente com o ID selecionado."
    )
    public ResponseEntity<User> show(@PathVariable Long id) {
        log.info("buscando cliente por id {}", id);
        return userRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(
            summary = "Deletar cliente",
            description = "Deleta um determinado cliente correspondente com o ID selecionado."
    )
    public void destroy(@PathVariable Long id) {
        log.info("Apagando cadastro de cliente");
        verifyExistingClient(id);

        userRepository.deleteById(id);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(
            summary = "Atualizar cliente",
            description = "Atualiza um determinado cliente correspondente com o ID selecionado."
    )
    public User update (@PathVariable Long id, @RequestBody User user) {
        log.info("atualizando cliente com id {} para {}", id, user);

        verifyExistingClient(id);

        user.setId(id);
        return userRepository.save(user);
    }

    private void verifyExistingClient(Long id) {
        userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Não existe cliente com o id informado. Consulte lista em /client"));
    }

}
