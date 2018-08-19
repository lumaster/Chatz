package io.github.jhipster.application.web.websocket.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseBean {

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String status;

}
