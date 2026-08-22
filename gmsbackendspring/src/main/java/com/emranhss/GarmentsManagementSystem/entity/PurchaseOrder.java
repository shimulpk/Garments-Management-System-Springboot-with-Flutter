package com.emranhss.GarmentsManagementSystem.entity;

import com.emranhss.GarmentsManagementSystem.enums.PurchaseOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String poNo;

    @Column(nullable = false)
    private LocalDate poDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    // Approved Store Requisition
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_requisition_id", nullable = false)
    private StoreRequisition storeRequisition;

    @Enumerated(EnumType.STRING)
    @Column
    private PurchaseOrderStatus status;

    @Column(nullable = false)
    private Double grandTotal = 0.0;

    @Column(length = 1000)
    private String remarks;

    @OneToMany(
            mappedBy = "purchaseOrder",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PurchaseOrderItem> items = new ArrayList<>();
}
