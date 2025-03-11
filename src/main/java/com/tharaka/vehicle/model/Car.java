package com.tharaka.vehicle.model;

public class Car {
    private int id;
    private String carNumber;
    private String model;
    private boolean availability;

    // Default constructor
    public Car() {
    }

    // Parameterized constructor
    public Car(int id, String carNumber, String model, boolean availability) {
        this.id = id;
        this.carNumber = carNumber;
        this.model = model;
        this.availability = availability;
    }

    // Getter and Setter for id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for carNumber
    public String getCarNumber() {
        return carNumber;
    }
    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    // Getter and Setter for model
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    // Getter and Setter for availability
    public boolean isAvailability() {
        return availability;
    }
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
