package com.sternitc.javatcpsocketsimple.server;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageHandlerImpl implements MessageHandler {
    @Override
//    public String handle(byte[] bytes) {
    public String handle(String payload) {
        log.info("xxat {}, received: {}", System.getenv().get("INSTANCE_NAME"), payload);
        return payload;
    }
}
