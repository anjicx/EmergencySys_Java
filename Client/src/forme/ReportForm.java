/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package forme;
import communication.Communication;
import domain.Diagnosis;
import domain.DiagnosisReport;
import model.DiagnosisTableModel;
import domain.Doctor;
import domain.Patient;
import domain.Report;
import domain.ReportItem;
import model.DiagnosisAddModel;
import model.ReportItemTableModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.TableColumn;
import operations.Operation;
import transfer.ClientRequest;
import transfer.ServerResponse;

/**
 *
 * @author USER
 */

public class ReportForm extends javax.swing.JDialog {
   private List<Diagnosis> searchResults; // Rezultati pretrage tbl1
    private List<DiagnosisReport> addedDiagnoses; // Izabrane dijagnoze tbl2
    private List<ReportItem>items;//tabela sa report items
    private DiagnosisTableModel modelSearchDiagnosis ;
    private DiagnosisAddModel  modelAddDiagnosis ;
     private Doctor doctor;
    private LocalDate patientsAdmitionDate;
    private LocalTime patientsAdmitionTime;
ReportItemTableModel modelReportTable;
  private Patient patient;
  private String anamnesis;
  private String therapy;
  private boolean isEditing = false; // Praćenje stanja uređivanja

  //ako je iz main forme onda je create mode
  
