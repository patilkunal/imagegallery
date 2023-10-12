package com.inovisionsoftware.imagegallery;

import com.inovisionsoftware.imagegallery.service.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovisionsoftware.imagegallery.model.Folder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/folders")
public class FoldersController {

    private final FileService fileService;

    public FoldersController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping()
    public List<Folder> getRootFolder() throws IOException {
        Folder root = new Folder();
        root.parent = "#";
        root.id = "ROOT";
        root.text = "ROOT";
        List<Folder> folders = new ArrayList<>();
        folders.add(root);
        folders.addAll(fileService.getFolders("ROOT", null));
        return folders;
    }

    @GetMapping("/{folder}")
    public List<Folder> getFolders(@PathVariable("folder") String folder) throws IOException {
        return fileService.getFolders(folder, folder);
    }

    @GetMapping("/{folder}/files")
    public List<String> getFiles(@PathVariable("folder") String folder) throws IOException {
        return fileService.getFiles(folder);
    }


}
