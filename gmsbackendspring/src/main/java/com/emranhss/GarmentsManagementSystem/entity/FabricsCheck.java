package com.emranhss.GarmentsManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fabric-check")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FabricsCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "style_id")
    @JsonIgnore
    private BomStyle style;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    private String orderCode;

    private String poNumber;

    private LocalDateTime createdAt;

    private BigDecimal totalFabricRequired;

    @OneToMany(
            mappedBy = "fabricsCheck",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FabricDetails> details = new ArrayList<>();
}
