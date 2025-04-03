/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.doctor;

import dbb.DBBrokerGenerics;
import domain.AbstractDO;
import exception.ServerException;
import so.AbstractSO;

/**
 *
 * @author USER
 */
public class UpdateDoctorSO  extends AbstractSO{


    @Override
    protected void doOperation(AbstractDO ado) throws Exception {
boolean uspeh = DBBrokerGenerics.getInstance().updateRecordById(ado);
        if (!uspeh) {
            throw new ServerException("Neuspesno menjanje statusa doktora.");
        }    }  
  

}
    

