package com.example.ElectronicWebJournal.util.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseException {
    private String message;
    private LocalDateTime localDateTime;

    public ResponseException(String message, LocalDateTime localDateTime) {
        this.message = message;
        this.localDateTime = localDateTime;
    }
}
