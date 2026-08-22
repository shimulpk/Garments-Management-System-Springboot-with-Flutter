package com.emranhss.GarmentsManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "new_rmc_check")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RmcCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================
    // Order Relation
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // ==========================
    // Style Relation
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "style_id", nullable = false)
    @JsonIgnore
    private BomStyle style;

    // Snapshot Information

    private String buyerName;

    private String styleName;

    private Integer totalOrderQty;

    private Double grandTotalCost;

    private LocalDateTime createdAt =
            LocalDateTime.now();

    // ==========================
    // Details
    // ==========================

    @OneToMany(

            mappedBy = "rmcCheck",

            cascade = CascadeType.ALL,

            orphanRemoval = true

    )
    private List<RmcDetails> rmcDetailsList =
            new ArrayList<>();



}
