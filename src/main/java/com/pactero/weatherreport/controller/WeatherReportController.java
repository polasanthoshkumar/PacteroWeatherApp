package com.pactero.weatherreport.controller;
/*
 * class acts as a front end controller to serve request to the application
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pactero.weatherreport.model.City;
import com.pactero.weatherreport.service.WeatherReportService;

@Controller
@RequestMapping("/")
public class WeatherReportController {
	
	Logger logger = Logger.getLogger(WeatherReportController.class);
	
	@Autowired WeatherReportService weatherReportService;
	
	@Value("${citiesList}")
	private String cities;
	
	@Autowired
    @Qualifier("cityValidator")
    private Validator validator;
	
	@InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }
	/*
	 * starting point of the application to get request
	 * @return cities list to render in front end
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model) {
		logger.info("Started to execute initForm ");
		City city = new City();
		model.addAttribute("city", city);
		initModelList(model);
		return "city";
	}
	
	/**
	 * post request which serves the request to get the weather details info for the given country
	 * @param model
	 * @param city
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String submitForm(Model model, @Validated City city, BindingResult result) {
		logger.info("Started to execute submitForm ");
		model.addAttribute("city", city);
		String returnVal = "city";
		initModelList(model);
		if(result.hasErrors()) {
			logger.error("Validation Errors exist");
			returnVal = "city";
		} else {
			City weatherReportForGivenCity = weatherReportService.getWeatherReportForGivenCity(city);
			model.addAttribute("city", weatherReportForGivenCity);
		}		
		return returnVal;
	}
	
	private void initModelList(Model model) {
		
		List<String> cityList = new ArrayList<String>();
		for (String city : cities.split(",")) {
			cityList.add(city);
		}
		model.addAttribute("cities", cityList);
	}

}
