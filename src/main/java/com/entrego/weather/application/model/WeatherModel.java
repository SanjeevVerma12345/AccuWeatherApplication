package com.entrego.weather.application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherModel {

	@JsonProperty("WeatherText")
	private String weatherText;

	@JsonProperty("WeatherIcon")
	private Integer weatherIcon;

	@JsonProperty("IsDayTime")
	private Boolean isDayTime;

	@JsonProperty("Temperature")
	private TemperatureModel temperatureModel;

	public String getWeatherText() {
		return weatherText;
	}

	public void setWeatherText(final String weatherText) {
		this.weatherText = weatherText;
	}

	public Integer getWeatherIcon() {
		return weatherIcon;
	}

	public void setWeatherIcon(final Integer weatherIcon) {
		this.weatherIcon = weatherIcon;
	}

	public Boolean getDayTime() {
		return isDayTime;
	}

	public void setDayTime(final Boolean dayTime) {
		isDayTime = dayTime;
	}

	public TemperatureModel getTemperatureModel() {
		return temperatureModel;
	}

	public void setTemperatureModel(final TemperatureModel temperatureModel) {
		this.temperatureModel = temperatureModel;
	}
}
