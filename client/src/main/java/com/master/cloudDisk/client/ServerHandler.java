package com.master.cloudDisk.client;

import com.master.cloudDisk.client.Interfaces.IClient;
import com.master.cloudDisk.client.Interfaces.IServerHandler;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerHandler implements IServerHandler {
    private IClient client;
    private String host;
    private Integer port;
    private DataOutputStream out;

    public void IServerHandler(IClient client, String host, Integer port) {
        this.client = client;
        this.host = host;
        this.port = port;
    }

    public void SendMessage(Object message) {
        try {
            this.getOut().write(65);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DataOutputStream getOut() throws IOException {
        if(this.out == null){
                Socket socket = new Socket (this.host, this.port) ;
                this.out = new DataOutputStream(socket.getOutputStream());
        }
        return this.out;
    }
}
