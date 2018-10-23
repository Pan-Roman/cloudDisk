package com.master.cloudDisk.client;

import com.master.cloudDisk.client.Interfaces.IClient;
import com.master.cloudDisk.client.Interfaces.IServerHandler;
import com.master.cloudDisk.common.Interfaces.ICommand;
import com.master.cloudDisk.common.WriteFileCommand;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import static com.master.cloudDisk.common.Command.MAX_OBJ_SIZE;

public class ServerHandler extends Thread implements Runnable, IServerHandler {
    private static final String DEFAULT_HOST = "localhost";
    private static final Integer DEFAULT_PORT = 8189;
    // Instance
    private static ServerHandler instance;
    // Network config
    private IClient client;
    private String host;
    private Integer port;
    // Network // DiscardServer разобран в Lesson3 1:10
//    private Socket socket;
//    private DataOutputStream out;
//    private Scanner in;
    private Channel currentChannel;

    public static ServerHandler getInstance() {
        if(null == instance){
            instance = new ServerHandler();
        }
        return instance;
    }

    public boolean sendCommand(ICommand command) {
        try {
//            this.getOut().write(65);
//            ChannelFuture cf = getCurrentChannel().write(command);
            ChannelFuture cf = getCurrentChannel().writeAndFlush(command);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: Send command.
//        System.out.println("Не реализованна отправка команды!");
        return true;
    }

    public boolean sendFile(String fileName) {
        System.out.println("Не реализована отправка файла!");
        // Читаем файл
        /// ...
        // TODO: Send File
        byte [] arr = fileName.getBytes(); // Сюда надо запихать набор байт из файла а не байты имени файла

        ByteBufAllocator al = new PooledByteBufAllocator();
        ByteBuf bb = al.buffer(arr.length);
        bb.writeBytes(arr);
        this.sendCommand(new WriteFileCommand(fileName, bb));
        return false;
    }

    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public void setClient(IClient client) {
        this.client = client;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    private ServerHandler() {
        super("ServerHandler");
        this.client = null;
        this.host = DEFAULT_HOST;
        this.port = DEFAULT_PORT;
        start();
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run(){
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap clientBootstrap = new Bootstrap();
                clientBootstrap
                        .group(group)
                        .channel(NioSocketChannel.class)
                        .remoteAddress(new InetSocketAddress(host, port))
                        .handler(new ChannelInitializer<SocketChannel>() {
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                socketChannel.pipeline().addLast(
                                        new ObjectDecoder(MAX_OBJ_SIZE, ClassResolvers.cacheDisabled(null)),
                                        new ObjectEncoder(),
                                        new AuthHandler() // (actionAfterAuth)
                                );
                                currentChannel = socketChannel;
                            }
                        });
                ChannelFuture channelFuture = clientBootstrap.connect().sync();
                channelFuture.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("Finally init!!");
                try{
                    group.shutdownGracefully().sync();
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("END Finally!!");
            }
    }

    public boolean isConnected(){return currentChannel != null && currentChannel.isActive();}
    public void closeConnection(){currentChannel.close();}


//    private void onGetMessage (Object message) {
//        if(null != client) {
//            this.client.onGetMessage(message);
//        }
//    }

}
