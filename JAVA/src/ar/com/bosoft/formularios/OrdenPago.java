/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.LiquidacionProfesionalDataSource;
import ar.com.bosoft.Modelos.LiquidacionProfesional;
import ar.com.bosoft.RenderTablas.DateRenderer;
import ar.com.bosoft.RenderTablas.FilasResaltadas;
import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ProfesionalCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.ProfesionalData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.vistasRapidas.Turno;
import java.awt.Color;
import java.awt.HeadlessException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class OrdenPago extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, id_profesional, copias, indiceDetalle;
    String empresa, usuario, impresora, rutaArchivo, dni;
    
    ResultSet rsConsulta, rsLiquidacion;
    DefaultTableModel modeloConsulta, modeloDetalle;
    TableRowSorter sorterConsulta, sorterDetalle;
    TableCellRenderer tableCellRenderer;
    
    ZonaCRUD zonaCRUD;
    ProfesionalCRUD profesionalCRUD;
    
    ArrayList zonaArray,profesionalArray, arrayId_profesional;
    
    RoundingMode RM = RoundingMode.HALF_UP;
    int escala = 2;
    
    Salida salida = new Salida(null, true);
    
    FilasResaltadas liquida;
    
    public OrdenPago(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.profesionalCRUD = new ProfesionalCRUD(conexion, id_empresa, empresa);
        
        this.arrayId_profesional = new ArrayList();
        
        this.liquida = new FilasResaltadas();
        this.liquida.setFondo(Color.GREEN);
        this.liquida.setFuente(Color.BLACK);
        this.liquida.setColumnaVerificadora(8);
        this.liquida.setVerificador("S");
        
        initComponents();
        
        modeloConsulta = (DefaultTableModel) this.jTablaConsulta.getModel();
        jTablaConsulta.setModel(modeloConsulta);
        sorterConsulta = new TableRowSorter(modeloConsulta);
        jTablaConsulta.setRowSorter (sorterConsulta);
        
        tableCellRenderer = new DateRenderer();
        jTablaConsulta.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        this.jTablaConsulta.setDefaultRenderer(Object.class, liquida);
        
        modeloDetalle = (DefaultTableModel) this.jTablaDetalle.getModel();
        jTablaDetalle.setModel(modeloDetalle);
        jTablaDetalle.setRowSorter (new TableRowSorter(modeloDetalle));
        sorterDetalle = new TableRowSorter(modeloDetalle);
        
        llenaComboZona();
        consultaProfesional();
        llenaComboProfesional(Principal.usuarioData.getId_zonaSistema());
        
        // No dejar seleccionar fechas anteriores a la actual
        jVence.setMinSelectableDate(new Date());
        limpia();
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
        this.jComboZona.addItem("-- TODAS --");
        zonaArray = zonaCRUD.consulta(true);
        Iterator i = zonaArray.iterator();
        while (i.hasNext()){
            ZonaData tmp = (ZonaData) i.next();
            this.jComboZona.addItem(tmp.getNombre());
        }
    }
    
    private void consultaProfesional(){
        profesionalArray = profesionalCRUD.consulta(true);
    }
    
    private void llenaComboProfesional(int id_zona){
        this.arrayId_profesional.clear();
        this.jComboProfesional.removeAllItems();
        this.jComboProfesional.addItem("-- TODOS --");
        
        Iterator i = profesionalArray.iterator();
        while (i.hasNext()){
            ProfesionalData item = (ProfesionalData) i.next();
            if (id_zona == 0 || item.getId_zona() == id_zona){
                this.jComboProfesional.addItem(item.getNombre());
                this.arrayId_profesional.add(item.getId_profesional());
            }
        }
        this.jComboProfesional.setSelectedIndex(0);
    }
    
    private void limpia(){
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedIndex(0);
        }
        
        this.jComboProfesional.setSelectedIndex(0);
        
        this.jLblProfesional.setText("");
        this.dni = "";
        this.jTxtBanco.setText("");
        this.jTxtCheque.setText("");
        this.jVence.setDate(null);
        this.jFmtImporte.setValue(0.00);
        modeloDetalle.getDataVector().removeAllElements();
        modeloDetalle.fireTableDataChanged();
        this.jTxtObservaciones.setText("");
        
        this.jLblOrden.setText("0,00");
        this.jLblPago.setText("0,00");
        this.jLblDiferencia.setText("0,00");     
        
        this.indiceDetalle = -1;
        
        zonaUsuario();
    }
    
    private void consulta(){
        try{
            modeloConsulta.getDataVector().removeAllElements();
            modeloConsulta.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            
            int id_zona = 0;
            if (this.jComboZona.getSelectedIndex() > 0){
                ZonaData z = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
                id_zona = z.getId_zona();
            }
            
            int id_p = 0;
            if (this.jComboProfesional.getSelectedIndex() > 0){
                id_p = (int) this.arrayId_profesional.get(this.jComboProfesional.getSelectedIndex() - 1);
            }
            
            parametros.add(id_zona);
            parametros.add(id_p);
            parametros.add(this.id_empresa);
            
            rsConsulta = conexion.procAlmacenado("consultaPresuLiquidacion", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsConsulta.beforeFirst();
            while (rsConsulta.next()){
                String profesional = rsConsulta.getString("profesional");
                Date fecha = rsConsulta.getDate("fecha");
                int id_presupuesto = rsConsulta.getInt("id_presupuesto");
                Double importe = rsConsulta.getDouble("importe");
                String paciente = rsConsulta.getString("paciente");
                String entidad = rsConsulta.getString("entidad");
                String formaPago = rsConsulta.getString("formaPago");
                String observaciones = rsConsulta.getString("observaciones");
                int id_mayorProfesional = rsConsulta.getInt("id_mayorProfesional");
                String dni = rsConsulta.getString("dni");
                
                Object[] fila = {profesional, fecha, id_presupuesto, importe, paciente, 
                                    entidad, formaPago, observaciones, "N", id_mayorProfesional,
                                    dni};
                modeloConsulta.addRow(fila);
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    private void actualizaTotalOrden(){
        BigDecimal totalOrden = BigDecimal.ZERO;
        for (int i = 0; i < modeloConsulta.getRowCount(); i++){
            if (modeloConsulta.getValueAt(i, 8).toString().equals("S")){
                BigDecimal importe = new BigDecimal(modeloConsulta.getValueAt(i, 3).toString());
                totalOrden = totalOrden.add(importe).setScale(escala, RM);
            }
        }
        
        if (totalOrden.compareTo(BigDecimal.ZERO) == 0) {
            this.jLblProfesional.setText("");
        }
        
        this.jLblOrden.setText(String.format("%.2f", totalOrden));
        actualizaValores();
    }
    
    private void actualizaTotalPago(){
        BigDecimal totalPago = BigDecimal.ZERO;
        for (int i = 0; i < modeloDetalle.getRowCount(); i++){
            BigDecimal importe = new BigDecimal(modeloDetalle.getValueAt(i, 3).toString());
            totalPago = totalPago.add(importe).setScale(escala, RM);
        }
        
        this.jLblPago.setText(String.format("%.2f", totalPago));
        actualizaValores();
    }
    
    private void actualizaValores(){
        BigDecimal totalOrden = new BigDecimal(Double.parseDouble(this.jLblOrden.getText().replace(".", "").replace(",", ".")));
        BigDecimal totalPago = new BigDecimal(Double.parseDouble(this.jLblPago.getText().replace(".", "").replace(",", ".")));
        
        BigDecimal diferencia = totalOrden.subtract(totalPago).setScale(escala, RM);
        this.jLblDiferencia.setText(String.format("%.2f", diferencia));
    }
    
    private boolean validaObligatorios(){
        return this.jComboProfesional.getSelectedIndex() >= 0 &&
                !this.jLblOrden.getText().equals("0,00") &&
                this.jLblDiferencia.getText().equals("0,00");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenuPagos = new javax.swing.JPopupMenu();
        jMenuModifica = new javax.swing.JMenuItem();
        jMenuElimina = new javax.swing.JMenuItem();
        jPopupMenuConsulta = new javax.swing.JPopupMenu();
        jDetalleTurno = new javax.swing.JMenuItem();
        jAmpliaObservaciones = new javax.swing.JMenuItem();
        jMenuEliminar = new javax.swing.JMenuItem();
        jDetallePresupuesto = new javax.swing.JMenuItem();
        jBtnSalir = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jBtnFiltra = new javax.swing.JButton();
        jComboProfesional = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablaDetalle = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTxtBanco = new javax.swing.JTextField();
        jTxtBanco.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jTxtCheque = new javax.swing.JTextField();
        jTxtCheque.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jFmtImporte = new ar.com.bosoft.formatosCampos.Decimal(true)
        ;
        jVence = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jVence.getJCalendar().setTodayButtonVisible(true); 
        this.jVence.getJCalendar().setTodayButtonText("Hoy"); 
        this.jVence.getJCalendar().setWeekOfYearVisible(false);
        jBtnCarga = new javax.swing.JButton();
        jLblProfesional = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLblOrden = new javax.swing.JLabel();
        jLblPago = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLblDiferencia = new javax.swing.JLabel();
        jBtnImprime = new javax.swing.JButton();

        jMenuModifica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/16x16/enabled/editar_popup.png"))); // NOI18N
        jMenuModifica.setLabel("Modificar");
        jMenuModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuModificaActionPerformed(evt);
            }
        });
        jPopupMenuPagos.add(jMenuModifica);

        jMenuElimina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/16x16/enabled/eliminar_popup.png"))); // NOI18N
        jMenuElimina.setLabel("Eliminar");
        jMenuElimina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuEliminaActionPerformed(evt);
            }
        });
        jPopupMenuPagos.add(jMenuElimina);

        jDetalleTurno.setText("Turno");
        jDetalleTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetalleTurnoActionPerformed(evt);
            }
        });
        jPopupMenuConsulta.add(jDetalleTurno);

        jAmpliaObservaciones.setText("Ver observaciones");
        jAmpliaObservaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAmpliaObservacionesActionPerformed(evt);
            }
        });
        jPopupMenuConsulta.add(jAmpliaObservaciones);

        jMenuEliminar.setText("Eliminar");
        jMenuEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuEliminarActionPerformed(evt);
            }
        });
        jPopupMenuConsulta.add(jMenuEliminar);

        jDetallePresupuesto.setText("Ver presupuesto");
        jDetallePresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetallePresupuestoActionPerformed(evt);
            }
        });
        jPopupMenuConsulta.add(jDetallePresupuesto);

        setTitle("Orden de pago");
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

        jTablaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Profesional", "Fecha", "Turno", "Importe", "Paciente", "Entidad", "Forma de pago", "Observaciones", "liquida", "id_mayorprofesional", "dni"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
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
        jTablaConsulta.setComponentPopupMenu(jPopupMenuConsulta);
        jTablaConsulta.getTableHeader().setReorderingAllowed(false);
        jTablaConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaConsultaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaConsulta);
        if (jTablaConsulta.getColumnModel().getColumnCount() > 0) {
            jTablaConsulta.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(1).setMaxWidth(120);
            jTablaConsulta.getColumnModel().getColumn(2).setPreferredWidth(50);
            jTablaConsulta.getColumnModel().getColumn(2).setMaxWidth(50);
            jTablaConsulta.getColumnModel().getColumn(3).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(3).setMaxWidth(75);
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

        jComboProfesional.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProfesionalMouseClicked(evt);
            }
        });
        jComboProfesional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboProfesionalActionPerformed(evt);
            }
        });

        jLabel20.setText("Profesional");

        jLabel21.setText("Zona");

        jComboZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboZonaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboZona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnFiltra)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnFiltra)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))))
                .addGap(1, 1, 1))
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Detalle del pago"));

        jScrollPane3.setBackground(new java.awt.Color(153, 204, 255));
        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Observaciones"));

        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jScrollPane3.setViewportView(jTxtObservaciones);

        jTablaDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Banco", "N° cheque", "vencimiento", "Importe"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaDetalle.setComponentPopupMenu(jPopupMenuPagos);
        jScrollPane2.setViewportView(jTablaDetalle);
        if (jTablaDetalle.getColumnModel().getColumnCount() > 0) {
            jTablaDetalle.getColumnModel().getColumn(1).setMinWidth(120);
            jTablaDetalle.getColumnModel().getColumn(1).setPreferredWidth(120);
            jTablaDetalle.getColumnModel().getColumn(1).setMaxWidth(120);
            jTablaDetalle.getColumnModel().getColumn(2).setMinWidth(75);
            jTablaDetalle.getColumnModel().getColumn(2).setPreferredWidth(75);
            jTablaDetalle.getColumnModel().getColumn(2).setMaxWidth(75);
            jTablaDetalle.getColumnModel().getColumn(3).setMinWidth(75);
            jTablaDetalle.getColumnModel().getColumn(3).setPreferredWidth(75);
            jTablaDetalle.getColumnModel().getColumn(3).setMaxWidth(75);
        }

        jLabel1.setText("Banco");

        jLabel3.setText("N° cheque");

        jLabel4.setText("vencimiento");

        jLabel5.setText("Importe");

        jBtnCarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/cargar.png"))); // NOI18N
        jBtnCarga.setText("Cargar");
        jBtnCarga.setBorderPainted(false);
        jBtnCarga.setContentAreaFilled(false);
        jBtnCarga.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnCarga.setFocusPainted(false);
        jBtnCarga.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/cargar.png"))); // NOI18N
        jBtnCarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCargaActionPerformed(evt);
            }
        });

        jLblProfesional.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLblProfesional.setText("...");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtBanco)
                            .addComponent(jTxtCheque)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jVence, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jFmtImporte))
                                .addGap(33, 33, 33)
                                .addComponent(jBtnCarga)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                    .addComponent(jLblProfesional, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTxtBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTxtCheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLblProfesional, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jVence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jFmtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jBtnCarga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(1, 1, 1))
        );

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Total orden");

        jLblOrden.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblOrden.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblOrden.setText("0,00");

        jLblPago.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblPago.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblPago.setText("0,00");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Total pago");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Diferencia");

        jLblDiferencia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblDiferencia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblDiferencia.setText("0,00");

        jBtnImprime.setText("Imprimir");
        jBtnImprime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnImprimeActionPerformed(evt);
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
                        .addComponent(jBtnGuardar)
                        .addGap(352, 352, 352)
                        .addComponent(jBtnSalir)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(207, 207, 207)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                                    .addComponent(jBtnImprime)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(175, 175, 175)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLblPago, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLblOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLblDiferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 6, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jBtnImprime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLblOrden))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLblPago))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLblDiferencia))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnSalir))
                .addGap(1, 1, 1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        try {
            if (this.rsConsulta != null){
                this.rsConsulta.close();
            }
            if (this.rsLiquidacion != null){
                this.rsLiquidacion.close();
            }
            this.dispose();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios()){
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Confirma la liquidación?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                try{
                    ArrayList parametros = new ArrayList();
                    
                    Long fecha = new Date().getTime();
                    String observaciones = this.jTxtObservaciones.getText().trim();
                    
                    parametros.add(fecha);
                    parametros.add(observaciones);
                    parametros.add(this.id_empresa);
                    parametros.add(this.usuario);
                    
                    conexion.procAlmacenado("insertLiquidacion", parametros, 
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    
                    parametros.clear();
                    parametros.add(this.id_empresa);
                    
                    rsLiquidacion = conexion.funcAlmacenada("proximaLiquidacion", parametros, 
                                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    
                    rsLiquidacion.next();
                    int id_liquidacion = rsLiquidacion.getInt(1);
                    
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    for (int i = 0; i < modeloDetalle.getRowCount(); i++){
                        parametros.clear();
                        
                        String banco = modeloDetalle.getValueAt(i, 0).toString();
                        String cheque = modeloDetalle.getValueAt(i, 1).toString();
                        
                        Long vencimiento = (long) 0;
                        if (!modeloDetalle.getValueAt(i, 2).toString().isEmpty()){
                            Date v = formatter.parse(modeloDetalle.getValueAt(i, 2).toString());
                            vencimiento = v.getTime();
                        }
                        
                        Double importe = Double.parseDouble(modeloDetalle.getValueAt(i, 3).toString());
                        
                        parametros.add(id_liquidacion);
                        parametros.add(banco);
                        parametros.add(cheque);
                        parametros.add(vencimiento);
                        parametros.add(importe);
                        parametros.add(this.id_empresa);
                        parametros.add(this.usuario);
                        
                        conexion.procAlmacenado("insertLiquidacionDetalle", parametros, 
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    }
                    
                    ArrayList turnos = new ArrayList();
                    for (int i = 0; i < modeloConsulta.getRowCount(); i++){
                        if (modeloConsulta.getValueAt(i, 8).toString().equals("S")){
                            parametros.clear();
                            
                            int id_mayorProfesional = Integer.parseInt(modeloConsulta.getValueAt(i, 9).toString());
                            
                            parametros.add(id_mayorProfesional);
                            parametros.add(id_liquidacion);
                            parametros.add(this.id_empresa);
                            parametros.add(this.usuario);
                            
                            conexion.procAlmacenado("liquidaMayorProfesional", parametros, 
                                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                            
                            int id_presupuesto = Integer.parseInt(modeloConsulta.getValueAt(i, 2).toString());
                            String paciente = modeloConsulta.getValueAt(i, 4).toString();
                            Double importe = Double.parseDouble(modeloConsulta.getValueAt(i, 3).toString());
                            
                            Object[] nuevo = {id_presupuesto, paciente, importe};
                            turnos.add(nuevo);
                        }
                    }

                    respuesta = JOptionPane.showConfirmDialog(this, "¿Desea imprimir la Orden de pago?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION){
                        this.salida.setVisible(true);
                        if (this.salida.getSiNo()){
                            this.impresora = salida.getImpresora();
                            this.copias = salida.getCopias();
                            this.rutaArchivo = salida.getRutaArchivo();
                            int tipoSalida = salida.getTipoSalida();
                            
                            Map param=new HashMap();
                            param.put("empresa", this.empresa);
                            param.put("id_liquidacion", id_liquidacion);
                            param.put("profesional", this.jLblProfesional.getText());
                            param.put("fecha", new Date());
                            param.put("dni", this.dni);

                            LiquidacionProfesionalDataSource data = new LiquidacionProfesionalDataSource();

                            int indice = 0;
                            if (turnos.size() > modeloDetalle.getRowCount()){
                                Iterator i = turnos.iterator();
                                while (i.hasNext()){
                                    Object[] turno = (Object[]) i.next();
                                    int id_presupuesto = (int) turno[0];
                                    String paciente = id_presupuesto == 0 ? "ANTICIPO" : turno[1].toString();
                                    Double importe  = (double) turno[2];

                                    String banco = "";
                                    String cheque = "";
                                    Date vencimiento = null;
                                    Double valor = 0.00;
                                    if (indice < modeloDetalle.getRowCount()){
                                        banco = modeloDetalle.getValueAt(indice, 0).toString();
                                        cheque = modeloDetalle.getValueAt(indice, 1).toString();

                                        if (!modeloDetalle.getValueAt(indice, 2).toString().isEmpty()){
                                            try{
                                                vencimiento = formatter.parse(modeloDetalle.getValueAt(indice, 2).toString());
                                            }catch(Exception ex){
                                                //No se ha cargado una fecha válida
                                            }
                                        }

                                        valor = Double.parseDouble(modeloDetalle.getValueAt(indice, 3).toString());
                                    }

                                    LiquidacionProfesional nuevo = new LiquidacionProfesional(id_presupuesto, paciente, banco, cheque, importe, valor, vencimiento);

                                    data.add(nuevo);
                                    indice++;
                                }
                            }else{
                                for (int i = 0; i < modeloDetalle.getRowCount(); i++){
                                    int id_presupuesto = 0;
                                    String paciente = "";
                                    Double importe = 0.00;
                                    String banco = modeloDetalle.getValueAt(i, 0).toString();
                                    String cheque = modeloDetalle.getValueAt(i, 1).toString();

                                    Date vencimiento = null;
                                    if (!modeloDetalle.getValueAt(i, 2).toString().isEmpty()){
                                        vencimiento = formatter.parse(modeloDetalle.getValueAt(i, 2).toString());
                                    }

                                    Double valor = Double.parseDouble(modeloDetalle.getValueAt(i, 3).toString());

                                    if (i < turnos.size()){
                                        Object[] turno = (Object[]) turnos.get(i);
                                        id_presupuesto = (int) turno[0];
                                        paciente = turno[1].toString();
                                        importe  = (double) turno[2];
                                    }
                                    LiquidacionProfesional nuevo = new LiquidacionProfesional(id_presupuesto, paciente, banco, cheque, importe, valor, vencimiento);

                                    data.add(nuevo);
                                }
                            }

                            Reporte reporte = new Reporte();
                            reporte.startReport("LiquidacionProfesional",param, data, tipoSalida, rutaArchivo, impresora, copias);
                        }   
                    }

                    limpia();
                } catch (ParseException | NumberFormatException | HeadlessException | SQLException ex) {
                    Utiles.enviaError(this.empresa,ex);
                } 
            }     
        }else{
            JOptionPane.showMessageDialog(this, "Complete todos los datos correctamente", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        consulta();
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jComboProfesionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboProfesionalActionPerformed
        modeloConsulta.getDataVector().removeAllElements();
        modeloConsulta.fireTableDataChanged();
        
        modeloDetalle.getDataVector().removeAllElements();
        modeloDetalle.fireTableDataChanged();
        this.jTxtObservaciones.setText("");
        
        this.jLblOrden.setText("0,00");
        this.jLblPago.setText("0,00");
        this.jLblDiferencia.setText("0,00");        
    }//GEN-LAST:event_jComboProfesionalActionPerformed

    private void jMenuModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuModificaActionPerformed
        this.indiceDetalle = jTablaDetalle.convertRowIndexToModel(jTablaDetalle.getSelectedRow());
        if (this.indiceDetalle >= 0){
            try {
                this.jTxtBanco.setText(modeloDetalle.getValueAt(this.indiceDetalle, 0).toString().equals("Efectivo") ? "" : modeloDetalle.getValueAt(this.indiceDetalle, 0).toString());
                this.jTxtCheque.setText(modeloDetalle.getValueAt(this.indiceDetalle, 0).toString().equals("Efectivo") ? "" : modeloDetalle.getValueAt(this.indiceDetalle, 1).toString());
                
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                this.jVence.setDate(modeloDetalle.getValueAt(this.indiceDetalle, 0).toString().equals("Efectivo") ? null : formatter.parse(modeloDetalle.getValueAt(this.indiceDetalle, 2).toString()));
                
                this.jFmtImporte.setValue(modeloDetalle.getValueAt(this.indiceDetalle, 3));
                
                this.jTxtBanco.requestFocus();
            } catch (ParseException ex) {
                Utiles.enviaError(this.empresa,ex);
            }    
        }else{
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún registro", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jMenuModificaActionPerformed

    private void jMenuEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuEliminaActionPerformed
        this.indiceDetalle = jTablaDetalle.convertRowIndexToModel(jTablaDetalle.getSelectedRow());
        if (this.indiceDetalle >= 0){
            modeloDetalle.removeRow(this.indiceDetalle);
            actualizaTotalPago();
            this.indiceDetalle = -1;
            this.jTxtBanco.requestFocus();
        }else{
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún registro", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jMenuEliminaActionPerformed

    private void jBtnCargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCargaActionPerformed
        if (Double.parseDouble(this.jFmtImporte.getValue().toString()) > 0.00){
            
            String banco = this.jTxtBanco.getText().isEmpty() ? "Efectivo" : this.jTxtBanco.getText();
            String cheque = this.jTxtBanco.getText().isEmpty() ? "" : this.jTxtCheque.getText();
            
            if (!cheque.isEmpty()) {
                if (this.jVence.getDate() == null) {
                    JOptionPane.showMessageDialog(this, "Complete el vencimiento del cheque", "Atención", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String vence = this.jTxtBanco.getText().isEmpty() ? "" : formatter.format(this.jVence.getDate());
            
            Double importe = Double.parseDouble(this.jFmtImporte.getValue().toString());
            
            Object[] nuevo = {banco, cheque, vence, importe};
            
            if (this.indiceDetalle >= 0){
                modeloDetalle.removeRow(this.indiceDetalle);
            }
            
            modeloDetalle.addRow(nuevo);
            actualizaTotalPago();
            
            this.jTxtBanco.setText("");
            this.jTxtCheque.setText("");
            this.jVence.setDate(null);
            this.jFmtImporte.setValue(0.00);
            this.indiceDetalle = -1;
            this.jTxtBanco.requestFocus();
        }else{
            JOptionPane.showMessageDialog(this, "Complete los datos del pago", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnCargaActionPerformed

    private void jTablaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsultaMouseClicked
        if (evt.getClickCount() == 2) {
            int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
            if (this.jLblProfesional.getText().isEmpty()) {
                this.jLblProfesional.setText(modeloConsulta.getValueAt(fila, 0).toString());
                this.dni = modeloConsulta.getValueAt(fila, 10).toString();
            } else {
                String prof = modeloConsulta.getValueAt(fila, 0).toString();
                if (!prof.equals(this.jLblProfesional.getText())) {
                    JOptionPane.showMessageDialog(this, "Este turno no pertenece a " + this.jLblProfesional.getText(), "Atención", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            modeloConsulta.setValueAt(modeloConsulta.getValueAt(fila, 8).toString().equals("S") ? "N" : "S", fila, 8);
            this.jTablaConsulta.clearSelection();
            actualizaTotalOrden();
        }            
    }//GEN-LAST:event_jTablaConsultaMouseClicked

    private void jDetalleTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetalleTurnoActionPerformed
        int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
        if (fila >= 0){
            Turno turno = new Turno(null, true, conexion, id_empresa, empresa);
            turno.setId_presupuesto(modeloConsulta.getValueAt(fila, 2).toString());
            turno.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetalleTurnoActionPerformed

    private void jAmpliaObservacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAmpliaObservacionesActionPerformed
        int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
        if (fila >= 0){
            String turno = modeloConsulta.getValueAt(fila, 2).toString();
            String[] palabras = modeloConsulta.getValueAt(fila, 7).toString().trim().split(" ");
            String mensaje = "<html>";
            int contadorPalabras = 0;
            int maximoPalabras = 15;

            for (String palabra : palabras) {
                mensaje += " " + palabra;
                contadorPalabras++;
                if (contadorPalabras == maximoPalabras){
                    contadorPalabras = 0;
                    mensaje += "</br>";
                }
            }

            mensaje += "</html>";

            JOptionPane.showMessageDialog(this, mensaje, "Observaciones del turno " + turno, JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jAmpliaObservacionesActionPerformed

    private void jMenuEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuEliminarActionPerformed
        int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
        if (fila >= 0){
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Realmente desea eliminar este registro?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                int id_mayorProfesional = (int) modeloConsulta.getValueAt(fila, 9);
                ArrayList parametros = new ArrayList();
                parametros.add(id_mayorProfesional);
                conexion.procAlmacenado("deleteMayorProfesional", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                
                limpia();
                llenaComboProfesional(0);   
            }
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el registro a eliminar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jMenuEliminarActionPerformed

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        try{
            int id_zona = 0;
            if (this.jComboZona.getSelectedIndex() > 0){
                ZonaData z = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
                id_zona = z.getId_zona();
            }
            llenaComboProfesional(id_zona);
        }catch (Exception ex){
            
        }
    }//GEN-LAST:event_jComboZonaActionPerformed

    private void jDetallePresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetallePresupuestoActionPerformed
        int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
        if (fila >= 0) {
            Presupuesto presupuesto = new Presupuesto(conexion, id_empresa, empresa, "");
            presupuesto.setId_presupuesto(modeloConsulta.getValueAt(fila, 2).toString());
            Principal.dp.add(presupuesto);
            presupuesto.toFront();
            presupuesto.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetallePresupuestoActionPerformed

    private void jBtnImprimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImprimeActionPerformed
        if (modeloConsulta.getRowCount() > 0) {
            Map param = new HashMap();
            param.put("empresa", this.empresa);
            param.put("zona", this.jComboZona.getSelectedItem().toString());
            param.put("profesional", this.jComboProfesional.getSelectedItem().toString());
            
            Reporte reporte = new Reporte();
            reporte.startReport("ListaPendientePago", param, new JRTableModelDataSource(modeloConsulta), 0, "", "", 1);
        }else{
            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Atencion", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnImprimeActionPerformed

    private void jComboProfesionalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProfesionalMouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Profesional");
            Iterator it = this.profesionalArray.iterator();
            while (it.hasNext()) {
                ProfesionalData tmp = (ProfesionalData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboProfesional.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboProfesionalMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jAmpliaObservaciones;
    private javax.swing.JButton jBtnCarga;
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnImprime;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JComboBox jComboProfesional;
    private javax.swing.JComboBox jComboZona;
    private javax.swing.JMenuItem jDetallePresupuesto;
    private javax.swing.JMenuItem jDetalleTurno;
    private javax.swing.JFormattedTextField jFmtImporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLblDiferencia;
    private javax.swing.JLabel jLblOrden;
    private javax.swing.JLabel jLblPago;
    private javax.swing.JLabel jLblProfesional;
    private javax.swing.JMenuItem jMenuElimina;
    private javax.swing.JMenuItem jMenuEliminar;
    private javax.swing.JMenuItem jMenuModifica;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenuConsulta;
    private javax.swing.JPopupMenu jPopupMenuPagos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTablaConsulta;
    private javax.swing.JTable jTablaDetalle;
    private javax.swing.JTextField jTxtBanco;
    private javax.swing.JTextField jTxtCheque;
    private javax.swing.JTextArea jTxtObservaciones;
    private com.toedter.calendar.JDateChooser jVence;
    // End of variables declaration//GEN-END:variables
}
