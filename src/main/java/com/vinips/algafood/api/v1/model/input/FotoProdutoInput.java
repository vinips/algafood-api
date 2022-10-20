package com.vinips.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.vinips.algafood.core.validation.FileContentType;
import com.vinips.algafood.core.validation.FileSize;

import io.swagger.annotations.ApiModelProperty;

public class FotoProdutoInput {
	
	@ApiModelProperty(value = "Arquivo da foto do produto (apenas JPG e PNG)", required = true)
	@NotNull
	@FileSize(max = "2MB")
	@FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	private MultipartFile arquivo;
	
	@ApiModelProperty(example = "temaki", value = "Descrição da foto do produto", required = true)
	@NotBlank
	private String descricao;

	public MultipartFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(MultipartFile arquivo) {
		this.arquivo = arquivo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
