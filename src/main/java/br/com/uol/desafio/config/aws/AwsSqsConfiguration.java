package br.com.uol.desafio.config.aws;

import br.com.uol.desafio.config.properties.SqsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Configuration
public class AwsSqsConfiguration {

    @Autowired
    private SqsConfiguration sqsConfig;

    @Bean
    public SqsClient sqsClient() {

        AwsBasicCredentials awsBasicCredentials =  AwsBasicCredentials.create(
                sqsConfig.getAccessKey(), sqsConfig.getSecretKey());

        var builder = SqsClient.builder()
                .region(Region.of(sqsConfig.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials));

        if (sqsConfig.isLocal()) {
            builder.endpointOverride(URI.create(sqsConfig.getEndpointOverrideUrl()));
        }

        return builder.build();
    }

}
