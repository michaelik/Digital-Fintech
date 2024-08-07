package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "transaction_id"
    )
    long transactionId;

    @Column(
            name = "recipient_firstname"
    )
    String recipientName;

    @Column(
            name = "recipient_account"
    )
    String recipientAccount;

    @Column(
            name = "recipient_bank"
    )
    String recipientBank;

    @Column(
            name = "amount"
    )
    BigDecimal amount;
    @Column(
            name = "transaction_type"
    )
    String transactionType;

    @Column(
            name = "user_current_balance"
    )
    BigDecimal currentBalance;

    @ManyToOne(
            targetEntity = User.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "fk_user_id",
            referencedColumnName = "user_id"
    )
    User user;
    @ManyToOne(
            targetEntity = Account.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "fk_account_id",
            referencedColumnName = "account_id"
    )
    Account account;

    @Builder.Default
    @CreationTimestamp
    @Column(
            name = "createdAt",
            columnDefinition = "TIMESTAMP NOT NULL"
    )
    LocalDateTime timestamp = LocalDateTime.now();
}
