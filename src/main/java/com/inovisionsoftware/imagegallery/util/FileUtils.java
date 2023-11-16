package com.inovisionsoftware.imagegallery.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    public static String[] allowedExtensions = {"jpg", "jpeg", "png", "bmp", "gif", "webp"};
    public static boolean hasImageFiles(Path path) {
        try {
            return Files.find(path, 3, (p, attrs) -> isImageFilename(p.toFile().getName().toLowerCase()))
                    .findAny().isPresent();
        } catch (IOException ignored) {}
        return false;
    }

    private static boolean isImageFilename(String name) {
        for(String ext: allowedExtensions) {
            if(name.endsWith(ext))
                return true;
        }
        return false;
    }
}
