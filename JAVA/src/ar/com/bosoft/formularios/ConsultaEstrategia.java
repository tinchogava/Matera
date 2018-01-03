/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.EstrategiaVentaDataSource;
import ar.com.bosoft.Modelos.EstrategiaVenta;
import ar.com.bosoft.RenderTablas.DateRenderer;
import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ProfesionalCRUD;
import ar.com.bosoft.crud.VendedorCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.ProfesionalData;
import ar.com.bosoft.data.VendedorData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
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
public class ConsultaEstrategia extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, id_estrategia, copias;
    String empresa, usuario, todos, nombreArchivo, rutaArchivo, historico;
    
    ZonaCRUD zonaCRUD;
    ProfesionalCRUD profesionalCRUD;
    VendedorCRUD vendedorCRUD;
    
    ArrayList zonaArray, profesionalArray, id_profesionalArray, vendedorArray, id_vendedorArray;
    
    ResultSet rsConsulta;
    DefaultTableModel modelo;
    TableRowSorter sorter;
    TableCellRenderer tableCellRenderer;
    
    SeleccionImp seleccionImp;
    
    Reporte reporte = new Reporte();
    
    public ConsultaEstrategia(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        this.todos = "-- TODOS --";
        
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.profesionalCRUD = new ProfesionalCRUD(conexion, id_empresa, empresa);
        this.vendedorCRUD = new VendedorCRUD(conexion, id_empresa, empresa);
        
        this.zonaArray = new ArrayList();
        this.profesionalArray = new ArrayList();
        this.id_profesionalArray = new ArrayList();
        this.vendedorArray = new ArrayList();
        this.id_vendedorArray = new ArrayList();
        
        this.seleccionImp = new SeleccionImp(null, true);
         
        initComponents();
        
        modelo = (DefaultTableModel) this.jTablaConsulta.getModel();
        jTablaConsulta.setModel(modelo);
        jTablaConsulta.setRowSorter (new TableRowSorter(modelo));
        sorter = new TableRowSorter(modelo);
        tableCellRenderer = new DateRenderer();
        jTablaConsulta.getColumnModel().getColumn(0).setCellRenderer(tableCellRenderer);
        jTablaConsulta.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        
        llenaComboZona();
        consultaVendedor();
        consultaProfesional();
        
        llenaComboProfesional1();
                
        limpia();
        zonaUsuario();
        setJTexFieldChanged(this.jTxtBusca);
        this.jComboZonaActionPerformed(null);
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
        this.jComboZona.addItem(this.todos);
        zonaArray = zonaCRUD.consulta(true);
        Iterator i = zonaArray.iterator();
        while (i.hasNext()){
            ZonaData tmp = (ZonaData) i.next();
            this.jComboZona.addItem(tmp.getNombre());
        }
    }
    
    private void consultaVendedor(){
        this.vendedorArray = vendedorCRUD.consulta(true);
    }
    
    private void llenaComboVendedor(int id_zona){
        this.jComboVendedor.removeAllItems();
        this.id_vendedorArray.clear();
        this.jComboVendedor.addItem(todos);
        
        Iterator i = vendedorArray.iterator();
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
            
            if (incluye) {
                this.id_vendedorArray.add(tmp.getId_vendedor());
                this.jComboVendedor.addItem(tmp.getNombre());
            }
        }
        this.jComboVendedor.setSelectedIndex(0);
    }
    
    private void consultaProfesional(){
        this.profesionalArray = profesionalCRUD.consulta(true);
    }
    
    private void llenaComboProfesional(int id_zona){
        this.jComboProfesional.removeAllItems();
        this.id_profesionalArray.clear();
        this.jComboProfesional.addItem(todos);
        
        Iterator i = profesionalArray.iterator();
        while (i.hasNext()) {
            ProfesionalData tmp = (ProfesionalData) i.next();
            boolean incluye = false;
            if (id_zona == 0){
                incluye = true;
            }else{
                if (tmp.getId_zona() == id_zona){
                    incluye = true;
                }
            }
            
            if (incluye) {
                this.id_profesionalArray.add(tmp.getId_profesional());
                this.jComboProfesional.addItem(tmp.getNombre());
            }
        }
        this.jComboProfesional.setSelectedIndex(0);
    }
    
    private void llenaComboProfesional1(){
        Iterator i = profesionalArray.iterator();
        while (i.hasNext()) {
            ProfesionalData tmp = (ProfesionalData) i.next();
            this.jComboProfesional1.addItem(tmp.getNombre());
        }
    }
    
    private void limpia(){
        this.jDesde.setDate(null);
        this.jHasta.setDate(null);
        
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedItem(this.todos);
        }
        
        this.jComboProfesional.setSelectedItem(this.todos);
        this.jComboVendedor.setSelectedItem(this.todos);
        this.jComboHistoricos.setSelectedItem("NO");
        
        this.jTxtBusca.setText("");
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
            
        this.jComboProfesional1.setSelectedIndex(-1);
        this.jInicio.setDate(null);
        this.jAgenda.setDate(null);
        this.jTxtPerfil.setText("");
        this.jTxtEstrategia.setText("");
        this.jTxtRespuesta.setText("");
        
        this.id_estrategia = 0;
        this.historico = "N";
    }
    private void consulta(){
        try {
            modelo.getDataVector().removeAllElements();
            modelo.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            parametros.add(this.jDesde.getDate() == null ? (long) 0 : this.jDesde.getDate().getTime());
            parametros.add(this.jHasta.getDate() == null ? (long) 0 : this.jHasta.getDate().getTime());
            
            if (this.jComboProfesional.getSelectedIndex() == 0){
                parametros.add(0);
            }else{
                int id_profesional = (int) id_profesionalArray.get(this.jComboProfesional.getSelectedIndex() - 1);
                parametros.add(id_profesional);
            }
            
            if (this.jComboVendedor.getSelectedIndex() == 0){
                parametros.add(0);
            }else{
                int id_vendedor = (int) id_vendedorArray.get(this.jComboVendedor.getSelectedIndex() - 1);
                parametros.add(id_vendedor);
            }
            
            if (this.jComboZona.getSelectedIndex() == 0){
                parametros.add(0);
            }else{
                ZonaData tmp = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
                parametros.add(tmp.getId_zona());
            }
            
            
            parametros.add(this.id_empresa); 
            
            switch (this.jComboHistoricos.getSelectedIndex()){
                case 0:
                    parametros.add('X');
                    break;
                    
                case 1:
                    parametros.add('S');
                    break;
                    
                case 2:
                    parametros.add('N');
                    break;                
            }
            
            rsConsulta = conexion.procAlmacenado("consultaEstrategia", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            if (rsConsulta.first()){
                rsConsulta.beforeFirst();
                while (rsConsulta.next()){
                    Date fechaAgenda = rsConsulta.getDate("fechaAgenda");
                    Date fechaInicio = rsConsulta.getDate("fechaInicio");
                    String profesional = rsConsulta.getString("profesional");
                    String estrategia = rsConsulta.getString("estrategia");
                    String perfil = rsConsulta.getString("perfil");
                    String respuesta = rsConsulta.getString("respuesta");
                    int id = rsConsulta.getInt("id_estrategia");
                    String h = rsConsulta.getString("historico");
                    
                    Object[] fila = {fechaAgenda, fechaInicio, profesional, estrategia, perfil, respuesta, id, h};
                    modelo.addRow(fila);
                }
            }else{
                JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Atención", JOptionPane.INFORMATION_MESSAGE);
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
          jTablaConsulta.setRowSorter(sorter);
        }
    }
    
    private void imprime(int salida){
        if (modelo.getRowCount() > 0){
            Map param=new HashMap();
            param.put("empresa", this.empresa);
            param.put("desde", this.jDesde.getDate());
            param.put("hasta", this.jHasta.getDate());
            param.put("zona", this.jComboZona.getSelectedItem().toString());
            param.put("profesional", this.jComboProfesional.getSelectedItem().toString());
            param.put("vendedor", this.jComboVendedor.getSelectedItem().toString());
            param.put("historicos", this.jComboHistoricos.getSelectedItem().toString());
            EstrategiaVentaDataSource data = new EstrategiaVentaDataSource();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < modelo.getRowCount(); i++){
                String profesional = modelo.getValueAt(i, 2).toString();
                Date fechaAgenda = null;
                try {
                    fechaAgenda = formatter.parse(modelo.getValueAt(i, 0).toString());
                } catch (Exception e) {
                    //Fecha inválida
                }
                Date fechaInicio = null;
                try {
                    fechaInicio = formatter.parse(modelo.getValueAt(i, 1).toString());
                } catch (Exception e) {
                    //Fecha inválida
                }
                String estrategia = modelo.getValueAt(i, 3).toString();
                
                ar.com.bosoft.Modelos.EstrategiaVenta nuevo = new EstrategiaVenta(profesional, estrategia, fechaInicio, fechaAgenda);
                
                data.add(nuevo);
            }
            reporte.startReport("EstrategiaVenta", param, data, salida, nombreArchivo, empresa, copias);
        }else{
            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private boolean validaObligatorios(){
        return this.jComboProfesional1.getSelectedIndex() >= 0 &&
                this.jInicio.getDate() != null &&
                this.jAgenda.getDate() != null &&
                !this.jTxtEstrategia.getText().trim().isEmpty();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
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
        jLabel8 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jComboProfesional = new javax.swing.JComboBox();
        jComboVendedor = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jComboHistoricos = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jComboProfesional1 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jInicio = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jInicio.getJCalendar().setTodayButtonVisible(true); 
        this.jInicio.getJCalendar().setTodayButtonText("Hoy"); 
        this.jInicio.getJCalendar().setWeekOfYearVisible(false);
        jLabel14 = new javax.swing.JLabel();
        jAgenda = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jAgenda.getJCalendar().setTodayButtonVisible(true); 
        this.jAgenda.getJCalendar().setTodayButtonText("Hoy"); 
        this.jAgenda.getJCalendar().setWeekOfYearVisible(false);
        jScrollPane5 = new javax.swing.JScrollPane();
        jTxtPerfil = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTxtEstrategia = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTxtRespuesta = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jBtnGuardar = new javax.swing.JButton();
        jBtnLimpiar = new javax.swing.JButton();
        jBtnHistorico = new javax.swing.JButton();

        setTitle("Estrategias de venta");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jTablaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Agenda", "Fecha Inicio", "Profesional", "Estrategia", "perfil", "respuesta", "id_estrategia", "historico"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaConsultaMouseClicked(evt);
            }
        });
        jTablaConsulta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTablaConsultaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaConsulta);
        if (jTablaConsulta.getColumnModel().getColumnCount() > 0) {
            jTablaConsulta.getColumnModel().getColumn(0).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(0).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(1).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(1).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(4).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(4).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(5).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(5).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(6).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(6).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(6).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(7).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(7).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setFocusable(false);

        jTxtBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaFocusGained(evt);
            }
        });

        jLabel6.setText("Buscar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 702, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)))
        );

        jPanel6.setBackground(new java.awt.Color(153, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Salida"));

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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jBtnScr, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnImp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBtnScr)
            .addComponent(jBtnImp)
            .addComponent(jBtnPdf)
        );

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

        jLabel8.setText("Zona");

        jComboZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboZonaActionPerformed(evt);
            }
        });

        jLabel9.setText("Profesional");

        jComboProfesional.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProfesionalMouseClicked(evt);
            }
        });

        jLabel10.setText("Agente");

        jLabel12.setText("Históricos");

        jComboHistoricos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- TODOS --", "SI", "NO" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboZona, 0, 250, Short.MAX_VALUE)
                    .addComponent(jComboProfesional, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboHistoricos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jBtnFiltra)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jComboVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jComboHistoricos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnFiltra)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel13.setText("* Profesional");

        jComboProfesional1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProfesional1MouseClicked(evt);
            }
        });
        jComboProfesional1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboProfesional1ActionPerformed(evt);
            }
        });

        jLabel5.setText("* Fecha Inicio");

        jLabel14.setText("* Fecha Agenda");

        jTxtPerfil.setEditable(false);
        jTxtPerfil.setColumns(20);
        jTxtPerfil.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtPerfil.setRows(5);
        jTxtPerfil.setBorder(javax.swing.BorderFactory.createTitledBorder("Perfil"));
        jScrollPane5.setViewportView(jTxtPerfil);

        jTxtEstrategia.setColumns(20);
        jTxtEstrategia.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtEstrategia.setRows(5);
        jTxtEstrategia.setBorder(javax.swing.BorderFactory.createTitledBorder("* Estrategia"));
        jScrollPane6.setViewportView(jTxtEstrategia);

        jTxtRespuesta.setColumns(20);
        jTxtRespuesta.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtRespuesta.setRows(5);
        jTxtRespuesta.setBorder(javax.swing.BorderFactory.createTitledBorder("Respuesta"));
        jScrollPane7.setViewportView(jTxtRespuesta);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboProfesional1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(jAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jComboProfesional1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel7.setText("(*)Datos Obligatorios");

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

        jBtnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/limpiar.png"))); // NOI18N
        jBtnLimpiar.setText("Limpiar");
        jBtnLimpiar.setBorderPainted(false);
        jBtnLimpiar.setContentAreaFilled(false);
        jBtnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnLimpiar.setFocusPainted(false);
        jBtnLimpiar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/limpiar.png"))); // NOI18N
        jBtnLimpiar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/limpiar.png"))); // NOI18N
        jBtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLimpiarActionPerformed(evt);
            }
        });

        jBtnHistorico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/reloj.png"))); // NOI18N
        jBtnHistorico.setText("A histórico");
        jBtnHistorico.setBorderPainted(false);
        jBtnHistorico.setContentAreaFilled(false);
        jBtnHistorico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnHistorico.setFocusPainted(false);
        jBtnHistorico.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/reloj.png"))); // NOI18N
        jBtnHistorico.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/reloj.png"))); // NOI18N
        jBtnHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnHistoricoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 762, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(125, 125, 125)
                        .addComponent(jBtnGuardar)
                        .addGap(27, 27, 27)
                        .addComponent(jBtnHistorico)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnLimpiar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(526, 526, 526)
                        .addComponent(jBtnSalir)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnLimpiar)
                    .addComponent(jBtnHistorico)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnSalir, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTxtBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaFocusGained
        this.jTxtBusca.select(0, this.jTxtBusca.getText().length());
    }//GEN-LAST:event_jTxtBuscaFocusGained

    private void jBtnScrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScrActionPerformed
        imprime(0);
    }//GEN-LAST:event_jBtnScrActionPerformed

    private void jBtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImpActionPerformed
        seleccionImp.setVisible(true);
        if (seleccionImp.getSino()){
            imprime(1);
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
                this.rutaArchivo = archivoElegido.getCanonicalPath() + ".pdf";
                imprime(2);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jBtnPdfActionPerformed

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

    private void jTablaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsultaMouseClicked
        int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaAgenda = null;
        try {
            fechaAgenda = formatter.parse(modelo.getValueAt(fila, 0).toString());
        } catch (Exception e) {
            //Fecha inválida
        }
        Date fechaInicio = null;
        try {
            fechaInicio = formatter.parse(modelo.getValueAt(fila, 1).toString());
        } catch (Exception e) {
            //Fecha inválida
        }
        String profesional = modelo.getValueAt(fila, 2).toString();
        String estrategia = modelo.getValueAt(fila, 3).toString();
        String perfil = modelo.getValueAt(fila, 4).toString();
        String respuesta = modelo.getValueAt(fila, 5).toString();
        this.id_estrategia = (int) modelo.getValueAt(fila, 6);
        this.historico = modelo.getValueAt(fila, 7).toString();
        this.jAgenda.setDate(fechaAgenda);
        this.jInicio.setDate(fechaInicio);
        this.jComboProfesional1.setSelectedIndex(-1);
        this.jComboProfesional1.setSelectedItem(profesional);
        this.jTxtPerfil.setText(perfil);
        this.jTxtEstrategia.setText(estrategia);
        this.jTxtRespuesta.setText(respuesta);
    }//GEN-LAST:event_jTablaConsultaMouseClicked

    private void jTablaConsultaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTablaConsultaKeyReleased
        this.jTablaConsultaMouseClicked(null);
    }//GEN-LAST:event_jTablaConsultaKeyReleased

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        consulta();
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        int id_zona = 0;
        
        if (this.jComboZona.getSelectedIndex() > 0) {
            ZonaData tmp = (ZonaData) this.zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
            id_zona = tmp.getId_zona();  
        }
        
        try{
            this.llenaComboVendedor(id_zona);
            this.llenaComboProfesional(id_zona);                
        } catch (Exception ex){}
    }//GEN-LAST:event_jComboZonaActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios()){
            ProfesionalData tmp = (ProfesionalData) profesionalArray.get(this.jComboProfesional1.getSelectedIndex());
            int id_profesional = tmp.getId_profesional();
            
            long fechaInicio = this.jInicio.getDate().getTime();
            long fechaAgenda = this.jAgenda.getDate().getTime();
            String perfil = this.jTxtPerfil.getText().trim();
            String estrategia = this.jTxtEstrategia.getText().trim();
            String respuesta = this.jTxtRespuesta.getText().trim();
            
            ArrayList parametros = new ArrayList();
            parametros.add(this.id_estrategia);
            parametros.add(id_profesional);
            parametros.add(fechaInicio);
            parametros.add(fechaAgenda);
            parametros.add(perfil);
            parametros.add(estrategia);
            parametros.add(respuesta);
            parametros.add(this.historico);
            parametros.add(this.id_empresa);
            parametros.add(this.usuario);
            
            conexion.procAlmacenado("insertEstrategia", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            limpia();
        }else{
            JOptionPane.showMessageDialog(this,"Complete todos los datos obligatorios (*)", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
        this.jComboProfesional1.setSelectedIndex(-1);
        this.jInicio.setDate(null);
        this.jAgenda.setDate(null);
        this.jTxtPerfil.setText("");
        this.jTxtEstrategia.setText("");
        this.jTxtRespuesta.setText("");
        
        this.id_estrategia = 0;
        this.historico = "N";
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

    private void jBtnHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnHistoricoActionPerformed
        this.historico = "S";
        this.jBtnGuardarActionPerformed(null);
    }//GEN-LAST:event_jBtnHistoricoActionPerformed

    private void jComboProfesional1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboProfesional1ActionPerformed
        if (this.jComboProfesional1.getSelectedIndex() >= 0){
            ProfesionalData tmp = (ProfesionalData) profesionalArray.get(this.jComboProfesional1.getSelectedIndex());
            this.jTxtPerfil.setText(tmp.getPerfil());
        }else{
            this.jTxtPerfil.setText("");
        }
    }//GEN-LAST:event_jComboProfesional1ActionPerformed

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

    private void jComboProfesional1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProfesional1MouseClicked
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
                this.jComboProfesional1.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboProfesional1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser jAgenda;
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnHistorico;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnLimpiar;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JComboBox jComboHistoricos;
    private javax.swing.JComboBox jComboProfesional;
    private javax.swing.JComboBox jComboProfesional1;
    private javax.swing.JComboBox jComboVendedor;
    private javax.swing.JComboBox jComboZona;
    private com.toedter.calendar.JDateChooser jDesde;
    private com.toedter.calendar.JDateChooser jHasta;
    private com.toedter.calendar.JDateChooser jInicio;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTable jTablaConsulta;
    private javax.swing.JTextField jTxtBusca;
    private javax.swing.JTextArea jTxtEstrategia;
    private javax.swing.JTextArea jTxtPerfil;
    private javax.swing.JTextArea jTxtRespuesta;
    // End of variables declaration//GEN-END:variables
}
