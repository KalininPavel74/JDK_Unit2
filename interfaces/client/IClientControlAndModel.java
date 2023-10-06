package org.unit1.interfaces.client;

public interface IClientControlAndModel {
    void setClientView(IClientView clientView);

    boolean connect(String name);

    void disconnectFromClient();

    void sendMessageFromClient(String text);
}
