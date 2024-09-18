package br.com.uol.desafio.domains.seller.usecases;


import br.com.uol.desafio.domains.seller.exceptions.SellerNotFoundException;
import br.com.uol.desafio.infra.repositories.seller.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyIfSellerExistsUseCaseImpl implements VerifyIfSellerExistsUseCase {

    @Autowired
    private SellerRepository repository;

    @Override
    public void verifyIfSellerExists(String id) {
        if (!repository.existsById(id)) {
            String message = String.format("Seller [%s] not found", id);
            throw new SellerNotFoundException(message);
        }
    }
}
