/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.LiquidacionProfesionalDataSource;
import ar.com.bosoft.DataSources.ListaLiquidacionDataSource;
import ar.com.bosoft.Modelos.LiquidacionProfesional;
import ar.com.bosoft.Modelos.ListaLiquidacion;
import ar.com.bosoft.RenderTablas.DateRenderer;
import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ProfesionalCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.ProfesionalData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.conexion.ActiveDatabase;
import ar.com.bosoft.vistasRapidas.Turno;
import java.awt.HeadlessException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import matera.db.LiquidacionOLP;
import matera.jooq.tables.Trackingolp;
import matera.jooq.tables.records.TrackingolpRecord;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class ConsultaOrdenPago extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, copias, id_liquidacion;
    String empresa, usuario, profesional, dni, impresora, rutaArchivo;
    
    ResultSet rsConsulta, rsTurnos, rsPagos;
    DefaultTableModel modeloConsulta, modeloTurnos, modeloPagos;
    TableRowSorter sorterConsulta, sorterTurnos, sorterPagos;
    TableCellRenderer tableCellRenderer;

    ZonaCRUD zonaCRUD;
    ProfesionalCRUD profesionalCRUD;
    ArrayList arrayZona, profesionalArray, arrayId_profesional;
    
    RoundingMode RM = RoundingMode.HALF_UP;
    int escala = 2;
    
    Salida salida = new Salida(null, true);
    
    public ConsultaOrdenPago(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.profesionalCRUD = new ProfesionalCRUD(conexion, id_empresa, empresa);
        this.arrayId_profesional = new ArrayList();
        
        initComponents();
        
        modeloConsulta = (DefaultTableModel) this.jTablaConsulta.getModel();
        jTablaConsulta.setModel(modeloConsulta);
        jTablaConsulta.setRowSorter (new TableRowSorter(modeloConsulta));
        sorterConsulta = new TableRowSorter(modeloConsulta);
        tableCellRenderer = new DateRenderer();
        jTablaConsulta.getColumnModel().getColumn(0).setCellRenderer(tableCellRenderer);
        
        modeloTurnos = (DefaultTableModel) this.jTablaTurnos.getModel();
        jTablaTurnos.setModel(modeloTurnos);
        jTablaTurnos.setRowSorter (new TableRowSorter(modeloTurnos));
        sorterTurnos = new TableRowSorter(modeloTurnos);
        
        modeloPagos = (DefaultTableModel) this.jTablaPagos.getModel();
        jTablaPagos.setModel(modeloPagos);
        jTablaPagos.setRowSorter (new TableRowSorter(modeloPagos));
        sorterPagos = new TableRowSorter(modeloPagos);
        
        consultaZona();
        consultaProfesional();
        llenaComboZona();
        limpia();
        zonaUsuario();
        this.jComboZonaActionPerformed(null);
        setJTexFieldChanged(jTxtBusca);
    }

    private void consultaZona(){
        arrayZona = zonaCRUD.consulta(true);
    }
    
    private void consultaProfesional(){
        profesionalArray = profesionalCRUD.consulta(true);
    }
    
    private void llenaComboZona(){
        if (arrayZona.isEmpty()){
            JOptionPane.showMessageDialog(this, "No hay zonas habilitadas", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }else{
            this.jComboZona.addItem("-- TODAS --");
            Iterator i = arrayZona.iterator();
            while (i.hasNext()){
                ZonaData tmp = (ZonaData) i.next();
                this.jComboZona.addItem(tmp.getNombre());
            }
        }
    }
    
    private void llenaComboProfesional(int indiceComboZona){
        this.arrayId_profesional.clear();
        this.jComboProfesional.removeAllItems();
        this.jComboProfesional.addItem("-- TODOS --");
            
        if (indiceComboZona >= 0){
            ZonaData z = (ZonaData) arrayZona.get(indiceComboZona - 1);
            int id_zona = z.getId_zona();
            Iterator i = profesionalArray.iterator();
            while (i.hasNext()){
                ProfesionalData item = (ProfesionalData) i.next();
                if (item.getId_zona() == id_zona){
                    this.jComboProfesional.addItem(item.getNombre());
                    this.arrayId_profesional.add(item.getId_profesional());
                }
            }
        }
        this.jComboProfesional.setSelectedIndex(0);
    }
    
    private void zonaUsuario(){
        int id_zonaUsuario = Principal.usuarioData.getId_zonaSistema();
        if (id_zonaUsuario > 0) {
            if (this.arrayZona != null){
                Iterator it = arrayZona.iterator();
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
        
        this.jComboProfesional.setSelectedIndex(-1);
        
        modeloTurnos.getDataVector().removeAllElements();
        modeloTurnos.fireTableDataChanged();
        
        modeloPagos.getDataVector().removeAllElements();
        modeloPagos.fireTableDataChanged();
        
        id_liquidacion = 0;
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

    private void consulta(){
        try{
            modeloConsulta.getDataVector().removeAllElements();
            modeloConsulta.fireTableDataChanged();
            jFormattedTextOLP.setText("");
            modeloPagos.getDataVector().removeAllElements();
            modeloTurnos.getDataVector().removeAllElements();
            
            ArrayList parametros = new ArrayList();
            
            Long desde = this.jDesde.getDate() == null ? (long)0 : this.jDesde.getDate().getTime();
            Long hasta = this.jHasta.getDate() == null ? (long)0 : this.jHasta.getDate().getTime();
            
            int id_zona = 0;
            if (this.jComboZona.getSelectedIndex() > 0){
                ZonaData z = (ZonaData) arrayZona.get(this.jComboZona.getSelectedIndex() - 1);
                id_zona = z.getId_zona();
            }
            
            int id_profesional = 0;
            if (this.jComboProfesional.getSelectedIndex() > 0){
                id_profesional = (int) arrayId_profesional.get(this.jComboProfesional.getSelectedIndex() - 1);
            }
            
            parametros.add(desde);
            parametros.add(hasta);
            parametros.add(id_zona);
            parametros.add(id_profesional);
            parametros.add(this.id_empresa);
            
            rsConsulta = conexion.procAlmacenado("consultaLiquidacion", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsConsulta.beforeFirst();
            while (rsConsulta.next()){
                modeloConsulta.addRow(Utiles.fillTableObject(modeloConsulta, rsConsulta));
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    private void traeTurnos(){
        try {
            modeloTurnos.getDataVector().removeAllElements();
            modeloTurnos.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            parametros.add(this.id_liquidacion);
            
            rsTurnos = conexion.procAlmacenado("traeTurnosLiquidacion", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsTurnos.beforeFirst();
            while (rsTurnos.next()){
                int turno = rsTurnos.getInt("id_presupuesto");
                String paciente = rsTurnos.getString("paciente");
                Double importe = rsTurnos.getDouble("importe");
                
                Object[] fila = {turno, paciente, importe};
                
                modeloTurnos.addRow(fila);
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }        
    }
    
    private void traePagos(){
        try {
            modeloPagos.getDataVector().removeAllElements();
            modeloPagos.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            parametros.add(this.id_liquidacion);
            
            rsPagos = conexion.procAlmacenado("traePagosLiquidacion", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsPagos.beforeFirst();
            while (rsPagos.next()){
                String banco = rsPagos.getString("banco");
                String cheque = rsPagos.getString("cheque");
                String vencimiento = rsPagos.getString("vencimiento");
                Double importe = rsPagos.getDouble("importe");
                
                Object[] fila = {banco, cheque, vencimiento, importe};
                
                modeloPagos.addRow(fila);
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    private void reimprime(){
        this.salida.setVisible(true);
        if (salida.getSiNo()){
            this.impresora = salida.getImpresora();
            this.copias = salida.getCopias();
            this.rutaArchivo = salida.getRutaArchivo();
            
            String fecha = modeloConsulta.getValueAt(
                            jTablaConsulta.convertRowIndexToModel(this.jTablaConsulta.getSelectedRow()),
                            Utiles.getColumnByName(modeloConsulta, "fecha")).toString();
            
            SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd"); 
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");            

            Map param=new HashMap();
            param.put("empresa", this.empresa);
            param.put("id_liquidacion", id_liquidacion);
            param.put("profesional", this.profesional);
            try {
                param.put("fecha", sqlDate.parse(fecha));
            } catch (ParseException ex) {
                Logger.getLogger(ConsultaOrdenPago.class.getName()).log(Level.SEVERE, null, ex);
            }
            param.put("dni", this.dni);
            param.put("olp", this.jFormattedTextOLP.getText());

            traeTurnos();
            traePagos();

            LiquidacionProfesionalDataSource data = new LiquidacionProfesionalDataSource();

            if (modeloTurnos.getRowCount() > modeloPagos.getRowCount()){
                for (int i = 0; i < modeloTurnos.getRowCount(); i++){
                    int id_presupuesto = (int) modeloTurnos.getValueAt(i, 0);
                    String paciente = modeloTurnos.getValueAt(i, 1).toString();
                    Double importe = (double) modeloTurnos.getValueAt(i, 2);

                    String banco = "";
                    String cheque = "";
                    Date vencimiento = null;
                    Double valor = 0.00;
                    if (i < modeloPagos.getRowCount()){
                        banco = modeloPagos.getValueAt(i, 0).toString();
                        cheque = modeloPagos.getValueAt(i, 1).toString();

                        if (!modeloPagos.getValueAt(i, 2).toString().isEmpty()){
                            try {
                                vencimiento = formatter.parse(modeloPagos.getValueAt(i, 2).toString());
                            } catch (ParseException ex) {
                                Utiles.enviaError(this.empresa,ex);
                            }
                        }

                        valor = Double.parseDouble(modeloPagos.getValueAt(i, 3).toString());
                    }

                    LiquidacionProfesional nuevo = new LiquidacionProfesional(id_presupuesto, paciente, banco, cheque, importe, valor, vencimiento);

                    data.add(nuevo);
                }
            }else{
                for (int i = 0; i < modeloPagos.getRowCount(); i++){
                    int id_presupuesto = 0;
                    String paciente = "";
                    Double importe = 0.00;
                    String banco = modeloPagos.getValueAt(i, 0).toString();
                    String cheque = modeloPagos.getValueAt(i, 1).toString();

                    Date vencimiento = null;
                    if (!modeloPagos.getValueAt(i, 2).toString().isEmpty()){
                        try {
                            vencimiento = formatter.parse(modeloPagos.getValueAt(i, 2).toString());
                        } catch (ParseException ex) {
                            Utiles.enviaError(this.empresa,ex);
                        }
                    }

                    Double valor = Double.parseDouble(modeloPagos.getValueAt(i, 3).toString());

                    if (i < modeloTurnos.getRowCount()){
                        id_presupuesto = (int) modeloTurnos.getValueAt(i, 0);
                        paciente = modeloTurnos.getValueAt(i, 1).toString();
                        importe  = (double) modeloTurnos.getValueAt(i, 2);
                    }

                    LiquidacionProfesional nuevo = new LiquidacionProfesional(id_presupuesto, paciente, banco, cheque, importe, valor, vencimiento);

                    data.add(nuevo);
                }
            }

            Reporte reporte = new Reporte();
            reporte.startReport("LiquidacionProfesional",param, data, copias, rutaArchivo, impresora, copias);
        }   
    }
    
    private void imprimeLista(){
        try{
            this.salida.setVisible(true);
            if (this.salida.getSiNo()){
                this.impresora = salida.getImpresora();
                this.copias = salida.getCopias();
                this.rutaArchivo = salida.getRutaArchivo();

                Map param=new HashMap();
                param.put("empresa", this.empresa);
                param.put("desde", this.jDesde.getDate());
                param.put("hasta", this.jHasta.getDate());

                ListaLiquidacionDataSource data = new ListaLiquidacionDataSource();

                ArrayList parametros = new ArrayList();
                ArrayList turnos = new ArrayList();
                ArrayList pagos = new ArrayList();

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                for (int i = 0; i < modeloConsulta.getRowCount(); i++){
                    parametros.clear();
                    turnos.clear();
                    pagos.clear();

                    int id_liq = (int) modeloConsulta.getValueAt(i, 2);
                    String prof = modeloConsulta.getValueAt(i, 1).toString();
                    
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha = null;
                    try {
                        fecha = format.parse(modeloConsulta.getValueAt(i, 0).toString());
                    } catch (Exception e) {
                        //Fecha inválida
                    }

                    parametros.add(id_liq);

                    rsTurnos = conexion.procAlmacenado("traeTurnosLiquidacion", parametros,
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                    rsTurnos.beforeFirst();
                    while (rsTurnos.next()){
                        int turno = rsTurnos.getInt("id_presupuesto");
                        String paciente = rsTurnos.getString("paciente");
                        Double importe = rsTurnos.getDouble("importe");

                        Object[] fila = {turno, paciente, importe};

                        turnos.add(fila);
                    }

                    rsPagos = conexion.procAlmacenado("traePagosLiquidacion", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                    rsPagos.beforeFirst();
                    while (rsPagos.next()){
                        String banco = rsPagos.getString("banco");
                        String cheque = rsPagos.getString("cheque");
                        String vencimiento = rsPagos.getString("vencimiento");
                        Double importe = rsPagos.getDouble("importe");

                        Object[] fila = {banco, cheque, vencimiento, importe};

                        pagos.add(fila);
                    }

                    int indice = 0;
                    if (turnos.size() > pagos.size()){
                        Iterator it = turnos.iterator();
                        while (it.hasNext()){
                            Object[] turno = (Object[]) it.next();
                            int id_presupuesto = (int) turno[0];
                            String paciente = turno[1].toString();
                            Double importe = (double) turno[2];

                            String banco = "";
                            String cheque = "";
                            Date vencimiento = null;
                            Double valor = 0.00;

                            if (indice < pagos.size()){
                                Object[] pago = (Object[]) pagos.get(indice);
                                banco = pago[0].toString();
                                cheque = pago[1].toString();

                                if (!pago[2].toString().isEmpty()){
                                    try {
                                        vencimiento = formatter.parse(pago[2].toString());
                                    } catch (ParseException ex) {
                                        Utiles.enviaError(this.empresa,ex);
                                    }
                                }

                                valor = (double) pago[3];
                            }

                            ListaLiquidacion nuevo = new ListaLiquidacion(id_presupuesto, id_liq, paciente, banco, cheque, prof, importe, valor, vencimiento, fecha);
                            data.add(nuevo);

                            indice++;
                        }
                    }else{
                        Iterator it = pagos.iterator();
                        while (it.hasNext()){
                            Object[] pago = (Object[]) it.next();
                            String banco = pago[0].toString();
                            String cheque = pago[1].toString();

                            Date vencimiento = null;
                            if (!pago[2].toString().isEmpty()){
                                try {
                                    vencimiento = formatter.parse(pago[2].toString());
                                } catch (ParseException ex) {
                                    Utiles.enviaError(this.empresa,ex);
                                }
                            }

                            Double valor = (double) pago[3];

                            int id_presupuesto = 0;
                            String paciente = "";
                            Double importe = 0.00;

                            if (indice < turnos.size()){
                                Object[] turno = (Object[]) turnos.get(indice);
                                id_presupuesto = (int) turno[0];
                                paciente = turno[1].toString();
                                importe = (double) turno[2];
                            }

                            ListaLiquidacion nuevo = new ListaLiquidacion(id_presupuesto, id_liq, paciente, banco, cheque, prof, importe, valor, vencimiento, fecha);
                            data.add(nuevo);

                            indice++;
                        }
                    }
                }

                Reporte reporte = new Reporte();
                reporte.startReport("ListaLiquidaciones", param, data, copias, rutaArchivo, impresora, copias);
            }   
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    private boolean validaObligatorios(){
        return this.id_liquidacion > 0;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenuConsulta = new javax.swing.JPopupMenu();
        jDetalleTurno = new javax.swing.JMenuItem();
        jBtnSalir = new javax.swing.JButton();
        jBtnAnular = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jBtnFiltra = new javax.swing.JButton();
        jComboProfesional = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jDesde = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jDesde.getJCalendar().setTodayButtonVisible(true); 
        this.jDesde.getJCalendar().setTodayButtonText("Hoy"); 
        this.jDesde.getJCalendar().setWeekOfYearVisible(false);
        jHasta = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jHasta.getJCalendar().setTodayButtonVisible(true); 
        this.jHasta.getJCalendar().setTodayButtonText("Hoy"); 
        this.jHasta.getJCalendar().setWeekOfYearVisible(false);
        jLabel9 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTablaTurnos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTablaPagos = new javax.swing.JTable();
        jBtnReimprimir = new javax.swing.JButton();
        jBtnListar = new javax.swing.JButton();
        jFormattedTextOLP = new javax.swing.JFormattedTextField();
        jButtonGuardar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jDetalleTurno.setText("Ver turno");
        jDetalleTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetalleTurnoActionPerformed(evt);
            }
        });
        jPopupMenuConsulta.add(jDetalleTurno);

        setClosable(true);
        setMaximizable(true);
        setTitle("Consulta orden de pago");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

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

        jBtnAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/anular.png"))); // NOI18N
        jBtnAnular.setText("Anular liquidación");
        jBtnAnular.setBorderPainted(false);
        jBtnAnular.setContentAreaFilled(false);
        jBtnAnular.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnAnular.setFocusPainted(false);
        jBtnAnular.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/anular.png"))); // NOI18N
        jBtnAnular.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAnularActionPerformed(evt);
            }
        });

        jTablaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Profesional", "Liquidacion", "dni", "OLP"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
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
        jTablaConsulta.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTablaConsulta.getTableHeader().setReorderingAllowed(false);
        jTablaConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaConsultaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaConsulta);
        if (jTablaConsulta.getColumnModel().getColumnCount() > 0) {
            jTablaConsulta.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTablaConsulta.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTablaConsulta.getColumnModel().getColumn(3).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(3).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(3).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(4).setPreferredWidth(200);
        }

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

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

        jLabel8.setText("Desde");

        jLabel9.setText("Hasta");

        jLabel16.setText("Zona");

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
                    .addComponent(jLabel8)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboZona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnFiltra))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jBtnFiltra)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))))
                .addGap(1, 1, 1))
        );

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel10.setText("Doble clic para ver el detalle");

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
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)))
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Turnos aplicados"));

        jTablaTurnos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Turno", "Paciente", "Importe"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaTurnos.setComponentPopupMenu(jPopupMenuConsulta);
        jTablaTurnos.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jTablaTurnos);
        if (jTablaTurnos.getColumnModel().getColumnCount() > 0) {
            jTablaTurnos.getColumnModel().getColumn(0).setMinWidth(75);
            jTablaTurnos.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTablaTurnos.getColumnModel().getColumn(0).setMaxWidth(75);
            jTablaTurnos.getColumnModel().getColumn(2).setMinWidth(50);
            jTablaTurnos.getColumnModel().getColumn(2).setPreferredWidth(50);
            jTablaTurnos.getColumnModel().getColumn(2).setMaxWidth(50);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Detalle de pago"));

        jTablaPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Banco", "Cheque", "Venc.", "Valor"
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
        jTablaPagos.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(jTablaPagos);
        if (jTablaPagos.getColumnModel().getColumnCount() > 0) {
            jTablaPagos.getColumnModel().getColumn(0).setMinWidth(75);
            jTablaPagos.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTablaPagos.getColumnModel().getColumn(0).setMaxWidth(75);
            jTablaPagos.getColumnModel().getColumn(2).setMinWidth(75);
            jTablaPagos.getColumnModel().getColumn(2).setPreferredWidth(75);
            jTablaPagos.getColumnModel().getColumn(2).setMaxWidth(75);
            jTablaPagos.getColumnModel().getColumn(3).setMinWidth(50);
            jTablaPagos.getColumnModel().getColumn(3).setPreferredWidth(50);
            jTablaPagos.getColumnModel().getColumn(3).setMaxWidth(50);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        jBtnReimprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/preview.png"))); // NOI18N
        jBtnReimprimir.setText("Reimprimir liquidación");
        jBtnReimprimir.setBorderPainted(false);
        jBtnReimprimir.setContentAreaFilled(false);
        jBtnReimprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnReimprimir.setFocusPainted(false);
        jBtnReimprimir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/preview.png"))); // NOI18N
        jBtnReimprimir.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnReimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnReimprimirActionPerformed(evt);
            }
        });

        jBtnListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/table.png"))); // NOI18N
        jBtnListar.setText("Listado completo");
        jBtnListar.setBorderPainted(false);
        jBtnListar.setContentAreaFilled(false);
        jBtnListar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnListar.setFocusPainted(false);
        jBtnListar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/table.png"))); // NOI18N
        jBtnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnListarActionPerformed(evt);
            }
        });

        jButtonGuardar.setText("Guardar");
        jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarActionPerformed(evt);
            }
        });

        jLabel1.setText("Factura OLP");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBtnAnular)
                                .addGap(27, 27, 27)
                                .addComponent(jBtnReimprimir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnListar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtnSalir))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jFormattedTextOLP, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButtonGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(149, 149, 149)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFormattedTextOLP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonGuardar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(18, 23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnAnular)
                    .addComponent(jBtnReimprimir)
                    .addComponent(jBtnListar)
                    .addComponent(jBtnSalir))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        try {
            if (this.rsConsulta != null){
                this.rsConsulta.close();
            }
            if (this.rsTurnos != null){
                this.rsTurnos.close();
            }
            if (this.rsPagos != null){
                this.rsPagos.close();
            }
            this.dispose();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAnularActionPerformed
        if (validaObligatorios()){
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Confirma la anulación de la liquidación N° " + this.id_liquidacion + "?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                try{
                    ArrayList parametros = new ArrayList();
                    
                    parametros.add(this.id_liquidacion);
                    parametros.add(this.id_empresa);
                    
                    conexion.procAlmacenado("anulaLiquidacion", parametros, 
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    
                    limpia();
                } catch (NumberFormatException | HeadlessException ex) {
                    Utiles.enviaError(this.empresa,ex);
                } 
            }     
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione la liquidación que desea anular", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_jBtnAnularActionPerformed

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        consulta();
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jComboProfesionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboProfesionalActionPerformed
        modeloConsulta.getDataVector().removeAllElements();
        modeloConsulta.fireTableDataChanged();             
    }//GEN-LAST:event_jComboProfesionalActionPerformed

    private void jTablaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsultaMouseClicked
        int indice = jTablaConsulta.convertRowIndexToModel(this.jTablaConsulta.getSelectedRow());
        this.id_liquidacion = Integer.parseInt(modeloConsulta.getValueAt(indice, Utiles.getColumnByName(modeloConsulta, "Liquidacion")).toString());
        this.profesional = modeloConsulta.getValueAt(indice, Utiles.getColumnByName(modeloConsulta, "profesional")).toString();
        this.dni = modeloConsulta.getValueAt(indice, Utiles.getColumnByName(modeloConsulta, "dni")).toString();
        this.jFormattedTextOLP.setText(modeloConsulta.getValueAt(indice, Utiles.getColumnByName(modeloConsulta, "olp")).toString());
        
        if (evt.getClickCount() == 2){
            traeTurnos();
            traePagos();
        }
    }//GEN-LAST:event_jTablaConsultaMouseClicked

    private void jTxtBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaFocusGained
        this.jTxtBusca.select(0, this.jTxtBusca.getText().length());
    }//GEN-LAST:event_jTxtBuscaFocusGained

    private void jBtnReimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnReimprimirActionPerformed
        if (validaObligatorios()){
            reimprime();
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione la liquidación que desea reimprimir", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnReimprimirActionPerformed

    private void jBtnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnListarActionPerformed
        if (this.modeloConsulta.getRowCount() > 0){
            imprimeLista();
        }else{
            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnListarActionPerformed

    private void jDetalleTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetalleTurnoActionPerformed
        int fila = jTablaTurnos.convertRowIndexToModel(jTablaTurnos.getSelectedRow());
        if (fila >= 0){
            Turno turno = new Turno(null, true, conexion, id_empresa, empresa);
            turno.setId_presupuesto(modeloTurnos.getValueAt(fila, 0).toString());
            turno.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetalleTurnoActionPerformed

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        try {
            int indiceZona = this.jComboZona.getSelectedIndex();
            llenaComboProfesional(indiceZona);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jComboZonaActionPerformed

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

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
        
        String factura = Utiles.v(this.jFormattedTextOLP.getText()).trim();
        LiquidacionOLP olp = LiquidacionOLP.findById(this.id_liquidacion);
        TrackingolpRecord trackingolpRecord = new TrackingolpRecord();
        trackingolpRecord.attach(ActiveDatabase.getDSLContext().configuration());
        if (olp != null){
            try{
                olp.set("olp", factura).saveIt();
            }
            catch(Exception e){
                Utiles.showErrorMessage(e);
            }

        }
        else{
            olp = new LiquidacionOLP();
            olp.set("id", this.id_liquidacion);
            olp.set("olp", factura);
            olp.insert();
        }
        trackingolpRecord.setIdLiquidacion(this.id_liquidacion);
        trackingolpRecord.store();

        consulta();
    }//GEN-LAST:event_jButtonGuardarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAnular;
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnListar;
    private javax.swing.JButton jBtnReimprimir;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JComboBox jComboProfesional;
    private javax.swing.JComboBox jComboZona;
    private com.toedter.calendar.JDateChooser jDesde;
    private javax.swing.JMenuItem jDetalleTurno;
    private javax.swing.JFormattedTextField jFormattedTextOLP;
    private com.toedter.calendar.JDateChooser jHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenuConsulta;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTablaConsulta;
    private javax.swing.JTable jTablaPagos;
    private javax.swing.JTable jTablaTurnos;
    private javax.swing.JTextField jTxtBusca;
    // End of variables declaration//GEN-END:variables
}
