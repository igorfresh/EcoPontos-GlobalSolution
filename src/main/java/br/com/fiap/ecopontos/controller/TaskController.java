package br.com.fiap.ecopontos.controller;

import br.com.fiap.ecopontos.model.Reward;
import br.com.fiap.ecopontos.model.Task;
import br.com.fiap.ecopontos.repository.RewardRepository;
import br.com.fiap.ecopontos.repository.TaskRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
@RequestMapping("task")
public class TaskController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    TaskRepository taskRepository;

    @GetMapping
    public Page<Task> index(
            @RequestParam(required = false) String type,
            @PageableDefault(size = 3, sort = "type", direction = Sort.Direction.ASC) Pageable pageable
    ){
        if (type != null){
            return taskRepository.findByType(type, pageable);
        }

        return taskRepository.findAll(pageable);

    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Task create(@RequestBody @Valid Task task) {
        log.info("cadastrando compra {}", task);
        return taskRepository.save(task);
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> show (@PathVariable Long id) {
        log.info("buscando compra por id {}", id);
        return taskRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando compra com id {}");
        verifyExistingBuy(id);

        taskRepository.deleteById(id);
    }

    @PutMapping("{id}")
    public Task update (@PathVariable Long id, @RequestBody Task task) {
        log.info("atualizando compra com id {}", id, task);

        verifyExistingBuy(id);

        task.setId(id);
        return taskRepository.save(task);
    }

    private void verifyExistingBuy(Long id) {
        taskRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe compra com o id informado. Consulte lista em /buy"));
    }
}
