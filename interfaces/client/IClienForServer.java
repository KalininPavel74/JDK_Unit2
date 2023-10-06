package org.unit1.interfaces.client;

public interface IClienForServer {
    void disconnectFromServer();
    String getName();
    void sendMessageFromServer(String text);
}
