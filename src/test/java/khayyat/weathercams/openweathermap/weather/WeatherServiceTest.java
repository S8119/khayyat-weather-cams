package khayyat.weathercams.openweathermap.weather;

import com.andrewoid.apikeys.ApiKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest
{

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
        assertNotNull(main.feels_like());

        Weather weather = weatherData.weather()[0];
        //assertNotNull, not AssertEquals, since result depends on current weather and is not constant.
        assertNotNull(weather.description());
    }
}