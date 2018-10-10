package com.master.cloudDisk.server;

import com.master.cloudDisk.common.Interfaces.ICommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MainServerHandler extends ChannelInboundHandlerAdapter {
    MainServerHandler(){
        System.out.println("MainServerHandler created!");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object cmd) throws Exception {
        if(cmd instanceof ICommand){
            ((ICommand) cmd).execute();
        }else{
            System.out.println("We have wrong object!");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("WE HAVE EXCEPTION: ");
        cause.printStackTrace();
        ctx.close(); // Закрытие канала! Прекращение взаимодействия!
    }
}
