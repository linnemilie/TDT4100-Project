package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class BookingTest {
    private Person person;
    private Vehicle vehicle;
    String discountCode;
    String emptyDiscountCode;
    private DiscountHandler discountHandler;
    private RandomUUIDGenerator randomeUUIDGenerator = new RandomUUIDGenerator();

    public static final String discountFile = """
            SUMER22;20
            """;;

    @BeforeEach
    public void setup() throws IOException{
        person = new Person("Marion", "Ravn", "+4790070222", "55", "tumy1@vaf.no");
        vehicle = new Vehicle("0", "van", "Annie", 3, "Manual", 500);
        discountCode = "SUMER22";
        emptyDiscountCode = "";
        discountHandler = new DiscountHandler();
        Files.write(discountHandler.getDiscountFilPath("discountBookingTest"), discountFile.getBytes());
        discountHandler.setUpDiscounts("discountBookingTest");
    }


    @Test
    @DisplayName("Teser at konstruktør setter rtiktige verdier, uten noe rabattkode")
    public void testConstructor1(){
        Booking booking1 = new Booking(LocalDate.parse("2022-06-01"), LocalDate.parse("2022-06-04"), person, vehicle, emptyDiscountCode, randomeUUIDGenerator);
        assertEquals(person, booking1.getPerson());
        assertEquals(vehicle, booking1.getVehicle());
        assertEquals(1500, booking1.getPrice()); 
        assertEquals(0, booking1.getMoneySaved());
        assertEquals(LocalDate.parse("2022-06-01"), booking1.getStartDate());
        assertEquals(LocalDate.parse("2022-06-04"), booking1.getEndDate());
        assertEquals(0, booking1.getDiscount());
    }

    @Test
    @DisplayName("Teser at konstruktør setter rtiktige verdier, med gyldig rabattkode")
    public void testConstructor2(){
        Booking booking2 = new Booking(LocalDate.parse("2022-06-01"), LocalDate.parse("2022-06-04"), person, vehicle, discountCode, randomeUUIDGenerator);
        assertEquals(person, booking2.getPerson());
        assertEquals(vehicle, booking2.getVehicle());
        assertEquals(1200, booking2.getPrice());
        assertEquals(300, booking2.getMoneySaved());
        assertEquals(LocalDate.parse("2022-06-01"), booking2.getStartDate());
        assertEquals(LocalDate.parse("2022-06-04"), booking2.getEndDate());
        assertEquals(20, booking2.getDiscount());
    }

    @Test
    @DisplayName("Test av getVehicleDiscountText uten noen rabattkode")
    public void testVehicleDiscountText1(){
        Booking booking1 = new Booking(LocalDate.parse("2022-06-01"), LocalDate.parse("2022-06-04"), person, vehicle, emptyDiscountCode, randomeUUIDGenerator);
        String excpectedVehicleDiscountText = "";
        String actualVehicleDiscountText = booking1.getVehicleDiscountText(emptyDiscountCode);
        assertEquals(excpectedVehicleDiscountText, actualVehicleDiscountText);
    }

    @Test
    @DisplayName("Test av getVehicleDiscountText med gyldig rabattkode")
    public void testVehicleDiscountText2(){
        Booking booking2 = new Booking(LocalDate.parse("2022-06-01"), LocalDate.parse("2022-06-04"), person, vehicle, discountCode, randomeUUIDGenerator);
        String excpectedVehicleDiscountText = "Discount: " + 20 + "%" + "\n";
        String actualVehicleDiscountText = booking2.getVehicleDiscountText(discountCode);
        assertEquals(excpectedVehicleDiscountText, actualVehicleDiscountText);
    }

    @Test
    @DisplayName("Test av getPriceForVechicleText uten noen rabattkode")
    public void testPriceForVechicleText1(){
        Booking booking1 = new Booking(LocalDate.parse("2022-06-01"), LocalDate.parse("2022-06-04"), person, vehicle, emptyDiscountCode, randomeUUIDGenerator);
        String excpectedPriceForVehicleText = "Total price: " + 1500  + "\n" ;
        String actualPriceForVehicleText = booking1.getPriceForVechicleText();
        assertEquals(excpectedPriceForVehicleText, actualPriceForVehicleText);
    }

    @Test
    @DisplayName("Test av getPriceForVechicleText med rabattkode")
    public void testPriceForVechicleText2(){
        Booking booking2 = new Booking(LocalDate.parse("2022-06-01"), LocalDate.parse("2022-06-04"), person, vehicle, discountCode, randomeUUIDGenerator);
        String excpectedPriceForVehicleText = "Total price: " + 1200  + "\n" 
        + "Discount: " + 20 + "%" + "\n" 
        + "Money saved: " + 300;
        String actualPriceForVehicleText = booking2.getPriceForVechicleText();
        assertEquals(excpectedPriceForVehicleText, actualPriceForVehicleText);
    }


    @AfterEach
    public void tearDown() throws FileNotFoundException, IOException{
        discountHandler.removeDiscount("SUMER22", "discountBookingTest");
        discountHandler.getSelectedFile("discountBookingTest").delete();
    }
    
}
