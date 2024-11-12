package br.com.fiap.ecopontos.repository;

import br.com.fiap.ecopontos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
