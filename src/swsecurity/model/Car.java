package swsecurity.model;

public class Car {
    private final String make;
    private final String model;
    private final String category;
    private final String tier;
    private final double mpg;
    private final double dailyRate;
    private final int maxPassengers;
    private final int comfortRank;
    private final String comfortLabel;

    public Car(String make, String model, String category, String tier,
               double mpg, double dailyRate, int maxPassengers,
               int comfortRank, String comfortLabel) {
        this.make = make;
        this.model = model;
        this.category = category;
        this.tier = tier;
        this.mpg = mpg;
        this.dailyRate = dailyRate;
        this.maxPassengers = maxPassengers;
        this.comfortRank = comfortRank;
        this.comfortLabel = comfortLabel;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getCategory() {
        return category;
    }

    public String getTier() {
        return tier;
    }

    public double getMpg() {
        return mpg;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public int getComfortRank() {
        return comfortRank;
    }

    public String getComfortLabel() {
        return comfortLabel;
    }

    public double totalCost(int rentalDays, double mileage, double gasPricePerGallon) {
        double rentalCost = dailyRate * rentalDays;
        double fuelCost = (mileage / mpg) * gasPricePerGallon;
        return rentalCost + fuelCost;
    }

    public String displayLine(int rentalDays, double mileage, double gasPricePerGallon) {
        return String.format(
            "%s %s | Category: %s | Tier: %s | Max Passengers: %d | Comfort: %s | Total Cost: $%.2f",
            make, model, category, tier, maxPassengers, comfortLabel,
            totalCost(rentalDays, mileage, gasPricePerGallon)
        );
    }
}