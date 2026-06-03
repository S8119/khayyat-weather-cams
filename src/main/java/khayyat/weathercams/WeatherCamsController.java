package khayyat.weathercams;

import com.andrewoid.apikeys.ApiKey;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import khayyat.weathercams.openweathermap.*;
import khayyat.weathercams.windy.*;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherCamsController
{
    private final WeatherService weatherService;
    private final WindyService windyService;

    private final JTextField cityField;
    private final JTextField stateField;
    private final JTextField countryField;

    private final JLabel temperatureValueLabel;
    private final JLabel feelValueLabel;
    private final JLabel descriptionValueLabel;

    private final JLabel[] picLabels;

    private double latitude;
    private double longitude;
    private final int RADIUS = 10;

    public WeatherCamsController(WeatherService weatherService, WindyService windyService, JTextField cityField,
                                 JTextField stateField, JTextField countryField, JLabel temperatureValueLabel,
                                 JLabel feelValueLabel, JLabel descriptionValueLabel, JLabel[] picLabels)
    {
        this.weatherService = weatherService;
        this.windyService = windyService;
        this.cityField = cityField;
        this.stateField = stateField;
        this.countryField = countryField;
        this.temperatureValueLabel = temperatureValueLabel;
        this.feelValueLabel = feelValueLabel;
        this.descriptionValueLabel = descriptionValueLabel;
        this.picLabels = picLabels;
    }

    public void doSearch()
    {
        String city = cityField.getText();
        String country = countryField.getText();
        String state = country.equals("US") ? stateField.getText() : "";

        ApiKey openWeatherMapKey = new ApiKey("openweathermap");
        String openWeatherMapKeyString = openWeatherMapKey.get();

        Disposable disposableCoordinates = weatherService.getCoordinates(
                city + "," + state + "," + country, openWeatherMapKeyString)
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

        ApiKey windyKey = new ApiKey("windy");
        String windyKeyString = windyKey.get();

        Disposable disposableWindyResults = windyService.getResults(
                        windyKeyString, latitude + "," + longitude + "," + RADIUS)
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

    private void handleResponse(Results results)
    {
        Webcams[] webcams = results.webcams();
        for (int i = 0; i < webcams.length; i++)
        {
                try
                {
                    Images images = webcams[i].images();
                    Current current = images.current();
                    ImageIcon imageIcon = new ImageIcon(new URL(current.preview()));
                    picLabels[i].setIcon(imageIcon);
                }
                catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
        }
    }
}
