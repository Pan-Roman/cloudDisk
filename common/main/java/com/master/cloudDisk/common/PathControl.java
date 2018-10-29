package com.master.cloudDisk.common;

import javafx.scene.control.TreeItem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class PathControl {
    private Path rootPath;


    public PathControl(String rootPath) {
        setRootPath(rootPath);
    }

    public Path getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        Path path = Paths.get(rootPath);
        checkPath(path);
        this.rootPath = path;
    }

    public void checkPath(Path path){
        if(!Files.exists(path)){
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getRelativePath(Path path){
        String relativePath = getCurrentPath().relativize(path).toString();
        return relativePath;
    }

    protected abstract Path getCurrentPath();


    public void putToCurrentPath(Path filePath){
        String fileName = filePath.getFileName().toString();
        // TODO ...
    }

    public static FileChannel getChannelFromFile(String fileName){
        RandomAccessFile srcFile = null;
        try {
            srcFile = new RandomAccessFile(fileName, "rw");
//            srcFile.getChannel().
        } catch (FileNotFoundException e) {
            System.out.println("NOT FOUND FILE - " + fileName);
//            e.printStackTrace();
            return null;
        }
        return srcFile.getChannel();
    }

    public List<String> getList (Path path){ // L2 1.36.21 (ByteBuffer ~ 1.53.00)
        List<String> paths = new ArrayList<>();
//        path.
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
            paths.add(path.toString());
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return paths;
    }

}
