package com.entrego.weather.application.accuweatherclient;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.entrego.weather.application.exception.AccuWeatherBusinessException;
import com.entrego.weather.application.exception.RestCallException;
import com.entrego.weather.application.model.CountryModel;
import com.entrego.weather.application.model.PostalCodeSearch;
import com.entrego.weather.application.util.RestCallHelper;

@RunWith(MockitoJUnitRunner.class)
public class LocationKeyResolverTest {

	@Mock
	protected RestCallHelper restCallHelper;

	@InjectMocks
	private LocationKeyResolver locationKeyResolver;
	private String requestUrl;
	private String postalCode;

	@Before
	public void setup(){
		requestUrl = "http://www.google.com";
		postalCode = "12345";
		ReflectionTestUtils.setField(locationKeyResolver, "requestUrl", requestUrl);
		ReflectionTestUtils.setField(locationKeyResolver, "accuweatherApiKeyValue", "123456");
	}


	@Test(expected = RestCallException.class)
	public void getUsPostalCodeSearchFromPostalCode_restCallHelperReturns404_expectsRestCallException() throws AccuWeatherBusinessException {

		final ResponseEntity responseEntity = new ResponseEntity(null, HttpStatus.NOT_FOUND);
		when(restCallHelper.doGetWithReturnCode(eq(requestUrl),eq(PostalCodeSearch[].class), eq(StringUtils.EMPTY),
			eq(MediaType.APPLICATION_JSON),any())).thenReturn(responseEntity);

		locationKeyResolver.getUsPostalCodeSearchFromPostalCode(postalCode);
	}

	@Test(expected = RestCallException.class)
	public void getUsPostalCodeSearchFromPostalCode_restCallHelperReturnsEmptyBody_expectsRestCallException() throws AccuWeatherBusinessException {

		final ResponseEntity responseEntity = new ResponseEntity(StringUtils.EMPTY, HttpStatus.OK);
		when(restCallHelper.doGetWithReturnCode(eq(requestUrl),eq(PostalCodeSearch[].class), eq(StringUtils.EMPTY),
			eq(MediaType.APPLICATION_JSON),any())).thenReturn(responseEntity);

		locationKeyResolver.getUsPostalCodeSearchFromPostalCode(postalCode);
	}

	@Test(expected = AccuWeatherBusinessException.class)
	public void getUsPostalCodeSearchFromPostalCode_withPostalCodeNotForUS_throwsAccuWeatherException() throws AccuWeatherBusinessException {

		final PostalCodeSearch postalCodeSearch = mock(PostalCodeSearch.class);
		final CountryModel countryModel = mock(CountryModel.class);
		final ResponseEntity responseEntity = new ResponseEntity(new PostalCodeSearch[]{postalCodeSearch},HttpStatus.OK);
		when(postalCodeSearch.getCountryModel()).thenReturn(countryModel);
		when(countryModel.getId()).thenReturn("INDIA");
		when(restCallHelper.doGetWithReturnCode(eq(requestUrl),eq(PostalCodeSearch[].class), eq(StringUtils.EMPTY),
			eq(MediaType.APPLICATION_JSON),any())).thenReturn(responseEntity);

		locationKeyResolver.getUsPostalCodeSearchFromPostalCode(postalCode);
	}



	@Test
	public void getUsPostalCodeSearchFromPostalCode_withNoException_returnsPostalCodeSearch() throws AccuWeatherBusinessException {

		final CountryModel countryModel = mock(CountryModel.class);
		final PostalCodeSearch postalCodeSearch = mock(PostalCodeSearch.class);
		final ResponseEntity responseEntity = new ResponseEntity(new PostalCodeSearch[]{postalCodeSearch},HttpStatus.OK);
		when(restCallHelper.doGetWithReturnCode(eq(requestUrl),eq(PostalCodeSearch[].class), eq(StringUtils.EMPTY),
			eq(MediaType.APPLICATION_JSON),any())).thenReturn(responseEntity);
		when(postalCodeSearch.getCountryModel()).thenReturn(countryModel);
		when(countryModel.getId()).thenReturn("US");

		final PostalCodeSearch usPostalCodeSearchFromPostalCode = locationKeyResolver.getUsPostalCodeSearchFromPostalCode(postalCode);

		Assert.assertNotNull(usPostalCodeSearchFromPostalCode);
		Assert.assertEquals(postalCodeSearch,usPostalCodeSearchFromPostalCode);
	}
}
