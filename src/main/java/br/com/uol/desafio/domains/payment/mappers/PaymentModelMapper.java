package br.com.uol.desafio.domains.payment.mappers;

import br.com.uol.desafio.controllers.Payment;
import br.com.uol.desafio.domains.payment.model.PaymentItemModel;
import br.com.uol.desafio.domains.payment.model.PaymentModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentModelMapper {

    public static PaymentModel mapToPaymentModel(Payment payment) {
        List<PaymentItemModel> paymentItems = payment.getPaymentItems().stream()
                .map(PaymentItemModelMapper::mapToPaymentItemModel)
                .collect(Collectors.toList());

        return PaymentModel.builder()
                .sellerId(payment.getClientId())
                .paymentItems(paymentItems).build();
    }

}
