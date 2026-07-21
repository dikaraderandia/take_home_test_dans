package com.dikara.dans.service.impl;

import com.dikara.dans.dto.request.EventRequest;
import com.dikara.dans.dto.response.EventResponse;
import com.dikara.dans.entity.Event;
import com.dikara.dans.entity.User;
import com.dikara.dans.exception.ResourceNotFoundException;
import com.dikara.dans.mapper.EventResponseMapper;
import com.dikara.dans.repository.EventRepository;
import com.dikara.dans.repository.UserRepository;
import com.dikara.dans.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {
    private  final UserRepository userRepository;

    private  final EventRepository eventRepository;
    private final EventResponseMapper eventResponseMapper;

    @Override
    public EventResponse createEvent(EventRequest request, String email) {

        User admin =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );


        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .date(LocalDateTime.now())
                .createdBy(admin)
                .build();

        eventRepository.save(event);

        return eventResponseMapper.toResponse(event);

    }

    @Transactional(readOnly = true)
    @Override
    public List<EventResponse> getAllEvents() {

        return eventRepository.findAll()
                .stream()
                .map(eventResponseMapper::toResponse)
                .toList();
    }
}
