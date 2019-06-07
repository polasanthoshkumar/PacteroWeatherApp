package com.pactero.weatherreport.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.pactero.weatherreport.model.City;


public class CityValidator implements Validator {

	public boolean supports(Class<?> paramClass) {
		return City.class.equals(paramClass);
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cityName", "valid.city");
	}

}
