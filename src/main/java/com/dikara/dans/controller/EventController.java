package com.dikara.dans.controller;

import com.dikara.dans.common.ApiResponse;
import com.dikara.dans.common.ResponseUtil;
import com.dikara.dans.dto.request.EventRequest;
import com.dikara.dans.dto.response.EvenStatsResponse;
import com.dikara.dans.dto.response.EventRegistrationResponse;
import com.dikara.dans.dto.response.EventResponse;
import com.dikara.dans.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EventResponse>> createEvent(@Valid @RequestBody EventRequest request, Authentication authentication) {

        EventResponse response =
                eventService.createEvent(
                        request,
                        authentication.getName()
                );

        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(
                ResponseUtil.created(
                        "Event created successfully",
                        response
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EventResponse>>> getAllEvents() {

        return ResponseEntity.ok(
                ResponseUtil.success(
                        "Success",
                        eventService.getAllEvents()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> getEventById(@PathVariable Long id) {

        return ResponseEntity.ok(
                ResponseUtil.success(
                        "Success",
                        eventService.getEventById(id)
                )
        );
    }

    @PostMapping("/{id}/register")
    public ResponseEntity<ApiResponse<EventRegistrationResponse>> registerEvent(
            @PathVariable Long id,
            Authentication authentication
    ) {

        EventRegistrationResponse response = eventService.registerEvent(id, authentication.getName());

        return ResponseEntity.ok(
                ResponseUtil.success(
                        "Registration success",
                        response
                )
        );
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<EvenStatsResponse>> getStatistics() {

        return ResponseEntity.ok(
                ResponseUtil.success(
                        "Success",
                        eventService.getStatistics()
                )
        );
    }
}
