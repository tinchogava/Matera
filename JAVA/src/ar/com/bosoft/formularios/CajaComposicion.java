/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.formularios;

import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.CajaComposicionCRUD;
import ar.com.bosoft.crud.CajaDepositoCRUD;
import ar.com.bosoft.crud.ClasiProductoCRUD;
import ar.com.bosoft.crud.ProductoCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.CajaComposicionData;
import ar.com.bosoft.data.CajaDepositoData;
import ar.com.bosoft.data.ClasiProductoData;
import ar.com.bosoft.data.ProductoData;
import ar.com.bosoft.data.ZonaData;
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
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class CajaComposicion extends javax.swing.JInternalFrame {
    Conexion conexion;
    DefaultTableModel modeloProducto, modeloComposicion;
    TableRowSorter sorterProducto;
    ZonaCRUD zonaCRUD;
    CajaDepositoCRUD cajaDepositoCRUD;
    ClasiProductoCRUD clasiProductoCRUD;
    ProductoCRUD productoCRUD;
    CajaComposicionCRUD cajaComposicionCRUD;
    ArrayList arrayZona,arrayCajaDeposito,arrayIdCajaDeposito,arrayClasiProducto,arrayProducto,arrayCajaComposicion;
    
    String empresa, usuario, rutaArchivo, impresora;
    int id_empresa,id_cajaDeposito, copias;
    SeleccionImp seleccionImp;
    
    public CajaComposicion(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.cajaDepositoCRUD = new CajaDepositoCRUD(conexion, id_empresa, empresa);
        this.clasiProductoCRUD = new ClasiProductoCRUD(conexion, empresa);
        this.productoCRUD = new ProductoCRUD(conexion, id_empresa, empresa);
        this.cajaComposicionCRUD = new CajaComposicionCRUD(conexion, id_empresa, empresa, usuario);
        this.arrayIdCajaDeposito = new ArrayList();
        this.seleccionImp = new SeleccionImp(null, true);
        initComponents();
        
        this.modeloProducto = (DefaultTableModel) this.jTablaProducto.getModel();
        this.jTablaProducto.setModel(modeloProducto);
        jTablaProducto.setRowSorter (new TableRowSorter(modeloProducto));
        sorterProducto = new TableRowSorter(modeloProducto);
        
        this.modeloComposicion = (DefaultTableModel) this.jTablaComposicion.getModel();
        this.jTablaComposicion.setModel(modeloComposicion);
        
        consultaCajaDeposito();
        llenaComboZona();
        llenaComboClasiProducto();
        limpia();
        consultaProducto();
        filtraProducto(0);
        setJtxtBuscaProductoChanged(jTxtBuscaProducto);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenuProductos = new javax.swing.JPopupMenu();
        jMenuItemCargar = new javax.swing.JMenuItem();
        jBtnGuardar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboCaja = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablaProducto = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jTxtBuscaProducto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jComboClasiProducto = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaComposicion = new ar.com.bosoft.RenderTablas.RXTable();
        jLabel2 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();

        jMenuItemCargar.setText("Cargar");
        jMenuItemCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCargarActionPerformed(evt);
            }
        });
        jPopupMenuProductos.add(jMenuItemCargar);

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Composición de cajas");
        setAutoscrolls(true);
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N
        getContentPane().setLayout(new java.awt.GridBagLayout());

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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 137, 0, 0);
        getContentPane().add(jBtnGuardar, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel7.setText("(*)Datos Obligatorios");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 3, 0, 0);
        getContentPane().add(jLabel7, gridBagConstraints);

        jLabel5.setText("* Caja");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 0, 0);
        getContentPane().add(jLabel5, gridBagConstraints);

        jComboCaja.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComboCajaFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 257;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 12, 0, 0);
        getContentPane().add(jComboCaja, gridBagConstraints);

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

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 12, 0);
        getContentPane().add(jPanel4, gridBagConstraints);

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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 21;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(33, 256, 12, 3);
        getContentPane().add(jBtnSalir, gridBagConstraints);

        jPanel2.setBackground(java.awt.SystemColor.activeCaption);
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), java.awt.Color.white)); // NOI18N

        jTablaProducto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTablaProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "id_producto", "indice"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaProducto.setComponentPopupMenu(jPopupMenuProductos);
        jTablaProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaProductoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTablaProducto);
        if (jTablaProducto.getColumnModel().getColumnCount() > 0) {
            jTablaProducto.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTablaProducto.getColumnModel().getColumn(0).setMaxWidth(200);
            jTablaProducto.getColumnModel().getColumn(2).setMinWidth(0);
            jTablaProducto.getColumnModel().getColumn(2).setPreferredWidth(0);
            jTablaProducto.getColumnModel().getColumn(2).setMaxWidth(0);
            jTablaProducto.getColumnModel().getColumn(3).setMinWidth(0);
            jTablaProducto.getColumnModel().getColumn(3).setPreferredWidth(0);
            jTablaProducto.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setFocusable(false);

        jTxtBuscaProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaProductoFocusGained(evt);
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
                .addComponent(jTxtBuscaProducto)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBuscaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)))
        );

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tipo");

        jComboClasiProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboClasiProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboClasiProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboClasiProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.ipadx = 307;
        gridBagConstraints.ipady = 275;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 0, 0);
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel6.setBackground(java.awt.Color.pink);
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "* Composición", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), java.awt.Color.white)); // NOI18N

        jTablaComposicion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Cantidad", "id_producto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaComposicion.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTablaComposicion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaComposicionMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaComposicion);
        if (jTablaComposicion.getColumnModel().getColumnCount() > 0) {
            jTablaComposicion.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTablaComposicion.getColumnModel().getColumn(0).setMaxWidth(200);
            jTablaComposicion.getColumnModel().getColumn(2).setPreferredWidth(40);
            jTablaComposicion.getColumnModel().getColumn(2).setMaxWidth(60);
            jTablaComposicion.getColumnModel().getColumn(3).setMinWidth(0);
            jTablaComposicion.getColumnModel().getColumn(3).setPreferredWidth(0);
            jTablaComposicion.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.ipadx = 325;
        gridBagConstraints.ipady = 378;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 3);
        getContentPane().add(jPanel6, gridBagConstraints);

        jLabel2.setText("Zona");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 7, 0, 0);
        getContentPane().add(jLabel2, gridBagConstraints);

        jComboZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboZonaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 257;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 12, 0, 0);
        getContentPane().add(jComboZona, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void consultaCajaDeposito(){
        arrayCajaDeposito = cajaDepositoCRUD.consulta(true);
    }
    
    private void llenaComboZona(){
        arrayZona = zonaCRUD.consulta(true);
        Iterator it = arrayZona.iterator();
        while (it.hasNext()){
            ZonaData tmp = (ZonaData) it.next();
            this.jComboZona.addItem(tmp.getNombre());
        }
    }
    
    private void llenaComboClasiProducto(){
        this.jComboClasiProducto.addItem("-- TODOS --");
        arrayClasiProducto = clasiProductoCRUD.consulta(true);
        Iterator i = arrayClasiProducto.iterator();
        while (i.hasNext()){
            ClasiProductoData tmp = (ClasiProductoData) i.next();
            this.jComboClasiProducto.addItem(tmp.getNombre());
        }
    }
    
    private void limpia(){
        this.jComboZona.setSelectedIndex(-1);
        this.jComboCaja.setSelectedIndex(-1);
        this.jComboClasiProducto.setSelectedIndex(0);
        this.jTxtBuscaProducto.setText("");
        
        modeloComposicion.getDataVector().removeAllElements();
        modeloComposicion.fireTableDataChanged();
        
        this.id_cajaDeposito = 0;
    }
    
    private void consultaProducto(){
        arrayProducto = productoCRUD.consulta(0, true);        
    }
    
    private void filtraProducto(int indiceClasiProducto){
        modeloProducto.getDataVector().removeAllElements();
        modeloProducto.fireTableDataChanged();
        Iterator i = arrayProducto.iterator();
        int indice = 0;
        while (i.hasNext()){
            ProductoData tmp = (ProductoData) i.next();
            String codigo = tmp.getCodigo();
            String nombre = tmp.getNombre();
            int id_producto = tmp.getId_producto();
            if (indiceClasiProducto != 0) {
                if (!tmp.getClasiProducto().equals(this.jComboClasiProducto.getSelectedItem().toString())) {
                    indice++;
                    continue;
                }
            }
            Object [] nuevo = {codigo,nombre,id_producto, indice};
            modeloProducto.addRow(nuevo);
            indice++;
        }
    }
    
    private void llenaCajas(int id_zona){
        this.jComboCaja.removeAllItems();
        this.arrayIdCajaDeposito.clear();
        Iterator it = arrayCajaDeposito.iterator();
        while (it.hasNext()) {
            CajaDepositoData tmp = (CajaDepositoData) it.next();
            if (tmp.getId_zona() == id_zona) {
                this.jComboCaja.addItem(tmp.getCodigo());
                this.arrayIdCajaDeposito.add(tmp.getId_cajaDeposito());
            }   
        }
    }
    
    private void consultaComposicion(){
        modeloComposicion.getDataVector().removeAllElements();
        modeloComposicion.fireTableDataChanged();
        
        if (this.id_cajaDeposito > 0){
            arrayCajaComposicion = cajaComposicionCRUD.consulta(id_cajaDeposito);
            Iterator i = arrayCajaComposicion.iterator();
            while (i.hasNext()){
                CajaComposicionData c = (CajaComposicionData) i.next();
                Object[] fila = {c.getCodigo(), c.getProducto(), c.getCantidad(), c.getId_producto()};
                modeloComposicion.addRow(fila);
            }
        }
    }
    
    private void setJtxtBuscaProductoChanged(JTextField txt){
        DocumentListener documentListener = new DocumentListener() {
 
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                printItProducto(documentEvent);
            }
 
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                printItProducto(documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                printItProducto(documentEvent);
            }
        };
        txt.getDocument().addDocumentListener(documentListener); 
    }
 
    private void printItProducto(DocumentEvent documentEvent) {
        DocumentEvent.EventType type = documentEvent.getType();
 
        if (type.equals(DocumentEvent.EventType.CHANGE)){
 
        }else if (type.equals(DocumentEvent.EventType.INSERT)){
            JTxtBuscaProductoChanged();
        }else if (type.equals(DocumentEvent.EventType.REMOVE)){
            JTxtBuscaProductoChanged();
        }
    }
 
    private void JTxtBuscaProductoChanged(){
        String text = jTxtBuscaProducto.getText().trim().replaceAll("'", "´");
        if (text.length() == 0) {
          sorterProducto.setRowFilter(null);
        } else {
          sorterProducto.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTablaProducto.setRowSorter(sorterProducto);
        }
    }
    
    private boolean validaObligatorios(){
        return this.id_cajaDeposito != 0 &&
                modeloComposicion.getRowCount() > 0;
    }
    
    private void imprimir(int salida){
        Map param = new HashMap();
        param.put("empresa", this.empresa);
        param.put("zona", this.jComboZona.getSelectedItem().toString());
        param.put("caja", this.jComboCaja.getSelectedItem().toString());

        Reporte reporte = new Reporte();
        reporte.startReport("ComposicionCajas", param, new JRTableModelDataSource(modeloComposicion), salida, rutaArchivo, impresora, copias);
    }
    
    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios()){
            cajaComposicionCRUD.delete(this.id_cajaDeposito);
            
            CajaComposicionData nuevo = new CajaComposicionData();
            nuevo.setId_cajaDeposito(id_cajaDeposito);
            for (int i = 0; i < modeloComposicion.getRowCount(); i++){
                nuevo.setId_producto(Integer.parseInt(modeloComposicion.getValueAt(i, 3).toString()));
                nuevo.setCantidad(Integer.parseInt(modeloComposicion.getValueAt(i, 2).toString()));
                if (nuevo.getCantidad() > 0) {
                    cajaComposicionCRUD.insert(nuevo);
                }
            }
            //CacheComposicionCaja.instance().fetch();
            limpia();
            this.jComboCaja.requestFocus();
        }else{
            JOptionPane.showMessageDialog(this,"Complete todos los datos obligatorios (*)", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jTxtBuscaProductoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaProductoFocusGained
        this.jTxtBuscaProducto.select(0, this.jTxtBuscaProducto.getText().length());
    }//GEN-LAST:event_jTxtBuscaProductoFocusGained

    private void jBtnScrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScrActionPerformed
        if (modeloComposicion.getRowCount() > 0){
            imprimir(0);
        }
    }//GEN-LAST:event_jBtnScrActionPerformed

    private void jBtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImpActionPerformed
        if(modeloComposicion.getRowCount() > 0){
            seleccionImp.setVisible(true);
            if (seleccionImp.getSino()){
                this.impresora = seleccionImp.getImpresora();
                imprimir(1);
            }
        }
    }//GEN-LAST:event_jBtnImpActionPerformed

    private void jBtnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPdfActionPerformed
        if (modeloComposicion.getRowCount() > 0){
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
        }
    }//GEN-LAST:event_jBtnPdfActionPerformed

    private void jBtnXlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnXlsActionPerformed
        if (modeloComposicion.getRowCount() > 0){
            //Crear un objeto FileChooser
            JFileChooser fc = new JFileChooser();
            //Mostrar la ventana para abrir archivo y recoger la respuesta
            int respuesta = fc.showSaveDialog(null);
            //Comprobar si se ha pulsado Aceptar
            if (respuesta == JFileChooser.APPROVE_OPTION){
                //Crear un objeto File con el archivo elegido
                File archivoElegido = fc.getSelectedFile();
                try {
                        this.rutaArchivo = archivoElegido.getCanonicalPath() + ".xlsx";
                        imprimir(3);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
    }//GEN-LAST:event_jBtnXlsActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jTablaProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaProductoMouseClicked
        if (evt.getClickCount() == 2) {
            int indiceSeleccion = jTablaProducto.convertRowIndexToModel(jTablaProducto.getSelectedRow());
            int indiceArray = (int) modeloProducto.getValueAt(indiceSeleccion, 3);
            ProductoData tmp = (ProductoData) arrayProducto.get(indiceArray);
            int id_producto = tmp.getId_producto();
            for (int i = 0; i < modeloComposicion.getRowCount(); i++){
                if (id_producto == Integer.parseInt(modeloComposicion.getValueAt(i, 3).toString())){
                    JOptionPane.showMessageDialog(this, "Ya se ha elegido este producto", "Atencion", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            String codigo = tmp.getCodigo();
            String nombre = tmp.getNombre();
            int cantidad = 1;
            Object[] fila = {codigo,nombre,cantidad,id_producto};
            modeloComposicion.addRow(fila);
        }
    }//GEN-LAST:event_jTablaProductoMouseClicked

    private void jComboClasiProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboClasiProductoActionPerformed
        try{
            filtraProducto(this.jComboClasiProducto.getSelectedIndex());
        }catch (Exception ex){
            //Solo para iniciar el combo. NO LLENAR!!!
        }
    }//GEN-LAST:event_jComboClasiProductoActionPerformed

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        try{
            ZonaData tmp = (ZonaData) arrayZona.get(this.jComboZona.getSelectedIndex());
            llenaCajas(tmp.getId_zona());
        }catch (Exception ex){
            //Solo para inicializar el combo.
        }
    }//GEN-LAST:event_jComboZonaActionPerformed

    private void jComboCajaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboCajaFocusLost
        if (this.jComboCaja.getSelectedIndex() >= 0){
            this.id_cajaDeposito = (int) arrayIdCajaDeposito.get(this.jComboCaja.getSelectedIndex());
        }else{
            this.id_cajaDeposito = 0;
        }
        consultaComposicion();
    }//GEN-LAST:event_jComboCajaFocusLost

    private void jTablaComposicionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaComposicionMouseClicked
        if (evt.getClickCount() == 2) {
            int indice = jTablaComposicion.convertRowIndexToModel(jTablaComposicion.getSelectedRow());
            modeloComposicion.removeRow(indice);
        }
    }//GEN-LAST:event_jTablaComposicionMouseClicked

    private void jMenuItemCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCargarActionPerformed
        //JOptionPane.showMessageDialog(jTablaProducto, "WARNING.", "Warning", JOptionPane.WARNING_MESSAGE);
        int [] rows = jTablaProducto.getSelectedRows();
        for (int row : rows){
            row = jTablaProducto.convertRowIndexToModel(row);
            HashMap<Object,Object> map = new HashMap<>();
            for (int c = 0; c < modeloProducto.getColumnCount(); c++){
                map.put(Utiles.formattedColumn(modeloProducto.getColumnName(c)),modeloProducto.getValueAt(row, c));
            }
            int id_producto = (int) map.get("id_producto");
            for (int i = 0; i < modeloComposicion.getRowCount(); i++){
                if (id_producto == (int) modeloComposicion.getValueAt(i, Utiles.getColumnByName(jTablaComposicion, "id_producto"))){
                    JOptionPane.showMessageDialog(this, "Ya se ha elegido este producto", "Atencion", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            map.put("cantidad",1);
            modeloComposicion.addRow(Utiles.fillTableObject(modeloComposicion, map));
        }       
    }//GEN-LAST:event_jMenuItemCargarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jBtnXls;
    private javax.swing.JComboBox jComboCaja;
    private javax.swing.JComboBox jComboClasiProducto;
    private javax.swing.JComboBox jComboZona;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuItem jMenuItemCargar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPopupMenu jPopupMenuProductos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private ar.com.bosoft.RenderTablas.RXTable jTablaComposicion;
    private javax.swing.JTable jTablaProducto;
    private javax.swing.JTextField jTxtBuscaProducto;
    // End of variables declaration//GEN-END:variables
}
