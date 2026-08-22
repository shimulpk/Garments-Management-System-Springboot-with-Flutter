package com.emranhss.GarmentsManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bom_styles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BomStyle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String styleCode;

    @Column(nullable = false)
    private String styleName;

    private String styleType;

    @Column(length = 1000)
    private String description;

    private String approvalStatus;

    private String sizeSet;

    private Boolean active;

//    new Add

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = true)
    @JsonIgnoreProperties({"styles", "contacts"})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Buyer buyer;

}
