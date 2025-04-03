/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class Report implements AbstractDO {
    //atributi reporta
    private long id;
    private LocalDate patientsAdmitionDate;
    private LocalTime patientsAdmitionTime;
    private String givenTherapy;
    private String anamnesis;
    
    //spoljni kljuc
    private Doctor doctor;//doctor
    private Patient patient;//pacijent na kog se odnosi ovo
    //slab objekat
    private List<ReportItem>items;
    private List<DiagnosisReport>addedDiagnosis;
    public Report() {//inicijal lista da iybegne nullpointer
         this.items = new ArrayList<>();
         this.addedDiagnosis = new ArrayList<>(); 
    }
    
    public Report( LocalDate patientsAdmitionDate, LocalTime patientsAdmitionTime, String anamnesis,String givenTerapy, List<ReportItem> items,List<DiagnosisReport>dr,Doctor doctor, Patient patient) {
        
        this.patientsAdmitionDate = patientsAdmitionDate;
        this.patientsAdmitionTime = patientsAdmitionTime;
        this.anamnesis=anamnesis;
        this.givenTherapy = givenTerapy;
        this.doctor = doctor;
        this.patient = patient;
        this.items = items;
        this.addedDiagnosis=dr;
    }

    public String getGivenTherapy() {
        return givenTherapy;
    }

    public void setGivenTherapy(String givenTherapy) {
        this.givenTherapy = givenTherapy;
    }

    public List<DiagnosisReport> getDiagnosis() {
        return addedDiagnosis;
    }

    public void setDiagnosis(List<DiagnosisReport> diagnosis) {
        this.addedDiagnosis = diagnosis;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getPatientsAdmitionDate() {
        return patientsAdmitionDate;
    }

    public void setPatientsAdmitionDate(LocalDate patientsAdmitionDate) {
        this.patientsAdmitionDate = patientsAdmitionDate;
    }

    public LocalTime getPatientsAdmitionTime() {
        return patientsAdmitionTime;
    }

    public void setPatientsAdmitionTime(LocalTime patientsAdmitionTime) {
        this.patientsAdmitionTime = patientsAdmitionTime;
    }


    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


    public List<ReportItem> getItems() {
        return items;
    }

    public void setItems(List<ReportItem> items) {
        this.items = items;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Report other = (Report) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return 
    "Report{" + "id=" + id + ", patientsAdmitionDate=" + patientsAdmitionDate + ", patientsAdmitionTime=" + patientsAdmitionTime + ", givenTerapy=" + givenTherapy + ", anamnesisDoctor=" + anamnesis + ", doctor=" + doctor + ", patient=" + patient + ", items=" + items + '}';
    }

    public String getAnamnesis() {
        return anamnesis;
    }

    public void setAnamnesis(String anamnesis) {
        this.anamnesis = anamnesis;
    }

    @Override
    public String getTableName() {
        return "report";
    }

    //u prvom delu on kada spoji nece sve spojiti jer ako ima mnogo izvestaja bilol bi neefikasno toliko spajanja zato bolje ove naj info
    //izvuci i staviti 2 ovakva
    
    //bitno kad vraca ako 2 dijagnoze i 2 reda , nisu 2 reporta!!HASHMAP KAO RESENJE
    @Override
    public List<AbstractDO> rsInTblList(ResultSet rs) {
    List<AbstractDO> reportsList = new ArrayList<>();
    Map<Long, Report> reportMap = new HashMap<>(); // Sprečava dupliranje izveštaja

    try {
        while (rs.next()) {
            String anamnesis = rs.getString(1); // Nova kolona dodata
            Long id = rs.getLong(2);
            LocalDate date = rs.getDate(3).toLocalDate();
            LocalTime time = rs.getTime(4).toLocalTime();
            String therapy = rs.getString(5);
            String doctorName = rs.getString(6);
            String doctorSurname = rs.getString(7);
            String latineName = rs.getString(8);
            Long diagnosisId = rs.getLong(9);
            String category = rs.getString(10);
            
            // Kreiranje doktora
            Doctor doctor = new Doctor();
            doctor.setName(doctorName);
            doctor.setSurname(doctorSurname);

            // Provera da li već postoji izveštaj sa istim ID-jem
            Report report = reportMap.get(id);
            if (report == null) {
                report = new Report();
                report.setDoctor(doctor);
                report.setId(id);
                report.setPatientsAdmitionDate(date);
                report.setPatientsAdmitionTime(time);
                report.setGivenTherapy(therapy);
                report.setAnamnesis(anamnesis); // Dodaj anamnezu
                report.setDiagnosis(new ArrayList<>()); // Inicijalizacija liste dijagnoza

                reportMap.put(id, report); // Dodavanje u mapu
                reportsList.add(report);   // Dodavanje u listu izveštaja
            }

            // Kreiranje dijagnoze
            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setLatineName(latineName);
            diagnosis.setId(diagnosisId);

            DiagnosisReport diagnosisReport = new DiagnosisReport();
            diagnosisReport.setCategory(category);
            diagnosisReport.setDiagnosis(diagnosis);
            diagnosisReport.setReport(report);

            // Dodavanje dijagnoze u postojeći izveštaj
            report.getDiagnosis().add(diagnosisReport);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
    }

    return reportsList;
}


   

    @Override
    public AbstractDO rsInTblObj(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String setAttrValue() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    }

    @Override
    public String getValuesInsert() {
        if (patientsAdmitionDate == null || patientsAdmitionTime == null || givenTherapy == null || anamnesis == null
                || getDoctor() == null || getPatient() == null || getPatient().getId() == 0) {
            throw new IllegalArgumentException("Nijedan od polja ne sme biti null ili prazno.");
        }
        return "'" + Date.valueOf(patientsAdmitionDate) + "', '" + Time.valueOf(patientsAdmitionTime) + "', '" + givenTherapy + "', '" + anamnesis + "' ," + getDoctor().getId() + ", " + getPatient().getId();
    }


    @Override
    public Long getPrimaryKey() {
return id;
    }

    @Override
    public String getColumnsInsert() {
return "(patientsAdmitionDate, patientsAdmitionTime, giventherapy, anamnesis, idDoctor, idPatient) ";
    }

    @Override
    public void setID(long id) {
        this.id=id;
    }

    @Override
    public String getWhereCondition() {

return "r.patientsAdmitionDate>='"+Date.valueOf(this.getPatientsAdmitionDate())+"' and p.id="+getPatient().getId();
    }

    @Override
    public String getTwoJoinTbl() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    

    @Override
    public String getSelectValues() {
        return "r.anamnesis,r.id,r.patientsAdmitionDate,r.patientsAdmitionTime,r.givenTherapy,d.name,d.surname,diagnosis.latineName,diagnosis.id,dr.category ";
    }

    @Override
    public String setAttrValueUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //kod dodavanja da doda slab obj asoc klasu itd
    //posaljes listu obj i prodjes kroz listu u kolliko je clan liste ovog tipa u koliko onog
    @Override
    public List<AbstractDO> getConnected(int tip) {
   if (tip == 0) {
        return new ArrayList<>(items); // vrati ReportItem listu
    } else if (tip == 1) {
        return new ArrayList<>(addedDiagnosis); // vrati DiagnosisReport listu
    }
    return new ArrayList<>();
}

    @Override
    public String getFourJoinTbl() {
    return " report r JOIN patient p on p.id=r.idPatient join doctor d ON r.idDoctor=d.id JOIN diagnosisreport dr ON r.id=dr.idReport JOIN diagnosis ON diagnosis.id=idDiagnosis ";
 
    }
    
/*
    
  SELECT r.patientsAdmitionDate,r.patientsAdmitionTime,r.givenTherapy,d.name,d.surname,diagnosis.latineName 
    
    FROM report r JOIN doctor d ON r.doctor=d.id JOIN diagnosisreport dr ON r.id=dr.idReport JOIN diagnosis ON diagnosis.id=idDiagnosis
    
    
    
    
    */

    /* for (DiagnosisReport dr : addedDiagnosis) {
        diagnosis+=dr.getDiagnosis().getLatineName()+
          " ["+dr.getCategory()+"], ";
    }
    
    string immutable pa bi svaki += novi bio obj zato stringbuilder*/
    
    
    
  public String getDiagnosisAsString() {
    if (addedDiagnosis == null || addedDiagnosis.isEmpty()) {
        return "NEMA DIJAGNOZE!"; // Ako nema dijagnoza
    }

    if (addedDiagnosis.size() == 1) {
        return addedDiagnosis.get(0).getDiagnosis().getLatineName(); // Samo jedna dijagnoza, bez kategorije-ne ispisuje da je primarna
    }
    StringBuilder diagnosis = new StringBuilder();
    for (DiagnosisReport dr : addedDiagnosis) {
        diagnosis.append(dr.getDiagnosis().getLatineName())
                 .append(" [")
                 .append(dr.getCategory())
                 .append("], ");
    }return diagnosis.substring(0, diagnosis.length()-2);//makne 2 zadnja , i razmak
}

    
}
