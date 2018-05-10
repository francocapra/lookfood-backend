package com.lookfood.backend.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lookfood.backend.domain.ItemProduct;
import com.lookfood.backend.domain.ItemProfessional;
import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.domain.Review;
import com.lookfood.backend.domain.enums.TypeStatus;
import com.lookfood.backend.repositories.ItemProductRepository;
import com.lookfood.backend.repositories.ItemProfessionalRepository;
import com.lookfood.backend.repositories.ReviewRepository;
import com.lookfood.backend.security.SSUserDetails;
import com.lookfood.backend.services.exceptions.AuthorizationException;
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
	@Autowired
	private EmailService emailService;

	public Review find(Integer id) {

		Optional<Review> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Review.class.getName()));

	}

	@Transactional
	public Review insert(Review obj) {

		obj.setId(null);
		obj.setDate(new Date());
		obj.setStatus(TypeStatus.OPEN);
		obj.setPartner(partnerService.find(obj.getPartner().getId()));
		obj = repository.save(obj);

		for (ItemProduct ip : obj.getItemsProduct()) {
			ip.setProduct(productService.find(ip.getProduct().getId()));
			ip.setRate(ip.getRate());
			ip.setReview(obj);
		}
		itemProductRepository.saveAll(obj.getItemsProduct());

		for (ItemProfessional ip : obj.getItemsProfessional()) {
			ip.setProfessional(professionalService.find(ip.getProfessional().getId()));
			ip.setRate(ip.getRate());
			ip.setReview(obj);
		}

		itemProfessionalRepository.saveAll(obj.getItemsProfessional());
		emailService.sendReviewConfirmationHtmlEmail(obj);
		return obj;
		
	}

	public Review update(Review obj) {

		find(obj.getId());
		return repository.save(obj);
		
	}

	public List<Review> listAll() {
		return repository.findAll();
	}
	
	public Page<Review> findPage(Integer page, Integer linesPerPage, Direction sortDirection, String orderBy ) {
		
		SSUserDetails user = UserService.authenticated();
		
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, sortDirection, orderBy);
		
		Partner partner = partnerService.find(user.getId());
		
		return repository.findByPartner(partner, pageRequest);
	}
	
}
