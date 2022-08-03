package com.example.assignmentt2009m1springboot.repository;

import com.example.assignmentt2009m1springboot.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findAccountByEmail(String email);

    Optional<Account> findByEmail(String email);
}
