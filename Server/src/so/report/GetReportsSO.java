/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.report;

import dbb.DBBrokerGenerics;
import domain.AbstractDO;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author USER
 */
public class GetReportsSO extends AbstractSO {
    /*    //pretraga pacijent dr+bez kriterijuma*/
    private List<AbstractDO> list;
    public List<AbstractDO> getList() {
        return list;
    }

    @Override
    protected void doOperation(AbstractDO ado) throws Exception {

list=DBBrokerGenerics.getInstance().getListByConditionMultiJoin(ado);







    }

 }