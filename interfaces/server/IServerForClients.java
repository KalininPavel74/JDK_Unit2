package org.unit1.interfaces.server;

import org.unit1.interfaces.client.IClienForServer;

public interface IServerForClients {
    boolean connect(IClienForServer client);
    String getLog();
    void sendMessageFromClient(IClienForServer client, String text);
    void disconnectFromClient(IClienForServer client);

}
