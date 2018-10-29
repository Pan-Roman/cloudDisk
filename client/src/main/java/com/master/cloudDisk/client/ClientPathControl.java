package com.master.cloudDisk.client;

import com.master.cloudDisk.common.PathControl;
import javafx.scene.control.TreeItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClientPathControl extends PathControl {
    private String currentPath;

    public ClientPathControl(String rootPath) {
        super(rootPath);
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    public Path getCurrentPath() {
        return Paths.get(currentPath);
    }

    public static String getFullPathName(TreeItem<String> selectedItem) {
        String fullPathName = selectedItem.getValue();
        try {
            fullPathName = getFullPathName(selectedItem.getParent()) + "\\" + fullPathName;
        } catch (NullPointerException ex) {
            return Client.getMainPath();
        }
        return fullPathName;
    }

    public static byte[] getByteArrFromFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        return Files.readAllBytes(path);
    }
}
