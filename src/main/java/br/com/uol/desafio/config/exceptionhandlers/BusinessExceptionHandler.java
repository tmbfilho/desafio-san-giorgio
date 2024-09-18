package br.com.uol.desafio.config.exceptionhandlers;


import br.com.uol.desafio.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleValidationExceptions(BusinessException ex) {

        Map<String, String> errors = new HashMap<>();
        errors.put("msg", ex.getMessage());

        ErrorDetailResponse error = new ErrorDetailResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                errors);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}