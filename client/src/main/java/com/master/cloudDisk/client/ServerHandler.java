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

public class ServerHandler implements IServerHandler {
    private static final String DEFAULT_HOST = "localhost";
    private static final Integer DEFAULT_PORT = 8189;
    // Instance
    private static ServerHandler instance;
    private boolean initialized = false;
    // Network config
    private IClient client;
    private String host;
    private Integer port;
    // Network
//    private Socket socket;
//    private DataOutputStream out;
//    private Scanner in;
    private Channel currentChannel;

    public static ServerHandler getInstance() {
        if(null == instance){
            instance = new ServerHandler();
//            instance.init();
        }
        return instance;
    }

    public boolean sendCommand(ICommand command) {
        try {
//            this.getOut().write(65);
            ChannelFuture cf = getCurrentChannel().write(command);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: Send command.
        System.out.println("Не реализованна отправка команды!");
        return true;
    }

    public boolean sendFile(String fileName) {
        init();
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
        init();
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
        this.client = null;
        this.host = DEFAULT_HOST;
        this.port = DEFAULT_PORT;
    }

    private void init(){
        System.out.println("Before init.");
        if(!initialized) {
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                System.out.println("first try");
                Bootstrap clientBootstrap = new Bootstrap();
                clientBootstrap
                        .group(group)
                        .channel(NioSocketChannel.class)
                        .remoteAddress(new InetSocketAddress(host, port))
                        .handler(new ChannelInitializer<SocketChannel>() {
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                socketChannel.pipeline().addLast(
                                        new ObjectDecoder(MAX_OBJ_SIZE, ClassResolvers.cacheDisabled(null)),
                                        new ObjectEncoder()
//                                        new AuthHandler() // (actionAfterAuth)
                                );
                                initialized = true;
                                currentChannel = socketChannel;
                            }
                        });
                System.out.println("After bootstrap!");
                ChannelFuture channelFuture = clientBootstrap.connect().sync();
                System.out.println("After bootstrap 2!");
                channelFuture.channel().closeFuture().sync();
                System.out.println("After bootstrap 3!");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("Finally init!!");
                try{
                    group.shutdownGracefully().sync();
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("END Finally init!!");
            }
        }
        System.out.println("After init !!");
    }







//    private void onGetMessage (Object message) {
//        if(null != client) {
//            this.client.onGetMessage(message);
//        }
//    }








//    private Socket getSocket() throws IOException {
//        if(this.socket == null){
//            this.socket = this.getSocket() ;
//        }
//        return this.socket;
//    }
//
//    private Scanner getIn() throws IOException {
//        if(this.in == null){
//            this.in = new Scanner(this.getSocket().getInputStream());
//        }
//        return this.in;
//    }
//
//    private DataOutputStream getOut() throws IOException {
//        if(this.out == null){
//                Socket socket = new Socket(this.host, this.port) ;
//                this.out = new DataOutputStream(socket.getOutputStream());
//        }
//        return this.out;
//    }
}
