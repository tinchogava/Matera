/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.AltaStockDataSource;
import ar.com.bosoft.RenderTablas.DateRenderer;
import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.buscadores.General1;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ProductoCRUD;
import ar.com.bosoft.crud.ProveedorCRUD;
import ar.com.bosoft.crud.TipoCompCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.ProveedorData;
import ar.com.bosoft.data.TipoCompData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import matera.gui.renderers.DateStringRenderer;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class ConsultaAltaStock extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, id_stock;
    Double costoPesos;
    String empresa, usuario;
    Long desde, hasta;
    ResultSet rsConsulta;
    DefaultTableModel modeloConsulta, modeloProductos, modelo;
    TableRowSorter sorterConsulta, sorterProductos, sorter;
    TableCellRenderer tableCellRenderer;

    ProveedorCRUD proveedorCRUD;
    TipoCompCRUD tipoCompCRUD;
    ZonaCRUD zonaCRUD;
    ProductoCRUD productoCRUD;
    
    ArrayList arrayProveedor, arrayTipoComp, arrayZona, arrayProducto;
    
    Salida salida = new Salida(null, true);
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
    General1 general1 = new General1(null, true);
    
    public ConsultaAltaStock(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        this.id_stock = 0;
        this.costoPesos = 0.00;
        this.proveedorCRUD = new ProveedorCRUD(conexion, id_empresa, empresa);
        this.tipoCompCRUD = new TipoCompCRUD(conexion, empresa);
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.productoCRUD = new ProductoCRUD(conexion, id_empresa, empresa);
        
        initComponents();
        
        this.modeloConsulta = (DefaultTableModel) this.jTablaConsulta.getModel();
        this.jTablaConsulta.setModel(modeloConsulta);        
        jTablaConsulta.setRowSorter (new TableRowSorter(modeloConsulta));
        sorterConsulta = new TableRowSorter(modeloConsulta);
        tableCellRenderer = new DateRenderer();
        jTablaConsulta.getColumnModel().getColumn(0).setCellRenderer(tableCellRenderer);
        
        this.modeloProductos = (DefaultTableModel) this.jTablaProductos.getModel();
        this.jTablaProductos.setModel(modeloProductos);        
        jTablaProductos.setRowSorter (new TableRowSorter(modeloProductos));
        sorterProductos = new TableRowSorter(modeloProductos);
        
        this.modelo = (DefaultTableModel) this.jTabla.getModel();
        this.jTabla.setModel(modelo);
        jTabla.setRowSorter (new TableRowSorter(modelo));
        sorter = new TableRowSorter(modelo);
        
        llenaComboProveedor();
        llenaComboTipoComp();
        llenaComboZona();
        
        limpia();
        consultaAltaStock();
        consulta(0);
        setJTexFieldChanged(this.jTxtBusca);
        setJTexFieldChanged2(jTxtBusca1);
        jTabla.getColumn("vencimiento").setCellRenderer(new DateStringRenderer());
    }

    private void llenaComboProveedor(){
        arrayProveedor = proveedorCRUD.consulta(true);
        if (arrayProveedor.isEmpty()){
            JOptionPane.showMessageDialog(this, "No hay proveedores habilitados", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }else{
            Iterator i = arrayProveedor.iterator();
            while (i.hasNext()){
                ProveedorData tmp = (ProveedorData) i.next();
                this.jComboProveedor.addItem(tmp.getNombre());
            }
        }
    }
    
    private void llenaComboTipoComp(){
        arrayTipoComp = tipoCompCRUD.consulta();
        if (arrayTipoComp.isEmpty()){
            JOptionPane.showMessageDialog(this, "No hay tipos de comprobante habilitados", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }else{
            Iterator i = arrayTipoComp.iterator();
            while (i.hasNext()){
                TipoCompData tmp = (TipoCompData) i.next();
                this.jComboTipoComp.addItem(tmp.getDenominacion());
            }
        }
    }
    
    private void llenaComboZona(){
        arrayZona = zonaCRUD.consulta(true);
        if (arrayZona.isEmpty()){
            JOptionPane.showMessageDialog(this, "No hay zonas habilitadas", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }else{
            Iterator i = arrayZona.iterator();
            while (i.hasNext()){
                ZonaData tmp = (ZonaData) i.next();
                this.jComboZona.addItem(tmp.getNombre());
            }
        }
    }

    private void consultaAltaStock(){
        try {
            modeloConsulta.getDataVector().removeAllElements();
            modeloConsulta.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            parametros.add(this.desde);
            parametros.add(this.hasta);
            parametros.add(this.id_empresa);
            rsConsulta = conexion.procAlmacenado("consultaAltaStock", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
            rsConsulta.beforeFirst();
            while (rsConsulta.next()){
                Date fecha = rsConsulta.getDate("fecha");
                String tComp = rsConsulta.getString("tipoComp");
                String numComp = rsConsulta.getString("numComp");
                String prov = rsConsulta.getString("proveedor");
                String z = rsConsulta.getString("zona");
                int id = rsConsulta.getInt("id_stock");
                
                Object[] fila = {fecha, tComp, numComp, prov, z, id};
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

    private void setJTexFieldChanged2(JTextField txt){
        DocumentListener documentListener = new DocumentListener() {
 
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                printIt2(documentEvent);
            }
 
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                printIt2(documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                printIt2(documentEvent);
            }
        };
        txt.getDocument().addDocumentListener(documentListener); 
    }
 
    private void printIt2(DocumentEvent documentEvent) {
        DocumentEvent.EventType type = documentEvent.getType();
 
        if (type.equals(DocumentEvent.EventType.CHANGE)){
 
        }else if (type.equals(DocumentEvent.EventType.INSERT)){
            JTxtBuscaChanged2();
        }else if (type.equals(DocumentEvent.EventType.REMOVE)){
            JTxtBuscaChanged2();
        }
    }
 
    private void JTxtBuscaChanged2(){
        String text = jTxtBusca1.getText().trim().replaceAll("'", "´");
        if (text.length() == 0) {
          sorterProductos.setRowFilter(null);
        } else {
          sorterProductos.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTablaProductos.setRowSorter(sorterProductos);
        }
    }

    private void selectAll(JTextField campo){
        campo.select(0, campo.getText().length());
    }
    
    private void limpia(){
        this.jDesde.setDate(null);
        this.jHasta.setDate(null);
        this.desde = (long) 0;
        this.hasta = (long) 0;
        this.jTxtBusca.setText("");
        
        this.jFecha.setDate(new Date());
        this.jComboProveedor.setSelectedIndex(arrayProveedor.isEmpty() ? -1 : 0);
        this.jComboTipoComp.setSelectedIndex(arrayTipoComp.isEmpty() ? -1 : 0);
        this.jTxtPuntoVta.setText("");
        this.jTxtNumComp.setText("");
        this.jComboZona.setSelectedIndex(arrayZona.isEmpty() ? -1 : 0);
        
        this.modelo.getDataVector().removeAllElements();
        this.modelo.fireTableDataChanged();
        this.jTxtObservaciones.setText("");
    }
    
    private void traeAltaStock(){
        try {
            ArrayList parametros = new ArrayList();
            parametros.add(this.id_stock);
            parametros.add(this.id_empresa);
            ResultSet rs = conexion.procAlmacenado("traeAltaStock", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
            
            rs.first();
            
            this.jFecha.setDate(rs.getDate("fecha"));
            
            this.jComboTipoComp.setSelectedIndex(-1);
            this.jComboTipoComp.setSelectedItem(rs.getString("tipoComp"));
            
            String numComp = rs.getString("numComp");
            this.jTxtPuntoVta.setText(numComp.substring(0, 4));
            this.jTxtNumComp.setText(numComp.substring(4));
            
            this.jComboProveedor.setSelectedIndex(-1);
            this.jComboProveedor.setSelectedItem(rs.getString("proveedor"));
            
            this.jComboZona.setSelectedIndex(-1);
            this.jComboZona.setSelectedItem(rs.getString("zona"));
            
            this.jTxtObservaciones.setText(rs.getString("observaciones"));
            
            traeStockDetalle();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        
    }
    
    private void consulta(int id_proveedor){
        try {
            modeloProductos.getDataVector().removeAllElements();
            modeloProductos.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            parametros.add(id_proveedor);
            parametros.add(0);
            parametros.add(0);
            parametros.add(this.id_empresa);
            rsConsulta = conexion.procAlmacenado("consultaStock", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
            rsConsulta.beforeFirst();
            
            while (rsConsulta.next()){
                /*
                String codigo = rsConsulta.getString("codigo");
                String nombre = rsConsulta.getString("nombre");
                int existencia = rsConsulta.getInt("existencia");
                int id = rsConsulta.getInt("id_producto");
                String pm = rsConsulta.getString("pm");
                
                Object[] fila = {codigo, nombre, existencia, id, pm};
                modeloProductos.addRow(fila);
               */
                modeloProductos.addRow(Utiles.fillTableObject(modeloProductos, rsConsulta));
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    private void traeStockDetalle(){
        try {
            ArrayList parametros = new ArrayList();
            parametros.add(this.id_stock);
            ResultSet rs = conexion.procAlmacenado("traeStockDetalle", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
            rs.beforeFirst();
            
            while(rs.next()){
                /*
                String codigo = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                String lote = rs.getString("lote");
                String serie = rs.getString("serie");
                String pm = rs.getString("pm");
                
                Date v = rs.getDate("vencimiento");
                String venc = "";
                if (v != null) {
                    venc = formatter.format(v);
                } 
                
                int id = rs.getInt("id_producto");
                
                Object[] fila = {codigo, nombre, cantidad, lote, serie, pm, venc, id};
                modelo.addRow(fila);
                */
                modelo.addRow(Utiles.fillTableObject(modelo, rs));
                
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
            
    }
    
    private String validaObligatorios(){
        String aviso = "";
        
        if (this.jFecha.getDate().after(new Date())){
            aviso = "La fecha del comprobante no puede ser mayor a la fecha actual";
        }
        
        if (modelo.getRowCount() == 0){
            aviso = "No se han cargado productos";
        }
        
        if (this.jFecha.getDate() == null ||
                this.jComboProveedor.getSelectedIndex() < 0 ||
                this.jComboTipoComp.getSelectedIndex() < 0 ||
                this.jTxtPuntoVta.getText().isEmpty() ||
                this.jTxtNumComp.getText().isEmpty() ||
                this.jComboZona.getSelectedIndex() < 0){
            aviso = "Complete todos los datos obligatorios (*)";
        }
        
        return aviso;
    }
    
    private void unoAbajo(){
        int indice = jTablaProductos.convertRowIndexToModel(jTablaProductos.getSelectedRow());
        if (indice >= 0){
            /*
            String codigo = modeloProductos.getValueAt(indice, Utiles.getColumnByName(jTablaProductos, "codigo")).toString();
            String nombre = modeloProductos.getValueAt(indice, Utiles.getColumnByName(jTablaProductos, "nombre")).toString();
            int cantidad = 1;
            String lote = "";
            String serie = "";
            String pm = modeloProductos.getValueAt(indice, Utiles.getColumnByName(jTablaProductos, "pm")).toString();
            String venc = "";
            int id_producto = (int)modeloProductos.getValueAt(indice, Utiles.getColumnByName(jTablaProductos, "id_producto"));
            
            if (Utiles.existeEnModelo(modelo, 6, id_producto)) {
                int respuesta = JOptionPane.showConfirmDialog(this, "Este producto ya ha sido seleccionado.\n¿Desea cargarlo nuevamente?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (respuesta == JOptionPane.NO_OPTION) {
                    return;
                }
            }
            Object[] nuevo = {codigo, nombre, cantidad, lote, serie, pm, venc, id_producto};
            modelo.addRow(nuevo);
           */
            HashMap<Object,Object> defaults = new HashMap<>();
            defaults.put("cantidad",1);
            modelo.addRow(Utiles.fillTableObject(indice, modeloProductos, modelo, defaults));
            Rectangle r = this.jTabla.getCellRect(modelo.getRowCount() - 1, 0, true);
            this.jScrollTabla.getViewport().scrollRectToVisible(r);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
        
        //this.jTablaProductos.clearSelection();
    }
    
    private void todosAbajo(){
        todosArriba();
        
        for ( int i = 0; i < modeloProductos.getRowCount(); i++){
            /*
            String codigo = modeloProductos.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "codigo")).toString();
            String nombre = modeloProductos.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "nombre")).toString();
            int cantidad = 1;
            String lote = "";
            String serie = "";
            String pm = "";
            String venc = "";
            int id_producto = (int)modeloProductos.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "id_producto"));
            
            Object[] nuevo = {codigo, nombre, cantidad, lote, serie, pm, venc, id_producto};

            modelo.addRow(nuevo);
            */
            HashMap<Object,Object> defaults = new HashMap<>();
            defaults.put("cantidad",1);
            modelo.addRow(Utiles.fillTableObject(i, modeloProductos, modelo, defaults));            
        }
        
        jTablaProductos.clearSelection();
        jTabla.clearSelection();
    }
    
    private void unoArriba(){
        int indice = jTabla.convertColumnIndexToModel(jTabla.getSelectedRow());
        
        if (indice >= 0){
            modelo.removeRow(indice);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
        
        jTabla.clearSelection();
    }
    
    private void todosArriba(){
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
    }
    
    private void imprime(int salida, boolean previa){
        String impresora = "";
        int copias = 1;
        if (salida == 1) {
            impresora = this.salida.getImpresora();
            copias = this.salida.getCopias();
        }
            
        Map param=new HashMap();
        param.put("empresa", this.empresa);
        param.put("proveedor", this.jComboProveedor.getSelectedItem().toString());
        param.put("tipoComp", this.jComboTipoComp.getSelectedItem().toString());
        param.put("numComp", this.jTxtPuntoVta.getText() + this.jTxtNumComp.getText());
        param.put("zona", this.jComboZona.getSelectedItem().toString());
        param.put("fecha", this.jFecha.getDate());
        param.put("id_stock", 0);
        param.put("observaciones", this.jTxtObservaciones.getText().trim());
        param.put("previa", previa);
                
        AltaStockDataSource data = new AltaStockDataSource();
        
        for (int i = 0; i < modelo.getRowCount(); i++){
            String codigo = modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "codigo")).toString();
            String gtin = modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "gtin")).toString();
            String nombre = modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "nombre")).toString();
            int cantidad = Integer.parseInt(modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "cantidad")).toString());
            String lote = modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "lote")).toString();
            String serie = modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "serie")).toString();
            String pm = modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "pm")).toString();
            Date vencimiento = null;
            try{
                vencimiento = formatter.parse(modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "vencimiento")).toString());
            }catch (Exception ex){
                //No se ingreso una fecha válida
            }

            ar.com.bosoft.Modelos.AltaStock nuevo = new ar.com.bosoft.Modelos.AltaStock(codigo, gtin, nombre, lote, serie, pm, cantidad, vencimiento);
            data.addPresupuestoProd(nuevo);
        }

        Reporte reporte = new Reporte();
        String nombreReporte = "AltaStock";
        String rutaArchivo = "";
        reporte.startReport(nombreReporte, param, data, salida, rutaArchivo, impresora, copias);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu = new javax.swing.JPopupMenu();
        jMenuElimina = new javax.swing.JMenuItem();
        jExcel = new javax.swing.JMenuItem();
        jScrollBar1 = new javax.swing.JScrollBar();
        jBtnGuardar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jBtnPrevia = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jDesde = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jDesde.getJCalendar().setTodayButtonVisible(true); 
        this.jDesde.getJCalendar().setTodayButtonText("Hoy"); 
        this.jDesde.getJCalendar().setWeekOfYearVisible(false);
        jLabel12 = new javax.swing.JLabel();
        jHasta = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jHasta.getJCalendar().setTodayButtonVisible(true); 
        this.jHasta.getJCalendar().setTodayButtonText("Hoy"); 
        this.jHasta.getJCalendar().setWeekOfYearVisible(false);
        jBtnFiltrar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
        jBtnEditar = new javax.swing.JButton();
        jBtnEditar1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jFecha = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jFecha.getJCalendar().setTodayButtonVisible(true); 
        this.jFecha.getJCalendar().setTodayButtonText("Hoy"); 
        this.jFecha.getJCalendar().setWeekOfYearVisible(false);
        jLabel16 = new javax.swing.JLabel();
        jComboTipoComp = new javax.swing.JComboBox();
        jTxtPuntoVta = new javax.swing.JTextField();
        jTxtPuntoVta.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(4,true));
        jLabel1 = new javax.swing.JLabel();
        jTxtNumComp = new javax.swing.JTextField();
        jTxtNumComp.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(8,true));
        jLabel3 = new javax.swing.JLabel();
        jComboProveedor = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        jTxtBusca1 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTablaProductos = new javax.swing.JTable();
        jBtnUnoAbajo = new javax.swing.JButton();
        jBtnTodosAbajo = new javax.swing.JButton();
        jBtnTodosArriba = new javax.swing.JButton();
        jBtnUnoArriba = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollTabla = new javax.swing.JScrollPane();
        jTabla = new ar.com.bosoft.RenderTablas.RXTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();

        jMenuElimina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/16x16/enabled/eliminar_popup.png"))); // NOI18N
        jMenuElimina.setText("Elimina");
        jMenuElimina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuEliminaActionPerformed(evt);
            }
        });
        jPopupMenu.add(jMenuElimina);

        jExcel.setText("Exportar a Excel");
        jExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jExcelActionPerformed(evt);
            }
        });
        jPopupMenu.add(jExcel);

        setTitle("Consulta de altas de stock");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

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

        jBtnPrevia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/preview.png"))); // NOI18N
        jBtnPrevia.setText("Vista previa");
        jBtnPrevia.setBorderPainted(false);
        jBtnPrevia.setContentAreaFilled(false);
        jBtnPrevia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnPrevia.setFocusPainted(false);
        jBtnPrevia.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jBtnPrevia.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/preview.png"))); // NOI18N
        jBtnPrevia.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/preview.png"))); // NOI18N
        jBtnPrevia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPreviaActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel11.setText("Desde");

        jLabel12.setText("Hasta");

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jBtnFiltrar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel11)
                    .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnFiltrar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setFocusable(false);

        jTxtBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaFocusGained(evt);
            }
        });

        jLabel9.setText("Buscar");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)))
        );

        jTablaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "", "Comp.", "Proveedor", "Destino", "id_stock"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaConsulta.setComponentPopupMenu(jPopupMenu);
        jTablaConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaConsultaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTablaConsulta);
        if (jTablaConsulta.getColumnModel().getColumnCount() > 0) {
            jTablaConsulta.getColumnModel().getColumn(0).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(0).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(1).setMinWidth(50);
            jTablaConsulta.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTablaConsulta.getColumnModel().getColumn(1).setMaxWidth(50);
            jTablaConsulta.getColumnModel().getColumn(2).setMinWidth(100);
            jTablaConsulta.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTablaConsulta.getColumnModel().getColumn(2).setMaxWidth(100);
            jTablaConsulta.getColumnModel().getColumn(4).setMinWidth(150);
            jTablaConsulta.getColumnModel().getColumn(4).setPreferredWidth(150);
            jTablaConsulta.getColumnModel().getColumn(4).setMaxWidth(150);
            jTablaConsulta.getColumnModel().getColumn(5).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        jBtnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/editar.png"))); // NOI18N
        jBtnEditar.setText("Editar alta");
        jBtnEditar.setBorderPainted(false);
        jBtnEditar.setContentAreaFilled(false);
        jBtnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnEditar.setFocusPainted(false);
        jBtnEditar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/editar.png"))); // NOI18N
        jBtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarActionPerformed(evt);
            }
        });

        jBtnEditar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/limpiar.png"))); // NOI18N
        jBtnEditar1.setText("Limpiar");
        jBtnEditar1.setBorderPainted(false);
        jBtnEditar1.setContentAreaFilled(false);
        jBtnEditar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnEditar1.setFocusPainted(false);
        jBtnEditar1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/limpiar.png"))); // NOI18N
        jBtnEditar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditar1ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("* Fecha");

        jLabel16.setText("* Tipo");

        jTxtPuntoVta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtPuntoVtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtPuntoVtaFocusLost(evt);
            }
        });

        jLabel1.setText("* Número");

        jTxtNumComp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtNumCompFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtNumCompFocusLost(evt);
            }
        });

        jLabel3.setText("* Proveedor");

        jComboProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProveedorMouseClicked(evt);
            }
        });
        jComboProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboProveedorActionPerformed(evt);
            }
        });

        jLabel4.setText("* Destino");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboTipoComp, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTxtPuntoVta, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtNumComp, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jComboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jComboTipoComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jTxtPuntoVta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNumComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1))
        );

        jPanel6.setBackground(new java.awt.Color(153, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel6.setFocusable(false);

        jLabel22.setText("Buscar");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca1)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBusca1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)))
        );

        jTablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "GTIN", "Nombre", "Existencia", "id_producto", "pm"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaProductos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTablaProductos.getTableHeader().setReorderingAllowed(false);
        jTablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaProductosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTablaProductos);
        if (jTablaProductos.getColumnModel().getColumnCount() > 0) {
            jTablaProductos.getColumnModel().getColumn(0).setMinWidth(120);
            jTablaProductos.getColumnModel().getColumn(0).setPreferredWidth(120);
            jTablaProductos.getColumnModel().getColumn(0).setMaxWidth(120);
            jTablaProductos.getColumnModel().getColumn(3).setMinWidth(75);
            jTablaProductos.getColumnModel().getColumn(3).setPreferredWidth(75);
            jTablaProductos.getColumnModel().getColumn(3).setMaxWidth(75);
            jTablaProductos.getColumnModel().getColumn(4).setMinWidth(0);
            jTablaProductos.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTablaProductos.getColumnModel().getColumn(4).setMaxWidth(0);
            jTablaProductos.getColumnModel().getColumn(5).setMinWidth(0);
            jTablaProductos.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTablaProductos.getColumnModel().getColumn(5).setMaxWidth(0);
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

        jLabel6.setText("Formato de fecha");

        jLabel5.setText("dd/mm/yyyy");

        jTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "GTIN", "Nombre", "cantidad", "Lote", "serie", "PM", "vencimiento", "id_producto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaMouseClicked(evt);
            }
        });
        jScrollTabla.setViewportView(jTabla);
        if (jTabla.getColumnModel().getColumnCount() > 0) {
            jTabla.getColumnModel().getColumn(0).setMinWidth(120);
            jTabla.getColumnModel().getColumn(0).setPreferredWidth(120);
            jTabla.getColumnModel().getColumn(0).setMaxWidth(120);
            jTabla.getColumnModel().getColumn(2).setMinWidth(200);
            jTabla.getColumnModel().getColumn(2).setPreferredWidth(200);
            jTabla.getColumnModel().getColumn(2).setMaxWidth(200);
            jTabla.getColumnModel().getColumn(3).setMinWidth(50);
            jTabla.getColumnModel().getColumn(3).setPreferredWidth(50);
            jTabla.getColumnModel().getColumn(3).setMaxWidth(50);
            jTabla.getColumnModel().getColumn(4).setMinWidth(120);
            jTabla.getColumnModel().getColumn(4).setPreferredWidth(120);
            jTabla.getColumnModel().getColumn(4).setMaxWidth(120);
            jTabla.getColumnModel().getColumn(5).setMinWidth(120);
            jTabla.getColumnModel().getColumn(5).setPreferredWidth(120);
            jTabla.getColumnModel().getColumn(5).setMaxWidth(120);
            jTabla.getColumnModel().getColumn(6).setMinWidth(75);
            jTabla.getColumnModel().getColumn(6).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(6).setMaxWidth(75);
            jTabla.getColumnModel().getColumn(7).setMinWidth(100);
            jTabla.getColumnModel().getColumn(7).setPreferredWidth(100);
            jTabla.getColumnModel().getColumn(7).setMaxWidth(100);
            jTabla.getColumnModel().getColumn(8).setMinWidth(0);
            jTabla.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Observaciones"));

        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jScrollPane2.setViewportView(jTxtObservaciones);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(237, 237, 237)
                                .addComponent(jBtnUnoAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnTodosAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(jBtnTodosArriba, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnUnoArriba, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(295, 295, 295)
                                        .addComponent(jLabel5)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel6))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addGap(302, 302, 302)
                                            .addComponent(jBtnPrevia)
                                            .addGap(60, 60, 60)
                                            .addComponent(jBtnGuardar))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jBtnEditar)
                                                    .addGap(46, 46, 46)
                                                    .addComponent(jBtnEditar1))
                                                .addComponent(jScrollPane3)))
                                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jScrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnSalir)
                        .addGap(29, 29, 29))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jBtnEditar, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jBtnEditar1)))
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel5))
                        .addComponent(jBtnTodosArriba, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBtnUnoArriba, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jBtnUnoAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnTodosAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jScrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnPrevia)
                    .addComponent(jBtnSalir))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jScrollPane4, jScrollTabla});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (jTabla.isEditing())
            jTabla.getCellEditor().stopCellEditing();        
        if (validaObligatorios().isEmpty()){
            Long fecha = this.jFecha.getDate().getTime();
            
            TipoCompData t = (TipoCompData) arrayTipoComp.get(this.jComboTipoComp.getSelectedIndex());
            int id_tipoComp = t.getId_tipoComp();
            
            String numComp = this.jTxtPuntoVta.getText() + this.jTxtNumComp.getText();
            
            ProveedorData p = (ProveedorData) arrayProveedor.get(this.jComboProveedor.getSelectedIndex());
            int id_proveedor = p.getId_proveedor();
            
            ZonaData z = (ZonaData) arrayZona.get(this.jComboZona.getSelectedIndex());
            int id_zona = z.getId_zona();
            
            String observaciones = this.jTxtObservaciones.getText().trim();
            
            ArrayList parametros = new ArrayList();
            parametros.add(this.id_stock);
            parametros.add(fecha);
            parametros.add(id_tipoComp);
            parametros.add(numComp);
            parametros.add(id_proveedor);
            parametros.add(id_zona);
            parametros.add(observaciones);
            parametros.add(this.id_empresa);
            parametros.add(this.usuario);
            
            conexion.procAlmacenado("insertStock", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            if (this.id_stock > 0) {
                parametros.clear();
                parametros.add(this.id_stock);
                conexion.procAlmacenado("deleteStockDetalle", parametros, 
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            }
                
            
            for (int i = 0; i < modelo.getRowCount(); i++){
                parametros.clear();
                
                int id = Integer.parseInt(modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "id_producto")).toString());
                int cantidad = Integer.parseInt(modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "cantidad")).toString());
                String dc = "D";
                String lote = modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "lote")).toString();
                String serie = modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "serie")).toString();
                String pm = modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "pm")).toString();
                Long vencimiento = (long)0;
                try{
                    vencimiento = formatter.parse(modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "vencimiento")).toString()).getTime();
                }catch (Exception ex){
                    //No se ingreso una fecha válida
                }
                String obs = this.jComboTipoComp.getSelectedItem().toString() + " " + numComp;
                Double costoP = 0.00;
                
                parametros.add(this.id_stock);
                parametros.add(id);
                parametros.add(cantidad);
                parametros.add(dc);
                parametros.add(lote);
                parametros.add(serie);
                parametros.add(pm);
                parametros.add(vencimiento);
                parametros.add(costoP);
                parametros.add(obs);
                parametros.add(id_zona);
                parametros.add(this.id_empresa);
                
                conexion.procAlmacenado("insertStockDetalle", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            }

            int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea imprimir el alta de productos?", "BOSOFT", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                this.salida.setVisible(true);
                if (salida.getSiNo()){
                    imprime(salida.getTipoSalida(), false);
                }
            }
            
            limpia();
            consultaAltaStock();
            this.id_stock = 0;
            this.jTablaConsulta.clearSelection();
            this.jFecha.requestFocus();
        }else{
            JOptionPane.showMessageDialog(this,validaObligatorios(), "Atención",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        if (rsConsulta != null){
            try {
                rsConsulta.close();
            } catch (Exception ex) {
                Utiles.enviaError(this.empresa, ex);
            }
        }
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jTxtPuntoVtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtPuntoVtaFocusGained
        selectAll(jTxtPuntoVta);
    }//GEN-LAST:event_jTxtPuntoVtaFocusGained

    private void jTxtNumCompFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtNumCompFocusGained
        selectAll(jTxtNumComp);
    }//GEN-LAST:event_jTxtNumCompFocusGained

    private void jMenuEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuEliminaActionPerformed
        this.unoArriba();
    }//GEN-LAST:event_jMenuEliminaActionPerformed

    private void jTxtPuntoVtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtPuntoVtaFocusLost
        String aux = this.jTxtPuntoVta.getText();
        String c = "0";
        while (aux.length() < 4){
            aux = c + aux;
        }
        this.jTxtPuntoVta.setText(aux);
    }//GEN-LAST:event_jTxtPuntoVtaFocusLost

    private void jTxtNumCompFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtNumCompFocusLost
        String aux = this.jTxtNumComp.getText();
        String c = "0";
        while (aux.length() < 8){
            aux = c + aux;
        }
        this.jTxtNumComp.setText(aux);
    }//GEN-LAST:event_jTxtNumCompFocusLost

    private void jTablaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsultaMouseClicked
        int indiceConsulta = jTablaConsulta.convertRowIndexToModel (jTablaConsulta.getSelectedRow());
        this.id_stock = Integer.parseInt(modeloConsulta.getValueAt(indiceConsulta, 5).toString());
    }//GEN-LAST:event_jTablaConsultaMouseClicked

    private void jTxtBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaFocusGained
        this.jTxtBusca.select(0, this.jTxtBusca.getText().length());
    }//GEN-LAST:event_jTxtBuscaFocusGained

    private void jBtnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltrarActionPerformed
        this.desde = new Long(0);
        this.hasta = new Long(0);
        if (this.jDesde.getDate() != null){
            this.desde = this.jDesde.getDate().getTime();
        }
        if (this.jHasta.getDate() != null){
            this.hasta = this.jHasta.getDate().getTime();
        }
        
        consultaAltaStock();
    }//GEN-LAST:event_jBtnFiltrarActionPerformed

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarActionPerformed
        if (this.id_stock > 0){
            limpia();
            traeAltaStock();
        }else{
            JOptionPane.showMessageDialog(this, "no ha seleccionado ninguna fila", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnEditarActionPerformed

    private void jBtnEditar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditar1ActionPerformed
        limpia();
        this.id_stock = 0;
        this.jTablaConsulta.clearSelection();
    }//GEN-LAST:event_jBtnEditar1ActionPerformed

    private void jTablaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaProductosMouseClicked
        if (evt.getClickCount() == 2) {
            unoAbajo();
        }
    }//GEN-LAST:event_jTablaProductosMouseClicked

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

    private void jBtnPreviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPreviaActionPerformed
        if (modelo.getRowCount() > 0){
            imprime(0, true);
        }else{
            JOptionPane.showMessageDialog(this, "No se han cargado productos", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }        
    }//GEN-LAST:event_jBtnPreviaActionPerformed

    private void jComboProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboProveedorActionPerformed
