package com.agular.hello.service;

import com.agular.hello.DTO.GeolocationServiceResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class GeolocationService {

    private RestTemplate restTemplate = new RestTemplate();

    public GeolocationService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Optional<GeolocationServiceResponse> getCoordinates(String city) {
        String url = "http://api.openweathermap.org/geo/1.0/direct?q={city},Poland&appid=2e0dac5f1c52bce60a0c18ac24834809";
        ResponseEntity<List<GeolocationServiceResponse>> result = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<List<GeolocationServiceResponse>>(){}, city);
        List<GeolocationServiceResponse> itemList = result.getBody();
        return Optional.ofNullable(itemList.isEmpty() ? null : itemList.get(0));
    }

//    public static void main(String[] args) {
//        GeolocationService s = new GeolocationService(null);
//        GeolocationServiceResponse response = s.getCoordinates();
//        GeolocationServiceResponse responsew = s.getCoordinates();
//
//    }
}
