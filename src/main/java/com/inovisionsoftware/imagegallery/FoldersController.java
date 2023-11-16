package com.inovisionsoftware.imagegallery;

import com.inovisionsoftware.imagegallery.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovisionsoftware.imagegallery.model.Folder;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/api/folders")
public class FoldersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FoldersController.class.getName());
    private final FileService fileService;

    public FoldersController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/api/folders")
    public List<Folder> getRootFolder() throws IOException {
        LOGGER.info("Getting Root folders");
        Folder root = new Folder();
        root.parent = "#";
        root.id = "ROOT";
        root.text = "ROOT";
        List<Folder> folders = new ArrayList<>();
        folders.add(root);
        folders.addAll(fileService.getFolders("ROOT", null));
        return folders;
    }

    @GetMapping("/api/folders/**")
    public List<Folder> getFolders(HttpServletRequest request) throws IOException {
        String restOfTheUrl = new AntPathMatcher().extractPathWithinPattern(request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(),request.getRequestURI());
        LOGGER.info("Getting folders for " + restOfTheUrl);
        return fileService.getFolders(restOfTheUrl, restOfTheUrl);
    }

    @GetMapping("/api/files/**")
    public List<String> getFiles(HttpServletRequest request) throws IOException {
        String restOfTheUrl = new AntPathMatcher().extractPathWithinPattern(request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(),request.getRequestURI());
        LOGGER.info("Getting files for " + restOfTheUrl);
        return fileService.getFiles(restOfTheUrl);
    }


}