  public ReportForm(java.awt.Frame parent, boolean modal,Patient p,Doctor d) {
         super(parent, modal);
         initComponents();

        //vel ekr da stavi formu
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Postavljanje veličine dijaloga na veličinu ekrana
        this.setSize(screenSize);
        this.setLocationRelativeTo(null); // Postavljanje u centar
        
        txtPatientNameSurname.setText(p.getName()+" "+p.getSurname());
        txtJmbg.setText(p.getJmbg());
        txtDoctor.setText(d.toString());
        //pacijent i doktor u modelu
        doctor=d;
        System.out.println(d.getName());
        patient=p;
        
        customizeTextField(txtDoctor, false, false, Color.lightGray, Color.red);
        customizeTextField(txtJmbg, false, false, Color.lightGray, Color.red);
        customizeTextField(txtPatientNameSurname, false, false, Color.lightGray, Color.red);
        
        searchResults=new ArrayList<>();
        addedDiagnoses = new ArrayList<>();
        items=new ArrayList<>();
                
        // Rezultati pretrage  
        initializeTables();//postavi nazive kolona
         setReportDateTime();

        
    
        //
    }
  
 
 private void setReportDateTime(){
    SpinnerDateModel dateModel = new SpinnerDateModel();
    SpinnerDateModel timeModel = new SpinnerDateModel();
    dateSpinner.setModel(dateModel);
    timeSpinner.setModel(timeModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd.MM.yyyy");
    //maksimum je danasnji datum za kreiranje izvestaja
    //minimum moze biti do pre 10dana jer dr ne unosi izvestaj mzd istog dana
    //al ne moze za sledeci dan pa je max danasnji datum ovde
    //ogranicenja
     java.sql.Date minDate = java.sql.Date.valueOf(LocalDate.now().minusDays(10));
     dateModel.setStart(minDate); // Minimalni datum 
     dateModel.setEnd(new Date()); // Maksimalni datum (danas)


        //format vreme
     JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
     

     //setovanje date i time
        dateSpinner.setEditor(dateEditor); // Formatiranje datuma
        timeSpinner.setEditor(timeEditor);
 
 
 }
 
 
 public LocalDate getDate(){
     java.util.Date givenDate = (java.util.Date) dateSpinner.getValue();
     java.sql.Date sqlDate = new java.sql.Date(givenDate.getTime());
     LocalDate localDate = sqlDate.toLocalDate(); 
     return localDate;
 }
 public LocalTime getTime(){
 
 
    java.util.Date givenTime = (java.util.Date) timeSpinner.getValue();  
    java.sql.Time sqlTime = new java.sql.Time(givenTime.getTime());  
    LocalTime localTime = sqlTime.toLocalTime();  
    return localTime;
 
 
 }
 /*da vrati izvestaje od datuma sa datumom*/

 
 
 
 
 //fja zajednicka za sve ove delove-bez ponavljanja da bude sve osobine podesene
private void customizeTextField(JTextField textField, boolean editable, boolean focusable, Color background, Color foreground) {
    textField.setEditable(editable);//da nema menjanja
    textField.setFocusable(focusable);//da nema selektovanja
    //postavila boju pozadine i txt za ove podatke popunjene
    textField.setBackground(background);
    textField.setForeground(foreground);
}
private void initializeTables() {
    //setovanje modela
    modelSearchDiagnosis = new DiagnosisTableModel(searchResults);
    modelAddDiagnosis = new DiagnosisAddModel(addedDiagnoses);
    modelReportTable=new ReportItemTableModel(items);
//setovanje tabela
  tblSearchDiagnosis.setModel(modelSearchDiagnosis);
  tblAddDiagnosis.setModel(modelAddDiagnosis);
  //da postavi cmb
  //KATEGORIJE CMB MODELA
  String[] categories = {"PRIMARNA DIJAGNOZA", "SEKUNDARNA DIJAGNOZA", "DIFERENCIJALNA DIJAGNOZA", "KORMBIDITET", "PREDISPOZICIJA", "POVREDA"};
 //KOLONA TABELE NA KOJU STAVLJAMO
  TableColumn categoryColumn = tblAddDiagnosis.getColumnModel().getColumn(2);//od tabele
  //PRAVIMO CMB SA KATEGORIJAMA
  JComboBox<String> comboBox = new JComboBox<>(categories);
  //STAVLJAMO NA ODABRANU KOLONU
  categoryColumn.setCellEditor(new DefaultCellEditor(comboBox));
  
  //ZA PREBRZU IZMENUUU
   tblAddDiagnosis.addPropertyChangeListener(evt -> {
        if ("tableCellEditor".equals(evt.getPropertyName())) {
            if (tblAddDiagnosis.isEditing()) {
                if (isEditing) {
                    System.out.println("Nije moguće urediti dok je trenutna izmena u toku.");
                    tblAddDiagnosis.getCellEditor().stopCellEditing(); // Završava aktivno uređivanje
                } else {
                    System.out.println("Uređivanje započeto.");
                    isEditing = true; // Postavlja stanje na "uređuje se"
                }
            } else {
                System.out.println("Uređivanje završeno.");
                isEditing = false; // Resetuje stanje na "nije u toku"
                modelAddDiagnosis.fireTableDataChanged(); // Osvežava tabelu
            }
        }
    });
  
  
  
  
  tblProtocol.setModel(modelReportTable);
  
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
        txtTherapy = new javax.swing.JTextField();
        jButtonAddProcedure = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProtocol = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSearchDiagnosis = new javax.swing.JTable();
        jButtonDelete = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblAddDiagnosis = new javax.swing.JTable();
        jButtonChoose = new javax.swing.JButton();
        jButtonSaveReport = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtAnamnisis = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        txtPatientNameSurname = new javax.swing.JTextField();
        dateSpinner = new javax.swing.JSpinner();
        timeSpinner = new javax.swing.JSpinner();
        jButtonChooseD = new javax.swing.JButton();
        jButtonDeleteProcedure = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();

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

        jLabel1.setFont(new java.awt.Font("Palatino Linotype", 1, 12)); // NOI18N
        jLabel1.setText("Doktor:");

        jLabel2.setFont(new java.awt.Font("Palatino Linotype", 1, 12)); // NOI18N
        jLabel2.setText("Propisana terapija");

        jLabel3.setFont(new java.awt.Font("Palatino Linotype", 1, 12)); // NOI18N
        jLabel3.setText("Pacijent");

        jLabel4.setFont(new java.awt.Font("Palatino Linotype", 1, 12)); // NOI18N
        jLabel4.setText("Datum i vreme prijema");

        txtDoctor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDoctorActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Palatino Linotype", 1, 12)); // NOI18N
        jLabel10.setText("PROTOKOL:");

        jLabel11.setFont(new java.awt.Font("Palatino Linotype", 1, 12)); // NOI18N
        jLabel11.setText("Dijagnoza");

        jButtonAddProcedure.setFont(new java.awt.Font("Palatino Linotype", 1, 12)); // NOI18N
        jButtonAddProcedure.setText("DODAJ PROCEDURU");
        jButtonAddProcedure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddProcedureActionPerformed(evt);
            }
        });

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

        tblSearchDiagnosis.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblSearchDiagnosis);

        jButtonDelete.setFont(new java.awt.Font("Palatino Linotype", 3, 14)); // NOI18N
        jButtonDelete.setText("OBRIŠI DIJAGNOZU");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

        tblAddDiagnosis.setModel(new javax.swing.table.DefaultTableModel(
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
        tblAddDiagnosis.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblAddDiagnosisPropertyChange(evt);
            }
        });
        jScrollPane4.setViewportView(tblAddDiagnosis);

        jButtonChoose.setFont(new java.awt.Font("Palatino Linotype", 3, 14)); // NOI18N
        jButtonChoose.setText("PRETRAŽI");
        jButtonChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseActionPerformed(evt);
            }
        });

        jButtonSaveReport.setFont(new java.awt.Font("Palatino Linotype", 3, 14)); // NOI18N
        jButtonSaveReport.setText("SAČUVAJ IZVESTAJ");
        jButtonSaveReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveReportActionPerformed(evt);
            }
        });

        txtAnamnisis.setColumns(20);
        txtAnamnisis.setRows(5);
        jScrollPane5.setViewportView(txtAnamnisis);

        jLabel5.setFont(new java.awt.Font("Palatino Linotype", 1, 12)); // NOI18N
        jLabel5.setText("Jmbg");

        txtPatientNameSurname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPatientNameSurnameActionPerformed(evt);
            }
        });

        jButtonChooseD.setFont(new java.awt.Font("Palatino Linotype", 3, 14)); // NOI18N
        jButtonChooseD.setText("IZABERI DIJAGNOZU");
        jButtonChooseD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseDActionPerformed(evt);
            }
        });

        jButtonDeleteProcedure.setFont(new java.awt.Font("Palatino Linotype", 1, 12)); // NOI18N
        jButtonDeleteProcedure.setText("OBRIŠI PROCEDURU");
        jButtonDeleteProcedure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteProcedureActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Palatino Linotype", 1, 12)); // NOI18N
        jLabel12.setText("Anamneza");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(dateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(timeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(944, 944, 944)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtPatientNameSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtJmbg, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(340, 340, 340)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(62, 62, 62)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonChoose, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(49, 49, 49)
                                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButtonChooseD)
                                    .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButtonAddProcedure, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButtonDeleteProcedure, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTherapy, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonSaveReport, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(221, 221, 221))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPatientNameSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtJmbg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txtDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(dateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(timeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButtonChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(57, 57, 57)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(261, 261, 261)
                                .addComponent(jButtonChooseD, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(276, 276, 276)
                                .addComponent(jButtonAddProcedure, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonDeleteProcedure, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(97, 97, 97)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTherapy, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))))
                        .addGap(52, 52, 52)
                        .addComponent(jButtonSaveReport, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(77, 77, 77))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchKeyTyped

    private void jButtonChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChooseActionPerformed
        // TODO zadd your handling code here:

        String query = txtSearch.getText().trim();

    if (query.isEmpty()) {
        modelSearchDiagnosis.setDiagnosisList(new ArrayList<>());
    }
    else{
 try{
                ClientRequest kz = new ClientRequest();
                Diagnosis d = new Diagnosis();
                d.setCriteria(query);
                kz.setOperacija(Operation.VRATI_DIJAGNOZE);
                kz.setObjekatOperacije(d);

                // Slanje zahteva
                Communication.getInstance().posaljiZahtev(kz);
                // Primanje odgovora
                ServerResponse so = Communication.getInstance().primiOdgovor();
                if(so!=null){
                searchResults=(List<Diagnosis>) so.getOdgovor();
                }
                if (searchResults == null || searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nije pronađena ni jedna dijagnoza za uneti kriterijum!", "", JOptionPane.INFORMATION_MESSAGE);
                } else {
                JOptionPane.showMessageDialog(this, "Pronađeno " + searchResults.size() + " dijagnoza.", "", JOptionPane.INFORMATION_MESSAGE);
                }

                modelSearchDiagnosis.setDiagnosisList(searchResults);
    }    catch (Exception ex) {
    JOptionPane.showMessageDialog(this, "Veza sa serverom je prekinuta!", "Greška", JOptionPane.ERROR_MESSAGE);
    System.exit(0); 
}}

    }//GEN-LAST:event_jButtonChooseActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblAddDiagnosis.getSelectedRow();
        if(selectedRow==-1){
    JOptionPane.showMessageDialog(this, "Molimo vas da selektujete dijagnozu iz tabele.", "Upozorenje", JOptionPane.WARNING_MESSAGE);        
        return;}
        if (selectedRow >= 0) {
            int i=JOptionPane.showConfirmDialog(this, "Da li ste sigurni da želite obrisati selektovanu dijagnozu?", "BRISANJE", JOptionPane.YES_NO_OPTION);
           if(i==JOptionPane.YES_OPTION){
            addedDiagnoses.remove(selectedRow);
            modelAddDiagnosis.setDiagnosisReportList(addedDiagnoses);}
           
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonAddProcedureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddProcedureActionPerformed
        ReportItemForm f = new ReportItemForm(doctor.getDepartment(),this, true);
        System.out.println("otvori");
    // Setovanje minimalnog datuma i vremena
  
    try{
    if (!items.isEmpty()) {
        
        ReportItem lastItem = items.get(items.size() - 1);
        System.out.println("a");
        f.setMinDateTime(lastItem.getDate(), lastItem.getTime());
 } else {
        
        f.setMinDateTime(this.getDate(), this.getTime());
        System.out.println("b");

    }
    }
     catch (Exception ex) {
    ex.printStackTrace();
    JOptionPane.showMessageDialog(this, "Greška pri setovanju minimalnog datuma i vremena.", "Greška", JOptionPane.ERROR_MESSAGE);
}   
f.setVisible(true);
    // Ako je korisnik potvrdio unos (Save kliknut)
        if(f.isSelected){
       ReportItem item = f.getReportItem();
        items.add(item); // Dodaj u model
        modelReportTable.setReportList(items); // Osveži tabelu
        JOptionPane.showMessageDialog(this, "Procedura uspešno dodata u izveštaj!", "", JOptionPane.INFORMATION_MESSAGE);
    
        }
        


    }//GEN-LAST:event_jButtonAddProcedureActionPerformed

    private void txtDoctorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDoctorActionPerformed
        // TODOe add your handling code here:
    }//GEN-LAST:event_txtDoctorActionPerformed

    private void jButtonSaveReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveReportActionPerformed
        // TODO add your handling code here:
        try{ 
         patientsAdmitionDate=getDate();
         patientsAdmitionTime=getTime();
         anamnesis=txtAnamnisis.getText();
         therapy=txtTherapy.getText();
         //anamnesa mora biti uneta, procedure i dijagnoza
         if(anamnesis==null || anamnesis.isBlank() || items==null ||addedDiagnoses==null){
         JOptionPane.showMessageDialog(this, "Unesite sve parametre.","GREŠKA",JOptionPane.ERROR_MESSAGE);
         return;
         }
         // Azuriranje kategorija za dodane
         //dijagnoze na osnovu vrednosti iz tabele jer je default na pocetku
        for (int i = 0; i < tblAddDiagnosis.getRowCount(); i++) {
        String selectedCategory = tblAddDiagnosis.getValueAt(i, 2).toString(); // Kolona za kategoriju (2. kolona)
        addedDiagnoses.get(i).setCategory(selectedCategory); // Ažuriranje kategorije u modelu
         }
           int i=JOptionPane.showConfirmDialog(this, "Da li ste sigurni da želite sačuvati izveštaj?", "ČUVANJE", JOptionPane.YES_NO_OPTION);
           if(i==JOptionPane.YES_OPTION){
         
        //PAMTIMO IZVESTAJ A U DIAGNOSISREPORT DIJAGNOZE ZA IZVESTAJ
         Report created=new Report(patientsAdmitionDate,patientsAdmitionTime,anamnesis,therapy,items,addedDiagnoses,doctor,patient);
        //added diagnosis
         ClientRequest kz=new ClientRequest();
         kz.setObjekatOperacije(created);
         kz.setOperacija(Operation.KREIRAJ_IZVESTAJ_GLAVNI);
         Communication.getInstance().posaljiZahtev(kz);
         ServerResponse so=Communication.getInstance().primiOdgovor();//INSERT OPERACIJA
         System.out.println("IZVRSIO INSERT GLAVNOG DELA IZVESTAJ-A");
         if(so.getUspesnost()){
        JOptionPane.showMessageDialog(this, "Izveštaj je uspešno sačuvan.", "", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
         } else {
             JOptionPane.showMessageDialog(this, "Greška pri čuvanju izveštaja.", "Greška", JOptionPane.ERROR_MESSAGE);
            }
        }
        }
           catch(Exception ex){
        ex.printStackTrace();
    JOptionPane.showMessageDialog(this, "Greška pri čuvanju izveštaja.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButtonSaveReportActionPerformed

    private void txtPatientNameSurnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPatientNameSurnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPatientNameSurnameActionPerformed

    private void jButtonChooseDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChooseDActionPerformed
        // TODO add your handling code here:
        int selectedR = tblSearchDiagnosis.getSelectedRow();

    if (selectedR == -1) {
        JOptionPane.showMessageDialog(this, "Molimo vas da selektujete dijagnozu iz tabele.", "Upozorenje", JOptionPane.WARNING_MESSAGE);
    }

   else {            
        Diagnosis d=modelSearchDiagnosis.getDiagnosis(selectedR);
        String number=d.getNumber();
        String latineName=d.getLatineName();
        DiagnosisReport newReport = new DiagnosisReport(); // Default category
        newReport.setDiagnosis(d);
        //DEFAULTNO JE DA NIJE NI JEDNA STAVLJENA VEC BIRA
        newReport.setCategory("PRIMARNA DIJAGNOZA");

        addedDiagnoses.add(newReport);
        modelAddDiagnosis.setDiagnosisReportList(addedDiagnoses);
    }
            
    }//GEN-LAST:event_jButtonChooseDActionPerformed

    private void tblAddDiagnosisPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblAddDiagnosisPropertyChange
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tblAddDiagnosisPropertyChange

    private void jButtonDeleteProcedureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteProcedureActionPerformed
        // TODO add your handling code here:
         int selectedRow = tblProtocol.getSelectedRow();
        if(selectedRow==-1){
    JOptionPane.showMessageDialog(this, "Molimo vas da selektujete proceduru iz tabele.", "Upozorenje", JOptionPane.WARNING_MESSAGE);        
        return;}
        
        if (selectedRow >= 0) {
            int i=JOptionPane.showConfirmDialog(this, "Da li ste sigurni da želite obrisati selektovanu proceduru?", "BRISANJE", JOptionPane.YES_NO_OPTION);
           if(i==JOptionPane.YES_OPTION){
         items.remove(selectedRow); // Dodaj u model
        modelReportTable.setReportList(items); // Osveži tabelu
           }
        }
    }//GEN-LAST:event_jButtonDeleteProcedureActionPerformed

    
    
    
    
    
    
    
    
    
    
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner dateSpinner;
    private javax.swing.JButton jButtonAddProcedure;
    private javax.swing.JButton jButtonChoose;
    private javax.swing.JButton jButtonChooseD;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonDeleteProcedure;
    private javax.swing.JButton jButtonSaveReport;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblAddDiagnosis;
    private javax.swing.JTable tblProtocol;
    private javax.swing.JTable tblSearchDiagnosis;
    private javax.swing.JSpinner timeSpinner;
    private javax.swing.JTextArea txtAnamnisis;
    private javax.swing.JTextField txtDoctor;
    private javax.swing.JTextField txtJmbg;
    private javax.swing.JTextField txtPatientNameSurname;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTherapy;
    // End of variables declaration//GEN-END:variables


 
}
