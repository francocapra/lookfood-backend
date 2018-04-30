package com.lookfood.backend.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.dto.PartnerNewDTO;
import com.lookfood.backend.repositories.PartnerRepository;
import com.lookfood.backend.resources.exceptions.FieldMessage;

public class PartnerInsertValidator implements ConstraintValidator<PartnerInsert, PartnerNewDTO> {

	@Autowired
	private PartnerRepository repository;
	
	@Override
	public void initialize(PartnerInsert ann) {
	}

	@Override
	public boolean isValid(PartnerNewDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		// inclua os testes aqui, inserindo erros na lista
		Partner aux = repository.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já cadastrado"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}