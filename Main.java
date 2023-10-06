package org.unit1;

import org.unit1.client.Client;
import org.unit1.client.FrameClient;
import org.unit1.interfaces.client.IClientControlAndModel;
import org.unit1.interfaces.client.IClientView;
import org.unit1.interfaces.server.IServerControlAndModel;
import org.unit1.interfaces.server.IServerForClients;
import org.unit1.interfaces.server.IServerView;
import org.unit1.server.FrameServer;
import org.unit1.server.Server;

/**
 Java Development Kit (семинары)
 Урок 2. Программные интерфейсы

 Калинин Павел
 06.10.2023

 Задача исправить аналогичным образом серверную часть проекта, выделить интерфейсы. Можно работать со своим проектом.
 */

public class Main {
    public static void main(String[] args) {

        String chatFile = "chat.log";
        IServerControlAndModel server = new Server(chatFile);
        IServerView serverView = new FrameServer(server);
        server.setServerView(serverView);

        IClientControlAndModel client1 = new Client((IServerForClients) server);
        IClientView clientView1 = new FrameClient(client1);
        client1.setClientView(clientView1);

        IClientControlAndModel client2 = new Client((IServerForClients) server);
        IClientView clientView2 = new FrameClient(client2);
        client2.setClientView(clientView2);

        System.out.println("Hello world!");
    }
}