package br.com.uol.desafio.infra.queues.payment;

import br.com.uol.desafio.domains.payment.model.PaymentItemModel;

public interface PaymentProducer {

    void sendPayment(PaymentItemModel paymentItem);

}
