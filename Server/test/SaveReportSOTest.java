
import domain.Diagnosis;
import domain.DiagnosisReport;
import domain.Doctor;
import domain.Patient;
import domain.Procedure;
import domain.Report;
import domain.ReportItem;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import so.report.SaveReportSO;

/**
 *
 * @author USER
 */
public class SaveReportSOTest {
    private SaveReportSO so;
    private Report report;
    private ReportItem reportItem;
    private DiagnosisReport diagnosisReport;
    private Diagnosis diagnosis;
    private Procedure procedure;
    private Doctor doctor;
    private Patient patient;
    @Before
    public void setUp() {
        so = new SaveReportSO();
        doctor = new Doctor();
        doctor.setId(1);
        patient = new Patient();
        patient.setId(1);
        procedure = new Procedure();
        procedure.setId(1);
        diagnosis = new Diagnosis();
        diagnosis.setId(42874);
        // prvo inicijalizacija izvestaja pa onda stavki i dijagnoza vezanih za njega
        report = new Report();
        report.setPatientsAdmitionDate(LocalDate.of(2025, 4, 1));
        report.setPatientsAdmitionTime(LocalTime.of(12, 0, 0));
        report.setGivenTherapy("Terapija A");
        report.setAnamnesis("Pacijent ima simptome X");
        report.setDoctor(doctor);
        report.setPatient(patient);
        // inicijalizujemo stavku izvestaja
        reportItem = new ReportItem();
        reportItem.setIdReport(report.getId());
        reportItem.setDate(LocalDate.of(2025, 4, 1));
        reportItem.setTime(LocalTime.of(12, 30, 0));
        reportItem.setProcedure(procedure);
        // inicijalizujemo dijagnozu izvestaja
        diagnosisReport = new DiagnosisReport();
        diagnosisReport.setReport(report);  // Sada report postoji
        diagnosisReport.setDiagnosis(diagnosis);
        diagnosisReport.setCategory("Primary");
        // dodajemo stavku i dijagnozu u izvestaj
        report.getDiagnosis().add(diagnosisReport);
        report.getItems().add(reportItem);
    }

    @After
    public void tearDown() {
        so = null;
        report = null;
        reportItem = null;
        diagnosisReport = null;
        diagnosis = null;
        procedure = null;
        doctor = null;
        patient = null;
    }

    // Test za validan unos
    @Test
    public void testExecuteValidReport() {
        try {
            so.execute(report);
            assertNotNull(so.getDomainObject());
        } catch (Exception e) {
            fail("Validan unos nije uspeo: " + e.getMessage());
        }
    }
    //Testovi za nevalidan Report
    // Test za nevalidan objekat (nije Report)
    @Test
    public void testExecuteInvalidObjectType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> so.execute(new Doctor()));
    }

    // Test za nevalidan Report (nedostaju ključna polja)
    @Test
    public void testExecuteInvalidReportDate() {
        report.setPatientsAdmitionDate(null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> so.execute(report));
        assertEquals("Nijedan od polja ne sme biti null ili prazno.", exception.getMessage());
    }
    @Test
    public void testExecuteInvalidReportTime() {
        report.setPatientsAdmitionTime(null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> so.execute(report));
        assertEquals("Nijedan od polja ne sme biti null ili prazno.", exception.getMessage());
    }

      // Testovi za nevalidan ReportItem (nedostaju atributi)
    @Test
    public void testExecuteInvalidReportItemProcedure() {
        reportItem.setProcedure(null);
        report.getItems().add(reportItem);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> so.execute(report));
        assertEquals("Nijedan od parametara za ReportItem ne sme biti null ili 0.", exception.getMessage());
    }
    @Test
    public void testExecuteInvalidReportItemDate() {
        reportItem.setDate(null);
        report.getItems().add(reportItem);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> so.execute(report));
        assertEquals("Nijedan od parametara za ReportItem ne sme biti null ili 0.", exception.getMessage());
    }
      @Test
    public void testExecuteInvalidReportItemTime() {
        reportItem.setTime(null);
        report.getItems().add(reportItem);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> so.execute(report));
        assertEquals("Nijedan od parametara za ReportItem ne sme biti null ili 0.", exception.getMessage());
    }
 
    // Testovi za nevalidan DiagnosisReport (nedostaju atributi)
    @Test
    public void testExecuteInvalidDiagnosisReport() {
        diagnosisReport.setDiagnosis(null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> so.execute(report));
        assertEquals("Report i Diagnosis moraju biti postavljeni i imati validne ID vrednosti.", exception.getMessage());
    }
    
    @Test
    public void testExecuteInvalidDiagnosisReportCategory() {
        diagnosisReport.setCategory(null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> so.execute(report));
        assertEquals("Morate uneti kategoriju izveštaja.", exception.getMessage());
    }
    

}
