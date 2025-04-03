
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*insert into diagnosis(code,serbianname,latinename,group)*/
public class Diagnosis implements AbstractDO {
    private long id;
    private String code;
    private String serbianName;
    private String latineName;
    private DiagnosisGroup group; // Referenca na grupu kojoj pripada
    private String criteria;//KRITERIJUM PRETRAGE ZA DIJAGNOZU SEARCH!
    public Diagnosis() {
    }

    public Diagnosis(long id, String c, String serbianName, String latineName, DiagnosisGroup group) {
        this.id = id;
        this.code =c ;
        this.serbianName = serbianName;
        this.latineName = latineName;
        this.group = group;
    }

    public Diagnosis(String code, String serbianName, String latineName,DiagnosisGroup group) {
        this.code = code;
        this.serbianName = serbianName;
        this.latineName = latineName;
        this.group=group;
    }

    public Diagnosis(long id, String code, String serbianName, String latineName, DiagnosisGroup group, String criteria) {
        this.id = id;
        this.code = code;
        this.serbianName = serbianName;
        this.latineName = latineName;
        this.group = group;
        this.criteria = criteria;
    }

    public Diagnosis(String code, String serbianName, String latineName, DiagnosisGroup group, String criteria) {
        this.code = code;
        this.serbianName = serbianName;
        this.latineName = latineName;
        this.group = group;
        this.criteria = criteria;
    }
    
//!na 1 red smanjiti getere i setere
    public long getId() { return id;}

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return code;
    }

    public void setNumber(String c) {
        this.code = c;
    }

    public String getSerbianName() {
        return serbianName;
    }

    public void setSerbianName(String serbianName) {
        this.serbianName = serbianName;
    }

    public String getLatineName() {
        return latineName;
    }

    public void setLatineName(String latineName) {
        this.latineName = latineName;
    }

    public DiagnosisGroup getGroup() {
        return group;
    }

    public void setGroup(DiagnosisGroup group) {
        this.group = group;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    @Override
    public String toString() {
        return "Sifra je" + code;
    }

    @Override
    public String getTableName() {
return "diagnosis" ;   }

    @Override
    public List<AbstractDO> rsInTblList(ResultSet rs) {
        List<AbstractDO> diagnosisList = new ArrayList<>();

        try {
            while (rs.next()) {
                // Kreiranje objekta dijagnoze sa podacima iz rezultata pretrage
                Long id= rs.getLong("id");
                String code=rs.getString("code");      // Šifra dijagnoze
                String sName=rs.getString("serbianName");// Srpski naziv
                String lName=rs.getString("latineName");  // Latinski naziv
                long groupId = rs.getLong("idGroup");  // ID grupe
                String gName=rs.getString("name") ;  // Naziv grupe
                DiagnosisGroup g=new DiagnosisGroup(groupId,gName);
                Diagnosis diagnosis = new Diagnosis(id,code,sName,lName,g);
                diagnosisList.add(diagnosis);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Diagnosis.class.getName()).log(Level.SEVERE, null, ex);
        }
return diagnosisList;
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
return "'"+code+"', '"+serbianName+"', '"+latineName+"', "+group.getId();    }

    @Override
    public Long getPrimaryKey() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getColumnsInsert() {
return "(code,serbianname,latinename, idgroup)";   
     }

    @Override
    public void setID(long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    @Override
    public String getTwoJoinTbl() {
                return " diagnosis JOIN diagnosisgroup ON diagnosis.idGroup = diagnosisgroup.id ";

    }
  public String conditionLikeCreate(String s) {
    if (s == null || s.trim().isEmpty()) {
        return "'%'"; // Ako nema unosa, podrazumeva se bilo šta
    }
    return " '"+s.trim().toUpperCase() + "%'";
}

    @Override
    public String getSelectValues() {
return " diagnosis.*, diagnosisgroup.id, diagnosisgroup.name ";

    }

    @Override
    public String getWhereCondition() {

    String whereCondition = "diagnosis.serbianName LIKE " + conditionLikeCreate(criteria)
            +" or diagnosis.latineName LIKE " + conditionLikeCreate(criteria)+" or diagnosis.code LIKE "
            + conditionLikeCreate(criteria)+ " or diagnosisgroup.name like "+conditionLikeCreate(criteria);
        return whereCondition;


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
