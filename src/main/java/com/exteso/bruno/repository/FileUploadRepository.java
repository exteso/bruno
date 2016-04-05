package com.exteso.bruno.repository;

import ch.digitalfondue.npjt.Bind;
import ch.digitalfondue.npjt.Query;
import ch.digitalfondue.npjt.QueryRepository;

@QueryRepository
public interface FileUploadRepository {
    
    @Query("select count(*) from b_file_upload where file_hash = :hash")
    int count(@Bind("hash") String hash);

    @Query("insert into b_file_upload(file_hash, file_name, file_content_type, file_path, file_user_id_fk) " +
            " values (:hash, :name, :contentType, :path, :userId)")
    int add(@Bind("hash") String hash, @Bind("name") String name, @Bind("contentType") String contentType, @Bind("path") String path, @Bind("userId") long userId);

    @Query("insert into b_job_request_attachments(file_id_fk, job_request_fk) values ((select file_id from b_file_upload where file_hash = :hash) , :requestId)")
    int addToJobRequest(@Bind("requestId") long requestId, @Bind("hash") String hash);
}
