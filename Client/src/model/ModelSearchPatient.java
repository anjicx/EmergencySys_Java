/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import domain.Patient;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author USER
 */
public class ModelSearchPatient extends AbstractTableModel{
  private final String[] columns = {"JMBG", "Ime", "Prezime", "Datum rođenja"};
    private List<Patient> patientList;

    public ModelSearchPatient(List<Patient> patientList) {
        this.patientList = patientList;
    }
    @Override
    public int getRowCount() {
       return patientList == null ? 0 : patientList.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
         Patient patient = patientList.get(rowIndex);
        switch (columnIndex) {
            case 0: return patient.getJmbg();
            case 1: return patient.getName();
            case 2: return patient.getSurname();
            case 3:return patient.getBirthday().format(formatter);//PREBACITI localdate U STRING
            default:throw new AssertionError();
        }
    }
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void setPatientList(List<Patient> newPatientList) {
        this.patientList = newPatientList;
        fireTableDataChanged();
    }
      public List<Patient> getPatientList() {
return patientList;
      }
    public Patient getPatient(int row){//da vrati dijagnozu u nekom redu
        return patientList.get(row);
    }

}


    

