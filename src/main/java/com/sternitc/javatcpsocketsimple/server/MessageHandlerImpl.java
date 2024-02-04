package com.sternitc.javatcpsocketsimple.server;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageHandlerImpl implements MessageHandler {
    @Override
    public String handle(byte[] bytes) {
        log.debug("Received: {}", new String(bytes));
        return new String(bytes);
    }
}
