package com.exteso.bruno.repository;

import java.util.List;

import com.exteso.bruno.model.UploadedFile;

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
    
    @Query("select file_path from b_file_upload where file_hash = :hash")
    String getPath(@Bind("hash") String hash);
    
    @Query("select file_content_type from b_file_upload where file_hash = :hash")
    String getContentType(@Bind("hash") String hash);

    @Query("select file_hash, file_content_type from b_file_upload inner join b_job_request_attachments on file_id_fk = file_id where job_request_fk = :id")
    List<UploadedFile> findUploadedFilesForRequest(@Bind("id") long id);
}
