/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.RenderTablas.DateCellEditor;
import ar.com.bosoft.RenderTablas.RowColorRenderer;
import ar.com.bosoft.Utilidades.DateUtil;
import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.buscadores.BuscaPresupuesto;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.ActiveDatabase;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.PrestacionCRUD;
import ar.com.bosoft.crud.TecnicoCRUD;
import ar.com.bosoft.formatosCampos.ConMascara;
import ar.com.matera.TableModels.RemitoTableModel;
import com.toedter.calendar.JDateChooser;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import matera.TableObjects.RemitoDetalleTableObject;
import matera.TableObjects.RemitoTableObject;

import matera.db.Presupuesto;
import matera.db.Producto;
import matera.db.Remito;
import matera.db.RemitoDetalle;
import matera.db.dao.RemitoConsumidoDao;
import matera.db.managers.RemitoMgr;
import matera.gui.dialog.LoadingDialog;
import matera.helpers.LogHelper;
import matera.jooq.Tables;
import matera.jooq.tables.pojos.RemitoConsumido;
import matera.jooq.tables.records.StockdetalleRecord;
import matera.workers.ActionWorker;
import org.javalite.activejdbc.Base;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class ModificaConsumido extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, id_presupuesto;
    String empresa, usuario;
    
    PrestacionCRUD prestacionCRUD;
    TecnicoCRUD tecnicoCRUD;
    
    ArrayList prestacionArray, tecnicoArray;
    
    ResultSet rsConsulta;
    DefaultTableModel modeloProducto, modeloConsumo, modeloProductoCantidad, modeloConsumoCantidad;
    RemitoTableModel modeloRemito;
    TableRowSorter sorterProducto, sorterConsumo, sorterRemito;
    
    Salida salida;
    
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    ConMascara conMascara = new ConMascara();
    BuscaPresupuesto buscaPresupuesto;
    
    Presupuesto presupuesto;
    Remito remito;
    List<Remito> remitoList;
    List<RemitoDetalle> productosList;
    List<RemitoDetalle> consumoList;
    List<RemitoDetalleTableObject> remitos;
    List<RemitoTableObject> remitosResult;
    
    public ModificaConsumido(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        this.prestacionCRUD = new PrestacionCRUD(conexion, id_empresa, empresa);
        this.tecnicoCRUD = new TecnicoCRUD(conexion, id_empresa, empresa);
        
        this.salida = new Salida(null, true);
        
        initComponents();
        
        // Modelo de tabla Remito
        modeloRemito = new RemitoTableModel();
        modeloRemito.getPropertiesFromDefaultModel(jTableRemito.getModel());
        jTableRemito.setModel(modeloRemito);
        sorterRemito = new TableRowSorter(modeloRemito);
        jTableRemito.setRowSorter(sorterRemito);
        
        modeloProducto = (DefaultTableModel) this.jTablaProducto.getModel();
        jTablaProducto.setModel(modeloProducto);
        sorterProducto = new TableRowSorter(modeloProducto);
        jTablaProducto.setRowSorter (sorterProducto);
        jTablaProducto.setDefaultRenderer(Object.class, new RowColorRenderer());
        
        
        TableColumn dateCol = jTablaProducto.getColumnModel().getColumn(Utiles.getColumnByName(jTablaProducto, "vencimiento"));
        JDateChooser date = new JDateChooser("dd/MM/20yy", "##/##/####", '_');
        date.setMinSelectableDate(new Date());
        dateCol.setCellEditor(new DateCellEditor(date));          
        
        modeloConsumo = (DefaultTableModel) this.jTablaConsumo.getModel();
        jTablaConsumo.setModel(modeloConsumo);
        sorterConsumo = new TableRowSorter(modeloConsumo);
        jTablaConsumo.setRowSorter (sorterConsumo);
        
        modeloProducto.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE){
                    if (e.getColumn() != -1 && e.getFirstRow() < modeloProducto.getRowCount()){
                        String columnName = modeloProducto.getColumnName(e.getColumn());
                        
                        if (columnName.equals("color")) return;
                        
                        Object val = Utiles.valueAt(modeloProducto, e.getFirstRow(), columnName);
                        Object id_stockDetalle = Utiles.valueAt(modeloProducto, e.getFirstRow(), "id_stockdetalle");
                        Integer consumoIdColumn = Utiles.getColumnByName(modeloConsumo, "id_stockdetalle");
                        Integer consumoRow = Utiles.getRowByValue(modeloConsumo, consumoIdColumn, id_stockDetalle);
                        if (consumoRow != -1)
                            modeloConsumo.setValueAt(val, consumoRow, Utiles.getColumnByName(modeloConsumo, columnName));                    
                    }
                }
            }
        });        
        
        modeloConsumo.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getFirstRow() == e.getLastRow() &&
                        e.getType() == TableModelEvent.INSERT){
                    Object id_stockDetalle = Utiles.valueAt(modeloConsumo, e.getFirstRow(), "id_stockdetalle");
                    Integer row = Utiles.getRowByValue(modeloProducto, "id_stockdetalle", id_stockDetalle);
                    modeloProducto.setValueAt("green", row, Utiles.getColumnByName(modeloProducto, "color"));
                    jTablaProducto.repaint();
                }
            }
        });
       
        modeloProductoCantidad = (DefaultTableModel) this.jTableProductoCantidad.getModel();
        jTableProductoCantidad.setModel(modeloProductoCantidad);
        
        modeloConsumoCantidad = (DefaultTableModel) this.jTableConsumoCantidad.getModel();
        jTableConsumoCantidad.setModel(modeloConsumoCantidad);          
        
        limpia();
        setJTexFieldChangedProducto(this.jTxtBuscaProducto);
    }
    
    private void limpia(){
        this.jTxtId_presupuesto.setText("");
        this.jTxtBuscaProducto.setText("");
        this.jTxtObservaciones.setText("");
        clearTables();
    }
    
    private void clearTables(){
        clearRemitoTable();
        clearRemitoDetalleTables();
    }
    
    private void clearRemitoTable(){
        modeloRemito.removeAllRows();
        modeloRemito.fireTableDataChanged();    
    }
    
    private void clearRemitoDetalleTables(){        
        clearProductoTable();
        clearConsumoTable();
    }
    
    private void clearProductoTable(){
        modeloProducto.getDataVector().removeAllElements();
        modeloProducto.fireTableDataChanged();
        modeloProductoCantidad.getDataVector().removeAllElements();
        modeloProductoCantidad.fireTableDataChanged();
    }
    
    private void clearConsumoTable(){
        modeloConsumo.getDataVector().removeAllElements();
        modeloConsumo.fireTableDataChanged();
        modeloConsumoCantidad.getDataVector().removeAllElements();
        modeloConsumoCantidad.fireTableDataChanged();
    }    
    
    private void setJTexFieldChangedProducto(JTextField txt){
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
          sorterConsumo.setRowFilter(null);
        } else {
          sorterProducto.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          sorterConsumo.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTablaProducto.setRowSorter(sorterProducto);
          jTablaConsumo.setRowSorter(sorterConsumo);
        }
    }
    
    private void loadRemitos(Integer id_presupuesto){
        try{
            clearTables();
            remitosResult = RemitoMgr.getRemitosByPresupuesto(id_presupuesto);
            this.remitosResult.forEach(rto->{
                    modeloRemito.addRow(rto);
                });
            loadPresupuesto(id_presupuesto);

                if (remitosResult.isEmpty()){
                    Utiles.showMessage("No se han encontrado resultados.");
                }     
        }
        catch(Exception ex){
            Utiles.showErrorMessage(ex);
        }
        
        //remitoList = presupuesto.getAll(Remito.class);
        //loadRemitosTable();
    }
    
    private void loadPresupuesto(Integer id_presupuesto){
        this.presupuesto = Presupuesto.findById(id_presupuesto);
        this.jTxtObservaciones.setText(presupuesto.getString("observaciones"));
    }
    /*
    private void loadRemitosTable(){
        
        for (Remito rd : remitoList){         
            HashMap map = new HashMap();
            map.put("id_remito", rd.get("id_remito"));
            map.put("numcomp", rd.get("numComp"));
            map.put("fecha", rd.get("fecha"));
            map.put("entidad", rd.getEntidad().get("nombre"));
            map.put("recibido", rd.get("recibido"));
            modeloRemito.addRow(Utiles.fillTableObject(modeloRemito, map));
        }
        
    }
    */
    
    private void loadCantidadTable(DefaultTableModel model, List<RemitoDetalle> list){
        HashMap map = new HashMap();
        List<Integer> products = list.stream().map( p -> p.getInteger("id_producto")).collect(Collectors.toList());
        for (RemitoDetalle p : list){
            if (products.contains(p.get("id_producto"))){
                map.put("codigo", p.getProducto().get("codigo"));
                Integer cantidad = Collections.frequency(products, p.get("id_producto"));
                map.put("cantidad",cantidad);
                model.addRow(Utiles.fillTableObject(model, map));
                while(products.contains(p.get("id_producto"))) products.remove(p.get("id_producto"));
            }
        }
    }      
    
    private void loadProductos(Remito remito){
        productosList = remito.getEnviado();
        loadProductosTable();
        loadCantidadTable(modeloProductoCantidad, productosList);
    }
    
    private void loadProductosTable(){
        
        for (RemitoDetalle rd : productosList){
            Producto producto = rd.getProducto();
            HashMap map = new HashMap();
            map.put("id_stockdetalle", rd.get("id_stockdetalle"));
            map.put("codigo", producto.get("codigo"));
            map.put("gtin", producto.get("gtin"));
            map.put("nombre", producto.get("nombre"));
            map.put("pm", producto.get("pm"));
            map.put("lote", rd.get("lote"));
            map.put("serie", rd.get("serie"));
            map.put("vencimiento", rd.get("vencimiento"));
            map.put("cantidad", rd.get("cantidad"));
            map.put("remito", rd.parent(Remito.class).get("numComp"));
            modeloProducto.addRow(Utiles.fillTableObject(modeloProducto, map));
        }
    }
    
    private void loadConsumo(Remito remito){
        consumoList = remito.getConsumidoFromRemitoDetalle(false);
        loadConsumoTable();
        loadCantidadTable(modeloConsumoCantidad, consumoList);
    }  
    
    private void loadConsumoTable(){
        
        for (RemitoDetalle rd : consumoList){
            Producto producto = rd.getProducto();
            HashMap map = new HashMap();
            map.put("id_stockdetalle", rd.get("id_stockdetalle"));
            map.put("codigo", producto.get("codigo"));
            map.put("gtin", producto.get("gtin"));
            map.put("nombre", producto.get("nombre"));
            map.put("pm", producto.get("pm"));
            map.put("lote", rd.get("lote"));
            map.put("serie", rd.get("serie"));
            map.put("vencimiento", rd.get("vencimiento"));
            map.put("cantidad", rd.get("cantidad"));
            map.put("remito", rd.parent(Remito.class).get("numComp"));
            modeloConsumo.addRow(Utiles.fillTableObject(modeloConsumo, map));
        }
    }
    
    public Boolean isModifiedRecord(Integer row, RemitoDetalle rd){
        ArrayList<String> fields = new ArrayList<>(Arrays.asList("lote", "serie", "vencimiento"));
        for (String field : fields){
            Object o = Utiles.v(Utiles.valueAt(modeloProducto, row, field));
            String v = Utiles.v(rd.get(field));
            if (!o.equals(v))
                return true;
        }
        return false;
    }
    

    private boolean validaObligatorios(){
        return !this.jTxtId_presupuesto.getText().isEmpty();
    }
    
    private void cargarProductosComoConsumo(){
        int[] selected = jTablaProducto.getSelectedRows();
        for (int i = 0; i < selected.length; i++){
            int fila = jTablaProducto.convertRowIndexToModel(selected[i]);
            Object id_stockdetalle = modeloProducto.getValueAt(fila, Utiles.getColumnByName(modeloProducto, "id_stockdetalle"));
            if (!Utiles.existeEnModelo(modeloConsumo, Utiles.getColumnByName(modeloProducto, "id_stockdetalle"), id_stockdetalle)) {
                HashMap map = new HashMap();
                modeloConsumo.addRow(Utiles.fillTableObject(fila, modeloProducto, modeloConsumo, map));
            }
            /*
            else{
                JOptionPane.showMessageDialog(this, "Ya ha seleccionado este producto", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
                    */
        }
    
    }
    
    private void guardar(){
        try{
            if (jTablaProducto.isEditing())
                jTablaProducto.getCellEditor().stopCellEditing();

            if (validaObligatorios()){
                try{
                    String obs = this.jTxtObservaciones.getText().trim();
                    presupuesto.set("observaciones", obs);

                    LinkedList<RemitoDetalle> consumidoList = new LinkedList<>();

                    // ACTUALIZO LOS DATOS DE PRODUCTOS ENVIADOS
                    //List<RemitoDetalle> enviados = remito.getEnviado();
                    List<RemitoDetalle> needUpdate = new ArrayList<>();
                    for (int i = 0; i < modeloProducto.getRowCount(); i++){
                        Integer id_stockdetalle = Integer.parseInt(Utiles.valueAt(modeloProducto, i, "id_stockdetalle").toString());
                        RemitoDetalle rd = productosList.stream().filter(
                                remitoDetalle -> remitoDetalle.getId().equals(id_stockdetalle)
                                ).findFirst().orElse(null);
                        if (rd != null){
                            if (isModifiedRecord(i, rd)){
                                rd.set("lote", Utiles.valueAt(modeloProducto, i, "lote"));
                                rd.set("serie", Utiles.valueAt(modeloProducto, i, "serie"));
                                rd.set("vencimiento", DateUtil.stringToDate(Utiles.valueAt(modeloProducto, i, "vencimiento")));
                                needUpdate.add(rd);
                                //rd.saveIt();                        
                            }
                            // AGREGANDO A LA LISTA DE CONSUMIDO
                            if(Utiles.isInModel(modeloConsumo, "id_stockdetalle", rd.get("id_stockdetalle")))
                                consumidoList.add(rd);                   
                        }
                    }

                    // DELETING PRODUCTOS RECIBIDOS
                    remito.deleteRecibidoAndConsumido();

                    Iterator<RemitoDetalle> iter = productosList.iterator();
                    while(iter.hasNext()){
                        RemitoDetalle enviado = iter.next();
                        for (RemitoDetalle consumido : consumidoList){
                            if (consumido.equals(enviado)){
                                iter.remove();
                            }
                        }
                    }

                    PreparedStatement ps;
                    ps = Base.startBatch(RemitoDetalle.insertPreparedStatementString());
                    try{
                            Base.openTransaction();
                            // Guardo observaciones del presupuesto
                            System.out.println(presupuesto.saveIt());
                            

                            // ACTUALIZO LOS REMITODETALLE ENVIADOS QUE MODIFICARON SUS DATOS DE LOTE/SERIE
                            needUpdate.stream().forEach(rd -> rd.saveIt());                        

                            for (RemitoDetalle m : productosList){
                                m.set("id_stockdetalle", null);
                                m.set("dc", "D");
                                ArrayList params = new ArrayList();
                                RemitoDetalle
                                        .getMetaModel()
                                        .getColumnMetadata()
                                        .keySet()
                                        .stream()
                                        .filter(k -> !k.toLowerCase().equals(
                                                RemitoDetalle
                                                        .getMetaModel()
                                                        .getIdName().toLowerCase()))
                                        .collect(Collectors.toSet())
                                        .forEach(k -> params.add(m.get(k)));
                                Base.addBatch(ps, params.toArray());
                            }
                            Base.executeBatch(ps);
                            Base.commitTransaction();
                            
                            //RemitoMgr.saveConsumidoFromDetalle(remito.getInteger("id_remito"));
                            RemitoConsumidoDao sdd = new RemitoConsumidoDao();
                            List<RemitoConsumido> lrc = new ArrayList<>();
                            for (int i = 0; i < modeloConsumo.getRowCount(); ++i){
                                lrc.add(new RemitoConsumido((Integer)Utiles.valueAt(modeloConsumo, i, "id_stockdetalle"), remito.getInteger("id_remito")));
                            }
                            
                            sdd.insert(lrc);
                         
                            
                            LogHelper.logRemitoEvent(Utiles.LOG_EVENTO.MODIFICA_REMITO, (Integer) remito.getId());
                            
                        }catch(Exception e){
                            Utiles.showErrorMessage(e);
                            Base.rollbackTransaction();
                    }
                    finally{
                        ps.close();
                    }

                    clearRemitoDetalleTables();
                    clearRemitoTable();
                    productosList.clear();
                    consumoList.clear();
                    remito = null;

                    this.jTxtId_presupuesto.requestFocus();
                }catch (NumberFormatException ex){
                    Utiles.showErrorMessage(ex);
                }

            }else{
                JOptionPane.showMessageDialog(this,"Complete todos los datos obligatorios (*)", "Información",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e){
            System.out.println(e);
            Utiles.showErrorMessage(e);
        }    
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenuConsumido = new javax.swing.JPopupMenu();
        jMenuRemoverSeleccion = new javax.swing.JMenuItem();
        menuProductos = new javax.swing.JPopupMenu();
        consumirProducto = new javax.swing.JMenuItem();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();
        jBtnSalir = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTxtBuscaProducto = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jBtnGuardar = new javax.swing.JButton();
        jTxtId_presupuesto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaProducto = new ar.com.bosoft.RenderTablas.RXTable();
        jBtnBusca = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablaConsumo = new ar.com.bosoft.RenderTablas.RXTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableRemito = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableProductoCantidad = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableConsumoCantidad = new javax.swing.JTable();
        jButtonCalcularAllConsumidos = new javax.swing.JButton();
        jTextFieldPresupuestosCalculados = new javax.swing.JTextField();
        jButtonConsultaRemitos = new javax.swing.JButton();

        jMenuRemoverSeleccion.setText("remover seleccionados");
        jMenuRemoverSeleccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuRemoverSeleccionActionPerformed(evt);
            }
        });
        jPopupMenuConsumido.add(jMenuRemoverSeleccion);

        consumirProducto.setText("Cargar seleccionados como consumo...");
        consumirProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consumirProductoActionPerformed(evt);
            }
        });
        menuProductos.add(consumirProducto);

        setTitle("Modificación de consumido");
        setAutoscrolls(true);
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jTxtObservaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Observaciones"));
        jScrollPane5.setViewportView(jTxtObservaciones);

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

        jLabel7.setText("Turno");

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setFocusable(false);

        jTxtBuscaProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaProductoFocusGained(evt);
            }
        });

        jLabel8.setText("Buscar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBuscaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBuscaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)))
        );

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

        jTxtId_presupuesto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtId_presupuestoFocusLost(evt);
            }
        });
        jTxtId_presupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtId_presupuestoActionPerformed(evt);
            }
        });
        jTxtId_presupuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtId_presupuestoKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtId_presupuestoKeyPressed(evt);
            }
        });

        jTablaProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id_stockdetalle", "Código", "GTIN", "nombre", "PM", "Lote", "serie", "vencimiento", "cantidad", "Remito", "id_producto", "costo", "id_moneda", "id_zona", "id_remito", "indice", "color"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaProducto.setComponentPopupMenu(menuProductos);
        jTablaProducto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTablaProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaProductoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaProducto);
        if (jTablaProducto.getColumnModel().getColumnCount() > 0) {
            jTablaProducto.getColumnModel().getColumn(0).setMinWidth(50);
            jTablaProducto.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTablaProducto.getColumnModel().getColumn(0).setMaxWidth(50);
            jTablaProducto.getColumnModel().getColumn(8).setMinWidth(0);
            jTablaProducto.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTablaProducto.getColumnModel().getColumn(8).setMaxWidth(0);
            jTablaProducto.getColumnModel().getColumn(9).setMinWidth(0);
            jTablaProducto.getColumnModel().getColumn(9).setPreferredWidth(0);
            jTablaProducto.getColumnModel().getColumn(9).setMaxWidth(0);
            jTablaProducto.getColumnModel().getColumn(10).setMinWidth(0);
            jTablaProducto.getColumnModel().getColumn(10).setPreferredWidth(0);
            jTablaProducto.getColumnModel().getColumn(10).setMaxWidth(0);
            jTablaProducto.getColumnModel().getColumn(11).setMinWidth(0);
            jTablaProducto.getColumnModel().getColumn(11).setPreferredWidth(0);
            jTablaProducto.getColumnModel().getColumn(11).setMaxWidth(0);
            jTablaProducto.getColumnModel().getColumn(12).setMinWidth(0);
            jTablaProducto.getColumnModel().getColumn(12).setPreferredWidth(0);
            jTablaProducto.getColumnModel().getColumn(12).setMaxWidth(0);
            jTablaProducto.getColumnModel().getColumn(13).setMinWidth(0);
            jTablaProducto.getColumnModel().getColumn(13).setPreferredWidth(0);
            jTablaProducto.getColumnModel().getColumn(13).setMaxWidth(0);
            jTablaProducto.getColumnModel().getColumn(14).setMinWidth(0);
            jTablaProducto.getColumnModel().getColumn(14).setPreferredWidth(0);
            jTablaProducto.getColumnModel().getColumn(14).setMaxWidth(0);
            jTablaProducto.getColumnModel().getColumn(15).setMinWidth(0);
            jTablaProducto.getColumnModel().getColumn(15).setPreferredWidth(0);
            jTablaProducto.getColumnModel().getColumn(15).setMaxWidth(0);
            jTablaProducto.getColumnModel().getColumn(16).setMinWidth(0);
            jTablaProducto.getColumnModel().getColumn(16).setPreferredWidth(0);
            jTablaProducto.getColumnModel().getColumn(16).setMaxWidth(0);
        }

        jBtnBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/24x24/enabled/buscar.png"))); // NOI18N
        jBtnBusca.setBorderPainted(false);
        jBtnBusca.setContentAreaFilled(false);
        jBtnBusca.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnBusca.setFocusPainted(false);
        jBtnBusca.setFocusable(false);
        jBtnBusca.setRequestFocusEnabled(false);
        jBtnBusca.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/24x24/rollover/buscar.png"))); // NOI18N
        jBtnBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscaActionPerformed(evt);
            }
        });

        jTablaConsumo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id_stockdetalle", "Código", "GTIN", "nombre", "PM", "Lote", "serie", "vencimiento", "cantidad", "Remito", "id_producto", "costo", "id_moneda", "id_zona", "id_remito", "indice"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaConsumo.setComponentPopupMenu(jPopupMenuConsumido);
        jTablaConsumo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTablaConsumo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaConsumoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTablaConsumo);
        if (jTablaConsumo.getColumnModel().getColumnCount() > 0) {
            jTablaConsumo.getColumnModel().getColumn(0).setMinWidth(50);
            jTablaConsumo.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTablaConsumo.getColumnModel().getColumn(0).setMaxWidth(50);
            jTablaConsumo.getColumnModel().getColumn(8).setMinWidth(0);
            jTablaConsumo.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTablaConsumo.getColumnModel().getColumn(8).setMaxWidth(0);
            jTablaConsumo.getColumnModel().getColumn(9).setMinWidth(0);
            jTablaConsumo.getColumnModel().getColumn(9).setPreferredWidth(0);
            jTablaConsumo.getColumnModel().getColumn(9).setMaxWidth(0);
            jTablaConsumo.getColumnModel().getColumn(10).setMinWidth(0);
            jTablaConsumo.getColumnModel().getColumn(10).setPreferredWidth(0);
            jTablaConsumo.getColumnModel().getColumn(10).setMaxWidth(0);
            jTablaConsumo.getColumnModel().getColumn(11).setMinWidth(0);
            jTablaConsumo.getColumnModel().getColumn(11).setPreferredWidth(0);
            jTablaConsumo.getColumnModel().getColumn(11).setMaxWidth(0);
            jTablaConsumo.getColumnModel().getColumn(12).setMinWidth(0);
            jTablaConsumo.getColumnModel().getColumn(12).setPreferredWidth(0);
            jTablaConsumo.getColumnModel().getColumn(12).setMaxWidth(0);
            jTablaConsumo.getColumnModel().getColumn(13).setMinWidth(0);
            jTablaConsumo.getColumnModel().getColumn(13).setPreferredWidth(0);
            jTablaConsumo.getColumnModel().getColumn(13).setMaxWidth(0);
            jTablaConsumo.getColumnModel().getColumn(14).setMinWidth(0);
            jTablaConsumo.getColumnModel().getColumn(14).setPreferredWidth(0);
            jTablaConsumo.getColumnModel().getColumn(14).setMaxWidth(0);
            jTablaConsumo.getColumnModel().getColumn(15).setMinWidth(0);
            jTablaConsumo.getColumnModel().getColumn(15).setPreferredWidth(0);
            jTablaConsumo.getColumnModel().getColumn(15).setMaxWidth(0);
        }

        jTableRemito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "remito", "numero", "fecha", "entidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableRemito.getTableHeader().setReorderingAllowed(false);
        jTableRemito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableRemitoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTableRemito);

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        jLabel1.setText("Consumido:");

        jTableProductoCantidad.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jTableProductoCantidad);

        jTableConsumoCantidad.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(jTableConsumoCantidad);

        jButtonCalcularAllConsumidos.setText("Calcular todos los consumidos");
        jButtonCalcularAllConsumidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCalcularAllConsumidosActionPerformed(evt);
            }
        });

        jButtonConsultaRemitos.setText("Consultar");
        jButtonConsultaRemitos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultaRemitosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtId_presupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108)
                .addComponent(jButtonConsultaRemitos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonCalcularAllConsumidos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPresupuestosCalculados, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jBtnGuardar)
                                .addGap(101, 101, 101)
                                .addComponent(jBtnSalir)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnSalir)
                            .addComponent(jBtnGuardar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(jTxtId_presupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jBtnBusca, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButtonCalcularAllConsumidos)
                                .addComponent(jTextFieldPresupuestosCalculados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButtonConsultaRemitos)))
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed

        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jTxtBuscaProductoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaProductoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtBuscaProductoFocusGained

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        LoadingDialog loadingDialog = new LoadingDialog();

        SwingWorker sw = new ActionWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                
                loadingDialog.getProgressBar().setIndeterminate(true);
                Base.open(ActiveDatabase.getDataSource());
                guardar();
                loadingDialog.getDialog().setVisible(false);
                jBtnGuardar.enable();
                Base.close();
                return null;
            }

            @Override
            public void done(){
                loadingDialog.getProgressBar().setIndeterminate(false);
                loadingDialog.getLabel().setText("Successful");
                loadingDialog.getProgressBar().setValue(100); // 100%
            }
        };
        sw.execute();
        jBtnGuardar.disable();
        loadingDialog.getDialog().setVisible(true);
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jTxtId_presupuestoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtId_presupuestoFocusLost

    }//GEN-LAST:event_jTxtId_presupuestoFocusLost

    private void jBtnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscaActionPerformed
        if (!this.jTxtId_presupuesto.getText().isEmpty()){
            try{
                this.id_presupuesto = Integer.parseInt(this.jTxtId_presupuesto.getText().trim());
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this, "El número de turno es incorrecto", "Atención", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            presupuesto = Presupuesto.findById(this.id_presupuesto);            
            //this.loadRemitos(presupuesto);            
            this.jTxtId_presupuesto.setText(String.valueOf(id_presupuesto));
            this.jTxtObservaciones.setText(presupuesto.getString("observaciones"));
        }else{
            this.buscaPresupuesto = new BuscaPresupuesto(conexion,null,true,this.id_empresa,this.empresa, 6);
            this.buscaPresupuesto.setVisible(true);
            this.jTxtId_presupuesto.setText(buscaPresupuesto.getId_presupuesto().isEmpty() ? "" : buscaPresupuesto.getId_presupuesto());
            this.jTxtId_presupuestoFocusLost(null);
        }        

    }//GEN-LAST:event_jBtnBuscaActionPerformed

    private void jTablaProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaProductoMouseClicked
        try{
            if (evt.getClickCount() == 2) {
                cargarProductosComoConsumo();
            }
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }        
        
    }//GEN-LAST:event_jTablaProductoMouseClicked

    private void jTablaConsumoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsumoMouseClicked
        //if (evt.getClickCount() == 2) {
        //    jMenuRemoverSeleccionActionPerformed(null);
        //}
    }//GEN-LAST:event_jTablaConsumoMouseClicked

    private void jTableRemitoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRemitoMouseClicked
        // TODO add your handling code here:
        try{
            if (evt.getClickCount() == 2) {
                if (jTableRemito.getSelectedRowCount() > 0) {
                    clearRemitoDetalleTables();
                    int row = jTableRemito.convertRowIndexToModel(jTableRemito.getSelectedRow());
                    Integer id_remito = (Integer) modeloRemito.getValueAt(row, 0);
                    remito = Remito.findById(id_remito);
                    
                    loadProductos(remito);
                    loadConsumo(remito);

                }
            }
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }
    }//GEN-LAST:event_jTableRemitoMouseClicked

    private void jTxtId_presupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtId_presupuestoActionPerformed
        
    }//GEN-LAST:event_jTxtId_presupuestoActionPerformed

    private void jMenuRemoverSeleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuRemoverSeleccionActionPerformed
        while (jTablaConsumo.getSelectedRowCount() > 0 ) {
            Integer selectedRow = jTablaConsumo.convertRowIndexToModel(jTablaConsumo.getSelectedRow());
            Object id_stockdetalle = Utiles.valueAt(modeloConsumo,selectedRow,"id_stockdetalle");
            Integer row = Utiles.getRowByValue(modeloProducto, "id_stockdetalle", id_stockdetalle);
            modeloProducto.setValueAt(null, row, Utiles.getColumnByName(modeloProducto, "color"));
            jTablaProducto.repaint();            
            modeloConsumo.removeRow(selectedRow);
        }        
    }//GEN-LAST:event_jMenuRemoverSeleccionActionPerformed

    private void jButtonCalcularAllConsumidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCalcularAllConsumidosActionPerformed
        
        int respuesta = JOptionPane.showConfirmDialog(this, "Esta a punto de recalcular todo el consumido del sistema."
                + " Esta operación puede tardar algunos minutos ¿Esta seguro que desea continuar?",
                "MASA", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (respuesta == JOptionPane.NO_OPTION){
            return;
        }
        
        LoadingDialog loadingDialog = new LoadingDialog();

        SwingWorker sw = new ActionWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                try{
                loadingDialog.getProgressBar().setIndeterminate(true);
                
                DSLContext dsl = ActiveDatabase.getDSLContext();
                
                dsl.deleteFrom(Tables.REMITO_CONSUMIDO).execute();
                
                Result<Record> records = dsl.select(Tables.STOCKDETALLE.fields()).from(Tables.STOCKDETALLE)
                        .join(Tables.REMITO).on(
                                Tables.REMITO.ID_REMITO.eq(Tables.STOCKDETALLE.ID_REMITO)
                                        .and(Tables.REMITO.RECIBIDO.eq(Utiles.SI)
                                        )
                        )
                        .join(Tables.PRESUPUESTO).on(
                                Tables.PRESUPUESTO.ID_PRESUPUESTO.eq(Tables.REMITO.ID_PRESUPUESTO)
                                        .and(Tables.PRESUPUESTO.ESTADO.eq(Utiles.PRESUPUESTO_FINALIZADO)
                                        )
                        )
                        .orderBy(Tables.STOCKDETALLE.ID_REMITO)
                        .fetch();

                Map<Integer, Result<Record>> map = records.intoGroups(Tables.STOCKDETALLE.ID_REMITO);
                
                BatchBindStep batch = dsl.batch(dsl.insertInto(Tables.REMITO_CONSUMIDO).values(null,null));
                    
                map.forEach((k,v)->{
                    Result<StockdetalleRecord> d = v.into(Tables.STOCKDETALLE);
                    List<StockdetalleRecord> consumido = RemitoMgr.getConsumido(d);
                    consumido.forEach(c->{
                        batch.bind(c.getIdStockdetalle(), c.getIdRemito());
                    });
                });
                
                try{
                    batch.execute();
                }
                catch(Exception e){
                    Utiles.showErrorMessage(e);
                }

                loadingDialog.getDialog().setVisible(false);
                jButtonCalcularAllConsumidos.enable();
                }
                catch(Exception e){
                    Utiles.showErrorMessage(e);
                }                
                return null;
            }

            @Override
            public void done(){
                loadingDialog.getProgressBar().setIndeterminate(false);
                loadingDialog.getLabel().setText("Successful");
                loadingDialog.getProgressBar().setValue(100); // 100%
            }
        };
        sw.execute();
        jButtonCalcularAllConsumidos.disable();
        loadingDialog.getDialog().setVisible(true);        

    }//GEN-LAST:event_jButtonCalcularAllConsumidosActionPerformed

    private void consumirProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consumirProductoActionPerformed
        cargarProductosComoConsumo();
    }//GEN-LAST:event_consumirProductoActionPerformed

    private void jButtonConsultaRemitosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultaRemitosActionPerformed
        loadRemitos(Integer.parseInt(this.jTxtId_presupuesto.getText().trim()));
        /*
        JInternalFrame frame = new JInternalFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MarchaNegociosPanel());
        Principal.muestra(frame);
        */
    }//GEN-LAST:event_jButtonConsultaRemitosActionPerformed

    private void jTxtId_presupuestoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtId_presupuestoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtId_presupuestoKeyPressed

    private void jTxtId_presupuestoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtId_presupuestoKeyTyped
        char c = evt.getKeyChar();
        if(c < '0' || c > '9') evt.consume();
    }//GEN-LAST:event_jTxtId_presupuestoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem consumirProducto;
    private javax.swing.JButton jBtnBusca;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jButtonCalcularAllConsumidos;
    private javax.swing.JButton jButtonConsultaRemitos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuItem jMenuRemoverSeleccion;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenuConsumido;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private ar.com.bosoft.RenderTablas.RXTable jTablaConsumo;
    private ar.com.bosoft.RenderTablas.RXTable jTablaProducto;
    private javax.swing.JTable jTableConsumoCantidad;
    private javax.swing.JTable jTableProductoCantidad;
    private javax.swing.JTable jTableRemito;
    private javax.swing.JTextField jTextFieldPresupuestosCalculados;
    private javax.swing.JTextField jTxtBuscaProducto;
    private javax.swing.JTextField jTxtId_presupuesto;
    private javax.swing.JTextArea jTxtObservaciones;
    private javax.swing.JPopupMenu menuProductos;
    // End of variables declaration//GEN-END:variables
}
