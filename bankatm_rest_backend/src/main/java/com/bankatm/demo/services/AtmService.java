package com.bankatm.demo.services;

import com.bankatm.demo.entities.Balance;
import com.bankatm.demo.entities.Customer;
import com.bankatm.demo.repositories.BalanceRepository;
import com.bankatm.demo.repositories.CustomerRepository;
import com.bankatm.demo.repositories.DepositRepository;
import com.bankatm.demo.repositories.WithdrawalRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AtmService {

    private BalanceRepository balanceRepository;
    private CustomerRepository customerRepository;
    private WithdrawalRepository withdrawalRepository;
    private DepositRepository depositRepository;

    public AtmService(BalanceRepository balanceRepository, CustomerRepository customerRepository, WithdrawalRepository withdrawalRepository, DepositRepository depositRepository) {
        this.balanceRepository = balanceRepository;
        this.customerRepository = customerRepository;
        this.withdrawalRepository = withdrawalRepository;
        this.depositRepository = depositRepository;
    }

    public Customer authenticateCustomer(String username, int PIN){
        Customer customer = customerRepository.authenticateCustomer(username, PIN);
        return customer;
    }


    public int updateCustomerPIN(int customer_id, int pin) {
        return customerRepository.updateCustomerPin(customer_id, pin);
    }

    public Balance getCustomerBalance(int customer_id) {
        return balanceRepository.getBalance(customer_id);
    }

    public void updateBalance(int customer_id, double amount) {
        balanceRepository.updateBalance(customer_id, amount);
    }

    public void withdrawAmount(int customer_id, double amount) {
        withdrawalRepository.withdrawAmount(customer_id, amount);
    }

    public Double getDailyWIthdrawalAmount(int customer_id) {
        return withdrawalRepository.getDailyWithdrawalAmount(customer_id);
    }

    public void depositAmount(int customer_id, double amount) {
        depositRepository.depositAmount(customer_id, amount);
    }
}
