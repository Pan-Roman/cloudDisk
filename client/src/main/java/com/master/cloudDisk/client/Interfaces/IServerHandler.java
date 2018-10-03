package com.master.cloudDisk.client.Interfaces;

public interface IServerHandler {
    public void IServerHandler(IClient client, String host, Integer port);
    public void SendMessage(Object message);
}
