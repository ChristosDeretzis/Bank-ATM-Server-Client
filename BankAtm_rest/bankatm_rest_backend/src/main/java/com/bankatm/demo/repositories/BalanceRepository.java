package com.bankatm.demo.repositories;

import com.bankatm.demo.entities.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    @Query(value = "select * from balance where cid =:customer_id", nativeQuery = true)
    Balance getBalance(int customer_id);

    @Modifying
    @Transactional
    @Query(value = "update balance set balance =:amount, last_updated = now() where cid =:customer_id", nativeQuery = true)
    void updateBalance(int customer_id, double amount);


}