//        todosArriba();
//        if (this.jComboProveedor.getSelectedIndex() >= 0) {
//            ProveedorData tmp = (ProveedorData) arrayProveedor.get(this.jComboProveedor.getSelectedIndex());
//            int id_proveedor = tmp.getId_proveedor();
//            consulta(id_proveedor);
//        }            
    }//GEN-LAST:event_jComboProveedorActionPerformed

    private void jTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaMouseClicked
        if (evt.getClickCount() == 2) {
            this.unoArriba();
        }
    }//GEN-LAST:event_jTablaMouseClicked

    private void jExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jExcelActionPerformed
        if (modeloConsulta.getRowCount() > 0) {
            //Crear un objeto FileChooser
            JFileChooser fc = new JFileChooser();
            //Mostrar la ventana para abrir archivo y recoger la respuesta
            int respuesta = fc.showSaveDialog(null);
            //Comprobar si se ha pulsado Aceptar
            if (respuesta == JFileChooser.APPROVE_OPTION){
                try {
                    File file = new File(fc.getSelectedFile().getCanonicalPath() + ".xls");
                    Utiles.exportaTablaExcel(jTablaConsulta, file);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }else{
            JOptionPane.showMessageDialog(this,"No hay resultados para mostrar", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jExcelActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnEditar1;
    private javax.swing.JButton jBtnFiltrar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnPrevia;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnTodosAbajo;
    private javax.swing.JButton jBtnTodosArriba;
    private javax.swing.JButton jBtnUnoAbajo;
    private javax.swing.JButton jBtnUnoArriba;
    private javax.swing.JComboBox jComboProveedor;
    private javax.swing.JComboBox jComboTipoComp;
    private javax.swing.JComboBox jComboZona;
    private com.toedter.calendar.JDateChooser jDesde;
    private javax.swing.JMenuItem jExcel;
    private com.toedter.calendar.JDateChooser jFecha;
    private com.toedter.calendar.JDateChooser jHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuElimina;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollTabla;
    private ar.com.bosoft.RenderTablas.RXTable jTabla;
    private javax.swing.JTable jTablaConsulta;
    private javax.swing.JTable jTablaProductos;
    private javax.swing.JTextField jTxtBusca;
    private javax.swing.JTextField jTxtBusca1;
    private javax.swing.JTextField jTxtNumComp;
    private javax.swing.JTextArea jTxtObservaciones;
    private javax.swing.JTextField jTxtPuntoVta;
    // End of variables declaration//GEN-END:variables
}
