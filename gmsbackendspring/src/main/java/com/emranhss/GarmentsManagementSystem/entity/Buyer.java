package com.emranhss.GarmentsManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import javax.swing.text.Style;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "buyers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

    @Column(unique = true)
    private String buyerCode;

    private String buyerName;

    private String country;

    private String address;

    private String website;

    private String currency;

    private String paymentTerms;

    private Boolean active;

    @OneToMany(
            mappedBy = "buyer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<BuyerContact> contacts = new ArrayList<>();

    //    new Add
    @OneToMany(
            mappedBy = "buyer",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
   
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<BomStyle> styles = new ArrayList<>();
}
