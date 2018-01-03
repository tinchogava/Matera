/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.ReclamoExternoDataSource;
import ar.com.bosoft.buscadores.BuscaPresupuesto;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.conexion.ActiveDatabase;
import static ar.com.bosoft.formularios.Principal.conexion;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import matera.cache.CacheProveedores;
import matera.db.managers.PresupuestoMgr;
import matera.db.managers.ProveedorMgr;
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
import matera.jooq.tables.records.ProveedorRecord;
import matera.jooq.tables.records.ReclamoRecord;
import matera.jooq.tables.records.ReclamoexternoRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author tinchogava
 */
public class ReclamoExterno extends javax.swing.JInternalFrame {
    Result<Record> records = null;
    Date dateReclamo, dateNotificacion, dateDevolucion;
    String fechaCirugia, fechaNotificacion,fechaDevolucion, lugarCirugia, paciente, prestacion, profesional,
            descripcion, acciones, direccion, recibe, impresora, nombreArchivo, destinoReclamo, telefono;
    PresupuestoRecord presupuestoRecord;
    EntidadRecord entidadRecord;
    PrestacionRecord prestacionRecord;
    ProfesionalRecord profesionalRecord;
    int id_presupuesto, id_reclamoExterno, id_reclamo, copias;
    Calendar hoy = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    java.sql.Date sqlReclamoDate, sqlNotificacionDate, sqlDevolucionDate;
    BuscaPresupuesto buscaPresupuesto;
    ReclamoRecord reclamo;
    File imagen;
    private FileInputStream fis;
    private Integer bytes;
    byte[] file1, file2, file3, file4;

    /**
     * Creates new form CreaReclamo
     * @param reclamo
     * @param entidad
     * @param presupuesto
     * @param profesional
     * @param prestacion
     */
    
    public ReclamoExterno(ReclamoRecord reclamo, EntidadRecord entidad, PresupuestoRecord presupuesto, 
            ProfesionalRecord profesional, PrestacionRecord prestacion) {
        
        initComponents();
        this.jComboBoxArchivos.setEnabled(false);
        this.jButtonImagen.setEnabled(false);
        this.reclamo = reclamo;
        this.JTextNumeroCirugia.setEnabled(true);
        this.jButtonBuscar.setEnabled(true);
        this.jTextFechaCirugia.setEnabled(true);
        this.jTextLugarCirugia.setEnabled(true);
        this.jTextPaciente.setEnabled(true);
        this.jTextProfesional.setEnabled(true);
        this.jComboBoxReclamoExterno.setEnabled(true);
        this.GlazedComboProveedores.setEnabled(true);
        this.jTextDireccion.setEnabled(true);
        this.JTextTelefono.setEnabled(true);
        this.jTextRecibe.setEnabled(true);
        ComboBoxMgr.fillReclamoExternoCombo(jComboBoxReclamoExterno, false);
        ComboBoxMgr.fillProveedorCombo(GlazedComboProveedores, false);
        this.JTextNumeroCirugia.requestFocusInWindow();
        this.jTextAreaAcciones.setEnabled(true);
        this.jTextAreaDescripcion.setEnabled(true);
        this.jFechaDevolucion.setEnabled(true);
        this.jFechaNotificacion.setEnabled(true);
        this.jFecha.setEnabled(true);
        this.JButtonGuardar.setEnabled(true);
        this.jButtonClean.setEnabled(true);
        this.jTextReclamoExterno.setEnabled(false);
        if(reclamo.getFile1() != null) {
            this.jButtonImagen.setText("Modificar Imagen");
            //this.jLabelImagen.setText(reclamo.getFile1());
        } else
            this.jButtonImagen.setText("Cargar Imagen");
        
        ComboBoxMgr.setSelectedItemById(GlazedComboProveedores, 
                CacheProveedores.instance().getProveedor(reclamo.getDestinoreclamo()).getIdProveedor());
        
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
        this.jTextAreaAcciones.setText(reclamo.getAcciones());
        this.jTextAreaDescripcion.setText(reclamo.getDescripcion());
        this.jTextRecibe.setText(reclamo.getRecibe());
        this.jFecha.setDate(reclamo.getFechareclamo());
        this.JTextTelefono.setText(entidad.getTelefono1());
        this.jFechaNotificacion.setDate(reclamo.getFechanotificacion());
        if(reclamo.getFechadevolucion() != null){
            this.jFechaDevolucion.setDate(reclamo.getFechadevolucion());
        }
    }
    
