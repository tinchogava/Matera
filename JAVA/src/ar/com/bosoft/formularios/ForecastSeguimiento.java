/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.RenderTablas.ColumnColorRenderer;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.ActiveDatabase;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ForecastGrupoCRUD;
import ar.com.bosoft.crud.VendedorCRUD;
import ar.com.bosoft.data.ForecastGrupoData;
import ar.com.bosoft.data.VendedorData;
import ar.com.dialogos.AvisoEspera;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import matera.helpers.ExportHelper;
import static matera.jooq.Tables.ENTIDAD;
import static matera.jooq.Tables.FORECASTENTIDAD;
import static matera.jooq.Tables.FORECASTGRUPO;
import static matera.jooq.Tables.PRESUPUESTO;
import static matera.jooq.Tables.PRESUPUESTOPROD;
import static matera.jooq.Tables.PRODUCTOFACT;
import static matera.jooq.Tables.PROFESIONAL;
import static matera.jooq.Tables.VENDEDOR;
import matera.jooq.tables.records.EntidadRecord;
import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class ForecastSeguimiento extends javax.swing.JInternalFrame {
    private final Conexion conexion;
    private final int id_empresa;
    private final String empresa;
    VendedorCRUD vendedorCRUD;
    ForecastGrupoCRUD forecastGrupoCRUD;
    double mensual;
    ArrayList forecastGrupoArray, vendedorArray, id_vendedorArray, id_zonaArray, id_entidadArray, id_productoFactArray;
    ResultSet rsConsulta;
    DefaultTableModel modeloConsulta;
    TableRowSorter sorter;

    AvisoEspera avisoEspera;
    
    DetalleForecast detalleForecast;
    
    Result<Record> vendido = null;
    
    DSLContext dsl;
            
    /**
     * Creates new form ForecastSeguimiento
     * @param conexion
     * @param id_empresa
     * @param empresa
     */
    public ForecastSeguimiento(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.vendedorCRUD = new VendedorCRUD(conexion, id_empresa, empresa);
        this.forecastGrupoCRUD = new ForecastGrupoCRUD(conexion, id_empresa, empresa);
        
        this.id_vendedorArray = new ArrayList();
        this.id_zonaArray = new ArrayList();
        this.id_entidadArray = new ArrayList();
        this.id_productoFactArray = new ArrayList();
        
        
        initComponents();
        
        llenaComboForecastGrupo();
        llenaComboVendedor();
        
        jTablaConsulta.setDefaultRenderer(Object.class, new ColumnColorRenderer("cumplimiento"));
    }

    private void llenaComboVendedor(){
        this.jComboVendedor.removeAllItems();
        this.id_vendedorArray.clear();
        this.id_zonaArray.clear();
        
        this.vendedorArray = vendedorCRUD.consulta(true);
        Iterator i = vendedorArray.iterator();
        while (i.hasNext()){
            VendedorData tmp = (VendedorData) i.next();
            if (tmp.getId_especialidad() == 13){
                if (Principal.usuarioData.getId_zonaSistema() == 0) {
                    this.jComboVendedor.addItem(tmp.getNombre());
                    this.id_vendedorArray.add(tmp.getId_vendedor());
                    this.id_zonaArray.add(tmp.getId_zona());
                }else if (tmp.getId_zona() == Principal.usuarioData.getId_zonaSistema()) {
                    this.jComboVendedor.addItem(tmp.getNombre());
                    this.id_vendedorArray.add(tmp.getId_vendedor());
                    this.id_zonaArray.add(tmp.getId_zona());
                }
            } 
        }
        this.jComboVendedor.setSelectedIndex(-1);
    }
    
    private void llenaComboForecastGrupo(){
        this.jComboForecastGrupo.addItem("-- TODOS --");
        this.forecastGrupoArray = forecastGrupoCRUD.consulta(true);
        Iterator i = forecastGrupoArray.iterator();        
        while (i.hasNext()){
            ForecastGrupoData tmp = (ForecastGrupoData) i.next();
            this.jComboForecastGrupo.addItem(tmp.getNombre());
        }
    }
    
    private String validaObligatorios(){
        String respuesta = "";
        if (this.jAño.getYear() > Calendar.getInstance().get(Calendar.YEAR)) {
            respuesta = "El año no puede ser mayor al actual";
        }
        
        if (this.jComboVendedor.getSelectedIndex() < 0) {
            respuesta = "Seleccione el vendedor";
        }
        return respuesta;
    }
    
    private Result<Record> getVendido(Date from, Date to, Integer vendedor, Integer forecastGrupo){
        // Join two tables
        try{
            dsl = ActiveDatabase.getDSLContext();
            java.sql.Date fromDate = new java.sql.Date(from.getTime());
            java.sql.Date toDate = new java.sql.Date(to.getTime());
            SelectConditionStep step;
            step = dsl.select()
                                  .from(PRESUPUESTO)
                                  .join(PRESUPUESTOPROD).on(PRESUPUESTOPROD.ID_PRESUPUESTO.equal(PRESUPUESTO.ID_PRESUPUESTO))
                                  .join(PRODUCTOFACT).on(PRESUPUESTOPROD.ID_PRODUCTOFACT.equal(PRODUCTOFACT.ID_PRODUCTOFACT))
                                  .join(FORECASTGRUPO).on(FORECASTGRUPO.ID_FORECASTGRUPO.equal(PRODUCTOFACT.ID_FORECASTGRUPO))
                                  .join(VENDEDOR).on(VENDEDOR.ID_VENDEDOR.equal(PRESUPUESTO.ID_VENDEDOR))
                                  .leftJoin(ENTIDAD).on(ENTIDAD.ID_ENTIDAD.equal(PRESUPUESTO.ID_LUGARCIRUGIA))
                                  .leftJoin(PROFESIONAL).on(PROFESIONAL.ID_PROFESIONAL.equal(PRESUPUESTO.ID_PROFESIONAL1))
                                  .where(PRESUPUESTO.FECHAAPROBACION.greaterOrEqual(fromDate))
                                  .and(PRESUPUESTO.FECHAAPROBACION.lessOrEqual(toDate))
                                  .and(VENDEDOR.ID_VENDEDOR.equal(vendedor))
                                  ;

            if (forecastGrupo > 0){
                step = step.and(FORECASTGRUPO.ID_FORECASTGRUPO.equal(forecastGrupo));
            }
            return step.fetch();            
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }
        return null;
    }
    
    private List<EntidadRecord> getEntidades(Integer zona){
        List<EntidadRecord> records = new ArrayList<>();
        try {
            dsl = ActiveDatabase.getDSLContext();
            records = dsl.select(ENTIDAD.fields())
                    .from(ENTIDAD)
                    .join(FORECASTENTIDAD)
                    .on(ENTIDAD.ID_ENTIDAD
                            .equal(FORECASTENTIDAD.ID_ENTIDAD)).and(ENTIDAD.ID_ZONA.equal(zona))
                    .fetch()
                    .into(ENTIDAD);
            
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }
        return records;
    }
    
    private List<Record> filterByEntidadAndProducto(String entidad, String codProductoFact, Boolean otros){
        if (!otros)
            return vendido.parallelStream()
                    .filter(p -> p.into(ENTIDAD).getNombre() != null) 
                    .filter(p -> p.into(ENTIDAD).getNombre().equals(entidad))
                    .filter(p -> p.into(PRODUCTOFACT).getCodigo().equals(codProductoFact))
                .collect(Collectors.toList());
        
        return vendido.parallelStream().filter(
                p -> !id_entidadArray.contains(p.into(ENTIDAD).getIdEntidad()))
                        .filter(
                                j -> j.into(PRODUCTOFACT).getCodigo().equals(codProductoFact))
                .collect(Collectors.toList());
    }
    
    private void consulta(){
        try {
                vendido = null;
                
                this.id_entidadArray.clear();
                
                Integer id_zona = (int) this.id_zonaArray.get(this.jComboVendedor.getSelectedIndex());
                
                List<EntidadRecord> entidades = getEntidades(id_zona);                
                
                if (entidades.size() > 0) {
                    
                    ArrayList parametros = new ArrayList();
                    parametros.add("Código");
                    parametros.add("Producto");
                    parametros.add("Forecast");
                    parametros.add("Acumulado");
                    parametros.add("Saldo");
                    parametros.add("Cumplimiento");
                    
                    int cantEntidades = 0;
                    for (EntidadRecord entidad : entidades){
                        parametros.add(entidad.getNombre());
                        id_entidadArray.add(entidad.getIdEntidad());
                        cantEntidades++;
                    }
                    
                    parametros.add("Otros");
                    
                    Object[] columnas = parametros.toArray();
                    
                    modeloConsulta = new DefaultTableModel(columnas, 0);
                    this.jTablaConsulta.setModel(modeloConsulta);
                    
                    //Codigo
                    jTablaConsulta.getColumnModel().getColumn(0).setMinWidth(100);
                    jTablaConsulta.getColumnModel().getColumn(0).setPreferredWidth(120);
                    
                    //Producto
                    jTablaConsulta.getColumnModel().getColumn(1).setMinWidth(150);
                    jTablaConsulta.getColumnModel().getColumn(1).setPreferredWidth(240);
                    
                    //Forecast
                    jTablaConsulta.getColumnModel().getColumn(2).setMinWidth(50);
                    jTablaConsulta.getColumnModel().getColumn(2).setPreferredWidth(75);
                    
                    //Acumulado
                    jTablaConsulta.getColumnModel().getColumn(3).setMinWidth(50);
                    jTablaConsulta.getColumnModel().getColumn(3).setPreferredWidth(75);
                    
                    //Saldo
                    jTablaConsulta.getColumnModel().getColumn(4).setMinWidth(50);
                    jTablaConsulta.getColumnModel().getColumn(4).setPreferredWidth(75);
                    
                    //Cumplimiento
                    jTablaConsulta.getColumnModel().getColumn(5).setMinWidth(50);
                    jTablaConsulta.getColumnModel().getColumn(5).setPreferredWidth(75);
                    
                    //Entidades
                    int columnaActual = 6;
                    for (int i = 0; i < cantEntidades; i++) {
                        jTablaConsulta.getColumnModel().getColumn(columnaActual).setMinWidth(50);
                        jTablaConsulta.getColumnModel().getColumn(columnaActual).setPreferredWidth(modeloConsulta.getColumnName(columnaActual).length()*10);
                        columnaActual++;
                    }
                    
                    //Otros
                    jTablaConsulta.getColumnModel().getColumn(columnaActual).setMinWidth(50);
                    jTablaConsulta.getColumnModel().getColumn(columnaActual).setPreferredWidth(75);
                    
                    // Obtener presupuestos aprobados 
                    Calendar c = Calendar.getInstance();
                    Integer year = this.jAño.getYear();
                    Integer month = this.jComboMes.getSelectedIndex();
                    Date from, to;
                    
                    Integer id_vendedor = (int) this.id_vendedorArray.get(this.jComboVendedor.getSelectedIndex());
                    
                    Integer id_forecastGrupo = 0;
                    if (this.jComboForecastGrupo.getSelectedIndex() > 0) {
                        ForecastGrupoData f = (ForecastGrupoData) this.forecastGrupoArray.get(this.jComboForecastGrupo.getSelectedIndex() - 1);
                        id_forecastGrupo = f.getId_forecastGrupo();
                    }                    
                    //TRAER DE TODO EL YEAR
                    if (month.equals(0)){
                        c.set(year,Calendar.JANUARY,1);
                        from = c.getTime();
                        c.set(Calendar.MONTH, Calendar.DECEMBER);
                        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                        to = c.getTime();
                    }
                    else{
                        c.set(year,month-1,1);
                        from = c.getTime();
                        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                        to = c.getTime();                        
                    }
                    
                    vendido = getVendido(from, to, id_vendedor, id_forecastGrupo);

                    parametros.clear();
                    
                    parametros.add(year);
                    parametros.add(id_vendedor);
                    parametros.add(id_forecastGrupo);
                    
                    rsConsulta = conexion.procAlmacenado("productosForecast", parametros,  
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
                    
                    this.id_productoFactArray.clear();
                    
                    Map <Integer, Result<Record>> map = vendido.intoGroups(PRODUCTOFACT.ID_PRODUCTOFACT);
                    
                    if (rsConsulta.first()) {
                        rsConsulta.beforeFirst();
                        while (rsConsulta.next()) {
                            String codigo = rsConsulta.getString("codigo");
                            String nombre = rsConsulta.getString("nombre");
                            int forecast = rsConsulta.getInt("cantidad");
                            int id_productoFact = rsConsulta.getInt("id_productoFact");
                            
                            Integer acumulado = 0;
                            double saldo = forecast;
                            
                            Map rowMap = new HashMap();
                            rowMap.put("codigo", codigo);
                            rowMap.put("producto", nombre);
                            if(jComboMes.getSelectedIndex() > 0){
                                mensual = forecast / 12;
                                rowMap.put("forecast", mensual);
                            } else {
                                rowMap.put("forecast", forecast);                  
                            }
                            Result<Record> productoFactRecords = map.get(id_productoFact);
                            if (productoFactRecords != null){
                                acumulado = productoFactRecords
                                        .parallelStream()
                                        .mapToInt(p -> p.into(PRESUPUESTOPROD).getCantidad())
                                        .sum();
                                
                                Map <String, Result<Record>> entMap = productoFactRecords.intoGroups(ENTIDAD.NOMBRE);
                                for(EntidadRecord e : entidades){
                                    String nombreEntidad = e.getNombre();
                                    Result<Record> recordsOfEntidad = entMap.get(nombreEntidad);
                                    if (recordsOfEntidad != null){
                                        Integer cantidad = recordsOfEntidad.into(PRESUPUESTOPROD).parallelStream()
                                            .mapToInt(p -> p.into(PRESUPUESTOPROD).getCantidad())
                                            .sum();

                                        rowMap.put(StringUtils.stripAccents(nombreEntidad.toLowerCase()), cantidad);                                    
                                    }
                                    else{
                                        rowMap.put(StringUtils.stripAccents(nombreEntidad.toLowerCase()), 0);
                                    }    
                                }
                                Integer otrosCant = productoFactRecords
                                        .parallelStream()
                                        .filter( p -> !id_entidadArray.contains(p.into(ENTIDAD).getIdEntidad()))
                                        .mapToInt(e -> e.into(PRESUPUESTOPROD).getCantidad())
                                        .sum();
                                rowMap.put("otros", otrosCant);
                            }
                            else{
                                for(EntidadRecord e : entidades){
                                    rowMap.put(StringUtils.stripAccents(e.getNombre().toLowerCase()), 0);
                                }
                                rowMap.put("otros", 0);
                            }
                            
                            rowMap.put("acumulado", acumulado);
                            if(jComboMes.getSelectedIndex() > 0){
                                saldo = mensual - acumulado;
                            } else {
                                saldo = forecast - acumulado;                 
                            }
                            
                            rowMap.put("saldo", saldo > 0 ? saldo : 0);
                            Double cumplimiento;
                            if(jComboMes.getSelectedIndex() > 0)
                                cumplimiento = ( 1.0 * acumulado / mensual) * 100.0;
                            else 
                                cumplimiento = ( 1.0 * acumulado / forecast) * 100.0;
                                                     
                            NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                            nf.setMaximumFractionDigits(2);
                            rowMap.put("cumplimiento", nf.format(cumplimiento));
                            
                            modeloConsulta.addRow(Utiles.fillTableObject(modeloConsulta, rowMap));
                            this.id_productoFactArray.add(id_productoFact);
                        }
                    }else{
                        this.avisoEspera.setVisible(false);
                        JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Atención", JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                }else{
                    this.avisoEspera.setVisible(false);
                    JOptionPane.showMessageDialog(this, "No hay entidades afectadas en la zona del vendedor", "Atención", JOptionPane.INFORMATION_MESSAGE);
                }
                
            } catch (Exception e) {
                Utiles.showErrorMessage(e);
            }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jAño = new com.toedter.calendar.JYearChooser();
        jLabel7 = new javax.swing.JLabel();
        jComboVendedor = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jComboForecastGrupo = new javax.swing.JComboBox();
        jComboMes = new javax.swing.JComboBox();
        jBtnFiltrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jBtnSalir = new javax.swing.JButton();
        jButtonToExcel = new javax.swing.JButton();

        setTitle("Seguimiento Forecast");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jTablaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTablaConsulta.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTablaConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaConsultaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaConsulta);

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Año");

        jLabel7.setText("Vendedor");

        jComboVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboVendedorActionPerformed(evt);
            }
        });

        jLabel10.setText("Grupo");

        jComboForecastGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboForecastGrupoActionPerformed(evt);
            }
        });

        jComboMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- TODOS --", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        jComboMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboMesActionPerformed(evt);
            }
        });

        jBtnFiltrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/filtrar.png"))); // NOI18N
        jBtnFiltrar.setText("Filtrar");
        jBtnFiltrar.setBorderPainted(false);
        jBtnFiltrar.setContentAreaFilled(false);
        jBtnFiltrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnFiltrar.setFocusPainted(false);
        jBtnFiltrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/filtrar.png"))); // NOI18N
        jBtnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnFiltrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel7)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboForecastGrupo, 0, 338, Short.MAX_VALUE)
                    .addComponent(jComboVendedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnFiltrar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(jAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jComboForecastGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(24, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnFiltrar)
                        .addGap(2, 2, 2))))
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel22.setText("Buscar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(1, 1, 1))
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

        jButtonToExcel.setText("Exportar a Excel");
        jButtonToExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonToExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 350, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonToExcel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnSalir)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBtnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonToExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboVendedorActionPerformed
        //this.borra();
    }//GEN-LAST:event_jComboVendedorActionPerformed

    private void jComboForecastGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboForecastGrupoActionPerformed
        //this.borra();
    }//GEN-LAST:event_jComboForecastGrupoActionPerformed

    private void jBtnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltrarActionPerformed
        try{
        if (validaObligatorios().equals("")) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ForecastSeguimiento.this.avisoEspera = new AvisoEspera(null, false, AvisoEspera.CARGANDO);
                    ForecastSeguimiento.this.avisoEspera.setVisible(true);
                    Thread performer = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ForecastSeguimiento.this.consulta();                            
                            ForecastSeguimiento.this.avisoEspera.setVisible(false);
                        }
                    }, "Performer");
                    performer.start();
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, validaObligatorios(), "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }
    }//GEN-LAST:event_jBtnFiltrarActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        if (this.rsConsulta != null) {
            try {
                this.rsConsulta.close();
            } catch (Exception e) {
            }
        }
        
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jTablaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsultaMouseClicked
        try{
            if (evt.isMetaDown()) {
                int columna = jTablaConsulta.convertColumnIndexToModel(this.jTablaConsulta.getSelectedColumn());
                if (columna >= 6 && this.jTablaConsulta.getSelectedRow() >= 0) {
                    int fila = jTablaConsulta.convertRowIndexToModel(this.jTablaConsulta.getSelectedRow());
                    String entidad = modeloConsulta.getColumnName(columna);
                    String productoFact = modeloConsulta.getValueAt(fila, 0).toString() + " - " + modeloConsulta.getValueAt(fila, 1).toString();
                    String vendedor = this.jComboVendedor.getSelectedItem().toString();
                    
                    int anio = this.jAño.getYear();
                    int mes = this.jComboMes.getSelectedIndex();
                    Boolean otros = entidad.toLowerCase().equals("otros");
                    List<Record> records = filterByEntidadAndProducto(entidad, modeloConsulta.getValueAt(fila, 0).toString(), otros);
                    Integer vipCount = java.lang.Math.toIntExact(
                            records.parallelStream().filter(
                                    p -> p.into(PRESUPUESTO).getVip().equals("S")).count()
                    );

                    this.detalleForecast = new DetalleForecast(null, false, records);
                    this.detalleForecast.carga(this.empresa, entidad, productoFact, vendedor, anio, mes, records.size(), vipCount);
                    this.detalleForecast.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(this, "Seleccione una celda válida", "Atención", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }
    }//GEN-LAST:event_jTablaConsultaMouseClicked

    private void jButtonToExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonToExcelActionPerformed
        ExportHelper.tableToExcel(modeloConsulta, jTablaConsulta, false, 0, 6);
    }//GEN-LAST:event_jButtonToExcelActionPerformed

    private void jComboMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboMesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboMesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JYearChooser jAño;
    private javax.swing.JButton jBtnFiltrar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jButtonToExcel;
    private javax.swing.JComboBox jComboForecastGrupo;
    private javax.swing.JComboBox jComboMes;
    private javax.swing.JComboBox jComboVendedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablaConsulta;
    private javax.swing.JTextField jTxtBusca;
    // End of variables declaration//GEN-END:variables
}
