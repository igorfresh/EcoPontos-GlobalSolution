package br.com.fiap.ecopontos.repository;

import br.com.fiap.ecopontos.model.TaskRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRegistrationRepository extends JpaRepository<TaskRegistration, Long> {

}
