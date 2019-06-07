package com.pactero.weatherreport.service.impl;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.pactero.weatherreport.controller.WeatherReportController;
import com.pactero.weatherreport.model.City;
import com.pactero.weatherreport.service.WeatherReportService;

public class WeatherReportServiceImpl implements WeatherReportService {
	
	Logger logger = Logger.getLogger(WeatherReportController.class);

	@Value("${uriWithApiKey}")
	private String uriWithApiKey;
	
	public City getWeatherReportForGivenCity(City city) {
		
		try {
			String weatherInformationResponse = invokeWeatherApi(city.getCityName());
			return mapResponseJsonFieldsToObj(city, weatherInformationResponse); 
			
			
		} catch (JSONException e) {
			logger.error("Excepton occured while reading from response "+e.getMessage());
		}
		return city;
	}

	/**
	 * @param city
	 * @param weatherInformationResponse
	 * @throws JSONException
	 */
	private City mapResponseJsonFieldsToObj(City city, String weatherInformationResponse) throws JSONException {
		logger.info("Mapping Json Response  to Model f");
		JSONObject jsonObj = new JSONObject(weatherInformationResponse);
		
		JSONArray weatherObj = (JSONArray)jsonObj.get("weather");
		JSONObject desc = (JSONObject)weatherObj.get(weatherObj.length()-1);
		city.setWeather(desc.get("description").toString());
		
		
		JSONObject tempInfo = (JSONObject)jsonObj.get("main");
		city.setTemperature(tempInfo.get("temp")+"\u00B0"+"C".toString());
		
		JSONObject speedInfo = (JSONObject)jsonObj.get("wind");
		city.setWindSpeed(String.valueOf(milesTokm(Double.parseDouble(speedInfo.get("speed").toString()))));
		return city;
	}
	
	private String invokeWeatherApi(String cityName) {
			logger.info("Invoking Weather API URL");
		RestTemplate restTemplate = new RestTemplate();
		String url = uriWithApiKey+cityName;
		return restTemplate.getForObject(url, String.class);
	}

	private double milesTokm(double distanceInMiles) {
        return distanceInMiles * 1.60934;
    }
}
