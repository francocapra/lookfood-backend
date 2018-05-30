package com.lookfood.backend.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lookfood.backend.domain.ItemProduct;
import com.lookfood.backend.domain.Product;
import com.lookfood.backend.dto.ProductDTO;
import com.lookfood.backend.dto.ProductTopDTO;
import com.lookfood.backend.resources.utils.URL;
import com.lookfood.backend.services.ProductService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/products")
public class ProductResources {

	@Autowired
	private ProductService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> find(@PathVariable Integer id) {
		
		Product obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(
			@Valid @RequestBody ProductDTO objDTO) {
		
		Product obj = service.fromDTO(objDTO);		
		
		obj = this.service.insert(obj);		
	
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();

	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(
			@PathVariable Integer id, 
			@Valid @RequestBody ProductDTO objDTO) {
				
		Product obj = service.updateFromDTO(id, objDTO);
		
		obj = service.update(obj);		
		
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(
			@PathVariable Integer id) {
		
		service.delete(id);
		
		return ResponseEntity.noContent().build();
		
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ProductDTO>> findAll() {
		
		List<Product> list = service.findAll();
		
		List<ProductDTO> listDTO = list.stream().map(obj -> new ProductDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value="/page", method = RequestMethod.GET )
	public ResponseEntity<Page<ProductDTO>> findPage(
			@RequestParam(value="description"	, defaultValue="")	String description,
			@RequestParam(value="professionals" , defaultValue="") 	String professionals,
			@RequestParam(value="page"			, defaultValue="0" ) Integer page, 
			@RequestParam(value="linesPerPage"	, defaultValue="24" ) Integer linesPerPage, 
			@RequestParam(value="orderBy"		, defaultValue="description" ) String orderBy, 
			@RequestParam(value="sortDirection"	, defaultValue="ASC" ) String sortDirection) {

		String descriptionDecoded = URL.decodeParam(description);
		
		List<Integer> ids = URL.decodeIntList(professionals);
		
		Page<Product> list = service.search(descriptionDecoded, ids, page, linesPerPage, orderBy, sortDirection);		
		
		Page<ProductDTO> listDTO = list.map(obj -> new ProductDTO(obj));
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@ApiOperation(value="List Top Products") 
	@RequestMapping(value="/top", method=RequestMethod.GET)
	public ResponseEntity<List<ProductTopDTO>> findTop(
			@RequestParam(value="limit", defaultValue="5" ) Integer nro	) {
		
		List<ItemProduct> list = service.findTop(nro);
		
		List<ProductTopDTO> listTopDTO = list.stream().map(obj -> new ProductTopDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listTopDTO);
	}
	
	@ApiOperation(value="Upload picture to Product") 
	@RequestMapping(value = "/picture", method = RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(
			@RequestParam(name = "file") MultipartFile file,
			@RequestParam(name = "id") Integer id ) {
		
		URI uri = service.uploadProfilePicture(file, id);
		
		return ResponseEntity.created(uri).build();

	}
}
