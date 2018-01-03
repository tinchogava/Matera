/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.AgendaProveedorDataSource;
import ar.com.bosoft.Modelos.AgendaProveedor;
import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.clases.TableHeaderMouseListener;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.DepartamentoCRUD;
import ar.com.bosoft.crud.FormaPagoCRUD;
import ar.com.bosoft.crud.LocalidadCRUD;
import ar.com.bosoft.crud.OpcionCRUD;
import ar.com.bosoft.crud.ProveedorCRUD;
import ar.com.bosoft.crud.ProvinciaCRUD;
import ar.com.bosoft.data.DepartamentoData;
import ar.com.bosoft.data.FormaPagoData;
import ar.com.bosoft.data.LocalidadData;
import ar.com.bosoft.data.OpcionData;
import ar.com.bosoft.data.ProveedorData;
import ar.com.bosoft.data.ProvinciaData;
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
import matera.cache.CacheProveedores;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class ABMProveedor extends javax.swing.JInternalFrame {
    Conexion conexion;
    DefaultTableModel modelo;
    TableRowSorter sorter;
    JTableHeader header;
    TableHeaderMouseListener tableHeaderMoudseListener;
    LocalidadCRUD localidadCRUD;
    DepartamentoCRUD departamentoCRUD;
    ProvinciaCRUD provinciaCRUD;
    FormaPagoCRUD formaPagoCRUD;
    OpcionCRUD opcionCRUD;
    ProveedorCRUD proveedorCRUD;
    ArrayList arrayProvincia, arrayDepartamento, arrayId_departamento, arrayLocalidad, arrayId_localidad, 
            arrayFormaPago,arrayOpcion,arrayProveedor;
    String empresa, impresora, rutaArchivo;
    int id_empresa, id_proveedor, copias;
    
    SeleccionImp seleccionImp;
    Reporte reporte;
    
    /**
     * Creates new form AltaUsuario
     * @param conexion
     * @param empresa
     * @param id_empresa
     */
    public ABMProveedor(Conexion conexion, int id_empresa, String empresa){
        this.conexion = conexion;
        this.empresa = empresa;
        this.id_empresa = id_empresa;
        this.localidadCRUD = new LocalidadCRUD(conexion, empresa);
        this.departamentoCRUD = new DepartamentoCRUD(conexion, empresa);
        this.provinciaCRUD = new ProvinciaCRUD(conexion, empresa);
        this.formaPagoCRUD = new FormaPagoCRUD(conexion, empresa);
        this.opcionCRUD = new OpcionCRUD(conexion, empresa);
        this.proveedorCRUD = new ProveedorCRUD(conexion, id_empresa, empresa);
        
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
        llenaComboFormaPago();
        llenaComboHabilita();
        limpia();
        consulta();
        setJTexFieldChanged(jTxtBusca);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jLblId_Proveedor = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTxtGerente = new javax.swing.JTextField();
        jTxtGerente.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jLabel15 = new javax.swing.JLabel();
        jTxtCuentaBanco = new javax.swing.JTextField();
        jTxtCuentaBanco.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel16 = new javax.swing.JLabel();
        jComboFormaPago = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTxtDepTelefono = new javax.swing.JTextField();
        jTxtDepTelefono.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtAtTelefono = new javax.swing.JTextField();
        jTxtAtTelefono.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtAtEmail = new javax.swing.JTextField();
        jTxtAtEmail.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtAtNombre = new javax.swing.JTextField();
        jTxtAtNombre.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtPagoEmail = new javax.swing.JTextField();
        jTxtPagoEmail.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtDepEmail = new javax.swing.JTextField();
        jTxtDepEmail.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtDepNombre = new javax.swing.JTextField();
        jTxtDepNombre.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtPagoTelefono = new javax.swing.JTextField();
        jTxtPagoTelefono.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jTxtPagoNombre = new javax.swing.JTextField();
        jTxtPagoNombre.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jLabel10 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTxtNombre = new javax.swing.JTextField();
        jTxtNombre.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel13 = new javax.swing.JLabel();
        try{
            jFmtCuit = ar.com.bosoft.formatosCampos.ConMascara.class.newInstance().getjFmt("##-########-#",' ',true);
        }catch (Exception ex){
            Utiles.enviaError(this.empresa,ex);
        }
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
        jTxtCodPostal.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(6,true));
        jLabel23 = new javax.swing.JLabel();
        jTxtEmail = new javax.swing.JTextField();
        jTxtEmail.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jLabel11 = new javax.swing.JLabel();
        jTxtTelefono1 = new javax.swing.JTextField();
        jTxtTelefono1.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel12 = new javax.swing.JLabel();
        jTxtTelefono2 = new javax.swing.JTextField();
        jTxtTelefono2.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();
        jLabel31 = new javax.swing.JLabel();
        jTxtGln = new javax.swing.JTextField();
        jTxtGln.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(14,true));

        setTitle("Proveedores");
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
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
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
                "Código", "Nombre", "Dirección", "Teléfonos", "", "Gerente", "Hab."
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
            jTabla.getColumnModel().getColumn(3).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(3).setMaxWidth(200);
            jTabla.getColumnModel().getColumn(4).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(4).setMaxWidth(200);
            jTabla.getColumnModel().getColumn(5).setPreferredWidth(120);
            jTabla.getColumnModel().getColumn(5).setMaxWidth(200);
            jTabla.getColumnModel().getColumn(6).setPreferredWidth(40);
            jTabla.getColumnModel().getColumn(6).setMaxWidth(40);
        }

        jLblId_Proveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblId_Proveedor.setText("---");

        jLabel14.setText("Gerente");

        jTxtGerente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtGerenteFocusGained(evt);
            }
        });

        jLabel15.setText("Cuenta banc.");

        jTxtCuentaBanco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtCuentaBancoFocusGained(evt);
            }
        });

        jLabel16.setText("Forma de pago");

        jLabel17.setText("Observaciones");

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTxtDepTelefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtDepTelefonoFocusGained(evt);
            }
        });

        jTxtAtTelefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtAtTelefonoFocusGained(evt);
            }
        });

        jTxtAtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtAtEmailFocusGained(evt);
            }
        });

        jTxtAtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtAtNombreFocusGained(evt);
            }
        });

        jTxtPagoEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtPagoEmailFocusGained(evt);
            }
        });

        jTxtDepEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtDepEmailFocusGained(evt);
            }
        });

        jTxtDepNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtDepNombreFocusGained(evt);
            }
        });

        jTxtPagoTelefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtPagoTelefonoFocusGained(evt);
            }
        });

        jTxtPagoNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtPagoNombreFocusGained(evt);
            }
        });

        jLabel10.setText("Nombre");

        jLabel21.setText("Teléfono - Interno");

        jLabel22.setText("e-mail");

        jLabel18.setText("Atención al cliente");

        jLabel19.setText("Encargado de pagos");

        jLabel20.setText("Encargado de depósito");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jTxtDepNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtDepTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtDepEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jTxtAtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtAtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtAtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtPagoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtPagoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jTxtPagoEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel10))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel19)
                    .addComponent(jTxtPagoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtPagoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtPagoEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel18)
                    .addComponent(jTxtAtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtAtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtAtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel20)
                    .addComponent(jTxtDepNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtDepTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtDepEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1))
        );

        jLabel1.setText("* Nombre");

        jTxtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtNombreFocusGained(evt);
            }
        });

        jLabel13.setText("C.U.I.T");

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

        jLabel8.setText("C.P.");

        jLabel23.setText("e-mail");

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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGap(15, 15, 15)
                                            .addComponent(jLabel5)
                                            .addGap(238, 238, 238))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(79, 79, 79)
                                            .addComponent(jComboHabilita, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(180, 180, 180)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(67, 67, 67)
                                        .addComponent(jLabel7)))
                                .addGap(2, 2, 2)
                                .addComponent(jBtnGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtnLimpiar))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel11)
                                                    .addComponent(jLabel9)
                                                    .addComponent(jLabel3)
                                                    .addComponent(jLabel2)
                                                    .addComponent(jLabel1)
                                                    .addComponent(jLblId_Proveedor)
                                                    .addComponent(jLabel14)))
                                            .addComponent(jLabel17))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel13)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jFmtCuit))
                                                .addComponent(jTxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jComboProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel4)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jComboDepartamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jComboLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel8)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jTxtCodPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel23)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jTxtEmail))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jTxtTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel12)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jTxtTelefono2))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jTxtGerente, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel15)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jTxtCuentaBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel16)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jComboFormaPago, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(43, 43, 43)
                                        .addComponent(jLabel31)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTxtGln, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(126, 126, 126)
                                .addComponent(jBtnSalir))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblId_Proveedor)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jFmtCuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jTxtGerente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtCuentaBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jComboFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(jTxtGln, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jComboHabilita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5))
                                        .addGap(26, 26, 26)
                                        .addComponent(jLabel7))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jBtnGuardar)
                                        .addComponent(jBtnLimpiar)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jBtnSalir))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
    
    private void llenaComboFormaPago(){
        arrayFormaPago = formaPagoCRUD.consulta(true);
        Iterator i = arrayFormaPago.iterator();
        while (i.hasNext()){
            FormaPagoData tmp = (FormaPagoData) i.next();
            this.jComboFormaPago.addItem(tmp.getNombre());
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
        this.jLblId_Proveedor.setText("---");
        this.jTxtNombre.setText("");
        this.jFmtCuit.setValue("");
        this.jTxtDireccion.setText("");
        this.jComboProvincia.setSelectedIndex(-1);
        this.jComboDepartamento.setSelectedIndex(-1);
        this.jComboLocalidad.setSelectedIndex(-1);
        this.jTxtCodPostal.setText("");
        this.jTxtEmail.setText("");
        this.jTxtTelefono1.setText("");
        this.jTxtTelefono2.setText("");
        
        this.jTxtGerente.setText("");
        this.jTxtCuentaBanco.setText("");
        this.jComboFormaPago.setSelectedIndex(-1);
        this.jTxtObservaciones.setText("");
        this.jTxtGln.setText("");
        
        this.jTxtPagoNombre.setText("");
        this.jTxtPagoTelefono.setText("");
        this.jTxtPagoEmail.setText("");
        
        this.jTxtAtNombre.setText("");
        this.jTxtAtTelefono.setText("");
        this.jTxtAtEmail.setText("");
        
        this.jTxtDepNombre.setText("");
        this.jTxtDepTelefono.setText("");
        this.jTxtDepEmail.setText("");
        
        this.jComboHabilita.setSelectedItem("SI");
        
        this.jTxtBusca.setText("");
        
        this.id_proveedor = 0;
    }
    
    private void consulta(){
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
        
        arrayProveedor = proveedorCRUD.consulta(false);
        Iterator i = arrayProveedor.iterator();
        while (i.hasNext()){
            ProveedorData tmp = (ProveedorData) i.next();
            int id = tmp.getId_proveedor();
            String nombre = tmp.getNombre();
            String direccion = tmp.getDireccion();
            String telefono1 = tmp.getTelefono1();
            String telefono2 = tmp.getTelefono2();
            String gerente = tmp.getGerente();
            String habilita = (tmp.getHabilita().equals("S") ? "SI" : "NO");
            
            Object[] fila = {id,nombre,direccion,telefono1,telefono2,gerente,habilita};
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
        return !this.jTxtNombre.getText().isEmpty()&&
                !this.jTxtGln.getText().isEmpty();
    }
    
    private void imprimir(int salida){
        Map param = new HashMap();
        param.put("empresa", this.empresa);
        
        AgendaProveedorDataSource data = new AgendaProveedorDataSource();
        Iterator it = this.arrayProveedor.iterator();
        while (it.hasNext()) {
            ProveedorData registro = (ProveedorData) it.next();
            String nombre = registro.getNombre();
            String direccion = registro.getDireccion();
            String localidad = registro.getLocalidad();
            String departamento = registro.getDepartamento();
            String provincia = registro.getProvincia();
            String codPostal = registro.getCodPostal();
            String telefono1 = registro.getTelefono1();
            String telefono2 = registro.getTelefono2();
            String email = registro.getEmail();
            String cuentaBanco = registro.getCuentaBanco();
            String formaPago = registro.getFormaPago();
            String cuit = registro.getCuit();
            String gerente = registro.getGerente();
            String nombrePagos = registro.getPagoNombre();
            String telPagos = registro.getPagoTelefono();
            String emailPagos = registro.getPagoEmail();
            String nombreAtencion = registro.getClienteNombre();
            String telAtencion = registro.getClienteTelefono();
            String emailAtencion = registro.getClienteEmail();
            String nombreDeposito = registro.getDepositoNombre();
            String telDeposito = registro.getDepositoTelefono();
            String emailDeposito = registro.getDepositoEmail();
            String gln = registro.getGln();
            
            ar.com.bosoft.Modelos.AgendaProveedor nuevo = new AgendaProveedor(nombre, direccion, localidad, departamento, provincia, codPostal, telefono1, telefono2, email, cuentaBanco, formaPago, cuit, gerente, nombrePagos, telPagos, emailPagos, nombreAtencion, telAtencion, emailAtencion, nombreDeposito, telDeposito, emailDeposito, gln);
            data.add(nuevo);
        }
        reporte.startReport("AgendaProveedor", param, data, salida, rutaArchivo, impresora, copias);
    }
    
    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
        limpia();
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios()){
            ProveedorData nuevo = new ProveedorData();
            nuevo.setId_proveedor(this.id_proveedor);
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
            nuevo.setTelefono1(this.jTxtTelefono1.getText());
            nuevo.setTelefono2(this.jTxtTelefono2.getText());
            nuevo.setEmail(this.jTxtEmail.getText());
            nuevo.setCuit(this.jFmtCuit.getText().replace("-",""));
            nuevo.setGerente(this.jTxtGerente.getText());
            nuevo.setCuentaBanco(this.jTxtCuentaBanco.getText());
            
            nuevo.setId_formaPago(0);
            if (this.jComboFormaPago.getSelectedIndex() >= 0){
                FormaPagoData f =(FormaPagoData) arrayFormaPago.get(this.jComboFormaPago.getSelectedIndex());
                nuevo.setId_formaPago(f.getId_formaPago());
            }
            
            nuevo.setObservaciones(this.jTxtObservaciones.getText().trim());
            nuevo.setGln(this.jTxtGln.getText());
            nuevo.setHabilita(this.jComboHabilita.getSelectedItem().toString().substring(0, 1));
            
            nuevo.setPagoNombre(this.jTxtPagoNombre.getText());
            nuevo.setPagoTelefono(this.jTxtPagoTelefono.getText());
            nuevo.setPagoEmail(this.jTxtPagoEmail.getText());
            
            nuevo.setClienteNombre(this.jTxtAtNombre.getText());
            nuevo.setClienteTelefono(this.jTxtAtTelefono.getText());
            nuevo.setClienteEmail(this.jTxtAtEmail.getText());
            
            nuevo.setDepositoNombre(this.jTxtDepNombre.getText());
            nuevo.setDepositoTelefono(this.jTxtDepTelefono.getText());
            nuevo.setDepositoEmail(this.jTxtDepEmail.getText());
            
            proveedorCRUD.insert(nuevo);
            
            CacheProveedores.instance().fetch();            
            
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
        ProveedorData tmp = (ProveedorData) arrayProveedor.get(indice);
        this.id_proveedor = tmp.getId_proveedor();
        this.jLblId_Proveedor.setText(String.valueOf(tmp.getId_proveedor()));
        this.jTxtNombre.setText(tmp.getNombre());
        this.jFmtCuit.setText(tmp.getCuit());
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
        this.jTxtGerente.setText(tmp.getGerente());
        this.jTxtCuentaBanco.setText(tmp.getCuentaBanco());
        
        this.jComboFormaPago.setSelectedIndex(-1);
        this.jComboFormaPago.setSelectedItem(tmp.getFormaPago());
        
        this.jTxtObservaciones.setText(tmp.getObservaciones());
        this.jTxtGln.setText(tmp.getGln());
        
        this.jTxtPagoNombre.setText(tmp.getPagoNombre());
        this.jTxtPagoTelefono.setText(tmp.getPagoTelefono());
        this.jTxtPagoEmail.setText(tmp.getPagoEmail());
        
        this.jTxtAtNombre.setText(tmp.getClienteNombre());
        this.jTxtAtTelefono.setText(tmp.getClienteTelefono());
        this.jTxtAtEmail.setText(tmp.getClienteEmail());
        
        this.jTxtDepNombre.setText(tmp.getDepositoNombre());
        this.jTxtDepTelefono.setText(tmp.getDepositoTelefono());
        this.jTxtDepEmail.setText(tmp.getDepositoEmail());
        
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

    private void jTxtTelefono1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtTelefono1FocusGained
        this.jTxtTelefono1.select(0, this.jTxtTelefono1.getText().length());
    }//GEN-LAST:event_jTxtTelefono1FocusGained

    private void jTxtTelefono2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtTelefono2FocusGained
        this.jTxtTelefono2.select(0, this.jTxtTelefono2.getText().length());
    }//GEN-LAST:event_jTxtTelefono2FocusGained

    private void jTxtGerenteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtGerenteFocusGained
        this.jTxtGerente.select(0, this.jTxtGerente.getText().length());
    }//GEN-LAST:event_jTxtGerenteFocusGained

    private void jTxtCuentaBancoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtCuentaBancoFocusGained
        this.jTxtCuentaBanco.select(0, this.jTxtCuentaBanco.getText().length());
    }//GEN-LAST:event_jTxtCuentaBancoFocusGained

    private void jTxtPagoNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtPagoNombreFocusGained
        this.jTxtPagoNombre.select(0, this.jTxtPagoNombre.getText().length());
    }//GEN-LAST:event_jTxtPagoNombreFocusGained

    private void jTxtPagoTelefonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtPagoTelefonoFocusGained
        this.jTxtPagoTelefono.select(0, this.jTxtPagoTelefono.getText().length());
    }//GEN-LAST:event_jTxtPagoTelefonoFocusGained

    private void jTxtPagoEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtPagoEmailFocusGained
        this.jTxtPagoEmail.select(0, this.jTxtPagoEmail.getText().length());
    }//GEN-LAST:event_jTxtPagoEmailFocusGained

    private void jTxtAtNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtAtNombreFocusGained
        this.jTxtAtNombre.select(0, this.jTxtAtNombre.getText().length());
    }//GEN-LAST:event_jTxtAtNombreFocusGained

    private void jTxtAtTelefonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtAtTelefonoFocusGained
        this.jTxtAtTelefono.select(0, this.jTxtAtTelefono.getText().length());
    }//GEN-LAST:event_jTxtAtTelefonoFocusGained

    private void jTxtAtEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtAtEmailFocusGained
        this.jTxtAtEmail.select(0, this.jTxtAtEmail.getText().length());
    }//GEN-LAST:event_jTxtAtEmailFocusGained

    private void jTxtDepTelefonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtDepTelefonoFocusGained
        this.jTxtDepTelefono.select(0, this.jTxtDepTelefono.getText().length());
    }//GEN-LAST:event_jTxtDepTelefonoFocusGained

    private void jTxtDepEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtDepEmailFocusGained
        this.jTxtDepEmail.select(0, this.jTxtDepEmail.getText().length());
    }//GEN-LAST:event_jTxtDepEmailFocusGained

    private void jTxtDepNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtDepNombreFocusGained
        this.jTxtDepNombre.select(0, this.jTxtDepNombre.getText().length());
    }//GEN-LAST:event_jTxtDepNombreFocusGained

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
    private javax.swing.JComboBox jComboDepartamento;
    private javax.swing.JComboBox jComboFormaPago;
    private javax.swing.JComboBox jComboHabilita;
    private javax.swing.JComboBox jComboLocalidad;
    private javax.swing.JComboBox jComboProvincia;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblId_Proveedor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabla;
    private javax.swing.JTextField jTxtAtEmail;
    private javax.swing.JTextField jTxtAtNombre;
    private javax.swing.JTextField jTxtAtTelefono;
    private javax.swing.JTextField jTxtBusca;
    private javax.swing.JTextField jTxtCodPostal;
    private javax.swing.JTextField jTxtCuentaBanco;
    private javax.swing.JTextField jTxtDepEmail;
    private javax.swing.JTextField jTxtDepNombre;
    private javax.swing.JTextField jTxtDepTelefono;
    private javax.swing.JTextField jTxtDireccion;
    private javax.swing.JTextField jTxtEmail;
    private javax.swing.JTextField jTxtGerente;
    private javax.swing.JTextField jTxtGln;
    private javax.swing.JTextField jTxtNombre;
    private javax.swing.JTextArea jTxtObservaciones;
    private javax.swing.JTextField jTxtPagoEmail;
    private javax.swing.JTextField jTxtPagoNombre;
    private javax.swing.JTextField jTxtPagoTelefono;
    private javax.swing.JTextField jTxtTelefono1;
    private javax.swing.JTextField jTxtTelefono2;
    // End of variables declaration//GEN-END:variables
}
