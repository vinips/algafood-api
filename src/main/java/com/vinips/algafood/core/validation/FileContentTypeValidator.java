package com.vinips.algafood.core.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile>{

	private String[] allowed;
	
	@Override
	public void initialize(FileContentType constraintAnnotation) {
		this.allowed = constraintAnnotation.allowed();
	}
	
	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		if(file == null) {
			return false;
		}
		
		List<String> types = Arrays.asList(this.allowed);  
		
		boolean match = types.stream().anyMatch(
				extensao -> extensao.equalsIgnoreCase(file.getContentType().toString())
			);
		
		return match;
	}

}
