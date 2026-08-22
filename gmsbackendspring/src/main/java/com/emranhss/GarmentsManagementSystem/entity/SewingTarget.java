package com.emranhss.GarmentsManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sewing_targets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SewingTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_line_id",
            nullable = false)
    @JsonIgnore
    private ProductionLine productionLine;

    private Integer targetQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sewing_plan_id",
            nullable = false)
    @JsonIgnore
    private SewingPlan sewingPlan;
}
