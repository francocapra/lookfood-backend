package com.lookfood.backend.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.lookfood.backend.services.exceptions.AuthorizationException;
import com.lookfood.backend.services.exceptions.DataIntegrityException;
import com.lookfood.backend.services.exceptions.FileException;
import com.lookfood.backend.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Objeto não encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);

	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro na integridade de dados", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> dataValidation(MethodArgumentNotValidException e, HttpServletRequest request) {

		ValidationError err = new ValidationError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(),
				"Erro na validação", e.getMessage(), request.getRequestURI());
		// Percorrer Lista de Erros da "MethodArgumentNotValidException", e para cada
		// erro declarar para o tipo "FieldError"
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);

	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> handleAuthorizationException(AuthorizationException e,
			HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Acesso negado",
				e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);

	}

	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> handleFileException(FileException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro de arquivo", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}

	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> handleAmazonServiceException(AmazonServiceException e,
			HttpServletRequest request) {

		HttpStatus httpStatus = HttpStatus.valueOf(e.getErrorCode());

		StandardError err = new StandardError(System.currentTimeMillis(), httpStatus.value(), "Erro Amazon Service",
				e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(httpStatus).body(err);

	}

	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> handleAmazonClientException(AmazonClientException e,
			HttpServletRequest request) {
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro Amazon Client", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}

	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> handleAmazonS3Exception(AmazonS3Exception e, HttpServletRequest request) {
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro Amazon S3", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}
}
