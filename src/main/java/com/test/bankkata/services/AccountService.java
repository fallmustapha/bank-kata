package com.test.bankkata.services;

import com.test.bankkata.exceptions.AccountNotFoundException;
import com.test.bankkata.exceptions.InvalidOperationException;
import com.test.bankkata.model.Account;
import com.test.bankkata.model.Statement;
import com.test.bankkata.repositories.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * This method is used to create a new account
     * @param account
     * @return the created account with all information completed
     */
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

    /**
     * Finds an account by the given number
     * @param accountNumber
     * @return
     */
    public Account findAccount(long accountNumber){
        if (accountNumber<=0)
            throw new IllegalArgumentException("invalid accountNumber");
        var account=accountRepository.findById(accountNumber)
                .orElseThrow(()->new AccountNotFoundException(String.format("Account %d not found",accountNumber)));
        return account;
    }

    /**
     * make a deposite in the account by retrieving it and increasing the balance
     * @param accountNumber
     * @param amount
     */
    public void makeDeposit(long accountNumber, BigDecimal amount){
        if (amount==null||amount.intValue()<=0 )
            throw new IllegalArgumentException("Invalid amount");
        if (accountNumber<=0)
            throw new IllegalArgumentException("invalid accountNumber");
        var account = findAccount(accountNumber);
        var newAccount = account.deposit(amount,new Statement(LocalDateTime.now(),amount,account.balance()));
        try {
            accountRepository.save(newAccount);
        }catch (Exception e){
            log.error(String.format("AccountService->createAccount :Error while updating the account %d to the database: %s",accountNumber,e.getMessage()));
            // TODO: Send monitoring to count database errors
            throw e;
        }

    }
    /**
     * make a withdrawal in the account by retrieving it and decreasing the balance
     * @param accountNumber
     * @param amount
     */
    // Je suppose que les documents ne sont pas volumineux en base c'est pourquoi je récupère l'objet avant de le mettre à jour
    // mais dans une situation reel avec des documents volumineux et aussi pour éviter de faire sortir des infos sensibles
    // qui ne concernant pas l'opération en cours, j'aurais fait une projection sur la collection avant de mettre à jour le document directment
    public void makeWithdraw(long accountNumber, BigDecimal amount){
        if (amount==null||amount.intValue()<=0 )
            throw new IllegalArgumentException("Invalid amount");
        if (accountNumber<=0)
            throw new IllegalArgumentException("invalid accountNumber");
        var account = findAccount(accountNumber);
        var newAccount = account.withdraw(amount,new Statement(LocalDateTime.now(),amount.negate(),account.balance()));
        try {
            accountRepository.save(newAccount);
        }
        catch(InvalidOperationException ex){
            throw ex;
        }
        catch (Exception e){
            log.error(String.format("AccountService->createAccount :Error while updating the account %d to the database: %s",accountNumber,e.getMessage()));
            // TODO: Send monitoring to count database errors
            throw e;
        }

    }

    /**
     * return Operation history
     * for each Opération when the amount is négativ then it's a withdrawal, if it's positive then it's a deposit
     * @param accountNumber
     * @return Statement
     */
    public Set<Statement> getHistory(long accountNumber){
        if (accountNumber<=0)
            throw new IllegalArgumentException("invalid accountNumber");
        var account=findAccount(accountNumber);
        return account.history();
    }
}
