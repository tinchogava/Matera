/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.ControlPreciosDataSource;
import ar.com.bosoft.RenderTablas.DateRenderer;
import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.clases.Reporte;
import ar.com.dialogos.AvisoEspera;
//import com.lowagie.text.Anchor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import matera.db.model.Producto;
import matera.services.HistoricalPriceProductService;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class ControlPrecios extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, id_stock, copias;
    String empresa, comprobante, proveedor, zona, impresora, nombreArchivo;
    Date fecha;
    
    ResultSet rsConsulta;
    DefaultTableModel modeloConsulta, modeloDetalle;
    TableRowSorter sorterConsulta;
    TableCellRenderer tableCellRenderer;

    RoundingMode RM = RoundingMode.HALF_UP;
    int escala = 2;
    
    Salida salida = new Salida(null, true);
    
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    Reporte reporte = new Reporte();
    
    AvisoEspera avisoEspera;
    
    Producto productoSeleccionado;
    
    public ControlPrecios(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        
        initComponents();
        
        modeloConsulta = (DefaultTableModel) this.jTablaConsulta.getModel();
        jTablaConsulta.setModel(modeloConsulta);
        jTablaConsulta.setRowSorter (new TableRowSorter(modeloConsulta));
        sorterConsulta = new TableRowSorter(modeloConsulta);
        tableCellRenderer = new DateRenderer();
        jTablaConsulta.getColumnModel().getColumn(0).setCellRenderer(tableCellRenderer);
        
        modeloDetalle = (DefaultTableModel) this.jTablaDetalle.getModel();
        jTablaDetalle.setModel(modeloDetalle);
        
        setJTexFieldChanged(this.jTxtBuscaConsulta);
        
        disableBtnSearchHistoricalPriceProduct();
        
        limpia();
        consulta();
    }

    private void limpia(){
        this.jDesde.setDate(null);
        this.jHasta.setDate(null);
        this.jTxtBuscaConsulta.setText("");
        
        modeloDetalle.getDataVector().removeAllElements();
        modeloDetalle.fireTableDataChanged();
        
        this.jLblTotal.setText("$ 0,00");
        this.jTxtObservaciones.setText("");
        
        this.id_stock = 0;
        this.copias = 0;
        this.impresora = "";
        this.nombreArchivo = "";
        this.fecha = null;
        this.comprobante = "";
        this.proveedor = "";
        this.zona = "";
        
        disableBtnSearchHistoricalPriceProduct();
    }
    
    private void consulta(){
        try{
            modeloConsulta.getDataVector().removeAllElements();
            modeloConsulta.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            Long desde = this.jDesde.getDate() == null ? (long) 0 : this.jDesde.getDate().getTime();
            Long hasta = this.jHasta.getDate() == null ? (long) 0 : this.jHasta.getDate().getTime();
            
            parametros.add(desde);
            parametros.add(hasta);
            parametros.add(this.id_empresa);
            
            rsConsulta = conexion.procAlmacenado("consultaControlPrecios", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsConsulta.beforeFirst();
            while (rsConsulta.next()){
                Date fecha = rsConsulta.getDate("fecha");
                String tipoComp = rsConsulta.getString("tipoComp");
                String numComp = rsConsulta.getString("numComp");
                String proveedor = rsConsulta.getString("proveedor");
                String zona = rsConsulta.getString("zona");
                int id = rsConsulta.getInt("id_stock");
                String observaciones = rsConsulta.getString("observaciones");
                
                Object[] fila = {fecha, tipoComp, numComp, proveedor, 
                                    zona, id, observaciones};
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
            JTxtBuscaConsultaChanged();
        }else if (type.equals(DocumentEvent.EventType.REMOVE)){
            JTxtBuscaConsultaChanged();
        }
    }
 
    private void JTxtBuscaConsultaChanged(){
        String text = jTxtBuscaConsulta.getText().trim().replaceAll("'", "´");
        if (text.length() == 0) {
          sorterConsulta.setRowFilter(null);
        } else {
          sorterConsulta.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTablaConsulta.setRowSorter(sorterConsulta);
        }
    }
    
    private void calculaTotal(){
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < modeloDetalle.getRowCount(); i++){
            BigDecimal cantidad = new BigDecimal((int) modeloDetalle.getValueAt(i, 3));
            BigDecimal costoNuevo = new BigDecimal((double) modeloDetalle.getValueAt(i, 5));
            BigDecimal subtotal = cantidad.multiply(costoNuevo).setScale(escala, RM);
            modeloDetalle.setValueAt(subtotal, i, 6);
            total = total.add(subtotal).setScale(escala, RM);
        }
        this.jLblTotal.setText("$ " + String.format("%.2f", total));
    }
    
    private void updateObservaciones(){
        ArrayList parametros = new ArrayList();
        try{
            parametros.add(this.id_stock);
            parametros.add(this.jTxtObservaciones.getText().trim());
            
            conexion.procAlmacenado("updateObservacionesStock", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        } catch (Exception ex) {
            Utiles.enviaError(this.empresa,ex);
        }            
    }
    
    private void guarda(){
        ArrayList parametros = new ArrayList();
        try{
            for (int i = 0; i < modeloDetalle.getRowCount(); i++){
                parametros.clear();
                double oldPrice =(double)modeloDetalle.getValueAt(i, 4); 
                double newPrice = (double)modeloDetalle.getValueAt(i, 5);
                int productId = (int)modeloDetalle.getValueAt(i, 8);
                int stockId = this.id_stock;
                parametros.add(newPrice);
                parametros.add(productId);
                parametros.add(stockId);
                
                conexion.procAlmacenado("cambioPrecio", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                
                HistoricalPriceProductService.createFromStock(stockId, productId, oldPrice, newPrice);
            }
        } catch (Exception ex) {
            Utiles.enviaError(this.empresa,ex);
        }            
    }
    
    private void chequeado(){
        ArrayList parametros = new ArrayList();
        try{
            parametros.add(this.id_stock);
            
            conexion.procAlmacenado("chequeaStock", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        } catch (Exception ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    private void imprime(){
        this.salida.setVisible(true);
        if (this.salida.getSiNo()) {
            this.nombreArchivo = this.salida.getRutaArchivo();
            this.impresora = this.salida.getImpresora();
            this.copias = this.salida.getCopias();
            
            Map param = new HashMap();
            param.put("fecha", this.fecha);
            param.put("comprobante", this.comprobante);
            param.put("proveedor", this.proveedor);
            param.put("zona", this.zona);
            param.put("observaciones", this.jTxtObservaciones.getText().trim());
            param.put("empresa", this.empresa);
            
            ControlPreciosDataSource data = new ControlPreciosDataSource();
            
            for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
                int cantidad = (int) modeloDetalle.getValueAt(i, Utiles.getColumnByName(jTablaDetalle, "cantidad"));
                String codigo = modeloDetalle.getValueAt(i, Utiles.getColumnByName(jTablaDetalle, "codigo")).toString();
                String gtin = Utiles.v(modeloDetalle.getValueAt(i, Utiles.getColumnByName(jTablaDetalle, "gtin")));
                String descripcion = modeloDetalle.getValueAt(i, Utiles.getColumnByName(jTablaDetalle, "descripcion")).toString();
                String clasificacion = modeloDetalle.getValueAt(i, Utiles.getColumnByName(jTablaDetalle, "clasificacion")).toString();
                Double precio = Double.parseDouble(modeloDetalle.getValueAt(i, Utiles.getColumnByName(jTablaDetalle, "$ nuevo")).toString());
                
                ar.com.bosoft.Modelos.ControlPrecios nuevo = new ar.com.bosoft.Modelos.ControlPrecios(cantidad, codigo, gtin, descripcion, clasificacion, precio);
                data.add(nuevo);
            }
            
            reporte.startReport("ControlPrecios", param, data, this.salida.getTipoSalida(), nombreArchivo, impresora, copias);
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
    
    private boolean detectAndSetSameProductInDetailTable(int idProduct, int count){
        for (int i = 0; i < modeloDetalle.getRowCount(); i++) {          
            int currentidProduct = (int) modeloDetalle.getValueAt(i, Utiles.getColumnByName(jTablaDetalle, "id_producto"));
            if(idProduct == currentidProduct){
                int oldCount = (int) modeloDetalle.getValueAt(i, Utiles.getColumnByName(jTablaDetalle, "cantidad"));
                modeloDetalle.setValueAt(oldCount + count, i, Utiles.getColumnByName(jTablaDetalle, "cantidad"));
                return true;
            }    
        }
        return false;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jDesde = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jDesde.getJCalendar().setTodayButtonVisible(true); 
        this.jDesde.getJCalendar().setTodayButtonText("Hoy"); 
        this.jDesde.getJCalendar().setWeekOfYearVisible(false);
        jLabel11 = new javax.swing.JLabel();
        jHasta = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jHasta.getJCalendar().setTodayButtonVisible(true); 
        this.jHasta.getJCalendar().setTodayButtonText("Hoy"); 
        this.jHasta.getJCalendar().setWeekOfYearVisible(false);
        jBtnFiltra = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTxtBuscaConsulta = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();
        jLblTotal = new javax.swing.JLabel();
        jBtnGuardar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jBtnChequeado = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTablaDetalle = new ar.com.bosoft.RenderTablas.RXTable();
        jBtnHistoricoPrecio = new javax.swing.JButton();

        setTitle("Control de precios");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jLabel4.setText("Desde");

        jLabel11.setText("Hasta");

        jBtnFiltra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/filtrar.png"))); // NOI18N
        jBtnFiltra.setText("Filtrar");
        jBtnFiltra.setBorderPainted(false);
        jBtnFiltra.setContentAreaFilled(false);
        jBtnFiltra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnFiltra.setFocusPainted(false);
        jBtnFiltra.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/filtrar.png"))); // NOI18N
        jBtnFiltra.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/filtrar.png"))); // NOI18N
        jBtnFiltra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnFiltraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jBtnFiltra)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel4)
                            .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addComponent(jBtnFiltra))
                .addGap(1, 1, 1))
        );

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setFocusable(false);

        jTxtBuscaConsulta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaConsultaFocusGained(evt);
            }
        });

        jLabel8.setText("Buscar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBuscaConsulta)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBuscaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)))
        );

        jTablaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "", "Comprobante", "Proveedor", "Zona", "id_stock", "observaciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
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
        jTablaConsulta.getTableHeader().setReorderingAllowed(false);
        jTablaConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaConsultaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaConsulta);
        if (jTablaConsulta.getColumnModel().getColumnCount() > 0) {
            jTablaConsulta.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(0).setMaxWidth(150);
            jTablaConsulta.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTablaConsulta.getColumnModel().getColumn(1).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTablaConsulta.getColumnModel().getColumn(2).setMaxWidth(200);
            jTablaConsulta.getColumnModel().getColumn(5).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(5).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(6).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(6).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jTxtObservaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Observaciones"));
        jScrollPane3.setViewportView(jTxtObservaciones);

        jLblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblTotal.setText("$ 0.00");

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

        jBtnChequeado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/finalizar.png"))); // NOI18N
        jBtnChequeado.setText("Chequeado");
        jBtnChequeado.setBorderPainted(false);
        jBtnChequeado.setContentAreaFilled(false);
        jBtnChequeado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnChequeado.setFocusPainted(false);
        jBtnChequeado.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/finalizar.png"))); // NOI18N
        jBtnChequeado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnChequeadoActionPerformed(evt);
            }
        });

        jTablaDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción", "Clasificación", "cantidad", "$ Actual", "$ Nuevo", "$ Subt.", "id_stockDetalle", "id_producto", "GTIN"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaDetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaDetalleMouseClicked(evt);
            }
        });
        jTablaDetalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTablaDetalleKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(jTablaDetalle);
        if (jTablaDetalle.getColumnModel().getColumnCount() > 0) {
            jTablaDetalle.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTablaDetalle.getColumnModel().getColumn(0).setMaxWidth(200);
            jTablaDetalle.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTablaDetalle.getColumnModel().getColumn(2).setMaxWidth(200);
            jTablaDetalle.getColumnModel().getColumn(3).setPreferredWidth(60);
            jTablaDetalle.getColumnModel().getColumn(3).setMaxWidth(120);
            jTablaDetalle.getColumnModel().getColumn(4).setPreferredWidth(60);
            jTablaDetalle.getColumnModel().getColumn(4).setMaxWidth(120);
            jTablaDetalle.getColumnModel().getColumn(5).setPreferredWidth(60);
            jTablaDetalle.getColumnModel().getColumn(5).setMaxWidth(120);
            jTablaDetalle.getColumnModel().getColumn(6).setPreferredWidth(60);
            jTablaDetalle.getColumnModel().getColumn(6).setMaxWidth(120);
            jTablaDetalle.getColumnModel().getColumn(7).setMinWidth(0);
            jTablaDetalle.getColumnModel().getColumn(7).setPreferredWidth(0);
            jTablaDetalle.getColumnModel().getColumn(7).setMaxWidth(0);
            jTablaDetalle.getColumnModel().getColumn(8).setMinWidth(0);
            jTablaDetalle.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTablaDetalle.getColumnModel().getColumn(8).setMaxWidth(0);
            jTablaDetalle.getColumnModel().getColumn(9).setMinWidth(0);
            jTablaDetalle.getColumnModel().getColumn(9).setPreferredWidth(0);
            jTablaDetalle.getColumnModel().getColumn(9).setMaxWidth(0);
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBtnGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnChequeado)
                        .addGap(84, 84, 84)
                        .addComponent(jBtnSalir))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane3)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLblTotal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(139, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jBtnHistoricoPrecio)
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jBtnHistoricoPrecio)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLblTotal)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnChequeado)
                    .addComponent(jBtnSalir))
                .addGap(1, 1, 1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        consulta();
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jTxtBuscaConsultaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaConsultaFocusGained
        this.jTxtBuscaConsulta.select(0, this.jTxtBuscaConsulta.getText().length());
    }//GEN-LAST:event_jTxtBuscaConsultaFocusGained

    private void jTablaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsultaMouseClicked
        if (evt.getClickCount() == 2){
            disableBtnSearchHistoricalPriceProduct();
            try {
                modeloDetalle.getDataVector().removeAllElements();
                modeloDetalle.fireTableDataChanged();
                
                int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
                this.id_stock = (int) modeloConsulta.getValueAt(fila, 5);
                this.jTxtObservaciones.setText(modeloConsulta.getValueAt(fila, 6).toString());
                
                this.fecha = null;
                try {
                    this.fecha = formatter.parse(modeloConsulta.getValueAt(fila, 0).toString());
                } catch (Exception e) {
                    //Fecha inválida
                }
                this.comprobante = modeloConsulta.getValueAt(fila, 1).toString() + modeloConsulta.getValueAt(fila, 2).toString();
                this.proveedor = modeloConsulta.getValueAt(fila, 3).toString();
                this.zona = modeloConsulta.getValueAt(fila, 4).toString();
                
                ArrayList parametros = new ArrayList();
                parametros.add(this.id_stock);

                rsConsulta = conexion.procAlmacenado("traeStockDetalle", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                rsConsulta.beforeFirst();
                while (rsConsulta.next()){
                    String codigo = rsConsulta.getString("codigo");
                    String nombre = rsConsulta.getString("nombre");
                    String clasiProducto = rsConsulta.getString("clasiProducto");
                    int cantidad = rsConsulta.getInt("cantidad");
                    Double costoPesos = rsConsulta.getDouble("costoPesos");
                    int id = rsConsulta.getInt("id_stockDetalle");
                    int id_producto = rsConsulta.getInt("id_producto");

                    BigDecimal subtotal = new BigDecimal(cantidad);
                    subtotal = subtotal.multiply(new BigDecimal(costoPesos)).setScale(escala, RM);
                    
                    if(!detectAndSetSameProductInDetailTable(id_producto, cantidad)){
                        Object[] f = {codigo, nombre, clasiProducto, cantidad, costoPesos, 
                                    costoPesos, subtotal, id, id_producto};
                        modeloDetalle.addRow(f);
                    }
                    
                    
                }
                calculaTotal();
            } catch (SQLException ex) {
                Utiles.enviaError(this.empresa,ex);
            }
        }            
    }//GEN-LAST:event_jTablaConsultaMouseClicked

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (this.id_stock > 0){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ControlPrecios.this.avisoEspera = new AvisoEspera(null, false, AvisoEspera.GUARDANDO);
                    ControlPrecios.this.avisoEspera.setVisible(true);
                    Thread performer = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ControlPrecios.this.updateObservaciones();
                            ControlPrecios.this.guarda();
                            ControlPrecios.this.avisoEspera.setVisible(false);
                        }
                    }, "Performer");
                    performer.start();
                }
            });
            
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea imprimir el reporte?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                this.imprime();
            }
            
            limpia();
            
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ControlPrecios.this.avisoEspera = new AvisoEspera(null, false, AvisoEspera.CARGANDO);
                    ControlPrecios.this.avisoEspera.setVisible(true);
                    Thread performer = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ControlPrecios.this.consulta();
                            ControlPrecios.this.avisoEspera.setVisible(false);
                        }
                    }, "Performer");
                    performer.start();
                }
            });            
        }else{
            JOptionPane.showMessageDialog(this,"Seleccione un comprobante", "Atención",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        try{
            if (this.rsConsulta != null){
                rsConsulta.close();
            }
            this.dispose();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnChequeadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnChequeadoActionPerformed
        if (this.id_stock > 0){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ControlPrecios.this.avisoEspera = new AvisoEspera(null, false, AvisoEspera.GUARDANDO);
                    ControlPrecios.this.avisoEspera.setVisible(true);
                    Thread performer = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ControlPrecios.this.updateObservaciones();
                            ControlPrecios.this.guarda();
                            ControlPrecios.this.chequeado();
                            ControlPrecios.this.avisoEspera.setVisible(false);
                        }
                    }, "Performer");
                    performer.start();
                }
            });
            
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea imprimir el reporte?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                this.imprime();
            }
            
            limpia();
            
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ControlPrecios.this.avisoEspera = new AvisoEspera(null, false, AvisoEspera.CARGANDO);
                    ControlPrecios.this.avisoEspera.setVisible(true);
                    Thread performer = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ControlPrecios.this.consulta();
                            ControlPrecios.this.avisoEspera.setVisible(false);
                        }
                    }, "Performer");
                    performer.start();
                }
            });            
        }else{
            JOptionPane.showMessageDialog(this,"Seleccione un comprobante", "Atención",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnChequeadoActionPerformed

    private void jTablaDetalleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTablaDetalleKeyReleased
        int fila = jTablaDetalle.getSelectedRow();
       
        BigDecimal cantidad = new BigDecimal((int) modeloDetalle.getValueAt(fila, 3));
        BigDecimal costoNuevo = new BigDecimal((double) modeloDetalle.getValueAt(fila, 5));
        
        BigDecimal subtotal = cantidad.multiply(costoNuevo).setScale(escala, RM);
        
        modeloDetalle.setValueAt(subtotal, fila, 6);
        calculaTotal();
    }//GEN-LAST:event_jTablaDetalleKeyReleased

    private void jBtnHistoricoPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnHistoricoPrecioActionPerformed
        HistoricoPreciosProducto hpp = new HistoricoPreciosProducto(productoSeleccionado);
        Principal.dp.add(hpp);
        hpp.toFront();
        hpp.setVisible(true);
    }//GEN-LAST:event_jBtnHistoricoPrecioActionPerformed

    private void jTablaDetalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaDetalleMouseClicked
        int fila = jTablaDetalle.getSelectedRow();
        Integer idProducto = (Integer) modeloDetalle.getValueAt(fila, 8);
        String nombre = (String) modeloDetalle.getValueAt(fila, 1);
        String codigo = (String) modeloDetalle.getValueAt(fila, 0);
        enableBtnSearchHistoricalPriceProduct(new Long(idProducto), nombre, codigo);
    }//GEN-LAST:event_jTablaDetalleMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnChequeado;
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnHistoricoPrecio;
    private javax.swing.JButton jBtnSalir;
    private com.toedter.calendar.JDateChooser jDesde;
    private com.toedter.calendar.JDateChooser jHasta;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLblTotal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTablaConsulta;
    private ar.com.bosoft.RenderTablas.RXTable jTablaDetalle;
    private javax.swing.JTextField jTxtBuscaConsulta;
    private javax.swing.JTextArea jTxtObservaciones;
    // End of variables declaration//GEN-END:variables
}
