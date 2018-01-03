/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.RemitoDataSource;
import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ClasiProductoCRUD;
import ar.com.bosoft.crud.ProductoCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.ClasiProductoData;
import ar.com.bosoft.data.ZonaData;
import ar.com.dialogos.AvisoEspera;
import ar.com.matera.TableModels.ProductoTableModel;
import ar.com.matera.TableModels.RemitoDetalleTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import matera.TableObjects.RemitoDetalleTableObject;
import matera.gui.RemitoInternalFrame;
import matera.gui.renderers.TrazabilidadRenderer;
import matera.helpers.RemitoHelper;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class IntersucursalSalida extends RemitoInternalFrame {
    Conexion conexion;
    int id_empresa, indiceProducto, indiceSeleccion, copias;
    String empresa, usuario, rutaArchivo, impresora;
    TableRowSorter sorterProducto, sorterCaja, sorterCajaSeleccion;
    
    ZonaCRUD zonaCRUD;
    ClasiProductoCRUD clasiProductoCRUD;
    ProductoCRUD productoCRUD;
    
    ArrayList arrayZona = new ArrayList(), arrayZonaDestino = new ArrayList(), arrayClasi, arrayProducto;
    
    ResultSet rsCaja, rsCajaComposicion, rsTraeRemito, rsUltimoRemito;
    
    AvisoEspera avisoEspera;
    
    public IntersucursalSalida(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.clasiProductoCRUD = new ClasiProductoCRUD(conexion, empresa);
        this.productoCRUD = new ProductoCRUD(conexion, id_empresa, empresa);
        this.detalleCajaRemito = new DetalleCajaRemito(null, true);
        
        initComponents();
        
        modeloProducto = new ProductoTableModel();
        modeloProducto.getPropertiesFromDefaultModel(jTablaProducto.getModel());
        jTablaProducto.setModel(modeloProducto);
        
        jTablaProducto.setRowSorter (new TableRowSorter(modeloProducto));
        sorterProducto = new TableRowSorter(modeloProducto);
        
        modeloCaja = (DefaultTableModel) jTablaCaja.getModel();
        jTablaCaja.setModel(modeloCaja);
        jTablaCaja.setRowSorter (new TableRowSorter(modeloCaja));
        sorterCaja = new TableRowSorter(modeloCaja);
        
        modeloCajaSeleccion = (DefaultTableModel) jTablaCajaSeleccion.getModel();
        jTablaCajaSeleccion.setModel(modeloCajaSeleccion);
        jTablaCajaSeleccion.setRowSorter (new TableRowSorter(modeloCajaSeleccion));
        sorterCajaSeleccion = new TableRowSorter(modeloCajaSeleccion);
        
        modeloSeleccion = new RemitoDetalleTableModel();
        modeloSeleccion.getPropertiesFromDefaultModel(jTablaSeleccion.getModel());
        jTablaSeleccion.setModel(modeloSeleccion);
        jTablaSeleccion.setDefaultRenderer(Object.class, new TrazabilidadRenderer());
        
        llenaComboOrigen();
        llenaComboClasificacion();
        limpia();
        consultaProducto();
        setJTexFieldChanged(jTxtBusca);
        programaBuscaCaja(jTxtBuscaCaja);
    }

    private void llenaComboOrigen(){
        arrayZona = zonaCRUD.consulta(true);
        
        if (arrayZona.isEmpty()){
            JOptionPane.showMessageDialog(this, "No hay zonas habilitadas", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }else{
            Iterator i = arrayZona.iterator();
            while (i.hasNext()){
                ZonaData tmp = (ZonaData) i.next();
                this.jComboOrigen.addItem(tmp.getNombre());
            }
        }
    }
    
    private void llenaComboClasificacion(){
        this.jComboClasificacion.addItem("-- TODOS --");
        arrayClasi = clasiProductoCRUD.consulta(true);
        Iterator i = arrayClasi.iterator();
        while (i.hasNext()){
            ClasiProductoData tmp = (ClasiProductoData) i.next();
            this.jComboClasificacion.addItem(tmp.getNombre());
        }
    }
    
    public final void limpia(){
        this.jFecha.setDate(new Date());
        this.jTxtSucursal.setText("");
        this.jTxtNumero.setText("");
        this.jComboOrigen.setSelectedIndex(-1);
        this.jComboDestino.setSelectedIndex(-1);
        
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
        
        this.rutaArchivo = "";
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
                int id_cajaDeposito = rsCaja.getInt("id_cajaDeposito");
                
                Object[] fila = {codigo, caja, id_cajaDeposito};
                modeloCaja.addRow(fila);
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    private void llenaComboDestino(int id_zona){
        arrayZonaDestino.clear();
        this.jComboDestino.removeAllItems();
        Iterator it = arrayZona.iterator();
        while (it.hasNext()) {
            ZonaData tmp = (ZonaData)it.next();
            if (tmp.getId_zona() != id_zona) {
                arrayZonaDestino.add(tmp);
                this.jComboDestino.addItem(tmp.getNombre());
            }
        }
        this.jComboDestino.setSelectedIndex(-1);
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
        
        if (this.jComboOrigen.getSelectedIndex() < 0){
            error = "Falta cargar el origen del remito";
            return error;
        }
        
        if (this.jComboDestino.getSelectedIndex() < 0){
            error = "Falta cargar el destino del remito";
            return error;
        }
        
        boolean cajas = modeloCajaSeleccion.getRowCount() > 0;
        
        boolean hasProductos = false;
        if (modeloSeleccion.getRowCount() > 0){
            hasProductos = true;
        }
        
        if (!cajas  && !hasProductos){
            error = "No hay ítems cargados";
        }
        
        return error;
    }
    
    private void secuencia(){
        jBtnGuardar.disable();
        if (avisoError().isEmpty()){
            int respuesta = JOptionPane.showConfirmDialog(this, "Confirma la carga del remito?", "Atencíon", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                if (!existeRemito(this.jTxtSucursal.getText() + this.jTxtNumero.getText())){
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            IntersucursalSalida.this.avisoEspera = new AvisoEspera(null, false, 2);
                            //IntersucursalSalida.this.avisoEspera.setVisible(true);
                            Thread performer = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    IntersucursalSalida.this.insertRemito();
                                    IntersucursalSalida.this.insertIntersucursal();
                                    //IntersucursalSalida.this.avisoEspera.setVisible(false);
                                    
                                    
                                }
                            }, "Performer");
                            performer.start();
                        }
                    });
                    salida = new Salida(null, true);
                    salida.setVisible(true);
                    if (salida.getSiNo()) {
                        this.rutaArchivo = salida.getRutaArchivo();
                        this.impresora = salida.getImpresora();
                        this.copias = salida.getCopias();
                        int s = salida.getTipoSalida();
                        imprime(s, false);
                    }
                    limpia();
                }else{
                    JOptionPane.showMessageDialog(this, "El remito ya existe", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }else{
            JOptionPane.showMessageDialog(this, avisoError(), "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
        jBtnGuardar.enable();
    }
    
    private void insertRemito(){
        
        String numComp = this.jTxtSucursal.getText() + this.jTxtNumero.getText();
        int id_entidad = 0;
        
        ZonaData d = (ZonaData) arrayZonaDestino.get(this.jComboDestino.getSelectedIndex());
        int id_zonaDestino = d.getId_zona();
        
        ZonaData z = (ZonaData) arrayZona.get(this.jComboOrigen.getSelectedIndex());
        this.setZona(z.getId_zona());
        
        String observaciones = this.jTxtObservaciones.getText().trim();
        
        String cajas = "";
        for (int i = 0; i < modeloCajaSeleccion.getRowCount(); i++) {
            cajas += modeloCajaSeleccion.getValueAt(i, 0).toString() + (i == modeloCajaSeleccion.getRowCount() - 1 ? "" : " / ");
        }            
        
        String sets = "";
        
        Integer output = insertRemito(id_zonaDestino, RemitoInternalFrame.TIPO_ENTIDAD.INTERSUCURSAL, id_entidad, numComp, cajas, sets, observaciones);
        if (output == Utiles.INSERT.ERROR){
            Utiles.showMessage("error al guardar.");
            return;
        }        
    }
    
    private void insertIntersucursal(){
        ArrayList parametros = new ArrayList();
        parametros.add(this.usuario);
        parametros.add(this.id_empresa);
        
        conexion.procAlmacenado("insertIntersucursal", parametros, 
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        for (int i = 0; i < modeloCajaSeleccion.getRowCount(); i++) {
            parametros.clear();
            
            int id_cajaDeposito = (int)modeloCajaSeleccion.getValueAt(i, 2);
            parametros.add(id_cajaDeposito);
            parametros.add("N");
            conexion.procAlmacenado("insertIntersucursalCaja", parametros, 
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        }
    }
    
    private void imprime (Integer tipoSalida, boolean previa){
        salida = new Salida(null, true);
        salida.setTipoSalida(tipoSalida);
        imprime(salida, previa);
    }    
    
    private void imprime(Salida salida, boolean previa){
        Map param=new HashMap();
        param.put("fecha", this.jFecha.getDate());
        param.put("id_presupuesto", 0);
        param.put("entidad", "ENVÍA " + this.jComboOrigen.getSelectedItem().toString());
        param.put("lugarCirugia", "DEPOSITO " + this.jComboDestino.getSelectedItem().toString());
        param.put("profesional", "");
        param.put("paciente", "");
        param.put("telefono", "");
        param.put("dirLugarCirugia", "");
        param.put("numComp", this.jTxtSucursal.getText() + this.jTxtNumero.getText());
        param.put("tipo", "");
        param.put("observaciones", this.jTxtObservaciones.getText().trim());
        param.put("previa", previa);
        
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
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLocaleChooser1 = new com.toedter.components.JLocaleChooser();
        productosMenu = new javax.swing.JPopupMenu();
        agregarProductos = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jComboOrigen = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jComboDestino = new javax.swing.JComboBox();
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
        jBtnGuardar = new javax.swing.JButton();

        agregarProductos.setText("Agregar productos seleccionados...");
        agregarProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarProductosActionPerformed(evt);
            }
        });
        productosMenu.add(agregarProductos);

        setTitle("Envío intersucursal");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jLabel1.setText("* Envía");

        jComboOrigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboOrigenActionPerformed(evt);
            }
        });

        jLabel2.setText("* Recibe");

        jComboDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboDestinoActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Productos"));

        jComboClasificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboClasificacionActionPerformed(evt);
            }
        });

        jLabel10.setText("Busca");

        jTablaProducto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTablaProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaProducto.setComponentPopupMenu(productosMenu);
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
                "cantidad", "Código", "GTIN", "Nombre", "PM", "Lote", "serie", "vencimiento", "tipo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
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
        jTablaSeleccion.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTablaSeleccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaSeleccionMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTablaSeleccion);
        if (jTablaSeleccion.getColumnModel().getColumnCount() > 0) {
            jTablaSeleccion.getColumnModel().getColumn(0).setMinWidth(50);
            jTablaSeleccion.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTablaSeleccion.getColumnModel().getColumn(0).setMaxWidth(50);
            jTablaSeleccion.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTablaSeleccion.getColumnModel().getColumn(1).setMaxWidth(150);
            jTablaSeleccion.getColumnModel().getColumn(4).setMinWidth(75);
            jTablaSeleccion.getColumnModel().getColumn(4).setPreferredWidth(75);
            jTablaSeleccion.getColumnModel().getColumn(4).setMaxWidth(75);
            jTablaSeleccion.getColumnModel().getColumn(5).setMinWidth(100);
            jTablaSeleccion.getColumnModel().getColumn(5).setPreferredWidth(100);
            jTablaSeleccion.getColumnModel().getColumn(5).setMaxWidth(100);
            jTablaSeleccion.getColumnModel().getColumn(6).setMinWidth(100);
            jTablaSeleccion.getColumnModel().getColumn(6).setPreferredWidth(100);
            jTablaSeleccion.getColumnModel().getColumn(6).setMaxWidth(100);
            jTablaSeleccion.getColumnModel().getColumn(7).setMinWidth(75);
            jTablaSeleccion.getColumnModel().getColumn(7).setPreferredWidth(75);
            jTablaSeleccion.getColumnModel().getColumn(7).setMaxWidth(75);
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
            jTablaCaja.getColumnModel().getColumn(0).setMinWidth(95);
            jTablaCaja.getColumnModel().getColumn(0).setPreferredWidth(95);
            jTablaCaja.getColumnModel().getColumn(0).setMaxWidth(95);
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
            jTablaCajaSeleccion.getColumnModel().getColumn(0).setMinWidth(95);
            jTablaCajaSeleccion.getColumnModel().getColumn(0).setPreferredWidth(95);
            jTablaCajaSeleccion.getColumnModel().getColumn(0).setMaxWidth(95);
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
                    .addComponent(jScrollPane7)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnComposicion)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTxtBuscaCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnComposicion))
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
        jBtnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnPrevia)
                        .addGap(361, 361, 361)
                        .addComponent(jBtnGuardar)
                        .addGap(323, 323, 323)
                        .addComponent(jBtnSalir))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(105, 105, 105)
                                                .addComponent(jLabel1))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jTxtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel2)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jComboOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 938, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTxtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jComboOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jBtnGuardar)
                            .addComponent(jBtnPrevia))
                        .addGap(12, 12, 12))
                    .addComponent(jBtnSalir))
                .addGap(0, 2, Short.MAX_VALUE))
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
        tablaProductoMouseClicked(jTablaProducto, evt);   
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
            this.dispose();
        } catch (Exception ex) {
            Utiles.showErrorMessage(ex);
        }
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jComboOrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboOrigenActionPerformed
        try{
            if (arrayZona.size() < 1)
                return;
            ZonaData tmp = (ZonaData) arrayZona.get(this.jComboOrigen.getSelectedIndex());
            llenaCajas(tmp.getId_zona());
            llenaComboDestino(tmp.getId_zona());
        }catch (Exception ex){
            //Utiles.showErrorMessage(ex);
        }  
    }//GEN-LAST:event_jComboOrigenActionPerformed

    private void jBtnPreviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPreviaActionPerformed
        imprime(0, true);
    }//GEN-LAST:event_jBtnPreviaActionPerformed

    private void jComboDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboDestinoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboDestinoActionPerformed

    private void jTablaCajaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaCajaMouseClicked
        if (evt.getClickCount() == 2) {
            int fila = jTablaCaja.convertRowIndexToModel(jTablaCaja.getSelectedRow());
            String codigo = modeloCaja.getValueAt(fila, Utiles.getColumnByName(modeloCaja, "codigo")).toString();
            String nombre = modeloCaja.getValueAt(fila, Utiles.getColumnByName(modeloCaja, "nombre")).toString();
            int id_cajaDeposito = (int) modeloCaja.getValueAt(fila, Utiles.getColumnByName(modeloCaja, "id_cajaDeposito"));
            
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
        secuencia();
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jTablaSeleccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaSeleccionMouseClicked
        if (evt.getClickCount() == 2) {
            int fila = jTablaSeleccion.convertRowIndexToModel(jTablaSeleccion.getSelectedRow());
            modeloSeleccion.removeRow(fila);
        }
    }//GEN-LAST:event_jTablaSeleccionMouseClicked

    private void agregarProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarProductosActionPerformed
        agregarProductos(jTablaProducto);
    }//GEN-LAST:event_agregarProductosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem agregarProductos;
    private javax.swing.JButton jBtnComposicion;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnPrevia;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JComboBox jComboClasificacion;
    private javax.swing.JComboBox jComboDestino;
    private javax.swing.JComboBox jComboOrigen;
    private com.toedter.calendar.JDateChooser jFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
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
    private javax.swing.JTextField jTxtSucursal;
    private javax.swing.JPopupMenu productosMenu;
    // End of variables declaration//GEN-END:variables
}
