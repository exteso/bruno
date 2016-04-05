package com.exteso.bruno.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.exteso.bruno.model.UserIdentifier;
import com.exteso.bruno.repository.FileUploadRepository;
import com.exteso.bruno.repository.UserRepository;
import com.exteso.bruno.service.FileService;

@Controller
public class FileUploadController {
    
    private final FileUploadRepository fileUploadRepository;
    private final UserRepository userRepository;
    private final FileService fileService;
    
    @Autowired
    public FileUploadController(FileService fileService, FileUploadRepository fileUploadRepository, UserRepository userRepository) {
        this.fileService = fileService;
        this.fileUploadRepository = fileUploadRepository;
        this.userRepository = userRepository;
    }
    

    @RequestMapping(value = "/api/file", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> upload(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        Path p = Files.createTempFile("bruno", "upload");
        try (InputStream fileIs = file.getInputStream()) {
            Files.copy(fileIs, p, StandardCopyOption.REPLACE_EXISTING);
            String digest = DigestUtils.sha256Hex(Files.newInputStream(p));
            
            //bypass, file already uploaded
            if(fileUploadRepository.count(digest) == 1) {
                return Collections.singletonMap("hash", digest);    
            }
            
            //
            String path = fileService.uploadFile(p, digest);
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

    private static Set<String> WHITE_LIST_MIME_TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(//
            "image/jpeg", "image/png", "image/webp", "image/bmp",// images
            "video/webm", "video/ogg", "video/mp4"//video
            )));

    @RequestMapping(value = "/api/file/{hash}", method = RequestMethod.GET)
    public void readFile(@PathVariable("hash") String hash, HttpServletResponse res) throws IOException {
        try (OutputStream os = res.getOutputStream()) {
            String contentType = fileUploadRepository.getContentType(hash);
            if (WHITE_LIST_MIME_TYPES.contains(contentType)) {
                res.setContentType(contentType);
            } else {
                res.setContentType("application/octet-stream");
            }
            fileService.read(hash, os);
        }
    }
}
