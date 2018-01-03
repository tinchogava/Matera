
/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.ComisionesDataSource;
import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.EspecialidadCRUD;
import ar.com.bosoft.crud.TipoOperacionCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.EspecialidadData;
import ar.com.bosoft.data.TipoOperacionData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javax.swing.table.TableRowSorter;
import matera.services.FacturaService;
import matera.services.RmService;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class Comisiones extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, escala, copias;
    String empresa, usuario, impresora, rutaArchivo;
    
    ZonaCRUD zonaCRUD;
    EspecialidadCRUD especialidadCRUD;
    TipoOperacionCRUD tipoOperacionCRUD;
    
    ArrayList zonaArray, especialidadArray, tipoOperacionArray;
    
    ResultSet rsVendedor, rsComision, rsCostoVta;
    
    DefaultTableModel modeloVendedor, modeloSeleccion;
    TableRowSorter sorterVendedor, sorterSeleccion;
    
    RoundingMode RM = RoundingMode.HALF_UP;
    BigDecimal cien = new BigDecimal(100);
    
    Reporte reporte = new Reporte();
    SeleccionImp seleccionImp = new SeleccionImp(null, true);
    
    public Comisiones(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        this.escala = 2;
        
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.especialidadCRUD = new EspecialidadCRUD(conexion, empresa);
        this.tipoOperacionCRUD = new TipoOperacionCRUD(conexion, empresa);
        
        initComponents();
        
        modeloVendedor = (DefaultTableModel) jTablaVendedor.getModel();
        jTablaVendedor.setModel(modeloVendedor);
        jTablaVendedor.setRowSorter (new TableRowSorter(modeloVendedor));
        sorterVendedor = new TableRowSorter(modeloVendedor);
        
        modeloSeleccion = (DefaultTableModel) jTablaSeleccion.getModel();
        jTablaSeleccion.setModel(modeloSeleccion);
        jTablaSeleccion.setRowSorter (new TableRowSorter(modeloSeleccion));
        sorterSeleccion = new TableRowSorter(modeloSeleccion);
        
        llenaComboZona();
        llenaComboEspecialidad();
        llenaComboTipoOperacion();
        limpia();
        zonaUsuario();
        consultaVendedores();
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
        this.jComboZona.addItem("-- TODAS --");
        this.zonaArray = zonaCRUD.consulta(true);
        Iterator i = zonaArray.iterator();
        while (i.hasNext()){
            ZonaData z = (ZonaData) i.next();
            this.jComboZona.addItem(z.getNombre());
        }
    }
    
    private void llenaComboEspecialidad(){
        this.jComboEspecialidad.addItem("-- TODAS --");
        this.especialidadArray = this.especialidadCRUD.consulta(true);
        Iterator i = especialidadArray.iterator();
        while (i.hasNext()) {
            EspecialidadData tmp = (EspecialidadData) i.next();
            this.jComboEspecialidad.addItem(tmp.getNombre());
        }
    }
    
    private void llenaComboTipoOperacion(){
        this.jComboTipoOperacion.addItem("-- TODAS --");
        this.tipoOperacionArray = tipoOperacionCRUD.consulta();
        Iterator i = tipoOperacionArray.iterator();
        while (i.hasNext()){
            TipoOperacionData t = (TipoOperacionData) i.next();
            this.jComboTipoOperacion.addItem(t.getNombre());
        }
    }
    
    private void limpia(){
        this.jDesde.setDate(null);
        this.jHasta.setDate(null);
        
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedIndex(0);
        }
        
        this.jComboTipoOperacion.setSelectedIndex(0);
        
        this.jTxtBusca.setText("");
        
        modeloSeleccion.getDataVector().removeAllElements();
        modeloSeleccion.fireTableDataChanged();
        
        this.jRadioDetallado.setSelected(true);
        this.jRadioDetalladoActionPerformed(null);
    }
    
    private void consultaVendedores(){
        try {
            modeloVendedor.getDataVector().removeAllElements();
            modeloVendedor.fireTableDataChanged();
            
            int id_zona = 0;
            if (this.jComboZona.getSelectedIndex() > 0) {
                ZonaData z = (ZonaData) this.zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
                id_zona = z.getId_zona();
            }
            
            int id_espec = 0;
            if (this.jComboEspecialidad.getSelectedIndex() > 0) {
                EspecialidadData e = (EspecialidadData) this.especialidadArray.get(this.jComboEspecialidad.getSelectedIndex() - 1);
                id_espec = e.getId_especialidad();
            }
            
            ArrayList parametros = new ArrayList();
            parametros.add(id_zona);
            parametros.add(id_espec);
            parametros.add(this.id_empresa);
            
            rsVendedor = conexion.procAlmacenado("consultaVendedorComision", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsVendedor.beforeFirst();
            int indice = 0;
            while(rsVendedor.next()){
                String nombre = rsVendedor.getString("nombre");
                String especialidad = rsVendedor.getString("especialidad");
                String zona = rsVendedor.getString("zona");
                String beneficio = rsVendedor.getString("beneficio").equals("S") ? "SI" : "NO";
                int id_vendedor = rsVendedor.getInt("id_vendedor");
                Double comision = rsVendedor.getDouble("comision");
                int id_especialidad = rsVendedor.getInt("id_especialidad");
                int id_z = rsVendedor.getInt("id_zona");

                Object[] fila = {nombre, especialidad, zona, beneficio, id_vendedor, comision, id_especialidad, id_z, indice};
                modeloVendedor.addRow(fila);
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
            JTxtBuscaChanged();
        }else if (type.equals(DocumentEvent.EventType.REMOVE)){
            JTxtBuscaChanged();
        }
    }
 
    private void JTxtBuscaChanged(){
        String text = jTxtBusca.getText().trim().replaceAll("'", "´");
        if (text.length() == 0) {
          sorterVendedor.setRowFilter(null);
        } else {
          sorterVendedor.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTablaVendedor.setRowSorter(sorterVendedor);
        }
    }

    private void unoAbajo(){
        if (jTablaVendedor.getSelectedRow() >= 0){
            int fila = jTablaVendedor.convertRowIndexToModel(jTablaVendedor.getSelectedRow());
            int indice = Integer.parseInt(modeloVendedor.getValueAt(fila, 8).toString());
            if (!existe(indice)){
                String nombre = modeloVendedor.getValueAt(fila, 0).toString();
                String especialidad = modeloVendedor.getValueAt(fila, 1).toString();
                String zona = modeloVendedor.getValueAt(fila, 2).toString();
                String beneficio = modeloVendedor.getValueAt(fila, 3).toString();
                int id_vendedor = Integer.parseInt(modeloVendedor.getValueAt(fila, 4).toString());
                Double comision = Double.parseDouble(modeloVendedor.getValueAt(fila, 5).toString());
                int id_especialidad = Integer.parseInt(modeloVendedor.getValueAt(fila, 6).toString());
                int id_zona = Integer.parseInt(modeloVendedor.getValueAt(fila, 7).toString());

                Object[] nuevo = {nombre, especialidad, zona, beneficio, id_vendedor, comision, id_especialidad, id_zona, indice};
                modeloSeleccion.addRow(nuevo);
            }else{
                JOptionPane.showMessageDialog(this, "Ya se ha seleccionado este agente", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(this, "No ha seleccionado ningún agente", "Atención", JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    
    private void todosAbajo(){
        todosArriba();
        
        for (int i = 0; i < modeloVendedor.getRowCount(); i++){
            String nombre = modeloVendedor.getValueAt(i, 0).toString();
            String especialidad = modeloVendedor.getValueAt(i, 1).toString();
            String zona = modeloVendedor.getValueAt(i, 2).toString();
            String beneficio = modeloVendedor.getValueAt(i, 3).toString();
            int id_vendedor = Integer.parseInt(modeloVendedor.getValueAt(i, 4).toString());
            Double comision = Double.parseDouble(modeloVendedor.getValueAt(i, 5).toString());
            int id_especialidad = Integer.parseInt(modeloVendedor.getValueAt(i, 6).toString());
            int id_zona = Integer.parseInt(modeloVendedor.getValueAt(i, 7).toString());
            int indice = Integer.parseInt(modeloVendedor.getValueAt(i, 8).toString());
            
            Object[] nuevo = {nombre, especialidad, zona, beneficio, id_vendedor, comision, id_especialidad, id_zona, indice};
            modeloSeleccion.addRow(nuevo);
        }
    }
    
    private void unoArriba(){
        if (jTablaSeleccion.getSelectedRow() >= 0){
            int fila = jTablaSeleccion.convertRowIndexToModel(jTablaSeleccion.getSelectedRow());
            modeloSeleccion.removeRow(fila);
        }else{
            JOptionPane.showMessageDialog(this, "No ha seleccionado ningún agente", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }            
    }
    
    private void todosArriba(){
        modeloSeleccion.getDataVector().removeAllElements();
        modeloSeleccion.fireTableDataChanged();
    }
    
    private boolean existe(int indice){
        for (int i = 0; i < modeloSeleccion.getRowCount(); i++){
            if (Integer.parseInt(modeloSeleccion.getValueAt(i, 7).toString()) == indice){
                return true;
            }
        }
        return false;
    }
    
    private void imprimir(int salida){
        if (modeloSeleccion.getRowCount() > 0){
            try{
                BigDecimal costoVta;
                ArrayList parametros = new ArrayList();
                
                parametros.add(2);
                parametros.add(0);
                parametros.add(0);
                parametros.add(this.id_empresa);

                rsCostoVta = conexion.procAlmacenado("traePorcentaje", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                if(rsCostoVta.first()){
                    costoVta = new BigDecimal(rsCostoVta.getDouble("valor"));
                    costoVta = costoVta.divide(cien, escala, RM);
                }else{
                    JOptionPane.showMessageDialog(this, "No se ha parametrizado el costo de venta", "Atención", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                Long desde = this.jDesde.getDate() == null ? (long) 0 : this.jDesde.getDate().getTime();
                Long hasta = this.jHasta.getDate() == null ? (long) 0 : this.jHasta.getDate().getTime();
                
                int id_tipoOp = 0;
                if (this.jComboTipoOperacion.getSelectedIndex() > 0) {
                    TipoOperacionData t = (TipoOperacionData) this.tipoOperacionArray.get(this.jComboTipoOperacion.getSelectedIndex() - 1);
                    id_tipoOp = t.getId_tipoOperacion();
                }
                
                ComisionesDataSource data = new ComisionesDataSource();
                    
                Map param = new HashMap();
                param.put("empresa", this.empresa);
                param.put("desde", this.jDesde.getDate());
                param.put("hasta", this.jHasta.getDate());
                param.put("tipoOperacion", this.jComboTipoOperacion.getSelectedItem().toString());
                param.put("zona", this.jComboZona.getSelectedItem().toString());
                
                String nombreReporte = "Comisiones" + (this.jRadioConsolidado.isSelected() ? "Consolidado" : "");
                
                for (int i = 0; i < modeloSeleccion.getRowCount(); i++){
                    parametros.clear();
                    int id_vendedor = (int) modeloSeleccion.getValueAt(i, 4);
                    int id_especialidad = (int) modeloSeleccion.getValueAt(i, 6);
                    int id_zona = (int) modeloSeleccion.getValueAt(i, 7);
                    boolean tieneBeneficio = modeloSeleccion.getValueAt(i, 3).toString().equals("SI");
                    BigDecimal comision = new BigDecimal((double) modeloSeleccion.getValueAt(i, 5));
                    comision = comision.divide(cien, 4, RM);
                    
                    String zona = modeloSeleccion.getValueAt(i, 2).toString();
                    String nombre = modeloSeleccion.getValueAt(i, 0).toString();
                    String especialidad = modeloSeleccion.getValueAt(i, 1).toString();
                    String beneficio = modeloSeleccion.getValueAt(i, 3).toString();
                    
                    parametros.add(desde);
                    parametros.add(hasta);
                    parametros.add(id_vendedor);
                    parametros.add(id_especialidad);
                    parametros.add(id_zona);
                    parametros.add(tieneBeneficio);
                    parametros.add(id_tipoOp);
                    
                    rsComision = conexion.procAlmacenado("consultaComision", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    
                    if(rsComision.first()){
                        rsComision.beforeFirst();
                        
                        while (rsComision.next()){
                            Integer id_presupuesto = rsComision.getInt("id_presupuesto");
                             
                            BigDecimal neto = new BigDecimal(rsComision.getDouble("neto"));
                            BigDecimal netoFlot = neto.abs();                         
                            BigDecimal rm = new BigDecimal(rsComision.getDouble("rmLineaFlotacion"));                            
                            BigDecimal rmPresupuesto = new BigDecimal(rsComision.getDouble("rmPresupuesto"));
                            BigDecimal lineaFlotacion = BigDecimal.ZERO;
                            int id_tipoOperacion = rsComision.getInt("id_tipoOperacion");
                            BigDecimal importeComision;
                            System.out.println("RM = " + rm);
                            if(rm.compareTo(BigDecimal.ZERO) == 0){
                                rm = rmPresupuesto;
                            } else {
                                rm = RmService.getRMbyTotal(id_presupuesto, neto.doubleValue());
                                if(rm.equals(BigDecimal.ZERO))
                                    rm = rmPresupuesto;
                            }

                            if (netoFlot.compareTo(BigDecimal.ZERO) != 0) {
                                switch (id_tipoOperacion) {
                                    case 1: //Standard
                                        lineaFlotacion = netoFlot.add(rm).setScale(escala, RM);
                                        break;
                                    case 2: //50/50
                                        BigDecimal importeCostoVta = netoFlot.multiply(costoVta).setScale(escala, RM);
                                        rm = rm.add(importeCostoVta).setScale(escala, RM);
                                        lineaFlotacion = netoFlot.add(rm).setScale(escala, RM);
                                        break;

                                    case 3: //Especial
                                        lineaFlotacion = netoFlot.add(rm).setScale(escala, RM).divide(new BigDecimal(2), escala, RM);
                                        break;
                                    case 6: //Selectiva
                                        lineaFlotacion = netoFlot.add(rm).setScale(escala, RM).divide(new BigDecimal(4), escala, RM);
                                        break;
                                }
                            }

                            lineaFlotacion = (neto.compareTo(BigDecimal.ZERO) == -1 ? lineaFlotacion.negate() : lineaFlotacion);

                            importeComision = lineaFlotacion.multiply(id_tipoOperacion == 4 ? BigDecimal.ZERO : comision).setScale(escala, RM);
                            String comparte = rsComision.getString("comparte");
                            if (comparte.equals("S")) {
                                importeComision = importeComision.divide(new BigDecimal(2), escala, RM);
                            }
                            String tipoOperacion = rsComision.getString("tipoOperacion");
                            Integer id_tipoComp = rsComision.getInt("id_tipoComp");
                            String numComp = id_tipoComp.equals(Utiles.TIPO_COMP.FACTURA_X) ? id_presupuesto.toString() :  rsComision.getString("numComp");

                            switch(id_especialidad){
                                case 9:
                                case 13:
                                    //if(Principal.TIPO_COMP.FACTURAS.contains(id_tipoComp)){
                                        if(FacturaService.validForComisiones(id_presupuesto)){
                                            ar.com.bosoft.Modelos.Comisiones nuevo = new ar.com.bosoft.Modelos.Comisiones(zona, nombre, especialidad,
                                                    beneficio, tipoOperacion, comision, neto, rm, lineaFlotacion,
                                                    importeComision, id_presupuesto, comparte, numComp);
                                            data.add(nuevo);
                                        }
                                    //}
                                    break;
                                default:
                                    ar.com.bosoft.Modelos.Comisiones nuevo = new ar.com.bosoft.Modelos.Comisiones(zona, nombre, especialidad,
                                                beneficio, tipoOperacion, comision, neto, rm, lineaFlotacion,
                                                importeComision, id_presupuesto, comparte, numComp);
                                    data.add(nuevo);
                            }
                        }
                    }       
                    if (this.jCheckUnoHoja.isSelected() && data.tieneDatos()) {
                        reporte.startReport(nombreReporte, param, data, salida, rutaArchivo, impresora, copias);
                        data = new ComisionesDataSource();
                    }
                }
                
                if (!this.jCheckUnoHoja.isSelected()) {
                    reporte.startReport(nombreReporte, param, data, salida, rutaArchivo, impresora, copias);
                }
            } catch (SQLException ex) {
                Utiles.enviaError(this.empresa,ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún agente", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }   
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoModo = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jDesde = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        jLabel2 = new javax.swing.JLabel();
        jHasta = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        jLabel3 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jComboTipoOperacion = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jComboEspecialidad = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaVendedor = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablaSeleccion = new javax.swing.JTable();
        jBtnTodosAbajo = new javax.swing.JButton();
        jBtnUnoAbajo = new javax.swing.JButton();
        jBtnUnoArriba = new javax.swing.JButton();
        jBtnTodosArriba = new javax.swing.JButton();
        jRadioDetallado = new javax.swing.JRadioButton();
        jRadioConsolidado = new javax.swing.JRadioButton();
        jCheckUnoHoja = new javax.swing.JCheckBox();

        setTitle("Comisiones");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jLabel1.setText("Desde");

        jLabel2.setText("Hasta");

        jLabel3.setText("Zona");

        jComboZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboZonaActionPerformed(evt);
            }
        });

        jLabel4.setText("Tipo de operación");

        jLabel5.setText("Especialidad");

        jComboEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboEspecialidadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboTipoOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboTipoOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jTablaVendedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Agente", "Especialidad", "Zona", "Benef.", "id_vendedor", "comision", "id_especialidad", "id_zona", "indice"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
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
        jTablaVendedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaVendedorMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaVendedor);
        if (jTablaVendedor.getColumnModel().getColumnCount() > 0) {
            jTablaVendedor.getColumnModel().getColumn(2).setMinWidth(150);
            jTablaVendedor.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTablaVendedor.getColumnModel().getColumn(2).setMaxWidth(150);
            jTablaVendedor.getColumnModel().getColumn(3).setMinWidth(50);
            jTablaVendedor.getColumnModel().getColumn(3).setPreferredWidth(50);
            jTablaVendedor.getColumnModel().getColumn(3).setMaxWidth(50);
            jTablaVendedor.getColumnModel().getColumn(4).setMinWidth(0);
            jTablaVendedor.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTablaVendedor.getColumnModel().getColumn(4).setMaxWidth(0);
            jTablaVendedor.getColumnModel().getColumn(5).setMinWidth(0);
            jTablaVendedor.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTablaVendedor.getColumnModel().getColumn(5).setMaxWidth(0);
            jTablaVendedor.getColumnModel().getColumn(6).setMinWidth(0);
            jTablaVendedor.getColumnModel().getColumn(6).setPreferredWidth(0);
            jTablaVendedor.getColumnModel().getColumn(6).setMaxWidth(0);
            jTablaVendedor.getColumnModel().getColumn(7).setMinWidth(0);
            jTablaVendedor.getColumnModel().getColumn(7).setPreferredWidth(0);
            jTablaVendedor.getColumnModel().getColumn(7).setMaxWidth(0);
            jTablaVendedor.getColumnModel().getColumn(8).setMinWidth(0);
            jTablaVendedor.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTablaVendedor.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setFocusable(false);

        jTxtBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaFocusGained(evt);
            }
        });

        jLabel6.setText("Buscar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)))
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

        jTablaSeleccion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Agente", "Especialidad", "Zona", "Benef.", "id_vendedor", "comision", "id_especialidad", "id_zona", "indice"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
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
        jTablaSeleccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaSeleccionMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTablaSeleccion);
        if (jTablaSeleccion.getColumnModel().getColumnCount() > 0) {
            jTablaSeleccion.getColumnModel().getColumn(2).setMinWidth(150);
            jTablaSeleccion.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTablaSeleccion.getColumnModel().getColumn(2).setMaxWidth(150);
            jTablaSeleccion.getColumnModel().getColumn(3).setMinWidth(50);
            jTablaSeleccion.getColumnModel().getColumn(3).setPreferredWidth(50);
            jTablaSeleccion.getColumnModel().getColumn(3).setMaxWidth(50);
            jTablaSeleccion.getColumnModel().getColumn(4).setMinWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(4).setMaxWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(5).setMinWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(5).setMaxWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(6).setMinWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(6).setPreferredWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(6).setMaxWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(7).setMinWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(7).setPreferredWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(7).setMaxWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(8).setMinWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        jBtnTodosAbajo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/todosabajo.png"))); // NOI18N
        jBtnTodosAbajo.setBorderPainted(false);
        jBtnTodosAbajo.setContentAreaFilled(false);
        jBtnTodosAbajo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnTodosAbajo.setFocusPainted(false);
        jBtnTodosAbajo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/todosabajo.png"))); // NOI18N
        jBtnTodosAbajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTodosAbajoActionPerformed(evt);
            }
        });

        jBtnUnoAbajo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/unoabajo.png"))); // NOI18N
        jBtnUnoAbajo.setBorderPainted(false);
        jBtnUnoAbajo.setContentAreaFilled(false);
        jBtnUnoAbajo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnUnoAbajo.setFocusPainted(false);
        jBtnUnoAbajo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/unoabajo.png"))); // NOI18N
        jBtnUnoAbajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnUnoAbajoActionPerformed(evt);
            }
        });

        jBtnUnoArriba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/unoarriba.png"))); // NOI18N
        jBtnUnoArriba.setBorderPainted(false);
        jBtnUnoArriba.setContentAreaFilled(false);
        jBtnUnoArriba.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnUnoArriba.setFocusPainted(false);
        jBtnUnoArriba.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/unoarriba.png"))); // NOI18N
        jBtnUnoArriba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnUnoArribaActionPerformed(evt);
            }
        });

        jBtnTodosArriba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/todosarriba.png"))); // NOI18N
        jBtnTodosArriba.setBorderPainted(false);
        jBtnTodosArriba.setContentAreaFilled(false);
        jBtnTodosArriba.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnTodosArriba.setFocusPainted(false);
        jBtnTodosArriba.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/todosarriba.png"))); // NOI18N
        jBtnTodosArriba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTodosArribaActionPerformed(evt);
            }
        });

        grupoModo.add(jRadioDetallado);
        jRadioDetallado.setSelected(true);
        jRadioDetallado.setText("Detallado");
        jRadioDetallado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioDetalladoActionPerformed(evt);
            }
        });

        grupoModo.add(jRadioConsolidado);
        jRadioConsolidado.setText("Consolidado");
        jRadioConsolidado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioConsolidadoActionPerformed(evt);
            }
        });

        jCheckUnoHoja.setText("Uno por hioja");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(jBtnTodosAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnUnoAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94)
                .addComponent(jBtnUnoArriba, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnTodosArriba, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioConsolidado)
                            .addComponent(jRadioDetallado)
                            .addComponent(jCheckUnoHoja))
                        .addGap(111, 111, 111)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnTodosArriba, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBtnTodosAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnUnoAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnUnoArriba, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnSalir)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jRadioConsolidado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioDetallado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckUnoHoja)))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTxtBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaFocusGained
        this.jTxtBusca.select(0, this.jTxtBusca.getText().length());
    }//GEN-LAST:event_jTxtBuscaFocusGained

    private void jBtnScrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScrActionPerformed
        //        if (producto()){
            imprimir(0);
            //        }
    }//GEN-LAST:event_jBtnScrActionPerformed

    private void jBtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImpActionPerformed
        //        if(producto()){
                        seleccionImp.setVisible(true);
                        if (seleccionImp.getSino()){
                                this.impresora = seleccionImp.getImpresora();
                                imprimir(1);
                            }
            //        }
    }//GEN-LAST:event_jBtnImpActionPerformed

    private void jBtnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPdfActionPerformed
        //        if (producto()){
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
            //        }
    }//GEN-LAST:event_jBtnPdfActionPerformed

    private void jBtnXlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnXlsActionPerformed
        //        if (producto()){
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
            //        }
    }//GEN-LAST:event_jBtnXlsActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jTablaVendedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaVendedorMouseClicked
        if (evt.getClickCount() == 2){
            unoAbajo();
        }
    }//GEN-LAST:event_jTablaVendedorMouseClicked

    private void jBtnTodosAbajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTodosAbajoActionPerformed
        todosAbajo();
    }//GEN-LAST:event_jBtnTodosAbajoActionPerformed

    private void jBtnUnoAbajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnUnoAbajoActionPerformed
        unoAbajo();
    }//GEN-LAST:event_jBtnUnoAbajoActionPerformed

    private void jBtnUnoArribaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnUnoArribaActionPerformed
        unoArriba();
    }//GEN-LAST:event_jBtnUnoArribaActionPerformed

    private void jBtnTodosArribaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTodosArribaActionPerformed
        todosArriba();
    }//GEN-LAST:event_jBtnTodosArribaActionPerformed

    private void jTablaSeleccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaSeleccionMouseClicked
        if (evt.getClickCount() == 2){
            unoArriba();
        }
    }//GEN-LAST:event_jTablaSeleccionMouseClicked

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        modeloSeleccion.getDataVector().removeAllElements();
        modeloSeleccion.fireTableDataChanged();
        
        consultaVendedores();
    }//GEN-LAST:event_jComboZonaActionPerformed

    private void jComboEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboEspecialidadActionPerformed
        modeloSeleccion.getDataVector().removeAllElements();
        modeloSeleccion.fireTableDataChanged();
        
        consultaVendedores();
    }//GEN-LAST:event_jComboEspecialidadActionPerformed

    private void jRadioDetalladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioDetalladoActionPerformed
        this.jCheckUnoHoja.setSelected(false);
        this.jCheckUnoHoja.setEnabled(this.jRadioDetallado.isSelected());
    }//GEN-LAST:event_jRadioDetalladoActionPerformed

    private void jRadioConsolidadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioConsolidadoActionPerformed
        this.jRadioDetalladoActionPerformed(evt);
    }//GEN-LAST:event_jRadioConsolidadoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup grupoModo;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jBtnTodosAbajo;
    private javax.swing.JButton jBtnTodosArriba;
    private javax.swing.JButton jBtnUnoAbajo;
    private javax.swing.JButton jBtnUnoArriba;
    private javax.swing.JButton jBtnXls;
    private javax.swing.JCheckBox jCheckUnoHoja;
    private javax.swing.JComboBox jComboEspecialidad;
    private javax.swing.JComboBox jComboTipoOperacion;
    private javax.swing.JComboBox jComboZona;
    private com.toedter.calendar.JDateChooser jDesde;
    private com.toedter.calendar.JDateChooser jHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioConsolidado;
    private javax.swing.JRadioButton jRadioDetallado;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTablaSeleccion;
    private javax.swing.JTable jTablaVendedor;
    private javax.swing.JTextField jTxtBusca;
    // End of variables declaration//GEN-END:variables
}
