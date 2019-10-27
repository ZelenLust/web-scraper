package com.scraper.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {

    @Builder.Default
    private Date time = new Date();

    private HttpStatus status;

    private List<ErrorItemDTO> errors;

}

