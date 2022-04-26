package com.dth.spring_boot_shoe.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "size")
@Entity
@Data
public class SizeEntity extends BaseEntity {
    @Column(name = "name",unique = true)
    private Integer name;
}