package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VehicleTest { 
   
    @Test
    @DisplayName("Sjekker at konstruktøren setter verdiene riktig.")
    public void testConstructor(){
        Vehicle vehicle = new Vehicle("0", "van", "Annie", 3, "Manual", 500);
        assertEquals(vehicle.getId(), "0");
        assertEquals(vehicle.getVehicleType(), "van");
        assertEquals(vehicle.getVehicleName(), "Annie");
        assertEquals(vehicle.getPassangerCapacity(), 3);
        assertEquals(vehicle.getGearType(), "Manual");
        assertEquals(vehicle.getPricePerDay(), 500);
    } 
    
}
