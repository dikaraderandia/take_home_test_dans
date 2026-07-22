package com.dikara.dans.service.impl;

import com.dikara.dans.dto.request.EventRequest;
import com.dikara.dans.dto.response.EvenStatsResponse;
import com.dikara.dans.dto.response.EventRegistrationResponse;
import com.dikara.dans.dto.response.EventResponse;
import com.dikara.dans.dto.response.TopEventResponse;
import com.dikara.dans.entity.Event;
import com.dikara.dans.entity.EventRegistration;
import com.dikara.dans.entity.User;
import com.dikara.dans.exception.DuplicateResourceException;
import com.dikara.dans.exception.ResourceNotFoundException;
import com.dikara.dans.mapper.EventRegistrationMapper;
import com.dikara.dans.mapper.EventResponseMapper;
import com.dikara.dans.repository.EventRegistrationRepository;
import com.dikara.dans.repository.EventRepository;
import com.dikara.dans.repository.UserRepository;
import com.dikara.dans.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {
    private  final UserRepository userRepository;

    private  final EventRepository eventRepository;
    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventResponseMapper eventResponseMapper;
    private final EventRegistrationMapper eventRegistrationMapper;

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


    @Transactional(readOnly = true)
    @Override
    public EventResponse getEventById(Long eventId) {

        Event event = eventRepository.findById(eventId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Event not found"
                                )
                        );
        return eventResponseMapper.toResponse(event);
    }

    @Override
    public EventRegistrationResponse registerEvent(Long eventId, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        boolean registered = eventRegistrationRepository.existsByUserAndEvent(user,event);

        if (registered) {
            throw new DuplicateResourceException(
                    "User already registered to this event"
            );
        }

        EventRegistration eventRegistration = EventRegistration.builder()
                .user(user)
                .event(event)
                .registrationDate(LocalDateTime.now())
                .build();

        eventRegistration = eventRegistrationRepository.save(eventRegistration);
        return eventRegistrationMapper.toResponse(eventRegistration);
    }

    @Transactional(readOnly = true)
    @Override
    public EvenStatsResponse getStatistics() {

        List<Event> events = eventRepository.findAll();
        List<EventRegistration> registrations = eventRegistrationRepository.findAll();

        long totalEvents = events.size();
        long totalRegistrations = registrations.size();

        double averageParticipants =
                registrations.stream().collect(
                                Collectors.groupingBy(
                                        r -> r.getEvent().getId(),
                                        Collectors.counting()
                                )
                        )
                        .values()
                        .stream()
                        .mapToLong(Long::longValue)
                        .average()
                        .orElse(0);

        System.out.println("rata rata peserta per event: "+totalRegistrations/totalEvents);

        List<TopEventResponse> topEvents =
                registrations.stream()
                        .collect(
                                Collectors.groupingBy(
                                        EventRegistration::getEvent,
                                        Collectors.counting()
                                )
                        )
                        .entrySet()
                        .stream()
                        .sorted(
                                Map.Entry
                                        .<Event, Long>comparingByValue()
                                        .reversed()
                        )
                        .limit(3)
                        .map(entry -> TopEventResponse.builder()
                                .eventTitle(entry.getKey().getTitle())
                                .totalParticipants(entry.getValue())
                                .build())
                        .toList();



        return EvenStatsResponse.builder()
                .totalEvents(totalEvents)
                .totalRegistrations(totalRegistrations)
                .averageParticipants(averageParticipants)
                .topEventResponse(topEvents)
                .build();
    }
}
