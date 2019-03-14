package com.entrego.weather.application.accuweatherclient;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.entrego.weather.application.exception.RestCallException;
import com.entrego.weather.application.util.HttpHeaderAdaptor;
import com.entrego.weather.application.util.RestCallHelper;

@Component
public abstract class AccuWeatherClient {

	@Autowired
	protected RestCallHelper restCallHelper;

	@Autowired
	protected HttpHeaderAdaptor httpHeaderAdaptor;

	@Value("${accuweather.apikey}")
	protected String accuweatherApiKeyValue;

	protected void validateResponseCodeAsOKAndBodyNotEmpty(final String requestUrl, final ResponseEntity<?> responseEntity){
		if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
			throw new RestCallException(
				String.format("Received an unexpected Http Status code %s", responseEntity.getStatusCode()));
		}
		if(StringUtils.isBlank(String.valueOf(responseEntity.getBody()))){
			throw new RestCallException(
				String.format("Received empty body for request [%s]", requestUrl));
		}
	}

}
