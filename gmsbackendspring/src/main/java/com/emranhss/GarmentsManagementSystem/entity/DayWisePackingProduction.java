package com.emranhss.GarmentsManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "day_wise_packing_productions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayWisePackingProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    FK → Packing Plan

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packing_plan_id", nullable = false)
    @JsonIgnore
    private PackingPlan packingPlan;

    @Column(nullable = false)
    private LocalDate date;


    private Integer todayPackedQty;

//  Auto Calculate ceil(todayPackedQty / pcsPerCarton)

    private Integer todayPackedCartons;


    private Integer todayRejectQty;

//   Display Purpose

    private String buyerName;

    private String orderNo;

    private String styleNo;

    private LocalDateTime createdAt;
}
