package swsecurity.service;

import swsecurity.model.Car;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RentalService {
    private static final double GAS_PRICE = 2.25;

    private final List<Car> cars;

    public RentalService() {
        this.cars = new ArrayList<>();
        loadCars();
    }

    private void loadCars() {
        cars.add(new Car("Honda", "Civic Coupe", "Coupe", "Economy", 34, 45, 4, 1, "Poor"));
        cars.add(new Car("Toyota", "Camry", "Sedan", "Intermediate", 32, 50, 4, 2, "Medium"));
        cars.add(new Car("Toyota", "Prius", "Hybrid", "Intermediate", 50, 50, 4, 2, "Medium"));
        cars.add(new Car("Hyundai", "Tucson", "SUV", "Standard", 28, 55, 5, 3, "Good"));
        cars.add(new Car("Ford", "Maverick", "Truck", "Standard", 30, 55, 5, 3, "Good"));
        cars.add(new Car("Subaru", "Outback", "Crossover", "Standard", 29, 55, 5, 3, "Good"));
        cars.add(new Car("Honda", "Odyssey", "Van/Minivan", "Van", 22, 70, 7, 2, "Medium"));
    }

    public List<Car> recommendCars(int passengers, int rentalDays, double mileage) {
        List<Car> eligible = cars.stream()
            .filter(car -> car.getMaxPassengers() >= passengers)
            .sorted(Comparator
                .comparingDouble((Car c) -> c.totalCost(rentalDays, mileage, GAS_PRICE))
                .thenComparing((Car c) -> -c.getComfortRank()))
            .collect(Collectors.toList());

        if (eligible.isEmpty()) {
            return eligible;
        }

        double lowestCost = eligible.get(0).totalCost(rentalDays, mileage, GAS_PRICE);

        int bestComfort = eligible.stream()
            .filter(c -> Double.compare(c.totalCost(rentalDays, mileage, GAS_PRICE), lowestCost) == 0)
            .mapToInt(Car::getComfortRank)
            .max()
            .orElse(0);

        return eligible.stream()
            .filter(c -> Double.compare(c.totalCost(rentalDays, mileage, GAS_PRICE), lowestCost) == 0)
            .filter(c -> c.getComfortRank() == bestComfort)
            .collect(Collectors.toList());
    }

    public double getGasPrice() {
        return GAS_PRICE;
    }
}