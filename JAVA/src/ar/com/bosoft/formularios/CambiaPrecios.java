/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.formularios;

import ar.com.bosoft.buscadores.General1;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ClasiProductoCRUD;
import ar.com.bosoft.crud.ProveedorCRUD;
import ar.com.bosoft.data.ClasiProductoData;
import ar.com.bosoft.data.ProveedorData;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import matera.db.model.Producto;
import matera.services.HistoricalPriceProductService;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class CambiaPrecios extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, escala;
    String empresa, todos;
    
    ClasiProductoCRUD clasiProductoCRUD;
    ProveedorCRUD proveedorCRUD;
    
    ArrayList arrayClasiProducto, arrayProveedor;
    
    DefaultTableModel modeloConsulta, modeloSeleccion;
    TableRowSorter sorterConsulta;
    
    RoundingMode RM = RoundingMode.HALF_UP;
    
    ResultSet rsConsulta;
    General1 general1 = new General1(null, true);
    
    Producto productoSeleccionado;
    
    public CambiaPrecios(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.todos = "-- TODOS --";
        
        this.escala = 2;
        
        this.clasiProductoCRUD = new ClasiProductoCRUD(conexion, empresa);
        this.proveedorCRUD = new ProveedorCRUD(conexion, id_empresa, empresa);
        
        initComponents();
        
        this.modeloConsulta = (DefaultTableModel) this.jTablaConsulta.getModel();
        this.jTablaConsulta.setModel(modeloConsulta);        
        jTablaConsulta.setRowSorter (new TableRowSorter(modeloConsulta));
        sorterConsulta = new TableRowSorter(modeloConsulta);
        
        this.modeloSeleccion = (DefaultTableModel) this.jTablaSeleccion.getModel();
        this.jTablaSeleccion.setModel(modeloSeleccion);

        disableBtnSearchHistoricalPriceProduct();
        
        llenaComboClasiProducto();
        llenaComboProveedor();
        limpia();
        consulta();
        setJTexFieldChanged(jTxtBusca);
    }

    private void llenaComboClasiProducto(){
        this.jComboClasiProducto.addItem(this.todos);
        arrayClasiProducto = clasiProductoCRUD.consulta(true);
        Iterator i = arrayClasiProducto.iterator();
        while (i.hasNext()){
            ClasiProductoData tmp = (ClasiProductoData) i.next();
            this.jComboClasiProducto.addItem(tmp.getNombre());
        }
    }
    
    private void llenaComboProveedor(){
        this.jComboProveedor.addItem(this.todos);
        arrayProveedor = proveedorCRUD.consulta(true);
        Iterator i = arrayProveedor.iterator();
        while (i.hasNext()){
            ProveedorData tmp = (ProveedorData) i.next();
            this.jComboProveedor.addItem(tmp.getNombre());
        }
    }
    
    private void limpia(){
        this.jComboClasiProducto.setSelectedIndex(0);
        this.jComboProveedor.setSelectedIndex(0);
        
        this.jTxtBusca.setText("");        
        
        this.jComboTipo.setSelectedIndex(0);
        this.jComboModo.setSelectedIndex(0);
        this.jFmtValor.setValue(0.00);
        
        this.modeloSeleccion.getDataVector().removeAllElements();
        this.modeloSeleccion.fireTableDataChanged();
        
        disableBtnSearchHistoricalPriceProduct();
    }
    
    private void consulta(){
        try {
            modeloConsulta.getDataVector().removeAllElements();
            modeloConsulta.fireTableDataChanged();
            
            modeloSeleccion.getDataVector().removeAllElements();
            modeloSeleccion.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            
            int id_clasiProducto = 0;
            if (this.jComboClasiProducto.getSelectedIndex() > 0){
                ClasiProductoData tmp = (ClasiProductoData) arrayClasiProducto.get(this.jComboClasiProducto.getSelectedIndex() - 1);
                id_clasiProducto = tmp.getId_clasiProducto();
            }
            
            int id_proveedor = 0;
            if (this.jComboProveedor.getSelectedIndex() > 0){
                ProveedorData tmp = (ProveedorData) arrayProveedor.get(this.jComboProveedor.getSelectedIndex() - 1);
                id_proveedor = tmp.getId_proveedor();
            }
            
            parametros.add(id_clasiProducto);
            parametros.add(id_proveedor);
            parametros.add(this.id_empresa);
            rsConsulta = conexion.procAlmacenado("consultaCambioPrecios", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            rsConsulta.beforeFirst();
            
            while (rsConsulta.next()){
                String codigo = rsConsulta.getString("codigo");
                String nombre = rsConsulta.getString("nombre");
                String moneda = rsConsulta.getString("moneda");
                Double costo = rsConsulta.getDouble("costo");
                int id = rsConsulta.getInt("id_producto");
                
                Object[] fila = {codigo, nombre, moneda, costo, id};
                modeloConsulta.addRow(fila);
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
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
          sorterConsulta.setRowFilter(null);
        } else {
          sorterConsulta.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTablaConsulta.setRowSorter(sorterConsulta);
        }
        disableBtnSearchHistoricalPriceProduct();
    }
    
    private void unoAbajo(){
        if (jTablaConsulta.getSelectedRow() >= 0){
            int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
            Object id = modeloConsulta.getValueAt(fila, 4);
            if (!Utiles.existeEnModelo(modeloSeleccion, 4, id)){
                String codigo = modeloConsulta.getValueAt(fila, 0).toString();
                String nombre = modeloConsulta.getValueAt(fila, 1).toString();
                String moneda = modeloConsulta.getValueAt(fila, 2).toString();
                Double costo = (double) modeloConsulta.getValueAt(fila, 3);
                Double costoActual = costo;
                
                Object[] nuevo = {codigo, nombre, moneda, costo, id, costoActual};
                modeloSeleccion.addRow(nuevo);
            }else{
                JOptionPane.showMessageDialog(this, "Ya se ha seleccionado este producto", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void todosAbajo(){
        todosArriba();
        for (int i = 0; i < modeloConsulta.getRowCount(); i++){
            String codigo = modeloConsulta.getValueAt(i, 0).toString();
            String nombre = modeloConsulta.getValueAt(i, 1).toString();
            String moneda = modeloConsulta.getValueAt(i, 2).toString();
            Double costo = (double) modeloConsulta.getValueAt(i, 3);
            int id = (int) modeloConsulta.getValueAt(i, 4);
            Double costoActual = costo;
            
            Object[] nuevo = {codigo, nombre, moneda, costo, id, costoActual};
            modeloSeleccion.addRow(nuevo);
        }
    }
    
    private void unoArriba(){
        if (jTablaSeleccion.getSelectedRow() >= 0){
            int fila = jTablaSeleccion.convertRowIndexToModel(jTablaSeleccion.getSelectedRow());
            modeloSeleccion.removeRow(fila);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void todosArriba(){
        modeloSeleccion.getDataVector().removeAllElements();
        modeloSeleccion.fireTableDataChanged();
    }
    
    private void validateBtnSearchHistorialPriceProduct(){
        if (jTablaConsulta.getSelectedRow() >= 0){
            int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
            Integer id = (Integer)modeloConsulta.getValueAt(fila, 4);
            String codigo = modeloConsulta.getValueAt(fila, 0).toString();
            String nombre = modeloConsulta.getValueAt(fila, 1).toString();
            enableBtnSearchHistoricalPriceProduct(new Long(id), nombre, codigo);
        }else{
            disableBtnSearchHistoricalPriceProduct();
        }
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
        jBtnUnoAbajo = new javax.swing.JButton();
        jBtnUnoArriba = new javax.swing.JButton();
        jBtnTodosAbajo = new javax.swing.JButton();
        jBtnTodosArriba = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboClasiProducto = new javax.swing.JComboBox();
        jComboProveedor = new javax.swing.JComboBox();
        jBtnFiltrar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jComboTipo = new javax.swing.JComboBox();
        jBtnAplicar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboModo = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jFmtValor = new ar.com.bosoft.formatosCampos.Decimal(true);
        jLblSimbolo = new javax.swing.JLabel();
        jBtnGuardar = new javax.swing.JButton();
        jProgressBar = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaSeleccion = new ar.com.bosoft.RenderTablas.RXTable();
        jBtnHistoricoPrecio = new javax.swing.JButton();

        setTitle("Cambio de precios");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setFocusable(false);

        jLabel22.setText("Buscar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)))
        );

        jTablaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "", "Costo actual", "id_producto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaConsulta.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTablaConsulta.getTableHeader().setReorderingAllowed(false);
        jTablaConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaConsultaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTablaConsulta);
        if (jTablaConsulta.getColumnModel().getColumnCount() > 0) {
            jTablaConsulta.getColumnModel().getColumn(0).setMinWidth(130);
            jTablaConsulta.getColumnModel().getColumn(0).setPreferredWidth(130);
            jTablaConsulta.getColumnModel().getColumn(0).setMaxWidth(130);
            jTablaConsulta.getColumnModel().getColumn(2).setMinWidth(60);
            jTablaConsulta.getColumnModel().getColumn(2).setPreferredWidth(60);
            jTablaConsulta.getColumnModel().getColumn(2).setMaxWidth(60);
            jTablaConsulta.getColumnModel().getColumn(3).setMinWidth(90);
            jTablaConsulta.getColumnModel().getColumn(3).setPreferredWidth(90);
            jTablaConsulta.getColumnModel().getColumn(3).setMaxWidth(90);
            jTablaConsulta.getColumnModel().getColumn(4).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        jBtnUnoAbajo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/unoabajo.png"))); // NOI18N
        jBtnUnoAbajo.setBorderPainted(false);
        jBtnUnoAbajo.setContentAreaFilled(false);
        jBtnUnoAbajo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnUnoAbajo.setFocusPainted(false);
        jBtnUnoAbajo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/unoabajo.png"))); // NOI18N
        jBtnUnoAbajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnUnoAbajoActionPerformed(evt);
            }
        });

        jBtnUnoArriba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/unoarriba.png"))); // NOI18N
        jBtnUnoArriba.setBorderPainted(false);
        jBtnUnoArriba.setContentAreaFilled(false);
        jBtnUnoArriba.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnUnoArriba.setFocusPainted(false);
        jBtnUnoArriba.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/unoarriba.png"))); // NOI18N
        jBtnUnoArriba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnUnoArribaActionPerformed(evt);
            }
        });

        jBtnTodosAbajo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/todosabajo.png"))); // NOI18N
        jBtnTodosAbajo.setBorderPainted(false);
        jBtnTodosAbajo.setContentAreaFilled(false);
        jBtnTodosAbajo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnTodosAbajo.setFocusPainted(false);
        jBtnTodosAbajo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/todosabajo.png"))); // NOI18N
        jBtnTodosAbajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTodosAbajoActionPerformed(evt);
            }
        });

        jBtnTodosArriba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/todosarriba.png"))); // NOI18N
        jBtnTodosArriba.setBorderPainted(false);
        jBtnTodosArriba.setContentAreaFilled(false);
        jBtnTodosArriba.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnTodosArriba.setFocusPainted(false);
        jBtnTodosArriba.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/todosarriba.png"))); // NOI18N
        jBtnTodosArriba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTodosArribaActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jLabel1.setText("Clasificación");

        jLabel2.setText("Proveedor");

        jComboProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProveedorMouseClicked(evt);
            }
        });

        jBtnFiltrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/filtrar.png"))); // NOI18N
        jBtnFiltrar.setText("Filtrar");
        jBtnFiltrar.setBorderPainted(false);
        jBtnFiltrar.setContentAreaFilled(false);
        jBtnFiltrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnFiltrar.setFocusPainted(false);
        jBtnFiltrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/filtrar.png"))); // NOI18N
        jBtnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnFiltrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboClasiProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnFiltrar)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboClasiProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnFiltrar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jLabel6.setText("Tipo operación");

        jComboTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aumenta", "Disminuye" }));

        jBtnAplicar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/24x24/enabled/recarga.png"))); // NOI18N
        jBtnAplicar.setText("Aplicar");
        jBtnAplicar.setBorderPainted(false);
        jBtnAplicar.setContentAreaFilled(false);
        jBtnAplicar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnAplicar.setFocusPainted(false);
        jBtnAplicar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/24x24/rollover/recarga.png"))); // NOI18N
        jBtnAplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAplicarActionPerformed(evt);
            }
        });

        jLabel7.setText("Modo operación");

        jComboModo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Porcentaje (%)", "Importe ($)" }));
        jComboModo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboModoActionPerformed(evt);
            }
        });

        jLabel8.setText("Valor");

        jLblSimbolo.setText("(%)");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboModo, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jFmtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLblSimbolo))
                    .addComponent(jBtnAplicar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboModo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jFmtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblSimbolo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnAplicar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/guardar.png"))); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.setBorderPainted(false);
        jBtnGuardar.setContentAreaFilled(false);
        jBtnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnGuardar.setFocusPainted(false);
        jBtnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jProgressBar.setStringPainted(true);

        jTablaSeleccion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "", "Costo nuevo", "id_producto", "Costo Actual"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaSeleccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaSeleccionMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaSeleccion);
        if (jTablaSeleccion.getColumnModel().getColumnCount() > 0) {
            jTablaSeleccion.getColumnModel().getColumn(0).setMinWidth(130);
            jTablaSeleccion.getColumnModel().getColumn(0).setPreferredWidth(130);
            jTablaSeleccion.getColumnModel().getColumn(0).setMaxWidth(130);
            jTablaSeleccion.getColumnModel().getColumn(2).setMinWidth(60);
            jTablaSeleccion.getColumnModel().getColumn(2).setPreferredWidth(60);
            jTablaSeleccion.getColumnModel().getColumn(2).setMaxWidth(60);
            jTablaSeleccion.getColumnModel().getColumn(3).setMinWidth(90);
            jTablaSeleccion.getColumnModel().getColumn(3).setPreferredWidth(90);
            jTablaSeleccion.getColumnModel().getColumn(3).setMaxWidth(90);
            jTablaSeleccion.getColumnModel().getColumn(4).setMinWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(4).setMaxWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(5).setMinWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        jBtnHistoricoPrecio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/table.png"))); // NOI18N
        jBtnHistoricoPrecio.setText("Historico");
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(jBtnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnSalir))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                                    .addComponent(jScrollPane3))
                                .addGap(0, 2, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnHistoricoPrecio)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(397, 397, 397)
                .addComponent(jBtnUnoAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnTodosAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110)
                .addComponent(jBtnTodosArriba, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnUnoArriba, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnHistoricoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnUnoAbajo)
                    .addComponent(jBtnTodosAbajo)
                    .addComponent(jBtnTodosArriba)
                    .addComponent(jBtnUnoArriba))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBtnGuardar)
                        .addComponent(jBtnSalir))
                    .addComponent(jProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTablaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsultaMouseClicked
        if (evt.getClickCount() == 2){
            unoAbajo();
        }
        validateBtnSearchHistorialPriceProduct();
    }//GEN-LAST:event_jTablaConsultaMouseClicked

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        try{
            if (rsConsulta != null){
                rsConsulta.close();
            }
            this.dispose();
        }catch (Exception ex){
            Utiles.enviaError(this.empresa,ex);
        }
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnUnoAbajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnUnoAbajoActionPerformed
        unoAbajo();
    }//GEN-LAST:event_jBtnUnoAbajoActionPerformed

    private void jBtnTodosAbajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTodosAbajoActionPerformed
        todosAbajo();
    }//GEN-LAST:event_jBtnTodosAbajoActionPerformed

    private void jBtnTodosArribaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTodosArribaActionPerformed
        todosArriba();
    }//GEN-LAST:event_jBtnTodosArribaActionPerformed

    private void jBtnUnoArribaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnUnoArribaActionPerformed
        unoArriba();
    }//GEN-LAST:event_jBtnUnoArribaActionPerformed

    private void jBtnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltrarActionPerformed
        consulta();
    }//GEN-LAST:event_jBtnFiltrarActionPerformed

    private void jBtnAplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAplicarActionPerformed
        if (modeloSeleccion.getRowCount() > 0){
            if (Double.parseDouble(this.jFmtValor.getValue().toString()) > 0){
                for (int i = 0; i < modeloSeleccion.getRowCount(); i++){
                    BigDecimal costoActual = new BigDecimal(modeloSeleccion.getValueAt(i, 3).toString());                   
                    BigDecimal valor = new BigDecimal(Double.parseDouble(this.jFmtValor.getValue().toString()));
                    
                    switch (this.jComboTipo.getSelectedIndex()){
                        case 0:     //Aumenta
                            switch (this.jComboModo.getSelectedIndex()){
                                case 0:     //Porcentaje
                                    valor = valor.divide(new BigDecimal(100), escala, RM).add(BigDecimal.ONE).setScale(escala, RM);
                                    
                                    costoActual = costoActual.multiply(valor).setScale(escala, RM);
                                    modeloSeleccion.setValueAt((costoActual.compareTo(BigDecimal.ZERO) == -1 ? BigDecimal.ZERO : costoActual), i, 3);
                                    break;
                                    
                                case 1:     //Importe
                                    costoActual = costoActual.add(valor).setScale(escala, RM);
                                    modeloSeleccion.setValueAt((costoActual.compareTo(BigDecimal.ZERO) == -1 ? BigDecimal.ZERO : costoActual), i, 3);
                                    break;
                            }
                            break;
                        
                        case 1:     //Disminuye
                            switch (this.jComboModo.getSelectedIndex()){
                                case 0:     //Porcentaje
                                    valor = valor.divide(new BigDecimal(100), escala, RM);
                                    
                                    BigDecimal resta = costoActual.multiply(valor).setScale(escala, RM);
                                    costoActual = costoActual.subtract(resta).setScale(escala, RM);
                                    modeloSeleccion.setValueAt((costoActual.compareTo(BigDecimal.ZERO) == -1 ? BigDecimal.ZERO : costoActual), i, 3);
                                    break;
                                    
                                case 1:     //Importe
                                    costoActual = costoActual.subtract(valor).setScale(escala, RM);
                                    modeloSeleccion.setValueAt((costoActual.compareTo(BigDecimal.ZERO) == -1 ? BigDecimal.ZERO : costoActual), i, 3);
                                    break;
                            }
                            break;
                    }
                }
                    
            }else{
                JOptionPane.showMessageDialog(this, "Cargue el valor a aplicar", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(this, "No hay productos seleccionados", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnAplicarActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        int reg = modeloSeleccion.getRowCount();
        this.jProgressBar.setMaximum(reg);
        
        ArrayList parametros = new ArrayList();
        
        for (int i = 0; i < modeloSeleccion.getRowCount(); i++){
            parametros.clear();
            int id_producto = (int) modeloSeleccion.getValueAt(i, 4);
            Double costo = Double.parseDouble(modeloSeleccion.getValueAt(i, 3).toString());
            BigDecimal costoNuevo = new BigDecimal(Double.parseDouble(modeloSeleccion.getValueAt(i, 3).toString()));
            BigDecimal costoActual = new BigDecimal(Double.parseDouble(modeloSeleccion.getValueAt(i, 5).toString()));
            
            parametros.add(id_producto);
            parametros.add(costo);
            
            conexion.procAlmacenado("cambiaPrecios", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            HistoricalPriceProductService.create(id_producto, costoActual, costoNuevo);
            
            this.jProgressBar.setValue(i + 1);
            Rectangle progressRect = this.jProgressBar.getBounds();
            progressRect.x = 0;
            progressRect.y = 0;
            this.jProgressBar.paintImmediately( progressRect );
        }
        
        this.jProgressBar.setValue(reg);
        Rectangle progressRect = this.jProgressBar.getBounds();
        progressRect.x = 0;
        progressRect.y = 0;
        this.jProgressBar.paintImmediately( progressRect );
        
        limpia();
        consulta();
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jComboModoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboModoActionPerformed
        String[] partes = this.jComboModo.getSelectedItem().toString().split(" ");
        this.jLblSimbolo.setText(partes[partes.length - 1]);
    }//GEN-LAST:event_jComboModoActionPerformed

    private void jTablaSeleccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaSeleccionMouseClicked
        if (evt.getClickCount() == 2){
            unoArriba();
        }
    }//GEN-LAST:event_jTablaSeleccionMouseClicked

    private void jComboProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProveedorMouseClicked
        if (evt.isMetaDown()) {
            this.general1.limpia();
            this.general1.setTitle("Proveedor");
            Iterator it = this.arrayProveedor.iterator();
            while (it.hasNext()) {
                ProveedorData tmp = (ProveedorData) it.next();
                Object[] fila = {tmp.getNombre()};
                this.general1.add(fila);
            }
            this.general1.setVisible(true);
            if (!this.general1.getResultado().isEmpty()) {
                this.jComboProveedor.setSelectedItem(this.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboProveedorMouseClicked

    private void jBtnHistoricoPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnHistoricoPrecioActionPerformed
        HistoricoPreciosProducto hpp = new HistoricoPreciosProducto(productoSeleccionado);
        Principal.dp.add(hpp);
        hpp.toFront();
        hpp.setVisible(true);

    }//GEN-LAST:event_jBtnHistoricoPrecioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAplicar;
    private javax.swing.JButton jBtnFiltrar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnHistoricoPrecio;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnTodosAbajo;
    private javax.swing.JButton jBtnTodosArriba;
    private javax.swing.JButton jBtnUnoAbajo;
    private javax.swing.JButton jBtnUnoArriba;
    private javax.swing.JComboBox jComboClasiProducto;
    private javax.swing.JComboBox jComboModo;
    private javax.swing.JComboBox jComboProveedor;
    private javax.swing.JComboBox jComboTipo;
    private javax.swing.JFormattedTextField jFmtValor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLblSimbolo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JProgressBar jProgressBar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTablaConsulta;
    private ar.com.bosoft.RenderTablas.RXTable jTablaSeleccion;
    private javax.swing.JTextField jTxtBusca;
    // End of variables declaration//GEN-END:variables
}
