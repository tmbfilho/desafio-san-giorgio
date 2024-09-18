package br.com.uol.desafio.infra.queues.payment;

import br.com.uol.desafio.domains.payment.enums.PaymentStatus;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentProducerFactory {

    @Autowired
    private PaymentTotalProducer paymentTotalProducer;

    @Autowired
    private PaymentPartialProducer paymentPartialProducer;

    @Autowired
    private PaymentExcessProducer paymentExcessProducer;

    private final Map<PaymentStatus, PaymentProducer> paymentProducerMap
            = new HashMap<PaymentStatus, PaymentProducer>();

    @PostConstruct
    private void loadPaymentProducerMap() {
        paymentProducerMap.put(PaymentStatus.TOTAL, paymentTotalProducer);
        paymentProducerMap.put(PaymentStatus.PARTIAL, paymentPartialProducer);
        paymentProducerMap.put(PaymentStatus.EXCESS, paymentExcessProducer);
    }

    public PaymentProducer createPaymentProducer(PaymentStatus status) {
        PaymentProducer producer = paymentProducerMap.get(status);
        if (producer == null) {
            String message = String.format("There is no PaymentProducer configured for the PaymentStatus [%s]", status);
            throw new PaymentProducerNotFoundException(message);
        }
        return producer;
    }

}
