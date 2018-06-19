package com.lookfood.backend.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lookfood.backend.domain.ItemProduct;
import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.domain.Product;
import com.lookfood.backend.domain.Professional;
import com.lookfood.backend.domain.enums.Profile;
import com.lookfood.backend.dto.ProductDTO;
import com.lookfood.backend.dto.ProductTopDTO;
import com.lookfood.backend.repositories.ProductRepository;
import com.lookfood.backend.repositories.ProfessionalRepository;
import com.lookfood.backend.security.SSUserDetails;
import com.lookfood.backend.services.exceptions.AuthorizationException;
import com.lookfood.backend.services.exceptions.DataIntegrityException;
import com.lookfood.backend.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private ProfessionalRepository professionalRepository;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.product.portrait}")
	private String prefixFile;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	@Autowired
	private PartnerService partnerService;
	
	public Product find(Integer id) {		
		Optional<Product> obj = repository.findById(id);			
		return obj.orElseThrow( () -> new ObjectNotFoundException
				("Objeto não encontrado! Id: " + id + ", Tipo: " + Product.class.getName() )); 		
	
	}
	
	public Page<Product> search(
			String description, 
			List<Integer> ids,
			Integer page, 
			Integer linesPerPage, 
			String orderBy, 
			String sortDirection){
		
		PageRequest pageRequest = PageRequest.of(
				page, 
				linesPerPage, 
				Direction.valueOf(sortDirection) , 
				orderBy);
		
		List<Professional> professionals = professionalRepository.findAllById(ids);
		
		return repository.findDistinctByDescriptionContainingAndProfessionalsIn(description, professionals, pageRequest);
	}
	
	@Transactional
	public Product insert(Product obj) {
		
		SSUserDetails user = UserService.authenticated();
		if (user==null || !user.hasRole(Profile.ADMIN) ) {
			throw new AuthorizationException("User haven't authorization or haven't profile Administrator.");
		};
		
		obj.setId(null);		
		obj.setPartner(partnerService.find(user.getId()));
		if ( obj.getCreatedDate() == null ) {
			obj.setCreatedDate(new Date());
		}
		return repository.save(obj);		
	}

	public Product update(Product obj) {
		Product newObj = find(obj.getId());
		updateData( newObj, obj);
		return repository.save(obj);
	}
	
	private void updateData(Product newObj, Product obj) {
		newObj.setDescription(obj.getDescription());
		newObj.setModifiedDate(new Date());
		newObj.setPrice(obj.getPrice());
		newObj.setCurrency(obj.getCurrency());		
		newObj.setIdExternal(obj.getIdExternal());		
	}

	public void delete(Integer id) {
		this.find(id);		
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException
			("Não é possivel excluir um Product que possui um Professional ");
		}
	}
	
	public List<Product> findAll() {	
		SSUserDetails user = UserService.authenticated();
		if (user==null) {
			throw new AuthorizationException("Access denied");
		};
			
		return repository.findAllByPartnerId(user.getId());		
	}
	
	public Page<Product> findPage(
			Integer page, 
			Integer linesPerPage, 
			String orderBy, 
			String sortDirection){
		
		PageRequest pageRequest = PageRequest.of(
				page, 
				linesPerPage, 
				Direction.valueOf(sortDirection) , 
				orderBy);
		
		return repository.findAll(pageRequest);
		
	}
	
	public Product fromDTO(ProductDTO objDTO) {
		return new Product(
				objDTO.getId(), 
				objDTO.getDescription(), 
				objDTO.getPrice(),
				objDTO.getCurrency(),				
				objDTO.getIdExternal(),
				objDTO.getFromCountry(),
				objDTO.getCategory()
				);
	}
	
	public List<ProductTopDTO> findTop(Integer top){
		
		PageRequest pageRequest = PageRequest.of(0, top);
		
		List<ProductTopDTO> list = repository.findTop(pageRequest);
		
		return list;
	}
	
	public List<ProductTopDTO> findTopUpToFifty(){
		PageRequest pageRequest = PageRequest.of(0, 20);
		
		return repository.findTopUpToFifty(pageRequest);
	}
	
	public List<ProductTopDTO> findByCountry(String countryIsoCode){
		PageRequest pageRequest = PageRequest.of(0, 20);
		
		return repository.findByCountry(pageRequest, countryIsoCode);
	}
	
	public List<ProductTopDTO> findByCategory(Integer categoryCode){
		PageRequest pageRequest = PageRequest.of(0, 20);
		
		return repository.findByCategory(pageRequest, categoryCode);
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile, Integer productId) {
		
		//Verificar se usuario esta logado!
		SSUserDetails user = UserService.authenticated();
		if (user==null) {
			throw new AuthorizationException("Access denied");
		};
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
				
		String fileName = prefixFile + productId + ".jpg";
		
		return s3Service.uploadFile(fileName, imageService.getInputStream(jpgImage, "jpg") , "image");
				
	}

	public Product updateFromDTO(Integer id, @Valid ProductDTO objDTO) {
		
		Product product = find(id);
		
		updateData(product, fromDTO(objDTO) );
		
		return product;
	}

}
