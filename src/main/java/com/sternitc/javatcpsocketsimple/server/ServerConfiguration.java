package com.sternitc.javatcpsocketsimple.server;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNioServerConnectionFactory;
import org.springframework.messaging.MessageChannel;

@EnableIntegration
@Configuration
public class ServerConfiguration {

    public static final String TCP_CHANNEL = "tcpChannel";
    public static final String TCP_CHANNEL_REPLY = "tcpReplyChannel";

    @Bean
    public AbstractServerConnectionFactory serverConnectionFactory(
            @Value("${tcp.server.port}") int port) {
        TcpNioServerConnectionFactory factory = new TcpNioServerConnectionFactory(port);
        factory.setUsingDirectBuffers(true);
        return factory;
    }

    @Bean(name = TCP_CHANNEL)
    public MessageChannel tcpChannel() {
        return new DirectChannel();
    }

    @Bean
    public TcpInboundGateway tcpInboundGateway(
            AbstractServerConnectionFactory serverConnectionFactory,
            @Qualifier(TCP_CHANNEL) MessageChannel tcpChannel) {
        TcpInboundGateway gateway = new TcpInboundGateway();
        gateway.setConnectionFactory(serverConnectionFactory);
        gateway.setRequestChannel(tcpChannel);
        return gateway;
    }

    @Bean
    public MessageHandler handler() {
        return new MessageHandlerImpl();
    }

}
