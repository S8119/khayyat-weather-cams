package khayyat.weathercams.openweathermap;

import com.andrewoid.apikeys.ApiKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest
{
    @Test
    void getCoordinates()
    {
        //given
        ApiKey openWeatherMapKey = new ApiKey("openweathermap");
        String keyString = openWeatherMapKey.get();
        WeatherService service = new WeatherServiceFactory().create();

        //when
        Coordinates[] coordinatesList = service.getCoordinates(
                "New York,NY,US",
                keyString
        ).blockingGet();
        Coordinates coordinates = coordinatesList[0];

        //then
        assertEquals(40.6966727, coordinates.lat(), 0.5);
        assertEquals(-74.3091458, coordinates.lon(), 0.5);
    }

    @Test
    void getWeatherData()
    {
        //given
        ApiKey openWeatherMapKey = new ApiKey("openweathermap");
        String keyString = openWeatherMapKey.get();
        WeatherService service = new WeatherServiceFactory().create();

        //when
        WeatherData weatherData = service.getWeatherData(
                "imperial",
                40.6966727,
                -74.3091458,
                keyString
        ).blockingGet();

        //then
        Main main = weatherData.main();
        //assertNotNull, not AssertEquals, since result depends on current weather and is not constant.
        assertNotNull(main.temp());
        assertNotNull(main.feelsLike());

        Weather weather = weatherData.weather()[0];
        //assertNotNull, not AssertEquals, since result depends on current weather and is not constant.
        assertNotNull(weather.description());
    }
}