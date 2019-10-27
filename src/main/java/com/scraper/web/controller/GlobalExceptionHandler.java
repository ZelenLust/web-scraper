package com.scraper.web.controller;

import com.scraper.web.dto.ErrorDTO;
import com.scraper.web.dto.ErrorItemDTO;
import com.scraper.web.exception.BaseException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<ErrorItemDTO> apiErrorItems = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> {
            ErrorItemDTO.ErrorItemDTOBuilder apiErrorItemBuilder = ErrorItemDTO.builder()
                    .message(fieldError.getDefaultMessage()).field(fieldError.getField());

            Optional.of(fieldError.getRejectedValue()).map(Object::toString).filter(value -> !value.isEmpty())
                    .ifPresent(apiErrorItemBuilder::rejectedValue);

            return apiErrorItemBuilder.build();
        }).collect(Collectors.toList());

        return ResponseEntity.badRequest().body(ErrorDTO.builder().status(BAD_REQUEST).errors(apiErrorItems).build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(ErrorDTO.builder().status(BAD_REQUEST).errors(getApiErrorItems(ex)).build());
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseAgSpaceException(BaseException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(ErrorDTO.builder().status(ex.getStatus()).errors(getApiErrorItems(ex)).build());
    }

    private List<ErrorItemDTO> getApiErrorItems(Exception ex) {
        return Optional.ofNullable(ex.getMessage()).filter(message -> !message.isEmpty())
                .map(message -> ErrorItemDTO.builder().message(message).build()).map(Collections::singletonList)
                .orElse(Collections.emptyList());
    }
}
