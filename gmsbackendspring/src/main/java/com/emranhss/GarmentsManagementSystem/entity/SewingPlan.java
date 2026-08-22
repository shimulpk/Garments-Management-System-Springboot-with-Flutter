package com.emranhss.GarmentsManagementSystem.entity;

import com.emranhss.GarmentsManagementSystem.enums.SewingPlanStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sewing_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SewingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sewingPlanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "cutting_plan_id",
            nullable = false
    )
    @JsonIgnore
    private CuttingPlan cuttingPlan;

    private String buyerName;

    private String orderNo;

    private String styleNo;

    private String color;

    private Integer inputReceivedQty;

    private Integer outputQty;

    private Integer rejectionQty;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SewingPlanStatus status;

    @OneToMany(
            mappedBy = "sewingPlan",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SewingTarget> targets =
            new ArrayList<>();
}
