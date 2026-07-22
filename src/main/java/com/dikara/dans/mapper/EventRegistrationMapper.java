package com.dikara.dans.mapper;

import com.dikara.dans.dto.response.EventRegistrationResponse;
import com.dikara.dans.entity.EventRegistration;
import org.springframework.stereotype.Component;

@Component
public class EventRegistrationMapper {




    public EventRegistrationResponse toResponse (EventRegistration event){
        return EventRegistrationResponse.builder()
                .registrationId(event.getId())
                .eventId(event.getEvent().getId())
                .eventTitle(event.getEvent().getTitle())
                .registrationDate(event.getRegistrationDate())
                .build();
    }
}
