package com.bay.controller.post;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bay.common.dto.core.post.PostCustomerDTO;
import com.bay.common.dto.response.ResponseDTO;
import com.bay.facade.post.PostFacade;

@RestController
@RequestMapping("post/")
@Validated
@CrossOrigin(origins = "*")
public class PostController {

	@Autowired
	private PostFacade post;

	@RequestMapping(value = "add", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@ResponseStatus(HttpStatus.OK)
    ResponseDTO<PostCustomerDTO> add(@RequestPart("post") @Valid PostCustomerDTO post, @RequestPart("file[]") @Valid @NotNull @NotBlank MultipartFile[] files) {
		return this.post.add(post, files);
	}
	
}
