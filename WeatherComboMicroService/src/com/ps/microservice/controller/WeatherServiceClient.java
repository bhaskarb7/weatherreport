package com.ps.microservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ps.microservice.model.Forecast;
import com.ps.microservice.model.ForecastDetails;
import com.ps.microservice.model.Weather;
import com.ps.microservice.model.WeatherAggregated;
import com.ps.microservice.service.WeatherServiceIntegration;

@RestController
public class WeatherServiceClient {
	
	@Autowired
	private WeatherServiceIntegration integration;

	@RequestMapping("/weathercomposite/{city}")
	@ResponseBody
	WeatherAggregated getWeatherComposite(@PathVariable String city) {

		// Step 1 Call get weather

		Weather weather = integration.getWeather(city);

		// Step 2 call get Forecast

		Forecast forecast = integration.getForecast(city);

		List<ForecastDetails> list = forecast.getList();

		ForecastDetails forecastDetails = new ForecastDetails();
		if (null != list && list.size() > 0) {
			forecastDetails = list.get(0);
		}
		WeatherAggregated weatherAgg = new WeatherAggregated(weather, forecastDetails);
		System.out.println(" weatherAgg --->"+ weatherAgg.getForecast().getDt_txt());
		return weatherAgg; 
	}

	

}
