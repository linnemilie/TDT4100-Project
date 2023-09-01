package project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class HeadOfBooking implements IFilePath {
    public ArrayList<Vehicle> freeVehicles = new ArrayList<>();
    public Vehicle selectedVehicle = null;
    public DatesHandler dateHandler = new DatesHandler();
    public DiscountHandler discountHandler = new DiscountHandler();
    public HandleBookingFile fileHandler = new HandleBookingFile();
    public AdminMode adminMode = new AdminMode();
    public Person person;
    public Booking tempBooking;
    private ArrayList<Vehicle> vehicles = new ArrayList<>();


    public HeadOfBooking() throws FileNotFoundException {
        setVehicles("vehiclesFile");
        discountHandler.setUpDiscounts("discountFile");
        setDatesFromFileToDatesBooked("bookingFile");
    }

    // Denne konstruktøren brukes for å kunne teste med andre filer i testkode
    public HeadOfBooking(String vehicleFile, String discountFile, String bookingFile) throws FileNotFoundException {
        setVehicles(vehicleFile);
        discountHandler.setUpDiscounts(discountFile);
        setDatesFromFileToDatesBooked(bookingFile);
    }

    public void makeNewBooking(LocalDate startDate, LocalDate endDate, Person person, String discountCode, String filename, IUUIDGenerator uuid) throws IOException { 
        checkIfVehicleIsSelected();
        tempBooking = new Booking(startDate, endDate, person, this.selectedVehicle, discountCode, uuid);
        fileHandler.writeToFile(filename, tempBooking);
    }

    public void checkIfVehicleIsSelected() {
        if (this.selectedVehicle.equals(null)) {
            throw new NullPointerException("A vehicle must be selected");
        }
    }

    public void setFreeVehicles(LocalDate startDate, LocalDate endDate) {
        dateHandler.checkIfValidDates(startDate, endDate);
        freeVehicles.clear();
        Collection<LocalDate> wantedDates = dateHandler.makeListOfWantedDates(startDate, endDate);
        Collection<LocalDate> tempDateList = new ArrayList<LocalDate>();
        for (Vehicle vehicle : vehicles) {
            tempDateList.clear();
            wantedDates.forEach(date -> {
                if (vehicle.getDatesBooked().contains(date)) {
                    tempDateList.add(date);
                }
            });
            if (tempDateList.isEmpty()) {
                freeVehicles.add(vehicle);
            }
        }
        if (freeVehicles.isEmpty()) {
            throw new IllegalArgumentException("No available vehicles on the days selected, pleace pick another time");
        }
    }

    private void setDatesFromFileToDatesBooked(String filename) throws FileNotFoundException {
        try {
            HandleBookingFile fileHandler = new HandleBookingFile();
            ArrayList<String> bookingsList = fileHandler.readFromFile(filename);
            for (String string : bookingsList) {
                String[] splittedString = string.split(";");
                LocalDate startDate = LocalDate.parse(splittedString[5]);
                LocalDate endDate = LocalDate.parse(splittedString[6]);
                dateHandler.addDatesToVehicleChosen(startDate, endDate, vehicles.get(Integer.parseInt(splittedString[7])));
            }
        } 
        catch (FileNotFoundException fNfE) {
            throw new FileNotFoundException("Bookingfile not found");
        } 
        catch (DateTimeParseException dTpE) {
            throw new IllegalArgumentException(dTpE.getMessage());
        }
    }

    private void setVehicles(String filename) throws FileNotFoundException {
        try (Scanner vechicleScanner = new Scanner(getSelectedFile(filename))) {
            while (vechicleScanner.hasNext()) {
                String vehicleString = vechicleScanner.nextLine();
                String[] value = vehicleString.split(";");
                if (value[1].equals("van")) {
                    Vehicle vehicle = new Van(value[0], value[2], Integer.parseInt(value[3]), value[4], Integer.parseInt(value[5]));
                    vehicles.add(vehicle);
                } 
                else if (value[1].equals("motorcycle")){
                    Vehicle vehicle = new Motorcycle(value[0], value[2], Integer.parseInt(value[3]), value[4], Integer.parseInt(value[5]));
                    vehicles.add(vehicle);
                }
                else {
                    Vehicle vehicle = new Vehicle(value[0], value[1], value[2], Integer.parseInt(value[3]), value[4], Integer.parseInt(value[5]));
                    vehicles.add(vehicle);
                }
            }
            vechicleScanner.close();
        } 
        catch (NumberFormatException e) {
            throw new NumberFormatException("Coldn not read from file");
        } 
        catch (FileNotFoundException fNfE) {
            throw new FileNotFoundException("vehichlesFile not found");
        }
    }

    public void setSelectedVehicle(Vehicle vehicle) {
        this.selectedVehicle = vehicle;
    }

    public ArrayList<Vehicle> getVehicles() {
        return this.vehicles;
    }

    public ArrayList<Vehicle> getFreeVehicles() {
        return this.freeVehicles;
    }

    public Path getVehiclePath(String filename) throws IOException {
        Path path = new File(getClass().getResource("vehicles").getFile() + filename + ".txt").toPath();
        return path;
    }

    @Override
    public File getSelectedFile(String filename) {
        return new File(DiscountHandler.class.getResource("vehicles/").getFile() + filename + ".txt");
    }

}
