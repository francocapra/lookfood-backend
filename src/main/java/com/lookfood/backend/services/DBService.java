package com.lookfood.backend.services;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.lookfood.backend.domain.enums.Profile;
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

	@Value("${default.recipient}")
	private String recipient;

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
		Region region1 = new Region(null, "Rio Grande do Sul");

		City city1 = new City(null, "Porto Alegre", region1);
		City city2 = new City(null, "Canoas", region1);

		region1.getCities().addAll(Arrays.asList(city1, city2));

		regionRepository.saveAll(Arrays.asList(region1));
		cityRepository.saveAll(Arrays.asList(city1, city2));

		Partner partner1 = new Partner(null, "Fulano da Silva", "email@dominio.com.br", "61516394000120",
				"www.restaurante.com", pe.encode("123"));
		partner1.getPhones().addAll(Arrays.asList("5127363323", "93838393"));

		Address address1 = new Address(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", partner1, city1);
		partner1.getAddresses().addAll(Arrays.asList(address1));

		Partner partner2 = new Partner(null, "Adminstrador", "admin@email.com.br",
				// recipient, //configurado no "application.properties"
				"26173313000135", "www.restaurante2.com", pe.encode("123"));
		partner2.addProfile(Profile.ADMIN);

		Address e2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", partner2, city2);
		partner2.getAddresses().addAll(Arrays.asList(e2));

		partnerRepository.saveAll(Arrays.asList(partner1, partner2));
		addressRepository.saveAll(Arrays.asList(address1, e2));

		// Persist: Product/ Professional
		Product p1 = new Product(null, "Moqueca", 23.00, "BRL", null, "brasil");
		Product p2 = new Product(null, "Acarajé", 34.00, "BRL", null, "brasil");
		Product p3 = new Product(null, "Poutine", 45.80, "BRL", null, "canada");
		Product p4 = new Product(null, "Bannock", 28.00, "BRL", null, "canada");
		Product p5 = new Product(null, "Butter tarts", 59.00, "BRL", null, "canada");
		Product p6 = new Product(null, "Wurst e Currywurst", 56.00, "BRL", null, "alemanha");
		Product p7 = new Product(null, "Schnitzel", 14.00, "BRL", null, "alemanha");
		Product p8 = new Product(null, "Feijoada", 11.00, "BRL", null, "brasil");
		Product p9 = new Product(null, "Churrasco", 23.00, "BRL", null, "brasil");

		Professional prof1 = new Professional(null, "José", TypePosition.CHEF);
		Professional prof2 = new Professional(null, "Maria", TypePosition.ASSISTANT);
		Professional prof3 = new Professional(null, "Luiz", TypePosition.MANAGER);
		Professional prof4 = new Professional(null, "Renata", TypePosition.WAITER);

		prof1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		prof2.getProducts().addAll(Arrays.asList(p1, p3));

		p1.getProfessionals().addAll(Arrays.asList(prof1, prof2));
		p2.getProfessionals().addAll(Arrays.asList(prof1));
		p3.getProfessionals().addAll(Arrays.asList(prof1, prof2));
		
		p1.setPartner(partner1);
		p2.setPartner(partner1);
		p3.setPartner(partner1);
		p4.setPartner(partner1);
		p5.setPartner(partner1);
		p6.setPartner(partner1);
		p7.setPartner(partner1);
		p8.setPartner(partner1);
		p9.setPartner(partner1);

		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9));
		professionalRepository.saveAll(Arrays.asList(prof1, prof2, prof3, prof4));

		// Persist Review
		Review rw1 = new Review(null, TypeStatus.OPEN, new Date(), partner1);
		Review rw2 = new Review(null, TypeStatus.OPEN, new Date(), partner1);
		Review rw3 = new Review(null, TypeStatus.OPEN, new Date(), partner2);

		reviewRepository.saveAll(Arrays.asList(rw1, rw2, rw3));
		
		
		// Persist: Item Product
		ItemProduct itmProd1 = new ItemProduct(rw1, p1, 10);
		ItemProduct itmProd2 = new ItemProduct(rw1, p2, 9);
		ItemProduct itmProd3 = new ItemProduct(rw1, p3, 8);
		ItemProduct itmProd4 = new ItemProduct(rw1, p4, 10);
		ItemProduct itmProd5 = new ItemProduct(rw1, p5, 9);
		ItemProduct itmProd6 = new ItemProduct(rw1, p6, 8);
		ItemProduct itmProd7 = new ItemProduct(rw1, p7, 10);
		ItemProduct itmProd8 = new ItemProduct(rw1, p8, 9);
		ItemProduct itmProd9 = new ItemProduct(rw1, p9, 8);
		ItemProfessional itmProf1 = new ItemProfessional(rw1, prof3, 10);
		ItemProfessional itmProf2 = new ItemProfessional(rw1, prof4, 10);

		ItemProduct itmProd10 = new ItemProduct(rw3, p1, 10);
		ItemProduct itmProd11 = new ItemProduct(rw3, p2, 9);
		ItemProduct itmProd12 = new ItemProduct(rw3, p3, 8);
		ItemProduct itmProd13 = new ItemProduct(rw3, p4, 10);
		ItemProduct itmProd14 = new ItemProduct(rw3, p5, 9);
		ItemProduct itmProd15 = new ItemProduct(rw3, p6, 8);
		ItemProfessional itmProf3 = new ItemProfessional(rw3, prof3, 10);
		ItemProfessional itmProf4 = new ItemProfessional(rw3, prof4, 10);

		rw1.getItemsProduct().addAll(Arrays.asList(itmProd1, itmProd2, itmProd3, itmProd4, itmProd5, itmProd6, itmProd7,
				itmProd8, itmProd9));
		rw1.getItemsProfessional().addAll(Arrays.asList(itmProf1, itmProf2));
		
		rw2.getItemsProduct().addAll(Arrays.asList(itmProd1, itmProd2, itmProd3, itmProd4, itmProd5, itmProd6, itmProd7,
				itmProd8, itmProd9));
		rw2.getItemsProfessional().addAll(Arrays.asList(itmProf1, itmProf2));
		
		rw3.getItemsProduct().addAll(Arrays.asList(itmProd10, itmProd11, itmProd12, itmProd13, itmProd14, itmProd15));
		rw3.getItemsProfessional().addAll(Arrays.asList(itmProf3, itmProf4));

		p1.getItensProduct().addAll(Arrays.asList(itmProd1, itmProd10));
		p2.getItensProduct().addAll(Arrays.asList(itmProd2, itmProd11));
		p3.getItensProduct().addAll(Arrays.asList(itmProd3, itmProd12));
		p4.getItensProduct().addAll(Arrays.asList(itmProd4, itmProd14));
		p5.getItensProduct().addAll(Arrays.asList(itmProd5, itmProd15));
		p6.getItensProduct().addAll(Arrays.asList(itmProd6));
		p7.getItensProduct().addAll(Arrays.asList(itmProd7));
		p8.getItensProduct().addAll(Arrays.asList(itmProd8));
		p9.getItensProduct().addAll(Arrays.asList(itmProd9));

		prof3.getItensProfessional().addAll(Arrays.asList(itmProf1, itmProf3));
		prof4.getItensProfessional().addAll(Arrays.asList(itmProf2, itmProf4));

		itemProductRepository.saveAll(Arrays.asList(itmProd1, itmProd2, itmProd3, itmProd4, itmProd5, itmProd6,
				itmProd7, itmProd8, itmProd9, itmProd10, itmProd11, itmProd12, itmProd13, itmProd14, itmProd15));
		itemProfessionalRepository.saveAll(Arrays.asList(itmProf1, itmProf2, itmProf3, itmProf4));
		//
	}
}
