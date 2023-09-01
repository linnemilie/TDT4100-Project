package project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MotorcycleTest {

    @Test
    @DisplayName("Sjekker at konstruktøren setter verdiene riktig.")
    public void testConstructor(){
        Motorcycle motorcycle= new Motorcycle("5", "Roar", 2, "Manual", 500);
        assertEquals(motorcycle.getId(), "5");
        assertEquals(motorcycle.getVehicleType(), "motorcycle");
        assertEquals(motorcycle.getVehicleName(), "Roar");
        assertEquals(motorcycle.getPassangerCapacity(), 2);
        assertEquals(motorcycle.getGearType(), "Manual");
        assertEquals(motorcycle.getPricePerDay(), 500);
    } 
    
}
