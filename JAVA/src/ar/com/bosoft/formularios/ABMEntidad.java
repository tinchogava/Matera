/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.AgendaEntidadDataSource;
import ar.com.bosoft.Modelos.AgendaEntidad;
import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.clases.TableHeaderMouseListener;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ClasiEntidadCRUD;
import ar.com.bosoft.crud.DepartamentoCRUD;
import ar.com.bosoft.crud.EntidadCRUD;
import ar.com.bosoft.crud.FormaPagoCRUD;
import ar.com.bosoft.crud.LocalidadCRUD;
import ar.com.bosoft.crud.OpcionCRUD;
import ar.com.bosoft.crud.ProvinciaCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.ClasiEntidadData;
import ar.com.bosoft.data.DepartamentoData;
import ar.com.bosoft.data.EntidadData;
import ar.com.bosoft.data.FormaPagoData;
import ar.com.bosoft.data.LocalidadData;
import ar.com.bosoft.data.OpcionData;
import ar.com.bosoft.data.ProvinciaData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.formatosCampos.Decimal3;
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
public class ABMEntidad extends javax.swing.JInternalFrame {
    Conexion conexion;
    DefaultTableModel modelo;
    TableRowSorter sorter;
    JTableHeader header;
    TableHeaderMouseListener tableHeaderMoudseListener;
    LocalidadCRUD localidadCRUD;
    DepartamentoCRUD departamentoCRUD;
    ProvinciaCRUD provinciaCRUD;
    ZonaCRUD zonaCRUD;
    OpcionCRUD opcionCRUD;
    ClasiEntidadCRUD clasiEntidadCRUD;
    FormaPagoCRUD formaPagoCRUD;
    EntidadCRUD entidadCRUD;
    
    ArrayList arrayProvincia, arrayDepartamento, arrayId_departamento, arrayLocalidad, arrayId_localidad, 
            arrayZona, arrayPosicionIva, arrayClasiEntidad, arrayFormaPago, arrayEntidad, arrayOpcion;
    String empresa, todos, impresora, rutaArchivo;
    int id_empresa, id_entidad, copias;
    
    SeleccionImp seleccionImp;
    Reporte reporte;
    
