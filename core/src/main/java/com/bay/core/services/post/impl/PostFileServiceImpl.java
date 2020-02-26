package com.bay.core.services.post.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bay.common.dto.core.post.PostFileDTO;
import com.bay.common.dto.filemananer.UploadFileResponse;
import com.bay.core.repositories.post.PostFileRepository;
import com.bay.core.services.post.PostFileService;
import com.bay.entity.core.post.TblPostFile;

@Service
public class PostFileServiceImpl implements PostFileService {

	//private static final Logger LOGGER = LoggerFactory.getLogger(PostFileServiceImpl.class);
	
	@Autowired
	private PostFileRepository postFileRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<PostFileDTO> getAll(Long id) {
		List<TblPostFile> x = this.postFileRepo.findByIdPost(id);
		if (x != null && !x.isEmpty()) {
			return x.stream().map(item -> modelMapper.map(item, PostFileDTO.class)).collect(Collectors.toList()); 
		} else {
			return null;
		}
	}

	@Override
	public List<PostFileDTO> add(Long idPost, MultipartFile[] files, List<UploadFileResponse> result) {
		List<PostFileDTO> postFiles = new ArrayList<>();
		for ( MultipartFile file: files) {
			PostFileDTO postFile = new PostFileDTO();
			String originaName = file.getOriginalFilename().toLowerCase();
			UploadFileResponse x = result.stream().filter(i-> i.getFileName().equalsIgnoreCase(originaName)).findFirst().get();
			if (x == null) {
				continue;
			}
			postFile.setContentType(x.getFileType());
			postFile.setFilename(x.getFileName());
			postFile.setFileUri(x.getFileDownloadUri());
			postFile.setIdPostCustomer(idPost);
			postFiles.add(postFile);
		}
		Type listType = new TypeToken<List<TblPostFile>>(){}.getType();
		List<TblPostFile> postDtoList = this.modelMapper.map(postFiles, listType);
		this.postFileRepo.saveAll(postDtoList);
		return postFiles;
	}

	@Override
	public List<PostFileDTO> delete(Long idPost) {
		List<TblPostFile> postDtoList = this.postFileRepo.removeByidPostCustomer(idPost);
		Type listType = new TypeToken<List<PostFileDTO>>(){}.getType();
		List<PostFileDTO> postDtoListX = modelMapper.map(postDtoList,listType);
		return postDtoListX;
	}

	
}
