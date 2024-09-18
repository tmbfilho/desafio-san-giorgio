package br.com.uol.desafio.domains.payment.usecases;

import br.com.uol.desafio.domains.payment.exceptions.PaymentRegisterNotFoundException;
import br.com.uol.desafio.infra.repositories.payment.PaymentRegisterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VerifyIfPaymentRegisterExistsUseCaseTest {

    @Mock
    private PaymentRegisterRepository repository;

    @InjectMocks
    private VerifyIfPaymentRegisterExistsUseCaseImpl usecase;

    @Test
    public void verifyIfPaymentRegisterExistsSuccess() {
        when(repository.existsById(anyString())).thenReturn(true);
        assertDoesNotThrow(() -> usecase.verifyIfPaymentRegisterExists("1234"));
    }

    @Test
    public void verifyIfPaymentRegisterExistsNotFound() {
        when(repository.existsById(anyString())).thenReturn(false);
        assertThrows(PaymentRegisterNotFoundException.class,
                () -> usecase.verifyIfPaymentRegisterExists("1234"));
    }
}
