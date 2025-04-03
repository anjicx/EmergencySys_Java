/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operations;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public enum Operation implements Serializable{
    
    LOGIN,
    ODJAVA,
    VRATI_MESTA,//POPUNJAVA CMB SA MESTIMA
    VRATI_PACIJENTE,//VRACA PACIJENTE PO PRETR KRITER
    VRATI_PACIJENTA,//VRACA PACIJENTA ZA DETAILS
    IZMENI_PACIJENTA,//UPDATUJE PACIJENTA
    KREIRAJ_PACIJENTA,//KREIRA PACIJENTA INSERT
    VRATI_ODELENJA,//VRACA ODELENJA ZA CMB SELECT
    IZMENI_KORISNIKA,//MENJA DOKTORA UPDATE
    VRATI_PROCEDURE,//POPUNJAVA CMB SA PROCEDURAMA U ODN NA ODEL U KOME JE DR
    VRATI_DIJAGNOZE,//VRACA DIJAGNOZE U ODN NA REZ PRETR
    KREIRAJ_IZVESTAJ_GLAVNI,//CUVA IZVESTAJA GL DEO
    PRONADJI_IZVESTAJE,
    PRONADJI_STAVKE_IZVESTAJA;

    
    
}
