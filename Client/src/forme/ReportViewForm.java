/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package forme;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;  
import com.itextpdf.layout.element.Paragraph;
import domain.Report;
import domain.ReportItem;
import model.ReportItemTableModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.text.JTextComponent;
import communication.Communication;
import operations.Operation;
import transfer.ClientRequest;
import transfer.ServerResponse;


/**
 *
 * @author USER
 */

public class ReportViewForm extends javax.swing.JDialog {
    ReportItemTableModel modelProcedureTable;
    Report r;
  
 public ReportViewForm(java.awt.Dialog  parent, boolean modal,Report r) {
        super(parent,modal);
        initComponents();
        this.setTitle("Detalji izveštaja");

        //vel ekr da stavi formu
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Postavljanje veličine dijaloga na veličinu ekrana
        this.setSize(screenSize);
        
        this.r=r;
        txtPatientNameSurname.setText(r.getPatient().getName()+" "+r.getPatient().getSurname());
        txtJmbg.setText(r.getPatient().getJmbg());
        txtDoctor.setText(r.getDoctor().getName()+" "+r.getDoctor().getSurname());
        txtTherapy.setText(r.getGivenTherapy());
        txtDiagnosis.setText(r.getDiagnosisAsString());
        txtAnamnisis1.setText(r.getAnamnesis());
         setReportDateTime();
         popuniTbl();
        setFormFields();
        JOptionPane.showMessageDialog(this, "Sistem je učitao izveštaj.","",JOptionPane.INFORMATION_MESSAGE);
    
    }
 private void popuniTbl(){
     try{
     ReportItem ri=new ReportItem();
     ri.setIdReport(r.getId());
     
     ClientRequest kz=new ClientRequest();
     kz.setOperacija(Operation.PRONADJI_STAVKE_IZVESTAJA);
     kz.setObjekatOperacije(ri);
     
     Communication.getInstance().posaljiZahtev(kz);
     ServerResponse so=Communication.getInstance().primiOdgovor();
     
     List<ReportItem>items=(List <ReportItem>)so.getOdgovor();
 
    //setovanje modela
    modelProcedureTable=new ReportItemTableModel(items);
//setovanje tabela
 
  
      tblProtocol.setModel(modelProcedureTable);
   tblProtocol.setEnabled(false);
     } 
         catch(Exception e){
   JOptionPane.showMessageDialog(this, "Sistem ne može da učita izveštaj", "Greška", JOptionPane.ERROR_MESSAGE);
 JOptionPane.showMessageDialog(this, "Veza sa serverom je prekinuta!", "Greška", JOptionPane.ERROR_MESSAGE);
    System.exit(0); 
        }
 }
 private void setFormFields(){
   
        customizeTextComponent(txtDoctor);
        customizeTextComponent(txtJmbg);
        customizeTextComponent(txtPatientNameSurname);
       customizeTextComponent(txtDiagnosis);
        customizeTextComponent(txtAnamnisis1);
        customizeTextComponent(txtTherapy);


 
 }
private void setReportDateTime() {
    SpinnerDateModel dateModel = new SpinnerDateModel();
    SpinnerDateModel timeModel = new SpinnerDateModel();
    
    dateSpinner.setModel(dateModel);
    timeSpinner.setModel(timeModel);


    // Postavljanje vrednosti iz Report objekta
    if (r != null) {
        dateSpinner.setValue(java.sql.Date.valueOf(r.getPatientsAdmitionDate()));
        timeSpinner.setValue(java.sql.Time.valueOf(r.getPatientsAdmitionTime()));
    }
    
    JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd.MM.yyyy");
    JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");

    dateSpinner.setEditor(dateEditor);
    timeSpinner.setEditor(timeEditor);
    dateSpinner.setEnabled(false); // Potpuno onemogućava
    timeSpinner.setEnabled(false);

}

 
 
 
 
