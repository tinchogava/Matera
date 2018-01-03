/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.RankingVentaDataSource;
import ar.com.bosoft.Utilidades.QueryBuilder;
import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ProductoFactCRUD;
import ar.com.bosoft.crud.ProfesionalCRUD;
import ar.com.bosoft.crud.ProveedorCRUD;
import ar.com.bosoft.crud.VendedorCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.ProductoFactData;
import ar.com.bosoft.data.ProfesionalData;
import ar.com.bosoft.data.VendedorData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.data.ProveedorData;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import matera.cache.CacheProfesional;
import matera.services.RankingVentaService;
import org.apache.commons.lang.math.NumberUtils;
import org.javalite.activejdbc.CaseInsensitiveMap;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class RankingVenta extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, copias;
    String empresa, todos, nombreArchivo, impresora;
    ProfesionalCRUD profesionalCRUD;
    ProductoFactCRUD productoFactCRUD;
    ZonaCRUD zonaCRUD;
    ProveedorCRUD proveedorCRUD;
    VendedorCRUD vendedorCRUD;
    
    ArrayList zonaArray, profesionalArray, id_profesionalArray, vendedorArray, id_vendedorArray, productoFactArray, proveedorArray;
    
    ResultSet rsConsulta;
    
    SeleccionImp seleccionImp;
    Reporte reporte;
    
    /**
     * Creates new form Comparativa
     * @param conexion
     * @param id_empresa
     * @param empresa
     */
    public RankingVenta(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.todos = "-- TODOS --";
        
        this.profesionalCRUD = new ProfesionalCRUD(conexion, id_empresa, empresa);
        this.productoFactCRUD = new ProductoFactCRUD(conexion, empresa);
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.proveedorCRUD = new ProveedorCRUD(conexion, id_empresa, empresa);
        this.vendedorCRUD = new VendedorCRUD(conexion, id_empresa, empresa);
        
        this.zonaArray = new ArrayList();
        this.profesionalArray = new ArrayList();
        this.id_profesionalArray = new ArrayList();
        this.vendedorArray = new ArrayList();
        this.id_vendedorArray = new ArrayList();
        
        this.seleccionImp = new SeleccionImp(null, true);
        this.reporte = new Reporte();
        
        initComponents();
        
        llenaComboZona();
        consultaProfesional();
        consultaVendedor();
        consultaProductoFact();
        llenaComboProveedor();
        
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
            if (id_zona == 0 &&
                    tmp.getId_especialidad() == 13){
                incluye = true;
            }else{
                if (tmp.getId_zona() == id_zona &&
                        tmp.getId_especialidad() == 13){
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
        this.jComboProfesionalProducto.removeAllItems();
        this.id_profesionalArray.clear();
        this.jComboProfesionalProducto.addItem(todos);
        
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
                this.jComboProfesionalProducto.addItem(tmp.getNombre());
            }
        }
        this.jComboProfesionalProducto.setSelectedIndex(0);
    }
    
    private void consultaProductoFact(){
        this.productoFactArray = productoFactCRUD.consulta(true, 2);
    }
    
    private void llenaComboProducto(){
        this.jComboProfesionalProducto.removeAllItems();
        this.id_profesionalArray.clear();
        this.jComboProfesionalProducto.addItem(todos);
        
        Iterator i = productoFactArray.iterator();
        while (i.hasNext()){
            ProductoFactData tmp = (ProductoFactData) i.next();
            this.jComboProfesionalProducto.addItem(tmp.getNombre());
        }
        
        this.jComboProfesionalProducto.setSelectedItem(todos);
    }
    
    private void llenaComboProveedor(){
        this.jComboProveedor.addItem(this.todos);
        this.proveedorArray = this.proveedorCRUD.consulta(true);
        Iterator i = proveedorArray.iterator();
        while (i.hasNext()) {
            ProveedorData p = (ProveedorData) i.next();
            this.jComboProveedor.addItem(p.getNombre());
        }
    }
    
    private void limpia(){
        this.jDesde.setDate(null);
        this.jHasta.setDate(null);
        
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedItem(todos);
        }
        
        this.jComboPor.setSelectedIndex(0);
        this.jLblPor.setText(this.jComboPor.getSelectedItem().toString());
        this.jCheckAgrupa.setSelected(false);
        this.jComboProfesionalProducto.setSelectedIndex(0);
        this.jComboVisualiza.setSelectedIndex(0);
        this.jComboPresupuestos.setSelectedIndex(0);
        this.jComboVendedor.setSelectedItem(todos);
        
        this.nombreArchivo = "";
        this.impresora = "";
        this.copias = 0;
    }
    
    
    private void imprimir(int salida){
        try {
            int id_profesional = 0;
            int id_productoFact = 0;
            if (this.jComboProfesionalProducto.getSelectedIndex() > 0){
                switch (this.jComboPor.getSelectedIndex()){
                    case 0:     //Profesional
                        id_profesional = (int) id_profesionalArray.get(this.jComboProfesionalProducto.getSelectedIndex() - 1);
                        break;
                        
                    case 1:     //Producto
                        ProductoFactData productoFact = (ProductoFactData) productoFactArray.get(this.jComboProfesionalProducto.getSelectedIndex() - 1);
                        id_productoFact = productoFact.getId_productoFact();
                        break;
                }
            }
            
            String aprobado = "X";
            Integer facturado = Utiles.FACTURADO.TODOS;
            switch (this.jComboPresupuestos.getSelectedIndex()){
                case 1:     //Aprobados
                    aprobado = "S";
                    break;
                    
                case 2:
                    aprobado = "S";
                    facturado = Utiles.FACTURADO.SIN_FACTURAR;
                    break;
                    
                case 3:
                    aprobado = "S";
                    facturado = Utiles.FACTURADO.FACTURADO;
                    break;                    
                    
                case 4:     //No aprobados
                    aprobado = "N";
                    break;
            }
            
            int id_zona = 0;
            if (this.jComboZona.getSelectedIndex() > 0){
                ZonaData zona = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
                id_zona = zona.getId_zona();
            }
            
            int id_vendedor = 0;
            if (this.jComboVendedor.getSelectedIndex() > 0){
                id_vendedor = (int) id_vendedorArray.get(this.jComboVendedor.getSelectedIndex() - 1);
            }
            
            int id_proveedor = 0;
            if (this.jComboProveedor.getSelectedIndex() > 0) {
                ProveedorData p = (ProveedorData) proveedorArray.get(this.jComboProveedor.getSelectedIndex() - 1);
                id_proveedor = p.getId_proveedor();
            }
            
            QueryBuilder q = new QueryBuilder();
            ArrayList<Object> params = new ArrayList<>();
            if(this.jDesde.getDate() != null) {q.And("p.fecha >= ?"); params.add(this.jDesde.getDate());}
            if(this.jHasta.getDate() != null) {q.And("p.fecha <= ?"); params.add(this.jHasta.getDate());}
            if(id_proveedor != 0) {q.And("presupuestoprod.id_proveedor = ?"); params.add(id_proveedor);}
            if(id_profesional != 0) {q.And("p.id_profesional1 = ?"); params.add(id_profesional);}
            if(id_zona != 0) {q.And("p.id_zona = ?"); params.add(id_zona);}
            if(id_vendedor != 0) {q.And("p.id_vendedor = ?"); params.add(id_vendedor);}
            switch (aprobado) {
                case "S":
                    q.And("p.estado NOT IN ('R','N','P')");
                    break;
                case "N":
                    q.And("p.estado = 'P'");
                    break;
            }
            
            if (id_productoFact != 0){ 
                q.And("productofact.id_productofact = ?");
                params.add(id_productoFact); 
            }
            
            switch(facturado){
                case Utiles.FACTURADO.TODOS: 
                    break;
                case Utiles.FACTURADO.SIN_FACTURAR:
                    q.And("(select count(1) from factura where factura.id_presupuesto = p.id_presupuesto) = 0");
                    break;
                case Utiles.FACTURADO.FACTURADO:
                    q.And("(select count(1) from factura where factura.id_presupuesto = p.id_presupuesto) > 0");
                    break;
            }
            
            if (this.jCheckAgrupa.isSelected()){
                if (this.jComboPor.getSelectedIndex()  == 0){
                    q.groupBy("profesional");
                    q.orderBy("profesional");
                }
                else{
                    q.groupBy("productofact.id_productofact");
                    q.orderBy("producto");
                }
            }
            
            ArrayList<CaseInsensitiveMap> presupuestos = matera.db.Presupuesto
                    .getRankingVentas(q.getQuery(), params.toArray(), this.jCheckAgrupa.isSelected());
            
            if (!presupuestos.isEmpty()){
                
                Map param = new HashMap();
                param.put("empresa", this.empresa);
                param.put("desde", this.jDesde.getDate());
                param.put("hasta", this.jHasta.getDate());
                param.put("zona", this.jComboZona.getSelectedItem().toString());
                param.put("por", this.jComboPor.getSelectedItem().toString());
                param.put("profesionalProducto", this.jComboProfesionalProducto.getSelectedItem().toString());
                param.put("presupuestos", jComboPresupuestos.getSelectedItem().toString());
                param.put("vendedor", jComboVendedor.getSelectedItem().toString());
                param.put("proveedor", jComboProveedor.getSelectedItem().toString());
                
                String visualiza = this.jComboVisualiza.getSelectedItem().toString();                
                param.put("visualiza", visualiza);
                param.put("oneHundred", new BigDecimal(100));

                RankingVentaDataSource data = new RankingVentaDataSource();
                
                for (Object o : presupuestos){
                    CaseInsensitiveMap m = (CaseInsensitiveMap) o;
                    String cabecera = Utiles.v(m.get(this.jComboPor.getSelectedIndex() == 0 ? "profesional" : "producto"));
                    String registro = Utiles.v(m.get(this.jComboPor.getSelectedIndex() == 1 ? "profesional" : "producto"));
                    Double pxUnit = Double.parseDouble(Utiles.v(m.get("pxUnit")));
                    Integer cant = Integer.parseInt(Utiles.v(m.get("cantidad")));
                    BigDecimal valor = new BigDecimal(pxUnit);
                    BigDecimal facturacion;
                    if(m.get("montoFacturado") != null){
                        facturacion = BigDecimal.valueOf(NumberUtils.toDouble(Utiles.v(m.get("montoFacturado")), 0));
                    } else {
                        facturacion = BigDecimal.ZERO;
                    }
                    if (!this.jCheckAgrupa.isSelected() && visualiza.equals("Cantidad"))
                        valor = BigDecimal.valueOf(cant);
                        
                    else if(this.jCheckAgrupa.isSelected() && visualiza.equals("Cantidad"))
                        valor = BigDecimal.valueOf(Integer.parseInt(Utiles.v(m.get("cantidadAgrupada"))));
                        
                    else if (this.jCheckAgrupa.isSelected() && visualiza.equals("Importe")){
                        valor = BigDecimal.valueOf(Double.parseDouble(Utiles.v(m.get("precioAgrupado"))));
                        //BigDecimal montoFacturado = BigDecimal.valueOf(NumberUtils.toDouble(Utiles.v(m.get("montoFacturado")), 0));
                        try{
                        facturacion = RankingVentaService
                                .getMontoFacturadoByProfesional(Integer.parseInt(Utiles.v(m.get("id_profesional"))), 
                                        jDesde.getDate(), jHasta.getDate());
                        } catch(Exception e){
                            Utiles.showMessage("Las fechas contempladas son recientes y pueden contener "
                                    + "\nvalores que no pueden ser calculados aún");
                        }
                    }
                    
                    ar.com.bosoft.Modelos.RankingVenta nuevo = new ar.com.bosoft.Modelos.RankingVenta(cabecera, registro, valor, facturacion);
                    data.add(nuevo);
                }
                reporte.startReport("RankingVenta" + (this.jCheckAgrupa.isSelected() ? "Agrupado" : "Detallado"), param, data, salida, nombreArchivo, impresora, copias);
            }

            else{
                JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(Exception ex){
           //Utiles.enviaError(this.empresa,ex);
           ex.printStackTrace();
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
        jLblPor = new javax.swing.JLabel();
        jComboProfesionalProducto = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jComboVendedor = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jComboPor = new javax.swing.JComboBox();
        jCheckAgrupa = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        jComboVisualiza = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jComboPresupuestos = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();
        jComboProveedor = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jBtnSalir = new javax.swing.JButton();

        setTitle("Ranking de ventas");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jLabel1.setText("Desde");

        jLabel2.setText("Hasta");

        jLblPor.setText("Profesional");

        jComboProfesionalProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProfesionalProductoMouseClicked(evt);
            }
        });

        jLabel6.setText("Zona");

        jLabel7.setText("Vendedor");

        jComboZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboZonaActionPerformed(evt);
            }
        });

        jLabel12.setText("Consulta por");

        jComboPor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Profesional", "Producto" }));
        jComboPor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboPorActionPerformed(evt);
            }
        });

        jCheckAgrupa.setText("Agrupado");
        jCheckAgrupa.setContentAreaFilled(false);
        jCheckAgrupa.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel13.setText("Visualiza");

        jComboVisualiza.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cantidad", "Importe" }));

        jLabel8.setText("Presupuestos");

        jComboPresupuestos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- TODOS --", "Aprobados", "Aprobados sin facturar", "Aprobados y facturados", "No aprobados" }));
        jComboPresupuestos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboPresupuestosActionPerformed(evt);
            }
        });

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

        jComboProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProveedorMouseClicked(evt);
            }
        });
        jComboProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboProveedorKeyReleased(evt);
            }
        });

        jLabel9.setText("Proveedor");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12)
                    .addComponent(jLabel7)
                    .addComponent(jLblPor)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboVisualiza, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jComboVendedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboProfesionalProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboPresupuestos, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jComboPor, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckAgrupa))
                            .addComponent(jComboZona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboProfesionalProducto, jComboVendedor, jComboZona});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jComboPor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckAgrupa))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLblPor)
                            .addComponent(jComboProfesionalProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jComboVisualiza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jComboPresupuestos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnSalir)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
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
        if (this.jComboZona.getSelectedIndex() > 0){
            ZonaData tmp = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
            id_zona = tmp.getId_zona();
        }
        
        if (this.jLblPor.getText().equals("Profesional")){
            llenaComboProfesional(id_zona);
        }
        
        llenaComboVendedor(id_zona);
    }//GEN-LAST:event_jComboZonaActionPerformed

    private void jComboPresupuestosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboPresupuestosActionPerformed
        //this.jCheckFacturado.setSelected(false);
        //this.jCheckFacturado.setEnabled(this.jComboPresupuestos.getSelectedItem().toString().equals("Aprobados"));
    }//GEN-LAST:event_jComboPresupuestosActionPerformed

    private void jComboPorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboPorActionPerformed
        try{
            this.jLblPor.setText(this.jComboPor.getSelectedItem().toString());
            if (this.jLblPor.getText().equals("Profesional")){
                int id_zona = 0;
                if (this.jComboZona.getSelectedIndex() > 0){
                    ZonaData tmp = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
                    id_zona = tmp.getId_zona();
                }
                this.llenaComboProfesional(id_zona);
            }else{
                llenaComboProducto();
            }
        }catch (Exception ex){
            //Se captura para poder inicializar
        }
    }//GEN-LAST:event_jComboPorActionPerformed

    private void jComboProfesionalProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProfesionalProductoMouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle(this.jLblPor.getText());
            Iterator it = this.jLblPor.getText().equals("Profesional") ? this.profesionalArray.iterator() : this.productoFactArray.iterator();
            while (it.hasNext()) {
                if (this.jLblPor.getText().equals("Profesional")) {
                    ProfesionalData tmp = (ProfesionalData) it.next();
                    Object[] fila = {tmp.getNombre()};
                    Principal.general1.add(fila);
                } else {
                    ProductoFactData tmp = (ProductoFactData) it.next();
                    Object[] fila = {tmp.getNombre()};
                    Principal.general1.add(fila);
                }
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboProfesionalProducto.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboProfesionalProductoMouseClicked

    private void jComboProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProveedorMouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Proveedor");
            Iterator it = this.proveedorArray.iterator();
            while (it.hasNext()) {
                ProveedorData tmp = (ProveedorData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboProveedor.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboProveedorMouseClicked

    private void jComboProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboProveedorKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            this.jComboProveedor.setSelectedIndex(0);
        }
    }//GEN-LAST:event_jComboProveedorKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroupAprob;
    private javax.swing.ButtonGroup btnGroupCx;
    private javax.swing.ButtonGroup btnGroupFact;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jBtnXls;
    private javax.swing.JCheckBox jCheckAgrupa;
    private javax.swing.JComboBox jComboPor;
    private javax.swing.JComboBox jComboPresupuestos;
    private javax.swing.JComboBox jComboProfesionalProducto;
    private javax.swing.JComboBox jComboProveedor;
    private javax.swing.JComboBox jComboVendedor;
    private javax.swing.JComboBox jComboVisualiza;
    private javax.swing.JComboBox jComboZona;
    private com.toedter.calendar.JDateChooser jDesde;
    private com.toedter.calendar.JDateChooser jHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblPor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables
}
