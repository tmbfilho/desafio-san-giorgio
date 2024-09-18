package br.com.uol.desafio.infra.repositories.payment;

import br.com.uol.desafio.domains.payment.entities.PaymentRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRegisterRepository extends JpaRepository<PaymentRegister, String> {
}
