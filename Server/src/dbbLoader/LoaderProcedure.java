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
public class LoaderProcedure implements LoaderInterface {
    
    //za procedure
    @Override
    public AbstractDO findObject(AbstractDO criteria) throws Exception {
        return Controller.getInstance().vratiOdelenje(criteria);
    }

    @Override
    public void loadData(List<AbstractDO> list) throws Exception {
         Controller.getInstance().kreirajProcedure(list);
    }

    @Override
    public int isLoaded(AbstractDO odo) throws Exception {
        return Controller.getInstance().popunjeneProcedure(odo);
    }

    
}

