package br.com.uol.desafio.domains.payment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {
    private String sellerId;
    private List<PaymentItemModel> paymentItems;
}
