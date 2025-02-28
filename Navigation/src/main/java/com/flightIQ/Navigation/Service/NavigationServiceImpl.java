package com.flightIQ.Navigation.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightIQ.Navigation.Models.Airport;
import com.flightIQ.Navigation.Repository.AirportRepository;

@Service
public class NavigationServiceImpl implements Navigation_svc {
	
	@Autowired
	private AirportRepository airportRepository;

	public void AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }
	
	
    @Override
    public String GetATISOFDestination(String X_coord, String Y_coord, String DestAirportCode) {
        // TODO Auto-generated method stub


        String response = ""
        
        if distance formula (x,y) , DestAirportCode <= 10{


            try{
                //make GET REQ using https://www.geeksforgeeks.org/spring-resttemplate/

                freqAPI_reponse = https://frq-svc-272565453292.us-central1.run.app/api/v1/getAirportFrequencies?airportCode=KPMP

                //parse out ATIS 

                String ATIS_response = //maybe global var in function. 

                response = ATIS_response;
                
            }catch{

                trow exception

            }

        }else{

            response = ""
        }
        return response;
    }
    
    @Override
    public String getAirportFromIdent(String ident) {
    	Airport airport = airportRepository.findByIdent(ident)
    			.orElseThrow(() -> new RuntimeException("Airport does not exist!"));
    	
    	return airport.toString();
    }
    
    
}
