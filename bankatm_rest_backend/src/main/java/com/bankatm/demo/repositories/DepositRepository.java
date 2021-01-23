package com.bankatm.demo.repositories;

import com.bankatm.demo.entities.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
    @Modifying
    @Query(value = "insert into deposit(amount, last_updated, cid) values (:amount, now(), :customer_id)", nativeQuery = true)
    @Transactional
    void depositAmount(@Param("customer_id") int customer_id, @Param("amount") double amount);
}
