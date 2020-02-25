package com.bay.filemanager.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bay.common.dto.filemananer.FileDTO;
import com.bay.filemanager.controller.FilePostController;
import com.bay.filemanager.exception.FileStorageException;
import com.bay.filemanager.exception.MyFileNotFoundException;
import com.bay.filemanager.property.FileStorageProperties;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    public final static String resources = "resources";
    private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif");
    
    private String getIdPost() {
    	return String.valueOf(new Date().getTime());
    }

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    
    public String storeFile(MultipartFile file, FileDTO user) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
        	String id =  this.getIdPost();
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            if(!contentTypes.contains(file.getContentType().toLowerCase())) {
            	throw new FileStorageException("Sorry! Filename contains invalid extension " + fileName);
            }
            Path pathFile  = Paths.get(user.getUsername(), resources, FilePostController.file_saved, id, fileName);
            Path targetLocation = this.fileStorageLocation.resolve(pathFile);
            targetLocation.toFile().mkdirs();
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            Path pathFileOutPut  = Paths.get(user.getUsername(), id, fileName);
            String newStr = pathFileOutPut.toString().replaceAll(Pattern.quote("\\"), "/");
            return newStr;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
        	Path pathFile  = Paths.get(fileName);    
            Path filePath = this.fileStorageLocation.resolve(pathFile).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