 //txtfield i txtarea

private void customizeTextComponent(JComponent component) {
    if (component instanceof JTextComponent) {
        JTextComponent textComponent = (JTextComponent) component;
      textComponent.setEditable(false); // Onemogućeno menjanje
        textComponent.setFocusable(false); // Onemogućeno selektovanje
        textComponent.setBackground(Color.LIGHT_GRAY);
        textComponent.setForeground(Color.RED);
    }
}
 private void saveAsPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sačuvaj izveštaj kao PDF");
        fileChooser.setSelectedFile(new File("izvestaj.pdf"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            //ako korisnik ne doda .pdf da doda automatski
            if (!fileToSave.getAbsolutePath().endsWith(".pdf")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
            }

            try {
                PdfWriter writer = new PdfWriter(fileToSave.getAbsolutePath());
                PdfDocument pdfDoc = new PdfDocument(writer);
                Document document = new Document(pdfDoc); 
                // Dodaj naslov
                document.add(new Paragraph("Izveštaj o pacijentu").setBold().setFontSize(16));

                // Dodaj podatke iz forme
                document.add(new Paragraph("Pacijent: " + r.getPatient().getName() + " " + r.getPatient().getSurname()));
                document.add(new Paragraph("JMBG: " + r.getPatient().getJmbg()));
                document.add(new Paragraph("Doktor: " + r.getDoctor().getName() + " " + r.getDoctor().getSurname()));
                document.add(new Paragraph("Dijagnoza: " + r.getDiagnosisAsString()));
                document.add(new Paragraph("Terapija: " + r.getGivenTherapy()));
                document.add(new Paragraph("Anamneza: " + r.getAnamnesis()));
                document.add(new Paragraph("Datum prijema: " + r.getPatientsAdmitionDate()));
                document.add(new Paragraph("Vreme prijema: " + r.getPatientsAdmitionTime()));
                document.close();
                JOptionPane.showMessageDialog(this, "PDF sačuvan na lokaciji: " + fileToSave.getAbsolutePath());            } 
            catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Greška pri čuvanju PDF-a.");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtJmbg = new javax.swing.JTextField();
        txtDoctor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTherapy = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProtocol = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtDiagnosis = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        txtPatientNameSurname = new javax.swing.JTextField();
        dateSpinner = new javax.swing.JSpinner();
        timeSpinner = new javax.swing.JSpinner();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtAnamnisis1 = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("frame()\n"); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Doktor");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Propisana terapija");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("PACIJENT");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Datum i vreme prijema");

        txtDoctor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDoctorActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("PROTOKOL:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Dijagnoza:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Anamneza");

        tblProtocol.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblProtocol);

        txtDiagnosis.setColumns(20);
        txtDiagnosis.setRows(5);
        jScrollPane5.setViewportView(txtDiagnosis);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Ime i prezime");

        txtPatientNameSurname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPatientNameSurnameActionPerformed(evt);
            }
        });

        txtAnamnisis1.setColumns(20);
        txtAnamnisis1.setRows(5);
        jScrollPane6.setViewportView(txtAnamnisis1);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("JMBG");

        jButton1.setBackground(new java.awt.Color(204, 204, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setText("SAČUVAJ KAO PDF");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTherapy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(340, 340, 340))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(timeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtPatientNameSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtJmbg, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96)
                .addComponent(txtDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(136, 136, 136))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1003, 1003, 1003)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(529, 529, 529)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(2, 2, 2))
                            .addComponent(txtPatientNameSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtJmbg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(dateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(timeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(182, 182, 182)
                                .addComponent(jLabel11))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(96, 96, 96)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(34, 34, 34))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTherapy, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))))
                .addGap(78, 78, 78)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDoctorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDoctorActionPerformed
        // TODOe add your handling code here:
    }//GEN-LAST:event_txtDoctorActionPerformed

    private void txtPatientNameSurnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPatientNameSurnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPatientNameSurnameActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        saveAsPDF();
    }//GEN-LAST:event_jButton1ActionPerformed

    
    
    
    
    
    
    
    
    
    
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner dateSpinner;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblProtocol;
    private javax.swing.JSpinner timeSpinner;
    private javax.swing.JTextArea txtAnamnisis1;
    private javax.swing.JTextArea txtDiagnosis;
    private javax.swing.JTextField txtDoctor;
    private javax.swing.JTextField txtJmbg;
    private javax.swing.JTextField txtPatientNameSurname;
    private javax.swing.JTextField txtTherapy;
    // End of variables declaration//GEN-END:variables


 
}
