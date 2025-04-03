/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import domain.AbstractDO;
import domain.Doctor;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author USER
 */
public class ModelTableUser extends AbstractTableModel {

    ArrayList<Doctor> listUsers;
    String[] columns = new String[]{"ID", "Korisničko ime"};
    
    public ModelTableUser() {
    }

    public ModelTableUser(ArrayList<Doctor> listUsers) {
        this.listUsers = listUsers;
    }
    
    
    @Override
    public int getRowCount() {
        return listUsers.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Doctor user = listUsers.get(rowIndex);
         switch (columnIndex) {
            case 0: return user.getId();
            case 1: return user.getUsername();
            default:throw new AssertionError();
        }
        
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    
     public Doctor getUser(int rowIndex) {//vraca korisnika iz tabele
        return listUsers.get(rowIndex);
    }
    
    public void removeUser(Doctor user) {//oizbrise iz liste korisnika
        listUsers.remove(user);
        fireTableDataChanged();
    }
    public void addUser(Doctor user) {//doda u listu korisnika
        listUsers.add(user);
        fireTableDataChanged();
    }
    
    
    
    
}

