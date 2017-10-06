package com.test.locations;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
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

	@RequestMapping(value = "/location", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<?> location() {
		RestTemplate restTemplate = new RestTemplate();
		String baseUrl = "https://bookdrive-user-api.telstra.net/api/locationgetAll";

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl).queryParam("adminPass", "n1nz@");
		List<?> locations = restTemplate.getForObject(builder.build().encode().toUri(), List.class);
		return locations;
	}
	
	@RequestMapping(value = "/secureLocation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<?> secureLocation() {
		RestTemplate restTemplate = new RestTemplate();
		String baseUrl = "https://bookdrive-user-api.telstra.net/api/locationgetAll";

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl).queryParam("adminPass", "n1nz@");
		List<?> locations = restTemplate.getForObject(builder.build().encode().toUri(), List.class);
		return locations;
	}
	
	
	/*public String securedLocation(){
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(new FileInputStream(new File("keystore.jks")),
		        "secret".toCharArray());
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
		        new SSLContextBuilder()
		                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
		                .loadKeyMaterial(keyStore, "password".toCharArray()).build());
		HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
		        httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		ResponseEntity<String> response = restTemplate.getForEntity(
		        "https://localhost:8443", String.class);
	}*/
	
	
	@ResponseBody
	@RequestMapping(value = "/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String groups() {
		RestTemplate restTemplate = new RestTemplate();
		//-Djavax.net.ssl.TrustStore=classpath:resources/keystore.p12
		String baseUrl = "https://localhost:8443/getGroups";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
				
		String response = restTemplate.getForObject(builder.build().encode().toUri(), String.class);
		return response;

	} 

}
