package com.master.cloudDisk.client;

import com.master.cloudDisk.client.Interfaces.IClient;
import com.master.cloudDisk.client.View.FileView;
import com.master.cloudDisk.common.commands.AuthCommand;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public HBox AuthBox;
    @FXML
    public TextField LoginField;
    @FXML
    public PasswordField PassField;

    @FXML
    private TreeView<String> pathTree;

    @FXML
    private TableView<FileView> pathTable;
    @FXML
    private TableColumn<FileView, String> pathTableColumnName;
    @FXML
    private TableColumn<FileView, String> pathTableColumnDate;
    @FXML
    private TableColumn<FileView, String> pathTableColumnType;
    @FXML
    private TableColumn<FileView, Long> pathTableColumnSize;

    private IClient client;
    private ObservableList<FileView> fileData = FXCollections.observableArrayList();
    private String currentPath;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        TreeItem<String> root = new TreeItem<String>(Client.getMainPathName());
        pathTree.setRoot(root);
        pathTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                TreeItem<String> selectedItem = newValue;
                Client.getPathControl().setCurrentPath(ClientPathControl.getFullPathName(selectedItem));
                fillTableViewFromPath(Client.getPathControl().getCurrentPath());
                updateTree(selectedItem);
            }
        });
        pathTree.getSelectionModel().select(root);
        initializeTableView();
        Client.getPathControl().getRelativePath(Paths.get("C:\\Users\\Romio\\CloudDisk\\FirstLevel_2\\SecondLevel_2\\2.txt"));
    }

    public TreeItem<String> updateTree(TreeItem<String> item) {
        Path path = Paths.get(ClientPathControl.getFullPathName(item));
        // clean path
        item.getChildren().removeAll(item.getChildren());
        try {

            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                TreeItem<String> parent;
                TreeItem<String> currItem = item;

                @Override
                public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                    parent = currItem;
                    if (!currItem.getValue().equals(path.getFileName().toString())) {
                        currItem = new TreeItem<String>(path.getFileName().toString());
                        parent.getChildren().add(currItem);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path path, IOException e) throws IOException {
                    currItem = currItem.getParent();
                    try {
                        parent = parent.getParent();
                    } catch (NullPointerException ex) {
                        parent = null;
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
        }

        return item;
    }

    public void copyPath(Path source, Path dest) {
        try {
            Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
            if (Files.isDirectory(source)) {
                Files.walk(source, 1)
                        .filter(path1 -> (!path1.getFileName().toString().equals(dest.getFileName().toString())))
                        .forEach(path1 -> copyPath(path1, Paths.get(dest.toString() + "\\" + path1.getFileName().toString())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFilesToCloud(List<File> files) {
        for (int i = 0; i < files.size(); i++) {
            Path path = files.get(i).toPath();
                Path dest = Paths.get(Client.getPathControl().getCurrentPath().toString() + "\\" + path.getFileName());
                copyPath(path, dest);
            fileData.add(new FileView(path));
            // TODO: Раскоментить, как будет готово
            ServerHandler.getInstance().sendPath(path);
        }
        updateTree(pathTree.getFocusModel().getFocusedItem());
        pathTable.setItems(fileData);
    }

    public void fillTableViewFromPath(Path path) {
        fileData.removeAll();
        fileData.clear();
        if (Files.isDirectory(path)) {
            try {
                Files.walk(path, 1)
                        .filter(path1 -> (!path.getFileName().toString().equals(path1.getFileName().toString())))
                        .forEach(path1 -> fileData.add(new FileView(path1)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        pathTable.setItems(fileData);
    }

    public void initializeTableView() {
        // устанавливаем тип и значение которое должно хранится в колонке
        pathTableColumnName.setCellValueFactory(new PropertyValueFactory<FileView, String>("name"));
        pathTableColumnDate.setCellValueFactory(new PropertyValueFactory<FileView, String>("date"));
        pathTableColumnType.setCellValueFactory(new PropertyValueFactory<FileView, String>("type"));
        pathTableColumnSize.setCellValueFactory(new PropertyValueFactory<FileView, Long>("size"));
        pathTable.setItems(fileData);
    }

    public TableView<FileView> getPathTable() {
        return pathTable;
    }

    public void setRootPath(Path rootPath) {
        getPathTree().getRoot().setValue(rootPath.getFileName().toString());
    }

    public void test(ActionEvent event) {
        System.out.println("TEST! ");
    }

    public void auth(ActionEvent event) {
        String pass = Client.md5(AuthCommand.salt + LoginField.getText() + PassField.getText());
        System.out.println("Login: " + LoginField.getText() + " Password: " + pass);
        ServerHandler.getInstance().auth(LoginField.getText(), pass);
    }

    // Перетаскиваем с зажатой мышкой (до бросания)
    public void setOnDragOver(DragEvent dragEvent) {
        if (dragEvent.getGestureSource() != dragEvent.getSource() && dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.COPY); // COPY_OR_MOVE
//            dragEvent.getDragboard().setDragView(dragEvent.getDragboard().getDragView()); // как получить картинку файла??
        }
//        else{
//            System.out.println(dragEvent.getDragboard().getContentTypes());
//            if(dragEvent.getDragboard().hasFiles())
//            System.out.println(dragEvent.getDragboard().getFiles().toString());
//        }
        dragEvent.consume();
    }

    // Бросание объекта
    public void setOnDragDropped(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
//            dropped.setText(db.getFiles().toString());
//            String fileName = db.getFiles().toString();
            List<File> files = db.getFiles();
            addFilesToCloud(files);
            success = true;
        }
        dragEvent.setDropCompleted(success);
        dragEvent.consume();
    }

    public void setClient(IClient client) {
        this.client = client;
    }

    private TreeView<String> getPathTree() {
        return pathTree;
    }

    public void onMouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2){
            System.out.println("We have double Click!!");
            String pathName = getPathTable().getSelectionModel().getSelectedItem().getName();
            String fullPathName = Client.getPathControl().getCurrentPath() + "\\" + pathName;
            Path path = Paths.get(fullPathName);

            if(Files.isDirectory(path)){
                // TODO:
                System.out.println("Открытие директории ещё не реализовано!");
            }else {
                try {
                    Desktop.getDesktop().open(path.toFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
