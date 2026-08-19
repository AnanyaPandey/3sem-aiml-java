// Program to demonstrate Method Object and Class 

public class Car {
    // Fields (attributes) of the Car class
    private String brand;
    private String model;
    private double fuelInTank;      // in litres
    private double mileagePerLitre; // km the car runs per litre of fuel
    private double totalDistanceCovered; // in km

    // Constructor to initialize a Car object
    public Car(String brand, String model, double fuelInTank, double mileagePerLitre) {
        this.brand = brand;
        this.model = model;
        this.fuelInTank = fuelInTank;
        this.mileagePerLitre = mileagePerLitre;
        this.totalDistanceCovered = 0;
    }

    // Method to drive the car a certain distance
    public void drive(double distance) {
        double fuelNeeded = distance / mileagePerLitre;

        if (fuelNeeded > fuelInTank) {
            System.out.println(brand + " " + model + " does not have enough fuel to travel " + distance + " km.");
            return;
        }

        fuelInTank -= fuelNeeded;
        totalDistanceCovered += distance;
        System.out.println(brand + " " + model + " drove " + distance + " km. Fuel left: " + fuelInTank + " litres.");
    }

    // Method to refuel the car
    public void refuel(double litres) {
        fuelInTank += litres;
        System.out.println(brand + " " + model + " refueled with " + litres + " litres. Total fuel: " + fuelInTank + " litres.");
    }

    // Method to calculate average mileage based on usage so far
    public double calculateAverageMileage() {
        if (totalDistanceCovered == 0) {
            return 0;
        }
        return totalDistanceCovered / (totalDistanceCovered / mileagePerLitre);
    }

    // Method to display current status of the car
    public void displayStatus() {
        System.out.println("Car: " + brand + " " + model);
        System.out.println("Fuel in tank: " + fuelInTank + " litres");
        System.out.println("Total distance covered: " + totalDistanceCovered + " km");
        System.out.println("Mileage: " + mileagePerLitre + " km/litre");
    }

    // Main method to test the class
    public static void main(String[] args) {
        // Creating an object of Car class
        Car myCar = new Car("Maruti", "Swift", 40, 18);

        myCar.displayStatus();
        myCar.drive(150);
        myCar.drive(200);
        myCar.refuel(20);
        myCar.displayStatus();

        System.out.println("Average mileage so far: " + myCar.calculateAverageMileage() + " km/litre");
    }
}