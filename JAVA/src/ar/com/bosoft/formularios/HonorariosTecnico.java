/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.RenderTablas.DateRenderer;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.PrestacionCRUD;
import ar.com.bosoft.crud.TecnicoCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.PrestacionData;
import ar.com.bosoft.data.TecnicoData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.vistasRapidas.Turno;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class HonorariosTecnico extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa;
    String empresa, usuario, todos;
    
    ResultSet rsConsulta;
    DefaultTableModel modeloConsulta;
    TableRowSorter sorterConsulta;
    TableCellRenderer tableCellRenderer;
    
    PrestacionCRUD prestacionCRUD;
    TecnicoCRUD tecnicoCRUD;
    ZonaCRUD zonaCRUD;
    
    ArrayList prestacionArray, tecnicoArray, zonaArray;
    
    public HonorariosTecnico(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        this.todos = "-- TODOS --";
        
        this.prestacionCRUD = new PrestacionCRUD(conexion, id_empresa, empresa);
        this.tecnicoCRUD = new TecnicoCRUD(conexion, id_empresa, empresa);
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        
        initComponents();
        
        modeloConsulta = (DefaultTableModel) this.jTablaConsulta.getModel();
        jTablaConsulta.setModel(modeloConsulta);
        jTablaConsulta.setRowSorter (new TableRowSorter(modeloConsulta));
        sorterConsulta = new TableRowSorter(modeloConsulta);
        tableCellRenderer = new DateRenderer();
        jTablaConsulta.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        
        setJTexFieldChanged(this.jTxtBuscaConsulta);
        
        llenaComboPrestacion();
        llenaComboTecnico();
        llenaComboZona();
        limpia();
        zonaUsuario();                
        consulta();
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
    
    private void llenaComboTecnico(){
        this.jComboTecnico.addItem(this.todos);
        tecnicoArray = tecnicoCRUD.consulta(true);
        Iterator i = tecnicoArray.iterator();
        while (i.hasNext()){
            TecnicoData t = (TecnicoData) i.next();
            this.jComboTecnico.addItem(t.getNombre());
        }
    }
    
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
    }
    
    private void consulta(){
        try{
            modeloConsulta.getDataVector().removeAllElements();
            modeloConsulta.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            Long desde = this.jDesde.getDate() == null ? (long) 0 : this.jDesde.getDate().getTime();
            Long hasta = this.jHasta.getDate() == null ? (long) 0 : this.jHasta.getDate().getTime();
            
            int id_prestacion = 0;
            if (this.jComboPrestacion.getSelectedIndex() > 0){
                PrestacionData p = (PrestacionData) prestacionArray.get(this.jComboPrestacion.getSelectedIndex() - 1);
                id_prestacion = p.getId_prestacion();
            }
            
            int id_tecnico = 0;
            if (this.jComboTecnico.getSelectedIndex() > 0){
                TecnicoData t = (TecnicoData) tecnicoArray.get(this.jComboTecnico.getSelectedIndex() - 1);
                id_tecnico = t.getId_tecnico();
            }
            
            int id_zona = 0;
            if (this.jComboZona.getSelectedIndex() > 0){
                ZonaData z = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
                id_zona = z.getId_zona();
            }
            
            parametros.add(desde);
            parametros.add(hasta);
            parametros.add(id_prestacion);
            parametros.add(id_tecnico);
            parametros.add(id_zona);
            parametros.add(this.id_empresa);
            rsConsulta = conexion.procAlmacenado("consultaPresuHonorarios", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsConsulta.beforeFirst();
            while (rsConsulta.next()){
                int id_presupuesto = rsConsulta.getInt("id_presupuesto");
                Date fechaCirugia = rsConsulta.getDate("fechaCirugia");
                String profesional = rsConsulta.getString("profesional");
                String paciente = rsConsulta.getString("paciente");
                String prestacion = rsConsulta.getString("prestacion");
                String tecnico = rsConsulta.getString("tecnico");
                String alternativas = rsConsulta.getString("alternativas");
                String instrumental = rsConsulta.getString("instrumental");
                String asistencia = rsConsulta.getString("asistencia");
                String puntualidad = rsConsulta.getString("puntualidad");
                Double importePrestacion = rsConsulta.getDouble("importePrestacion");
                int id_tec = rsConsulta.getInt("id_tecnico");
                
                Object[] fila = {id_presupuesto, fechaCirugia, profesional, paciente, prestacion, 
                                    tecnico, alternativas, instrumental, asistencia, puntualidad, 
                                    false, importePrestacion, id_tec};
                modeloConsulta.addRow(fila);
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu = new javax.swing.JPopupMenu();
        jDetalleTurno = new javax.swing.JMenuItem();
        jBtnSalir = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTxtBuscaConsulta = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jDesde = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jDesde.getJCalendar().setTodayButtonVisible(true); 
        this.jDesde.getJCalendar().setTodayButtonText("Hoy"); 
        this.jDesde.getJCalendar().setWeekOfYearVisible(false);
        jLabel11 = new javax.swing.JLabel();
        jHasta = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jHasta.getJCalendar().setTodayButtonVisible(true); 
        this.jHasta.getJCalendar().setTodayButtonText("Hoy"); 
        this.jHasta.getJCalendar().setWeekOfYearVisible(false);
        jBtnFiltra = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jComboPrestacion = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jComboTecnico = new javax.swing.JComboBox();

        jDetalleTurno.setText("Turno");
        jDetalleTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetalleTurnoActionPerformed(evt);
            }
        });
        jPopupMenu.add(jDetalleTurno);

        setTitle("Honorarios Técnico");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

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

        jBtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/guardar.png"))); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.setBorderPainted(false);
        jBtnGuardar.setContentAreaFilled(false);
        jBtnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnGuardar.setFocusPainted(false);
        jBtnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBuscaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 826, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                "Turno", "Fecha Cx", "Profesional", "Paciente", "Prestación", "Técnico", "Set Alt.", "Set Instr.", "Asis. Téc.", "Punt.", "", "importePrestacion", "id_tecnico"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Double.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaConsulta.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTablaConsulta);
        if (jTablaConsulta.getColumnModel().getColumnCount() > 0) {
            jTablaConsulta.getColumnModel().getColumn(0).setMinWidth(50);
            jTablaConsulta.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTablaConsulta.getColumnModel().getColumn(0).setMaxWidth(50);
            jTablaConsulta.getColumnModel().getColumn(1).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(1).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(6).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(6).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(6).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(7).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(7).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(7).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(8).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(8).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(8).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(9).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(9).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(9).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(10).setMinWidth(30);
            jTablaConsulta.getColumnModel().getColumn(10).setPreferredWidth(30);
            jTablaConsulta.getColumnModel().getColumn(10).setMaxWidth(30);
            jTablaConsulta.getColumnModel().getColumn(11).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(11).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(11).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(12).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(12).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(12).setMaxWidth(0);
        }

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jLabel4.setText("Desde");

        jLabel11.setText("Hasta");

        jBtnFiltra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/filtrar.png"))); // NOI18N
        jBtnFiltra.setText("Filtrar");
        jBtnFiltra.setBorderPainted(false);
        jBtnFiltra.setContentAreaFilled(false);
        jBtnFiltra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnFiltra.setFocusPainted(false);
        jBtnFiltra.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/filtrar.png"))); // NOI18N
        jBtnFiltra.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/filtrar.png"))); // NOI18N
        jBtnFiltra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnFiltraActionPerformed(evt);
            }
        });

        jLabel16.setText("Zona");

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Fecha de cirugía");

        jLabel18.setText("Prestación");

        jLabel19.setText("Técnico");

        jComboTecnico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboTecnicoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel4)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jComboZona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboPrestacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboTecnico, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jBtnFiltra)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(1, 11, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnFiltra)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel11)
                            .addComponent(jLabel4)
                            .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jComboPrestacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jComboTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(1, 1, 1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jBtnGuardar)
                .addGap(334, 334, 334)
                .addComponent(jBtnSalir))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 886, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(264, 264, 264))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnSalir, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jBtnGuardar)
                        .addGap(1, 1, 1))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        try {
            if (this.rsConsulta != null){
                this.rsConsulta.close();
            }
            this.dispose();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (modeloConsulta.getRowCount() > 0){
            try{
                ArrayList parametros = new ArrayList();
                for (int i = 0; i < modeloConsulta.getRowCount(); i++){
                    if (Boolean.parseBoolean(modeloConsulta.getValueAt(i, 10).toString())){
                        parametros.clear();
                        
                        Long fecha = new Date().getTime();
                        int id_presupuesto = Integer.parseInt(modeloConsulta.getValueAt(i, 0).toString());
                        int id_tecnico = Integer.parseInt(modeloConsulta.getValueAt(i, 12).toString());
                        String dc = "C";
                        Double importePrestacion = Double.parseDouble(modeloConsulta.getValueAt(i, 11).toString());
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
                }
                
                limpia();
                consulta();
                this.jTxtBuscaConsulta.requestFocus();
            }catch (NumberFormatException ex){
                Utiles.enviaError(this.empresa,ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jTxtBuscaConsultaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaConsultaFocusGained
        this.jTxtBuscaConsulta.select(0, this.jTxtBuscaConsulta.getText().length());
    }//GEN-LAST:event_jTxtBuscaConsultaFocusGained

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        consulta();
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jDetalleTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetalleTurnoActionPerformed
        int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
        if (fila >= 0){
            Turno turno = new Turno(null, true, conexion, id_empresa, empresa);
            turno.setId_presupuesto(modeloConsulta.getValueAt(fila, 0).toString());
            turno.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetalleTurnoActionPerformed

    private void jComboTecnicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboTecnicoMouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Técnico");
            Iterator it = this.tecnicoArray.iterator();
            while (it.hasNext()) {
                TecnicoData tmp = (TecnicoData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboTecnico.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboTecnicoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JComboBox jComboPrestacion;
    private javax.swing.JComboBox jComboTecnico;
    private javax.swing.JComboBox jComboZona;
    private com.toedter.calendar.JDateChooser jDesde;
    private javax.swing.JMenuItem jDetalleTurno;
    private com.toedter.calendar.JDateChooser jHasta;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablaConsulta;
    private javax.swing.JTextField jTxtBuscaConsulta;
    // End of variables declaration//GEN-END:variables
}
