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
@Table(name = "tbl_category")
public class BillCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "category_id"
    )
    private long id;

    @Column(
            name = "category_name"
    )
    private String name;
}
