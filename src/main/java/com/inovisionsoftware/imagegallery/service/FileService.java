package com.inovisionsoftware.imagegallery.service;

import com.inovisionsoftware.imagegallery.model.Folder;
import com.inovisionsoftware.imagegallery.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class.getName());

    @Value("${imagegallery.basedirectory:./}")
    private String baseDirectory;

    public Folder getRootFolder() throws IOException {
        Folder root = new Folder();
        root.id = "ROOT";
        root.text = "ROOT";
        root.relativePath = ".";
        root.children = getFolders("ROOT",null);
        return root;
    }

    public List<Folder> getFolders(String parentId, String name) throws IOException {
        Path folderpath;
        if(name == null) {
            folderpath = Path.of(baseDirectory);
        } else {
            folderpath = Path.of(baseDirectory, name);
        }
        LOGGER.info("Getting files for path: " + folderpath.toString());
        //Skip 1 - which is the folder itself
        List<Folder> folders = Files.find(folderpath, 4, (path, attrs) -> attrs.isDirectory())
                .skip(1).map(Folder::create).collect(Collectors.toList());
        folders.forEach(f -> {
            System.out.println(f.parent);
            if(baseDirectory.equals(f.parent)) {
                f.parent = parentId;
                f.relativePath = f.text;
            } else {
                f.parent = f.parent.substring(f.parent.lastIndexOf(File.separator) + 1);
                f.url = "/api/folders/" + f.parent + "/" + f.text;
                f.relativePath = f.parent + "/" + f.text;
            }
        });
        return folders;
    }

    public List<String> getFiles(String parent) throws IOException {
        Path folderpath;
        if(parent == null || parent.equals("ROOT")) {
            folderpath = Path.of(baseDirectory);
        } else {
            folderpath = Path.of(baseDirectory, parent);
        }
        System.out.println("Getting files from " + folderpath);
        return Files.find(folderpath, 1, (path, attrs) -> attrs.isRegularFile() && fileFilter(path.getFileName().toString()))
                .map(path -> path.getFileName().toString()).collect(Collectors.toList());
    }

    private boolean fileFilter(String name) {
        for(String ext: FileUtils.allowedExtensions) {
            if(name.toLowerCase().endsWith(ext)) {
                return true;
            } else {
                //System.out.println("Does not match: " + name);
            }
        }
        return false;
    }
}
