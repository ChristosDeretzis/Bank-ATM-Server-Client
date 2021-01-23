package com.bankatm.demo.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "withdrawal")
public class Withdrawal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wid")
    private Long balance_id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "last_updated")
    private LocalDateTime last_updated;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cid")
    private Customer customer;
}