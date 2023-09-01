package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DateComparatorTest {
    private String bookingOne;
    private String bookingTwo;
    private String bookingThree;
    private DateComparator dateComparator;
   
    @BeforeEach
    public void setUp(){
        bookingOne = "dbb4dcba-1032-4619-ad38-8822780a9899;Per;Halvorsen;+4798765432;per@j.no;2022-04-11;2022-04-15;0;Louise;4800;";
        bookingTwo = "1de89bbc-d4ae-4c40-ad66-6211d3942a8d;Donald;Trump;+4740070345;expresident@gmail.com;2022-04-18;2022-04-23;2;van;6900;";
        bookingThree = "1de89bbc-d4ae-4c40-ad66-6211d3942a8d;Donald;Trump;+4740070345;expresident@gmail.com;2022-04-k8;2022-04-23;2;van;6900;";
        dateComparator = new DateComparator();
    }

    @Test
    @DisplayName("Tester om bookingene sorteres med eldste dato først")
    public void compareTest(){
        assertEquals(-1, dateComparator.compare(bookingOne, bookingTwo));
        assertEquals(1, dateComparator.compare(bookingTwo, bookingOne));
        assertEquals(0, dateComparator.compare(bookingOne, bookingOne));
    }

    @Test
    @DisplayName("Tester at DateTimeParseException utløses ved dato på ugyldig format")
    public void exceptionTest(){
        assertThrows(DateTimeParseException.class, () -> {
            dateComparator.compare(bookingOne, bookingThree);
        });
    }
}
