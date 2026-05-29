package khayyat.weathercams;

import com.andrewoid.apikeys.ApiKey;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import khayyat.weathercams.openweathermap.*;
import khayyat.weathercams.windy.WindyService;

import javax.swing.*;

public class WeatherCamsController
{
    private final WeatherService weatherService;
    private final WindyService windyService;

    private final JTextField cityField;
    private final JTextField stateField;

    private final JLabel temperatureValueLabel;
    private final JLabel feelValueLabel;
    private final JLabel descriptionValueLabel;

    private double latitude;
    private double longitude;

    public WeatherCamsController(WeatherService weatherService, WindyService windyService, JTextField cityField,
                                 JTextField stateField, JLabel temperatureValueLabel, JLabel feelValueLabel,
                                 JLabel descriptionValueLabel)
    {
        this.weatherService = weatherService;
        this.windyService = windyService;
        this.cityField = cityField;
        this.stateField = stateField;
        this.temperatureValueLabel = temperatureValueLabel;
        this.feelValueLabel = feelValueLabel;
        this.descriptionValueLabel = descriptionValueLabel;
    }

    public void doSearch()
    {
        String city = cityField.getText();
        String state = stateField.getText();

        ApiKey openWeatherMapKey = new ApiKey("openweathermap");
        String openWeatherMapKeyString = openWeatherMapKey.get();

        Disposable disposableCoordinates = weatherService.getCoordinates(
                city + "," + state + ",US", openWeatherMapKeyString)
                // tells Rx to request the data on a background Thread
                .subscribeOn(Schedulers.io())
                // tells Rx to handle the response on Swing's main Thread
                .observeOn(Schedulers.from(SwingUtilities::invokeLater))
                .subscribe(
                        (this::handleResponse),
                        Throwable::printStackTrace);

        Disposable disposableWeatherData = weatherService.getWeatherData(
                "imperial", latitude, longitude, openWeatherMapKeyString)
                // tells Rx to request the data on a background Thread
                .subscribeOn(Schedulers.io())
                // tells Rx to handle the response on Swing's main Thread
                .observeOn(Schedulers.from(SwingUtilities::invokeLater))
                .subscribe(
                        (this::handleResponse),
                        Throwable::printStackTrace);
    }

    private void handleResponse(Coordinates[] coordinates)
    {
        latitude = coordinates[0].lat();
        longitude = coordinates[0].lon();
    }

    private void handleResponse(WeatherData weatherData)
    {
        Main main = weatherData.main();
        temperatureValueLabel.setText(String.valueOf(main.temp()));
        feelValueLabel.setText(String.valueOf(main.feelsLike()));

        Weather weather = weatherData.weather()[0];
        descriptionValueLabel.setText(weather.description());
    }
}
