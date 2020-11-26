package station;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = {"station"})
public class StationApplication {
    private static final Logger logger = LoggerFactory.getLogger(StationApplication.class);

//    @Autowired
//    private Station station;
//
//    @Autowired
//    private SendService sendService;

//    @Autowired
//    Optional<CameraService> cameraServiceOptional;

//    public static void main(String[] args) {
//        new SpringApplicationBuilder(StationApplication.class)
//                .web(WebApplicationType.NONE)
//                .run(args);
//    }

//    @Scheduled(fixedRateString = "${station.reading-interval-secs:60000}", initialDelay = 0)
//    public void collectAndReport() {
//
//        List<Reading> readings = station.readAllSensors();
//
//        try {
//            sendService.send(new StationReadings(station.getUUID(), readings));
//        } catch (Exception e) {
//            logger.error("Exception occurred sending readings", e);
//        }
//    }
    
    public static void main(String[] args) {
        new SpringApplicationBuilder(StationApplication.class).run(args);
    }

}
