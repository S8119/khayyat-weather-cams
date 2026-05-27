package khayyat.weathercams.windy;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface WindyService
{
    @GET("webcams/api/v3/webcams")
    Single<Results> getResults(
            @Header("x-windy-api-key") String apikey,
            @Query("limit") int limit,
            @Query("nearby") String nearby,
            @Query("include") String[] include
    );
}
