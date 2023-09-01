package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AdminModeTest {
    private AdminMode adminMode;
    private ArrayList<String> testBookingList = new ArrayList<>();
    private String allOutput;
    private String phoneOutput;
    private String bookingreferenceOutput;

    @BeforeEach
    public void setUp(){
        adminMode = new AdminMode();
        testBookingList.add("66482a87-454e-4f77-9531-f7dcdc5e1ff3;Tore;Sagen;+4799454545;toresagen@gmail.com;2022-04-25;2022-04-28;4;van;3;3600;0;");
        testBookingList.add("dbb4dcba-1032-4619-ad38-8822780a9899;Per;Halvorsen;+4798765432;per@j.no;2022-04-11;2022-04-15;0;van;4;4800;0;");
        testBookingList.add("9fb408b1-1ecf-487a-a80a-4f8985b16b64;Marius;Nilsen;+4790070111;mani@online.no;2022-04-19;2022-04-23;1;van;3;2600;0;");
        phoneOutput = "Bookingreference:9fb408b1-1ecf-487a-a80a-4f8985b16b64Name:MariusNilsenPhonenumber:+4790070111Datesbooked:2022-04-19-2022-04-23VehicleID:1Price:2600Discount:0%"; 
        allOutput = "Bookingreference:dbb4dcba-1032-4619-ad38-8822780a9899Name:PerHalvorsenPhonenumber:+4798765432Datesbooked:2022-04-11-2022-04-15VehicleID:0Price:4800Discount:0%Bookingreference:9fb408b1-1ecf-487a-a80a-4f8985b16b64Name:MariusNilsenPhonenumber:+4790070111Datesbooked:2022-04-19-2022-04-23VehicleID:1Price:2600Discount:0%Bookingreference:66482a87-454e-4f77-9531-f7dcdc5e1ff3Name:ToreSagenPhonenumber:+4799454545Datesbooked:2022-04-25-2022-04-28VehicleID:4Price:3600Discount:0%";
        bookingreferenceOutput = "Bookingreference:dbb4dcba-1032-4619-ad38-8822780a9899Name:PerHalvorsenPhonenumber:+4798765432Datesbooked:2022-04-11-2022-04-15VehicleID:0Price:4800Discount:0%";
    }


    @Test
    @DisplayName("Tester verifyAdminLogIn")
    public void testVerifyAdminLogIn(){
        assertThrows(IllegalArgumentException.class, () -> adminMode.verifyAdminLogIn("", ""));
        assertThrows(IllegalArgumentException.class, () -> adminMode.verifyAdminLogIn("", "admin123"));
        assertThrows(IllegalArgumentException.class, () -> adminMode.verifyAdminLogIn("admin", ""));
        assertThrows(IllegalArgumentException.class, () -> adminMode.verifyAdminLogIn("aa", "admin123"));
        assertThrows(IllegalArgumentException.class, () -> adminMode.verifyAdminLogIn("admin", "kul"));
        assertThrows(IllegalArgumentException.class, () -> adminMode.verifyAdminLogIn("Admin", "Admin123"));
        adminMode.verifyAdminLogIn("admin", "admin123");
    }

    @Test
    @DisplayName("Tester getBookingsWithSearchword")
    public void testGetBookingsWithSearchword(){
        assertEquals(adminMode.getBookingsWithSearchword("+4790070111", testBookingList).replaceAll("\\s+", ""), phoneOutput); 
        assertEquals(adminMode.getBookingsWithSearchword("all", testBookingList).replaceAll("\\s+", ""), allOutput);
        assertEquals(adminMode.getBookingsWithSearchword("dbb4dcba-1032-4619-ad38-8822780a9899", testBookingList).replaceAll("\\s+", ""), bookingreferenceOutput);
        
        assertThrows(IllegalArgumentException.class, () -> adminMode.getBookingsWithSearchword("noe annet", testBookingList));
        assertThrows(IllegalArgumentException.class, () -> adminMode.getBookingsWithSearchword("", testBookingList));
        assertThrows(IllegalArgumentException.class, () -> adminMode.getBookingsWithSearchword("+4799999999", testBookingList));
    }  
    
}
