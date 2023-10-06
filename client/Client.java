package org.unit1.client;

import org.unit1.interfaces.client.IClienForServer;
import org.unit1.interfaces.client.IClientControlAndModel;
import org.unit1.interfaces.client.IClientView;
import org.unit1.interfaces.server.IServerForClients;

public class Client implements IClientControlAndModel, IClienForServer {

    private IClientView clientView;
    private final IServerForClients server;
    private boolean connected = false;
    private String name = String.valueOf(this.hashCode());

    public Client(IServerForClients server) {
        this.server = server;
    }

    @Override
    public void setClientView(IClientView clientView) {
        if (this.clientView == null)
            this.clientView = clientView;
    }

    @Override
    public boolean connect(String name) {
        this.name = name + " №" + this.hashCode();
        boolean b = server.connect(this);
        connected = b;
        if (connected)
            clientView.showMessage(server.getLog());
        return connected;
    }

    @Override
    public void disconnectFromClient() {
        if (connected)
            server.disconnectFromClient(this);
    }

    @Override
    public void sendMessageFromClient(String text) {
        if (connected)
            server.sendMessageFromClient(this, text);
    }

    @Override
    public void disconnectFromServer() {
        clientView.showMessage("Сервер отключился!");
        connected = false;
        clientView.disconnect();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void sendMessageFromServer(String text) {
        if (connected) clientView.showMessage(text);
    }
}
