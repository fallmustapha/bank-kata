package com.test.bankkata.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Statement(LocalDateTime date, BigDecimal amount, BigDecimal balance) {
}