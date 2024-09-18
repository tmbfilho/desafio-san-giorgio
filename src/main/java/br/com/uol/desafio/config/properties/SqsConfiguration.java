package br.com.uol.desafio.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "aws.sqs")
public class SqsConfiguration {
    private String region;
    private String accessKey;
    private String secretKey;
    private boolean local;
    private String endpointOverrideUrl;

}
