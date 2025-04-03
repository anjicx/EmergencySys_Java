/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.report;

import dbb.DBBrokerGenerics;
import domain.AbstractDO;
import domain.Report;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author USER
 */public class SaveReportSO extends AbstractSO {
    private AbstractDO ado;

    public AbstractDO getDomainObject() {
        return ado;
    }

    @Override
    protected void doOperation(AbstractDO ado) throws Exception {
//insert gl dela izvestaja
//insert report
        if(!(ado instanceof Report)){
             throw new IllegalArgumentException("Očekivan je objekat klase Report.");

        }
        AbstractDO insertedReport = DBBrokerGenerics.getInstance().insertRecordPrepared(ado);
       
        if (insertedReport == null) {
           throw new Exception("Kreiranje glavnog izveštaja nije uspelo.");
        }
//reportitem insert
        //ako je slab objekat--reportitem insert
        List<AbstractDO> povezaniItems = ado.getConnected(0);
        for (AbstractDO item : povezaniItems) {
            // postavljas kljuc primarni da je isti kao reporta,prvi se sam generise
            item.setID(insertedReport.getPrimaryKey());
            boolean success = DBBrokerGenerics.getInstance().insertRecord(item);
            if (!success) {
                throw new Exception("Kreiranje stavke ReportItem nije uspelo.");
            }
        }
        //ako je asoc klasa
        // Unos povezanih stavki `DiagnosisReport`
        //jedan id je od report dr od diagnosis koji vraca
        //diagnosisreport insert
        List<AbstractDO> povezani_Items = ado.getConnected(1);
        for (AbstractDO item : povezani_Items) {
            // Poveži glavni ID sa stavkom
            item.setID(insertedReport.getPrimaryKey());
            boolean success = DBBrokerGenerics.getInstance().insertRecord(item);
            if (!success) {
                throw new Exception("Kreiranje stavke DiagnosisReport nije uspelo.");
            }
        }

        this.ado = insertedReport; // Postavi rezultat kreiranja
    }

 }