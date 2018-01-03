/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.RenderTablas.DateCellEditor;
import ar.com.bosoft.clases.TableHeaderMouseListener;
import ar.com.bosoft.clases.Utiles;
import ar.com.matera.TableModels.ProductoTableModel;
import ar.com.matera.TableModels.RemitoDetalleTableModel;
import com.toedter.calendar.JDateChooser;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import matera.TableObjects.CargaProductoTableObject;
import matera.TableObjects.ProductoTableObject;
import matera.TableObjects.RemitoDetalleTableObject;
import matera.cache.CacheClasiProducto;
import matera.cache.CacheComposicionCaja;
import matera.cache.CacheCotizaciones;
import matera.cache.CacheProductos;
import matera.gui.CargaProductos;
import matera.gui.helper.ExcelAdapter;
import matera.gui.renderers.TrazabilidadRenderer;
import matera.helpers.RemitoHelper;
import matera.jooq.Tables;
import matera.jooq.tables.records.ClasiproductoRecord;
import matera.jooq.tables.records.ProductoRecord;
import matera.jooq.tables.records.StockdetalleRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author h2o
 */
public class DetalleCajaRemito extends javax.swing.JDialog {
    RemitoDetalleTableModel modelo;
    ProductoTableModel productoModel;
    DefaultTableModel cantidadModel;
    String caja;
    Integer id_zona;
    
    TableRowSorter sorterProducto;
    JTableHeader header;
    TableHeaderMouseListener tableHeaderMouseListener;
    
    protected Result<Record> productos;
    
