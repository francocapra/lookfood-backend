package com.lookfood.backend.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lookfood.backend.domain.enums.TypeProfile;

@Entity
public class Partner implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer id;
	private String name;
	
	@Column(unique=true)
	private String email;
	
	@JsonIgnore
	private String password;

	private String cnpj;
	private String website;
	
	@ElementCollection
	@CollectionTable(name="PHONE")
	private Set<String> phones = new HashSet<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="PROFILE")
	private Set<Integer> profiles = new HashSet<>();
	
	@OneToMany(mappedBy="partner", cascade=CascadeType.ALL)
	private List<Address> addresses = new ArrayList<>();
	
	@OneToMany(mappedBy="partner")
	@JsonIgnore
	private List<Review> reviews = new ArrayList<>();

	public Partner() {
		super();
		addProfile(TypeProfile.PARTNER);
	}

	public Partner(Integer id, String name, String email, String cnpj, String website, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.cnpj = cnpj;
		this.website = website;
		this.password = password;
		addProfile(TypeProfile.PARTNER);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public String getPassword() {
		return password;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getWebsite() {
		return website;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getPhones() {
		return phones;
	}

	public void setPhones(Set<String> phones) {
		this.phones = phones;
	}

	public List<Address> getAddresses() {
		return addresses;
	}
	
	public Set<TypeProfile> getProfiles(){
		return profiles.stream().map(x -> TypeProfile.toEnum(x)).collect(Collectors.toSet());
	}
	
	public void addProfile(TypeProfile profile) {
		profiles.add(profile.getCod());
	}
	
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partner other = (Partner) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}		
		
}
