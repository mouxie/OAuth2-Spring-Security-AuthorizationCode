package com.yanmouxie.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ClientController {
	
	@Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/test")
    public String test(Principal principal) {
        return "Hello, " + principal.getName();
    }
    
    @GetMapping("/hello")
    public String hello(OAuth2AuthenticationToken authentication) {
    	
        OAuth2AuthorizedClient authorizedClient =
            this.authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        
        String token = accessToken.getTokenValue();
        
        System.out.println("AccessToken >> " + token);
        
        RestTemplate restTemplate = new RestTemplate();

     	//String url = "http://localhost:9090/user"; // url in resource server
     	String url = authorizedClient.getClientRegistration()
				.getProviderDetails()
				.getUserInfoEndpoint()
				.getUri();
     	
     	System.out.println("url >> " + url);

     	// Use the access token for authentication
     	HttpHeaders header = new HttpHeaders();
     	header.add("Authorization", "Bearer " + token);
     	HttpEntity<String> entity = new HttpEntity<>(header);
     	ResponseEntity<String> response = restTemplate.exchange(url,
     				HttpMethod.GET, entity, String.class);

     	System.out.println("Resource Server Response >> " + response.getBody());

     	return response.getBody();
    }
}