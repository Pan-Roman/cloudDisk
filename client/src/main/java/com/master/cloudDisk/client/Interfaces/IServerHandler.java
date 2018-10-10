package com.master.cloudDisk.client.Interfaces;

import com.master.cloudDisk.common.Interfaces.ICommand;
import io.netty.buffer.ByteBuf;

public interface IServerHandler {
    public boolean sendCommand(ICommand command);
    public boolean sendFile(String fileName);
}
