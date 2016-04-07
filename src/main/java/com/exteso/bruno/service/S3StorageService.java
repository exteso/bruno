package com.exteso.bruno.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.utils.IOUtils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class S3StorageService implements StorageService {

    private final AmazonS3Client amazonS3Client;
    private final String bucketName;

    public S3StorageService() {
        amazonS3Client = new AmazonS3Client();
        bucketName = "bucketName";
    }

    @Override
    public String write(String key, InputStream input) {
        amazonS3Client.putObject(bucketName, key, input, new ObjectMetadata());
        return key;
    }

    @Override
    public void delete(String key) {
        amazonS3Client.deleteObject(bucketName, key);
    }

    @Override
    public void read(String key, OutputStream os) throws IOException {
        try (InputStream is = amazonS3Client.getObject(bucketName, key).getObjectContent()) {
            IOUtils.copy(is, os);
        }
    }
}