    public DetalleCajaRemito(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Detalle de cajas");
        initComponents();
        
        modelo = new RemitoDetalleTableModel();
        modelo.getPropertiesFromDefaultModel(this.jTablaComposicion.getModel());
        this.jTablaComposicion.setModel(modelo);
        this.jTablaComposicion.setDefaultRenderer(Object.class, new TrazabilidadRenderer());
        
        productoModel = new ProductoTableModel();
        productoModel.getPropertiesFromDefaultModel(this.productosTable.getModel());
        productosTable.setModel(productoModel);
        sorterProducto = new TableRowSorter(productoModel);
        productosTable.setRowSorter (sorterProducto);
        header = productosTable.getTableHeader();
        tableHeaderMouseListener = new TableHeaderMouseListener(productosTable);
        header.addMouseListener(this.tableHeaderMouseListener);        
        
        cantidadModel = (DefaultTableModel) this.cantidadTable.getModel();
        cantidadTable.setModel(cantidadModel);
        
        TableColumn dateCol = jTablaComposicion.getColumnModel().getColumn(Utiles.getColumnByName(jTablaComposicion, "vencimiento"));
        JDateChooser date = new JDateChooser("dd/MM/20yy", "##/##/####", '_');
        date.setMinSelectableDate(new Date());
        dateCol.setCellEditor(new DateCellEditor(date));
        loadClasiProductoCombo();
        setLocationRelativeTo(null);
        consultaProducto();
        setJTexFieldChanged(buscarField);
        ExcelAdapter adapter = new ExcelAdapter(jTablaComposicion);
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
            bucarFieldChanged();
        }else if (type.equals(DocumentEvent.EventType.REMOVE)){
            bucarFieldChanged();
        }
    }    
    
    private void bucarFieldChanged(){
        String text = buscarField.getText().trim().replaceAll("'", "´");
        if (text.length() == 0) {
          sorterProducto.setRowFilter(null);
        } else {
          sorterProducto.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*", this.tableHeaderMouseListener.getColumna()));
          productosTable.setRowSorter(sorterProducto);
        }
    }    
    
    protected void consultaProducto(){
        productos = CacheProductos.instance().getHabilitados();
    }     
    
    public void setIdZona(Integer id_zona){
        this.id_zona = id_zona;
    }
    
    private void loadClasiProductoCombo(){
        this.clasiProductoCombo.addItem("-- TODOS --");
        
        Result<Record> clasiProductos = CacheClasiProducto.instance().get();
        clasiProductos.forEach(c ->{
            this.clasiProductoCombo.addItem(c.into(Tables.CLASIPRODUCTO).getNombre());
        });
        clasiProductoCombo.setSelectedIndex(-1);
    }    

    public void setNombreCaja(String caja){
        this.jLblCaja.setText(caja);
    }
    
    public void limpia(){
        modelo.removeAllRows();
        modelo.fireTableDataChanged();
        
        cantidadModel.getDataVector().removeAllElements();
        cantidadModel.fireTableDataChanged();
    }
    
    public RemitoDetalleTableModel getModel(){
        return this.modelo;
    }
    
    public void loadCantidadTable(){
        cantidadModel.getDataVector().removeAllElements();

        RemitoHelper remitoHelper = new RemitoHelper();
        List<RemitoDetalleTableObject> list = modelo.getRowsAsList();
        if (list.isEmpty())
            return;
        
        Map<String, Long> treeMap = new TreeMap<>(remitoHelper.getProductCounts(list));
        remitoHelper.sortByCodigo(list);
        treeMap.forEach((k,v)->{
            Vector vec = new Vector();
            vec.add(k);
            vec.add(v);
            cantidadModel.addRow(vec);            
        });
        cantidadModel.fireTableDataChanged();
    }
    
    // Muestra el Dialog, llena la tabla y devuelve el contenido al guardar
    public List<RemitoDetalleTableObject> getDatosComposicion(Integer id_cajaDeposito, String nombreCaja, Integer id_zona){
        this.limpia();
        
        setIdZona(id_zona);
        List<RemitoDetalleTableObject> productosCaja = getRemitoDetalleTableObjects(id_cajaDeposito, id_zona);
        
        productosCaja.forEach(p->{
            modelo.addRow(p);
        });
        
        loadCantidadTable();
        
        this.setNombreCaja(nombreCaja);
        this.setVisible(true);

        return modelo.getRowsAsList();
    }
    /**
     * Metodo utilizado cuando ya se ha generado la lista de productos con anterioridad
     * @param list
     * @param nombreCaja
     * @param id_zona
     * @return 
     */
    public List<RemitoDetalleTableObject> getDatosComposicion(List<RemitoDetalleTableObject> list, String nombreCaja, Integer id_zona){
        this.limpia();
        
        setIdZona(id_zona);
        List<RemitoDetalleTableObject> productosCaja = list;
        
        productosCaja.forEach(p->{
            modelo.addRow(p);
        });
        
        loadCantidadTable();
        
        this.setNombreCaja(nombreCaja);
        this.setVisible(true);

        return modelo.getRowsAsList();
    }
    
    public List<RemitoDetalleTableObject> getRemitoDetalleTableObjects(Integer id_cajaDeposito, Integer id_zona){
        List<RemitoDetalleTableObject> rdoList = new ArrayList<>();
        try {
            Result<Record> records = CacheComposicionCaja.instance().getComposicionCaja(id_cajaDeposito);
            
            if (records==null || records.isEmpty())
                return rdoList;
            
            BigDecimal cotizacionActual = CacheCotizaciones.instance().getCotizacionActual();
            
            records.forEach(r ->{
                Integer cantidad = r.into(Tables.CAJACOMPOSICION).getCantidad();
                for (int i = 0; i < cantidad; i++){
                    ProductoRecord producto = r.into(Tables.PRODUCTO);
                    ClasiproductoRecord clasiProducto = r.into(Tables.CLASIPRODUCTO);
                    StockdetalleRecord stockdetalle = new StockdetalleRecord();
                    stockdetalle.setIdProducto(producto.getIdProducto());
                    stockdetalle.setCantidad(1);
                    stockdetalle.setDc("C");
                    stockdetalle.setLote("");
                    stockdetalle.setSerie("");
                    stockdetalle.setIdMoneda(producto.getIdMoneda());
                    stockdetalle.setIdZona(id_zona);
                    stockdetalle.setPm(producto.getPm());
                    BigDecimal cotizacion = producto.getIdMoneda() > 1 ? cotizacionActual : new BigDecimal(BigInteger.ONE);
                    stockdetalle.setCostopesos(producto.getCosto().multiply(cotizacion));
                    stockdetalle.setCotizacion(cotizacionActual);
                    rdoList.add(new RemitoDetalleTableObject( producto, clasiProducto, stockdetalle));
                }
            });
            
        } catch (Exception ex) {
            Utiles.showErrorMessage(ex);
        }
        return rdoList;
    }
    
    public void loadSeleccion(List<CargaProductoTableObject> list){
        list.forEach(p -> {
            for (int i=0; i < p.getCantidad(); i++){
                RemitoDetalleTableObject rdo = new RemitoDetalleTableObject(p.getProducto(), p.getClasiProducto(), id_zona);
                modelo.addRow(rdo);
            }
        });
        loadCantidadTable();
    }
    
    protected void filtraProducto(String clasificacion){
        productoModel.removeAllRows();
        productoModel.fireTableDataChanged();
        
        if (productos != null){
            productos.forEach(r ->{
                ClasiproductoRecord clasiProductoRecord = r.into(Tables.CLASIPRODUCTO);
                if (!clasificacion.equals("-- TODOS --")){
                    if (clasificacion.equals(clasiProductoRecord.getNombre())){
                        ProductoTableObject productoTableObject = new ProductoTableObject(r.into(Tables.PRODUCTO), clasiProductoRecord, r.into(Tables.MONEDA));
                        productoModel.addRow(productoTableObject);
                    }
                }
                else{
                    ProductoTableObject productoTableObject = new ProductoTableObject(r.into(Tables.PRODUCTO), r.into(Tables.CLASIPRODUCTO), r.into(Tables.MONEDA));
                    productoModel.addRow(productoTableObject);
                }
            });        
        }

    }    
       
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new javax.swing.JPopupMenu();
        jMenuItemRemover = new javax.swing.JMenuItem();
        productoMenu = new javax.swing.JPopupMenu();
        agregarProductos = new javax.swing.JMenuItem();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaComposicion = new ar.com.bosoft.RenderTablas.RXTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        productosTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        cantidadTable = new javax.swing.JTable();
        buscarField = new javax.swing.JTextField();
        clasiProductoCombo = new javax.swing.JComboBox();
        jBtnGuardar = new javax.swing.JButton();
        jLblCaja = new javax.swing.JLabel();

        jMenuItemRemover.setText("Remover Seleccion...");
        jMenuItemRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRemoverActionPerformed(evt);
            }
        });
        menu.add(jMenuItemRemover);

        agregarProductos.setText("Agregar Productos Seleccionados...");
        agregarProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarProductosActionPerformed(evt);
            }
        });
        productoMenu.add(agregarProductos);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel6.setBackground(new java.awt.Color(153, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "* Composición"));

        jTablaComposicion.setAutoCreateRowSorter(true);
        jTablaComposicion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cantidad", "Código", "GTIN", "Nombre", "PM", "Lote", "serie", "vencimiento", "tipo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaComposicion.setCellSelectionEnabled(true);
        jTablaComposicion.setComponentPopupMenu(menu);
        jTablaComposicion.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTablaComposicion);
        if (jTablaComposicion.getColumnModel().getColumnCount() > 0) {
            jTablaComposicion.getColumnModel().getColumn(0).setMinWidth(50);
            jTablaComposicion.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTablaComposicion.getColumnModel().getColumn(0).setMaxWidth(50);
            jTablaComposicion.getColumnModel().getColumn(1).setMinWidth(100);
            jTablaComposicion.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTablaComposicion.getColumnModel().getColumn(1).setMaxWidth(100);
            jTablaComposicion.getColumnModel().getColumn(4).setMinWidth(75);
            jTablaComposicion.getColumnModel().getColumn(4).setPreferredWidth(75);
            jTablaComposicion.getColumnModel().getColumn(4).setMaxWidth(75);
            jTablaComposicion.getColumnModel().getColumn(5).setPreferredWidth(100);
            jTablaComposicion.getColumnModel().getColumn(6).setPreferredWidth(100);
            jTablaComposicion.getColumnModel().getColumn(7).setMinWidth(75);
            jTablaComposicion.getColumnModel().getColumn(7).setPreferredWidth(75);
            jTablaComposicion.getColumnModel().getColumn(7).setMaxWidth(75);
        }

        productosTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "codigo", "nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        productosTable.setComponentPopupMenu(productoMenu);
        jScrollPane3.setViewportView(productosTable);

        cantidadTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "codigo", "cantidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(cantidadTable);

        buscarField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarFieldActionPerformed(evt);
            }
        });

        clasiProductoCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clasiProductoComboActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clasiProductoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnGuardar, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(clasiProductoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnGuardar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLblCaja.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblCaja.setText("...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLblCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(140, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jLblCaja)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(201, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (jTablaComposicion.isEditing())
            jTablaComposicion.getCellEditor().stopCellEditing();        
        this.dispose();
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jMenuItemRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRemoverActionPerformed
        while (jTablaComposicion.getSelectedRowCount() > 0 ) {          
        	modelo.removeRow(jTablaComposicion.convertRowIndexToModel(jTablaComposicion.getSelectedRow()));
        }
        loadCantidadTable();
    }//GEN-LAST:event_jMenuItemRemoverActionPerformed

    private void buscarFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarFieldActionPerformed

    private void agregarProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarProductosActionPerformed
        CargaProductos JDialogCargaProductos = new CargaProductos(null, true);
        List<CargaProductoTableObject> list = new ArrayList<>();
        int[] selectedRows = productosTable.getSelectedRows();
        for (int i=0; i<selectedRows.length;i++){
            ProductoTableObject p = productoModel.getRow(productosTable.convertRowIndexToModel(selectedRows[i]));
            list.add(new CargaProductoTableObject(p.getProducto(), p.getClasiProducto(), p.getMoneda(), 1));
        }
        JDialogCargaProductos.loadSeleccion(list);
        JDialogCargaProductos.setVisible(true);
        if (JDialogCargaProductos.isLoaded())
            loadSeleccion(JDialogCargaProductos.getProductos());
    }//GEN-LAST:event_agregarProductosActionPerformed

    private void clasiProductoComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clasiProductoComboActionPerformed
        try {
            if (clasiProductoCombo.getSelectedIndex() >=0){
                String clasificacion = this.clasiProductoCombo.getSelectedItem().toString();
                filtraProducto(clasificacion);            
            }
        } catch (Exception ex) {
            Utiles.showErrorMessage(ex);
        }
    }//GEN-LAST:event_clasiProductoComboActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem agregarProductos;
    private javax.swing.JTextField buscarField;
    private javax.swing.JTable cantidadTable;
    private javax.swing.JComboBox clasiProductoCombo;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JLabel jLblCaja;
    private javax.swing.JMenuItem jMenuItemRemover;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private ar.com.bosoft.RenderTablas.RXTable jTablaComposicion;
    private javax.swing.JPopupMenu menu;
    private javax.swing.JPopupMenu productoMenu;
    private javax.swing.JTable productosTable;
    // End of variables declaration//GEN-END:variables
}
