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

        final String INITIAL_CITY = "New York";
        final String INITIAL_STATE = "NY";
        final String INITIAL_COUNTRY = "US";

        final JLabel cityLabel = new JLabel("City");
        final JTextField cityField = new JTextField(INITIAL_CITY, 10);

        // State is ignored unless country is US.
        final JLabel stateLabel = new JLabel("State");
        final JTextField stateField = new JTextField(INITIAL_STATE, 2);

        final JLabel countryLabel = new JLabel("Country");
        final JTextField countryField = new JTextField(INITIAL_COUNTRY, 10);

        final JButton searchButton = new JButton("Search");

        final JLabel temperatureLabel = new JLabel("Temperature");
        final JLabel temperatureValueLabel = new JLabel("0");
        final JLabel feelLabel = new JLabel("Feels Like");
        final JLabel feelValueLabel = new JLabel("0");
        final JLabel descriptionLabel = new JLabel("Description");
        final JLabel descriptionValueLabel = new JLabel("0");

        final int MAX_NUM_PICS = 5;

        final JLabel[] picLabels = new JLabel[MAX_NUM_PICS];
        for (int i = 0; i < MAX_NUM_PICS; i++)
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

        addJComponent(cityLabel, 0, 0);
        addJComponent(cityField, 1, 0);
        addJComponent(stateLabel, 0, 1);
        addJComponent(stateField, 1, 1);
        addJComponent(countryLabel, 0, 2);
        addJComponent(countryField, 1, 2);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.NORTH;
        add(searchButton, constraints);

        addJComponent(temperatureLabel, 0, 4);
        addJComponent(temperatureValueLabel, 1, 4);
        addJComponent(feelLabel, 0, 5);
        addJComponent(feelValueLabel, 1, 5);
        addJComponent(descriptionLabel, 0, 6);
        addJComponent(descriptionValueLabel, 1, 6);
        for (int i = 0; i < MAX_NUM_PICS; i++)
        {
            addPicLabel(picLabels[i], 2 + i / 3, (7 * i) % 21);
        }

        //Populate frame with initial values
        controller.doSearch();
    }

    private void addJComponent(JComponent component, int x, int y)
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
