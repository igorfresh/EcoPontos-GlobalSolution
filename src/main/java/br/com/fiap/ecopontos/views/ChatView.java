package br.com.fiap.ecopontos.views;

import br.com.fiap.ecopontos.chat.ChatAIService;
import br.com.fiap.ecopontos.config.MessageProducer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("chat")
@CssImport("../resources/templates/chat-view.css")
public class ChatView extends VerticalLayout {

    private final ChatAIService chatService;
    private final MessageProducer messageProducer;
    private final TextArea chatHistory;
    private final TextField userInput;

    public ChatView(ChatAIService chatService, MessageProducer messageProducer) {
        this.chatService = chatService;
        this.messageProducer = messageProducer;

        setSizeFull();
        addClassName("chat-view");

        chatHistory = new TextArea("Chat");
        chatHistory.setWidthFull();
        chatHistory.setReadOnly(true);
        chatHistory.setHeight("400px");

        userInput = new TextField();
        userInput.setWidthFull();
        userInput.setPlaceholder("Digite sua mensagem...");

        Button sendButton = new Button("Enviar", e -> sendMessage());
        HorizontalLayout inputLayout = new HorizontalLayout(userInput, sendButton);
        inputLayout.setWidthFull();
        inputLayout.expand(userInput);

        // Botão para voltar para a Home
        Button goToHomeButton = new Button("Voltar para Home", event ->
                getUI().ifPresent(ui -> ui.navigate(""))
        );

        add(chatHistory, inputLayout, goToHomeButton);
    }

    private void sendMessage() {
        String userMessage = userInput.getValue();
        if (userMessage.isEmpty()) return;

        chatHistory.setValue(chatHistory.getValue() + "\nVocê: " + userMessage);
        userInput.clear();

        // Enviar para a fila do RabbitMQ
        messageProducer.sendMessage(userMessage);

        // Obter resposta da IA
        String aiResponse = chatService.sendToAI(userMessage);
        chatHistory.setValue(chatHistory.getValue() + "\nIA: " + aiResponse);
    }
}
