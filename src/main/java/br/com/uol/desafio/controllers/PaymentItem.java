package br.com.uol.desafio.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentItem {

    @JsonProperty("payment_id")
    @NotBlank
    @Size(min=1, max=50)
    private String paymentId;

    @JsonProperty("payment_value")
    @NotNull
    private BigDecimal paymentValue;

    @JsonProperty("payment_status")
    private String paymentStatus;
}
