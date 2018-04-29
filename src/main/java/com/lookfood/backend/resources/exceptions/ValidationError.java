package com.lookfood.backend.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> error = new ArrayList<>();
	
	public ValidationError(Integer status, String msg, Long timestamp) {
		super(status, msg, timestamp);

	}

	public List<FieldMessage> getMessageErrors() {
		return error;
	}

	public void setList(List<FieldMessage> error) {
		this.error = error;
	}
	
	public void addError(String fieldName, String message) {
		error.add(new FieldMessage(fieldName,message));
	}

}
