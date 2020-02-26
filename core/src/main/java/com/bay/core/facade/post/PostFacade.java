package com.bay.core.facade.post;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bay.common.dto.core.location.LocationDTO;
import com.bay.common.dto.post.PostCustomerDTO;
import com.bay.common.dto.response.ResponseDTO;

public interface PostFacade {
	ResponseDTO<List<PostCustomerDTO>> getAll(LocationDTO location);
	ResponseDTO<List<PostCustomerDTO>> getMe(String username);
	ResponseDTO<PostCustomerDTO> add(PostCustomerDTO post, MultipartFile[] files);
	ResponseDTO<PostCustomerDTO> delete(PostCustomerDTO post);
}
