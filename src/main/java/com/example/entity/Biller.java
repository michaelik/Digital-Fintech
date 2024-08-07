package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_biller")
public class Biller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "biller_id"
    )
    private Long id;

    @Column(
            name = "biller_name"
    )
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private BillCategory category;
}
