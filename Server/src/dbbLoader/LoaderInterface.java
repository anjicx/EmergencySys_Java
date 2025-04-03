/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbbLoader;

import domain.AbstractDO;
import java.util.List;

/**
 *
 * @author USER
 */
public interface LoaderInterface {
    //za ucitavanje procedura
    AbstractDO findObject(AbstractDO criteria) throws Exception;
    void loadData(List<AbstractDO> list) throws Exception;
    int isLoaded(AbstractDO odo)throws Exception;
}

