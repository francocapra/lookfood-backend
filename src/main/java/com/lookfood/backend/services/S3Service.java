package com.lookfood.backend.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.lookfood.backend.services.exceptions.FileException;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Value("${s3.bucket}")
	private String bucketName;

	@Autowired
	private AmazonS3 s3Client;

	// MultipartFile: é o tipo de arquivo que os enpoint do Spring recebem
	public URI uploadFile(MultipartFile multipartFile) {

		try {
			String fileName = multipartFile.getOriginalFilename();
			// InputStream encapsula o metodo de leitura de uma origem(no caso o multipartFile) 
			InputStream inputStream = multipartFile.getInputStream();
			// Informação do tipo de arquivo do "multipartFile"
			String contentType = multipartFile.getContentType();
			return uploadFile(fileName, inputStream, contentType);

		} catch (IOException e) {
			throw new FileException("Erro IO: " + e.getMessage());
		}

	}

	public URI uploadFile(String fileName, InputStream inputStream, String contentType) {

		try {
			ObjectMetadata metadata = new ObjectMetadata();

			metadata.setContentType(contentType);

			LOG.info("Inicializando upload...");

			s3Client.putObject(bucketName, fileName, inputStream, metadata);

			LOG.info("Upload finalizado!");

			return s3Client.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter URl para URI " + e.getMessage());
		}

	}

}
