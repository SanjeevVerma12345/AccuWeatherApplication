package com.entrego.weather.application.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import com.entrego.weather.application.accuweatherclient.AccuWeatherPrediction;
import com.entrego.weather.application.accuweatherclient.LocationKeyResolver;
import com.entrego.weather.application.exception.AccuWeatherBusinessException;
import com.entrego.weather.application.model.PostalCodeSearch;
import com.entrego.weather.application.model.WeatherFormModel;
import com.entrego.weather.application.model.WeatherModel;

@RunWith(MockitoJUnitRunner.class)
public class EntregoWeatherControllerTest {

	@Mock
	private LocationKeyResolver locationKeyResolver;
	@Mock
	private AccuWeatherPrediction accuWeatherPrediction;
	@InjectMocks
	private EntregoWeatherController entregoWeatherController;

	@Spy
	private final WeatherFormModel weatherFormModel = new WeatherFormModel();
	private Model model;

	@Before
	public void setup(){
		model = mock(Model.class);
	}

	@Test
	public void getLocationAndWeatherForPostalCode_withEmptyPostalCode_zeroInteractionsWithAccuweather() throws AccuWeatherBusinessException {

		weatherFormModel.setPostalCode(StringUtils.EMPTY);

		entregoWeatherController.getLocationAndWeatherForPostalCode(weatherFormModel,model);

		verify(locationKeyResolver,never()).getUsPostalCodeSearchFromPostalCode(any());
		verify(accuWeatherPrediction,never()).getWeatherUsingLocationKey(any());
	}

	@Test
	public void getLocationAndWeatherForPostalCode_withStringPostalCode_zeroInteractionsWithAccuweather() throws AccuWeatherBusinessException {

		weatherFormModel.setPostalCode("number");

		entregoWeatherController.getLocationAndWeatherForPostalCode(weatherFormModel,model);

		verify(locationKeyResolver,never()).getUsPostalCodeSearchFromPostalCode(any());
		verify(accuWeatherPrediction,never()).getWeatherUsingLocationKey(any());
	}

	@Test
	public void getLocationAndWeatherForPostalCode_withInvalidPostalCode_zeroInteractionsWithAccuweather() throws AccuWeatherBusinessException {

		weatherFormModel.setPostalCode("12");

		entregoWeatherController.getLocationAndWeatherForPostalCode(weatherFormModel,model);

		weatherFormModel.setPostalCode("123456");

		entregoWeatherController.getLocationAndWeatherForPostalCode(weatherFormModel,model);

		verify(locationKeyResolver,never()).getUsPostalCodeSearchFromPostalCode(any());
		verify(accuWeatherPrediction,never()).getWeatherUsingLocationKey(any());
	}

	@Test
	public void getLocationAndWeatherForPostalCode_withValidPostalCode_returnsWeatherForm() throws AccuWeatherBusinessException {

		weatherFormModel.setPostalCode("12345");
		final PostalCodeSearch postalCodeSearch = mock(PostalCodeSearch.class);
		final WeatherModel weatherModel = mock(WeatherModel.class);
		postalCodeSearch.setLocationKey("CP_1001");
		when(locationKeyResolver.getUsPostalCodeSearchFromPostalCode(weatherFormModel.getPostalCode())).thenReturn(postalCodeSearch);
		when(accuWeatherPrediction.getWeatherUsingLocationKey(postalCodeSearch.getLocationKey())).thenReturn(weatherModel);

		final String locationAndWeatherForPostalCode = entregoWeatherController.getLocationAndWeatherForPostalCode(weatherFormModel, model);

		verify(model).addAttribute("postalCodeSearch",postalCodeSearch);
		verify(model).addAttribute("weather",weatherModel);
		Assert.assertEquals("weatherForm",locationAndWeatherForPostalCode);

	}
}
