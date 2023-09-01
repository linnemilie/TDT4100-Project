package project;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;

public class DateComparator implements Comparator<String> {

    @Override
    public int compare(String bookingOne, String bookingTwo) {
        String bookingOneSplitted[] = bookingOne.split(";");
        String bookingTwoSplitted[] = bookingTwo.split(";");
        LocalDate date1;
        LocalDate date2;
        try {
            date1 = LocalDate.parse(bookingOneSplitted[5]);
            date2 = LocalDate.parse(bookingTwoSplitted[5]);
        } 
        catch (DateTimeParseException e) {
            throw new DateTimeParseException("Invalid date format.", bookingOneSplitted[5], 0);
        }
        if (date1.isBefore(date2)) {
            return -1;
        } 
        else if (date1.isAfter(date2)) {
            return 1;
        }
        return 0;
    }

}
