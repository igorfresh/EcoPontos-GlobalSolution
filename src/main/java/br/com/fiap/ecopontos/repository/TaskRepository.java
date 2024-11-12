package br.com.fiap.ecopontos.repository;

import br.com.fiap.ecopontos.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
