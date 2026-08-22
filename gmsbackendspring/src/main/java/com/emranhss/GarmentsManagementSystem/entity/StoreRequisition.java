package com.emranhss.GarmentsManagementSystem.entity;

import com.emranhss.GarmentsManagementSystem.enums.StoreRequisitionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store_requisitions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequisition {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String prNo;

    private LocalDate requisitionDate;

    private String requestedBy;

    private String department;

    private String remarks;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreRequisitionStatus status;

    @OneToMany(
            mappedBy = "storeRequisition",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<StoreRequisitionItem> items = new ArrayList<>();

}
