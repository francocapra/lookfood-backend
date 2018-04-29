package com.lookfood.backend.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lookfood.backend.services.exceptions.DataIntegrityException;
import com.lookfood.backend.services.exceptions.ObjectNotFoundException;

@ControllerAdvice 	
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		
		StandardError err = new StandardError(	
				HttpStatus.NOT_FOUND.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
				
		return ResponseEntity.status(
				HttpStatus.NOT_FOUND).body(err);
		
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		
		StandardError err = new StandardError(
				HttpStatus.BAD_REQUEST.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
				
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> dataValidation(MethodArgumentNotValidException e, HttpServletRequest request){
		
		ValidationError err = new ValidationError(
				HttpStatus.BAD_REQUEST.value(), //"Codigo de Erro"
				//e.getMessage(), 				//"Mensagem"
				"Validation Failed",
				System.currentTimeMillis()		//"TimeStamp"				
				);
//		Percorrer Lista de Erros da "MethodArgumentNotValidException", e para cada erro declarar para o tipo "FieldError"
		for ( FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
		
	}
}
