package com.bankatm.demo.repositories;

import com.bankatm.demo.entities.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {

    @Query(value = "select sum(amount) as total_amount from withdrawal where cid = :customer_id and CAST(last_updated AS DATE) = CURRENT_DATE", nativeQuery = true)
    Double getDailyWithdrawalAmount(int customer_id);

    @Modifying
    @Query(value = "insert into withdrawal(amount, last_updated, cid) values (:amount, now(), :customer_id)", nativeQuery = true)
    @Transactional
    void withdrawAmount(@Param("customer_id") int customer_id, @Param("amount") double amount);
}
