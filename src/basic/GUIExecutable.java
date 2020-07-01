package basic;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import database.DatabaseDriver;
import database.RandomCitiesChooser;
import exception.WikipediaNoArcticleException;
import javafx.util.Pair;

import java.awt.Font;
import java.awt.Color;

import javax.swing.SpinnerNumberModel;
import javax.swing.ScrollPaneConstants;

public class GUIExecutable {

    private static JTextField textField;
    private static JSpinner textField_1;
    private static JTextField textField_2;
    private static JTextField textField_3;
    static String travellerType;
    static String travellerName;
    static int travellerAge;
    static JTextField textField_4;
    static String cityContainer;
    static int rowCount = 0;
    static boolean found = false;

    //initializing handler for passing the cities to db
    static Handler handler = new Handler();
    // Arraylist for loading cities from Database
    static ArrayList<City> storedCities = new ArrayList<City>();
    static ArrayList<City> cityList = new ArrayList<City>();
    static ArrayList<City> cityList2 = new ArrayList<City>();
    static City wantedCity = new City();
    // Container for cities that are not in the Database
    static ArrayList<City> tempCities = new ArrayList<City>();
    // Arraylist for retrieving and showing Travellers from file
    static ArrayList<Traveller> travellersFromFile = new ArrayList<Traveller>();
    // Arraylist for loading Travellers from file
    static ArrayList<Traveller> storedTravellers = new ArrayList<Traveller>();

    
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws FileNotFoundException {
        // Initialize cities from which to randomly select
        RandomCitiesChooser.InitializeCities();
        
        // connection to the city DataBase and check for cities table
        final DatabaseDriver databaseDriver = new DatabaseDriver();
        databaseDriver.dbConnect();
        // ------Loading Cities from database-------
        storedCities = databaseDriver.retrieveCities();
        // --------extracting traveller count from file--------
        Traveller.setTravCount(FileWriterReader.retrieveCount());
        // --------loading the traveller List from File--------
        storedTravellers = FileWriterReader.RetrieveTravellers(Traveller.getTravCount());

//---------------------GUI stuff here-----------------------------------------------------------------
        JFrame f = new JFrame("Travel Agency App");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
        f.getContentPane().setForeground(Color.BLACK);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screenSize.width, screenSize.height);

