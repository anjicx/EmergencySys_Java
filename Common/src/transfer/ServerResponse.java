/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transfer;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class ServerResponse implements Serializable{
    private Object odgovor;
    private boolean uspesnost;
    private String poruka;

    public ServerResponse() {
    }

    public ServerResponse(Object odgovor,boolean uspesnost) {
        this.odgovor = odgovor;
        this.uspesnost=uspesnost;
    }

    public ServerResponse(Object odgovor, boolean uspesnost, String poruka) {
        this.odgovor = odgovor;
        this.uspesnost = uspesnost;
        this.poruka = poruka;
    }

    public Object getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(Object odgovor) {
        this.odgovor = odgovor;
    }

    public boolean getUspesnost() {
        return uspesnost;
    }

    public void setUspesnost(boolean uspesnost) {
        this.uspesnost = uspesnost;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }


    
    

    
}
