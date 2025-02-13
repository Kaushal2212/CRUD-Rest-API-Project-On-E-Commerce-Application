package com.example.rom;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.customerid = :id")
    UserWithNoPassword findUserWithNoPassword(@Param("id") Long id);

    @Query("SELECT c FROM Customer c")
    List<UserWithNoPassword> findAllUsersWithNoPassword();
}

