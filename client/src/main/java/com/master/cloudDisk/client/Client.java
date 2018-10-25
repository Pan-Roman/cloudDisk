package com.master.cloudDisk.client;

import com.master.cloudDisk.client.Interfaces.IClient;
import com.master.cloudDisk.client.Interfaces.IServerHandler;
import com.master.cloudDisk.common.ServerAnswers;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Client extends Application implements IClient {
    private IServerHandler serverHandler;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Scene.fxml"));
        primaryStage.setTitle("Cloud Disk");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
        String userHome = System.getProperty("user.home");

        // Клиент на Netty Lesson3(1.53)
    }

    @Override
    public void init() throws Exception {
        System.out.println("INIT!!!");
        serverHandler = ServerHandler.getInstance();
        serverHandler.setClient(this);
        super.init();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("STOP!!!");
        ServerHandler.getInstance().closeConnection();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static String md5(String message) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] resultByte =  messageDigest.digest(message.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < resultByte.length; ++i) {
                sb.append(Integer.toHexString((resultByte[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void onGotMessage(ServerAnswers msg) {
        System.out.println("We have answer from Server!");
        switch (msg){
            case AUTH_OK:
                System.out.println("Auth OKKKKK!!");
                break;
            default:
                System.out.println("We have some answer from Server!");
                break;
        }
    }


}
