package br.com.uol.desafio.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws.sqs.queue")
@Getter
@Setter
public class QueuesConfiguration {
    private String paymentTotalUrl;
    private String paymentPartialUrl;
    private String paymentExcessUrl;
}
