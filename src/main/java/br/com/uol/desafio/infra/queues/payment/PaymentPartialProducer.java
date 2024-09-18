package br.com.uol.desafio.infra.queues.payment;

import org.springframework.stereotype.Service;

@Service
public class PaymentPartialProducer extends AbstractPaymentProducer {

    @Override
    protected String getQueueUrl() {
        return queuesConfig.getPaymentPartialUrl();
    }

}
