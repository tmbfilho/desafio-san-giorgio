package br.com.uol.desafio.domains.payment.usecases;


import br.com.uol.desafio.domains.payment.entities.PaymentRegister;
import br.com.uol.desafio.domains.payment.enums.PaymentStatus;
import br.com.uol.desafio.domains.payment.model.PaymentItemModel;
import br.com.uol.desafio.domains.payment.model.PaymentModel;
import br.com.uol.desafio.domains.seller.usecases.VerifyIfSellerExistsUseCase;
import br.com.uol.desafio.infra.queues.payment.PaymentProducerFactory;
import br.com.uol.desafio.infra.repositories.payment.PaymentRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ConfirmPaymentUseCaseImpl implements ConfirmPaymentUseCase {

    @Autowired
    private VerifyIfSellerExistsUseCase verifyIfSellerExistsUseCase;

    @Autowired
    private VerifyIfPaymentRegisterExistsUseCase verifyIfPaymentRegisterExistsUseCase;

    @Autowired
    private PaymentRegisterRepository repository;

    @Autowired
    private PaymentProducerFactory paymentProducerFactory;

    @Override
    public PaymentModel confirm(PaymentModel paymentModel) {

        verifyIfSellerExistsUseCase.verifyIfSellerExists(paymentModel.getSellerId());

        paymentModel.getPaymentItems().forEach(paymentItem ->
                verifyIfPaymentRegisterExistsUseCase.verifyIfPaymentRegisterExists(paymentItem.getPaymentId())
        );

        processItems(paymentModel);

        return paymentModel;
    }

    private void processItems(PaymentModel paymentModel) {
        for(PaymentItemModel paymentItem: paymentModel.getPaymentItems()) {
            PaymentRegister paymentRegister = repository.getReferenceById(paymentItem.getPaymentId());

            PaymentStatus paymentStatus = calcPaymentStatus(paymentRegister.getValue(), paymentItem.getPaymentValue());
            paymentItem.setPaymentStatus(paymentStatus);

            sendPayment(paymentItem);
        }
    }

    private void sendPayment(PaymentItemModel paymentItem) {
        paymentProducerFactory
                .createPaymentProducer(paymentItem.getPaymentStatus())
                .sendPayment(paymentItem);
    }

    private PaymentStatus calcPaymentStatus(BigDecimal registeredValue, BigDecimal informedValue) {
        if (registeredValue.equals(informedValue)) {
            return PaymentStatus.TOTAL;
        }
        if (informedValue.compareTo(registeredValue) > 0) {
            return PaymentStatus.EXCESS;
        }
        return PaymentStatus.PARTIAL;
    }

}
