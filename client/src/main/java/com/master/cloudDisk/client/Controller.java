package com.master.cloudDisk.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{


    public void initialize(URL location, ResourceBundle resources) {
        TreeItem<String> root = new TreeItem<String>("CloudDisk");
        pathTree.setRoot(root);
//        System.out.println("Test");

    }

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

    private void handleDragAction(ActionEvent event){
        System.out.println("DRAG: " + event.toString());
    }

    private void handleDropAction(ActionEvent event){
        System.out.println("DROP: " + event.toString());
    }

    public void test(ActionEvent event){
        System.out.println("DROP: " + event.toString());
    }



}
