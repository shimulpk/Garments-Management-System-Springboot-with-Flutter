package com.emranhss.GarmentsManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "production_lines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lineId;          // L-1, L-2, L-3

    private Integer capacityPerDay; // pieces per day

    private String supervisor;

    @OneToMany(mappedBy = "productionLine")
    private List<Machine> machines = new ArrayList<>();
}
