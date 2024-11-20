package br.com.fiap.ecopontos.repository;

import br.com.fiap.ecopontos.model.Task;
import br.com.fiap.ecopontos.model.TaskRegistration;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRegistrationRepository extends JpaRepository<TaskRegistration, Long> {

    Page<TaskRegistration> findByTotalPoints(Integer totalPoints, Pageable pageable);

}
