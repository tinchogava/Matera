/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.NegociosPendientesDataSource;
import ar.com.bosoft.RenderTablas.DateRenderer;
import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.clases.TableHeaderMouseListener;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.EntidadCRUD;
import ar.com.bosoft.crud.VendedorCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.EntidadData;
import ar.com.bosoft.data.VendedorData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.vistasRapidas.Observaciones;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class NegociosPendientes extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, copias;
    String empresa, usuario, todos, nombreArchivo, impresora;
    ZonaCRUD zonaCRUD;
    VendedorCRUD vendedorCRUD;
    EntidadCRUD entidadCRUD;
    
    ArrayList arrayZona, arrayVendedor, arrayId_vendedor, arrayEntidad, arrayId_entidad;
    
    DefaultTableModel modeloConsulta, modeloProducto;
    TableRowSorter sorterConsulta, sorterProducto;
    JTableHeader header;
    TableHeaderMouseListener tableHeaderMoudseListener;
    TableCellRenderer tableCellRenderer;
    
    ResultSet rsConsulta;
    
    SeleccionImp seleccionImp;
    Reporte reporte;
    
    Observaciones observaciones;
    
    public NegociosPendientes(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        
        this.todos = "-- TODOS --";
        
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.vendedorCRUD = new VendedorCRUD(conexion, id_empresa, empresa);
        this.entidadCRUD = new EntidadCRUD(conexion, id_empresa, empresa);
        
        this.arrayZona = new ArrayList();
        this.arrayVendedor = new ArrayList();        
        this.arrayId_vendedor = new ArrayList();
        this.arrayEntidad = new ArrayList();
        this.arrayId_entidad = new ArrayList();
        
        this.seleccionImp = new SeleccionImp(null, true);
        this.reporte = new Reporte();
        
        this.observaciones = new Observaciones(null, true, conexion, empresa, usuario);
        
        initComponents();
        
        modeloConsulta = (DefaultTableModel) jTabla.getModel();
        jTabla.setModel(modeloConsulta);
        jTabla.setRowSorter (new TableRowSorter(modeloConsulta));
        sorterConsulta = new TableRowSorter(modeloConsulta);
        this.header = jTabla.getTableHeader();
        this.tableHeaderMoudseListener = new TableHeaderMouseListener(jTabla);
        this.header.addMouseListener(this.tableHeaderMoudseListener);
        tableCellRenderer = new DateRenderer();
        jTabla.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        
        modeloProducto = (DefaultTableModel) jTablaProducto.getModel();
        jTablaProducto.setModel(modeloProducto);
        jTablaProducto.setRowSorter (new TableRowSorter(modeloProducto));
        sorterProducto = new TableRowSorter(modeloProducto);
        
        llenaComboZona();
        consultaVendedor();
        consultaEntidad();
        
        limpia();
        zonaUsuario();
        
        setJTexFieldChanged(jTxtBusca);
        this.jComboZonaActionPerformed(null);
    }

    private void zonaUsuario(){
        int id_zonaUsuario = Principal.usuarioData.getId_zonaSistema();
        if (id_zonaUsuario > 0) {
            if (this.arrayZona != null){
                Iterator it = arrayZona.iterator();
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
    
    private void llenaComboZona(){
        this.jComboZona.addItem(this.todos);
        arrayZona = zonaCRUD.consulta(true);
        Iterator i = arrayZona.iterator();
        while (i.hasNext()){
            ZonaData tmp = (ZonaData) i.next();
            this.jComboZona.addItem(tmp.getNombre());
        }
    }
    
    private void consultaVendedor(){
        this.arrayVendedor = vendedorCRUD.consulta(true);
    }
    
    private void llenaComboVendedor(int id_zona){
        this.jComboVendedor.removeAllItems();
        this.arrayId_vendedor.clear();
        this.jComboVendedor.addItem(this.todos);
        
        Iterator i = arrayVendedor.iterator();
        while (i.hasNext()) {
            VendedorData tmp = (VendedorData) i.next();
            boolean incluye = false;
            if (id_zona == 0){
                incluye = true;
            }else{
                if (tmp.getId_zona() == id_zona){
                    incluye = true;
                }
            }
            
            if (incluye && tmp.getId_especialidad() == 13) {
                this.arrayId_vendedor.add(tmp.getId_vendedor());
                this.jComboVendedor.addItem(tmp.getNombre());
            }
        }
        this.jComboVendedor.setSelectedItem(this.todos);
    }
    
    private void consultaEntidad(){
        this.arrayEntidad = entidadCRUD.consulta(true);
    }
    
    private void llenaComboEntidad(int id_zona){
        this.jComboEntidad.removeAllItems();
        this.arrayId_entidad.clear();
        this.jComboEntidad.addItem(this.todos);
        
        Iterator i = arrayEntidad.iterator();
        while (i.hasNext()) {
            EntidadData tmp = (EntidadData) i.next();
            boolean incluye = false;
            if (id_zona == 0){
                incluye = true;
            }else{
                if (tmp.getId_zona() == id_zona){
                    incluye = true;
                }
            }
            
            if (incluye) {
                this.arrayId_entidad.add(tmp.getId_entidad());
                this.jComboEntidad.addItem(tmp.getNombre());
            }
        }
        this.jComboEntidad.setSelectedItem(this.todos);
    }
    
    private void limpia(){
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedItem(todos);
        }
        
        this.jComboVendedor.setSelectedItem(todos);
        this.jComboEntidad.setSelectedItem(todos);
        
        this.jTxtBusca.setText("");
        this.modeloConsulta.getDataVector().removeAllElements();
        this.modeloConsulta.fireTableDataChanged();
        
        this.jCheckImprimeTodos.setSelected(true);
        
        this.nombreArchivo = "";
        this.impresora = "";
        this.copias = 0;
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
          sorterConsulta.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*",this.tableHeaderMoudseListener.getColumna()));
          jTabla.setRowSorter(sorterConsulta);
        }
    }
    
    private void limpiaProductos(){
        modeloProducto.getDataVector().removeAllElements();
        modeloProducto.fireTableDataChanged();
    }
    
    private void llenaProductos(int id_presupuesto){
        limpiaProductos();
        try {
            ArrayList parametros = new ArrayList();
            parametros.add(id_presupuesto);
            
            rsConsulta = conexion.procAlmacenado("traePresupuestoProd", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsConsulta.beforeFirst();
            while (rsConsulta.next()){
                Double cantidad = rsConsulta.getDouble("cantidad");
                String codigo = rsConsulta.getString("codigo");
                String producto = rsConsulta.getString("producto");
                String observaciones = rsConsulta.getString("observaciones");
                String proveedor = rsConsulta.getString("proveedor");
                
                Object[] fila = {cantidad, codigo, producto, observaciones, proveedor};
                
                modeloProducto.addRow(fila);
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    private void imprimir(int salida){
        try {
            Map param = new HashMap();
            param.put("empresa", this.empresa);
            param.put("zona", this.jComboZona.getSelectedItem().toString());
            param.put("entidad", this.jComboEntidad.getSelectedItem().toString());
            param.put("vendedor", this.jComboVendedor.getSelectedItem().toString());
            
            NegociosPendientesDataSource data = new NegociosPendientesDataSource();
            
            for (int i = 0; i < modeloConsulta.getRowCount(); i++){
                if (Boolean.parseBoolean(modeloConsulta.getValueAt(i, 8).toString())){
                    int id_presupuesto = (int) modeloConsulta.getValueAt(i, 0);
                    
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha = null;
                    try {
                        fecha = formatter.parse(modeloConsulta.getValueAt(i, 1).toString());
                    } catch (Exception e) {
                        //Fecha inválida
                    }

                    String entidad = modeloConsulta.getValueAt(i, 2).toString();
                    String paciente = modeloConsulta.getValueAt(i, 3).toString();
                    String direccion = modeloConsulta.getValueAt(i, 4).toString();
                    String telefono = modeloConsulta.getValueAt(i, 5).toString();
                    String profesional = modeloConsulta.getValueAt(i, 10).toString();
                    String observaciones = modeloConsulta.getValueAt(i, 6).toString();
                    BigDecimal total = new BigDecimal((double) modeloConsulta.getValueAt(i, 7));
                    
                    
                    ArrayList parametros = new ArrayList();
                    parametros.add(id_presupuesto);

                    rsConsulta = conexion.procAlmacenado("traePresupuestoProd", parametros,
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                    rsConsulta.beforeFirst();
                    while (rsConsulta.next()){
                        String linea1 = rsConsulta.getString("codigo");
                        String linea2 = rsConsulta.getString("producto");
                        String linea3 = rsConsulta.getString("observaciones");
                        
                        String producto = linea1 + (linea2.isEmpty() ? "" : "\n" + linea2) + (linea3.isEmpty() ? "" : "\n" + linea3);
                        
                        ar.com.bosoft.Modelos.NegociosPendientes nuevo = new ar.com.bosoft.Modelos.NegociosPendientes(id_presupuesto, fecha, entidad, paciente, direccion, telefono, profesional, producto, observaciones, total);
                        
                        data.add(nuevo);
                    }
                }
            }
            reporte.startReport("NegociosPendientes", param, data, salida, nombreArchivo, impresora, copias);            
        }catch(SQLException ex){
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenuConsulta = new javax.swing.JPopupMenu();
        jDetallePresupuesto = new javax.swing.JMenuItem();
        jEditaObservaciones = new javax.swing.JMenuItem();
        jPopupMenuProducto = new javax.swing.JPopupMenu();
        jMenuDetalle = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jComboEntidad = new javax.swing.JComboBox();
        jBtnFiltra = new javax.swing.JButton();
        jComboVendedor = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablaProducto = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jCheckImprimeTodos = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();

        jDetallePresupuesto.setText("Ver presupuesto");
        jDetallePresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetallePresupuestoActionPerformed(evt);
            }
        });
        jPopupMenuConsulta.add(jDetallePresupuesto);

        jEditaObservaciones.setText("Editar observaciones");
        jEditaObservaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditaObservacionesActionPerformed(evt);
            }
        });
        jPopupMenuConsulta.add(jEditaObservaciones);

        jMenuDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/16x16/enabled/eye_icon&16.png"))); // NOI18N
        jMenuDetalle.setText("Ver detalle");
        jMenuDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuDetalleActionPerformed(evt);
            }
        });
        jPopupMenuProducto.add(jMenuDetalle);

        setTitle("Negocios pendientes");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jLabel6.setText("Zona");

        jComboZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboZonaActionPerformed(evt);
            }
        });

        jLabel3.setText("Entidad");

        jComboEntidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboEntidadMouseClicked(evt);
            }
        });

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

        jComboVendedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboVendedorMouseClicked(evt);
            }
        });

        jLabel7.setText("Agente");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboEntidad, 0, 420, Short.MAX_VALUE)
                            .addComponent(jComboVendedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnFiltra))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 114, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jComboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnFiltra)))
                .addGap(1, 1, 1))
        );

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setFocusable(false);

        jTxtBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaFocusGained(evt);
            }
        });

        jLabel11.setText("Buscar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 749, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)))
        );

        jTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Turno", "Fecha", "Entidad", "Paciente", "Dcción.", "Tel.", "Obs.", "$ Presu.", "Imprime", "Activo", "profesional"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabla.setComponentPopupMenu(jPopupMenuConsulta);
        jTabla.getTableHeader().setReorderingAllowed(false);
        jTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTabla);
        if (jTabla.getColumnModel().getColumnCount() > 0) {
            jTabla.getColumnModel().getColumn(0).setMinWidth(50);
            jTabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTabla.getColumnModel().getColumn(0).setMaxWidth(50);
            jTabla.getColumnModel().getColumn(1).setMinWidth(75);
            jTabla.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(1).setMaxWidth(75);
            jTabla.getColumnModel().getColumn(7).setMinWidth(75);
            jTabla.getColumnModel().getColumn(7).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(7).setMaxWidth(75);
            jTabla.getColumnModel().getColumn(8).setMinWidth(55);
            jTabla.getColumnModel().getColumn(8).setPreferredWidth(55);
            jTabla.getColumnModel().getColumn(8).setMaxWidth(55);
            jTabla.getColumnModel().getColumn(9).setMinWidth(55);
            jTabla.getColumnModel().getColumn(9).setPreferredWidth(55);
            jTabla.getColumnModel().getColumn(9).setMaxWidth(55);
            jTabla.getColumnModel().getColumn(10).setMinWidth(0);
            jTabla.getColumnModel().getColumn(10).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(10).setMaxWidth(0);
        }

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTablaProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cantidad", "Código", "Nombre", "Detalle", "Proveedor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jTablaProducto.setComponentPopupMenu(jPopupMenuProducto);
        jScrollPane2.setViewportView(jTablaProducto);
        if (jTablaProducto.getColumnModel().getColumnCount() > 0) {
            jTablaProducto.getColumnModel().getColumn(0).setMinWidth(50);
            jTablaProducto.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTablaProducto.getColumnModel().getColumn(0).setMaxWidth(50);
            jTablaProducto.getColumnModel().getColumn(1).setMinWidth(120);
            jTablaProducto.getColumnModel().getColumn(1).setPreferredWidth(120);
            jTablaProducto.getColumnModel().getColumn(1).setMaxWidth(120);
        }

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("PRODUCTOS");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1))
        );

        jCheckImprimeTodos.setText("Imprime todos");
        jCheckImprimeTodos.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jCheckImprimeTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckImprimeTodosActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Salida"));

        jBtnScr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/pantalla.png"))); // NOI18N
        jBtnScr.setBorder(null);
        jBtnScr.setBorderPainted(false);
        jBtnScr.setContentAreaFilled(false);
        jBtnScr.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        jBtnImp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        jBtnPdf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        jBtnXls.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnXls.setFocusPainted(false);
        jBtnXls.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/xls.png"))); // NOI18N
        jBtnXls.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/xls.png"))); // NOI18N
        jBtnXls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnXlsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jBtnScr, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnImp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnXls, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnXls, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnPdf, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnImp, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnScr, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        jBtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/guardar.png"))); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.setBorderPainted(false);
        jBtnGuardar.setContentAreaFilled(false);
        jBtnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(726, 726, 726)
                        .addComponent(jCheckImprimeTodos))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(199, 199, 199)
                        .addComponent(jBtnGuardar)
                        .addGap(209, 209, 209)
                        .addComponent(jBtnSalir)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jCheckImprimeTodos)
                .addGap(3, 3, 3)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBtnGuardar)
                            .addComponent(jBtnSalir)))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        int id_zona = 0;
        
        if (this.jComboZona.getSelectedIndex() > 0) {
            ZonaData tmp = (ZonaData) this.arrayZona.get(this.jComboZona.getSelectedIndex() - 1);
            id_zona = tmp.getId_zona();  
        }
        
        try{
            this.llenaComboVendedor(id_zona);
            this.llenaComboEntidad(id_zona);                
        } catch (Exception ex){}
    }//GEN-LAST:event_jComboZonaActionPerformed

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        try {
            modeloConsulta.getDataVector().removeAllElements();
            modeloConsulta.fireTableDataChanged();

            int id_zona = 0;
            if (this.jComboZona.getSelectedIndex() > 0){
                ZonaData z = (ZonaData) arrayZona.get(this.jComboZona.getSelectedIndex() - 1);
                id_zona = z.getId_zona();
            }

            int id_vendedor = 0;
            if (this.jComboVendedor.getSelectedIndex() > 0){
                id_vendedor = (int) arrayId_vendedor.get(this.jComboVendedor.getSelectedIndex() - 1);
            }

            int id_entidad = 0;
            if (this.jComboEntidad.getSelectedIndex() > 0){
                id_entidad = (int) arrayId_entidad.get(this.jComboEntidad.getSelectedIndex() - 1);
            }

            ArrayList parametros = new ArrayList();
            parametros.add(id_zona);
            parametros.add(id_vendedor);
            parametros.add(id_entidad);
            parametros.add(this.id_empresa);

            rsConsulta = conexion.procAlmacenado("consultaNegociosPendientes", parametros,
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

            if (rsConsulta.first()){
                rsConsulta.beforeFirst();
                while (rsConsulta.next()){
                    int id_presupuesto = rsConsulta.getInt("id_presupuesto");
                    Date fecha = rsConsulta.getDate("fecha");
                    String entidad = rsConsulta.getString("entidad");
                    String paciente = rsConsulta.getString("paciente");
                    String direccion = rsConsulta.getString("direccion");
                    String telefono = rsConsulta.getString("telefono");
                    String observaciones = rsConsulta.getString("observaciones");
                    Double total = rsConsulta.getDouble("total");
                    String profesional = rsConsulta.getString("profesional");
                    
                    Object[] fila = {id_presupuesto, fecha, entidad, paciente, direccion,
                        telefono, observaciones, total, true, true, profesional};

                    modeloConsulta.addRow(fila);
                }
            }else{
                JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jTxtBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaFocusGained
        this.jTxtBusca.select(0, this.jTxtBusca.getText().length());
    }//GEN-LAST:event_jTxtBuscaFocusGained

    private void jTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaMouseClicked
        if (evt.getClickCount() == 2){
            int fila = jTabla.convertRowIndexToModel(jTabla.getSelectedRow());
            int id_presupuesto = (int) modeloConsulta.getValueAt(fila, 0);
            llenaProductos(id_presupuesto);
        }
    }//GEN-LAST:event_jTablaMouseClicked

    private void jBtnScrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScrActionPerformed
        if (modeloConsulta.getRowCount() > 0){
            imprimir(0);
        }
    }//GEN-LAST:event_jBtnScrActionPerformed

    private void jBtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImpActionPerformed
        if (modeloConsulta.getRowCount() > 0){
            seleccionImp.setVisible(true);
            if (seleccionImp.getSino()){
                this.impresora = seleccionImp.getImpresora();
                this.copias = seleccionImp.getCopias();
                imprimir(1);
            }
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
                imprimir(2);
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
                imprimir(3);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jBtnXlsActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (modeloConsulta.getRowCount() > 0){
            int respuesta = JOptionPane.showConfirmDialog(this, "Confirma los cambios?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                ArrayList parametros = new ArrayList();
                for (int i = 0; i < modeloConsulta.getRowCount(); i++){
                    if (!Boolean.parseBoolean(modeloConsulta.getValueAt(i, 9).toString())){
                        parametros.clear();
                        parametros.add((int) modeloConsulta.getValueAt(i, 0));
                        
                        try{
                            conexion.procAlmacenado("desactivaVip", parametros,
                                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                        }catch (NumberFormatException ex){
                            Utiles.enviaError(this.empresa,ex);
                        }
                    }
                }
                limpia();
                limpiaProductos();
            }
        }else{
            JOptionPane.showMessageDialog(this, "No hay resultados", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jCheckImprimeTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckImprimeTodosActionPerformed
        for (int i = 0; i < modeloConsulta.getRowCount(); i++){
            modeloConsulta.setValueAt(this.jCheckImprimeTodos.isSelected(), i, 8);
        }
    }//GEN-LAST:event_jCheckImprimeTodosActionPerformed

    private void jDetallePresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetallePresupuestoActionPerformed
        int fila = jTabla.convertRowIndexToModel(jTabla.getSelectedRow());
        if (fila >= 0){
            Presupuesto presupuesto = new Presupuesto(conexion, id_empresa, empresa, "");
            presupuesto.setId_presupuesto(modeloConsulta.getValueAt(fila, 0).toString());
            Principal.dp.add(presupuesto);
            presupuesto.toFront();
            presupuesto.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetallePresupuestoActionPerformed

    private void jEditaObservacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditaObservacionesActionPerformed
        if (jTabla.getSelectedRow() >= 0){
            int fila = jTabla.convertRowIndexToModel(jTabla.getSelectedRow());
            observaciones.setId_presupuesto(modeloConsulta.getValueAt(fila, 0).toString());
            observaciones.setObservaciones(modeloConsulta.getValueAt(fila, 6).toString().trim());
            observaciones.setVisible(true);
            if (observaciones.isSiNo()) {
                modeloConsulta.setValueAt(observaciones.getObservaciones(), fila, 6);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jEditaObservacionesActionPerformed

    private void jMenuDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuDetalleActionPerformed
        int indiceTabla = jTablaProducto.getSelectedRow();
        if (indiceTabla >= 0){
            String titulo = modeloProducto.getValueAt(indiceTabla, 1).toString();
            String message = modeloProducto.getValueAt(indiceTabla, 2).toString() + " " + modeloProducto.getValueAt(indiceTabla, 3).toString();
            JOptionPane.showMessageDialog(this, message, titulo, JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this,"No ha seleccionado ninguna fila", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jMenuDetalleActionPerformed

    private void jComboEntidadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboEntidadMouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Entidad");
            Iterator it = this.arrayEntidad.iterator();
            while (it.hasNext()) {
                EntidadData tmp = (EntidadData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboEntidad.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboEntidadMouseClicked

    private void jComboVendedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboVendedorMouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Agente");
            Iterator it = this.arrayVendedor.iterator();
            while (it.hasNext()) {
                VendedorData tmp = (VendedorData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboVendedor.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboVendedorMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jBtnXls;
    private javax.swing.JCheckBox jCheckImprimeTodos;
    private javax.swing.JComboBox jComboEntidad;
    private javax.swing.JComboBox jComboVendedor;
    private javax.swing.JComboBox jComboZona;
    private javax.swing.JMenuItem jDetallePresupuesto;
    private javax.swing.JMenuItem jEditaObservaciones;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuItem jMenuDetalle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu jPopupMenuConsulta;
    private javax.swing.JPopupMenu jPopupMenuProducto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabla;
    private javax.swing.JTable jTablaProducto;
    private javax.swing.JTextField jTxtBusca;
    // End of variables declaration//GEN-END:variables
}
