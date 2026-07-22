package com.dikara.dans.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRegistrationResponse {

    private Long registrationId;
    private Long eventId;
    private String eventTitle;
    private String participantName;
    private LocalDateTime registrationDate;
}
