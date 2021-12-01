package com.dth.spring_boot_shoe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Table(name = "roles")
@Entity
@Setter
@Getter
public class RoleEntity extends BaseEntity {
    @Column(name = "code", length = 10)
    private String code;

    @Column(name = "name", length = 45)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<UserEntity> users;
}