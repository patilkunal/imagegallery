package com.inovisionsoftware.imagegallery.model;

import java.nio.file.Path;
import java.util.List;

public class Folder {
    
    public String name;
    public boolean directory;
    public List<Folder> folders;
    public List<String> files;

    public static Folder create(Path path) {
        Folder f = new Folder();
        f.directory = path.toFile().isDirectory();
        f.name = path.toString();
        return f;
    }
}
