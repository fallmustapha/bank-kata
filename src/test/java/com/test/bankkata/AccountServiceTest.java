package com.test.bankkata;

import com.test.bankkata.model.Account;
import com.test.bankkata.model.Customer;
import com.test.bankkata.model.enums.AccountType;
import com.test.bankkata.repositories.AccountRepository;
import com.test.bankkata.services.AccountService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.assertj.core.api.*;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountService accountService;


    @Test
    public void createAccountShouldSucceed(){
        //given
        var account = new Account(0,new Customer("test","test"), BigDecimal.ZERO, Set.of(), LocalDateTime.now(),null, AccountType.RUNNING);
        when(repository.save(account)).thenReturn(account);

        //when
        var createdAccount=accountService.createAccount(account);

        //then
        assertThat(Collections.singletonList(createdAccount)).isNotNull();
        Mockito.verify(repository,times(1)).save(account);
    }

    @Test
    public void createAccountShouldThrowExceptionWhenAccountIsNull(){
        assertThatThrownBy(() -> {
            accountService.createAccount(null);
        }).isInstanceOf(AssertionError.class)
                .hasMessageContaining("Account object is required");
    }

    @Test
    public void createAccountShouldThrowExceptionWhenRepositoryThrowsException(){
        var account = new Account(0,new Customer("test","test"), BigDecimal.ZERO, Set.of(), LocalDateTime.now(),null, AccountType.RUNNING);

        when(repository.save(account)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> {
            accountService.createAccount(account);
        }).isInstanceOf(RuntimeException.class);
    }
    @Test
    public void findAccountShouldReturnValidAccount(){
        var accountNumber= 64564l;
        var account = new Account(accountNumber,new Customer("test","test"), BigDecimal.ZERO, Set.of(), LocalDateTime.now(),null, AccountType.RUNNING);
        when(repository.findById(accountNumber)).thenReturn(Optional.of(account));
        var foundAccount=accountService.findAccount(accountNumber);
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.number()).isEqualTo(accountNumber);
    }

    @Test
    public void findAccountShouldThrowsExceptionWhenAccountNumberInvalid(){
        var accountNumber= -1l;
        assertThatThrownBy(() -> {
            accountService.findAccount(accountNumber);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    //Make deposit
    @Test
    public void depositAccountShouldThrowsExceptionWhenAccountNumberInvalid(){
        var accountNumber= -1l;
        assertThatThrownBy(() -> {
            accountService.makeDeposit(accountNumber,BigDecimal.ONE);
        }).isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void depositAccountShouldThrowsExceptionWhenAmountInvalid(){
        var accountNumber= 1l;
        assertThatThrownBy(() -> {
            accountService.makeDeposit(accountNumber,null);
        }).isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void makeDepositAccountShouldBeOk(){
        var accountNumber= 64564l;
        var account = new Account(accountNumber,new Customer("test","test"), BigDecimal.ZERO, Set.of(), LocalDateTime.now(),null, AccountType.RUNNING);
        when(repository.findById(accountNumber)).thenReturn(Optional.of(account));
        when(repository.save(any(Account.class))).thenReturn(account);
        accountService.makeDeposit(accountNumber,BigDecimal.valueOf(200));
        Mockito.verify(repository,times(1)).save(any(Account.class));
        Mockito.verify(repository,times(1)).findById(anyLong());
    }

    @Test
    public void depositAccountShouldThrowsExceptionWhenTheRepositoryThrowsException(){
        var accountNumber= 1l;
        var account = new Account(accountNumber,new Customer("test","test"), BigDecimal.ZERO, Set.of(), LocalDateTime.now(),null, AccountType.RUNNING);
        when(repository.findById(accountNumber)).thenReturn(Optional.of(account));
        when(repository.save(any(Account.class))).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> {
            accountService.makeDeposit(accountNumber,BigDecimal.valueOf(200));
        }).isInstanceOf(RuntimeException.class);
    }
}