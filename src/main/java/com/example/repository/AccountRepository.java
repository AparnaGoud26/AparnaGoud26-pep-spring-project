package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;
import com.example.entity.Message;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Message> findByUserId(int userId);
    Account findByUsername(String username);
    boolean existsByUsername(String username);


}
