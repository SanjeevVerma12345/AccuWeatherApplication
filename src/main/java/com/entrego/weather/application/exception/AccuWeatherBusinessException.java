package com.entrego.weather.application.exception;

public class AccuWeatherBusinessException extends Exception {

	public AccuWeatherBusinessException(final String exceptionMessage){
		super(exceptionMessage);
	}
}
