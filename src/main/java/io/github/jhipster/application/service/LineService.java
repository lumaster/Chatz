package io.github.jhipster.application.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.jhipster.application.config.APIProperties;
import io.github.jhipster.application.model.constant.Constant;
import io.github.jhipster.application.model.line.LineHook;
import io.github.jhipster.application.model.line.LineProfile;
import io.github.jhipster.application.repository.MessageRepository;
import io.github.jhipster.application.web.websocket.WebSocketConstant;
import io.github.jhipster.application.web.websocket.dto.ChatMessage;
import io.github.jhipster.application.web.websocket.dto.Customer;
import io.github.jhipster.application.web.websocket.dto.LineRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.Map;

@Service
public class LineService {

    private static final Logger log = LoggerFactory.getLogger(LineService.class);

    private APIProperties apiProperties;
    private RestTemplate restTemplate;
    private SimpMessageSendingOperations messagingTemplate;
    private MessageRepository messageRepository;

    public LineService(APIProperties apiProperties,
                       RestTemplate restTemplate,
                       SimpMessageSendingOperations messagingTemplate,
                       MessageRepository messageRepository) {
        this.apiProperties = apiProperties;
        this.restTemplate = restTemplate;
        this.messagingTemplate = messagingTemplate;
        this.messageRepository = messageRepository;
    }

    public boolean saveMessage(LineHook lineHook) {

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.getCustomer().setChannel(Constant.LINE);
        chatMessage.getCustomer().setSocialId(lineHook.getEvents().get(0).getSource().getUserId());
        chatMessage.setStatus(Constant.IN);
        chatMessage.setType(lineHook.getEvents().get(0).getMessage().getType().toUpperCase());

        if ("sticker".equalsIgnoreCase(lineHook.getEvents().get(0).getMessage().getType())) {
            chatMessage.setMessage("sticker");
            chatMessage.setType("TEXT");
        }else{
            chatMessage.setMessage(lineHook.getEvents().get(0).getMessage().getText());
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization", apiProperties.getLineHeaders().get("Authorization"));
        HttpEntity httpEntity = new HttpEntity<>(headers);

        try {

            if (Constant.IMAGE.equalsIgnoreCase(lineHook.getEvents().get(0).getMessage().getType())) {

                ResponseEntity<byte[]> responseEntity = restTemplate
                    .exchange(apiProperties.getLineURLs().get("content"), HttpMethod.GET, httpEntity, byte[].class, lineHook.getEvents().get(0).getMessage().getId());
//                log.debug("Resp :: responseEntity = {}", responseEntity.toString());
                chatMessage.setContent(responseEntity.getBody());

            }

            ResponseEntity<String> responseEntity = restTemplate
                .exchange(apiProperties.getLineURLs().get("profile"), HttpMethod.GET, httpEntity, String.class, chatMessage.getCustomer().getSocialId());
            log.debug("Resp :: responseEntity = {}", responseEntity.toString());

            LineProfile lineProfile = new Gson().fromJson(responseEntity.getBody(), LineProfile.class);
            chatMessage.getCustomer().setSocialName(lineProfile.getDisplayName());
            chatMessage.getCustomer().setImageUrl(lineProfile.getPictureUrl());

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
            log.error("response Http Code = {}, Body = {}", e.getRawStatusCode(), e.getResponseBodyAsString());
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while saving message", e);
            throw e;
        }


    }

    public boolean sendMessage(ChatMessage message) {

        LineRequest lineRequest = new LineRequest();
        lineRequest.setTo(message.getCustomer().getSocialId());
        LineRequest.LineMessage lineMessage = lineRequest.createLineMessage();
        lineMessage.setText(message.getMessage());
        lineRequest.getMessages().add(lineMessage);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization", apiProperties.getLineHeaders().get("Authorization"));
        HttpEntity<LineRequest> httpEntity = new HttpEntity<>(lineRequest, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate
                .exchange(apiProperties.getLineURLs().get("sendMessage"), HttpMethod.POST, httpEntity, String.class);
            log.debug("Resp :: responseEntity = {}", responseEntity.toString());
        } catch (RestClientResponseException e) {
            log.error("response Http Code = {}, Body = {}", e.getRawStatusCode(), e.getResponseBodyAsString());
            throw e;
        }
        return true;
    }


}
