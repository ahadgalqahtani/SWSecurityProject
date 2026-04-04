package swsecurity.service;

import swsecurity.model.User;
import swsecurity.util.ValidationUtil;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private static final int MAX_FAILED_ATTEMPTS = 3;

    private final UserStore userStore;
    private final Map<String, Integer> failedAttempts;

    public AuthService() {
        this.userStore = new UserStore();
        this.failedAttempts = new HashMap<>();
    }

    public User register(String username, String password, String confirmPassword) {
        ValidationUtil.requireNonBlank(username, "Username");
        ValidationUtil.requireUsernameFormat(username);
        ValidationUtil.requireNonBlank(password, "Password");
        ValidationUtil.requirePasswordStrength(password);

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        if (userStore.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }

        String salt = PasswordUtil.generateSalt();
        String hash = PasswordUtil.hashPassword(password, salt);

        User user = new User(username, salt, hash);
        userStore.saveUser(user);
        return user;
    }

    public User login(String username, String password) {
        ValidationUtil.requireNonBlank(username, "Username");
        ValidationUtil.requireNonBlank(password, "Password");

        if (failedAttempts.getOrDefault(username, 0) >= MAX_FAILED_ATTEMPTS) {
            throw new IllegalArgumentException("Too many failed login attempts for this username.");
        }

        User user = userStore.findByUsername(username);
        if (user == null) {
            failedAttempts.put(username, failedAttempts.getOrDefault(username, 0) + 1);
            throw new IllegalArgumentException("User is not registered.");
        }

        String computedHash = PasswordUtil.hashPassword(password, user.getSalt());
        if (!computedHash.equals(user.getPasswordHash())) {
            failedAttempts.put(username, failedAttempts.getOrDefault(username, 0) + 1);
            throw new IllegalArgumentException("Invalid username or password.");
        }

        failedAttempts.remove(username);
        return user;
    }

    public int getRemainingAttempts(String username) {
        int used = failedAttempts.getOrDefault(username, 0);
        return Math.max(0, MAX_FAILED_ATTEMPTS - used);
    }
}