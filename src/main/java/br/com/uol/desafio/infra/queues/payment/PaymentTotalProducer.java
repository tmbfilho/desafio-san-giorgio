package br.com.uol.desafio.infra.queues.payment;

import org.springframework.stereotype.Service;

@Service
public class PaymentTotalProducer extends AbstractPaymentProducer {

    @Override
    protected String getQueueUrl() {
        return queuesConfig.getPaymentTotalUrl();
    }
}
