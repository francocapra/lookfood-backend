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

import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.dto.PartnerDTO;
import com.lookfood.backend.dto.PartnerNewDTO;
import com.lookfood.backend.services.PartnerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/partners")
public class PartnerResources {

	@Autowired
	private PartnerService service;
	
	@ApiOperation(value="Busca por id") 
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Partner> find(@PathVariable Integer id) {

		Partner obj = service.find(id);
		return ResponseEntity.ok().body(obj);

	}
	
	@ApiOperation(value="Busca por email") 
	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public ResponseEntity<Partner> find(@RequestParam(value="value") String email) {
		
		Partner obj = service.findByEmail(email);
		return ResponseEntity.ok().body(obj);
		
	}
	
	@ApiOperation(value="Inseri novo Parceiro") 
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody PartnerNewDTO objDTO) {

		Partner obj = service.fromDTO(objDTO);
		obj = this.service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();

	}
	
	@ApiOperation(value="Atualiza Parceiro") 
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody PartnerDTO objDTO) {

		Partner obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
		
	}
	
	@ApiOperation(value="Deleta Partner{only has perfil:ADMIN}") 
	@ApiResponses(value = { 
			 @ApiResponse(code = 400, message = "Não é possível excluir uma Partner que possui Reviews relacionados"), 
			 @ApiResponse(code = 404, message = "Código inexistente") }) 
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value="Busca todos os parceiro {only has perfil:ADMIN}") 
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PartnerDTO>> findAll() {

		// Recebo a LISTA de Produtos, e para cada elemnto desta lista eu vou instanciar
		// um DTO
		List<Partner> list = service.findAll();

		// Percorrer objeto LIST, usando STREAM (Recurso do Java 8),
		// Operação, MAP(vai efetuar uma operação para cada elemento)
		// Apelido, "obj" cada elemento da lista eu posso dar um apelido
		// Função, "->" ARROW executar uma função em cada elemento da lista
		// Criar, "new" Criar um novo objeto DTO
		// Collect, voltar o STREAM de objetos para o tipo LIST, usando COLLECTOR
		List<PartnerDTO> listDTO = list.stream().map(obj -> new PartnerDTO(obj)).collect(Collectors.toList());

		return ResponseEntity.ok().body(listDTO);
	}
	
	@ApiOperation(value="Lista parceiro com paginação {only has perfil:ADMIN}") 
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<PartnerDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection) {

		Page<Partner> list = service.findPage(page, linesPerPage, orderBy, sortDirection);

		// PAGE(é java complice, JAVA 8 ), não precisa de STREAM, nem de Collect
		Page<PartnerDTO> listDTO = list.map(obj -> new PartnerDTO(obj));
		return ResponseEntity.ok().body(listDTO);

	}
	
	@ApiOperation(value="Upload de foto do perfil") 
	@RequestMapping(value = "/picture", method = RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file) {
		
		URI uri = service.uploadProfilePicture(file);
		return ResponseEntity.created(uri).build();

	}

}