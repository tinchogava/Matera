package ar.com.bosoft.formularios;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import matera.cache.CacheZona;
import matera.gui.combobox.ComboBoxMgr;
import matera.gui.combobox.GlazedAutocomplete;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class MovimientoMayorProfesional extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa;
    String empresa, usuario;
    
    ResultSet rsConsulta;
    DefaultTableModel modeloConsulta;
    TableRowSorter sorterConsulta;
    
    RoundingMode RM = RoundingMode.HALF_UP;
    int escala = 2;
        
    public MovimientoMayorProfesional(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;

        initComponents();
        
        modeloConsulta = (DefaultTableModel) this.jTablaConsulta.getModel();
        jTablaConsulta.setModel(modeloConsulta);
        jTablaConsulta.setRowSorter (new TableRowSorter(modeloConsulta));
        sorterConsulta = new TableRowSorter(modeloConsulta);
        
        setJTexFieldChanged(this.jTxtBuscaConsulta);
        ComboBoxMgr.fillZonaCombo(jComboZona, true, true);
        ComboBoxMgr.fillProfesionalCombo(jComboProfesional, Utiles.TODOS, true);
        ComboBoxMgr.fillProfesionalCombo(jComboProfesional1, Utiles.TODOS, true);
        
        ItemListener jComboProfesional1Listener = new ItemListener() {
              public void itemStateChanged(ItemEvent itemEvent) {
                //int state = itemEvent.getStateChange();
                jComboProfesional1.setSelectedIndex(-1);
                    jComboProfesional1.setEnabled(jRadioTransferencia.isSelected());
              }
            };        
        /*
        consultaZona();
        consultaProfesional();
        llenaComboZona();
        limpia();
        
        */
        zonaUsuario();
        jRadioTransferencia.addItemListener(jComboProfesional1Listener);
        this.jComboZonaActionPerformed(null);
    }
