/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.patient;
import dbb.DBBrokerGenerics;
import domain.AbstractDO;
import domain.Patient;
import exception.ServerException;
import so.AbstractSO;

/**
 *
 * @author USER
 */
public class UpdatePatientSO extends AbstractSO {


    @Override
    protected void doOperation(AbstractDO ado) throws Exception {
          if(!(ado instanceof Patient)){
            throw new IllegalArgumentException("Očekivan objekat pacijenta");
        }
         boolean uspeh = DBBrokerGenerics.getInstance().updateRecordById(ado);


       if (!uspeh) {
            throw new ServerException("Neuspesna izmena pacijenta.");
        }    }
    }

   
