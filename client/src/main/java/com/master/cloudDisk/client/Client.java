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
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Client extends Application implements IClient {
    static public String MAIN_PATH_NAME = "CloudDisk";

    private IServerHandler serverHandler;
    private Controller controller;
    private static ClientPathControl pathControl;

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Settings.load();

        primaryStage.setTitle("DownloadFX");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene.fxml"));

        try{
            Parent root = (Parent)loader.load();
            controller = loader.getController();
            controller.setClient(this);
//            Paths.get("D:\\!Learning\\GeekUnivers\\Java5\\Lesson3\\netty-servers\\netty-servers\\1.txt")
//            controller.getPathTree().getRoot().setValue(getPathControl().getRootPath().getFileName().toString());
            controller.setRootPath(getPathControl().getRootPath());
//            controller.fillTableViewFromPath(Paths.get("D:\\!Learning\\GeekUnivers\\Java5\\Lesson3\\netty-servers\\netty-servers"));
//            controller.fillTableViewFromPath(Paths.get("D:\\!Learning\\GeekUnivers\\Java5\\Lesson3\\netty-servers\\netty-servers"));
//            controller.setStage(mainStage);
//            primaryStage.setTitle("Cloud Disk");
            primaryStage.setScene(new Scene(root, 700, 500));
            primaryStage.show();
        } catch(Exception e){
            e.printStackTrace();
        }

//        String userHome = System.getProperty("user.home") + "\\";
//        String userHome = getMainPath();
//        System.out.println("USER_HOME: " + userHome);
        // Клиент на Netty Lesson3(1.53)
    }


    public static String getMainPathName(){
        return MAIN_PATH_NAME;
    }

    public static String getMainPath(){
        return System.getProperty("user.home") + "\\" + MAIN_PATH_NAME;
    }

    public static ClientPathControl getPathControl(){
        if(null == pathControl){
            pathControl = new ClientPathControl(getMainPath());
        }
        return pathControl;
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
                if(null != controller){
                    if(null != controller.AuthBox) {

                        controller.AuthBox.setVisible(false);
                    }else{
                        System.out.println("AuthBox!!!!----");
                    }
                }else {
                    System.out.println("controller---");
                }

                break;
            default:
                System.out.println("We have some answer from Server!");
                break;
        }
    }


}
