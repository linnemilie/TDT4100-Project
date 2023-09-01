package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HeadOfBookingTest {

    private HeadOfBooking headOfBooking;
    private DiscountHandler discountHandler;
    private DatesHandler datesHandler;
    private HandleBookingFile fileHandler;
    private ConstantUUIDGenerator constantUUIDGenerator;
    private Vehicle vehicle;
    private Person person;
    private LocalDate startDate;
    private LocalDate endDate;
    private String discountString;

    private static final String testBookingFilePrint = """
            dbb4dcba-1032-4619-ad38-8822780a9899;Anastasia;Lunde;+4799887766;anastasia@gmail.com;2022-06-07;2022-06-09;3;van;3;1200;0
            """;

    private static final String testUnvalidBookingFilePrint = """
            dbb4dcba-1032-4619-ad38-8822780a9899;Anastasia;Lunde;+4799887766;anastasia@gmail.com;2022-06-0k;2022-0609;3;Louise;3;1200;0
            """;

    private static final String testVehcileFile = """
            0;van;Nellie;4;Manual;500
            1;van;Nora;3;Automatic;600
            2;van;Vera;6;Manual;900
            3;van;Louise;3;Automatic;600
            4;motorcycle;Anne;2;Automatic;400
            """;

    private static final String testDiscountFile = """
            gammelKu;30
                """;;

    private static final String empty = "";

    @BeforeEach
    public void setup() throws IOException {
        discountHandler = new DiscountHandler();
        datesHandler = new DatesHandler();
        fileHandler = new HandleBookingFile();
        person = new Person("Anastasia", "Lunde", "+4799887766", "24", "anastasia@gmail.com");
        vehicle = new Vehicle("3", "van", "Louise", 3, "Automatic", 600);
        startDate = LocalDate.parse("2022-06-07");
        endDate = LocalDate.parse("2022-06-09");
        constantUUIDGenerator = new ConstantUUIDGenerator();
        Files.write(new File(getClass().getResource("bookings").getFile() + "actualNewBookingFile" + ".txt").toPath(), empty.getBytes());
        Files.write(new File(getClass().getResource("vehicles").getFile() + "testVehcileFile" + ".txt").toPath(), testVehcileFile.getBytes());
        Files.write(discountHandler.getDiscountFilPath("testDiscountFile"), testDiscountFile.getBytes());
        Files.write(fileHandler.getBookingPath("excpectedNewBookingFile"), testBookingFilePrint.getBytes());
        headOfBooking = new HeadOfBooking("testVehcileFile", "testDiscountFile", "excpectedNewBookingFile");
    }

    @Test
    @DisplayName("IllegalArgumentException når selectedVehicle er null")
    public void testCheckIfVehicleIsSelected() {
        assertThrows(NullPointerException.class, () -> {
            headOfBooking.checkIfVehicleIsSelected();
        });
    }

    @Test
    @DisplayName("Tester at tilgjengelige kjøretøy legges til i vehicles arraylist")
    public void testSetVehicles() throws IOException {
        ArrayList<Vehicle> actualVehiclesList = headOfBooking.getVehicles();
        for (Vehicle vehicle : actualVehiclesList) {
            assertTrue(vehicle instanceof Vehicle);
        }
        assertEquals(5, actualVehiclesList.size());
    }

    @Test
    @DisplayName("Tester at bookingen skrives til fil når det lages ny booking")
    public void testMakeNewBooking() throws IOException {
        String str = "dbb4dcba-1032-4619-ad38-8822780a9899;Anastasia;Lunde;+4799887766;anastasia@gmail.com;2022-06-07;2022-06-09;3;van;3;1200;0";
        headOfBooking.setSelectedVehicle(vehicle);
        headOfBooking.makeNewBooking(startDate, endDate, person, discountString, "actualNewBookingFile", constantUUIDGenerator);
        String content = (Files.readString(new File(getClass().getResource("bookings").getFile() + "actualNewBookingFile" + ".txt").toPath())).trim();
        assertTrue(content.equals(str));

    }

    @Test
    @DisplayName(("Tester at ledige vaner blir lagt til "))
    public void testSetFreeVehicles() {
        headOfBooking.setFreeVehicles(startDate, endDate);
        ArrayList<Vehicle> actualVehiclesList = headOfBooking.getFreeVehicles();
        for (Vehicle vehicle : actualVehiclesList) {
            assertTrue(vehicle instanceof Vehicle);
        }
        assertEquals(4, actualVehiclesList.size());
    }

    @Test
    @DisplayName(("Tester ledige vaner mangler en van når den allerede er booket."))
    public void testSetFreeVehicles2() throws IOException {
        headOfBooking.setSelectedVehicle(headOfBooking.getVehicles().get(3));
        headOfBooking.makeNewBooking(startDate, endDate, person, "", "excpectedNewBookingFile", constantUUIDGenerator);
        headOfBooking.setFreeVehicles(startDate, endDate);
        ArrayList<Vehicle> actualFreeVehiclesList = headOfBooking.getFreeVehicles();
        for (Vehicle vehicle : actualFreeVehiclesList) {
            assertTrue(vehicle instanceof Vehicle);
        }
        assertEquals(4, actualFreeVehiclesList.size());
    }

    @Test
    @DisplayName(("Tester om det kommer feilmelding når det er ingen ledige vaner på de ønskede datoene."))
    public void testSetFreeVehicles3() throws IOException {
        headOfBooking.setSelectedVehicle(headOfBooking.getVehicles().get(0));
        headOfBooking.makeNewBooking(startDate, endDate, person, "", "excpectedNewBookingFile", constantUUIDGenerator);
        headOfBooking.setSelectedVehicle(headOfBooking.getVehicles().get(1));
        headOfBooking.makeNewBooking(startDate, endDate, person, "", "excpectedNewBookingFile", constantUUIDGenerator);
        headOfBooking.setSelectedVehicle(headOfBooking.getVehicles().get(2));
        headOfBooking.makeNewBooking(startDate, endDate, person, "", "excpectedNewBookingFile", constantUUIDGenerator);
        headOfBooking.setSelectedVehicle(headOfBooking.getVehicles().get(3));
        headOfBooking.makeNewBooking(startDate, endDate, person, "", "excpectedNewBookingFile", constantUUIDGenerator);
        headOfBooking.setSelectedVehicle(headOfBooking.getVehicles().get(4));
        headOfBooking.makeNewBooking(startDate, endDate, person, "", "excpectedNewBookingFile", constantUUIDGenerator);
        headOfBooking = new HeadOfBooking("testVehcileFile", "testDiscountFile", "excpectedNewBookingFile");

        assertThrows(IllegalArgumentException.class, () -> {
            headOfBooking.setFreeVehicles(startDate, endDate);
        });
    }

    @Test
    @DisplayName("Tester at datoer som bookes legges til i vehicle sin datesBooked-liste")
    public void testSetDatesFromFileToDatesBooked() throws IOException {
        try {
            headOfBooking.setSelectedVehicle(vehicle);
            Files.write(headOfBooking.getVehiclePath("testBookingFilePrint"), testBookingFilePrint.getBytes());
            headOfBooking.makeNewBooking(startDate, endDate, person, discountString, ("testBookingFilePrint"), constantUUIDGenerator);
            ArrayList<LocalDate> actualDatesBooked = vehicle.getDatesBooked();
            ArrayList<LocalDate> excpectedDatesBooked = datesHandler.makeListOfWantedDates(startDate, endDate);
            assertEquals(excpectedDatesBooked, actualDatesBooked);
        } catch (IOException iOe) {
            System.out.println(iOe);
        }
    }

    @Test
    @DisplayName("Test av konstruktør med ikkeEksisterende vehicle fil")
    public void testNoExcistingVehicleFile() {
        assertThrows(FileNotFoundException.class,
                () -> new HeadOfBooking("notecxisitng1", "testDiscountFile", "excpectedNewBookingFile"));
    }

    @Test
    @DisplayName("Test av konstruktør med ikkeEksisterende discount fil")
    public void testNoExcistingDiscountFile() {
        assertThrows(FileNotFoundException.class,
                () -> new HeadOfBooking("testVehcileFile", "notecxisitng2", "excpectedNewBookingFile"));
    }

    @Test
    @DisplayName("Test av konstruktør med ikkeEksisterende booking fil")
    public void testNoExcistingBookingFile() {
        assertThrows(FileNotFoundException.class,
                () -> new HeadOfBooking("testVehcileFile", "testDiscountFile", "notExcisitngBookingFile"));
    }

    @Test
    @DisplayName("Test av set av setDatesFromFileToDatesBooked(via konstruktør) med fil med ikke-riktig input")
    public void testSetDatesFromFileToDatesBookedInvalidInput() throws IOException {
        Files.write(fileHandler.getBookingPath("unvalidBookingPrint"), testUnvalidBookingFilePrint.getBytes());
        assertThrows(IllegalArgumentException.class,
                () -> new HeadOfBooking("testVehcileFile", "testDiscountFile", "unvalidBookingPrint"));
    }

    @AfterEach
    public void tearDown() throws IOException {
        headOfBooking = new HeadOfBooking("testVehcileFile", "testDiscountFile", "excpectedNewBookingFile");
        fileHandler.getSelectedFile("actualNewBookingFile").delete();
        fileHandler.getSelectedFile("excpectedNewBookingFile").delete();
        headOfBooking.getSelectedFile("testVehcileFile").delete();
        fileHandler.getSelectedFile("testDiscountFile").delete();
        headOfBooking.getSelectedFile("testBookingFilePrint").delete();
        fileHandler.getSelectedFile("unvalidBookingPrint").delete();
    }
}
