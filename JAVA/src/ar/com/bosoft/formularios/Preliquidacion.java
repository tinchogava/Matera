/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import matera.gui.dialog.RemitoDetalle;
import ar.com.bosoft.RenderTablas.DateRenderer;
import ar.com.bosoft.buscadores.General1;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ProfesionalCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.ProfesionalData;
import ar.com.bosoft.data.ZonaData;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import matera.services.FacturaService;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class Preliquidacion extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa;
    String empresa, usuario;
    
    ResultSet rsConsulta, rsProximaPreliquidacion;
    DefaultTableModel modeloConsulta;
    TableRowSorter sorterConsulta;
    TableCellRenderer tableCellRenderer;
    
    ZonaCRUD zonaCRUD;
    ProfesionalCRUD profesionalCRUD;
    ArrayList arrayZona, profesionalArray, arrayId_profesional;
    
    //Buscador
    General1 general1 = new General1(null, true);
    
    RoundingMode RM = RoundingMode.HALF_UP;
    int escala = 2;
        
    public Preliquidacion(Conexion conexion, int id_empresa, String empresa, String usuario) {
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
        
        setJTexFieldChanged(this.jTxtBuscaConsulta);
        
        consultaZona();
        consultaProfesional();
        llenaComboZona();
        limpia();
        this.jComboZonaActionPerformed(null);
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
        if (indiceComboZona >= 0){
            ZonaData z = (ZonaData) arrayZona.get(indiceComboZona);
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
        this.jComboProfesional.setSelectedIndex(-1);
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
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedIndex(-1);
        }
        
        this.jComboProfesional.setSelectedIndex(-1);
        this.jTxtBuscaConsulta.setText("");
        modeloConsulta.getDataVector().removeAllElements();
        modeloConsulta.fireTableDataChanged();
        this.jFmtAnticipo.setValue(0.00);
        this.jTxtObservaciones.setText("");
        this.jLblTotal.setText("0,00");
        
        zonaUsuario();
    }
    
    private void consulta(){
        try{
            if (this.jComboProfesional.getSelectedIndex() >= 0) {
                modeloConsulta.getDataVector().removeAllElements();
                modeloConsulta.fireTableDataChanged();

                ArrayList parametros = new ArrayList();

                int id_profesional = (int) this.arrayId_profesional.get(this.jComboProfesional.getSelectedIndex());

                parametros.add(id_profesional);
                parametros.add(this.id_empresa);

                rsConsulta = conexion.procAlmacenado("consultaPresuPreliquidacion", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                Calendar currentDate = Calendar.getInstance();

                rsConsulta.beforeFirst();
                while (rsConsulta.next()){
                    Date fechaCirugia = rsConsulta.getDate("fechaCirugia");
                    int id_presupuesto = rsConsulta.getInt("id_presupuesto");
                    String paciente = rsConsulta.getString("paciente");
                    String entidad = rsConsulta.getString("entidad");
                    Double debe = rsConsulta.getDouble("debe");
                    Double haber = rsConsulta.getDouble("haber");
                    Double pendiente = rsConsulta.getDouble("pendiente");
                    Double disponible = rsConsulta.getDouble("disponible");
                    Double aplicado = 0.00;
                    String observaciones = "";
                    String prestacion = rsConsulta.getString("prestacion");
                    String tecnico = rsConsulta.getString("tecnico");
                    Calendar ciruriaDate = Calendar.getInstance();
                    ciruriaDate.setTime(fechaCirugia);
                    /*
                    System.out.println("ID = " + id_presupuesto);
                    System.out.println("fechaCirugia = " + fechaCirugia);
                    
                    System.out.println("Count=" + FacturaService.isFactured(id_presupuesto));
                    */
                    //if(FacturaService.isFactured(id_presupuesto) > 0 && ciruriaDate.compareTo(currentDate) < 1){
                    
                        Object[] fila = {fechaCirugia, id_presupuesto, paciente, entidad, debe, 
                                            haber, pendiente, disponible, aplicado, observaciones,
                                            prestacion, tecnico};
                        modeloConsulta.addRow(fila);
                    //}
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
    
    private void actualizaTotal(){
        BigDecimal total = new BigDecimal(Double.parseDouble(this.jFmtAnticipo.getValue().toString()));
        for (int i = 0; i < modeloConsulta.getRowCount(); i++){
            BigDecimal aplicado = new BigDecimal(Double.parseDouble(modeloConsulta.getValueAt(i, 8).toString()));
            total = total.add(aplicado).setScale(escala, RM);
        }
        
        this.jLblTotal.setText(String.format("%.2f", total));
    }
    
    private boolean validaObligatorios(){
        return !this.jLblTotal.getText().equals("0,00");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu = new javax.swing.JPopupMenu();
        jDetallePresupuesto = new javax.swing.JMenuItem();
        jBtnSalir = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTxtBuscaConsulta = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jBtnFiltra = new javax.swing.JButton();
        jComboProfesional = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jFmtAnticipo = new ar.com.bosoft.formatosCampos.Decimal(true)
        ;
        jScrollPane3 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLblTotal = new javax.swing.JLabel();
        jBtnVerConsumido = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablaConsulta = new ar.com.bosoft.RenderTablas.RXTable();

        jDetallePresupuesto.setText("Ver presupuesto");
        jDetallePresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetallePresupuestoActionPerformed(evt);
            }
        });
        jPopupMenu.add(jDetallePresupuesto);

        setTitle("Preliquidación");
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
                .addComponent(jTxtBuscaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboZona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnFiltra)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jBtnFiltra))
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Anticipo"));

        jLabel1.setText("Importe");

        jFmtAnticipo.setNextFocusableComponent(jTxtObservaciones);
        jFmtAnticipo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFmtAnticipoFocusLost(evt);
            }
        });

        jScrollPane3.setBackground(new java.awt.Color(153, 204, 255));
        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Observaciones"));

        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jScrollPane3.setViewportView(jTxtObservaciones);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFmtAnticipo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 770, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jFmtAnticipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1))
        );

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Total Preliquidación");

        jLblTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblTotal.setText("0,00");

        jBtnVerConsumido.setText("Ver consumido");
        jBtnVerConsumido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnVerConsumidoActionPerformed(evt);
            }
        });

        jTablaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha Cx.", "Turno", "Paciente", "Entidad", "Debe", "Haber", "Pendiente", "Disponible", "Aplicado", "Observaciones", "prestacion", "tecnico"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaConsulta.setComponentPopupMenu(jPopupMenu);
        jTablaConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaConsultaMouseClicked(evt);
            }
        });
        jTablaConsulta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTablaConsultaKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTablaConsulta);
        if (jTablaConsulta.getColumnModel().getColumnCount() > 0) {
            jTablaConsulta.getColumnModel().getColumn(0).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(0).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(1).setMinWidth(50);
            jTablaConsulta.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTablaConsulta.getColumnModel().getColumn(1).setMaxWidth(50);
            jTablaConsulta.getColumnModel().getColumn(4).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(4).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(4).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(5).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(5).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(5).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(6).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(6).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(6).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(7).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(7).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(7).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(8).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(8).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(8).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(10).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(10).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(10).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(11).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(11).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(11).setMaxWidth(0);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnGuardar)
                        .addGap(312, 312, 312)
                        .addComponent(jBtnSalir)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(207, 207, 207)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtnVerConsumido))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2))
                        .addGap(0, 10, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnVerConsumido))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel2)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBtnGuardar)
                        .addComponent(jBtnSalir))
                    .addComponent(jLblTotal))
                .addGap(1, 1, 1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        try {
            if (this.rsConsulta != null){
                this.rsConsulta.close();
            }
            if (this.rsProximaPreliquidacion != null){
                this.rsProximaPreliquidacion.close();
            }
            this.dispose();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios()){
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Confirma la preliquidación?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                try{
                    ArrayList parametros = new ArrayList();

                    parametros.add(this.id_empresa);
                    rsProximaPreliquidacion = conexion.funcAlmacenada("proximaPreliquidacion", parametros, 
                                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    
                    rsProximaPreliquidacion.next();
                    int preliquidacion = rsProximaPreliquidacion.getInt(1);
                    
                    Long fecha = new Date().getTime();

                    int id_profesional = (int) this.arrayId_profesional.get(this.jComboProfesional.getSelectedIndex());

                    String dc = "D";

                    for (int i = 0; i < modeloConsulta.getRowCount(); i++){
                        Double aplicacion = Double.parseDouble(modeloConsulta.getValueAt(i, 8).toString());
                        if (aplicacion > 0){
                            parametros.clear();

                            int id_presupuesto = Integer.parseInt(modeloConsulta.getValueAt(i, 1).toString());
                            String observaciones = "Solicitado por " + this.usuario + (modeloConsulta.getValueAt(i, 9).toString().isEmpty() ? "" : " - " + modeloConsulta.getValueAt(i, 9).toString());

                            parametros.add(fecha);
                            parametros.add(id_presupuesto);
                            parametros.add(id_profesional);
                            parametros.add(dc);
                            parametros.add(aplicacion);
                            parametros.add(observaciones);
                            parametros.add(preliquidacion);
                            parametros.add(this.id_empresa);
                            parametros.add(this.usuario);                        

                            conexion.procAlmacenado("insertPreliquidacion", parametros, 
                                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                        }
                    }

                    if (Double.parseDouble(this.jFmtAnticipo.getValue().toString()) > 0.00){
                        parametros.clear();

                        int id_presupuesto = 0;
                        String observaciones = "Solicitado por " + this.usuario + (this.jTxtObservaciones.getText().isEmpty() ? "" : " - " + this.jTxtObservaciones.getText());

                        parametros.add(fecha);
                        parametros.add(id_presupuesto);
                        parametros.add(id_profesional);
                        parametros.add(dc);
                        parametros.add(Double.parseDouble(this.jFmtAnticipo.getValue().toString()));
                        parametros.add(observaciones);
                        parametros.add(preliquidacion);
                        parametros.add(this.id_empresa);
                        parametros.add(this.usuario);                        

                        conexion.procAlmacenado("insertPreliquidacion", parametros, 
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    }

                    limpia();
                    consulta();
                    this.jTxtBuscaConsulta.requestFocus();
                } catch (SQLException | NumberFormatException ex) {
                    Utiles.enviaError(this.empresa,ex);
                } 
            }     
        }else{
            JOptionPane.showMessageDialog(this, "No se han cargado importes", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jTxtBuscaConsultaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaConsultaFocusGained
        this.jTxtBuscaConsulta.select(0, this.jTxtBuscaConsulta.getText().length());
    }//GEN-LAST:event_jTxtBuscaConsultaFocusGained

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        consulta();
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jComboProfesionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboProfesionalActionPerformed
//        consulta();
    }//GEN-LAST:event_jComboProfesionalActionPerformed

    private void jFmtAnticipoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFmtAnticipoFocusLost
        actualizaTotal();
    }//GEN-LAST:event_jFmtAnticipoFocusLost

    private void jDetallePresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetallePresupuestoActionPerformed
        if (jTablaConsulta.getSelectedRow() >= 0){
            int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
        
            Presupuesto presupuesto = new Presupuesto(conexion, id_empresa, empresa, "");
            presupuesto.setId_presupuesto(modeloConsulta.getValueAt(fila, 1).toString());
            Principal.dp.add(presupuesto);
            presupuesto.toFront();
            presupuesto.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetallePresupuestoActionPerformed

    private void jBtnVerConsumidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnVerConsumidoActionPerformed
        if (jTablaConsulta.getSelectedRow() >= 0){
            int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
        
            String id_presupuesto = modeloConsulta.getValueAt(fila, 1).toString();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String fechaCirugia = "";
            try {
                fechaCirugia = format.format((Date) modeloConsulta.getValueAt(fila, 0));
            } catch (Exception e) {
                //Fecha invalida
            }
            String prestacion = modeloConsulta.getValueAt(fila, 10).toString();
            String tecnico = modeloConsulta.getValueAt(fila, 11).toString();
            String entidad = modeloConsulta.getValueAt(fila, 3).toString();
            String profesional = this.jComboProfesional.getSelectedItem().toString();
            String paciente = modeloConsulta.getValueAt(fila, 2).toString();
            String observaciones = modeloConsulta.getValueAt(fila, 9).toString();
            RemitoDetalle remitoDetalle = new RemitoDetalle(null, false, Integer.parseInt(id_presupuesto));
            remitoDetalle.setDatos();
            remitoDetalle.setVisible(true);
//            Consumido consumido = new Consumido(conexion, id_empresa, empresa);
//            consumido.setDatos(id_presupuesto, fechaCirugia, prestacion, tecnico, entidad, profesional, paciente, observaciones);
//            if (consumido.valida()) {
//                consumido.toFront();
//                Principal.muestra(consumido);
//                consumido.setVisible(true);
//            }
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnVerConsumidoActionPerformed

    private void jTablaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsultaMouseClicked
        if (evt.getClickCount() == 2){
            int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
            if (this.jTablaConsulta.getSelectedColumn() < 9){
                modeloConsulta.setValueAt(modeloConsulta.getValueAt(fila, 7), fila, 8);
                actualizaTotal();
            }
        }
    }//GEN-LAST:event_jTablaConsultaMouseClicked

    private void jTablaConsultaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTablaConsultaKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
            
            Double aplicacion = Double.parseDouble(modeloConsulta.getValueAt(fila, 8).toString());
            Double disponible = Double.parseDouble(modeloConsulta.getValueAt(fila, 7).toString());
            if (aplicacion > disponible){
                modeloConsulta.setValueAt(disponible, fila, 8);
            }
            actualizaTotal();
        }
    }//GEN-LAST:event_jTablaConsultaKeyReleased

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

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        int indiceZona = this.jComboZona.getSelectedIndex();
        llenaComboProfesional(indiceZona);
    }//GEN-LAST:event_jComboZonaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnVerConsumido;
    private javax.swing.JComboBox jComboProfesional;
    private javax.swing.JComboBox jComboZona;
    private javax.swing.JMenuItem jDetallePresupuesto;
    private javax.swing.JFormattedTextField jFmtAnticipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLblTotal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private ar.com.bosoft.RenderTablas.RXTable jTablaConsulta;
    private javax.swing.JTextField jTxtBuscaConsulta;
    private javax.swing.JTextArea jTxtObservaciones;
    // End of variables declaration//GEN-END:variables
}
