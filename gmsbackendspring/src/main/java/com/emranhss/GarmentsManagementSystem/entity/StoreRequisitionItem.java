package com.emranhss.GarmentsManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store_requisition_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequisitionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_requisition_id")
    private StoreRequisition storeRequisition;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private Double quantity;


}
