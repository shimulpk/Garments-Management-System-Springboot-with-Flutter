package com.emranhss.GarmentsManagementSystem.entity;

import com.emranhss.GarmentsManagementSystem.enums.MaterialIssueStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "material_issues")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String issueNo;

    private LocalDate issueDate;

    private String department;

    private String requestedBy;

    private String remarks;

    @Enumerated(EnumType.STRING)
    private MaterialIssueStatus status;

    @OneToMany(
            mappedBy = "materialIssue",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MaterialIssueItem> items = new ArrayList<>();
}
