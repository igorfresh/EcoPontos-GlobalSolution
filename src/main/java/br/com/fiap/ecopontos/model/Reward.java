package br.com.fiap.ecopontos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Entity
@Table(name = "rewards")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @NotBlank(message = "{rewards.description.notblank}")
    private String description;

    @Min(value = 1, message = "{rewards.necessaryPoints.min}")
    @Max(value = 10000, message = "{rewards.necessaryPoints.max}")
    private int necessaryPoints;

}
