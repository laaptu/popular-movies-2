package com.laaptu.popmovies;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtils {
    public static String getStringFromResources(String filePath) {
        try {
            URL url = TestUtils.class.getClassLoader().getResource(filePath);
            Path path = Paths.get(url.toURI());
            return new String(Files.readAllBytes(path), "UTF8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
