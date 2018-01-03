/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.ReclamoInternoDataSource;
import ar.com.bosoft.buscadores.BuscaPresupuesto;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.conexion.ActiveDatabase;
import static ar.com.bosoft.formularios.Principal.conexion;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import matera.db.managers.PresupuestoMgr;
import matera.db.managers.ReclamoMgr;
import matera.gui.combobox.ComboBoxMgr;
import static matera.jooq.Tables.ENTIDAD;
import static matera.jooq.Tables.PRESTACION;
import static matera.jooq.Tables.PRESUPUESTO;
import static matera.jooq.tables.Profesional.PROFESIONAL;
import matera.jooq.tables.records.EntidadRecord;
import matera.jooq.tables.records.PrestacionRecord;
import matera.jooq.tables.records.PresupuestoRecord;
import matera.jooq.tables.records.ProfesionalRecord;
import matera.jooq.tables.records.ReclamoRecord;
import matera.jooq.tables.records.ReclamoexternoRecord;
import matera.jooq.tables.records.ReclamointernoRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author tinchogava
 */
public class ReclamoInterno extends javax.swing.JInternalFrame {
    Result<Record> records = null;
    Date fechaReclamo;
    java.sql.Date sqlDate;
    String fechaCirugia,lugarCirugia, paciente, prestacion, profesional, descripcion, procedimiento, acciones,
            impresora, nombreArchivo;
    byte[] file1, file2, file3, file4;
    PresupuestoRecord presupuestoRecord;
    EntidadRecord entidadRecord;
    ReclamointernoRecord reclamoInternoRecord;
    ReclamoexternoRecord reclamoexternoRecord;
    PrestacionRecord prestacionRecord;
    ProfesionalRecord profesionalRecord;
    int id_presupuesto, id_reclamoInterno, id_reclamo, copias;
    String sectorReclamo;
    Calendar hoy = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    BuscaPresupuesto buscaPresupuesto;
    ReclamoRecord reclamo;
    private FileInputStream fis;
    private Integer bytes;

    /**
     * Creates new form CreaReclamo
     * @param reclamo
     * @param entidad
     * @param presupuesto
     * @param profesional
     * @param prestacion
     */
    
