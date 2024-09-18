package br.com.uol.desafio.domains.payment.usecases;

import br.com.uol.desafio.domains.payment.exceptions.PaymentRegisterNotFoundException;
import br.com.uol.desafio.infra.repositories.payment.PaymentRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyIfPaymentRegisterExistsUseCaseImpl implements VerifyIfPaymentRegisterExistsUseCase {

    @Autowired
    private PaymentRegisterRepository repository;

    @Override
    public void verifyIfPaymentRegisterExists(String id) {
        if (!repository.existsById(id)) {
            String message = String.format("Payment [%s] not found", id);
            throw new PaymentRegisterNotFoundException(message);
        }
    }
}
