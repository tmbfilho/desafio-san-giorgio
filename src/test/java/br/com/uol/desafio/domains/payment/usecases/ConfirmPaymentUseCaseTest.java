package br.com.uol.desafio.domains.payment.usecases;

import br.com.uol.desafio.domains.payment.entities.PaymentRegister;
import br.com.uol.desafio.domains.payment.enums.PaymentStatus;
import br.com.uol.desafio.domains.payment.model.PaymentItemModel;
import br.com.uol.desafio.domains.payment.model.PaymentModel;
import br.com.uol.desafio.domains.seller.usecases.VerifyIfSellerExistsUseCaseImpl;
import br.com.uol.desafio.infra.queues.payment.PaymentExcessProducer;
import br.com.uol.desafio.infra.queues.payment.PaymentPartialProducer;
import br.com.uol.desafio.infra.queues.payment.PaymentProducerFactory;
import br.com.uol.desafio.infra.queues.payment.PaymentTotalProducer;
import br.com.uol.desafio.infra.repositories.payment.PaymentRegisterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ConfirmPaymentUseCaseTest {

    @Mock
    private PaymentRegisterRepository repository;

    @Mock
    private VerifyIfSellerExistsUseCaseImpl verifyIfSellerExistsUseCase;

    @Mock
    private VerifyIfPaymentRegisterExistsUseCaseImpl verifyIfPaymentRegisterExistsUseCase;

    @Mock
    private PaymentProducerFactory paymentProducerFactory;

    @Mock
    private PaymentTotalProducer paymentTotalProducer;

    @Mock
    private PaymentPartialProducer paymentPartialProducer;

    @Mock
    private PaymentExcessProducer paymentExcessProducer;

    @InjectMocks
    private ConfirmPaymentUseCaseImpl usecase;

    private PaymentItemModel paymentItem500;
    private PaymentItemModel paymentItem1000;
    private PaymentItemModel paymentItem1500;

    @BeforeEach
    public void setup() {
        lenient().when(paymentProducerFactory.createPaymentProducer(PaymentStatus.TOTAL)).thenReturn(paymentTotalProducer);
        lenient().when(paymentProducerFactory.createPaymentProducer(PaymentStatus.PARTIAL)).thenReturn(paymentPartialProducer);
        lenient().when(paymentProducerFactory.createPaymentProducer(PaymentStatus.EXCESS)).thenReturn(paymentExcessProducer);

        paymentItem500 = PaymentItemModel.builder()
                .paymentId("1")
                .paymentValue(new BigDecimal(500))
                .build();

        paymentItem1000 = PaymentItemModel.builder()
                .paymentId("1")
                .paymentValue(new BigDecimal(1000))
                .build();

        paymentItem1500 = PaymentItemModel.builder()
                .paymentId("1")
                .paymentValue(new BigDecimal(1500))
                .build();

        PaymentRegister paymentRegister =
                new PaymentRegister("1", new BigDecimal(1000));

        when(repository.getReferenceById(anyString())).thenReturn(paymentRegister);

    }

    @Test
    public void confirmPaymentTotal() {
        PaymentModel payment = PaymentModel.builder()
                .sellerId("1")
                .paymentItems(List.of(paymentItem1000))
                .build();

        PaymentModel response = usecase.confirm(payment);

        assertNotNull(response);
        assertFalse(response.getPaymentItems().isEmpty());
        assertEquals(payment.getPaymentItems().size(), response.getPaymentItems().size());

        PaymentItemModel responseItem = response.getPaymentItems().get(0);

        assertEquals(paymentItem1000.getPaymentId(), responseItem.getPaymentId());
        assertEquals(paymentItem1000.getPaymentValue(), responseItem.getPaymentValue());
        assertEquals(PaymentStatus.TOTAL, responseItem.getPaymentStatus());

        verify(paymentTotalProducer, times(payment.getPaymentItems().size())).sendPayment(any(PaymentItemModel.class));
    }

    @Test
    public void confirmPaymentPartial() {
        PaymentModel payment = PaymentModel.builder()
                .sellerId("1")
                .paymentItems(List.of(paymentItem500))
                .build();

        PaymentModel response = usecase.confirm(payment);

        assertNotNull(response);
        assertFalse(response.getPaymentItems().isEmpty());
        assertEquals(payment.getPaymentItems().size(), response.getPaymentItems().size());

        PaymentItemModel responseItem = response.getPaymentItems().get(0);

        assertEquals(paymentItem500.getPaymentId(), responseItem.getPaymentId());
        assertEquals(paymentItem500.getPaymentValue(), responseItem.getPaymentValue());
        assertEquals(PaymentStatus.PARTIAL, responseItem.getPaymentStatus());

        verify(paymentPartialProducer, times(payment.getPaymentItems().size())).sendPayment(any(PaymentItemModel.class));
    }

    @Test
    public void confirmPaymentExcess() {
        PaymentModel payment = PaymentModel.builder()
                .sellerId("1")
                .paymentItems(List.of(paymentItem1500))
                .build();

        PaymentModel response = usecase.confirm(payment);

        assertNotNull(response);
        assertFalse(response.getPaymentItems().isEmpty());
        assertEquals(payment.getPaymentItems().size(), response.getPaymentItems().size());

        PaymentItemModel responseItem = response.getPaymentItems().get(0);

        assertEquals(paymentItem1500.getPaymentId(), responseItem.getPaymentId());
        assertEquals(paymentItem1500.getPaymentValue(), responseItem.getPaymentValue());
        assertEquals(PaymentStatus.EXCESS, responseItem.getPaymentStatus());

        verify(paymentExcessProducer, times(payment.getPaymentItems().size())).sendPayment(any(PaymentItemModel.class));
    }

}
