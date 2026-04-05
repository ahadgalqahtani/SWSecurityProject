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
    private JButton loginButton;

    public LoginFrame() {
        this.authService = new AuthService();
        initializeUi();
    }

    private void initializeUi() {
        setTitle("Car Rental System - Login");
        setSize(460, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = UiUtil.gbc(0, 0);
        panel.add(new JLabel("Username:"), gbc);

        usernameField = new JTextField(18);
        gbc = UiUtil.gbc(1, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(usernameField, gbc);

        gbc = UiUtil.gbc(0, 1);
        panel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(18);
        gbc = UiUtil.gbc(1, 1);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(passwordField, gbc);

        loginButton = new JButton("Login");
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
        gbc.fill = GridBagConstraints.HORIZONTAL;
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

            if (remaining == 0) {
                statusLabel.setText("Login locked for this session.");
                loginButton.setEnabled(false);

                JOptionPane.showMessageDialog(
                    this,
                    "Too many failed login attempts for this username.\nLogin is disabled for this session.",
                    "Login Locked",
                    JOptionPane.ERROR_MESSAGE
                );
            } else {
                statusLabel.setText("Login failed. Remaining attempts: " + remaining);

                JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage() + "\nRemaining attempts: " + remaining,
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}