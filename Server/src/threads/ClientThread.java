/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threads;

import controller.Controller;
import domain.AbstractDO;
import domain.Department;
import domain.Diagnosis;
import domain.Doctor;
import domain.Patient;
import domain.Place;
import domain.Procedure;
import domain.Report;
import domain.ReportItem;
import exception.ServerException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import operations.Operation;
import transfer.ClientRequest;
import transfer.ServerResponse;

/**
 *
 * @author USER
 */
public class ClientThread extends Thread {
    /*ovo je nit za jednog klijenta!!*/
    private Socket connectionSocket;
//SERVER PRIMA ODG KLIJENT
//SERVER SALJE ODG KLIJENTU
    private boolean running=true;
    public ClientThread(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
        start();
    }  
   

 
 @Override
public void run() {
    while (running&& !connectionSocket.isClosed()) { // Proveravamo i da li je konekcija zatvorena
        try {//DA HVATA GRESKU KOMUNIKACIJE S KLIJENTOM
           
            ClientRequest kz = primiZahtev();
           if (kz == null) {//posalje se null kz onda se prekida nit znak zatvaranja niti
                break; // Prekidamo rad ako nema više zahteva zatvorena nit
            }
         
            ServerResponse sodg = new ServerResponse();
  switch (kz.getOperacija()) {
    case Operation.LOGIN:
        try {
            //SISTEMSKA OPERACIJA PRONADJI DOKTORA U BAZI
            AbstractDO korisnik = Controller.getInstance().loginSO((Doctor) kz.getObjekatOperacije());
            //ako dr s poslatim vr ne postoji u bazi
            if (korisnik == null) {
                throw new ServerException("Ne postoji korisnik sa unetim vrednostima!");
            }
            Doctor d = (Doctor) korisnik;
            // dda li doktor vec ulogovan
            if (d.isLogged()) {
                throw new ServerException("Već ste ulogovani!");
            }
            //ako dovde onda nema greski
            // unesi u bazu da je ulogovan
            else{
            d.setLogged(true);//izmenis vrednost koju unosis u bazu
            //SISTEMSKA OPERACIJA PRIJAVE KORISNIKA
            Controller.getInstance().postaviStatusLogovanja(d);
            // uspesan login odgovor
            sodg.setUspesnost(true);
            sodg.setOdgovor(d);
            sodg.setPoruka("Uspešno ulogovani.");
            //tbl azuriranje
            //daj mi server formu i dodaj na modelu njene tbl korisnika
            Controller.getInstance().getForma().getTblUsers().addUser(d);

       }
            
            } catch (ServerException ex) {
            //specificne greske
            sodg.setUspesnost(false);
            sodg.setOdgovor(null);
            sodg.setPoruka(ex.getMessage());
        } catch (Exception ex) {
            // ako se neka nepredvidjena greska desi
            sodg.setUspesnost(false);
            sodg.setOdgovor(null);
            sodg.setPoruka("Došlo je do greške prilikom prijavljivanja. Pokušajte ponovo.");//ако није покренут сервер!
            
        }
        break;
        case Operation.ODJAVA:
              try {
            //primi obj operacije i setuje na false logovanje
           Doctor d = (Doctor) kz.getObjekatOperacije();
           d.setLogged(false);
            // unesi u bazu da je ulogovan
            Controller.getInstance().postaviStatusLogovanja(d);
            // uspesan login odgovor
            sodg.setUspesnost(true);
            sodg.setOdgovor(d);
            sodg.setPoruka("Uspešno ste se odjavili sa sistema.");
            Controller.getInstance().getForma().getTblUsers().removeUser(d);

        }  catch (Exception ex) {
            // ako se neka nepredvidjena greska desi
            sodg.setUspesnost(false);
            sodg.setOdgovor(null);
            sodg.setPoruka("Došlo je do greške prilikom odjavljivanja. Pokušajte ponovo.");
        }  
            
            
            break;
        case Operation.VRATI_MESTA://IMAMO 2 CMB POPUNJAVANJE
                     try  
                     {
                        List<AbstractDO>mesta=Controller.getInstance().getAllPlacesListSO((Place)kz.getObjekatOperacije());
                        if (mesta == null) {
                            throw new ServerException("Greska prilikom popunjavanja cmb mesta");
                        }
                        sodg.setUspesnost(true);
                        sodg.setOdgovor(mesta);
                    } catch (Exception ex) {
                        sodg.setUspesnost(false);
                        Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                    
                 case Operation.VRATI_ODELENJA://IMAMO 2 CMB POPUNJAVANJE
                     try {
                        List<AbstractDO>odelenja=Controller.getInstance().getDepartmentListSO((Department)kz.getObjekatOperacije());
                          if (odelenja == null) {
                            throw new ServerException("Greska prilikom popunjavanja cmb mesta");
                        }
                        sodg.setUspesnost(true);
                        sodg.setOdgovor(odelenja);
                    } catch (Exception ex) {
                        sodg.setUspesnost(false);
                        Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                          
                 case Operation.VRATI_PROCEDURE:
                     try {
                        List<AbstractDO>listP=(List<AbstractDO>) 
                                Controller.getInstance().getProceduresListSO((Procedure)kz.getObjekatOperacije());
                          if (listP == null) {
                            throw new ServerException("Greska prilikom popunjavanja cmb procedura");
                        }
                          System.out.println("LISTP JE");
                          for(AbstractDO list:listP){
                              System.out.println(list.toString());
                          }
                                  
                        sodg.setUspesnost(true);
                        sodg.setOdgovor(listP);
                    } catch (Exception ex) {
                        sodg.setUspesnost(false);
                        sodg.setOdgovor(null);
                        Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                    
                    
                    
                     case Operation.VRATI_PACIJENTE:
                     try {
                        List<AbstractDO>patient=Controller.getInstance().getPatientListSO((Patient)kz.getObjekatOperacije());
                        if (patient == null) {
                            throw new ServerException("Greska prilikom pretrage pacijenta");
                        }
                        sodg.setUspesnost(true);
                        sodg.setOdgovor(patient);
                    } catch (Exception ex) {
                        sodg.setUspesnost(false);
                        Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                     case Operation.VRATI_PACIJENTA:
                     try {
                     AbstractDO patient=Controller.getInstance().getPatientSO((Patient)kz.getObjekatOperacije());
                       
                        //KAKO JOPTIONPANE DA OVO PRIKAZE AKO GRESKA
                       sodg.setUspesnost(true);
                        sodg.setOdgovor(patient);
                        sodg.setPoruka("Sistem je učitao pacijenta.");
                    } catch (Exception ex) {
                        sodg.setUspesnost(false);
                        sodg.setOdgovor("");
                        sodg.setPoruka("Sistem ne može da učita pacijenta.");
                        Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                      
                    case Operation.KREIRAJ_PACIJENTA://IMAMO 2 CMB POPUNJAVANJE
                     try {
                        Controller.getInstance().createPatientSO((Patient)kz.getObjekatOperacije());
                        sodg.setUspesnost(true);
                        sodg.setOdgovor("Uspešno kreiran pacijent!");
                    } catch (Exception ex) {
                        sodg.setUspesnost(false);
                        Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                    case Operation.IZMENI_PACIJENTA://IMAMO 2 CMB POPUNJAVANJE
                     try {/* boolean uspeh=  OVAKO NESTO IZMENI*/
                      Controller.getInstance().updatePatientSO((Patient)kz.getObjekatOperacije());
                        sodg.setUspesnost(true);
                        sodg.setOdgovor("Zadati pacijent je izmenjen.");
                    } catch (Exception ex) {
                        sodg.setUspesnost(false);
                        sodg.setOdgovor("Sistem ne može da izmeni pacijenta");

                        Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                    
                     case Operation.IZMENI_KORISNIKA:
                           try {
                      Controller.getInstance().updateDoctorSO((Doctor)kz.getObjekatOperacije());
                        sodg.setUspesnost(true);
                        sodg.setOdgovor("Uspešno izmenjeni podaci!");
                    } catch (Exception ex) {
                        sodg.setUspesnost(false);
                        sodg.setOdgovor("Sistem ne može da izmeni podatke.");
                        Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                    
                     case Operation.VRATI_DIJAGNOZE:
                         try {/* boolean uspeh=  OVAKO NESTO IZMENI*/
                      List <AbstractDO> list=Controller.getInstance().getDiagnosisByConditionSO((Diagnosis)kz.getObjekatOperacije());
                        sodg.setUspesnost(true);
                        sodg.setOdgovor(list);
                    } catch (Exception ex) {
                        sodg.setUspesnost(false);
                        Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                         break;//AKO NE BACIS BREAK GRESKAA!!!!!!!
                     
                    /*AKO IMAS JEDNU KONEKCIJU NE,KAO 1 SIST OPER SACUVAJ IZVESTAJ
                    A DA 3CUVA*/
                    
                    case Operation.KREIRAJ_IZVESTAJ_GLAVNI:
                try {
                    //dobijam report
                Report report = (Report) kz.getObjekatOperacije();
                 // Unos glavnog dela izvestaja i dobijanje generisanog ID-a
                Report insertedReport = (Report) Controller.getInstance().saveReportSO(report);
                //ovde nema commit nego ovo uradi bez commit-a pa onda vrati se na kraju idSlobodan koji je
                if(insertedReport==null){
                  throw new Exception("Insert za Report nije uspeo.");
                }
                 sodg.setUspesnost(true);

                      }
                      catch (Exception ex) {
                sodg.setUspesnost(false);
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                     }
                     break;
                     //ODAVDE
                case Operation.PRONADJI_IZVESTAJE:
                     try {
                    //dobijam report odnosno iz njega dobijam onda pacijent pretragu
                Report report = (Report) kz.getObjekatOperacije();
                
                 // Unos glavnog dela izvestaja i dobijanje generisanog ID-a
                List <AbstractDO> list =  Controller.getInstance().getReportsSO(report);
                //ovde nema commit nego ovo uradi bez commit-a pa onda vrati se na kraju idSlobodan koji je
                
                sodg.setOdgovor(list);
                 sodg.setUspesnost(true);

                      }
                      catch (Exception ex) {
                sodg.setUspesnost(false);
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                     }
                    
                    
                    break;
                    case Operation.PRONADJI_STAVKE_IZVESTAJA:
                     try {
                    //dobijam report odnosno iz njega dobijam onda pacijent pretragu
                ReportItem reportitem = (ReportItem) kz.getObjekatOperacije();
                
                 // Unos glavnog dela izvestaja i dobijanje generisanog ID-a
                List <AbstractDO> list =  Controller.getInstance().getReportItemsSO(reportitem);
                //ovde nema commit nego ovo uradi bez commit-a pa onda vrati se na kraju idSlobodan koji je
                if(list==null){
                  throw new Exception("Nema stavki izvestaja.");
                }
                System.out.println("Vraćam listu sa " + list.size() + " stavki.");

                sodg.setOdgovor(list);
                sodg.setUspesnost(true);

                      }
                      catch (Exception ex) {
                sodg.setUspesnost(false);
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                     }
                    
                    
                    break;
                    
                          
                     
                             
                default:
                    throw new AssertionError();
            }
            posaljiOdgovor(sodg);
        } catch (Exception ex) {
            System.out.println("Greška u komunikaciji sa klijentom: " + ex.getMessage());
            break;
        }
    }
    zatvoriVezu(); // Zatvaramo soket kada nit završi rad
    System.out.println("Nit za klijenta je prekinuta.");
}


//deserijaliz citanje iz streama
   //inputstream readobj
 private ClientRequest primiZahtev() {
    try {
        ObjectInputStream ois = new ObjectInputStream(connectionSocket.getInputStream());
        return (ClientRequest) ois.readObject();
    } catch (IOException ex) {
        System.out.println("Klijent je prekinuo vezu: " + ex.getMessage());
        zatvoriVezu();  // Zatvaramo vezu i zaustavljamo nit
        running = false;
    } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(ClientRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    return null;
}

//output write
    private void posaljiOdgovor(ServerResponse sodg) {
ObjectOutputStream oos;
        try {
            oos=new ObjectOutputStream(connectionSocket.getOutputStream());
            oos.writeObject(sodg);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientRequest.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public Socket getConnectionSocket() {
        return connectionSocket;
    }

    public void setConnectionSocket(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }
 public void zatvoriVezu() {
    try {
running=false;
if (connectionSocket != null && !connectionSocket.isClosed()) {
            connectionSocket.close();  // Zatvaramo soket
        }
    } catch (IOException ex) {
        java.util.logging.Logger.getLogger(ClientThread.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
 }
    
}
