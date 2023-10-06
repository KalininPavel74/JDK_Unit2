package org.unit1.server;

import org.unit1.interfaces.server.IServerControlAndModel;
import org.unit1.interfaces.server.IServerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameServer extends JFrame implements IServerView {
    private JTextArea taLog;
    private IServerControlAndModel server;
    public FrameServer(IServerControlAndModel server) {
        this.server = server;
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocation(100,100);
        setSize(400,400);
        setTitle("Чат Сервер");
        setResizable(false);

        this.taLog = new JTextArea();
        JScrollPane spLog = new JScrollPane(taLog);
        taLog.setEditable(false);
        add(spLog, BorderLayout.CENTER);

        JPanel panBottom = new JPanel(new GridLayout(1,2));
        JButton btnStart = new JButton("Запустить");
        panBottom.add(btnStart);
        JButton btnStop = new JButton("Остановить");
        panBottom.add(btnStop);

        add(panBottom, BorderLayout.SOUTH);

        btnStart.addActionListener(e -> { taLog.setText(""); server.startServer(); });
        btnStop.addActionListener(e -> server.stopServer());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                server.stopServer();
            }
        });

        setVisible(true);
    }

    @Override
    public void showMessage(String text) {
        this.taLog.append(text+"\n");
    }

    @Override
    public String getChatLog() {
        return this.taLog.getText();
    }
}
