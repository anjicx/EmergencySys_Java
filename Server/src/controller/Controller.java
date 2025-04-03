/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dbb.DBBrokerGenerics;
import domain.AbstractDO;
import forms.ServerForm;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import so.AbstractSO;
import so.doctor.SOIzmeniStatusDoktora;
import so.doctor.LoginSO;
import so.places.GetAllPlacesListSO;
import so.departments.GetDepartmentListSO;
import so.departments.SOVratiOdelenje;
import so.diagnosis.SOKreirajDijagnoze;
import so.diagnosis.SOPopunjeneDijagnoze;
import so.diagnosis.GetDiagnosisByConditionSO;
import so.doctor.UpdateDoctorSO;
import so.report.SaveReportSO;
import so.report.GetReportsSO;
import so.report.GetReportItemsSO;
import so.patient.UpdatePatientSO;
import so.patient.CreatePatientSO;
import so.patient.GetPatientListSO;
import so.patient.GetPatientSO;
import so.procedures.SOKreirajProcedure;
import so.procedures.SOPopunjeneProcedure;
import so.procedures.GetProceduresListSO;

/**
 *
 * @author USER
 */
public class Controller {
    private static Controller instance;
    DBBrokerGenerics dbb;
    ArrayList<Socket> listUser = new ArrayList<>();
    private ServerForm forma;


    private Controller() {
    }
    
    public static Controller getInstance(){
        if(instance==null){
            instance = new Controller();
        }
        return instance;
    }
    
    
    
     public ArrayList<Socket> getListUser() {
        return listUser;
    }
     
    public void addUser(Socket socket) {
        listUser.add(socket);
    }

    public ServerForm getForma() {
        return forma;
    }

    public void setForma(ServerForm forma) {
        this.forma = forma;
    }
    public AbstractDO loginSO(AbstractDO odo) throws Exception{
     AbstractSO oso=new LoginSO();
    oso.execute(odo);
    return ((LoginSO)oso).getLoggedDoctor();

 }
   public List<AbstractDO> getAllPlacesListSO(AbstractDO odo) throws Exception {
       AbstractSO oso = new GetAllPlacesListSO();
        oso.execute(odo);
    return ((GetAllPlacesListSO) oso).getLista();

    }
   //ако иф код инсерта онда ти креира 2пут позове операцију!
    public void createPatientSO(AbstractDO ado) throws Exception {
        AbstractSO oso = new CreatePatientSO();
        oso.execute(ado);
    }
    
     public void updatePatientSO(AbstractDO ado) throws Exception {

        AbstractSO oso = new UpdatePatientSO();
        oso.execute(ado);    }
      
    public List<AbstractDO>getReportsSO(AbstractDO odo) throws Exception{
        AbstractSO oso=new GetReportsSO();
        oso.execute(odo);
    return ((GetReportsSO)oso).getList();
    }
     public AbstractDO getPatientSO(AbstractDO odo) throws Exception {
       AbstractSO aso=new GetPatientSO();
       aso.execute(odo);
      return ((GetPatientSO) aso).getPatient();
    }
    
   public List<AbstractDO> getDepartmentListSO(AbstractDO odo) throws Exception{
        AbstractSO oso = new GetDepartmentListSO();
        oso.execute(odo);
    return ((GetDepartmentListSO) oso).getLista();
    }
 
   /*vraca procedure po odelenjima*/
   public List <AbstractDO> getProceduresListSO(AbstractDO odo) throws Exception{
    AbstractSO oso = new GetProceduresListSO();
    oso.execute(odo);
    return ((GetProceduresListSO) oso).getLista();    
    
    }
    public List<AbstractDO> getPatientListSO(AbstractDO ado) throws Exception {
   AbstractSO oso = new GetPatientListSO();
    oso.execute(ado);
    return ((GetPatientListSO) oso).getLista();     }
    
    public List<AbstractDO> getReportItemsSO(AbstractDO ado) throws Exception {
        
   AbstractSO oso = new GetReportItemsSO();
    oso.execute(ado);
    return ((GetReportItemsSO) oso).getLista();     
                }
    
    
   public AbstractDO saveReportSO(AbstractDO ado) throws Exception {
    AbstractSO so = new SaveReportSO();
    so.execute(ado);
    return ((SaveReportSO) so).getDomainObject();
}
    
      
    /*List<diagnosis>searchDiagnosis(search)*/
    public List<AbstractDO> getDiagnosisByConditionSO(AbstractDO odo) throws Exception{
    AbstractSO oso = new GetDiagnosisByConditionSO();
    oso.execute(odo);
    return ((GetDiagnosisByConditionSO) oso).getLista();
    }
   
    public void updateDoctorSO(AbstractDO odo) throws Exception {
       AbstractSO aso=new UpdateDoctorSO();
       aso.execute(odo);
    }
   
   
   
 
      //update login status-prijavi ili odjavi dr
 public void postaviStatusLogovanja(AbstractDO odo) throws Exception{
        AbstractSO aso=new SOIzmeniStatusDoktora();
       aso.execute(odo);
 }
  
 
    
   
   
   
    public AbstractDO vratiOdelenje(AbstractDO odo)throws Exception{
    AbstractSO oso = new SOVratiOdelenje();
    oso.execute(odo);
    return ((SOVratiOdelenje) oso).getObject();
    }
   
   
   //сервер део учитавања
    public void kreirajProcedure(List<AbstractDO> procedures) throws Exception {
    SOKreirajProcedure so = new SOKreirajProcedure();
    so.setLista(procedures);
    so.execute(null);
    }
    public void kreirajDijagnoze(List<AbstractDO> diagnosis) throws Exception {
    SOKreirajDijagnoze so = new SOKreirajDijagnoze();
    so.setLista(diagnosis);
    so.execute(null);
    }
    
    public int popunjeneProcedure(AbstractDO ado)throws Exception {
    SOPopunjeneProcedure so = new SOPopunjeneProcedure();
     so.execute(ado);
     return so.getNum();
    }
    
    public int popunjeneDijagnoze(AbstractDO ado)throws Exception {
    SOPopunjeneDijagnoze so = new SOPopunjeneDijagnoze();
     so.execute(ado);
     return so.getNum();
    }
    

}
