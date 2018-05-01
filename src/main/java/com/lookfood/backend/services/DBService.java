package com.lookfood.backend.services;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lookfood.backend.domain.Address;
import com.lookfood.backend.domain.City;
import com.lookfood.backend.domain.ItemProduct;
import com.lookfood.backend.domain.ItemProfessional;
import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.domain.Product;
import com.lookfood.backend.domain.Professional;
import com.lookfood.backend.domain.Region;
import com.lookfood.backend.domain.Review;
import com.lookfood.backend.domain.enums.TypePosition;
import com.lookfood.backend.domain.enums.TypeStatus;
import com.lookfood.backend.repositories.AddressRepository;
import com.lookfood.backend.repositories.CityRepository;
import com.lookfood.backend.repositories.ItemProductRepository;
import com.lookfood.backend.repositories.ItemProfessionalRepository;
import com.lookfood.backend.repositories.PartnerRepository;
import com.lookfood.backend.repositories.ProductRepository;
import com.lookfood.backend.repositories.ProfessionalRepository;
import com.lookfood.backend.repositories.RegionRepository;
import com.lookfood.backend.repositories.ReviewRepository;

@Service
public class DBService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private PartnerRepository partnerRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProfessionalRepository professionalRepository;
	@Autowired
	private ItemProductRepository itemProductRepository;
	@Autowired
	private ItemProfessionalRepository itemProfessionalRepository;

	public void instatiateTestDataBase() throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		// Persist: Region/City/Address/Partner
		Region reg1 = new Region(null, "Rio Grande do Sul");

		City c1 = new City(null, "Porto Alegre", reg1);
		City c2 = new City(null, "Canoas", reg1);

		reg1.getCities().addAll(Arrays.asList(c1, c2));

		regionRepository.saveAll(Arrays.asList(reg1));
		cityRepository.saveAll(Arrays.asList(c1, c2));

		Partner partner1 = new Partner(
								null, 
								"Maria Silva", 
								"email@dominio.com.br", 
								"123456789", 
								"www.restaurante.com",
								pe.encode("123"));

		partner1.getPhones().addAll(Arrays.asList("5127363323", "93838393"));

		Address e1 = new Address(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", partner1, c1);
		Address e2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", partner1, c2);

		partner1.getAddresses().addAll(Arrays.asList(e1, e2));

		partnerRepository.saveAll(Arrays.asList(partner1));
		addressRepository.saveAll(Arrays.asList(e1, e2));

		// Persist: Product/ Professional
		Product p1 = new Product(null, "Prato1", sdf.parse("30/09/2017 10:32"));
		Product p2 = new Product(null, "Prato2", sdf.parse("30/09/2017 10:32"));
		Product p3 = new Product(null, "Prato3", sdf.parse("30/09/2017 10:32"));
		Product p4 = new Product(null, "Prato4", sdf.parse("30/09/2017 10:32"));
		Product p5 = new Product(null, "Prato5", sdf.parse("30/09/2017 10:32"));
		Product p6 = new Product(null, "Prato6", sdf.parse("30/09/2017 10:32"));
		Product p7 = new Product(null, "Prato7", sdf.parse("30/09/2017 10:32"));
		Product p8 = new Product(null, "Prato8", sdf.parse("30/09/2017 10:32"));
		Product p9 = new Product(null, "Prato9", sdf.parse("30/09/2017 10:32"));

		Professional prof1 = new Professional(null, "José", TypePosition.CHEF);
		Professional prof2 = new Professional(null, "Maria", TypePosition.ASSISTANT);
		Professional prof3 = new Professional(null, "Luiz", TypePosition.MANAGER);
		Professional prof4 = new Professional(null, "Renata", TypePosition.WAITER);

		prof1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		prof2.getProducts().addAll(Arrays.asList(p1, p3));

		p1.getProfessionals().addAll(Arrays.asList(prof1, prof2));
		p2.getProfessionals().addAll(Arrays.asList(prof1));
		p3.getProfessionals().addAll(Arrays.asList(prof1, prof2));

		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9));
		professionalRepository.saveAll(Arrays.asList(prof1, prof2, prof3, prof4));

		// Persist Review
		Review rw1 = new Review(null, TypeStatus.OPEN, new Date(), partner1);

		reviewRepository.saveAll(Arrays.asList(rw1));

		// Persist: Item Product
		ItemProduct itmProd1 = new ItemProduct(rw1, p1, 10);
		ItemProduct itmProd2 = new ItemProduct(rw1, p2, 9);
		ItemProduct itmProd3 = new ItemProduct(rw1, p3, 8);
		ItemProfessional itmProf1 = new ItemProfessional(rw1, prof3, 10);
		ItemProfessional itmProf2 = new ItemProfessional(rw1, prof4, 10);

		rw1.getItensProduct().addAll(Arrays.asList(itmProd1, itmProd2, itmProd3));
		rw1.getItensProfessional().addAll(Arrays.asList(itmProf1, itmProf2));

		p1.getItensProduct().addAll(Arrays.asList(itmProd1));
		p2.getItensProduct().addAll(Arrays.asList(itmProd2));
		p3.getItensProduct().addAll(Arrays.asList(itmProd3));

		prof3.getItensProfessional().addAll(Arrays.asList(itmProf1));
		prof4.getItensProfessional().addAll(Arrays.asList(itmProf2));

		itemProductRepository.saveAll(Arrays.asList(itmProd1, itmProd2, itmProd3));
		itemProfessionalRepository.saveAll(Arrays.asList(itmProf1, itmProf2));
		//
	}
}