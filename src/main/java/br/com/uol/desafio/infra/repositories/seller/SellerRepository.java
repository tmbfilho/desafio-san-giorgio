package br.com.uol.desafio.infra.repositories.seller;

import br.com.uol.desafio.domains.seller.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, String> {

    boolean existsById(String id);

}
