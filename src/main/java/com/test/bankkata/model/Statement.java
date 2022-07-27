package com.test.bankkata.model;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Statement(@NotNull LocalDateTime date,@NotNull BigDecimal amount,@NotNull BigDecimal balance) {
}