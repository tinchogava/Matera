/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class AplicacionAnticipos extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa;
    String empresa, usuario;
    ZonaCRUD zonaCRUD;
    ProfesionalCRUD profesionalCRUD;
    ArrayList arrayZona, profesionalArray, arrayId_profesional;
    DefaultTableModel modeloPresupuesto, modeloAnticipo;
    TableCellRenderer tableCellRenderer;
    
    ResultSet rsConsulta;
    
    int escala = 2;
    RoundingMode RM = RoundingMode.HALF_UP;
    
    General1 general1 = new General1(null, true);
    
    /**
     * Creates new form AplicacionAnticipos
     * @param conexion
     * @param id_empresa
     * @param empresa
     * @param usuario
     */
    public AplicacionAnticipos(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.profesionalCRUD = new ProfesionalCRUD(conexion, id_empresa, empresa);
        this.arrayId_profesional = new ArrayList();
        
        initComponents();
        
        modeloPresupuesto = (DefaultTableModel) this.jTablaPrespuesto.getModel();
        this.jTablaPrespuesto.setModel(modeloPresupuesto);
        tableCellRenderer = new DateRenderer();
        jTablaPrespuesto.getColumnModel().getColumn(0).setCellRenderer(tableCellRenderer);
        
        modeloAnticipo = (DefaultTableModel) this.jTablaAnticipo.getModel();
        this.jTablaAnticipo.setModel(modeloAnticipo);
        
        consultaZona();
        consultaProfesional();
        llenaComboZona();
        limpia();
        zonaUsuario();
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
            this.jComboZona.setSelectedIndex(0);
        }
        this.jComboProfesional.setSelectedIndex(-1);
        
        modeloPresupuesto.getDataVector().removeAllElements();
        modeloPresupuesto.fireTableDataChanged();
        
        modeloAnticipo.getDataVector().removeAllElements();
        modeloAnticipo.fireTableDataChanged();
        
        this.jLblId_presupuesto.setText("---");
        this.jLblImporte.setText("0,00");
        this.jLblAplicado.setText("0,00");
        this.jLblSaldo.setText("0,00");
    }
    
    private boolean consultaPresuAplicacion(ArrayList parametros){
        modeloPresupuesto.getDataVector().removeAllElements();
        modeloPresupuesto.fireTableDataChanged();
        try {
            rsConsulta = conexion.procAlmacenado("consultaPresuAplicacion", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            if (rsConsulta.first()){
                rsConsulta.beforeFirst();
                while (rsConsulta.next()){
                    Date fechaCirugia = rsConsulta.getDate("fechaCirugia");
                    int id_presupuesto = rsConsulta.getInt("id_presupuesto");
                    String paciente = rsConsulta.getString("paciente");
                    String entidad = rsConsulta.getString("entidad");
                    Double pendiente = rsConsulta.getDouble("pendiente");
                    
                    Object[] fila = {fechaCirugia, id_presupuesto, paciente, entidad, pendiente};
                    modeloPresupuesto.addRow(fila);
                }
            }else{
                JOptionPane.showMessageDialog(this, "No hay presupuestos pendientes", title, WIDTH);
                return false;
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return true;
    }
    
    private void consultaAnticipoAplicacion(ArrayList parametros){
        modeloAnticipo.getDataVector().removeAllElements();
        modeloAnticipo.fireTableDataChanged();
        try {
            rsConsulta = conexion.procAlmacenado("consultaAnticipoAplicacion", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            if (rsConsulta.first()){
                rsConsulta.beforeFirst();
                while (rsConsulta.next()){
                    String fecha = rsConsulta.getString("fecha");
                    Double importe = rsConsulta.getDouble("importe");
                    Double aplica = 0.00;
                    int id_mayorProfesional = rsConsulta.getInt("id_mayorProfesional");
                    
                    Object[] fila = {fecha, importe, aplica, id_mayorProfesional};
                    modeloAnticipo.addRow(fila);
                }
            }else{
                JOptionPane.showMessageDialog(this, "No hay anticipos para aplicar", title, WIDTH);
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    private void calculaAplicacion(){
        BigDecimal totalAplicacion = BigDecimal.ZERO;
        for (int i = 0; i < modeloAnticipo.getRowCount(); i++){
            BigDecimal aplicacion = new BigDecimal((double) modeloAnticipo.getValueAt(i, 2));
            totalAplicacion = totalAplicacion.add(aplicacion).setScale(escala, RM);
        }
        
        this.jLblAplicado.setText(String.format("%.2f", totalAplicacion));
    }
    
    private void calculaSaldo(){
        BigDecimal importe = new BigDecimal(this.jLblImporte.getText().replace(',', '.'));
        BigDecimal aplicado = new BigDecimal(this.jLblAplicado.getText().replace(',', '.'));
        BigDecimal saldo = importe.subtract(aplicado).setScale(escala, RM);
        
        this.jLblSaldo.setText(String.format("%.2f", saldo));
    }
    
    private String validaObligatorios(){
        String respuesta = "";
        if (this.jLblId_presupuesto.getText().equals("---")){
            respuesta = "Seleccione algún presupuesto";
            return respuesta;
        }
        
        /*
        if (new BigDecimal(this.jLblSaldo.getText().replace(',', '.')).compareTo(BigDecimal.ZERO) != 0){
            respuesta = "No se ha completado la aplicación";
            return respuesta;
        }
        */
        return respuesta;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu = new javax.swing.JPopupMenu();
        jDetallePresupuesto = new javax.swing.JMenuItem();
        jLabel2 = new javax.swing.JLabel();
        jLblId_presupuesto = new javax.swing.JLabel();
        jLblImporte = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLblAplicado = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLblSaldo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaPrespuesto = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablaAnticipo = new javax.swing.JTable();
        jBtnGuardar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboProfesional = new javax.swing.JComboBox();
        jBtnFiltra = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();

        jDetallePresupuesto.setText("Ver presupuesto");
        jDetallePresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetallePresupuestoActionPerformed(evt);
            }
        });
        jPopupMenu.add(jDetallePresupuesto);

        setTitle("Aplicación de anticipos");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Turno");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLblId_presupuesto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLblId_presupuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblId_presupuesto.setText("0");
        jLblId_presupuesto.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLblImporte.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLblImporte.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblImporte.setText("0,00");
        jLblImporte.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Importe");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Aplicado");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLblAplicado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLblAplicado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblAplicado.setText("0,00");
        jLblAplicado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Saldo");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLblSaldo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLblSaldo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblSaldo.setText("0,00");
        jLblSaldo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Turnos pendientes"));

        jTablaPrespuesto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha Cx.", "Turno", "Paciente", "Entidad", "Pendiente"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
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
        jTablaPrespuesto.setComponentPopupMenu(jPopupMenu);
        jTablaPrespuesto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaPrespuestoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaPrespuesto);
        if (jTablaPrespuesto.getColumnModel().getColumnCount() > 0) {
            jTablaPrespuesto.getColumnModel().getColumn(0).setMinWidth(75);
            jTablaPrespuesto.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTablaPrespuesto.getColumnModel().getColumn(0).setMaxWidth(75);
            jTablaPrespuesto.getColumnModel().getColumn(1).setMinWidth(50);
            jTablaPrespuesto.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTablaPrespuesto.getColumnModel().getColumn(1).setMaxWidth(50);
            jTablaPrespuesto.getColumnModel().getColumn(4).setMinWidth(75);
            jTablaPrespuesto.getColumnModel().getColumn(4).setPreferredWidth(75);
            jTablaPrespuesto.getColumnModel().getColumn(4).setMaxWidth(75);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Anticipos sin aplicar"));

        jTablaAnticipo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Importe", "Aplica", "id_mayorProfesional"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaAnticipo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaAnticipoMouseClicked(evt);
            }
        });
        jTablaAnticipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTablaAnticipoKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTablaAnticipo);
        if (jTablaAnticipo.getColumnModel().getColumnCount() > 0) {
            jTablaAnticipo.getColumnModel().getColumn(0).setMinWidth(75);
            jTablaAnticipo.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTablaAnticipo.getColumnModel().getColumn(0).setMaxWidth(75);
            jTablaAnticipo.getColumnModel().getColumn(1).setMinWidth(75);
            jTablaAnticipo.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTablaAnticipo.getColumnModel().getColumn(1).setMaxWidth(75);
            jTablaAnticipo.getColumnModel().getColumn(2).setMinWidth(75);
            jTablaAnticipo.getColumnModel().getColumn(2).setPreferredWidth(75);
            jTablaAnticipo.getColumnModel().getColumn(2).setMaxWidth(75);
            jTablaAnticipo.getColumnModel().getColumn(3).setMinWidth(0);
            jTablaAnticipo.getColumnModel().getColumn(3).setPreferredWidth(0);
            jTablaAnticipo.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("* Profesional");

        jComboProfesional.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProfesionalMouseClicked(evt);
            }
        });

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

        jLabel16.setText("* Zona");

        jComboZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboZonaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboProfesional, 0, 280, Short.MAX_VALUE)
                    .addComponent(jComboZona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jBtnFiltra)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)))
                    .addComponent(jBtnFiltra))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(315, 315, 315)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jBtnGuardar)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLblAplicado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLblImporte, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLblId_presupuesto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLblSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnSalir, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLblId_presupuesto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLblImporte))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLblAplicado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLblSaldo))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnSalir))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios().equals("")){
            ArrayList parametros = new ArrayList();
            
            int id_profesional = (int) arrayId_profesional.get(this.jComboProfesional.getSelectedIndex());
            int id_presupuesto = Integer.parseInt(this.jLblId_presupuesto.getText());
            
            for (int i = 0; i < modeloAnticipo.getRowCount(); i++){
                double aplicacion = (double) modeloAnticipo.getValueAt(i, 2);
                if (aplicacion > 0.00) {
                    parametros.clear();
                    parametros.add(id_profesional);
                    parametros.add(aplicacion);
                    parametros.add(id_presupuesto);
                    parametros.add(this.usuario);

                    try{
                        conexion.procAlmacenado("aplicaAnticipo", parametros,
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    }catch (Exception ex){
                        Utiles.enviaError(this.empresa,ex);
                    }   
                }                   
            }
            
            limpia();
        }else{
            JOptionPane.showMessageDialog(this, validaObligatorios(), "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

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

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        if (this.jComboProfesional.getSelectedIndex() >= 0){
            ArrayList parametros = new ArrayList();
            int tmp = (int) arrayId_profesional.get(this.jComboProfesional.getSelectedIndex());
            parametros.add(tmp);
            parametros.add(this.id_empresa);
            
            if (consultaPresuAplicacion(parametros)){
                consultaAnticipoAplicacion(parametros);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione un profesional", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jTablaPrespuestoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaPrespuestoMouseClicked
        if (evt.getClickCount() == 2){            
            for (int i = 0; i < modeloAnticipo.getRowCount(); i++){
                modeloAnticipo.setValueAt(0.00, i, 2);
            }
            
            int fila = jTablaPrespuesto.convertRowIndexToModel(jTablaPrespuesto.getSelectedRow());
            this.jLblId_presupuesto.setText(modeloPresupuesto.getValueAt(fila, 1).toString());
            this.jLblImporte.setText(String.format("%.2f", (double) modeloPresupuesto.getValueAt(fila, 4)));
            calculaAplicacion();
            calculaSaldo();
        }
    }//GEN-LAST:event_jTablaPrespuestoMouseClicked

    private void jTablaAnticipoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaAnticipoMouseClicked
        if (evt.getClickCount() == 2){
            int fila = jTablaAnticipo.convertRowIndexToModel(jTablaAnticipo.getSelectedRow());
            modeloAnticipo.setValueAt(0.00, fila, 2);
            calculaAplicacion();
            calculaSaldo();
            
            BigDecimal saldo = new BigDecimal(this.jLblSaldo.getText().replace(',', '.'));
            BigDecimal importe = new BigDecimal((double) modeloAnticipo.getValueAt(fila, 1));
            BigDecimal aplicacion;
            
            if(importe.compareTo(saldo) == 1){
                aplicacion = saldo;
            }else{
                aplicacion = importe;
            }
            
            modeloAnticipo.setValueAt(aplicacion.doubleValue(), fila, 2);
        }
        
        calculaAplicacion();
        calculaSaldo();
    }//GEN-LAST:event_jTablaAnticipoMouseClicked

    private void jTablaAnticipoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTablaAnticipoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            int fila = jTablaAnticipo.convertRowIndexToModel(jTablaAnticipo.getSelectedRow());
            BigDecimal saldo = new BigDecimal(this.jLblSaldo.getText().replace(',', '.'));
            BigDecimal aplicacion = new BigDecimal((double) modeloAnticipo.getValueAt(fila, 2));
            if (aplicacion.compareTo(saldo) == 1){
                modeloAnticipo.setValueAt(saldo.doubleValue(), fila, 2);
            }
        }
        
        calculaAplicacion();
        calculaSaldo();
    }//GEN-LAST:event_jTablaAnticipoKeyReleased

    private void jDetallePresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetallePresupuestoActionPerformed
        int fila = jTablaPrespuesto.convertRowIndexToModel(jTablaPrespuesto.getSelectedRow());
        if (fila >= 0){
            Presupuesto presupuesto = new Presupuesto(conexion, id_empresa, empresa, "");
            presupuesto.setId_presupuesto(modeloPresupuesto.getValueAt(fila, 1).toString());
            Principal.dp.add(presupuesto);
            presupuesto.toFront();
            presupuesto.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetallePresupuestoActionPerformed

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        int indiceZona = this.jComboZona.getSelectedIndex();
        llenaComboProfesional(indiceZona);
    }//GEN-LAST:event_jComboZonaActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JComboBox jComboProfesional;
    private javax.swing.JComboBox jComboZona;
    private javax.swing.JMenuItem jDetallePresupuesto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLblAplicado;
    private javax.swing.JLabel jLblId_presupuesto;
    private javax.swing.JLabel jLblImporte;
    private javax.swing.JLabel jLblSaldo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTablaAnticipo;
    private javax.swing.JTable jTablaPrespuesto;
    // End of variables declaration//GEN-END:variables
}
