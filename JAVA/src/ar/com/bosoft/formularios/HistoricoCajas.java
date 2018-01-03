/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import matera.gui.dialog.RemitoDetalle;
import ar.com.bosoft.RenderTablas.DateRenderer;
import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.clases.JRTableSorterDataSource;
import ar.com.bosoft.clases.TableHeaderMouseListener;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.EntidadCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.EntidadData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.vistasRapidas.Turno;
import ar.com.dialogos.AvisoEspera;
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
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import net.sf.jasperreports.engine.JRDataSource;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class HistoricoCajas extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, copias;
    String empresa, todos, impresora, nombreArchivo;
    
    EntidadCRUD entidadCRUD;
    ZonaCRUD zonaCRUD;
    ArrayList zonaArray, entidadArray, entidadIdArray;
    
    DefaultTableModel modelo;
    TableRowSorter sorter;
    JTableHeader header;
    TableHeaderMouseListener tableHeaderMoudseListener;
    TableCellRenderer tableCellRenderer;

    ResultSet rsConsulta;
    
    AvisoEspera avisoEspera;
    
    RemitoDetalle remitoDetalle;
    
    SeleccionImp seleccionImp = new SeleccionImp(null, true);
    
    Reporte reporte = new Reporte();
    
    /**
     * Creates new form HistoricoOperaciones
     * @param conexion
     * @param id_empresa
     * @param empresa
     */
    public HistoricoCajas(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.todos = "-- TODOS --";
        
        this.entidadCRUD = new EntidadCRUD(conexion, id_empresa, empresa);
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.entidadIdArray = new ArrayList();
        
        initComponents();
        
        modelo = (DefaultTableModel) this.jTabla.getModel();
        this.jTabla.setModel(modelo);
        sorter = new TableRowSorter(modelo);
        this.jTabla.setRowSorter(sorter);
        this.header = jTabla.getTableHeader();
        this.tableHeaderMoudseListener = new TableHeaderMouseListener(jTabla);
        this.header.addMouseListener(this.tableHeaderMoudseListener);
        tableCellRenderer = new DateRenderer();
        jTabla.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        
        setJTexFieldChanged(this.jTxtBuscaConsulta);
        
        llenaComboZona();
        consultaEntidad();
        limpia();
        zonaUsuario();
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
          sorter.setRowFilter(null);
        } else {
          sorter.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*",this.tableHeaderMoudseListener.getColumna()));
          jTabla.setRowSorter(sorter);
        }
    }
    
    private void llenaComboZona(){
        this.jComboZona.addItem(this.todos);
        this.zonaArray = zonaCRUD.consulta(true);
        Iterator it = zonaArray.iterator();
        while (it.hasNext()) {
            ZonaData tmp = (ZonaData) it.next();
            this.jComboZona.addItem(tmp.getNombre());
        }
    }
    
    private void consultaEntidad(){
        this.entidadArray = entidadCRUD.consulta(true);
    }
    
    private void filtraLugarCirugia(int id_zona){
        this.entidadIdArray.clear();
        this.jComboLugarCirugia.removeAllItems();
        this.jComboLugarCirugia.addItem(this.todos);
        Iterator it = entidadArray.iterator();
        while (it.hasNext()) {
            EntidadData tmp = (EntidadData) it.next();
            if (id_zona > 0) {
                if (tmp.getId_zona() != id_zona) {
                    continue;
                }
            }
            this.jComboLugarCirugia.addItem(tmp.getNombre());
            this.entidadIdArray.add(tmp.getId_entidad());
        }
    }
    
    private void limpia(){
        this.jComboLugarCirugia.setSelectedIndex(0);
        
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedIndex(0);
        }
        
        this.jTxtBuscaConsulta.setText("");
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
        this.jLblContador.setText("");
    }

    private void consulta(){
        try {
            modelo.getDataVector().removeAllElements();
            modelo.fireTableDataChanged();
            this.jLblContador.setText("");
            
            long desde = this.jDesde.getDate() == null ? (long)0 : this.jDesde.getDate().getTime();
            long hasta = this.jHasta.getDate() == null ? (long)0 : this.jHasta.getDate().getTime();
            
            int id_zona = 0;
            if (!this.jComboZona.getSelectedItem().equals(this.todos)) {
                ZonaData tmp = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
                id_zona = tmp.getId_zona();
            }
            
            int id_lugarCirugia = 0;
            if (!this.jComboLugarCirugia.getSelectedItem().equals(this.todos)) {
                id_lugarCirugia = (int) entidadIdArray.get(this.jComboLugarCirugia.getSelectedIndex() - 1);
            }
            
            ArrayList parametros = new ArrayList();
            parametros.add(desde);
            parametros.add(hasta);
            parametros.add(id_lugarCirugia);
            parametros.add(id_zona);
            parametros.add(this.id_empresa);
            
            rsConsulta = conexion.procAlmacenado("consultaHistoricoCirugias", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            if (rsConsulta.first()) {
                rsConsulta.beforeFirst();
                while (rsConsulta.next()) {
                    int id_presupuesto = rsConsulta.getInt("id_presupuesto");
                    Date fechaCx = rsConsulta.getDate("fechaCirugia");
                    String cajas = rsConsulta.getString("cajas");
                    String lugar = rsConsulta.getString("lugar");
                    String zona = rsConsulta.getString("zona");
                    String profesional = rsConsulta.getString("profesional");
                    String paciente = rsConsulta.getString("paciente");
                    String remitos = rsConsulta.getString("remitos");
                    String prestacion = rsConsulta.getString("prestacion");
                    String tecnico = rsConsulta.getString("tecnico");
                    String observaciones = rsConsulta.getString("observaciones");
                    String facturas = rsConsulta.getString("facturas");
                    String entidad = rsConsulta.getString("entidad");
                    String set = rsConsulta.getString("sets");
                    
                    Object[] fila = {id_presupuesto, fechaCx, cajas, lugar, zona, 
                        profesional, paciente, remitos, prestacion, tecnico, 
                        observaciones, facturas, entidad, set};
                    
                    modelo.addRow(fila);
                }
                this.jLblContador.setText("Son " + modelo.getRowCount() + " registros");
            }
        } catch (SQLException ex) {
            Utiles.enviaError(empresa, ex);
        }
        
    }
    
    private void verRemitos(){
        int fila = jTabla.convertRowIndexToModel(jTabla.getSelectedRow());
        int id_presupuesto = (int)modelo.getValueAt(fila, 0);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String fechaCx = "";
        try {
            fechaCx = format.format((Date) modelo.getValueAt(fila, 1));
        } catch (Exception e) {
            //Fecha invalida
        }
        String entidad = modelo.getValueAt(fila, 12).toString();
        String lugarCx = modelo.getValueAt(fila, 3).toString();
        String paciente = modelo.getValueAt(fila, 6).toString();
        String profesional = modelo.getValueAt(fila, 5).toString();
        String tipoPrestacion = modelo.getValueAt(fila, 8).toString();
        String tecnico = modelo.getValueAt(fila, 9).toString();
        String observaciones = modelo.getValueAt(fila, 10).toString();
        
        this.remitoDetalle = new RemitoDetalle(null, false, id_presupuesto);
        this.remitoDetalle.setDatos();
        this.avisoEspera.setVisible(false);
        this.remitoDetalle.setVisible(true);        
    }
    
    private void imprimir (int salida){
        Map param = new HashMap();
        param.put("empresa", this.empresa);
        param.put("desde", this.jDesde.getDate());
        param.put("hasta", this.jHasta.getDate());
        param.put("zona", this.jComboZona.getSelectedItem().toString());
        param.put("lugarCx", this.jComboLugarCirugia.getSelectedItem().toString());
        
        JRDataSource jr = new JRTableSorterDataSource(this.jTabla.getRowSorter());
        reporte.startReport("HistoricoCajas", param, jr, salida, nombreArchivo, impresora, copias);   
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu = new javax.swing.JPopupMenu();
        jDetalleTurno = new javax.swing.JMenuItem();
        jDetallePresupuesto = new javax.swing.JMenuItem();
        jMenuRemitos = new javax.swing.JMenuItem();
        jExcel = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboLugarCirugia = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jBtnFiltra = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jDesde = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jDesde.getJCalendar().setTodayButtonVisible(true); 
        this.jDesde.getJCalendar().setTodayButtonText("Hoy"); 
        this.jDesde.getJCalendar().setWeekOfYearVisible(false);
        jLabel3 = new javax.swing.JLabel();
        jHasta = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jHasta.getJCalendar().setTodayButtonVisible(true); 
        this.jHasta.getJCalendar().setTodayButtonText("Hoy"); 
        this.jHasta.getJCalendar().setWeekOfYearVisible(false);
        jPanel2 = new javax.swing.JPanel();
        jTxtBuscaConsulta = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla = new javax.swing.JTable();
        jBtnSalir = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();
        jLblContador = new javax.swing.JLabel();

        jDetalleTurno.setText("Turno");
        jDetalleTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetalleTurnoActionPerformed(evt);
            }
        });
        jPopupMenu.add(jDetalleTurno);

        jDetallePresupuesto.setText("Presupuesto");
        jDetallePresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetallePresupuestoActionPerformed(evt);
            }
        });
        jPopupMenu.add(jDetallePresupuesto);

        jMenuRemitos.setText("Ver remitos");
        jMenuRemitos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuRemitosActionPerformed(evt);
            }
        });
        jPopupMenu.add(jMenuRemitos);

        jExcel.setText("Exportar a Excel");
        jExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jExcelActionPerformed(evt);
            }
        });
        jPopupMenu.add(jExcel);

        setTitle("Histórico de cajas de cirugías");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jLabel1.setText("Lugar de cirugía");

        jComboLugarCirugia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboLugarCirugiaMouseClicked(evt);
            }
        });

        jLabel16.setText("Zona");

        jComboZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboZonaActionPerformed(evt);
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

        jLabel2.setText("Desde");

        jLabel3.setText("Hasta");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboLugarCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnFiltra)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel2)
                            .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel3)
                            .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboLugarCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)))
                    .addComponent(jBtnFiltra, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(1, 1, 1))
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setFocusable(false);

        jTxtBuscaConsulta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaConsultaFocusGained(evt);
            }
        });

        jLabel8.setText("Buscar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBuscaConsulta)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBuscaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)))
        );

        jTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Turno", "Fecha Cx.", "Cajas", "Lugar Cx.", "Zona", "Profesional", "Paciente", "Remitos", "prestacion", "tecnico", "observaciones", "Facturas", "entidad", "N° Set"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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
        jTabla.setComponentPopupMenu(jPopupMenu);
        jTabla.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTabla);
        if (jTabla.getColumnModel().getColumnCount() > 0) {
            jTabla.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTabla.getColumnModel().getColumn(0).setMaxWidth(100);
            jTabla.getColumnModel().getColumn(1).setPreferredWidth(65);
            jTabla.getColumnModel().getColumn(1).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTabla.getColumnModel().getColumn(2).setMaxWidth(300);
            jTabla.getColumnModel().getColumn(7).setPreferredWidth(150);
            jTabla.getColumnModel().getColumn(7).setMaxWidth(300);
            jTabla.getColumnModel().getColumn(8).setMinWidth(0);
            jTabla.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(8).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(9).setMinWidth(0);
            jTabla.getColumnModel().getColumn(9).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(9).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(10).setMinWidth(0);
            jTabla.getColumnModel().getColumn(10).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(10).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(11).setPreferredWidth(150);
            jTabla.getColumnModel().getColumn(11).setMaxWidth(300);
            jTabla.getColumnModel().getColumn(12).setMinWidth(0);
            jTabla.getColumnModel().getColumn(12).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(12).setMaxWidth(0);
        }

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

        jLblContador.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblContador.setText("...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLblContador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(289, 289, 289)
                        .addComponent(jBtnSalir))
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 524, Short.MAX_VALUE)))
                .addGap(3, 3, 3))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnSalir)
                    .addComponent(jLblContador, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HistoricoCajas.this.avisoEspera = new AvisoEspera(null, false, AvisoEspera.CARGANDO);
                HistoricoCajas.this.avisoEspera.setVisible(true);
                Thread performer = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HistoricoCajas.this.avisoEspera.setVisible(false);
                        HistoricoCajas.this.consulta();
                    }
                }, "Performer");
                performer.start();
            }
        });
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jTxtBuscaConsultaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaConsultaFocusGained
        this.jTxtBuscaConsulta.select(0, this.jTxtBuscaConsulta.getText().length());
    }//GEN-LAST:event_jTxtBuscaConsultaFocusGained

    private void jDetalleTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetalleTurnoActionPerformed
        if (jTabla.getSelectedRow() >= 0){
            int fila = jTabla.convertRowIndexToModel(jTabla.getSelectedRow());
        
            Turno turno = new Turno(null, true, conexion, id_empresa, empresa);
            turno.setId_presupuesto(modelo.getValueAt(fila, 0).toString());
            turno.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetalleTurnoActionPerformed

    private void jDetallePresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetallePresupuestoActionPerformed
        if (jTabla.getSelectedRow() >= 0){
            int fila = jTabla.convertRowIndexToModel(jTabla.getSelectedRow());
        
            Presupuesto presupuesto = new Presupuesto(conexion, id_empresa, empresa, "");
            presupuesto.setId_presupuesto(modelo.getValueAt(fila, 0).toString());
            Principal.dp.add(presupuesto);
            presupuesto.toFront();
            presupuesto.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetallePresupuestoActionPerformed

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        try {
            int id_zona = 0;
            if (!this.jComboZona.getSelectedItem().equals(this.todos)) {
                ZonaData tmp = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
                id_zona = tmp.getId_zona();
            }
            filtraLugarCirugia(id_zona);
        } catch (Exception e) {
            //Solo para inicializar el combo
        }
    }//GEN-LAST:event_jComboZonaActionPerformed

    private void jMenuRemitosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuRemitosActionPerformed
        if (jTabla.getSelectedRow() >= 0){
            int fila = jTabla.convertRowIndexToModel(jTabla.getSelectedRow());
        
            String remitos = modelo.getValueAt(fila, 7).toString();
            if (!remitos.isEmpty()) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        HistoricoCajas.this.avisoEspera = new AvisoEspera(null, false, AvisoEspera.CARGANDO);
                        HistoricoCajas.this.avisoEspera.setVisible(true);
                        Thread performer = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HistoricoCajas.this.verRemitos();
                            }
                        }, "Performer");
                        performer.start();
                    }
                });
            }else{
                JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }  
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jMenuRemitosActionPerformed

    private void jBtnScrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScrActionPerformed
        if (modelo.getRowCount() > 0){
            imprimir(0);
        }
    }//GEN-LAST:event_jBtnScrActionPerformed

    private void jBtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImpActionPerformed
        if (modelo.getRowCount() > 0){
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

    private void jExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jExcelActionPerformed
        if (modelo.getRowCount() > 0) {
            //Crear un objeto FileChooser
            JFileChooser fc = new JFileChooser();
            //Mostrar la ventana para abrir archivo y recoger la respuesta
            int respuesta = fc.showSaveDialog(null);
            //Comprobar si se ha pulsado Aceptar
            if (respuesta == JFileChooser.APPROVE_OPTION){
                try {
                    //Crear un objeto File con el archivo elegido
                    File file = new File(fc.getSelectedFile().getCanonicalPath() + ".xls");
                    WritableWorkbook workbook1 = Workbook.createWorkbook(file);
                    WritableSheet sheet1 = workbook1.createSheet("Hoja", 0);
                    for (int i = 0; i < modelo.getColumnCount(); i++) {
                        if (i > 13) {
                            break;
                        }
                        Label column = new Label(i, 0, modelo.getColumnName(i));
                        sheet1.addCell(column);
                    }

                    for (int i = 0; i < modelo.getRowCount(); i++) {
                        for (int j = 0; j < modelo.getColumnCount(); j++) {
                            if (j > 13) {
                                break;
                            }

                            String val = (modelo.getValueAt(i, j) == null ? "" : modelo.getValueAt(i, j).toString());
                            Label row = new Label(j, i + 1, val);
                            sheet1.addCell(row);
                        }
                    }
                    workbook1.write();
                    workbook1.close();
                    JOptionPane.showMessageDialog(this,"Exportación exitosa!", "Información",JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException | WriteException ex) {
                    JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }else{
            JOptionPane.showMessageDialog(this,"No hay resultados para mostrar", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jExcelActionPerformed

    private void jComboLugarCirugiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboLugarCirugiaMouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Entidad");
            Iterator it = this.entidadArray.iterator();
            while (it.hasNext()) {
                EntidadData tmp = (EntidadData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboLugarCirugia.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboLugarCirugiaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jBtnXls;
    private javax.swing.JComboBox jComboLugarCirugia;
    private javax.swing.JComboBox jComboZona;
    private com.toedter.calendar.JDateChooser jDesde;
    private javax.swing.JMenuItem jDetallePresupuesto;
    private javax.swing.JMenuItem jDetalleTurno;
    private javax.swing.JMenuItem jExcel;
    private com.toedter.calendar.JDateChooser jHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLblContador;
    private javax.swing.JMenuItem jMenuRemitos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTabla;
    private javax.swing.JTextField jTxtBuscaConsulta;
    // End of variables declaration//GEN-END:variables
}
