/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.AnalisisFacturacionDataSource;
import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.clases.TableHeaderMouseListener;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.vistasRapidas.ComprobantesAsociados;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.swing.table.TableRowSorter;
import matera.cache.CacheTipoOperacion;
import matera.cache.CacheVendedor;
import matera.cache.CacheZona;
import matera.jooq.tables.records.PresupuestoRecord;
import matera.services.CostoVentasService;
import matera.services.FacturaService;
import matera.services.PresupuestoService;
import matera.services.RmService;
import org.jooq.Result;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class AnalisisFacturacionComercial extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, copias, escala;
    String empresa, impresora, nombreArchivo;
    
    ZonaCRUD zonaCRUD;
    
    ArrayList zonaArray;
    
    ResultSet rsConsulta;
    
    DefaultTableModel modeloConsulta;
    TableRowSorter sorterConsulta;
    JTableHeader header;
    TableHeaderMouseListener tableHeaderMoudseListener;
    SeleccionImp seleccionImp = new SeleccionImp(null, true);
    
    Reporte reporte = new Reporte();
    
    RoundingMode RM = RoundingMode.HALF_UP;
    
    public AnalisisFacturacionComercial(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        
        this.escala = 2;
        
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        
        initComponents();
        
        this.modeloConsulta = (DefaultTableModel) this.jTablaConsulta.getModel();
        this.jTablaConsulta.setModel(modeloConsulta);        
        jTablaConsulta.setRowSorter (new TableRowSorter(modeloConsulta));
        sorterConsulta = new TableRowSorter(modeloConsulta);
        this.header = jTablaConsulta.getTableHeader();
        this.tableHeaderMoudseListener = new TableHeaderMouseListener(jTablaConsulta);
        this.header.addMouseListener(this.tableHeaderMoudseListener);
        
        llenaComboZona();
        limpia();
        zonaUsuario();
        setJTexFieldChanged(jTxtBusca);
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
    
    private void limpia(){
        this.jDesde.setDate(null);
        this.jHasta.setDate(null);
        
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedIndex(0);
        }
        
        this.jTxtBusca.setText("");
        
        modeloConsulta.getDataVector().removeAllElements();
        modeloConsulta.fireTableDataChanged();
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
    
    private void filtra(){
        try {
            modeloConsulta.getDataVector().removeAllElements();
            modeloConsulta.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            ArrayList<Integer> notasDeCredito = new ArrayList<>();
            notasDeCredito.add(3);
            notasDeCredito.add(7);
            notasDeCredito.add(10);
            notasDeCredito.add(13);
            notasDeCredito.add(25);
            ArrayList<Integer> presupuestosPorRevisar = new ArrayList<>();
            ArrayList<Integer> presupuestosEnTabla = new ArrayList<>();
            Long desde = this.jDesde.getDate() == null ? (long) 0 : this.jDesde.getDate().getTime();
            Long hasta = this.jHasta.getDate() == null ? (long) 0 : this.jHasta.getDate().getTime();
            
            Calendar fechaCirugia = Calendar.getInstance();
            Calendar todoCosteado = Calendar.getInstance();
            Calendar currentDate = Calendar.getInstance();
            Calendar untilDate = jHasta.getCalendar();
            untilDate.add(Calendar.DAY_OF_MONTH, 1);
            Calendar sinceDate = jDesde.getCalendar();
            sinceDate.add(Calendar.DAY_OF_MONTH, -1);
            todoCosteado.set(2016, 8, 30);
            boolean putInTable;
            
            int id_zona = 0;
            if (this.jComboZona.getSelectedIndex() > 0){
                ZonaData z  = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
                id_zona = z.getId_zona();
            }
            
            int id_entidad = 0;
            
            parametros.add(desde);
            parametros.add(hasta);
            parametros.add(id_zona);
            parametros.add(id_entidad);
            parametros.add(this.id_empresa);
            rsConsulta = conexion.procAlmacenado("consultaAnalisisFacturacion", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            if (rsConsulta.first()) {
                rsConsulta.beforeFirst();

                while (rsConsulta.next()){
                    int id_presupuesto = rsConsulta.getInt("id_presupuesto");
                    String tipoOperacion = rsConsulta.getString("tipoOperacion");
                    Double aliPercIIBB = rsConsulta.getDouble("aliPercIIBB");
                    Double percIIBB = rsConsulta.getDouble("percIIBB");
                    Double iva21 = rsConsulta.getDouble("iva21");
                    Double iva105 = rsConsulta.getDouble("iva105");
                    Double neto21 = rsConsulta.getDouble("neto21");
                    Double neto105 = rsConsulta.getDouble("neto105");
                    Double netoX = rsConsulta.getDouble("netoX");
                    Double netoExento = rsConsulta.getDouble("netoExento");
                    Double iibb = rsConsulta.getDouble("iibb");
                    Double rmFacturaAntes = rsConsulta.getDouble("rm");
                    Double rm = RmService.getRMatMayor(id_presupuesto, this.jDesde.getDate(), rmFacturaAntes);
                    String zona = rsConsulta.getString("zona");
                    String vendedor = rsConsulta.getString("vendedor");
                    int id_tipoOperacion = rsConsulta.getInt("id_tipoOperacion");
                    Integer tipoComp = rsConsulta.getInt("tipoComp");
                    BigDecimal costoVentas;
                    Double costoVentasTabla = 0.00;
                    Double rmTabla = 0.00;
                    putInTable = true;
                    
                    Date result = PresupuestoService.getFechaCirugia(id_presupuesto);
                    if(result != null){
                        fechaCirugia.setTime(result);                  
                        if(fechaCirugia.before(todoCosteado)){
                            costoVentas = BigDecimal.ZERO; 
                            costoVentas.setScale(2, BigDecimal.ROUND_HALF_UP);
                            costoVentasTabla = costoVentas.doubleValue();                                
                        } else if(fechaCirugia.after(todoCosteado)){
                            if(notasDeCredito.contains(tipoComp)){
                                if(FacturaService.isFactured(id_presupuesto, sinceDate.getTime(), untilDate.getTime())){
                                    costoVentas = CostoVentasService.getCostoVentasByPresupuesto(id_presupuesto)
                                        .getCostoTotal();
                                    costoVentas.setScale(2, BigDecimal.ROUND_HALF_UP);
                                    costoVentasTabla = costoVentas.doubleValue();
                                } else
                                    putInTable = false;
                            } else if(fechaCirugia.before(untilDate)){
                                costoVentas = CostoVentasService.getCostoVentasByPresupuesto(id_presupuesto)
                                        .getCostoTotal();
                                costoVentas.setScale(2, BigDecimal.ROUND_HALF_UP);
                                costoVentasTabla = costoVentas.doubleValue();
                            }                           
                        }
                        if(fechaCirugia.before(currentDate)){
                            rmTabla = rm;                                   
                        }
                    } else {
                        costoVentas = BigDecimal.ZERO;
                        costoVentas.setScale(2, BigDecimal.ROUND_HALF_UP);
                        costoVentasTabla = costoVentas.doubleValue();      
                    }

                    BigDecimal i21 = new BigDecimal(iva21);
                    BigDecimal i105 = new BigDecimal(iva105);
                    BigDecimal n21 = new BigDecimal(neto21);
                    BigDecimal n105 = new BigDecimal(neto105);
                    BigDecimal nX = new BigDecimal(netoX);
                    BigDecimal nEx = new BigDecimal(netoExento);
                    BigDecimal p = new BigDecimal(percIIBB);
                    BigDecimal total = n21.add(i21).add(n105).add(i105).add(nX).add(nEx).add(p).setScale(escala, RM);
                    
                    if(putInTable){
                        Object[] fila = {id_presupuesto, tipoOperacion, total, percIIBB, iva21,
                                        iva105, netoExento, netoX, neto21, neto105,
                                        rmTabla, zona, aliPercIIBB, iibb, id_tipoOperacion, costoVentasTabla, vendedor};

                        modeloConsulta.addRow(fila);
                        presupuestosEnTabla.add(id_presupuesto);
                    } else {
                        presupuestosPorRevisar.add(id_presupuesto);
                    }
                }
                
            }else{
                JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            
            Result<PresupuestoRecord> result = PresupuestoService.
                    getPresupuestosByFechaCirugia(sinceDate, untilDate, presupuestosPorRevisar);
            
            result.stream().forEach((presupuestoRecord) -> {
                String tipoOperacion;
                String zona;
                String vendedor;
                Integer id_tipoOperacion;
                          
                if (FacturaService.facturacionPrevia(presupuestoRecord.getIdPresupuesto(), 
                        new java.sql.Date(hasta)).compareTo(BigDecimal.ZERO) > 0) {
                    try {   
                        tipoOperacion = CacheTipoOperacion.instance()
                                .getTipoOperacion(presupuestoRecord.getIdTipooperacion()).getNombre();
                    } catch(Exception e) { 
                        tipoOperacion = "";
                    }
                    try {
                        zona = CacheZona.instance().getZona(presupuestoRecord.getIdZona()).getNombre();
                    } catch(Exception e) {     
                        zona = "";    
                    }
                    try {
                        vendedor = CacheVendedor.instance().getVendedor(presupuestoRecord.getIdVendedor()).getNombre();                        
                    } catch(Exception e) {
                        vendedor = "";
                    }
                    
                    try {     
                        id_tipoOperacion = presupuestoRecord.getIdTipooperacion();
                    
                    } catch(Exception e) {                         
                        id_tipoOperacion = 0;
                    }
                    
                    BigDecimal costoVentas;
                    Double costoVentasTabla = 0.00;
                    costoVentas = CostoVentasService
                            .getCostoVentasByPresupuesto(presupuestoRecord.getIdPresupuesto()).getCostoTotal();
                    costoVentas.setScale(2, BigDecimal.ROUND_HALF_UP);
                    costoVentasTabla = costoVentas.doubleValue();         
         
                    Object[] fila = {presupuestoRecord.getIdPresupuesto(), tipoOperacion, 0, 0, 0,
                        0, 0, 0, 0, 0,
                        0, zona, 0, 0, id_tipoOperacion, costoVentasTabla, vendedor};
                    modeloConsulta.addRow(fila);        
                }
            });
            
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
          sorterConsulta.setRowFilter(null);
        } else {
          sorterConsulta.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*",this.tableHeaderMoudseListener.getColumna()));
          jTablaConsulta.setRowSorter(sorterConsulta);
        }
    }
    
    private void imprimir(int salida){
        if (modeloConsulta.getRowCount() > 0){
            try {
                BigDecimal costoVta;
                ArrayList parametros = new ArrayList();
                
                parametros.add(2);
                parametros.add(0);
                parametros.add(0);
                parametros.add(this.id_empresa);
                
                rsConsulta = conexion.procAlmacenado("traePorcentaje", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                
                if(rsConsulta.first()){
                    costoVta = new BigDecimal(rsConsulta.getDouble("valor"));
                    costoVta = costoVta.divide(new BigDecimal(100), escala, RM);
                }else{
                    JOptionPane.showMessageDialog(this, "No se ha parametrizado el costo de venta", "Atención", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                Date desde = this.jDesde.getDate();
                Date hasta = this.jHasta.getDate();
                String zona = this.jComboZona.getSelectedItem().toString();
                
                Map param = new HashMap();
                param.put("empresa", this.empresa);
                param.put("desde", desde);
                param.put("hasta", hasta);
                param.put("zona", zona);
                
                AnalisisFacturacionDataSource data = new AnalisisFacturacionDataSource();
                
                for (int i = 0; i < modeloConsulta.getRowCount(); i++){
                    String z = modeloConsulta.getValueAt(i, 11).toString();
                    
                    int id_presupuesto = (int) modeloConsulta.getValueAt(i, 0);
                    String tipoOperacion = modeloConsulta.getValueAt(i, 1).toString();
                    BigDecimal total = new BigDecimal(Double.parseDouble(modeloConsulta.getValueAt(i, 2).toString()));
                    BigDecimal aliPercIIBB = new BigDecimal(Double.parseDouble(modeloConsulta.getValueAt(i, 12).toString()));
                    BigDecimal percIIBB = new BigDecimal(Double.parseDouble(modeloConsulta.getValueAt(i, 3).toString()));
                    BigDecimal iva21 = new BigDecimal(Double.parseDouble(modeloConsulta.getValueAt(i, 4).toString()));
                    BigDecimal iva105 = new BigDecimal(Double.parseDouble(modeloConsulta.getValueAt(i, 5).toString()));
                    BigDecimal netoExento = new BigDecimal(Double.parseDouble(modeloConsulta.getValueAt(i, 6).toString()));
                    BigDecimal netoX = new BigDecimal(Double.parseDouble(modeloConsulta.getValueAt(i, 7).toString()));
                    BigDecimal neto21 = new BigDecimal(Double.parseDouble(modeloConsulta.getValueAt(i, 8).toString()));
                    BigDecimal neto105 = new BigDecimal(Double.parseDouble(modeloConsulta.getValueAt(i, 9).toString()));
                    BigDecimal iibb = new BigDecimal(Double.parseDouble(modeloConsulta.getValueAt(i, 13).toString()));
                    BigDecimal rm = new BigDecimal(Double.parseDouble(modeloConsulta.getValueAt(i, 10).toString()));
                    BigDecimal costoVentas = new BigDecimal(Double.parseDouble(modeloConsulta.getValueAt(i, 15).toString()));
                    String vendedor = modeloConsulta.getValueAt(i, 16).toString();
                    BigDecimal divisor = BigDecimal.ONE.add(new BigDecimal(21).divide(new BigDecimal(100), escala, RM));
                    BigDecimal netoFlot = total.divide(divisor, escala, RM).abs();
                    
                    int id_tipoOperacion = (int) modeloConsulta.getValueAt(i, 14);
                    BigDecimal importeFlotacion = new BigDecimal(0);
                    
                    if (total.compareTo(BigDecimal.ZERO) != 0) {
                        switch (id_tipoOperacion) {
                            case 1: //Standard
                                importeFlotacion = netoFlot.add(rm).setScale(escala, RM);
                                break;
                            case 2: //50/50
                                BigDecimal importeCostoVta = netoFlot.multiply(costoVta).setScale(escala, RM);
                                // El rm es negativo, al sumarle el costo de venta se le reduce el impacto (magia de analisis de facturacion)
                                rm = rm.add(importeCostoVta).setScale(escala, RM);
                                // Solo restar RM si es menor a 0
                                if (rm.compareTo(BigDecimal.ZERO) <= 0){
                                     importeFlotacion = netoFlot.add(rm).setScale(escala, RM);
                                }
                                else{
                                    rm = BigDecimal.ZERO;
                                    importeFlotacion = netoFlot;                                
                                }
                                break;

                            case 3: //Especial
                                importeFlotacion = netoFlot.add(rm).setScale(escala, RM).divide(new BigDecimal(2), escala, RM);
                                break;
                            case 6: //Selectiva
                                importeFlotacion = netoFlot.add(rm).setScale(escala, RM).divide(new BigDecimal(4), escala, RM);
                                break;
                        }
                    }
                    
                    importeFlotacion = (total.compareTo(BigDecimal.ZERO) == -1 ? importeFlotacion.negate() : importeFlotacion);
                    
                    ar.com.bosoft.Modelos.AnalisisFacturacion nuevo =
                            new ar.com.bosoft.Modelos.AnalisisFacturacion(z, vendedor, tipoOperacion, id_presupuesto, total, aliPercIIBB, 
                                    percIIBB, iva21, iva105, netoExento, netoX, neto21, neto105, iibb, rm, importeFlotacion, 
                                    costoVentas);
                    
                    data.add(nuevo);
                    
                }
                
                reporte.startReport("AnalisisFacturacion", param, data, salida, nombreArchivo, impresora, copias);
                
                this.limpia();
            } catch (SQLException ex) {
                Utiles.enviaError(empresa, ex);
            }
            
        }else{
            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupTipo = new javax.swing.ButtonGroup();
        buttonGroupModo = new javax.swing.ButtonGroup();
        jPopupMenu = new javax.swing.JPopupMenu();
        jDetallePresupuesto = new javax.swing.JMenuItem();
        jComprobantesAsociados = new javax.swing.JMenuItem();
        jPanel3 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
        jBtnSalir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jBtnFiltra = new javax.swing.JButton();
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
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();

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

        setTitle("Analisis de facturación - Comercial");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setFocusable(false);

        jLabel22.setText("Buscar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 793, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)))
        );

        jTablaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Turno", "Tipo Op.", "$ Total", "Perc. IIBB", "IVA 21%", "IVA 10.5%", "Neto Exento", "Neto X", "Neto 21%", "Neto 10.5%", "RM", "zona", "aliPercIIBB", "iibb", "id_tipoOperacion", "Costo de Ventas", "vendedor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaConsulta.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTablaConsulta.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTablaConsulta);
        if (jTablaConsulta.getColumnModel().getColumnCount() > 0) {
            jTablaConsulta.getColumnModel().getColumn(0).setMinWidth(50);
            jTablaConsulta.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTablaConsulta.getColumnModel().getColumn(0).setMaxWidth(50);
            jTablaConsulta.getColumnModel().getColumn(1).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(1).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(11).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(11).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(11).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(12).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(12).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(12).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(13).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(13).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(13).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(14).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(14).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(14).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(16).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(16).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(16).setMaxWidth(0);
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

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Zona");

        jBtnFiltra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/filtrar.png"))); // NOI18N
        jBtnFiltra.setText("Filtrar");
        jBtnFiltra.setBorder(null);
        jBtnFiltra.setBorderPainted(false);
        jBtnFiltra.setContentAreaFilled(false);
        jBtnFiltra.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnFiltra.setFocusPainted(false);
        jBtnFiltra.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/filtrar.png"))); // NOI18N
        jBtnFiltra.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/pantalla.png"))); // NOI18N
        jBtnFiltra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnFiltraActionPerformed(evt);
            }
        });

        jLabel1.setText("Desde");

        jLabel2.setText("Hasta");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jBtnFiltra)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnFiltra)
                .addContainerGap())
        );

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 853, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(238, 238, 238)
                        .addComponent(jBtnSalir))))
            .addGroup(layout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 15, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnScrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScrActionPerformed
        imprimir(0);
    }//GEN-LAST:event_jBtnScrActionPerformed

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
        try{
            if (rsConsulta != null){
                rsConsulta.close();
            }

            this.dispose();
        }catch (Exception ex){
            Utiles.enviaError(this.empresa,ex);
        }
        
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        filtra();
        jTablaConsulta.getRowSorter().toggleSortOrder(0);
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jBtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImpActionPerformed
        seleccionImp.setVisible(true);
        if (seleccionImp.getSino()){
            this.impresora = seleccionImp.getImpresora();
            this.copias = seleccionImp.getCopias();
            imprimir(1);
        }
    }//GEN-LAST:event_jBtnImpActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupModo;
    private javax.swing.ButtonGroup buttonGroupTipo;
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jBtnXls;
    private javax.swing.JComboBox jComboZona;
    private javax.swing.JMenuItem jComprobantesAsociados;
    private com.toedter.calendar.JDateChooser jDesde;
    private javax.swing.JMenuItem jDetallePresupuesto;
    private com.toedter.calendar.JDateChooser jHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTablaConsulta;
    private javax.swing.JTextField jTxtBusca;
    // End of variables declaration//GEN-END:variables
}
