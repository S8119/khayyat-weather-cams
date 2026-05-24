package khayyat.weathercams.openweathermap.geocoding;

import com.andrewoid.apikeys.ApiKey;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GeocodingServiceTest
{

    @Test
    void getCoordinates() throws IOException
    {
        //given
        ApiKey openWeatherMapKey = new ApiKey("openweathermap");
        String keyString = openWeatherMapKey.get();
        GeocodingService service = new GeocodingServiceFactory().create();

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
}