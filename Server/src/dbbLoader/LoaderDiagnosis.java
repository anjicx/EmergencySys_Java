/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbbLoader;

import controller.Controller;
import domain.AbstractDO;
import java.util.List;

/**
 *
 * @author USER
 */
public class LoaderDiagnosis implements LoaderInterface {

    @Override
    public AbstractDO findObject(AbstractDO criteria) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void loadData(List<AbstractDO> list) throws Exception {
         Controller.getInstance().kreirajDijagnoze(list);
    }

    @Override
    public int isLoaded(AbstractDO odo) throws Exception {
    return Controller.getInstance().popunjeneDijagnoze(odo); }
    
}
