package com.altimetrik.usecase.proxy;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.altimetrik.usecase.model.CovidTrackingDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CovidTrackingProxy {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    public List<CovidTrackingDto> getCovidTrackingData() {
        String url = environment.getProperty("covid.rest.service.url");
        return getCovidTrackingRequest(url);
    }

    private List<CovidTrackingDto> getCovidTrackingRequest(String url) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return mapper.readValue(response.getBody(), mapper.getTypeFactory().constructCollectionType(List.class,
                                                                                                        CovidTrackingDto.class));
        } catch (final HttpServerErrorException | IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

}
