package br.com.uol.desafio.infra.queues.payment;

public class PaymentProducerNotFoundException extends RuntimeException {
    public PaymentProducerNotFoundException(String message) {
        super(message);
    }
}
