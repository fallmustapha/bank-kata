package com.test.bankkata.services;

import com.test.bankkata.model.Account;
import com.test.bankkata.repositories.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account){
        assert Objects.nonNull(account):"Account object is required";
        Account savedAccount=null;
        try{
            savedAccount=accountRepository.save(account);

        }catch (Exception exception){
            log.error(String.format("AccountService->createAccount :Error while saving account to the database: %s",exception.getMessage()));
            // TODO: Send monitoring to count database errors
            throw exception;
        }
        return savedAccount;
    }
}
