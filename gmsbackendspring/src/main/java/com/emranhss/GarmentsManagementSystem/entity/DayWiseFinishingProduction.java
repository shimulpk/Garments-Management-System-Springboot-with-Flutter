package com.emranhss.GarmentsManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "day_wise_finishing_productions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayWiseFinishingProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//      FK -> Finishing Plan

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "finishing_plan_id",
            nullable = false
    )
    @JsonIgnore
    private FinishingPlan finishingPlan;

    @Column(nullable = false)
    private LocalDate date;


//   Today's Pass finishing Quantity
    private Integer passQty;

//    Today's Reject Quantity

    private Integer rejectQty;

    @Column(columnDefinition = "TEXT")
    private String remarks;

//    Auto Fill From Finishing Plan

    private String styleNo;

//    Auto Fill From Finishing Plan

    private String buyerName;

    private LocalDateTime createdAt;
}
