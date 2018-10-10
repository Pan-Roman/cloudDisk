package com.master.cloudDisk.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;


public class TestServerHandler extends ChannelInboundHandlerAdapter {
//public class TestServerHandler extends SimpleChannelInboundHandler {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(null == msg){
            System.out.println("NULL MESSAGE");
            return;
        }
        if(msg instanceof ByteBuf){
            ByteBuf data = (ByteBuf) msg;
            System.out.println("We can read " + data.readableBytes() + "bytes!");
            byte b1 = data.getByte(1);
            System.out.println("We can read " + data.readableBytes() + "bytes!");
            if(1 == data.readByte()){
                System.out.println("We have some file!");
            }else if(0 == data.readByte()){
                System.out.println("We have some command!");
            }else{
                System.out.println("We have some trouble!");
            }
        }else{
            System.out.println("MESSAGE CLASS: " + msg.getClass());
            System.out.println("MESSAGE TO STRING: " + msg.toString());
            ctx.fireChannelRead(msg); // Передача сообщения дальше по конвейеру. Если не вызвать - конвейер прервётся на этом обработчике.

        }

        // Завершение работы
        ((ByteBuf) msg).release(); // Освобождает буфер. Дальнейшая обработка запроса (НЕ?) происходит?
//        ctx.write(str); // Запись сообщения для отправки
//        ctx.flush(); // Непосредственная отправка сообщения записанного командой ctx.write(str)

//        Вопросы:
//        1. В чём разница между ((ByteBuf) msg).release() и ReferenceCountUtil.release(msg) ?
//        2. ctx.write(str) отсылает сообщение по всему исходящему pipeLine или только по части из которой отправлен?

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("WE HAVE EXCEPTION: ");
        cause.printStackTrace();
        ctx.close(); // Закрытие канала! Прекращение взаимодействия!
    }
}
