package com.test.locations;

import java.security.KeyStore;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		System.out.println(builder.build().encode().toUri());
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
	
	@Value("${keyStore}")
	private String keyStoreName;
	
	@Value("${keyStorePassword}")
	private String keyStorePassword;
	
	@Autowired
    private ResourceLoader resourceLoader;
	
	@RequestMapping(value = "/cert/location", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<?> certlocation() {
		List<?> locations = null;
		try {
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

			keyStore.load(resourceLoader.getResource("classpath:" + keyStoreName).getInputStream(),
					keyStorePassword.toCharArray());

			SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
					new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(),
					NoopHostnameVerifier.INSTANCE);

			HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();

			ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
			RestTemplate restTemplate = new RestTemplate(requestFactory);

			String baseUrl = "https://bookdrive-user-api.telstra.net/api/locationgetAll";
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl).queryParam("adminPass", "n1nz@");
			locations = restTemplate.getForObject(builder.build().encode().toUri(), List.class);
		} catch (Exception e) {
			System.out.println("Exception" + e);
		}
		return locations;
	}

	
/*	@ResponseBody
	@RequestMapping(value = "/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String groups() {
		RestTemplate restTemplate = new RestTemplate();
		//-Djavax.net.ssl.TrustStore=classpath:resources/keystore.p12
		String baseUrl = "https://localhost:8443/getGroups";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
				
		String response = restTemplate.getForObject(builder.build().encode().toUri(), String.class);
		return response;

	} */

}
