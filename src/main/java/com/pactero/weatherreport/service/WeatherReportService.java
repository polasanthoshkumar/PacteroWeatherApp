package com.pactero.weatherreport.service;

import com.pactero.weatherreport.model.City;

@FunctionalInterface
public interface WeatherReportService {
	
	public City getWeatherReportForGivenCity(City city);

}
