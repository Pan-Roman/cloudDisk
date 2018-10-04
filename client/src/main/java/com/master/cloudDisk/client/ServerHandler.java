package com.master.cloudDisk.client;

import com.master.cloudDisk.client.Interfaces.IClient;
import com.master.cloudDisk.client.Interfaces.IServerHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ServerHandler implements IServerHandler {
    private IClient client;
    private Socket socket;
    private String host;
    private Integer port;
    private DataOutputStream out;
    private Scanner in;

    public ServerHandler(IClient client, String host, Integer port) {
        this.client = client;
        this.host = host;
        this.port = port;

    }

    private void onGetMessage (Object message) {
        this.client.onGetMessage(message);
    }

    public void SendMessage(Object message) {
        try {
            this.getOut().write(65);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Socket getSocket() throws IOException {
        if(this.socket == null){
            this.socket = this.getSocket() ;
        }
        return this.socket;
    }

    private Scanner getIn() throws IOException {
        if(this.in == null){
            this.in = new Scanner(this.getSocket().getInputStream());
        }
        return this.in;
    }

    private DataOutputStream getOut() throws IOException {
        if(this.out == null){
                Socket socket = new Socket(this.host, this.port) ;
                this.out = new DataOutputStream(socket.getOutputStream());
        }
        return this.out;
    }
}
