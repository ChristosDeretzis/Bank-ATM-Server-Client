package com.bankatm.demo.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "balance")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid")
    private Long id;

    @Column(name = "balance")
    private double balance_amount;

    @Column(name = "last_updated")
    private LocalDateTime last_updated;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cid")
    private Customer customer;

    public double getBalance_amount() {
        return balance_amount;
    }

    public void setBalance_amount(double balance_amount) {
        this.balance_amount = balance_amount;
    }
}


