package br.com.uol.desafio.controllers;

import br.com.uol.desafio.domains.payment.enums.PaymentStatus;
import br.com.uol.desafio.domains.payment.model.PaymentItemModel;
import br.com.uol.desafio.domains.payment.model.PaymentModel;
import br.com.uol.desafio.domains.payment.usecases.ConfirmPaymentUseCaseImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    private static final String URL_TEMPLATE = "/api/payment";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConfirmPaymentUseCaseImpl confirmPaymentUseCase;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Payment payment;

    private PaymentItem paymentItem;

    private PaymentModel paymentModel;

    private PaymentItemModel paymentItemModel;

    @BeforeEach
    public void setup() {
        paymentItemModel = PaymentItemModel.builder()
                .paymentId("1111")
                .paymentValue(new BigDecimal(1000))
                .paymentStatus(PaymentStatus.TOTAL)
                .build();

        paymentModel = PaymentModel.builder()
                .sellerId("123456")
                .paymentItems(List.of(paymentItemModel))
                .build();

        when(confirmPaymentUseCase.confirm(any(PaymentModel.class))).thenReturn(paymentModel);

        paymentItem = PaymentItem.builder()
                .paymentId("1111")
                .paymentValue(new BigDecimal(1000))
                .build();

        payment = Payment.builder()
                .clientId("123456")
                .paymentItems(List.of(paymentItem))
                .build();
    }

    @Test
    public void setPaymentSuccess() throws Exception {

        String content = toJson(payment);

        mockMvc.perform(put(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.client_id").value(paymentModel.getSellerId()))
                .andExpect(jsonPath("$.payment_items[0].payment_id").value(paymentItemModel.getPaymentId()))
                .andExpect(jsonPath("$.payment_items[0].payment_value").value(paymentItemModel.getPaymentValue().toString()))
                .andExpect(jsonPath("$.payment_items[0].payment_status").value(paymentItemModel.getPaymentStatus().toString()));
    }

    @Test
    public void setPaymentEmpty() throws Exception {

        Payment paymentInvalid = new Payment();

        String content = toJson(paymentInvalid);

        mockMvc.perform(put(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.paymentItems").exists())
                .andExpect(jsonPath("$.errors.clientId").exists());
    }

    @Test
    public void setPaymentClientIdWrongSize() throws Exception {

        Payment paymentInvalid = new Payment();
        paymentInvalid.setClientId("123456789012345678901234567890123456789012345678901234567890");
        paymentInvalid.setPaymentItems(List.of(paymentItem));

        String content = toJson(paymentInvalid);

        mockMvc.perform(put(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.clientId").exists());
    }

    @Test
    public void setPaymentPaymentIdEmpty() throws Exception {
        PaymentItem paymentItemInvalid = PaymentItem.builder()
                .paymentValue(BigDecimal.TEN).build();

        Payment paymentInvalid = new Payment();
        paymentInvalid.setClientId("1111");
        paymentInvalid.setPaymentItems(List.of(paymentItemInvalid));

        String content = toJson(paymentInvalid);

        mockMvc.perform(put(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors['paymentItems[0].paymentId']").exists());
    }

    @Test
    public void setPaymentPaymentIdWrongSize() throws Exception {
        PaymentItem paymentItemInvalid = PaymentItem.builder()
                .paymentId("0123456789012345678901234567890123456789012345678901234567890123456789")
                .paymentValue(BigDecimal.TEN).build();

        Payment paymentInvalid = new Payment();
        paymentInvalid.setClientId("1111");
        paymentInvalid.setPaymentItems(List.of(paymentItemInvalid));

        String content = toJson(paymentInvalid);

        mockMvc.perform(put(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors['paymentItems[0].paymentId']").exists());
    }

    @Test
    public void setPaymentPaymentValueEmpty() throws Exception {
        PaymentItem paymentItemInvalid = PaymentItem.builder()
                .paymentId("1111").build();

        Payment paymentInvalid = new Payment();
        paymentInvalid.setClientId("1111");
        paymentInvalid.setPaymentItems(List.of(paymentItemInvalid));

        String content = toJson(paymentInvalid);

        mockMvc.perform(put(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors['paymentItems[0].paymentValue']").exists());
    }


    private String toJson(Payment payment) throws JsonProcessingException {
        return objectMapper.writeValueAsString(payment);
    }

}
