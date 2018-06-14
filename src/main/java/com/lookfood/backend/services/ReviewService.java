package com.lookfood.backend.services;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import com.lookfood.backend.dto.ItemReviewDTO;
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

		SSUserDetails user = UserService.authenticated();
		if (user==null) {
			throw new AuthorizationException("Acesso negado");
		};	
		
		Optional<Review> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Review.class.getName()));

	}

	@Transactional
	public Review insert(ReviewDTO reviewDTO) {
		
		SSUserDetails user = UserService.authenticated();
		if (user==null) {
			throw new AuthorizationException("Acesso negado");
		};		
		
		Review rw = new Review();
		rw.setId(null);
		rw.setDate(new Date());
		rw.setStatus(TypeStatus.OPEN);
		rw.setPartner(partnerService.find(user.getId()));		
		rw = repository.save(rw);
		
		for (ItemReviewDTO itemReviewDTO : reviewDTO.getItemsReviewDTO() ) {
			Product pd = productService.find(itemReviewDTO.getId());
			ItemProduct ip = new ItemProduct(rw,pd,null);
			rw.getItemsProduct().add(ip);
			pd.getItensProduct().add(ip);
		};
						
		itemProductRepository.saveAll(rw.getItemsProduct());
		
//		emailService.sendReviewConfirmationHtmlEmail(rw);
		return rw;
		
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

	public ReviewDTO findByCode(String reviewCode) {

		Review review = repository.findByReviewCode(reviewCode);		
		ReviewDTO reviewDTO = new ReviewDTO() ;
		
		reviewDTO.setId(review.getId());
		reviewDTO.setStatus(review.getStatus().getDescription());
		reviewDTO.setReviewCode(review.getReviewCode());
		
		for( ItemProduct ip : review.getItemsProduct()) {			
			ItemReviewDTO itemReviewDTO = new ItemReviewDTO(ip);
			reviewDTO.getItemsReviewDTO().addAll(Arrays.asList(itemReviewDTO));
		}
		
		return reviewDTO;
	}

	public Review updateFromDTO(@Valid ReviewDTO reviewDTO) {
		
		Review review = find(reviewDTO.getId());
		updateDataFromDTO(review, reviewDTO);		
		return review;
	}

	private void updateDataFromDTO(Review review, ReviewDTO fromDTO) {
		
		review.setStatus(TypeStatus.CLOSE);
        						
		for (ItemReviewDTO itemReviewDTO : fromDTO.getItemsReviewDTO() ) {	
			Iterator<ItemProduct> ItemProductAsIterator = review.getItemsProduct().iterator();
			while (ItemProductAsIterator.hasNext()){
				ItemProduct ip = ItemProductAsIterator.next();
				if ( ip.getProduct().getId() == itemReviewDTO.getId()) {
					ip.setRate(itemReviewDTO.getRate());
				}               
			}
		}
			
	}

	
}
