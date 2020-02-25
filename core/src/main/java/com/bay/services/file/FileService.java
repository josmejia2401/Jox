package com.bay.services.file;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bay.common.dto.filemananer.FileDTO;
import com.bay.common.dto.filemananer.UploadFileResponse;
import com.bay.common.exceptions.FileManagerException;

public interface FileService {
	List<UploadFileResponse> add(FileDTO info, MultipartFile[] file) throws FileManagerException;
	List<UploadFileResponse> delete(FileDTO info) throws FileManagerException;
}
