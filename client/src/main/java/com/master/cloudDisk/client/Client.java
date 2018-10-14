package com.master.cloudDisk.client;

import com.master.cloudDisk.client.Interfaces.IClient;
import com.master.cloudDisk.client.Interfaces.IServerHandler;
import com.master.cloudDisk.common.AuthCommand;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application implements IClient {
    private ServerHandler serverHandler;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Scene.fxml"));
        primaryStage.setTitle("Cloud Disk");
        primaryStage.setScene(new Scene(root, 550, 400));
        primaryStage.show();

//        ServerHandler sh = ServerHandler.getInstance();
//        sh.sendCommand(new AuthCommand("login", "password"));

        Thread thread = new Thread(new Runnable() {
            public void run() {
                Net.getInstance().start();
            }
        });
        thread.start();
        try {
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
        Net.getInstance().sendData("My message!!!!!");
        Net.getInstance().sendData("My message!!!!!");
        Net.getInstance().getCurrentChannel().writeAndFlush("My 2 message!!!!!");


//        serverHandler.sendCommand("Privet! ");
        // Клиент на Netty Lesson3(1.53)
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void onGetMessage(Object msg) {
        System.out.println("ON_GET_MESSAGE: " + msg.toString());
    }


}
