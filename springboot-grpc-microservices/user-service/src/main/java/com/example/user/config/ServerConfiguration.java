package com.example.user.config;

import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class ServerConfiguration {
    @Bean
    public GrpcServerConfigurer grpcServerConfigurer() {
        return serverBuilder ->
                serverBuilder.executor(Executors.newVirtualThreadPerTaskExecutor());
    }
}
