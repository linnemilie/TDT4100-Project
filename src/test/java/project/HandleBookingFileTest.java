package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HandleBookingFileTest { 

    private HandleBookingFile bookingHandler;
    private Person person;
    private Vehicle vehicle;
    private Booking booking;

    private LocalDate startDate;
    private LocalDate endDate;
    private String discountCode;

    private static ArrayList<String> excpectedBookingsList = new ArrayList<String>(); 
    private static ArrayList<String> actualBookingsList = new ArrayList<String>();

    private static final String testInvalidBookingFilePrint = """ 
            dbb4dcba-1032-4619-ad38-8822780a9899;;;0;;;
            """;;

    private static final String fileTest = """
        66482a87-454e-4f77-9531-f7dcdc5e1ff3;Tore;Sagen;+4799454545;toresagen@gmail.com;2022-04-25;2022-04-28;4;van;3;3600;0
        dbb4dcba-1032-4619-ad38-8822780a9899;Per;Halvorsen;+4798765432;per@j.no;2022-04-11;2022-04-15;0;van;4;4800;0
            """;

    @BeforeEach
    public void setup(){
        bookingHandler = new HandleBookingFile();
        excpectedBookingsList.add("66482a87-454e-4f77-9531-f7dcdc5e1ff3;Tore;Sagen;+4799454545;toresagen@gmail.com;2022-04-25;2022-04-28;4;van;3;3600;0"); 
        excpectedBookingsList.add("dbb4dcba-1032-4619-ad38-8822780a9899;Per;Halvorsen;+4798765432;per@j.no;2022-04-11;2022-04-15;0;van;4;4800;0");
        
        try{
            Files.write(bookingHandler.getBookingPath("bookingExcpected"), fileTest.getBytes());
        }
        catch(IOException iOe){
            System.out.println(iOe);
        }

        startDate = LocalDate.parse("2022-08-11");
        endDate = LocalDate.parse("2022-08-15");
        person = new Person("Per", "Halvorsen", "+4798765432", "22", "per@j.no");
        vehicle = new Vehicle("3", "van", "Louise", 3, "Automatic", 600);
        discountCode = null;        
    }

    @Test
    @DisplayName("Tester at bookingene leses fra fil")
    public void readFromFileTest() throws IllegalArgumentException, IOException{
        actualBookingsList = bookingHandler.readFromFile("bookingExcpected");
        assertEquals(excpectedBookingsList, actualBookingsList);
    }

    @Test
    @DisplayName("Tester at FileNotFoundException kastes når man prøver å lese fra en fil som ikke eksisterer")
    public void readFromFileFileNotExcistingTest(){
        assertThrows(FileNotFoundException.class, () -> {
            bookingHandler.readFromFile("notexcistingFile");
        });
    }

    @Test
    @DisplayName("Tester om IllegalArgumentException kastes dersom filen ikke er på riktig format")
    public void readUnvalidBooking() throws IOException{ 
        Files.write(bookingHandler.getBookingPath("invalidBooking"), testInvalidBookingFilePrint.getBytes());
        assertThrows(IllegalArgumentException.class, () -> {
            bookingHandler.readFromFile("invalidBooking");
        });
    }

    
    @Test 
    public void writeToFileTest() throws IOException{
        ConstantUUIDGenerator generatorUUID = new ConstantUUIDGenerator();
        booking = new Booking(startDate, endDate, person, vehicle, discountCode, generatorUUID);
        try{ 
            bookingHandler.writeToFile("actualBooking", booking);
            byte[] excpectedBookingFile = Files.readAllBytes(bookingHandler.getBookingPath("bookingExcpectedSimple"));
            byte[] actualBookingFile = Files.readAllBytes(bookingHandler.getBookingPath("actualBooking"));
            assertTrue(Arrays.equals(excpectedBookingFile, actualBookingFile));
        }
        catch (IOException e){
            System.out.println(e);
        }
    }


    @AfterEach
    public void tearDown(){
        bookingHandler.getSelectedFile("actualBooking").delete();
        bookingHandler.getSelectedFile("bookingExcpected").delete();
        bookingHandler.getSelectedFile("invalidBooking").delete();
        excpectedBookingsList.clear();
        actualBookingsList.clear();
        bookingHandler.getSelectedFile("testWriteBooking").delete();
        bookingHandler.getSelectedFile("invalidBooking").delete();
    }

}
