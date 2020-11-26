package station;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reading.Reading;

@RestController
public class StationController {
	private static final Logger logger = LoggerFactory.getLogger(StationController.class);

    @Autowired
    private Station station;

//    @Autowired
//    private SendService sendService;
    
    @GetMapping("/station")
    public List<Reading> retrieveAllSensors() {
    	logger.info("/station");
    	List<Reading> readings = station.readAllSensors();
    	return readings;
//        try {
//            sendService.send(new StationReadings(station.getUUID(), readings));
//        } catch (Exception e) {
//            logger.error("Exception occurred sending readings", e);
//        }
    }

}