package com.exteso.bruno.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.utils.IOUtils;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class S3StorageService implements StorageService {

    private final AmazonS3Client amazonS3Client;
    private final String bucketName;

    public S3StorageService(String accessKey, String secretKey, String bucketName, String endpoint) {
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setSignerOverride("S3SignerType");
        amazonS3Client = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey), configuration);
        amazonS3Client.setEndpoint(endpoint);
        
        
        //no bucket named bucketName, will try to create a new one
        if (!amazonS3Client.listBuckets().stream().map(Bucket::getName).anyMatch(bucketName::equals)) {
            amazonS3Client.createBucket(bucketName);
        }
        
        this.bucketName = bucketName;
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
