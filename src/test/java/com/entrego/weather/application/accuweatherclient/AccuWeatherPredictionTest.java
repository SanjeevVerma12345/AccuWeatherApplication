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

import com.entrego.weather.application.exception.RestCallException;
import com.entrego.weather.application.model.WeatherModel;
import com.entrego.weather.application.util.RestCallHelper;

@RunWith(MockitoJUnitRunner.class)
public class AccuWeatherPredictionTest {

	@Mock
	protected RestCallHelper restCallHelper;

	@InjectMocks
	private AccuWeatherPrediction accuWeatherPrediction;

	private String requestUrl;
	private String locationKey;


	@Before
	public void setup(){
		requestUrl = "http://www.google.com";
		locationKey = "1";
		ReflectionTestUtils.setField(accuWeatherPrediction, "requestUrl", requestUrl);
		ReflectionTestUtils.setField(accuWeatherPrediction, "accuweatherApiKeyValue", "123456");
	}

	@Test(expected = RestCallException.class)
	public void getWeatherUsingLocationKey_restCallHelperReturns404_expectsRestCallException(){

		final ResponseEntity responseEntity = new ResponseEntity(null,HttpStatus.NOT_FOUND);
		when(restCallHelper.doGetWithReturnCode(eq(requestUrl),eq(WeatherModel[].class), eq(StringUtils.EMPTY),
			eq(MediaType.APPLICATION_JSON),any())).thenReturn(responseEntity);

		accuWeatherPrediction.getWeatherUsingLocationKey(locationKey);
	}

	@Test(expected = RestCallException.class)
	public void getWeatherUsingLocationKey_restCallHelperReturnsEmptyBody_expectsRestCallException(){

		final ResponseEntity responseEntity = new ResponseEntity(StringUtils.EMPTY,HttpStatus.OK);
		when(restCallHelper.doGetWithReturnCode(eq(requestUrl),eq(WeatherModel[].class), eq(StringUtils.EMPTY),
			eq(MediaType.APPLICATION_JSON),any())).thenReturn(responseEntity);

		accuWeatherPrediction.getWeatherUsingLocationKey(locationKey);
	}

	@Test
	public void getWeatherUsingLocationKey_withNoException_returnsWeatherModel(){

		final WeatherModel weatherModel = mock(WeatherModel.class);
		final ResponseEntity responseEntity = new ResponseEntity(new WeatherModel[]{weatherModel},HttpStatus.OK);
		when(restCallHelper.doGetWithReturnCode(eq(requestUrl),eq(WeatherModel[].class), eq(StringUtils.EMPTY),
			eq(MediaType.APPLICATION_JSON),any())).thenReturn(responseEntity);

		final WeatherModel weatherUsingLocationKey = accuWeatherPrediction.getWeatherUsingLocationKey(locationKey);

		Assert.assertNotNull(weatherModel);
		Assert.assertEquals(weatherModel,weatherUsingLocationKey);
	}

}
