package com.entrego.weather.application.accuweatherclient;

import java.text.MessageFormat;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.entrego.weather.application.exception.AccuWeatherBusinessException;
import com.entrego.weather.application.model.PostalCodeSearch;

@Service
public class LocationKeyResolver extends AccuWeatherClient{

	@Value("${accuweather.postalCodeSearch.requestUrl}")
	private String requestUrl;


	public PostalCodeSearch getUsPostalCodeSearchFromPostalCode(final String postalCode)
		throws AccuWeatherBusinessException {

		final String postalCodeSearchRequestUrl = MessageFormat.format(requestUrl,
			accuweatherApiKeyValue,postalCode);

		final ResponseEntity<PostalCodeSearch[]> postalCodeSearchResponseEntity = restCallHelper.doGetWithReturnCode(
			postalCodeSearchRequestUrl, PostalCodeSearch[].class, StringUtils.EMPTY, MediaType.APPLICATION_JSON,
			httpHeaders -> httpHeaderAdaptor.generateHeaders(httpHeaders));
		validateResponseCodeAsOKAndBodyNotEmpty(requestUrl,postalCodeSearchResponseEntity);
		return validateAndConsumeResponseForUSPostalCode(postalCodeSearchResponseEntity.getBody());
	}

	private PostalCodeSearch validateAndConsumeResponseForUSPostalCode(final PostalCodeSearch[] postalCodeSearchResponse)
		throws AccuWeatherBusinessException {
		if(ArrayUtils.isEmpty(postalCodeSearchResponse))
			throw new AccuWeatherBusinessException("Zipcode not found");
		return Arrays.asList(postalCodeSearchResponse).parallelStream()
			.filter(postalCode -> postalCode.getCountryModel().getId().equalsIgnoreCase("US"))
			.findFirst().orElseThrow(() -> new AccuWeatherBusinessException("Zipcode not found"));
	}

}
