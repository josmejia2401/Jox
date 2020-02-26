package com.bay.core.facade.post.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bay.common.dto.core.LocationDTO;
import com.bay.common.dto.core.post.PostCustomerDTO;
import com.bay.common.dto.response.ResponseDTO;
import com.bay.core.facade.post.PostFacade;
import com.bay.core.services.post.PostService;

@Service
public class PostFacadeImpl implements PostFacade {

	@Autowired
	private PostService postService;

	@Override
	public ResponseDTO<List<PostCustomerDTO>> getAll(LocationDTO location) {
		return this.postService.getAll(location);
	}

	@Override
	public ResponseDTO<List<PostCustomerDTO>> getMe(String username) {
		return this.postService.getMe(username);
	}

	@Override
	public ResponseDTO<PostCustomerDTO> add(PostCustomerDTO post, MultipartFile[] files) {
		return this.postService.add(post, files);
	}

	@Override
	public ResponseDTO<PostCustomerDTO> delete(PostCustomerDTO post) {
		return this.postService.delete(post);
	}

}
