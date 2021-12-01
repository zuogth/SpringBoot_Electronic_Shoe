package com.dth.spring_boot_shoe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Table(name = "material")
@Entity
@Data
public class MaterialEntity extends BaseEntity {
    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "code", length = 45)
    private String code;

    @ManyToMany(mappedBy = "materials")
    @JsonIgnore
    private Set<ProductEntity> products;
}