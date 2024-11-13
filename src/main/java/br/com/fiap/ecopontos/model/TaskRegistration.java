package br.com.fiap.ecopontos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "taskregistration")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @ManyToOne
    @NotNull(message = "{taskRegistration.user.notnull}")
    public User user;

    @ManyToOne
    @NotNull(message = "{taskRegistration.task.notnull}")
    public Task task;

    @NotNull(message = "{taskRegistration.taskDateCompleted.notnull}")
    @Past(message = "{taskRegistration.taskDateCompleted.past}")
    private Date tasKDateCompleted;

    @Min(value = 0, message = "{taskRegistration.duration.min}")
    private int duration;

    @Min(value = 0, message = "{taskRegistration.totalPoints.min}")
    private int totalPoints;

}
