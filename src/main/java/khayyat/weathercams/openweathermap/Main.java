package khayyat.weathercams.openweathermap;

import com.google.gson.annotations.SerializedName;

public record Main(double temp, @SerializedName("feels_like") double feelsLike)
{
}
