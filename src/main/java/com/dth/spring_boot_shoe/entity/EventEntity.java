package com.dth.spring_boot_shoe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "events")
@Entity
@Data
public class EventEntity extends BaseEntity {
    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "slug", length = 45)
    private String slug;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "description")
    private String description;

    @Column(name = "show_web")
    private Integer showWeb;

    @Column(name = "style", length = 5)
    private String style;

    @Column(name = "banner", length = 45)
    private String banner;

    @Column(name = "url")
    private String url;

}