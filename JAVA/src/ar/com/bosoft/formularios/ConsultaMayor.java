/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.MayorComposicionDetalladoDataSource;
import ar.com.bosoft.DataSources.MayorDevengadoDetalladoDataSource;
import ar.com.bosoft.DataSources.MayorGeneradoConsolidadoDataSource;
import ar.com.bosoft.DataSources.MayorGeneralConsolidadoDataSource;
import ar.com.bosoft.DataSources.MayorGeneradoDetalladoDataSource;
import ar.com.bosoft.DataSources.MayorGeneralDetalladoDataSource;
import ar.com.bosoft.DataSources.MayorPagadoDetalladoDataSource;
import ar.com.bosoft.DataSources.MayorPendienteDetalladoDataSource;
import ar.com.bosoft.Modelos.MayorComposicionDetallado;
import ar.com.bosoft.Modelos.MayorDevengadoDetallado;
import ar.com.bosoft.Modelos.MayorPagadoDetallado;
import ar.com.bosoft.Modelos.MayorPendienteDetallado;
import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.EspecialidadCRUD;
import ar.com.bosoft.crud.ProfesionalCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.ProfesionalData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import ar.com.dialogos.AvisoEspera;
import com.google.common.base.Optional;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import matera.gui.dialog.LoadingDialog;
import matera.services.MayorProfesionalService;
import matera.workers.ActionWorker;
import org.apache.commons.lang3.StringUtils;
/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class ConsultaMayor extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, indiceConsulta, indiceSeleccion, copias;
    String empresa, todas, impresora, nombreArchivo;
    boolean hayRegistros;
    
    private volatile Thread performer;
    
    EspecialidadCRUD especialidadCRUD;
    ZonaCRUD zonaCRUD;
    ProfesionalCRUD profesionalCRUD;
    
    ArrayList especialidadArray, zonaArray, profesionalArray;
    
    ResultSet rsProfesional, rsMayor;
    
    DefaultTableModel modeloConsulta, modeloSeleccion;
    TableRowSorter sorterConsulta,sorterSeleccion;
    SeleccionImp seleccionImp = new SeleccionImp(null, true);
    
    Reporte reporte = new Reporte();
    
    AvisoEspera avisoEspera;
    
    BigDecimal acumulado;
    
    public ConsultaMayor(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.todas = "-- TODAS --";
        
        this.especialidadCRUD = new EspecialidadCRUD(conexion, empresa);
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.profesionalCRUD = new ProfesionalCRUD(conexion, id_empresa, empresa);
        
        initComponents();
        
        this.modeloConsulta = (DefaultTableModel) this.jTablaConsulta.getModel();
        this.jTablaConsulta.setModel(modeloConsulta);
        sorterConsulta = new TableRowSorter(modeloConsulta);
        jTablaConsulta.setRowSorter (sorterConsulta);
        
        
        this.modeloSeleccion = (DefaultTableModel) this.jTablaSeleccion.getModel();
        this.jTablaSeleccion.setModel(modeloSeleccion);
        sorterSeleccion = new TableRowSorter(modeloSeleccion);
        jTablaSeleccion.setRowSorter (sorterSeleccion);
        
        
        llenaComboZona();
        consultaProfesionales();
        limpia();
        zonaUsuario();
        filtra();
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
        
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedIndex(0);
        }
        
        this.jTxtBusca.setText("");
        this.jTablaConsulta.clearSelection();
        
        this.modeloSeleccion.getDataVector().removeAllElements();
        this.modeloSeleccion.fireTableDataChanged();
        
        this.jDesde.setDate(null);
        this.jHasta.setDate(null);
        this.jRadioGeneral.setSelected(true);
        this.jRadioDetallado.setSelected(true);
        this.jRadioConsolidadoActionPerformed(null);
        this.indiceConsulta = -1;
        this.indiceSeleccion = -1;
        this.hayRegistros = false;
    }
    
    private void llenaComboZona(){
        this.jComboZona.addItem(todas);
        zonaArray = zonaCRUD.consulta(true);
        Iterator i = zonaArray.iterator();
        while (i.hasNext()){
            ZonaData z = (ZonaData) i.next();
            this.jComboZona.addItem(z.getNombre());
        }
    }
    
    private void consultaProfesionales(){
        this.profesionalArray = profesionalCRUD.consulta(true);
    }
    
    private void filtra(){
        modeloConsulta.getDataVector().removeAllElements();
        modeloConsulta.fireTableDataChanged();
        
        //String especialidad = this.jComboEspecialidad.getSelectedItem().toString();        
        String zona = this.jComboZona.getSelectedItem().toString();
        
        Iterator i = profesionalArray.iterator();
        while (i.hasNext()){
            boolean incluido;
            ProfesionalData tmp = (ProfesionalData) i.next();
            /*
            if (!especialidad.equals(todas)) {
                incluido = especialidad.equals(tmp.getEspecialidad());
                if (!incluido){
                    continue;
                }
            }
            */
            if (!zona.equals(todas)){
                incluido = zona.equals(tmp.getZona());
                if (!incluido) {
                    continue;
                }
            }
            
            String nombre = tmp.getNombre();
            String esp = tmp.getEspecialidad();
            String z = tmp.getZona();
            int id = tmp.getId_profesional();

            Object[] fila = {nombre, esp, z, id};
            modeloConsulta.addRow(fila);
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
          sorterConsulta.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTablaConsulta.setRowSorter(sorterConsulta);
        }
    }
    
    private void unoAbajo(){
        int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
        int id_profesional = (int) modeloConsulta.getValueAt(fila, 3);
        for (int i = 0; i < modeloSeleccion.getRowCount(); i++){
            if (id_profesional == (int) modeloSeleccion.getValueAt(i, 3)){
                JOptionPane.showMessageDialog(this, "Este profesional ya ha sido elegido", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        String nombre = modeloConsulta.getValueAt(fila, 0).toString();
        String especialidad = modeloConsulta.getValueAt(fila, 1).toString();
        String zona = modeloConsulta.getValueAt(fila, 2).toString();
        
        Object[] nuevo = {nombre, especialidad, zona, id_profesional};
        modeloSeleccion.addRow(nuevo);
    }
    
    private Boolean isInseleccion(Integer id_profesional){
        for (int i = 0; i < modeloSeleccion.getRowCount(); i++){
            if (id_profesional == (int) modeloSeleccion.getValueAt(i, 3)){
                return true;
            }
        }
        return false;
    }
    
    private void todosAbajo(){
        int[] selected = jTablaConsulta.getSelectedRows();
        for (int i = 0; i < selected.length; i++){
            Integer row = jTablaConsulta.convertRowIndexToModel(selected[i]);
            int id_profesional = (int) modeloConsulta.getValueAt(row, 3);
            if (!isInseleccion(id_profesional)){
                String nombre = modeloConsulta.getValueAt(row, 0).toString();
                String especialidad = modeloConsulta.getValueAt(row, 1).toString();
                String zona = modeloConsulta.getValueAt(row, 2).toString();

                Object[] nuevo = {nombre, especialidad, zona, id_profesional};
                modeloSeleccion.addRow(nuevo);            
            }
        }
    }
    
    private void unoArriba(){
        int fila = jTablaSeleccion.convertRowIndexToModel(jTablaSeleccion.getSelectedRow());
        modeloSeleccion.removeRow(fila);
    }
    
    private void todosArriba(){
        modeloSeleccion.getDataVector().removeAllElements();
        modeloSeleccion.fireTableDataChanged();
    }
    
    private Boolean isAplicacionManualDeAnticipos(String observaciones){
        return observaciones !=null && observaciones.contains("Aplicacion manual de anticipos - ");   
    }
    
    private List<Integer> getProfesionalesSeleccionados(){
        ArrayList<Integer> l = new ArrayList<>();
        for (int i = 0; i < modeloSeleccion.getRowCount(); i++){
            l.add((Integer)Utiles.valueAt(modeloSeleccion, i, "id_profesional"));
        }
        return l;
    }
    
    // ESTA FRUTA ES GRACIAS AL QUE HIZO LOS STORED PROCEDURES
    private String getProfesionalesSeleccionadosString(){
        return StringUtils.join(getProfesionalesSeleccionados(),",");
    }
    
    private void imprimir(int salida){
        if (modeloSeleccion.getRowCount() > 0){
            String nombreReporte = "Mayor";
            
            if (this.jRadioGeneral.isSelected()){
                nombreReporte += "General";
            }else if (this.jRadioDevengados.isSelected()){
                nombreReporte += "Devengado";
            }else if (this.jRadioPendientes.isSelected()){
                nombreReporte += "Pendiente";
            }else if (this.jRadioPagados.isSelected()){
                nombreReporte += "Pagado";
            }else if (this.jRadioComposicion.isSelected()){
                nombreReporte += "Composicion";
            }else if (this.jRadioGenerado.isSelected()){
                nombreReporte += "Generado";
            }
            
            Date desde = Optional.fromNullable(jDesde.getDate()).or(new Date(0L));
            Date hasta = Optional.fromNullable(jHasta.getDate()).or(new Date());
            java.sql.Date mayorDesde = new java.sql.Date(desde.getTime());
            java.sql.Date mayorHasta = new java.sql.Date(hasta.getTime());
            String zona = this.jComboZona.getSelectedItem().toString();
            
            Map param = new HashMap();
            param.put("empresa", this.empresa);
            param.put("desde", desde);
            param.put("hasta", hasta);
            //param.put("especialidad", especialidad);
            param.put("zona", zona);
            
            ArrayList parametros = new ArrayList();
            hayRegistros = false;
            
            switch (nombreReporte){
                case "MayorGeneral":
                    if (this.jRadioDetallado.isSelected()){
                        MayorGeneralDetalladoDataSource data = 
                                MayorProfesionalService.getMayorGeneralDetallado(
                                        mayorDesde, mayorHasta, getProfesionalesSeleccionados());
                        if(data.isEmpty()){
                            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            reporte.startReport(nombreReporte + "Detallado", param, data, salida, nombreArchivo, impresora, copias, avisoEspera);              
                        }                        
                    }
                    else if(this.jRadioConsolidado.isSelected()){
                        MayorGeneralConsolidadoDataSource data = 
                                MayorProfesionalService.getMayorGeneralConsolidado(
                                        mayorDesde, mayorHasta, getProfesionalesSeleccionados());
                        if(data.isEmpty()){
                            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            reporte.startReport(nombreReporte + "Consolidado", param, data, salida, nombreArchivo, impresora, copias);              
                        }                    
                    }
                    
                    
                    break;                    
                    
                case "MayorPendiente":
                    MayorPendienteDetalladoDataSource dataPendienteDetallado = new MayorPendienteDetalladoDataSource();                    
                    
                    try {
                        parametros.clear();
                        parametros.add(getProfesionalesSeleccionadosString());
                        parametros.add(desde == null ? (long) 0 : desde.getTime());
                        parametros.add(hasta == null ? (long) 0 : hasta.getTime());
                        parametros.add(this.id_empresa);

                        rsMayor = conexion.procAlmacenado("consultaMayorPendiente2", parametros, 
                                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                        if (rsMayor.first()) {
                            rsMayor.beforeFirst();
                            BigDecimal acumulado = BigDecimal.ZERO;
                            while (rsMayor.next()) {
                                if (isAplicacionManualDeAnticipos(rsMayor.getString("observaciones")))
                                        continue;
                                String nombre = rsMayor.getString("profesional");
                                Date fecha = rsMayor.getDate("fecha");
                                int id_presupuesto = rsMayor.getInt("id_presupuesto");
                                String paciente = rsMayor.getString("paciente");
                                String tipoOperacion = rsMayor.getString("tipoOperacion");
                                String observaciones = rsMayor.getString("observaciones");
                                BigDecimal saldo = new BigDecimal(rsMayor.getDouble("saldo"));
                                acumulado = acumulado.add(saldo);
                                
                                ar.com.bosoft.Modelos.MayorPendienteDetallado nuevo = new MayorPendienteDetallado(nombre, paciente, tipoOperacion, observaciones, saldo, fecha, id_presupuesto, acumulado);

                                dataPendienteDetallado.add(nuevo);
                                
                                if (rsMayor.next()) {
                                    String nombreProximo = rsMayor.getString("profesional");
                                    rsMayor.previous();
                                    if (!nombre.equals(nombreProximo)) { //Si no hay mas movimientos
                                        acumulado = BigDecimal.ZERO;
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        Utiles.showErrorMessage(ex);
                    }
                    
                    if (this.jCheckUnoPorHoja.isSelected() &&
                            hayRegistros){
                        nombreReporte += "Detallado";
                        if (dataPendienteDetallado.tieneDatos()) {
                            reporte.startReport(nombreReporte, param, dataPendienteDetallado, salida, nombreArchivo, impresora, copias);
                        }
                        // Limpio el DataSource
                        dataPendienteDetallado = new MayorPendienteDetalladoDataSource();
                        hayRegistros = false;
                    }
                    
                    if (!this.jCheckUnoPorHoja.isSelected()){
                        if (this.jRadioDetallado.isSelected()){
                            nombreReporte += "Detallado";
                        }else if (this.jRadioConsolidado.isSelected()){
                            nombreReporte += "Consolidado";
                        }
                        if (dataPendienteDetallado.tieneDatos()) {
                            reporte.startReport(nombreReporte, param, dataPendienteDetallado, salida, nombreArchivo, impresora, copias);
                        }else{
                            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    break;
                    
                case "MayorDevengado":
                    MayorDevengadoDetalladoDataSource dataDevengadoDetallado = new MayorDevengadoDetalladoDataSource();
                    
                    try{
                        parametros.clear();
                        parametros.add(getProfesionalesSeleccionadosString());
                        parametros.add(desde == null ? (long) 0 : desde.getTime());
                        parametros.add(hasta == null ? (long) 0 : hasta.getTime());
                        parametros.add(this.id_empresa);

                        rsMayor = conexion.procAlmacenado("consultaMayorDevengado2", parametros, 
                                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                        BigDecimal acumulado = BigDecimal.ZERO;
                        if (rsMayor.first()){
                            hayRegistros = true;
                            rsMayor.beforeFirst();
                            while (rsMayor.next()){
                                if (isAplicacionManualDeAnticipos(rsMayor.getString("observaciones")))
                                        continue;
                                String nombre = rsMayor.getString("profesional");
                                Date fechaCirugia = rsMayor.getDate("fechaCirugia");
                                Date fechaFactura = rsMayor.getDate("fechaFactura");
                                int id_presupuesto = rsMayor.getInt("id_presupuesto");
                                String paciente = rsMayor.getString("paciente");
                                String tipoOperacion = rsMayor.getString("tipoOperacion");
                                String observaciones = rsMayor.getString("observaciones");
                                BigDecimal debe = new BigDecimal(rsMayor.getDouble("debe"));
                                BigDecimal haber = new BigDecimal(rsMayor.getDouble("haber"));
                                acumulado = acumulado.add(debe).subtract(haber);

                                ar.com.bosoft.Modelos.MayorDevengadoDetallado nuevo = new MayorDevengadoDetallado(nombre, paciente, tipoOperacion, observaciones, debe, haber, acumulado, fechaCirugia, fechaFactura, id_presupuesto);

                                dataDevengadoDetallado.add(nuevo);
                                
                                if (rsMayor.next()) {
                                    String nombreProximo = rsMayor.getString("profesional");
                                    rsMayor.previous();
                                    if (!nombre.equals(nombreProximo)) { //Si no hay mas movimientos
                                        acumulado = BigDecimal.ZERO;
                                    }
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        Utiles.showErrorMessage(ex);
                    }
                    if (this.jCheckUnoPorHoja.isSelected() &&
                            hayRegistros){
                        nombreReporte += "Detallado";
                        if (dataDevengadoDetallado.tieneDatos()) {
                            reporte.startReport(nombreReporte, param, dataDevengadoDetallado, salida, nombreArchivo, impresora, copias);
                        }   
                        // Limpio el DataSource
                        dataDevengadoDetallado = new MayorDevengadoDetalladoDataSource();
                        hayRegistros = false;
                    }
                    
                    if (!this.jCheckUnoPorHoja.isSelected()){
                        if (this.jRadioDetallado.isSelected()){
                            nombreReporte += "Detallado";
                        }else if (this.jRadioConsolidado.isSelected()){
                            nombreReporte += "Consolidado";
                        }
                        if (dataDevengadoDetallado.tieneDatos()) {
                            reporte.startReport(nombreReporte, param, dataDevengadoDetallado, salida, nombreArchivo, impresora, copias);
                        }else{
                            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
                        } 
                    }
                    break;
                    
                case "MayorPagado":
                    MayorPagadoDetalladoDataSource dataPagadoDetallado = new MayorPagadoDetalladoDataSource();
                    
                    try{
                        parametros.clear();
                        parametros.add(getProfesionalesSeleccionadosString());
                        parametros.add(desde == null ? (long) 0 : desde.getTime());
                        parametros.add(hasta == null ? (long) 0 : hasta.getTime());
                        parametros.add(this.id_empresa);

                        rsMayor = conexion.procAlmacenado("consultaMayorPagado2", parametros, 
                                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                        if (rsMayor.first()){
                            hayRegistros = true;

                            rsMayor.beforeFirst();
                            while (rsMayor.next()){
                                String nombre = rsMayor.getString("profesional");
                                Date fechaLiquidacion = rsMayor.getDate("fechaLiquidacion");
                                int id_presupuesto = rsMayor.getInt("id_presupuesto");
                                String paciente = rsMayor.getString("paciente");
                                String detallePago = rsMayor.getString("detallePago");
                                BigDecimal importe = new BigDecimal(rsMayor.getDouble("importe"));

                                MayorPagadoDetallado nuevo = new MayorPagadoDetallado(nombre, paciente, detallePago, importe, fechaLiquidacion, id_presupuesto);

                                dataPagadoDetallado.add(nuevo);
                            }
                        }
                    } catch (SQLException ex) {
                        Utiles.showErrorMessage(ex);
                    }
                    if (this.jCheckUnoPorHoja.isSelected() &&
                            hayRegistros){
                        nombreReporte += "Detallado";
                        if (dataPagadoDetallado.tieneDatos()) {
                            reporte.startReport(nombreReporte, param, dataPagadoDetallado, salida, nombreArchivo, impresora, copias);
                        } 
                        // Limpio el DataSource
                        dataPagadoDetallado = new MayorPagadoDetalladoDataSource();
                        hayRegistros = false;
                    }
                    
                    if (!this.jCheckUnoPorHoja.isSelected()){
                        if (this.jRadioDetallado.isSelected()){
                            nombreReporte += "Detallado";
                        }else if (this.jRadioConsolidado.isSelected()){
                            nombreReporte += "Consolidado";
                        }
                        //this.avisoEspera.setVisible(false);
                        if (dataPagadoDetallado.tieneDatos()) {
                            reporte.startReport(nombreReporte, param, dataPagadoDetallado, salida, nombreArchivo, impresora, copias);
                        }else{
                            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    break;
                
                    
                case "MayorGenerado":
                    if (this.jRadioDetallado.isSelected()){
                        MayorGeneradoDetalladoDataSource data = 
                                MayorProfesionalService.getMayorGeneradoDetallado(
                                        mayorDesde, mayorHasta, getProfesionalesSeleccionados());
                        if(data.isEmpty()){
                            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            reporte.startReport(nombreReporte + "Detallado", param, data, salida, nombreArchivo, impresora, copias);              
                        }                        
                    }
                    else if(this.jRadioConsolidado.isSelected()){
                        MayorGeneradoConsolidadoDataSource data = 
                                MayorProfesionalService.getMayorGeneradoConsolidado(
                                        mayorDesde, mayorHasta, getProfesionalesSeleccionados());
                        if(data.isEmpty()){
                            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            reporte.startReport(nombreReporte + "Consolidado", param, data, salida, nombreArchivo, impresora, copias);              
                        }                    
                    }
                    
                    
                    break;
                    
                case "MayorComposicion":
                    MayorComposicionDetalladoDataSource dataComposicionDetallado = new MayorComposicionDetalladoDataSource();                    
                    
                    try{
                        parametros.clear();
                        parametros.add(getProfesionalesSeleccionadosString());
                        parametros.add(desde == null ? (long) 0 : desde.getTime());
                        parametros.add(hasta == null ? (long) 0 : hasta.getTime());
                        parametros.add(this.id_empresa);

                        rsMayor = conexion.procAlmacenado("consultaMayorComposicion2", parametros, 
                                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                        if (rsMayor.first()){
                            rsMayor.beforeFirst();
                            acumulado = BigDecimal.ZERO;
                            while (rsMayor.next()){
                                if (isAplicacionManualDeAnticipos(rsMayor.getString("observaciones")))
                                        continue;                                
                                int id_presupuesto = rsMayor.getInt("id_presupuesto");
                                BigDecimal saldoInicial = new BigDecimal(rsMayor.getDouble("saldoInicial"));                                
                                String nombre = rsMayor.getString("profesional");
                                Date fecha;
                                String paciente;
                                String tipoOperacion;
                                String observaciones;
                                BigDecimal saldo;
                                if (id_presupuesto == -1) { //Saldo inicial
                                    acumulado = saldoInicial;
                                    if (saldoInicial.compareTo(BigDecimal.ZERO) == 0) { //Si saldo inicial es cero
                                        if (rsMayor.next()) {
                                            String nombreProximo = rsMayor.getString("profesional");
                                            if (!nombre.equals(nombreProximo)) {    //Si no tiene movimientos
                                                rsMayor.previous();
                                            }else{
                                                id_presupuesto = rsMayor.getInt("id_presupuesto");
                                                saldoInicial = new BigDecimal(rsMayor.getDouble("saldoInicial"));                                
                                                nombre = rsMayor.getString("profesional");
                                                fecha = rsMayor.getDate("fecha");
                                                paciente = rsMayor.getString("paciente");
                                                tipoOperacion = rsMayor.getString("tipoOperacion");
                                                observaciones = rsMayor.getString("observaciones");
                                                saldo = new BigDecimal(rsMayor.getDouble("saldo"));
                                                acumulado = acumulado.add(saldo);
                                                ar.com.bosoft.Modelos.MayorComposicionDetallado nuevo = new MayorComposicionDetallado(nombre, paciente, tipoOperacion, observaciones, saldoInicial, saldo, acumulado, fecha, id_presupuesto);
                                                if(!saldo.equals(BigDecimal.ZERO))
                                                    dataComposicionDetallado.add(nuevo);
                                            }
                                        }
                                    }else{  //Si el saldo inicial no es cero
                                        if (rsMayor.next()) {
                                            String nombreProximo = rsMayor.getString("profesional");
                                            if (!nombre.equals(nombreProximo)) {    //Si no tiene movimientos
                                                rsMayor.previous();
                                                id_presupuesto = rsMayor.getInt("id_presupuesto");
                                                saldoInicial = new BigDecimal(rsMayor.getDouble("saldoInicial"));                                
                                                nombre = rsMayor.getString("profesional");
                                                fecha = rsMayor.getDate("fecha");
                                                paciente = rsMayor.getString("paciente");
                                                tipoOperacion = rsMayor.getString("tipoOperacion");
                                                observaciones = rsMayor.getString("observaciones");
                                                saldo = new BigDecimal(rsMayor.getDouble("saldo"));
                                                ar.com.bosoft.Modelos.MayorComposicionDetallado nuevo = new MayorComposicionDetallado(nombre, paciente, tipoOperacion, observaciones, saldoInicial, saldo, acumulado, fecha, id_presupuesto);
                                                dataComposicionDetallado.add(nuevo);
                                            }else{
                                                id_presupuesto = rsMayor.getInt("id_presupuesto");
                                                nombre = rsMayor.getString("profesional");
                                                fecha = rsMayor.getDate("fecha");
                                                paciente = rsMayor.getString("paciente");
                                                tipoOperacion = rsMayor.getString("tipoOperacion");
                                                observaciones = rsMayor.getString("observaciones");
                                                saldo = new BigDecimal(rsMayor.getDouble("saldo"));
                                                acumulado = acumulado.add(saldo);
                                                ar.com.bosoft.Modelos.MayorComposicionDetallado nuevo = new MayorComposicionDetallado(nombre, paciente, tipoOperacion, observaciones, saldoInicial, saldo, acumulado, fecha, id_presupuesto);
                                                dataComposicionDetallado.add(nuevo);
                                            }
                                        }else{
                                            rsMayor.previous();
                                            id_presupuesto = rsMayor.getInt("id_presupuesto");
                                            saldoInicial = new BigDecimal(rsMayor.getDouble("saldoInicial"));                                
                                            nombre = rsMayor.getString("profesional");
                                            fecha = rsMayor.getDate("fecha");
                                            paciente = rsMayor.getString("paciente");
                                            tipoOperacion = rsMayor.getString("tipoOperacion");
                                            observaciones = rsMayor.getString("observaciones");
                                            saldo = new BigDecimal(rsMayor.getDouble("saldo"));
                                            ar.com.bosoft.Modelos.MayorComposicionDetallado nuevo = new MayorComposicionDetallado(nombre, paciente, tipoOperacion, observaciones, saldoInicial, saldo, acumulado, fecha, id_presupuesto);
                                            dataComposicionDetallado.add(nuevo);
                                        }
                                    }
                                }else{
                                    id_presupuesto = rsMayor.getInt("id_presupuesto");
                                    saldoInicial = new BigDecimal(rsMayor.getDouble("saldoInicial"));                                
                                    nombre = rsMayor.getString("profesional");
                                    fecha = rsMayor.getDate("fecha");
                                    paciente = rsMayor.getString("paciente");
                                    tipoOperacion = rsMayor.getString("tipoOperacion");
                                    observaciones = rsMayor.getString("observaciones");
                                    saldo = new BigDecimal(rsMayor.getDouble("saldo"));
                                    acumulado = acumulado.add(saldo);
                                    
                                    ar.com.bosoft.Modelos.MayorComposicionDetallado nuevo = new MayorComposicionDetallado(nombre, paciente, tipoOperacion, observaciones, saldoInicial, saldo, acumulado, fecha, id_presupuesto);
                                    dataComposicionDetallado.add(nuevo);
                                }
                            }
                        }else{
                            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        Utiles.showErrorMessage(ex);
                    }
                    
                    if (this.jCheckUnoPorHoja.isSelected() &&
                            hayRegistros){
                        nombreReporte += "Detallado";

                        if (dataComposicionDetallado.tieneDatos()) {
                            reporte.startReport(nombreReporte, param, dataComposicionDetallado, salida, nombreArchivo, impresora, copias);
                        }

                        // Limpio el DataSource
                        dataComposicionDetallado = new MayorComposicionDetalladoDataSource();
                        hayRegistros = false;
                    }
                    
                    if (!this.jCheckUnoPorHoja.isSelected()){
                        if (this.jRadioDetallado.isSelected()){
                            nombreReporte += "Detallado";
                        }else if (this.jRadioConsolidado.isSelected()){
                            nombreReporte += "Consolidado";
                        }
                        if (dataComposicionDetallado.tieneDatos()) {
                            reporte.startReport(nombreReporte, param, dataComposicionDetallado, salida, nombreArchivo, impresora, copias);
                        }else{
                            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    break;                    
            }
            
            //this.limpia();
             ConsultaMayor.this.avisoEspera.setVisible(false);
            this.filtra();
        }else{
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún profesional", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void disablePrintButtons(){
        jBtnImp.disable();
        jBtnPdf.disable();
        jBtnScr.disable();
        jBtnXls.disable();
    }
    
    private void enablePrintButtons(){
        jBtnImp.enable();
        jBtnPdf.enable();
        jBtnScr.enable();
        jBtnXls.enable();
    }    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupTipo = new javax.swing.ButtonGroup();
        buttonGroupModo = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
        jBtnUnoAbajo = new javax.swing.JButton();
        jBtnUnoArriba = new javax.swing.JButton();
        jBtnTodosAbajo = new javax.swing.JButton();
        jBtnTodosArriba = new javax.swing.JButton();
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
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jRadioDetallado = new javax.swing.JRadioButton();
        jRadioConsolidado = new javax.swing.JRadioButton();
        jCheckUnoPorHoja = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jRadioGeneral = new javax.swing.JRadioButton();
        jRadioGenerado = new javax.swing.JRadioButton();
        jRadioDevengados = new javax.swing.JRadioButton();
        jRadioPendientes = new javax.swing.JRadioButton();
        jRadioPagados = new javax.swing.JRadioButton();
        jRadioComposicion = new javax.swing.JRadioButton();
        jBtnSalir = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTablaSeleccion = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jBtnFiltra = new javax.swing.JButton();

        setTitle("Consulta de mayor de profesional");
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
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                "Nombre", "Especialidad", "Zona", "id_profesional"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
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
        jTablaConsulta.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTablaConsulta.getTableHeader().setReorderingAllowed(false);
        jTablaConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaConsultaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTablaConsulta);
        if (jTablaConsulta.getColumnModel().getColumnCount() > 0) {
            jTablaConsulta.getColumnModel().getColumn(1).setMinWidth(170);
            jTablaConsulta.getColumnModel().getColumn(1).setPreferredWidth(170);
            jTablaConsulta.getColumnModel().getColumn(1).setMaxWidth(170);
            jTablaConsulta.getColumnModel().getColumn(2).setMinWidth(150);
            jTablaConsulta.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTablaConsulta.getColumnModel().getColumn(2).setMaxWidth(150);
            jTablaConsulta.getColumnModel().getColumn(3).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(3).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(3).setMaxWidth(0);
        }

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

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jLabel1.setText("Desde");

        jLabel2.setText("Hasta");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
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

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));

        buttonGroupModo.add(jRadioDetallado);
        jRadioDetallado.setText("Detallado");
        jRadioDetallado.setContentAreaFilled(false);
        jRadioDetallado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioDetalladoActionPerformed(evt);
            }
        });

        buttonGroupModo.add(jRadioConsolidado);
        jRadioConsolidado.setText("Consolidado");
        jRadioConsolidado.setContentAreaFilled(false);
        jRadioConsolidado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioConsolidadoActionPerformed(evt);
            }
        });

        jCheckUnoPorHoja.setText("Uno por hoja");
        jCheckUnoPorHoja.setContentAreaFilled(false);
        jCheckUnoPorHoja.setEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckUnoPorHoja)
                    .addComponent(jRadioConsolidado)
                    .addComponent(jRadioDetallado))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioDetallado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioConsolidado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jCheckUnoPorHoja)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(153, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo"));

        buttonGroupTipo.add(jRadioGeneral);
        jRadioGeneral.setText("General");
        jRadioGeneral.setContentAreaFilled(false);
        jRadioGeneral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioGeneralActionPerformed(evt);
            }
        });

        buttonGroupTipo.add(jRadioGenerado);
        jRadioGenerado.setSelected(true);
        jRadioGenerado.setText("Generado");
        jRadioGenerado.setContentAreaFilled(false);
        jRadioGenerado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioGeneradoActionPerformed(evt);
            }
        });

        buttonGroupTipo.add(jRadioDevengados);
        jRadioDevengados.setText("Devengados");
        jRadioDevengados.setContentAreaFilled(false);
        jRadioDevengados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioDevengadosActionPerformed(evt);
            }
        });

        buttonGroupTipo.add(jRadioPendientes);
        jRadioPendientes.setText("Pendientes");
        jRadioPendientes.setContentAreaFilled(false);

        buttonGroupTipo.add(jRadioPagados);
        jRadioPagados.setText("Pagados");
        jRadioPagados.setContentAreaFilled(false);

        buttonGroupTipo.add(jRadioComposicion);
        jRadioComposicion.setText("Composición de saldos");
        jRadioComposicion.setContentAreaFilled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioGeneral)
                    .addComponent(jRadioGenerado)
                    .addComponent(jRadioDevengados)
                    .addComponent(jRadioPendientes)
                    .addComponent(jRadioPagados)
                    .addComponent(jRadioComposicion))

                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jRadioGeneral)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioGenerado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioDevengados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioPendientes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioPagados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioComposicion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))

                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.getAccessibleContext().setAccessibleName("");

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
                "Nombre", "Especialidad", "Zona", "id_profesional"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
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
        jTablaSeleccion.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTablaSeleccion.getTableHeader().setReorderingAllowed(false);
        jTablaSeleccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaSeleccionMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTablaSeleccion);
        if (jTablaSeleccion.getColumnModel().getColumnCount() > 0) {
            jTablaSeleccion.getColumnModel().getColumn(1).setMinWidth(170);
            jTablaSeleccion.getColumnModel().getColumn(1).setPreferredWidth(170);
            jTablaSeleccion.getColumnModel().getColumn(1).setMaxWidth(170);
            jTablaSeleccion.getColumnModel().getColumn(2).setMinWidth(150);
            jTablaSeleccion.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTablaSeleccion.getColumnModel().getColumn(2).setMaxWidth(150);
            jTablaSeleccion.getColumnModel().getColumn(3).setMinWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(3).setPreferredWidth(0);
            jTablaSeleccion.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Zona");

        jComboZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboZonaActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jComboZona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnFiltra)
                .addGap(59, 59, 59))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnFiltra)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnSalir)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(159, 159, 159)
                                .addComponent(jBtnUnoAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnTodosAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnTodosArriba, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnUnoArriba, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBtnUnoAbajo)
                            .addComponent(jBtnTodosAbajo)
                            .addComponent(jBtnTodosArriba)
                            .addComponent(jBtnUnoArriba))
                        .addGap(3, 3, 3)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnSalir))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnScrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScrActionPerformed

        LoadingDialog loadingDialog = new LoadingDialog();

        SwingWorker sw = new ActionWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                
                imprimir(0);
                return null;
            }


            @Override
            public void done(){
                loadingDialog.getProgressBar().setIndeterminate(false);
                loadingDialog.getLabel().setText("Successful");
                loadingDialog.getProgressBar().setValue(100); // 100%
                loadingDialog.getDialog().dispose();
                enablePrintButtons();
            }
        };
        sw.execute();
        disablePrintButtons();
        loadingDialog.getDialog().setVisible(true);
        
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
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ConsultaMayor.this.avisoEspera = new AvisoEspera(null, false, AvisoEspera.CARGANDO);
                        ConsultaMayor.this.avisoEspera.setVisible(true);
                        performer = new Thread(new Runnable() {
                            
                            @Override
                            public void run() {
                                        ConsultaMayor.this.imprimir(2);
                            }
                        }, "Performer");
                        performer.start();
                    }
                });
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
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ConsultaMayor.this.avisoEspera = new AvisoEspera(null, false, AvisoEspera.CARGANDO);
                        ConsultaMayor.this.avisoEspera.setVisible(true);
                        performer = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ConsultaMayor.this.imprimir(3);
                            }
                        }, "Performer");
                        performer.start();
                    }
                });
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jBtnXlsActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        try{
            if (rsProfesional != null){
                rsProfesional.close();
            }

            if (rsMayor != null){
                rsMayor.close();
            }

            this.dispose();
        }catch (Exception ex){
            Utiles.showErrorMessage(ex);
        }
        
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        filtra();
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jTablaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsultaMouseClicked
        if (evt.getClickCount() == 2){
            unoAbajo();
        }
    }//GEN-LAST:event_jTablaConsultaMouseClicked

    private void jBtnTodosAbajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTodosAbajoActionPerformed
        todosAbajo();
    }//GEN-LAST:event_jBtnTodosAbajoActionPerformed

    private void jBtnUnoAbajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnUnoAbajoActionPerformed
        int fila = jTablaConsulta.getSelectedRow();
        if (fila >= 0){
            unoAbajo();
        }else{
            JOptionPane.showMessageDialog(this, "No hay ningún profesional seleccionado", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnUnoAbajoActionPerformed

    private void jBtnTodosArribaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTodosArribaActionPerformed
        todosArriba();
    }//GEN-LAST:event_jBtnTodosArribaActionPerformed

    private void jBtnUnoArribaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnUnoArribaActionPerformed
        int fila = jTablaSeleccion.getSelectedRow();
        if (fila >= 0){
            unoArriba();
        }else{
            JOptionPane.showMessageDialog(this, "No hay ningún profesional seleccionado", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnUnoArribaActionPerformed

    private void jTablaSeleccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaSeleccionMouseClicked
        if (evt.getClickCount() == 2){
            unoArriba();
        }
    }//GEN-LAST:event_jTablaSeleccionMouseClicked

    private void jBtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImpActionPerformed
        seleccionImp.setVisible(true);
        int fila = jTablaConsulta.getSelectedRow();
        
        if (seleccionImp.getSino()){
            this.impresora = seleccionImp.getImpresora();
            this.copias = seleccionImp.getCopias();
            if (fila >= 0){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ConsultaMayor.this.avisoEspera = new AvisoEspera(null, false, AvisoEspera.CARGANDO);
                    ConsultaMayor.this.avisoEspera.setVisible(true);
                    Thread performer = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ConsultaMayor.this.imprimir(1);
                        }
                    }, "Performer");
                    performer.start();
                }
            });
            }
        }
    }//GEN-LAST:event_jBtnImpActionPerformed

    private void jRadioConsolidadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioConsolidadoActionPerformed
        this.jCheckUnoPorHoja.setSelected(false);
        //this.jCheckUnoPorHoja.setEnabled(!this.jRadioConsolidado.isSelected());
    }//GEN-LAST:event_jRadioConsolidadoActionPerformed

    private void jRadioDetalladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioDetalladoActionPerformed
        this.jRadioConsolidadoActionPerformed(evt);
    }//GEN-LAST:event_jRadioDetalladoActionPerformed

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        todosArriba();
    }//GEN-LAST:event_jComboZonaActionPerformed

    private void jRadioGeneralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioGeneralActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioGeneralActionPerformed

    private void jRadioGeneradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioGeneradoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioGeneradoActionPerformed

    private void jRadioDevengadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioDevengadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioDevengadosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupModo;
    private javax.swing.ButtonGroup buttonGroupTipo;
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jBtnTodosAbajo;
    private javax.swing.JButton jBtnTodosArriba;
    private javax.swing.JButton jBtnUnoAbajo;
    private javax.swing.JButton jBtnUnoArriba;
    private javax.swing.JButton jBtnXls;
    private javax.swing.JCheckBox jCheckUnoPorHoja;
    private javax.swing.JComboBox jComboZona;
    private com.toedter.calendar.JDateChooser jDesde;
    private com.toedter.calendar.JDateChooser jHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton jRadioComposicion;
    private javax.swing.JRadioButton jRadioConsolidado;
    private javax.swing.JRadioButton jRadioDetallado;
    private javax.swing.JRadioButton jRadioDevengados;
    private javax.swing.JRadioButton jRadioGenerado;
    private javax.swing.JRadioButton jRadioGeneral;
    private javax.swing.JRadioButton jRadioPagados;
    private javax.swing.JRadioButton jRadioPendientes;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTablaConsulta;
    private javax.swing.JTable jTablaSeleccion;
    private javax.swing.JTextField jTxtBusca;
    // End of variables declaration//GEN-END:variables
}
