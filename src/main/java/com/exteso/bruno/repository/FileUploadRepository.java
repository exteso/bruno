package com.exteso.bruno.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    
    @Query("delete from b_job_request_attachments where job_request_fk = :requestId and file_id_fk = (select file_id from b_file_upload where file_hash = :hash)")
    int removeFromJobRequest(@Bind("requestId") long requestId, @Bind("hash") String hash);
    
    @Query("select file_path from b_file_upload where file_hash = :hash")
    String getPath(@Bind("hash") String hash);
    
    @Query("select file_content_type from b_file_upload where file_hash = :hash")
    String getContentType(@Bind("hash") String hash);

    @Query("select file_hash, file_content_type from b_file_upload inner join b_job_request_attachments on file_id_fk = file_id where job_request_fk = :id")
    List<UploadedFile> findUploadedFilesForRequest(@Bind("id") long id);

    @Query("select file_id from b_file_upload left  join b_job_request_attachments on file_id = file_id_fk where file_id_fk is null and file_upload_date < :date")
    List<Long> findUnusedFilesOlderThan(@Bind("date") Date date);

    @Query("delete from b_file_upload where file_id = :id")
    int delete(@Bind("id") long id);

    @Query("select file_path from b_file_upload where file_id = :id")
    Optional<String> getPath(@Bind("id") long unusedId);
}
