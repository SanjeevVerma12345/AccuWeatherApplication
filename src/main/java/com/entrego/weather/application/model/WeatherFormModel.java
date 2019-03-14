package com.entrego.weather.application.model;

import org.apache.commons.lang3.StringUtils;

import com.entrego.weather.application.exception.AccuWeatherBusinessException;

public class WeatherFormModel {

	private String postalCode;

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(final String postalCode) {
		this.postalCode = postalCode;
	}

	public Boolean validatePostalCode() throws AccuWeatherBusinessException {
		if(!StringUtils.isNumeric(postalCode)|| postalCode.length()!=5){
			throw new AccuWeatherBusinessException("Invalid zip code format");
		}
		return true;
	}
}
