package khayyat.weathercams.openweathermap;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService
{
    @GET("geo/1.0/direct")
    Single<Coordinates[]> getCoordinates(
            @Query("q") String cityStateCountry,
            @Query("appid") String apiKey
    );

    @GET("data/2.5/weather")
    Single<WeatherData> getWeatherData(
            @Query("units") String units,
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("appid") String apiKey
    );
}
