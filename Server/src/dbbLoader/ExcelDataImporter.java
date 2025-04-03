/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbbLoader;
import domain.Department;
import domain.Diagnosis;
import domain.DiagnosisGroup;
import domain.Procedure;
import jxl.Sheet;
import jxl.Workbook;
import java.io.File;
import java.util.*;

//posmatramo kao utility klasu koja prosledjuje dalje diagnosisgroup odgovornost
public class ExcelDataImporter {
    //sluc otvaranja bez pokretanja
  private LoaderInterface loaderMainObject;//veza sa interfejsom za ucitavanje procedura i dijagnoza

    // Injekcija zavisnosti
    public ExcelDataImporter(LoaderInterface loaderMainObject) {
//instancira loaderinterfejs kao poslati-bice poslat procedure/diagnosis
        this.loaderMainObject = loaderMainObject;
    }
    public int loadProcedures(String fileLocation) throws Exception {
        
//ispita ima li neke procedure posalje null,ako s null dep onda sbe ucitano znc
        int status=loaderMainObject.isLoaded(new Procedure(null));
        if(status==1 || status==-1 ){
        return status;
        }      
//otvara excell fajl
        Workbook workbook = Workbook.getWorkbook(new File(fileLocation));
        //da ucitava procedure iz fajla u hashset

        Set<Procedure> procedures = new LinkedHashSet<>();
//ide kroz sheet svaki i kroz njegove redove
        for (Sheet sheet : workbook.getSheets()) {
            String sheetName = sheet.getName();
            Department department = null;

            if (!sheetName.equals("ZAJEDNICKE_PROCEDURE")) {
                Department criteria = new Department();
                criteria.setCriteria(sheetName);
                department = (Department) loaderMainObject.findObject(criteria);
                
            }
//ide kroz redove svakog sheet-a
            for (int rowIndex = 1; rowIndex < sheet.getRows(); rowIndex++) {
                String code = sheet.getCell(1, rowIndex).getContents();
                String name = sheet.getCell(2, rowIndex).getContents();
                //dodaje svakog sheet-a procedure oznacene i povezuje sa odelenjem njihovim
                procedures.add(new Procedure(department, code, name));
            }
        }
        // kada se zavrse 2 for petlje insertovanje  ucitanih u listu
        loaderMainObject.loadData(new ArrayList<>(procedures));
        workbook.close();
        return status;
    }
    // ucitavanje jedinstvene gr dijagnoza iz fajla
    public Set<DiagnosisGroup> loadDiagnosisGroups(String fileLocation) throws Exception {
        Workbook workbook = Workbook.getWorkbook(new File(fileLocation));
        Sheet sheet = workbook.getSheet(0);

        Set<DiagnosisGroup> diagnosisGroups = new LinkedHashSet<>();
        for (int rowIndex = 1; rowIndex < sheet.getRows(); rowIndex++) {
            String groupIdStr = sheet.getCell(0, rowIndex).getContents();
            String groupName = sheet.getCell(1, rowIndex).getContents();
            long id = Long.parseLong(groupIdStr);

            DiagnosisGroup group = new DiagnosisGroup(id, groupName);
            diagnosisGroups.add(group);
        }
        workbook.close();
        return diagnosisGroups;
    }
    // ucitavanje dijagnoza i povezivanje sa gr dijagnoza
    public int loadDiagnoses(String fileLocation) throws Exception {
        //otvori fajl
        int status=loaderMainObject.isLoaded(new Diagnosis());
        if(status==1 || status==-1 ){
        return status;
        }      
        Workbook workbook = Workbook.getWorkbook(new File(fileLocation));
        Sheet sheet = workbook.getSheet(0);
        //učitava grupe dijagnoza
         Set<DiagnosisGroup> diagnosisGroups =loadDiagnosisGroups(fileLocation);
        //inicijal seta za učitavanje dijagnoza        
        Set<Diagnosis> diagnoses = new LinkedHashSet<>();
       //prolazak kroz svaku ćeliju i dodela
        for (int rowIndex = 1; rowIndex < sheet.getRows(); rowIndex++) {
            
            String groupIdStr = sheet.getCell(0, rowIndex).getContents();
            long groupId = Long.parseLong(groupIdStr);
            String nameGroup = sheet.getCell(1, rowIndex).getContents();
            DiagnosisGroup group = new DiagnosisGroup(groupId, nameGroup);

            String code = sheet.getCell(2, rowIndex).getContents();
            String serbianName = sheet.getCell(3, rowIndex).getContents();
            String latineName = sheet.getCell(4, rowIndex).getContents();

            Diagnosis diagnosis = new Diagnosis(code, serbianName, latineName, group);
            diagnoses.add(diagnosis);
        }
        //učitava grupe dijagnoza
        loaderMainObject.loadData(new ArrayList<>(diagnosisGroups));
        loaderMainObject.loadData(new ArrayList<>(diagnoses));
        workbook.close();
        return status;
    }

   

}
