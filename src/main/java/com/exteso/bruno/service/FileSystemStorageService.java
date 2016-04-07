package com.exteso.bruno.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileSystemStorageService implements StorageService {

    private static final String FILES_DIR = "/tmp/bruno/files/";

    public void read(String key, OutputStream os) throws IOException {
        Files.copy(Paths.get(FILES_DIR, key), os);
    }

    public void delete(String key) throws IOException {
        Files.deleteIfExists(Paths.get(FILES_DIR, key));
    }

    public String write(String key, InputStream input) throws IOException {
        new File(FILES_DIR).mkdirs();
        Path tmp = Paths.get(FILES_DIR, key);
        Files.copy(input, tmp, StandardCopyOption.REPLACE_EXISTING);
        return key;
    }

}
