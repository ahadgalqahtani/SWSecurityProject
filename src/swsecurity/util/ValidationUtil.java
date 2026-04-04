package swsecurity.util;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void requireNonBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be empty.");
        }
    }

    public static void requireUsernameFormat(String username) {
        if (!username.matches("^[A-Za-z0-9_]{3,20}$")) {
            throw new IllegalArgumentException(
                "Username must be 3-20 characters and contain only letters, numbers, or underscore."
            );
        }
    }

    public static void requirePasswordStrength(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter.");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one number.");
        }
    }

    public static int parsePassengers(String value) {
        try {
            int passengers = Integer.parseInt(value);
            if (passengers < 1 || passengers > 7) {
                throw new IllegalArgumentException("Passengers must be between 1 and 7.");
            }
            return passengers;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Passengers must be a valid integer.");
        }
    }

    public static int parseRentalDays(String value) {
        try {
            int days = Integer.parseInt(value);
            if (days < 1 || days > 365) {
                throw new IllegalArgumentException("Rental days must be between 1 and 365.");
            }
            return days;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Rental days must be a valid integer.");
        }
    }

    public static double parseMileage(String value) {
        try {
            double mileage = Double.parseDouble(value);
            if (mileage < 0 || mileage > 100000) {
                throw new IllegalArgumentException("Mileage must be between 0 and 100000.");
            }
            return mileage;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Mileage must be a valid number.");
        }
    }
}