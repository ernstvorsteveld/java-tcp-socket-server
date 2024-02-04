package com.sternitc.javatcpsocketsimple.server;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNioServerConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.TcpCodecs;
import org.springframework.integration.util.CompositeExecutor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@EnableIntegration
@Configuration
public class ServerConfiguration {

    public static final String TCP_CHANNEL = "tcpChannel";

    @Bean
    public AbstractServerConnectionFactory serverConnectionFactory(
            @Value("${tcp.server.port}") int port) {
        TcpNioServerConnectionFactory factory = new TcpNioServerConnectionFactory(port);
        factory.setUsingDirectBuffers(true);
        return factory;
    }

    @Bean(name = TCP_CHANNEL)
    public MessageChannel tcpChannel() {
        return new QueueChannel();
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

//    @Bean
//    public IntegrationFlow server(
//            @Value("${tcp.server.port}") int port) {
//        return IntegrationFlow.from(Tcp.inboundAdapter(Tcp.netServer(port)
//                                .deserializer(TcpCodecs.lengthHeader1())
//                                .backlog(30))
//                        .errorChannel("tcpIn.errorChannel")
//                        .id("tcpIn"))
//                .transform(Transformers.objectToString())
//                .channel(TCP_CHANNEL)
//                .get();
//    }

    @Bean
    public MessageHandler handler() {
        return new MessageHandlerImpl();
    }

}