    public ReclamoExterno() {
        initComponents();
        this.jComboBoxArchivos.setEnabled(false);
        this.jButtonImagen.setEnabled(false);
        this.JTextNumeroCirugia.setEnabled(false);
        this.jButtonBuscar.setEnabled(false);
        this.jTextFechaCirugia.setEnabled(true);
        this.jTextLugarCirugia.setEnabled(true);
        this.jTextPaciente.setEnabled(true);
        this.jTextProfesional.setEnabled(true);
        this.jComboBoxReclamoExterno.setEnabled(true);
        this.GlazedComboProveedores.setEnabled(true);
        this.jTextDireccion.setEnabled(false);
        this.JTextTelefono.setEnabled(false);
        this.jTextRecibe.setEnabled(false);
        ComboBoxMgr.fillReclamoExternoCombo(jComboBoxReclamoExterno, false);
        ComboBoxMgr.fillProveedorCombo(GlazedComboProveedores, false);
        this.JTextNumeroCirugia.requestFocusInWindow();
        this.jTextAreaAcciones.setEnabled(true);
        this.jTextAreaDescripcion.setEnabled(true);
        this.jFechaDevolucion.setEnabled(true);
        this.jFechaNotificacion.setEnabled(true);
        this.jFecha.setEnabled(true);
        this.JButtonGuardar.setEnabled(true);
        this.jButtonClean.setEnabled(true);
        this.jTextReclamoExterno.setEnabled(false);
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
        jComboBoxReclamoExterno = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jCheckBoxReclamo = new javax.swing.JCheckBox();
        jTextReclamoExterno = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextPrestacion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaDescripcion = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaAcciones = new javax.swing.JTextArea();
        JButtonGuardar = new javax.swing.JButton();
        JButtonCancelar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jFechaNotificacion = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jFecha.getJCalendar().setTodayButtonVisible(true); 
        this.jFecha.getJCalendar().setTodayButtonText("Hoy"); 
        this.jFecha.getJCalendar().setWeekOfYearVisible(false);
        this.jFecha.getCalendarButton().setVisible(false);
        jLabel16 = new javax.swing.JLabel();
        jFechaDevolucion = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jFecha.getJCalendar().setTodayButtonVisible(true); 
        this.jFecha.getJCalendar().setTodayButtonText("Hoy"); 
        this.jFecha.getJCalendar().setWeekOfYearVisible(false);
        this.jFecha.getCalendarButton().setVisible(false);
        JTextTelefono = new javax.swing.JFormattedTextField();
        jTextDireccion = new javax.swing.JTextField();
        jTextRecibe = new javax.swing.JTextField();
        jButtonClean = new javax.swing.JButton();
        jCheckBoxNumeroCirugia = new javax.swing.JCheckBox();
        jButtonPrint = new javax.swing.JButton();
        GlazedComboProveedores = new matera.gui.combobox.GlazedCombo();
        jButtonImagen = new javax.swing.JButton();
        jComboBoxArchivos = new javax.swing.JComboBox<>();

        setTitle("Generación de Reclamos Externos");

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

        jComboBoxReclamoExterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxReclamoExternoActionPerformed(evt);
            }
        });

        jLabel7.setText("* Reclamo Externo");

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

        jLabel9.setText("* Fabricante/Importador/Distribuidor al que se dirige el Reclamo");

        jLabel10.setText("Fecha de Notificación al Fabricante/Importador/Distribuidor:");

        jLabel11.setText("Descripción del Reclamo:");

        jTextAreaDescripcion.setColumns(20);
        jTextAreaDescripcion.setRows(5);
        jScrollPane2.setViewportView(jTextAreaDescripcion);

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

        jLabel13.setText("Dirección");

        jLabel14.setText("Teléfono");

        jLabel15.setText("Personal que recibe");

        jFechaNotificacion.setRequestFocusEnabled(false);

        jLabel16.setText("Fecha de Devolución:");

        jFechaDevolucion.setRequestFocusEnabled(false);

        JTextTelefono.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        JTextTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextTelefonoActionPerformed(evt);
            }
        });
        JTextTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JTextTelefonoKeyTyped(evt);
            }
        });

        jTextDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDireccionActionPerformed(evt);
            }
        });

        jButtonClean.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/limpiar.png"))); // NOI18N
        jButtonClean.setText("Limpiar Formulario");
        jButtonClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCleanActionPerformed(evt);
            }
        });

        jCheckBoxNumeroCirugia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxNumeroCirugiaActionPerformed(evt);
            }
        });

        jButtonPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/pantalla.png"))); // NOI18N
        jButtonPrint.setText("Ver Reclamo");
        jButtonPrint.setActionCommand("jButtonReclamo");
        jButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintActionPerformed(evt);
            }
        });

        GlazedComboProveedores.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                GlazedComboProveedoresItemStateChanged(evt);
            }
        });
        GlazedComboProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GlazedComboProveedoresActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2)
                                    .addComponent(jScrollPane1))
                                .addGap(82, 82, 82))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(243, 243, 243)
                                        .addComponent(JButtonGuardar)
                                        .addGap(18, 18, 18)
                                        .addComponent(JButtonCancelar)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButtonClean)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButtonPrint))
                                    .addComponent(jFechaNotificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16))
                                .addContainerGap(80, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel12)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(GlazedComboProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jFechaDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jComboBoxReclamoExterno, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jCheckBoxReclamo))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jTextPrestacion, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextReclamoExterno, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel6))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(JTextTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel15)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextRecibe))
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(354, 354, 354)
                                .addComponent(jTextPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jCheckBoxNumeroCirugia)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(JTextNumeroCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFechaCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextLugarCirugia))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonImagen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxArchivos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63))))
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
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jButtonBuscar))))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFechaCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jTextLugarCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBoxArchivos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButtonImagen))
                            .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
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
                    .addComponent(jComboBoxReclamoExterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxReclamo)
                    .addComponent(jTextReclamoExterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(GlazedComboProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(JTextTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jTextRecibe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFechaNotificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jFechaDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(109, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JButtonCancelar)
                            .addComponent(JButtonGuardar)
                            .addComponent(jButtonClean)
                            .addComponent(jButtonPrint))
                        .addGap(38, 38, 38))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JTextNumeroCirugiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextNumeroCirugiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTextNumeroCirugiaActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        if(JTextNumeroCirugia.getText().isEmpty())
        {
            this.buscaPresupuesto = new BuscaPresupuesto(conexion, null, true, Principal.empresaData.getId_empresa(),
                     Principal.empresaData.getFantasia(), 6);
            this.buscaPresupuesto.setVisible(true);
            this.JTextNumeroCirugia.setText(buscaPresupuesto.getId_presupuesto().isEmpty() ? "" : buscaPresupuesto.getId_presupuesto());
            consulta();
            this.jTextFechaCirugia.setEnabled(true);
            this.jTextLugarCirugia.setEnabled(true);
            this.jTextPaciente.setEnabled(true);
            this.jTextProfesional.setEnabled(true);
            this.jComboBoxReclamoExterno.setEnabled(true);
            this.jCheckBoxReclamo.setEnabled(true);
            this.GlazedComboProveedores.setEnabled(true);
            this.jTextDireccion.setEnabled(true);
            this.JTextTelefono.setEnabled(true);
            this.jTextRecibe.setEnabled(true);
            this.jTextAreaAcciones.setEnabled(true);
            this.jTextAreaDescripcion.setEnabled(true);
            this.jFechaDevolucion.setEnabled(true);
            this.jFechaNotificacion.setEnabled(true);
            this.jFecha.setEnabled(true); 
            this.JButtonGuardar.setEnabled(true);
            this.jButtonClean.setEnabled(true);
            this.jButtonPrint.setEnabled(true);
        } else {
            consulta();
            this.jTextFechaCirugia.setEnabled(true);
            this.jTextLugarCirugia.setEnabled(true);
            this.jTextPaciente.setEnabled(true);
            this.jTextProfesional.setEnabled(true);
            this.jComboBoxReclamoExterno.setEnabled(true);
            this.jCheckBoxReclamo.setEnabled(true);
            this.GlazedComboProveedores.setEnabled(true);
            this.jTextDireccion.setEnabled(true);
            this.JTextTelefono.setEnabled(true);
            this.jTextRecibe.setEnabled(true);
            this.jTextAreaAcciones.setEnabled(true);
            this.jTextAreaDescripcion.setEnabled(true);
            this.jFechaDevolucion.setEnabled(true);
            this.jFechaNotificacion.setEnabled(true);
            this.jFecha.setEnabled(true);
            this.JButtonGuardar.setEnabled(true);
            this.jButtonClean.setEnabled(true);
            this.jButtonPrint.setEnabled(true);
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
            jComboBoxReclamoExterno.setEnabled(false);
            jTextReclamoExterno.setEnabled(true);
        } else {
            jComboBoxReclamoExterno.setEnabled(true);
            jTextReclamoExterno.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBoxReclamoActionPerformed

    private void jTextPrestacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPrestacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPrestacionActionPerformed

    private void JButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButtonGuardarActionPerformed
        generaReclamoExterno();
        
    }//GEN-LAST:event_JButtonGuardarActionPerformed

    private void jComboBoxReclamoExternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxReclamoExternoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxReclamoExternoActionPerformed

    private void JTextTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTextTelefonoActionPerformed

    private void jTextDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDireccionActionPerformed

    private void jButtonCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCleanActionPerformed
        cleanAll();
    }//GEN-LAST:event_jButtonCleanActionPerformed

    private void JButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButtonCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_JButtonCancelarActionPerformed

    private void JTextNumeroCirugiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTextNumeroCirugiaKeyTyped
        char c = evt.getKeyChar();
        if(c < '0' || c > '9') evt.consume();
    }//GEN-LAST:event_JTextNumeroCirugiaKeyTyped

    private void jCheckBoxNumeroCirugiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxNumeroCirugiaActionPerformed
        if(jCheckBoxNumeroCirugia.isSelected()){
            this.JTextNumeroCirugia.setEnabled(true);
            this.jButtonBuscar.setEnabled(true);
            this.jTextFechaCirugia.setEnabled(false);
            this.jTextLugarCirugia.setEnabled(false);
            this.jTextPaciente.setEnabled(false);
            this.jTextProfesional.setEnabled(false);
            this.jComboBoxReclamoExterno.setEnabled(false);
            this.jTextReclamoExterno.setEnabled(false);
            this.jCheckBoxReclamo.setEnabled(false);
            this.GlazedComboProveedores.setEnabled(false);
            this.jTextDireccion.setEnabled(false);
            this.JTextTelefono.setEnabled(false);
            this.jTextRecibe.setEnabled(false);
            this.jTextAreaAcciones.setEnabled(false);
            this.jTextAreaDescripcion.setEnabled(false);
            this.jFechaDevolucion.setEnabled(false);
            this.jFechaNotificacion.setEnabled(false);
            this.jFecha.setEnabled(false);
            this.JButtonGuardar.setEnabled(false);
            this.jButtonClean.setEnabled(false);
            this.jButtonPrint.setEnabled(false);
        } else {
            this.JTextNumeroCirugia.setEnabled(false);
            this.jButtonBuscar.setEnabled(false);
            this.jTextFechaCirugia.setEnabled(true);
            this.jTextLugarCirugia.setEnabled(true);
            this.jTextPaciente.setEnabled(true);
            this.jTextProfesional.setEnabled(true);
            this.jCheckBoxReclamo.setEnabled(true);
            this.jComboBoxReclamoExterno.setEnabled(true);
            this.GlazedComboProveedores.setEnabled(true);
            this.jTextDireccion.setEnabled(true);
            this.JTextTelefono.setEnabled(true);
            this.jTextRecibe.setEnabled(true);
            this.jTextAreaAcciones.setEnabled(true);
            this.jTextAreaDescripcion.setEnabled(true);
            this.jFechaDevolucion.setEnabled(true);
            this.jFechaNotificacion.setEnabled(true);
            this.jFecha.setEnabled(true);
        }
    }//GEN-LAST:event_jCheckBoxNumeroCirugiaActionPerformed

    private void jButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintActionPerformed
        if(getData());
            print(0);
    }//GEN-LAST:event_jButtonPrintActionPerformed

    private void JTextTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTextTelefonoKeyTyped
        char c = evt.getKeyChar();
        if(c < '0' || c > '9') evt.consume();
    }//GEN-LAST:event_JTextTelefonoKeyTyped

    private void GlazedComboProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GlazedComboProveedoresActionPerformed
    
    }//GEN-LAST:event_GlazedComboProveedoresActionPerformed

    private void GlazedComboProveedoresItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_GlazedComboProveedoresItemStateChanged
        if ( evt.getStateChange() == ItemEvent.SELECTED){
            fillDatosProveedor();
            this.JTextTelefono.setEnabled(true);
            this.jTextDireccion.setEnabled(true);
            this.jTextRecibe.setEnabled(true);
        }
    }//GEN-LAST:event_GlazedComboProveedoresItemStateChanged
   
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
    private matera.gui.combobox.GlazedCombo GlazedComboProveedores;
    private javax.swing.JButton JButtonCancelar;
    private javax.swing.JButton JButtonGuardar;
    private javax.swing.JFormattedTextField JTextNumeroCirugia;
    private javax.swing.JFormattedTextField JTextTelefono;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonClean;
    private javax.swing.JButton jButtonImagen;
    private javax.swing.JButton jButtonPrint;
    private javax.swing.JCheckBox jCheckBoxNumeroCirugia;
    private javax.swing.JCheckBox jCheckBoxReclamo;
    private javax.swing.JComboBox<String> jComboBoxArchivos;
    private javax.swing.JComboBox<String> jComboBoxReclamoExterno;
    private com.toedter.calendar.JDateChooser jFecha;
    private com.toedter.calendar.JDateChooser jFechaDevolucion;
    private com.toedter.calendar.JDateChooser jFechaNotificacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JTextArea jTextAreaAcciones;
    private javax.swing.JTextArea jTextAreaDescripcion;
    private javax.swing.JTextField jTextDireccion;
    private javax.swing.JTextField jTextFechaCirugia;
    private javax.swing.JTextField jTextLugarCirugia;
    private javax.swing.JTextField jTextPaciente;
    private javax.swing.JTextField jTextPrestacion;
    private javax.swing.JTextField jTextProfesional;
    private javax.swing.JTextField jTextRecibe;
    private javax.swing.JTextField jTextReclamoExterno;
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
            this.jTextFechaCirugia.setText(presupuestoRecord.getFechacirugia().toString());
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

    private void generaReclamoExterno() {
        /*if(jCheckBoxReclamo.isSelected()){
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea generar un nuevo Tipo de Reclamo Externo?",
                    "Reclamos Externos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                ReclamoexternoRecord reclamoexternoRecord = new ReclamoexternoRecord();
                reclamoexternoRecord.setNombre(jTextReclamoExterno.getText().toUpperCase());
                reclamoexternoRecord.setHabilita("S");
                reclamoexternoRecord.attach(ActiveDatabase.getDSLContext().configuration());
                reclamoexternoRecord.store();
                this.id_reclamoExterno = reclamoexternoRecord.getIdReclamoexterno();
            } else {
                JOptionPane.showMessageDialog(this, "Ingrese un Tipo de Reclamo válido", "Reclamos Externos",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            this.id_reclamoExterno = ComboBoxMgr.getSelectedId(jComboBoxReclamoExterno);
        }
        this.fechaCirugia = jTextFechaCirugia.getText();
        this.lugarCirugia = jTextLugarCirugia.getText();
        this.profesional = jTextProfesional.getText();
        this.prestacion = jTextPrestacion.getText();
        this.paciente = jTextPaciente.getText();
        this.descripcion = jTextAreaDescripcion.getText();
        this.direccion = jTextDireccion.getText();
        this.telefono = JTextTelefono.getText();
        this.recibe = jTextRecibe.getText();
        this.acciones = jTextAreaAcciones.getText();
        if(jFecha.getDate() != null){
            this.dateReclamo = jFecha.getDate();            
        } else {
            int respuesta = JOptionPane.showConfirmDialog(this, "No ha seleccionado fecha de Reclamo,"
                    + " el Reclamo se generará con fecha de hoy. \n ¿Desea continuar?", "Reclamos Externos",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                this.dateReclamo = new Date();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione la fecha del Reclamo");
                jFecha.requestFocusInWindow();
            }
        }
        sqlReclamoDate = new java.sql.Date(this.dateReclamo.getTime());
        if(!GlazedComboProveedores.getSelectedItem().toString().isEmpty()){
            this.destinoReclamo = CacheProveedores.instance()
                    .getProveedor(ComboBoxMgr.getSelectedId(GlazedComboProveedores)).getNombre();
        } else {
            JOptionPane.showMessageDialog(this, "No ha seleccionado el Sector al que va dirigido el Reclamo",
                    "Reclamos Externos", JOptionPane.INFORMATION_MESSAGE);
            GlazedComboProveedores.requestFocusInWindow();
        }
        if(jFechaNotificacion.getDate() != null){
            this.dateNotificacion = jFechaNotificacion.getDate();            
        } else {
            int respuesta = JOptionPane.showConfirmDialog(this, "No ha seleccionado fecha de Notificación,"
                    + " se notificará con fecha de hoy. \n ¿Desea continuar?", "Reclamos Externos", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                this.dateNotificacion = new Date();
                this.fechaNotificacion = format.format(dateNotificacion);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione la fecha de Notificación");
                jFechaNotificacion.requestFocusInWindow();
            }
        }
        sqlNotificacionDate = new java.sql.Date(this.dateNotificacion.getTime());
        if(jFechaDevolucion.getDate() != null){
            this.dateDevolucion = jFechaDevolucion.getDate();
            this.fechaDevolucion = format.format(this.dateDevolucion);            
        } else {
            int respuesta = JOptionPane.showConfirmDialog(this, "No ha seleccionado fecha de Devolución. \n ¿Desea continuar?",
                    "Reclamos Externos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                
                this.fechaDevolucion = "No detallada";
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione la fecha de Devolución");
                jFechaDevolucion.requestFocusInWindow();
            }
        }
        */
        if(getData()){
            ReclamoRecord reclamoRecord;
            if(this.reclamo != null){
                 reclamoRecord = this.reclamo;
            } else {
                reclamoRecord = new ReclamoRecord();
            }    
            reclamoRecord.attach(ActiveDatabase.getDSLContext().configuration());
            reclamoRecord.setFechareclamo(sqlReclamoDate);
            reclamoRecord.setFechanotificacion(sqlNotificacionDate);
            if(this.dateDevolucion != null){
            reclamoRecord.setFechadevolucion(sqlDevolucionDate);
                sqlDevolucionDate = new java.sql.Date(this.dateDevolucion.getTime());
            }
            if(this.id_presupuesto != 0){
                reclamoRecord.setIdPresupuesto(this.id_presupuesto);
            }
            reclamoRecord.setDescripcion(this.descripcion);
            reclamoRecord.setAcciones(this.acciones);
            reclamoRecord.setIdReclamoexterno(this.id_reclamoExterno);
            reclamoRecord.setDestinoreclamo(this.destinoReclamo);
            reclamoRecord.setIdUsuario(Principal.usuarioData.getId_usuario());
            reclamoRecord.setIdZona(Principal.usuarioData.getId_zonaSistema());
            reclamoRecord.setRecibe(this.recibe);
            //reclamoRecord.setFile1(this.jLabelImagen.getText());

            if((ReclamoMgr.storeReclamo(reclamoRecord)) > 0){
                
                JOptionPane.showMessageDialog(this, "El Reclamo se efectuó correctamente", "Reclamos Externos",
                        JOptionPane.INFORMATION_MESSAGE);
                id_reclamo = reclamoRecord.getIdReclamo();
            } else {
                 JOptionPane.showMessageDialog(this, "Atención! El Reclamo no se pudo realizar. Intente nuevamente.",
                         "Reclamos Externos", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private boolean checkData() {
        return  this.dateReclamo != null &&
                this.fechaDevolucion != null &&
                this.dateNotificacion != null &&
                this.id_reclamoExterno != 0 &&
                !this.destinoReclamo.isEmpty() &&
                !this.recibe.isEmpty();

    }
    
    private void cleanAll(){
        this.JTextNumeroCirugia.setText("");
        this.JTextTelefono.setText("");
        this.jTextAreaAcciones.setText("");
        this.jTextAreaDescripcion.setText("");
        this.GlazedComboProveedores.setSelectedIndex(-1);
        this.jTextDireccion.setText("");
        this.jTextFechaCirugia.setText("");
        this.jTextLugarCirugia.setText("");
        this.jTextPaciente.setText("");
        this.jTextPrestacion.setText("");
        this.jTextProfesional.setText("");
        this.jTextRecibe.setText("");
        this.jTextReclamoExterno.setText("");
        this.jComboBoxReclamoExterno.setSelectedIndex(0);
        this.jFecha.setCalendar(null);
        this.jFechaDevolucion.setCalendar(null);
        this.jFechaNotificacion.setCalendar(null);
        this.jCheckBoxReclamo.setSelected(false);
        this.jTextFechaCirugia.setEnabled(true);
        this.jTextLugarCirugia.setEnabled(true);
        this.jTextPaciente.setEnabled(true);
        this.jTextProfesional.setEnabled(true);
        this.jComboBoxReclamoExterno.setEnabled(true);
        this.GlazedComboProveedores.setEnabled(true);
        this.jTextDireccion.setEnabled(false);
        this.JTextTelefono.setEnabled(false);
        this.jTextRecibe.setEnabled(false);
        this.jTextAreaAcciones.setEnabled(true);
        this.jTextAreaDescripcion.setEnabled(true);
        this.jFechaDevolucion.setEnabled(true);
        this.jFechaNotificacion.setEnabled(true);
        this.jFecha.setEnabled(true);
        this.JTextNumeroCirugia.requestFocusInWindow();
        this.JButtonGuardar.setEnabled(true);
        this.jButtonClean.setEnabled(true);
        this.jCheckBoxNumeroCirugia.setSelected(false);
        this.JTextNumeroCirugia.setEnabled(false);
        this.jButtonBuscar.setEnabled(false);
    }
    
    private void print(int salida){
            
            Map param=new HashMap();
            param.put("id_reclamo", this.id_reclamo);
            if (this.id_presupuesto != 0){
                param.put("id_cirugia", this.id_presupuesto);
            } else {
                param.put("id_cirugia", "No detallado");
            }
            param.put("tipoReclamo", this.jComboBoxReclamoExterno.getSelectedItem().toString());
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
            param.put("destinoReclamo", this.destinoReclamo);
            param.put("descripcion", this.descripcion);
            param.put("acciones", this.acciones);
            if (!this.fechaCirugia.isEmpty()){
                param.put("fechaCirugia", this.fechaCirugia);
            } else {
                param.put("fechaCirugia", "No detallado");
            }
            param.put("fechaReclamo", format.format(this.sqlReclamoDate));
            param.put("fechaNotificacion", format.format(this.dateNotificacion));            
            param.put("fechaDevolucion", this.fechaDevolucion);
            param.put("direccion", this.direccion);
            param.put("telefono", this.telefono);
            param.put("recibe", this.recibe);
            

            ReclamoExternoDataSource data = new ReclamoExternoDataSource();
            
            ar.com.bosoft.Modelos.ReclamoExterno nuevo = new ar.com.bosoft.Modelos.ReclamoExterno(this.id_reclamo, 
                    this.jComboBoxReclamoExterno.getSelectedItem().toString(), this.paciente, this.profesional, this.prestacion,
                    this.lugarCirugia, this.destinoReclamo, this.descripcion, this.acciones, format.format(this.sqlReclamoDate), 
                    this.fechaNotificacion, this.recibe, this.direccion, String.valueOf(this.telefono));
            
            data.add(nuevo);
            
            Reporte reporte = new Reporte();
            reporte.startReport("ReclamoExterno",param, data, salida, nombreArchivo, impresora, copias);
     
        }

    private void fillDatosProveedor() {
        ProveedorRecord proveedorRecord = ProveedorMgr.getProveedorById(ComboBoxMgr.getSelectedId(GlazedComboProveedores));
        this.jTextDireccion.setText(proveedorRecord.getDireccion());
        this.JTextTelefono.setText(proveedorRecord.getTelefono1());    
    }
    
    private boolean getData(){
        boolean next;
        if(jCheckBoxReclamo.isSelected()){
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea generar un nuevo Tipo de Reclamo Externo?",
                    "Reclamos Externos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                ReclamoexternoRecord reclamoexternoRecord = new ReclamoexternoRecord();
                reclamoexternoRecord.setNombre(jTextReclamoExterno.getText().toUpperCase());
                reclamoexternoRecord.setHabilita("S");
                reclamoexternoRecord.attach(ActiveDatabase.getDSLContext().configuration());
                reclamoexternoRecord.store();
                this.id_reclamoExterno = reclamoexternoRecord.getIdReclamoexterno();
            } else {
                JOptionPane.showMessageDialog(this, "Ingrese un Tipo de Reclamo válido", "Reclamos Externos",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            this.id_reclamoExterno = ComboBoxMgr.getSelectedId(jComboBoxReclamoExterno);
        }
        this.fechaCirugia = jTextFechaCirugia.getText();
        this.lugarCirugia = jTextLugarCirugia.getText();
        this.profesional = jTextProfesional.getText();
        this.prestacion = jTextPrestacion.getText();
        this.paciente = jTextPaciente.getText();
        
        this.direccion = jTextDireccion.getText();
        this.telefono = JTextTelefono.getText();
        this.recibe = jTextRecibe.getText();
        this.acciones = jTextAreaAcciones.getText();
        if(!jTextAreaDescripcion.getText().isEmpty()){
            this.descripcion = jTextAreaDescripcion.getText();            
        } else {
            int respuesta = JOptionPane.showConfirmDialog(this, "No ha colocado Descripción. \n ¿Desea continuar?", "Reclamos Externos", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
               this.descripcion = "";
               next = true;
            } else {
                JOptionPane.showMessageDialog(this, "Escriba una Descripcion");
                next =false;
                jTextAreaDescripcion.requestFocusInWindow();
            }
        }
        if(!jTextAreaAcciones.getText().isEmpty()){
            this.acciones = jTextAreaAcciones.getText();            
        } else {
            int respuesta = JOptionPane.showConfirmDialog(this, "No ha colocado las Acciones. \n ¿Desea continuar?", "Reclamos Externos", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
               this.acciones = "";
               next = true;
            } else {
                JOptionPane.showMessageDialog(this, "Escriba las Acciones");
                jTextAreaAcciones.requestFocusInWindow();
                next = false;
            }
        }
        
        if(!jTextRecibe.getText().isEmpty()){
            this.recibe = jTextRecibe.getText();            
        } else {
            int respuesta = JOptionPane.showConfirmDialog(this, "No ha especificado la persona que \n recibe el Reclamo ¿Desea continuar?", "Reclamos Externos", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
               this.recibe = "";
               next = true;
            } else {
                JOptionPane.showMessageDialog(this, "Especifique quien recibe el Reclamo");
                jTextRecibe.requestFocusInWindow();
                next = false;
            }
        }
        if(jFecha.getDate() != null){
            this.dateReclamo = jFecha.getDate();            
        } else {
            int respuesta = JOptionPane.showConfirmDialog(this, "No ha seleccionado fecha de Reclamo,"
                    + " el Reclamo se generará con fecha de hoy. \n ¿Desea continuar?", "Reclamos Externos",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                this.dateReclamo = new Date();
                next = true;
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione la fecha del Reclamo");
                jFecha.requestFocusInWindow();
                next = false;
            }
        }
        sqlReclamoDate = new java.sql.Date(this.dateReclamo.getTime());
        if(!GlazedComboProveedores.getSelectedItem().toString().isEmpty()){
            this.destinoReclamo = CacheProveedores.instance()
                    .getProveedor(ComboBoxMgr.getSelectedId(GlazedComboProveedores)).getNombre();
            next = true;
        } else {
            JOptionPane.showMessageDialog(this, "No ha seleccionado el Sector al que va dirigido el Reclamo",
                    "Reclamos Externos", JOptionPane.INFORMATION_MESSAGE);
            GlazedComboProveedores.requestFocusInWindow();
            next = false;
        }
        if(jFechaNotificacion.getDate() != null){
            this.dateNotificacion = jFechaNotificacion.getDate();
            next = true;            
        } else {
            int respuesta = JOptionPane.showConfirmDialog(this, "No ha seleccionado fecha de Notificación,"
                    + " se notificará con fecha de hoy. \n ¿Desea continuar?", "Reclamos Externos", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                this.dateNotificacion = new Date();
                this.fechaNotificacion = format.format(dateNotificacion);
                next = true;
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione la fecha de Notificación");
                jFechaNotificacion.requestFocusInWindow();
                next = false;
            }
        }
        sqlNotificacionDate = new java.sql.Date(this.dateNotificacion.getTime());
        if(jFechaDevolucion.getDate() != null){
            this.dateDevolucion = jFechaDevolucion.getDate();
            this.fechaDevolucion = format.format(this.dateDevolucion);
            next = false;
        } else {
            int respuesta = JOptionPane.showConfirmDialog(this, "No ha seleccionado fecha de Devolución. \n ¿Desea continuar?",
                    "Reclamos Externos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                this.fechaDevolucion = "No detallada";
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione la fecha de Devolución");
                jFechaDevolucion.requestFocusInWindow();
                next = false;
            }
        }
    return next;
    }
}
