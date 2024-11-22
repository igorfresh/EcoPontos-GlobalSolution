package br.com.fiap.ecopontos.views;

import br.com.fiap.ecopontos.model.Reward;
import br.com.fiap.ecopontos.model.Task;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Route("")
public class HomeView extends VerticalLayout {

    private static final String TASK_URL = "https://ecopontos-globalsolution-production.up.railway.app/task";
    private static final String REWARD_URL = "https://ecopontos-globalsolution-production.up.railway.app/reward";

    public HomeView() {
        add(new H1("Sistema de Tarefas e Recompensas"));

        // Botões para carregar e exibir Tasks e Rewards
        Button loadTasksButton = new Button("Carregar Tarefas", event -> {
            List<Task> tasks = fetchTasks();
            if (!tasks.isEmpty()) {
                openTasksDialog(tasks);
            }
        });

        Button loadRewardsButton = new Button("Carregar Recompensas", event -> {
            List<Reward> rewards = fetchRewards();
            if (!rewards.isEmpty()) {
                openRewardsDialog(rewards);
            }
        });

        // Botão para navegar para o Chat
        Button goToChatButton = new Button("Ir para o Chat", event ->
                getUI().ifPresent(ui -> ui.navigate("chat"))
        );

        // Adiciona os botões à view
        add(loadTasksButton, loadRewardsButton, goToChatButton);
    }

    private List<Task> fetchTasks() {
        RestTemplate restTemplate = new RestTemplate();
        TaskResponse response = restTemplate.getForObject(TASK_URL, TaskResponse.class);
        return response != null ? response.getContent() : List.of();
    }

    private List<Reward> fetchRewards() {
        RestTemplate restTemplate = new RestTemplate();
        RewardResponse response = restTemplate.getForObject(REWARD_URL, RewardResponse.class);
        return response != null ? response.getContent() : List.of();
    }

    private void openTasksDialog(List<Task> tasks) {
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");
        dialog.setHeight("400px");

        Grid<Task> grid = new Grid<>(Task.class);
        grid.setItems(tasks);
        grid.setColumns("id", "type", "description");

        Button closeButton = new Button("Fechar", event -> dialog.close());
        VerticalLayout dialogLayout = new VerticalLayout(grid, closeButton);
        dialog.add(dialogLayout);

        dialog.open();
    }

    private void openRewardsDialog(List<Reward> rewards) {
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");
        dialog.setHeight("400px");

        Grid<Reward> grid = new Grid<>(Reward.class);
        grid.setItems(rewards);
        grid.setColumns("id", "description", "necessaryPoints");

        Button closeButton = new Button("Fechar", event -> dialog.close());
        VerticalLayout dialogLayout = new VerticalLayout(grid, closeButton);
        dialog.add(dialogLayout);

        dialog.open();
    }

    private static class TaskResponse {
        private List<Task> content;

        public List<Task> getContent() {
            return content;
        }

        public void setContent(List<Task> content) {
            this.content = content;
        }
    }

    private static class RewardResponse {
        private List<Reward> content;

        public List<Reward> getContent() {
            return content;
        }

        public void setContent(List<Reward> content) {
            this.content = content;
        }
    }
}
