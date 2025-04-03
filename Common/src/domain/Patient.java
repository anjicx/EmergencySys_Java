/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class Patient implements AbstractDO {
    private long id;
    private String name;
    private String surname;
    private LocalDate birthday;
    private String number;
    private String jmbg;
    private Place place;

    public Patient() {
    }

    public Patient(long id, String name, String surname, LocalDate birthday, String number, String jmbg, Place place) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.number = number;
        this.jmbg = jmbg;
        this.place = place;
    }
    
    //new Patient("Andrea","Maric",LocalDate.of(),"0654283947","",new Place(1,"Beograd"))
        public Patient( String name, String surname, LocalDate birthday, String number, String jmbg, Place place) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.number = number;
        this.jmbg = jmbg;
        this.place = place;
    }

    public Patient(String ime, String prezime, Place place, String jmbg) {
        this.name = ime;
        this.surname = prezime;
        this.place = place;
        this.jmbg = jmbg;

    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    @Override
    public String toString() {
        return  name + " " + surname;
    }

    @Override
    public String getTableName() {
    return "patient";
    }

    @Override
    public String getColumnsInsert() {
    return "(name,surname,birthday,number,jmbg,idPlace)";

    }


    @Override
    public void setID(long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
     public List<AbstractDO> rsInTblList(ResultSet rs) {


 List<AbstractDO>patientList=new ArrayList<>();
    
        try {
            while (rs.next()) {
                Long id=rs.getLong(1);
                /*REZULTAT JE DOBIJEN OVDE*/
                String jmbg=rs.getString(2);
                //UNETA VR RODJ MZD DA MORA U BAZI NOT NULL SVAKAKO STAVI
                LocalDate birth = rs.getDate(3)!=null?rs.getDate(3).toLocalDate():null ;
                String number=rs.getString(4);
                String im=rs.getString(5);
                String prezi=rs.getString(6);
                Patient p=new Patient(id, im, prezi, birth, number, jmbg, place);
                patientList.add(p);
                System.out.println(p);
            }} catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return patientList;

    }
//datum pretvoriti u java.sql.date za upis u bazu podataka 
    @Override
    public String setAttrValue() {
    validate(); // 
return "name='"+name+"', surname='"+surname+"' , birthday='"+java.sql.Date.valueOf(birthday)+
        "', number='"+number+"', jmbg='"+jmbg+
        "', idPlace="+place.getId();
    }
    @Override
    public String getValuesInsert() {
    validate(); //         
    return "'" + name + "', '" + surname + "', '" + java.sql.Date.valueOf(birthday) + "', '" + number + "', '" + jmbg + "', " + place.getId();
    }

    @Override
    public Long getPrimaryKey() {
        return id;
    }

    @Override
    public AbstractDO rsInTblObj(ResultSet rs) {
 AbstractDO obj=null;
        try {
            if(rs.next()){
                
                //id
                long id=rs.getLong(1);
                String name=rs.getString(2);
                String surname=rs.getString(3);
                LocalDate birth=rs.getDate(4).toLocalDate();
               String number=rs.getString(5);
               String jmbg=rs.getString(6);
               long idPlace=rs.getLong(7);
               String namePlace=rs.getString(9);
               Patient p=new Patient(id,name, surname, birth, number, jmbg, new Place(idPlace,namePlace));
                obj=p;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
        }

return obj;

    }


    @Override
    public String getSelectValues() {
        return "p.id,p.jmbg,p.birthday,p.number,p.name,p.surname";//u uslovu toUpper da zanemari ovde upper 
        //znc da pretvoris u velika slova pri ispisu

    }
    @Override
    public String getTwoJoinTbl() {
        return "patient p join place pp ON p.idPlace=pp.id ";
    }
    /*za like pretragu condition da napravi !*/
    public String conditionLikeCreate(String s) {
    if (s == null || s.trim().isEmpty()) {
        return "'%'"; // Ako nema unosa, podrazumeva se bilo šta
    }
    return " '"+s.trim().toUpperCase() + "%'";
}

/*MOZE DA UKUCA SAMO MESTO I DA PRETRAZI ILI NEKo slovo koje
    ima u tom imenu I DA PRETRAZI NPR A R I MESTO I PRETRAGA ALI MESTO MORA*/
    /**/

    @Override
    public String getWhereCondition() {

    StringBuilder whereCondition = new StringBuilder("1=1"); // Osnovni uslov (uvek tačan)
    
    // Dodaj uslov za ime ako postoji
    if (name != null && !name.trim().isEmpty()) {
        whereCondition.append(" AND p.name LIKE ").append(conditionLikeCreate(name));
    }

    // Dodaj uslov za prezime ako postoji
    if (surname != null && !surname.trim().isEmpty()) {
        whereCondition.append(" AND p.surname LIKE ").append(conditionLikeCreate(surname));
    }

    // Dodaj obavezan uslov za mesto
    if (place != null && place.getName() != null && !place.getName().trim().isEmpty()) {
        whereCondition.append(" AND upper(pp.name) LIKE ").append(conditionLikeCreate(place.getName()));
    }
    if (jmbg != null && !jmbg.trim().isEmpty()) {
        whereCondition.append(" AND jmbg LIKE ").append(conditionLikeCreate(jmbg));
    }

    // Vraćanje generisanog uslova
    System.out.println(whereCondition.toString()); 
    return whereCondition.toString();
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

    private void validate() {

 if (name == null || name.trim().isEmpty() || !name.matches("[a-zA-Z]+")) {
        throw new IllegalArgumentException("Ime mora biti popunjeno i sadržavati samo slova.");
    }
    if (surname == null || surname.trim().isEmpty() || !surname.matches("[a-zA-Z]+")) {
        throw new IllegalArgumentException("Prezime mora biti popunjeno i sadržavati samo slova.");
    }
    if (birthday == null) {
        throw new IllegalArgumentException("Datum rođenja ne sme biti prazan.");
    }
    if (number == null || !number.matches("\\+?[0-9 /-]{6,15}")) {
        throw new IllegalArgumentException("Broj telefona mora biti u ispravnom formatu.");
    }
    if (jmbg == null || !jmbg.matches("\\d{13}")) {
        throw new IllegalArgumentException("JMBG mora sadržavati tačno 13 cifara.");
    }
    if (place == null || place.getId() <= 0) {
        throw new IllegalArgumentException("Mesto mora biti validno postavljeno.");
    }

    }

   

}
