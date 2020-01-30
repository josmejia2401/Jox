package com.bay.entity.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.postgresql.geometric.PGpoint;

@Entity
@Table(name = "locations", schema = "bay")
public class Location implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "country", nullable = false)
	private String country;

	@Column(name = "region", nullable = false)
	private String region;
	
	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "postalcode", nullable = false)
	private String postalcode;
	
	@Column(name = "location", nullable = false)
	private PGpoint location;
	
	@Column(name = "metrocode", nullable = false)
	private String metrocode;
	
	@Column(name = "areacode", nullable = false)
	private String areacode;
	
	@Column(name = "created", nullable = false)
	private LocalDateTime created;
	
	//@ManyToOne
    //@JoinColumn(name="id_users", nullable=false)
    //private User user;
	
	public Location() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public PGpoint getLocation() {
		return location;
	}

	public void setLocation(PGpoint location) {
		this.location = location;
	}

	public String getMetrocode() {
		return metrocode;
	}

	public void setMetrocode(String metrocode) {
		this.metrocode = metrocode;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	
	
}
