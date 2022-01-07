package com.dth.spring_boot_shoe.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "comments", indexes = {
        @Index(name = "fk_user_comments_idx", columnList = "user_id"),
        @Index(name = "fk_prod_comments_idx", columnList = "product_id")
})
@Entity
@Data
public class CommentEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "title",length = 45)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "star")
    private Integer star;
}