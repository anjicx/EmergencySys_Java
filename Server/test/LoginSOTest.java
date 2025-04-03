import domain.Doctor;
import domain.Patient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import so.doctor.LoginSO;

import static org.junit.Assert.*;

public class LoginSOTest {
    private LoginSO so;
    private Doctor object;

    @Before
    public void setUp() {
        so = new LoginSO();
        object = new Doctor();
    }

    @After
    public void tearDown() {
        so = null;
        object = null;
    }
     @Test
    public void testExecuteInvalidUsername() {
        object.setUsername("");
        object.setPassword("dmdndn");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> so.execute(object));
    }

    @Test
    public void testExecuteInvalidData() {
       //ako je prosledjen nevalidan tip podatka operaciji
        Exception exception = assertThrows(IllegalArgumentException.class, () -> so.execute(new Patient()));
    }
    @Test
    public void testExecuteInvalidPass() {
         //lozinka nije dobra
        object.setUsername("mila");
        object.setPassword("");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> so.execute(object));
    }

    @Test
    public void testExecuteValidOperation() {
        object.setUsername("mila");
        object.setPassword("mila");

        try {
            so.execute(object);
            assertNotNull(so.getLoggedDoctor());
        } catch (Exception ex) {
            fail("Validna prijava nije uspela: " + ex.getMessage());
        }
    }

    @Test
    public void testExecuteInvalidOperation() {
        object.setUsername("matija"); // Postoji korisnik, ali lozinka je pogrešna
        object.setPassword("pogresna");
        try {
            so.execute(object);
            assertNull(so.getLoggedDoctor()); // Očekujemo da ne pronađe korisnika
        } catch (Exception ex) {
            fail("Neuspešna prijava bi trebala biti bez greške u sistemu.");
        }
    }
    

    
    
}
