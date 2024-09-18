package br.com.uol.desafio.domains.payment.mappers;

import br.com.uol.desafio.controllers.Payment;
import br.com.uol.desafio.controllers.PaymentItem;
import br.com.uol.desafio.domains.payment.model.PaymentModel;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentMapper {

    public static Payment mapToPaymentModel(PaymentModel model) {
        List<PaymentItem> paymentItems =
                model.getPaymentItems().stream().map(PaymentItemMapper::mapToPaymentItem)
                        .collect(Collectors.toList());

        return Payment.builder()
                .clientId(model.getSellerId())
                .paymentItems(paymentItems)
                .build();
    }

}
