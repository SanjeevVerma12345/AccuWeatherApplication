package com.entrego.weather.application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostalCodeSearch {

	@JsonProperty("Key")
	private String locationKey;

	@JsonProperty("PrimaryPostalCode")
	private Integer primaryPostalCode;

	@JsonProperty("LocalizedName")
	private String localizedName;

	@JsonProperty("Country")
	private CountryModel countryModel;

	@JsonProperty("AdministrativeArea")
	private AdministrativeArea administrativeArea;

	public String getLocationKey() {
		return locationKey;
	}

	public void setLocationKey(final String locationKey) {
		this.locationKey = locationKey;
	}

	public Integer getPrimaryPostalCode() {
		return primaryPostalCode;
	}

	public void setPrimaryPostalCode(final Integer primaryPostalCode) {
		this.primaryPostalCode = primaryPostalCode;
	}

	public String getLocalizedName() {
		return localizedName;
	}

	public void setLocalizedName(final String localizedName) {
		this.localizedName = localizedName;
	}

	public CountryModel getCountryModel() {
		return countryModel;
	}

	public void setCountryModel(final CountryModel countryModel) {
		this.countryModel = countryModel;
	}

	public AdministrativeArea getAdministrativeArea() {
		return administrativeArea;
	}

	public void setAdministrativeArea(final AdministrativeArea administrativeArea) {
		this.administrativeArea = administrativeArea;
	}
}
