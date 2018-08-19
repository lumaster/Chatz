package io.github.jhipster.application.web.websocket.dto;

import lombok.Data;

@Data
public class ChatMessage extends BaseBean{

    private String message;
    private byte[] content;
    private String type;
    private Customer customer = new Customer();
    private boolean isGroupMessage;
    private Long groupId;
    private String status;

}
