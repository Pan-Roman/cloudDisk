package com.master.cloudDisk.common.commands;

import com.master.cloudDisk.common.Interfaces.ICommand;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.multipart.FileUpload;

public class UploadFileCommand extends Command {
    private String fileName;
    private byte [] buf;

    public UploadFileCommand(String fileName, byte [] buf) {
        // TODO: FileUpload.class ????? Надо разобраться что это!
        this.fileName = fileName;
        this.buf = buf;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getBuf() {
        return buf;
    }
}
