package project;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class HandleBookingFile implements IFileManagment, IFilePath {

    @Override
    public ArrayList<String> readFromFile(String filename) throws FileNotFoundException, IllegalArgumentException {
        ArrayList<String> bookingsList = new ArrayList<String>();
        try (Scanner scanner = new Scanner(getSelectedFile(filename))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] splittedLine = line.split(";");
                int parts = splittedLine.length;
                if (parts != 12) {
                    throw new IllegalArgumentException();
                }
                bookingsList.add(line);
            }
            scanner.close();
        } 
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("The file is not existing");
        } 
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("There has been an error writing to file, all content is not in the right format");
        } 
        catch (Exception e) {
            System.out.println("Error");
        }
        return bookingsList;
    }

    @Override
    public void writeToFile(String filename, Booking booking) throws IOException, FileNotFoundException {
        ArrayList<String> bookingslist = readFromFile(filename);
        String output = "";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getSelectedFile(filename)))) {
            String bookingString = booking.toString();
            bookingslist.add(bookingString);
            for (String bookings : bookingslist) {
                output += bookings + "\n";
            }
            writer.write(output);
            writer.close();
        } 
        catch (FileNotFoundException e) {throw new FileNotFoundException("An IO-exception. There is something wrong with the files.");
        } 
        catch (IOException e) {throw new IOException("An IO-exception. There is something wrong with the files.");
        } 
        catch (Exception e) {
            System.out.println("Unknown error occurred");
        }
    }

    @Override
    public File getSelectedFile(String filename) {
        return new File(DiscountHandler.class.getResource("bookings/").getFile() + filename + ".txt");
    }

    public Path getBookingPath(String filename) throws IOException {
        Path path = new File(getClass().getResource("bookings").getFile() + filename + ".txt").toPath();
        return path;
    }
}
