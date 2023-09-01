package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class DiscountHandlerTest {
    private DiscountHandler discountHandlerActual;
    private DiscountHandler discountHandlerExpected;

    private static LinkedHashMap<String, Integer> exceptedDiscounts = new LinkedHashMap<>();
    private static LinkedHashMap<String, Integer> actualDiscounts = new LinkedHashMap<>();

    private static final String testFileContent = """
        gammelKu;30 
        kanJegGåPåDo;10
        discount1;10
        discount2;20     
            """;;
     private static final String testFileContent2 = """
        gammelKu;30
            """;;

    @BeforeEach
    public void setup(){
        discountHandlerExpected = new DiscountHandler();
        discountHandlerActual = new DiscountHandler();
       
        exceptedDiscounts.put("gammelKu", 30);
        exceptedDiscounts.put("kanJegGåPåDo", 10);

        try (FileWriter writer = new FileWriter(discountHandlerActual.getSelectedFile("discountFileTest"))){
            writer.write("gammelKu;30" + "\n");
            writer.write("kanJegGåPåDo;10");
        }
        catch(IOException e){
            System.out.println("Could not write to testFile");
        }
    }

    @Test
    @DisplayName("Tester om rabattene leses fra fil")
    public void testSetUpDiscounts() throws FileNotFoundException{
        discountHandlerActual.setUpDiscounts("discountFileTest");
        actualDiscounts = discountHandlerActual.returnDiscounts("discountFileTest");
        assertEquals(exceptedDiscounts, actualDiscounts);
    }

    @Test
    @DisplayName("Tester å legge til rabattkode")
    public void testAddDiscount(){
        try{ 
            Files.write(discountHandlerExpected.getDiscountFilPath("dicountFileExpected"), testFileContent.getBytes());
            discountHandlerActual.setUpDiscounts("discountFileTest");
            discountHandlerActual.addDiscount("discount1", 10, "discountFileTest");
            discountHandlerActual.addDiscount("discount2", 20, "discountFileTest");
            discountHandlerActual.checkIfValidDiscount("discount1");
            discountHandlerActual.checkIfValidDiscount("discount2");
            assertThrows(IllegalArgumentException.class, () -> {
                discountHandlerActual.checkIfValidDiscount("ikkeGyldigKode");
            });

            byte[] actual = Files.readAllBytes(discountHandlerExpected.getDiscountFilPath("discountFileTest"));
            byte[] expected = Files.readAllBytes(discountHandlerActual.getDiscountFilPath("dicountFileExpected"));
            assertTrue(Arrays.equals(actual, expected));
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

    @Test
    @DisplayName("Tester å fjerne rabattkode")  
    public void testRemoveDiscount(){
        try{ 
            Files.write(discountHandlerExpected.getDiscountFilPath("dicountFileExpected"), testFileContent2.getBytes());
            discountHandlerActual.setUpDiscounts("discountFileTest");
            discountHandlerActual.removeDiscount("kanJegGåPåDo", "discountFileTest");
            assertNotEquals(exceptedDiscounts, actualDiscounts);
            assertThrows(IllegalArgumentException.class, () -> {
                discountHandlerActual.checkIfValidDiscount("kanJegGåPåDo");
            });
            byte[] actual = Files.readAllBytes(discountHandlerActual.getDiscountFilPath("discountFileTest"));
            byte[] expected = Files.readAllBytes(discountHandlerExpected.getDiscountFilPath("dicountFileExpected"));
            assertTrue(Arrays.equals(actual, expected));
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

    @Test 
    @DisplayName("Tester å hente ut rabatten som gis")
    public void testGetDiscountValue() throws FileNotFoundException{
        discountHandlerExpected.setUpDiscounts("discountFileTest");
        
        assertEquals(10, discountHandlerExpected.getDiscountValue("kanJegGåPåDo"));
        assertEquals(30, discountHandlerExpected.getDiscountValue("gammelKu"));
        assertEquals(0, discountHandlerExpected.getDiscountValue(null));
        assertEquals(0, discountHandlerExpected.getDiscountValue(""));
        assertNotEquals(20, discountHandlerExpected.getDiscountValue("kanJegGåPåDo"));
        assertThrows(IllegalArgumentException.class, () -> {
            discountHandlerExpected.getDiscountValue("ugyldigInput");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            discountHandlerExpected.getDiscountValue("-1");
        });  
    }

    @Test
    @DisplayName("Tester om checkIfValidDiscount metoden fungerer som den skal.")
    public void testCheckIfValidDiscount() throws FileNotFoundException{
        discountHandlerExpected.setUpDiscounts("discountFileTest");
        
        discountHandlerExpected.checkIfValidDiscount("kanJegGåPåDo");
        assertThrows(IllegalArgumentException.class, () -> {
            discountHandlerExpected.checkIfValidDiscount("langInputSomIkkeSkalVæreGyldig");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            discountHandlerExpected.checkIfValidDiscount("ikkeEnRabattKode");
        });
    }

    @Test
    @DisplayName("Tester SetupDiscount med ikke-eksisterende fil")
    public void testSetUpDiscountsNotExcistingFile(){
        DiscountHandler newDiscountHandler = new DiscountHandler();
            assertThrows(FileNotFoundException.class, () -> {
                newDiscountHandler.setUpDiscounts("notExcistingFile");
            });

    }

    @AfterEach
    public void tearDown(){
        actualDiscounts.clear();
        discountHandlerActual.getSelectedFile("discountFileTest").delete();
        discountHandlerActual.getSelectedFile("dicountFileExpected").delete();
        discountHandlerActual.getSelectedFile("testDiscountFile").delete();
    }

}
