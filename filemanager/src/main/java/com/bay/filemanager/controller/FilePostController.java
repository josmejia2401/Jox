package com.bay.filemanager.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bay.common.dto.filemananer.FileDTO;
import com.bay.common.dto.filemananer.UploadFileResponse;
import com.bay.filemanager.service.FileStorageService;

@RestController
@RequestMapping("post/")
@CrossOrigin(origins = "*")
public class FilePostController {
	
    private static final Logger logger = LoggerFactory.getLogger(FilePostController.class);
    public final static String file_saved = "post";
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @PostMapping("upload")
    public UploadFileResponse uploadFile(@RequestParam("file[]") @Valid @NotNull @NotBlank MultipartFile file, @RequestParam("user") @Valid FileDTO user) {
        String fileName = fileStorageService.storeFile(file, user);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/post/download/").path(fileName).toUriString();
        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }
    
    @PostMapping("uploadFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("file[]") @Valid @NotNull @NotBlank MultipartFile[] files, @RequestParam("user") @Valid FileDTO user) {
        return Arrays.asList(files).stream().map(file -> uploadFile(file, user)).collect(Collectors.toList());
    }

    @GetMapping("download/{username}/{id}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String username, @PathVariable String id,@PathVariable String fileName, HttpServletRequest request) {
        final String pathFile = Paths.get(username, FileStorageService.resources, FilePostController.file_saved, id, fileName).toString();
    	Resource resource = fileStorageService.loadFileAsResource(pathFile);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + resource.getFilename() + "\"").body(resource);
    }

}
