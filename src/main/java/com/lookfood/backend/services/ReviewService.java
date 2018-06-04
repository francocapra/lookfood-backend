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
import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.domain.Product;
import com.lookfood.backend.domain.Review;
import com.lookfood.backend.domain.enums.TypeStatus;
import com.lookfood.backend.dto.ReviewDTO;
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
	public Review insert(ReviewDTO objDTO) {
		
		Review newObj = new Review();
		newObj.setId(null);
		newObj.setDate(new Date());
		newObj.setStatus(TypeStatus.OPEN);
		newObj.setPartner(partnerService.find(objDTO.getPartner().getId()));
		newObj = repository.save(newObj);
		
		ItemProduct ip = new ItemProduct(); 
		
		for (Product pd : objDTO.getProducts()) {
			ip.setProduct(productService.find(pd.getId()));			
			ip.setReview(newObj);
		}

		itemProductRepository.saveAll(newObj.getItemsProduct());

		emailService.sendReviewConfirmationHtmlEmail(newObj);
		return newObj;
		
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
