package com.pactero.weatherreport.service.impl;

/**
 * Service to calculate the weather info
 */
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pactero.weatherreport.controller.WeatherReportController;
import com.pactero.weatherreport.model.City;
import com.pactero.weatherreport.service.WeatherReportService;

@Service
public class WeatherReportServiceImpl implements WeatherReportService {
	
	Logger logger = Logger.getLogger(WeatherReportController.class);

	@Value("${uriWithApiKey}")
	private String uriWithApiKey;
	
	@Autowired
	private RestTemplate restTemplate;
	
	/*
	 * @param <code> City</code>
	 * invoke the rest call to OpenWeatherApi to fetch the weather report for given city
	 * maps the response back to the model Obj and return back the response
	 */
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
	
	/*
	 * Makes a Rest call to the given webservice URL
	 * @return String response
	 */
	private String invokeWeatherApi(String cityName) {
			logger.info("Invoking Weather API URL");
		String url = uriWithApiKey+cityName;
		return restTemplate.getForObject(url, String.class);
	}

	/*
	 * convert the speed in miles to Km/h
	 * The respons returned by the api is in miles, so this api converts to km/h
	 */
	private double milesTokm(double distanceInMiles) {
        return distanceInMiles * 1.60934;
    }
}
