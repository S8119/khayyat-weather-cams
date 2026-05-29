package khayyat.weathercams;

import khayyat.weathercams.openweathermap.WeatherService;
import khayyat.weathercams.openweathermap.WeatherServiceFactory;
import khayyat.weathercams.windy.WindyService;
import khayyat.weathercams.windy.WindyServiceFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherCamsFrame extends JFrame
{
    public WeatherCamsFrame()
    {
        setSize(1000, 800);
        setTitle("Weather Cams");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());

        final WeatherService weatherService = new WeatherServiceFactory().create();
        final WindyService windyService = new WindyServiceFactory().create();

        final JLabel cityLabel = new JLabel("City");
        final JTextField cityField = new JTextField(10);

        // This application assumes the location is in the United States.
        final JLabel stateLabel = new JLabel("State");
        final JTextField stateField = new JTextField(2);

        final JButton searchButton = new JButton("Search");

        final JLabel temperatureLabel = new JLabel("Temperature");
        final JLabel temperatureValueLabel = new JLabel("0");
        final JLabel feelLabel = new JLabel("Feels Like");
        final JLabel feelValueLabel = new JLabel("0");
        final JLabel descriptionLabel = new JLabel("Description");
        final JLabel descriptionValueLabel = new JLabel("0");

        final WeatherCamsController controller = new WeatherCamsController(
                weatherService, windyService, cityField, stateField,
                temperatureValueLabel, feelValueLabel, descriptionValueLabel);

        searchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.doSearch();
            }
        });

        AddJComponent(cityLabel, 0, 0);
        AddJComponent(cityField, 1, 0);
        AddJComponent(stateLabel, 2, 0);
        AddJComponent(stateField, 3, 0);
        AddJComponent(searchButton, 4, 0);
        AddJComponent(temperatureLabel, 0, 1);
        AddJComponent(temperatureValueLabel, 1, 1);
        AddJComponent(feelLabel, 2, 1);
        AddJComponent(feelValueLabel, 3, 1);
        AddJComponent(descriptionLabel, 4, 1);
        AddJComponent(descriptionValueLabel, 5, 1);
    }

    private void AddJComponent(JComponent component, int x, int y)
    {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.anchor = GridBagConstraints.NORTH;
        add(component, constraints);
    }

    public static void main(String[] args)
    {
        WeatherCamsFrame frame = new WeatherCamsFrame();
        frame.setVisible(true);
    }
}
