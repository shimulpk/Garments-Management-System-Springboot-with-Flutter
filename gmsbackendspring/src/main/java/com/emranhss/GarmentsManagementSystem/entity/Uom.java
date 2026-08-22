package com.emranhss.GarmentsManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "uoms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Uom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private String size;

    private BigDecimal body;

    private BigDecimal sleeve;

    private BigDecimal pocket;

    private BigDecimal wastage;

    private BigDecimal shrinkage;

    private BigDecimal totalBaseFabric;
}
