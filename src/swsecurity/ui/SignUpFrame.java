package swsecurity.ui;

import swsecurity.service.AuthService;

import javax.swing.*;
import java.awt.*;

public class SignUpFrame extends JFrame {
    private final AuthService authService;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JLabel statusLabel;

    public SignUpFrame() {
        this.authService = new AuthService();
        initializeUi();
    }

    private void initializeUi() {
        setTitle("Car Rental System - Sign Up");
        setSize(460, 300);
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

        gbc = UiUtil.gbc(0, 2);
        panel.add(new JLabel("Confirm Password:"), gbc);

        confirmPasswordField = new JPasswordField(18);
        gbc = UiUtil.gbc(1, 2);
        panel.add(confirmPasswordField, gbc);

        JButton createButton = new JButton("Create Account");
        createButton.addActionListener(e -> handleSignUp());
        gbc = UiUtil.gbc(0, 3);
        panel.add(createButton, gbc);

        JButton backButton = new JButton("Back to Login");
        backButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        gbc = UiUtil.gbc(1, 3);
        panel.add(backButton, gbc);

        statusLabel = new JLabel("Create a new account.");
        statusLabel.setForeground(Color.DARK_GRAY);
        gbc = UiUtil.gbc(0, 4);
        gbc.gridwidth = 2;
        panel.add(statusLabel, gbc);

        add(panel);
    }

    private void handleSignUp() {
        try {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            authService.register(username, password, confirmPassword);

            JOptionPane.showMessageDialog(this, "Account created successfully.", "Success",
                JOptionPane.INFORMATION_MESSAGE);

            dispose();
            new LoginFrame().setVisible(true);

        } catch (IllegalArgumentException ex) {
            statusLabel.setText(ex.getMessage());
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Sign Up Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}