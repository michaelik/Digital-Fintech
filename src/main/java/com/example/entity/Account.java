package com.example.entity;

import com.example.enums.AccountCurrency;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    long accountId;

    @Column(
            name = "account_number"
    )
    String accountNumber;

    @Column(
            name = "balance",
            precision = 19,
            scale = 2
    )
    BigDecimal balance;

    @Enumerated(EnumType.STRING)
    AccountCurrency currency;

    @OneToOne(
            targetEntity = Bank.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "fk_bank_id",
            referencedColumnName = "bank_id"
    )
    private Bank bank;

    @OneToOne(mappedBy = "account")
    User user;

    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    List<Transaction> transaction;
}
