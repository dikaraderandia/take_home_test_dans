package com.dikara.dans.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;


}
