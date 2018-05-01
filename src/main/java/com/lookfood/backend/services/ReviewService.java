package com.lookfood.backend.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.lookfood.backend.domain.ItemProduct;
import com.lookfood.backend.domain.ItemProfessional;
import com.lookfood.backend.domain.Review;
import com.lookfood.backend.domain.enums.TypeStatus;
import com.lookfood.backend.repositories.ItemProductRepository;
import com.lookfood.backend.repositories.ItemProfessionalRepository;
import com.lookfood.backend.repositories.ReviewRepository;
import com.lookfood.backend.services.exceptions.DataIntegrityException;
import com.lookfood.backend.services.exceptions.ObjectNotFoundException;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository repository;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ItemProductRepository itemProductRepository;
	@Autowired
	private ProfessionalService professionalService;
	@Autowired
	private ItemProfessionalRepository itemProfessionalRepository;

	public Review find(Integer id) {

		Optional<Review> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Review.class.getName()));

	}

	public Review insert(Review obj) {

		obj.setId(null);
		obj.setDate(new Date());
		obj.setStatus(TypeStatus.OPEN);
		obj.setPartner(partnerService.find(obj.getPartner().getId()));
		obj = repository.save(obj);

		for (ItemProduct ip : obj.getItensProduct()) {
			ip.setProduct(productService.find(ip.getProduct().getId()));
			ip.setRate(ip.getRate());
			ip.setReview(obj);
		}
		itemProductRepository.saveAll(obj.getItensProduct());

		for (ItemProfessional ip : obj.getItensProfessional()) {
			ip.setProfessional(professionalService.find(ip.getProfessional().getId()));
			ip.setRate(ip.getRate());
			ip.setReview(obj);
		}

		itemProfessionalRepository.saveAll(obj.getItensProfessional());
		return obj;
		
	}

	public Review update(Review obj) {

		find(obj.getId());
		return repository.save(obj);
		
	}

	public void delete(Integer id) {

		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir Review pois existem Itens associados");
		}

	}

	public List<Review> listAll() {
		return repository.findAll();
	}
}
