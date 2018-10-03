package com.master.cloudDisk.client;

import com.master.cloudDisk.client.Interfaces.IClient;

public class MyTestClient implements IClient {

    public static void main(String[] args) {

    }

    public void onGetMessage(Object msg) {
        System.out.println("MESSAGE FROM SERVER: " + msg.toString());
    }
}
