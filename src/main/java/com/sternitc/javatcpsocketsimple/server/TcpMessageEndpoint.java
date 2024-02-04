package com.sternitc.javatcpsocketsimple.server;


import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@Slf4j
@MessageEndpoint
public class TcpMessageEndpoint {

    private final MessageHandler handler;

    public TcpMessageEndpoint(MessageHandler handler) {
        this.handler = handler;
    }

    @ServiceActivator(inputChannel = ServerConfiguration.TCP_CHANNEL)
    public byte[] process(byte[] message) {
        String response = handler.handle(message);
        log.debug("Send message to client: {}", response);
        return response.getBytes();
    }

}