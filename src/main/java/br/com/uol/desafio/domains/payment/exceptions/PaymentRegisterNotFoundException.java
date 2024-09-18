package br.com.uol.desafio.domains.payment.exceptions;

import br.com.uol.desafio.exceptions.BusinessException;

public class PaymentRegisterNotFoundException extends BusinessException {
    public PaymentRegisterNotFoundException(String message) {
        super(message);
    }
}
