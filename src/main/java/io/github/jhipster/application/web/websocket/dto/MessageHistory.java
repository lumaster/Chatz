package io.github.jhipster.application.web.websocket.dto;

import lombok.Data;

import java.util.List;

@Data
public class MessageHistory extends BaseBean{

    private List<ChatMessage> messageList;
    private Customer customer;

}
