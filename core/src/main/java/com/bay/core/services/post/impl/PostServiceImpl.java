package com.bay.core.services.post.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bay.common.dto.core.location.LocationDTO;
import com.bay.common.dto.filemananer.FileDTO;
import com.bay.common.dto.filemananer.UploadFileResponse;
import com.bay.common.dto.post.PostCustomerDTO;
import com.bay.common.dto.post.PostFileDTO;
import com.bay.common.dto.response.ResponseDTO;
import com.bay.common.exceptions.BayException;
import com.bay.common.exceptions.CustomException;
import com.bay.common.util.MessageUtil;
import com.bay.core.repositories.post.PostRepository;
import com.bay.core.services.file.FileService;
import com.bay.core.services.post.PostFileService;
import com.bay.core.services.post.PostService;
import com.bay.entity.core.post.TblPostCustomer;

@Service
public class PostServiceImpl implements PostService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private PostFileService postFileService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private MessageUtil messageUtil;
	
	@Override
	public ResponseDTO<List<PostCustomerDTO>> getAll(LocationDTO location) {
		return null;
	}

	@Override
	public ResponseDTO<List<PostCustomerDTO>> getMe(String username) {
		List<PostCustomerDTO> x = this.postRepo.findByUserId(Long.valueOf(username)).stream().map(i-> {
			return modelMapper.map(i, PostCustomerDTO.class);
		}).collect(Collectors.toList());
		ResponseDTO<List<PostCustomerDTO>> xy = new ResponseDTO<>();
		xy.setCode(0L);
		xy.setData(x);
		xy.setMessage("OK");
		return xy;
	}

	@Override
	public ResponseDTO<PostCustomerDTO> add(PostCustomerDTO post, MultipartFile[] files) {
		LOGGER.debug("PostServiceImpl.add start with data: {}", post);
		try {
			if (post != null) {
				ResponseDTO<PostCustomerDTO> response = new ResponseDTO<>();
				List<UploadFileResponse> result = null;
				if (files != null && files.length > 0) {
					result = this.saveFiles(post, files);
				}
				TblPostCustomer entity = modelMapper.map(post, TblPostCustomer.class);
				entity = postRepo.save(entity);
				if (entity != null && entity.getId() != null && entity.getId() != 0) {
					List<PostFileDTO> postFiles = null;
					if (result != null && !result.isEmpty()) {
						postFiles = this.postFileService.add(entity.getId(), files, result);
					}
					post.setId(entity.getId());
					post.setPostFiles(postFiles);
					response.setCode(0L);
					response.setData(post);
				} else {
					response.setCode(1L);
					response.setData(null);
					response.setMessage(messageUtil.getMessage("customer.post.error.internal_failure"));
				}
				return response;
			} else {
				throw new BayException(messageUtil.getMessage("customer.post.error.required_information"));
			}
		} catch (BayException e) {
			LOGGER.error("ERROR: CustomerServiceImpl.signIn.BayException", e);
			throw e;
		} catch (Exception e) {
			LOGGER.error("ERROR: CustomerServiceImpl.signIn.Exception", e);
			throw new CustomException(messageUtil.getMessage("customer.post.error.internal_failure"), e);
		} finally {
			LOGGER.debug("CustomerServiceImpl.signIn finish");
		}
	}

	@Override
	public ResponseDTO<PostCustomerDTO> delete(PostCustomerDTO post) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private List<UploadFileResponse> saveFiles(final PostCustomerDTO dto, final MultipartFile[] files) {
		FileDTO file = new FileDTO();
		file.setUsername(dto.getIdCustomer().toString());
		return this.fileService.add(file, files);
	}
}
