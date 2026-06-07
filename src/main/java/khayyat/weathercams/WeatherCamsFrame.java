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

        final String initialCity = "New York";
        final String initialState = "NY";
        final String initialCountry = "US";

        final JLabel cityLabel = new JLabel("City");
        final JTextField cityField = new JTextField(initialCity, 10);

        // State is ignored unless country is US.
        final JLabel stateLabel = new JLabel("State");
        final JTextField stateField = new JTextField(initialState, 2);

        final JLabel countryLabel = new JLabel("Country");
        final JTextField countryField = new JTextField(initialCountry, 10);

        final JButton searchButton = new JButton("Search");

        final JLabel temperatureLabel = new JLabel("Temperature");
        final JLabel temperatureValueLabel = new JLabel("0");
        final JLabel feelLabel = new JLabel("Feels Like");
        final JLabel feelValueLabel = new JLabel("0");
        final JLabel descriptionLabel = new JLabel("Description");
        final JLabel descriptionValueLabel = new JLabel("0");

        final int maxNumPics = 5;

        final JLabel[] picLabels = new JLabel[maxNumPics];
        for (int i = 0; i < maxNumPics; i++)
        {
            picLabels[i] = new JLabel();
        }

        final WeatherCamsController controller = new WeatherCamsController(
                weatherService, windyService, cityField, stateField, countryField,
                temperatureValueLabel, feelValueLabel, descriptionValueLabel, picLabels);

        searchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.doSearch();
            }
        });

        addComponent(cityLabel, 0, 0);
        addComponent(cityField, 1, 0);
        addComponent(stateLabel, 0, 1);
        addComponent(stateField, 1, 1);
        addComponent(countryLabel, 0, 2);
        addComponent(countryField, 1, 2);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.NORTH;
        add(searchButton, constraints);

        addComponent(temperatureLabel, 0, 4);
        addComponent(temperatureValueLabel, 1, 4);
        addComponent(feelLabel, 0, 5);
        addComponent(feelValueLabel, 1, 5);
        addComponent(descriptionLabel, 0, 6);
        addComponent(descriptionValueLabel, 1, 6);
        for (int i = 0; i < maxNumPics; i++)
        {
            addPicLabel(picLabels[i], 2 + i / 3, (7 * i) % 21);
        }

        //Populate frame with initial values
        controller.doSearch();
    }

    private void addComponent(JComponent component, int x, int y)
    {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.anchor = GridBagConstraints.NORTH;
        add(component, constraints);
    }

    private void addPicLabel(JComponent picLabel, int x, int y)
    {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridheight = 7;
        constraints.anchor = GridBagConstraints.NORTH;
        add(picLabel, constraints);
    }

    public static void main(String[] args)
    {
        WeatherCamsFrame frame = new WeatherCamsFrame();
        frame.setVisible(true);
    }
}
