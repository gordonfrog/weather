package station.sensor;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import reading.Reading;
import reading.ReadingUnits;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
//@ConditionalOnProperty(value = "station.temperature.enabled", havingValue = "true")
public class TemperatureHumiditySensor implements Sensor {
    private static final Logger logger = LoggerFactory.getLogger(TemperatureHumiditySensor.class);

    //private I2CDevice i2CDevice;

    //private static final String HUMIDITY_READING_NAME = "Humidity";
    private static final String TEMPERATURE_READING_NAME = "Temperature";


    private static final byte DEVICE_I2C_ADDR = 0x40;  //SHT020 I2C address is 0x40

    public TemperatureHumiditySensor() throws IOException, I2CFactory.UnsupportedBusNumberException {
        //I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
        //i2CDevice = bus.getDevice(DEVICE_I2C_ADDR);
    }

    @Override
    public List<Reading> read() {
        List<Reading> readings = new ArrayList<>();

        ZonedDateTime readingTime = ZonedDateTime.now();

//        try{
//            double humidity = readHumidity();
//            readings.add(new Reading(HUMIDITY_READING_NAME, humidity, ReadingUnits.RELATIVE_HUMIDITY, readingTime));
//        }catch (IOException e){
//            logger.error("Problem reading humidity", e);
//        }

        try{
            double temperature = readTemperatureF();
            readings.add(new Reading(TEMPERATURE_READING_NAME, temperature, ReadingUnits.FARENHEIT, readingTime));
        }catch (IOException e){
            logger.error("Problem reading temperature", e);
        }

        return readings;
    }


    private double readTemperatureF() throws IOException{
        byte[] data = new byte[2];
/*
        // Send temperature measurement command, NO HOLD MASTER
        i2CDevice.write((byte) 0xF3);
        sleep();

        // Read 2 bytes of temperature data
        // temp msb, temp lsb
        i2CDevice.read(data, 0, 2);
*/
       

        File file = new File("/sys/class/thermal/thermal_zone0/temp");
		final String filename = file.getName();
		logger.info("awk '$1/1000' /sys/class/thermal/thermal_zone0/temp");
		logger.info("awk '$1/1000 * (9 / 5.0f) + 32' /sys/class/thermal/thermal_zone0/temp");
		logger.info("/usr/local/bin/rpi-temp");
		logger.info("* filename: "+filename+" *");
		float tempC;
		try {
			tempC = readTempC(filename);
			logger.info("tempC: "+tempC);
			final float tempF = ((tempC * (9 / 5.0f)) + 32);
			logger.info("tempF: "+tempF);
			return tempF;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
    }
    
    private float readTempC(final String location) throws Exception {
		final String line = FileUtils.readLines(new File(location)).get(1);
		final String tempEqual = line.split(" ")[9];
		final int temp = Integer.parseInt(tempEqual.substring(2));
		return temp / 1000f;
	}

//    private double readHumidity() throws IOException{
//        // Send humidity measurement command, NO HOLD MASTER
//        i2CDevice.write((byte) 0xF5);
//        sleep();
//        // Read 2 bytes of humidity data
//        // humidity msb, humidity lsb
//        byte[] data = new byte[2];
//        i2CDevice.read(data, 0, 2);
//
//        // Convert the data
//        return (((((data[0] & 0xFF) * 256.0) + (data[1] & 0xFF)) * 125.0) / 65536.0) - 6;
//    }

    private void sleep(){
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            logger.error("Exception while sleeping", e);
        }
    }
}
