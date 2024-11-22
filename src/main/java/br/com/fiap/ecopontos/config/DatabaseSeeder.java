package br.com.fiap.ecopontos.config;

import java.time.LocalDate;
import java.util.List;

import br.com.fiap.ecopontos.model.Reward;
import br.com.fiap.ecopontos.model.Task;
import br.com.fiap.ecopontos.model.TaskRegistration;
import br.com.fiap.ecopontos.model.User;
import br.com.fiap.ecopontos.repository.RewardRepository;
import br.com.fiap.ecopontos.repository.TaskRegistrationRepository;
import br.com.fiap.ecopontos.repository.TaskRepository;
import br.com.fiap.ecopontos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskRegistrationRepository taskRegistrationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Adicionando Recompensas
        rewardRepository.saveAll(List.of(
                new Reward(null, "Desconto de 10% em produtos sustentáveis", 100),
                new Reward(null, "Cupom de 20 reais em lojas parceiras", 200),
                new Reward(null, "Kit de lâmpadas LED", 500),
                new Reward(null, "Painel solar portátil", 1000)
        ));

        // Adicionando Tarefas
        taskRepository.saveAll(List.of(
                new Task(null, "Economizar Energia", "Desligar aparelhos em stand-by por 7 dias"),
                new Task(null, "Economizar Energia", "Usar luz apagada na parte do dia"),
                new Task(null, "Utilidade", "Comprar painel solar"),
                new Task(null, "Educação Ambiental", "Assistir a 3 vídeos sobre economia de energia")
        ));

        // Adicionando Usuários
        List<User> users = userRepository.saveAll(List.of(
                new User(null, "João da Silva", "joao.silva@email.com", "senha123", LocalDate.of(2023, 1, 15), 350),
                new User(null, "Maria Oliveira", "maria.oliveira@email.com", "senha456", LocalDate.of(2023, 5, 20), 500),
                new User(null, "Carlos Souza", "carlos.souza@email.com", "senha789", LocalDate.of(2023, 3, 10), 200)
        ));

        // Adicionando Registros de Tarefas
        taskRegistrationRepository.saveAll(List.of(
                new TaskRegistration(null, users.get(0), taskRepository.findById(1L).orElseThrow(), LocalDate.of(2023, 10, 10), 60, 100),
                new TaskRegistration(null, users.get(0), taskRepository.findById(2L).orElseThrow(), LocalDate.of(2023, 11, 1), 90, 150),
                new TaskRegistration(null, users.get(1), taskRepository.findById(3L).orElseThrow(), LocalDate.of(2023, 10, 20), 30, 200),
                new TaskRegistration(null, users.get(2), taskRepository.findById(4L).orElseThrow(), LocalDate.of(2023, 11, 5), 45, 250)
        ));
    }
}

