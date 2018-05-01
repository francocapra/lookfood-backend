package com.lookfood.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lookfood.backend.domain.Address;
import com.lookfood.backend.domain.City;
import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.dto.PartnerDTO;
import com.lookfood.backend.dto.PartnerNewDTO;
import com.lookfood.backend.repositories.AddressRepository;
import com.lookfood.backend.repositories.PartnerRepository;
import com.lookfood.backend.services.exceptions.DataIntegrityException;
import com.lookfood.backend.services.exceptions.ObjectNotFoundException;

@Service
public class PartnerService {

	@Autowired
	private PartnerRepository repository;

	@Autowired
	private AddressRepository addressRepository;

	public Partner find(Integer id) {
		Optional<Partner> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Partner.class.getName()));
	}

	@Transactional
	public Partner insert(Partner obj) {
		obj.setId(null);
		obj = repository.save(obj);
		addressRepository.saveAll(obj.getAddresses());
		return obj;
	}

	public Partner update(Partner obj) {
		Partner newObj = find(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	private void updateData(Partner newObj, Partner obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
		newObj.setWebsite(obj.getWebsite());
	}

	public void delete(Integer id) {

		find(id);

		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um Partner porque há "
					+ String.valueOf(find(id).getReviews().size()) + " Review(s) relacionado(s)");
		}
	}

	public List<Partner> listAll() {
		return repository.findAll();
	}

	public Page<Partner> listPage(Integer page, Integer linesPerPage, String orderBy, String sortDirection) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(sortDirection), orderBy);

		return repository.findAll(pageRequest);

	}

	public Partner fromDTO(PartnerDTO objDTO) {
		return new Partner(objDTO.getId(), objDTO.getName(), objDTO.getEmail(), null, objDTO.getWebsite());
	}

	public Partner fromDTO(PartnerNewDTO objDTO) {
		Partner ptr = new Partner(null, objDTO.getName(), objDTO.getEmail(), objDTO.getCnpj(), objDTO.getWebsite());
		City cty = new City(objDTO.getCityId(), null, null);
		Address adr = new Address(null, objDTO.getStreet(), objDTO.getNumber(), objDTO.getComplement(),
				objDTO.getDistrict(), objDTO.getPostcode(), ptr, cty);
		ptr.getAddresses().add(adr);
		ptr.getPhones().add(objDTO.getPhone1());
		if (objDTO.getPhone2() != null) {
			ptr.getPhones().add(objDTO.getPhone2());
		}
		if (objDTO.getPhone3() != null) {
			ptr.getPhones().add(objDTO.getPhone3());
		}

		return ptr;
	}

}
