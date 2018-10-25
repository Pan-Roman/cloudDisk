package com.master.cloudDisk.client;

import io.netty.bootstrap.Bootstrap;
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

import static com.master.cloudDisk.common.Command.MAX_OBJ_SIZE;

public class Net extends Thread implements Runnable{
    private static Net instance = new Net();

    public static Net getInstance(){return instance;}
    private Net(){
        super("Net");
        start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Channel currentChannel;

    public Channel getCurrentChannel(){return currentChannel;}

    public void run(){
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap btstrp = new Bootstrap();
            btstrp.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress("localhost", 8189)
                    .handler(new ChannelInitializer<SocketChannel>(){
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new ObjectDecoder(MAX_OBJ_SIZE, ClassResolvers.cacheDisabled(null)),
                                    new ObjectEncoder(),
                                    new MainOutClientHandler());
                            currentChannel = socketChannel;
                        }
                    });
            ChannelFuture channelFuture = btstrp.connect().sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                group.shutdownGracefully();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void sendData(Object data){
        System.out.println("Send MESSAGE!");
        if(isConnected()) {
            currentChannel.writeAndFlush(data);
//            currentChannel.write("My two message");
        }else{
            System.out.println("Net doesn't conected!");
        }
    }
//    public void startHandler(Controller controller){currentChannel.pipeline().addLast(new MainHandler(controller));}
    public boolean isConnected(){return currentChannel != null && currentChannel.isActive();}
    public void closeConnection(){currentChannel.close();}
}
