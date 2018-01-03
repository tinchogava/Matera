/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.ConsultaStockDataSource;
import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ClasiProductoCRUD;
import ar.com.bosoft.crud.ProveedorCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.ClasiProductoData;
import ar.com.bosoft.data.ProveedorData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import ar.com.dialogos.AvisoEspera;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.swing.table.TableRowSorter;
import jxl.biff.drawing.ComboBox;
import matera.gui.combobox.ComboBoxMgr;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class ConsultaStock extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, copias;
    String empresa, impresora, nombreArchivo, todos;
    ProveedorCRUD proveedorCRUD;
    ClasiProductoCRUD clasProductoCRUD;
    ZonaCRUD zonaCRUD;
    
    ArrayList proveedorArray, clasiProductoArray, zonaArray;
    
    ResultSet rsConsulta;
            
    DefaultTableModel modeloConsulta, modeloSeleccion;
    TableRowSorter sorterConsulta,sorterSeleccion;
    SeleccionImp seleccionImp = new SeleccionImp(null, true);
    
    ConsultaStockDataSource data = new ConsultaStockDataSource();
            
    AvisoEspera avisoEspera;
            
    public ConsultaStock(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.todos = "-- TODOS --";
        
        this.proveedorCRUD = new ProveedorCRUD(conexion, id_empresa, empresa);
        this.clasProductoCRUD = new ClasiProductoCRUD(conexion, empresa);
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        
        initComponents();
        
        llenaComboProveedor();
        llenaComboClasificacion();
        llenaComboZona();
        ComboBoxMgr.fillProveedorCombo(jComboProveedores, true);
        
        this.modeloConsulta = (DefaultTableModel) this.jTablaConsulta.getModel();
        this.jTablaConsulta.setModel(modeloConsulta);        
        jTablaConsulta.setRowSorter (new TableRowSorter(modeloConsulta));
        sorterConsulta = new TableRowSorter(modeloConsulta);
        
        this.modeloSeleccion = (DefaultTableModel) this.jTablaSeleccion.getModel();
        this.jTablaSeleccion.setModel(modeloSeleccion);        
        jTablaSeleccion.setRowSorter (new TableRowSorter(modeloSeleccion));
        sorterSeleccion = new TableRowSorter(modeloSeleccion);
        
        limpia();
        zonaUsuario();
        consulta(0, 0);
        setJTexFieldChanged(jTxtBusca);
    }

    private void llenaComboProveedor(){
        this.jComboProveedor.addItem(todos);
        proveedorArray = proveedorCRUD.consulta(true);
        Iterator i = proveedorArray.iterator();
        while (i.hasNext()){
            ProveedorData tmp = (ProveedorData) i.next();
            this.jComboProveedor.addItem(tmp.getNombre());
        }
    }
    
    private void llenaComboClasificacion(){
        this.jComboClasificacion.addItem(todos);
        clasiProductoArray = clasProductoCRUD.consulta(true);
        Iterator i = clasiProductoArray.iterator();
        while (i.hasNext()){
            ClasiProductoData tmp = (ClasiProductoData) i.next();
            this.jComboClasificacion.addItem(tmp.getNombre());
        }
    }
    
    private void llenaComboZona(){
        this.jComboZona.addItem(this.todos);
        zonaArray = zonaCRUD.consulta(true);
        Iterator i = zonaArray.iterator();
        while (i.hasNext()){
            ZonaData tmp = (ZonaData) i.next();
            this.jComboZona.addItem(tmp.getNombre());
        }
    }
    
    private void zonaUsuario(){
        int id_zonaUsuario = Principal.usuarioData.getId_zonaSistema();
        if (id_zonaUsuario > 0) {
            if (this.zonaArray != null){
                Iterator it = zonaArray.iterator();
                while (it.hasNext()){
                    ZonaData tmp = (ZonaData) it.next();
                    if (tmp.getId_zona() == id_zonaUsuario) {
                        this.jComboZona.setSelectedItem(tmp.getNombre());
                    }
                }
            }
        }
        this.jComboZona.setEnabled(id_zonaUsuario == 0);
    }
    
    private void limpia(){
        this.jComboProveedor.setSelectedItem(todos);
        this.jComboClasificacion.setSelectedItem(todos);
        
        this.jTxtBusca.setText("");
        this.modeloSeleccion.getDataVector().removeAllElements();
        this.modeloSeleccion.fireTableDataChanged();
        
        this.jDesde.setDate(null);
        this.jHasta.setDate(null);
        
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedItem(0);
        }
        
        this.jComboDC.setSelectedIndex(0);
        this.jTxtLote.setText("");
        this.jComboPresentacion.setSelectedIndex(0);
    }
    
    private void consulta(int id_proveedor, int id_clasiProducto){
        try {
            modeloConsulta.getDataVector().removeAllElements();
            modeloConsulta.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            parametros.add(id_proveedor);
            parametros.add(id_clasiProducto);
            parametros.add(0);
            parametros.add(this.id_empresa);
            
            rsConsulta = conexion.procAlmacenado("consultaStock", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            rsConsulta.beforeFirst();
            
            while (rsConsulta.next()){
                String codigo = rsConsulta.getString("codigo");
                String gtin = rsConsulta.getString("gtin");
                String nombre = rsConsulta.getString("nombre");
                int existencia = rsConsulta.getInt("existencia");
                int id = rsConsulta.getInt("id_producto");
                
                Object[] fila = {codigo, gtin, nombre, existencia, id};
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
    }
    
    private void traeMovimientos(Date desde, Date hasta, int id_zona, String zona){
        try {
            String ids = "";

            for (int i = 0; i < modeloSeleccion.getRowCount(); i++) {
                ids += Utiles.valueAt(modeloSeleccion, i, "id_producto").toString().trim();

                if (i < modeloSeleccion.getRowCount() - 1) {
                    ids += ",";
                }
            }

            String dc = this.jComboDC.getSelectedIndex() == 0 ? "X" : (this.jComboDC.getSelectedIndex() == 1 ? "C" : "D");

            ArrayList parametros = new ArrayList();
            parametros.add(this.id_empresa);
            parametros.add(ids);
            parametros.add(desde != null ? desde.getTime() : new Long(0));
            parametros.add(hasta != null ? hasta.getTime() : new Long(0));
            parametros.add(this.jTxtLote.getText().trim()); //lote
            parametros.add(this.jTextSerie.getText().trim()); //serie
            parametros.add(id_zona);
            parametros.add(dc);
            int id_proveedor = ComboBoxMgr.getSelectedId(jComboProveedores);
            parametros.add(id_proveedor);
            rsConsulta = conexion.procAlmacenado("consultaMovimientosStock", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

            if (rsConsulta.first()) {
                rsConsulta.beforeFirst();
                
                int saldoInicialReporte = 0;
                    
                while (rsConsulta.next()) {
                    int acumulado = 0;
                    int cantidad = rsConsulta.getInt("cantidad");
                    int saldoInicial = rsConsulta.getInt("saldoInicial");
                    String codigo = rsConsulta.getString("codigo");
                    
                    String gtin;
                    String nombre;
                    String lote;
                    String serie;
                    String pm;
                    String observaciones;
                    String es;
                    Date fecha;
                    Date vencimiento;
                    Double costo;
                    String remito;
                    String proveedor;

                    if (cantidad == -1) {   //Saldo inicial
                        acumulado = saldoInicial;
                        saldoInicialReporte = saldoInicial;
                        if (saldoInicial == 0) {    //Si saldo inicial es cero
                            if (rsConsulta.next()) {
                                String codigoProximo = rsConsulta.getString("codigo");
                                if (!codigo.equals(codigoProximo)) {    //Si no tiene movimientos
                                    rsConsulta.previous();
                                }else{
                                    gtin = rsConsulta.getString("gtin");
                                    nombre = rsConsulta.getString("producto");
                                    lote = rsConsulta.getString("lote");
                                    serie = rsConsulta.getString("serie");
                                    pm = rsConsulta.getString("pm");
                                    observaciones = rsConsulta.getString("observaciones");
                                    es = rsConsulta.getString("dc");
                                    cantidad = rsConsulta.getInt("cantidad");
                                    fecha = rsConsulta.getDate("fecha");
                                    vencimiento = rsConsulta.getDate("vencimiento");
                                    acumulado += es.equals("D") ? cantidad : -cantidad;
                                    costo = rsConsulta.getDouble("costo");
                                    proveedor = rsConsulta.getString("proveedor");
                                    remito = rsConsulta.getString("remito");
                                    
 
                                    if (cantidad > 0) {
                                        ar.com.bosoft.Modelos.ConsultaStock nuevo = new ar.com.bosoft.Modelos.ConsultaStock(zona, codigo, gtin, nombre, lote, serie, pm, observaciones, es, cantidad, saldoInicialReporte, fecha, vencimiento, acumulado, costo, proveedor, remito);
                                        data.add(nuevo);
                                    }
                                }
                            }
                        }else{  //Si saldo inicial no es cero
                            if (rsConsulta.next()) {
                                String codigoProximo = rsConsulta.getString("codigo");
                                if (!codigo.equals(codigoProximo)) {    //Si no tiene movimientos
                                    rsConsulta.previous();
                                    /*
                                    gtin = rsConsulta.getString("gtin");
                                    nombre = rsConsulta.getString("producto");
                                    lote = rsConsulta.getString("lote");
                                    serie = rsConsulta.getString("serie");
                                    pm = rsConsulta.getString("pm");
                                    observaciones = rsConsulta.getString("observaciones");
                                    es = rsConsulta.getString("dc");
                                    cantidad = rsConsulta.getInt("cantidad");
                                    fecha = rsConsulta.getDate("fecha");
                                    vencimiento = rsConsulta.getDate("vencimiento");
                                    costo = rsConsulta.getDouble("costo");
                                    proveedor = rsConsulta.getString("proveedor");
                                    remito = rsConsulta.getString("remito");
                                    
                                    
                                    if (cantidad != 0) {
                                        ar.com.bosoft.Modelos.ConsultaStock nuevo = new ar.com.bosoft.Modelos.ConsultaStock(zona, codigo, gtin, nombre, lote, serie, pm, observaciones, es, cantidad, saldoInicialReporte, fecha, vencimiento, acumulado, costo, proveedor, remito);
                                        data.add(nuevo);
                                    }
                                    */
                                }else{
                                    gtin = rsConsulta.getString("gtin");
                                    nombre = rsConsulta.getString("producto");
                                    lote = rsConsulta.getString("lote");
                                    serie = rsConsulta.getString("serie");
                                    pm = rsConsulta.getString("pm");
                                    observaciones = rsConsulta.getString("observaciones");
                                    es = rsConsulta.getString("dc");
                                    cantidad = rsConsulta.getInt("cantidad");
                                    fecha = rsConsulta.getDate("fecha");
                                    vencimiento = rsConsulta.getDate("vencimiento");
                                    codigo = rsConsulta.getString("codigo");
                                    acumulado += es.equals("D") ? cantidad : -cantidad;
                                    costo = rsConsulta.getDouble("costo");
                                    proveedor = rsConsulta.getString("proveedor");
                                    remito = rsConsulta.getString("remito");
                                    
                                    
                                    if (cantidad > 0) {
                                        ar.com.bosoft.Modelos.ConsultaStock nuevo = new ar.com.bosoft.Modelos.ConsultaStock(zona, codigo, gtin, nombre, lote, serie, pm, observaciones, es, cantidad, saldoInicialReporte, fecha, vencimiento, acumulado, costo, proveedor, remito);
                                        data.add(nuevo);
                                    }
                                }
                            }else{
                                rsConsulta.previous();
                                gtin = rsConsulta.getString("gtin");
                                nombre = rsConsulta.getString("producto");
                                lote = rsConsulta.getString("lote");
                                serie = rsConsulta.getString("serie");
                                pm = rsConsulta.getString("pm");
                                observaciones = rsConsulta.getString("observaciones");
                                es = rsConsulta.getString("dc");
                                cantidad = rsConsulta.getInt("cantidad");
                                fecha = rsConsulta.getDate("fecha");
                                vencimiento = rsConsulta.getDate("vencimiento");
                                costo = rsConsulta.getDouble("costo");
                                proveedor = rsConsulta.getString("proveedor");
                                remito = rsConsulta.getString("remito");
                              
                                
                                if (cantidad > 0) {
                                    ar.com.bosoft.Modelos.ConsultaStock nuevo = new ar.com.bosoft.Modelos.ConsultaStock(zona, codigo, gtin, nombre, lote, serie, pm, observaciones, es, cantidad, saldoInicialReporte, fecha, vencimiento, acumulado, costo, proveedor, remito);
                                    data.add(nuevo);
                                }
                            }
                        }
                    }else{
                        gtin = rsConsulta.getString("gtin");
                        nombre = rsConsulta.getString("producto");
                        lote = rsConsulta.getString("lote");
                        serie = rsConsulta.getString("serie");
                        pm = rsConsulta.getString("pm");
                        observaciones = rsConsulta.getString("observaciones");
                        es = rsConsulta.getString("dc");
                        cantidad = rsConsulta.getInt("cantidad");
                        fecha = rsConsulta.getDate("fecha");
                        vencimiento = rsConsulta.getDate("vencimiento");
                        codigo = rsConsulta.getString("codigo");
                        acumulado += es.equals("D") ? cantidad : -cantidad;
                        costo = rsConsulta.getDouble("costo");
                        proveedor = rsConsulta.getString("proveedor");
                        remito = rsConsulta.getString("remito");
                        
                        if (cantidad > 0) {
                            ar.com.bosoft.Modelos.ConsultaStock nuevo = new ar.com.bosoft.Modelos.ConsultaStock(zona, codigo, gtin, nombre, lote, serie, pm, observaciones, es, cantidad, saldoInicialReporte, fecha, vencimiento, acumulado, costo, proveedor, remito);
                            data.add(nuevo);
                        }
                    }
                }
            }
        }catch (Exception ex){
            Utiles.enviaError(empresa, ex);
        }
    }
    
    private void imprimir(int salida){
        if (modeloSeleccion.getRowCount() > 0){
            
            data = new ConsultaStockDataSource();   //Limpio el dataSource
            Date desde = this.jDesde.getDate();
            Date hasta = this.jHasta.getDate();

            Map param = new HashMap();
            param.put("empresa", this.empresa);
            param.put("desde", desde);
            param.put("hasta", hasta);
            param.put("lote", this.jTxtLote.getText());
            param.put("zona", this.jComboZona.getSelectedItem().toString());
            param.put("tipoMovimiento", this.jComboDC.getSelectedItem().toString());
            param.put("proveedor", this.jComboProveedores.getSelectedItem().toString());
            
            if (this.jComboZona.getSelectedIndex() > 0) {
                ZonaData z = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
                int id_zona = z.getId_zona();
                String zona = z.getNombre();
                this.traeMovimientos(desde, hasta, id_zona, zona);
            }else{
                Iterator it = this.zonaArray.iterator();
                while (it.hasNext()) {
                    ZonaData z = (ZonaData) it.next();
                    int id_zona = z.getId_zona();
                    String zona = z.getNombre();
                    this.traeMovimientos(desde, hasta, id_zona, zona);
                }
            }
            
            this.avisoEspera.setVisible(false);
            
            if (data.tieneDatos()) {
                Reporte reporte = new Reporte();
                String nombreReporte = "ConsultaStock" + this.jComboPresentacion.getSelectedItem().toString();
                reporte.startReport(nombreReporte, param, data, salida, nombreArchivo, impresora, copias);
            }else{
                JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
            
            this.limpia();
        }else{
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún producto", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void unoAbajo(){
        int indiceConsulta = this.jTablaConsulta.convertRowIndexToModel(this.jTablaConsulta.getSelectedRow());
        
        if (indiceConsulta >= 0){
            
            String codigo = Utiles.valueAt(modeloConsulta, indiceConsulta, "codigo").toString();
            String gtin = Utiles.valueAt(modeloConsulta, indiceConsulta, "gtin").toString();
            String nombre = Utiles.valueAt(modeloConsulta, indiceConsulta, "nombre").toString();
            Object id = Utiles.valueAt(modeloConsulta, indiceConsulta, "id_producto");
            if (Utiles.existeEnModelo(modeloSeleccion, Utiles.getColumnByName(modeloSeleccion, "id_producto"), id)){
                JOptionPane.showMessageDialog(this, "Ya se ha seleccionado este producto", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }else{
                Object[] fila = {codigo, gtin, nombre, id};
                modeloSeleccion.addRow(fila);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
        
        this.jTablaConsulta.clearSelection();
    }
    
    private void todosAbajo(){
        this.todosArriba();
        
        for (int i = 0; i < modeloConsulta.getRowCount(); i++){
            /*
            String codigo = Utiles.valueAt(modeloConsulta, i, "codigo").toString();
            String gtin = Utiles.valueAt(modeloConsulta, i, "gtin").toString();
            String nombre = Utiles.valueAt(modeloConsulta, i, "nombre").toString();
            Integer id = Integer.parseInt(Utiles.valueAt(modeloConsulta, i, "id_producto").toString());
            
            Object[] fila = {codigo, gtin, nombre, id};
                    */
            modeloSeleccion.addRow(Utiles.fillTableObject(i, modeloConsulta, modeloSeleccion, null));
        }
        
        this.jTablaConsulta.clearSelection();
    }
    
    private void unoArriba(){
        int indiceSeleccion = this.jTablaSeleccion.convertRowIndexToModel(this.jTablaSeleccion.getSelectedRow());
        if (indiceSeleccion >= 0){
            modeloSeleccion.removeRow(indiceSeleccion);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
        this.jTablaSeleccion.clearSelection();
    }
    
    private void todosArriba(){
        modeloSeleccion.getDataVector().removeAllElements();
        modeloSeleccion.fireTableDataChanged();
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
        jScrollPane4 = new javax.swing.JScrollPane();
        jTablaSeleccion = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jDesde = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jDesde.getJCalendar().setTodayButtonVisible(true); 
        this.jDesde.getJCalendar().setTodayButtonText("Hoy"); 
        this.jDesde.getJCalendar().setWeekOfYearVisible(false);
        jLabel2 = new javax.swing.JLabel();
        jHasta = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jHasta.getJCalendar().setTodayButtonVisible(true); 
        this.jHasta.getJCalendar().setTodayButtonText("Hoy"); 
        this.jHasta.getJCalendar().setWeekOfYearVisible(false);
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jComboDC = new javax.swing.JComboBox();
        jComboPresentacion = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jTextSerie = new javax.swing.JTextField();
        jTxtLote = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextGtin = new javax.swing.JTextField();
        jComboProveedores = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboProveedor = new javax.swing.JComboBox();
        jComboClasificacion = new javax.swing.JComboBox();
        jBtnFiltra = new javax.swing.JButton();

        setTitle("Consulta de movimientos de stock");
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
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 727, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                "Código", "GTIN", "Nombre", "Existencia", "id_producto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class
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
            jTablaConsulta.getColumnModel().getColumn(4).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        jBtnUnoAbajo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/unoabajo.png"))); // NOI18N
        jBtnUnoAbajo.setBorderPainted(false);
        jBtnUnoAbajo.setContentAreaFilled(false);
        jBtnUnoAbajo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        jBtnUnoArriba.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        jBtnTodosAbajo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        jBtnTodosArriba.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnTodosArriba.setFocusPainted(false);
        jBtnTodosArriba.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/todosarriba.png"))); // NOI18N
        jBtnTodosArriba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTodosArribaActionPerformed(evt);
            }
        });

        jTablaSeleccion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "GTIN", "Nombre", "id_producto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class
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
        jTablaSeleccion.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTablaSeleccion.getTableHeader().setReorderingAllowed(false);
        jTablaSeleccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaSeleccionMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTablaSeleccion);
        if (jTablaSeleccion.getColumnModel().getColumnCount() > 0) {
            jTablaSeleccion.getColumnModel().getColumn(0).setMinWidth(130);
            jTablaSeleccion.getColumnModel().getColumn(0).setPreferredWidth(130);
            jTablaSeleccion.getColumnModel().getColumn(0).setMaxWidth(130);
            jTablaSeleccion.getColumnModel().getColumn(3).setMinWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(3).setPreferredWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jLabel1.setText("Desde");

        jLabel2.setText("Hasta");

        jLabel10.setText("Lote");

        jLabel11.setText("Zona");

        jLabel12.setText("Tipo movimiento");

        jLabel13.setText("Presentación");

        jComboDC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- TODOS --", "Alta", "Baja" }));

        jComboPresentacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Detallado", "Consolidado" }));

        jLabel5.setText("Serie");

        jTextSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextSerieActionPerformed(evt);
            }
        });

        jLabel6.setText("GTIN");

        jTextGtin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextGtinActionPerformed(evt);
            }
        });

        jComboProveedores.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- TODOS --", "Alta", "Baja" }));

        jLabel7.setText("Proveedor");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11))
                            .addComponent(jLabel12))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboZona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboDC, 0, 178, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTxtLote, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextGtin, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboPresentacion, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jComboDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jTxtLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel1)
                        .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jComboPresentacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jTextGtin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addContainerGap(12, Short.MAX_VALUE))))
        );

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

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jLabel3.setText("Proveedor");

        jLabel4.setText("Clasificación");

        jComboProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProveedorMouseClicked(evt);
            }
        });

        jBtnFiltra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/filtrar.png"))); // NOI18N
        jBtnFiltra.setText("Filtrar");
        jBtnFiltra.setBorderPainted(false);
        jBtnFiltra.setContentAreaFilled(false);
        jBtnFiltra.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnFiltra.setFocusPainted(false);
        jBtnFiltra.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/filtrar.png"))); // NOI18N
        jBtnFiltra.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/filtrar.png"))); // NOI18N
        jBtnFiltra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnFiltraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboProveedor, 0, 360, Short.MAX_VALUE)
                    .addComponent(jComboClasificacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jBtnFiltra)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jComboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jComboClasificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jBtnFiltra))
                .addGap(1, 1, 1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jBtnUnoAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnTodosAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnTodosArriba, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnUnoArriba, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(262, 262, 262))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 787, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 787, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(135, 135, 135)
                                        .addComponent(jBtnSalir))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnUnoAbajo)
                    .addComponent(jBtnUnoArriba)
                    .addComponent(jBtnTodosAbajo)
                    .addComponent(jBtnTodosArriba))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTablaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsultaMouseClicked
        if (evt.getClickCount() == 2){
            this.unoAbajo();
        }
    }//GEN-LAST:event_jTablaConsultaMouseClicked

    private void jTablaSeleccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaSeleccionMouseClicked
        if (evt.getClickCount() == 2){
            this.unoArriba();
        }
    }//GEN-LAST:event_jTablaSeleccionMouseClicked

    private void jBtnScrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScrActionPerformed
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ConsultaStock.this.avisoEspera = new AvisoEspera(null, false, AvisoEspera.CARGANDO);
                ConsultaStock.this.avisoEspera.setVisible(true);
                Thread performer = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ConsultaStock.this.imprimir(0);
                    }
                }, "Performer");
                performer.start();
            }
        });
    }//GEN-LAST:event_jBtnScrActionPerformed

    private void jBtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImpActionPerformed
        seleccionImp.setVisible(true);
        if (seleccionImp.getSino()){
            this.impresora = seleccionImp.getImpresora();
            this.copias = seleccionImp.getCopias();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ConsultaStock.this.avisoEspera = new AvisoEspera(null, false, AvisoEspera.CARGANDO);
                    ConsultaStock.this.avisoEspera.setVisible(true);
                    Thread performer = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ConsultaStock.this.imprimir(1);
                        }
                    }, "Performer");
                    performer.start();
                }
            });
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
                this.nombreArchivo = archivoElegido.getCanonicalPath() + ".pdf";
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ConsultaStock.this.avisoEspera = new AvisoEspera(null, false, AvisoEspera.CARGANDO);
                        ConsultaStock.this.avisoEspera.setVisible(true);
                        Thread performer = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ConsultaStock.this.imprimir(2);
                            }
                        }, "Performer");
                        performer.start();
                    }
                });
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jBtnPdfActionPerformed

    private void jBtnXlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnXlsActionPerformed
        //Crear un objeto FileChooser
        JFileChooser fc = new JFileChooser();
        //Mostrar la ventana para abrir archivo y recoger la respuesta
        int respuesta = fc.showSaveDialog(null);
        //Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION){
            //Crear un objeto File con el archivo elegido
            File archivoElegido = fc.getSelectedFile();
            try {
                this.nombreArchivo = archivoElegido.getCanonicalPath() + ".xlsx";
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ConsultaStock.this.avisoEspera = new AvisoEspera(null, false, AvisoEspera.CARGANDO);
                        ConsultaStock.this.avisoEspera.setVisible(true);
                        Thread performer = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ConsultaStock.this.imprimir(3);
                            }
                        }, "Performer");
                        performer.start();
                    }
                });
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jBtnXlsActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        if (rsConsulta != null){
            try {
                rsConsulta.close();
            } catch (SQLException ex) {
                Utiles.enviaError(empresa, ex);
            }
        }
        
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnUnoAbajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnUnoAbajoActionPerformed
        this.unoAbajo();
    }//GEN-LAST:event_jBtnUnoAbajoActionPerformed

    private void jBtnTodosAbajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTodosAbajoActionPerformed
        this.todosAbajo();
    }//GEN-LAST:event_jBtnTodosAbajoActionPerformed

    private void jBtnTodosArribaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTodosArribaActionPerformed
        this.todosArriba();
    }//GEN-LAST:event_jBtnTodosArribaActionPerformed

    private void jBtnUnoArribaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnUnoArribaActionPerformed
        this.unoArriba();
    }//GEN-LAST:event_jBtnUnoArribaActionPerformed

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        int id_proveedor = 0;
        int id_clasiProducto = 0;
        
        if (this.jComboProveedor.getSelectedIndex() > 0){
            ProveedorData p = (ProveedorData) proveedorArray.get(this.jComboProveedor.getSelectedIndex() - 1);
            id_proveedor = p.getId_proveedor();
        }
        
        if (this.jComboClasificacion.getSelectedIndex() > 0){
            ClasiProductoData c = (ClasiProductoData) clasiProductoArray.get(this.jComboClasificacion.getSelectedIndex() - 1);
            id_clasiProducto = c.getId_clasiProducto();
        }
        
        consulta(id_proveedor, id_clasiProducto);
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jComboProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProveedorMouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Proveedor");
            Iterator it = this.proveedorArray.iterator();
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
    }//GEN-LAST:event_jComboProveedorMouseClicked

    private void jTextSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextSerieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextSerieActionPerformed

    private void jTextGtinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextGtinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextGtinActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jBtnTodosAbajo;
    private javax.swing.JButton jBtnTodosArriba;
    private javax.swing.JButton jBtnUnoAbajo;
    private javax.swing.JButton jBtnUnoArriba;
    private javax.swing.JButton jBtnXls;
    private javax.swing.JComboBox jComboClasificacion;
    private javax.swing.JComboBox jComboDC;
    private javax.swing.JComboBox jComboPresentacion;
    private javax.swing.JComboBox jComboProveedor;
    private javax.swing.JComboBox jComboProveedores;
    private javax.swing.JComboBox jComboZona;
    private com.toedter.calendar.JDateChooser jDesde;
    private com.toedter.calendar.JDateChooser jHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTablaConsulta;
    private javax.swing.JTable jTablaSeleccion;
    private javax.swing.JTextField jTextGtin;
    private javax.swing.JTextField jTextSerie;
    private javax.swing.JTextField jTxtBusca;
    private javax.swing.JTextField jTxtLote;
    // End of variables declaration//GEN-END:variables

}
