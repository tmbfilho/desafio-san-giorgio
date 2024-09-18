package br.com.uol.desafio.infra.queues.payment;

import br.com.uol.desafio.config.properties.QueuesConfiguration;
import br.com.uol.desafio.domains.payment.model.PaymentItemModel;
import br.com.uol.desafio.util.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

public abstract  class AbstractPaymentProducer implements PaymentProducer {

    @Autowired
    protected QueuesConfiguration queuesConfig;

    @Autowired
    private SqsClient sqsClient;

    @Autowired
    private JsonService jsonService;

    @Override
    public void sendPayment(PaymentItemModel paymentItem) {

        String messageBody = jsonService.convertToJson(paymentItem);

        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(getQueueUrl())
                .messageBody(messageBody)
                .build();

        sqsClient.sendMessage(sendMessageRequest);
    }

    protected abstract String getQueueUrl();

}
