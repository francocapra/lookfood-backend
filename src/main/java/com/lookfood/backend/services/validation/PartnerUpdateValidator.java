package com.lookfood.backend.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.dto.PartnerDTO;
import com.lookfood.backend.repositories.PartnerRepository;
import com.lookfood.backend.resources.exceptions.FieldMessage;

public class PartnerUpdateValidator implements ConstraintValidator<PartnerUpdate, PartnerDTO> {
	
	@Autowired
	private PartnerRepository repository;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void initialize(PartnerUpdate ann) {
	}

	@Override
	public boolean isValid(PartnerDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		// TODO: inclua os testes aqui, inserindo erros na lista
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		Partner aux = repository.findByEmail(objDto.getEmail());
		if ( aux != null && aux.getId().equals(uriId) ) {
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