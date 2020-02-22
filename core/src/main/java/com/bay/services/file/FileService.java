package com.bay.services.file;

import java.util.List;

import com.bay.common.dto.file.FileDTO;
import com.bay.common.dto.file.UploadFileResponse;

public interface FileService<T> {
	List<UploadFileResponse> add(FileDTO<T> file);
	List<UploadFileResponse> delete(FileDTO<T> file);
}
