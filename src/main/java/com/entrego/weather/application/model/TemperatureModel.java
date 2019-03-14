package com.entrego.weather.application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemperatureModel {

	@JsonProperty("Imperial")
	private ImperialTemperatureModel imperialTemperatureModel;

	public ImperialTemperatureModel getImperialTemperatureModel() {
		return imperialTemperatureModel;
	}

	public void setImperialTemperatureModel(final ImperialTemperatureModel imperialTemperatureModel) {
		this.imperialTemperatureModel = imperialTemperatureModel;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public class ImperialTemperatureModel{

		@JsonProperty("Value")
		private Float value;

		@JsonProperty("Unit")
		private String unit;

		@JsonProperty("UnitType")
		private Integer unitType;

		public Float getValue() {
			return value;
		}

		public void setValue(final Float value) {
			this.value = value;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(final String unit) {
			this.unit = unit;
		}

		public Integer getUnitType() {
			return unitType;
		}

		public void setUnitType(final Integer unitType) {
			this.unitType = unitType;
		}
	}
}
