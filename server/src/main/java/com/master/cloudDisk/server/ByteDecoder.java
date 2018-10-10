package com.master.cloudDisk.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ByteDecoder extends ByteToMessageDecoder {

    protected void decode(ChannelHandlerContext ch, ByteBuf bBuf, List<Object> out) throws Exception {
        if(bBuf.isReadable() && bBuf.readByte() == 1){
            System.out.println("ddd-" + bBuf.readByte());
        }else{
            System.out.println(bBuf.readByte());
        }

    }
}
