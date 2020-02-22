package com.bay.entity.core.location;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_locations", schema = "bay_col")
public class TblLocation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "country", nullable = true)
	private String country;

	@Column(name = "region", nullable = true)
	private String region;

	@Column(name = "city", nullable = true)
	private String city;

	@Column(name = "postalcode", nullable = true)
	private String postalcode;

	@Column(name = "longitude", nullable = false)
	private float longitude;

	@Column(name = "latitude", nullable = false)
	private float latitude;
	
	@Column(name = "metrocode", nullable = true)
	private String metrocode;

	@Column(name = "areacode", nullable = true)
	private String areacode;
	
	@Column(name = "id_customer", nullable = false)
	private Long idCustomer;

	/*@ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name="id_customer", nullable=false)
	private TblCustomer customer;*/
	
	@Column(name = "created", nullable = true)
	private LocalDateTime created;

	
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

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
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

	public Long getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Long idCustomer) {
		this.idCustomer = idCustomer;
	}
	
}