package com.dth.spring_boot_shoe.response;

import com.dth.spring_boot_shoe.entity.DiscountEntity;
import com.dth.spring_boot_shoe.entity.EventEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EventResponse {
    private Long id;
    private String url;
    private String name;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String description;
    private List<DiscountEntity> discounts;
    private String style;
    private Integer showWeb;

    public static EventResponse converter(EventEntity event,List<DiscountEntity> discounts){
        return EventResponse.builder().id(event.getId()).url(event.getUrl()).name(event.getName())
                .startAt(event.getStartAt()).endAt(event.getEndAt()).description(event.getDescription())
                .discounts(discounts).style(event.getStyle()).showWeb(event.getShowWeb()).build();
    }
}
