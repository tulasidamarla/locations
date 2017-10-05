package com.test.locations;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootApplication
@RestController
public class LocationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationsApplication.class, args);
	}

	@ResponseBody
	@RequestMapping(value = "/location", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<?> location() {
		RestTemplate restTemplate = new RestTemplate();
		String baseUrl = "https://bookdrive-user-api.telstra.net/api/locationgetAll";

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl).queryParam("adminPass", "n1nz@");
		List<?> locations = restTemplate.getForObject(builder.build().encode().toUri(), List.class);
		return locations;

	} 

}
