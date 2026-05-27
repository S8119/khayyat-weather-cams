package khayyat.weathercams.openweathermap;

import com.google.gson.annotations.SerializedName;

public record Main(Double temp, @SerializedName("feels_like") Double feelsLike)
{
}
