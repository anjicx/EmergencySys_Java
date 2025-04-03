package transfer;


import java.io.Serializable;
import operations.Operation;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template


/**
 *
 * @author USER
 */
public class ClientRequest implements Serializable {
    private Operation operacija;
    private Object objekatOperacije;

    public ClientRequest() {
    }

    public ClientRequest(Operation operacija, Object objekatOperacije) {
        this.operacija = operacija;
        this.objekatOperacije = objekatOperacije;
    }

    public Operation getOperacija() {
        return operacija;
    }

    public void setOperacija(Operation operacija) {
        this.operacija = operacija;
    }

    public Object getObjekatOperacije() {
        return objekatOperacije;
    }

    public void setObjekatOperacije(Object objekatOperacije) {
        this.objekatOperacije = objekatOperacije;
    }


    
    
}
