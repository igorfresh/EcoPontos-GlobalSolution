package br.com.fiap.ecopontos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @NotBlank(message = "{user.name.notblank}")
    private String name;

    @NotBlank(message = "{user.email.notblank}")
    @Email(message = "{user.email.pattern}")  // Validação de formato de email
    private String email;

    @NotBlank(message = "{user.password.notblank}")
    @Size(min = 6, max = 20, message = "{user.password.size}")  // Senha entre 6 e 20 caracteres
    private String password;

    @NotNull(message = "{user.registerDate.notnull}")
    private Date register_date;

    @Min(value = 0, message = "{user.points.min}")  // Pontos não podem ser negativos
    private int points;

}
