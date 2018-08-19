package io.github.jhipster.application.web.rest;


import io.github.jhipster.application.model.facebook.FacebookHook;
import io.github.jhipster.application.model.line.LineHook;
import io.github.jhipster.application.repository.MessageRepository;
import io.github.jhipster.application.service.FacebookService;
import io.github.jhipster.application.service.LineService;
import io.github.jhipster.application.service.MessageService;
import io.github.jhipster.application.web.websocket.WebSocketConstant;
import io.github.jhipster.application.web.websocket.dto.ActivityDTO;
import io.github.jhipster.application.web.websocket.dto.ChatMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
public class ChatResources {

    private final Logger log = LoggerFactory.getLogger(ChatResources.class);

    private MessageService messageService;
    private SimpMessageSendingOperations messagingTemplate;
    private MessageRepository messageRepository;
    private FacebookService facebookService;
    private LineService lineService;

    public ChatResources(MessageService messageService,
                         SimpMessageSendingOperations messagingTemplate,
                         MessageRepository messageRepository,
                         FacebookService facebookService,
                         LineService lineService) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
        this.messageRepository = messageRepository;
        this.facebookService = facebookService;
        this.lineService = lineService;
    }

    @GetMapping("/facebook/hook")
    public ResponseEntity facebookVerify(HttpServletRequest req) {
        log.debug("GET URL[/facebook/hook] req.getParameterMap()={}", req.getParameterMap().toString());
        return ResponseEntity.ok(Optional.ofNullable(req.getParameter("hub.challenge")).orElse(""));
    }

    @PostMapping("/facebook/hook")
    public ResponseEntity facebookHook(@RequestBody FacebookHook body) {
        log.debug("POST URL[/facebook/hook] FacebookHook Object ={} ", body.toString());
        facebookService.saveMessage(body);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/line/hook")
    public ResponseEntity lineHook(@RequestBody LineHook body) {
        log.debug("POST URL[/chat/line/hook] LineHook Object={}", body.toString());
        if(!StringUtils.isNumeric(body.getEvents().get(0).getReplyToken())){
            lineService.saveMessage(body);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/customer")
    public ResponseEntity customer() {
        log.debug("GET URL[/chat/customer]");
        return ResponseEntity.ok(messageRepository.getCustomersByLatestMessage());
    }

    @GetMapping("/messageHistory/{customerId}")
    public ResponseEntity message(@PathVariable("customerId") String customerId) {
        log.debug("GET URL[/chat/messageHistory/{customerId}] Customer ID = {}", customerId);
        return ResponseEntity.ok(messageService.getMessageHistory(customerId));
    }


    @PostMapping("/sendMessage")
    public ResponseEntity sendMessage(ChatMessage chatMessage) {
        log.debug("GET URL[/chat/sendMessage]");
        messagingTemplate.convertAndSend(WebSocketConstant.TOPIC_CLIENT_MESSAGE_DESTIANTION, chatMessage);
        return ResponseEntity.ok().build();
    }

}
