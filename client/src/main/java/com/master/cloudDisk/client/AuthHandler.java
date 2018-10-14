package com.master.cloudDisk.client;

import com.master.cloudDisk.common.AuthCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class AuthHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("Send MESSAGE!!!");
//        ctx.writeAndFlush(msg);
        super.write(ctx, msg, promise);
    }
}
