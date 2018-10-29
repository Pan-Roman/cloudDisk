package com.master.cloudDisk.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class MainOutClientHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("Send MESSAGE!!!");
        ctx.writeAndFlush(msg);
//        super.write(ctx, msg, promise);
    }
}
