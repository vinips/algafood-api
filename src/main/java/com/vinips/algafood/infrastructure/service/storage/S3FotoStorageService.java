package com.vinips.algafood.infrastructure.service.storage;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.vinips.algafood.core.storage.StorageProperties;
import com.vinips.algafood.domain.service.FotoStorageService;
import com.vinips.algafood.infrastructure.exception.StorageException;

@Service
public class S3FotoStorageService implements FotoStorageService{

	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private StorageProperties storageProperties;
	
	//Aula 14.23
	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
			
			var metadata = new ObjectMetadata();
			metadata.setContentType(novaFoto.getContentType());
			
			//Prepara como queremos a requisição.
			//withCannedAcl serve para mudarmos a permissão desse arquivo. 
			//Deixando ele ser acessado publicamente para Leitura na S3.
			var putObjectRequest = new PutObjectRequest(
					storageProperties.getS3().getBucket(),
					caminhoArquivo,
					novaFoto.getInputStream(),
					metadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
			
			//Caso de erro de "The bucket does not allow ACLs", basta seguir o passo a passo
			// Entrar no Bucket > persmissões > prorpiedade de objeto > editar
			
			//Faz a chamada para enviar a foto.
			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar arquivo para Amazon S3", e);
		}
		
		
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
			
			var deleteObjectRequest =  new DeleteObjectRequest(
					storageProperties.getS3().getBucket(),
					caminhoArquivo);
			
			amazonS3.deleteObject(deleteObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir o arquivo da Amazon S3", e);
		}
		
	}

	@Override
	public InputStream recuperar(String nomeArquivo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
	}
	

}
