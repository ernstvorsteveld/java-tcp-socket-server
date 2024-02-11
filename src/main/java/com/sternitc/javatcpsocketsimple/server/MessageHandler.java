package com.sternitc.javatcpsocketsimple.server;

public interface MessageHandler {

//    String handle(byte[] bytes);
    String handle(String payload);
}
