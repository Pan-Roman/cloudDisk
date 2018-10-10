package com.master.cloudDisk.common;

import com.master.cloudDisk.common.Interfaces.ICommand;
import io.netty.buffer.ByteBuf;

public class WriteFileCommand implements ICommand {
    private String fileName;
    private ByteBuf buf;

    public WriteFileCommand(String fileName, ByteBuf buf) {
        this.fileName = fileName;
        this.buf = buf;
    }

    public void execute() {
        System.out.println("We have file - " + fileName);
        // TODO: Write file!
    }

    public String getFileName() {
        return fileName;
    }

    public ByteBuf getBuf() {
        return buf;
    }
}
