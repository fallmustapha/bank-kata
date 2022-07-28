package com.test.bankkata.api.dto.responses;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * This model is used to send readable message to the http caller
 * @param message
 * @param error
 * @param code
 * @param date
 */
public record ResponseMessage (String message, HttpStatus error, int code, LocalDateTime date){};
