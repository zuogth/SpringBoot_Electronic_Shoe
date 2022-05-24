package com.dth.spring_boot_shoe.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "discount",indexes = {
        @Index(name = "fk_discount_event_idx", columnList = "event_id")
})
@Entity
@Data
public class DiscountEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discount")
    private Integer discount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private EventEntity event;
}
