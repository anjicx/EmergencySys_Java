/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threads;

import dbb.DBProperties;
import dbb.PortUtil;
import forms.ServerForm;
import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Thread {
    private ServerSocket serverSocket;
    private ServerForm guiForm;

    private boolean running = true; // Zastavica za kontrolu rada niti

private final List<ClientThread> klijentskeNiti = new ArrayList<>();//lista aktivnih niti

   public ServerThread(ServerForm guiForm) throws IOException { // IOEXCEPTION DODATI JER ONDA MOGU UHVATITI I ISPISATI LEPO U FORMI
       this.guiForm = guiForm;

       //JER SALJE DALJE IZUZ
        DBProperties properties = new DBProperties();
        int port = Integer.parseInt(properties.getDBPort());
        
        if (!PortUtil.isPortAvailable(port)) {
            throw new IOException("Port " + port + " se već koristi.Server je već pokrenut!");
        }
        
        serverSocket = new ServerSocket(port); // Kreiramo ServerSocket
        System.out.println("SERVER POKRENUT!");
    }


    @Override
    public void run() {
                guiForm.updateStatus("SERVER JE POKRENUT!", Color.BLUE); // Ažuriramo GUI status nakon što je server stvarno pokrenut

            while (running) { // da li nastavlja s radom-true
                try {
                    /*Kreiranje novog ServerSocket neće omogućiti pravilno zatvaranje prethodnog socket-a.*/
                    Socket connectionSoket = serverSocket.accept(); // Čeka vezu
                    /*accept() ne prekida rad dok se ne pojavi nova veza ili dok se ServerSocket ne zatvori.*/
                    System.out.println("KLIJENT POVEZAN!");
                    ClientThread klijentNit = new ClientThread(connectionSoket);
                    klijentskeNiti.add(klijentNit);

                    
                }catch (IOException ex) {
                    if (!running) {
                        System.out.println("Server zaustavljen."); // zaustavljanje
                        break; // Izlazimo iz petlje kada je server zaustavljen
                    }
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        
    }
    
public void stopThread() {
    running = false; // Zaustavljamo server
    try {
        for (ClientThread nit : klijentskeNiti) {
            nit.zatvoriVezu(); // Zatvaramo sve klijentske veze
        }
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close(); // Zatvaramo ServerSocket
        }
    } catch (IOException ex) {
        Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
    }
}
public synchronized void dodajKlijenta(ClientThread klijent) {
    klijentskeNiti.add(klijent);
}

public synchronized void ukloniKlijenta(ClientThread klijent) {
    klijentskeNiti.remove(klijent);
}

public synchronized void prekiniSveKlijente() {
    for (ClientThread klijent : klijentskeNiti) {
        klijent.zatvoriVezu();
    }
    klijentskeNiti.clear();
}

}

