package com.emranhss.GarmentsManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "day_wise_cutting_production")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayWiseCuttingProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "cutting_plan_id",
            nullable = false
    )
    @JsonIgnore
    private CuttingPlan cuttingPlan;

    @Column(nullable = false)
    private LocalDate date;

    private Integer actualCutPieces;

    private Integer rejectPieces;

    private String styleNo;

    private String cuttingMaster;

    private LocalDateTime createdAt;
}
