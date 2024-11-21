package br.com.fiap.ecopontos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tasks")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;


    @NotBlank(message = "{tasks.type.notblank}")
    private String type;

    @NotBlank(message = "{tasks.description.notblank}")
    private String description;

    public Task(Long id) {
        this.id = id;
    }
}
