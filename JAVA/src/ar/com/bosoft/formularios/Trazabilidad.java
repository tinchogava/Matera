/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import matera.gui.dialog.RemitoDetalle;
import ar.com.bosoft.DataSources.TrazabilidadConsumoDataSource;
import ar.com.bosoft.Modelos.TrazabilidadConsumo;
import ar.com.bosoft.RenderTablas.DateRenderer;
import ar.com.bosoft.Utilidades.DateUtil;
import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.clases.JRTableSorterDataSource;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.CodConsumoCRUD;
import ar.com.bosoft.crud.ProveedorCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.CodConsumoData;
import ar.com.bosoft.data.ProveedorData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.vistasRapidas.Turno;
import java.awt.BorderLayout;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import matera.db.filters.FilterManager;
import static matera.jooq.Tables.ENTIDAD;
import static matera.jooq.Tables.PRESTACION;
import static matera.jooq.Tables.PRESUPUESTO;
import static matera.jooq.Tables.PRODUCTO;
import static matera.jooq.Tables.PROVEEDOR;
import static matera.jooq.Tables.STOCKDETALLE;
import static matera.jooq.tables.Profesional.PROFESIONAL;
import static matera.jooq.tables.Remito.REMITO;
import static matera.jooq.tables.Tecnico.TECNICO;
import matera.jooq.tables.records.EntidadRecord;
import matera.jooq.tables.records.PrestacionRecord;
import matera.jooq.tables.records.PresupuestoRecord;
import matera.jooq.tables.records.ProductoRecord;
import matera.jooq.tables.records.ProfesionalRecord;
import matera.jooq.tables.records.RemitoRecord;
import matera.jooq.tables.records.StockdetalleRecord;
import matera.jooq.tables.records.TecnicoRecord;
import matera.workers.ActionWorker;
import net.sf.jasperreports.engine.JRDataSource;
import org.apache.commons.lang.math.NumberUtils;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class Trazabilidad extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, copias;
    String empresa, todos, impresora, rutaArchivo;
    
    ZonaCRUD zonaCRUD;
    ProveedorCRUD proveedorCRUD;
    CodConsumoCRUD codConsumoCRUD;
    ArrayList zonaArray, especialidadArray, proveedorArray, codConsumoArray;
    
    DefaultTableModel modelo;
    TableRowSorter sorter;
    TableCellRenderer tableCellRenderer;
    
    ResultSet rsConsulta;
    SeleccionImp seleccionImp = new SeleccionImp(null, true);
    Reporte reporte = new Reporte();
    
    Result<Record> records = null;
    
    /**
     * Creates new form Trazabilidad
     * @param conexion
     * @param id_empresa
     * @param empresa
     */
    public Trazabilidad(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.todos = "-- TODOS --";
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.proveedorCRUD = new ProveedorCRUD(conexion, id_empresa, empresa);
        this.codConsumoCRUD = new CodConsumoCRUD(conexion, empresa);
        
        this.zonaArray = new ArrayList();
        this.especialidadArray = new ArrayList();
        this.proveedorArray = new ArrayList();
        this.codConsumoArray = new ArrayList();
        
        initComponents();
        
        modelo = (DefaultTableModel) this.jTabla.getModel();
        this.jTabla.setModel(modelo);
        sorter = new TableRowSorter(modelo);
        jTabla.setRowSorter (sorter);
        
        tableCellRenderer = new DateRenderer();
        jTabla.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        
        llenaComboZona();
        llenaComboProveedor();
        llenaComboCodConsumo();
        
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
    
    private void llenaComboZona(){
        this.jComboZona.addItem(this.todos);
        this.zonaArray = zonaCRUD.consulta(true);
        Iterator it = this.zonaArray.iterator();
        while (it.hasNext()) {
            ZonaData tmp = (ZonaData) it.next();
            this.jComboZona.addItem(tmp.getNombre());
        }
    }

    private void llenaComboProveedor(){
        this.jComboProveedor.addItem(this.todos);
        this.proveedorArray = proveedorCRUD.consulta(true);
        Iterator it = this.proveedorArray.iterator();
        while (it.hasNext()) {
            ProveedorData tmp = (ProveedorData) it.next();
            this.jComboProveedor.addItem(tmp.getNombre());
        }
    }

    private void llenaComboCodConsumo(){
        this.jComboCodConsumo.addItem(this.todos);
        this.codConsumoArray = codConsumoCRUD.consulta(true);
        Iterator it = this.codConsumoArray.iterator();
        while (it.hasNext()) {
            CodConsumoData tmp = (CodConsumoData) it.next();
            this.jComboCodConsumo.addItem(tmp.getCodigo() + " - " + tmp.getNombre());
        }
    }

    private void limpia(){
        this.jDesde.setDate(null);
        this.jHasta.setDate(null);
        
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedItem(this.todos);
        }
        
        this.jComboProveedor.setSelectedItem(this.todos);
        this.jComboCodConsumo.setSelectedItem(this.todos);
        this.jTxtLote.setText("");
        this.jTextSerie.setText("");
        this.jDetallado.setSelected(true);
        this.jConsumo.setSelected(false);        
        this.jTxtBusca.setText("");
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
        
        this.copias = 1;
        this.impresora = "";
        this.rutaArchivo = "";
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
        try{
            if (modelo.getRowCount() > 0){
                Date desde = this.jDesde.getDate();
                Date hasta = this.jHasta.getDate();
                String zona = this.jComboZona.getSelectedItem().toString();
                String proveedor = this.jComboProveedor.getSelectedItem().toString();
                String codConsumo = this.jComboCodConsumo.getSelectedItem().toString();
                String lote = this.jTxtLote.getText().trim();
                String serie = this.jTextSerie.getText().trim();

                Map param = new HashMap();
                param.put("empresa", this.empresa);
                param.put("desde", desde);
                param.put("hasta", hasta);
                param.put("zona", zona);
                param.put("proveedor", proveedor);
                param.put("codConsumo", codConsumo);
                param.put("lote", lote);
                param.put("serie", serie);

                if (this.jDetallado.isSelected()) {
                    JRDataSource jr = new JRTableSorterDataSource(this.jTabla.getRowSorter());                
                    reporte.startReport("Trazabilidad", param, jr, salida, rutaArchivo, impresora, copias);
                } else {
                    
                    TrazabilidadConsumoDataSource data = new TrazabilidadConsumoDataSource();

                    if (records.size() > 0) {
                        Map<Integer,Result<Record>> m = records.intoGroups(STOCKDETALLE.ID_PRODUCTO);
                        for (Integer id : m.keySet()){
                            Result<Record> r = m.get(id);
                            
                            ProductoRecord producto = r.get(0).into(PRODUCTO);
                            
                            // COSTO PROMEDIO
                            BigDecimal costo = BigDecimal.ZERO;
                            for (Record rec : r){
                                costo = costo.add(rec.into(STOCKDETALLE).getCostopesos());
                            }
                            costo = costo.divide(new BigDecimal(r.size()), RoundingMode.HALF_UP);
                            
                            ar.com.bosoft.Modelos.TrazabilidadConsumo nuevo = new TrazabilidadConsumo(r.size(),
                                    costo,
                                    producto.getCodigo(),
                                    producto.getNombre(),
                                    r.get(0).into(PROVEEDOR).getNombre());
                            data.add(nuevo);                            
                        }
                    }else{
                        JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
                    }

                    reporte.startReport("TrazabilidadConsumo", param, data, salida, rutaArchivo, impresora, copias);                    
                }
                this.limpia();            
            }else{
                JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }
    }
    
    private void filtrar(){
        try {
            modelo.getDataVector().removeAllElements();
            modelo.fireTableDataChanged();
            
            records = null;
            
            Integer id_presupuesto = NumberUtils.toInt(jTextFieldTurno.getText(), 0);
            
            // Filtro por ID Presupuesto
            if (id_presupuesto > 0){
                Condition condition = PRESUPUESTO.ID_PRESUPUESTO.eq(id_presupuesto);
                records = FilterManager.getTrazabilidad(condition);                
            }
            
            else{
                
                if(jDesde.getDate() == null){
                    JOptionPane.showMessageDialog(null, "Debe colocar una fecha inicial de busqueda", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                Long desde = (jDesde.getDate() == null ? (long)0 : jDesde.getDate().getTime());
                Long hasta = (jHasta.getDate() == null ? (long)0 : jHasta.getDate().getTime());

                int id_zona = 0;
                if (!jComboZona.getSelectedItem().equals(todos)) {
                    ZonaData z = (ZonaData) zonaArray.get(jComboZona.getSelectedIndex() - 1);
                    id_zona = z.getId_zona();
                }

                int id_proveedor = 0;
                if (!jComboProveedor.getSelectedItem().equals(todos)) {
                    ProveedorData p = (ProveedorData) proveedorArray.get(jComboProveedor.getSelectedIndex() - 1);
                    id_proveedor = p.getId_proveedor();
                }

                int id_codConsumo = 0;
                if (!jComboCodConsumo.getSelectedItem().equals(todos)) {
                    CodConsumoData c = (CodConsumoData) codConsumoArray.get(jComboCodConsumo.getSelectedIndex() - 1);
                    id_codConsumo = c.getId_codConsumo();
                }

                String lote = jTxtLote.getText().trim();
                String serie = jTextSerie.getText().trim();

                Condition condition = PRESUPUESTO.ESTADO.eq("Z");
                if(desde != 0) {
                    condition = condition.and(PRESUPUESTO.FECHACIRUGIA.greaterOrEqual(new java.sql.Date(jDesde.getDate().getTime())));
                }
                if(hasta != 0) {
                    condition = condition.and(PRESUPUESTO.FECHACIRUGIA.lessOrEqual(new java.sql.Date(jHasta.getDate().getTime())));
                }
                if(id_zona != 0) {
                    condition = condition.and(PRESUPUESTO.ID_ZONA.eq(id_zona));
                }
                if (id_proveedor != 0){
                    condition = condition.and(PRODUCTO.ID_PROVEEDOR.eq(id_proveedor));
                }
                if (id_codConsumo != 0){
                    condition = condition.and(PRODUCTO.ID_CODCONSUMO.eq(id_codConsumo));
                }
                if (!lote.isEmpty()){
                    condition = condition.and(STOCKDETALLE.LOTE.eq(lote));
                }
                if (!serie.isEmpty()){
                    condition = condition.and(STOCKDETALLE.SERIE.eq(serie));
                }            

                records = FilterManager.getTrazabilidad(condition);
            }
            
            if (records.size() > 0){
                for (Record r : records){
                    PresupuestoRecord presupuesto = r.into(PRESUPUESTO);
                    RemitoRecord remito = r.into(REMITO);
                    StockdetalleRecord remitoDetalle = r.into(STOCKDETALLE);
                    EntidadRecord entidad = r.into(ENTIDAD);
                    EntidadRecord lugarCirugia = r.into(ENTIDAD.as("lugarCirugia"));
                    ProductoRecord producto = r.into(PRODUCTO);
                    PrestacionRecord prestacion = r.into(PRESTACION);
                    ProfesionalRecord profesional = r.into(PROFESIONAL);
                    TecnicoRecord tecnico = r.into(TECNICO);


                    HashMap map = new HashMap();
                    map.put("turno", presupuesto.getIdPresupuesto());
                    map.put("fecha cx.", presupuesto.getFechacirugia());
                    map.put("producto", producto.getCodigo());
                    map.put("remito", remito.getNumcomp());
                    map.put("lote", remitoDetalle.getLote());
                    map.put("serie",remitoDetalle.getSerie());
                    map.put("cant.",remitoDetalle.getCantidad());
                    map.put("profesional", profesional != null ? profesional.getNombre() : "");
                    map.put("lugar de cirugia", lugarCirugia != null ? lugarCirugia.getNombre() : "");
                    map.put("entidad", entidad  != null ? entidad.getNombre() : "");
                    map.put("paciente",presupuesto.getPaciente());
                    map.put("nombreprod",producto.getNombre());
                    map.put("prestacion", prestacion  != null ? prestacion.getNombre() : "");
                    map.put("tecnico", tecnico != null ? tecnico.getNombre() : "");
                    //map.put("observaciones",presupuesto.getObservaciones());
                    modelo.addRow(Utiles.fillTableObject(modelo, map));
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Utiles.showErrorMessage(ex);
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

        jPopupMenu = new javax.swing.JPopupMenu();
        jDetalleTurno = new javax.swing.JMenuItem();
        jDetalleProducto = new javax.swing.JMenuItem();
        jDetalleConsumido = new javax.swing.JMenuItem();
        grupo = new javax.swing.ButtonGroup();
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
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTxtLote = new javax.swing.JTextField();
        jComboZona = new javax.swing.JComboBox();
        jComboProveedor = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jComboCodConsumo = new javax.swing.JComboBox();
        jBtnFiltra = new javax.swing.JButton();
        jTextSerie = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jTextFieldTurno = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jDetallado = new javax.swing.JRadioButton();
        jConsumo = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();

        jDetalleTurno.setText("Turno");
        jDetalleTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetalleTurnoActionPerformed(evt);
            }
        });
        jPopupMenu.add(jDetalleTurno);

        jDetalleProducto.setText("Producto");
        jDetalleProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetalleProductoActionPerformed(evt);
            }
        });
        jPopupMenu.add(jDetalleProducto);

        jDetalleConsumido.setText("Consumido");
        jDetalleConsumido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetalleConsumidoActionPerformed(evt);
            }
        });
        jPopupMenu.add(jDetalleConsumido);

        setTitle("Trazabilidad");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jLabel1.setText("Desde");

        jLabel2.setText("Hasta");

        jLabel10.setText("Lote");

        jLabel11.setText("Zona");

        jLabel12.setText("Proveedor");

        jComboProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProveedorMouseClicked(evt);
            }
        });

        jLabel14.setText("Código de consumo");

        jComboCodConsumo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboCodConsumoMouseClicked(evt);
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

        jTextSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextSerieActionPerformed(evt);
            }
        });

        jLabel3.setText("Serie");

        jLabel4.setText("Fecha Cirugia:");

        jTextFieldTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTurnoActionPerformed(evt);
            }
        });

        jLabel5.setText("Buscar por Turno");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtLote, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addComponent(jBtnFiltra))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboCodConsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(jSeparator1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnFiltra)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel1)
                            .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jComboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jComboCodConsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTxtLote, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(12, 12, 12)
                                    .addComponent(jLabel3))))
                        .addContainerGap())))
        );

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setFocusable(false);

        jLabel22.setText("Buscar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca)
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

        jTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Turno", "Fecha Cx.", "Producto", "Remito", "Lote", "serie", "Cant.", "Profesional", "Lugar de cirugía", "Entidad", "Paciente", "nombreProd", "prestacion", "tecnico", "observaciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabla.setComponentPopupMenu(jPopupMenu);
        jScrollPane1.setViewportView(jTabla);
        if (jTabla.getColumnModel().getColumnCount() > 0) {
            jTabla.getColumnModel().getColumn(0).setMinWidth(75);
            jTabla.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(0).setMaxWidth(75);
            jTabla.getColumnModel().getColumn(1).setMinWidth(75);
            jTabla.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(1).setMaxWidth(75);
            jTabla.getColumnModel().getColumn(2).setMinWidth(100);
            jTabla.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTabla.getColumnModel().getColumn(2).setMaxWidth(100);
            jTabla.getColumnModel().getColumn(3).setMinWidth(80);
            jTabla.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTabla.getColumnModel().getColumn(3).setMaxWidth(80);
            jTabla.getColumnModel().getColumn(4).setMinWidth(130);
            jTabla.getColumnModel().getColumn(4).setPreferredWidth(130);
            jTabla.getColumnModel().getColumn(4).setMaxWidth(130);
            jTabla.getColumnModel().getColumn(6).setMinWidth(40);
            jTabla.getColumnModel().getColumn(6).setPreferredWidth(40);
            jTabla.getColumnModel().getColumn(6).setMaxWidth(40);
            jTabla.getColumnModel().getColumn(11).setMinWidth(0);
            jTabla.getColumnModel().getColumn(11).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(11).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(12).setMinWidth(0);
            jTabla.getColumnModel().getColumn(12).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(12).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(13).setMinWidth(0);
            jTabla.getColumnModel().getColumn(13).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(13).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(14).setMinWidth(0);
            jTabla.getColumnModel().getColumn(14).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(14).setMaxWidth(0);
        }

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

        grupo.add(jDetallado);
        jDetallado.setSelected(true);
        jDetallado.setText("Detallado");
        jDetallado.setContentAreaFilled(false);

        grupo.add(jConsumo);
        jConsumo.setText("Consumo por producto");
        jConsumo.setContentAreaFilled(false);

        jButton1.setText("Imprimir tabla");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jConsumo)
                            .addComponent(jDetallado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 326, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(277, 277, 277)
                                .addComponent(jBtnSalir))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))))
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnSalir))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jDetallado)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jConsumo))
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
    
        final JProgressBar jProgressBar = new JProgressBar();   
        JDialog dialog = new JDialog( (JFrame) null, "Buscando datos...", true);    
        final JLabel status = new JLabel("Procesando...");
        dialog.add(BorderLayout.NORTH,status);
        dialog.add(BorderLayout.CENTER, jProgressBar);
        dialog.setSize(500, 200);
        dialog.setLocationRelativeTo(null);
        dialog.pack();

        SwingWorker sw = new ActionWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                
                jProgressBar.setIndeterminate(true);
                //Base.open(ActiveDatabase.getDataSource());
                filtrar();
                dialog.setVisible(false);
                jBtnFiltra.enable();
                //Base.close();
                return null;
            }

            @Override
            public void done(){
                jProgressBar.setIndeterminate(false);
                status.setText("Successful");
                jProgressBar.setValue(100); // 100%
            }
        };
        sw.execute();
        jBtnFiltra.disable();
        dialog.setVisible(true);
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jBtnScrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScrActionPerformed
        imprimir(0);
    }//GEN-LAST:event_jBtnScrActionPerformed

    private void jBtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImpActionPerformed
        seleccionImp.setVisible(true);
        if (seleccionImp.getSino()){
            this.impresora = seleccionImp.getImpresora();
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
                this.rutaArchivo = archivoElegido.getCanonicalPath() + ".pdf";
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
                this.rutaArchivo = archivoElegido.getCanonicalPath() + ".xlsx";
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
            } catch (SQLException ex) {
                Utiles.enviaError(empresa, ex);
            }
        }

        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

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

    private void jDetalleConsumidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetalleConsumidoActionPerformed
        if (jTabla.getSelectedRow() >= 0){
            int fila = jTabla.convertRowIndexToModel(jTabla.getSelectedRow());
        
            String id = Utiles.v(Utiles.valueAt(modelo, fila, "turno"));
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaCx = DateUtil.stringToDate(Utiles.valueAt(modelo, fila, "fecha cx."));
            String fechaCirugia = Utiles.formatDate(fechaCx);
            String prestacion = Utiles.v(Utiles.valueAt(modelo, fila, "prestacion"));
            String tecnico = Utiles.v(Utiles.valueAt(modelo, fila, "tecnico"));
            String entidad = Utiles.v(Utiles.valueAt(modelo, fila, "entidad"));
            String lugarCx = Utiles.v(Utiles.valueAt(modelo, fila, "lugar de cirugia"));
            String profesional = Utiles.v(Utiles.valueAt(modelo, fila, "profesional"));
            String paciente = Utiles.v(Utiles.valueAt(modelo, fila, "paciente"));
            String observaciones = Utiles.v(Utiles.valueAt(modelo, fila, "observaciones"));
            RemitoDetalle remitoDetalle = new RemitoDetalle(null, false, Integer.parseInt(id));
            remitoDetalle.setDatos();
            remitoDetalle.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetalleConsumidoActionPerformed

    private void jDetalleProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetalleProductoActionPerformed
        if (jTabla.getSelectedRow() >= 0){
            int fila = jTabla.convertRowIndexToModel(jTabla.getSelectedRow());
        
            String codigo = Utiles.v(Utiles.valueAt(modelo, fila, "producto"));
            String nombre = Utiles.v(Utiles.valueAt(modelo, fila, "nombreProd"));
            JOptionPane.showMessageDialog(this, "Detalle: " + nombre, codigo, JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el producto a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetalleProductoActionPerformed

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

    private void jComboCodConsumoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboCodConsumoMouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Código de consumo");
            Iterator it = this.codConsumoArray.iterator();
            while (it.hasNext()) {
                CodConsumoData tmp = (CodConsumoData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboCodConsumo.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboCodConsumoMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (this.jTabla.getRowCount() > 0) {
            try {
                MessageFormat titulo = new MessageFormat("Trazabilidad   " + "(Filtro : " + this.jTxtBusca.getText().trim() + ")");
                MessageFormat pie = new MessageFormat("Página - {0}");
                
                this.jTabla.print(JTable.PrintMode.FIT_WIDTH, titulo, pie);
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "No se ha podido imprimir", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextSerieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextSerieActionPerformed

    private void jTextFieldTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTurnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTurnoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup grupo;
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jBtnXls;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboCodConsumo;
    private javax.swing.JComboBox jComboProveedor;
    private javax.swing.JComboBox jComboZona;
    private javax.swing.JRadioButton jConsumo;
    private com.toedter.calendar.JDateChooser jDesde;
    private javax.swing.JRadioButton jDetallado;
    private javax.swing.JMenuItem jDetalleConsumido;
    private javax.swing.JMenuItem jDetalleProducto;
    private javax.swing.JMenuItem jDetalleTurno;
    private com.toedter.calendar.JDateChooser jHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTabla;
    private javax.swing.JTextField jTextFieldTurno;
    private javax.swing.JTextField jTextSerie;
    private javax.swing.JTextField jTxtBusca;
    private javax.swing.JTextField jTxtLote;
    // End of variables declaration//GEN-END:variables
}
