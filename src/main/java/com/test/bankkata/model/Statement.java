package com.test.bankkata.model;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * This record represent a statement or un operation
 * when the amount is negative then it's a withdrawal when it's positive, it's a deposit
 * @param date
 * @param amount
 * @param balance
 */
public record Statement(@NotNull LocalDateTime date,@NotNull BigDecimal amount,@NotNull BigDecimal balance) {
}