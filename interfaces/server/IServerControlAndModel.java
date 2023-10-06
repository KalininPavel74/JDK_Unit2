package org.unit1.interfaces.server;

public interface IServerControlAndModel {
    void setServerView(IServerView serverView);
    void startServer();
    void stopServer();
}
