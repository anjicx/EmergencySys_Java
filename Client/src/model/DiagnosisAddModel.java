/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import domain.DiagnosisReport;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author USER
 */
public class DiagnosisAddModel extends AbstractTableModel {
    private final String[] columns = {"Šifra", "Latinski naziv", "Kategorija"};
    private List<DiagnosisReport> diagnosisReportList; // Koristi DiagnosisReport klasu

    public DiagnosisAddModel(List<DiagnosisReport> diagnosisReportList) {
        this.diagnosisReportList = diagnosisReportList;
    }

    @Override
    public int getRowCount() {
        return diagnosisReportList == null ? 0 : diagnosisReportList.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DiagnosisReport dr = diagnosisReportList.get(rowIndex);
        switch (columnIndex) {
            case 0: return dr.getDiagnosis().getNumber(); // Šifra dijagnoze
            case 1: return dr.getDiagnosis().getLatineName(); // Latinski naziv dijagnoze
            case 2: return dr.getCategory(); // Kategorija
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Dozvoljava editovanje samo u koloni za kategoriju
        return columnIndex == 2;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //neusklađenosti između liste (addedDiagnoses) i tabele ako previse brza interakcija brisanja npr ako prrebrzo
       try {
        if (columnIndex == 2) {
            DiagnosisReport dr = diagnosisReportList.get(rowIndex);
            dr.setCategory((String) aValue);
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    } catch (IndexOutOfBoundsException ex) {//ako prebrzno menja se 
        System.err.println("Greška: pokušaj pristupa indeksu van granica. Indeks: " + rowIndex + ", Veličina liste: " + diagnosisReportList.size());
         System.out.println("Stanje liste nakon greške:");
    for (int i = 0; i < diagnosisReportList.size(); i++) {
        System.out.println("Red " + i + ": " + diagnosisReportList.get(i));
    }
        ex.printStackTrace();
        
    }
    }

    public void setDiagnosisReportList(List<DiagnosisReport> newList) {
        this.diagnosisReportList = newList;
        fireTableDataChanged();
    }

    public List<DiagnosisReport> getDiagnosisReportList() {
        return diagnosisReportList;
    }

    public DiagnosisReport getDiagnosisReport(int row) {
        return diagnosisReportList.get(row);
    }
}

