package swsecurity.ui;

import swsecurity.model.Car;
import swsecurity.model.User;
import swsecurity.service.RentalService;
import swsecurity.util.ValidationUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RentalFrame extends JFrame {
    private final User currentUser;
    private final RentalService rentalService;

    private JTextField passengersField;
    private JTextField daysField;
    private JTextField mileageField;
    private JTextArea outputArea;

    public RentalFrame(User currentUser) {
        this.currentUser = currentUser;
        this.rentalService = new RentalService();
        initializeUi();
    }

    private void initializeUi() {
        setTitle("Car Rental Recommendation");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = UiUtil.gbc(0, 0);
        inputPanel.add(new JLabel("Welcome, " + currentUser.getUsername()), gbc);

        gbc = UiUtil.gbc(0, 1);
        inputPanel.add(new JLabel("Number of Passengers:"), gbc);

        passengersField = new JTextField(12);
        gbc = UiUtil.gbc(1, 1);
        inputPanel.add(passengersField, gbc);

        gbc = UiUtil.gbc(0, 2);
        inputPanel.add(new JLabel("Number of Rental Days:"), gbc);

        daysField = new JTextField(12);
        gbc = UiUtil.gbc(1, 2);
        inputPanel.add(daysField, gbc);

        gbc = UiUtil.gbc(0, 3);
        inputPanel.add(new JLabel("Approximate Mileage:"), gbc);

        mileageField = new JTextField(12);
        gbc = UiUtil.gbc(1, 3);
        inputPanel.add(mileageField, gbc);

        JButton recommendButton = new JButton("Find Best Car");
        recommendButton.addActionListener(e -> handleRecommendation());
        gbc = UiUtil.gbc(0, 4);
        inputPanel.add(recommendButton, gbc);

        JButton clearButton = new JButton("Clear / Re-enter");
        clearButton.addActionListener(e -> clearForm());
        gbc = UiUtil.gbc(1, 4);
        inputPanel.add(clearButton, gbc);

        outputArea = new JTextArea(14, 50);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(outputArea);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void handleRecommendation() {
        try {
            int passengers = ValidationUtil.parsePassengers(passengersField.getText().trim());
            int rentalDays = ValidationUtil.parseRentalDays(daysField.getText().trim());
            double mileage = ValidationUtil.parseMileage(mileageField.getText().trim());

            List<Car> recommendedCars = rentalService.recommendCars(passengers, rentalDays, mileage);

            if (recommendedCars.isEmpty()) {
                outputArea.setText("No cars are available for the specified number of passengers.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("Recommended Car(s):\n\n");
            for (Car car : recommendedCars) {
                sb.append(car.displayLine(rentalDays, mileage, rentalService.getGasPrice())).append("\n");
            }

            outputArea.setText(sb.toString());

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        passengersField.setText("");
        daysField.setText("");
        mileageField.setText("");
        outputArea.setText("");
    }
}