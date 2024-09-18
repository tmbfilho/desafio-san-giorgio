package br.com.uol.desafio.domains.seller.usecases;

import br.com.uol.desafio.domains.seller.exceptions.SellerNotFoundException;
import br.com.uol.desafio.infra.repositories.seller.SellerRepository;
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
public class VerifyIfSellerExistsUseCaseTest {

    @Mock
    private SellerRepository repository;

    @InjectMocks
    private VerifyIfSellerExistsUseCaseImpl usecase;

    @Test
    void verifyIfSellerExistsSuccess() {
        when(repository.existsById(anyString())).thenReturn(true);
        assertDoesNotThrow(() -> usecase.verifyIfSellerExists("1234"));
    }

    @Test
    void verifyIfSellerExistsNotFound() {
        when(repository.existsById(anyString())).thenReturn(false);
        assertThrows(SellerNotFoundException.class,
                () -> usecase.verifyIfSellerExists("1234"));
    }


}
