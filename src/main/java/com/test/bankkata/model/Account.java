package com.test.bankkata.model;

import com.test.bankkata.model.enums.AccountType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Document
public record Account(
        long number,
        Customer owner,
        BigDecimal balance,
        Set<Statement> history,
        LocalDateTime creationDate,
        LocalDateTime lastOperationDate,
        AccountType type
        ) {
}
