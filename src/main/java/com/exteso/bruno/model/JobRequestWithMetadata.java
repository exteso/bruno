package com.exteso.bruno.model;

import java.util.List;

public class JobRequestWithMetadata extends JobRequest {

    private final List<UploadedFile> files;

    public JobRequestWithMetadata(JobRequest jobRequest, List<UploadedFile> files) {
        super(jobRequest.getId(), jobRequest.getCreationTime(), jobRequest.getCreationUser(), jobRequest.getAddress(), jobRequest.isUrgent(), jobRequest.getFailureType(),
                jobRequest.getDescription(), jobRequest.getRequestType(), jobRequest.getState());
        this.files = files;
    }

    public List<UploadedFile> getFiles() {
        return files;
    }
}
