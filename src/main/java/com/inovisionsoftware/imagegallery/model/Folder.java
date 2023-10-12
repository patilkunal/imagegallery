package com.inovisionsoftware.imagegallery.model;

import com.inovisionsoftware.imagegallery.util.FileUtils;

import java.nio.file.Path;
import java.util.List;

public class Folder {

    public String id;
    public String text;
    public String parent = "#";
    public boolean directory;
    public String url;
    public State state;
    public List<Folder> children;

    public static Folder create(Path path) {
        Folder f = new Folder();
        f.directory = path.toFile().isDirectory();
        f.text = path.toFile().getName();
        f.id = f.text;
        f.url = "/api/folders/" + f.text;
        //TODO: disabled = no image files in the directory
        f.state = new State(!FileUtils.hasImageFiles(path));
        return f;
    }

    private static class State {
        public boolean disabled;
        public State(boolean disabled) {
            this.disabled = disabled;
        }
    }
}
