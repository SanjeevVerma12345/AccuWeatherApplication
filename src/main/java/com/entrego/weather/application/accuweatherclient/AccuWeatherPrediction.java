package com.entrego.weather.application.accuweatherclient;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.entrego.weather.application.model.WeatherModel;

@Service
public class AccuWeatherPrediction extends AccuWeatherClient {

	@Value("${accuweather.weatherPredictionUsingLocationKey.requestUrl}")
	private String requestUrl;

	public WeatherModel getWeatherUsingLocationKey(final String locationKey) {

		final String postalCodeSearchRequestUrl = MessageFormat.format(requestUrl,
			String.valueOf(locationKey),accuweatherApiKeyValue);

		final ResponseEntity<WeatherModel[]> weatherModelResponseEntity = restCallHelper.doGetWithReturnCode(
			postalCodeSearchRequestUrl, WeatherModel[].class,	StringUtils.EMPTY, MediaType.APPLICATION_JSON,
			httpHeaders -> httpHeaderAdaptor.generateHeaders(httpHeaders));
		validateResponseCodeAsOKAndBodyNotEmpty(requestUrl,weatherModelResponseEntity);
		return weatherModelResponseEntity.getBody()[0];
	}

}
