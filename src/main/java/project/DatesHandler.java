package project;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.stream.Stream;

public class DatesHandler {

    public DatesHandler() {
    }

    public void checkIfValidDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null){
            throw new IllegalArgumentException("Please select wanted date for booking");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Your start date must be before the end date");
        }
        LocalDate today = LocalDate.now();
        if ((startDate.isBefore(today))) {
            throw new IllegalArgumentException("It is not possible to select dates from the past");
        }
        if ((getDurationOfBooking(startDate, endDate) < 2)) {
            throw new IllegalArgumentException("Your booking has to be at least two nights or longer.");
        }
    }

    public int getDurationOfBooking(LocalDate startDate, LocalDate endDate) {
        long daysDifference = ChronoUnit.DAYS.between(startDate, endDate);
        int days = (int) daysDifference;
        if (days <= 0) {
            throw new IllegalArgumentException("There must be a positive ammount of days");
        }
        return days;
    }

    public ArrayList<LocalDate> makeListOfWantedDates(LocalDate startDate, LocalDate endDate) {
        checkIfValidDates(startDate, endDate);
        ArrayList<LocalDate> listOfWantedDays = new ArrayList<LocalDate>();
        Stream<LocalDate> pickedDays = startDate.datesUntil(endDate);
        pickedDays.forEach(d -> listOfWantedDays.add(d));
        listOfWantedDays.add(endDate);
        return listOfWantedDays;
    }

    public void addDatesToVehicleChosen(LocalDate startDate, LocalDate endDate, Vehicle vehicle) {
        Stream<LocalDate> daysBetween = startDate.datesUntil(endDate);
        daysBetween.forEach(day -> vehicle.getDatesBooked().add(day));
        vehicle.getDatesBooked().add(endDate);
    }

}
