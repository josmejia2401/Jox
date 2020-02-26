package com.bay.core.services.post;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bay.common.dto.core.post.PostFileDTO;
import com.bay.common.dto.filemananer.UploadFileResponse;

public interface PostFileService {
	List<PostFileDTO> getAll(Long id);
	List<PostFileDTO> add(Long idPost, MultipartFile[] files, List<UploadFileResponse> result);
	List<PostFileDTO> delete(Long idPost);
}
