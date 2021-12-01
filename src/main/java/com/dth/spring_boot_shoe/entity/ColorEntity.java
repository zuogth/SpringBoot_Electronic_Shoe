package com.dth.spring_boot_shoe.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "color")
@Entity
@Data
public class ColorEntity extends BaseEntity {
    @Column(name = "name", length = 45)
    private String name;
}