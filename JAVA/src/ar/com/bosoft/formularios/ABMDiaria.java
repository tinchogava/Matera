/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.AgendaDiariaDataSource;
import ar.com.bosoft.Modelos.AgendaDiaria;
import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.clases.TableHeaderMouseListener;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.DepartamentoCRUD;
import ar.com.bosoft.crud.DiariaCRUD;
import ar.com.bosoft.crud.FormaPagoCRUD;
import ar.com.bosoft.crud.LocalidadCRUD;
import ar.com.bosoft.crud.OpcionCRUD;
import ar.com.bosoft.crud.ProvinciaCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.DepartamentoData;
import ar.com.bosoft.data.DiariaData;
import ar.com.bosoft.data.FormaPagoData;
import ar.com.bosoft.data.LocalidadData;
import ar.com.bosoft.data.OpcionData;
import ar.com.bosoft.data.ProvinciaData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class ABMDiaria extends javax.swing.JInternalFrame {
    Conexion conexion;
    DefaultTableModel modelo;
    TableRowSorter sorter;
    JTableHeader header;
    TableHeaderMouseListener tableHeaderMoudseListener;
    ProvinciaCRUD provinciaCRUD;
    DepartamentoCRUD departamentoCRUD;
    LocalidadCRUD localidadCRUD;
    OpcionCRUD opcionCRUD;
    ZonaCRUD zonaCRUD;
    FormaPagoCRUD formaPagoCRUD;
    DiariaCRUD diariaCRUD;
    
    ArrayList arrayProvincia, arrayDepartamento, arrayId_departamento, arrayLocalidad, arrayId_localidad, 
            arrayPosicionIva, arrayZona, arrayFormaPago, arrayOpcion, arrayDiaria;
    
    String empresa, todos, rutaArchivo, impresora;
    int id_empresa, id_diaria, copias;
    
    Reporte reporte;
    SeleccionImp seleccionImp;
    
    /**
     * Creates new form AltaUsuario
     * @param conexion
     * @param empresa
     * @param id_empresa
     */
    public ABMDiaria(Conexion conexion, int id_empresa, String empresa){
        this.conexion = conexion;
        this.empresa = empresa;
        this.id_empresa = id_empresa;
        this.todos = "-- TODOS --";
        this.provinciaCRUD = new ProvinciaCRUD(conexion, empresa);
        this.departamentoCRUD = new DepartamentoCRUD(conexion, empresa);
        this.localidadCRUD = new LocalidadCRUD(conexion, empresa);
        this.opcionCRUD = new OpcionCRUD(conexion, empresa);
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.formaPagoCRUD = new FormaPagoCRUD(conexion, empresa);
        this.diariaCRUD = new DiariaCRUD(conexion, id_empresa, empresa);
        
        this.arrayId_departamento = new ArrayList();
        this.arrayId_localidad = new ArrayList();
        
        this.reporte = new Reporte();
        this.seleccionImp = new SeleccionImp(null, true);
        
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
        llenaComboCondIva();
        llenaComboZona();
        llenaComboFormaPago();
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
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();
        jLblId_profesional = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTxtObservaciones = new javax.swing.JTextField();
        jTxtObservaciones.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(100,false));
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTxtMovContacto = new javax.swing.JTextField();
        jTxtMovContacto.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel30 = new javax.swing.JLabel();
        jTxtTelContacto = new javax.swing.JTextField();
        jTxtTelContacto.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel15 = new javax.swing.JLabel();
        jTxtSecretaria = new javax.swing.JTextField();
        jTxtSecretaria.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jLabel14 = new javax.swing.JLabel();
        jTxtContacto = new javax.swing.JTextField();
        jTxtContacto.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jLabel18 = new javax.swing.JLabel();
        jTxtEmailContacto = new javax.swing.JTextField();
        jTxtEmailContacto.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jComboFormaPago = new javax.swing.JComboBox();
        jLabel37 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTxtNombre = new javax.swing.JTextField();
        jTxtNombre.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(100,true));
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
        jComboPosicionIva = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        try{
            jFmtCuit = ar.com.bosoft.formatosCampos.ConMascara.class.newInstance().getjFmt("##-########-#",' ',true);
        }catch (Exception ex){
            Utiles.enviaError(this.empresa,ex);
        }
        jTxtTelefono1 = new javax.swing.JTextField();
        jTxtTelefono1.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel23 = new javax.swing.JLabel();
        jTxtTelefono2 = new javax.swing.JTextField();
        jTxtTelefono2.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jTxtTelefono3 = new javax.swing.JTextField();
        jTxtTelefono3.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jTxtTelefono4 = new javax.swing.JTextField();
        jTxtTelefono4.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel19 = new javax.swing.JLabel();
        jTxtEmail = new javax.swing.JTextField();
        jTxtEmail.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jPanel2 = new javax.swing.JPanel();
        jComboZonaFiltro = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();

        jCheckBox1.setText("jCheckBox1");

        setTitle("Diaria");
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
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 835, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                "Código", "Nombre", "Dirección", "Teléfono", "Contacto", "Secretaria", "Zona", "Hab."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
            jTabla.getColumnModel().getColumn(2).setPreferredWidth(225);
            jTabla.getColumnModel().getColumn(2).setMaxWidth(450);
            jTabla.getColumnModel().getColumn(3).setPreferredWidth(70);
            jTabla.getColumnModel().getColumn(3).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(4).setPreferredWidth(70);
            jTabla.getColumnModel().getColumn(4).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(5).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(5).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(6).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(6).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(7).setPreferredWidth(40);
            jTabla.getColumnModel().getColumn(7).setMaxWidth(40);
        }

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

        jLblId_profesional.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblId_profesional.setText("---");

        jLabel17.setText("Observaciones");

        jTxtObservaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtObservacionesActionPerformed(evt);
            }
        });
        jTxtObservaciones.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtObservacionesFocusGained(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos de contacto"));

        jLabel12.setText("Móvil");

        jTxtMovContacto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtMovContactoFocusGained(evt);
            }
        });

        jLabel30.setText("Tel.");

        jTxtTelContacto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtTelContactoFocusGained(evt);
            }
        });

        jLabel15.setText("Secretaria");

        jTxtSecretaria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtSecretariaFocusGained(evt);
            }
        });

        jLabel14.setText("Contacto");

        jTxtContacto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtContactoFocusGained(evt);
            }
        });

        jLabel18.setText("Email");

        jTxtEmailContacto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtEmailContactoFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTxtEmailContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jTxtTelContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtMovContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTxtSecretaria))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addComponent(jTxtTelContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtMovContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(1, 1, 1)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTxtSecretaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(jTxtEmailContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jComboFormaPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboFormaPagoKeyReleased(evt);
            }
        });

        jLabel37.setText("Forma de pago");

        jComboZona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboZonaKeyReleased(evt);
            }
        });

        jLabel16.setText("Zona");

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

        jLabel32.setText("Cond. IVA");

        jComboPosicionIva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboPosicionIvaKeyReleased(evt);
            }
        });

        jLabel13.setText("C.U.I.T");

        jTxtTelefono1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtTelefono1FocusGained(evt);
            }
        });

        jLabel23.setText("Teléfonos");

        jTxtTelefono2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtTelefono2FocusGained(evt);
            }
        });

        jTxtTelefono3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtTelefono3FocusGained(evt);
            }
        });

        jTxtTelefono4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtTelefono4FocusGained(evt);
            }
        });

        jLabel19.setText("Email");

        jTxtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtEmailFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel19))
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
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 794, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 794, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jComboPosicionIva, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel32)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(jComboProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel4)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jComboDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jTxtCodPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFmtCuit, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTxtTelefono1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(jTxtEmail))
                        .addGap(2, 2, 2)
                        .addComponent(jTxtTelefono2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jTxtTelefono3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jTxtTelefono4)))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTxtTelefono1, jTxtTelefono2, jTxtTelefono3});

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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTxtCodPostal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(jComboPosicionIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jFmtCuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(jTxtTelefono2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtTelefono3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtTelefono4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jScrollPane2.setViewportView(jPanel3);

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros de impresión"));

        jComboZonaFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboZonaFiltroKeyReleased(evt);
            }
        });

        jLabel20.setText("Zona");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboZonaFiltro, 0, 264, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboZonaFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(3, 3, 3))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 895, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel37)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jComboFormaPago, javax.swing.GroupLayout.Alignment.LEADING, 0, 262, Short.MAX_VALUE)
                                        .addComponent(jComboZona, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jComboHabilita, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTxtObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 819, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLblId_profesional))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(243, 243, 243)
                        .addComponent(jBtnSalir))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnGuardar)
                        .addGap(292, 292, 292)
                        .addComponent(jBtnLimpiar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblId_profesional)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnGuardar)
                            .addComponent(jBtnLimpiar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboHabilita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnSalir, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
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
    
    private void llenaComboCondIva(){
        arrayPosicionIva = opcionCRUD.consulta(1);
        Iterator i = arrayPosicionIva.iterator();
        while (i.hasNext()){
            OpcionData tmp = (OpcionData) i.next();
            this.jComboPosicionIva.addItem(tmp.getNombre());
        }
    }
    
    private void llenaComboFormaPago(){
        arrayFormaPago = formaPagoCRUD.consulta(true);
        Iterator i = arrayFormaPago.iterator();
        while (i.hasNext()){
            FormaPagoData tmp = (FormaPagoData) i.next();
            this.jComboFormaPago.addItem(tmp.getNombre());
        }
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
        this.jComboPosicionIva.setSelectedIndex(-1);
        this.jFmtCuit.setValue("");
        this.jTxtTelefono1.setText("");
        this.jTxtTelefono2.setText("");
        this.jTxtTelefono3.setText("");
        this.jTxtTelefono4.setText("");
        this.jTxtEmail.setText("");
        this.jTxtContacto.setText("");
        this.jTxtTelContacto.setText("");
        this.jTxtMovContacto.setText("");
        this.jTxtEmailContacto.setText("");
        this.jTxtSecretaria.setText("");
        this.jComboZona.setSelectedIndex(-1);
        this.jComboFormaPago.setSelectedIndex(-1);
        this.jTxtObservaciones.setText("");
        this.jComboHabilita.setSelectedItem("SI");
        
        this.jTxtBusca.setText("");
        
        this.id_diaria = 0;
    }
    
    private void consulta(){
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
        
        arrayDiaria = diariaCRUD.consulta(false);
        Iterator i = arrayDiaria.iterator();
        while (i.hasNext()){
            DiariaData tmp = (DiariaData) i.next();
            int id = tmp.getId_diaria();
            String nombre = tmp.getNombre();
            String direccion = tmp.getDireccion();
            String telefono = tmp.getTelefono1();
            String contacto = tmp.getContacto();
            String secretaria = tmp.getSecretaria();
            String zona = tmp.getZona();
            String habilita = (tmp.getHabilita().equals("S") ? "SI" : "NO");
            
            Object[] fila = {id, nombre, direccion, telefono, contacto, secretaria, zona, habilita};
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
    
    private void imprimir(int salida){
        String zonaFiltro = this.jComboZonaFiltro.getSelectedItem().toString();
        Map param = new HashMap();
        param.put("empresa", this.empresa);
        param.put("zona", zonaFiltro);
        
        AgendaDiariaDataSource data = new AgendaDiariaDataSource();
        Iterator it = this.arrayDiaria.iterator();
        while (it.hasNext()) {
            DiariaData registro = (DiariaData) it.next();
            String nombre = registro.getNombre();
            String direccion = registro.getDireccion();
            String localidad = registro.getLocalidad();
            String departamento = registro.getDepartamento();
            String provincia = registro.getProvincia();
            String telefono1 = registro.getTelefono1();
            String telefono2 = registro.getTelefono2();
            String telefono3 = registro.getTelefono3();
            String telefono4 = registro.getTelefono4();
            String email = registro.getEmail();
            String zona = registro.getZona();
            String cuit = registro.getCuit();
            String contacto = registro.getContacto();
            String telContacto = registro.getTelContacto();
            String movContacto = registro.getMovContacto();
            String secretaria = registro.getSecretaria();
            String emailContacto = registro.getEmailContacto();
            
            ar.com.bosoft.Modelos.AgendaDiaria nuevo = new AgendaDiaria(nombre, direccion, localidad, departamento, provincia, telefono1, telefono2, telefono3, telefono4, email, zona, cuit, contacto, telContacto, movContacto, secretaria, emailContacto);
            
            if (zonaFiltro.equals(this.todos)) {
                data.add(nuevo);
            }else if (zonaFiltro.equals(zona)) {
                data.add(nuevo);
            }
        }
        
        reporte.startReport("AgendaDiaria", param, data, salida, rutaArchivo, impresora, copias);
    }
    
    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
        limpia();
        this.jTxtNombre.requestFocus();
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios()){
            DiariaData nuevo = new DiariaData();
            nuevo.setId_diaria(this.id_diaria);
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
            
            nuevo.setId_opcion(0);
            if (this.jComboPosicionIva.getSelectedIndex() >= 0){
                OpcionData o = (OpcionData) arrayPosicionIva.get(this.jComboPosicionIva.getSelectedIndex());
                nuevo.setId_opcion(o.getId_opcion());
            }
            
            nuevo.setCuit(this.jFmtCuit.getText().replace("-",""));
            nuevo.setTelefono1(this.jTxtTelefono1.getText());
            nuevo.setTelefono2(this.jTxtTelefono2.getText());
            nuevo.setTelefono3(this.jTxtTelefono3.getText());
            nuevo.setTelefono4(this.jTxtTelefono4.getText());
            nuevo.setEmail(this.jTxtEmail.getText());
            nuevo.setSecretaria(this.jTxtSecretaria.getText());
            nuevo.setContacto(this.jTxtContacto.getText());
            nuevo.setTelContacto(this.jTxtTelContacto.getText());
            nuevo.setMovContacto(this.jTxtMovContacto.getText());
            nuevo.setEmailContacto(this.jTxtEmailContacto.getText());
            
            nuevo.setId_zona(0);
            if (this.jComboZona.getSelectedIndex() >= 0){
                ZonaData z =(ZonaData) arrayZona.get(this.jComboZona.getSelectedIndex());
                nuevo.setId_zona(z.getId_zona());
            }
            
            nuevo.setId_formaPago(0);
            if (this.jComboFormaPago.getSelectedIndex() >= 0){
                FormaPagoData f =(FormaPagoData) arrayFormaPago.get(this.jComboFormaPago.getSelectedIndex());
                nuevo.setId_formaPago(f.getId_formaPago());
            }
            
            nuevo.setObservaciones(this.jTxtObservaciones.getText());
            nuevo.setHabilita(this.jComboHabilita.getSelectedItem().toString().substring(0, 1));
            
            diariaCRUD.insert(nuevo);
            
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
        DiariaData tmp = (DiariaData) arrayDiaria.get(indice);
        this.id_diaria = tmp.getId_diaria();
        this.jLblId_profesional.setText(String.valueOf(tmp.getId_diaria()));
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
        
        this.jComboPosicionIva.setSelectedIndex(-1);
        this.jComboPosicionIva.setSelectedItem(tmp.getPosicionIva());
        
        this.jFmtCuit.setText(tmp.getCuit());
        this.jTxtTelefono1.setText(tmp.getTelefono1());
        this.jTxtTelefono2.setText(tmp.getTelefono2());
        this.jTxtTelefono3.setText(tmp.getTelefono3());
        this.jTxtTelefono4.setText(tmp.getTelefono4());
        this.jTxtEmail.setText(tmp.getEmail());
        this.jTxtSecretaria.setText(tmp.getSecretaria());
        this.jTxtContacto.setText(tmp.getContacto());
        this.jTxtTelContacto.setText(tmp.getTelContacto());
        this.jTxtMovContacto.setText(tmp.getMovContacto());
        this.jTxtEmailContacto.setText(tmp.getEmailContacto());
        
        this.jComboZona.setSelectedIndex(-1);
        this.jComboZona.setSelectedItem(tmp.getZona());
        
        this.jComboFormaPago.setSelectedIndex(-1);
        this.jComboFormaPago.setSelectedItem(tmp.getFormaPago());
        
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

    private void jBtnScrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScrActionPerformed
        imprimir(0);
    }//GEN-LAST:event_jBtnScrActionPerformed

    private void jBtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImpActionPerformed
        seleccionImp.setVisible(true);
        if (seleccionImp.getSino()){
            this.impresora = seleccionImp.getImpresora();
            imprimir(1);
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

    private void jTxtDireccionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtDireccionFocusGained
        this.jTxtDireccion.select(0, this.jTxtDireccion.getText().length());
    }//GEN-LAST:event_jTxtDireccionFocusGained

    private void jTxtTelefono1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtTelefono1FocusGained
        this.jTxtTelefono1.select(0, this.jTxtTelefono1.getText().length());
    }//GEN-LAST:event_jTxtTelefono1FocusGained

    private void jTxtMovContactoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMovContactoFocusGained
        this.jTxtMovContacto.select(0, this.jTxtMovContacto.getText().length());
    }//GEN-LAST:event_jTxtMovContactoFocusGained

    private void jTxtContactoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtContactoFocusGained
        this.jTxtContacto.select(0, this.jTxtContacto.getText().length());
    }//GEN-LAST:event_jTxtContactoFocusGained

    private void jTxtSecretariaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtSecretariaFocusGained
        this.jTxtSecretaria.select(0, this.jTxtSecretaria.getText().length());
    }//GEN-LAST:event_jTxtSecretariaFocusGained

    private void jTxtObservacionesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtObservacionesFocusGained
        this.jTxtObservaciones.select(0, this.jTxtObservaciones.getText().length());
    }//GEN-LAST:event_jTxtObservacionesFocusGained

    private void jComboProvinciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboProvinciaKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboProvincia.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jComboProvinciaKeyReleased

    private void jComboDepartamentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboDepartamentoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboDepartamento.setSelectedIndex(-1);
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

    private void jTxtTelefono2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtTelefono2FocusGained
        this.jTxtTelefono2.select(0, this.jTxtTelefono2.getText().length());
    }//GEN-LAST:event_jTxtTelefono2FocusGained

    private void jComboZonaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboZonaKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboZona.setSelectedIndex(-1);        
        }
    }//GEN-LAST:event_jComboZonaKeyReleased

    private void jTxtTelContactoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtTelContactoFocusGained
        this.jTxtTelContacto.select(0, this.jTxtTelContacto.getText().length());
    }//GEN-LAST:event_jTxtTelContactoFocusGained

    private void jComboFormaPagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboFormaPagoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboFormaPago.setSelectedIndex(-1);        
        }
    }//GEN-LAST:event_jComboFormaPagoKeyReleased

    private void jComboPosicionIvaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboPosicionIvaKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboPosicionIva.setSelectedIndex(-1);        
        }
    }//GEN-LAST:event_jComboPosicionIvaKeyReleased

    private void jTxtEmailContactoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtEmailContactoFocusGained
        this.jTxtEmailContacto.select(0, this.jTxtEmailContacto.getText().length());
    }//GEN-LAST:event_jTxtEmailContactoFocusGained

    private void jTxtTelefono3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtTelefono3FocusGained
        this.jTxtTelefono3.select(0, this.jTxtTelefono3.getText().length());
    }//GEN-LAST:event_jTxtTelefono3FocusGained

    private void jTxtTelefono4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtTelefono4FocusGained
        this.jTxtTelefono4.select(0, this.jTxtTelefono4.getText().length());
    }//GEN-LAST:event_jTxtTelefono4FocusGained

    private void jTxtEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtEmailFocusGained
        this.jTxtEmail.select(0, this.jTxtEmail.getText().length());
    }//GEN-LAST:event_jTxtEmailFocusGained

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

    private void jTxtObservacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtObservacionesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtObservacionesActionPerformed

    private void jComboZonaFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboZonaFiltroKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboZonaFiltroKeyReleased

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
    private javax.swing.JComboBox jComboFormaPago;
    private javax.swing.JComboBox jComboHabilita;
    private javax.swing.JComboBox jComboLocalidad;
    private javax.swing.JComboBox jComboPosicionIva;
    private javax.swing.JComboBox jComboProvincia;
    private javax.swing.JComboBox jComboZona;
    private javax.swing.JComboBox jComboZonaFiltro;
    private javax.swing.JFormattedTextField jFmtCuit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel37;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabla;
    private javax.swing.JTextField jTxtBusca;
    private javax.swing.JTextField jTxtCodPostal;
    private javax.swing.JTextField jTxtContacto;
    private javax.swing.JTextField jTxtDireccion;
    private javax.swing.JTextField jTxtEmail;
    private javax.swing.JTextField jTxtEmailContacto;
    private javax.swing.JTextField jTxtMovContacto;
    private javax.swing.JTextField jTxtNombre;
    private javax.swing.JTextField jTxtObservaciones;
    private javax.swing.JTextField jTxtSecretaria;
    private javax.swing.JTextField jTxtTelContacto;
    private javax.swing.JTextField jTxtTelefono1;
    private javax.swing.JTextField jTxtTelefono2;
    private javax.swing.JTextField jTxtTelefono3;
    private javax.swing.JTextField jTxtTelefono4;
    // End of variables declaration//GEN-END:variables
}
