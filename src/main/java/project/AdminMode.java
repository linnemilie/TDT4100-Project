package project;

import java.util.ArrayList;
import java.util.Collections;

public class AdminMode {
    private String password = "admin123";
    private String username = "admin";

    public AdminMode() {
    }

    public void verifyAdminLogIn(String username, String password) {
        if (username.equals("")) {
            throw new IllegalArgumentException("Enter a valid username");
        }
        if (password.equals("")) {
            throw new IllegalArgumentException("Enter a valid password.");
        }
        if (!(username.equals(this.username)) || (!(password.equals(this.password)))) {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    public String getBookingsWithSearchword(String searchWord, ArrayList<String> bookingList) {
        String bookingsToReturn = "";
        if (bookingList.isEmpty()) {
            bookingsToReturn += "There are no bookings.";
        }
        String strippedSearchWord = searchWord.trim();
        Collections.sort(bookingList, new DateComparator());
        if (((strippedSearchWord.matches("[+47]{3}[9]{1}[0-9]{7}")) || (strippedSearchWord.matches("[+47]{3}[4]{1}[0-9]{7}")))) {
            for (String string : bookingList) {
                String[] stringSplitted = string.split(";");
                if ((stringSplitted[3].equals(strippedSearchWord))) {
                    String bookingToReturn = writeOutputString(stringSplitted);
                    bookingsToReturn += bookingToReturn;
                }
            }
        }
        if (strippedSearchWord.equals("all")) {
            for (String string : bookingList) {
                String[] stringSplitted = string.split(";");
                String bookingToReturn = writeOutputString(stringSplitted);
                bookingsToReturn += bookingToReturn;
            }
        } else {
            for (String string : bookingList) {
                String[] stringSplitted = string.split(";");
                if (stringSplitted[0].equals(strippedSearchWord)) {
                    String bookingToReturn = writeOutputString(stringSplitted);
                    bookingsToReturn = bookingToReturn;
                }
            }
        }
        if (bookingsToReturn.equals("")) {
            throw new IllegalArgumentException(
                    "Not a valid searchword. Use a valid phonenumber, booking rerference or write 'all' to see all bookings.");
        }
        return bookingsToReturn;

    }

    private String writeOutputString(String[] stringSplitted) {
        String bookingreference = "\n" + "Booking reference: " + stringSplitted[0] + "\n";
        String driverInformation = "Name: " + stringSplitted[1] + " " + stringSplitted[2] + "\n" + "Phone number: " + stringSplitted[3] + "\n";
        String dateInformation = "Dates booked: " + stringSplitted[5] + " - " + stringSplitted[6] + "\n";
        String vehicleInformation = "VehicleID: " + stringSplitted[7] + "\n";
        String priceInformation = "Price: " + stringSplitted[10] + "\n" + "Discount: " + stringSplitted[11] + "%" + "\n";
        String output = bookingreference + driverInformation + dateInformation + vehicleInformation + priceInformation;
        return output;
    }

}
