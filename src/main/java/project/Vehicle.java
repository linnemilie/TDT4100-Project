package project;

import java.time.LocalDate;
import java.util.ArrayList;

public class Vehicle {
    public ArrayList<LocalDate> datesBooked = new ArrayList<LocalDate>();
    protected String vehicleType;
    private String id;
    private int passangerCapacity;
    private String gearType;
    private int pricePerDay;
    private String vehicleName;

    public Vehicle(String id, String vechicleType, String vehicleName, int passangerCapacity, String gearType, int pricePerDay) {
        setGearType(gearType);
        this.id = id;
        this.vehicleType = vechicleType;
        this.pricePerDay = pricePerDay;
        this.passangerCapacity = passangerCapacity;
        this.vehicleName = vehicleName;
    }

    private void setGearType(String gearType) {
        if (gearType.equals("Manual") || gearType.equals("Automatic")) {
            this.gearType = gearType;
        } 
        else {
            throw new IllegalArgumentException("Not a valid type of gear");
        }
    }

    public String getId() {
        return this.id;
    }

    public String getVehicleName() {
        return this.vehicleName;
    }

    public String getVehicleType() {
        return this.vehicleType;
    }

    public int getPassangerCapacity() {
        return this.passangerCapacity;
    }

    public String getGearType() {
        return this.gearType;
    }

    public int getPricePerDay() {
        return this.pricePerDay;
    }

    public ArrayList<LocalDate> getDatesBooked() {
        return this.datesBooked;
    }

    public String vehicleTextToString() {
        return "--- INFORMATION ABOUT SELECTED VEHICLE --- "
                + "\n" + "Vehicle type: " + vehicleType
                + "\n" + "Vechicle name: " + vehicleName
                + "\n" + "Gear type: " + gearType
                + "\n" + "Passanger capacity: " + passangerCapacity
                + "\n" + "Price per day: " + pricePerDay
                + "\n";
    }

    @Override
    public String toString() {
        return id + ";" + vehicleType + ";" + passangerCapacity;
    }

}
