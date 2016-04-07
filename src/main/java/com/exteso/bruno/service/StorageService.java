package com.exteso.bruno.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StorageService {
    void read(String key, OutputStream os) throws IOException;

    void delete(String key) throws IOException;

    String write(String key, InputStream input) throws IOException;
}
