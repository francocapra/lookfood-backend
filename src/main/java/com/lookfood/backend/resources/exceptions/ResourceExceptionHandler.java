package com.lookfood.backend.resources.exceptions;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), 
				"Lookfood: Object not found: " + e.getMessage(), 
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);

	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), 
				"Lookfood: Data integrity error: " + e.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> dataValidation(MethodArgumentNotValidException e, HttpServletRequest request) {

		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(),
				"Lookfood: Validation Failed: " + e.getMessage(), 
				System.currentTimeMillis());
		
		// Percorrer Lista de Erros da "MethodArgumentNotValidException", e para cada
		// erro declarar para o tipo "FieldError"
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> handleAuthorizationException(AuthorizationException exception,
			HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), 
				"Lookfood: Authorization Failed: " + exception.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);

	}

	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> handleFileException(FileException exception, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), 
				"Lookfood: File error: " + exception.getMessage(),
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}

	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> handleAmazonServiceException(AmazonServiceException exception, HttpServletRequest request) {
		
		HttpStatus httpStatus = HttpStatus.valueOf(exception.getErrorCode());
		
		StandardError err = new StandardError(httpStatus.value(), 
				"Lookfood: Amazon service error: " + exception.getMessage(),
				System.currentTimeMillis());
		
		return ResponseEntity.status(httpStatus).body(err);
		
	}

	@ExceptionHandler(AmazonClientException.class)
	public void handleAmazonClientException(AmazonClientException exception, HttpServletResponse response) throws IOException {
			
		response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
		
	}

	@ExceptionHandler(AmazonS3Exception.class)
	public void handleAmazonS3Exception(AmazonS3Exception exception, HttpServletResponse response) throws IOException {
		
		response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
		
	}
}
