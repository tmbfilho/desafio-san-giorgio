package br.com.uol.desafio.domains.payment.mappers;

import br.com.uol.desafio.controllers.PaymentItem;
import br.com.uol.desafio.domains.payment.model.PaymentItemModel;


public class PaymentItemModelMapper {
    public static PaymentItemModel mapToPaymentItemModel(PaymentItem payment) {
       return PaymentItemModel.builder()
                .paymentId(payment.getPaymentId())
                .paymentValue(payment.getPaymentValue())
                .build();
    }
}
