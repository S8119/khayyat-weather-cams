package khayyat.weathercams.openweathermap.weather;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService
{
    @GET("data/2.5/weather")
    Single<WeatherData> getWeatherData(
            @Query("units") String units,
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("appid") String apiKey
    );
}
