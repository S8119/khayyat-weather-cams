package khayyat.weathercams.windy;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface WindyService
{
    final int LIMIT = 5;
    final String INCLUDE = "categories,images,location";

    @GET("webcams/api/v3/webcams?limit=" + LIMIT + "&include=" + INCLUDE)
    Single<Results> getResults(
            @Header("x-windy-api-key") String apikey,
            @Query("nearby") String nearby
    );
}
