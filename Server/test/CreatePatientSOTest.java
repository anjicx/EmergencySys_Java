
import domain.Doctor;
import domain.Patient;
import domain.Place;
import java.time.LocalDate;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import so.patient.CreatePatientSO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author USER
 */
public class CreatePatientSOTest {
    private  CreatePatientSO so;
    private Patient patient;

    @Before
    public void setUp() {
        so = new CreatePatientSO();
        Place p=new Place();
        p.setId(1);
        patient = new Patient("Andrea","Maric",LocalDate.of(2002,1,1),"0654283947","0101691605011",p);
    }

    @After
    public void tearDown() {
        so = null;
        patient = null;
    }  
    // Test za nevalidan objekat (nije Patient)
    @Test
    public void testExecuteInvalidObjectType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> so.execute(new Doctor()));
    }
    
     @Test
    public void testExecuteValidPatient() {
   try {
        so.execute(patient);
    } catch (Exception e) {
        fail("Validan pacijent ne bi trebalo da izazove izuzetak: " + e.getMessage());
    }
    }

    @Test
    public void testExecuteNullPatient() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> so.execute(null));
        assertEquals("Očekivan objekat pacijenta", exception.getMessage());
    }

    @Test
    public void testExecuteInvalidName() {
        patient.setName(""); // Prazno ime
        Exception exception = assertThrows(IllegalArgumentException.class, () -> so.execute(patient));
        assertEquals("Ime mora biti popunjeno i sadržavati samo slova.", exception.getMessage());
    }

    @Test
    public void testExecuteInvalidJMBG() {
        patient.setJmbg("12345"); // Nevalidan JMBG
        Exception exception = assertThrows(IllegalArgumentException.class, () -> so.execute(patient));
        assertEquals("JMBG mora sadržavati tačno 13 cifara.", exception.getMessage());
    }

    @Test
    public void testExecuteInvalidPhoneNumber() {
        patient.setNumber("abc123"); // Nevalidan format broja
        Exception exception = assertThrows(IllegalArgumentException.class, () -> so.execute(patient));
        assertEquals("Broj telefona mora biti u ispravnom formatu.", exception.getMessage());
    }
        @Test
    public void testExecuteInvalidPlace() {
        patient.setPlace(null); // Mesto nije postavljeno
        Exception exception = assertThrows(IllegalArgumentException.class, () -> so.execute(patient));
        assertEquals("Mesto mora biti validno postavljeno.", exception.getMessage());
    }
}
