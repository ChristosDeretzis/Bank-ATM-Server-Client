package com.bankatm.demo.repositories;

import com.bankatm.demo.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "select username, first_name, last_name, pin, id from customer where username =:username and pin =:pin", nativeQuery = true)
    Customer authenticateCustomer(String username, int pin);

    @Modifying
    @Transactional
    @Query(value = "update customer set PIN =:new_pin where id =:customer_id", nativeQuery = true)
    int updateCustomerPin(int customer_id, int new_pin);
}
