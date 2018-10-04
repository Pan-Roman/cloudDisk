package com.master.cloudDisk.client;

import com.master.cloudDisk.client.Interfaces.IClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application implements IClient {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Scene.fxml"));
        primaryStage.setTitle("Cloud Disk");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
        ServerHandler serverHandler = new ServerHandler(this, "localhost", 8189);

        serverHandler.SendMessage("Privet! ");
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void onGetMessage(Object msg) {
        System.out.println("ON_GET_MESSAGE: " + msg.toString());
    }
}
