/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.patient;

import dbb.DBBrokerGenerics;
import domain.AbstractDO;
import so.AbstractSO;

/**
 *
 * @author USER
 */
public class GetPatientSO  extends AbstractSO{
    private AbstractDO ado;

    public AbstractDO getPatient() {
        return ado;
    }

    @Override
    protected void doOperation(AbstractDO ado) throws Exception {
        this.ado=DBBrokerGenerics.getInstance().getByConditionWithJoin(ado);
    }


    
}

