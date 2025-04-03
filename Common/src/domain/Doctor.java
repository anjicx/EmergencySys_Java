
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Doctor implements AbstractDO {

    private long id;
    //podaci za logovanje
    private String username;
    private String password;

    //osnovni podaci
    private MedicalTitle title;
    private String name;
    private String surname;
    //odelenje podaci
    private Department department;//zbog procedura bitno selekcija u odnosu na odel
    //za tbl sa str servera
    private boolean logged;//ko ulogovan

    public Doctor() {
    }

    public Doctor(long id, String name, String surname, MedicalTitle title, String username, String password, Department department) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.title = title;
        this.username = username;
        this.password = password;
        this.department = department;

    }

    public Doctor(long id, String name, String surname, MedicalTitle title, String username, String password, Department department, boolean logged) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.title = title;
        this.username = username;
        this.password = password;
        this.department = department;
        this.logged = logged;
    }

    public Doctor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public MedicalTitle getTitle() {
        return title;
    }

    public void setTitle(MedicalTitle title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //da kod doktora txt field izvestaj ispise za dr zvanje ime prezime 
    @Override
    public String toString() {

        return title.toString() + " " + name + " " + surname;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    /*equals po username i password poredi doktora*/
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
        final Doctor other = (Doctor) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return Objects.equals(this.password, other.password);
    }

    @Override
    public String getTableName() {
        return "doctor";
    }

    @Override
    public String getColumnsInsert() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setID(long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<AbstractDO> rsInTblList(ResultSet rs) {

        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String setAttrValue() {
        return "name='" + name + "', surname='" + surname + "', idDepartment=" + department.getId();
    }

    @Override
    public Long getPrimaryKey() {
        return id;
    }

    @Override
    public String getValuesInsert() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AbstractDO rsInTblObj(ResultSet rs) {
        AbstractDO obj = null;
        try {
            if (rs.next()) {//ako nema

                //id
                long id = rs.getLong(1);
                MedicalTitle title = MedicalTitle.valueOf(rs.getString(4));//enum pretvaranje!
                String name = rs.getString(5);
                String surname = rs.getString(6);
                System.out.println(name + "" + surname + "" + title);
                //odelenje podaci
                Long idDepartment = rs.getLong(7);
                boolean logged = rs.getBoolean(8);
                String nameD = rs.getString(10);
                Department dep = new Department(idDepartment, nameD);
                Doctor d = new Doctor(id, name, surname, title, username, password, dep, logged);
                obj = d;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return obj;

    }

    @Override
    public String getTwoJoinTbl() {
        return " doctor join department on doctor.idDepartment=department.id";
    }

    @Override
    public String getSelectValues() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getWhereCondition() {
      if (username == null || username.isEmpty()) {
           throw new IllegalArgumentException("Korisničko ime ne može biti prazno.");
        }
        if (password == null || password.isEmpty()) {
           throw new IllegalArgumentException("Lozinka ne može biti prazna.");
        }

        return "username = '" + username + "' AND password = '" + password + "'";
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    @Override
    public String setAttrValueUpdate() {
        int login = logged ? 1 : 0;
        return "logged = " + login;
    }

    @Override
    public List<AbstractDO> getConnected(int ado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getFourJoinTbl() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
