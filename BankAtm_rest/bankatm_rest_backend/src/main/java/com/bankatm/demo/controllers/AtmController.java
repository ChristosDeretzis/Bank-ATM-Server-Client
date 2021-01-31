package com.bankatm.demo.controllers;

import com.bankatm.demo.entities.Balance;
import com.bankatm.demo.entities.Customer;
import com.bankatm.demo.services.AtmService;
import com.bankatm.demo.utils.Messages;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atm")
public class AtmController {
    public static final double WITHDRAWAL_LIMIT = 600;

    private AtmService atmService;

    public AtmController(AtmService atmService) {
        this.atmService = atmService;
    }

    @GetMapping("/authenticate")
    public Customer authenticateCustomer(@RequestParam(name = "username") String username,@RequestParam(name = "pin") int pin){
        Customer customer = atmService.authenticateCustomer(username, pin);

        if (customer == null){
            throw new CustomerNotFoundException("The customer was not found");
        }

        return customer;
    }

    @PutMapping("/{customerId}/change_PIN")
    public String changePIN(@PathVariable int customerId, @RequestParam(name = "new_pin") int new_pin){
        if (new_pin < 1000 || new_pin > 9999){
            return Messages.INVALID_PIN_ERROR;
        }

        int rows = atmService.updateCustomerPIN(customerId, new_pin);

        if (rows != 1){
            throw new CustomerNotFoundException(Messages.PIN_CHANGE_FAILURE_ERROR);
        }

        return Messages.PIN_CHANCE_SUCCESSION_MESSAGE;
    }

    @GetMapping("/{customerId}/show_balance")
    public double getCustomerBalance(@PathVariable int customerId){
        Balance balance = atmService.getCustomerBalance(customerId);

        if (balance == null){
            throw new CustomerNotFoundException("Customer was not found");
        }

        return balance.getBalance_amount();
    }

    @PostMapping("/{customerId}/withdraw")
    public String withdrawAmount(@PathVariable int customerId, @RequestParam(name = "amount") double amount){
        Double dailyAmount = atmService.getDailyWIthdrawalAmount(customerId);

        if (dailyAmount == null){
            dailyAmount = 0.0;
        }
        if (dailyAmount + amount> WITHDRAWAL_LIMIT){
            return Messages.WITHDRAWAL_DAILY_LIMIT_REACHED;
        }

        double currentBalance = atmService.getCustomerBalance(customerId).getBalance_amount();

        atmService.withdrawAmount(customerId, amount);
        atmService.updateBalance(customerId, amount - currentBalance);

        return Messages.WITHDRAWAL_SUCCESSION_MESSAGE;
    }

    @PostMapping("/{customerId}/deposit")
    public String depositAmount(@PathVariable int customerId, @RequestParam(name = "amount") double amount) {
        double currentBalance = atmService.getCustomerBalance(customerId).getBalance_amount();

        atmService.depositAmount(customerId, amount);
        atmService.updateBalance(customerId, amount + currentBalance);

        return Messages.DEPOSIT_SUCCESSION_MESSAGE;
    }


}
