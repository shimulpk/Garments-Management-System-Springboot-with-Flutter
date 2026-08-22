package com.emranhss.GarmentsManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "bom_views")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BomView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer serial;

    private String materialName;

    private String unit;

    private String baseFabric;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "style_id", nullable = false)
    @JsonIgnore
    private BomStyle style;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalCost;

}
