package com.lookfood.backend.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lookfood.backend.domain.Address;
import com.lookfood.backend.domain.City;
import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.domain.Region;
import com.lookfood.backend.domain.enums.Profile;
import com.lookfood.backend.dto.PartnerDTO;
import com.lookfood.backend.dto.PartnerNewDTO;
import com.lookfood.backend.repositories.AddressRepository;
import com.lookfood.backend.repositories.CityRepository;
import com.lookfood.backend.repositories.PartnerRepository;
import com.lookfood.backend.repositories.RegionRepository;
import com.lookfood.backend.security.SSUserDetails;
import com.lookfood.backend.services.exceptions.AuthorizationException;
import com.lookfood.backend.services.exceptions.DataIntegrityException;
import com.lookfood.backend.services.exceptions.ObjectNotFoundException;

@Service
public class PartnerService {

	@Autowired
	private PartnerRepository repository;
	@Autowired
	private RegionRepository regionRepository;	
	@Autowired
	private CityRepository cityRepository;	
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private S3Service s3Service;
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.partner.profile}")
	private String prefixFile;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	public Partner find(Integer id) {
		
		SSUserDetails user = UserService.authenticated();
		if (user==null || !user.hasRole(Profile.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		};
		
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

	public List<Partner> findAll() {
		return repository.findAll();
	}
	
	public Partner findByEmail(String email) {
		SSUserDetails user = UserService.authenticated();
		if (user == null || !user.hasRole(Profile.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
	
		Partner obj = repository.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Partner.class.getName());
		}
		return obj;
	}
	
	public Page<Partner> findPage(Integer page, Integer linesPerPage, String orderBy, String sortDirection) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(sortDirection), orderBy);

		return repository.findAll(pageRequest);

	}

	public Partner fromDTO(PartnerDTO objDTO) {
		return new Partner(
				objDTO.getId(), 
				objDTO.getName(), 
				objDTO.getEmail(), 
				null, 
				objDTO.getWebsite(),
				null);
	}

	public Partner fromDTO(PartnerNewDTO objNewDTO) {
		
		Partner partner = new Partner(
							null, 
							objNewDTO.getName(), 
							objNewDTO.getEmail(), 
							objNewDTO.getCnpj(), 
							objNewDTO.getWebsite(),						
							pe.encode(objNewDTO.getPassword())
							);
		
		City city = verifyCityNotInformed(objNewDTO.getCityId());	
		
		Address address = new Address(
							null, 
							objNewDTO.getStreet(), 
							objNewDTO.getNumber(), 
							objNewDTO.getComplement(),
							objNewDTO.getDistrict(), 
							objNewDTO.getPostcode(), 
							partner, 
							city
							);
		partner.getAddresses().add(address);
		partner.getPhones().add(objNewDTO.getPhone1());
		if (objNewDTO.getPhone2() != null) {
			partner.getPhones().add(objNewDTO.getPhone2());
		}
		if (objNewDTO.getPhone3() != null) {
			partner.getPhones().add(objNewDTO.getPhone3());
		}

		return partner;
	}
	
	private City verifyCityNotInformed(Integer Id) {
		
		if ( Id == null ) {
			Region region1 = new Region(null, "Não informado");
			City city1 = new City(null, "Não informado", region1);
			region1.getCities().addAll(Arrays.asList(city1));
			regionRepository.save(region1);
			return cityRepository.save(city1);	
		}
		else {
			return new City( Id, null, null);
		}
		
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
