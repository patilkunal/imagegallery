package com.inovisionsoftware.imagegallery;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovisionsoftware.imagegallery.model.Folder;
import java.util.List;

@RestController
@RequestMapping("/folders")
public class FoldersController {
    
    @GetMapping("/")
    public List<Folder> getRootFolder() {
        return null;
    }

    @GetMapping("/{folder}")
    public List<Folder> getFolder(String folder) {
        return null;

    }

    @GetMapping("/{folder}/files")
    public List<String> getFiles(String folder) {
        return null;

    }


}
