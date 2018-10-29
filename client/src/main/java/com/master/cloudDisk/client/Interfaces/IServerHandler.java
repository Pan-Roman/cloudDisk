package com.master.cloudDisk.client.Interfaces;

import com.master.cloudDisk.common.Interfaces.ICommand;
import io.netty.buffer.ByteBuf;

import java.nio.file.Path;
import java.util.ArrayList;

public interface IServerHandler {
    public void auth(String login, String pass);
    public ArrayList<Path> getPathList(Path path);
    public boolean deletePath(Path path);
//    public boolean sendCommand(ICommand command);
    public boolean sendPath(Path path);
    public boolean isConnected();
    public void closeConnection();
    public void setClient(IClient _client);
}
