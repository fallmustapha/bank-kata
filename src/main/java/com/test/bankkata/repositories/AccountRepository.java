package com.test.bankkata.repositories;


import com.test.bankkata.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account,Long> {
}
