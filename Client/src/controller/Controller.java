/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


/**
 *
 * @author USER
 */
public class Controller {
    //LOGIN POSTAVLJA VREDNOST DOKTORA
    
    private static Controller instance;
    //private Doctor userDoctor;

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }
    
}