    public ReclamoInterno(ReclamoRecord reclamo, EntidadRecord entidad, PresupuestoRecord presupuesto, 
            ProfesionalRecord profesional, PrestacionRecord prestacion){
        
        initComponents();
        this.jComboBoxArchivos.setEnabled(false);
        this.jButtonImagen.setEnabled(false);
        this.jButtonImagen.setEnabled(false);
        this.jComboBoxArchivos.setEnabled(false);
        this.reclamo = reclamo;
        this.jTextFechaCirugia.setEnabled(true);
        this.jTextLugarCirugia.setEnabled(true);
        this.jTextPaciente.setEnabled(true);
        this.jTextProfesional.setEnabled(true);
        this.jTextReclamoInterno.setEnabled(false);
        this.JTextNumeroCirugia.setEnabled(true);
        ComboBoxMgr.fillReclamoInternoCombo(jComboReclamoInterno, false);
        fillComboBoxFiles();
        this.JTextNumeroCirugia.requestFocusInWindow();
        this.JTextNumeroCirugia.requestFocusInWindow();
        jButtonBuscar.setEnabled(true);
        JButtonGuardar.setEnabled(true);
        jButtonClean.setEnabled(true);
        jButtonImprimir.setEnabled(true);
        if(reclamo.getFile1()!= null) {
            this.jButtonImagen.setText("Modificar Imagen");
            //this.jLabelImagen.setText(reclamo.getFile1());
        } else
            this.jButtonImagen.setText("Cargar Imagen");
        
        if(entidad.getNombre() != null){
            this.jTextLugarCirugia.setText(entidad.getNombre());
        } else {
            this.jTextLugarCirugia.setText("No detallado");
        }
        if(profesional.getNombre() != null){
            this.jTextProfesional.setText(profesional.getNombre());
        } else {
            this.jTextProfesional.setText("No detallado");
        }
        if(prestacion.getNombre() != null){
            this.jTextPrestacion.setText(prestacion.getNombre());
        } else {
            this.jTextPrestacion.setText("No detallada");
        }
        if(presupuesto.getIdPresupuesto() != null){
            if(presupuesto.getFechacirugia() != null){
                this.jTextFechaCirugia.setText(format.format(presupuesto.getFechacirugia()));
            } else {
                this.jTextFechaCirugia.setText("No detallada");
            }
             if((presupuesto.getPaciente().compareTo("") == 0) || presupuesto.getPaciente() == null){
                this.jTextPaciente.setText("No detallado");
            } else {
                this.jTextPaciente.setText(presupuesto.getPaciente());
            }
            this.JTextNumeroCirugia.setText(String.valueOf(presupuesto.getIdPresupuesto()));
        } else {
             this.jTextFechaCirugia.setText("No detallada");
             this.jTextPaciente.setText("No detallado");
             this.JTextNumeroCirugia.setEnabled(false);
             
        }
        
        System.out.println(reclamo.getIdReclamointerno());
        ComboBoxMgr.setSelectedItemById(jComboReclamoInterno, reclamo.getIdReclamointerno());
        this.jTextAreaAcciones.setText(reclamo.getAcciones());
        this.jTextAreaDescripcion.setText(reclamo.getDescripcion());
        this.jTextAreaProcedimiento.setText(reclamo.getProcedimiento());
        this.jFecha.setDate(reclamo.getFechareclamo());
        this.jTextSectorReclamo.setText(reclamo.getDestinoreclamo().toUpperCase());
    }
    public ReclamoInterno() {
        initComponents();
        this.jComboBoxArchivos.setEnabled(false);
        this.jButtonImagen.setEnabled(false);
        this.jComboBoxArchivos.setEnabled(false);
        this.jButtonImagen.setEnabled(false);
        this.jTextFechaCirugia.setEnabled(false);
        this.jTextLugarCirugia.setEnabled(false);
        this.jTextPaciente.setEnabled(false);
        this.jTextProfesional.setEnabled(false);
        this.jTextReclamoInterno.setEnabled(false);
        this.JTextNumeroCirugia.setEnabled(false);
        ComboBoxMgr.fillReclamoInternoCombo(jComboReclamoInterno, false);
        fillComboBoxFiles();
        this.JTextNumeroCirugia.requestFocusInWindow();
        this.JTextNumeroCirugia.requestFocusInWindow();
        jButtonBuscar.setEnabled(false);
        JButtonGuardar.setEnabled(true);
        jButtonClean.setEnabled(true);
        jButtonImprimir.setEnabled(true);
        //this.jLabelImagen.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFecha = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jFecha.getJCalendar().setTodayButtonVisible(true); 
        this.jFecha.getJCalendar().setTodayButtonText("Hoy"); 
        this.jFecha.getJCalendar().setWeekOfYearVisible(false);
        this.jFecha.getCalendarButton().setVisible(false);
        jLabel1 = new javax.swing.JLabel();
        JTextNumeroCirugia = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jButtonBuscar = new javax.swing.JButton();
        jTextLugarCirugia = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFechaCirugia = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextProfesional = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextPaciente = new javax.swing.JTextField();
        jComboReclamoInterno = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jCheckBoxReclamo = new javax.swing.JCheckBox();
        jTextReclamoInterno = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextPrestacion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextSectorReclamo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaDescripcion = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaProcedimiento = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaAcciones = new javax.swing.JTextArea();
        JButtonGuardar = new javax.swing.JButton();
        JButtonCancelar = new javax.swing.JButton();
        jButtonClean = new javax.swing.JButton();
        jButtonImprimir = new javax.swing.JButton();
        jCheckBoxNumeroCirugia = new javax.swing.JCheckBox();
        jButtonImagen = new javax.swing.JButton();
        jComboBoxArchivos = new javax.swing.JComboBox<>();

        setTitle("Generación de Reclamos Internos");
        setSize(new java.awt.Dimension(0, 0));

        jFecha.setRequestFocusEnabled(false);

        jLabel1.setText("* Número de Cirugía");

        JTextNumeroCirugia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        JTextNumeroCirugia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextNumeroCirugiaActionPerformed(evt);
            }
        });
        JTextNumeroCirugia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JTextNumeroCirugiaKeyTyped(evt);
            }
        });

        jLabel2.setText("* Fecha de Reclamo");

        jButtonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/24x24/enabled/buscar.png"))); // NOI18N
        jButtonBuscar.setBorder(null);
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        jTextLugarCirugia.setEditable(false);
        jTextLugarCirugia.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jTextLugarCirugia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextLugarCirugiaActionPerformed(evt);
            }
        });

        jLabel3.setText("Fecha de Cirugía");

        jLabel4.setText("Lugar");

        jTextFechaCirugia.setEditable(false);
        jTextFechaCirugia.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jTextFechaCirugia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFechaCirugiaActionPerformed(evt);
            }
        });

        jLabel5.setText("Profesional");

        jTextProfesional.setEditable(false);
        jTextProfesional.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jTextProfesional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextProfesionalActionPerformed(evt);
            }
        });

        jLabel6.setText("Paciente");

        jTextPaciente.setEditable(false);
        jTextPaciente.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jTextPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextPacienteActionPerformed(evt);
            }
        });

        jComboReclamoInterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboReclamoInternoActionPerformed(evt);
            }
        });

        jLabel7.setText("* Reclamo Interno");

        jCheckBoxReclamo.setText("Otro");
        jCheckBoxReclamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxReclamoActionPerformed(evt);
            }
        });

        jLabel8.setText("Obra Social");

        jTextPrestacion.setEditable(false);
        jTextPrestacion.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jTextPrestacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextPrestacionActionPerformed(evt);
            }
        });

        jLabel9.setText("* Sector al que se dirige el reclamo");

        jLabel10.setText("Procedimiento al que aplica el Reclamo:");

        jLabel11.setText("Descripción del Reclamo:");

        jTextAreaDescripcion.setColumns(20);
        jTextAreaDescripcion.setRows(5);
        jScrollPane2.setViewportView(jTextAreaDescripcion);

        jTextAreaProcedimiento.setColumns(20);
        jTextAreaProcedimiento.setRows(5);
        jScrollPane3.setViewportView(jTextAreaProcedimiento);

        jLabel12.setText("Acciones Preventivas/Correctivas:");

        jTextAreaAcciones.setColumns(20);
        jTextAreaAcciones.setRows(5);
        jScrollPane1.setViewportView(jTextAreaAcciones);

        JButtonGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/guardar.png"))); // NOI18N
        JButtonGuardar.setText("Guardar");
        JButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButtonGuardarActionPerformed(evt);
            }
        });

        JButtonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/cancelar.png"))); // NOI18N
        JButtonCancelar.setText("Cancelar");
        JButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButtonCancelarActionPerformed(evt);
            }
        });

        jButtonClean.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/limpiar.png"))); // NOI18N
        jButtonClean.setText("Limpiar Formulario");
        jButtonClean.setActionCommand("Limpiar Fonarmulario");
        jButtonClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCleanActionPerformed(evt);
            }
        });

        jButtonImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/pantalla.png"))); // NOI18N
        jButtonImprimir.setText("Ver Reclamo");
        jButtonImprimir.setActionCommand("jButtonReclamo");
        jButtonImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImprimirActionPerformed(evt);
            }
        });

        jCheckBoxNumeroCirugia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxNumeroCirugiaActionPerformed(evt);
            }
        });

        jButtonImagen.setText("Cargar");
        jButtonImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImagenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3)
                                    .addComponent(jScrollPane1)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel10))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(181, 181, 181))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextSectorReclamo, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                    .addComponent(jLabel7)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(jComboReclamoInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jCheckBoxReclamo))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                    .addComponent(jLabel8)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(jTextPrestacion, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jTextReclamoInterno))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jTextProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jTextPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel11))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(213, 213, 213)
                                .addComponent(JButtonGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JButtonCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonClean)
                                .addGap(64, 64, 64)
                                .addComponent(jButtonImprimir)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBoxNumeroCirugia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTextNumeroCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonImagen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxArchivos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFechaCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jTextLugarCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(JTextNumeroCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBoxNumeroCirugia)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jButtonBuscar))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFechaCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jTextLugarCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxArchivos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonImagen))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextPrestacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboReclamoInterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxReclamo)
                    .addComponent(jTextReclamoInterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextSectorReclamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 92, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JButtonGuardar)
                            .addComponent(JButtonCancelar)
                            .addComponent(jButtonClean)
                            .addComponent(jButtonImprimir))))
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JTextNumeroCirugiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextNumeroCirugiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTextNumeroCirugiaActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        if(JTextNumeroCirugia.getText().isEmpty())
        {
            this.buscaPresupuesto = new BuscaPresupuesto(conexion, null, true, Principal.empresaData.getId_empresa(),Principal.empresaData.getFantasia(), 6);
            this.buscaPresupuesto.setVisible(true);
            this.JTextNumeroCirugia.setText(buscaPresupuesto.getId_presupuesto().isEmpty() ? "" : buscaPresupuesto.getId_presupuesto());
            consulta();
            
            this.jTextFechaCirugia.setEnabled(true);
            this.jTextLugarCirugia.setEnabled(true);
            this.jTextPaciente.setEnabled(true);
            this.jTextProfesional.setEnabled(true);
            this.jCheckBoxReclamo.setEnabled(true);
            this.jTextAreaProcedimiento.setEnabled(true);
            this.jTextAreaAcciones.setEnabled(true);
            this.jTextAreaDescripcion.setEnabled(true);
            this.jFecha.setEnabled(true);
            this.jComboReclamoInterno.setEnabled(true);
            this.jTextSectorReclamo.setEnabled(true);
            this.jTextPrestacion.setEnabled(true);
            this.JButtonGuardar.setEnabled(true);
            this.jButtonClean.setEnabled(true);
            this.jButtonImprimir.setEnabled(true);
        } else {
            consulta();
            this.jTextFechaCirugia.setEnabled(true);
            this.jTextLugarCirugia.setEnabled(true);
            this.jTextPaciente.setEnabled(true);
            this.jTextProfesional.setEnabled(true);
            this.jCheckBoxReclamo.setEnabled(true);
            this.jTextAreaProcedimiento.setEnabled(true);
            this.jTextAreaAcciones.setEnabled(true);
            this.jTextAreaDescripcion.setEnabled(true);
            this.jFecha.setEnabled(true);
            this.jComboReclamoInterno.setEnabled(true);
            this.jTextSectorReclamo.setEnabled(true);
            this.jTextPrestacion.setEnabled(true);
            this.JButtonGuardar.setEnabled(true);
            this.jButtonClean.setEnabled(true);
            this.jButtonImprimir.setEnabled(true);
        } 
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jTextLugarCirugiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextLugarCirugiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextLugarCirugiaActionPerformed

    private void jTextFechaCirugiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFechaCirugiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFechaCirugiaActionPerformed

    private void jTextProfesionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextProfesionalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextProfesionalActionPerformed

    private void jTextPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPacienteActionPerformed

    private void jCheckBoxReclamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxReclamoActionPerformed
        if(jCheckBoxReclamo.isSelected()){
            jComboReclamoInterno.setEnabled(false);
            jTextReclamoInterno.setEnabled(true);
        } else {
            jComboReclamoInterno.setEnabled(true);
            jTextReclamoInterno.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBoxReclamoActionPerformed

    private void jTextPrestacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPrestacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPrestacionActionPerformed

    private void JButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButtonGuardarActionPerformed
        generaReclamoInterno();
    }//GEN-LAST:event_JButtonGuardarActionPerformed

    private void jComboReclamoInternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboReclamoInternoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboReclamoInternoActionPerformed

    private void JButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButtonCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_JButtonCancelarActionPerformed

    private void jButtonCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCleanActionPerformed
        cleanAll();
    }//GEN-LAST:event_jButtonCleanActionPerformed

    private void JTextNumeroCirugiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTextNumeroCirugiaKeyTyped
        char c = evt.getKeyChar();
        if(c < '0' || c > '9') evt.consume();
    }//GEN-LAST:event_JTextNumeroCirugiaKeyTyped

    private void jButtonImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImprimirActionPerformed
        print(0);
    }//GEN-LAST:event_jButtonImprimirActionPerformed

    private void jCheckBoxNumeroCirugiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxNumeroCirugiaActionPerformed
       if(jCheckBoxNumeroCirugia.isSelected()){
           JTextNumeroCirugia.setEnabled(true);
           jButtonBuscar.setEnabled(true);
           JButtonGuardar.setEnabled(false);
           jButtonClean.setEnabled(false);
           jButtonImprimir.setEnabled(false);
           jFecha.setEnabled(false);
           jTextFechaCirugia.setEnabled(false);
           jTextLugarCirugia.setEnabled(false);
           jTextAreaAcciones.setEnabled(false);
           jTextAreaDescripcion.setEnabled(false);
           jTextAreaProcedimiento.setEnabled(false);
           jTextPaciente.setEnabled(false);
           jTextPrestacion.setEnabled(false);
           jTextProfesional.setEnabled(false);
           jTextReclamoInterno.setEnabled(false);
           jComboReclamoInterno.setEnabled(false);
           jTextSectorReclamo.setEnabled(false);
           jCheckBoxReclamo.setEnabled(false);
       } else {
           JTextNumeroCirugia.setEnabled(false);
           jFecha.setEnabled(true);
           jButtonBuscar.setEnabled(false);
           jTextFechaCirugia.setEnabled(true);
           jTextLugarCirugia.setEnabled(true);
           jTextAreaAcciones.setEnabled(true);
           jTextAreaDescripcion.setEnabled(true);
           jTextAreaProcedimiento.setEnabled(true);
           jTextPaciente.setEnabled(true);
           jTextPrestacion.setEnabled(true);
           jTextProfesional.setEnabled(true);
           jComboReclamoInterno.setEnabled(true);
           jTextSectorReclamo.setEnabled(true);
       }
    }//GEN-LAST:event_jCheckBoxNumeroCirugiaActionPerformed

    private void jButtonImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImagenActionPerformed
        
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        if(jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            try {
                fis = new FileInputStream(jFileChooser.getSelectedFile());
                bytes = (int) jFileChooser.getSelectedFile().length();
                Files.readAllBytes(jFileChooser.getSelectedFile().toPath());
                
                switch(jComboBoxArchivos.getSelectedItem().toString()){
                    case "Archivo 1":
                        file1 = Files.readAllBytes(jFileChooser.getSelectedFile().toPath());
                        break;
                    case "Archivo 2":
                        file2 = Files.readAllBytes(jFileChooser.getSelectedFile().toPath());
                        break;
                    case "Archivo 3":
                        file3 = Files.readAllBytes(jFileChooser.getSelectedFile().toPath());
                        break;
                    case "Archivo 4":
                        file4 = Files.readAllBytes(jFileChooser.getSelectedFile().toPath());
                        break;

                }
                
            } catch (FileNotFoundException e){
            } catch (IOException ex) {
                Logger.getLogger(ReclamoInterno.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        

    }//GEN-LAST:event_jButtonImagenActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JButtonCancelar;
    private javax.swing.JButton JButtonGuardar;
    private javax.swing.JFormattedTextField JTextNumeroCirugia;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonClean;
    private javax.swing.JButton jButtonImagen;
    private javax.swing.JButton jButtonImprimir;
    private javax.swing.JCheckBox jCheckBoxNumeroCirugia;
    private javax.swing.JCheckBox jCheckBoxReclamo;
    private javax.swing.JComboBox<String> jComboBoxArchivos;
    private javax.swing.JComboBox<String> jComboReclamoInterno;
    private com.toedter.calendar.JDateChooser jFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextAreaAcciones;
    private javax.swing.JTextArea jTextAreaDescripcion;
    private javax.swing.JTextArea jTextAreaProcedimiento;
    private javax.swing.JTextField jTextFechaCirugia;
    private javax.swing.JTextField jTextLugarCirugia;
    private javax.swing.JTextField jTextPaciente;
    private javax.swing.JTextField jTextPrestacion;
    private javax.swing.JTextField jTextProfesional;
    private javax.swing.JTextField jTextReclamoInterno;
    private javax.swing.JTextField jTextSectorReclamo;
    // End of variables declaration//GEN-END:variables

    private void consulta() {
        id_presupuesto = Integer.valueOf(JTextNumeroCirugia.getText());
        
        records = PresupuestoMgr.getPresupuestoById(id_presupuesto);
        if (records.size() > 0){
            records.stream().map((r) -> {
                presupuestoRecord = r.into(PRESUPUESTO);
                return r;
            }).map((r) -> {
                entidadRecord = r.into(ENTIDAD.as("lugarCirugia"));
                return r;
            }).map((r) -> {
                prestacionRecord = r.into(PRESTACION);
                return r;
            }).forEach((r) -> {
                profesionalRecord = r.into(PROFESIONAL);
            });
        }
        if(presupuestoRecord.getFechacirugia() != null){
            this.jTextFechaCirugia.setText(format.format(presupuestoRecord.getFechacirugia()));
        } else {
            this.jTextFechaCirugia.setText("No detallada");
        }
        if(entidadRecord.getNombre() != null){
            this.jTextLugarCirugia.setText(entidadRecord.getNombre());
        } else {
            this.jTextLugarCirugia.setText("No detallado");
        }
        if(profesionalRecord.getNombre() != null){
            this.jTextProfesional.setText(profesionalRecord.getNombre());
        } else {
            this.jTextProfesional.setText("No detallado");
        }
        if(prestacionRecord.getNombre() != null){
            this.jTextPrestacion.setText(prestacionRecord.getNombre());
        } else {
            this.jTextPrestacion.setText("No detallada");
        }
         if((presupuestoRecord.getPaciente().compareTo("") == 0) || presupuestoRecord.getPaciente() == null){
            this.jTextPaciente.setText("No detallado");
        } else {
            this.jTextPaciente.setText(presupuestoRecord.getPaciente());
        }
    }

    private void generaReclamoInterno() {
        if(jCheckBoxReclamo.isSelected()){
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea generar un nuevo Tipo de Reclamo Interno?", "Reclamos Internos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                ReclamointernoRecord reclamointernoRecord = new ReclamointernoRecord();
                reclamointernoRecord.setNombre(jTextReclamoInterno.getText().toUpperCase());
                reclamointernoRecord.setHabilita("S");
                reclamointernoRecord.attach(ActiveDatabase.getDSLContext().configuration());
                reclamointernoRecord.store();
                this.id_reclamoInterno = reclamointernoRecord.getIdReclamointerno();
            } else {
                JOptionPane.showMessageDialog(this, "Ingrese un Tipo de Reclamo válido", "Reclamos Internos", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            this.id_reclamoInterno = ComboBoxMgr.getSelectedId(jComboReclamoInterno);
        }
        this.fechaCirugia = jTextFechaCirugia.getText();
        this.lugarCirugia = jTextLugarCirugia.getText();
        this.profesional = jTextProfesional.getText();
        this.prestacion = jTextPrestacion.getText();
        this.paciente = jTextPaciente.getText();
        this.descripcion = jTextAreaDescripcion.getText();
        this.procedimiento = jTextAreaProcedimiento.getText();
        this.acciones = jTextAreaAcciones.getText();
        if(jFecha.getDate() != null){
            this.fechaReclamo = jFecha.getDate();            
        } else {
            int respuesta = JOptionPane.showConfirmDialog(this, "No ha seleccionado fecha, por lo tanto el Reclamo se generará con fecha de hoy. \n ¿Desea continuar?", "Reclamos Internos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                this.fechaReclamo = new Date();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione la fecha del Reclamo");
                jFecha.requestFocusInWindow();
            }
        }
        sqlDate = new java.sql.Date(this.fechaReclamo.getTime());
        if(!jTextSectorReclamo.getText().isEmpty()){
            this.sectorReclamo = jTextSectorReclamo.getText();
        } else {
            JOptionPane.showMessageDialog(this, "No ha seleccionado el Sector al que va dirigido el Reclamo", "Reclamos Internos", JOptionPane.INFORMATION_MESSAGE);
            jTextSectorReclamo.requestFocusInWindow();
        }
        if(checkData()){
            ReclamoRecord reclamoRecord;
            if(this.reclamo != null){
                 reclamoRecord = this.reclamo;
            } else {
                reclamoRecord = new ReclamoRecord();
            }    
            reclamoRecord.attach(ActiveDatabase.getDSLContext().configuration());
            reclamoRecord.setFechareclamo(sqlDate);
            if(this.id_presupuesto != 0){
                reclamoRecord.setIdPresupuesto(this.id_presupuesto);
            }
            reclamoRecord.setIdReclamo(3);
            reclamoRecord.setDescripcion(this.descripcion);
            reclamoRecord.setProcedimiento(this.procedimiento);
            reclamoRecord.setAcciones(this.acciones);
            reclamoRecord.setIdReclamointerno(this.id_reclamoInterno);
            reclamoRecord.setDestinoreclamo(this.sectorReclamo.toUpperCase());
            reclamoRecord.setIdUsuario(Principal.usuarioData.getId_usuario());
            reclamoRecord.setIdZona(Principal.usuarioData.getId_zonaSistema());
            reclamoRecord.setFile1(file1);
            reclamoRecord.setFile2(file2);
            reclamoRecord.setFile3(file3);
            reclamoRecord.setFile4(file4);

            if((ReclamoMgr.storeReclamo(reclamoRecord)) > 0){
                JOptionPane.showMessageDialog(this, "El Reclamo se efectuó correctamente", "Reclamos Internos", JOptionPane.INFORMATION_MESSAGE);
                id_reclamo = reclamoRecord.getIdReclamo();
            } else {
                 JOptionPane.showMessageDialog(this, "Atención! El Reclamo no se pudo realizar. Intente nuevamente.", "Reclamos Internos", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private boolean checkData() {
        return  this.fechaReclamo != null &&
                this.id_reclamoInterno != 0 &&
                !this.sectorReclamo.isEmpty();
    }
    
    private void cleanAll(){
        this.JTextNumeroCirugia.setText("");  
        this.jTextAreaAcciones.setText("");
        this.jTextAreaDescripcion.setText("");
        this.jTextAreaProcedimiento.setText("");
        this.jTextSectorReclamo.setText("");
        this.jTextFechaCirugia.setText("");
        this.jTextLugarCirugia.setText("");
        this.jTextPaciente.setText("");
        this.jTextPrestacion.setText("");
        this.jTextProfesional.setText("");
        this.jComboReclamoInterno.setSelectedIndex(0);
        this.jFecha.setCalendar(null);
        this.jCheckBoxReclamo.setSelected(false);
        this.jTextFechaCirugia.setEnabled(false);
        this.jTextLugarCirugia.setEnabled(false);
        this.jTextPaciente.setEnabled(false);
        this.jTextProfesional.setEnabled(false);
        this.jTextReclamoInterno.setEnabled(false);
        this.JTextNumeroCirugia.requestFocusInWindow();
        this.jCheckBoxNumeroCirugia.setEnabled(true);
        this.jCheckBoxNumeroCirugia.setSelected(false);
    }
    
    private void print(int salida){
            Map param=new HashMap();
            param.put("id_reclamo", this.id_reclamo);
            if (this.id_presupuesto != 0){
                param.put("id_cirugia", this.id_presupuesto);
            } else {
                param.put("id_cirugia", "No detallado");
            }
            param.put("tipoReclamo", this.jComboReclamoInterno.getSelectedItem().toString());
            if (!this.paciente.isEmpty()){
                param.put("paciente", this.paciente);
            } else {
                param.put("paciente", "No detallado");
            }
            if (!this.profesional.isEmpty()){
                param.put("profesional", this.profesional);
            } else {
                param.put("profesional", "No detallado");
            }
            if (!this.prestacion.isEmpty()){
                param.put("prestacion", this.prestacion);
            } else {
                param.put("prestacion", "No detallado");
            }
            if (!this.lugarCirugia.isEmpty()){
                param.put("lugarCirugia", this.lugarCirugia);
            } else {
                param.put("lugarCirugia", "No detallado");
            }
            param.put("sectorReclamo", this.sectorReclamo.toUpperCase());
            param.put("descripcion", this.descripcion);
            param.put("procedimientos", this.procedimiento);
            param.put("acciones", this.acciones);
            param.put("fechaReclamo", format.format(this.sqlDate));
            if (!this.fechaCirugia.isEmpty()){
                param.put("fechaCirugia", this.fechaCirugia);
            } else {
                param.put("fechaCirugia", "No detallado");
            }

            ReclamoInternoDataSource data = new ReclamoInternoDataSource();
            
            ar.com.bosoft.Modelos.ReclamoInterno nuevo = new ar.com.bosoft.Modelos.ReclamoInterno(this.id_reclamo, this.id_presupuesto, 
                    this.jComboReclamoInterno.getSelectedItem().toString(), this.paciente, this.profesional, this.prestacion,
                    this.lugarCirugia, this.sectorReclamo, this.descripcion, this.procedimiento, this.acciones, format.format(this.sqlDate),
                    this.fechaCirugia);
            
            data.add(nuevo);
            
            Reporte reporte = new Reporte();
            reporte.startReport("ReclamoInterno",param, data, salida, nombreArchivo, impresora, copias);
     
        }
    
    private void fillComboBoxFiles(){
        jComboBoxArchivos.addItem("Archivo 1");
        jComboBoxArchivos.addItem("Archivo 2");
        jComboBoxArchivos.addItem("Archivo 3");
        jComboBoxArchivos.addItem("Archivo 4");
        jComboBoxArchivos.addItem("Archivo 5");
        jComboBoxArchivos.addItem("Archivo 6");
        jComboBoxArchivos.addItem("Archivo 7");
        jComboBoxArchivos.addItem("Archivo 8");
        jComboBoxArchivos.addItem("Archivo 9");
        jComboBoxArchivos.addItem("Archivo 10");    
    }
    
}
