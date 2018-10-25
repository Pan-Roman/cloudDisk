package com.master.cloudDisk.client;

import com.master.cloudDisk.client.Interfaces.IClient;
import com.master.cloudDisk.common.Interfaces.ICommand;
import com.master.cloudDisk.common.ServerAnswers;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import static com.master.cloudDisk.common.ServerAnswers.*;

public class MainInClientHandler extends ChannelInboundHandlerAdapter {
    IClient client;

    public MainInClientHandler(IClient _client) {
        client = _client;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("The client got the MESSAGE!!!");
        if(msg instanceof ServerAnswers){
            client.onGotMessage((ServerAnswers) msg);
        }else{
            System.out.println("We have wrong object!");
        }
        super.channelRead(ctx, msg);
    }
}
