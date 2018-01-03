/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.RemitoDataSource;
import ar.com.bosoft.RenderTablas.DateCellEditor;
import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.buscadores.BuscaPresupuesto;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.formatosCampos.ConMascara;
import ar.com.matera.TableModels.ProductoTableModel;
import ar.com.matera.TableModels.RemitoDetalleTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import matera.TableObjects.CargaProductoTableObject;
import matera.TableObjects.ProductoTableObject;
import matera.TableObjects.RemitoDetalleTableObject;
import matera.cache.CacheClasiProducto;

import matera.gui.RemitoInternalFrame;
import matera.gui.helper.ExcelAdapter;
import matera.gui.renderers.TrazabilidadRenderer;
import matera.helpers.RemitoHelper;
import matera.jooq.Tables;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class RemitoTransito extends RemitoInternalFrame {
    Conexion conexion;
    int id_empresa, id_presupuesto, id_entidad, indiceProducto, indiceSeleccion, copias;
    String empresa, usuario, entidad, dirLugarCirugia, telefono, rutaArchivo, impresora;
    TableRowSorter sorterProducto, sorterCaja, sorterCajaSeleccion;
    
    ResultSet rsConsulta, rsCaja, rsCajaComposicion, rsTraeRemito, rsUltimoRemito;
    
    BuscaPresupuesto buscaPresupuesto;
    ConMascara conMascara = new ConMascara();
   

    public RemitoTransito(Conexion conexion) {

        this.conexion = conexion;
        this.id_empresa = Principal.getIdEmpresa();
        this.empresa = Principal.getEmpresaName();
        this.usuario = Principal.getUsuarioName();
        this.detalleCajaRemito = new DetalleCajaRemito(null, true);
        
        initComponents();
        
        modeloProducto = new ProductoTableModel();
        modeloProducto.getPropertiesFromDefaultModel(jTablaProducto.getModel());
        jTablaProducto.setModel(modeloProducto);
        sorterProducto = new TableRowSorter(modeloProducto);
        jTablaProducto.setRowSorter (sorterProducto);  
        
        modeloCaja = (DefaultTableModel) jTablaCaja.getModel();
        jTablaCaja.setModel(modeloCaja);
        sorterCaja = new TableRowSorter(modeloCaja);
        jTablaCaja.setRowSorter (sorterCaja);
        
        
        modeloCajaSeleccion = (DefaultTableModel) jTablaCajaSeleccion.getModel();
        jTablaCajaSeleccion.setModel(modeloCajaSeleccion);
        sorterCajaSeleccion = new TableRowSorter(modeloCajaSeleccion);
        jTablaCajaSeleccion.setRowSorter (sorterCajaSeleccion);
        
        
        modeloSeleccion = new RemitoDetalleTableModel();
        modeloSeleccion.getPropertiesFromDefaultModel(jTablaSeleccion.getModel());
        jTablaSeleccion.setModel(modeloSeleccion);
        jTablaSeleccion.setDefaultRenderer(Object.class, new TrazabilidadRenderer());
        
        
        TableColumn dateCol = jTablaSeleccion.getColumnModel().getColumn(Utiles.getColumnByName(jTablaSeleccion, "vencimiento"));
        JDateChooser date = new JDateChooser("dd/MM/20yy", "##/##/####", '_');
        date.setMinSelectableDate(new Date());
        dateCol.setCellEditor(new DateCellEditor(date));        
        
        loadClasiProductoCombo();
        limpia();
        consultaProducto();
        setJTexFieldChanged(jTxtBusca);
        programaBuscaCaja(jTxtBuscaCaja);
        ExcelAdapter adapter = new ExcelAdapter(jTablaSeleccion);
    }
    
    private void loadClasiProductoCombo(){
        this.jComboClasificacion.addItem("-- TODOS --");
        
        Result<Record> clasiProductos = CacheClasiProducto.instance().get();
        clasiProductos.forEach(c ->{
            this.jComboClasificacion.addItem(c.into(Tables.CLASIPRODUCTO).getNombre());
        });
    }
    
    public final void limpia(){
        this.jFecha.setDate(new Date());
        this.jTxtSucursal.setText("");
        this.jTxtNumero.setText("");
        this.jFmtId_presupuesto.setValue("");
        this.jLblLugarCirugia.setText("");
        this.jLblProfesional.setText("");
        this.jLblPaciente.setText("");
        
        this.jTxtBuscaCaja.setText("");
        modeloCaja.getDataVector().removeAllElements();
        modeloCaja.fireTableDataChanged();
        this.jTxtSet.setText("");
        modeloCajaSeleccion.getDataVector().removeAllElements();
        modeloCajaSeleccion.fireTableDataChanged();
        
        this.jComboClasificacion.setSelectedIndex(-1);
        this.jTxtBusca.setText("");
        
        modeloSeleccion.removeAllRows();
        modeloSeleccion.fireTableDataChanged();
        
        this.jTxtObservaciones.setText("");
        
        this.id_presupuesto = 0;
        this.id_entidad = 0;
        this.id_zona = 0;
        this.entidad = "";
        this.dirLugarCirugia = "";
        this.telefono = "";
        
        this.indiceProducto = -1;
        this.indiceSeleccion = -1;
        
        this.rutaArchivo = "";
    }
    
    private void consulta(){
        this.jTxtBuscaCaja.setText("");
        modeloCaja.getDataVector().removeAllElements();
        modeloCaja.fireTableDataChanged();
        modeloCajaSeleccion.getDataVector().removeAllElements();
        modeloCajaSeleccion.fireTableDataChanged();
        
        this.jComboClasificacion.setSelectedIndex(0);
        this.jTxtBusca.setText("");
        
        modeloSeleccion.removeAllRows();
        modeloSeleccion.fireTableDataChanged();
        
        this.jTxtObservaciones.setText("");
        
        this.indiceProducto = -1;
        this.indiceSeleccion = -1;
        
        try {
            ArrayList parametros = new ArrayList();
            parametros.add(this.id_presupuesto);
            
            rsConsulta = conexion.procAlmacenado("traeTurno", parametros,
                    empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            if (rsConsulta.first()) {
                this.jLblLugarCirugia.setText(rsConsulta.getString("lugarCirugia"));
                this.jLblProfesional.setText(rsConsulta.getString("profesional"));
                this.jLblPaciente.setText(rsConsulta.getString("paciente"));
                this.jTxtObservaciones.setText(rsConsulta.getString("observaciones"));
                this.id_entidad = rsConsulta.getInt("id_entidad");
                this.id_zona = rsConsulta.getInt("id_zona");   
                this.entidad = rsConsulta.getString("entidad");
                this.dirLugarCirugia = rsConsulta.getString("dirLugarCirugia");
                this.telefono = rsConsulta.getString("telefono");  
                
                llenaCajas(id_zona);
            }else{
                JOptionPane.showMessageDialog(this, "No se han recuperado los datos del turno", "Atención", JOptionPane.INFORMATION_MESSAGE);
                limpia();
            }
        } catch (SQLException ex) {
            Utiles.enviaError(empresa, ex);
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
          sorterProducto.setRowFilter(null);
        } else {
          sorterProducto.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTablaProducto.setRowSorter(sorterProducto);
        }
    }
    
    private void programaBuscaCaja(JTextField txt){
        DocumentListener documentListener = new DocumentListener() { 
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                printItCaja(documentEvent);
            }
 
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                printItCaja(documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                printItCaja(documentEvent);
            }
        };
        txt.getDocument().addDocumentListener(documentListener); 
    }
 
    private void printItCaja(DocumentEvent documentEvent) {
        DocumentEvent.EventType type = documentEvent.getType();
 
        if (type.equals(DocumentEvent.EventType.INSERT) || type.equals(DocumentEvent.EventType.REMOVE)){
            JTxtBuscaCajaChanged();
        }
    }
 
    private void JTxtBuscaCajaChanged(){
        String text = jTxtBuscaCaja.getText().trim().replaceAll("'", "´");
        if (text.length() == 0) {
          sorterCaja.setRowFilter(null);
        } else {
          sorterCaja.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTablaCaja.setRowSorter(sorterCaja);
        }
    }
    
    private void llenaCajas(int id_zona){
        modeloCaja.getDataVector().removeAllElements();
        modeloCaja.fireTableDataChanged();
        modeloCajaSeleccion.getDataVector().removeAllElements();
        modeloCajaSeleccion.fireTableDataChanged();
        
        try{
            ArrayList parametros = new ArrayList();
            parametros.add(id_zona);
            parametros.add(this.id_empresa);
            
            rsCaja = conexion.procAlmacenado("traeCajaZona", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsCaja.beforeFirst();
            while (rsCaja.next()){
                String codigo = rsCaja.getString("codigo");
                String caja = rsCaja.getString("caja");
                Integer id_cajaDeposito = rsCaja.getInt("id_cajaDeposito");
                
                Object[] fila = {codigo, caja, id_cajaDeposito};
                modeloCaja.addRow(fila);
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    private String avisoError(){
        String error = "";
        
        if (this.jFecha.getDate() == null){
            error = "Falta cargar la fecha del remito";
            return error;
        }else if (this.jFecha.getDate().after(new Date())){
            error = "La fecha del remito no puede ser mayor a la actual";
            return error;
        }
        
        if (this.jTxtSucursal.getText().isEmpty() || this.jTxtNumero.getText().isEmpty()){
            error = "Falta cargar el número del remito";
            return error;
        }
        
        if (this.id_presupuesto == 0) {
            error = "No se ha seleccionado un turno";
            return error;
        }
        
        boolean cajas = modeloCajaSeleccion.getRowCount() > 0;
        
        boolean hasProductos = modeloSeleccion.getRowCount() > 0;
        
        if (!cajas  && !hasProductos){
            error = "No hay ítems cargados";
        }
        
        return error;
    }
    
    private void secuencia(){
        if (jTablaSeleccion.isEditing())
            jTablaSeleccion.getCellEditor().stopCellEditing();        
        int respuesta = JOptionPane.showConfirmDialog(this, "Confirma la carga del remito?", "Atencíon", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION){
            if (avisoError().isEmpty()){
                if (!existeRemito(this.jTxtSucursal.getText() + this.jTxtNumero.getText())){
                    String cajas = "";
                    for (int i = 0; i < modeloCajaSeleccion.getRowCount(); i++) {
                         cajas += modeloCajaSeleccion.getValueAt(i, Utiles.getColumnByName(modeloCajaSeleccion, "codigo")).toString() + (i == modeloCajaSeleccion.getRowCount() - 1 ? "" : " / ");
                    }
                    Integer output = insertRemito(id_presupuesto, 
                            RemitoInternalFrame.TIPO_ENTIDAD.PRESUPUESTO,
                            id_entidad, 
                            this.jTxtSucursal.getText() + this.jTxtNumero.getText(), 
                            cajas,
                            jTxtSet.getText(),
                            jTxtObservaciones.getText());
                    if (output == Utiles.INSERT.ERROR){
                        Utiles.showMessage("error al guardar.");
                        return;
                    }                    
                    salida = new Salida(null, true);
                    salida.setVisible(true);
                    if (salida.getSiNo()) {
                        this.rutaArchivo = salida.getRutaArchivo();
                        this.impresora = salida.getImpresora();
                        this.copias = salida.getCopias();
                        int s = salida.getTipoSalida();
                        imprime(salida, false);
                    }
                    limpia();
                }else{
                    JOptionPane.showMessageDialog(this, "El remito ya existe", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(this, avisoError(), "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
 
    private void imprime (Integer tipoSalida, boolean previa){
        salida = new Salida(null, true);
        salida.setTipoSalida(tipoSalida);
        imprime(salida, previa);
    }    
    
    private void imprime(Salida salida, boolean previa){
        try {
            int id = this.id_presupuesto;
            String e = this.entidad;
            Date fecha = this.jFecha.getDate();
            Date fCx = null;
            
            String lCx = this.jLblLugarCirugia.getText();
            String tec = "";
            String prof = this.jLblProfesional.getText();
            String pac = this.jLblPaciente.getText();
            String observaciones = this.jTxtObservaciones.getText().trim();
            String mat = "";
            
            Map param=new HashMap();
            param.put("fecha", fecha);
            param.put("id_presupuesto", id);
            param.put("entidad", e);
            param.put("fechaCirugia", fCx);
            param.put("lugarCirugia", lCx);
            param.put("tecnico", tec);
            param.put("profesional", prof);
            param.put("paciente", pac);
            param.put("observaciones", observaciones);
            param.put("matricula", mat);
            param.put("dirLugarCirugia", dirLugarCirugia);
            param.put("numComp", this.jTxtSucursal.getText() + this.jTxtNumero.getText());
            param.put("previa", previa);
            param.put("set", this.jTxtSet.getText());
            
            HashMap cajaMap = new HashMap();
            for (int i = 0; i < modeloCajaSeleccion.getRowCount(); i++) {
                cajaMap.put(Integer.parseInt(Utiles.valueAt(modeloCajaSeleccion, i, "id_cajaDeposito").toString()), Utiles.valueAt(modeloCajaSeleccion, i, "codigo"));
            }
            
            RemitoHelper remitoHelper = new RemitoHelper();
            remitoHelper.printByCaja(param, composicionCajaMap, Utiles.modelToMapList(modeloCajaSeleccion), salida);

            if (modeloSeleccion.getRowCount() > 0) {
                param.put("cajas","Adicionales");
                param.put("tipo", "Adicionales");
                RemitoDataSource data = new RemitoDataSource(); //limpio el DataSource

                List<RemitoDetalleTableObject> extraList = modeloSeleccion.getRowsAsList();

                extraList = remitoHelper.groupProductOccurrences(extraList);

                remitoHelper.sortByCodigo(extraList);
                remitoHelper.fillRemitoReport(data, extraList);
                remitoHelper.printReporte(param, data, salida);
            }

        } catch (Exception ex) {
            Utiles.showErrorMessage(ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLocaleChooser1 = new com.toedter.components.JLocaleChooser();
        productoMenu = new javax.swing.JPopupMenu();
        agregarProductos = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jComboClasificacion = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jTxtBusca = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaProducto = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablaSeleccion = new ar.com.bosoft.RenderTablas.RXTable();
        jLabel3 = new javax.swing.JLabel();
        jFecha = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jFecha.getJCalendar().setTodayButtonVisible(true); 
        this.jFecha.getJCalendar().setTodayButtonText("Hoy"); 
        this.jFecha.getJCalendar().setWeekOfYearVisible(false);
        jLabel4 = new javax.swing.JLabel();
        jTxtSucursal = new javax.swing.JTextField();
        jTxtSucursal.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(4,true));
        jTxtNumero = new javax.swing.JTextField();
        jTxtNumero.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(8,true));
        jScrollPane4 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();
        jBtnSalir = new javax.swing.JButton();
        jBtnPrevia = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTablaCaja = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTablaCajaSeleccion = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jTxtBuscaCaja = new javax.swing.JTextField();
        jBtnComposicion = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jTxtSet = new javax.swing.JTextField();
        jBtnGuardar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLblLugarCirugia = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLblProfesional = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLblPaciente = new javax.swing.JLabel();
        jFmtId_presupuesto = new javax.swing.JFormattedTextField();
        jBtnBusca = new javax.swing.JButton();

        agregarProductos.setText("Agregar productos seleccionados...");
        agregarProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarProductosActionPerformed(evt);
            }
        });
        productoMenu.add(agregarProductos);

        setTitle("Carga de remito a turno en tránsito");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Productos"));

        jComboClasificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboClasificacionActionPerformed(evt);
            }
        });

        jLabel10.setText("Busca");

        jTablaProducto.setModel(new javax.swing.table.DefaultTableModel(
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
        jTablaProducto.setComponentPopupMenu(productoMenu);
        jTablaProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaProductoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaProducto);

        jLabel11.setText("Clasificación");

        jLabel6.setText("Formato de fecha");

        jLabel5.setText("dd/mm/yyyy");

        jTablaSeleccion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "GTIN", "Nombre", "cantidad", "PM", "Lote", "serie", "vencimiento", "tipo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
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
        jTablaSeleccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaSeleccionMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTablaSeleccion);
        if (jTablaSeleccion.getColumnModel().getColumnCount() > 0) {
            jTablaSeleccion.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTablaSeleccion.getColumnModel().getColumn(0).setMaxWidth(150);
            jTablaSeleccion.getColumnModel().getColumn(3).setPreferredWidth(50);
            jTablaSeleccion.getColumnModel().getColumn(3).setMaxWidth(75);
            jTablaSeleccion.getColumnModel().getColumn(4).setPreferredWidth(75);
            jTablaSeleccion.getColumnModel().getColumn(4).setMaxWidth(120);
            jTablaSeleccion.getColumnModel().getColumn(5).setPreferredWidth(100);
            jTablaSeleccion.getColumnModel().getColumn(5).setMaxWidth(200);
            jTablaSeleccion.getColumnModel().getColumn(7).setPreferredWidth(75);
            jTablaSeleccion.getColumnModel().getColumn(7).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboClasificacion, 0, 260, Short.MAX_VALUE)
                            .addComponent(jTxtBusca)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(1, 1, 1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboClasificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel5)))
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(1, 1, 1))
        );

        jLabel3.setText("* Fecha");

        jLabel4.setText("* Número");

        jTxtSucursal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtSucursalFocusLost(evt);
            }
        });

        jTxtNumero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtNumeroFocusLost(evt);
            }
        });

        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jTxtObservaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Observaciones"));
        jScrollPane4.setViewportView(jTxtObservaciones);

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

        jPanel6.setBackground(new java.awt.Color(153, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cajas"));

        jTablaCaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "id_cajaDeposito"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaCaja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaCajaMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTablaCaja);
        if (jTablaCaja.getColumnModel().getColumnCount() > 0) {
            jTablaCaja.getColumnModel().getColumn(0).setPreferredWidth(95);
            jTablaCaja.getColumnModel().getColumn(0).setMaxWidth(200);
            jTablaCaja.getColumnModel().getColumn(2).setMinWidth(0);
            jTablaCaja.getColumnModel().getColumn(2).setPreferredWidth(0);
            jTablaCaja.getColumnModel().getColumn(2).setMaxWidth(0);
        }

        jTablaCajaSeleccion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "id_cajaDeposito"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaCajaSeleccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaCajaSeleccionMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jTablaCajaSeleccion);
        if (jTablaCajaSeleccion.getColumnModel().getColumnCount() > 0) {
            jTablaCajaSeleccion.getColumnModel().getColumn(0).setPreferredWidth(95);
            jTablaCajaSeleccion.getColumnModel().getColumn(0).setMaxWidth(200);
            jTablaCajaSeleccion.getColumnModel().getColumn(2).setMinWidth(0);
            jTablaCajaSeleccion.getColumnModel().getColumn(2).setPreferredWidth(0);
            jTablaCajaSeleccion.getColumnModel().getColumn(2).setMaxWidth(0);
        }

        jLabel13.setText("Busca");

        jBtnComposicion.setText("Cargar composición");
        jBtnComposicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnComposicionActionPerformed(evt);
            }
        });

        jLabel8.setText("N° Set");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtBuscaCaja))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtSet)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnComposicion))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jTxtSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jTxtBuscaCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBtnComposicion)))
                .addGap(3, 3, 3)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jBtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/guardar.png"))); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.setBorderPainted(false);
        jBtnGuardar.setContentAreaFilled(false);
        jBtnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnGuardar.setFocusPainted(false);
        jBtnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jLabel2.setText("* Turno N°");

        jLabel7.setText("Lugar de Cx.");

        jLblLugarCirugia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblLugarCirugia.setText("...");

        jLabel14.setText("Profesional");

        jLblProfesional.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblProfesional.setText("...");

        jLabel18.setText("Paciente");

        jLblPaciente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblPaciente.setText("...");

        try {
            jFmtId_presupuesto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFmtId_presupuesto.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        jFmtId_presupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFmtId_presupuestoActionPerformed(evt);
            }
        });
        jFmtId_presupuesto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFmtId_presupuestoFocusLost(evt);
            }
        });
        jFmtId_presupuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFmtId_presupuestoKeyReleased(evt);
            }
        });

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jBtnPrevia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnGuardar)
                        .addGap(322, 322, 322)
                        .addComponent(jBtnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTxtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLblLugarCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel18))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jFmtId_presupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLblProfesional, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLblPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jFmtId_presupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnBusca)
                    .addComponent(jLabel14)
                    .addComponent(jLblProfesional))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTxtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel18)
                    .addComponent(jLblLugarCirugia)
                    .addComponent(jLblPaciente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnPrevia)
                    .addComponent(jBtnSalir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboClasificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboClasificacionActionPerformed
        try {
            String clasificacion = this.jComboClasificacion.getSelectedItem().toString();
            filtraProducto(clasificacion);
        } catch (Exception ex) {
            //Solo para iniciar
        }
    }//GEN-LAST:event_jComboClasificacionActionPerformed

    private void jTablaProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaProductoMouseClicked
        if (evt.getClickCount() == 2) {
            int row = jTablaProducto.convertRowIndexToModel(jTablaProducto.getSelectedRow());
            ProductoTableObject pto = modeloProducto.getRow(row);
            List<CargaProductoTableObject> list = new ArrayList();
            list.add(new CargaProductoTableObject(pto.getProducto(), pto.getClasiProducto(), pto.getMoneda(), 1));
            loadSeleccion(list);
        }   
    }//GEN-LAST:event_jTablaProductoMouseClicked

    private void jTxtSucursalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtSucursalFocusLost
        if (!this.jTxtSucursal.getText().isEmpty()){
            String aux = this.jTxtSucursal.getText();
            this.jTxtSucursal.setText("0000".substring(aux.length()) + aux);
        }
    }//GEN-LAST:event_jTxtSucursalFocusLost

    private void jTxtNumeroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtNumeroFocusLost
        if (!this.jTxtNumero.getText().isEmpty()){
            String aux = this.jTxtNumero.getText();
            this.jTxtNumero.setText("00000000".substring(aux.length()) + aux);
        }
    }//GEN-LAST:event_jTxtNumeroFocusLost

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        try {
            if (this.rsCaja != null){
                this.rsCaja.close();
            }
            if (this.rsCajaComposicion != null){
                this.rsCajaComposicion.close();
            }
            if (this.rsTraeRemito != null){
                this.rsTraeRemito.close();
            }
            if (this.rsUltimoRemito != null){
                this.rsUltimoRemito.close();
            }
            this.dispose();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnPreviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPreviaActionPerformed
        imprime(0, true);
    }//GEN-LAST:event_jBtnPreviaActionPerformed

    private void jTablaCajaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaCajaMouseClicked
        if (evt.getClickCount() == 2) {
            int fila = jTablaCaja.convertRowIndexToModel(jTablaCaja.getSelectedRow());
            String codigo = modeloCaja.getValueAt(fila, Utiles.getColumnByName(modeloCaja, "codigo")).toString();
            String nombre = modeloCaja.getValueAt(fila, Utiles.getColumnByName(modeloCaja, "nombre")).toString();
            Integer id_cajaDeposito = (Integer) modeloCaja.getValueAt(fila, Utiles.getColumnByName(modeloCaja, "id_cajaDeposito"));
            
            if (!Utiles.existeEnModelo(modeloCajaSeleccion, 2, id_cajaDeposito)) {
                Object[] nuevo = {codigo, nombre, id_cajaDeposito};
                modeloCajaSeleccion.addRow(nuevo);
            }else{
                JOptionPane.showMessageDialog(this, "Ya ha seleccionado esta caja", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }            
        }
    }//GEN-LAST:event_jTablaCajaMouseClicked

    private void jTablaCajaSeleccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaCajaSeleccionMouseClicked
        if (evt.getClickCount() == 2) {
            int fila = jTablaCajaSeleccion.convertRowIndexToModel(jTablaCajaSeleccion.getSelectedRow());
            Integer id_cajaDeposito = (Integer) modeloCajaSeleccion.getValueAt(fila, Utiles.getColumnByName(modeloCajaSeleccion, "id_cajaDeposito"));
            deleteDatosComposicion(fila);
            modeloCajaSeleccion.removeRow(fila);
        }
    }//GEN-LAST:event_jTablaCajaSeleccionMouseClicked

    private void jBtnComposicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnComposicionActionPerformed
        jBtnComposicion.disable();
        if (jTablaCajaSeleccion.getSelectedRow() >= 0) {
            int fila = jTablaCajaSeleccion.convertRowIndexToModel(jTablaCajaSeleccion.getSelectedRow());
        
            Integer id_cajaDeposito = (Integer)modeloCajaSeleccion.getValueAt(fila, Utiles.getColumnByName(modeloCajaSeleccion, "id_cajaDeposito"));
            String nombreCaja = modeloCajaSeleccion.getValueAt(fila, Utiles.getColumnByName(modeloCajaSeleccion, "codigo")) + " - " + modeloCajaSeleccion.getValueAt(fila, Utiles.getColumnByName(modeloCajaSeleccion, "nombre")).toString();
            getDatosComposicion(id_cajaDeposito, fila, nombreCaja);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione una caja", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
        jBtnComposicion.enable();
    }//GEN-LAST:event_jBtnComposicionActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        jBtnGuardar.disable();
        secuencia();
        jBtnGuardar.enable();
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jTablaSeleccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaSeleccionMouseClicked
        if (evt.getClickCount() == 2) {
            int fila = jTablaSeleccion.convertRowIndexToModel(jTablaSeleccion.getSelectedRow());
            modeloSeleccion.removeRow(fila);
        }
    }//GEN-LAST:event_jTablaSeleccionMouseClicked

    private void jFmtId_presupuestoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFmtId_presupuestoFocusLost
        if (!this.jFmtId_presupuesto.getText().trim().isEmpty()) {
            this.id_presupuesto = Integer.parseInt(this.jFmtId_presupuesto.getText().trim());
            consulta();
        }else{
            limpia();
        }
    }//GEN-LAST:event_jFmtId_presupuestoFocusLost

    private void jBtnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscaActionPerformed
        this.buscaPresupuesto = new BuscaPresupuesto(conexion,null,true,this.id_empresa,this.empresa, 6);
        this.buscaPresupuesto.setVisible(true);
        this.jFmtId_presupuesto.setValue(buscaPresupuesto.getId_presupuesto().isEmpty() ? "" : buscaPresupuesto.getId_presupuesto());
        if (!this.jFmtId_presupuesto.getText().trim().isEmpty()) {
            this.id_presupuesto = Integer.parseInt(this.jFmtId_presupuesto.getText().trim());
            consulta();
        }else{
            limpia();
        }
    }//GEN-LAST:event_jBtnBuscaActionPerformed

    private void jFmtId_presupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFmtId_presupuestoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFmtId_presupuestoActionPerformed

    private void jFmtId_presupuestoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFmtId_presupuestoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.jFmtId_presupuestoFocusLost(null);
            this.jTxtSucursal.requestFocus();
        }
    }//GEN-LAST:event_jFmtId_presupuestoKeyReleased

    private void agregarProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarProductosActionPerformed
        agregarProductos(jTablaProducto);
    }//GEN-LAST:event_agregarProductosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem agregarProductos;
    private javax.swing.JButton jBtnBusca;
    private javax.swing.JButton jBtnComposicion;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnPrevia;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JComboBox jComboClasificacion;
    private com.toedter.calendar.JDateChooser jFecha;
    private javax.swing.JFormattedTextField jFmtId_presupuesto;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLblLugarCirugia;
    private javax.swing.JLabel jLblPaciente;
    private javax.swing.JLabel jLblProfesional;
    private com.toedter.components.JLocaleChooser jLocaleChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTable jTablaCaja;
    private javax.swing.JTable jTablaCajaSeleccion;
    private javax.swing.JTable jTablaProducto;
    private ar.com.bosoft.RenderTablas.RXTable jTablaSeleccion;
    private javax.swing.JTextField jTxtBusca;
    private javax.swing.JTextField jTxtBuscaCaja;
    private javax.swing.JTextField jTxtNumero;
    private javax.swing.JTextArea jTxtObservaciones;
    private javax.swing.JTextField jTxtSet;
    private javax.swing.JTextField jTxtSucursal;
    private javax.swing.JPopupMenu productoMenu;
    // End of variables declaration//GEN-END:variables

}
