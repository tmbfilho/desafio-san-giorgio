package br.com.uol.desafio.domains.seller.exceptions;

import br.com.uol.desafio.exceptions.BusinessException;

public class SellerNotFoundException extends BusinessException {
    public SellerNotFoundException(String message) {
        super(message);
    }
}
