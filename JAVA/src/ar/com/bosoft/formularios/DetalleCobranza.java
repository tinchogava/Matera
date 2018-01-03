/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.ConsolidadoCobranzaEntidadDataSource;
import ar.com.bosoft.DataSources.ConsolidadoCobranzaGeneralDataSource;
import ar.com.bosoft.DataSources.DetalladoCobranzaEntidadDataSource;
import ar.com.bosoft.DataSources.DetalladoCobranzaGeneralDataSource;
import ar.com.bosoft.DataSources.PlazosCobranzaEntidadDataSource;
import ar.com.bosoft.DataSources.PlazosCobranzaGeneralDataSource;
import ar.com.bosoft.Modelos.ConsolidadoCobranzaEntidad;
import ar.com.bosoft.Modelos.ConsolidadoCobranzaGeneral;
import ar.com.bosoft.Modelos.DetalladoCobranzaEntidad;
import ar.com.bosoft.Modelos.DetalladoCobranzaGeneral;
import ar.com.bosoft.Modelos.PlazosCobranzaEntidad;
import ar.com.bosoft.Modelos.PlazosCobranzaGeneral;
import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.EntidadCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.EntidadData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class DetalleCobranza extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, copias;
    String empresa, todos, nombreArchivo, impresora;
    
    ZonaCRUD zonaCRUD;
    EntidadCRUD entidadCRUD;
    
    ArrayList zonaArray, entidadArray, id_entidadArray;
    
    ResultSet rsConsulta;
    
    SeleccionImp seleccionImp;
    Reporte reporte;
    
    /**
     * Creates new form Comparativa
     * @param conexion
     * @param id_empresa
     * @param empresa
     */
    public DetalleCobranza(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.todos = "-- TODOS --";
        
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.entidadCRUD = new EntidadCRUD(conexion, id_empresa, empresa);
        
        this.zonaArray = new ArrayList();
        this.entidadArray = new ArrayList();
        this.id_entidadArray = new ArrayList();
        
        this.seleccionImp = new SeleccionImp(null, true);
        this.reporte = new Reporte();
        
        initComponents();
        
        llenaComboZona();
        consultaEntidad();
        
        limpia();
        zonaUsuario();
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
        this.zonaArray = zonaCRUD.consulta(true);
        Iterator i = zonaArray.iterator();
        while (i.hasNext()){
            ZonaData tmp = (ZonaData) i.next();
            this.jComboZona.addItem(tmp.getNombre());
        }
    }
    
    private void consultaEntidad(){
        this.entidadArray = entidadCRUD.consulta(true);
    }
    
    private void llenaComboEntidad(int id_zona){
        this.jComboEntidad.removeAllItems();
        this.id_entidadArray.clear();
        this.jComboEntidad.addItem(this.todos);
        
        Iterator i = entidadArray.iterator();
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
                this.id_entidadArray.add(tmp.getId_entidad());
                this.jComboEntidad.addItem(tmp.getNombre());
            }
        }
        this.jComboEntidad.setSelectedIndex(0);
    }
    
    private void limpia(){
        this.jComboTipo.setSelectedIndex(0);
        this.jDesde.setDate(null);
        this.jHasta.setDate(null);
        
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedItem(todos);
        }
        
        this.jComboPresentacion.setSelectedIndex(0);
        this.jComboEntidad.setSelectedItem(todos);
        this.jComboEntidad.setEnabled(false);
        this.jComboOperaciones.setSelectedIndex(0);
        
        this.nombreArchivo = "";
        this.impresora = "";
        this.copias = 0;
    }
    
    
    private void imprimir(int salida){
        String nombreReporte = this.jComboTipo.getSelectedItem().toString().split(" ")[0] + "Cobranza" + 
                                (this.jComboEntidad.isEnabled() ? "Entidad" : "General");
        
        
        try {
            ArrayList parametros = new ArrayList();
            parametros.add(this.jDesde.getDate() == null ? (long) 0 : this.jDesde.getDate().getTime());
            parametros.add(this.jHasta.getDate() == null ? (long) 0 : this.jHasta.getDate().getTime());
            
            int id_zona = 0;
            if (this.jComboZona.getSelectedIndex() > 0){
                ZonaData zona = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
                id_zona = zona.getId_zona();
            }
            parametros.add(id_zona);
            
            int id_entidad = 0;
            if(this.jComboEntidad.isEnabled()){
                if (this.jComboEntidad.getSelectedIndex() > 0){
                    id_entidad = (int) id_entidadArray.get(this.jComboEntidad.getSelectedIndex() - 1);
                }
            }
            parametros.add(id_entidad);
            
            String operaciones = "XXXX";
            switch (this.jComboOperaciones.getSelectedIndex()){
                case 1:
                    operaciones = "0000";
                    break;
                    
                case 2:
                    operaciones = "9999";
                    break;
            }
            parametros.add(operaciones);
            parametros.add(this.jComboEntidad.isEnabled());
            parametros.add(this.id_empresa);
            
            Map param = new HashMap();
            param.put("empresa", this.empresa);
            param.put("desde", this.jDesde.getDate());
            param.put("hasta", this.jHasta.getDate());
            param.put("zona", this.jComboZona.getSelectedItem().toString());
            param.put("operaciones", this.jComboOperaciones.getSelectedItem().toString());
            param.put("entidad", this.jComboEntidad.getSelectedItem().toString());

            switch (nombreReporte){
                case "DetalladoCobranzaGeneral":
                    rsConsulta = conexion.procAlmacenado("consultaDetalladoCobranza", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    
                    if (rsConsulta.first()){
                        DetalladoCobranzaGeneralDataSource dataDetalladoGral = new DetalladoCobranzaGeneralDataSource();
                        rsConsulta.beforeFirst();
                        while (rsConsulta.next()){
                            Date fechaRecibo = rsConsulta.getDate("fechaRecibo");
                            String numRecibo = rsConsulta.getString("numRecibo");
                            int id_presupuesto = rsConsulta.getInt("id_presupuesto");
                            Date fechaFactura = rsConsulta.getDate("fechaFactura");
                            String numFactura = rsConsulta.getString("numFactura");
                            BigDecimal importeAplicado = rsConsulta.getBigDecimal("importeAplicado");
                            BigDecimal rm1 = rsConsulta.getBigDecimal("rm1");
                            BigDecimal rm2 = rsConsulta.getBigDecimal("rm2");
                            String tipoOperacion = rsConsulta.getString("tipoOperacion");

                            ar.com.bosoft.Modelos.DetalladoCobranzaGeneral nuevo = new DetalladoCobranzaGeneral(fechaRecibo, fechaFactura, numRecibo, numFactura, tipoOperacion, id_presupuesto, importeAplicado, rm1, rm2);
                            dataDetalladoGral.add(nuevo);
                        }
                        reporte.startReport(nombreReporte, param, dataDetalladoGral, salida, nombreArchivo, impresora, copias);
                        break;
                    }else{
                        JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                
                case "DetalladoCobranzaEntidad":
                    rsConsulta = conexion.procAlmacenado("consultaDetalladoCobranza", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    
                    if (rsConsulta.first()){
                        DetalladoCobranzaEntidadDataSource dataDetalladoEntidad = new DetalladoCobranzaEntidadDataSource();
                        rsConsulta.beforeFirst();
                        while (rsConsulta.next()){
                            String entidad = rsConsulta.getString("entidad");
                            Date fechaRecibo = rsConsulta.getDate("fechaRecibo");
                            String numRecibo = rsConsulta.getString("numRecibo");
                            int id_presupuesto = rsConsulta.getInt("id_presupuesto");
                            Date fechaFactura = rsConsulta.getDate("fechaFactura");
                            String numFactura = rsConsulta.getString("numFactura");
                            BigDecimal importeAplicado = rsConsulta.getBigDecimal("importeAplicado");
                            BigDecimal rm1 = rsConsulta.getBigDecimal("rm1");
                            BigDecimal rm2 = rsConsulta.getBigDecimal("rm2");
                            String tipoOperacion = rsConsulta.getString("tipoOperacion");

                            ar.com.bosoft.Modelos.DetalladoCobranzaEntidad nuevo = new DetalladoCobranzaEntidad(fechaRecibo, fechaFactura, entidad, numRecibo, numFactura, tipoOperacion, id_presupuesto, importeAplicado, rm1, rm2);
                            dataDetalladoEntidad.add(nuevo);
                        }
                        reporte.startReport(nombreReporte, param, dataDetalladoEntidad, salida, nombreArchivo, impresora, copias);
                        break;
                    }else{
                        JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                case "PlazosCobranzaGeneral":
                    rsConsulta = conexion.procAlmacenado("consultaPlazosCobranza", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    
                    if (rsConsulta.first()){
                        PlazosCobranzaGeneralDataSource dataPlazosGral = new PlazosCobranzaGeneralDataSource();
                        rsConsulta.beforeFirst();
                        while (rsConsulta.next()){
                            int dias = rsConsulta.getInt("dias");
                            BigDecimal importeAplicado = rsConsulta.getBigDecimal("importeAplicado");

                            ar.com.bosoft.Modelos.PlazosCobranzaGeneral nuevo = new PlazosCobranzaGeneral(dias, importeAplicado);
                            dataPlazosGral.add(nuevo);
                        }
                        reporte.startReport(nombreReporte, param, dataPlazosGral, salida, nombreArchivo, impresora, copias);
                        break;
                    }else{
                        JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                case "PlazosCobranzaEntidad":
                    rsConsulta = conexion.procAlmacenado("consultaPlazosCobranza", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    
                    if (rsConsulta.first()){
                        PlazosCobranzaEntidadDataSource dataPlazosEntidad = new PlazosCobranzaEntidadDataSource();
                        rsConsulta.beforeFirst();
                        while (rsConsulta.next()){
                            String entidad = rsConsulta.getString("entidad");
                            int dias = rsConsulta.getInt("dias");
                            BigDecimal importeAplicado = rsConsulta.getBigDecimal("importeAplicado");

                            ar.com.bosoft.Modelos.PlazosCobranzaEntidad nuevo = new PlazosCobranzaEntidad(entidad, dias, importeAplicado);
                            dataPlazosEntidad.add(nuevo);
                        }
                        reporte.startReport(nombreReporte, param, dataPlazosEntidad, salida, nombreArchivo, impresora, copias);
                        break;
                    }else{
                        JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                case "ConsolidadoCobranzaGeneral":
                    rsConsulta = conexion.procAlmacenado("consultaConsolidadoCobranza", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    
                    if (rsConsulta.first()){
                        ConsolidadoCobranzaGeneralDataSource dataConsolidadoGral = new ConsolidadoCobranzaGeneralDataSource();
                        rsConsulta.beforeFirst();
                        while (rsConsulta.next()){
                            int year = rsConsulta.getInt("year");
                            String mes = Utiles.nombreMes(rsConsulta.getInt("mes") - 1, false);
                            BigDecimal importeAplicado = rsConsulta.getBigDecimal("importeAplicado");

                            ar.com.bosoft.Modelos.ConsolidadoCobranzaGeneral nuevo = new ConsolidadoCobranzaGeneral(year, mes, importeAplicado);
                            dataConsolidadoGral.add(nuevo);
                        }
                        reporte.startReport(nombreReporte, param, dataConsolidadoGral, salida, nombreArchivo, impresora, copias);
                        break;
                    }else{
                        JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                case "ConsolidadoCobranzaEntidad":
                    rsConsulta = conexion.procAlmacenado("consultaConsolidadoCobranza", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    
                    if (rsConsulta.first()){
                        ConsolidadoCobranzaEntidadDataSource dataConsolidadoEntidad = new ConsolidadoCobranzaEntidadDataSource();
                        rsConsulta.beforeFirst();
                        while (rsConsulta.next()){
                            String entidad = rsConsulta.getString("entidad");
                            int year = rsConsulta.getInt("year");
                            String mes = Utiles.nombreMes(rsConsulta.getInt("mes") - 1, false);
                            BigDecimal importeAplicado = rsConsulta.getBigDecimal("importeAplicado");

                            ar.com.bosoft.Modelos.ConsolidadoCobranzaEntidad nuevo = new ConsolidadoCobranzaEntidad(year, entidad, mes, importeAplicado);
                            dataConsolidadoEntidad.add(nuevo);
                        }
                        reporte.startReport(nombreReporte, param, dataConsolidadoEntidad, salida, nombreArchivo, impresora, copias);
                        break;
                    }else{
                        JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
            }
            
            limpia();
        }catch(SQLException ex){
            Utiles.enviaError(this.empresa,ex);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupAprob = new javax.swing.ButtonGroup();
        btnGroupFact = new javax.swing.ButtonGroup();
        btnGroupCx = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jDesde = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jDesde.getJCalendar().setTodayButtonVisible(true); 
        this.jDesde.getJCalendar().setTodayButtonText("Hoy"); 
        this.jDesde.getJCalendar().setWeekOfYearVisible(false);
        jLabel2 = new javax.swing.JLabel();
        jHasta = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jHasta.getJCalendar().setTodayButtonVisible(true); 
        this.jHasta.getJCalendar().setTodayButtonText("Hoy"); 
        this.jHasta.getJCalendar().setWeekOfYearVisible(false);
        jLabel = new javax.swing.JLabel();
        jComboEntidad = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jComboTipo = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jComboPresentacion = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jComboOperaciones = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();

        setTitle("Listado de cobranza");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jLabel1.setText("Desde");

        jLabel2.setText("Hasta");

        jLabel.setText("Entidad");

        jComboEntidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboEntidadMouseClicked(evt);
            }
        });

        jLabel6.setText("Zona");

        jComboZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboZonaActionPerformed(evt);
            }
        });

        jLabel12.setText("Tipo");

        jComboTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Detallado de cobranza", "Plazos de cobranza", "Consolidado de cobranza" }));

        jLabel13.setText("Presentación");

        jComboPresentacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "General", "Entidad" }));
        jComboPresentacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboPresentacionActionPerformed(evt);
            }
        });

        jLabel8.setText("Operaciones");

        jComboOperaciones.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- TODOS --", "Corrientes", "No corrientes" }));

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Salida"));

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel13)
                    .addComponent(jLabel)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboPresentacion, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboOperaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboZona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboEntidad, jComboZona});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jComboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jComboPresentacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel)
                            .addComponent(jComboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jComboOperaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(1, 1, 1))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 13, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnSalir))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnScrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScrActionPerformed
        imprimir(0);
    }//GEN-LAST:event_jBtnScrActionPerformed

    private void jBtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImpActionPerformed
        seleccionImp.setVisible(true);
        if (seleccionImp.getSino()){
            this.impresora = seleccionImp.getImpresora();
            this.copias = seleccionImp.getCopias();
            imprimir(1);
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

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        int id_zona = 0;
        
        if (this.jComboZona.getSelectedIndex() > 0) {
            ZonaData tmp = (ZonaData) this.zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
            id_zona = tmp.getId_zona();  
        }
        
        try{
            this.llenaComboEntidad(id_zona);                
        } catch (Exception ex){}
    }//GEN-LAST:event_jComboZonaActionPerformed

    private void jComboPresentacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboPresentacionActionPerformed
        this.jComboEntidad.setEnabled(this.jComboPresentacion.getSelectedItem().toString().equals("Entidad"));
    }//GEN-LAST:event_jComboPresentacionActionPerformed

    private void jComboEntidadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboEntidadMouseClicked
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
                this.jComboEntidad.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboEntidadMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroupAprob;
    private javax.swing.ButtonGroup btnGroupCx;
    private javax.swing.ButtonGroup btnGroupFact;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jBtnXls;
    private javax.swing.JComboBox jComboEntidad;
    private javax.swing.JComboBox jComboOperaciones;
    private javax.swing.JComboBox jComboPresentacion;
    private javax.swing.JComboBox jComboTipo;
    private javax.swing.JComboBox jComboZona;
    private com.toedter.calendar.JDateChooser jDesde;
    private com.toedter.calendar.JDateChooser jHasta;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables
}
