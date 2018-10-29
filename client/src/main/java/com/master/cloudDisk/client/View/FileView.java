package com.master.cloudDisk.client.View;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;

public class FileView {
    private String name;
    private String date;
    private String type;
    private long size;

    public FileView(Path path) {
        name = path.getFileName().toString();
        try {
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            date = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(attr.creationTime().toMillis());
            type = getFileExt(path);
            size = attr.size();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public long getSize() {
        return size;
    }

    private String getFileExt(Path path){
        if(Files.isDirectory(path)){
            return "DIR";
        }
        String ext = "";
        int i = name.lastIndexOf('.');
        int p = Math.max(name.lastIndexOf('/'), name.lastIndexOf('\\'));
        if (i > p) {
            ext = name.substring(i+1);
        }
        return ext;
    }

}
