package com.exteso.bruno.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exteso.bruno.repository.FileUploadRepository;

@Service
public class FileService {

    private final FileUploadRepository fileUploadRepository;

    @Autowired
    public FileService(FileUploadRepository fileUploadRepository) {
        this.fileUploadRepository = fileUploadRepository;
    }

    public String uploadFile(Path p, String hash) throws IOException {
        // FIXME upload to "s3"
        new File("/tmp/bruno/files/").mkdirs();
        Path tmp = Paths.get("/tmp/bruno/files/", hash);
        Files.copy(p, tmp, StandardCopyOption.REPLACE_EXISTING);
        return tmp.toString();
    }

    public void read(String hash, OutputStream os) throws IOException {
        String path = fileUploadRepository.getPath(hash);
        Files.copy(Paths.get(path), os);
    }

    public void removeUnusedFilesOlderThan(Date dateLimit) {
        List<Long> unused = fileUploadRepository.findUnusedFilesOlderThan(dateLimit);
        for (long unusedId : unused) {
            // FIXME delete from "s3"
            fileUploadRepository.getPath(unusedId).ifPresent((file) -> {
                try {
                    Path path = Paths.get(file);
                    Files.deleteIfExists(path);
                    fileUploadRepository.delete(unusedId);
                } catch (IOException e) {
                    //TODO LOG
                }
            });
        }
    }
}
