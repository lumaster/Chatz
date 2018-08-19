package io.github.jhipster.application.service;

import com.google.gson.Gson;
import io.github.jhipster.application.config.APIProperties;
import io.github.jhipster.application.model.constant.Constant;
import io.github.jhipster.application.repository.MessageRepository;
import io.github.jhipster.application.web.websocket.WebSocketConstant;
import io.github.jhipster.application.web.websocket.dto.ChatMessage;
import io.github.jhipster.application.web.websocket.dto.Customer;
import io.github.jhipster.application.web.websocket.dto.FacebookRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import io.github.jhipster.application.model.facebook.*;

import java.util.List;

@Service
public class FacebookService {

    private static final Logger log = LoggerFactory.getLogger(FacebookService.class);
    private APIProperties apiProperties;
    private RestTemplate restTemplate;
    private SimpMessageSendingOperations messagingTemplate;
    private MessageRepository messageRepository;

    public FacebookService(APIProperties apiProperties,
                           RestTemplate restTemplate,
                           SimpMessageSendingOperations messagingTemplate,
                           MessageRepository messageRepository) {
        this.apiProperties = apiProperties;
        this.restTemplate = restTemplate;
        this.messagingTemplate = messagingTemplate;
        this.messageRepository = messageRepository;
    }

    public boolean saveMessage(FacebookHook facebookHook) {

        String socialId = facebookHook.getEntry().get(0).getMessaging().get(0).getSender().getId();

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.getCustomer().setChannel(Constant.FACEBOOK);
        chatMessage.getCustomer().setSocialId(socialId);
        chatMessage.setStatus(Constant.IN);


        List<FacebookAttachment> attachments = facebookHook.getEntry().get(0).getMessaging().get(0).getMessage().getAttachments();
        if (!attachments.isEmpty() && Constant.IMAGE.equalsIgnoreCase(attachments.get(0).getType())) {
            chatMessage.setType(Constant.IMAGE);
            chatMessage.setMessage(attachments.get(0).getPayload().getUrl());
        } else {
            chatMessage.setType(Constant.TEXT);
            chatMessage.setMessage(facebookHook.getEntry().get(0).getMessaging().get(0).getMessage().getText());
        }

        try {
            ResponseEntity<String> responseEntity = restTemplate
                .exchange(apiProperties.getFacebookURLs().get("profile"), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class, socialId);
            log.debug("Resp :: responseEntity = {}", responseEntity.toString());

            FacebookProfile facebookProfile = new Gson().fromJson(responseEntity.getBody(), FacebookProfile.class);
            chatMessage.getCustomer().setSocialName(facebookProfile.getFirstName() + " " + facebookProfile.getLastName());
            chatMessage.getCustomer().setImageUrl(facebookProfile.getProfilePic());

            Customer customerBySocialId = messageRepository.getCustomerBySocialId(chatMessage.getCustomer().getSocialId());

            if (customerBySocialId == null) {
                chatMessage.getCustomer().setCustomerId(messageRepository.saveCustomer(chatMessage.getCustomer()));
            }else{
                chatMessage.getCustomer().setCustomerId(customerBySocialId.getCustomerId());
                messageRepository.updateCustomer(chatMessage.getCustomer());

            }

            messageRepository.saveMessage(chatMessage);

            messagingTemplate.convertAndSend(WebSocketConstant.TOPIC_CLIENT_MESSAGE_DESTIANTION, chatMessage);

            return true;

        } catch (RestClientResponseException e) {
            log.error("Resp :: Http Code = {}, Body = {}", e.getRawStatusCode(), e.getResponseBodyAsString());
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while saving message");
            throw e;
        }
    }

    public void sendMessage(ChatMessage message) {

        FacebookRequest facebookRequest = new FacebookRequest();
        facebookRequest.getRecipient().setId(message.getCustomer().getSocialId());
        facebookRequest.getMessage().setText(message.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<FacebookRequest> httpEntity = new HttpEntity<>(facebookRequest, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate
                .exchange(apiProperties.getFacebookURLs().get("sendMessage"), HttpMethod.POST, httpEntity, String.class);
            log.debug("Resp :: responseEntity = {}", responseEntity.toString());
        } catch (RestClientResponseException e) {
            log.error("Resp :: Http Code = {}, Body = {}", e.getRawStatusCode(), e.getResponseBodyAsString());
            throw e;
        }
    }
}
