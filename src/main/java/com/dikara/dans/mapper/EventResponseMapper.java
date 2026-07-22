package com.dikara.dans.mapper;

import com.dikara.dans.dto.response.EventResponse;
import com.dikara.dans.entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventResponseMapper {

    public EventResponse toResponse (Event event){
        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .date(event.getDate())
                .createdBy(
                        event.getCreatedBy().getName()
                )
                .build();
    }


}
