package com.exteso.bruno.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exteso.bruno.repository.FileUploadRepository;

@Service
public class FileService {

    private final FileUploadRepository fileUploadRepository;
    private final StorageService storageService;

    @Autowired
    public FileService(FileUploadRepository fileUploadRepository, StorageService storageService) {
        this.fileUploadRepository = fileUploadRepository;
        this.storageService = storageService;
    }

    public String uploadFile(Path p, String hash) throws IOException {
        try(InputStream is = Files.newInputStream(p)) {
            return storageService.write(hash, is);
        }
    }

    public void read(String hash, OutputStream os) throws IOException {
        storageService.read(hash, os);
    }

    public void removeUnusedFilesOlderThan(Date dateLimit) {
        List<Long> unused = fileUploadRepository.findUnusedFilesOlderThan(dateLimit);
        for (long unusedId : unused) {
            // FIXME delete from "s3"
            fileUploadRepository.getPath(unusedId).ifPresent((file) -> {
                try {
                    storageService.delete(file);
                    fileUploadRepository.delete(unusedId);
                } catch (IOException e) {
                    //TODO LOG
                }
            });
        }
    }
}
