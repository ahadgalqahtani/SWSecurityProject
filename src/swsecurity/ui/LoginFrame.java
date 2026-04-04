package swsecurity.ui;

import swsecurity.model.User;
import swsecurity.service.AuthService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final AuthService authService;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    public LoginFrame() {
        this.authService = new AuthService();
        initializeUi();
    }

    private void initializeUi() {
        setTitle("Car Rental System - Login");
        setSize(420, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = UiUtil.gbc(0, 0);
        panel.add(new JLabel("Username:"), gbc);

        usernameField = new JTextField(18);
        gbc = UiUtil.gbc(1, 0);
        panel.add(usernameField, gbc);

        gbc = UiUtil.gbc(0, 1);
        panel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(18);
        gbc = UiUtil.gbc(1, 1);
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleLogin());
        gbc = UiUtil.gbc(0, 2);
        panel.add(loginButton, gbc);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {
            dispose();
            new SignUpFrame().setVisible(true);
        });
        gbc = UiUtil.gbc(1, 2);
        panel.add(signUpButton, gbc);

        statusLabel = new JLabel("Please log in or create an account.");
        statusLabel.setForeground(Color.DARK_GRAY);
        gbc = UiUtil.gbc(0, 3);
        gbc.gridwidth = 2;
        panel.add(statusLabel, gbc);

        add(panel);
    }

    private void handleLogin() {
        try {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            User user = authService.login(username, password);

            dispose();
            new RentalFrame(user).setVisible(true);

        } catch (IllegalArgumentException ex) {
            String username = usernameField.getText().trim();
            int remaining = authService.getRemainingAttempts(username);
            statusLabel.setText(ex.getMessage() + " Remaining attempts: " + remaining);
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}