package br.com.uol.desafio.config.exceptionhandlers;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorDetailResponse(LocalDateTime timestamp, int httpStatus, Map<String, String> errors) {
}
