/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.doctor;

import dbb.DBBrokerGenerics;
import domain.AbstractDO;
import domain.Doctor;
import so.AbstractSO;

/**
 *
 * @author USER
 */
public class LoginSO extends AbstractSO{
    
    private AbstractDO d;

    public AbstractDO getLoggedDoctor() {
        return d;
    }
   

    @Override
    protected void doOperation(AbstractDO ado) throws Exception {
        if (!(ado instanceof Doctor)) {
        throw new IllegalArgumentException("Očekivan je objekat klase Doctor.");
    }
        d=DBBrokerGenerics.getInstance().getByConditionWithJoin(ado);
    }
    
}
