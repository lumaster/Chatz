package io.github.jhipster.application.service;


import io.github.jhipster.application.model.constant.Constant;
import io.github.jhipster.application.repository.MessageRepository;
import io.github.jhipster.application.web.websocket.dto.ChatMessage;
import io.github.jhipster.application.web.websocket.dto.MessageHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    private MessageRepository messageRepository;
    private SimpMessageSendingOperations messagingTemplate;
    private final LineService lineService;
    private final FacebookService facebookService;

    public MessageService(MessageRepository messageRepository,
                          SimpMessageSendingOperations messagingTemplate,
                          LineService lineService,
                          FacebookService facebookService) {
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
        this.lineService = lineService;
        this.facebookService = facebookService;
    }

    public MessageHistory getMessageHistory(String customerId) {

        MessageHistory messageHistory = new MessageHistory();
        messageHistory.setCustomer(messageRepository.getCustomer(customerId));
        messageHistory.setMessageList(messageRepository.getMessages(customerId));
        return messageHistory;

    }

    public void sendMessage(ChatMessage message) {

        message.setStatus(Constant.OUT);

        switch (message.getCustomer().getChannel()) {
            case Constant.LINE:
                messageRepository.saveMessage(message);
                lineService.sendMessage(message);
                break;
            case Constant.FACEBOOK:
                messageRepository.saveMessage(message);
                facebookService.sendMessage(message);
                break;
            default:
                log.info("Send message channel not found = " + message.getCustomer().getChannel());
                break;
        }

    }

    public void receiveMessage(ChatMessage message) {
        messagingTemplate.convertAndSend("/topic/messageClient", message);

    }
}
