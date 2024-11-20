package br.com.fiap.ecopontos.repository;

import br.com.fiap.ecopontos.model.Reward;
import br.com.fiap.ecopontos.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByType(String type, Pageable pageable);
}
