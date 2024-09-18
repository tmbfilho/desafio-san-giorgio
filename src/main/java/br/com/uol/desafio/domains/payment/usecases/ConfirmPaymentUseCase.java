package br.com.uol.desafio.domains.payment.usecases;


import br.com.uol.desafio.domains.payment.model.PaymentModel;

public interface ConfirmPaymentUseCase {
    PaymentModel confirm(PaymentModel paymentModel);
}
