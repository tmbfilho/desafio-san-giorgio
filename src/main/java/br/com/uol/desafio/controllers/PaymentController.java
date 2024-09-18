package br.com.uol.desafio.controllers;


import br.com.uol.desafio.domains.payment.mappers.PaymentMapper;
import br.com.uol.desafio.domains.payment.mappers.PaymentModelMapper;
import br.com.uol.desafio.domains.payment.model.PaymentModel;
import br.com.uol.desafio.domains.payment.usecases.ConfirmPaymentUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentController {

    @Autowired
    private final ConfirmPaymentUseCase confirmPaymentUseCase;

    @PutMapping(path = "/api/payment")
    public ResponseEntity<Payment> setPayment(@RequestBody @Valid Payment request) {

        PaymentModel paymentRequest = PaymentModelMapper.mapToPaymentModel(request);

        PaymentModel paymentResponse = confirmPaymentUseCase.confirm(paymentRequest);

        Payment response = PaymentMapper.mapToPaymentModel(paymentResponse);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
