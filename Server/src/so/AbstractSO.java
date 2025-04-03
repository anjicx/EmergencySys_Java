/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import dbb.DBBrokerGenerics;
import domain.AbstractDO;

/**
 *
 * @author USER
 */public abstract class AbstractSO {
    public void execute(AbstractDO ado) throws Exception {
        try {
            doOperation(ado);
            commit();
        } catch (Exception ex) {
            rollback();
        throw ex;  // Umesto da pravimo novi Exception, samo ga propagiramo
        } 
    }

    protected abstract void doOperation(AbstractDO ado) throws Exception;

    private void commit() throws Exception {
        DBBrokerGenerics.getInstance().commitTransaction();
    }

    private void rollback() throws Exception {
        DBBrokerGenerics.getInstance().rollbackTransaction();
    }



}
