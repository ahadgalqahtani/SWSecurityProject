package swsecurity.service;

import swsecurity.model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class UserStore {
    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = "users.txt";

    private final Path usersFilePath;

    public UserStore() {
        this.usersFilePath = Paths.get(DATA_DIR, USERS_FILE);
        initializeStorage();
    }

    private void initializeStorage() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
            if (!Files.exists(usersFilePath)) {
                Files.createFile(usersFilePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize user storage.", e);
        }
    }

    public List<User> readAllUsers() {
        try {
            List<String> lines = Files.readAllLines(usersFilePath);
            List<User> users = new ArrayList<>();

            for (String line : lines) {
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }

                User user = parseUser(line);
                if (user != null) {
                    users.add(user);
                }
            }

            return users;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read users.", e);
        }
    }

    public User findByUsername(String username) {
        for (User user : readAllUsers()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public void saveUser(User user) {
        try {
            String line = user.toFileLine() + System.lineSeparator();
            Files.write(
                usersFilePath,
                line.getBytes(),
                StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to save user.", e);
        }
    }

    private User parseUser(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 3) {
            return null;
        }

        String username = parts[0];
        String salt = parts[1];
        String passwordHash = parts[2];

        return new User(username, salt, passwordHash);
    }
}