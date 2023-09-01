package project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VanTest {

    @Test
    @DisplayName("Sjekker at konstruktøren setter verdiene riktig.")
    public void testConstructor(){
        Van van = new Van("0", "Annie", 3, "Manual", 500);
        assertEquals(van.getId(), "0");
        assertEquals(van.getVehicleType(), "van");
        assertEquals(van.getVehicleName(), "Annie");
        assertEquals(van.getPassangerCapacity(), 3);
        assertEquals(van.getGearType(), "Manual");
        assertEquals(van.getPricePerDay(), 500);
    } 
}
