package com.test.bankkata.api;

import com.test.bankkata.api.dto.requests.AccountCreationDto;
import com.test.bankkata.model.Account;
import com.test.bankkata.model.Customer;
import com.test.bankkata.model.enums.AccountType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@EnableWebMvc
@RequestMapping("accounts")
public class Controller {


    @PostMapping(consumes = {"application/json"})
    public ResponseEntity createAccount(@Valid @RequestBody  AccountCreationDto accountDto){
        var account = new Account(
                0,
                new Customer(accountDto.getFirstName(), accountDto.getLastName()),
                BigDecimal.ZERO,
                Set.of(),
                LocalDateTime.now(),
                null,
                AccountType.valueOf(accountDto.getType().toUpperCase())
                );
        return new ResponseEntity(account, HttpStatus.CREATED);
    }
}
