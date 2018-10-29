package com.master.cloudDisk.server;

import com.master.cloudDisk.common.Interfaces.ICommand;
import com.master.cloudDisk.common.commands.UploadFileCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MainServerHandler extends ChannelInboundHandlerAdapter {
    MainServerHandler(){
        System.out.println("MainServerHandler created!");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object cmd) throws Exception {
        if(cmd instanceof ICommand){
            System.out.println("We have command object (Class: " + cmd.getClass() + " ) in MainServerHandler!");
//            switch (cmd.)
//            ((ICommand) cmd).execute();
            if(cmd instanceof UploadFileCommand){
                String fileName = ((UploadFileCommand) cmd).getFileName();
//                Path test = Paths.get(getCurrentPath().toString(), relativePath);
                System.out.println("FILE NAME: " + fileName);
            }
        }else{
            System.out.println("We have wrong object!");
            ByteBuf buf;

        }

//        ((ByteBuf) cmd).release(); // Освобождение Байт-Буфера. Работа с Байт-Буффером - Lesson2 (~1:30+)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("WE HAVE EXCEPTION: ");
        cause.printStackTrace();
        ctx.close(); // Закрытие канала! Прекращение взаимодействия!
    }

}
