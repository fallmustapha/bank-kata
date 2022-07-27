package com.test.bankkata.api;

import com.test.bankkata.api.dto.requests.AccountCreationDto;
import com.test.bankkata.api.dto.responses.ResponseMessage;
import com.test.bankkata.model.Account;
import com.test.bankkata.model.Customer;
import com.test.bankkata.model.Statement;
import com.test.bankkata.model.enums.AccountType;
import com.test.bankkata.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@EnableWebMvc
@RequestMapping("accounts")
public class Controller {

    private final AccountService service;

    @Autowired
    public Controller(AccountService service) {
        this.service = service;
    }

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
        var createdAccount = service.createAccount(account);
        return new ResponseEntity(createdAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{accountNumber}/deposit")
    public ResponseEntity makeDeposit(@PathVariable long accountNumber,@PathParam("amount") long amount){
        if(amount<=0){
            return new ResponseEntity<>(new ResponseMessage(
                    "the amount must be greatter than zero",
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    LocalDateTime.now()
            ),HttpStatus.BAD_REQUEST);
        }
        if(accountNumber<=0){
            return new ResponseEntity<>(new ResponseMessage(
                    "the account number is invalid",
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    LocalDateTime.now()
            ),HttpStatus.BAD_REQUEST);
        }
        service.makeDeposit(accountNumber,BigDecimal.valueOf(amount));
        return ResponseEntity.ok("The deposit of has successfully been processed");
    }

    @PutMapping("/{accountNumber}/withdraw")
    public ResponseEntity makeWithdraw(@PathVariable long accountNumber,@PathParam("amount") long amount){
        if(amount<=0){
            return new ResponseEntity<>(new ResponseMessage(
                    "the amount must be greatter than zero",
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    LocalDateTime.now()
            ),HttpStatus.BAD_REQUEST);
        }
        if(accountNumber<=0){
            return new ResponseEntity<>(new ResponseMessage(
                    "the account number is invalid",
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    LocalDateTime.now()
            ),HttpStatus.BAD_REQUEST);
        }
        service.makeWithdraw(accountNumber,BigDecimal.valueOf(amount));
        return ResponseEntity.ok("The debit of your account has successfully been processed");
    }

    @GetMapping("/{accountNumber}/history")
    public ResponseEntity getHistory(@PathVariable long accountNumber){
        if(accountNumber<=0){
            return new ResponseEntity<>(new ResponseMessage(
                    "the account number is invalid",
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    LocalDateTime.now()
            ),HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(service.getHistory(accountNumber));
    }
}
