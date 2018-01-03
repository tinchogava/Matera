/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.RenderTablas.DateRenderer;
import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.EntidadCRUD;
import ar.com.bosoft.crud.VendedorCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.EntidadData;
import ar.com.bosoft.data.VendedorData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.vistasRapidas.ComprobantesAsociados;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class CargaRecibo extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, id_tipoComp;
    String empresa, usuario;
    
    ResultSet rsConsulta;
    DefaultTableModel modeloConsulta, modeloAplicacion;
    TableRowSorter sorterConsulta, sorterAplicacion;
    TableCellRenderer tableCellRenderer;

    VendedorCRUD vendedorCRUD;
    ZonaCRUD zonaCRUD;
    EntidadCRUD entidadCRUD;
    
    ArrayList vendedorArray, arrayId_cobradorComparte, zonaArray, arrayEntidad, arrayId_entidad;
    
    RoundingMode RM = RoundingMode.HALF_UP;
    int escala = 2;
        
    public CargaRecibo(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        this.id_tipoComp = 4;
        
        this.vendedorCRUD = new VendedorCRUD(conexion, id_empresa, empresa);
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.entidadCRUD = new EntidadCRUD(conexion, id_empresa, empresa);
        
        this.arrayId_cobradorComparte = new ArrayList();
        this.arrayId_entidad = new ArrayList();
        
        initComponents();
        
        tableCellRenderer = new DateRenderer();
        
        modeloConsulta = (DefaultTableModel) this.jTablaConsulta.getModel();
        jTablaConsulta.setModel(modeloConsulta);
        jTablaConsulta.setRowSorter (new TableRowSorter(modeloConsulta));
        sorterConsulta = new TableRowSorter(modeloConsulta);
        jTablaConsulta.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        
        modeloAplicacion = (DefaultTableModel) this.jTablaAplicacion.getModel();
        jTablaAplicacion.setModel(modeloAplicacion);
        jTablaAplicacion.setRowSorter (new TableRowSorter(modeloAplicacion));
        sorterAplicacion = new TableRowSorter(modeloAplicacion);
        jTablaAplicacion.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        
        setJTexFieldChanged(this.jTxtBuscaConsulta);
        
        llenaComboCobrador();
        llenaComboZona();
        consultaEntidad();
        limpia();
//        zonaUsuario();
        consulta();
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
    
    private void llenaComboCobrador(){
        vendedorArray = vendedorCRUD.consultaCobrador(true);
        if (!vendedorArray.isEmpty()){
            Iterator i = vendedorArray.iterator();
            while (i.hasNext()){
                VendedorData v = (VendedorData) i.next();
                this.jComboCobrador.addItem(v.getNombre());
            }
        }else{
            JOptionPane.showMessageDialog(this, "No hay agentes asignados a cobranza habilitados", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void llenaComboZona(){
        this.jComboZona.addItem("-- TODAS --");
        zonaArray = zonaCRUD.consulta(true);        
        Iterator i = zonaArray.iterator();
        while (i.hasNext()){
            ZonaData z = (ZonaData) i.next();
            this.jComboZona.addItem(z.getNombre());
        }
    }
    
    private void consultaEntidad(){
        arrayEntidad = entidadCRUD.consulta(true);
    }
    
    private void llenaComboEntidadLugar(int id_zona){
        this.arrayId_entidad.clear();
        this.jComboEntidad.removeAllItems();
        this.jComboEntidad.addItem("-- TODAS --");
        Iterator i = arrayEntidad.iterator();
        while (i.hasNext()){
            EntidadData item = (EntidadData) i.next();
            if (id_zona == 0) {
                this.jComboEntidad.addItem(item.getNombre());
                this.arrayId_entidad.add(item.getId_entidad());
            }else if (item.getId_zona() == id_zona){
                this.jComboEntidad.addItem(item.getNombre());
                this.arrayId_entidad.add(item.getId_entidad());
            }
        }
        this.jComboEntidad.setSelectedIndex(0);
    }
    
    private void limpia(){
        this.jFecha.setDate(new Date());
        this.jTxtSucursal.setText("");
        this.jTxtNumero.setText("");
        this.jComboCobrador.setSelectedIndex(-1);
        this.jComboCobradorActionPerformed(null);
        
        this.jComboFactura.setSelectedIndex(0);
        this.jDesde.setDate(null);
        this.jHasta.setDate(null);
        
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedIndex(0);
        }

        this.jComboEntidad.setSelectedIndex(0);
        this.jTxtBuscaConsulta.setText("");
        
        modeloAplicacion.getDataVector().removeAllElements();
        modeloAplicacion.fireTableDataChanged();
        this.jLblTotal.setText("0,00");
    }
    
    private void consulta(){
        try{
            modeloConsulta.getDataVector().removeAllElements();
            modeloConsulta.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            int factura = this.jComboFactura.getSelectedIndex();
            Long desde = this.jDesde.getDate() == null ? (long) 0 : this.jDesde.getDate().getTime();
            Long hasta = this.jHasta.getDate() == null ? (long) 0 : this.jHasta.getDate().getTime();
            
            int id_zona = 0;
            if (this.jComboZona.getSelectedIndex() > 0){
                ZonaData z = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
                id_zona = z.getId_zona();
            }
            
            int id_entidad = 0;
            if (this.jComboEntidad.getSelectedIndex() > 0){
                id_entidad = (int) arrayId_entidad.get(this.jComboEntidad.getSelectedIndex() - 1);
            }
            
            parametros.add(factura);
            parametros.add(desde);
            parametros.add(hasta);
            parametros.add(id_zona);
            parametros.add(id_entidad);
            parametros.add(this.id_empresa);
            
            rsConsulta = conexion.procAlmacenado("consultaFacturaRecibo", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsConsulta.beforeFirst();
            int indice = 0;
            while (rsConsulta.next()){
                int id_presupuesto = rsConsulta.getInt("id_presupuesto");
                Date fecha = rsConsulta.getDate("fecha");
                String tipoComp = rsConsulta.getString("tipoComp");
                String numComp = rsConsulta.getString("numCompRel");
                Double total = rsConsulta.getDouble("total");
                Double pendiente = rsConsulta.getDouble("pendiente");
                int id_tipoCompRel = rsConsulta.getInt("id_tipoCompRel");
                String entidad = rsConsulta.getString("entidad");
                
                Object[] fila = {id_presupuesto, fecha, tipoComp, numComp, total, 
                                    pendiente, id_tipoCompRel, entidad, indice};
                modeloConsulta.addRow(fila);
                indice++;
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
    
    private void actualizaTotal(){
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < modeloAplicacion.getRowCount(); i++){
            BigDecimal aplicado = new BigDecimal(Double.parseDouble(modeloAplicacion.getValueAt(i, 6).toString()));
            total = total.add(aplicado).setScale(escala, RM);
        }
        
        this.jLblTotal.setText(String.format("%.2f", total));
    }
    
    private String validaObligatorios(){
        String respuesta = "";
        
        if (this.jFecha.getDate() == null ||
                this.jTxtSucursal.getText().isEmpty() ||
                this.jTxtNumero.getText().isEmpty() ||
                this.jComboCobrador.getSelectedIndex() < 0){
            respuesta = "Complete todos los datos obligatorios (*)";
            return respuesta;
        }
        
        if (this.jCheckComparte.isSelected() &&
                this.jComboCobradorComparte.getSelectedIndex() < 0) {
            respuesta = "Seleccione el cobrador a compartir";
            return respuesta;
        }
        
        if (Double.parseDouble(this.jLblTotal.getText().replace(',', '.')) <= 0){
            respuesta = "No hay aplicaciones realizadas";
            return respuesta;
        }
        
        return respuesta;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu = new javax.swing.JPopupMenu();
        jDetallePresupuesto = new javax.swing.JMenuItem();
        jComprobantesAsociados = new javax.swing.JMenuItem();
        jReporte = new javax.swing.JMenuItem();
        jBtnSalir = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTxtBuscaConsulta = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
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
        jLabel16 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jComboFactura = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jComboEntidad = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jFecha = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jFecha.getJCalendar().setTodayButtonVisible(true); 
        this.jFecha.getJCalendar().setTodayButtonText("Hoy"); 
        this.jFecha.getJCalendar().setWeekOfYearVisible(false);
        jLabel14 = new javax.swing.JLabel();
        jTxtSucursal = new javax.swing.JTextField();
        jTxtSucursal.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(4,true));
        jTxtNumero = new javax.swing.JTextField();
        jTxtNumero.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(8,true));
        jLabel18 = new javax.swing.JLabel();
        jComboCobrador = new javax.swing.JComboBox();
        jCheckComparte = new javax.swing.JCheckBox();
        jComboCobradorComparte = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLblTotal = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablaAplicacion = new ar.com.bosoft.RenderTablas.RXTable();

        jDetallePresupuesto.setText("Ver presupuesto");
        jDetallePresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetallePresupuestoActionPerformed(evt);
            }
        });
        jPopupMenu.add(jDetallePresupuesto);

        jComprobantesAsociados.setText("Ver comprobantes");
        jComprobantesAsociados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComprobantesAsociadosActionPerformed(evt);
            }
        });
        jPopupMenu.add(jComprobantesAsociados);

        jReporte.setText("Reporte");
        jReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jReporteActionPerformed(evt);
            }
        });
        jPopupMenu.add(jReporte);

        setTitle("Carga recibo");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

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
            .addGroup(jPanel1Layout.createSequentialGroup()
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
                "Turno", "Fecha", "", "Comprobante", "Total", "Pendiente", "id_tipoCompRel", "Entidad", "indice"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaConsulta.setComponentPopupMenu(jPopupMenu);
        jTablaConsulta.getTableHeader().setReorderingAllowed(false);
        jTablaConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaConsultaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaConsulta);
        if (jTablaConsulta.getColumnModel().getColumnCount() > 0) {
            jTablaConsulta.getColumnModel().getColumn(0).setMinWidth(50);
            jTablaConsulta.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTablaConsulta.getColumnModel().getColumn(0).setMaxWidth(50);
            jTablaConsulta.getColumnModel().getColumn(1).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(1).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(2).setMinWidth(45);
            jTablaConsulta.getColumnModel().getColumn(2).setPreferredWidth(45);
            jTablaConsulta.getColumnModel().getColumn(2).setMaxWidth(45);
            jTablaConsulta.getColumnModel().getColumn(4).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(4).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(4).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(5).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(5).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(5).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(6).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(6).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(6).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(8).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(8).setMaxWidth(0);
        }

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

        jLabel16.setText("Zona");

        jComboZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboZonaActionPerformed(evt);
            }
        });

        jComboFactura.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todas", "Pendientes", "Cobradas" }));

        jLabel19.setText("Facturas");

        jLabel20.setText("Entidad");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel4)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnFiltra)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel11)
                            .addComponent(jLabel4)
                            .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnFiltra)))
                .addGap(2, 2, 2))
        );

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos del recibo"));

        jLabel12.setText("* Fecha");

        jLabel14.setText("* Número");

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

        jLabel18.setText("* Cobrador");

        jComboCobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboCobradorActionPerformed(evt);
            }
        });

        jCheckComparte.setText("Comparte");
        jCheckComparte.setEnabled(false);
        jCheckComparte.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jCheckComparte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckComparteActionPerformed(evt);
            }
        });

        jComboCobradorComparte.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCheckComparte)
                    .addComponent(jLabel18)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboCobrador, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboCobradorComparte, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14)
                    .addComponent(jTxtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jComboCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckComparte)
                    .addComponent(jComboCobradorComparte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1))
        );

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Aplicación"));

        jLabel17.setText("* Total");

        jLblTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblTotal.setText("0,00");

        jTablaAplicacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Turno", "Fecha", "", "Comprobante", "Total", "Pendiente", "Aplicación", "Saldo final", "id_tipoCompRel", "indice"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaAplicacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaAplicacionMouseClicked(evt);
            }
        });
        jTablaAplicacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTablaAplicacionKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jTablaAplicacion);
        if (jTablaAplicacion.getColumnModel().getColumnCount() > 0) {
            jTablaAplicacion.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTablaAplicacion.getColumnModel().getColumn(0).setMaxWidth(75);
            jTablaAplicacion.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTablaAplicacion.getColumnModel().getColumn(1).setMaxWidth(150);
            jTablaAplicacion.getColumnModel().getColumn(2).setPreferredWidth(45);
            jTablaAplicacion.getColumnModel().getColumn(2).setMaxWidth(65);
            jTablaAplicacion.getColumnModel().getColumn(4).setPreferredWidth(75);
            jTablaAplicacion.getColumnModel().getColumn(4).setMaxWidth(120);
            jTablaAplicacion.getColumnModel().getColumn(5).setPreferredWidth(75);
            jTablaAplicacion.getColumnModel().getColumn(5).setMaxWidth(120);
            jTablaAplicacion.getColumnModel().getColumn(6).setPreferredWidth(75);
            jTablaAplicacion.getColumnModel().getColumn(6).setMaxWidth(120);
            jTablaAplicacion.getColumnModel().getColumn(7).setPreferredWidth(75);
            jTablaAplicacion.getColumnModel().getColumn(7).setMaxWidth(120);
            jTablaAplicacion.getColumnModel().getColumn(8).setMinWidth(0);
            jTablaAplicacion.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTablaAplicacion.getColumnModel().getColumn(8).setMaxWidth(0);
            jTablaAplicacion.getColumnModel().getColumn(9).setMinWidth(0);
            jTablaAplicacion.getColumnModel().getColumn(9).setPreferredWidth(0);
            jTablaAplicacion.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(372, 372, 372)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblTotal)
                    .addComponent(jLabel17))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnGuardar)
                        .addGap(154, 154, 154)
                        .addComponent(jBtnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnSalir)
                    .addComponent(jBtnGuardar))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        try {
            if (this.rsConsulta != null){
                this.rsConsulta.close();
            }
            this.dispose();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios().isEmpty()){
            try{
                ArrayList parametros = new ArrayList();
                
                Long fecha = this.jFecha.getDate().getTime();
                String numComp = this.jTxtSucursal.getText() + this.jTxtNumero.getText();
                
                parametros.add(this.id_tipoComp);
                parametros.add(numComp);
                parametros.add(this.id_empresa);
                
                rsConsulta = conexion.procAlmacenado("existeFactura", parametros, 
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                
                if (rsConsulta.first()) {
                    JOptionPane.showMessageDialog(this, "Este comprobante ya ha sido cargado", "Atención", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                VendedorData v = (VendedorData) vendedorArray.get(this.jComboCobrador.getSelectedIndex());
                int id_vendedor = v.getId_vendedor();
                
                String comparte = (this.jCheckComparte.isSelected() ? "S" : "N");
                int id_vendedorComparte = (comparte.equals("S") ? (int) arrayId_cobradorComparte.get(this.jComboCobradorComparte.getSelectedIndex()) : 0);
                Long vencimiento = (long) 0;
                int id_formaPago = 0;
                String dc = "C";
                Double bonificacion = 0.00;
                Double percIIBB = 0.00;
                Double aliPercIIBB = 0.00;
                Double iva = 0.00;
                Double aliIva = 0.00;
                String observaciones = "";
                
                for (int i = 0; i < modeloAplicacion.getRowCount(); i++){
                    Double aplicacion = Double.parseDouble(modeloAplicacion.getValueAt(i, 6).toString());
                    if (aplicacion > 0){
                        parametros.clear();
                        
                        int id_presupuesto = Integer.parseInt(modeloAplicacion.getValueAt(i, 0).toString());
                        int id_tipoCompRel = Integer.parseInt(modeloAplicacion.getValueAt(i, 8).toString());
                        String numCompRel = modeloAplicacion.getValueAt(i, 3).toString();
                        Double neto = Double.parseDouble(modeloAplicacion.getValueAt(i, 6).toString());
                        
                        parametros.add(fecha);
                        parametros.add(id_presupuesto);
                        parametros.add(this.id_tipoComp);
                        parametros.add(numComp);
                        parametros.add(id_tipoCompRel);
                        parametros.add(numCompRel);
                        parametros.add(vencimiento);
                        parametros.add(id_formaPago);
                        parametros.add(dc);
                        parametros.add(bonificacion);
                        parametros.add(neto);
                        parametros.add(aliPercIIBB);
                        parametros.add(percIIBB);
                        parametros.add(aliIva);
                        parametros.add(iva);
                        parametros.add(observaciones);
                        parametros.add(id_vendedor);
                        parametros.add(comparte);
                        parametros.add(id_vendedorComparte);                        
                        parametros.add(this.id_empresa);
                        parametros.add(this.usuario);                        
                        
                        conexion.procAlmacenado("insertFactura", parametros, 
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    }
                }
                
                limpia();
                consulta();
                this.jTxtBuscaConsulta.requestFocus();
            } catch (SQLException | HeadlessException | NumberFormatException ex) {
                Utiles.enviaError(this.empresa,ex);
            }   
        }else{
            JOptionPane.showMessageDialog(this, validaObligatorios(), "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jTxtBuscaConsultaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaConsultaFocusGained
        this.jTxtBuscaConsulta.select(0, this.jTxtBuscaConsulta.getText().length());
    }//GEN-LAST:event_jTxtBuscaConsultaFocusGained
 
    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        consulta();
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jTablaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsultaMouseClicked
        if (evt.getClickCount() == 2){
            int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
            int indice = Integer.parseInt(modeloConsulta.getValueAt(fila, 8).toString());
            
            for (int i = 0; i < modeloAplicacion.getRowCount(); i++){
                if (Integer.parseInt(modeloAplicacion.getValueAt(i, 9).toString()) == indice){
                    JOptionPane.showMessageDialog(this, "Ya se ha seleccionado éste comprobante", "Atención", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            
            int id_presupuesto = Integer.parseInt(modeloConsulta.getValueAt(fila, 0).toString());
            Date fecha = (Date) modeloConsulta.getValueAt(fila, 1);
            String tipoComp = modeloConsulta.getValueAt(fila, 2).toString();
            String numComp = modeloConsulta.getValueAt(fila, 3).toString();
            Double total = Double.parseDouble(modeloConsulta.getValueAt(fila, 4).toString());
            Double pendiente = Double.parseDouble(modeloConsulta.getValueAt(fila, 5).toString());
            int id_tipoCompRel = Integer.parseInt(modeloConsulta.getValueAt(fila, 6).toString());

            Object[] nuevo = {id_presupuesto, fecha, tipoComp, numComp, total, 
                                pendiente, 0.00, 0.00, id_tipoCompRel, indice};
            modeloAplicacion.addRow(nuevo);
        }
    }//GEN-LAST:event_jTablaConsultaMouseClicked

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

    private void jDetallePresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetallePresupuestoActionPerformed
        int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
        if (fila >= 0) {
            Presupuesto presupuesto = new Presupuesto(conexion, id_empresa, empresa, "");
            presupuesto.setId_presupuesto(modeloConsulta.getValueAt(fila, 0).toString());
            Principal.dp.add(presupuesto);
            presupuesto.toFront();
            presupuesto.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetallePresupuestoActionPerformed

    private void jComprobantesAsociadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComprobantesAsociadosActionPerformed
        int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
        if (fila >= 0) {
            ComprobantesAsociados comprobantesAsociados = new ComprobantesAsociados(null, true, conexion, id_empresa, empresa);
            comprobantesAsociados.setId_presupuesto(modeloConsulta.getValueAt(fila, 0).toString());
            comprobantesAsociados.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jComprobantesAsociadosActionPerformed

    private void jTablaAplicacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaAplicacionMouseClicked
        if(evt.getClickCount() == 2){
            int fila = jTablaAplicacion.convertRowIndexToModel(jTablaAplicacion.getSelectedRow());
            modeloAplicacion.removeRow(fila);
            
            actualizaTotal();
        }
    }//GEN-LAST:event_jTablaAplicacionMouseClicked

    private void jTablaAplicacionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTablaAplicacionKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            int fila = jTablaAplicacion.convertRowIndexToModel(jTablaAplicacion.getSelectedRow());
            Double aplicacion = Double.parseDouble(modeloAplicacion.getValueAt(fila, 6).toString());
            Double pendiente = Double.parseDouble(modeloAplicacion.getValueAt(fila, 5).toString());
            if (aplicacion > pendiente){
                aplicacion = pendiente;
                modeloAplicacion.setValueAt(pendiente, fila, 6);
            }
            
            modeloAplicacion.setValueAt((pendiente - aplicacion), fila, 7);
            
            actualizaTotal();
        }
    }//GEN-LAST:event_jTablaAplicacionKeyReleased

    private void jCheckComparteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckComparteActionPerformed
        this.jComboCobradorComparte.removeAllItems();  
        this.arrayId_cobradorComparte.clear();
        
        if (this.jCheckComparte.isSelected()) {
            String cobrador = this.jComboCobrador.getSelectedItem().toString();
            Iterator i = vendedorArray.iterator();
            while (i.hasNext()){
                VendedorData v = (VendedorData) i.next();
                if (!v.getNombre().equals(cobrador)) {
                    this.jComboCobradorComparte.addItem(v.getNombre());
                    this.arrayId_cobradorComparte.add(v.getId_vendedor());
                } 
            }
        }
        this.jComboCobradorComparte.setSelectedIndex(-1);  
        this.jComboCobradorComparte.setEnabled(this.jCheckComparte.isSelected());
    }//GEN-LAST:event_jCheckComparteActionPerformed

    private void jComboCobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboCobradorActionPerformed
        this.jCheckComparte.setEnabled(this.jComboCobrador.getSelectedIndex() >= 0);
        this.jCheckComparte.setSelected(false);
        this.jCheckComparteActionPerformed(evt);
    }//GEN-LAST:event_jComboCobradorActionPerformed

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        try {
            int id_zona = 0;
            if (this.jComboZona.getSelectedIndex() > 0) {
                ZonaData z = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
                id_zona = z.getId_zona();
            }
            llenaComboEntidadLugar(id_zona);
        } catch (Exception e) {
        }
            
    }//GEN-LAST:event_jComboZonaActionPerformed

    private void jReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jReporteActionPerformed
        if (modeloConsulta.getRowCount() > 0) {
            Salida salida = new Salida(null, true);
            salida.setVisible(true);
            if (salida.getSiNo()) {
                Map param = new HashMap();
                param.put("empresa", this.empresa);
                param.put("facturas", this.jComboFactura.getSelectedItem().toString());
                param.put("desde", this.jDesde.getDate());
                param.put("hasta", this.jHasta.getDate());
                param.put("zona", this.jComboZona.getSelectedItem().toString());
                param.put("entidad", this.jComboEntidad.getSelectedItem().toString());


                Reporte reporte = new Reporte();
                reporte.startReport("ReporteRecibo", param, new JRTableModelDataSource(modeloConsulta), salida.getTipoSalida(), salida.getRutaArchivo(), salida.getImpresora(), salida.getCopias());
            }
        }else{
            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        } 
    }//GEN-LAST:event_jReporteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JCheckBox jCheckComparte;
    private javax.swing.JComboBox jComboCobrador;
    private javax.swing.JComboBox jComboCobradorComparte;
    private javax.swing.JComboBox jComboEntidad;
    private javax.swing.JComboBox jComboFactura;
    private javax.swing.JComboBox jComboZona;
    private javax.swing.JMenuItem jComprobantesAsociados;
    private com.toedter.calendar.JDateChooser jDesde;
    private javax.swing.JMenuItem jDetallePresupuesto;
    private com.toedter.calendar.JDateChooser jFecha;
    private com.toedter.calendar.JDateChooser jHasta;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLblTotal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JMenuItem jReporte;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private ar.com.bosoft.RenderTablas.RXTable jTablaAplicacion;
    private javax.swing.JTable jTablaConsulta;
    private javax.swing.JTextField jTxtBuscaConsulta;
    private javax.swing.JTextField jTxtNumero;
    private javax.swing.JTextField jTxtSucursal;
    // End of variables declaration//GEN-END:variables
}
