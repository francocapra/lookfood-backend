package com.lookfood.backend.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lookfood.backend.domain.City;
import com.lookfood.backend.domain.Region;
import com.lookfood.backend.dto.CityDTO;
import com.lookfood.backend.dto.RegionDTO;
import com.lookfood.backend.services.CityService;
import com.lookfood.backend.services.RegionService;

@RestController
@RequestMapping(value="/regions")
public class RegionResource {
	
	@Autowired
	private RegionService service;
	
	@Autowired
	private CityService cityService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<RegionDTO>> findAll() {
		List<Region> list = service.findAll();
		List<RegionDTO> listDTO = list.stream().map(x -> new RegionDTO(x) ).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value="/{regionId}/cities", method=RequestMethod.GET)
	public ResponseEntity<List<CityDTO>> findCities(@PathVariable Integer regionId) {
		List<City> list = cityService.findByRegion(regionId);
		List<CityDTO> listDto = list.stream().map(obj -> new CityDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
}
