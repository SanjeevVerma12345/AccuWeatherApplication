package com.entrego.weather.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.entrego.weather.application.accuweatherclient.AccuWeatherPrediction;
import com.entrego.weather.application.accuweatherclient.LocationKeyResolver;
import com.entrego.weather.application.exception.AccuWeatherBusinessException;
import com.entrego.weather.application.exception.RestCallException;
import com.entrego.weather.application.model.PostalCodeSearch;
import com.entrego.weather.application.model.WeatherFormModel;
import com.entrego.weather.application.model.WeatherModel;

@Controller
public class EntregoWeatherController {

	@Autowired
	private LocationKeyResolver locationKeyResolver;
	@Autowired
	private AccuWeatherPrediction accuWeatherPrediction;

	@GetMapping("/")
	public String index() {
		return "weatherForm";
	}

	@PostMapping(value = "/getLocationAndWeather")
	public String getLocationAndWeatherForPostalCode(@ModelAttribute("weatherForm") final WeatherFormModel weatherFormModel, final Model model){
		try {
			weatherFormModel.validatePostalCode();
			final PostalCodeSearch usPostalCodeSearchFromPostalCode = locationKeyResolver.getUsPostalCodeSearchFromPostalCode(weatherFormModel.getPostalCode());
			final WeatherModel weatherUsingLocationKey = accuWeatherPrediction.getWeatherUsingLocationKey(usPostalCodeSearchFromPostalCode.getLocationKey());
			model.addAttribute("postalCodeSearch",usPostalCodeSearchFromPostalCode);
			model.addAttribute("weather",weatherUsingLocationKey);
		} catch (final AccuWeatherBusinessException e) {
			model.addAttribute("weatherError", e.getMessage());
		} catch(final RestCallException e){
			model.addAttribute("weatherError", "Could not communicate with Accuweather client. Please try again later");
		}
		return "weatherForm";
	}


}
