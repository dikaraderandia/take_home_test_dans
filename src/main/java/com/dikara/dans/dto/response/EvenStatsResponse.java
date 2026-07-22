package com.dikara.dans.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvenStatsResponse {
    private Long totalEvents;
    private Long totalRegistrations;
    private Double averageParticipants;
    private List<TopEventResponse> topEventResponse;

}
