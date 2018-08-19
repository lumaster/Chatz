package io.github.jhipster.application.web.websocket.dto;


import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Customer extends BaseBean{

    private Long customerId;
    private String socialId;
    private String socialName;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String email;
    private String channel;
    private String imageUrl;
    private String customerType;
    private Long customerRelationId;
    private LocalDateTime lastMessageDateTime;

}
