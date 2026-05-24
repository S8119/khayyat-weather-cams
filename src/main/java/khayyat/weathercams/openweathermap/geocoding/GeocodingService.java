package khayyat.weathercams.openweathermap.geocoding;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingService
{
    @GET("geo/1.0/direct")
    Single<Coordinates[]> getCoordinates(
            @Query("q") String cityStateCountry,
            @Query("appid") String apiKey
    );
}