package io.github.jhipster.application.model.facebook;


import lombok.Data;

@Data
public class Messaging {

    private Sender sender;
    private Recipient recipient;
    private String timestamp;
    private FacebookMessage message;

}
