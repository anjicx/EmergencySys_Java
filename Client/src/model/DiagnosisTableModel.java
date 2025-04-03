/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import domain.Diagnosis;
/**
 *
 * @author USER
 */import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DiagnosisTableModel extends AbstractTableModel {
    private final String[] columns = {"Sifra", "Srpski naziv", "Latinski naziv", "Grupa"};
    private List<Diagnosis> diagnosisList;

    public DiagnosisTableModel(List<Diagnosis> diagnosisList) {
        this.diagnosisList = diagnosisList;
    }

    @Override
    public int getRowCount() {
             return diagnosisList == null ? 0 : diagnosisList.size();

    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Diagnosis diagnosis = diagnosisList.get(rowIndex);
        switch (columnIndex) {
            case 0: return diagnosis.getNumber();
            case 1: return diagnosis.getSerbianName();
            case 2: return diagnosis.getLatineName();
            case 3: return diagnosis.getGroup().getName(); // Prikazujemo ime grupe
            default:throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void setDiagnosisList(List<Diagnosis> newDiagnosisList) {
        this.diagnosisList = newDiagnosisList;
        fireTableDataChanged();
    }
      public List<Diagnosis> getDiagnosisList() {
return diagnosisList;
      }
    public Diagnosis getDiagnosis(int row){//da vrati dijagnozu u nekom redu
        return diagnosisList.get(row);
    }

  
    
}