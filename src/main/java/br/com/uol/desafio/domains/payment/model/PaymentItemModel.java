package br.com.uol.desafio.domains.payment.model;

import br.com.uol.desafio.domains.payment.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentItemModel {
    private String paymentId;
    private BigDecimal paymentValue;
    private PaymentStatus paymentStatus;
}
