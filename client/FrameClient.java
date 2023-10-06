package org.unit1.client;

import org.unit1.interfaces.FI_KeyReleasedListener;
import org.unit1.interfaces.client.IClientControlAndModel;
import org.unit1.interfaces.client.IClientView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;

public class FrameClient extends JFrame implements IClientView {

    private final JTextArea taLog;
    private final JPanel panTop;
    private final IClientControlAndModel client;

    public FrameClient(IClientControlAndModel client) {
        this.client = client;

        setLocation(500, 100);
        setSize(400, 400);
        setTitle("Чат Клиент");
        setResizable(false);

        taLog = new JTextArea();
        JScrollPane spLog = new JScrollPane(taLog);
        taLog.setEditable(false);
        add(spLog, BorderLayout.CENTER);

        panTop = new JPanel(new GridLayout(2, 3));
        JTextField tfIp = new JTextField("172.0.0.1");
        JTextField tfPort = new JTextField("8189");
        JTextField tfLogin = new JTextField("Павел Калинин");
        JPasswordField pfPassword = new JPasswordField("12345");
        JButton btnLogin = new JButton("Подключиться");
        panTop.add(tfIp);
        panTop.add(tfPort);
        panTop.add(new JPanel());
        panTop.add(tfLogin);
        panTop.add(pfPassword);
        panTop.add(btnLogin);
        add(panTop, BorderLayout.NORTH);

        JPanel panBottom = new JPanel(new BorderLayout());
        JTextField tfMessage = new JTextField();
        JButton btnSend = new JButton("Отправить");

        panBottom.add(tfMessage, BorderLayout.CENTER);
        panBottom.add(btnSend, BorderLayout.EAST);
        add(panBottom, BorderLayout.SOUTH);
        setVisible(true);

        btnLogin.addActionListener(e ->
                Optional.ofNullable(tfLogin.getText())
                        .filter(s -> !s.trim().isBlank())
                        .ifPresent(s -> {
                            boolean connected = client.connect(s);
                            if(connected)
                                panTop.setVisible(false);
                        }));

        ActionListener listenerBtnSend = e ->
                Optional.ofNullable(tfMessage.getText())
                        .filter(s -> !s.trim().isBlank())
                        .ifPresent(s -> {
                            tfMessage.setText("");
                            client.sendMessageFromClient(s);
                        });

        btnSend.addActionListener(listenerBtnSend);

        tfMessage.addKeyListener((FI_KeyReleasedListener) evt -> {
            if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER)
                listenerBtnSend.actionPerformed(null);
        });


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                client.disconnectFromClient();
            }
        });
    }

    @Override
    public void setChatLog(String text) {
        taLog.setText(text);
    }

    @Override
    public void showMessage(String text) {
        taLog.append(text + "\n");
    }

    @Override
    public void disconnect() {
        panTop.setVisible(true);
    }

}
