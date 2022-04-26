package com.dth.spring_boot_shoe.entity;

import com.dth.spring_boot_shoe.security.provider.Provider;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Table(name = "users")
@Entity
@Setter
@Getter
public class UserEntity extends BaseEntity {
    @Column(name = "email", nullable = false, length = 45,unique = true)
    private String email;

    @Column(name = "firstname", length = 20)
    private String firstName;

    @Column(name = "lastname", length = 20)
    private String lastName;

    @Column(name = "fullname", length = 45)
    private String fullName;

    @Column(name = "slug", length = 45)
    private String slug;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status")
    private Integer status;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", length = 45)
    private Provider provider;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
                joinColumns=@JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> roles;
}