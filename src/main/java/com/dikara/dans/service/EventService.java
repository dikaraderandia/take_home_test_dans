package com.dikara.dans.service;

import com.dikara.dans.dto.request.EventRequest;
import com.dikara.dans.dto.response.EventResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventService {
    EventResponse createEvent(EventRequest request, String email);

    @Transactional(readOnly = true)
    List<EventResponse> getAllEvents();
}
