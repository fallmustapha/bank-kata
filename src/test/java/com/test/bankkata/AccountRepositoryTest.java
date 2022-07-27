package com.test.bankkata;

import com.test.bankkata.model.Account;
import com.test.bankkata.model.Customer;
import com.test.bankkata.model.enums.AccountType;
import com.test.bankkata.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;


@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository repository;

    @BeforeEach
    public void prepare(){
        repository.deleteAll();
    }

    @Test
    public void createAccountShouldBeOk(){
        var account = new Account(1,new Customer("test","test"), BigDecimal.ZERO, Set.of(), LocalDateTime.now(),null, AccountType.RUNNING);
        var result = repository.save(account);
        assertThat(result).isNotNull();
        assertThat(repository.findById(1l)).isNotNull();
    }

    @Test
    public void updateAccountShouldBeOk(){
        var account = new Account(1,new Customer("test","test"), BigDecimal.ZERO, Set.of(), LocalDateTime.now(),null, AccountType.RUNNING);
        var result = repository.save(account);

        assertThat(result).isNotNull();
        assertThat(repository.findById(1l)).isNotNull();

        var accountEdited= new Account(1,new Customer("test","test"), BigDecimal.valueOf(200), Set.of(), LocalDateTime.now(),null, AccountType.RUNNING);
        var editedResult = repository.save(accountEdited);
        var listOfAccount= StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList());
        assertThat(editedResult.balance().doubleValue()).isEqualTo(200);
        assertThat(listOfAccount.size()).isEqualTo(1);
    }


}
