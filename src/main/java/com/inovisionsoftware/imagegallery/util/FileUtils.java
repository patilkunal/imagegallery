package com.inovisionsoftware.imagegallery.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    private static String imageFileExts[] = {"jpg", "bmp", "gif", "png"};
    public static boolean hasImageFiles(Path path) {
        try {
            return Files.find(path, 1, (p, attrs) -> isImageFilename(p.toFile().getName().toLowerCase()))
                    .findAny().isPresent();
        } catch (IOException ignored) {}
        return false;
    }

    private static boolean isImageFilename(String name) {
        for(String ext: imageFileExts) {
            if(name.endsWith(ext))
                return true;
        }
        return false;
    }
}
