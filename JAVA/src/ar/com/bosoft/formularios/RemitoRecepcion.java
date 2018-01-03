/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;


import ar.com.bosoft.DataSources.ConsumidoDataSource;
import ar.com.bosoft.RenderTablas.DateCellEditor;
import ar.com.bosoft.RenderTablas.DateRenderer;
import ar.com.bosoft.RenderTablas.EstadoTurno;
import ar.com.bosoft.RenderTablas.RowColorRenderer;
import ar.com.bosoft.Utilidades.DateUtil;
import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.PrestacionCRUD;
import ar.com.bosoft.crud.TecnicoCRUD;
import ar.com.bosoft.data.PrestacionData;
import ar.com.bosoft.data.TecnicoData;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.clases.VerticalLabelUI;
import ar.com.bosoft.conexion.ActiveDatabase;
import com.toedter.calendar.JDateChooser;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import matera.db.Presupuesto;
import matera.db.Producto;
import matera.db.Remito;
import matera.db.RemitoDetalle;
import matera.db.dao.RemitoConsumidoDao;
import matera.db.managers.RemitoMgr;
import matera.gui.dialog.LoadingDialog;
import matera.helpers.LogHelper;
import matera.jooq.tables.daos.StockdetalleDao;
import matera.jooq.tables.pojos.RemitoConsumido;
import matera.workers.ActionWorker;
import org.javalite.activejdbc.Base;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jscroll.widgets.JScrollInternalFrame;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class RemitoRecepcion extends JScrollInternalFrame {
    Conexion conexion;
    int id_empresa, id_remito;
    String empresa, usuario, estado;
    
    PrestacionCRUD prestacionCRUD;
    TecnicoCRUD tecnicoCRUD;
    
    ArrayList prestacionArray, tecnicoArray;
    
    ResultSet rsConsulta, rsRemitoDetalle;
    DefaultTableModel modeloConsulta, modeloProducto, modeloConsumo;
    TableRowSorter sorterConsulta;
    TableRowSorter sorterProducto, sorterConsumo;
    TableCellRenderer tableCellRenderer;
    
    Salida salida;
    
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
    EstadoTurno estadoTurno;
    
    public RemitoRecepcion(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        this.prestacionCRUD = new PrestacionCRUD(conexion, id_empresa, empresa);
        this.tecnicoCRUD = new TecnicoCRUD(conexion, id_empresa, empresa);
        
        this.salida = new Salida(null, true);
         
        this.estadoTurno = new EstadoTurno();
        
        initComponents();
        llenaComboPrestacion();
        llenaComboTecnico();
        
        jTablaConsulta.setDefaultRenderer(Object.class, estadoTurno);
        modeloConsulta = (DefaultTableModel) this.jTablaConsulta.getModel();
        jTablaConsulta.setModel(modeloConsulta);
        sorterConsulta = new TableRowSorter(modeloConsulta);
        jTablaConsulta.setRowSorter (sorterConsulta);
        
        tableCellRenderer = new DateRenderer();
        jTablaConsulta.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        
        modeloProducto = (DefaultTableModel) this.jTablaProducto.getModel();
        jTablaProducto.setModel((DefaultTableModel) modeloProducto);
        sorterProducto = new TableRowSorter((TableModel) modeloProducto);
        jTablaProducto.setRowSorter (sorterProducto);
        jTablaProducto.setDefaultRenderer(Object.class, new RowColorRenderer());
        
        TableColumn dateCol = jTablaProducto.getColumnModel().getColumn(Utiles.getColumnByName(jTablaProducto, "vencimiento"));
        JDateChooser date = new JDateChooser("dd/MM/20yy", "##/##/####", '_');
        date.setMinSelectableDate(new Date());
        dateCol.setCellEditor(new DateCellEditor(date));        
        
        modeloConsumo = (DefaultTableModel) this.jTablaConsumo.getModel();
        jTablaConsumo.setModel((TableModel) modeloConsumo);
        
        sorterConsumo = new TableRowSorter((DefaultTableModel) modeloConsumo);
        jTablaConsumo.setRowSorter(sorterConsumo);
        
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
        
        dateCol = jTablaConsumo.getColumnModel().getColumn(Utiles.getColumnByName(jTablaConsumo, "vencimiento"));
        date = new JDateChooser("dd/MM/20yy", "##/##/####", '_');
        date.setMinSelectableDate(new Date());
        dateCol.setCellEditor(new DateCellEditor(date));        
        
        limpia();
        consulta();
        setJTexFieldChangedConsulta(this.jTxtBuscaConsulta);
        setJTexFieldChangedProducto(this.jTxtBuscaProducto);        
    }
    
    private void llenaComboPrestacion(){
        this.prestacionArray = prestacionCRUD.consulta(true);
        Iterator i = prestacionArray.iterator();
        while (i.hasNext()){
            PrestacionData tmp = (PrestacionData) i.next();
            this.jComboPrestacion.addItem(tmp.getNombre());
        }
    }
    
    private void llenaComboTecnico(){
        this.tecnicoArray = tecnicoCRUD.consulta(true);
        Iterator i = tecnicoArray.iterator();
        while (i.hasNext()){
            TecnicoData tmp = (TecnicoData) i.next();
            this.jComboTecnico.addItem(tmp.getNombre());
        }
    }

    private void limpia(){
        this.jTxtBuscaConsulta.setText("");
        this.jLblTurno.setText("");
        this.jLblNumero.setText("");
        this.jLblFecha.setText("");
        this.jLblOrigen.setText("");
        this.jLblProfesional.setText("");
        this.jLblPaciente.setText("");
        this.jLblEntidad.setText("");
        this.jLblLugar.setText("");
        this.jLblCajas.setText("");
        this.jComboPrestacion.setSelectedIndex(-1);
        this.jComboTecnico.setSelectedIndex(-1);
        this.jComboCirugia.setSelectedIndex(-1);
        this.jComboCirugia.setEnabled(true);
        this.jTxtBuscaProducto.setText("");
        modeloProducto.getDataVector().removeAllElements();
        modeloProducto.fireTableDataChanged();
        this.jTxtObservaciones.setText("");
    }
    
    private void consulta(){
        try {
            modeloConsulta.getDataVector().removeAllElements();
            modeloConsulta.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            parametros.add((long) 0);
            parametros.add((long) 0);
            parametros.add(Principal.usuarioData.getId_zonaSistema());
            parametros.add(this.id_empresa); 
            rsConsulta = conexion.procAlmacenado("consultaMapaRecepcion", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsConsulta.beforeFirst();
            while (rsConsulta.next()){
                int turno = rsConsulta.getInt("turno");
                Date fecha = rsConsulta.getDate("fechaCirugia");
                String remito = rsConsulta.getString("remito");
                String zona = rsConsulta.getString("zona");
                String profesional = rsConsulta.getString("profesional");
                String prestacion = rsConsulta.getString("prestacion");
                String tecnico = rsConsulta.getString("tecnico");
                int id = rsConsulta.getInt("id_remito");
                String observaciones = rsConsulta.getString("observaciones");
                String paciente = rsConsulta.getString("paciente");
                String estadoPresupuesto = rsConsulta.getString("estado");
                String cajas = rsConsulta.getString("cajas");
                String entidad = rsConsulta.getString("entidad");
                String lugar = rsConsulta.getString("lugar");
                
                
                Object[] fila = {turno, fecha, remito, zona, profesional, 
                                prestacion, tecnico, id, observaciones, paciente, 
                                estadoPresupuesto, cajas, entidad, lugar};
                //if(!estadoPresupuesto.equals("P")){
                    modeloConsulta.addRow(fila);
                //}
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        
    }
    
    private void setJTexFieldChangedConsulta(JTextField txt){
        DocumentListener documentListener = new DocumentListener() {
 
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                printItConsulta(documentEvent);
            }
 
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                printItConsulta(documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                printItConsulta(documentEvent);
            }
        };
        txt.getDocument().addDocumentListener(documentListener); 
    }
 
    private void printItConsulta(DocumentEvent documentEvent) {
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
          jTablaProducto.setRowSorter(sorterProducto);
          sorterConsumo.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTablaConsumo.setRowSorter(sorterConsumo);
        }
    }
    
    private void loadEnviados(Remito remito){
        List<RemitoDetalle> productosEnviados = remito.getEnviado();
        loadProductosTable(productosEnviados);
    }
    
    private void loadProductosTable(List<RemitoDetalle> productosEnviados){
        
        for (RemitoDetalle rd : productosEnviados){
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
    
    private void imprimeConsumo(List<RemitoDetalle> consumido, int tipoSalida, boolean previa){
        try {
            Map param=new HashMap();
            
            Date fecha = this.jLblFecha.getText().isEmpty() ? null : formatter.parse(this.jLblFecha.getText());
            param.put("id_presupuesto", Integer.parseInt(this.jLblTurno.getText()));
            param.put("fechaCx", fecha);
            param.put("remito", this.jLblNumero.getText());
            param.put("profesional", this.jLblProfesional.getText());
            param.put("paciente", this.jLblPaciente.getText());
            param.put("entidad", this.jLblEntidad.getText());
            param.put("lugarCx", this.jLblLugar.getText());
            param.put("tipoPrestacion", this.jComboPrestacion.getSelectedItem().toString());
            param.put("tecnico", (this.jComboTecnico.getSelectedIndex() >= 0 ? this.jComboTecnico.getSelectedItem().toString() : ""));
            param.put("observaciones", this.jTxtObservaciones.getText().trim());
            param.put("empresa", this.empresa);
            param.put("previa", previa);
            param.put("fechaCarga", new Date());
            param.put("cajas", this.jLblCajas.getText());
            
            ConsumidoDataSource dataConsumido = new ConsumidoDataSource();
            
            Remito.fillConsumidoReport(dataConsumido, consumido);
            
            if (!dataConsumido.isEmpty()) {
                Reporte reporte = new Reporte();
                reporte.startReport("Consumido", param, dataConsumido, tipoSalida, salida.getRutaArchivo(), salida.getImpresora(), salida.getCopias());
            }else{
                JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
            
                
        } catch (Exception ex) {
            Utiles.showErrorMessage(ex);
        }
    }
    
    private boolean validaObligatorios(){
        return !this.jLblTurno.getText().isEmpty() &&
                this.jComboPrestacion.getSelectedIndex() >= 0 &&
                (this.jComboTecnico.isEnabled() ? this.jComboTecnico.getSelectedIndex() >= 0 : true) &&
                this.jComboCirugia.getSelectedIndex() >= 0;
    }
    
    private List<RemitoDetalle> getConsumidoFromTables(){
        ArrayList ids = new ArrayList();
        for (int i = 0; i < modeloConsumo.getRowCount(); i++){
            ids.add(Utiles.valueAt(modeloConsumo, i, "id_stockdetalle"));
        }
        List<RemitoDetalle> consumido = RemitoDetalle.findByIds(ids);
        for (RemitoDetalle rd : consumido){
            String lote = Utiles.valueAt(modeloConsumo, Utiles.getRowByValue(modeloConsumo, "id_stockdetalle", rd.getId()), "lote").toString();
            String serie = Utiles.valueAt(modeloConsumo, Utiles.getRowByValue(modeloConsumo, "id_stockdetalle", rd.getId()), "serie").toString();
            String vencimiento = Utiles.valueAt(modeloConsumo, Utiles.getRowByValue(modeloConsumo, "id_stockdetalle", rd.getId()), "vencimiento").toString();
            rd.set("lote",lote);
            rd.set("serie",serie);
            rd.set("vencimiento",vencimiento);
        }  
        return consumido;
    }
    
    private void clearTables(){
        modeloProducto.getDataVector().removeAllElements();
        modeloProducto.fireTableDataChanged();
        
        modeloConsumo.getDataVector().removeAllElements();
        modeloConsumo.fireTableDataChanged();
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
    
    private void guardar(){
        try{
        if (jTablaProducto.isEditing())
            jTablaProducto.getCellEditor().stopCellEditing();
        
        // DESHABILITAMOS EL BOTON
        this.jBtnGuardar.disable();
                
        if (validaObligatorios()){
            try{
                int id_presupuesto = Integer.parseInt(this.jLblTurno.getText());
            
                String estadoElegido = "";
                switch (this.jComboCirugia.getSelectedItem().toString()){
                    case "Realizada":
                        estadoElegido = "Z";
                        break;
                    case "Suspendida":
                        estadoElegido = "S";
                        break;
                    case "Anulada":
                        estadoElegido = "N";
                        break;
                }
                
                int consumoCantidad = modeloConsumo.getRowCount();
                if (estadoElegido.equals("Z")) {
                    if (consumoCantidad == 0) {
                        int respuesta = JOptionPane.showConfirmDialog(this, "No hay consumos cargados.\n¿Desea continuar?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (respuesta == JOptionPane.NO_OPTION) {
                            return;
                        }
                    }
                } else{
                    if (consumoCantidad > 0) {
                        JOptionPane.showMessageDialog(this, "No se puede cargar consumo a una cirugia SUSPENDIDA/ANULADA");
                        return;
                    }                    
                }                
                
                int id_tecnico = 0;
                if (this.jComboTecnico.isEnabled()){
                    TecnicoData t = (TecnicoData) tecnicoArray.get(this.jComboTecnico.getSelectedIndex());
                    id_tecnico = t.getId_tecnico();
                }

                PrestacionData p = (PrestacionData) prestacionArray.get(this.jComboPrestacion.getSelectedIndex());
                int id_prestacion = p.getId_prestacion();
                
                //String obs = this.jTxtObservaciones.getText().trim();
                
                Presupuesto presupuesto = Presupuesto.findById(id_presupuesto);
                Remito remito = Remito.findById(this.id_remito);
                
                LinkedList<RemitoDetalle> consumidoList = new LinkedList<>();
                
                // ACTUALIZO LOS DATOS DE PRODUCTOS ENVIADOS
                List<RemitoDetalle> enviados = remito.getEnviado();
                List<RemitoDetalle> needUpdate = new ArrayList<>();
                for (int i = 0; i < modeloProducto.getRowCount(); i++){
                    Integer id_stockdetalle = Integer.parseInt(Utiles.valueAt(modeloProducto, i, "id_stockdetalle").toString());
                    RemitoDetalle rd = enviados.stream().filter(
                            remitoDetalle -> remitoDetalle.getId().equals(id_stockdetalle)
                            ).findFirst().orElse(null);
                    if (rd != null){
                        if (isModifiedRecord(i, rd)){
                            rd.set("lote", Utiles.valueAt(modeloProducto, i, "lote"));
                            rd.set("serie", Utiles.valueAt(modeloProducto, i, "serie"));
                            rd.set("vencimiento", DateUtil.stringToDate(Utiles.valueAt(modeloProducto, i, "vencimiento")));
                            needUpdate.add(rd);                  
                        }
                        // AGREGANDO A LA LISTA DE CONSUMIDO
                        if(Utiles.isInModel(modeloConsumo, "id_stockdetalle", rd.get("id_stockdetalle")))
                            consumidoList.add(rd);                   
                    }
                }
                
                // DELETING PRODUCTOS RECIBIDOS
                remito.deleteRecibidoAndConsumido();
                
                Iterator<RemitoDetalle> iter = enviados.iterator();
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
                        
                        // ACTUALIZO LOS REMITODETALLE ENVIADOS QUE MODIFICARON SUS DATOS DE LOTE/SERIE
                        needUpdate.stream().forEach(rd -> rd.saveIt());
                        
                        for (RemitoDetalle m : enviados){
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
                        
                        // RECIBO EL REMITO
                        remito.set("recibido", "S");
                        Date today = new Date();
                        Date cirugiaDate = DateUtil.stringToDate(presupuesto.get("fechaCirugia"));
                        if (this.estado.equals("C") && today.before(cirugiaDate)){
                            remito.set("fechaConsumido", cirugiaDate);
                        }
                        else{
                            remito.set("fechaConsumido", today);
                        }
                        remito.set("usuario", usuario);
                        remito.set("fechaReal", new Timestamp(System.currentTimeMillis()));
                        remito.saveIt();
                        LogHelper.logRemitoEvent((Integer) remito.getId(), Utiles.LOG_EVENTO.RECIBE_REMITO);
                        
                        // MODIFICO EL PRESUPUESTO SI ESTA EN ESTADO CONFIRMADO (PUEDE SER UN PRESUPUESTO FINALIZADO CON MAS DE UN REMITO)
                        if (this.estado.equals("C")) {
                            presupuesto.set("estado", estadoElegido);
                            if(id_tecnico != 0){
                                presupuesto.set("id_tecnico", id_tecnico);
                            }
                            presupuesto.set("id_prestacion", id_prestacion);
                            if(!this.jTxtObservaciones.getText().isEmpty()){// && this.jTxtObservaciones.getText().equals(modeloConsulta.getValueAt(indice, 8).toString())){
                                presupuesto.set("observaciones", this.jTxtObservaciones.getText().trim());
                            }
                            
                            presupuesto.set("usuario", this.usuario);
                            presupuesto.set("fechaReal", new Timestamp(System.currentTimeMillis()));
                            presupuesto.saveIt();
                            
                            switch(estadoElegido){
                                case "S":
                                    LogHelper.logPresupuestoEvent((Integer) presupuesto.getId(), Utiles.LOG_EVENTO.SUSPENDE_PRESUPUESTO);
                                    break;
                                case "N":
                                    LogHelper.logPresupuestoEvent((Integer) presupuesto.getId(), Utiles.LOG_EVENTO.ANULA_PRESUPUESTO);
                                    break;
                                case "Z":
                                    presupuesto.rmToMayorProfesional(DSL.using(Base.connection(), SQLDialect.MYSQL));
                                    LogHelper.logPresupuestoEvent((Integer) presupuesto.getId(), Utiles.LOG_EVENTO.FINALIZA_PRESUPUESTO);
                                    break;
                            }
                        }
                        
                        Base.commitTransaction();
                        
                        //RemitoMgr.saveConsumidoFromDetalle(remito.getInteger("id_remito"));
                        RemitoConsumidoDao sdd = new RemitoConsumidoDao();
                        List<RemitoConsumido> lrc = new ArrayList<>();
                        for (int i = 0; i < modeloConsumo.getRowCount(); ++i){
                            lrc.add(new RemitoConsumido((Integer)Utiles.valueAt(modeloConsumo, i, "id_stockdetalle"), id_remito));
                        }
                        sdd.insert(lrc);
                        
                        if (consumoCantidad > 0) {
                            int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea imprimir el reporte de consumido?", "BOSOFT", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (respuesta == JOptionPane.YES_OPTION){
                                this.salida.setVisible(true);
                                if (salida.getSiNo()){
                                    imprimeConsumo(remito.getConsumidoFromRemitoDetalle(true), salida.getTipoSalida(), false);
                                }
                            }
                        }

                        limpia();
                        consulta();
                        this.jTxtBuscaConsulta.requestFocus();                        
                    }catch(Exception e){
                        Utiles.showErrorMessage(e);
                        Base.rollbackTransaction();
                }
                finally{
                    ps.close();
                }
            }catch (Exception ex){
                Utiles.showErrorMessage(ex);
            }
        
        }else{
            JOptionPane.showMessageDialog(this,"Complete todos los datos obligatorios (*)", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }
        finally{
            this.jBtnGuardar.enable();
        }    
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenuEnviados = new javax.swing.JPopupMenu();
        jMenuItemLoad = new javax.swing.JMenuItem();
        jPopupMenuConsumidos = new javax.swing.JPopupMenu();
        jMenuItemRemove = new javax.swing.JMenuItem();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jBtnGuardar = new javax.swing.JButton();
        jBtnPrevia = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablaConsumo = new ar.com.bosoft.RenderTablas.RXTable();
        jLabel17 = new javax.swing.JLabel("CONSUMIDOS");
        jLabel17.setUI(new VerticalLabelUI(false));
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablaProducto = new ar.com.bosoft.RenderTablas.RXTable();
        jLabel16 = new javax.swing.JLabel("ENVIADOS");
        jLabel16.setUI(new VerticalLabelUI(false));
        jPanel2 = new javax.swing.JPanel();
        jTxtBuscaProducto = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLblFecha = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLblNumero = new javax.swing.JLabel();
        jLblOrigen = new javax.swing.JLabel();
        jLblProfesional = new javax.swing.JLabel();
        jLblTurno = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboPrestacion = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jComboTecnico = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jComboCirugia = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jLblPaciente = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLblCajas = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLblEntidad = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLblLugar = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTxtBuscaConsulta = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        jMenuItemLoad.setText("Cargar");
        jMenuItemLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLoadActionPerformed(evt);
            }
        });
        jPopupMenuEnviados.add(jMenuItemLoad);

        jMenuItemRemove.setText("Remover");
        jMenuItemRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRemoveActionPerformed(evt);
            }
        });
        jPopupMenuConsumidos.add(jMenuItemRemove);

        setTitle("Recepción de productos");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(1200, 700));

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

        jBtnPrevia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/preview.png"))); // NOI18N
        jBtnPrevia.setText("Vista previa");
        jBtnPrevia.setBorderPainted(false);
        jBtnPrevia.setContentAreaFilled(false);
        jBtnPrevia.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnPrevia.setFocusPainted(false);
        jBtnPrevia.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnPrevia.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/preview.png"))); // NOI18N
        jBtnPrevia.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/preview.png"))); // NOI18N
        jBtnPrevia.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnPrevia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPreviaActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(jBtnPrevia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnGuardar)
                .addGap(283, 283, 283)
                .addComponent(jBtnSalir)
                .addGap(55, 55, 55))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnSalir))
                .addGap(245, 245, 245))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBtnPrevia)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jTxtObservaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Observaciones"));
        jScrollPane5.setViewportView(jTxtObservaciones);

        jTablaConsumo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id_stockdetalle", "Código", "GTIN", "Nombre", "PM", "Lote", "serie", "vencimiento", "cantidad", "devuelto", "id_producto", "costo", "id_moneda", "id_zona", "indice"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaConsumo.setComponentPopupMenu(jPopupMenuConsumidos);
        jTablaConsumo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaConsumoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTablaConsumo);
        if (jTablaConsumo.getColumnModel().getColumnCount() > 0) {
            jTablaConsumo.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTablaConsumo.getColumnModel().getColumn(0).setMaxWidth(150);
            jTablaConsumo.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTablaConsumo.getColumnModel().getColumn(1).setMaxWidth(150);
            jTablaConsumo.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTablaConsumo.getColumnModel().getColumn(2).setMaxWidth(150);
            jTablaConsumo.getColumnModel().getColumn(4).setPreferredWidth(75);
            jTablaConsumo.getColumnModel().getColumn(4).setMaxWidth(150);
            jTablaConsumo.getColumnModel().getColumn(5).setPreferredWidth(100);
            jTablaConsumo.getColumnModel().getColumn(5).setMaxWidth(200);
            jTablaConsumo.getColumnModel().getColumn(6).setMinWidth(100);
            jTablaConsumo.getColumnModel().getColumn(6).setPreferredWidth(100);
            jTablaConsumo.getColumnModel().getColumn(6).setMaxWidth(100);
            jTablaConsumo.getColumnModel().getColumn(7).setPreferredWidth(75);
            jTablaConsumo.getColumnModel().getColumn(7).setMaxWidth(120);
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
        }

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("CONSUMIDO");

        jTablaProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id_stockdetalle", "Código", "GTIN", "Nombre", "PM", "Lote", "serie", "vencimiento", "cantidad", "id_producto", "costo", "id_moneda", "id_zona", "indice", "color"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true, true, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaProducto.setComponentPopupMenu(jPopupMenuEnviados);
        jTablaProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaProductoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTablaProducto);
        if (jTablaProducto.getColumnModel().getColumnCount() > 0) {
            jTablaProducto.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTablaProducto.getColumnModel().getColumn(0).setMaxWidth(150);
            jTablaProducto.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTablaProducto.getColumnModel().getColumn(1).setMaxWidth(150);
            jTablaProducto.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTablaProducto.getColumnModel().getColumn(2).setMaxWidth(150);
            jTablaProducto.getColumnModel().getColumn(4).setPreferredWidth(75);
            jTablaProducto.getColumnModel().getColumn(4).setMaxWidth(150);
            jTablaProducto.getColumnModel().getColumn(5).setPreferredWidth(100);
            jTablaProducto.getColumnModel().getColumn(5).setMaxWidth(200);
            jTablaProducto.getColumnModel().getColumn(6).setMinWidth(100);
            jTablaProducto.getColumnModel().getColumn(6).setPreferredWidth(100);
            jTablaProducto.getColumnModel().getColumn(6).setMaxWidth(100);
            jTablaProducto.getColumnModel().getColumn(7).setPreferredWidth(75);
            jTablaProducto.getColumnModel().getColumn(7).setMaxWidth(120);
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
        }

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("ENVIADOS");

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setFocusable(false);

        jTxtBuscaProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaProductoFocusGained(evt);
            }
        });

        jLabel15.setText("Buscar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBuscaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBuscaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)))
        );

        jTablaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Turno", "Fecha Cx", "Remito", "Zona", "Profesional", "prestacion", "tecnico", "id_remito", "observaciones", "Paciente", "estado", "cajas", "entidad", "lugar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
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
            jTablaConsulta.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTablaConsulta.getColumnModel().getColumn(0).setMaxWidth(100);
            jTablaConsulta.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(1).setMaxWidth(100);
            jTablaConsulta.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTablaConsulta.getColumnModel().getColumn(2).setMaxWidth(150);
            jTablaConsulta.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTablaConsulta.getColumnModel().getColumn(3).setMaxWidth(200);
            jTablaConsulta.getColumnModel().getColumn(5).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(5).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(6).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(6).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(6).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(7).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(7).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(7).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(8).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(8).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(11).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(11).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(11).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(12).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(12).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(12).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(13).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(13).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(13).setMaxWidth(0);
        }

        jLabel1.setText("Remito");

        jLabel2.setText("Zona");

        jLabel3.setText("Profesional");

        jLblFecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblFecha.setText("...");

        jLabel5.setText("Fecha de cirugía");

        jLblNumero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblNumero.setText("...");

        jLblOrigen.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblOrigen.setText("...");

        jLblProfesional.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblProfesional.setText("...");

        jLblTurno.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblTurno.setText("...");

        jLabel7.setText("Turno");

        jLabel9.setText("* Prestación técnica");

        jComboPrestacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboPrestacionActionPerformed(evt);
            }
        });

        jLabel11.setText("* Técnico");

        jLabel4.setText("* Cirugía");

        jComboCirugia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Realizada", "Suspendida", "Anulada" }));
        jComboCirugia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboCirugiaActionPerformed(evt);
            }
        });

        jLabel12.setText("Paciente");

        jLblPaciente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblPaciente.setText("...");

        jLabel13.setText("Cajas");

        jLblCajas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblCajas.setText("...");

        jLabel8.setText("Entidad");

        jLblEntidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblEntidad.setText("...");

        jLabel14.setText("Lugar cx.");

        jLblLugar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblLugar.setText("...");

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setFocusable(false);

        jTxtBuscaConsulta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaConsultaFocusGained(evt);
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
                .addComponent(jTxtBuscaConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBuscaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane3))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLblProfesional, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLblEntidad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLblTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLblNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLblOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLblPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLblLugar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jComboPrestacion, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLblCajas, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2))))
                .addGap(85, 85, 85))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblTurno)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLblFecha)
                    .addComponent(jLabel1)
                    .addComponent(jLblNumero)
                    .addComponent(jLabel2)
                    .addComponent(jLblOrigen))
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLblEntidad)
                    .addComponent(jLabel14)
                    .addComponent(jLblLugar))
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLblProfesional)
                    .addComponent(jLabel12)
                    .addComponent(jLblPaciente))
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLblCajas))
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboPrestacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jComboTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jComboCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(262, 262, 262))
        );

        jLabel16.getAccessibleContext().setAccessibleName("E\nN\nV\nI\nA\nD\nO\nS");

        jScrollPane4.setViewportView(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1174, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTxtBuscaConsultaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaConsultaFocusGained
        this.jTxtBuscaConsulta.select(0, this.jTxtBuscaConsulta.getText().length());
    }//GEN-LAST:event_jTxtBuscaConsultaFocusGained

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        try {
            if (this.rsConsulta != null){
                this.rsConsulta.close();
            }
            if (this.rsRemitoDetalle != null){
                this.rsRemitoDetalle.close();
            }
            this.dispose();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jTablaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsultaMouseClicked
        if (evt.getClickCount() == 2) {
            clearTables();
            int indice = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
            Integer id_presupuesto = (Integer ) Utiles.valueAt(modeloConsulta, indice, "Turno");
            this.jLblTurno.setText(id_presupuesto.toString());
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = "";
            try {
                fecha = format.format((Date) modeloConsulta.getValueAt(indice, 1));
            } catch (Exception e) {
                //Fecha inválida
            }
            this.jLblFecha.setText(fecha);
            this.jLblNumero.setText(modeloConsulta.getValueAt(indice, 2).toString());
            this.jLblOrigen.setText(modeloConsulta.getValueAt(indice, 3).toString());
            this.jLblProfesional.setText(modeloConsulta.getValueAt(indice, 4).toString());
            this.jLblPaciente.setText(modeloConsulta.getValueAt(indice, 9).toString());
            this.jLblCajas.setText(modeloConsulta.getValueAt(indice, 11).toString());
            this.jLblEntidad.setText(modeloConsulta.getValueAt(indice, 12).toString());
            this.jLblLugar.setText(modeloConsulta.getValueAt(indice, 13).toString());

            this.jComboPrestacion.setSelectedIndex(-1);
            Object prestacion = modeloConsulta.getValueAt(indice, Utiles.getColumnByName(modeloConsulta, "prestacion"));
            if (prestacion != null)
                this.jComboPrestacion.setSelectedItem(prestacion.toString());

            this.jComboTecnico.setSelectedIndex(-1);
            Object tecnico = modeloConsulta.getValueAt(indice, Utiles.getColumnByName(modeloConsulta, "tecnico"));
            if (tecnico != null)
                this.jComboTecnico.setSelectedItem(tecnico.toString());

            this.id_remito = Integer.parseInt(modeloConsulta.getValueAt(indice, 7).toString());
            this.estado = modeloConsulta.getValueAt(indice, Utiles.getColumnByName(modeloConsulta, "estado")).toString();
            
            this.jComboCirugia.setSelectedIndex(-1);
            if (!this.estado.equals("C")) {
                switch (this.estado){
                    case "Z":
                        this.jComboCirugia.setSelectedItem("Realizada");
                        break;
                        
                    case "A":
                        this.jComboCirugia.setSelectedItem("Suspendida");
                        break;                        
                        
                    case "S":
                        this.jComboCirugia.setSelectedItem("Suspendida");
                        break;
                        
                    case "N":
                        this.jComboCirugia.setSelectedItem("Anulada");
                        break;
                }
            }
            
            this.jComboCirugia.setEnabled(this.estado.equals("C"));
            this.jTxtObservaciones.setText(modeloConsulta.getValueAt(indice, 8).toString()); 
            
            // activo o desactivo las tablas dependiendo de si el presupuesto esta confirmado|finalizado (en caso de que sea un remito de un presupuesto con un remito ya recepcionado) o no
            Boolean tableEnabled = Presupuesto.findById(id_presupuesto).get("estado").toString().matches("C|Z");
            jTablaProducto.setEnabled(tableEnabled);
            jTablaConsumo.setEnabled(tableEnabled);
                
            loadEnviados(Remito.findById(this.id_remito));
            this.jComboCirugiaActionPerformed(null);
            
        }   
    }//GEN-LAST:event_jTablaConsultaMouseClicked

    private void jComboPrestacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboPrestacionActionPerformed
        try{
            String prestacion = this.jComboPrestacion.getSelectedItem().toString();
            this.jComboTecnico.setSelectedIndex(-1);
            this.jComboTecnico.setEnabled(!(prestacion.equals("NO ASISTIDA") || prestacion.equals("SIN SERVICIO")));
        }catch (Exception ex){
            //Exepcion para poder inicializar el combo, no agregar nada!!!
        }
    }//GEN-LAST:event_jComboPrestacionActionPerformed

    private void jComboCirugiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboCirugiaActionPerformed
        try {
            modeloConsumo.getDataVector().removeAllElements();
            modeloConsumo.fireTableDataChanged();
            
            if (this.jComboCirugia.getSelectedIndex() != -1){
                this.jTablaProducto.clearSelection();
                this.jTablaConsumo.setEnabled(this.jComboCirugia.getSelectedItem().toString().equals("Realizada"));
            }
        } catch (Exception e) {
            //Solo para inicializar el combo
            Utiles.showErrorMessage(e);
        }
    }//GEN-LAST:event_jComboCirugiaActionPerformed

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
        loadingDialog.finish();
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnPreviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPreviaActionPerformed
        imprimeConsumo(getConsumidoFromTables(), 0, true);
    }//GEN-LAST:event_jBtnPreviaActionPerformed

    private void jTxtBuscaProductoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaProductoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtBuscaProductoFocusGained

    private void jTablaProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaProductoMouseClicked
