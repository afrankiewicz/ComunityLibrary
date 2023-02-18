package com.agular.hello.geolocation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class GeolocationService {

    private final RestTemplate restTemplate;

    @Value( "${geolocation.url}" )
    private String url;

    @Value( "${geolocation.apiKey}" )
    private String apiKey;

    public GeolocationService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Optional<GeolocationServiceResponse> getCoordinates(String city) {
        String url = UriComponentsBuilder.fromUriString(this.url)
                .queryParam("q", city)
                .queryParam("apiKey", this.apiKey)
                .toUriString();
        ResponseEntity<List<GeolocationServiceResponse>> result = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {}, city);
        List<GeolocationServiceResponse> itemList = result.getBody();
        return Optional.ofNullable(itemList.isEmpty() ? null : itemList.get(0));
    }

}
