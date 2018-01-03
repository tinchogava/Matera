/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.AgendaProfesionalDataSource;
import ar.com.bosoft.Modelos.AgendaProfesional;
import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.clases.TableHeaderMouseListener;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.DepartamentoCRUD;
import ar.com.bosoft.crud.EntidadCRUD;
import ar.com.bosoft.crud.EspecialidadCRUD;
import ar.com.bosoft.crud.LocalidadCRUD;
import ar.com.bosoft.crud.OpcionCRUD;
import ar.com.bosoft.crud.ProfesionalCRUD;
import ar.com.bosoft.crud.ProvinciaCRUD;
import ar.com.bosoft.crud.SubespecialidadCRUD;
import ar.com.bosoft.crud.VendedorCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.DepartamentoData;
import ar.com.bosoft.data.EntidadData;
import ar.com.bosoft.data.EspecialidadData;
import ar.com.bosoft.data.LocalidadData;
import ar.com.bosoft.data.OpcionData;
import ar.com.bosoft.data.ProfesionalData;
import ar.com.bosoft.data.ProvinciaData;
import ar.com.bosoft.data.SubespecialidadData;
import ar.com.bosoft.data.VendedorData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import ar.com.dialogos.AvisoEspera;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import matera.cache.CacheProfesional;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class ABMProfesional extends javax.swing.JInternalFrame {
    Conexion conexion;
    DefaultTableModel modelo;
    TableRowSorter sorter;
    JTableHeader header;
    TableHeaderMouseListener tableHeaderMoudseListener;
    ProvinciaCRUD provinciaCRUD;
    DepartamentoCRUD departamentoCRUD;
    LocalidadCRUD localidadCRUD;
    EspecialidadCRUD especialidadCRUD;
    SubespecialidadCRUD subespecialidadCRUD;
    ZonaCRUD zonaCRUD;
    VendedorCRUD vendedorCRUD;
    EntidadCRUD entidadCRUD;
    OpcionCRUD opcionCRUD;
    ProfesionalCRUD profesionalCRUD;
    
    ArrayList arrayProvincia, arrayDepartamento, arrayId_departamento, arrayLocalidad, arrayId_localidad, 
            arrayEspecialidad, arraySubespecialidad, arrayId_subespecialidad, 
            arrayZona, arrayVendedor, arrayId_vendedor, arrayEntidad, arrayId_entidad, arrayOpcion, arrayProfesional;
    String empresa, todos, rutaArchivo, impresora;
    int id_empresa, id_profesional, copias;
    
    SeleccionImp seleccionImp;
    Reporte reporte;
    
    AvisoEspera avisoEspera;
    
    /**
     * Creates new form AltaUsuario
     * @param conexion
     * @param empresa
     * @param id_empresa
     */
    public ABMProfesional(Conexion conexion, int id_empresa, String empresa){
        this.conexion = conexion;
        this.empresa = empresa;
        this.id_empresa = id_empresa;
        this.todos = "-- TODOS --";
        this.provinciaCRUD = new ProvinciaCRUD(conexion, empresa);
        this.departamentoCRUD = new DepartamentoCRUD(conexion, empresa);
        this.localidadCRUD = new LocalidadCRUD(conexion, empresa);
        this.especialidadCRUD = new EspecialidadCRUD(conexion, empresa);
        this.subespecialidadCRUD = new SubespecialidadCRUD(conexion, empresa);
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.vendedorCRUD = new VendedorCRUD(conexion, id_empresa, empresa);
        this.entidadCRUD = new EntidadCRUD(conexion, id_empresa, empresa);
        this.opcionCRUD = new OpcionCRUD(conexion, empresa);
        this.profesionalCRUD = new ProfesionalCRUD(conexion, id_empresa, empresa);
        
        this.arrayId_departamento = new ArrayList();
        this.arrayId_localidad = new ArrayList();
        
        this.arrayId_subespecialidad = new ArrayList();
        
        this.arrayId_vendedor = new ArrayList();
        this.arrayId_entidad = new ArrayList();
        
        this.seleccionImp = new SeleccionImp(null, true);
        this.reporte = new Reporte();
        
        initComponents();
        
        modelo = (DefaultTableModel) jTabla.getModel();
        jTabla.setModel(modelo);
        jTabla.setRowSorter (new TableRowSorter(modelo));
        sorter = new TableRowSorter(modelo);
        this.header = jTabla.getTableHeader();
        this.tableHeaderMoudseListener = new TableHeaderMouseListener(jTabla);
        this.header.addMouseListener(this.tableHeaderMoudseListener);
        llenaComboProvincia();
        consultaDepartamento();
        consultaLocalidad();
        llenaComboEspecialidad();
        consultaSubespecialidad();
        llenaComboZona();
        consultaVendedor();
        consultaEntidad();
        llenaComboHabilita();
        limpia();
        consulta();
        setJTexFieldChanged(jTxtBusca);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jBtnLimpiar = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jComboHabilita = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla = new javax.swing.JTable();
        jLblId_profesional = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTxtNombre = new javax.swing.JTextField();
        jTxtNombre.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel2 = new javax.swing.JLabel();
        jTxtDireccion = new javax.swing.JTextField();
        jTxtDireccion.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(100,false));
        jComboProvincia = new javax.swing.JComboBox();
        jComboDepartamento = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboLocalidad = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTxtCodPostal = new javax.swing.JTextField();
        jTxtCodPostal.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(6,true));
        jLabel32 = new javax.swing.JLabel();
        jDateFechaNac = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jDateFechaNac.getJCalendar().setTodayButtonVisible(true); 
        this.jDateFechaNac.getJCalendar().setTodayButtonText("Hoy"); 
        this.jDateFechaNac.getJCalendar().setWeekOfYearVisible(false);
        jTxtDni = new javax.swing.JTextField();
        jTxtDni.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(10,true));
        jLabel34 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTxtContacto = new javax.swing.JTextField();
        jTxtContacto.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtPerfil = new javax.swing.JTextField();
        jTxtPerfil.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(100,false));
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jTxtMatricula = new javax.swing.JTextField();
        jTxtMatricula.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(12,true));
        jPanel5 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jTxtEmail = new javax.swing.JTextField();
        jTxtEmail.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jLabel11 = new javax.swing.JLabel();
        jTxtTelParticular = new javax.swing.JTextField();
        jTxtTelParticular.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel12 = new javax.swing.JLabel();
        jTxtTelMovil = new javax.swing.JTextField();
        jTxtTelMovil.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel30 = new javax.swing.JLabel();
        jTxtTelConsultorio = new javax.swing.JTextField();
        jTxtTelConsultorio.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel31 = new javax.swing.JLabel();
        jTxtTelOtros = new javax.swing.JTextField();
        jTxtTelOtros.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel15 = new javax.swing.JLabel();
        jTxtSecretaria = new javax.swing.JTextField();
        jTxtSecretaria.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jLabel33 = new javax.swing.JLabel();
        jTxtDirConsultorio = new javax.swing.JTextField();
        jTxtDirConsultorio.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(100,false));
        jPanel2 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jComboEspecialidad = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        jComboSubespecialidad = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        jComboVendedor = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jComboEntidad = new javax.swing.JComboBox();
        jLabel37 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jComboZonaFiltro = new javax.swing.JComboBox();
        jLabel38 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();

        jCheckBox1.setText("jCheckBox1");

        setTitle("Profesionales");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jLabel5.setText("* Habilitado");

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setFocusable(false);

        jTxtBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaFocusGained(evt);
            }
        });

        jLabel6.setText("Buscar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 842, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel7.setText("(*)Datos Obligatorios");

        jBtnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/limpiar.png"))); // NOI18N
        jBtnLimpiar.setText("Limpiar");
        jBtnLimpiar.setBorderPainted(false);
        jBtnLimpiar.setContentAreaFilled(false);
        jBtnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnLimpiar.setFocusPainted(false);
        jBtnLimpiar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/limpiar.png"))); // NOI18N
        jBtnLimpiar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/limpiar.png"))); // NOI18N
        jBtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLimpiarActionPerformed(evt);
            }
        });

        jBtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/guardar.png"))); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.setBorderPainted(false);
        jBtnGuardar.setContentAreaFilled(false);
        jBtnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnGuardar.setFocusPainted(false);
        jBtnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jBtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/salir.png"))); // NOI18N
        jBtnSalir.setText("Salir");
        jBtnSalir.setBorderPainted(false);
        jBtnSalir.setContentAreaFilled(false);
        jBtnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnSalir.setFocusPainted(false);
        jBtnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jBtnSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/salir.png"))); // NOI18N
        jBtnSalir.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/salir.png"))); // NOI18N
        jBtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSalirActionPerformed(evt);
            }
        });

        jTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Tel. part.", "Tel. consul.", "Tel. móvil", "Secretaria", "Espec.", "Subesp.", "Zona", "Hab."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTabla);
        if (jTabla.getColumnModel().getColumnCount() > 0) {
            jTabla.getColumnModel().getColumn(0).setMinWidth(0);
            jTabla.getColumnModel().getColumn(0).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(0).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(2).setPreferredWidth(70);
            jTabla.getColumnModel().getColumn(2).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(3).setPreferredWidth(70);
            jTabla.getColumnModel().getColumn(3).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(4).setPreferredWidth(70);
            jTabla.getColumnModel().getColumn(4).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(5).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(5).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(6).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(6).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(7).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(7).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(8).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(8).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(9).setPreferredWidth(40);
            jTabla.getColumnModel().getColumn(9).setMaxWidth(40);
        }

        jLblId_profesional.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblId_profesional.setText("---");

        jLabel17.setText("Observaciones");

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, java.awt.Color.black));

        jLabel1.setText("* Nombre");

        jTxtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtNombreFocusGained(evt);
            }
        });

        jLabel2.setText("Dirección");

        jTxtDireccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtDireccionFocusGained(evt);
            }
        });

        jComboProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboProvinciaActionPerformed(evt);
            }
        });
        jComboProvincia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboProvinciaKeyReleased(evt);
            }
        });

        jComboDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboDepartamentoActionPerformed(evt);
            }
        });
        jComboDepartamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboDepartamentoKeyReleased(evt);
            }
        });

        jLabel4.setText("Departamento");

        jLabel3.setText("Provincia");

        jComboLocalidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboLocalidadKeyReleased(evt);
            }
        });

        jLabel9.setText("Localidad");

        jLabel8.setText("C.P.");

        jTxtCodPostal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtCodPostalFocusGained(evt);
            }
        });

        jLabel32.setText("Fecha nac.");

        jTxtDni.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtDniFocusGained(evt);
            }
        });

        jLabel34.setText("DNI");

        jLabel14.setText("Contacto");

        jTxtContacto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtContactoFocusGained(evt);
            }
        });

        jTxtPerfil.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtPerfilFocusGained(evt);
            }
        });

        jLabel35.setText("Perfil");

        jLabel36.setText("Matrícula");

        jTxtMatricula.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtMatriculaFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(13, 13, 13)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel1))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel8)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTxtDireccion, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jTxtContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtPerfil))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jTxtCodPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68)
                                .addComponent(jTxtDni))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel34)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jComboProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel4)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel36))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jTxtNombre))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboDepartamento, jComboProvincia});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9)
                    .addComponent(jComboLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jTxtCodPostal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel32))
                    .addComponent(jDateFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTxtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel34)
                        .addComponent(jLabel36)
                        .addComponent(jTxtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)))
        );

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos de contacto"));

        jLabel23.setText("e-mail");

        jTxtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtEmailFocusGained(evt);
            }
        });

        jLabel11.setText("Tel. part.");

        jTxtTelParticular.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtTelParticularFocusGained(evt);
            }
        });

        jLabel12.setText("Tel. móvil");

        jTxtTelMovil.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtTelMovilFocusGained(evt);
            }
        });

        jLabel30.setText("Tel. consul.");

        jTxtTelConsultorio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtTelConsultorioFocusGained(evt);
            }
        });

        jLabel31.setText("Tel. otros");

        jTxtTelOtros.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtTelOtrosFocusGained(evt);
            }
        });

        jLabel15.setText("Secretaria");

        jTxtSecretaria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtSecretariaFocusGained(evt);
            }
        });

        jLabel33.setText("Dir. consul.");

        jTxtDirConsultorio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtDirConsultorioFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel23)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtTelConsultorio, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jTxtSecretaria, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(172, 456, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jTxtTelParticular, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtTelMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtTelOtros))))
                    .addComponent(jTxtDirConsultorio))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel11)
                    .addComponent(jTxtTelParticular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jTxtTelMovil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(jTxtTelOtros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jTxtTelConsultorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtSecretaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtDirConsultorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addGap(1, 1, 1))
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos profesionales"));

        jLabel24.setText("Especialidad");

        jComboEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboEspecialidadActionPerformed(evt);
            }
        });
        jComboEspecialidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboEspecialidadKeyReleased(evt);
            }
        });

        jLabel25.setText("Subespecialidad");

        jComboSubespecialidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboSubespecialidadKeyReleased(evt);
            }
        });

        jLabel26.setText("Agente");

        jComboVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboVendedorKeyReleased(evt);
            }
        });

        jLabel16.setText("Zona");

        jComboZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboZonaActionPerformed(evt);
            }
        });
        jComboZona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboZonaKeyReleased(evt);
            }
        });

        jComboEntidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboEntidadKeyReleased(evt);
            }
        });

        jLabel37.setText("Entidad");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboVendedor, 0, 200, Short.MAX_VALUE)
                    .addComponent(jComboEspecialidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jComboSubespecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboZona, 0, 282, Short.MAX_VALUE))
                    .addComponent(jComboEntidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(jComboSubespecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jComboVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)))
        );

        jPanel6.setBackground(new java.awt.Color(153, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros de impresión"));

        jComboZonaFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboZonaFiltroKeyReleased(evt);
            }
        });

        jLabel38.setText("Zona");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboZonaFiltro, 0, 192, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboZonaFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addGap(3, 3, 3))
        );

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Salida"));

        jBtnScr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/pantalla.png"))); // NOI18N
        jBtnScr.setBorder(null);
        jBtnScr.setBorderPainted(false);
        jBtnScr.setContentAreaFilled(false);
        jBtnScr.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnScr.setFocusPainted(false);
        jBtnScr.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/pantalla.png"))); // NOI18N
        jBtnScr.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/pantalla.png"))); // NOI18N
        jBtnScr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnScrActionPerformed(evt);
            }
        });

        jBtnImp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/impresora.png"))); // NOI18N
        jBtnImp.setBorder(null);
        jBtnImp.setBorderPainted(false);
        jBtnImp.setContentAreaFilled(false);
        jBtnImp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnImp.setFocusPainted(false);
        jBtnImp.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/impresora.png"))); // NOI18N
        jBtnImp.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/impresora.png"))); // NOI18N
        jBtnImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnImpActionPerformed(evt);
            }
        });

        jBtnPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/pdf.png"))); // NOI18N
        jBtnPdf.setBorder(null);
        jBtnPdf.setBorderPainted(false);
        jBtnPdf.setContentAreaFilled(false);
        jBtnPdf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnPdf.setFocusPainted(false);
        jBtnPdf.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/pdf.png"))); // NOI18N
        jBtnPdf.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/pdf.png"))); // NOI18N
        jBtnPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPdfActionPerformed(evt);
            }
        });

        jBtnXls.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/xls.png"))); // NOI18N
        jBtnXls.setBorder(null);
        jBtnXls.setBorderPainted(false);
        jBtnXls.setContentAreaFilled(false);
        jBtnXls.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnXls.setFocusPainted(false);
        jBtnXls.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/xls.png"))); // NOI18N
        jBtnXls.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/xls.png"))); // NOI18N
        jBtnXls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnXlsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jBtnScr, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnImp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnXls, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBtnScr)
            .addComponent(jBtnImp)
            .addComponent(jBtnPdf)
            .addComponent(jBtnXls)
        );

        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jScrollPane2.setViewportView(jTxtObservaciones);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(113, 113, 113)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnSalir))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLblId_profesional)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(255, 255, 255)
                                .addComponent(jBtnGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtnLimpiar))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jComboHabilita, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane2))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblId_profesional)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboHabilita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(55, 55, 55))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jBtnGuardar)
                                    .addComponent(jBtnLimpiar)
                                    .addComponent(jLabel7))
                                .addGap(12, 12, 12)))
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jBtnSalir, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(3, 3, 3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void llenaComboProvincia(){
        arrayProvincia = provinciaCRUD.consulta();
        Iterator i = arrayProvincia.iterator();
        while (i.hasNext()){
            ProvinciaData tmp = (ProvinciaData) i.next();
            this.jComboProvincia.addItem(tmp.getNombre());
        }
    }
    
    private void consultaDepartamento(){
        arrayDepartamento = departamentoCRUD.consulta();
    }
    
    private void llenaComboDepartamento(int id_provincia){
        this.jComboDepartamento.removeAllItems();
        this.arrayId_departamento.clear();
        
        if (id_provincia != 0) {
            Iterator i = arrayDepartamento.iterator();
            while (i.hasNext()) {
                DepartamentoData tmp = (DepartamentoData) i.next();
                if (tmp.getId_provincia() == id_provincia){
                    this.arrayId_departamento.add(tmp.getId_departamento());
                    this.jComboDepartamento.addItem(tmp.getNombre());
                }   
            }
        }
        this.jComboDepartamento.setSelectedIndex(-1);
    }
    
    private void consultaLocalidad(){
        arrayLocalidad = localidadCRUD.consulta();
    }
    
    private void llenaComboLocalidad(int id_departamento){
        this.jComboLocalidad.removeAllItems();
        this.arrayId_localidad.clear();
        
        if (id_departamento != 0){
            Iterator i = this.arrayLocalidad.iterator();
            while (i.hasNext()) {
                LocalidadData tmp = (LocalidadData) i.next();
                if (tmp.getId_departamento() == id_departamento){
                    this.arrayId_localidad.add(tmp.getId_localidad());
                    this.jComboLocalidad.addItem(tmp.getNombre());
                }   
            }
        }
        this.jComboLocalidad.setSelectedIndex(-1);
    }
    
    private void llenaComboEspecialidad(){
        arrayEspecialidad = especialidadCRUD.consulta(true);
        Iterator i = arrayEspecialidad.iterator();
        while (i.hasNext()){
            EspecialidadData tmp = (EspecialidadData) i.next();
            this.jComboEspecialidad.addItem(tmp.getNombre());
        }
    }
    
    private void consultaSubespecialidad(){
        arraySubespecialidad = subespecialidadCRUD.consulta(true);
        
    }
    private void llenaComboSubespecialidad(int id_especialidad){
        this.jComboSubespecialidad.removeAllItems();
        this.arrayId_subespecialidad.clear();
        
        if (id_especialidad != 0) {
            Iterator i = arraySubespecialidad.iterator();
            while (i.hasNext()) {
                SubespecialidadData tmp = (SubespecialidadData) i.next();
                if (tmp.getId_especialidad() == id_especialidad){
                    this.arrayId_subespecialidad.add(tmp.getId_subespecialidad());
                    this.jComboSubespecialidad.addItem(tmp.getNombre());
                }   
            }
        }
        this.jComboSubespecialidad.setSelectedIndex(-1);
    }
    
    private void llenaComboZona(){
        this.jComboZonaFiltro.addItem(this.todos);
        arrayZona = zonaCRUD.consulta(true);
        Iterator i = arrayZona.iterator();
        while (i.hasNext()){
            ZonaData tmp = (ZonaData) i.next();
            this.jComboZona.addItem(tmp.getNombre());
            this.jComboZonaFiltro.addItem(tmp.getNombre());            
        }
    }
    
    private void consultaVendedor(){
        this.arrayVendedor = vendedorCRUD.consulta(true);
    }
    
    private void llenaComboVendedor(int id_zona){
        this.jComboVendedor.removeAllItems();
        this.arrayId_vendedor.clear();
        
        if (id_zona != 0) {
            Iterator i = arrayVendedor.iterator();
            while (i.hasNext()) {
                VendedorData tmp = (VendedorData) i.next();
                if (tmp.getId_zona() == id_zona){
                    this.arrayId_vendedor.add(tmp.getId_vendedor());
                    this.jComboVendedor.addItem(tmp.getNombre());
                }   
            }
        }
        this.jComboVendedor.setSelectedIndex(-1);
    }
    
    private void consultaEntidad(){
        this.arrayEntidad = entidadCRUD.consulta(true);
    }
    
    private void llenaComboEntidad(int id_zona){
        this.jComboEntidad.removeAllItems();
        this.arrayId_entidad.clear();
        
        if (id_zona != 0) {
            Iterator i = arrayEntidad.iterator();
            while (i.hasNext()) {
                EntidadData tmp = (EntidadData) i.next();
                if (tmp.getId_zona() == id_zona){
                    this.arrayId_entidad.add(tmp.getId_entidad());
                    this.jComboEntidad.addItem(tmp.getNombre());
                }   
            }
        }
        this.jComboEntidad.setSelectedIndex(-1);
    }
    
    private void llenaComboHabilita(){
        arrayOpcion = opcionCRUD.consulta(2);
        Iterator i = arrayOpcion.iterator();
        while (i.hasNext()){
            OpcionData tmp = (OpcionData) i.next();
            jComboHabilita.addItem(tmp.getNombre());
        }
    }
    
    private void limpia(){
        this.jLblId_profesional.setText("---");
        this.jTxtNombre.setText("");
        this.jTxtDireccion.setText("");
        this.jComboProvincia.setSelectedIndex(-1);
        this.jComboDepartamento.setSelectedIndex(-1);
        this.jComboLocalidad.setSelectedIndex(-1);
        this.jTxtCodPostal.setText("");
        this.jDateFechaNac.setDate(new Date());
        this.jTxtDni.setText("");
        this.jTxtMatricula.setText("");
        this.jTxtContacto.setText("");
        this.jTxtPerfil.setText("");
        this.jTxtEmail.setText("");
        this.jTxtTelParticular.setText("");
        this.jTxtTelMovil.setText("");
        this.jTxtTelOtros.setText("");
        this.jTxtTelConsultorio.setText("");
        this.jTxtSecretaria.setText("");
        this.jTxtDirConsultorio.setText("");
        this.jComboEspecialidad.setSelectedIndex(-1);
        this.jComboSubespecialidad.setSelectedIndex(-1);
        this.jComboZona.setSelectedIndex(-1);
        this.jComboVendedor.setSelectedIndex(-1);
        this.jComboEntidad.setSelectedIndex(-1);
        this.jTxtObservaciones.setText("");
        this.jComboHabilita.setSelectedItem("SI");
        
        this.jTxtBusca.setText("");
        
        this.id_profesional = 0;
    }
    
    private void consulta(){
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
        
        arrayProfesional = profesionalCRUD.consulta(0, false);
        Iterator i = arrayProfesional.iterator();
        while (i.hasNext()){
            ProfesionalData tmp = (ProfesionalData) i.next();
            int id = tmp.getId_profesional();
            String nombre = tmp.getNombre();
            String telParticular = tmp.getTelParticular();
            String telConsultorio = tmp.getTelConsultorio();
            String telMovil = tmp.getTelMovil();
            String secretaria = tmp.getSecretaria();
            String especialidad = tmp.getEspecialidad();
            String subespecialidad = tmp.getSubespecialidad();
            String zona = tmp.getZona();
            String habilita = (tmp.getHabilita().equals("S") ? "SI" : "NO");
            
            Object[] fila = {id, nombre, telParticular, telConsultorio, telMovil, secretaria, especialidad, subespecialidad, zona, habilita};
            modelo.addRow(fila);
        }
    }
    
    private void setJTexFieldChanged(JTextField txt){
        DocumentListener documentListener = new DocumentListener() {
 
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                printIt(documentEvent);
            }
 
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                printIt(documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                printIt(documentEvent);
            }
        };
        txt.getDocument().addDocumentListener(documentListener); 
    }
 
    private void printIt(DocumentEvent documentEvent) {
        DocumentEvent.EventType type = documentEvent.getType();
 
        if (type.equals(DocumentEvent.EventType.CHANGE)){
 
        }else if (type.equals(DocumentEvent.EventType.INSERT)){
            JTxtBuscaChanged();
        }else if (type.equals(DocumentEvent.EventType.REMOVE)){
            JTxtBuscaChanged();
        }
    }
 
    private void JTxtBuscaChanged(){
        String text = jTxtBusca.getText().trim().replaceAll("'", "´");
        if (text.length() == 0) {
          sorter.setRowFilter(null);
        } else {
          sorter.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*",this.tableHeaderMoudseListener.getColumna()));
          jTabla.setRowSorter(sorter);
        }
    }

    private boolean validaObligatorios(){
        return !this.jTxtNombre.getText().isEmpty();
    }
    
    private void imprimir(final int salida){
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ABMProfesional.this.avisoEspera = new AvisoEspera(null, false, 4);
                    ABMProfesional.this.avisoEspera.setVisible(true);
                    Thread performer = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ABMProfesional.this.imprimeReal(salida);
                            ABMProfesional.this.avisoEspera.setVisible(false);
                        }
                    }, "Performer");
                    performer.start();
                }
            });
    }
    
    private void imprimeReal(int salida){
        String zonaFiltro = this.jComboZonaFiltro.getSelectedItem().toString();
        Map param = new HashMap();
        param.put("empresa", this.empresa);
        param.put("zona", zonaFiltro);
        
        AgendaProfesionalDataSource data = new AgendaProfesionalDataSource();
        Iterator it = this.arrayProfesional.iterator();
        while (it.hasNext()) {
            ProfesionalData registro = (ProfesionalData) it.next();
            String nombre = registro.getNombre();
            String direccion = registro.getDireccion();
            String localidad = registro.getLocalidad();
            String departamento = registro.getDepartamento();
            String provincia = registro.getProvincia();
            String telParticular = registro.getTelParticular();
            String telMovil = registro.getTelMovil();
            String telConsultorio = registro.getTelConsultorio();
            String telOtro = registro.getTelOtros();
            String email = registro.getEmail();
            String especialidad = registro.getEspecialidad();
            String secretaria = registro.getSecretaria();
            String entidad = registro.getEntidad();
            String dni = registro.getDni();
            String matricula = registro.getMatricula();
            String vendedor = registro.getVendedor();
            String zona = registro.getZona();
            String dirConsultorio = registro.getDirConsultorio();
            Date fechaNac = registro.getFechaNac();
            
            ar.com.bosoft.Modelos.AgendaProfesional nuevo = new AgendaProfesional(nombre, direccion, localidad, departamento, provincia, telParticular, telMovil, telConsultorio, telOtro, email, especialidad, secretaria, entidad, dni, matricula, vendedor, zona, dirConsultorio, fechaNac);
            if (zonaFiltro.equals(this.todos)) {
                data.add(nuevo);
            }else if (zonaFiltro.equals(zona)) {
                data.add(nuevo);
            }
        }
        reporte.startReport("AgendaProfesional", param, data, salida, rutaArchivo, impresora, copias);
    }
    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
        limpia();
        this.jTxtNombre.requestFocus();
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios()){
            ProfesionalData nuevo = new ProfesionalData();
            nuevo.setId_profesional(this.id_profesional);
            nuevo.setNombre(this.jTxtNombre.getText());
            nuevo.setDireccion(this.jTxtDireccion.getText());
            
            int id_provincia = 0;
            if (this.jComboProvincia.getSelectedIndex() >= 0){
                ProvinciaData p = (ProvinciaData) arrayProvincia.get(this.jComboProvincia.getSelectedIndex());
                id_provincia = p.getId_provincia();
            }
            nuevo.setId_provincia(id_provincia);
            
            int id_departamento = 0;
            if (this.jComboDepartamento.getSelectedIndex() >= 0){
                id_departamento = (int) this.arrayId_departamento.get(this.jComboDepartamento.getSelectedIndex());            
            }
            nuevo.setId_departamento(id_departamento);
            
            int id_localidad = 0;
            if (this.jComboLocalidad.getSelectedIndex() >= 0){
                id_localidad = (int) this.arrayId_localidad.get(this.jComboLocalidad.getSelectedIndex());                
            }            
            nuevo.setId_localidad(id_localidad);
            
            nuevo.setCodPostal(this.jTxtCodPostal.getText());
            nuevo.setFechaNac(this.jDateFechaNac.getDate() == null ? null : new java.sql.Date(this.jDateFechaNac.getDate().getTime()));
            nuevo.setDni(this.jTxtDni.getText());
            nuevo.setMatricula(this.jTxtMatricula.getText());
            nuevo.setContacto(this.jTxtContacto.getText());
            nuevo.setPerfil(this.jTxtPerfil.getText());
            nuevo.setEmail(this.jTxtEmail.getText());
            nuevo.setTelParticular(this.jTxtTelParticular.getText());
            nuevo.setTelMovil(this.jTxtTelMovil.getText());
            nuevo.setTelOtros(this.jTxtTelOtros.getText());
            nuevo.setTelConsultorio(this.jTxtTelConsultorio.getText());
            nuevo.setSecretaria(this.jTxtSecretaria.getText());
            nuevo.setDirConsultorio(this.jTxtDirConsultorio.getText());
            
            int id_especialidad = 0;
            
            if (this.jComboEspecialidad.getSelectedIndex() >= 0){
                EspecialidadData e =(EspecialidadData) arrayEspecialidad.get(this.jComboEspecialidad.getSelectedIndex());
                id_especialidad = e.getId_especialidad();
            }
            nuevo.setId_especialidad(id_especialidad);
            
            int id_subespecialidad = 0;
            if (this.jComboSubespecialidad.getSelectedIndex() >= 0){
                id_subespecialidad = (int) this.arrayId_subespecialidad.get(this.jComboSubespecialidad.getSelectedIndex());
            }
            nuevo.setId_subespecialidad(id_subespecialidad);
            
            int id_zona = 0;
            nuevo.setId_zona(0);
            if (this.jComboZona.getSelectedIndex() >= 0){
                ZonaData z =(ZonaData) arrayZona.get(this.jComboZona.getSelectedIndex());
                id_zona = z.getId_zona();
            }
            nuevo.setId_zona(id_zona);
            
            int id_vendedor = 0;
            if (this.jComboVendedor.getSelectedIndex() >= 0){
                id_vendedor =(int) arrayId_vendedor.get(this.jComboVendedor.getSelectedIndex());
            }
            nuevo.setId_vendedor(id_vendedor);
            
            int id_entidad = 0;
            if (this.jComboEntidad.getSelectedIndex() >= 0){
                id_entidad =(int) arrayId_entidad.get(this.jComboEntidad.getSelectedIndex());
            }
            nuevo.setId_entidad(id_entidad);
            
            nuevo.setObservaciones(this.jTxtObservaciones.getText().trim());
            nuevo.setHabilita(this.jComboHabilita.getSelectedItem().toString().substring(0, 1));
            
            profesionalCRUD.insert(nuevo);
            
            CacheProfesional.instance().fetch();
            
            limpia();
            consulta();
            this.jTxtNombre.requestFocus();
        }else{
            JOptionPane.showMessageDialog(this,"Complete todos los datos obligatorios (*)", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaMouseClicked
        int indice = jTabla.convertRowIndexToModel (jTabla.getSelectedRow());
        ProfesionalData tmp = (ProfesionalData) arrayProfesional.get(indice);
        this.id_profesional = tmp.getId_profesional();
        this.jLblId_profesional.setText(String.valueOf(tmp.getId_profesional()));
        this.jTxtNombre.setText(tmp.getNombre());
        this.jTxtDireccion.setText(tmp.getDireccion());
        
        this.jComboProvincia.setSelectedIndex(-1);
        this.jComboProvincia.setSelectedItem(tmp.getProvincia());
        this.jComboProvinciaActionPerformed(null);
        
        this.jComboDepartamento.setSelectedIndex(-1);
        this.jComboDepartamento.setSelectedItem(tmp.getDepartamento());
        this.jComboDepartamentoActionPerformed(null);
        
        this.jComboLocalidad.setSelectedIndex(-1);
        this.jComboLocalidad.setSelectedItem(tmp.getLocalidad());
        
        this.jTxtCodPostal.setText(tmp.getCodPostal());
        this.jDateFechaNac.setDate(tmp.getFechaNac());
        this.jTxtDni.setText(tmp.getDni());
        this.jTxtMatricula.setText(tmp.getMatricula());
        this.jTxtContacto.setText(tmp.getContacto());
        this.jTxtPerfil.setText(tmp.getPerfil());
        this.jTxtEmail.setText(tmp.getEmail());
        this.jTxtTelParticular.setText(tmp.getTelParticular());
        this.jTxtTelMovil.setText(tmp.getTelMovil());
        this.jTxtTelOtros.setText(tmp.getTelOtros());
        this.jTxtTelConsultorio.setText(tmp.getTelConsultorio());
        this.jTxtSecretaria.setText(tmp.getSecretaria());
        this.jTxtDirConsultorio.setText(tmp.getDirConsultorio());
        
        this.jComboEspecialidad.setSelectedIndex(-1);
        this.jComboEspecialidad.setSelectedItem(tmp.getEspecialidad());
        
        this.jComboSubespecialidad.setSelectedIndex(-1);
        this.jComboSubespecialidad.setSelectedItem(tmp.getSubespecialidad());
        
        this.jComboZona.setSelectedIndex(-1);
        this.jComboZona.setSelectedItem(tmp.getZona());
        
        this.jComboVendedor.setSelectedIndex(-1);
        this.jComboVendedor.setSelectedItem(tmp.getVendedor());
        
        this.jComboEntidad.setSelectedIndex(-1);
        this.jComboEntidad.setSelectedItem(tmp.getEntidad());
        
        this.jTxtObservaciones.setText(tmp.getObservaciones());
        this.jComboHabilita.setSelectedItem(tmp.getHabilita().equals("S") ? "SI" : "NO");
        
        this.jTxtNombre.requestFocus();
    }//GEN-LAST:event_jTablaMouseClicked

    private void jTxtNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtNombreFocusGained
        this.jTxtNombre.select(0, this.jTxtNombre.getText().length());
    }//GEN-LAST:event_jTxtNombreFocusGained

    private void jTxtBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaFocusGained
        this.jTxtBusca.select(0, this.jTxtBusca.getText().length());
    }//GEN-LAST:event_jTxtBuscaFocusGained

    private void jTxtDireccionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtDireccionFocusGained
        this.jTxtDireccion.select(0, this.jTxtDireccion.getText().length());
    }//GEN-LAST:event_jTxtDireccionFocusGained

    private void jTxtTelParticularFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtTelParticularFocusGained
        this.jTxtTelParticular.select(0, this.jTxtTelParticular.getText().length());
    }//GEN-LAST:event_jTxtTelParticularFocusGained

    private void jTxtTelMovilFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtTelMovilFocusGained
        this.jTxtTelMovil.select(0, this.jTxtTelMovil.getText().length());
    }//GEN-LAST:event_jTxtTelMovilFocusGained

    private void jTxtContactoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtContactoFocusGained
        this.jTxtContacto.select(0, this.jTxtContacto.getText().length());
    }//GEN-LAST:event_jTxtContactoFocusGained

    private void jTxtSecretariaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtSecretariaFocusGained
        this.jTxtSecretaria.select(0, this.jTxtSecretaria.getText().length());
    }//GEN-LAST:event_jTxtSecretariaFocusGained

    private void jComboProvinciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboProvinciaKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboProvincia.setSelectedIndex(-1);        
            this.jComboDepartamento.setSelectedIndex(-1);
            this.jComboLocalidad.setSelectedIndex(-1);        
        }
    }//GEN-LAST:event_jComboProvinciaKeyReleased

    private void jComboDepartamentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboDepartamentoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboDepartamento.setSelectedIndex(-1);
            this.jComboLocalidad.setSelectedIndex(-1);        
        }
    }//GEN-LAST:event_jComboDepartamentoKeyReleased

    private void jComboLocalidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboLocalidadKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboLocalidad.setSelectedIndex(-1);        
        }
    }//GEN-LAST:event_jComboLocalidadKeyReleased

    private void jTxtCodPostalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtCodPostalFocusGained
        this.jTxtCodPostal.select(0, this.jTxtCodPostal.getText().length());
    }//GEN-LAST:event_jTxtCodPostalFocusGained

    private void jTxtEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtEmailFocusGained
        this.jTxtEmail.select(0, this.jTxtEmail.getText().length());
    }//GEN-LAST:event_jTxtEmailFocusGained

    private void jComboZonaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboZonaKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboZona.setSelectedIndex(-1);        
            this.jComboVendedor.setSelectedIndex(-1);        
            this.jComboEntidad.setSelectedIndex(-1);        
        }
    }//GEN-LAST:event_jComboZonaKeyReleased

    private void jComboEspecialidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboEspecialidadKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboEspecialidad.setSelectedIndex(-1);
            this.jComboSubespecialidad.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jComboEspecialidadKeyReleased

    private void jComboSubespecialidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboSubespecialidadKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboSubespecialidad.setSelectedIndex(-1);        
        }
    }//GEN-LAST:event_jComboSubespecialidadKeyReleased

    private void jComboVendedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboVendedorKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboVendedor.setSelectedIndex(-1);        
        }
    }//GEN-LAST:event_jComboVendedorKeyReleased

    private void jTxtTelConsultorioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtTelConsultorioFocusGained
        this.jTxtTelConsultorio.select(0, this.jTxtTelConsultorio.getText().length());
    }//GEN-LAST:event_jTxtTelConsultorioFocusGained

    private void jTxtTelOtrosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtTelOtrosFocusGained
        this.jTxtTelOtros.select(0, this.jTxtTelOtros.getText().length());
    }//GEN-LAST:event_jTxtTelOtrosFocusGained

    private void jTxtDirConsultorioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtDirConsultorioFocusGained
        this.jTxtDirConsultorio.select(0, this.jTxtDirConsultorio.getText().length());
    }//GEN-LAST:event_jTxtDirConsultorioFocusGained

    private void jTxtDniFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtDniFocusGained
        this.jTxtDni.select(0, this.jTxtDni.getText().length());
    }//GEN-LAST:event_jTxtDniFocusGained

    private void jTxtPerfilFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtPerfilFocusGained
        this.jTxtPerfil.select(0, this.jTxtPerfil.getText().length());
    }//GEN-LAST:event_jTxtPerfilFocusGained

    private void jTxtMatriculaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMatriculaFocusGained
        this.jTxtMatricula.select(0, this.jTxtMatricula.getText().length());
    }//GEN-LAST:event_jTxtMatriculaFocusGained

    private void jComboEntidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboEntidadKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboEntidad.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jComboEntidadKeyReleased

    private void jComboProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboProvinciaActionPerformed
        if (this.jComboProvincia.getSelectedIndex() >= 0) {
            ProvinciaData tmp = (ProvinciaData) this.arrayProvincia.get(this.jComboProvincia.getSelectedIndex());
            try{
                this.llenaComboDepartamento(tmp.getId_provincia());
                this.llenaComboLocalidad(0);
            } catch (Exception ex){}            
        }
    }//GEN-LAST:event_jComboProvinciaActionPerformed

    private void jComboDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboDepartamentoActionPerformed
        if (this.jComboDepartamento.getSelectedIndex() >= 0) {
            int id_departamento = (int) this.arrayId_departamento.get(this.jComboDepartamento.getSelectedIndex());            
            try{
                this.llenaComboLocalidad(id_departamento);
            } catch (Exception ex){}            
        }
    }//GEN-LAST:event_jComboDepartamentoActionPerformed

    private void jComboEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboEspecialidadActionPerformed
        if (this.jComboEspecialidad.getSelectedIndex() >= 0) {
            EspecialidadData tmp = (EspecialidadData) this.arrayEspecialidad.get(this.jComboEspecialidad.getSelectedIndex());
            try{
                this.llenaComboSubespecialidad(tmp.getId_especialidad());
            } catch (Exception ex){}            
        }
    }//GEN-LAST:event_jComboEspecialidadActionPerformed

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        if (this.jComboZona.getSelectedIndex() >= 0) {
            ZonaData tmp = (ZonaData) this.arrayZona.get(this.jComboZona.getSelectedIndex());
            try{
                this.llenaComboVendedor(tmp.getId_zona());
                this.llenaComboEntidad(tmp.getId_zona());                
            } catch (Exception ex){}            
        }
    }//GEN-LAST:event_jComboZonaActionPerformed

    private void jComboZonaFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboZonaFiltroKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboZonaFiltroKeyReleased

    private void jBtnScrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScrActionPerformed
        imprimir(0);
    }//GEN-LAST:event_jBtnScrActionPerformed

    private void jBtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImpActionPerformed
        seleccionImp.setVisible(true);
        if (seleccionImp.getSino()){
            this.impresora = seleccionImp.getImpresora();
            this.copias = seleccionImp.getCopias();
            if (!this.impresora.isEmpty()) {
                imprimir(1);
            }            
        }
    }//GEN-LAST:event_jBtnImpActionPerformed

    private void jBtnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPdfActionPerformed
        //Crear un objeto FileChooser
        JFileChooser fc = new JFileChooser();
        //Mostrar la ventana para abrir archivo y recoger la respuesta
        int respuesta = fc.showSaveDialog(null);
        //Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION){
            //Crear un objeto File con el archivo elegido
            File archivoElegido = fc.getSelectedFile();
            try {
                this.rutaArchivo = archivoElegido.getCanonicalPath() + ".pdf";
                imprimir(2);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jBtnPdfActionPerformed

    private void jBtnXlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnXlsActionPerformed
        //        if (producto()){
            //            //Crear un objeto FileChooser
            //            JFileChooser fc = new JFileChooser();
            //            //Mostrar la ventana para abrir archivo y recoger la respuesta
            //            int respuesta = fc.showSaveDialog(null);
            //            //Comprobar si se ha pulsado Aceptar
            //            if (respuesta == JFileChooser.APPROVE_OPTION){
                //                //Crear un objeto File con el archivo elegido
                //                File archivoElegido = fc.getSelectedFile();
                //                try {
                    //                    this.rutaArchivo = archivoElegido.getCanonicalPath() + ".xlsx";
                    //                    imprimir(3);
                    //                } catch (IOException ex) {
                    //                    JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
                    //                }
                //            }
            //        }
    }//GEN-LAST:event_jBtnXlsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnLimpiar;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jBtnXls;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboDepartamento;
    private javax.swing.JComboBox jComboEntidad;
    private javax.swing.JComboBox jComboEspecialidad;
    private javax.swing.JComboBox jComboHabilita;
    private javax.swing.JComboBox jComboLocalidad;
    private javax.swing.JComboBox jComboProvincia;
    private javax.swing.JComboBox jComboSubespecialidad;
    private javax.swing.JComboBox jComboVendedor;
    private javax.swing.JComboBox jComboZona;
    private javax.swing.JComboBox jComboZonaFiltro;
    private com.toedter.calendar.JDateChooser jDateFechaNac;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblId_profesional;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabla;
    private javax.swing.JTextField jTxtBusca;
    private javax.swing.JTextField jTxtCodPostal;
    private javax.swing.JTextField jTxtContacto;
    private javax.swing.JTextField jTxtDirConsultorio;
    private javax.swing.JTextField jTxtDireccion;
    private javax.swing.JTextField jTxtDni;
    private javax.swing.JTextField jTxtEmail;
    private javax.swing.JTextField jTxtMatricula;
    private javax.swing.JTextField jTxtNombre;
    private javax.swing.JTextArea jTxtObservaciones;
    private javax.swing.JTextField jTxtPerfil;
    private javax.swing.JTextField jTxtSecretaria;
    private javax.swing.JTextField jTxtTelConsultorio;
    private javax.swing.JTextField jTxtTelMovil;
    private javax.swing.JTextField jTxtTelOtros;
    private javax.swing.JTextField jTxtTelParticular;
    // End of variables declaration//GEN-END:variables
}
