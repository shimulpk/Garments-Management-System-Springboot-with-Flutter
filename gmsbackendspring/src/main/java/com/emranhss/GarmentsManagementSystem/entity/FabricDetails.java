package com.emranhss.GarmentsManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "fabrics_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FabricDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private String size;

    private String type;

    private BigDecimal baseFabric;

    private Integer qty;

    private BigDecimal calculatedFabric;

    private Boolean hasUom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rmc_id")
    @JsonIgnore
    private FabricsCheck fabricsCheck;
}
