package com.exteso.bruno.model;

import ch.digitalfondue.npjt.ConstructorAnnotationRowMapper.Column;

public class UploadedFile {

    private final String hash;
    private final String contentType;

    public UploadedFile(@Column("file_hash") String hash, @Column("file_content_type") String contentType) {
        this.hash = hash;
        this.contentType = contentType;
    }

    public String getHash() {
        return hash;
    }

    public String getContentType() {
        return contentType;
    }
}
