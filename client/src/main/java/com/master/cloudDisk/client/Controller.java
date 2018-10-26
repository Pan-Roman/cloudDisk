package com.master.cloudDisk.client;

import com.master.cloudDisk.common.AuthCommand;
import com.master.cloudDisk.common.Interfaces.ICommand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
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

    // Перетаскиваем с зажатой мышкой (до бросания)
    public void setOnDragOver(DragEvent dragEvent) {
//        System.out.println("dragEvent: ");
//        System.out.println(dragEvent.getGestureSource()); // null
//        System.out.println(dragEvent.getSource()); // TableView[id=pathTable, styleClass=table-view]
//        System.out.println(dragEvent.getEventType()); // DRAG_OVER
//        System.out.println(dragEvent.getDragboard().getFiles().toString()); // [D:\!Learning\GeekUnivers\Java5\Lesson3\netty-servers\netty-servers\1.txt]
//        System.out.println(dragEvent.getDragboard().getContentTypes()); // [[FileName], [DisableDragText], [application/x-java-file-list, java.file-list], [IsComputingImage], [DragImageBits], [ComputedDragImage], [FileNameW], [DragSourceHelperFlags], [text/uri-list], [IsShowingLayered], [DropDescription], [IsShowingText], [Shell IDList Array], [InShellDragLoop], [UsingDefaultDragImage], [DragWindow]]
//        System.out.println(dragEvent.getAcceptingObject().toString());
        if (dragEvent.getGestureSource() != dragEvent.getSource() && dragEvent.getDragboard().hasFiles()){
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        dragEvent.consume();
    }

    // Бросание объекта
    public void setOnDragDropped(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
//            dropped.setText(db.getFiles().toString());
            String fileName = db.getFiles().toString();
            System.out.println("Файл получен: " + db.getFiles().toString());
            // TODO: Прикрутить отображение и отправку файла

            success = true;
        }
        dragEvent.setDropCompleted(success);
        dragEvent.consume();
    }
}