    /**
     * Creates new form AltaUsuario
     * @param conexion
     * @param empresa
     * @param id_empresa
     */
    public ABMEntidad(Conexion conexion, int id_empresa, String empresa){
        this.conexion = conexion;
        this.empresa = empresa;
        this.id_empresa = id_empresa;
        this.todos = "-- TODOS --";
        this.localidadCRUD = new LocalidadCRUD(conexion, empresa);
        this.departamentoCRUD = new DepartamentoCRUD(conexion, empresa);
        this.provinciaCRUD = new ProvinciaCRUD(conexion, empresa);
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.opcionCRUD = new OpcionCRUD(conexion, empresa);
        this.clasiEntidadCRUD = new ClasiEntidadCRUD(conexion, empresa);
        this.formaPagoCRUD = new FormaPagoCRUD(conexion, empresa);
        this.entidadCRUD = new EntidadCRUD(conexion, id_empresa, empresa);
        
        this.arrayId_departamento = new ArrayList();
        this.arrayId_localidad = new ArrayList();
        
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
        llenaComboZona();
        llenaComboFormaPago();
        llenaComboCondIva();
        llenaComboClasiEntidad();
        llenaComboHabilita();
        limpia();
        consulta();
        setJTexFieldChanged(jTxtBusca);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTxtNombre = new javax.swing.JTextField();
        jTxtNombre.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(100,true));
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
        jLblId_Entidad = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTxtContableTelefono = new javax.swing.JTextField();
        jTxtContableTelefono.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtTesoreriaTelefono = new javax.swing.JTextField();
        jTxtTesoreriaTelefono.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtTesoreriaEmail = new javax.swing.JTextField();
        jTxtTesoreriaEmail.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtTesoreriaNombre = new javax.swing.JTextField();
        jTxtTesoreriaNombre.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtComprasEmail = new javax.swing.JTextField();
        jTxtComprasEmail.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtContableEmail = new javax.swing.JTextField();
        jTxtContableEmail.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtContableNombre = new javax.swing.JTextField();
        jTxtContableNombre.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtComprasTelefono = new javax.swing.JTextField();
        jTxtComprasTelefono.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtComprasNombre = new javax.swing.JTextField();
        jTxtComprasNombre.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jLabel10 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTxtFarmaciaNombre = new javax.swing.JTextField();
        jTxtFarmaciaNombre.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtFarmaciaTelefono = new javax.swing.JTextField();
        jTxtFarmaciaTelefono.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtFarmaciaEmail = new javax.swing.JTextField();
        jTxtFarmaciaEmail.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jLabel29 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxtDireccion = new javax.swing.JTextField();
        jTxtDireccion.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(100,false));
        jLabel3 = new javax.swing.JLabel();
        jComboProvincia = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jComboDepartamento = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jComboLocalidad = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jTxtCodPostal = new javax.swing.JTextField();
        jTxtCodPostal.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(10,true));
        jLabel23 = new javax.swing.JLabel();
        jTxtEmail = new javax.swing.JTextField();
        jTxtEmail.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jLabel11 = new javax.swing.JLabel();
        jTxtTelefono1 = new javax.swing.JTextField();
        jTxtTelefono1.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel12 = new javax.swing.JLabel();
        jTxtTelefono2 = new javax.swing.JTextField();
        jTxtTelefono2.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel14 = new javax.swing.JLabel();
        jTxtAuditor = new javax.swing.JTextField();
        jTxtAuditor.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jLabel15 = new javax.swing.JLabel();
        jTxtSecretaria = new javax.swing.JTextField();
        jTxtSecretaria.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jLabel16 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jCheckCertImplante = new javax.swing.JCheckBox();
        jCheckRecomendaciones = new javax.swing.JCheckBox();
        jLabel24 = new javax.swing.JLabel();
        jComboFormaPago = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        jComboPosicionIva = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        try{
            jFmtCuit = ar.com.bosoft.formatosCampos.ConMascara.class.newInstance().getjFmt("##-########-#",' ',true);
        }catch (Exception ex){
            Utiles.enviaError(this.empresa,ex);
        }
        jLabel26 = new javax.swing.JLabel();
        jComboClasiEntidad = new javax.swing.JComboBox();
        jLabel27 = new javax.swing.JLabel();
        jTxtRiesgoCredito = new Decimal3(true);
        jLabel28 = new javax.swing.JLabel();
        jTxtReqFacturacion = new javax.swing.JTextField();
        jTxtReqFacturacion.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(100,false));
        jLabel17 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jComboZonaFiltro = new javax.swing.JComboBox();
        jLabel30 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();
        jLabel31 = new javax.swing.JLabel();
        jTxtGln = new javax.swing.JTextField();
        jTxtGln.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(14,true));

        setTitle("Entidades");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jLabel1.setText("* Nombre");

        jTxtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtNombreFocusGained(evt);
            }
        });

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
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                "Código", "Nombre", "Dirección", "Email", "Teléfono", "Zona", "Hab."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
            jTabla.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTabla.getColumnModel().getColumn(2).setMaxWidth(300);
            jTabla.getColumnModel().getColumn(3).setPreferredWidth(120);
            jTabla.getColumnModel().getColumn(3).setMaxWidth(200);
            jTabla.getColumnModel().getColumn(4).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(4).setMaxWidth(200);
            jTabla.getColumnModel().getColumn(5).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(5).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(6).setPreferredWidth(40);
            jTabla.getColumnModel().getColumn(6).setMaxWidth(40);
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

        jLblId_Entidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblId_Entidad.setText("---");

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTxtContableTelefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtContableTelefonoFocusGained(evt);
            }
        });

        jTxtTesoreriaTelefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtTesoreriaTelefonoFocusGained(evt);
            }
        });

        jTxtTesoreriaEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtTesoreriaEmailFocusGained(evt);
            }
        });

        jTxtTesoreriaNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtTesoreriaNombreFocusGained(evt);
            }
        });

        jTxtComprasEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtComprasEmailFocusGained(evt);
            }
        });

        jTxtContableEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtContableEmailFocusGained(evt);
            }
        });

        jTxtContableNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtContableNombreFocusGained(evt);
            }
        });

        jTxtComprasTelefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtComprasTelefonoFocusGained(evt);
            }
        });

        jTxtComprasNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtComprasNombreFocusGained(evt);
            }
        });

        jLabel10.setText("Nombre");

        jLabel21.setText("Teléfono - Interno");

        jLabel22.setText("e-mail");

        jLabel18.setText("Encargado de tesorería");

        jLabel19.setText("Encargado de compras");

        jLabel20.setText("Encargado contable");

        jTxtFarmaciaNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtFarmaciaNombreFocusGained(evt);
            }
        });

        jTxtFarmaciaTelefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtFarmaciaTelefonoFocusGained(evt);
            }
        });

        jTxtFarmaciaEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtFarmaciaEmailFocusGained(evt);
            }
        });

        jLabel29.setText("Encargado de farmacia");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jTxtContableNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtContableTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtContableEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jTxtTesoreriaNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtTesoreriaTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtTesoreriaEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTxtComprasNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTxtComprasTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(jTxtComprasEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtFarmaciaNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtFarmaciaTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtFarmaciaEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel19)
                    .addComponent(jTxtComprasNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtComprasTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtComprasEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel18)
                    .addComponent(jTxtTesoreriaNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtTesoreriaTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtTesoreriaEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel20)
                    .addComponent(jTxtContableNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtContableTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtContableEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jTxtFarmaciaNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtFarmaciaTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtFarmaciaEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(1, 1, 1))
        );

        jLabel2.setText("Dirección");

        jTxtDireccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtDireccionFocusGained(evt);
            }
        });

        jLabel3.setText("Provincia");

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

        jLabel4.setText("Departamento");

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

        jLabel9.setText("Localidad");

        jComboLocalidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboLocalidadKeyReleased(evt);
            }
        });

        jLabel8.setText("C.P.");

        jTxtCodPostal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtCodPostalFocusGained(evt);
            }
        });

        jLabel23.setText("e-mail");

        jTxtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtEmailFocusGained(evt);
            }
        });

        jLabel11.setText("Teléfono (1)");

        jTxtTelefono1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtTelefono1FocusGained(evt);
            }
        });

        jLabel12.setText("Teléfono (2)");

        jTxtTelefono2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtTelefono2FocusGained(evt);
            }
        });

        jLabel14.setText("Auditor");

        jTxtAuditor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtAuditorFocusGained(evt);
            }
        });

        jLabel15.setText("Secretaria");

        jTxtSecretaria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtSecretariaFocusGained(evt);
            }
        });

        jLabel16.setText("Zona");

        jComboZona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboZonaKeyReleased(evt);
            }
        });

        jCheckCertImplante.setText("Cert. de Implante");

        jCheckRecomendaciones.setText("Recomendaciones");

        jLabel24.setText("Forma de pago");

        jComboFormaPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboFormaPagoKeyReleased(evt);
            }
        });

        jLabel25.setText("Cond. IVA");

        jComboPosicionIva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboPosicionIvaKeyReleased(evt);
            }
        });

        jLabel13.setText("C.U.I.T");

        jLabel26.setText("Clas.");

        jComboClasiEntidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboClasiEntidadKeyReleased(evt);
            }
        });

        jLabel27.setText("Riesgo crediticio");

        jLabel28.setText("Req. Facturación");

        jTxtReqFacturacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtReqFacturacionFocusGained(evt);
            }
        });

        jLabel17.setText("Observaciones");

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros de impresión"));

        jComboZonaFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboZonaFiltroKeyReleased(evt);
            }
        });

        jLabel30.setText("Zona");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboZonaFiltro, 0, 192, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboZonaFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addGap(3, 3, 3))
        );

        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jScrollPane2.setViewportView(jTxtObservaciones);

        jLabel31.setText("* GLN");

        jTxtGln.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtGlnFocusGained(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(16, 16, 16)
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jComboHabilita, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(175, 175, 175))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(205, 205, 205)))
                            .addComponent(jBtnGuardar)
                            .addGap(155, 155, 155)
                            .addComponent(jBtnLimpiar))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLblId_Entidad)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel1))
                                .addComponent(jLabel3)
                                .addComponent(jLabel9)
                                .addComponent(jLabel11)
                                .addComponent(jLabel14)
                                .addComponent(jLabel25)
                                .addComponent(jLabel27)
                                .addComponent(jLabel17))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jTxtDireccion)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jComboProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jComboDepartamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jComboLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTxtCodPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel23)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTxtEmail))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jTxtTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTxtTelefono2))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jCheckCertImplante)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jCheckRecomendaciones)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel24))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jTxtAuditor, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel15)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jTxtSecretaria, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel16)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboZona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboFormaPago, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jComboPosicionIva, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jFmtCuit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel26)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jComboClasiEntidad, 0, 173, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jTxtRiesgoCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel28)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTxtReqFacturacion))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jTxtNombre)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel31)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTxtGln, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jBtnSalir)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 674, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 3, 3))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboLocalidad, jComboProvincia});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblId_Entidad)
                    .addComponent(jLabel31)
                    .addComponent(jTxtGln, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jComboDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTxtCodPostal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(jTxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTxtTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jTxtTelefono2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtAuditor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtSecretaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckCertImplante)
                    .addComponent(jCheckRecomendaciones)
                    .addComponent(jLabel24)
                    .addComponent(jComboFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jComboPosicionIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jFmtCuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(jComboClasiEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jTxtRiesgoCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(jTxtReqFacturacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboHabilita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7))
                            .addComponent(jBtnLimpiar))
                        .addGap(3, 3, 3)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jBtnGuardar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jBtnSalir))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1))
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
    
    private void llenaComboFormaPago(){
        arrayFormaPago = formaPagoCRUD.consulta(true);
        Iterator i = arrayFormaPago.iterator();
        while (i.hasNext()){
            FormaPagoData tmp = (FormaPagoData) i.next();
            this.jComboFormaPago.addItem(tmp.getNombre());
        }
    }
    
    private void llenaComboCondIva(){
        arrayPosicionIva = opcionCRUD.consulta(1);
        Iterator i = arrayPosicionIva.iterator();
        while (i.hasNext()){
            OpcionData tmp = (OpcionData) i.next();
            this.jComboPosicionIva.addItem(tmp.getNombre());
        }
    }
    
    private void llenaComboClasiEntidad(){
        arrayClasiEntidad = clasiEntidadCRUD.consulta(true);
        Iterator i = arrayClasiEntidad.iterator();
        while (i.hasNext()){
            ClasiEntidadData tmp = (ClasiEntidadData) i.next();
            this.jComboClasiEntidad.addItem(tmp.getNombre());
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
        this.jLblId_Entidad.setText("---");
        this.jTxtNombre.setText("");
        this.jTxtGln.setText("");
        this.jTxtDireccion.setText("");
        this.jComboProvincia.setSelectedIndex(-1);
        this.jComboDepartamento.setSelectedIndex(-1);
        this.jComboLocalidad.setSelectedIndex(-1);
        this.jTxtCodPostal.setText("");
        this.jTxtEmail.setText("");
        this.jTxtTelefono1.setText("");
        this.jTxtTelefono2.setText("");
        this.jTxtAuditor.setText("");
        this.jTxtSecretaria.setText("");
        this.jComboZona.setSelectedIndex(-1);
        this.jCheckCertImplante.setSelected(false);
        this.jCheckRecomendaciones.setSelected(false);
        this.jComboFormaPago.setSelectedIndex(-1);
        this.jComboPosicionIva.setSelectedIndex(-1);
        this.jFmtCuit.setValue("");
        this.jComboClasiEntidad.setSelectedIndex(-1);
        this.jTxtRiesgoCredito.setValue(0.000);
        this.jTxtReqFacturacion.setText("");
        
        this.jTxtObservaciones.setText("");
        
        this.jTxtComprasNombre.setText("");
        this.jTxtComprasTelefono.setText("");
        this.jTxtComprasEmail.setText("");
        
        this.jTxtTesoreriaNombre.setText("");
        this.jTxtTesoreriaTelefono.setText("");
        this.jTxtTesoreriaEmail.setText("");
        
        this.jTxtContableNombre.setText("");
        this.jTxtContableTelefono.setText("");
        this.jTxtContableEmail.setText("");
        
        this.jTxtFarmaciaNombre.setText("");
        this.jTxtFarmaciaTelefono.setText("");
        this.jTxtFarmaciaEmail.setText("");
        
        this.jComboHabilita.setSelectedItem("SI");
        
        this.jTxtBusca.setText("");
        
        this.id_entidad = 0;
    }
    
    private void consulta(){
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
        
        arrayEntidad = entidadCRUD.consulta(0, false);
        Iterator i = arrayEntidad.iterator();
        while (i.hasNext()){
            EntidadData tmp = (EntidadData) i.next();
            int id = tmp.getId_entidad();
            String nombre = tmp.getNombre();
            String direccion = tmp.getDireccion();
            String email = tmp.getEmail();
            String telefono1 = tmp.getTelefono1();
            String zona = tmp.getZona();
            String habilita = (tmp.getHabilita().equals("S") ? "SI" : "NO");
            
            Object[] fila = {id,nombre,direccion,email,telefono1,zona,habilita};
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
        return !this.jTxtNombre.getText().isEmpty() &&
                !this.jTxtGln.getText().isEmpty();
    }
    
    private void imprimir(int salida){
        String zonaFiltro = this.jComboZonaFiltro.getSelectedItem().toString();
        Map param = new HashMap();
        param.put("empresa", this.empresa);
        param.put("zona", zonaFiltro);
        
        AgendaEntidadDataSource data = new AgendaEntidadDataSource();
        Iterator it = this.arrayEntidad.iterator();
        while (it.hasNext()) {
            EntidadData registro = (EntidadData) it.next();
            String nombre = registro.getNombre();
            String direccion = registro.getDireccion();
            String localidad = registro.getLocalidad();
            String departamento = registro.getDepartamento();
            String provincia = registro.getProvincia();
            String telefono1 = registro.getTelefono1();
            String telefono2 = registro.getTelefono2();
            String zona = registro.getZona();
            String email = registro.getEmail();
            String cuit = registro.getCuit();
            String posIva = registro.getPosicionIva();
            Double riegoCredito = registro.getRiesgoCredito();
            String formaPago = registro.getFormaPago();
            String secretaria = registro.getSecretaria();
            String auditor = registro.getAuditor();
            String nombreCompras = registro.getComprasNombre();
            String telCompras = registro.getComprasTelefono();
            String emailCompras = registro.getComprasEmail();
            String nombreTesoreria = registro.getTesoreriaNombre();
            String telTesoreria = registro.getTesoreriaTelefono();
            String emailTesoreria = registro.getTesoreriaEmail();
            String nombreFarmacia = registro.getFarmaciaNombre();
            String telFarmacia = registro.getFarmaciaTelefono();
            String emailFarmacia = registro.getFarmaciaEmail();
            String nombreContable = registro.getContableNombre();
            String telContable = registro.getContableTelefono();
            String emailContable = registro.getContableEmail();
            String gln = registro.getGln();
            
            ar.com.bosoft.Modelos.AgendaEntidad nuevo = new AgendaEntidad(nombre, direccion, localidad, departamento, provincia, telefono1, telefono2, zona, email, cuit, posIva, formaPago, secretaria, auditor, nombreCompras, telCompras, emailCompras, nombreTesoreria, telTesoreria, emailTesoreria, nombreFarmacia, telFarmacia, emailFarmacia, nombreContable, telContable, emailContable, riegoCredito, gln);
            if (zonaFiltro.equals(this.todos)) {
                data.add(nuevo);
            }else if (zonaFiltro.equals(zona)) {
                data.add(nuevo);
            }
        }
        reporte.startReport("AgendaEntidad", param, data, salida, rutaArchivo, impresora, copias);
    }
    
    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
        limpia();
        this.jTxtNombre.requestFocus();
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios()){
            EntidadData nuevo = new EntidadData();
            nuevo.setId_entidad(this.id_entidad);
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
            nuevo.setEmail(this.jTxtEmail.getText());
            nuevo.setTelefono1(this.jTxtTelefono1.getText());
            nuevo.setTelefono2(this.jTxtTelefono2.getText());
            nuevo.setAuditor(this.jTxtAuditor.getText());
            nuevo.setSecretaria(this.jTxtSecretaria.getText());
            
            nuevo.setId_zona(0);
            if (this.jComboZona.getSelectedIndex() >= 0){
                ZonaData z =(ZonaData) arrayZona.get(this.jComboZona.getSelectedIndex());
                nuevo.setId_zona(z.getId_zona());
            }
            
            nuevo.setCertImplante(this.jCheckCertImplante.isSelected() ? "S" : "N");
            nuevo.setRecomendaciones(this.jCheckRecomendaciones.isSelected() ? "S" : "N");
            
            nuevo.setId_formaPago(0);
            if (this.jComboFormaPago.getSelectedIndex() >= 0){
                FormaPagoData f =(FormaPagoData) arrayFormaPago.get(this.jComboFormaPago.getSelectedIndex());
                nuevo.setId_formaPago(f.getId_formaPago());
            }
            
            nuevo.setId_opcion(0);
            if (this.jComboPosicionIva.getSelectedIndex() >= 0){
                OpcionData o =(OpcionData) arrayPosicionIva.get(this.jComboPosicionIva.getSelectedIndex());
                nuevo.setId_opcion(o.getId_opcion());
            }
            
            nuevo.setCuit(this.jFmtCuit.getText().replace("-",""));
            
            nuevo.setId_clasiEntidad(0);
            if (this.jComboClasiEntidad.getSelectedIndex() >= 0){
                ClasiEntidadData c =(ClasiEntidadData) arrayClasiEntidad.get(this.jComboClasiEntidad.getSelectedIndex());
                nuevo.setId_clasiEntidad(c.getId_clasiEntidad());
            }
            
            nuevo.setRiesgoCredito(Double.parseDouble(this.jTxtRiesgoCredito.getValue().toString()));
            nuevo.setReqFacturacion(this.jTxtReqFacturacion.getText());
            nuevo.setObservaciones(this.jTxtObservaciones.getText().trim());
            nuevo.setHabilita(this.jComboHabilita.getSelectedItem().toString().substring(0, 1));
            nuevo.setComprasNombre(this.jTxtComprasNombre.getText());
            nuevo.setComprasTelefono(this.jTxtComprasTelefono.getText());
            nuevo.setComprasEmail(this.jTxtComprasEmail.getText());
            nuevo.setTesoreriaNombre(this.jTxtTesoreriaNombre.getText());
            nuevo.setTesoreriaTelefono(this.jTxtTesoreriaTelefono.getText());
            nuevo.setTesoreriaEmail(this.jTxtTesoreriaEmail.getText());
            nuevo.setContableNombre(this.jTxtContableNombre.getText());
            nuevo.setContableTelefono(this.jTxtContableTelefono.getText());
            nuevo.setContableEmail(this.jTxtContableEmail.getText());
            nuevo.setFarmaciaNombre(this.jTxtFarmaciaNombre.getText());
            nuevo.setFarmaciaTelefono(this.jTxtFarmaciaTelefono.getText());
            nuevo.setFarmaciaEmail(this.jTxtFarmaciaEmail.getText());
            nuevo.setGln(this.jTxtGln.getText());
            
            entidadCRUD.insert(nuevo);
            
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
        EntidadData tmp = (EntidadData) arrayEntidad.get(indice);
        
        this.id_entidad = tmp.getId_entidad();
        this.jLblId_Entidad.setText(String.valueOf(tmp.getId_entidad()));
        this.jTxtNombre.setText(tmp.getNombre());
        this.jTxtGln.setText(tmp.getGln());
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
        this.jTxtEmail.setText(tmp.getEmail());
        this.jTxtTelefono1.setText(tmp.getTelefono1());
        this.jTxtTelefono2.setText(tmp.getTelefono2());
        this.jTxtAuditor.setText(tmp.getAuditor());
        this.jTxtSecretaria.setText(tmp.getSecretaria());
        
        this.jComboZona.setSelectedIndex(-1);
        this.jComboZona.setSelectedItem(tmp.getZona());
        
        this.jCheckCertImplante.setSelected(tmp.getCertImplante().equals("S"));
        this.jCheckRecomendaciones.setSelected(tmp.getRecomendaciones().equals("S"));
        
        this.jComboFormaPago.setSelectedIndex(-1);
        this.jComboFormaPago.setSelectedItem(tmp.getFormaPago());
        
        this.jComboPosicionIva.setSelectedIndex(-1);
        this.jComboPosicionIva.setSelectedItem(tmp.getPosicionIva());
        
        this.jFmtCuit.setText(tmp.getCuit());
        
        this.jComboClasiEntidad.setSelectedIndex(-1);
        this.jComboClasiEntidad.setSelectedItem(tmp.getClasiEntidad());
        
        this.jTxtRiesgoCredito.setValue(tmp.getRiesgoCredito());
        this.jTxtReqFacturacion.setText(tmp.getReqFacturacion());
        this.jTxtObservaciones.setText(tmp.getObservaciones());
        
        this.jTxtComprasNombre.setText(tmp.getComprasNombre());
        this.jTxtComprasTelefono.setText(tmp.getComprasTelefono());
        this.jTxtComprasEmail.setText(tmp.getComprasEmail());
        
        this.jTxtTesoreriaNombre.setText(tmp.getTesoreriaNombre());
        this.jTxtTesoreriaTelefono.setText(tmp.getTesoreriaTelefono());
        this.jTxtTesoreriaEmail.setText(tmp.getTesoreriaEmail());
        
        this.jTxtContableNombre.setText(tmp.getContableNombre());
        this.jTxtContableTelefono.setText(tmp.getContableTelefono());
        this.jTxtContableEmail.setText(tmp.getContableEmail());
        
        this.jTxtFarmaciaNombre.setText(tmp.getFarmaciaNombre());
        this.jTxtFarmaciaTelefono.setText(tmp.getFarmaciaTelefono());
        this.jTxtFarmaciaEmail.setText(tmp.getFarmaciaEmail());
        
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

    private void jTxtTelefono2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtTelefono2FocusGained
        this.jTxtTelefono2.select(0, this.jTxtTelefono2.getText().length());
    }//GEN-LAST:event_jTxtTelefono2FocusGained

    private void jTxtAuditorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtAuditorFocusGained
        this.jTxtAuditor.select(0, this.jTxtAuditor.getText().length());
    }//GEN-LAST:event_jTxtAuditorFocusGained

    private void jTxtSecretariaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtSecretariaFocusGained
        this.jTxtSecretaria.select(0, this.jTxtSecretaria.getText().length());
    }//GEN-LAST:event_jTxtSecretariaFocusGained

    private void jTxtComprasNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtComprasNombreFocusGained
        this.jTxtComprasNombre.select(0, this.jTxtComprasNombre.getText().length());
    }//GEN-LAST:event_jTxtComprasNombreFocusGained

    private void jTxtComprasTelefonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtComprasTelefonoFocusGained
        this.jTxtComprasTelefono.select(0, this.jTxtComprasTelefono.getText().length());
    }//GEN-LAST:event_jTxtComprasTelefonoFocusGained

    private void jTxtComprasEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtComprasEmailFocusGained
        this.jTxtComprasEmail.select(0, this.jTxtComprasEmail.getText().length());
    }//GEN-LAST:event_jTxtComprasEmailFocusGained

    private void jTxtTesoreriaNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtTesoreriaNombreFocusGained
        this.jTxtTesoreriaNombre.select(0, this.jTxtTesoreriaNombre.getText().length());
    }//GEN-LAST:event_jTxtTesoreriaNombreFocusGained

    private void jTxtTesoreriaTelefonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtTesoreriaTelefonoFocusGained
        this.jTxtTesoreriaTelefono.select(0, this.jTxtTesoreriaTelefono.getText().length());
    }//GEN-LAST:event_jTxtTesoreriaTelefonoFocusGained

    private void jTxtTesoreriaEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtTesoreriaEmailFocusGained
        this.jTxtTesoreriaEmail.select(0, this.jTxtTesoreriaEmail.getText().length());
    }//GEN-LAST:event_jTxtTesoreriaEmailFocusGained

    private void jTxtContableTelefonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtContableTelefonoFocusGained
        this.jTxtContableTelefono.select(0, this.jTxtContableTelefono.getText().length());
    }//GEN-LAST:event_jTxtContableTelefonoFocusGained

    private void jTxtContableEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtContableEmailFocusGained
        this.jTxtContableEmail.select(0, this.jTxtContableEmail.getText().length());
    }//GEN-LAST:event_jTxtContableEmailFocusGained

    private void jTxtContableNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtContableNombreFocusGained
        this.jTxtContableNombre.select(0, this.jTxtContableNombre.getText().length());
    }//GEN-LAST:event_jTxtContableNombreFocusGained

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

    private void jTxtReqFacturacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtReqFacturacionFocusGained
        this.jTxtReqFacturacion.select(0, this.jTxtReqFacturacion.getText().length());
    }//GEN-LAST:event_jTxtReqFacturacionFocusGained

    private void jTxtFarmaciaNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtFarmaciaNombreFocusGained
        this.jTxtFarmaciaNombre.select(0, this.jTxtFarmaciaNombre.getText().length());
    }//GEN-LAST:event_jTxtFarmaciaNombreFocusGained

    private void jTxtFarmaciaTelefonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtFarmaciaTelefonoFocusGained
        this.jTxtFarmaciaTelefono.select(0, this.jTxtFarmaciaTelefono.getText().length());
    }//GEN-LAST:event_jTxtFarmaciaTelefonoFocusGained

    private void jTxtFarmaciaEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtFarmaciaEmailFocusGained
        this.jTxtFarmaciaEmail.select(0, this.jTxtFarmaciaEmail.getText().length());
    }//GEN-LAST:event_jTxtFarmaciaEmailFocusGained

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
        }
    }//GEN-LAST:event_jComboZonaKeyReleased

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

    private void jComboClasiEntidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboClasiEntidadKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboClasiEntidad.setSelectedIndex(-1);        
        }
    }//GEN-LAST:event_jComboClasiEntidadKeyReleased

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

    private void jComboZonaFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboZonaFiltroKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboZonaFiltroKeyReleased

    private void jTxtGlnFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtGlnFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtGlnFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnLimpiar;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jBtnXls;
    private javax.swing.JCheckBox jCheckCertImplante;
    private javax.swing.JCheckBox jCheckRecomendaciones;
    private javax.swing.JComboBox jComboClasiEntidad;
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblId_Entidad;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabla;
    private javax.swing.JTextField jTxtAuditor;
    private javax.swing.JTextField jTxtBusca;
    private javax.swing.JTextField jTxtCodPostal;
    private javax.swing.JTextField jTxtComprasEmail;
    private javax.swing.JTextField jTxtComprasNombre;
    private javax.swing.JTextField jTxtComprasTelefono;
    private javax.swing.JTextField jTxtContableEmail;
    private javax.swing.JTextField jTxtContableNombre;
    private javax.swing.JTextField jTxtContableTelefono;
    private javax.swing.JTextField jTxtDireccion;
    private javax.swing.JTextField jTxtEmail;
    private javax.swing.JTextField jTxtFarmaciaEmail;
    private javax.swing.JTextField jTxtFarmaciaNombre;
    private javax.swing.JTextField jTxtFarmaciaTelefono;
    private javax.swing.JTextField jTxtGln;
    private javax.swing.JTextField jTxtNombre;
    private javax.swing.JTextArea jTxtObservaciones;
    private javax.swing.JTextField jTxtReqFacturacion;
    private javax.swing.JFormattedTextField jTxtRiesgoCredito;
    private javax.swing.JTextField jTxtSecretaria;
    private javax.swing.JTextField jTxtTelefono1;
    private javax.swing.JTextField jTxtTelefono2;
    private javax.swing.JTextField jTxtTesoreriaEmail;
    private javax.swing.JTextField jTxtTesoreriaNombre;
    private javax.swing.JTextField jTxtTesoreriaTelefono;
    // End of variables declaration//GEN-END:variables
}
