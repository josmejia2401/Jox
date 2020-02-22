package com.bay.common.dto.file;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;


public class FileDTO<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	@NotEmpty(message = "{validation.file.data.notempty}")
	private T data;
	@NotEmpty(message = "{validation.file.files.notempty}")
	private MultipartFile[] files;
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public MultipartFile[] getFiles() {
		return files;
	}
	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
	

}