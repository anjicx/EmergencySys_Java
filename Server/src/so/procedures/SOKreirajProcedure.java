/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.procedures;

import dbb.DBBrokerGenerics;
import domain.AbstractDO;
import exception.ServerException;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author USER
 */
public class SOKreirajProcedure extends AbstractSO {

    private List<AbstractDO> lista;

    public  void setLista(List<AbstractDO>list) {
        lista=list;
    }
   

    @Override
    protected void doOperation(AbstractDO ado) throws ServerException {
        boolean uspeh =  DBBrokerGenerics.getInstance().insertRecords(lista);

       if (!uspeh) {
            throw new ServerException("Neuspesno kreiranje pacijenta.");
        }    
    }
  }
   


