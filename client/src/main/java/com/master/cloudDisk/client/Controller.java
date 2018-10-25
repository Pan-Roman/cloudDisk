package com.master.cloudDisk.client;

import com.master.cloudDisk.common.AuthCommand;
import com.master.cloudDisk.common.Interfaces.ICommand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    public TextField LoginField;
    @FXML
    public PasswordField PassField;
    @FXML
    public HBox AuthBox;
    @FXML
    private TableView<String> pathTable;
    @FXML
    private TreeView<String> pathTree;
    @FXML
    private TableColumn<String, String> pathTableColumnName;
    @FXML
    private TableColumn<String, String> pathTableColumnDate;
    @FXML
    private TableColumn<String, String> pathTableColumnType;
    @FXML
    private TableColumn<String, Integer> pathTableColumnSize;

    public void initialize(URL location, ResourceBundle resources) {
        TreeItem<String> root = new TreeItem<String>("CloudDisk");
        pathTree.setRoot(root);
//        System.out.println("Test");

    }


    private void handleDragAction(ActionEvent event){
        System.out.println("DRAG: " + event.toString());
    }

    private void handleDropAction(ActionEvent event){
        System.out.println("DROP: " + event.toString());
    }

    public void test(ActionEvent event){
        System.out.println("DROP: " + event.toString());
    }

    public void auth(ActionEvent event){
        String pass = Client.md5(AuthCommand.salt +  LoginField.getText() + PassField.getText());
        System.out.println("Login: " + LoginField.getText() + " Password: " + pass);
        ICommand message = new AuthCommand(LoginField.getText(), pass);
        System.out.println("Send Auth mess...");
        ServerHandler.getInstance().sendCommand(message);
    }



}
