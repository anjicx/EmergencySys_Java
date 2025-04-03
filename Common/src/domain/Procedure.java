
package domain;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class Procedure implements AbstractDO{
    private long id;
    private String code;
    private String name;
    private Department department;

   
    public Procedure() {
    }
     public Procedure(long id, Department department, String code, String name) {
        this.id = id;
        this.department = department;
        this.code = code;
        this.name = name;
    }

      public Procedure( Department department, String code, String name) {
        this.department = department;
        this.code = code;
        this.name = name;
    }

    public Procedure(Department department) {
        this.department = department;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {

return name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 67 * hash + Objects.hashCode(this.code);
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
        final Procedure other = (Procedure) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.code, other.code);
    }

   
   

    @Override
    public String getTableName() {
        return "procedures";
    }

    @Override
    public List<AbstractDO> rsInTblList(ResultSet rs) {
            List<AbstractDO> procedures = new ArrayList<>();

        try {
            while (rs.next()) {
                Procedure procedure = new Procedure(
                        rs.getLong("id"),
                        department,
                        rs.getString("code"),
                        rs.getString("name")
                );
                procedures.add(procedure);}
        } catch (SQLException ex) {
            Logger.getLogger(Procedure.class.getName()).log(Level.SEVERE, null, ex);
        }return procedures;
 }

    @Override
    public String setAttrValue() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getValuesInsert() {
    return "'" + code + "', '" + name + "', " + getDepartmentIdValue();

    }

    @Override
    public Long getPrimaryKey() {
        return id;
    }
    public String getDepartmentIdValue() {
    return (department == null) ? "NULL" : String.valueOf(department.getId());
}

@Override
public String getColumnsInsert() {
    return "(code, name, idDepartment)";
}

    @Override
    public void setID(long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AbstractDO rsInTblObj(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


   

    @Override
    public String getTwoJoinTbl() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    @Override
    public String getSelectValues() {
return"*";    }

    @Override
    public String getWhereCondition() {
        if(department==null){return "idDepartment is null";}//kod procedura kada ucitava onda samo da izracuna da li su prve 
        //ako su prve onda je sve
        else{ return "idDepartment is null or idDepartment = "+department.getId();}
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