        JLabel lblNewLabel = new JLabel("Hello User! Please select  travel type from below:");
        final JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[]{"Simple", "Tourism", "Bussines"}));
        comboBox.setMaximumRowCount(3);

        final JLabel lblNewLabel_1 = new JLabel("Enter your credentials");
        lblNewLabel_1.setEnabled(false);
        final JButton btnNewButton = new JButton("Next");
        final JLabel lblNewLabel_2 = new JLabel("Name:");
        lblNewLabel_2.setEnabled(false);

        textField = new JTextField();
        textField.setEnabled(false);
        textField.setColumns(10);

        final JLabel lblNewLabel_3 = new JLabel("Age:");
        lblNewLabel_3.setEnabled(false);

        textField_1 = new JSpinner();
        textField_1.setModel(new SpinnerNumberModel(18, 18, 100, 1));
        textField_1.setEnabled(false);

        final JButton btnNewButton_1 = new JButton("Next");
        btnNewButton_1.setEnabled(false);

        final JLabel lblNewLabel_4 = new JLabel("Fill the criteria according to your liking:");
        lblNewLabel_4.setEnabled(false);

        final JLabel lblNewLabel_5 = new JLabel("Museums:");
        lblNewLabel_5.setEnabled(false);

        final JLabel lblNewLabel_6 = new JLabel("Bars:");
        lblNewLabel_6.setEnabled(false);

        final JLabel lblNewLabel_7 = new JLabel("Cafeterias:");
        lblNewLabel_7.setEnabled(false);

        final JLabel lblNewLabel_8 = new JLabel("Restaurants:");
        lblNewLabel_8.setEnabled(false);

        final JLabel lblNewLabel_9 = new JLabel("Beaches:");
        lblNewLabel_9.setEnabled(false);

        final JSpinner spinner = new JSpinner();
        spinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
        spinner.setEnabled(false);

        final JSpinner spinner_1 = new JSpinner();
        spinner_1.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
        spinner_1.setEnabled(false);

        final JSpinner spinner_2 = new JSpinner();
        spinner_2.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
        spinner_2.setEnabled(false);

        final JSpinner spinner_3 = new JSpinner();
        spinner_3.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
        spinner_3.setEnabled(false);

        final JSpinner spinner_4 = new JSpinner();
        spinner_4.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
        spinner_4.setEnabled(false);

        final JLabel lblNewLabel_10 = new JLabel("Current Latitude:");
        lblNewLabel_10.setEnabled(false);

        final JLabel lblNewLabel_11 = new JLabel("Current Longitude:");
        lblNewLabel_11.setEnabled(false);

        textField_2 = new JTextField();
        textField_2.setEnabled(false);
        textField_2.setColumns(10);

        textField_3 = new JTextField();
        textField_3.setEnabled(false);
        textField_3.setColumns(10);

        final JCheckBox chckbxNewCheckBox = new JCheckBox("Exclude cities currently raining");
        chckbxNewCheckBox.setEnabled(false);

        final JButton btnNewButton_2 = new JButton("Next");

        btnNewButton_2.setEnabled(false);

        final JLabel lblNewLabel_12 = new JLabel(
                "Enter the cities you're interested in, followed by a ',' and its country abrreviation:\r\n\r\n");
        lblNewLabel_12.setEnabled(false);

        final JLabel lblNewLabel_15 = new JLabel("-Example: Athens,GR");
        lblNewLabel_15.setEnabled(false);
        lblNewLabel_15.setFont(new Font("Tahoma", Font.ITALIC, 10));

        final JButton btnFinalize = new JButton("Finalize");
        btnFinalize.setEnabled(false);

        final JButton btnRandomChoice = new JButton("Randomly Select");
        btnRandomChoice.setEnabled(false);

        textField_4 = new JTextField();
        textField_4.setEnabled(false);
        textField_4.setColumns(10);

        final JButton btnAddCity = new JButton("Add");
        btnAddCity.setEnabled(false);

        final DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Cities Added");

        final DefaultTableModel tableModel_1 = new DefaultTableModel();
        tableModel_1.addColumn("name");
        tableModel_1.addColumn("age");

        final JLabel lblNewLabel_13 = new JLabel("result is displayed here");
        lblNewLabel_13.setForeground(new Color(0, 128, 0));
        lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
        lblNewLabel_13.setVisible(false);

        JLabel lblNewLabel_14 = new JLabel("Traveller List sorted by age");

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JScrollPane scrollPane_1 = new JScrollPane();

        final JLabel lblNewLabel_16 = new JLabel("Wrong(lat,lon) input, Try Again");
        lblNewLabel_16.setForeground(Color.RED);
        lblNewLabel_16.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblNewLabel_16.setVisible(false);

        final JLabel lblNewLabel_17 = new JLabel("Wrong input format, try again");
        lblNewLabel_17.setForeground(Color.RED);
        lblNewLabel_17.setVisible(false);

        final JLabel lblNewLabel_18 = new JLabel("One of the cities you enetered was not found");
        lblNewLabel_18.setForeground(Color.RED);
        lblNewLabel_18.setVisible(false);

        final JLabel lblNewLabel_19 = new JLabel("Dont leave the name field blank");
        lblNewLabel_19.setForeground(Color.RED);
        lblNewLabel_19.setVisible(false);

        GroupLayout groupLayout = new GroupLayout(f.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(6)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblNewLabel_4)
                                        .addComponent(lblNewLabel_1)
                                        .addComponent(lblNewLabel)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnNewButton))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addComponent(lblNewLabel_2)
                                                                .addGap(4)
                                                                .addComponent(textField, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addComponent(lblNewLabel_5)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(spinner, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                .addComponent(lblNewLabel_7)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGap(4)
                                                                .addComponent(lblNewLabel_3)
                                                                .addGap(4)
                                                                .addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(10)
                                                                .addComponent(btnNewButton_1)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(lblNewLabel_19))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                .addComponent(lblNewLabel_6)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                                .addGap(4)
                                                                                .addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                                .addComponent(chckbxNewCheckBox)
                                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                                .addComponent(btnNewButton_2))
                                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                                .addComponent(lblNewLabel_8)
                                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                                .addComponent(spinner_4, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                                .addComponent(lblNewLabel_9)
                                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                                .addComponent(spinner_3, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                                .addComponent(lblNewLabel_16, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE))))))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(lblNewLabel_11)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(lblNewLabel_10))
                                        .addComponent(lblNewLabel_15, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNewLabel_12)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(lblNewLabel_18, GroupLayout.PREFERRED_SIZE, 354, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblNewLabel_13, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnAddCity)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnFinalize)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnRandomChoice)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(lblNewLabel_17, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)))
                                .addGap(65)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(126)
                                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(185)
                                                .addComponent(lblNewLabel_14)))
                                .addGap(284))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(lblNewLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnNewButton))
                                                .addGap(12)
                                                .addComponent(lblNewLabel_1)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGap(4)
                                                                .addComponent(lblNewLabel_2))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGap(1)
                                                                .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGap(4)
                                                                .addComponent(lblNewLabel_3))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGap(1)
                                                                .addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                                .addComponent(btnNewButton_1)
                                                                .addComponent(lblNewLabel_19)))
                                                .addGap(18)
                                                .addComponent(lblNewLabel_4)
                                                .addGap(9)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGap(3)
                                                                .addComponent(lblNewLabel_5))
                                                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                                .addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(lblNewLabel_7)
                                                                .addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(lblNewLabel_6)
                                                                .addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(lblNewLabel_8)
                                                                .addComponent(spinner_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(lblNewLabel_9)
                                                                .addComponent(spinner_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(lblNewLabel_16)))
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(lblNewLabel_11)
                                                        .addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblNewLabel_10)
                                                        .addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(chckbxNewCheckBox)
                                                        .addComponent(btnNewButton_2))
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(lblNewLabel_12)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(lblNewLabel_15)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnAddCity)
                                                        .addComponent(btnFinalize)
                                                        .addComponent(btnRandomChoice)
                                                        .addComponent(lblNewLabel_17))
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addComponent(lblNewLabel_13)
                                                                .addGap(2)
                                                                .addComponent(lblNewLabel_18))))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(26)
                                                .addComponent(lblNewLabel_14)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(309, Short.MAX_VALUE))
        );

        final JTable table = new JTable(tableModel);
        scrollPane_1.setViewportView(table);
        JTable table_1_1 = new JTable(tableModel_1);
        scrollPane.setViewportView(table_1_1);
        f.getContentPane().setLayout(groupLayout);
        f.setVisible(true);

        // we lock the type of traveller the user chose
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // enabling the next step for the user
                comboBox.setEnabled(false);
                btnNewButton.setEnabled(false);
                lblNewLabel_1.setEnabled(true);
                lblNewLabel_2.setEnabled(true);
                textField.setEnabled(true);
                lblNewLabel_3.setEnabled(true);
                textField_1.setEnabled(true);
                btnNewButton_1.setEnabled(true);
            }
        });

        // we lock the name and age of the traveller
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //name not null validation
                if (textField.getText().isEmpty()) {
                    lblNewLabel_19.setVisible(true);
                } else {
                    // enabling the next step for the user
                    lblNewLabel_19.setVisible(false);
                    lblNewLabel_1.setEnabled(false);
                    lblNewLabel_2.setEnabled(false);
                    textField.setEnabled(false);
                    lblNewLabel_3.setEnabled(false);
                    textField_1.setEnabled(false);
                    btnNewButton_1.setEnabled(false);
                    lblNewLabel_5.setEnabled(true);
                    lblNewLabel_6.setEnabled(true);
                    lblNewLabel_7.setEnabled(true);
                    lblNewLabel_8.setEnabled(true);
                    lblNewLabel_9.setEnabled(true);
                    lblNewLabel_10.setEnabled(true);
                    lblNewLabel_11.setEnabled(true);
                    spinner.setEnabled(true);
                    spinner_1.setEnabled(true);
                    spinner_2.setEnabled(true);
                    spinner_3.setEnabled(true);
                    spinner_4.setEnabled(true);
                    textField_2.setEnabled(true);
                    textField_3.setEnabled(true);
                    chckbxNewCheckBox.setEnabled(true);
                    btnNewButton_2.setEnabled(true);
                    lblNewLabel_4.setEnabled(true);
                }
            }
        });
        // we lock users criteria
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //we perform coordination validation
                boolean notCoord = false;
                String validlat = textField_2.getText();
                String validlon = textField_3.getText();

                try {
                    double islat = Double.parseDouble(validlat);
                    double islon = Double.parseDouble(validlon);
                } catch (Exception e1) {
                    notCoord = true;
                }
                if (notCoord) {
                    lblNewLabel_16.setVisible(true);
                } else {
                    // we enable the next step for the user
                    lblNewLabel_16.setVisible(false);
                    lblNewLabel_5.setEnabled(false);
                    lblNewLabel_6.setEnabled(false);
                    lblNewLabel_7.setEnabled(false);
                    lblNewLabel_8.setEnabled(false);
                    lblNewLabel_9.setEnabled(false);
                    lblNewLabel_10.setEnabled(false);
                    lblNewLabel_11.setEnabled(false);
                    spinner.setEnabled(false);
                    spinner_1.setEnabled(false);
                    spinner_2.setEnabled(false);
                    spinner_3.setEnabled(false);
                    spinner_4.setEnabled(false);
                    textField_2.setEnabled(false);
                    textField_3.setEnabled(false);
                    chckbxNewCheckBox.setEnabled(false);
                    btnNewButton_2.setEnabled(false);
                    lblNewLabel_4.setEnabled(false);
                    lblNewLabel_12.setEnabled(true);
                    lblNewLabel_15.setEnabled(true);
                    textField_4.setEnabled(true);
                    btnAddCity.setEnabled(true);
                    btnRandomChoice.setEnabled(true);
                }
            }
        });

        // we add the input cities to a Jtable
        btnAddCity.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //validating input format
                cityContainer = textField_4.getText();
                if (!Pattern.matches("[A-Za-z]+,[A-Za-z]+", cityContainer)) {
                    lblNewLabel_17.setVisible(true);
                } else {
                    lblNewLabel_17.setVisible(false);
                    table.setEnabled(true);
                    tableModel.insertRow(0, new Object[]{cityContainer});
                    textField_4.setText("");
                    btnFinalize.setEnabled(true);
                }
            }
        });

        // we add the input cities to a Jtable
        btnRandomChoice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                RandomCitiesChooser.UserPressedRandomButton();
                
                System.out.println("Prrrrrrrrrrrrrrrrrr");
                
                // TODO: Here, select two cities from the text file
                Pair<String, String> pairRandomCities = RandomCitiesChooser.selectTwoRandomCities();

                lblNewLabel_17.setVisible(false);
                table.setEnabled(true);
                if(tableModel.getRowCount() > 0) {
                    tableModel.removeRow(1);
                    tableModel.removeRow(0);
                }
                tableModel.insertRow(0, new Object[]{pairRandomCities.getKey()});
                tableModel.insertRow(1, new Object[]{pairRandomCities.getValue()});
                textField_4.setText("Randomly selected cities");
                btnFinalize.setEnabled(true);
            }
        });

        // we lock city selection from user and we show the best city according to his
        // type
        btnFinalize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RandomCitiesChooser.produceFinalResults(textField.getText());
                
                lblNewLabel_12.setEnabled(false);
                lblNewLabel_15.setEnabled(false);
                textField_4.setEnabled(false);
                btnAddCity.setEnabled(false);
                table.setEnabled(false);
                btnFinalize.setEnabled(false);

                // we create the travellers object
                travellerType = comboBox.getSelectedItem().toString();
                travellerName = textField.getText();
                travellerAge = (Integer) textField_1.getValue();

                if (travellerType.equals("Simple")) {
                    Traveller selectedTraveller = new Traveller();
                    System.out.println("traveller object created");
                    selectedTraveller.setName(travellerName);
                    System.out.println(selectedTraveller.getName());
                    selectedTraveller.setAge(travellerAge);
                    System.out.println(selectedTraveller.getAge());
                    selectedTraveller.setCmuseums((Integer) spinner.getValue());
                    System.out.println(selectedTraveller.getCmuseums());
                    selectedTraveller.setCcafes((Integer) spinner_1.getValue());
                    System.out.println(selectedTraveller.getCcafes());
                    selectedTraveller.setCbars((Integer) spinner_2.getValue());
                    System.out.println(selectedTraveller.getCbars());
                    selectedTraveller.setCbeaches((Integer) spinner_3.getValue());
                    System.out.println(selectedTraveller.getCbeaches());
                    selectedTraveller.setCrestaurants((Integer) spinner_4.getValue());
                    System.out.println(selectedTraveller.getCrestaurants());
                    selectedTraveller.setClat(Double.parseDouble(textField_2.getText()));
                    System.out.println(selectedTraveller.getClat());
                    selectedTraveller.setClon(Double.parseDouble(textField_3.getText()));
                    System.out.println(selectedTraveller.getClon());
                    selectedTraveller.setCweather(chckbxNewCheckBox.isSelected());

                    rowCount = table.getRowCount();
                    for (int i = 0; i < rowCount; i++) {
                        wantedCity = new City();
                        wantedCity.setName(table.getValueAt(i, 0).toString());
                        cityList.add(wantedCity);
                    }

                    for (City iteratedCity : cityList) {
                        for (City iteratedCity2 : storedCities)
                            if (iteratedCity.equals(iteratedCity2)) {
                                cityList2.add(iteratedCity2);
                                found = true;
                            }
                        if (!found) {
                            try {
                                cityList2.add(iteratedCity.fillCity(iteratedCity));
                            } catch (JsonParseException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (JsonMappingException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (WikipediaNoArcticleException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (com.sun.jersey.api.client.UniformInterfaceException e1) {
                                lblNewLabel_18.setVisible(true);
                            }
                        }
                    }
                    // here we compare the cities to the Tourist traveller criteria(multiple
                    // appearance of criteria)
                    City bestCity = new City();
                    bestCity = selectedTraveller.CompareCities(cityList2, selectedTraveller, selectedTraveller.isCweather());
                    //we display the results to the gui
                    lblNewLabel_13.setText("the city that matches your criteria the most is: " + bestCity.getName());
                    lblNewLabel_13.setVisible(true);

                    // we store the cities that we dont have in the database
                    tempCities = handler.getCityList3(storedCities);
                    if (tempCities != null) {
                        for (City c : tempCities) {
                            databaseDriver.insertCities(c);
                        }
                    }
                    //storing the traveller to the list
                    storedTravellers.add(selectedTraveller);
                    // incrementing the number of travellers by 1
                    Traveller.travInc();
                    // saving the new count on the file (overwrite)
                    FileWriterReader.saveCount(Traveller.getTravCount());
                    // saving the new traveller list on a file
                    FileWriterReader.saveTravellers(storedTravellers);


                    //we show the traveller list in gui
                    travellersFromFile = FileWriterReader.RetrieveTravellers(Traveller.getTravCount());
                    Collections.sort(travellersFromFile);
                    String previousName = "";
                    for (Traveller printTraveller : travellersFromFile) {
                        if (!previousName.equals(printTraveller.getName())) {
                            //System.out.println(printTraveller.getName() + " Age: " + printTraveller.getAge());
                            tableModel_1.insertRow(0, new Object[]{printTraveller.getName(), printTraveller.getAge()});
                            //,printTraveller.getAge()
                        }
                        previousName = printTraveller.getName();
                    }


//----------------------------------------------------------------------------------------------------------------------------------------------------
                } else if (travellerType.equals("Tourism")) {
                    Tourist selectedTraveller = new Tourist();
                    System.out.println("tourist object created");
                    selectedTraveller.setName(travellerName);
                    System.out.println(selectedTraveller.getName());
                    selectedTraveller.setAge(travellerAge);
                    System.out.println(selectedTraveller.getAge());
                    selectedTraveller.setCmuseums((Integer) spinner.getValue());
                    System.out.println(selectedTraveller.getCmuseums());
                    selectedTraveller.setCcafes((Integer) spinner_1.getValue());
                    System.out.println(selectedTraveller.getCcafes());
                    selectedTraveller.setCbars((Integer) spinner_2.getValue());
                    System.out.println(selectedTraveller.getCbars());
                    selectedTraveller.setCbeaches((Integer) spinner_3.getValue());
                    System.out.println(selectedTraveller.getCbeaches());
                    selectedTraveller.setCrestaurants((Integer) spinner_4.getValue());
                    System.out.println(selectedTraveller.getCrestaurants());
                    selectedTraveller.setClat(Double.parseDouble(textField_2.getText()));
                    System.out.println(selectedTraveller.getClat());
                    selectedTraveller.setClon(Double.parseDouble(textField_3.getText()));
                    System.out.println(selectedTraveller.getClon());
                    selectedTraveller.setCweather(chckbxNewCheckBox.isSelected());

                    rowCount = table.getRowCount();
                    for (int i = 0; i < rowCount; i++) {
                        wantedCity = new City();
                        wantedCity.setName(table.getValueAt(i, 0).toString());
                        cityList.add(wantedCity);
                    }

                    for (City iteratedCity : cityList) {
                        for (City iteratedCity2 : storedCities)
                            if (iteratedCity.equals(iteratedCity2)) {
                                cityList2.add(iteratedCity2);
                                found = true;
                            }
                        if (!found) {
                            try {
                                cityList2.add(iteratedCity.fillCity(iteratedCity));
                            } catch (JsonParseException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (JsonMappingException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (WikipediaNoArcticleException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (com.sun.jersey.api.client.UniformInterfaceException e1) {
                                lblNewLabel_18.setVisible(true);
                            }
                        }
                    }
                    // here we compare the cities to the Tourist traveller criteria(multiple
                    // appearance of criteria)
                    City bestCity = new City();
                    bestCity = selectedTraveller.CompareCities(cityList2, selectedTraveller, selectedTraveller.isCweather());
                    //we display the results to the gui
                    lblNewLabel_13.setText("the city that matches your criteria the most is: " + bestCity.getName());
                    lblNewLabel_13.setVisible(true);

                    // we store the cities that we dont have in the database
                    tempCities = handler.getCityList3(storedCities);
                    if (tempCities != null) {
                        for (City c : tempCities) {
                            databaseDriver.insertCities(c);
                        }
                    }
                    //storing the traveller to the list
                    storedTravellers.add(selectedTraveller);
                    // incrementing the number of travellers by 1
                    Traveller.travInc();
                    // saving the new count on the file (overwrite)
                    FileWriterReader.saveCount(Traveller.getTravCount());
                    // saving the new traveller list on a file
                    FileWriterReader.saveTravellers(storedTravellers);

                    //we show the traveller list in gui
                    travellersFromFile = FileWriterReader.RetrieveTravellers(Traveller.getTravCount());
                    Collections.sort(travellersFromFile);
                    String previousName = "";
                    for (Traveller printTraveller : travellersFromFile) {
                        if (!previousName.equals(printTraveller.getName())) {
                            //System.out.println(printTraveller.getName() + " Age: " + printTraveller.getAge());
                            tableModel_1.insertRow(0, new Object[]{printTraveller.getName(), printTraveller.getAge()});
                            //,printTraveller.getAge()
                        }
                        previousName = printTraveller.getName();
                    }


                } else if (travellerType.equals("Bussines")) {
                    Bussines selectedTraveller = new Bussines();
                    System.out.println("bussines object created");
                    selectedTraveller.setName(travellerName);
                    System.out.println(selectedTraveller.getName());
                    selectedTraveller.setAge(travellerAge);
                    System.out.println(selectedTraveller.getAge());
                    selectedTraveller.setCmuseums((Integer) spinner.getValue());
                    System.out.println(selectedTraveller.getCmuseums());
                    selectedTraveller.setCcafes((Integer) spinner_1.getValue());
                    System.out.println(selectedTraveller.getCcafes());
                    selectedTraveller.setCbars((Integer) spinner_2.getValue());
                    System.out.println(selectedTraveller.getCbars());
                    selectedTraveller.setCbeaches((Integer) spinner_3.getValue());
                    System.out.println(selectedTraveller.getCbeaches());
                    selectedTraveller.setCrestaurants((Integer) spinner_4.getValue());
                    System.out.println(selectedTraveller.getCrestaurants());
                    selectedTraveller.setClat(Double.parseDouble(textField_2.getText()));
                    System.out.println(selectedTraveller.getClat());
                    selectedTraveller.setClon(Double.parseDouble(textField_3.getText()));
                    System.out.println(selectedTraveller.getClon());
                    selectedTraveller.setCweather(chckbxNewCheckBox.isSelected());

                    rowCount = table.getRowCount();
                    for (int i = 0; i < rowCount; i++) {
                        wantedCity = new City();
                        wantedCity.setName(table.getValueAt(i, 0).toString());
                        cityList.add(wantedCity);
                    }

                    for (City iteratedCity : cityList) {
                        for (City iteratedCity2 : storedCities)
                            if (iteratedCity.equals(iteratedCity2)) {
                                cityList2.add(iteratedCity2);
                                found = true;
                            }
                        if (!found) {
                            try {
                                cityList2.add(iteratedCity.fillCity(iteratedCity));
                            } catch (JsonParseException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (JsonMappingException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (WikipediaNoArcticleException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (com.sun.jersey.api.client.UniformInterfaceException e1) {
                                lblNewLabel_18.setVisible(true);
                            }
                        }
                    }
                    // here we compare the cities to the Tourist traveller criteria(multiple
                    // appearance of criteria)
                    City bestCity = new City();
                    bestCity = selectedTraveller.CompareCities(cityList2, selectedTraveller, selectedTraveller.isCweather());
                    //we display the results to the gui
                    lblNewLabel_13.setText("the city that matches your criteria the most is: " + bestCity.getName());
                    lblNewLabel_13.setVisible(true);

                    // we store the cities that we dont have in the database
                    tempCities = handler.getCityList3(storedCities);
                    if (tempCities != null) {
                        for (City c : tempCities) {
                            databaseDriver.insertCities(c);
                        }
                    }
                    //storing the traveller to the list
                    storedTravellers.add(selectedTraveller);
                    // incrementing the number of travellers by 1
                    Traveller.travInc();
                    // saving the new count on the file (overwrite)
                    FileWriterReader.saveCount(Traveller.getTravCount());
                    // saving the new traveller list on a file
                    FileWriterReader.saveTravellers(storedTravellers);

                    //we show the traveller list in gui
                    travellersFromFile = FileWriterReader.RetrieveTravellers(Traveller.getTravCount());
                    Collections.sort(travellersFromFile);
                    String previousName = "";
                    for (Traveller printTraveller : travellersFromFile) {
                        if (!previousName.equals(printTraveller.getName())) {
                            //System.out.println(printTraveller.getName() + " Age: " + printTraveller.getAge());
                            tableModel_1.insertRow(0, new Object[]{printTraveller.getName(), printTraveller.getAge()});
                            //,printTraveller.getAge()
                        }
                        previousName = printTraveller.getName();
                    }
                }
            }
        });
    }
}
