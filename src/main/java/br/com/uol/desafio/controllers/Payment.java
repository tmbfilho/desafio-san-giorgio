package br.com.uol.desafio.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @JsonProperty("client_id")
    @NotBlank
    @Size(min=1, max=50)
    private String clientId;

    @JsonProperty("payment_items")
    @NotNull
    @Size(min=1)
    @Valid
    private List<PaymentItem> paymentItems;
}
