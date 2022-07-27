package com.test.bankkata.api.dto.responses;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ResponseMessage (String message, HttpStatus error, int code, LocalDateTime date){};
