/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package forme;
import communication.Communication;
import domain.Patient;
import domain.Place;
import exception.ClientException;
import form.mode.FormMode;
import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import operations.Operation;
import transfer.ClientRequest;
import transfer.ServerResponse;

public class PatientForm extends javax.swing.JDialog {
    private Patient patient=null;
    
    public PatientForm(java.awt.Frame parent,Patient p,  FormMode mode, boolean modal) {
        super(parent, modal);
       
          if (p!=null) {//prosledjen pacijent-edit view mode
            patient = getPatientFromServer(p);
            if (patient== null) {
                this.dispose();
                return;
            }}
        initComponents();//u slucaju greske da mi ne otvori formu ovu
        this.setLocationRelativeTo(parent);
      //postavljas spinner Java.sql.date je podklasa java.util, pa konverzijom u nju zavr posao
        SpinnerDateModel dateModel = new SpinnerDateModel();
        birthdayDateSpinner.setModel(dateModel);
        // datum za spiner editujes
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(birthdayDateSpinner, "dd.MM.yyyy");
        birthdayDateSpinner.setEditor(dateEditor);
        setupForm(mode,dateModel);
       
       
         
        
        }
          private Patient getPatientFromServer(Patient pat) {
              try{
        ClientRequest kz = new ClientRequest();
        kz.setObjekatOperacije(pat);
        kz.setOperacija(Operation.VRATI_PACIJENTA);

        Communication.getInstance().posaljiZahtev(kz);
        ServerResponse odg = Communication.getInstance().primiOdgovor();

        if (!odg.getUspesnost()) {
           JOptionPane.showMessageDialog(this, odg.getPoruka(), "", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        JOptionPane.showMessageDialog(this, odg.getPoruka(), "", JOptionPane.INFORMATION_MESSAGE);
        return (Patient) odg.getOdgovor();} 
        catch (Exception ex) {
    JOptionPane.showMessageDialog(this, "Veza sa serverom je prekinuta!", "Greška", JOptionPane.ERROR_MESSAGE);
    System.exit(0); 
}return null;
    }
    
       private void setupForm(FormMode mode,SpinnerDateModel dateModel) {
           fillCmb();//za svaki mode mora da se ucita prvo cmb!
    if (mode == FormMode.FORM_CREATE) {
//SAMO SACUVAJ
        jButtonUpdate.setVisible(false);
        jButtonPrikazi.setVisible(false);
        setupDateSpinnerForNewPatient(dateModel);

    } else if (patient != null) { // Edit / View mode
        fillFormWithPatientData(dateModel);
        if (mode == FormMode.FORM_VIEW) {
            disableEditing();  
        } else if (mode == FormMode.FORM_EDIT) {
            jButtonSave.setVisible(false); 
            jButtonPrikazi.setVisible(false); 
        }
    }
}
    //za create
        private void setupDateSpinnerForNewPatient( SpinnerDateModel dateModel) {
        java.sql.Date minDate = java.sql.Date.valueOf(LocalDate.now().minusYears(140));
        Date maxDate = new Date();
        dateModel.setStart(minDate);
        dateModel.setEnd(maxDate);
    }
//za edit/view
    private void fillFormWithPatientData(SpinnerDateModel dateModel) {//update+view
        txtJmbg.setText(patient.getJmbg());
        txtName.setText(patient.getName());
        txtSurname.setText(patient.getSurname());
        txtNumber.setText(patient.getNumber());
        cmbPlace.setSelectedItem(patient.getPlace());
        System.out.println(patient.getPlace().toString());
        Date birthdayDate = java.sql.Date.valueOf(patient.getBirthday());
        dateModel.setValue(birthdayDate);
    }

    
   private void customizeTextField(JTextField textField, boolean editable, boolean focusable, Color background, Color foreground) {
        textField.setEditable(editable);
        textField.setFocusable(focusable);
        textField.setBackground(background);
        textField.setForeground(foreground);
    }
   
    private void disableEditing() {//view
        jButtonSave.setVisible(false);
        jButtonUpdate.setVisible(false);
        customizeTextField(txtName, false, false, Color.lightGray, Color.black);
        customizeTextField(txtSurname, false, false, Color.lightGray, Color.black);
        customizeTextField(txtJmbg, false, false, Color.lightGray, Color.black);
        customizeTextField(txtNumber, false, false, Color.lightGray, Color.black);
        cmbPlace.setEnabled(false);
        birthdayDateSpinner.setEnabled(false);
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtJmbg = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtSurname = new javax.swing.JTextField();
        cmbPlace = new javax.swing.JComboBox<>();
        jButtonSave = new javax.swing.JButton();
        birthdayDateSpinner = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        txtNumber = new javax.swing.JTextField();
        jButtonUpdate = new javax.swing.JButton();
        jButtonPrikazi = new javax.swing.JButton();

        jLabel6.setText("JMBG");

        jButton7.setBackground(new java.awt.Color(204, 204, 255));
        jButton7.setFont(new java.awt.Font("Palatino Linotype", 3, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(204, 0, 0));
        jButton7.setText("PRIKAŽI IZVEŠTAJE PACIJENTA");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PACIJENT");

        jLabel1.setText("IME");

        jLabel2.setText("PREZIME");

        jLabel3.setText("MESTO");

        jLabel4.setText("DATUM RODJENJA");

        jLabel5.setText("JMBG");

        txtJmbg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJmbgActionPerformed(evt);
            }
        });

        cmbPlace.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbPlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPlaceActionPerformed(evt);
            }
        });

        jButtonSave.setBackground(new java.awt.Color(204, 204, 255));
        jButtonSave.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonSave.setForeground(new java.awt.Color(204, 0, 0));
        jButtonSave.setText("SAČUVAJ");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jLabel7.setText("BROJ TELEFONA");

        jButtonUpdate.setBackground(new java.awt.Color(204, 204, 255));
        jButtonUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonUpdate.setForeground(new java.awt.Color(204, 0, 0));
        jButtonUpdate.setText("IZMENI");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jButtonPrikazi.setBackground(new java.awt.Color(204, 204, 255));
        jButtonPrikazi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonPrikazi.setForeground(new java.awt.Color(204, 0, 0));
        jButtonPrikazi.setText("PRIKAŽI IZVEŠTAJE PACIJENTA");
        jButtonPrikazi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrikaziActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(birthdayDateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(80, 80, 80)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtJmbg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(119, 119, 119)
                                .addComponent(cmbPlace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jButtonPrikazi, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(201, 201, 201))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtJmbg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(birthdayDateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbPlace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jButtonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonPrikazi, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtJmbgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJmbgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJmbgActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        // TODO add your handling code here:
        
try{
 Patient patient=new Patient();
        patient.setJmbg(txtJmbg.getText());
        patient.setName(txtName.getText());
        patient.setSurname(txtSurname.getText());
        patient.setNumber(txtNumber.getText());
        patient.setPlace((Place) cmbPlace.getSelectedItem());
        
//java.util.Date->java.sql.date->LocalDate        
        java.util.Date givenDate = (java.util.Date) birthdayDateSpinner.getValue();
        java.sql.Date sqlDate = new java.sql.Date(givenDate.getTime());
        LocalDate localDate = sqlDate.toLocalDate(); 
        patient.setBirthday(localDate);
        validateValues();
        int i=JOptionPane.showConfirmDialog(this, "Da li ste sigurni da želite da sačuvate podatke o unetom pacijentu?","KREIRANJE PACIJENTA",JOptionPane.YES_NO_OPTION);
        if(i==JOptionPane.YES_OPTION){
           ClientRequest kz=new ClientRequest();
        
        kz.setOperacija(Operation.KREIRAJ_PACIJENTA);
        kz.setObjekatOperacije(patient);
        
        Communication.getInstance().posaljiZahtev(kz);
        ServerResponse sod=Communication.getInstance().primiOdgovor();
        /*cmb moze i sa AbstractDO da se puni*/
        /*ABSTRACTDO PROBAJ AKO NE*/
        JOptionPane.showMessageDialog(this, sod.getOdgovor().toString(), "", JOptionPane.INFORMATION_MESSAGE);
        this.dispose(); 
        }
        
        

}catch(Exception ex){
    JOptionPane.showMessageDialog(this, "Sistem ne može da sačuva pacijenta. Detalj: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
   
    

}
       
       
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        // TODO add your handling code here:
        try{   
            
        patient.setJmbg(txtJmbg.getText());
        patient.setName(txtName.getText());
        patient.setSurname(txtSurname.getText());
        patient.setNumber(txtNumber.getText());
        patient.setPlace((Place) cmbPlace.getSelectedItem());
        
//java.util.Date->java.sql.date->LocalDate        
        java.util.Date givenDate = (java.util.Date) birthdayDateSpinner.getValue();
        java.sql.Date sqlDate = new java.sql.Date(givenDate.getTime());
        LocalDate localDate = sqlDate.toLocalDate(); // pretvaranje u localdate
        patient.setBirthday(localDate);
        System.out.println(localDate);

        /*da proveri unete vrednosti*/
        
        validateValues();
        int i=JOptionPane.showConfirmDialog(this, "Da li ste sigurni da želite da izmenite pacijenta?", "IZMENA", JOptionPane.YES_NO_OPTION);
        if(i==JOptionPane.YES_OPTION) {
        ClientRequest kz=new ClientRequest();
        kz.setOperacija(Operation.IZMENI_PACIJENTA);
        kz.setObjekatOperacije(patient);
        
        Communication.getInstance().posaljiZahtev(kz);
        ServerResponse sod=Communication.getInstance().primiOdgovor();
            System.out.println(sod.getOdgovor().toString());
        JOptionPane.showMessageDialog(this, sod.getOdgovor(), "Izmenjen pacijent", JOptionPane.INFORMATION_MESSAGE);
   this.dispose();
        }}
        catch( ClientException ex){ //hvata se vec greska u validate
}catch(Exception ex){
    JOptionPane.showMessageDialog(this, "Pacijent nije izmenjen! Detalj: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
   
    

}
    
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void cmbPlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPlaceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPlaceActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        /*ovaj deo se ponavlja moraces kao fju ovo za row!*/

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButtonPrikaziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrikaziActionPerformed
        // TODO add your handling code here:
        /*ovaj deo se ponavlja moraces kao fju ovo za row!*/

     
        ReportsView rvf=new ReportsView(this,true,patient);
        rvf.setVisible(true);

    }//GEN-LAST:event_jButtonPrikaziActionPerformed

    /**
     * @param args the command line arguments
     */
    
    private void fillCmb() {
    try{
      cmbPlace.removeAllItems();
        ClientRequest kz=new ClientRequest();
        
        kz.setOperacija(Operation.VRATI_MESTA);
        kz.setObjekatOperacije(new Place());
        
        Communication.getInstance().posaljiZahtev(kz);
        ServerResponse sod=Communication.getInstance().primiOdgovor();
        /*cmb moze i sa AbstractDO da se puni*/
        /*ABSTRACTDO PROBAJ AKO NE*/
        List<Place>mesta=(List<Place>) sod.getOdgovor();
        
        cmbPlace.setModel(new DefaultComboBoxModel(mesta.toArray()));
        cmbPlace.setSelectedIndex(-1);
    } catch (Exception ex) {
    JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
}
       
    }
    
    private void validateValues() throws ClientException {
    List<String> errors = new ArrayList<>();

    // jmbg 
    String jmbg = txtJmbg.getText().trim();
    if (jmbg.isEmpty()) {
        errors.add("* Morate uneti JMBG.");
    } else if (!jmbg.matches("\\d{13}")) {
        errors.add("* JMBG mora imati tačno 13 cifara i sme sadržavati samo brojeve.");
    }

    // Provera imena
    String name = txtName.getText().trim();
    if (name.isEmpty()) {
        errors.add("* Morate uneti ime.");
    } else if (!name.matches("[A-Za-zšđčćžŠĐČĆŽ ]+")) {
        errors.add("* Ime može sadržavati samo slova.");
    }

    // Provera prezimena
    String surname = txtSurname.getText().trim();
    if (surname.isEmpty()) {
        errors.add("* Morate uneti prezime.");
    } else if (!surname.matches("[A-Za-zšđčćžŠĐČĆŽ ]+")) {
        errors.add("* Prezime može sadržavati samo slova.");
    }

    // Provera broja telefona
    String number = txtNumber.getText().trim();
    if (number.isEmpty()) {
        errors.add("* Morate uneti broj telefona.");
    } else if (!number.matches("\\+?[0-9 /-]{6,15}")) {
        errors.add("* Neispravan format broja telefona. Primer: +381 64 1234567.");
    }

    // Provera mesta
    if (cmbPlace.getSelectedItem() == null) {
        errors.add("* Morate izabrati mesto.");
    }


    // Ako postoje greske prikazi i baci izuzetak 
    if (!errors.isEmpty()) {
        String errorMessage = "Sistem ne može da sačuva pacijenta.Ispravite sledeće greške:\n" + String.join("\n", errors);
        JOptionPane.showMessageDialog(this, errorMessage, "Greška pri unosu", JOptionPane.ERROR_MESSAGE);
        throw new ClientException("Validacija nije prošla.");
    }
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner birthdayDateSpinner;
    private javax.swing.JComboBox<String> cmbPlace;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButtonPrikazi;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtJmbg;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNumber;
    private javax.swing.JTextField txtSurname;
    // End of variables declaration//GEN-END:variables
}
