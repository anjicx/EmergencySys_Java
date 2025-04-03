/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class ReportItem implements AbstractDO {
    private long id;
    private LocalDate date;
    private LocalTime time;
    private Procedure procedure;//spoljni kljuc ka proceduri
    private long idReport; // za report da bi dohjvatili i upisali



    public ReportItem() {
    }

    public ReportItem(Procedure procedure,long id, LocalDate date, LocalTime time) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.procedure=procedure;
    }

    public ReportItem(long id, LocalDate date, LocalTime time, Procedure procedure, long idReport) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.procedure = procedure;
        this.idReport = idReport;
    }

    
    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    @Override
    public String toString() {
        return "ReportItem{" + "id=" + id + ", date=" + date + ", time=" + time + ", procedure=" + procedure + '}';
    }

    @Override
    public String getTableName() {

return "reportitem";    }

    
    @Override
    public List<AbstractDO> rsInTblList(ResultSet rs) {


 List<AbstractDO>list=new ArrayList<>();
    
        try {
            while (rs.next()) {
                Long id=rs.getLong(1);
           //     Long idReport=rs.getLong(2);
                LocalDate date = rs.getDate(2).toLocalDate();
                LocalTime time = rs.getTime(3).toLocalTime();
                
                Long idProcedure=rs.getLong(4);
                String name=rs.getString(5);
                
                Procedure p=new Procedure();
                p.setId(idProcedure);
                p.setName(name);
                
                ReportItem ri=new ReportItem(id, date, time, p, idReport);
                list.add(ri);
              //  System.out.println(p);
            }} catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;

    }

    @Override
    public AbstractDO rsInTblObj(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String setAttrValue() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getValuesInsert() {
        if (idReport == 0 || getDate() == null || getTime() == null || getProcedure() == null || getProcedure().getId() == 0) {
        throw new IllegalArgumentException("Nijedan od parametara za ReportItem ne sme biti null ili 0.");
    }
        return idReport+", '"+Date.valueOf(this.getDate())+"', '"+Time.valueOf(this.getTime())+"', "+this.getProcedure().getId();    }

    @Override
    public Long getPrimaryKey() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getColumnsInsert() {

return "(idReport, date, time, idProcedure) ";
    }

    @Override
    public void setID(long id) {
idReport=id;    }

    @Override
    public String getWhereCondition() {
return " idReport="+getIdReport();    }

    @Override
    public String getTwoJoinTbl() {
        return " reportitem r JOIN procedures p ON r.idProcedure=p.id ";    }


    @Override
    public String getSelectValues() {
return "r.id,r.date,r.time,p.id,p.name ";
    }

    public long getIdReport() {
        return idReport;
    }

    public void setIdReport(long idReport) {
        this.idReport = idReport;
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
