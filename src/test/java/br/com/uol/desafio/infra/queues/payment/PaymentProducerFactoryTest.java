package br.com.uol.desafio.infra.queues.payment;

import br.com.uol.desafio.domains.payment.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PaymentProducerFactoryTest {

    @Mock
    private PaymentTotalProducer paymentTotalProducer;

    @Mock
    private PaymentPartialProducer paymentPartialProducer;

    @Mock
    private PaymentExcessProducer paymentExcessProducer;

    @InjectMocks
    private PaymentProducerFactory factory;

    @BeforeEach
    public void setup() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = PaymentProducerFactory.class.getDeclaredMethod("loadPaymentProducerMap");
        method.setAccessible(true);
        method.invoke(factory);
    }

    @Test
    public void paymentProducerTotal() {
        PaymentProducer producer = factory.createPaymentProducer(PaymentStatus.TOTAL);
        assertInstanceOf(PaymentTotalProducer.class, producer);
    }

    @Test
    public void paymentProducerPartial() {
        PaymentProducer producer = factory.createPaymentProducer(PaymentStatus.PARTIAL);
        assertInstanceOf(PaymentPartialProducer.class, producer);
    }

    @Test
    public void paymentProducerExcess() {
        PaymentProducer producer = factory.createPaymentProducer(PaymentStatus.EXCESS);
        assertInstanceOf(PaymentExcessProducer.class, producer);
    }

    @Test
    public void paymentProducerNotFound() {
        assertThrows(PaymentProducerNotFoundException.class, () -> factory.createPaymentProducer(null));
    }
}
