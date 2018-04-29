package com.lookfood.backend.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lookfood.backend.domain.Product;
import com.lookfood.backend.dto.ProductDTO;
import com.lookfood.backend.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResources {

	// Declaração de dependencia,(Mecanismo de Injeção de dependencia, ou inversão
	// de controle)
	// @Autowired === Intanciar automaticamente
	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductDTO> find(@PathVariable Integer id) {
		
		ProductDTO obj = productService.find(id);

		return ResponseEntity.ok().body(obj);

	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ProductDTO objDTO) {
		
		Product obj = productService.fromDTO(objDTO);
		
		obj = this.productService.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody ProductDTO objDTO) {
		
		Product obj = productService.fromDTO(objDTO);
		
		obj.setId(id);
		
		obj = productService.update(obj);
		
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		productService.delete(id);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ProductDTO>> listAll() {

		// Recebo a LISTA de Produtos, e para cada elemnto desta lista eu vou instanciar
		// um DTO
		List<Product> list = productService.listAll();

		// Percorrer objeto LIST, usando STREAM (Recurso do Java 8),
		// Operação, MAP(vai efetuar uma operação para cada elemento)
		// Apelido, "obj" cada elemento da lista eu posso dar um apelido
		// Função, "->" ARROW executar uma função em cada elemento da lista
		// Criar, "new" Criar um novo objeto DTO
		// Collect, voltar o STREAM de objetos para o tipo LIST, usando COLLECTOR
		List<ProductDTO> listDTO = list.stream().map(obj -> new ProductDTO(obj)).collect(Collectors.toList());

		return ResponseEntity.ok().body(listDTO);
	}

	@RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ProductDTO>> listPage(
			@RequestParam(value="page"			, defaultValue="0" ) Integer page, 
			@RequestParam(value="linesPerPage"	, defaultValue="24" ) Integer linesPerPage, 
			@RequestParam(value="orderBy"		, defaultValue="description" ) String orderBy, 
			@RequestParam(value="sortDirection"	, defaultValue="ASC" ) String sortDirection) {
		
		Page<Product> list = productService.listPage(page, linesPerPage, orderBy, sortDirection);
//		PAGE(é java complice, JAVA 8 ), não precisa de STREAM, nem de Collect
		Page<ProductDTO> listDTO = list.map(obj -> new ProductDTO(obj));
		
		return ResponseEntity.ok().body(listDTO);
	}
}
