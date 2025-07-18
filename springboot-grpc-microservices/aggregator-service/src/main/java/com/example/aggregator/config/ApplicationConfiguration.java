package com.example.aggregator.config;

import com.google.protobuf.util.JsonFormat;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;

import java.util.concurrent.Executors;

@Configuration
public class ApplicationConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Bean
    public GrpcChannelConfigurer channelConfigurer() {
        return (channelBuilder, name) -> {
            LOGGER.info("channel builder {}", name);
            channelBuilder.executor(Executors.newVirtualThreadPerTaskExecutor());
        };
    }

    @Bean
    public ProtobufJsonFormatHttpMessageConverter protobufJsonFormatHttpMessageConverter() {
        return new ProtobufJsonFormatHttpMessageConverter(
                JsonFormat.parser().ignoringUnknownFields(),
                JsonFormat.printer().omittingInsignificantWhitespace().includingDefaultValueFields()
        );
    }

}
