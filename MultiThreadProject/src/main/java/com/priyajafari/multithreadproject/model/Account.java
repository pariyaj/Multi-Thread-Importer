package com.priyajafari.multithreadproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "record_number")
    @NotNull
    private Integer recordNumber;

    @Column(name = "account_number", length = 22, nullable = false)
    @NotNull
    private String accountNumber;

    @Column(name = "account_type", nullable = false)
    @NotNull
    @Enumerated
    private AccountType accountType;

    @Column(name = "account_customer_id", nullable = false)
    @NotNull
    private Long accountCustomerId;

    @Column(name = "account_limit", nullable = false)
    @NotNull
    private Double accountLimit;

    @Column(name = "account_open_date", nullable = false)
    @NotNull
    private LocalDate accountOpenDate;

    @Column(name = "account_balance", nullable = false)
    @NotNull
    private String accountBalance;
}
