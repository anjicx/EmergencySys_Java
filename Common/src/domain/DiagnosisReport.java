/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author USER
 */
public class DiagnosisReport implements AbstractDO{
    private Report report;
    private Diagnosis diagnosis;
    private String category; // PRIMARna sekudnarna da se pamti 

    public DiagnosisReport(Report report, Diagnosis diagnosis, String category) {
        this.report = report;
        this.diagnosis = diagnosis;
        this.category = category;
    }

    public DiagnosisReport() {
    }

    // Getteri i setteri
    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getTableName() {
        return "diagnosisreport";
    }

    @Override
    public List<AbstractDO> rsInTblList(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        if (report == null || report.getId() == 0 || diagnosis == null || diagnosis.getId() == 0 ) {
        throw new IllegalArgumentException("Report i Diagnosis moraju biti postavljeni i imati validne ID vrednosti.");
    }
        if (category == null) {
            throw new IllegalArgumentException("Morate uneti kategoriju izveštaja.");

        }
        return report.getId()+", "+diagnosis.getId()+",'"+category+"'";
    }

    @Override
    public Long getPrimaryKey() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getColumnsInsert() {
            return "(idReport,idDiagnosis,category)";
    }

    @Override
 
    public void setID(long id) {
    if (report == null) {
        report = new Report(); // Inicijalizuje prazan Report ako je null
    }
    report.setID(id);
}



    @Override
    public String getWhereCondition() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

