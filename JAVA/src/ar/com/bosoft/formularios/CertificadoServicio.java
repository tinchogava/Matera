/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import matera.gui.dialog.RemitoDetalle;
import ar.com.bosoft.DataSources.PresupuestoProdDataSource;
import ar.com.bosoft.Modelos.PresupuestoProd;
import ar.com.bosoft.RenderTablas.CalificacionCertificado;
import ar.com.bosoft.RenderTablas.DateRenderer;
import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.buscadores.General1;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.PrestacionCRUD;
import ar.com.bosoft.crud.TecnicoCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.PrestacionData;
import ar.com.bosoft.data.TecnicoData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.vistasRapidas.Turno;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import matera.gui.combobox.ComboBoxMgr;
import matera.jooq.tables.records.PrestacionRecord;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class CertificadoServicio extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, id_nivelesCertificado, id_tecnico, id_prestacion;
    double importePrestacion;
    String empresa, usuario, todos;
    
    ResultSet rsConsulta, rsPresupuestoProd;
    DefaultTableModel modeloConsulta, modeloProducto;
    TableRowSorter sorterConsulta, sorterProducto;
    
    PrestacionCRUD prestacionCRUD;
    TecnicoCRUD tecnicoCRUD;
    ZonaCRUD zonaCRUD;
    
    ArrayList prestacionArray, tecnicoArray, zonaArray;
    
    Salida salida;
    
    TableCellRenderer calificacionCertificado, tableCellRenderer;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
    General1 general1 = new General1(null, true);
    
    public CertificadoServicio(Conexion conexion, int id_empresa, String empresa, int id_usuario, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        
        this.salida = new Salida(null, true);
        this.calificacionCertificado = new CalificacionCertificado();
        
         this.todos = "-- TODOS --";
        
        this.prestacionCRUD = new PrestacionCRUD(conexion, id_empresa, empresa);
        this.tecnicoCRUD = new TecnicoCRUD(conexion, id_empresa, empresa);
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        
        initComponents();
        
        tableCellRenderer = new DateRenderer();

        modeloConsulta = (DefaultTableModel) this.jTablaConsulta.getModel();
        jTablaConsulta.setModel(modeloConsulta);
        jTablaConsulta.setRowSorter (new TableRowSorter(modeloConsulta));
        sorterConsulta = new TableRowSorter(modeloConsulta);
        try{
            this.jTablaConsulta.setDefaultRenderer( Class.forName( "java.lang.Integer" ), calificacionCertificado );
        }catch(ClassNotFoundException cnf){}
        jTablaConsulta.getColumnModel().getColumn(5).setCellRenderer(tableCellRenderer);
        
        modeloProducto = (DefaultTableModel) this.jTablaProducto.getModel();
        jTablaProducto.setModel(modeloProducto);
        
        setJTexFieldChanged(this.jTxtBuscaConsulta);
        
        llenaComboPrestacion();
        llenaComboTecnico();
        llenaComboZona();
        ComboBoxMgr.fillPrestacionCombo(jComboModificaPrestacion, false);
        ComboBoxMgr.fillTecnicoCombo(jComboModificaTecnico, false);
        //llenaComboPrestacionChange();
        //llenaComboTecnicoChange() ;
        limpia();
        zonaUsuario();
        
//        this.jCheckControlado.setEnabled(id_usuario == 10 ||    //QUATTRINI. GERMAN MAURICIO
//                                            id_usuario == 14 || //VACCARONE DANILO EDGARDO
//                                            id_usuario == 16 || //VACCARONE MARIO EDGARDO
//                                            id_usuario == 75);  //ORELLANO MOFFICONE MARIA CARLA
        this.jCheckControlado.setVisible(false);
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
    
    private void llenaComboPrestacion(){
        this.jComboPrestacion.addItem(this.todos);
        prestacionArray = prestacionCRUD.consulta(true);
        Iterator i = prestacionArray.iterator();
        while (i.hasNext()){
            PrestacionData p = (PrestacionData) i.next();
            this.jComboPrestacion.addItem(p.getNombre());
        }
    }
    /*
    private void llenaComboPrestacionChange(){
        prestacionArray = prestacionCRUD.consultaSinOrden(true);
        Iterator i = prestacionArray.iterator();
        PrestacionData noP = new PrestacionData();
        noP.setId_prestacion(0);
        noP.setNombre("Sin Prestación");
        this.jComboPrestacionChange.addItem(noP.getNombre());
        while (i.hasNext()){
            PrestacionData p = (PrestacionData) i.next();
            this.jComboPrestacionChange.addItem(p.getNombre());
        }
    }
*/
    
    private void llenaComboTecnico(){
        this.jComboTecnico.addItem(this.todos);
        tecnicoArray = tecnicoCRUD.consulta(true);
        Iterator i = tecnicoArray.iterator();
        while (i.hasNext()){
            TecnicoData t = (TecnicoData) i.next();
            this.jComboTecnico.addItem(t.getNombre());
        }
    }
    /*
    private void llenaComboTecnicoChange(){
        tecnicoArray = tecnicoCRUD.consultaSinOrden(true);
        Iterator i = tecnicoArray.iterator();
        TecnicoData noT = new TecnicoData();
        noT.setId_tecnico(0);
        noT.setNombre("Sin Técnico");
        this.jComboTecnicoChange.addItem(noT.getNombre());
        while (i.hasNext()){
            TecnicoData t = (TecnicoData) i.next();
            this.jComboTecnicoChange.addItem(t.getNombre());
        }
    }
    */
    private void llenaComboZona(){
        this.jComboZona.addItem(this.todos);
        zonaArray = zonaCRUD.consulta(true);
        Iterator i = zonaArray.iterator();
        while (i.hasNext()){
            ZonaData z = (ZonaData) i.next();
            this.jComboZona.addItem(z.getNombre());
        }
    }
    
    private void limpia(){
        this.jDesde.setDate(null);
        this.jHasta.setDate(null);
        this.jComboPrestacion.setSelectedItem(this.todos);
        this.jComboTecnico.setSelectedItem(this.todos);
        
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedItem(this.todos);
        }
        
        this.jComboMuestra.setSelectedIndex(0);
        this.jComboMuestraActionPerformed(null);
        this.jTxtBuscaConsulta.setText("");
        this.jLblContador.setText("");
        this.jLblTurno.setText("");
        this.jLblProfesional.setText("");
        this.jLblFecha.setText("");
        this.jLblPaciente.setText("");
        this.jLblEntidad.setText("");
        this.jLblLugar.setText("");
        this.jComboModificaTecnico.setEnabled(false);
        this.jComboModificaPrestacion.setEnabled(false);
        this.jComboModificaTecnico.setSelectedIndex(-1);
        this.jComboModificaPrestacion.setSelectedIndex(-1);
        this.jComboAlternativas.setSelectedIndex(-1);
        this.jComboAsistencia.setSelectedIndex(-1);
        this.jComboInstrumental.setSelectedIndex(-1);
        this.jComboPuntualidad.setSelectedIndex(-1);
        this.jBtnConsumido.setEnabled(false);
        modeloProducto.getDataVector().removeAllElements();
        modeloProducto.fireTableDataChanged();
        this.jTxtObservaciones.setText("");
        this.jCheckControlado.setSelected(false);
        
        this.id_nivelesCertificado = 0;
        this.id_tecnico = 0;
        this.importePrestacion = 0.00;
    }
    
    private void consulta(){
        try{
            modeloConsulta.getDataVector().removeAllElements();
            modeloConsulta.fireTableDataChanged();
            
            long desde = this.jDesde.getDate() == null ? (long) 0 : this.jDesde.getDate().getTime();
            long hasta = this.jHasta.getDate() == null ? (long) 0 : this.jHasta.getDate().getTime();
            
            int id_zona = 0;
            if (this.jComboZona.getSelectedIndex() > 0) {
                ZonaData z = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
                id_zona = z.getId_zona();
            }
            
            int id_prestacion = 0;
            if (this.jComboPrestacion.getSelectedIndex() > 0) {
                PrestacionData p = (PrestacionData) prestacionArray.get(this.jComboPrestacion.getSelectedIndex() - 1);
                id_prestacion = p.getId_prestacion();
            }
            
            int id_t = 0;
            if (this.jComboTecnico.getSelectedIndex() > 0) {
                TecnicoData t = (TecnicoData) tecnicoArray.get(this.jComboTecnico.getSelectedIndex() - 1);
                id_t = t.getId_tecnico();
            }
            
            int muestra = this.jComboMuestra.getSelectedIndex();
            
            String calificaciones = "";
            if (this.jCheckMalo.isSelected()) {
                calificaciones += "0";
            }
            
            if (this.jCheckRegular.isSelected()) {
                calificaciones += (!calificaciones.isEmpty() ? "," : "") + "1";
            }
            
            if (this.jCheckBueno.isSelected()) {
                calificaciones += (!calificaciones.isEmpty() ? "," : "") + "2";
            }
            
            if (this.jCheckExcelente.isSelected()) {
                calificaciones += (!calificaciones.isEmpty() ? "," : "") + "3";
            }
            
            ArrayList parametros = new ArrayList();
            parametros.add(desde);
            parametros.add(hasta);
            parametros.add(id_zona);
            parametros.add(id_prestacion);
            parametros.add(id_t);
            parametros.add(muestra);
            parametros.add(calificaciones);
            parametros.add(this.id_empresa);
            rsConsulta = conexion.procAlmacenado("consultaPresuCertificado", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsConsulta.beforeFirst();
            while (rsConsulta.next()){
                int alternativas = rsConsulta.getInt("alternativas");
                int instrumental = rsConsulta.getInt("instrumental");
                int asistencia = rsConsulta.getInt("asistencia");
                int puntualidad = rsConsulta.getInt("puntualidad");
                
                int id_presupuesto = rsConsulta.getInt("id_presupuesto");
                Date fechaCirugia = rsConsulta.getDate("fechaCirugia");
                String paciente = rsConsulta.getString("paciente");
                String profesional = rsConsulta.getString("profesional");
                String entidad = rsConsulta.getString("entidad");
                String lugarCirugia = rsConsulta.getString("lugarCirugia");
                String prestacion = rsConsulta.getString("prestacion");
                String tecnico = rsConsulta.getString("tecnico");
                String observaciones = rsConsulta.getString("observaciones");
                int id_nivelesCert = rsConsulta.getInt("id_nivelesCertificado");
                double importePres = rsConsulta.getInt("importePrestacion");
                int pres_id_tec = rsConsulta.getInt("id_tecnico");
                int pres_id_prest = rsConsulta.getInt("id_prestacion");
                
                Object[] fila = {alternativas, instrumental, asistencia, puntualidad, id_presupuesto, 
                                    fechaCirugia, paciente, profesional, entidad, lugarCirugia, 
                                    prestacion, tecnico, observaciones, id_nivelesCert, importePres,
                                    pres_id_tec, pres_id_prest};
                modeloConsulta.addRow(fila);
            }
            
            this.jLblContador.setText("Hay " + modeloConsulta.getRowCount() + " registros");
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    private void consultaProductos(int id_presupuesto){
        try{
            modeloProducto.getDataVector().removeAllElements();
            modeloProducto.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            parametros.add(id_presupuesto);
            rsPresupuestoProd = conexion.procAlmacenado("traePresupuestoProd", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsPresupuestoProd.beforeFirst();
            while (rsPresupuestoProd.next()){
                int cantidad = rsPresupuestoProd.getInt("cantidad");
                String codigo = rsPresupuestoProd.getString("codigo");
                String nombre = rsPresupuestoProd.getString("producto");
                String observaciones = rsPresupuestoProd.getString("observaciones");
                String proveedor = rsPresupuestoProd.getString("proveedor");
                Object[] fila = {cantidad, codigo, nombre, observaciones, proveedor};
                modeloProducto.addRow(fila);                
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
            JTxtBuscaConsultaChanged();
        }else if (type.equals(DocumentEvent.EventType.REMOVE)){
            JTxtBuscaConsultaChanged();
        }
    }
 
    private void JTxtBuscaConsultaChanged(){
        String text = jTxtBuscaConsulta.getText().trim().replaceAll("'", "´");
        if (text.length() == 0) {
          sorterConsulta.setRowFilter(null);
        } else {
          sorterConsulta.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTablaConsulta.setRowSorter(sorterConsulta);
        }
    }
    
    private void imprime(int tipoSalida){
        try {
            Map param=new HashMap();
            
            Date fecha = formatter.parse(this.jLblFecha.getText());
            param.put("id_presupuesto", Integer.parseInt(this.jLblTurno.getText()));
            param.put("fechaCx", fecha);
            param.put("profesional", this.jLblProfesional.getText());
            param.put("entidad", this.jLblEntidad.getText());
            param.put("paciente", this.jLblPaciente.getText());
            param.put("lugarCx", this.jLblLugar.getText());
            param.put("observaciones", this.jTxtObservaciones.getText().trim());
            param.put("tecnico", this.jComboModificaTecnico.getSelectedItem().toString());
            param.put("alternativa", this.jComboAlternativas.getSelectedIndex());
            param.put("instrumental", this.jComboInstrumental.getSelectedIndex());
            param.put("asistencia", this.jComboAsistencia.getSelectedIndex());
            param.put("puntualidad", this.jComboPuntualidad.getSelectedIndex());
            param.put("empresa", this.empresa);
            
            PresupuestoProdDataSource dataPresupuestoProd = new PresupuestoProdDataSource();
            
            for (int i = 0; i < modeloProducto.getRowCount(); i++){
                int cantidad = Integer.parseInt(modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProducto, "cant.")).toString());
                String codigo = modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProducto, "codigo")).toString();
                String nombre = modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProducto, "nombre")).toString();
                String obsProducto = modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProducto, "detalle")).toString();
                BigDecimal pxUnit = BigDecimal.ZERO;
                
                PresupuestoProd presupuestoProd = new PresupuestoProd(cantidad, codigo, nombre, obsProducto, pxUnit);
                dataPresupuestoProd.addPresupuestoProd(presupuestoProd);
            }
            
            Reporte reporte = new Reporte();
            reporte.startReport("CertificadoServicio",param, dataPresupuestoProd, tipoSalida, salida.getRutaArchivo(), salida.getImpresora(), salida.getCopias());
        } catch (ParseException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    private boolean validaObligatorios(){
        return !this.jLblTurno.getText().isEmpty() &&
                this.jComboAlternativas.getSelectedIndex() >= 0 &&
                this.jComboInstrumental.getSelectedIndex() >= 0 &&
                this.jComboAsistencia.getSelectedIndex() >= 0 &&
                this.jComboPuntualidad.getSelectedIndex() >= 0;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupAlternativas = new javax.swing.ButtonGroup();
        groupInstrumental = new javax.swing.ButtonGroup();
        groupAsistencia = new javax.swing.ButtonGroup();
        groupPuntualidad = new javax.swing.ButtonGroup();
        jPopupMenu = new javax.swing.JPopupMenu();
        jExcel = new javax.swing.JMenuItem();
        jDetalleTurno = new javax.swing.JMenuItem();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();
        jBtnSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLblFecha = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLblProfesional = new javax.swing.JLabel();
        jLblPaciente = new javax.swing.JLabel();
        jLblEntidad = new javax.swing.JLabel();
        jBtnGuardar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLblLugar = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablaProducto = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jTxtBuscaConsulta = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLblTurno = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jComboAlternativas = new javax.swing.JComboBox();
        jComboInstrumental = new javax.swing.JComboBox();
        jComboAsistencia = new javax.swing.JComboBox();
        jComboPuntualidad = new javax.swing.JComboBox();
        jBtnConsumido = new javax.swing.JButton();
        jCheckControlado = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jDesde = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jDesde.getJCalendar().setTodayButtonVisible(true); 
        this.jDesde.getJCalendar().setTodayButtonText("Hoy"); 
        this.jDesde.getJCalendar().setWeekOfYearVisible(false);
        jHasta = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jHasta.getJCalendar().setTodayButtonVisible(true); 
        this.jHasta.getJCalendar().setTodayButtonText("Hoy"); 
        this.jHasta.getJCalendar().setWeekOfYearVisible(false);
        jLabel11 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jComboPrestacion = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jComboTecnico = new javax.swing.JComboBox();
        jBtnFiltra = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jComboMuestra = new javax.swing.JComboBox();
        jPanelCalificaciones = new javax.swing.JPanel();
        jCheckMalo = new javax.swing.JCheckBox();
        jCheckRegular = new javax.swing.JCheckBox();
        jCheckBueno = new javax.swing.JCheckBox();
        jCheckExcelente = new javax.swing.JCheckBox();
        jCheckTodos = new javax.swing.JCheckBox();
        jLblContador = new javax.swing.JLabel();
        jComboModificaPrestacion = new javax.swing.JComboBox();
        jComboModificaTecnico = new javax.swing.JComboBox();

        jExcel.setText("Exportar a Excel");
        jExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jExcelActionPerformed(evt);
            }
        });
        jPopupMenu.add(jExcel);

        jDetalleTurno.setText("Turno");
        jDetalleTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetalleTurnoActionPerformed(evt);
            }
        });
        jPopupMenu.add(jDetalleTurno);

        setTitle("Certificado de servicio");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jTxtObservaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Observaciones"));
        jScrollPane5.setViewportView(jTxtObservaciones);

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

        jLabel1.setText("Paciente");

        jLabel2.setText("Profesional");

        jLabel3.setText("Entidad");

        jLblFecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblFecha.setText("...");

        jLabel5.setText("Fecha de cirugía");

        jLblProfesional.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblProfesional.setText("...");

        jLblPaciente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblPaciente.setText("...");

        jLblEntidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblEntidad.setText("...");

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

        jLabel6.setText("Lugar de cirugía");

        jLblLugar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblLugar.setText("...");

        jLabel7.setText("Prestación");

        jLabel10.setText("Técnico");

        jTablaProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cant.", "Código", "Nombre", "Detalle", "Proveedor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane2.setViewportView(jTablaProducto);
        if (jTablaProducto.getColumnModel().getColumnCount() > 0) {
            jTablaProducto.getColumnModel().getColumn(0).setMinWidth(75);
            jTablaProducto.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTablaProducto.getColumnModel().getColumn(0).setMaxWidth(75);
            jTablaProducto.getColumnModel().getColumn(1).setMinWidth(120);
            jTablaProducto.getColumnModel().getColumn(1).setPreferredWidth(120);
            jTablaProducto.getColumnModel().getColumn(1).setMaxWidth(120);
        }

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setFocusable(false);

        jTxtBuscaConsulta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaConsultaFocusGained(evt);
            }
        });

        jLabel8.setText("Buscar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBuscaConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, 884, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBuscaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)))
        );

        jTablaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Alt.", "Inst.", "Asis.", "Punt.", "Turno", "Fecha Cx", "Paciente", "Profesional", "Entidad", "Lugar Cx", "Prestación", "Técnico", "observaciones", "id_nivelesCertificado", "importePrestacion", "id_tecnico", "id_prest"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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
        jTablaConsulta.setComponentPopupMenu(jPopupMenu);
        jTablaConsulta.getTableHeader().setReorderingAllowed(false);
        jTablaConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaConsultaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaConsulta);
        if (jTablaConsulta.getColumnModel().getColumnCount() > 0) {
            jTablaConsulta.getColumnModel().getColumn(0).setMinWidth(36);
            jTablaConsulta.getColumnModel().getColumn(0).setPreferredWidth(36);
            jTablaConsulta.getColumnModel().getColumn(0).setMaxWidth(36);
            jTablaConsulta.getColumnModel().getColumn(1).setMinWidth(36);
            jTablaConsulta.getColumnModel().getColumn(1).setPreferredWidth(36);
            jTablaConsulta.getColumnModel().getColumn(1).setMaxWidth(36);
            jTablaConsulta.getColumnModel().getColumn(2).setMinWidth(36);
            jTablaConsulta.getColumnModel().getColumn(2).setPreferredWidth(36);
            jTablaConsulta.getColumnModel().getColumn(2).setMaxWidth(36);
            jTablaConsulta.getColumnModel().getColumn(3).setMinWidth(36);
            jTablaConsulta.getColumnModel().getColumn(3).setPreferredWidth(36);
            jTablaConsulta.getColumnModel().getColumn(3).setMaxWidth(36);
            jTablaConsulta.getColumnModel().getColumn(4).setPreferredWidth(50);
            jTablaConsulta.getColumnModel().getColumn(4).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(5).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(5).setMaxWidth(100);
            jTablaConsulta.getColumnModel().getColumn(12).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(12).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(12).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(13).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(13).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(13).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(14).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(14).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(14).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(15).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(15).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(15).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(16).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(16).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(16).setMaxWidth(0);
        }

        jLabel9.setText("Turno");

        jLblTurno.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblTurno.setText("...");

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Calificación"));

        jLabel12.setText("Set de alternativas");

        jLabel13.setText("Set de instrumental");

        jLabel14.setText("Asistencia técnica");

        jLabel15.setText("Puntualidad");

        jComboAlternativas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MALO", "REGULAR", "BUENO", "EXCELENTE" }));

        jComboInstrumental.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MALO", "REGULAR", "BUENO", "EXCELENTE" }));

        jComboAsistencia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MALO", "REGULAR", "BUENO", "EXCELENTE" }));

        jComboPuntualidad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MALO", "REGULAR", "BUENO", "EXCELENTE" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboAlternativas, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboInstrumental, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboAsistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboPuntualidad, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jComboAlternativas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jComboInstrumental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jComboAsistencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jComboPuntualidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1))
        );

        jBtnConsumido.setText("Consumido");
        jBtnConsumido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnConsumidoActionPerformed(evt);
            }
        });

        jCheckControlado.setText("Controlado");

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Desde");

        jLabel11.setText("Hasta");

        jLabel18.setText("Prestación");

        jLabel19.setText("Técnico");

        jComboTecnico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboTecnicoMouseClicked(evt);
            }
        });

        jBtnFiltra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/filtrar.png"))); // NOI18N
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

        jLabel16.setText("Zona");

        jLabel17.setText("Muestra");

        jComboMuestra.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- TODOS --", "Con calificación", "Sin calificación" }));
        jComboMuestra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboMuestraActionPerformed(evt);
            }
        });

        jPanelCalificaciones.setBackground(new java.awt.Color(153, 204, 255));
        jPanelCalificaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Calificaciones"));

        jCheckMalo.setText("Malo");
        jCheckMalo.setContentAreaFilled(false);

        jCheckRegular.setText("Regular");
        jCheckRegular.setContentAreaFilled(false);

        jCheckBueno.setText("Bueno");
        jCheckBueno.setContentAreaFilled(false);

        jCheckExcelente.setText("Excelente");
        jCheckExcelente.setContentAreaFilled(false);

        jCheckTodos.setText("TODOS");
        jCheckTodos.setContentAreaFilled(false);
        jCheckTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckTodosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCalificacionesLayout = new javax.swing.GroupLayout(jPanelCalificaciones);
        jPanelCalificaciones.setLayout(jPanelCalificacionesLayout);
        jPanelCalificacionesLayout.setHorizontalGroup(
            jPanelCalificacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCalificacionesLayout.createSequentialGroup()
                .addGroup(jPanelCalificacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBueno)
                    .addComponent(jCheckMalo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCalificacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckRegular)
                    .addComponent(jCheckExcelente)))
            .addComponent(jCheckTodos)
        );
        jPanelCalificacionesLayout.setVerticalGroup(
            jPanelCalificacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCalificacionesLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanelCalificacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckMalo)
                    .addComponent(jCheckRegular))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCalificacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBueno)
                    .addComponent(jCheckExcelente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckTodos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel16)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboPrestacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboTecnico, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboMuestra, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboZona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelCalificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnFiltra, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboPrestacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboMuestra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnFiltra))
                    .addComponent(jPanelCalificaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLblContador.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblContador.setText("...");

        jComboModificaPrestacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboModificaPrestacionMouseClicked(evt);
            }
        });

        jComboModificaTecnico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboModificaTecnicoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jCheckControlado)
                                        .addGap(382, 382, 382)
                                        .addComponent(jBtnGuardar)
                                        .addGap(194, 194, 194)
                                        .addComponent(jBtnSalir)
                                        .addGap(91, 91, 91))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel10))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLblTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(101, 101, 101)
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLblPaciente)
                                            .addComponent(jLblProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLblEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLblLugar, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboModificaPrestacion, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboModificaTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jBtnConsumido)))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 944, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLblContador, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 944, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLblContador)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLblTurno)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5)
                            .addComponent(jLblFecha))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLblPaciente))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLblProfesional)
                            .addComponent(jLabel2))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLblEntidad)
                            .addComponent(jLabel3))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLblLugar)
                            .addComponent(jLabel6))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jBtnConsumido)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jComboModificaPrestacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(jComboModificaTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8))))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnSalir)
                    .addComponent(jCheckControlado))
                .addGap(1, 1, 1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        try {
            if (this.rsConsulta != null){
                this.rsConsulta.close();
            }
            if (this.rsPresupuestoProd != null){
                this.rsPresupuestoProd.close();
            }
            this.dispose();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios()){
            try{
                ArrayList parametros = new ArrayList();
                
                int id_presupuesto = Integer.parseInt(this.jLblTurno.getText());
                int new_id_tec = ComboBoxMgr.getSelectedId(this.jComboModificaTecnico);
                int new_id_prest = ComboBoxMgr.getSelectedId(this.jComboModificaPrestacion);
                
                parametros.add(id_presupuesto);
                parametros.add(this.jTxtObservaciones.getText().trim());
                parametros.add(new_id_prest);
                parametros.add(new_id_tec);
                
                conexion.procAlmacenado("updateObservacionesPresupuesto", parametros,
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                
                int alternativas = this.jComboAlternativas.getSelectedIndex();
                int instrumental = this.jComboInstrumental.getSelectedIndex();
                int asistencia = this.jComboAsistencia.getSelectedIndex();
                int puntualidad = this.jComboPuntualidad.getSelectedIndex();
                
                parametros.clear();
                parametros.add(this.id_nivelesCertificado);
                parametros.add(id_presupuesto);
                parametros.add(alternativas);
                parametros.add(instrumental);
                parametros.add(asistencia);
                parametros.add(puntualidad);
//                parametros.add(this.jCheckControlado.isSelected() ? 'S' : 'N');
                parametros.add('S');
                parametros.add(this.usuario);
                
                conexion.procAlmacenado("insertNivelesCertificado", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                
                if (this.id_tecnico > 0) {
                    parametros.clear();

                    Long fecha = new Date().getTime();
                    String dc = "C";
                    String observaciones = "";

                    parametros.add(fecha);
                    parametros.add(id_presupuesto);
                    parametros.add(id_tecnico);
                    parametros.add(dc);
                    parametros.add(importePrestacion);
                    parametros.add(observaciones);
                    parametros.add(this.id_empresa);
                    parametros.add(this.usuario);

                    conexion.procAlmacenado("insertMayorTecnico", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                }
                
                int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea imprimir el certificado de servicios?", "BOSOFT", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION){
                    this.salida.setVisible(true);
                    if (salida.getSiNo()){
                        imprime(salida.getTipoSalida());
                    }
                }
                
                limpia();
                consulta();
                this.jTxtBuscaConsulta.requestFocus();
            }catch (NumberFormatException ex){
                Utiles.enviaError(this.empresa,ex);
            }
        }else{
            JOptionPane.showMessageDialog(this,"Complete todos los campos obligatorios", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jTxtBuscaConsultaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaConsultaFocusGained
        this.jTxtBuscaConsulta.select(0, this.jTxtBuscaConsulta.getText().length());
    }//GEN-LAST:event_jTxtBuscaConsultaFocusGained

    private void jTablaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsultaMouseClicked
        if (evt.getClickCount() == 2) {
            int indice = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
            
            int alternativas = ((int)modeloConsulta.getValueAt(indice, 0) == 99 ? -1 : (int)modeloConsulta.getValueAt(indice, 0));
            int instrumental = ((int)modeloConsulta.getValueAt(indice, 1) == 99 ? -1 : (int)modeloConsulta.getValueAt(indice, 1));
            int asistencia = ((int)modeloConsulta.getValueAt(indice, 2) == 99 ? -1 : (int)modeloConsulta.getValueAt(indice, 2));
            int puntualidad = ((int)modeloConsulta.getValueAt(indice, 3) == 99 ? -1 : (int)modeloConsulta.getValueAt(indice, 3));
            
            this.jComboAlternativas.setSelectedIndex(alternativas);
            this.jComboInstrumental.setSelectedIndex(instrumental);
            this.jComboAsistencia.setSelectedIndex(asistencia);
            this.jComboPuntualidad.setSelectedIndex(puntualidad);
            
            if (Principal.usuarioData.getId_usuario() == 10 || //QUATTRINI GERMAN MAURICIO
                Principal.usuarioData.getId_usuario() == 78 || //CICCIOLI NICOLAS
                Principal.usuarioData.getId_usuario() == 32 || //BEROIZA SEBASTIAN
                Principal.usuarioData.getId_usuario() == 88 || //DE LOS SANTOS ALDO
                Principal.usuarioData.getId_usuario() == 75){  //ORELLANO CARLA
                    this.jComboModificaTecnico.setEnabled(true);
                    this.jComboModificaPrestacion.setEnabled(true);
             }
            
            this.jLblTurno.setText(modeloConsulta.getValueAt(indice, 4).toString());
            this.jLblFecha.setText(formatter.format((Date) modeloConsulta.getValueAt(indice, 5)));
            this.jLblPaciente.setText(modeloConsulta.getValueAt(indice, 6).toString());
            this.jLblProfesional.setText(modeloConsulta.getValueAt(indice, 7).toString());
            this.jLblEntidad.setText(modeloConsulta.getValueAt(indice, 8).toString());
            this.jLblLugar.setText(modeloConsulta.getValueAt(indice, 9).toString());   
            //this.jLblPrestacion.setText(modeloConsulta.getValueAt(indice, 10).toString());
            //this.jLblTecnico.setText(modeloConsulta.getValueAt(indice, 11).toString());
            this.jTxtObservaciones.setText(modeloConsulta.getValueAt(indice, 12).toString());
            this.id_nivelesCertificado = (int) modeloConsulta.getValueAt(indice, 13);
            this.importePrestacion = (double) modeloConsulta.getValueAt(indice, 14);
            this.id_tecnico = (int) modeloConsulta.getValueAt(indice, 15);
            this.id_prestacion = (int) modeloConsulta.getValueAt(indice, 16);
            if(this.id_prestacion > 0){
                ComboBoxMgr.setSelectedItemById(jComboModificaPrestacion, id_prestacion);
            }
            if(this.id_tecnico > 0){
                ComboBoxMgr.setSelectedItemById(jComboModificaTecnico, this.id_tecnico);
            }
            consultaProductos(Integer.parseInt(this.jLblTurno.getText()));
            this.jBtnConsumido.setEnabled(true);
        }
    }//GEN-LAST:event_jTablaConsultaMouseClicked

    private void jBtnConsumidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnConsumidoActionPerformed
        String id = this.jLblTurno.getText();
        String fechaCirugia = this.jLblFecha.getText();
        String prestacion = this.jComboModificaTecnico.getSelectedItem().toString();
        String tecnico = this.jComboModificaPrestacion.getSelectedItem().toString();
        String entidad = this.jLblEntidad.getText();
        String lugarCx = this.jLblLugar.getText();
        String profesional = this.jLblProfesional.getText();
        String paciente = this.jLblPaciente.getText();
        String observaciones = this.jTxtObservaciones.getText();
        RemitoDetalle remitoDetalle = new RemitoDetalle(null, false, Integer.parseInt(id));
        remitoDetalle.setDatos();
        remitoDetalle.setVisible(true);
    }//GEN-LAST:event_jBtnConsumidoActionPerformed

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        consulta();
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jComboMuestraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboMuestraActionPerformed
        Utiles.setEnableContainer(this.jPanelCalificaciones, (this.jComboMuestra.getSelectedIndex() == 1));
        this.jCheckTodos.setSelected(true);  
        this.jCheckTodosActionPerformed(evt);
    }//GEN-LAST:event_jComboMuestraActionPerformed

    private void jExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jExcelActionPerformed
        if (modeloConsulta.getRowCount() > 0) {
            //Crear un objeto FileChooser
            JFileChooser fc = new JFileChooser();
            //Mostrar la ventana para abrir archivo y recoger la respuesta
            int respuesta = fc.showSaveDialog(null);
            //Comprobar si se ha pulsado Aceptar
            if (respuesta == JFileChooser.APPROVE_OPTION){
                try {
                //Crear un objeto File con el archivo elegido
                    File file = new File(fc.getSelectedFile().getCanonicalPath() + ".xls");
                    WritableWorkbook workbook1 = Workbook.createWorkbook(file);
                    WritableSheet sheet1 = workbook1.createSheet("Hoja", 0); 
                    for (int i = 0; i < modeloConsulta.getColumnCount(); i++) {
                        if (i > 12) {
                            break;
                        }
                        Label column = new Label(i, 0, modeloConsulta.getColumnName(i));
                        sheet1.addCell(column);
                    }

                    for (int i = 0; i < modeloConsulta.getRowCount(); i++) {
                        for (int j = 0; j < modeloConsulta.getColumnCount(); j++) {
                            if (j > 12) {
                                break;
                            }

                            String val = (modeloConsulta.getValueAt(i, j) == null ? "" : modeloConsulta.getValueAt(i, j).toString());
                            if (j <= 3) {
                                switch (val){
                                    case "0":
                                        val = "MALO";
                                        break;
                                    case "1":
                                        val = "REGULAR";
                                        break;
                                    case "2":
                                        val = "BUENO";
                                        break;
                                    case "3":
                                        val = "EXCELENTE";
                                        break;
                                    case "99":
                                        val = "";
                                        break;

                                }
                            }
                            Label row = new Label(j, i + 1, val);
                            sheet1.addCell(row);
                        }
                    }
                    workbook1.write();
                    workbook1.close();
                    JOptionPane.showMessageDialog(this,"Exportación exitosa!", "Información",JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException | WriteException ex) {
                    JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }else{
           JOptionPane.showMessageDialog(this,"No hay resultados para mostrar", "Información",JOptionPane.INFORMATION_MESSAGE); 
        }
    }//GEN-LAST:event_jExcelActionPerformed

    private void jDetalleTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetalleTurnoActionPerformed
        if (jTablaConsulta.getSelectedRow() >= 0){
            int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());

            Turno turno = new Turno(null, true, conexion, id_empresa, empresa);
            turno.setId_presupuesto(modeloConsulta.getValueAt(fila, 4).toString());
            turno.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetalleTurnoActionPerformed

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

    private void jCheckTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckTodosActionPerformed
        this.jCheckMalo.setSelected(this.jCheckTodos.isSelected());
        this.jCheckRegular.setSelected(this.jCheckTodos.isSelected());
        this.jCheckBueno.setSelected(this.jCheckTodos.isSelected());
        this.jCheckExcelente.setSelected(this.jCheckTodos.isSelected());
        this.jCheckMalo.setEnabled(!this.jCheckTodos.isSelected());
        this.jCheckRegular.setEnabled(!this.jCheckTodos.isSelected());
        this.jCheckBueno.setEnabled(!this.jCheckTodos.isSelected());
        this.jCheckExcelente.setEnabled(!this.jCheckTodos.isSelected());        
    }//GEN-LAST:event_jCheckTodosActionPerformed

    private void jComboModificaPrestacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboModificaPrestacionMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboModificaPrestacionMouseClicked

    private void jComboModificaTecnicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboModificaTecnicoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboModificaTecnicoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup groupAlternativas;
    private javax.swing.ButtonGroup groupAsistencia;
    private javax.swing.ButtonGroup groupInstrumental;
    private javax.swing.ButtonGroup groupPuntualidad;
    private javax.swing.JButton jBtnConsumido;
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JCheckBox jCheckBueno;
    private javax.swing.JCheckBox jCheckControlado;
    private javax.swing.JCheckBox jCheckExcelente;
    private javax.swing.JCheckBox jCheckMalo;
    private javax.swing.JCheckBox jCheckRegular;
    private javax.swing.JCheckBox jCheckTodos;
    private javax.swing.JComboBox jComboAlternativas;
    private javax.swing.JComboBox jComboAsistencia;
    private javax.swing.JComboBox jComboInstrumental;
    private javax.swing.JComboBox jComboModificaPrestacion;
    private javax.swing.JComboBox jComboModificaTecnico;
    private javax.swing.JComboBox jComboMuestra;
    private javax.swing.JComboBox jComboPrestacion;
    private javax.swing.JComboBox jComboPuntualidad;
    private javax.swing.JComboBox jComboTecnico;
    private javax.swing.JComboBox jComboZona;
    private com.toedter.calendar.JDateChooser jDesde;
    private javax.swing.JMenuItem jDetalleTurno;
    private javax.swing.JMenuItem jExcel;
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
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblContador;
    private javax.swing.JLabel jLblEntidad;
    private javax.swing.JLabel jLblFecha;
    private javax.swing.JLabel jLblLugar;
    private javax.swing.JLabel jLblPaciente;
    private javax.swing.JLabel jLblProfesional;
    private javax.swing.JLabel jLblTurno;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelCalificaciones;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTablaConsulta;
    private javax.swing.JTable jTablaProducto;
    private javax.swing.JTextField jTxtBuscaConsulta;
    private javax.swing.JTextArea jTxtObservaciones;
    // End of variables declaration//GEN-END:variables
}
