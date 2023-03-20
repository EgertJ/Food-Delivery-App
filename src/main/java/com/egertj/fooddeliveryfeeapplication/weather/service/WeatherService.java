package com.egertj.fooddeliveryfeeapplication.weather.service;

import com.egertj.fooddeliveryfeeapplication.weather.model.WeatherData;
import com.egertj.fooddeliveryfeeapplication.weather.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WeatherService implements CommandLineRunner {
    private final WeatherRepository repository;
    private static final String dataWebsite = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    public WeatherService(WeatherRepository repository) {
        this.repository = repository;
    }

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    /**
     *
     * @return a list of weather data, read from https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php
     */
    public List<WeatherData> readWeatherData(){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        List<WeatherData> dataList = new ArrayList<>();

        try{
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new URL(dataWebsite).openStream());

            doc.getDocumentElement().normalize();

            long timestamp = Long.parseLong(doc.getDocumentElement().getAttribute("timestamp"));

            NodeList list = doc.getElementsByTagName("station");

            for (int i = 0; i < list.getLength(); i++) {

                Node node = list.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;

                    String stationName = element.getElementsByTagName("name").item(0).getTextContent();
                    if(stationName.equals("Tallinn-Harku") || stationName.equals("Tartu-Tõravere") ||stationName.equals("Pärnu")) {
                        String wmoCode = element.getElementsByTagName("wmocode").item(0).getTextContent();
                        if (wmoCode.equals("")) wmoCode = null;
                        String temperature = element.getElementsByTagName("airtemperature").item(0).getTextContent();
                        if (temperature.equals("")) temperature = null;
                        String windSpeed = element.getElementsByTagName("windspeed").item(0).getTextContent();
                        if (windSpeed.equals("")) windSpeed = null;
                        String phenomenon = element.getElementsByTagName("phenomenon").item(0).getTextContent();
                        if (phenomenon.equals("")) phenomenon = null;

                        WeatherData weatherData = new WeatherData();
                        weatherData.setPhenomenon(phenomenon);
                        weatherData.setTimestamp(timestamp);
                        if (temperature != null) {
                            weatherData.setTemperature(Double.parseDouble(temperature));
                        }
                        if (windSpeed != null) {
                            weatherData.setWindSpeed(Double.parseDouble(windSpeed));
                        }
                        weatherData.setStationName(stationName);
                        if (wmoCode != null) {
                            weatherData.setWmoCode(Integer.parseInt(wmoCode));
                        }

                        dataList.add(weatherData);
                    }


                }

            }
        } catch (ParserConfigurationException | SAXException | IOException e){
            logger.error("Error with reading weather data.");
        }

        return dataList;
    }

    /**
     * Adds weather data to the database at the startup.
     */
    @Override
    public void run(String... args) throws Exception{
        if(!Arrays.asList(args).contains("test")) {
            updateWeatherData();
        }
    }

    /**
     * Adds new weather data entry every 15 minutes after a full hour
     */
    @Scheduled(cron = "0 15 * * * *")
    public void updateWeatherData(){
        List<WeatherData> weatherData = readWeatherData();
        repository.saveAll(weatherData);
        logger.info("Saved weather data to the database.");
    }

    /**
     *
     * @return all weather data from database.
     */
    public List<WeatherData> getAllWeatherData(){
        return repository.findAll();
    }

    public List<WeatherData> getWeatherDataByStation(String station) { return repository.findLatestByStation(station);}
}
