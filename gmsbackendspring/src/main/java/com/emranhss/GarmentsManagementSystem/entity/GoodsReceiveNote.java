package com.emranhss.GarmentsManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "goods_receive_notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsReceiveNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String grnNo;

    @Column(nullable = false)
    private LocalDate grnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    private String invoiceNo;

    private String challanNo;

    @Column(length = 1000)
    private String remarks;

    private Double grandTotal = 0.0;

    @OneToMany(
            mappedBy = "goodsReceiveNote",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<GoodsReceiveNoteItem> items = new ArrayList<>();

}
