/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.PresupuestoProdDataSource;
import ar.com.bosoft.Modelos.PresupuestoProd;
import ar.com.bosoft.RenderTablas.AgendaRenderer;
import ar.com.bosoft.RenderTablas.AprobacionRenderer;
import ar.com.bosoft.RenderTablas.FilasResaltadas;
import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.buscadores.General1;
import ar.com.bosoft.clases.TableHeaderMouseListener;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.EntidadCRUD;
import ar.com.bosoft.crud.PrestacionCRUD;
import ar.com.bosoft.crud.ProfesionalCRUD;
import ar.com.bosoft.crud.TecnicoCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.EntidadData;
import ar.com.bosoft.data.PrestacionData;
import ar.com.bosoft.data.ProfesionalData;
import ar.com.bosoft.data.TecnicoData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.vistasRapidas.EntidadVista;
import ar.com.bosoft.vistasRapidas.Motivo;
import ar.com.bosoft.vistasRapidas.ProfesionalVista;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import matera.helpers.LogHelper;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class ConfirmaTurno extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa;
    String empresa, usuario;
    DefaultTableModel modeloConsulta, modeloProducto, modeloCaja, modeloCajaSeleccion;
    TableRowSorter sorterConsulta, sorterProducto, sorterCaja, sorterCajaSeleccion;
    JTableHeader header;
    TableHeaderMouseListener tableHeaderMoudseListener;
    TableCellRenderer tableCellRenderer, agendaCellRenderer, aprobacionCellRenderer;
    ZonaCRUD zonaCRUD;
    EntidadCRUD entidadCRUD;
    ProfesionalCRUD profesionalCRUD;
    PrestacionCRUD prestacionCRUD;
    TecnicoCRUD tecnicoCRUD;
    ArrayList zonaArray, lugarArray, entidadArray, profesionalArray, prestacionArray, tecnicoArray;
    
    ResultSet rsHoy, rsConsulta, rsProducto, rsCajaPrestada, rsCaja, rsMapa, rsTraeTurno, rsTraePresupuestoProd;
    
    SimpleDateFormat aprobacionFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
    private final SimpleDateFormat sdfNewValue = new SimpleDateFormat("dd/MM/yyyy");
    
    
    //Vistas
    EntidadVista entidadVista;
    ProfesionalVista profesionalVista;
    
    //Buscador
    General1 general1;
    
    Salida salida;
    
    FilasResaltadas turnosHoyNoFechaAgenda, cajasPrestadas;
    
    Motivo motivo;
    
    public ConfirmaTurno(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.entidadCRUD = new EntidadCRUD(conexion, id_empresa, empresa);
        this.profesionalCRUD = new ProfesionalCRUD(conexion, id_empresa, empresa);
        this.prestacionCRUD = new PrestacionCRUD(conexion, id_empresa, empresa);
        this.tecnicoCRUD = new TecnicoCRUD(conexion, id_empresa, empresa);
        
        this.entidadVista = new EntidadVista(conexion, id_empresa, empresa, null, true);
        this.profesionalVista = new ProfesionalVista(conexion, id_empresa, empresa, null, true);
        
        this.general1 = new General1(null, true);
        
        salida = new Salida(null, true);
        
        this.turnosHoyNoFechaAgenda = new FilasResaltadas();
        this.turnosHoyNoFechaAgenda.setFondo(Color.red);
        this.turnosHoyNoFechaAgenda.setFuente(Color.white);
        this.turnosHoyNoFechaAgenda.setColumnaVerificadora(9);
        
        this.cajasPrestadas = new FilasResaltadas();
        this.cajasPrestadas.setFondo(Color.CYAN);
        this.cajasPrestadas.setFuente(Color.BLACK);
        this.cajasPrestadas.setColumnaVerificadora(4);
        
        initComponents();
        
        llenaComboZona();
        llenaComboLugar();
        llenaComboEntidad();
        llenaComboProfesional();
        llenaComboPrestacion();
        llenaComboTecnico();

        //this.jTablaConsulta.setDefaultRenderer(Object.class, turnosHoyNoFechaAgenda);
        modeloConsulta = (DefaultTableModel) jTablaConsulta.getModel();
        jTablaConsulta.setModel(modeloConsulta);
        sorterConsulta = new TableRowSorter(modeloConsulta);
        jTablaConsulta.setRowSorter (sorterConsulta);
        
        agendaCellRenderer = new AgendaRenderer();
        aprobacionCellRenderer = new AprobacionRenderer();
        jTablaConsulta.getColumnModel().getColumn(5).setCellRenderer(agendaCellRenderer);
        jTablaConsulta.getColumnModel().getColumn(10).setCellRenderer(aprobacionCellRenderer);
        //jTablaConsulta.setDefaultRenderer(Object.class, new ConfirmaTurnoRenderer());
        this.header = jTablaConsulta.getTableHeader();
        this.tableHeaderMoudseListener = new TableHeaderMouseListener(jTablaConsulta);
        this.header.addMouseListener(this.tableHeaderMoudseListener);
        
        modeloProducto = (DefaultTableModel) jTablaProducto.getModel();
        jTablaProducto.setModel(modeloProducto);
        sorterProducto = new TableRowSorter(modeloProducto);
        jTablaProducto.setRowSorter (sorterProducto);
        
        
        this.jTablaCaja.setDefaultRenderer(Object.class, cajasPrestadas);
        modeloCaja = (DefaultTableModel) jTablaCaja.getModel();
        jTablaCaja.setModel(modeloCaja);
        jTablaCaja.setRowSorter (new TableRowSorter(modeloCaja));
        sorterCaja = new TableRowSorter(modeloCaja);
        
        this.jTablaCajaSeleccion.setDefaultRenderer(Object.class, cajasPrestadas);
        modeloCajaSeleccion = (DefaultTableModel) jTablaCajaSeleccion.getModel();
        jTablaCajaSeleccion.setModel(modeloCajaSeleccion);
        jTablaCajaSeleccion.setRowSorter (new TableRowSorter(modeloCajaSeleccion));
        sorterCajaSeleccion = new TableRowSorter(modeloCajaSeleccion);
        
        limpia();
        zonaUsuario();
        consultaHoy();
        consulta();
        llenaTabla();
        programaBuscaConsulta(jTxtBuscaConsulta);
        programaBuscaCaja(jTxtBuscaCaja);
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
        this.zonaArray = zonaCRUD.consulta(true);
        Iterator i = zonaArray.iterator();
        while (i.hasNext()){
            ZonaData tmp = (ZonaData) i.next();
            this.jComboZona.addItem(tmp.getNombre());
        }
    }
    
    private void llenaComboLugar(){
        this.lugarArray = entidadCRUD.consulta(0, true);
        Iterator i = lugarArray.iterator();
        while (i.hasNext()){
            EntidadData tmp = (EntidadData) i.next();
            this.jComboLugar.addItem(tmp.getNombre());
        }
    }
    
    private void llenaComboEntidad(){
        this.entidadArray = entidadCRUD.consulta(0, true);
        Iterator i = entidadArray.iterator();
        while (i.hasNext()){
            EntidadData tmp = (EntidadData) i.next();
            this.jComboEntidad.addItem(tmp.getNombre());
        }
    }
    
    private void llenaComboProfesional(){
        this.profesionalArray = profesionalCRUD.consulta(0, true);
        Iterator i = profesionalArray.iterator();
        while (i.hasNext()){
            ProfesionalData tmp = (ProfesionalData) i.next();
            this.jComboProfesional.addItem(tmp.getNombre());
        }
    }
    
    private void llenaComboPrestacion(){
        this.prestacionArray = prestacionCRUD.consulta(true);
        Iterator i = prestacionArray.iterator();
        while (i.hasNext()){
            PrestacionData tmp = (PrestacionData) i.next();
            this.jComboPrestacion.addItem(tmp.getNombre());
        }
    }
    
    private void llenaComboTecnico(){
        this.tecnicoArray = tecnicoCRUD.consulta(true);
        Iterator i = tecnicoArray.iterator();
        while (i.hasNext()){
            TecnicoData tmp = (TecnicoData) i.next();
            this.jComboTecnico.addItem(tmp.getNombre());
        }
    }
    
    private void limpia(){
        limpiaFiltros();
        limpiaCabecera();
        limpiaProductos();
        limpiaCajas();
    }
    
    private void consulta(){
        try {
            String muestra = this.jComboMuestra.getSelectedItem().toString();
            Date desde = null;
            Date hasta = new Date();
            int tipoBusqueda = 1;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(hasta);
            switch(muestra){
                case "30 días":
                    calendar.add(Calendar.DAY_OF_YEAR, -30);
                    desde = calendar.getTime();
                    break;
                    
                case "60 días":
                    calendar.add(Calendar.DAY_OF_YEAR, -60);
                    desde = calendar.getTime();
                    break;
                    
                case "90 días":
                    calendar.add(Calendar.DAY_OF_YEAR, -90);
                    desde = calendar.getTime();
                    break;
                    
                case "120 días":
                    calendar.add(Calendar.DAY_OF_YEAR, -120);
                    desde = calendar.getTime();
                    break;
                    
                case "150 días":
                    calendar.add(Calendar.DAY_OF_YEAR, -150);
                    desde = calendar.getTime();
                    break;
                    
                case "180 días":
                    calendar.add(Calendar.DAY_OF_YEAR, -180);
                    desde = calendar.getTime();
                    break;
                    
                case "Todos":
                    hasta = null;
                    break;
                    
                case "Personalizado":
                    tipoBusqueda = 2;
                    desde = this.jDesde.getDate();
                    hasta = this.jHasta.getDate();
                    break;
            }
            
            int id_zona = 0;
            if (this.jComboZona.getSelectedIndex() >= 0){
                ZonaData tmp = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex());
                id_zona = tmp.getId_zona();
            }
            
            ArrayList parametros = new ArrayList();
            parametros.add(desde == null ? (long) 0 : desde.getTime());
            parametros.add(hasta == null ? (long) 0 : hasta.getTime());
            parametros.add(id_zona);
            parametros.add(this.id_empresa);
            parametros.add(tipoBusqueda);
            
            if(jRadioFechaAprobacion.isSelected()){
                rsConsulta = conexion.procAlmacenado("consultaConfirmaTurnoByFechaAprobacion", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            } else {
                rsConsulta = conexion.procAlmacenado("consultaConfirmaTurno", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());             
            }
                   
        } catch (Exception ex) {
            Utiles.enviaError(this.empresa,ex);
        }        
    }
    
    private void consultaHoy(){
        try {
            int id_zona = 0;
            if (this.jComboZona.getSelectedIndex() >= 0){
                ZonaData tmp = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex());
                id_zona = tmp.getId_zona();
            }
            
            ArrayList parametros = new ArrayList();
            parametros.add(id_zona);
            parametros.add(this.id_empresa);
            
            rsHoy = conexion.procAlmacenado("consultaConfirmaTurnoHoy", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        } catch (Exception ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    private void llenaTabla(){
        try {
            modeloConsulta.getDataVector().removeAllElements();
            modeloConsulta.fireTableDataChanged();
            
            consultaHoy();
            int id_presupuesto, id_zona;
            String entidad, lugar, profesional, paciente, telefono, observaciones, resalta;
            Date  fechaAgenda;
            Timestamp fechaAprobacion;
            resalta = "SI";
            rsHoy.beforeFirst();
            while (rsHoy.next()){
                id_presupuesto = rsHoy.getInt("id_presupuesto");
                entidad = rsHoy.getString("entidad");
                lugar = rsHoy.getString("lugar");
                profesional = rsHoy.getString("profesional");
                paciente = rsHoy.getString("paciente");
                fechaAgenda = rsHoy.getDate("fechaAgenda");
                fechaAprobacion = rsHoy.getTimestamp("fechaAprobacion");
                telefono = rsHoy.getString("telefono");
                observaciones = rsHoy.getString("observaciones");
                id_zona = rsHoy.getInt("id_zona");
                
                Object[] fila = {id_presupuesto, entidad, lugar, profesional, paciente, 
                                    fechaAgenda, telefono, observaciones, id_zona, resalta, fechaAprobacion};
                
                modeloConsulta.addRow(fila);
            }
            
            if (!this.jComboMuestra.getSelectedItem().toString().equals("Hoy")) {
                consulta();
            
                resalta = "NO";
                rsConsulta.beforeFirst();
                while (rsConsulta.next()) {
                    id_presupuesto = rsConsulta.getInt("id_presupuesto");
                    entidad = rsConsulta.getString("entidad");
                    lugar = rsConsulta.getString("lugar");
                    profesional = rsConsulta.getString("profesional");
                    paciente = rsConsulta.getString("paciente");
                    fechaAgenda = rsConsulta.getDate("fechaAgenda");
                    fechaAprobacion = rsConsulta.getTimestamp("fechaAprobacion");
                    telefono = rsConsulta.getString("telefono");
                    observaciones = rsConsulta.getString("observaciones");
                    id_zona = rsConsulta.getInt("id_zona");

                    Object[] fila = {id_presupuesto, entidad, lugar, profesional, paciente, 
                                        fechaAgenda, telefono, observaciones, id_zona, resalta, fechaAprobacion};

                    modeloConsulta.addRow(fila);
                
                }
                jLabelNumRows.setText(String.valueOf(modeloConsulta.getRowCount()));
            }
            
            
        } catch (Exception ex) {
            Utiles.enviaError(this.empresa, ex);
        }
    }
    
    private void programaBuscaConsulta(JTextField txt){
        DocumentListener documentListener = new DocumentListener() { 
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                printItConsulta(documentEvent);
            }
 
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                printItConsulta(documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                printItConsulta(documentEvent);
            }
        };
        txt.getDocument().addDocumentListener(documentListener); 
    }
 
    private void printItConsulta(DocumentEvent documentEvent) {
        DocumentEvent.EventType type = documentEvent.getType();
 
        if (type.equals(DocumentEvent.EventType.INSERT) || type.equals(DocumentEvent.EventType.REMOVE)){
            JTxtBuscaConsultaChanged();
        }
    }
 
    private void JTxtBuscaConsultaChanged(){
        String text = jTxtBuscaConsulta.getText().trim().replaceAll("'", "´");
        if (text.length() == 0) {
          sorterConsulta.setRowFilter(null);
        } else {
          sorterConsulta.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*",this.tableHeaderMoudseListener.getColumna()));
          jTablaConsulta.setRowSorter(sorterConsulta);
        }
    }
    
    private void programaBuscaCaja(JTextField txt){
        DocumentListener documentListener = new DocumentListener() { 
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                printItCaja(documentEvent);
            }
 
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                printItCaja(documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                printItCaja(documentEvent);
            }
        };
        txt.getDocument().addDocumentListener(documentListener); 
    }
 
    private void printItCaja(DocumentEvent documentEvent) {
        DocumentEvent.EventType type = documentEvent.getType();
 
        if (type.equals(DocumentEvent.EventType.INSERT) || type.equals(DocumentEvent.EventType.REMOVE)){
            JTxtBuscaCajaChanged();
        }
    }
 
    private void JTxtBuscaCajaChanged(){
        String text = jTxtBuscaCaja.getText().trim().replaceAll("'", "´");
        if (text.length() == 0) {
          sorterCaja.setRowFilter(null);
        } else {
          sorterCaja.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTablaCaja.setRowSorter(sorterCaja);
        }
    }
    
    private void muestra(boolean enabled){
        this.jDesde.setEnabled(enabled);
        this.jHasta.setEnabled(enabled);
        this.jDesde.setDate(null);
        this.jHasta.setDate(null);
        this.jRadioFechaAgenda.setEnabled(enabled);
        this.jRadioFechaAprobacion.setEnabled(enabled);
        this.jRadioFechaAgenda.setSelected(enabled);
    }
    
    private void limpiaFiltros(){
        this.jComboMuestra.setSelectedItem("180 días");
        muestra(false);
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedIndex(-1);
        }
        
        this.jTxtBuscaConsulta.setText("");
    }
    
    private void limpiaCabecera(){
        this.jComboLugar.setSelectedIndex(-1);
        this.jComboEntidad.setSelectedIndex(-1);
        this.jComboProfesional.setSelectedIndex(-1);
        this.jTxtPaciente.setText("");
        this.jLbltelefono.setText("");
        this.jComboPrestacion.setSelectedIndex(-1);
        this.jComboTecnico.setSelectedIndex(-1);
        this.jFechaCirugia.setDate(null);
        this.jBtnId_presupuesto.setText("");
        this.jTxtObservaciones.setText("");
    }
    
    private void limpiaProductos(){
        modeloProducto.getDataVector().removeAllElements();
        modeloProducto.fireTableDataChanged();
    }
    
    private void limpiaCajas(){
        this.jTxtBuscaCaja.setText("");
        modeloCaja.getDataVector().removeAllElements();
        modeloCaja.fireTableDataChanged();
        modeloCajaSeleccion.getDataVector().removeAllElements();
        modeloCajaSeleccion.fireTableDataChanged();
    }
    
    private void llenaProductos(){
        limpiaProductos();
        try {
            ArrayList parametros = new ArrayList();
            parametros.add(Integer.parseInt(this.jBtnId_presupuesto.getText()));
            
            rsProducto = conexion.procAlmacenado("traePresupuestoProd", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsProducto.beforeFirst();
            while (rsProducto.next()){
                Double cantidad = rsProducto.getDouble("cantidad");
                String codigo = rsProducto.getString("codigo");
                String producto = rsProducto.getString("producto");
                String observaciones = rsProducto.getString("observaciones");
                String proveedor = rsProducto.getString("proveedor");
                
                Object[] fila = {cantidad, codigo, producto, observaciones, proveedor};
                
                modeloProducto.addRow(fila);
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    private void llenaCajas(int id_zona){
        limpiaCajas();
        
        try{
            ArrayList parametros = new ArrayList();
            parametros.add(id_zona);
            parametros.add(this.id_empresa);
            
            rsCajaPrestada = conexion.procAlmacenado("consultaCajaPrestada", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            while (rsCajaPrestada.next()){
                String codigo = rsCajaPrestada.getString("codigo");
                String caja = rsCajaPrestada.getString("caja");
                String zona = rsCajaPrestada.getString("zona");
                int id_cajaDeposito = rsCajaPrestada.getInt("id_cajaDeposito");
                
                Object[] fila = {codigo, caja, zona, id_cajaDeposito, "SI"};
                modeloCaja.addRow(fila);
            }
            
            rsCaja = conexion.procAlmacenado("traeCajaZona", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsCaja.beforeFirst();
            while (rsCaja.next()){
                String codigo = rsCaja.getString("codigo");
                String caja = rsCaja.getString("caja");
                String zona = rsCaja.getString("zona");
                int id_cajaDeposito = rsCaja.getInt("id_cajaDeposito");
                
                Object[] fila = {codigo, caja, zona, id_cajaDeposito, "NO"};
                modeloCaja.addRow(fila);
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        
        try{
            ArrayList parametros = new ArrayList();
            parametros.add(Integer.parseInt(this.jBtnId_presupuesto.getText()));
            
            ResultSet rs = conexion.procAlmacenado("getPresupuestoCajasAsignadas", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            while (rs.next()){
                modeloCajaSeleccion.addRow(Utiles.fillTableObject(modeloCajaSeleccion,rs));
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }        
    }
    
    private boolean validaObligatorios(boolean confirma){
        return (confirma ? this.jComboLugar.getSelectedIndex() >= 0 : true) &&
               (confirma ? this.jComboEntidad.getSelectedIndex() >= 0 : true) &&
               (confirma ? this.jComboProfesional.getSelectedIndex() >= 0 : true) &&
               (confirma ? this.jComboPrestacion.getSelectedIndex() >= 0 : true) &&
               (confirma ? (this.jComboTecnico.isEnabled() ? this.jComboTecnico.getSelectedIndex() >= 0 : true) : true) &&
               (confirma ? !this.jTxtPaciente.getText().isEmpty() : true) &&
               (confirma ? this.jFechaCirugia.getDate() != null : true) &&
               (confirma ? modeloCajaSeleccion.getRowCount() > 0 : true);
    }
    
    private void suspendeAnula(String accion, String estado){
        try {
            if (!this.jBtnId_presupuesto.getText().trim().isEmpty()) {
                int respuesta = JOptionPane.showConfirmDialog(this, 
                        "Está a punto de " + accion + " este turno.\nDesea continuar?", "Atención", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.QUESTION_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) {
                    int id_motivo = 0;
                    String detalleMotivo = "";
                    if (estado.equals("N")) {    //Anula
                        motivo = new Motivo(conexion, empresa, null, true);
                        motivo.setVisible(true);
                        if (motivo.isSiNo()) {
                            id_motivo = motivo.getId_motivo();
                            detalleMotivo = motivo.getMotivo();
                        }else{
                            return;
                        }
                    }
                    ArrayList parametros = new ArrayList();
                    parametros.add(Integer.parseInt(this.jBtnId_presupuesto.getText()));
                    parametros.add(estado);
                    parametros.add(id_motivo);
                    parametros.add(detalleMotivo);
                    parametros.add(usuario);
                    parametros.add(Principal.equipo);

                    conexion.procAlmacenado("suspendeAnulaPresupuesto", parametros, 
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                    limpia();
                    consultaHoy();
                    consulta();
                    llenaTabla();
                }
            }else{
                JOptionPane.showMessageDialog(this, "Seleccione un turno", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }  
        } catch (HeadlessException | NumberFormatException ex) {
            Utiles.enviaError(this.empresa, ex);
        }
    }
    
    private void saveCajasAsignadas(Integer presupuesto){
        matera.db.Presupuesto presu = matera.db.Presupuesto.findById(presupuesto);
        presu.saveCajasAsignadas(modeloCajaSeleccion);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenuProducto = new javax.swing.JPopupMenu();
        jMenuDetalle = new javax.swing.JMenuItem();
        filtroFechaButtonGroup = new javax.swing.ButtonGroup();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboMuestra = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jDesde = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jDesde.getJCalendar().setTodayButtonVisible(true); 
        this.jDesde.getJCalendar().setTodayButtonText("Hoy"); 
        this.jDesde.getJCalendar().setWeekOfYearVisible(false);
        jLabel3 = new javax.swing.JLabel();
        jHasta = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jHasta.getJCalendar().setTodayButtonVisible(true); 
        this.jHasta.getJCalendar().setTodayButtonText("Hoy"); 
        this.jHasta.getJCalendar().setWeekOfYearVisible(false);
        jBtnFiltra = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jRadioFechaAprobacion = new javax.swing.JRadioButton();
        jRadioFechaAgenda = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jTxtBuscaConsulta = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabelNumRows = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jBtnId_presupuesto = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jFechaCirugia = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jFechaCirugia.getJCalendar().setTodayButtonVisible(true);    
        this.jFechaCirugia.getJCalendar().setTodayButtonText("Hoy");    
        this.jFechaCirugia.getJCalendar().setWeekOfYearVisible(false);
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboLugar = new javax.swing.JComboBox();
        jComboProfesional = new javax.swing.JComboBox();
        jComboPrestacion = new javax.swing.JComboBox();
        jBtnLugar = new javax.swing.JButton();
        jBtnProfesional = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jComboEntidad = new javax.swing.JComboBox();
        jTxtPaciente = new javax.swing.JTextField();
        jComboTecnico = new javax.swing.JComboBox();
        jFechaAgenda = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jFechaAgenda.getJCalendar().setTodayButtonVisible(true);    
        this.jFechaAgenda.getJCalendar().setTodayButtonText("Hoy");    
        this.jFechaAgenda.getJCalendar().setWeekOfYearVisible(false);
        jBtnEntidad = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLbltelefono = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablaProducto = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTxtAprobacion = new javax.swing.JTextField();
        jBtnEntidad2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablaCaja = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jBtnMapa = new javax.swing.JButton();
        jScrollCajaSeleccion = new javax.swing.JScrollPane();
        jTablaCajaSeleccion = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jTxtBuscaCaja = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jBtnConfirmar = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jBtnAnular = new javax.swing.JButton();

        jMenuDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/16x16/enabled/eye_icon&16.png"))); // NOI18N
        jMenuDetalle.setText("Ver detalle");
        jMenuDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuDetalleActionPerformed(evt);
            }
        });
        jPopupMenuProducto.add(jMenuDetalle);

        setResizable(true);
        setTitle("Confirma turno de cirugía");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(1241, 700));

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jLabel1.setText("Mostrar");

        jComboMuestra.setMaximumRowCount(10);
        jComboMuestra.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30 días", "60 días", "90 días", "120 días", "150 días", "180 días", "Hoy", "Todos", "Personalizado" }));
        jComboMuestra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboMuestraActionPerformed(evt);
            }
        });

        jLabel2.setText("Desde");

        jLabel3.setText("Hasta");

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

        jLabel4.setText("Zona");

        filtroFechaButtonGroup.add(jRadioFechaAprobacion);
        jRadioFechaAprobacion.setText("Fecha de Aprobación");
        jRadioFechaAprobacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioFechaAprobacionActionPerformed(evt);
            }
        });

        filtroFechaButtonGroup.add(jRadioFechaAgenda);
        jRadioFechaAgenda.setText("Fecha de Agenda");
        jRadioFechaAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioFechaAgendaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboMuestra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jRadioFechaAprobacion)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jRadioFechaAgenda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jBtnFiltra)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.CENTER, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel3)
                            .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jComboMuestra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jRadioFechaAgenda)
                            .addComponent(jLabel2)))
                    .addComponent(jBtnFiltra, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 27, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jRadioFechaAprobacion)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setFocusable(false);

        jTxtBuscaConsulta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaConsultaFocusGained(evt);
            }
        });

        jLabel6.setText("Buscar");

        jLabel17.setText("Turnos:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelNumRows)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBuscaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 865, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBuscaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel17)
                    .addComponent(jLabelNumRows)))
        );

        jTablaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N°", "Entidad", "Lugar de cirugía", "Profesional", "Paciente", "Agenda", "telefono", "observaciones", "id_zona", "resaltar", "Aprobacion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
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
        jTablaConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaConsultaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaConsulta);
        if (jTablaConsulta.getColumnModel().getColumnCount() > 0) {
            jTablaConsulta.getColumnModel().getColumn(0).setMinWidth(60);
            jTablaConsulta.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTablaConsulta.getColumnModel().getColumn(0).setMaxWidth(60);
            jTablaConsulta.getColumnModel().getColumn(6).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(6).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(6).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(7).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(7).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(7).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(8).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(8).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(9).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(9).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        jBtnId_presupuesto.setBackground(new java.awt.Color(153, 204, 255));
        jBtnId_presupuesto.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jBtnId_presupuesto.setText("99999");
        jBtnId_presupuesto.setBorderPainted(false);
        jBtnId_presupuesto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnId_presupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnId_presupuestoActionPerformed(evt);
            }
        });

        jLabel5.setText("* Lugar de cirugía");

        jLabel7.setText("* Profesional");

        jLabel9.setText("* Prestación técnica");

        jComboLugar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboLugarMouseClicked(evt);
            }
        });

        jComboProfesional.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProfesionalMouseClicked(evt);
            }
        });

        jComboPrestacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboPrestacionMouseClicked(evt);
            }
        });
        jComboPrestacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboPrestacionActionPerformed(evt);
            }
        });

        jBtnLugar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/16x16/enabled/eye_icon&16.png"))); // NOI18N
        jBtnLugar.setBorderPainted(false);
        jBtnLugar.setContentAreaFilled(false);
        jBtnLugar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnLugar.setFocusPainted(false);
        jBtnLugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLugarActionPerformed(evt);
            }
        });

        jBtnProfesional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/16x16/enabled/eye_icon&16.png"))); // NOI18N
        jBtnProfesional.setBorderPainted(false);
        jBtnProfesional.setContentAreaFilled(false);
        jBtnProfesional.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnProfesional.setFocusPainted(false);
        jBtnProfesional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnProfesionalActionPerformed(evt);
            }
        });

        jLabel10.setText("* Entidad");

        jLabel8.setText("* Paciente");

        jLabel11.setText("* Técnico");

        jComboEntidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboEntidadMouseClicked(evt);
            }
        });

        jComboTecnico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboTecnicoMouseClicked(evt);
            }
        });

        jBtnEntidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/16x16/enabled/eye_icon&16.png"))); // NOI18N
        jBtnEntidad.setBorderPainted(false);
        jBtnEntidad.setContentAreaFilled(false);
        jBtnEntidad.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnEntidad.setFocusPainted(false);
        jBtnEntidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEntidadActionPerformed(evt);
            }
        });

        jLabel19.setText("Agenda");

        jLbltelefono.setText("Tel paciente");

        jLabel16.setText("* Fecha de Cx.");

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTablaProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cantidad", "Código", "Nombre", "Detalle", "Proveedor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaProducto.setComponentPopupMenu(jPopupMenuProducto);
        jScrollPane2.setViewportView(jTablaProducto);
        if (jTablaProducto.getColumnModel().getColumnCount() > 0) {
            jTablaProducto.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTablaProducto.getColumnModel().getColumn(0).setMaxWidth(100);
            jTablaProducto.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTablaProducto.getColumnModel().getColumn(1).setMaxWidth(150);
            jTablaProducto.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTablaProducto.getColumnModel().getColumn(2).setMaxWidth(300);
        }

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("PRODUCTOS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel20.setText("Aprobacion");

        jBtnEntidad2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/16x16/enabled/eye_icon&16.png"))); // NOI18N
        jBtnEntidad2.setBorderPainted(false);
        jBtnEntidad2.setContentAreaFilled(false);
        jBtnEntidad2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnEntidad2.setFocusPainted(false);
        jBtnEntidad2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEntidad2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jBtnId_presupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jComboLugar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnLugar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jComboProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboPrestacion, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jComboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jBtnEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(jFechaAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnEntidad2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jLbltelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jComboTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFechaCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtAprobacion, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel5)
                            .addComponent(jComboLugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnLugar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jComboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnEntidad)
                            .addComponent(jLabel19)
                            .addComponent(jFechaAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(jTxtAprobacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnEntidad2))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel7)
                            .addComponent(jComboProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnProfesional)
                            .addComponent(jLabel8)
                            .addComponent(jTxtPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLbltelefono))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jFechaCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(jComboTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jComboPrestacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addComponent(jBtnId_presupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTablaCaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Depósito", "id_cajaDeposito", "prestada"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaCaja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaCajaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTablaCaja);
        if (jTablaCaja.getColumnModel().getColumnCount() > 0) {
            jTablaCaja.getColumnModel().getColumn(0).setPreferredWidth(95);
            jTablaCaja.getColumnModel().getColumn(0).setMaxWidth(150);
            jTablaCaja.getColumnModel().getColumn(2).setPreferredWidth(120);
            jTablaCaja.getColumnModel().getColumn(2).setMaxWidth(150);
            jTablaCaja.getColumnModel().getColumn(3).setMinWidth(0);
            jTablaCaja.getColumnModel().getColumn(3).setPreferredWidth(0);
            jTablaCaja.getColumnModel().getColumn(3).setMaxWidth(0);
            jTablaCaja.getColumnModel().getColumn(4).setMinWidth(0);
            jTablaCaja.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTablaCaja.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("* CAJAS");

        jBtnMapa.setText("Ver Mapa");
        jBtnMapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnMapaActionPerformed(evt);
            }
        });

        jTablaCajaSeleccion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Depósito", "id_cajaDeposito", "prestada"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaCajaSeleccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaCajaSeleccionMouseClicked(evt);
            }
        });
        jScrollCajaSeleccion.setViewportView(jTablaCajaSeleccion);
        if (jTablaCajaSeleccion.getColumnModel().getColumnCount() > 0) {
            jTablaCajaSeleccion.getColumnModel().getColumn(0).setPreferredWidth(95);
            jTablaCajaSeleccion.getColumnModel().getColumn(0).setMaxWidth(150);
            jTablaCajaSeleccion.getColumnModel().getColumn(2).setPreferredWidth(120);
            jTablaCajaSeleccion.getColumnModel().getColumn(2).setMaxWidth(150);
            jTablaCajaSeleccion.getColumnModel().getColumn(3).setMinWidth(0);
            jTablaCajaSeleccion.getColumnModel().getColumn(3).setPreferredWidth(0);
            jTablaCajaSeleccion.getColumnModel().getColumn(3).setMaxWidth(0);
            jTablaCajaSeleccion.getColumnModel().getColumn(4).setMinWidth(0);
            jTablaCajaSeleccion.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTablaCajaSeleccion.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        jLabel13.setText("Busca");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnMapa, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtBuscaCaja))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollCajaSeleccion, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBuscaCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(3, 3, 3)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnMapa)
                        .addGap(24, 24, 24))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollCajaSeleccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jPanel6.setBackground(new java.awt.Color(153, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Observaciones"));

        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jScrollPane6.setViewportView(jTxtObservaciones);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(0, 207, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 199, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(jPanel5);

        jLabel12.setText("* Datos obligatorios");

        jBtnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/aprobar.png"))); // NOI18N
        jBtnConfirmar.setText("Confirmar");
        jBtnConfirmar.setBorder(null);
        jBtnConfirmar.setBorderPainted(false);
        jBtnConfirmar.setContentAreaFilled(false);
        jBtnConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnConfirmar.setFocusPainted(false);
        jBtnConfirmar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/aprobar.png"))); // NOI18N
        jBtnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnConfirmarActionPerformed(evt);
            }
        });

        jBtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/guardar.png"))); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.setBorderPainted(false);
        jBtnGuardar.setContentAreaFilled(false);
        jBtnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnGuardar.setFocusPainted(false);
        jBtnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

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

        jBtnAnular.setBackground(new java.awt.Color(204, 0, 0));
        jBtnAnular.setText("Anular");
        jBtnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAnularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(252, 252, 252)
                .addComponent(jBtnConfirmar)
                .addGap(61, 61, 61)
                .addComponent(jBtnGuardar)
                .addGap(18, 18, 18)
                .addComponent(jBtnAnular, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 368, Short.MAX_VALUE)
                .addComponent(jBtnSalir))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBtnConfirmar)
                        .addComponent(jBtnGuardar)
                        .addComponent(jBtnAnular))
                    .addComponent(jLabel12)))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jBtnSalir))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1205, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        limpiaCabecera();
        limpiaProductos();
        limpiaCajas();
        llenaTabla();
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jTxtBuscaConsultaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaConsultaFocusGained
        
    }//GEN-LAST:event_jTxtBuscaConsultaFocusGained

    private void jBtnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnConfirmarActionPerformed
        if (validaObligatorios(true)){
            if (Principal.usuarioData.getId_usuario() != 10 &&    //QUATTRINI. GERMAN MAURICIO
                Principal.usuarioData.getId_usuario() != 14 && //VACCARONE DANILO EDGARDO
                Principal.usuarioData.getId_usuario() != 16 && //VACCARONE MARIO EDGARDO
                Principal.usuarioData.getId_usuario() != 75){  //ORELLANO MOFFICONE MARIA CARLA)
                Calendar fechaAgenda = Calendar.getInstance();
                Calendar fechaHoy = Calendar.getInstance();
                try {
                    fechaHoy.setTime(sdfNewValue.parse(Calendar.DATE + "/" + Calendar.MONTH + "/" + Calendar.YEAR));
                } catch (ParseException ex) {
                    Logger.getLogger(ConfirmaTurno.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if(fechaHoy.after(this.jFechaCirugia.getDate())){
                    JOptionPane.showMessageDialog(this, "La fecha de cirugía no puede ser anterior a la fecha actual", "Atención", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            
            int respuesta = JOptionPane.showConfirmDialog(this, "Está por confirmar un turno de cirugía.\nDesea continuar?", 
                                                        "Atención", 
                                                        JOptionPane.YES_NO_OPTION,
                                                        JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                
                int id_p = Integer.parseInt(this.jBtnId_presupuesto.getText());
                saveCajasAsignadas(id_p);

                int id_tecnico =  0;
                if (this.jComboTecnico.isEnabled()){
                    TecnicoData tmp = (TecnicoData) tecnicoArray.get(this.jComboTecnico.getSelectedIndex());
                    id_tecnico = tmp.getId_tecnico();
                }

                PrestacionData prestacion = (PrestacionData) prestacionArray.get(this.jComboPrestacion.getSelectedIndex());
                int id_prestacion = prestacion.getId_prestacion();
                Double importePrestacion = prestacion.getImporte();

                String observaciones = this.jTxtObservaciones.getText();

                EntidadData entidad = (EntidadData) entidadArray.get(this.jComboEntidad.getSelectedIndex());
                int id_entidad = entidad.getId_entidad();

                entidad = (EntidadData) entidadArray.get(this.jComboLugar.getSelectedIndex());
                int id_lugarCirugia = entidad.getId_entidad();

                ProfesionalData profesional = (ProfesionalData) profesionalArray.get(this.jComboProfesional.getSelectedIndex());
                int id_profesional = profesional.getId_profesional();

                Long fechaCirugia = this.jFechaCirugia.getDate().getTime();
                String paciente = this.jTxtPaciente.getText();
                
                ArrayList parametros = new ArrayList();

                parametros.clear();
                parametros.add(id_p);
                parametros.add(id_tecnico);
                parametros.add(id_prestacion);
                parametros.add(importePrestacion);
                parametros.add(observaciones);
                parametros.add(id_entidad);
                parametros.add(id_lugarCirugia);
                parametros.add(id_profesional);
                parametros.add(fechaCirugia);
                parametros.add(paciente);
                parametros.add(this.usuario);
                parametros.add(Principal.equipo);
                

                conexion.procAlmacenado("confirmaTurno", parametros, 
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                
                LogHelper.logPresupuestoEvent(id_p, Utiles.LOG_EVENTO.CONFIRMA_PRESUPUESTO);

                respuesta = JOptionPane.showConfirmDialog(this, "Desea imprimir el turno?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) {
                    try {
                        parametros.clear();
                        parametros.add(Integer.parseInt(this.jBtnId_presupuesto.getText()));

                        rsTraeTurno = conexion.procAlmacenado("traeTurno", parametros,
                                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                        rsTraePresupuestoProd = conexion.procAlmacenado("traePresupuestoProd", parametros, 
                                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                        rsTraeTurno.first();
                        int id = rsTraeTurno.getInt("id_presupuesto");
                        String e = rsTraeTurno.getString("entidad");
                        Date fCx = rsTraeTurno.getDate("fechaCirugia");
                        String lCx = rsTraeTurno.getString("lugarCirugia");
                        String fechaAprobacion = rsTraeTurno.getString("fechaAprobacion");
                        String presta = rsTraeTurno.getString("prestacion");
                        String tecnico = rsTraeTurno.getString("tecnico");
                        String z = rsTraeTurno.getString("zona");
                        Date fechaPresupuesto = rsTraeTurno.getDate("fecha");
                        String prof = rsTraeTurno.getString("profesional");
                        String pac = rsTraeTurno.getString("paciente");
                        String telefono = rsTraeTurno.getString("telefono");
                        String dni = rsTraeTurno.getString("dni");
                        String reqFacturacion = rsTraeTurno.getString("reqFacturacion");
                        String perfil = rsTraeTurno.getString("perfil");
                        String observa = rsTraeTurno.getString("observaciones");
System.out.println(fechaAprobacion);
                        Map param=new HashMap();
                        param.put("id_presupuesto", id);
                        param.put("entidad", e);
                        param.put("fechaCirugia", fCx);
                        param.put("lugarCirugia", lCx);
                        param.put("prestacion", presta);
                        param.put("tecnico", tecnico);
                        param.put("zona", z);
                        param.put("fechaPresupuesto", fechaPresupuesto);
                        param.put("fechaAprobacion", fechaAprobacion);
                        param.put("profesional", prof);
                        param.put("paciente", pac);
                        param.put("telefono", telefono);
                        param.put("dni", dni);
                        param.put("reqFacturacion", reqFacturacion);
                        param.put("perfil", perfil);
                        param.put("observaciones", observa);

                        PresupuestoProdDataSource dataTurnoConfirmado = new PresupuestoProdDataSource();
                        rsTraePresupuestoProd.beforeFirst();
                        while (rsTraePresupuestoProd.next()){
                            int cantidad = rsTraePresupuestoProd.getInt("cantidad");
                            String codigo = rsTraePresupuestoProd.getString("codigo");
                            String nombre = rsTraePresupuestoProd.getString("producto");
                            String obsProducto = rsTraePresupuestoProd.getString("observaciones");

                            PresupuestoProd pp = new PresupuestoProd(cantidad, codigo, nombre, obsProducto, BigDecimal.ZERO);
                            dataTurnoConfirmado.addPresupuestoProd(pp);
                        }
                        salida.setVisible(true);

                        Reporte reporte = new Reporte();
                        reporte.startReport("TurnoConfirmado",param, dataTurnoConfirmado, salida.getTipoSalida(), salida.getRutaArchivo(), salida.getImpresora(), salida.getCopias());
                    } catch (SQLException ex) {
                        Utiles.enviaError(empresa, ex);
                    }
                }

                limpia();
                consultaHoy();
                consulta();
                llenaTabla();
            }
        }else{
            JOptionPane.showMessageDialog(this, "Complete todos los datos obligatorios (*).", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnConfirmarActionPerformed

    private void jComboMuestraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboMuestraActionPerformed
        boolean enabled = this.jComboMuestra.getSelectedItem().toString().equals("Personalizado");
        muestra(enabled);
    }//GEN-LAST:event_jComboMuestraActionPerformed

    private void jTablaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsultaMouseClicked
        if (evt.getClickCount() == 2) {
            limpiaCabecera();
            int indice = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
            int id = Integer.parseInt(modeloConsulta.getValueAt(indice, 0).toString());
            String entidad = modeloConsulta.getValueAt(indice, 1).toString();
            String lugar = modeloConsulta.getValueAt(indice, 2).toString();
            String profesional = modeloConsulta.getValueAt(indice, 3).toString();
            String paciente = modeloConsulta.getValueAt(indice, 4).toString();
            Calendar fechaAgenda = Calendar.getInstance();
            String fechaAprobacion = "";
            if(modeloConsulta.getValueAt(indice, 10) != null){
                 fechaAprobacion = aprobacionFormat.format((Date) modeloConsulta.getValueAt(indice, 10));
            } else {
                JOptionPane.showMessageDialog(this, "La cirugía seleccionada no posee fecha de aprobación", "Atención", JOptionPane.WARNING_MESSAGE);                   
            }
            if(modeloConsulta.getValueAt(indice, 5) != null){
                fechaAgenda.setTime((Date) modeloConsulta.getValueAt(indice, 5));
                
            } else {
                JOptionPane.showMessageDialog(this, "La cirugía seleccionada no posee fecha de agenda", "Atención", JOptionPane.WARNING_MESSAGE);                   
            }
         
            String telefono = modeloConsulta.getValueAt(indice, 6).toString();
            String observaciones = modeloConsulta.getValueAt(indice, 7).toString();
            this.jBtnId_presupuesto.setText(String.valueOf(id));
            this.jComboEntidad.setSelectedIndex(-1);
            this.jComboEntidad.setSelectedItem(entidad);
            this.jFechaAgenda.setDate(fechaAgenda.getTime());
            this.jComboLugar.setSelectedIndex(-1);
            this.jComboLugar.setSelectedItem(lugar);
            this.jComboProfesional.setSelectedIndex(-1);
            this.jComboProfesional.setSelectedItem(profesional);
            this.jTxtPaciente.setText(paciente);
            this.jLbltelefono.setText(telefono);
            this.jTxtObservaciones.setText(observaciones);
            this.jTxtAprobacion.setText(fechaAprobacion);
            
            llenaProductos();
            llenaCajas(Integer.parseInt(modeloConsulta.getValueAt(indice, 8).toString()));
        }   
    }//GEN-LAST:event_jTablaConsultaMouseClicked

    private void jComboPrestacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboPrestacionActionPerformed
        try{
            String prestacion = this.jComboPrestacion.getSelectedItem().toString();
            this.jComboTecnico.setSelectedIndex(-1);
            this.jComboTecnico.setEnabled(!(prestacion.equals("NO ASISTIDA") || prestacion.equals("SIN SERVICIO")));
        }catch (Exception ex){
            //Exepcion para poder inicializar el combo, no agregar nada!!!
        }
    }//GEN-LAST:event_jComboPrestacionActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        try {
            if (rsHoy != null){
                rsHoy.close();
            }
            if (rsConsulta != null){
                rsConsulta.close();
            }
            if (rsProducto != null){
                rsProducto.close();
            }
            if (rsCajaPrestada != null){
                rsCajaPrestada.close();
            }
            if (rsCaja != null){
                rsCaja.close();
            }
            if (rsMapa != null){
                rsMapa.close();
            }            
            if (rsTraeTurno != null){
                rsTraeTurno.close();
            }
            if (rsTraePresupuestoProd != null){
                rsTraePresupuestoProd.close();
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }finally{
            this.dispose();
        }
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLugarActionPerformed
        EntidadData tmp = (EntidadData) entidadArray.get(this.jComboLugar.getSelectedIndex());
        int id_entidad = tmp.getId_entidad();
        
        entidadVista.setId_Entidad(id_entidad);
        entidadVista.trae();
        entidadVista.setVisible(true);
    }//GEN-LAST:event_jBtnLugarActionPerformed

    private void jBtnEntidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEntidadActionPerformed
        EntidadData tmp = (EntidadData) entidadArray.get(this.jComboEntidad.getSelectedIndex());
        int id_entidad = tmp.getId_entidad();
        
        entidadVista.setId_Entidad(id_entidad);
        entidadVista.trae();
        entidadVista.setVisible(true);
    }//GEN-LAST:event_jBtnEntidadActionPerformed

    private void jBtnProfesionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnProfesionalActionPerformed
        ProfesionalData tmp = (ProfesionalData) profesionalArray.get(this.jComboProfesional.getSelectedIndex());
        int id_profesional = tmp.getId_profesional();
        
        profesionalVista.setId_Entidad(id_profesional);
        profesionalVista.trae();
        profesionalVista.setVisible(true);
    }//GEN-LAST:event_jBtnProfesionalActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios(false)){
            int id_p = Integer.parseInt(this.jBtnId_presupuesto.getText());
            
            saveCajasAsignadas(id_p);
            
            int id_tecnico =  0;
            if (this.jComboTecnico.getSelectedIndex() >= 0){
                TecnicoData tmp = (TecnicoData) tecnicoArray.get(this.jComboTecnico.getSelectedIndex());
                id_tecnico = tmp.getId_tecnico();
            }

            int id_prestacion = 0;
            Double importePrestacion = 0.00;
            if (this.jComboPrestacion.getSelectedIndex() >= 0) {
                PrestacionData prestacion = (PrestacionData) prestacionArray.get(this.jComboPrestacion.getSelectedIndex());
                id_prestacion = prestacion.getId_prestacion();
                importePrestacion = prestacion.getImporte();
            }
                

            String observaciones = this.jTxtObservaciones.getText();

            int id_entidad = 0;
            if (this.jComboEntidad.getSelectedIndex() >= 0) {
                EntidadData entidad = (EntidadData) entidadArray.get(this.jComboEntidad.getSelectedIndex());
                id_entidad = entidad.getId_entidad();
            }
                
            int id_lugarCirugia = 0;
            if (this.jComboLugar.getSelectedIndex() >= 0) {
                EntidadData entidad = (EntidadData) entidadArray.get(this.jComboLugar.getSelectedIndex());
                id_lugarCirugia = entidad.getId_entidad();
            }
            
            int id_profesional = 0;
            if (this.jComboProfesional.getSelectedIndex() >= 0) {
                ProfesionalData profesional = (ProfesionalData) profesionalArray.get(this.jComboProfesional.getSelectedIndex());
                id_profesional = profesional.getId_profesional();
            }
                

            Long fechaCirugia = (this.jFechaCirugia.getDate() == null ? (long) 0 : this.jFechaCirugia.getDate().getTime());
            Long fechaAgenda = (this.jFechaAgenda.getDate() == null ? (long) 0 : this.jFechaAgenda.getDate().getTime());
            String paciente = this.jTxtPaciente.getText();
            
            ArrayList parametros = new ArrayList();
            parametros.add(id_p);
            parametros.add(id_tecnico);
            parametros.add(id_prestacion);
            parametros.add(importePrestacion);
            parametros.add(observaciones);
            parametros.add(id_entidad);
            parametros.add(id_lugarCirugia);
            parametros.add(id_profesional);
            parametros.add(fechaCirugia);
            parametros.add(fechaAgenda);
            parametros.add(paciente);
            parametros.add(this.usuario);
            parametros.add(Principal.equipo);

            conexion.procAlmacenado("guardaConfirmaTurno", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

            limpia();
            consultaHoy();
            consulta();
            llenaTabla();
        }else{
            JOptionPane.showMessageDialog(this,"Complete todos los datos obligatorios (*)", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnId_presupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnId_presupuestoActionPerformed
        if (!this.jBtnId_presupuesto.getText().isEmpty()) {
            Presupuesto presupuesto = new Presupuesto(conexion, id_empresa, empresa, usuario);
            presupuesto.setId_presupuesto(this.jBtnId_presupuesto.getText());
            Principal.dp.add(presupuesto);
            presupuesto.toFront();
            presupuesto.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnId_presupuestoActionPerformed

    private void jBtnMapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnMapaActionPerformed
        MapaCirugia mapa = new MapaCirugia(conexion, id_empresa, empresa);
        mapa.filtrar(0, 0, 0, 0, 0, 0);
        Principal.dp.add(mapa);
        mapa.toFront();
        mapa.setVisible(true);
    }//GEN-LAST:event_jBtnMapaActionPerformed

    private void jTablaCajaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaCajaMouseClicked
        if (evt.getClickCount() == 2) {
            int fila = jTablaCaja.convertRowIndexToModel(jTablaCaja.getSelectedRow());
            String codigo = modeloCaja.getValueAt(fila, 0).toString();
            String nombre = modeloCaja.getValueAt(fila, 1).toString();
            String deposito = modeloCaja.getValueAt(fila, 2).toString();
            int id = (int) modeloCaja.getValueAt(fila, 3);
            String prestada = modeloCaja.getValueAt(fila, 4).toString();

            Object[] nuevo = {codigo, nombre, deposito, id, prestada};
            modeloCajaSeleccion.addRow(nuevo);
            Rectangle r = this.jTablaCajaSeleccion.getCellRect(modeloCajaSeleccion.getRowCount() - 1, 0, true);
            this.jScrollCajaSeleccion.getViewport().scrollRectToVisible(r); 
        }
    }//GEN-LAST:event_jTablaCajaMouseClicked

    private void jTablaCajaSeleccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaCajaSeleccionMouseClicked
        if (evt.getClickCount() == 2) {
            int fila = jTablaCajaSeleccion.convertRowIndexToModel(jTablaCajaSeleccion.getSelectedRow());
            modeloCajaSeleccion.removeRow(fila);
        }
    }//GEN-LAST:event_jTablaCajaSeleccionMouseClicked

    private void jMenuDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuDetalleActionPerformed
        int indiceTabla = jTablaProducto.getSelectedRow();
        if (indiceTabla >= 0){
            String titulo = modeloProducto.getValueAt(indiceTabla, 1).toString() + "   " + modeloProducto.getValueAt(indiceTabla, 2).toString();
            String message = modeloProducto.getValueAt(indiceTabla, 3).toString();
            JOptionPane.showMessageDialog(this, message, titulo, JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this,"No ha seleccionado ninguna fila", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jMenuDetalleActionPerformed

    private void jComboLugarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboLugarMouseClicked
        if (evt.isMetaDown()) {
            this.general1.limpia();
            this.general1.setTitle("Lugar de cirugía");
            Iterator it = this.entidadArray.iterator();
            while (it.hasNext()) {
                EntidadData tmp = (EntidadData) it.next();
                Object[] fila = {tmp.getNombre()};
                this.general1.add(fila);
            }
            this.general1.setVisible(true);
            if (!this.general1.getResultado().isEmpty()) {
                this.jComboLugar.setSelectedItem(this.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboLugarMouseClicked

    private void jComboEntidadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboEntidadMouseClicked
        if (evt.isMetaDown()) {
            this.general1.limpia();
            this.general1.setTitle("Entidad");
            Iterator it = this.entidadArray.iterator();
            while (it.hasNext()) {
                EntidadData tmp = (EntidadData) it.next();
                Object[] fila = {tmp.getNombre()};
                this.general1.add(fila);
            }
            this.general1.setVisible(true);
            if (!this.general1.getResultado().isEmpty()) {
                this.jComboEntidad.setSelectedItem(this.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboEntidadMouseClicked

    private void jComboProfesionalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProfesionalMouseClicked
        if (evt.isMetaDown()) {
            this.general1.limpia();
            this.general1.setTitle("Profesional");
            Iterator it = this.profesionalArray.iterator();
            while (it.hasNext()) {
                ProfesionalData tmp = (ProfesionalData) it.next();
                Object[] fila = {tmp.getNombre()};
                this.general1.add(fila);
            }
            this.general1.setVisible(true);
            if (!this.general1.getResultado().isEmpty()) {
                this.jComboProfesional.setSelectedItem(this.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboProfesionalMouseClicked

    private void jComboPrestacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboPrestacionMouseClicked
        if (evt.isMetaDown()) {
            this.general1.limpia();
            this.general1.setTitle("Prestación técnica");
            Iterator it = this.prestacionArray.iterator();
            while (it.hasNext()) {
                PrestacionData tmp = (PrestacionData) it.next();
                Object[] fila = {tmp.getNombre()};
                this.general1.add(fila);
            }
            this.general1.setVisible(true);
            if (!this.general1.getResultado().isEmpty()) {
                this.jComboPrestacion.setSelectedItem(this.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboPrestacionMouseClicked

    private void jComboTecnicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboTecnicoMouseClicked
        if (evt.isMetaDown()) {
            this.general1.limpia();
            this.general1.setTitle("Técnico");
            Iterator it = this.tecnicoArray.iterator();
            while (it.hasNext()) {
                TecnicoData tmp = (TecnicoData) it.next();
                Object[] fila = {tmp.getNombre()};
                this.general1.add(fila);
            }
            this.general1.setVisible(true);
            if (!this.general1.getResultado().isEmpty()) {
                this.jComboTecnico.setSelectedItem(this.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboTecnicoMouseClicked

    private void jBtnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAnularActionPerformed
        suspendeAnula("anular", "N");
    }//GEN-LAST:event_jBtnAnularActionPerformed

    private void jBtnEntidad2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEntidad2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnEntidad2ActionPerformed

    private void jRadioFechaAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioFechaAgendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioFechaAgendaActionPerformed

    private void jRadioFechaAprobacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioFechaAprobacionActionPerformed
         // TODO add your handling code here:
    }//GEN-LAST:event_jRadioFechaAprobacionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup filtroFechaButtonGroup;
    private javax.swing.JButton jBtnAnular;
    private javax.swing.JButton jBtnConfirmar;
    private javax.swing.JButton jBtnEntidad;
    private javax.swing.JButton jBtnEntidad2;
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnId_presupuesto;
    private javax.swing.JButton jBtnLugar;
    private javax.swing.JButton jBtnMapa;
    private javax.swing.JButton jBtnProfesional;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JComboBox jComboEntidad;
    private javax.swing.JComboBox jComboLugar;
    private javax.swing.JComboBox jComboMuestra;
    private javax.swing.JComboBox jComboPrestacion;
    private javax.swing.JComboBox jComboProfesional;
    private javax.swing.JComboBox jComboTecnico;
    private javax.swing.JComboBox jComboZona;
    private com.toedter.calendar.JDateChooser jDesde;
    private com.toedter.calendar.JDateChooser jFechaAgenda;
    private com.toedter.calendar.JDateChooser jFechaCirugia;
    private com.toedter.calendar.JDateChooser jHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelNumRows;
    private javax.swing.JLabel jLbltelefono;
    private javax.swing.JMenuItem jMenuDetalle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPopupMenu jPopupMenuProducto;
    private javax.swing.JRadioButton jRadioFechaAgenda;
    private javax.swing.JRadioButton jRadioFechaAprobacion;
    private javax.swing.JScrollPane jScrollCajaSeleccion;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTablaCaja;
    private javax.swing.JTable jTablaCajaSeleccion;
    private javax.swing.JTable jTablaConsulta;
    private javax.swing.JTable jTablaProducto;
    private javax.swing.JTextField jTxtAprobacion;
    private javax.swing.JTextField jTxtBuscaCaja;
    private javax.swing.JTextField jTxtBuscaConsulta;
    private javax.swing.JTextArea jTxtObservaciones;
    private javax.swing.JTextField jTxtPaciente;
    // End of variables declaration//GEN-END:variables
}
