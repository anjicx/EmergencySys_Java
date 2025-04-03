/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.departments;

import dbb.DBBrokerGenerics;
import domain.AbstractDO;
import so.AbstractSO;

/**
 *
 * @author USER
 */
public class SOVratiOdelenje extends AbstractSO {
/*VRACA KONKRETNOG*/

    private AbstractDO d;

    public AbstractDO getObject() {
        return d;
    }
   
    @Override
    protected void doOperation(AbstractDO ado) throws Exception {
       d = DBBrokerGenerics.getInstance().getByCondition(ado);
    }
    
}

