/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package communication;

import exception.ClientException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import transfer.ClientRequest;
import transfer.ServerResponse;

/**
 *
 * @author USER
 */
    
    //OVOM PRISTUPAMO IZ FORMI
    //A KLIJENTNIT SE DESAVA U OKVIRU SERVERA
  public class Communication {

    private Socket clientSocket;
    private static Communication instance;
    private Exception lastException; //  poslednja greska povezivanja

    private Communication() {
        try {
            clientSocket = new Socket("localhost", 9000); // vrata na koja salje
        } catch (IOException ex) {
            lastException = new ClientException("Neuspešno povezivanje sa serverom!"); // cuva gresku ovo je ako je server
        }
    }

    public static Communication getInstance() {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }
  

    public Exception getLastException() {
        return lastException; // vraca gresku
    }


    
    //posalje zahtev od nekog klijenta
    //primi odgovor od 1servera
    //singleton jer ako 1klijent pos serveru izmeni i procitaj npr objekat.

    //KLIJENT SALJE ZAHTEV SERVERU
    // KLIJENT PRIMA ZAHTEV OD SERVERA.
  
//output write
public void posaljiZahtev(ClientRequest kz) throws IOException {
ObjectOutputStream oos; 
        try {
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.writeObject(kz);
            oos.flush();
        } catch (IOException ex) {
        lastException = ex; // Čuvamo poslednju grešku
   
        throw new IOException("Greška u komunikaciji sa serverom! Server je možda ugašen.", ex);
    }
 }

    
public ServerResponse primiOdgovor() throws IOException{
        try {
            ObjectInputStream ois=new ObjectInputStream(clientSocket.getInputStream());
            return (ServerResponse)ois.readObject();
        } 
        catch (IOException | ClassNotFoundException ex) {
        lastException = ex; // Čuvamo poslednju grešku
            
                throw new IOException("Veza sa serverom je prekinuta!", ex);
           
        }
}
 public void zatvoriVezu() {
    try {
        if (clientSocket != null && !clientSocket.isClosed()) {
            clientSocket.close();  // Zatvaramo soket
        }
    } catch (IOException ex) {
        java.util.logging.Logger.getLogger(Communication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    
 }}