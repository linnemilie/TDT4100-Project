package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DatesHandlerTest {
    private DatesHandler datesHandler;
    private LocalDate dateNull;
    private LocalDate dateOne;
    private LocalDate dateTwo;
    private LocalDate dateBetween1;
    private LocalDate dateBetween2;
    private LocalDate dateThree;
    private ArrayList<LocalDate> listOfDatesBetweenDateOneDateTwo;
    private Vehicle vehicle;

    @BeforeEach
    public void setUp(){
        datesHandler = new DatesHandler();
        dateOne = LocalDate.parse("2022-09-25");
        dateBetween1 = LocalDate.parse("2022-09-26");
        dateBetween2 = LocalDate.parse("2022-09-27");
        dateTwo = LocalDate.parse("2022-09-28");
        dateThree = LocalDate.parse("2021-10-04");
        listOfDatesBetweenDateOneDateTwo = new ArrayList<>(){
            {
                add(dateOne);
                add(dateBetween1);
                add(dateBetween2);
                add(dateTwo);
            }
        };
        vehicle = new Vehicle("1", "van", "Clara", 3, "Automatic", 300);
    }
   

    @Test
    @DisplayName("Tester om checkIfValidDates utløser IllegalArgumentException ved ugyldige datoer.")
    public void testCheckIfValidDates(){
        datesHandler.checkIfValidDates(dateOne, dateTwo);
        Assertions.assertThrows(IllegalArgumentException.class, () -> datesHandler.checkIfValidDates(dateTwo, dateOne));
        Assertions.assertThrows(IllegalArgumentException.class, () -> datesHandler.checkIfValidDates(dateThree, dateOne));
        Assertions.assertThrows(IllegalArgumentException.class, () -> datesHandler.checkIfValidDates(dateNull, dateNull));
    }

    @Test
    @DisplayName("Tester om getDurationOfBooking gir riktig antall dager, og at det utløses IllegalArgumentException dersom det er negativt eller null antall dager.")
    public void testGetDurationOfBooking(){
        assertEquals(3, datesHandler.getDurationOfBooking(dateOne, dateTwo));
        Assertions.assertThrows(IllegalArgumentException.class, () -> datesHandler.getDurationOfBooking(dateTwo, dateOne));
        Assertions.assertThrows(IllegalArgumentException.class, () -> datesHandler.getDurationOfBooking(dateOne, dateOne));
 }

    @Test
    @DisplayName("Tester om makeListOfWantedDates lager en riktig liste, og at det utløses IllegalArgumentException dersom datoene er ugyldige.")
    public void testMakeListOfWantedDates(){
        assertEquals(listOfDatesBetweenDateOneDateTwo, datesHandler.makeListOfWantedDates(dateOne, dateTwo));
        Assertions.assertThrows(IllegalArgumentException.class, () -> datesHandler.makeListOfWantedDates(dateTwo, dateOne));
        Assertions.assertThrows(IllegalArgumentException.class, () -> datesHandler.makeListOfWantedDates(dateOne, dateOne));
    }

    @Test
    @DisplayName("Tester om metoden addDatesToVehicleChosen legger til datoer til valgt vehicle.") 
    public void testAddDatesToVehicleChosen(){
        datesHandler.addDatesToVehicleChosen(dateOne, dateTwo, vehicle);
        assertEquals(listOfDatesBetweenDateOneDateTwo, vehicle.getDatesBooked());
    }

    @Test 
    @DisplayName("Tester metoden addDatesToVehicleChosen når det ligger datoer i listen fra før.")
    public void testAddDatesToVehicleChosen2(){
        listOfDatesBetweenDateOneDateTwo.add(dateThree);
        datesHandler.addDatesToVehicleChosen(dateOne, dateTwo, vehicle);
        vehicle.datesBooked.add(dateThree);
        assertEquals(listOfDatesBetweenDateOneDateTwo, vehicle.getDatesBooked());
    }

    @AfterEach
    public void tearDown() {
        datesHandler = null;
        dateOne = null;
        dateTwo = null;
        dateThree = null;
        dateBetween1 = null; 
        dateBetween2 = null; 
        vehicle = null;
        listOfDatesBetweenDateOneDateTwo = null;
    }
}
