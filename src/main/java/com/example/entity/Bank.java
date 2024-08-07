package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "bank_id"
    )
    Long bankId;
    @Column(
            name = "bank_name"
    )
    String bankName;
    @OneToOne(
            mappedBy = "bank"
    )
    private Account account;
}
