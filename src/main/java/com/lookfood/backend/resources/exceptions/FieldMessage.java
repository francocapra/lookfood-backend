package com.lookfood.backend.resources.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private String fieldName;
	
	private String messageError;
	
	public FieldMessage() {
		
	}

	public FieldMessage(String fieldName, String messageError) {
		super();
		this.fieldName = fieldName;
		this.messageError = messageError;
	}

	public String getFieldname() {
		return fieldName;
	}

	public void setFieldname(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessageError() {
		return messageError;
	}

	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}
	
	
}
