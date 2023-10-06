package org.unit1.server;

import org.unit1.interfaces.client.IClienForServer;
import org.unit1.interfaces.server.IServerControlAndModel;
import org.unit1.interfaces.server.IServerForClients;
import org.unit1.interfaces.server.IServerView;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class Server implements IServerControlAndModel, IServerForClients {

    private IServerView serverView;
    private final List<IClienForServer> clients;
    private final String chatFile;
    private boolean isWork = false;

    public Server(String chatFile) {
        this.chatFile = chatFile;
        this.clients = new LinkedList<>();
    }

    @Override
    public void setServerView(IServerView serverView) {
        if (this.serverView == null)
            this.serverView = serverView;
    }

    @Override
    public void startServer() {
        isWork = true;
        StringBuilder chatLog = null;
        if (Path.of(chatFile).toFile().exists())
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(chatFile), StandardCharsets.UTF_8))
            ) {
                chatLog = br.lines()
                        .collect(StringBuilder::new,
                                (acc, str) -> acc.append(str).append("\n"),
                                StringBuilder::append);
                System.out.println(chatLog.toString());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        if (chatLog != null && chatLog.length() > 0) {
            String s = chatLog.toString();
            serverView.showMessage(s.substring(0,s.length()-2));
        }
    }

    @Override
    public void stopServer() {
        isWork = false;
        for (IClienForServer client : clients)
            client.disconnectFromServer();
        clients.clear();

        try (FileWriter fw = new FileWriter(chatFile, StandardCharsets.UTF_8, false)) {
            fw.append(serverView.getChatLog());
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean connect(IClienForServer client) {
        if (!isWork) {
            client.sendMessageFromServer("Сервер не работает.");
            return false;
        } else {
            clients.add(client);
            sendMessage("Добавлен новый клиент " + client.getName());
            return true;
        }
    }

    @Override
    public String getLog() {
        return (isWork) ? serverView.getChatLog() : "";
    }

    @Override
    public void sendMessageFromClient(IClienForServer client, String text) {
        if(isWork) sendMessage(client.getName() + ": " + text);
    }

    @Override
    public void disconnectFromClient(IClienForServer client) {
        if(isWork) {
            clients.remove(client);
            sendMessage("Клиент отключился " + client.getName());
        }
    }

    private void sendMessage(String text) {
        if(isWork) {
            serverView.showMessage(text);
            for (IClienForServer client : clients)
                client.sendMessageFromServer(text);
        }
    }

}
