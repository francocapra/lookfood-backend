package com.lookfood.backend.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lookfood.backend.domain.Professional;
import com.lookfood.backend.repositories.ProfessionalRepository;
import com.lookfood.backend.security.SSUserDetails;
import com.lookfood.backend.services.exceptions.AuthorizationException;
import com.lookfood.backend.services.exceptions.DataIntegrityException;
import com.lookfood.backend.services.exceptions.ObjectNotFoundException;

@Service
public class ProfessionalService {
	
	@Autowired
	private ProfessionalRepository repository;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.professional.portrait}")
	private String prefixFile;
	
	@Value("${img.profile.size}")
	private Integer size;	
	
	public Professional find(Integer id) {
		Optional<Professional> obj = repository.findById(id);
		return obj.orElseThrow( () -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Professional.class.getName() )); 
		
	}
	
	@Transactional
	public Professional insert(Professional obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	public Professional update(Professional obj) {
		find(obj.getId());
		return repository.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
		repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma Professional que possui Products");
		}		
	}
	
	public List<Professional> listAll() {
		
		return repository.findAll();
		
	}
	
	public Page<Professional> findPage(Integer page, Integer linesPerPage, Direction sortDirection, String orderBy ) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, sortDirection, orderBy);
		return repository.findAll(pageRequest);
	}
	
	public List<Professional> findTop(Integer partnerId, Integer top){
		PageRequest pageRequest = PageRequest.of(0, top);
		List<Professional> list = repository.findTop(partnerId, pageRequest);
		return list;
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		
		//Verificar se usuario esta logado!
		SSUserDetails user = UserService.authenticated();
		if (user==null) {
			throw new AuthorizationException("Acesso negado");
		};
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		
		String fileName = prefixFile + user.getId() + ".jpg";
		
		return s3Service.uploadFile(fileName, imageService.getInputStream(jpgImage, "jpg") , "image");
		
		
	}
}
