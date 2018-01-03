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
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.FormaPagoCRUD;
import ar.com.bosoft.crud.TipoCompCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.FormaPagoData;
import ar.com.bosoft.data.TipoCompData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.vistasRapidas.Turno;
import java.awt.HeadlessException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
public class CargaFactura extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, id_presupuesto;
    String empresa, usuario;
    
    ResultSet rsConsulta;
    DefaultTableModel modeloConsulta;
    TableRowSorter sorterConsulta;
    TableCellRenderer tableCellRenderer;
    
    ZonaCRUD zonaCRUD;
    TipoCompCRUD tipoCompCRUD;
    FormaPagoCRUD formaPagoCRUD;
    
    ArrayList zonaArray, tipoCompArray, formaPagoArray;
    
    public CargaFactura(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.tipoCompCRUD = new TipoCompCRUD(conexion, empresa);
        this.formaPagoCRUD = new FormaPagoCRUD(conexion, empresa);
        
        initComponents();
        
        modeloConsulta = (DefaultTableModel) this.jTablaConsulta.getModel();
        jTablaConsulta.setModel(modeloConsulta);
        jTablaConsulta.setRowSorter (new TableRowSorter(modeloConsulta));
        sorterConsulta = new TableRowSorter(modeloConsulta);
        tableCellRenderer = new DateRenderer();
        jTablaConsulta.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        
        setJTexFieldChanged(this.jTxtBuscaConsulta);
        
        llenaComboZona();
        llenaComboTipoComp();
        llenaComboFormaPago();
        limpia();
        zonaUsuario();
        limpiaPanel();
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
    
    private void llenaComboZona(){
        zonaArray = zonaCRUD.consulta(true);
        Iterator i = zonaArray.iterator();
        while (i.hasNext()){
            ZonaData z = (ZonaData) i.next();
            this.jComboZona.addItem(z.getNombre());
        }
    }
    
    private void llenaComboTipoComp(){
        tipoCompArray = tipoCompCRUD.consulta();
        if (!tipoCompArray.isEmpty()){
            Iterator i = tipoCompArray.iterator();
            while (i.hasNext()){
                TipoCompData t = (TipoCompData) i.next();
                this.jComboTipoComp.addItem(t.getAbreviatura());
            }
        }else{
            JOptionPane.showMessageDialog(this, "No hay tipos de comprobantes habilitados", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void llenaComboFormaPago(){
        formaPagoArray = formaPagoCRUD.consulta(true);
        if (!formaPagoArray.isEmpty()){
            Iterator i = formaPagoArray.iterator();
            while (i.hasNext()){
                FormaPagoData f = (FormaPagoData) i.next();
                this.jComboFormaPago.addItem(f.getNombre());
            }
        }else{
            JOptionPane.showMessageDialog(this, "No hay formas de pago habilitadas", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void limpia(){
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedIndex(-1);
        }
        
        this.jTxtBuscaConsulta.setText("");
        this.id_presupuesto = 0;
    }
    
    private void limpiaPanel(){
        this.jLblId_presupuesto.setText("");
        this.jFecha.setDate(new Date());
        this.jComboTipoComp.setSelectedIndex(tipoCompArray.isEmpty() ? -1 : 0);
        this.jTxtSucursal.setText("");
        this.jTxtNumero.setText("");
        this.jVence.setDate(null);
        this.jComboFormaPago.setSelectedIndex(formaPagoArray.isEmpty() ? -1 : 0);
        this.jFmtSubtotal.setValue(0.00);
        this.jFmtBonificacion.setValue(0.00);
        this.jLblNeto.setText("0,00");
        this.jFmtPercIIBB.setValue(0.00);
        this.jLblPercIIBB.setText("0,00");
        this.jComboAliIva.setSelectedIndex(0);
        this.jLblIva.setText("0,00");
        this.jLblTotal.setText("0,00");
        this.jTxtObservaciones.setText("");
    }
    
    private void consulta(){
        try{
            modeloConsulta.getDataVector().removeAllElements();
            modeloConsulta.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            
            int id_zona = 0;
            if (this.jComboZona.getSelectedIndex() >= 0){
                ZonaData z = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex());
                id_zona = z.getId_zona();
            }
            
            parametros.add(id_zona);
            parametros.add(this.id_empresa);
            
            rsConsulta = conexion.procAlmacenado("consultaPresuFactura", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsConsulta.beforeFirst();
            while (rsConsulta.next()){
                int id = rsConsulta.getInt("id_presupuesto");
                Date fechaCirugia = rsConsulta.getDate("fechaCirugia");
                String lugarCirugia = rsConsulta.getString("lugarCirugia");
                String profesional = rsConsulta.getString("profesional");
                String paciente = rsConsulta.getString("paciente");
                Double total = rsConsulta.getDouble("total");
                Double facturado = rsConsulta.getDouble("facturado");
                String formaPago = rsConsulta.getString("formaPago");
                String prestacion = rsConsulta.getString("prestacion");
                String tecnico = rsConsulta.getString("tecnico");
                String observaciones = rsConsulta.getString("observaciones");
                
                BigDecimal t = new BigDecimal(total);
                BigDecimal f = new BigDecimal(facturado);
                BigDecimal saldo = t.subtract(f).setScale(2, RoundingMode.HALF_UP);
                
                Object[] fila = {id, fechaCirugia, lugarCirugia, profesional, paciente, 
                                    total, saldo, formaPago, prestacion, tecnico,
                                    observaciones};
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
    
    private void actualizaValores(){
        RoundingMode RM = RoundingMode.HALF_UP;
        int escala = 2;
        BigDecimal cien = new BigDecimal(100);
        
        BigDecimal subtotal = new BigDecimal(Double.parseDouble(this.jFmtSubtotal.getValue().toString()));
        BigDecimal bonificacion = new BigDecimal(Double.parseDouble(this.jFmtBonificacion.getValue().toString()));

        BigDecimal neto = subtotal.subtract(bonificacion).setScale(escala, RM);

        BigDecimal aliPerc = new BigDecimal(Double.parseDouble(this.jFmtPercIIBB.getValue().toString()));
        aliPerc = aliPerc.divide(cien, 4, RM);
        BigDecimal perc = neto.multiply(aliPerc).setScale(escala, RM);

        BigDecimal aliIva = new BigDecimal(Double.parseDouble(this.jComboAliIva.getSelectedItem().toString().replace(',', '.')));
        aliIva = aliIva.divide(cien, 4, RM);
        BigDecimal iva = neto.multiply(aliIva).setScale(escala, RM);
        
        BigDecimal total = neto.add(perc).setScale(escala, RM);
        total = total.add(iva).setScale(escala, RM);
        
        this.jLblNeto.setText(String.format("%.2f", neto));
        this.jLblPercIIBB.setText(String.format("%.2f", perc));
        this.jLblIva.setText(String.format("%.2f", iva));
        this.jLblTotal.setText(String.format("%.2f", total));
    }
    
    private String validaObligatorios(){
        String respuesta = "";
        
        if (this.jLblId_presupuesto.getText().isEmpty() ||
                this.jFecha.getDate() == null ||
                this.jComboTipoComp.getSelectedIndex() < 0 ||
                this.jTxtSucursal.getText().isEmpty() ||
                this.jTxtNumero.getText().isEmpty() ||
                this.jComboFormaPago.getSelectedIndex() < 0 ||
                Double.parseDouble(this.jLblTotal.getText().replace(".", "").replace(',', '.')) <= 0){
            respuesta = "Complete todos los datos obligatorios (*)";
            return respuesta;
        }
        
        if (this.jVence.getDate() != null){
            if (this.jVence.getDate().before(this.jFecha.getDate())){
                respuesta = "Hay un error en las fechas";
                return respuesta;
            }
        }
        
        return respuesta;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu = new javax.swing.JPopupMenu();
        jDetalleTurno = new javax.swing.JMenuItem();
        jDetallePresupuesto = new javax.swing.JMenuItem();
        jDetalleConsumido = new javax.swing.JMenuItem();
        jBtnSalir = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTxtBuscaConsulta = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jBtnFiltra = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jFecha = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jFecha.getJCalendar().setTodayButtonVisible(true); 
        this.jFecha.getJCalendar().setTodayButtonText("Hoy"); 
        this.jFecha.getJCalendar().setWeekOfYearVisible(false);
        jLabel1 = new javax.swing.JLabel();
        jComboTipoComp = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jTxtSucursal = new javax.swing.JTextField();
        jTxtSucursal.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(4,true));
        jTxtNumero = new javax.swing.JTextField();
        jTxtNumero.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(8,true));
        jLabel3 = new javax.swing.JLabel();
        jVence = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jVence.getJCalendar().setTodayButtonVisible(true); 
        this.jVence.getJCalendar().setTodayButtonText("Hoy"); 
        this.jVence.getJCalendar().setWeekOfYearVisible(false);
        jLabel6 = new javax.swing.JLabel();
        jComboFormaPago = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jFmtSubtotal = new ar.com.bosoft.formatosCampos.Decimal(true);
        ;
        jLabel9 = new javax.swing.JLabel();
        jFmtBonificacion = new ar.com.bosoft.formatosCampos.Decimal(true);  ;
        jLabel10 = new javax.swing.JLabel();
        jLblNeto = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jFmtPercIIBB = new ar.com.bosoft.formatosCampos.Decimal(true);  ;
        jLabel15 = new javax.swing.JLabel();
        jLblPercIIBB = new javax.swing.JLabel();
        jLblIva = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLblTotal = new javax.swing.JLabel();
        jLblId_presupuesto = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();
        jComboAliIva = new javax.swing.JComboBox();
        jBtnDesactiva = new javax.swing.JButton();

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

        jDetalleConsumido.setText("Consumido");
        jDetalleConsumido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetalleConsumidoActionPerformed(evt);
            }
        });
        jPopupMenu.add(jDetalleConsumido);

        setTitle("Carga de factura");
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBuscaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 897, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                "Turno", "Fecha Cx", "Lugar Cx", "Profesional", "Paciente", "Importe", "Saldo", "formaPago", "prestacion", "tecnico", "observaciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
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
            jTablaConsulta.getColumnModel().getColumn(5).setMinWidth(100);
            jTablaConsulta.getColumnModel().getColumn(5).setPreferredWidth(100);
            jTablaConsulta.getColumnModel().getColumn(5).setMaxWidth(100);
            jTablaConsulta.getColumnModel().getColumn(6).setMinWidth(100);
            jTablaConsulta.getColumnModel().getColumn(6).setPreferredWidth(100);
            jTablaConsulta.getColumnModel().getColumn(6).setMaxWidth(100);
            jTablaConsulta.getColumnModel().getColumn(7).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(7).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(7).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(8).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(8).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(9).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(9).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(9).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(10).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(10).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(10).setMaxWidth(0);
        }

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnFiltra)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16))
                    .addComponent(jBtnFiltra))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos del comprobante"));

        jLabel5.setText("* Fecha");

        jLabel1.setText("* Tipo");

        jLabel2.setText("* Número");

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

        jLabel3.setText("Vencimiento");

        jLabel6.setText("* Forma de pago");

        jLabel7.setText("* Subtotal");

        jFmtSubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmtSubtotal.setNextFocusableComponent(jFmtBonificacion);
        jFmtSubtotal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFmtSubtotalFocusLost(evt);
            }
        });

        jLabel9.setText("Bonificación");

        jFmtBonificacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmtBonificacion.setNextFocusableComponent(jFmtPercIIBB);
        jFmtBonificacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFmtBonificacionFocusLost(evt);
            }
        });

        jLabel10.setText("Neto");

        jLblNeto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLblNeto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblNeto.setText("0,00");

        jLabel13.setText("Perc. IIBB (%)");

        jFmtPercIIBB.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmtPercIIBB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFmtPercIIBBFocusLost(evt);
            }
        });

        jLabel15.setText("IVA (%)");

        jLblPercIIBB.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLblPercIIBB.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblPercIIBB.setText("0,00");

        jLblIva.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLblIva.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblIva.setText("0,00");

        jLabel21.setText("Total");

        jLblTotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblTotal.setText("0,00");

        jLblId_presupuesto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblId_presupuesto.setText("...");

        jLabel100.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel100.setText("* Turno");

        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jTxtObservaciones.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Observaciones"));
        jScrollPane2.setViewportView(jTxtObservaciones);

        jComboAliIva.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "21", "10,5", "0,0" }));
        jComboAliIva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboAliIvaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel15)
                    .addComponent(jLabel13)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel100))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jComboAliIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLblIva, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jFmtPercIIBB, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLblPercIIBB, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jFmtSubtotal, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jFmtBonificacion, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLblNeto, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                        .addGap(16, 16, 16)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 703, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboTipoComp, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jVence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLblId_presupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 859, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblId_presupuesto)
                    .addComponent(jLabel100))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1)
                    .addComponent(jComboTipoComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel3)
                        .addComponent(jTxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTxtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jVence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jFmtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jFmtBonificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLblNeto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jFmtPercIIBB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblPercIIBB))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jLblIva)
                            .addComponent(jComboAliIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jLblTotal)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBtnDesactiva.setText("Desactivar");
        jBtnDesactiva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDesactivaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(127, 127, 127)
                .addComponent(jBtnDesactiva, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnGuardar)
                        .addGap(314, 314, 314)
                        .addComponent(jBtnSalir))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jBtnDesactiva)
                        .addGap(3, 3, 3)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jBtnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBtnGuardar))))
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

                TipoCompData t = (TipoCompData) tipoCompArray.get(this.jComboTipoComp.getSelectedIndex());
                int id_tipoComp = t.getId_tipoComp();

                String numComp = this.jTxtSucursal.getText() + this.jTxtNumero.getText();
                Long vencimiento = (this.jVence.getDate() != null ? this.jVence.getDate().getTime() : (long) 0);

                FormaPagoData f = (FormaPagoData) formaPagoArray.get(this.jComboFormaPago.getSelectedIndex());
                int id_formaPago = f.getId_formaPago();

                String dc = "D";
                if (id_tipoComp == 3 ||
                        id_tipoComp == 7 ||
                        id_tipoComp == 10 ||
                        id_tipoComp == 13 ||
                        id_tipoComp == 25){
                    dc = "C";
                }

                Double bonificacion = Double.parseDouble(this.jFmtBonificacion.getValue().toString());
                Double neto = Double.parseDouble(this.jLblNeto.getText().replace(".", "").replace(',', '.'));
                Double aliPercIIBB = Double.parseDouble(this.jFmtPercIIBB.getValue().toString());
                Double percIIBB = Double.parseDouble(this.jLblPercIIBB.getText().replace(".", "").replace(',', '.'));
                Double aliIva = Double.parseDouble(this.jComboAliIva.getSelectedItem().toString().replace(',', '.'));
                Double iva = Double.parseDouble(this.jLblIva.getText().replace(".", "").replace(',', '.'));
                String observaciones = this.jTxtObservaciones.getText().trim();

                parametros.add(id_tipoComp);
                parametros.add(numComp);
                parametros.add(this.id_empresa);
                
                rsConsulta = conexion.procAlmacenado("existeFactura", parametros, 
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                
                if (rsConsulta.first()) {
                    int turno = rsConsulta.getInt("id_presupuesto");
                    JOptionPane.showMessageDialog(this, "Este comprobante ya ha sido cargado " + 
                                                            (turno == this.id_presupuesto ? "a este presupuesto" : 
                                                                                            "al presupuesto " + turno), "Atención", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                parametros.clear();
                parametros.add(fecha);
                parametros.add(this.id_presupuesto);
                parametros.add(id_tipoComp);
                parametros.add(numComp);
                parametros.add(id_tipoComp);
                parametros.add(numComp);
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
                parametros.add(0);
                parametros.add("N");
                parametros.add(0);
                parametros.add(this.id_empresa);
                parametros.add(this.usuario);

                conexion.procAlmacenado("insertFactura", parametros, 
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                limpia();
                limpiaPanel();
                consulta();
                this.jTxtBuscaConsulta.requestFocus();
            } catch (NumberFormatException | SQLException | HeadlessException ex) {
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
        int indiceModelo = jTablaConsulta.convertRowIndexToModel(this.jTablaConsulta.getSelectedRow());
        this.id_presupuesto = Integer.parseInt(modeloConsulta.getValueAt(indiceModelo, 0).toString());
        this.jLblId_presupuesto.setText(String.valueOf(this.id_presupuesto));
        
        this.jComboFormaPago.setSelectedIndex(-1);
        this.jComboFormaPago.setSelectedItem(modeloConsulta.getValueAt(indiceModelo, 7).toString());
    }//GEN-LAST:event_jTablaConsultaMouseClicked

    private void jFmtSubtotalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFmtSubtotalFocusLost
        actualizaValores();
    }//GEN-LAST:event_jFmtSubtotalFocusLost

    private void jFmtBonificacionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFmtBonificacionFocusLost
        actualizaValores();
    }//GEN-LAST:event_jFmtBonificacionFocusLost

    private void jFmtPercIIBBFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFmtPercIIBBFocusLost
        actualizaValores();
    }//GEN-LAST:event_jFmtPercIIBBFocusLost

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

    private void jComboAliIvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboAliIvaActionPerformed
        actualizaValores();
    }//GEN-LAST:event_jComboAliIvaActionPerformed

    private void jDetalleTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetalleTurnoActionPerformed
        if (jTablaConsulta.getSelectedRow() >= 0){
            int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
        
            Turno turno = new Turno(null, true, conexion, id_empresa, empresa);
            turno.setId_presupuesto(modeloConsulta.getValueAt(fila, 0).toString());
            turno.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetalleTurnoActionPerformed

    private void jDetallePresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetallePresupuestoActionPerformed
        if (jTablaConsulta.getSelectedRow() >= 0){
            int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
        
            Presupuesto presupuesto = new Presupuesto(conexion, id_empresa, empresa, this.usuario);
            presupuesto.setId_presupuesto(modeloConsulta.getValueAt(fila, 0).toString());
            Principal.dp.add(presupuesto);
            presupuesto.toFront();
            presupuesto.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetallePresupuestoActionPerformed

    private void jDetalleConsumidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetalleConsumidoActionPerformed
        if (jTablaConsulta.getSelectedRow() >= 0){
            int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
        
            String fechaCirugia = "";
            try {
                fechaCirugia = new SimpleDateFormat("dd/MM/yyyy").format((Date) modeloConsulta.getValueAt(fila, 1));
            } catch (Exception e) {
            }
            String lugarCx = modeloConsulta.getValueAt(fila, 2).toString();
            String profesional = modeloConsulta.getValueAt(fila, 3).toString();
            String paciente = modeloConsulta.getValueAt(fila, 4).toString();
            String prestacion = modeloConsulta.getValueAt(fila, 8).toString();
            String tecnico = modeloConsulta.getValueAt(fila, 9).toString();
            String observaciones = modeloConsulta.getValueAt(fila, 10).toString();
            
            RemitoDetalle remitoDetalle = new RemitoDetalle(null, false, id_presupuesto);
            remitoDetalle.setDatos();
            remitoDetalle.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetalleConsumidoActionPerformed

    private void jBtnDesactivaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDesactivaActionPerformed
        if (jTablaConsulta.getSelectedRow() >= 0){
            int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
            int id = (int)modeloConsulta.getValueAt(fila, 0);
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Realmente desea desactivar el presupuesto " + id + "?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                ArrayList parametros = new ArrayList();
                parametros.add(id);
                
                conexion.procAlmacenado("desactivaPresupuesto", parametros, 
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                
                limpia();
                limpiaPanel();
                consulta();
                this.jTxtBuscaConsulta.requestFocus();
            }
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a desactivar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnDesactivaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnDesactiva;
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JComboBox jComboAliIva;
    private javax.swing.JComboBox jComboFormaPago;
    private javax.swing.JComboBox jComboTipoComp;
    private javax.swing.JComboBox jComboZona;
    private javax.swing.JMenuItem jDetalleConsumido;
    private javax.swing.JMenuItem jDetallePresupuesto;
    private javax.swing.JMenuItem jDetalleTurno;
    private com.toedter.calendar.JDateChooser jFecha;
    private javax.swing.JFormattedTextField jFmtBonificacion;
    private javax.swing.JFormattedTextField jFmtPercIIBB;
    private javax.swing.JFormattedTextField jFmtSubtotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblId_presupuesto;
    private javax.swing.JLabel jLblIva;
    private javax.swing.JLabel jLblNeto;
    private javax.swing.JLabel jLblPercIIBB;
    private javax.swing.JLabel jLblTotal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTablaConsulta;
    private javax.swing.JTextField jTxtBuscaConsulta;
    private javax.swing.JTextField jTxtNumero;
    private javax.swing.JTextArea jTxtObservaciones;
    private javax.swing.JTextField jTxtSucursal;
    private com.toedter.calendar.JDateChooser jVence;
    // End of variables declaration//GEN-END:variables
}
