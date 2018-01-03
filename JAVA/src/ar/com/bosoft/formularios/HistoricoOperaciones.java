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
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.vistasRapidas.ComprobantesAsociados;
import ar.com.bosoft.vistasRapidas.Turno;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
public class HistoricoOperaciones extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa;
    String empresa, todos;
    
    ZonaCRUD zonaCRUD;
    ArrayList zonaArray;
    
    DefaultTableModel modelo;
    TableRowSorter sorter;
    TableCellRenderer tableCellRenderer;
    ResultSet rsConsulta;
    
    /**
     * Creates new form HistoricoOperaciones
     * @param conexion
     * @param id_empresa
     * @param empresa
     */
    public HistoricoOperaciones(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.todos = "-- TODOS --";
        
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        
        initComponents();
        
        modelo = (DefaultTableModel) this.jTabla.getModel();
        this.jTabla.setModel(modelo);
        sorter = new TableRowSorter(modelo);
        this.jTabla.setRowSorter(sorter);
        tableCellRenderer = new DateRenderer();
        jTabla.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        
        setJTexFieldChanged(this.jTxtBuscaConsulta);
        
        llenaComboZona();
        limpia();
        zonaUsuario();
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
          sorter.setRowFilter(null);
        } else {
          sorter.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTabla.setRowSorter(sorter);
        }
    }
    
    private void llenaComboZona(){
        this.jComboZona.addItem(this.todos);
        this.zonaArray = zonaCRUD.consulta(true);
        Iterator it = zonaArray.iterator();
        while (it.hasNext()) {
            ZonaData tmp = (ZonaData) it.next();
            this.jComboZona.addItem(tmp.getNombre());
        }
    }
    
    private void limpia(){
        this.jComboDias.setSelectedIndex(0);
        
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedIndex(0);
        }
        
        this.jTxtBuscaConsulta.setText("");
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
    }

    private void consulta(){
        try {
            modelo.getDataVector().removeAllElements();
            modelo.fireTableDataChanged();
            
            int dias = 0;
            if (this.jComboDias.getSelectedIndex() == 0) {
                dias = 180;
            }
            
            int id_zona = 0;
            if (!this.jComboZona.getSelectedItem().equals(this.todos)) {
                ZonaData tmp = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
                id_zona = tmp.getId_zona();
            }
            
            ArrayList parametros = new ArrayList();
            parametros.add(dias);
            parametros.add(id_zona);
            parametros.add(this.id_empresa);
            
            rsConsulta = conexion.procAlmacenado("consultaHistoricoOperaciones", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            if (rsConsulta.first()) {
                rsConsulta.beforeFirst();
                while (rsConsulta.next()) {
                    int id_presupuesto = rsConsulta.getInt("id_presupuesto");
                    Date fechaCx = rsConsulta.getDate("fechaCirugia");
                    String remitos = rsConsulta.getString("remitos");
                    String entidad = rsConsulta.getString("entidad");
                    String facturas = rsConsulta.getString("facturas");
                    String observaciones = rsConsulta.getString("observaciones");
                    String profesional = rsConsulta.getString("profesional");
                    String paciente = rsConsulta.getString("paciente");
                    String prestacion = rsConsulta.getString("prestacion");
                    String tecnico = rsConsulta.getString("tecnico");
                    
                    Object[] fila = {id_presupuesto, fechaCx, remitos, entidad, facturas, observaciones, profesional, paciente, prestacion, tecnico};
                    
                    modelo.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            Utiles.enviaError(empresa, ex);
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
        jDetallePresupuesto = new javax.swing.JMenuItem();
        jDetalleConsumido = new javax.swing.JMenuItem();
        jComprobantes = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboDias = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jBtnFiltra = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jTxtBuscaConsulta = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla = new javax.swing.JTable();
        jBtnSalir = new javax.swing.JButton();

        jDetalleTurno.setText("Turno");
        jDetalleTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetalleTurnoActionPerformed(evt);
            }
        });
        jPopupMenu.add(jDetalleTurno);

        jDetallePresupuesto.setText("Presupuesto");
        jDetallePresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetallePresupuestoActionPerformed(evt);
            }
        });
        jPopupMenu.add(jDetallePresupuesto);

        jDetalleConsumido.setText("Consumido");
        jDetalleConsumido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetalleConsumidoActionPerformed(evt);
            }
        });
        jPopupMenu.add(jDetalleConsumido);

        jComprobantes.setText("Ver comprobantes");
        jComprobantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComprobantesActionPerformed(evt);
            }
        });
        jPopupMenu.add(jComprobantes);

        setTitle("Histórico de operaciones");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jLabel1.setText("Período");

        jComboDias.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "180 días", "Todos" }));

        jLabel16.setText("Zona");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnFiltra))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnFiltra))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setFocusable(false);

        jTxtBuscaConsulta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaConsultaFocusGained(evt);
            }
        });

        jLabel8.setText("Buscar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBuscaConsulta)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBuscaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)))
        );

        jTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Turno", "Fecha Cx.", "Remitos", "Entidad", "Facturas", "Observaciones", "profesional", "paciente", "prestacion", "tecnico"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabla.setComponentPopupMenu(jPopupMenu);
        jTabla.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTabla);
        if (jTabla.getColumnModel().getColumnCount() > 0) {
            jTabla.getColumnModel().getColumn(0).setMinWidth(50);
            jTabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTabla.getColumnModel().getColumn(0).setMaxWidth(50);
            jTabla.getColumnModel().getColumn(1).setMinWidth(75);
            jTabla.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(1).setMaxWidth(75);
            jTabla.getColumnModel().getColumn(2).setMinWidth(150);
            jTabla.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTabla.getColumnModel().getColumn(2).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(4).setMinWidth(150);
            jTabla.getColumnModel().getColumn(4).setPreferredWidth(150);
            jTabla.getColumnModel().getColumn(4).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(6).setMinWidth(0);
            jTabla.getColumnModel().getColumn(6).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(6).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(7).setMinWidth(0);
            jTabla.getColumnModel().getColumn(7).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(7).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(8).setMinWidth(0);
            jTabla.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(8).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(9).setMinWidth(0);
            jTabla.getColumnModel().getColumn(9).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(9).setMaxWidth(0);
        }

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 329, Short.MAX_VALUE)))))
                .addGap(3, 3, 3))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnSalir)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        consulta();
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jTxtBuscaConsultaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaConsultaFocusGained
        this.jTxtBuscaConsulta.select(0, this.jTxtBuscaConsulta.getText().length());
    }//GEN-LAST:event_jTxtBuscaConsultaFocusGained

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

    private void jDetallePresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetallePresupuestoActionPerformed
        if (jTabla.getSelectedRow() >= 0){
            int fila = jTabla.convertRowIndexToModel(jTabla.getSelectedRow());
        
            Presupuesto presupuesto = new Presupuesto(conexion, id_empresa, empresa, "");
            presupuesto.setId_presupuesto(modelo.getValueAt(fila, 0).toString());
            Principal.dp.add(presupuesto);
            presupuesto.toFront();
            presupuesto.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetallePresupuestoActionPerformed

    private void jDetalleConsumidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetalleConsumidoActionPerformed
        if (jTabla.getSelectedRow() >= 0){
            int fila = jTabla.convertRowIndexToModel(jTabla.getSelectedRow());
        
            String id = String.valueOf(modelo.getValueAt(fila, 0).toString());
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String fechaCx = "";
            try {
                fechaCx = format.format((Date) modelo.getValueAt(fila, 1));
            } catch (Exception e) {
                //Fecha invalida
            }
            String prestacion = Utiles.valueAt(modelo, fila, "prestacion").toString();
            if (prestacion.isEmpty()){
                JOptionPane.showMessageDialog(this, "Turno: "+id+" - No hay nada para mostrar", "Atención", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String tecnico = modelo.getValueAt(fila, 9).toString();
            String entidad = modelo.getValueAt(fila, 3).toString();
            String profesional = modelo.getValueAt(fila, 6).toString();
            String paciente = modelo.getValueAt(fila, 7).toString();
            String observaciones = modelo.getValueAt(fila, 5).toString();
            RemitoDetalle remitoDetalle = new RemitoDetalle(null, false, Integer.parseInt(id));
            remitoDetalle.setDatos();
            remitoDetalle.setVisible(true);
//            Consumido consumido = new Consumido(conexion, id_empresa, empresa);
//            consumido.setDatos(id, fechaCirugia, prestacion, tecnico, entidad, profesional, paciente, observaciones);
//            if (consumido.valida()) {
//                consumido.toFront();
//                Principal.muestra(consumido);
//                consumido.setVisible(true);
//            }
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jDetalleConsumidoActionPerformed

    private void jComprobantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComprobantesActionPerformed
        if (jTabla.getSelectedRow() >= 0) {
            int fila = jTabla.convertRowIndexToModel(jTabla.getSelectedRow());
            ComprobantesAsociados comprobantesAsociados = new ComprobantesAsociados(null, true, conexion, id_empresa, empresa);
            comprobantesAsociados.setId_presupuesto(modelo.getValueAt(fila, 0).toString());
            comprobantesAsociados.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el turno a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jComprobantesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JComboBox jComboDias;
    private javax.swing.JComboBox jComboZona;
    private javax.swing.JMenuItem jComprobantes;
    private javax.swing.JMenuItem jDetalleConsumido;
    private javax.swing.JMenuItem jDetallePresupuesto;
    private javax.swing.JMenuItem jDetalleTurno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTabla;
    private javax.swing.JTextField jTxtBuscaConsulta;
    // End of variables declaration//GEN-END:variables
}
