package com.master.cloudDisk.server;

import com.master.cloudDisk.common.Interfaces.ICommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MainServerHandler extends ChannelInboundHandlerAdapter {
    MainServerHandler(){
        System.out.println("MainServerHandler created!");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object cmd) throws Exception {
        if(cmd instanceof ICommand){
            System.out.println("We have command object in MainServerHandler!");
            ((ICommand) cmd).execute();
        }else{
            System.out.println("We have wrong object!");
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
