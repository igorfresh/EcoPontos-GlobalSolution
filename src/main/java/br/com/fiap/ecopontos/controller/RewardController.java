package br.com.fiap.ecopontos.controller;

import br.com.fiap.ecopontos.model.Reward;
import br.com.fiap.ecopontos.repository.RewardRepository;
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
@RequestMapping("reward")
public class RewardController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    RewardRepository rewardRepository;

    @GetMapping
    public Page<Reward> index(
            @RequestParam(required = false) Integer necessaryPoints,
            @PageableDefault(size = 3, sort = "necessaryPoints", direction = Sort.Direction.ASC) Pageable pageable
    ){
        if (necessaryPoints != null){
            return rewardRepository.findByNecessaryPoints(necessaryPoints, pageable);
        }

        return rewardRepository.findAll(pageable);

    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Reward create(@RequestBody @Valid Reward reward) {
        log.info("cadastrando compra {}", reward);
        return rewardRepository.save(reward);
    }

    @GetMapping("{id}")
    public ResponseEntity<Reward> show (@PathVariable Long id) {
        log.info("buscando compra por id {}", id);
        return rewardRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando compra com id {}");
        verifyExistingBuy(id);

        rewardRepository.deleteById(id);
    }

    @PutMapping("{id}")
    public Reward update (@PathVariable Long id, @RequestBody Reward reward) {
        log.info("atualizando compra com id {}", id, reward);

        verifyExistingBuy(id);

        reward.setId(id);
        return rewardRepository.save(reward);
    }

    private void verifyExistingBuy(Long id) {
        rewardRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe compra com o id informado. Consulte lista em /buy"));
    }
}
