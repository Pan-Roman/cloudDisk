package com.master.cloudDisk.client;

import com.master.cloudDisk.client.Interfaces.IClient;
import com.master.cloudDisk.client.Interfaces.IServerHandler;
import com.master.cloudDisk.common.commands.AuthCommand;
import com.master.cloudDisk.common.Interfaces.ICommand;
import com.master.cloudDisk.common.commands.UploadFileCommand;
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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static com.master.cloudDisk.common.commands.Command.MAX_OBJ_SIZE;

public class ServerHandler extends Thread implements Runnable, IServerHandler {
    private IClient client;
    private static final String DEFAULT_HOST = "localhost";
    private static final Integer DEFAULT_PORT = 8189;
    // Instance
    private static IServerHandler instance;
    // Network config
    private String host;
    private Integer port;
    // Network // DiscardServer разобран в Lesson3 1:10
    private Channel currentChannel;

    public static IServerHandler getInstance() {
        if(null == instance){
            instance = new ServerHandler();
        }
        return instance;
    }

    @Override
    public void auth(String login, String pass) {
        ICommand message = new AuthCommand(login, pass);
        System.out.println("Send Auth mess...");
        sendCommand(message);
    }

    @Override
    public ArrayList<Path> getPathList(Path path){

        return null;
    }

    @Override
    public boolean deletePath(Path path) {

        return false;
    }

    @Override
    public boolean sendPath(Path path) {
        connect();
//        Path path = Paths.get(fileName);
//        ByteBuf buf = path
        String fileName = Client.getPathControl().getCurrentPath() + "\\" + path.getFileName().toString();
//        FileChannel fc = PathControl.getChannelFromFile(fileName);



//        System.out.println("Не реализована отправка файла!");

        // Читаем файл
        // Работа с файлами через nio - Lesson 2 (50:00)
        /// ...
        // TODO: Send File
//        byte [] arr = fileName.getBytes(); // Сюда надо запихать набор байт из файла а не байты имени файла
        byte [] arr = new byte[0];
        try {
            arr = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        ByteBufAllocator al = new PooledByteBufAllocator();
//        ByteBuf bb = al.buffer(arr.length);
//        bb.writeBytes(arr);
//        bb.
        this.sendCommand(new UploadFileCommand(fileName, arr));
        return false;
    }

    @Override
    public void setClient(IClient _client) {
        client = _client;
    }

    private boolean sendCommand(ICommand command) {
        connect();
        try {
            ChannelFuture cf = getCurrentChannel().writeAndFlush(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    private ServerHandler() {
        super("ServerHandler");
        this.host = DEFAULT_HOST;
        this.port = DEFAULT_PORT;
    }

    private void connect(){
        if(!isConnected()) {
            start();
            try {
                Thread.currentThread().sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        else {
//            System.out.println("It's conected!");
//        }
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
                                        new MainOutClientHandler(),
                                        new MainInClientHandler(client)
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

    public void closeConnection(){
        if(isConnected()){
            currentChannel.close();
        }
    }

}
