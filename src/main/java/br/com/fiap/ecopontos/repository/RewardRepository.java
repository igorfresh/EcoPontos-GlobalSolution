package br.com.fiap.ecopontos.repository;

import br.com.fiap.ecopontos.model.Reward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward, Long> {

    Page <Reward> findByNecessaryPoints(Integer necessaryPoints, Pageable pageable);
}
