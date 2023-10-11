package com.inovisionsoftware.imagegallery.service;

import com.inovisionsoftware.imagegallery.model.Folder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Value("${imagegallery.basedirectory:./}")
    private String baseDirectory;

    public List<Folder> getFolders(String parent) throws IOException {
        Path folderpath;
        if(parent == null) {
            folderpath = Path.of(baseDirectory);
        } else {
            folderpath = Path.of(baseDirectory, parent);
        }
        return Files.find(folderpath, 1, (path, attrs) -> attrs.isDirectory())
                .map(Folder::create).collect(Collectors.toList());
    }

    public List<String> getFiles(String parent) throws IOException {
        Path folderpath;
        if(parent == null) {
            folderpath = Path.of(baseDirectory);
        } else {
            folderpath = Path.of(baseDirectory, parent);
        }
        System.out.println("Getting files from " + folderpath);
        return Files.find(folderpath, 1, (path, attrs) -> attrs.isRegularFile() && fileFilter(path.getFileName().toString()))
                .map(path -> path.getFileName().toString()).collect(Collectors.toList());
    }

    private boolean fileFilter(String name) {
        String[] allowedExtensions = {"jpg", "jpeg", "png", "bmp", "gif", "png'"};
        for(String ext: allowedExtensions) {
            if(name.toLowerCase().endsWith(ext)) {
                return true;
            } else {
                //System.out.println("Does not match: " + name);
            }
        }
        return false;
    }
}
