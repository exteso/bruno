package com.exteso.bruno.web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exteso.bruno.model.UserIdentifier;
import com.exteso.bruno.repository.FileUploadRepository;
import com.exteso.bruno.repository.UserRepository;

@RestController
public class FileUploadController {
    
    private final FileUploadRepository fileUploadRepository;
    private final UserRepository userRepository;
    
    @Autowired
    public FileUploadController(FileUploadRepository fileUploadRepository, UserRepository userRepository) {
        this.fileUploadRepository = fileUploadRepository;
        this.userRepository = userRepository;
    }
    

    @RequestMapping(value = "/api/file", method = RequestMethod.POST)
    public Map<String, String> upload(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        Path p = Files.createTempFile("bruno", "upload");
        try (InputStream fileIs = file.getInputStream()) {
            Files.copy(fileIs, p, StandardCopyOption.REPLACE_EXISTING);
            String digest = DigestUtils.sha256Hex(Files.newInputStream(p));
            
            //FIXME upload to "s3"
            String path = null;
            UserIdentifier ui = UserIdentifier.from(principal);
            long userId = userRepository.getId(ui.getProvider(), ui.getUsername());
            String contentType = file.getContentType() != null ? file.getContentType() : "application/octet-stream";
            String name = file.getOriginalFilename();
            
            fileUploadRepository.add(digest, name, contentType, path, userId);
            
            return Collections.singletonMap("hash", digest);
        } finally {
            Files.delete(p);
        }
    }

    @RequestMapping(value = "/api/file/{hash}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("hash") String hash, Principal principal) {
        // check if file has same user
        // check if file is not linked
    }

    @RequestMapping(value = "/api/file/{hash}", method = RequestMethod.GET)
    public void readFile(@PathVariable("hash") String hash) {
    }
}
