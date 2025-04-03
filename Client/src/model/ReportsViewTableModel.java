/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import domain.Report;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author USER
 */
public class ReportsViewTableModel extends AbstractTableModel {
 private final String[] columns = {"ID Izveštaja", "Datum prijema", "Vreme prijema", "Dijagnoze","Terapija", "Doktor"};
    private List<Report> reportsList;
    
   public ReportsViewTableModel(List<Report> reportsList){
       this.reportsList=reportsList;
   }
    @Override
    public int getRowCount() {
        return reportsList == null ? 0 : reportsList.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

 @Override
public Object getValueAt(int rowIndex, int columnIndex) {
    Report report = reportsList.get(rowIndex);

    switch (columnIndex) {
        case 0: return report.getId(); // ID izveštaja
        case 1: return report.getPatientsAdmitionDate(); // Datum prijema
        case 2: return report.getPatientsAdmitionTime(); // Vreme prijema
        case 3: return report.getDiagnosisAsString(); // Dijagnoze (obrađene za prikaz)
        case 4: return report.getGivenTherapy(); // Terapija
        case 5: return report.getDoctor().getName() + " " + report.getDoctor().getSurname(); // Ime doktora
        default: return null;
    }
}

     @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void setReportList(List<Report> newReportsList) {
        this.reportsList = newReportsList;
        fireTableDataChanged();
    }

    public List<Report> getReportList() {
        return reportsList;
    }

    public Report getReport(int row) { // vraca report u nekom redu
        return reportsList.get(row);
    }

  
}
