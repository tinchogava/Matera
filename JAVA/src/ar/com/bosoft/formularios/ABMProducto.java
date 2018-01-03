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
import ar.com.bosoft.conexion.ActiveDatabase;
import ar.com.bosoft.conexion.Conexion;
import ar.com.matera.TableModels.ABMProductoTableModel;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import matera.TableObjects.ProductoTableObject;
import matera.cache.CacheCotizaciones;
import matera.cache.CacheProductos;
import matera.db.model.Producto;
import matera.services.HistoricalPriceProductService;
import matera.gui.combobox.ComboBoxMgr;
import matera.gui.combobox.GlazedAutocomplete;
import matera.jooq.tables.records.ClasiproductoRecord;
import matera.jooq.tables.records.CodconsumoRecord;
import matera.jooq.tables.records.MonedaRecord;
import matera.jooq.tables.records.ProductoRecord;
import matera.jooq.tables.records.ProveedorRecord;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class ABMProducto extends javax.swing.JInternalFrame {
    Conexion conexion;
    ABMProductoTableModel modelo;
    TableRowSorter sorter;
    String empresa, rutaArchivo, impresora;
    int id_empresa, id_producto, copias;
    SeleccionImp seleccionImp;
    List<ProductoTableObject> productosList;
    Producto productoSeleccionado;
    
    /**
     * Creates new form AltaUsuario
     * @param conexion
     * @param empresa
     * @param id_empresa
     */
    public ABMProducto(Conexion conexion, int id_empresa, String empresa){
        this.conexion = conexion;
        this.empresa = empresa;
        this.id_empresa = id_empresa;
        this.seleccionImp = new SeleccionImp(null, true);
        
        initComponents();
        
        modelo = new ABMProductoTableModel();
        jTabla.setModel(modelo);
        sorter = new TableRowSorter(jTabla.getModel());
        jTabla.setRowSorter (sorter);
        
        ComboBoxMgr.loadCodConsumoCombo(jComboCodConsumo);
        ComboBoxMgr.loadMonedaCombo(jComboMoneda);
        ComboBoxMgr.loadClasiProductoCombo(jComboClasiProducto, false);
        ComboBoxMgr.loadClasiProductoCombo(jComboClasiProductoFiltro, true);
        ComboBoxMgr.loadProveedorCombo(jComboProveedor);
        ComboBoxMgr.loadHabilitaCombo(jComboHabilita, false);
        
        disableBtnSearchHistoricalPriceProduct();
        
        limpia();
        consulta();
        setJTexFieldChanged(jTxtBusca);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        productosMenu = new javax.swing.JPopupMenu();
        sinTrazabilidadMenuItem = new javax.swing.JMenuItem();
        trazabilidadLoteMenuItem = new javax.swing.JMenuItem();
        trazabilidadSerieMenuItem = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboClasiProductoFiltro = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jBtnLimpiar = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();
        jLblId_Producto = new javax.swing.JLabel();
        jTxtCodigo = new javax.swing.JTextField();
        jTxtCodigo.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(25,true));
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTxtNombre = new javax.swing.JTextField();
        jTxtNombre.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(255,true));
        jLabel3 = new javax.swing.JLabel();
        jComboCodConsumo = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jTxtPm = new javax.swing.JTextField();
        jTxtPm.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel4 = new javax.swing.JLabel();
        jComboMoneda = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jFmtCotizacion = new ar.com.bosoft.formatosCampos.Decimal3(true);
        jLabel24 = new javax.swing.JLabel();
        jFmtCosto = new ar.com.bosoft.formatosCampos.Decimal3(true);
        jLabel25 = new javax.swing.JLabel();
        jFmtPesos = new ar.com.bosoft.formatosCampos.Decimal3(true);
        jLabel9 = new javax.swing.JLabel();
        jFmtStockMinimo = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        jComboClasiProducto = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        jComboProveedor = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jComboHabilita = new javax.swing.JComboBox();
        jTextGtin = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        trazabilidadCombo = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        checkBoxHabilitados = new javax.swing.JCheckBox();
        jBtnHistoricoPrecio = new javax.swing.JButton();

        sinTrazabilidadMenuItem.setText("No trazar productos seleccionados...");
        sinTrazabilidadMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sinTrazabilidadMenuItemActionPerformed(evt);
            }
        });
        productosMenu.add(sinTrazabilidadMenuItem);

        trazabilidadLoteMenuItem.setText("Trazar por lote productos seleccionados...");
        trazabilidadLoteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trazabilidadLoteMenuItemActionPerformed(evt);
            }
        });
        productosMenu.add(trazabilidadLoteMenuItem);

        trazabilidadSerieMenuItem.setText("Trazar por lote y serie productos seleccionados...");
        trazabilidadSerieMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trazabilidadSerieMenuItemActionPerformed(evt);
            }
        });
        productosMenu.add(trazabilidadSerieMenuItem);

        setTitle("Productos");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setFocusable(false);

        jTxtBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaFocusGained(evt);
            }
        });

        jLabel6.setText("Buscar");

        jComboClasiProductoFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboClasiProductoFiltroActionPerformed(evt);
            }
        });

        jLabel17.setText("Clasificación");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboClasiProductoFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jComboClasiProductoFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel7.setText("(*)Datos Obligatorios");

        jBtnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/limpiar.png"))); // NOI18N
        jBtnLimpiar.setText("Limpiar");
        jBtnLimpiar.setBorderPainted(false);
        jBtnLimpiar.setContentAreaFilled(false);
        jBtnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        jBtnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        jBtnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
                "codigo", "GTIN", "nombre", "costo", "moneda", "clasificacion", "PM", "habilitado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabla.setComponentPopupMenu(productosMenu);
        jTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTabla);

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Salida"));

        jBtnScr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/pantalla.png"))); // NOI18N
        jBtnScr.setBorder(null);
        jBtnScr.setBorderPainted(false);
        jBtnScr.setContentAreaFilled(false);
        jBtnScr.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        jBtnImp.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        jBtnPdf.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        jBtnXls.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        jLblId_Producto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblId_Producto.setText("---");

        jLabel13.setText("* Código");

        jLabel1.setText("* Nombre");

        jLabel3.setText("* Código de Consumo");

        jComboCodConsumo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboCodConsumoMouseClicked(evt);
            }
        });

        jLabel14.setText("* PM");

        jLabel4.setText("* Moneda");

        jLabel8.setText("Cotización");

        jFmtCotizacion.setEditable(false);
        jFmtCotizacion.setFocusable(false);

        jLabel24.setText("Costo");

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("=");
        jLabel25.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jFmtPesos.setEditable(false);
        jFmtPesos.setFocusable(false);

        jLabel9.setText("Stock mínimo");

        jLabel16.setText("* Clasificación");

        jComboClasiProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboClasiProductoActionPerformed(evt);
            }
        });

        jLabel26.setText("*Proveedor");

        jComboProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProveedorMouseClicked(evt);
            }
        });
        jComboProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboProveedorKeyReleased(evt);
            }
        });

        jLabel5.setText("* Habilitado");

        jTextGtin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextGtinActionPerformed(evt);
            }
        });

        jLabel2.setText("GTIN");

        trazabilidadCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sin trazabilidad", "Por Lote", "Por Lote + Serie", " " }));

        jLabel10.setText("*Trazabilidad");

        checkBoxHabilitados.setSelected(true);
        checkBoxHabilitados.setText("Mostrar solo Habilitados");
        checkBoxHabilitados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxHabilitadosActionPerformed(evt);
            }
        });

        jBtnHistoricoPrecio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/table.png"))); // NOI18N
        jBtnHistoricoPrecio.setText("Historico Precios");
        jBtnHistoricoPrecio.setActionCommand("HistoricoPrecios");
        jBtnHistoricoPrecio.setBorderPainted(false);
        jBtnHistoricoPrecio.setContentAreaFilled(false);
        jBtnHistoricoPrecio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnHistoricoPrecio.setFocusPainted(false);
        jBtnHistoricoPrecio.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/table.png"))); // NOI18N
        jBtnHistoricoPrecio.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/table.png"))); // NOI18N
        jBtnHistoricoPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnHistoricoPrecioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabel9)
                        .addGap(17, 17, 17)
                        .addComponent(jFmtStockMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(113, 113, 113)
                                .addComponent(jLabel24)
                                .addGap(25, 25, 25)
                                .addComponent(jFmtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(jFmtPesos, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(trazabilidadCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addComponent(jLabel13)
                                .addGap(24, 24, 24)
                                .addComponent(jTxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68)
                                .addComponent(jLabel2)
                                .addGap(12, 12, 12)
                                .addComponent(jTextGtin, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnHistoricoPrecio)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(jLblId_Producto))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jLabel1)
                        .addGap(23, 23, 23)
                        .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel3)
                        .addGap(12, 12, 12)
                        .addComponent(jComboCodConsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jLabel14)
                        .addGap(29, 29, 29)
                        .addComponent(jTxtPm, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jLabel4)
                        .addGap(24, 24, 24)
                        .addComponent(jComboMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel8)
                        .addGap(12, 12, 12)
                        .addComponent(jFmtCotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel16)
                        .addGap(21, 21, 21)
                        .addComponent(jComboClasiProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel26)
                        .addGap(12, 12, 12)
                        .addComponent(jComboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 885, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(307, 307, 307)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(312, 312, 312)
                        .addComponent(jBtnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(jLabel5))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel7)))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkBoxHabilitados)
                                .addGap(101, 101, 101)
                                .addComponent(jBtnGuardar)
                                .addGap(323, 323, 323)
                                .addComponent(jBtnLimpiar))
                            .addComponent(jComboHabilita, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLblId_Producto)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextGtin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel2))))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel1))
                    .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel3))
                    .addComponent(jComboCodConsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel14))
                    .addComponent(jTxtPm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFmtCotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8))))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFmtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFmtPesos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(trazabilidadCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(jBtnHistoricoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel9))
                    .addComponent(jFmtStockMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboClasiProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel26))))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel5))
                    .addComponent(jComboHabilita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jBtnGuardar)
                            .addComponent(jBtnLimpiar))
                        .addGap(6, 6, 6)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jBtnSalir))))
                    .addComponent(checkBoxHabilitados)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    

    private void limpia(){
        this.jLblId_Producto.setText("---");
        this.jTxtCodigo.setText("");
        this.jTextGtin.setText("");
        this.jTxtNombre.setText("");
        this.jComboCodConsumo.setSelectedIndex(-1);
        this.jTxtPm.setText("");
        this.jComboMoneda.setSelectedIndex(-1);
        this.jFmtCotizacion.setValue(0.000);
        this.jFmtCosto.setValue(0.000);
        this.jFmtPesos.setValue(0.000);
        this.jFmtStockMinimo.setValue(0);
        this.jComboClasiProducto.setSelectedIndex(-1);
        this.jComboProveedor.setSelectedIndex(-1);
        this.jComboHabilita.setSelectedItem("SI");
        trazabilidadCombo.setSelectedIndex(-1);
        
        this.jTxtBusca.setText("");
        this.jComboClasiProductoFiltro.setSelectedIndex(this.jComboClasiProductoFiltro.getSelectedIndex());
        this.id_producto = 0;
        
        disableBtnSearchHistoricalPriceProduct();
    }
    
    private void consulta(){
        productosList = CacheProductos.instance().getProductoTableObjectList(checkBoxHabilitados.isSelected());
        this.jComboClasiProductoFiltroActionPerformed(null);
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
          sorter.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTabla.setRowSorter(sorter);
        }
    }

    private void calcula(){
        BigDecimal cotizacion = new BigDecimal(Double.parseDouble(this.jFmtCotizacion.getValue().toString()));
        BigDecimal costo = new BigDecimal(Double.parseDouble(this.jFmtCosto.getValue().toString()));
        BigDecimal pesos = cotizacion.multiply(costo);
        this.jFmtPesos.setValue(pesos);
    }
    
    private boolean validaObligatorios(){
        return !this.jTxtCodigo.getText().isEmpty() &&
               //!this.jTextGtin.getText().isEmpty() &&
               !this.jTxtNombre.getText().isEmpty() &&
               this.jComboCodConsumo.getSelectedIndex() >= 0 &&
               !this.jTxtPm.getText().isEmpty() &&
               this.jComboMoneda.getSelectedIndex() >= 0 &&
               //Double.parseDouble(this.jFmtCosto.getValue().toString()) > 0.000 &&
               this.jComboClasiProducto.getSelectedIndex() >= 0 &&
               this.jComboProveedor.getSelectedIndex() >= 0;
    }
    
    private boolean existeProducto(){
        String codigo = this.jTxtCodigo.getText();
        return modelo.findProductoByCodigo(codigo) != null;
    }
    
    private void imprimir (int salida){
        Map param = new HashMap();
        param.put("empresa", this.empresa);
        param.put("clasificacion", this.jComboClasiProductoFiltro.getSelectedItem().toString());

        Reporte reporte = new Reporte();
        reporte.startReport("Productos", param, new JRTableModelDataSource(modelo), salida, rutaArchivo, impresora, copias);
    }
    
    private void actualizarTrazabilidad(Integer trazabilidad){        
        int[] selectedRows = jTabla.getSelectedRows();
        for (int i=0; i<selectedRows.length;i++){
            ProductoTableObject p = modelo.getRow(jTabla.convertRowIndexToModel(selectedRows[i]));
            p.getProducto().setTrazabilidad(trazabilidad);
            p.getProducto().attach(ActiveDatabase.getDSLContext().configuration());
            p.getProducto().update();
        }
        //limpia();
    }
    
    private void disableBtnSearchHistoricalPriceProduct(){
        this.jBtnHistoricoPrecio.setEnabled(false);
        this.productoSeleccionado = null;
    }
    
    private void enableBtnSearchHistoricalPriceProduct(Long productId, String productName, String productCode){
        productoSeleccionado = new Producto();
        productoSeleccionado.setId(productId);
        productoSeleccionado.setNombre(productName);
        productoSeleccionado.setCodigo(productCode);
        this.jBtnHistoricoPrecio.setEnabled(true);
    }
    
    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
        limpia();
        this.jTxtCodigo.requestFocus();
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios()){
                disableBtnSearchHistoricalPriceProduct();
                Integer id = this.id_producto > 0 ? this.id_producto : null;
                
                CodconsumoRecord codConsumo = (CodconsumoRecord) jComboCodConsumo.getSelectedItem();
                MonedaRecord moneda = (MonedaRecord) jComboMoneda.getSelectedItem();
                ClasiproductoRecord clasi = (ClasiproductoRecord) jComboClasiProducto.getSelectedItem();
                ProveedorRecord prov = (ProveedorRecord) jComboProveedor.getSelectedItem();
                ProductoRecord p = new ProductoRecord();
                if (id != null){
                    ProductoTableObject pto = modelo.getRow(jTabla.convertRowIndexToModel(jTabla.getSelectedRow()));
                    p = pto.getProducto();
                }
                   
                p.setIdProducto(id);
                p.setCodigo(this.jTxtCodigo.getText().trim());
                p.setGtin(this.jTextGtin.getText());
                p.setNombre(this.jTxtNombre.getText());
                p.setIdCodconsumo(codConsumo.getIdCodconsumo());
                p.setIdMoneda(moneda.getIdMoneda());
                BigDecimal precioActual = p.getCosto();
                p.setCosto(new BigDecimal(Double.parseDouble(this.jFmtCosto.getValue().toString())));
                p.setStockminimo(Integer.parseInt(this.jFmtStockMinimo.getValue().toString()));
                p.setIdClasiproducto(clasi.getIdClasiproducto());
                p.setPm(this.jTxtPm.getText());
                p.setIdProveedor(prov.getIdProveedor());
                p.setHabilita(this.jComboHabilita.getSelectedItem().toString().substring(0, 1));
                p.setIdEmpresa(Principal.getIdEmpresa());
                p.setTrazabilidad(trazabilidadCombo.getSelectedIndex());

                try{
                    p.attach(ActiveDatabase.getDSLContext().configuration());
                    if (id != null){
                        p.update();
                        HistoricalPriceProductService.create(p.getIdProducto() ,precioActual, p.getCosto());
                        
                        Utiles.showMessage("Producto Actualizado correctamente.");
                    }
                    else {
                        if (existeProducto()){
                            Utiles.showMessage("Ya existe un producto con el codigo: " + this.jTxtCodigo.getText().trim());
                            return;
                        }                        
                        p.insert();
                        Utiles.showMessage("Nuevo producto guardado correctamente.");
                    }
                    //limpia();
                    CacheProductos.instance().fetch();
                    consulta();
                }
                catch(Exception e){
                    Utiles.showErrorMessage(e);
                }
        }else{
            JOptionPane.showMessageDialog(this,"Complete todos los datos obligatorios (*)", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaMouseClicked
        
        if (SwingUtilities.isRightMouseButton(evt)){
               productosMenu.show();
               return;
        }        
        int fila = jTabla.convertRowIndexToModel (jTabla.getSelectedRow());
        ProductoTableObject pto = modelo.getRow(fila);
        ProductoRecord p = pto.getProducto();
        this.id_producto = p.getIdProducto();
        this.jLblId_Producto.setText(p.getIdProducto().toString());
        this.jTxtCodigo.setText(p.getCodigo());
        this.jTextGtin.setText(p.getGtin());
        this.jTxtNombre.setText(p.getNombre());
        
        //this.jComboCodConsumo.setSelectedIndex(-1);
        this.jComboCodConsumo.setSelectedItem(pto.getCodConsumo());
        
        this.jTxtPm.setText(p.getPm());
        
        //this.jComboMoneda.setSelectedIndex(-1);
        this.jComboMoneda.setSelectedItem(pto.getMoneda());
        if (pto.getMoneda().getIdMoneda().equals(1))
            this.jFmtCotizacion.setValue(1.00);
        else
            this.jFmtCotizacion.setValue(CacheCotizaciones.instance().getCotizacionActual());
        
        this.jFmtCosto.setValue(p.getCosto());
        calcula();
        
        this.jFmtStockMinimo.setValue(p.getStockminimo());
        
        //this.jComboClasiProducto.setSelectedIndex(-1);
        this.jComboClasiProducto.setSelectedItem(pto.getClasiProducto());
        
        this.jComboProveedor.setSelectedItem(pto.getProveedor());
        
        this.jComboHabilita.setSelectedItem(p.getHabilita().equals("S") ? "SI" : "NO");
        
        this.trazabilidadCombo.setSelectedIndex(p.getTrazabilidad());
        this.jTxtCodigo.requestFocus();

        enableBtnSearchHistoricalPriceProduct(new Long(p.getIdProducto()), p.getNombre(), p.getCodigo());
    }//GEN-LAST:event_jTablaMouseClicked

    private void jTxtBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaFocusGained
        //this.jTxtBusca.select(0, this.jTxtBusca.getText().length());
    }//GEN-LAST:event_jTxtBuscaFocusGained

    private void jBtnScrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScrActionPerformed
        if (modelo.getRowCount() > 0){
            imprimir(0);
        }
    }//GEN-LAST:event_jBtnScrActionPerformed

    private void jBtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImpActionPerformed
        if(modelo.getRowCount() > 0){
            seleccionImp.setVisible(true);
            if (seleccionImp.getSino()){
                this.impresora = seleccionImp.getImpresora();
                imprimir(1);
            }
        }
    }//GEN-LAST:event_jBtnImpActionPerformed

    private void jBtnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPdfActionPerformed
        if (modelo.getRowCount() > 0){
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
        if (modelo.getRowCount() > 0){
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

    private void jComboProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboProveedorKeyReleased
        /*
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboProveedor.setSelectedIndex(-1);
        }
        */
    }//GEN-LAST:event_jComboProveedorKeyReleased

    private void jComboClasiProductoFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboClasiProductoFiltroActionPerformed
        try{
            if (productosList != null){
                modelo.removeAllRows();
                modelo.fireTableDataChanged();
                
                ClasiproductoRecord c = (ClasiproductoRecord) this.jComboClasiProductoFiltro.getSelectedItem() != null ?
                        (ClasiproductoRecord) this.jComboClasiProductoFiltro.getSelectedItem() :
                        (ClasiproductoRecord) this.jComboClasiProductoFiltro.getItemAt(0);

                productosList.forEach(p->{
                    if (c.getIdClasiproducto().equals(p.getProducto().getIdClasiproducto())) {
                        modelo.addRow(p);
                    }else if (c.getIdClasiproducto().equals(Utiles.TODOS)){
                        modelo.addRow(p);
                    }              
                });
                
            }
            /*
            Iterator i = arrayProducto.iterator();
            
            int indice = 0;
            while (i.hasNext()){
                ProductoData tmp = (ProductoData) i.next();
                String codigo = tmp.getCodigo();
                String gtin = tmp.getGtin();
                String nombre = tmp.getNombre();
                String simbolo = tmp.getMoneda();
                Double costo = tmp.getCosto();
                int stockMinimo = tmp.getStockMinimo();
                String clasificacion = tmp.getClasiProducto();
                String pm = tmp.getPm();
                String habilita = (tmp.getHabilita().equals("S") ? "SI" : "NO");
                String proveedor = tmp.getProveedor();
                String codConsumo = tmp.getCodConsumo();

                Object[] fila = {codigo, gtin,nombre,simbolo,costo,stockMinimo,
                                    clasificacion,pm,habilita,indice,proveedor,
                                    codConsumo};

                if (this.jComboClasiProductoFiltro.getSelectedIndex() == 0) {
                    modelo.addRow(fila);
                }else if (this.jComboClasiProductoFiltro.getSelectedItem().toString().equals(clasificacion)) {
                    modelo.addRow(fila);
                }
                indice++;
            }
                    */
        }catch (Exception ex){
            Utiles.showErrorMessage(ex);
            //Solo para inicializar el combo
        }
        
    }//GEN-LAST:event_jComboClasiProductoFiltroActionPerformed

    private void jComboProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProveedorMouseClicked
        /*
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Proveedor");
            Iterator it = this.arrayProveedor.iterator();
            while (it.hasNext()) {
                ProveedorData tmp = (ProveedorData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboProveedor.setSelectedItem(Principal.general1.getResultado());
            }
        }
                */
    }//GEN-LAST:event_jComboProveedorMouseClicked

    private void jComboCodConsumoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboCodConsumoMouseClicked
        /*
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Código de consumo");
            Iterator it = this.arrayCodConsumo.iterator();
            while (it.hasNext()) {
                CodConsumoData tmp = (CodConsumoData) it.next();
                Object[] fila = {tmp.getNombre() + " (" + tmp.getCodigo() + ")"};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboCodConsumo.setSelectedItem(Principal.general1.getResultado());
            }
        }
                */
    }//GEN-LAST:event_jComboCodConsumoMouseClicked

    private void jTextGtinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextGtinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextGtinActionPerformed

    private void sinTrazabilidadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sinTrazabilidadMenuItemActionPerformed
        actualizarTrazabilidad(Utiles.TRAZABILIDAD.SIN_TRAZABILIDAD);
    }//GEN-LAST:event_sinTrazabilidadMenuItemActionPerformed

    private void trazabilidadLoteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trazabilidadLoteMenuItemActionPerformed
        actualizarTrazabilidad(Utiles.TRAZABILIDAD.LOTE);
    }//GEN-LAST:event_trazabilidadLoteMenuItemActionPerformed

    private void trazabilidadSerieMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trazabilidadSerieMenuItemActionPerformed
        actualizarTrazabilidad(Utiles.TRAZABILIDAD.LOTE_SERIE);
    }//GEN-LAST:event_trazabilidadSerieMenuItemActionPerformed

    private void checkBoxHabilitadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxHabilitadosActionPerformed
        consulta();
    }//GEN-LAST:event_checkBoxHabilitadosActionPerformed


    private void jComboClasiProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboClasiProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboClasiProductoActionPerformed

    private void jBtnHistoricoPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnHistoricoPrecioActionPerformed
        HistoricoPreciosProducto hpp = new HistoricoPreciosProducto(productoSeleccionado);
        Principal.dp.add(hpp);
        hpp.toFront();
        hpp.setVisible(true);
   
    }//GEN-LAST:event_jBtnHistoricoPrecioActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox checkBoxHabilitados;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnHistoricoPrecio;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnLimpiar;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jBtnXls;
    private javax.swing.JComboBox jComboClasiProducto;
    private javax.swing.JComboBox jComboClasiProductoFiltro;
    private javax.swing.JComboBox jComboCodConsumo;
    private javax.swing.JComboBox jComboHabilita;
    private javax.swing.JComboBox jComboMoneda;
    private javax.swing.JComboBox jComboProveedor;
    private javax.swing.JFormattedTextField jFmtCosto;
    private javax.swing.JFormattedTextField jFmtCotizacion;
    private javax.swing.JFormattedTextField jFmtPesos;
    private javax.swing.JFormattedTextField jFmtStockMinimo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblId_Producto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTabla;
    private javax.swing.JTextField jTextGtin;
    private javax.swing.JTextField jTxtBusca;
    private javax.swing.JTextField jTxtCodigo;
    private javax.swing.JTextField jTxtNombre;
    private javax.swing.JTextField jTxtPm;
    private javax.swing.JPopupMenu productosMenu;
    private javax.swing.JMenuItem sinTrazabilidadMenuItem;
    private javax.swing.JComboBox trazabilidadCombo;
    private javax.swing.JMenuItem trazabilidadLoteMenuItem;
    private javax.swing.JMenuItem trazabilidadSerieMenuItem;
    // End of variables declaration//GEN-END:variables
}
