package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PersonTest {

    private Person person;

    @BeforeEach
    public void setUp(){
        person = new Person("Gunnar", "Halvorsen", "+4795034544", "30", "gunnar.halvorsen@gmail.com");
    }

    @Test
    @DisplayName("Sjekker konstruktøren")
    public void testConstructor(){
        assertEquals(person.getFirstName(), "Gunnar");
        assertEquals(person.getLastName(), "Halvorsen");
        assertEquals(person.getPhoneNumber(), "+4795034544");
        assertEquals(person.getAge(), "30");
        assertEquals(person.getEmail(), "gunnar.halvorsen@gmail.com");
    }

    @Test
    @DisplayName("Sjekker om fornavn og etternavn er gyldige")
    public void testName(){
        assertThrows(IllegalArgumentException.class , () -> { //Tester ingen input fornavn
            person = new Person("", "Halvorsen", "+4795034544", "30", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //Tester ingen input etternavn
            person = new Person("Gunnar", "", "+4795034544", "30", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //Tester for langt etternavn
            person = new Person("Gunnar", "halvorsenhalvorsenhalvorsenghjksdjkjdkjfkdjfkd", "+4795034544", "30", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //Tester for langt fornavn
            person = new Person("Gunnarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr", "Halvorsen", "+4795034544", "30", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //Tester navn med tall
            person = new Person("Gunnar", "Halv2ers", "+4795034544", "30", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //Tester navn med ugyldig tegn
            person = new Person("Gunnar", "Halvors&n", "+4795034544", "30", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //Tester navn med ugyldig tegn bokstav
            person = new Person("Gunnar-Ole", "Halvors&n", "+4795034544", "30", "gunnar.halvorsen@gmail.com");
        });
    }

    @Test
    @DisplayName("Sjekker tilfeller med bindestrek i navn")
    public void testNameWithLine(){
        person = new Person("Gunnar-Ole", "Halvorsen", "+4795034544", "30", "gunnar.halvorsen@gmail.com");
        assertEquals("Gunnar-Ole", person.getFirstName()); //tester at det er lov med bindestrek
        person = new Person("Gunnar-Oleeeeeeeeeeeeeeeeeee", "Halvorsen", "+4795034544", "30", "gunnar.halvorsen@gmail.com");
        assertEquals("Gunnar-Oleeeeeeeeeeeeeeeeeee", person.getFirstName()); //tester at det er lov med fornavn på rett under 30 bokstaver  
        assertThrows(IllegalArgumentException.class , () -> { //Tester bindestrek-navn med tall
            person = new Person("Gunnar-Ol1n", "Halvorsen", "+4795034544", "30", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //Tester bindestrek-navn ugyldig tegn
            person = new Person("Gunnar-Ol;n", "Halvorsen", "+4795034544", "30", "gunnar.halvorsen@gmail.com");
        });
    }
    
    @Test
    @DisplayName("Sjekker telefon nummer")
    public void testPhoneNumber(){
        assertThrows(IllegalArgumentException.class , () -> { //Tester uten +47
            person = new Person("Gunnar", "Halvorsen", "95034544", "30", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //Tester for kort nummer
            person = new Person("Gunnar", "Halvorsen", "+479", "30", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //Tester for langt nummer
            person = new Person("Gunnar", "Halvorsen", "+47950345442346", "30", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //Tester feil start på nummereret
            person = new Person("Gunnar", "Halvorsen", "+4739099334", "30", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //Sjekker med bokstaver i nummeret
            person = new Person("Gunnar", "Halvorsen", "+47950abc44", "30", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //Tester tom input
            person = new Person("Gunnar", "Halvorsen", "", "30", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //Tester for ugylidg tegn
            person = new Person("Gunnar", "Halvorsen", "+479920348&", "30", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //Tester for ingen input
            person = new Person("Gunnar", "Halvorsen", "", "30", "gunnar.halvorsen@gmail.com");
        });
        
    }

    @Test
    @DisplayName("Sjekker alder input")
    public void testAge(){
        assertThrows(IllegalArgumentException.class , () -> { //test av for lang inpustreng
            person = new Person("Gunnar", "Halvorsen", "+4795034544", "335735352839455", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //tester negativt tall
            person = new Person("Gunnar", "Halvorsen", "+4795034544", "-7", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //tester boksatver
            person = new Person("Gunnar", "Halvorsen", "+4795034544", "ni", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //tester boksatver
            person = new Person("Gunnar", "Halvorsen", "+4795034544", "&/", "gunnar.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //tester ingen input
            person = new Person("Gunnar", "Halvorsen", "+4795034544", "", "gunnar.halvorsen@gmail.com");
        });
    }

    @Test
    @DisplayName("Sjekker mail input")
    public void testEmail(){ 
        assertThrows(IllegalArgumentException.class , () -> { //tester tre punktom i "username"
            person = new Person("Gunnar", "Halvorsen", "+4795034544", "30", "gunnar.adrian.halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //tester username kortere enn to tegn
            person = new Person("Gunnar", "Halvorsen", "+4795034544", "30", "g@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //tester første tegn tall
            person = new Person("Gunnar", "Halvorsen", "+4795034544", "30", "123gunnar@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //tester flere krøllalfa enn 1
            person = new Person("Gunnar", "Halvorsen", "+4795034544", "30", "gunnar@halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //tester mellomrom i mailadresse
            person = new Person("Gunnar", "Halvorsen", "+4795034544", "30", "gunnar halvorsen@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //tester ugyldige tegn
            person = new Person("Gunnar", "Halvorsen", "+4795034544", "30", "gunnar.halvors%n@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //tester username kortere lengre enn 30 tegn
            person = new Person("Gunnar", "Halvorsen", "+4795034544", "30", "gunnar.halvorsennnnnnnnnnnnnnnn123456789@gmail.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //tester domene1 med tall
            person = new Person("Gunnar", "Halvorsen", "+4795034544", "30", "gunnar.halvorsen@gmail123.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //tester domene2 med tall
            person = new Person("Gunnar", "Halvorsen", "+4795034544", "30", "gunnar.halvorsen@gmail.com123");
        });
        assertThrows(IllegalArgumentException.class , () -> { //tester domene1 med mer enn 20 bokstaver
            person = new Person("Gunnar", "Halvorsen", "+4795034544", "30", "gunnar.halvorsen@gmailllllllllllllllllll.com");
        });
        assertThrows(IllegalArgumentException.class , () -> { //tester domene1 med mer enn 20 bokstaver
            person = new Person("Gunnar", "Halvorsen", "+4795034544", "30", "gunnar.halvorsen@gmail.commmmmmmmmm");
        });
        assertThrows(IllegalArgumentException.class , () -> { //tester dingen input
            person = new Person("Gunnar", "Halvorsen", "+4795034544", "30", "");
        });
    }

}
    
