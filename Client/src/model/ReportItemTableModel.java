/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import domain.ReportItem;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.AbstractTableModel;
/**
 *
 * @author USER
 */
public class ReportItemTableModel extends AbstractTableModel {
    private final String[] columns = {"Datum", "Vreme", "Naziv procedure"};
    private List<ReportItem> itemsList;

    public ReportItemTableModel(List<ReportItem> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public int getRowCount() {
        return itemsList == null ? 0 : itemsList.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ReportItem item = itemsList.get(rowIndex);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm"); // Bez sekundiii
        switch (columnIndex) {
            case 0: return item.getDate().format(dateFormatter);
            case 1: return item.getTime().format(timeFormatter);
            case 2: return item.getProcedure().toString();
            default: throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void setReportList(List<ReportItem> newItemsList) {
        this.itemsList = newItemsList;
        fireTableDataChanged();
    }

    public List<ReportItem> getReportList() {
        return itemsList;
    }

    public ReportItem getReportItem(int row) { // vraca proceduru u nekom redu
        return itemsList.get(row);
    }

 
    
}
