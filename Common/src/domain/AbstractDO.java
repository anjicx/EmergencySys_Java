/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author USER
 */
public interface AbstractDO  extends Serializable{

   public String getTableName(); // Ime tabele
   public List<AbstractDO> rsInTblList(ResultSet rs);
   //reportitem
   //public List<AbstractDO> resultSetUTabeluLista2(ResultSet rs);
    //da rs pretvori u tbl za nazad-LISTA KAD SE VRACA
   /*t DA MOZES U PATIENT NPR PRETVORITI*/
   /*AKO LISTU VRACA*/
   public AbstractDO rsInTblObj(ResultSet rs);
   public String setAttrValue();//vrednosti za update
   public String setAttrValueUpdate();
   public String getColumnsInsert(); // Kolone za INSERT
   public String getValuesInsert();//vr za insert
   
   public Long getPrimaryKey();//vrednost primarnog kljuca
   public void setID(long id); // Postavljanje ID nakon INSERT-a
   
   public String getWhereCondition();//where uslov postavljanje->  za search u diagnosis 
   public String getSelectValues();

   public String getTwoJoinTbl();//spajanje 2 tabele
   
   public String getFourJoinTbl();
   public List<AbstractDO>getConnected(int tip);
   
}