//        if (evt.getClickCount() == 2) {
//            jMenuItemLoadActionPerformed(null);
//        }
    }//GEN-LAST:event_jTablaProductoMouseClicked

    private void jTablaConsumoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsumoMouseClicked
        if (evt.getClickCount() == 2) {
            jMenuItemRemoveActionPerformed(null);
        }
    }//GEN-LAST:event_jTablaConsumoMouseClicked

    private void jMenuItemLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLoadActionPerformed
          
        if (this.jComboCirugia.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(this,"Debe seleccionar el estado de la cirugía antes de cargar consumo", "Información",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
            
        int [] rows = jTablaProducto.getSelectedRows();
        for (int row : rows){
            row = jTablaProducto.convertRowIndexToModel(row);
            if(!Utiles.existeEnModelo(modeloConsumo, Utiles.getColumnByName(jTablaConsumo, "id_stockdetalle"), modeloProducto.getValueAt(row, Utiles.getColumnByName(jTablaProducto, "id_stockdetalle")))){
                HashMap<Object,Object> map = new HashMap<>();
                for (int c = 0; c < modeloProducto.getColumnCount(); c++){
                    map.put(Utiles.formattedColumn(modeloProducto.getColumnName(c)),modeloProducto.getValueAt(row, c));
                }
                map.put("devuelto",0);
                modeloConsumo.addRow(Utiles.fillTableObject(modeloConsumo, map));
            }
        }
    }//GEN-LAST:event_jMenuItemLoadActionPerformed

    private void jMenuItemRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRemoveActionPerformed

        while (jTablaConsumo.getSelectedRowCount() > 0 ) {
            Integer selectedRow = jTablaConsumo.convertRowIndexToModel(jTablaConsumo.getSelectedRow());
            Object id_stockdetalle = Utiles.valueAt(modeloConsumo,selectedRow,"id_stockdetalle");
            Integer row = Utiles.getRowByValue(modeloProducto, "id_stockdetalle", id_stockdetalle);
            modeloProducto.setValueAt(null, row, Utiles.getColumnByName(modeloProducto, "color"));
            jTablaProducto.repaint();            
            modeloConsumo.removeRow(selectedRow);
        }
    }//GEN-LAST:event_jMenuItemRemoveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnPrevia;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JComboBox jComboCirugia;
    private javax.swing.JComboBox jComboPrestacion;
    private javax.swing.JComboBox jComboTecnico;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblCajas;
    private javax.swing.JLabel jLblEntidad;
    private javax.swing.JLabel jLblFecha;
    private javax.swing.JLabel jLblLugar;
    private javax.swing.JLabel jLblNumero;
    private javax.swing.JLabel jLblOrigen;
    private javax.swing.JLabel jLblPaciente;
    private javax.swing.JLabel jLblProfesional;
    private javax.swing.JLabel jLblTurno;
    private javax.swing.JMenuItem jMenuItemLoad;
    private javax.swing.JMenuItem jMenuItemRemove;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenuConsumidos;
    private javax.swing.JPopupMenu jPopupMenuEnviados;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTablaConsulta;
    private ar.com.bosoft.RenderTablas.RXTable jTablaConsumo;
    private ar.com.bosoft.RenderTablas.RXTable jTablaProducto;
    private javax.swing.JTextField jTxtBuscaConsulta;
    private javax.swing.JTextField jTxtBuscaProducto;
    private javax.swing.JTextArea jTxtObservaciones;
    // End of variables declaration//GEN-END:variables
}
