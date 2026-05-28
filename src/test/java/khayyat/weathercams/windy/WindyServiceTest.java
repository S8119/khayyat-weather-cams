package khayyat.weathercams.windy;

import com.andrewoid.apikeys.ApiKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WindyServiceTest
{

    @Test
    void getResults()
    {
        //given
        ApiKey windyKey = new ApiKey("windy");
        String keyString = windyKey.get();
        WindyService service = new WindyServiceFactory().create();

        //when
        String[] include = {"categories", "images", "location"};

        Results results = service.getResults(
                keyString,
                "40.6966727,-74.3091458,10"
        ).blockingGet();

        //then
        Webcams[] webcams = results.webcams();
        for (Webcams webcam : webcams)
        {
            Images image = webcam.images();
            Current current = image.current();
            assertNotNull(current.preview());
        }
    }
}