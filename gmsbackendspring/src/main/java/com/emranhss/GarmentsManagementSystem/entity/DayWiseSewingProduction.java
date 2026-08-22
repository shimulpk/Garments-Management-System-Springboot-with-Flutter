package com.emranhss.GarmentsManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "day_wise_sewing_productions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayWiseSewingProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//      FK  Sewing Plan

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sewing_plan_id",
            nullable = false
    )
    @JsonIgnore
    private SewingPlan sewingPlan;


//   Production Line
//      Example: L-1,L-2

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "production_line_id",
            nullable = false
    )
    @JsonIgnore
    private ProductionLine productionLine;

    private LocalDate date;


//  Actual Sewing
    private Integer achievedQuantity;

//     Reject Sewing

    private Integer rejectionQty;


//     Auto From Sewing Plan

    private String styleNo;

    //     Auto From Sewing Plan
    private String orderNo;

    private LocalDateTime createdAt;
}
