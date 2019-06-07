package com.pactero.weatherreport.service.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactero.weatherreport.model.City;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:weather-mvc-config.xml")
public class WeatherReportServiceImplTest {

    @Autowired
    private WeatherReportServiceImpl weatherReportServiceImpl;
    
    @Value("${uriWithApiKey}")
    private String uriWithApiKey;

    @Before
    public void setupMock() {
        weatherReportServiceImpl.setUriWithApiKey(uriWithApiKey);
    }

    @Test
    public void test() {
        City city = new City();
        city.setCityName("Melbourne");
        assertNotNull(weatherReportServiceImpl.getWeatherReportForGivenCity(city));
    }

}
