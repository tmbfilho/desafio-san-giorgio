package br.com.uol.desafio.domains.payment.mappers;

import br.com.uol.desafio.controllers.PaymentItem;
import br.com.uol.desafio.domains.payment.model.PaymentItemModel;


public class PaymentItemMapper {
    public static PaymentItem mapToPaymentItem(PaymentItemModel model) {
       return PaymentItem.builder()
                .paymentId(model.getPaymentId())
                .paymentValue(model.getPaymentValue())
                .paymentStatus(model.getPaymentStatus().toString())
                .build();
    }
}
