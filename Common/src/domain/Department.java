/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class Department  implements AbstractDO{
    private long id;
    private String name;
    private String criteria;//KRITERIJUM PRETRAGE ZA department dodavanje proceedura!


    public Department() {
    }

    public Department(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Department(long id, String name, String criteria) {
        this.id = id;
        this.name = name;
        this.criteria = criteria;
    }
    

    public Department(String name) {
        this.name = name;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
//EQUALS OBAVEZNO ZBOG 
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
        final Department other = (Department) obj;
        return this.id == other.id;
    }

    @Override
    public String getTableName() {
    return "department";
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
 List<AbstractDO>lista=new ArrayList<>();
    
        try {
            while(rs.next()){
                Long id=rs.getLong(1);
                String name=rs.getString(2);
                Department d=new Department(id, name);
                lista.add(d);
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Department.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return lista;
    }

    @Override
    public String setAttrValue() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        AbstractDO obj=null;
        try {
            if(rs.next()){
                System.out.println("Pronadjen department!");
                long id=rs.getLong(1);
                String name=rs.getString(2);
                Department d=new Department(id,name);
                obj=d;
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Department.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
return obj;
    }


    @Override
    public String getTwoJoinTbl() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    @Override
    public String getSelectValues() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getWhereCondition() {
        return "name="+"'"+criteria+"'";
    }

    @Override
    public String setAttrValueUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    
      
      


