package com.flightIQ.Navigation.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightIQ.Navigation.models.AccessToken;
import com.flightIQ.Navigation.models.StateVector;


@Service
public class NavigationServiceImpl implements NavigationService {

    // End Points
    private static final String ENDPOINT_OPENSKY = "https://opensky-network.org/api";
    private static final String ENDPOINT_OPENSKY_AUTH = "https://auth.opensky-network.org/auth/realms/opensky-network/protocol/openid-connect/token";

    private static final ObjectMapper OBJ_MAPPER = new ObjectMapper(); // Maps StateVectors from API to a List
    private final RestTemplate _restTemplate = new RestTemplate();
    private final Logger _logger = LoggerFactory.getLogger(NavigationServiceImpl.class);

    private static final float[] US_BOUNDING_BOX = new float[] {24.5f, -125.0f, 49.5f, -66.9f}; // {lamin, lomin, lamax, lomax}

    @Value("${opensky.client-id}")
    private String clientId;

    @Value("${opensky.client-secret}")
    private String clientSecret;

    private AccessToken openskyToken;


    public NavigationServiceImpl() { }


    private void configureAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        
        ResponseEntity<Map> response = _restTemplate.postForEntity(ENDPOINT_OPENSKY_AUTH, request, Map.class);

        // Access Token expires after 30 minutes. So reconfigure after 28 minutes.
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            String accessToken = (String) response.getBody().get("access_token");
            openskyToken = new AccessToken(accessToken, Instant.now().plusSeconds(1680));
        } else {
            _logger.error("configureAccessToken: Failed to obtain AccessToken.");
        }
    }


    private StateVector[] parseRawVectors(String rawJson) {
        try {
            Map<String, Object> map = OBJ_MAPPER.readValue(rawJson, new TypeReference<>() {});
            List<List<Object>> rawStates = (List<List<Object>>) map.get("states");

            if (rawStates == null) return new StateVector[0];
            return rawStates.stream().map(StateVector::fromList).toArray(StateVector[]::new);

        } catch (JsonProcessingException e) {
            _logger.error(">>>Error Processing Raw Vectors<<<", e);
            return null;
        }
    }


    @Override
    public StateVector[] getStateVectors(float lamin, float lomin, float lamax, float lomax) {
        // Check Token
        if (openskyToken == null || openskyToken.expired())
            configureAccessToken();

        // Build URI
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT_OPENSKY+"/states/all");
        uriBuilder.queryParam("lamin", lamin);
        uriBuilder.queryParam("lomin", lomin);
        uriBuilder.queryParam("lamax", lamax);
        uriBuilder.queryParam("lomax", lomax);

        // Build request
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(openskyToken.getToken());
        HttpEntity<Void> request = new HttpEntity<>(headers);

        // Send Endpoint output
        return parseRawVectors(_restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, String.class).getBody());
    }


    @Override
    public StateVector[] getStateVectorsUS() {
        return getStateVectors(US_BOUNDING_BOX[0], US_BOUNDING_BOX[1], US_BOUNDING_BOX[2], US_BOUNDING_BOX[3]);
    }


    // @Override
    // public StateVector[] getStateVectors(int time, String icao24) {
    //     // Check Auth Token
    //     if (openskyToken == null || openskyToken.expired()) 
    //         configureAccessToken();
        
    //     // Build URI
    //     UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT_OPENSKY+"/states/all");
    //     if (time > 0) { 
    //         uriBuilder.queryParam("time", time);
    //     }
    //     if (icao24 != null) {
    //         uriBuilder.queryParam("icao24", icao24);
    //     }

    //     // Build Request
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setBearerAuth(openskyToken.getToken());
    //     HttpEntity<Void> request = new HttpEntity<>(headers);

    //     // Send Endpoint output
    //     return parseRawVectors(_restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, String.class).getBody());
    // }


    // @Override
    // public AirportFrequencies getAirportFrequencies(String ICAO) {
    //     String url = ENDPOINT_APFREQ+"?airportCode="+ICAO;
    //     try {
    //         String response = _restTemplate.getForObject(url, String.class);
    //         Map<String, Float> map = OBJ_MAPPER.readValue(response, new TypeReference<Map<String, Float>>() {});
    //         AirportFrequencies ff = new AirportFrequencies(map);
    //         return ff;

    //     } catch(RestClientException | JsonProcessingException e) {
    //         _logger.error("getAirportFrequencies: Failed to fetch.", e.getMessage());
    //         return new AirportFrequencies();
    //     }
    // }


    // @Override
    // public String GetATISOFDestination(String X_coord, String Y_coord, String DestAirportCode) {
    //     String response = ""
    //     if distance formula (x,y) , DestAirportCode <= 10{
    //         try{
    //             //make GET REQ using https://www.geeksforgeeks.org/spring-resttemplate/
    //             freqAPI_reponse = https://frq-svc-272565453292.us-central1.run.app/api/v1/getAirportFrequencies?airportCode=KPMP
    //             //parse out ATIS 
    //             String ATIS_response = //maybe global var in function. 
    //             response = ATIS_response;
    //         }catch{
    //             trow exception
    //         }
    //     }else{
    //         response = ""
    //     }
    //     return response;
    // }
}