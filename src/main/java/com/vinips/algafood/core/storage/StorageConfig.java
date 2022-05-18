package com.vinips.algafood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.vinips.algafood.core.storage.StorageProperties.TipoStorage;
import com.vinips.algafood.domain.service.FotoStorageService;
import com.vinips.algafood.infrastructure.service.storage.LocalFotoStorageService;
import com.vinips.algafood.infrastructure.service.storage.S3FotoStorageService;

//Classe de configuração do S3, com as chaves e informações para acessar nossa conta na AmazonS3
@Configuration
public class StorageConfig {

	@Autowired
	private StorageProperties storageProperties;

	// Esse @ConditionalOnProperty funciona para caso estivermos usando apenas o tipo LOCAL, ele não cria o Bean
	// Da amazonS3, fazendo assim com que não precisemos configurar algumas coisas no application.properties
	// Como o id-chave-acesso ou chave-secreta.
	@Bean
	@ConditionalOnProperty(name = "algafood.storage.tipo", havingValue = "s3")
	public AmazonS3 amazonS3() {
		var credentials = new BasicAWSCredentials(
				storageProperties.getS3().getIdChaveAcesso(),
				storageProperties.getS3().getChaveAcessoSecreta());
		
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegiao())
				.build();
	}
	
	@Bean
	public FotoStorageService fotoStorageService() {
		if(TipoStorage.S3.equals(storageProperties.getTipo())) {
			return new S3FotoStorageService();
		} else {
			return new LocalFotoStorageService();
		}
	}

}
