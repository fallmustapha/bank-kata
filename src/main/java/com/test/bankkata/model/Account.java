package com.test.bankkata.model;

import com.test.bankkata.exceptions.InvalidOperationException;
import com.test.bankkata.model.enums.AccountType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Document
public record Account(
        @Id
        long number,
        Customer owner,
        BigDecimal balance,
        Set<Statement> history,
        LocalDateTime creationDate,
        LocalDateTime lastOperationDate,
        AccountType type
        ) {
        @Override
        public Set<Statement> history() {
                return new HashSet<Statement>(history);
        }

        public Account deposit(BigDecimal amount,@Valid  Statement statement){
                if (statement==null)
                        throw new IllegalArgumentException("The statement shouldn't be null");
                var newHistory=new HashSet<Statement>(history);
                newHistory.add(statement);
                return new Account(number,owner,balance.add(amount),newHistory,creationDate,LocalDateTime.now(),type);
        }
}
