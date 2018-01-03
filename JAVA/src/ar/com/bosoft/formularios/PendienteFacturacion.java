/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.PendienteFacturacionDataSource;
import ar.com.bosoft.RenderTablas.DateRenderer;
import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.conexion.ActiveDatabase;
import ar.com.matera.TableModels.PendienteFacturacionTM;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import matera.TableObjects.PendienteFacturacionTO;
import matera.db.dao.RemitoDao;
import matera.db.managers.PresupuestoMgr;
import matera.gui.combobox.ComboBoxMgr;
import matera.gui.dialog.PresupuestoEventosDialog;
import matera.gui.dialog.RemitoDetalle;
import matera.gui.renderers.MultilineTableCell;
import matera.jooq.Tables;
import matera.jooq.tables.records.PresupuestoControlfacturacionRecord;
import matera.jooq.tables.records.ZonaRecord;
import matera.services.PresupuestoService;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Result;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class PendienteFacturacion extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, copias;
    String empresa, todos, nombreArchivo, impresora;
    
    DefaultTableModel obsRemitoModel;
    
    PendienteFacturacionTM modelo = new PendienteFacturacionTM();
    TableRowSorter sorter;
    TableCellRenderer tableCellRenderer;
    
    ResultSet rsConsulta;
    
    SeleccionImp seleccionImp;
    Reporte reporte;
    Map<Integer, PresupuestoControlfacturacionRecord> controlMap = new HashMap<>();
    Map<Integer, String> obsPresupuesto = new HashMap<>();
    Map<Integer, Map<String,String>> obsRemito = new HashMap<>();
    DSLContext dsl = ActiveDatabase.getDSLContext();

    public PendienteFacturacion(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        
        this.seleccionImp = new SeleccionImp(null, true);
        this.reporte = new Reporte();
        
        initComponents();
        
        modelo.getPropertiesFromDefaultModel(jTabla.getModel());
        
//Seteo las columnas a Boolean para checkbox
        for (int i = 8; i < modelo.getColumnCount();++i){
            modelo.setColumnClass(i, Boolean.class);
        }
        
        jTabla.setModel(modelo);
        sorter = new TableRowSorter(modelo);
        jTabla.setRowSorter (sorter);
        
        tableCellRenderer = new DateRenderer();
        jTabla.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        
        obsRemitoModel = (DefaultTableModel) obsRemitoTable.getModel();
        obsRemitoTable.setModel(obsRemitoModel);
        obsRemitoTable.setDefaultRenderer(String.class, new MultilineTableCell());
        
        ComboBoxMgr.fillZonaCombo(jComboZona, true, true);
        
        limpia();
        
        setJTexFieldChanged(jTxtBusca);
        
        addTurnoSelectionEvent();
    }
    
    private void addTurnoSelectionEvent(){
        jTabla.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent e) {
              if (jTabla.getSelectedRowCount() < 1) return;
              
              Integer id = (Integer) jTabla.getValueAt(jTabla.getSelectedRow(), 0);
              observacionesPresupuesto.setText(obsPresupuesto.get(id));
              fillObservacionesRemitoForPresupuesto(id);
          }
        });    
    }
    
    private void fillObservacionesRemitoForPresupuesto(Integer id){
        obsRemitoModel.getDataVector().removeAllElements();
        obsRemito.get(id).forEach((k,v)->{
            Vector vec = new Vector();
            vec.add(k);
            vec.add(v);
            obsRemitoModel.addRow(vec);
        });
    }

    private void limpia(){
        
        this.jTxtBusca.setText("");
        modelo.removeAllRows();
        this.modelo.fireTableDataChanged();
        
        obsRemitoModel.getDataVector().removeAllElements();
        obsRemitoModel.fireTableDataChanged();
        
        observacionesPresupuesto.setText("");
        
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
          sorter.setRowFilter(null);
        } else {
          sorter.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTabla.setRowSorter(sorter);
        }
    }
    
   private void imprimir(int salida){
        try {
            Map param = new HashMap();
            param.put("empresa", this.empresa);
            param.put("zona", this.jComboZona.getSelectedItem().toString());
            param.put("entidad", this.entidadCombo.getSelectedItem().toString());
            param.put("profesional", this.profesionalCombo.getSelectedItem().toString());
            param.put("vendedor", this.jComboVendedor.getSelectedItem().toString());
            
            PendienteFacturacionDataSource data = new PendienteFacturacionDataSource();
            rsConsulta.beforeFirst();
            while (rsConsulta.next()){
                String entidad = rsConsulta.getString("entidad");
                int id_presupuesto = rsConsulta.getInt("id_presupuesto");
                
                Date f = rsConsulta.getDate("fechaCirugia");
                
                String lugarCx = rsConsulta.getString("lugarCx");
                String profesional = rsConsulta.getString("profesional");
                String paciente = rsConsulta.getString("paciente");
                String circuito = "", orden= "", protocolo= "", rx= "", remito= "", firma= "", sticker= "", completo = "";
                String observaciones = rsConsulta.getString("observaciones");
                switch(controlMap.get(rsConsulta.getInt("id_presupuesto")).getCircuito()){  
                    case 1:
                    circuito = "SI";
                    break;
                 
                    case 0:
                    circuito = "NO";
                    break;
                }
                switch(controlMap.get(rsConsulta.getInt("id_presupuesto")).getOrden()){
                    
                    case 1:
                    orden = "SI";
                    break;
                 
                    case 0:
                    orden = "NO";
                    break;
                }
                switch(controlMap.get(rsConsulta.getInt("id_presupuesto")).getProtocolo()){
                    
                    case 1:
                    protocolo = "SI";
                    break;
                 
                    case 0:
                    protocolo = "NO";
                    break;            
                }
                switch(controlMap.get(rsConsulta.getInt("id_presupuesto")).getRx()){
                    
                    case 1:
                    rx = "SI";
                    break;
                 
                    case 0:
                    rx = "NO";
                    break;
                }
                 switch(controlMap.get(rsConsulta.getInt("id_presupuesto")).getRemito()){
                    
                    case 1:
                    remito = "SI";
                    break;
                 
                    case 0:
                    remito = "NO";
                    break;
                }
                  switch(controlMap.get(rsConsulta.getInt("id_presupuesto")).getFirma()){
                    
                    case 1:
                    firma = "SI";
                    break;
                 
                    case 0:
                    firma = "NO";
                    break;
                }
                   switch(controlMap.get(rsConsulta.getInt("id_presupuesto")).getSticker()){
                    
                    case 1:
                    sticker = "SI";
                    break;
                 
                    case 0:
                    sticker = "NO";
                    break;
                }
                     switch(controlMap.get(rsConsulta.getInt("id_presupuesto")).getCompleto()){
                    
                    case 1:
                    completo = "SI";
                    break;
                 
                    case 0:
                    completo = "NO";
                    break;
                }
                BigDecimal montoPresupuesto = rsConsulta.getBigDecimal("montoPresupuesto");
                BigDecimal montoFacturado = rsConsulta.getBigDecimal("montoFacturado");
                System.out.println(circuito);
                System.out.println(orden);
                System.out.println(protocolo);
                System.out.println(rx);
                System.out.println(remito);
                System.out.println(firma);
                System.out.println(sticker);
                System.out.println(completo);
                System.out.println(observaciones);
                ar.com.bosoft.Modelos.PendienteFacturacion nuevo = 
                        new ar.com.bosoft.Modelos.PendienteFacturacion(entidad, lugarCx, profesional, paciente, f,
                                id_presupuesto, circuito, orden, protocolo, rx, remito, firma, sticker, montoPresupuesto, 
                                montoFacturado, observaciones, completo);
                
                data.add(nuevo);
            }
            reporte.startReport("PendienteFacturacion", param, data, salida, nombreArchivo, impresora, copias);
            
        }catch(SQLException ex){
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new javax.swing.JPopupMenu();
        showConsumido = new javax.swing.JMenuItem();
        showHistorial = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jComboVendedor = new javax.swing.JComboBox();
        jBtnFiltra = new javax.swing.JButton();
        entidadCombo = new matera.gui.combobox.GlazedCombo();
        profesionalCombo = new matera.gui.combobox.GlazedCombo();
        jLabel8 = new javax.swing.JLabel();
        jDesde = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jDesde.getJCalendar().setTodayButtonVisible(true); 
        this.jDesde.getJCalendar().setTodayButtonText("Hoy"); 
        this.jDesde.getJCalendar().setWeekOfYearVisible(false);
        jHasta = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jHasta.getJCalendar().setTodayButtonVisible(true); 
        this.jHasta.getJCalendar().setTodayButtonText("Hoy"); 
        this.jHasta.getJCalendar().setWeekOfYearVisible(false);
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla = new javax.swing.JTable();
        jButtonGuardar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        observacionesPresupuesto = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        obsRemitoTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        showConsumido.setText("Ver Consumido");
        showConsumido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showConsumidoActionPerformed(evt);
            }
        });
        menu.add(showConsumido);

        showHistorial.setText("Ver historial del turno");
        showHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showHistorialActionPerformed(evt);
            }
        });
        menu.add(showHistorial);

        setTitle("Pendientes de facturación");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jLabel3.setText("Entidad");

        jLabel5.setText("Profesional");

        jLabel6.setText("Zona");

        jLabel7.setText("Agente");

        jComboZona.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboZonaItemStateChanged(evt);
            }
        });

        jComboVendedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboVendedorMouseClicked(evt);
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

        jLabel8.setText("Desde");

        jLabel9.setText("Hasta");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboZona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboVendedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(profesionalCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(entidadCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jBtnFiltra)))
                .addGap(18, 18, 18))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboVendedor, jComboZona});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8))
                    .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnFiltra))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(entidadCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(profesionalCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
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
                "Turno", "Fecha Cx.", "Entidad", "Profesional", "Paciente", "$ Presu.", "$ Pend.", "lugarCx", "circuito", "orden", "protocolo", "rx", "remito", "firma", "sticker", "completo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabla.setToolTipText("Tildar los documentos que faltan para facturar");
        jTabla.setComponentPopupMenu(menu);
        jTabla.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTabla);
        if (jTabla.getColumnModel().getColumnCount() > 0) {
            jTabla.getColumnModel().getColumn(0).setMinWidth(50);
            jTabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTabla.getColumnModel().getColumn(0).setMaxWidth(50);
            jTabla.getColumnModel().getColumn(1).setMinWidth(75);
            jTabla.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(1).setMaxWidth(75);
            jTabla.getColumnModel().getColumn(5).setMinWidth(50);
            jTabla.getColumnModel().getColumn(5).setPreferredWidth(50);
            jTabla.getColumnModel().getColumn(5).setMaxWidth(50);
            jTabla.getColumnModel().getColumn(6).setMinWidth(50);
            jTabla.getColumnModel().getColumn(6).setPreferredWidth(50);
            jTabla.getColumnModel().getColumn(6).setMaxWidth(50);
            jTabla.getColumnModel().getColumn(7).setMinWidth(0);
            jTabla.getColumnModel().getColumn(7).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        jButtonGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/guardar.png"))); // NOI18N
        jButtonGuardar.setText("Guardar");
        jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarActionPerformed(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnXls, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        observacionesPresupuesto.setColumns(20);
        observacionesPresupuesto.setLineWrap(true);
        observacionesPresupuesto.setRows(5);
        observacionesPresupuesto.setWrapStyleWord(true);
        jScrollPane3.setViewportView(observacionesPresupuesto);

        jLabel1.setText("Observaciones de Remitos");

        obsRemitoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "remito", "observaciones"
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
        jScrollPane5.setViewportView(obsRemitoTable);
        if (obsRemitoTable.getColumnModel().getColumnCount() > 0) {
            obsRemitoTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            obsRemitoTable.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        jLabel2.setText("Observaciones Presupuesto");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 184, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonGuardar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnSalir, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(2, 2, 2))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 727, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addComponent(jBtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane3))
                        .addContainerGap())))
        );

        jScrollPane2.setViewportView(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        getPresupuestos();
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jTxtBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaFocusGained
        this.jTxtBusca.select(0, this.jTxtBusca.getText().length());
    }//GEN-LAST:event_jTxtBuscaFocusGained

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
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jComboVendedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboVendedorMouseClicked

    }//GEN-LAST:event_jComboVendedorMouseClicked

    private void jComboZonaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboZonaItemStateChanged
        
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            ZonaRecord zona = (ZonaRecord) evt.getItem();
            Integer id_zona = zona.getIdZona();  

            try{
                ComboBoxMgr.fillEntidadCombo(entidadCombo, id_zona, true);
                ComboBoxMgr.fillProfesionalCombo(profesionalCombo, id_zona, true);
                ComboBoxMgr.fillVendedorCombo(jComboVendedor, id_zona, true);
                
            } catch (Exception ex){}
        }
    }//GEN-LAST:event_jComboZonaItemStateChanged

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
        try{
            modelo.getRowsAsList().forEach(r->{
                if (r.getControl().changed())
                    r.getControl().store();
            });
            int id = (Integer) jTabla.getValueAt(jTabla.getSelectedRow(), 0);
            
            String observacionNew = observacionesPresupuesto.getText().toUpperCase();
            observacionesPresupuesto.setText(observacionNew);
            if(!observacionNew.isEmpty()){
                PresupuestoService.updateObservaciones(observacionNew, id);
                limpia();
                getPresupuestos();  
                Utiles.showMessage("Observacines actualizadas.");
            }else {
                Utiles.showMessage("Control de presupuestos guardado correctamente.");  
            }
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }

    }//GEN-LAST:event_jButtonGuardarActionPerformed

    private void showConsumidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showConsumidoActionPerformed
        if (jTabla.getSelectedRowCount() < 1)
            return;
        RemitoDetalle remitoDetalle = new RemitoDetalle(null, false, (Integer) jTabla.getValueAt(jTabla.getSelectedRow(), 0));
        remitoDetalle.setDatos();
        remitoDetalle.setVisible(true);
    }//GEN-LAST:event_showConsumidoActionPerformed

    private void showHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showHistorialActionPerformed
        if (jTabla.getSelectedRowCount() < 1)
            return;
        PresupuestoEventosDialog eventos = new PresupuestoEventosDialog(null, true, (Integer) jTabla.getValueAt(jTabla.getSelectedRow(), 0));
        eventos.setVisible(true);
    }//GEN-LAST:event_showHistorialActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private matera.gui.combobox.GlazedCombo entidadCombo;
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jBtnXls;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JComboBox jComboVendedor;
    private javax.swing.JComboBox jComboZona;
    private com.toedter.calendar.JDateChooser jDesde;
    private com.toedter.calendar.JDateChooser jHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTabla;
    private javax.swing.JTextField jTxtBusca;
    private javax.swing.JPopupMenu menu;
    private javax.swing.JTable obsRemitoTable;
    private javax.swing.JTextArea observacionesPresupuesto;
    private matera.gui.combobox.GlazedCombo profesionalCombo;
    private javax.swing.JMenuItem showConsumido;
    private javax.swing.JMenuItem showHistorial;
    // End of variables declaration//GEN-END:variables

    private void getPresupuestos() {
    try {
            modelo.removeAllRows();
            modelo.fireTableDataChanged();
            
            PresupuestoMgr mgr = new PresupuestoMgr();
            int id_entidad = ComboBoxMgr.getSelectedId(entidadCombo);
            int id_profesional = ComboBoxMgr.getSelectedId(profesionalCombo);
            int id_zona = ComboBoxMgr.getSelectedId(jComboZona);
            int id_vendedor = ComboBoxMgr.getSelectedId(jComboVendedor);
            Long since = this.jDesde.getDate() == null ? (long) 0 : this.jDesde.getDate().getTime();
            Long until = this.jHasta.getDate() == null ? (long) 0 : this.jHasta.getDate().getTime();

            ArrayList parametros = new ArrayList();
            parametros.add(since);
            parametros.add(until);
            parametros.add(id_entidad);
            parametros.add(id_profesional);
            parametros.add(id_zona);
            parametros.add(id_vendedor);
            parametros.add(this.id_empresa);
            
            rsConsulta = conexion.procAlmacenado("consultaPendienteFacturacion", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            if (rsConsulta.first()){
                rsConsulta.beforeFirst();
                
                List<Integer> presupuestos = new ArrayList<>();
                while(rsConsulta.next()){
                    presupuestos.add(rsConsulta.getInt("id_presupuesto"));
                    obsPresupuesto.put(rsConsulta.getInt("id_presupuesto"), rsConsulta.getString("observaciones"));
                }
                
                obsRemito = new RemitoDao().getObservacionesForPresupuestos(presupuestos);
                
                controlMap = new HashMap<>();
                Map<Integer, Result<PresupuestoControlfacturacionRecord>> m = mgr.getControlFacturacion(presupuestos)
                        .intoGroups(Tables.PRESUPUESTO_CONTROLFACTURACION.ID_PRESUPUESTO);
                        presupuestos.forEach((k)->{
                            if (m.get(k) != null)
                                controlMap.put( k,m.get(k).get(0));
                            else{
                                PresupuestoControlfacturacionRecord control = new PresupuestoControlfacturacionRecord(k,0,0,0,0,0,0,0,0);
                                control.attach(ActiveDatabase.getDSLContext().configuration());
                                controlMap.put(k, control);
                            }                            
                        });

                rsConsulta.beforeFirst();
                Utiles.resultSetToArrayList(rsConsulta).forEach(r->{
                    
                    PresupuestoControlfacturacionRecord control = controlMap.get(r.get("id_presupuesto"));
                    PendienteFacturacionTO to = new PendienteFacturacionTO(r, control);
                    modelo.addRow(to);
                });

            }else{
                JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }
}
