package br.com.fiap.ecopontos.controller;

import br.com.fiap.ecopontos.model.Task;
import br.com.fiap.ecopontos.model.TaskRegistration;
import br.com.fiap.ecopontos.repository.TaskRegistrationRepository;
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
@RequestMapping("taskRegistration")
public class TaskRegistrationController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    TaskRegistrationRepository taskRegistrationRepository;

    @GetMapping
    public Page<TaskRegistration> index(
            @RequestParam(required = false) Integer totalPoints,
            @PageableDefault(size = 3, sort = "totalPoints", direction = Sort.Direction.ASC) Pageable pageable
    ){
        if (totalPoints != null){
            return taskRegistrationRepository.findByTotalPoints(totalPoints, pageable);
        }

        return taskRegistrationRepository.findAll(pageable);


    }

    @PostMapping
    @ResponseStatus(CREATED)
    public TaskRegistration create(@RequestBody @Valid TaskRegistration taskRegistration) {
        log.info("cadastrando compra {}", taskRegistration);
        return taskRegistrationRepository.save(taskRegistration);
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskRegistration> show (@PathVariable Long id) {
        log.info("buscando compra por id {}", id);
        return taskRegistrationRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando compra com id {}");
        verifyExistingBuy(id);

        taskRegistrationRepository.deleteById(id);
    }

    @PutMapping("{id}")
    public TaskRegistration update (@PathVariable Long id, @RequestBody TaskRegistration taskRegistration) {
        log.info("atualizando compra com id {}", id, taskRegistration);

        verifyExistingBuy(id);

        taskRegistration.setId(id);
        return taskRegistrationRepository.save(taskRegistration);
    }

    private void verifyExistingBuy(Long id) {
        taskRegistrationRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe compra com o id informado. Consulte lista em /buy"));
    }
}
