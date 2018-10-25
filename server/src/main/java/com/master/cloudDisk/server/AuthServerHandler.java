package com.master.cloudDisk.server;

import com.master.cloudDisk.common.AuthCommand;
import com.master.cloudDisk.common.ServerAnswers;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AuthServerHandler extends ChannelInboundHandlerAdapter {
    ChannelInboundHandlerAdapter handlerAfterAuth;

    AuthServerHandler(ChannelInboundHandlerAdapter handlerAA){
        handlerAfterAuth = handlerAA;
        System.out.println("AuthServerHandler created!");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("We have message.");
        if(msg instanceof AuthCommand){
            AuthCommand cmd = (AuthCommand) msg;
            System.out.println("We have Auth object! - " + cmd.getLogin());
            if(Repository.checkAuthData(cmd.getLogin(), cmd.getPassword())){
                System.out.println("Auth OK!");
                ctx.writeAndFlush(ServerAnswers.AUTH_OK);
                ctx.pipeline().remove(this).addLast(handlerAfterAuth);
            }else{
                System.out.println("Error Auth!");
            }
        }else{
            System.out.println("We have wrong object!");
            return;
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("WE HAVE EXCEPTION: ");
        cause.printStackTrace();
        ctx.close();
    }
}
