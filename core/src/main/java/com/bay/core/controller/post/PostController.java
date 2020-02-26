package com.bay.core.controller.post;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bay.common.dto.post.PostCustomerDTO;
import com.bay.common.dto.response.ResponseDTO;
import com.bay.core.facade.post.PostFacade;

@RestController
@RequestMapping("post/")
@Validated
@CrossOrigin(origins = "*")
public class PostController {

	@Autowired
	private PostFacade post;

	@PostMapping("add")
	@ResponseStatus(HttpStatus.OK)
    ResponseDTO<PostCustomerDTO> add(@RequestParam("file[]") @Valid @NotNull MultipartFile[] file, @RequestParam("user") @Valid  PostCustomerDTO post) {
		return this.post.add(post, file);
	}
	
}
