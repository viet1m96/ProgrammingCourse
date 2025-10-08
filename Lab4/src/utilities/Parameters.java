package utilities;

import exceptions.NotEnoughFuel;

import java.util.Random;

public class Parameters {
    private double acceleration;
    private final double oilAssumption;
    private int fuelCapacity;
    private double speed;

    public Parameters(double acceleration, double oilAssumption, int distance) {
        this.speed = 0;
        this.acceleration = acceleration;
        this.oilAssumption = oilAssumption;
        Random random = new Random();
        fuelCapacity= random.nextInt(1500 - 1) + 1;
        try {
            if(fuelCapacity / oilAssumption < distance) throw new NotEnoughFuel("The fuel is not enough for the distance! Loading again...");
        } catch (NotEnoughFuel e) {
            while((fuelCapacity / oilAssumption) < distance) {
                fuelCapacity = random.nextInt(1500 - 1) + 1;
            }
        }
    }

    public void setAcceleration(double maxSpeed, double distance) {
        acceleration = (maxSpeed * maxSpeed - speed * speed) / (2 * distance);
        System.out.println("The acceleration was set to " + acceleration);
    }

    public void modifySpeed(double t) {
        speed = acceleration * t;
    }

    public double getSpeed() {
        return speed;
    }
    public double getAcceleration() {
        return acceleration;
    }
}