/*
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
    */
    
    private void zonaUsuario(){
        int id_zonaUsuario = Principal.usuarioData.getId_zonaSistema();
        if (id_zonaUsuario > 0){
            jComboZona.getModel().setSelectedItem(CacheZona.instance().getZona(id_zonaUsuario));
        }
        this.jComboZona.setEnabled(id_zonaUsuario == 0);
    }
    
    private void limpia(){
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedIndex(-1);
        }
        
        this.jComboProfesional.setSelectedIndex(-1);
        this.jTxtBuscaConsulta.setText("");
        
        this.jLblTurno.setText("");
        this.jRadioDebito.setSelected(true);
        this.jComboProfesional1.setSelectedIndex(-1);
        this.jFmtImporte.setValue(0.00);
        this.jFmtDisponible.setValue(0.00);
        this.jTxtObservaciones.setText("");
    }
    
    private void consulta(){
        try{
            modeloConsulta.getDataVector().removeAllElements();
            modeloConsulta.fireTableDataChanged();
            
            if (this.jComboProfesional.getSelectedIndex() >= 0) {
                ArrayList parametros = new ArrayList();

                int id_profesional = ComboBoxMgr.getSelectedId(jComboProfesional);

                parametros.add(id_profesional);
                parametros.add(this.id_empresa);

                rsConsulta = conexion.procAlmacenado("consultaPresuPreliquidacion", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                rsConsulta.beforeFirst();
                while (rsConsulta.next()){
                    int id_presupuesto = rsConsulta.getInt("id_presupuesto");
                    String paciente = rsConsulta.getString("paciente");
                    String entidad = rsConsulta.getString("entidad");
                    Double debe = rsConsulta.getDouble("debe");
                    Double haber = rsConsulta.getDouble("haber");


                    Object[] fila = {id_presupuesto, debe, haber, paciente, entidad};
                    modeloConsulta.addRow(fila);
                }
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
    
    private String validaObligatorios(){
        String mensaje = "";
        if (this.jComboProfesional.getSelectedIndex() < 0 ||
                this.jLblTurno.getText().isEmpty() ||
                Double.parseDouble(this.jFmtImporte.getValue().toString()) <= 0){
            mensaje = "Complete todos los datos obligatorios (*)";
            return mensaje;
        }
        
        if (this.jRadioDebito.isSelected()){
            if (Integer.parseInt(this.jLblTurno.getText()) == 0){
                mensaje = "No puede aplicar un débito a un anticipo";
                return mensaje;
            }
        }
        
        if (this.jRadioTransferencia.isSelected()){
            if (this.jComboProfesional1.getSelectedIndex() < 0){
                mensaje = "Seleccione el profesional a transferir";
                return mensaje;
            }
        }
        
        if (Double.parseDouble(this.jFmtImporte.getValue().toString()) > Double.parseDouble(this.jFmtDisponible.getValue().toString())) {
             mensaje = "El importe supera al disponible";
            return mensaje;
        }
        
        return mensaje;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu = new javax.swing.JPopupMenu();
        jDetallePresupuesto = new javax.swing.JMenuItem();
        jBtnSalir = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTxtBuscaConsulta = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jBtnFiltra = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jComboProfesional = new matera.gui.combobox.GlazedCombo();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jFmtImporte = new ar.com.bosoft.formatosCampos.Decimal(true)
        ;
        jScrollPane3 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLblTurno = new javax.swing.JLabel();
        jRadioDebito = new javax.swing.JRadioButton();
        jRadioCredito = new javax.swing.JRadioButton();
        jRadioTransferencia = new javax.swing.JRadioButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jFmtDisponible = new ar.com.bosoft.formatosCampos.Decimal(true)
        ;
        jComboProfesional1 = new matera.gui.combobox.GlazedCombo();

        jDetallePresupuesto.setText("Ver presupuesto");
        jDetallePresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetallePresupuestoActionPerformed(evt);
            }
        });
        jPopupMenu.add(jDetallePresupuesto);

        setTitle("Movimientos de cuenta corriente");
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
                .addComponent(jTxtBuscaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                "Turno", "Debe", "Haber", "Paciente", "Entidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
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
        jTablaConsulta.setComponentPopupMenu(jPopupMenu);
        jTablaConsulta.getTableHeader().setReorderingAllowed(false);
        jTablaConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaConsultaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaConsulta);
        if (jTablaConsulta.getColumnModel().getColumnCount() > 0) {
            jTablaConsulta.getColumnModel().getColumn(0).setMinWidth(50);
            jTablaConsulta.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTablaConsulta.getColumnModel().getColumn(0).setMaxWidth(50);
            jTablaConsulta.getColumnModel().getColumn(1).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(1).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(2).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(2).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(2).setMaxWidth(75);
        }

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

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
                    .addComponent(jComboZona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboProfesional, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnFiltra)
                .addContainerGap())
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
                    .addComponent(jLabel20)
                    .addComponent(jComboProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jBtnFiltra))
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Detalle"));

        jLabel1.setText("* Importe");

        jFmtImporte.setNextFocusableComponent(jTxtObservaciones);

        jScrollPane3.setBackground(new java.awt.Color(153, 204, 255));
        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Observaciones"));

        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jScrollPane3.setViewportView(jTxtObservaciones);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("* Turno N°");

        jLblTurno.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblTurno.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLblTurno.setText("...");

        buttonGroup1.add(jRadioDebito);
        jRadioDebito.setText("Débito");
        jRadioDebito.setContentAreaFilled(false);
        jRadioDebito.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jRadioDebito.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        buttonGroup1.add(jRadioCredito);
        jRadioCredito.setText("Crédito");
        jRadioCredito.setContentAreaFilled(false);
        jRadioCredito.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jRadioCredito.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        buttonGroup1.add(jRadioTransferencia);
        jRadioTransferencia.setText("Transferencia");
        jRadioTransferencia.setContentAreaFilled(false);
        jRadioTransferencia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jRadioTransferencia.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jRadioTransferencia.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jRadioTransferenciaPropertyChange(evt);
            }
        });

        jLabel21.setText("* Profesional");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Disponible");

        jFmtDisponible.setEditable(false);
        jFmtDisponible.setBackground(new java.awt.Color(153, 204, 255));
        jFmtDisponible.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jFmtDisponible.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jFmtDisponible.setNextFocusableComponent(jTxtObservaciones);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jRadioDebito, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jRadioCredito, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jRadioTransferencia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLblTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jFmtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFmtDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboProfesional1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 633, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(62, 62, 62))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLblTurno))
                .addGap(1, 1, 1)
                .addComponent(jRadioDebito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioCredito)
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21)
                        .addComponent(jComboProfesional1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jRadioTransferencia))
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFmtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jFmtDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnGuardar)
                        .addGap(197, 197, 197)
                        .addComponent(jBtnSalir)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 10, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnSalir))
                .addGap(1, 1, 1))
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
        if (validaObligatorios().isEmpty()){
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Confirma el movimiento?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                try{
                    ArrayList parametros = new ArrayList();

                    Long fecha = new Date().getTime();
                    int id_presupuesto = Integer.parseInt(this.jLblTurno.getText());

                    int id_profesional = ComboBoxMgr.getSelectedId(jComboProfesional);

                    String dc = (this.jRadioCredito.isSelected() || Integer.parseInt(this.jLblTurno.getText()) == 0) ? "C" : "D";
                    Double importe = Double.parseDouble(this.jFmtImporte.getValue().toString());
                    String observaciones = this.jTxtObservaciones.getText().trim() + " (Movimientos de cuenta corriente)";
                    String transferencia = this.jRadioTransferencia.isSelected() ? "S" : "N";
                    
                    parametros.add(fecha);
                    parametros.add(id_presupuesto);
                    parametros.add(id_profesional);
                    parametros.add(dc);
                    parametros.add(importe);
                    parametros.add(observaciones);
                    parametros.add(this.id_empresa);
                    parametros.add(this.usuario);
                    parametros.add(transferencia);
                    
                    conexion.procAlmacenado("insertMayorProfesional", parametros, 
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                    if (this.jRadioTransferencia.isSelected()){
                        parametros.clear();

                        id_profesional = ComboBoxMgr.getSelectedId(jComboProfesional1);

                        dc = Integer.parseInt(this.jLblTurno.getText()) == 0 ? "D" : "C";

                        parametros.add(fecha);
                        parametros.add(id_presupuesto);
                        parametros.add(id_profesional);
                        parametros.add(dc);
                        parametros.add(importe);
                        parametros.add(observaciones);
                        parametros.add(this.id_empresa);
                        parametros.add(this.usuario);
                        parametros.add(transferencia);
                    
                        conexion.procAlmacenado("insertMayorProfesional", parametros, 
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    }
                    
                    limpia();
                    //consulta();

                }catch (NumberFormatException ex) {
                    Utiles.enviaError(this.empresa,ex);
                } 
            }
        }else{
            JOptionPane.showMessageDialog(this, validaObligatorios(), "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jTxtBuscaConsultaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaConsultaFocusGained
        this.jTxtBuscaConsulta.select(0, this.jTxtBuscaConsulta.getText().length());
    }//GEN-LAST:event_jTxtBuscaConsultaFocusGained

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        if (this.jComboProfesional.getSelectedIndex() >= 0) {
            consulta();
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el profesional", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jTablaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsultaMouseClicked
        if (evt.getClickCount() == 2){
            int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
            
            this.jLblTurno.setText(modeloConsulta.getValueAt(fila, 0).toString());
            
            double disponible = (double) modeloConsulta.getValueAt(fila, 2) - (double) modeloConsulta.getValueAt(fila, 1);
            this.jFmtDisponible.setValue(Math.abs(disponible));
            
            /*
            this.jComboProfesional1.removeAllItems();
            Iterator it = profesionalArray.iterator();
            while (it.hasNext()){
                ProfesionalData p = (ProfesionalData) it.next();
                if (!p.getNombre().equals(this.jComboProfesional.getSelectedItem().toString())){
                    this.jComboProfesional1.addItem(p.getNombre());
                }
            }
            this.jComboProfesional1.setSelectedIndex(-1);
                    */
        }
    }//GEN-LAST:event_jTablaConsultaMouseClicked

    private void jRadioTransferenciaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jRadioTransferenciaPropertyChange
        this.jComboProfesional1.setSelectedIndex(-1);
        this.jComboProfesional1.setEnabled(this.jRadioTransferencia.isSelected());        
    }//GEN-LAST:event_jRadioTransferenciaPropertyChange

    private void jDetallePresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetallePresupuestoActionPerformed
        int fila = jTablaConsulta.convertRowIndexToModel(jTablaConsulta.getSelectedRow());
        if (fila >= 0){
            Presupuesto presupuesto = new Presupuesto(conexion, id_empresa, empresa, "");
            presupuesto.setId_presupuesto(modeloConsulta.getValueAt(fila, 0).toString());
            Principal.dp.add(presupuesto);
            presupuesto.toFront();
            presupuesto.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetallePresupuestoActionPerformed

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        int indiceZona = ComboBoxMgr.getSelectedId(jComboZona);
        ComboBoxMgr.fillProfesionalCombo(jComboProfesional, indiceZona, true);
    }//GEN-LAST:event_jComboZonaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnSalir;
    private matera.gui.combobox.GlazedCombo jComboProfesional;
    private matera.gui.combobox.GlazedCombo jComboProfesional1;
    private javax.swing.JComboBox jComboZona;
    private javax.swing.JMenuItem jDetallePresupuesto;
    private javax.swing.JFormattedTextField jFmtDisponible;
    private javax.swing.JFormattedTextField jFmtImporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLblTurno;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JRadioButton jRadioCredito;
    private javax.swing.JRadioButton jRadioDebito;
    private javax.swing.JRadioButton jRadioTransferencia;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTablaConsulta;
    private javax.swing.JTextField jTxtBuscaConsulta;
    private javax.swing.JTextArea jTxtObservaciones;
    // End of variables declaration//GEN-END:variables
}
