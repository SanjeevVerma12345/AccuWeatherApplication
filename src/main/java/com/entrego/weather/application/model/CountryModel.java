package com.entrego.weather.application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryModel {

	@JsonProperty("ID")
	private String id;

	@JsonProperty("LocalizedName")
	private String localizedName;

	@JsonProperty("EnglishName")
	private String englishName;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getLocalizedName() {
		return localizedName;
	}

	public void setLocalizedName(final String localizedName) {
		this.localizedName = localizedName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(final String englishName) {
		this.englishName = englishName;
	}
}
