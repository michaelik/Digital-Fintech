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
@Table(name = "tbl_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "product_id"
    )
    private Long id;

    @Column(
            name = "product_name"
    )
    private String name;

    @ManyToOne
    @JoinColumn(name = "biller_id")
    private Biller biller;
}
