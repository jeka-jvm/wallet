package org.example.wallet.exception;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;


@Setter
@Getter
@XmlRootElement
public class ErrorResponse {
    private String errorCode;
    private LocalDateTime timestamp;
    private String description;
    private String tip;

    public ErrorResponse() {
    }

    public ErrorResponse(String errorCode, String description, String tip) {
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
        this.description = description;
        this.tip = tip;
    }

}
