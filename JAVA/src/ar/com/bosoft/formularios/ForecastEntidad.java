/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.dialogos.AvisoEspera;
import java.awt.Rectangle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class ForecastEntidad extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa;
    String empresa;
    ResultSet rsConsulta;
    DefaultTableModel modeloDisponibles, modeloAsignadas;
    TableRowSorter sorterDisponibles, sorterAsignadas;

    AvisoEspera avisoEspera;
    /**
     * Creates new form ForecastEntidad
     * @param conexion
     * @param id_empresa
     * @param empresa
     */
    public ForecastEntidad(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        
        initComponents();
        
        modeloDisponibles  = (DefaultTableModel) this.jTablaDisponibles.getModel();
        this.jTablaDisponibles.setModel(modeloDisponibles);
        sorterDisponibles = new TableRowSorter(modeloDisponibles);
        jTablaDisponibles.setRowSorter(sorterDisponibles);
        
        modeloAsignadas  = (DefaultTableModel) this.jTablaAsignadas.getModel();
        this.jTablaAsignadas.setModel(modeloAsignadas);
        sorterAsignadas = new TableRowSorter(modeloAsignadas);
        jTablaAsignadas.setRowSorter(sorterAsignadas);
        
        progamaBuscaDisponibles(jTxtBuscaDisponible);
        progamaBuscaAsignadas(jTxtBuscaAsignadas);
        
        consulta();
    }

    private void progamaBuscaDisponibles(JTextField txt){
        DocumentListener documentListener = new DocumentListener() {
 
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                printItDisponibles(documentEvent);
            }
 
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                printItDisponibles(documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                printItDisponibles(documentEvent);
            }
        };
        txt.getDocument().addDocumentListener(documentListener); 
    }
    
    private void printItDisponibles(DocumentEvent documentEvent) {
        DocumentEvent.EventType type = documentEvent.getType();
 
        if (type.equals(DocumentEvent.EventType.CHANGE)){
 
        }else if (type.equals(DocumentEvent.EventType.INSERT)){
            JTxtBuscaDisponiblesChanged();
        }else if (type.equals(DocumentEvent.EventType.REMOVE)){
            JTxtBuscaDisponiblesChanged();
        }
    }
    
    private void JTxtBuscaDisponiblesChanged(){
        String text = jTxtBuscaDisponible.getText().trim().replaceAll("'", "´");
        if (text.length() == 0) {
          sorterDisponibles.setRowFilter(null);
        } else {
          sorterDisponibles.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTablaDisponibles.setRowSorter(sorterDisponibles);
        }
    }
    
    private void progamaBuscaAsignadas(JTextField txt){
        DocumentListener documentListener = new DocumentListener() {
 
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                printItAsignadas(documentEvent);
            }
 
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                printItAsignadas(documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                printItAsignadas(documentEvent);
            }
        };
        txt.getDocument().addDocumentListener(documentListener); 
    }
    
    private void printItAsignadas(DocumentEvent documentEvent) {
        DocumentEvent.EventType type = documentEvent.getType();
 
        if (type.equals(DocumentEvent.EventType.CHANGE)){
 
        }else if (type.equals(DocumentEvent.EventType.INSERT)){
            JTxtBuscaAsignadasChanged();
        }else if (type.equals(DocumentEvent.EventType.REMOVE)){
            JTxtBuscaAsignadasChanged();
        }
    }
    
    private void JTxtBuscaAsignadasChanged(){
        String text = jTxtBuscaAsignadas.getText().trim().replaceAll("'", "´");
        if (text.length() == 0) {
          sorterAsignadas.setRowFilter(null);
        } else {
          sorterAsignadas.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTablaAsignadas.setRowSorter(sorterAsignadas);
        }
    }
    
    private void consulta(){
        try {
            modeloDisponibles.getDataVector().removeAllElements();
            modeloDisponibles.fireTableDataChanged();
            modeloAsignadas.getDataVector().removeAllElements();
            modeloAsignadas.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            parametros.add(this.id_empresa);
            
            rsConsulta = conexion.procAlmacenado("consultaForecastEntidad", parametros,  
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            if (rsConsulta.first()) {
                rsConsulta.beforeFirst();
                while (rsConsulta.next()) {
                    String nombre = rsConsulta.getString("nombre");
                    int id_entidad = rsConsulta.getInt("id_entidad");
                    String asignada = rsConsulta.getString("asignada");
                    
                    Object[] fila = {nombre, id_entidad};
                    if (asignada.equals("SI")) {
                        modeloAsignadas.addRow(fila);
                    }else{
                        modeloDisponibles.addRow(fila);
                    }
                }
            }
            
        } catch (Exception ex) {
            Utiles.enviaError(this.empresa, ex);
        }
    }
    
    private void guardar(){
        ArrayList parametros = new ArrayList();
        parametros.add(Principal.usuarioData.getNombre());
        parametros.add(Principal.equipo);
        parametros.add(this.id_empresa);
        
        conexion.procAlmacenado("deleteForecastEntidad", parametros,  
            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        for (int i = 0; i < modeloAsignadas.getRowCount(); i++) {
            parametros.clear();
            
            int id_entidad = (int) modeloAsignadas.getValueAt(i, 1);
            parametros.add(id_entidad);
            parametros.add(this.id_empresa);
            
            conexion.procAlmacenado("insertForecastEntidad", parametros,  
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaDisponibles = new javax.swing.JTable();
        jTxtBuscaDisponible = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollAsignadas = new javax.swing.JScrollPane();
        jTablaAsignadas = new javax.swing.JTable();
        jTxtBuscaAsignadas = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jBtnGuardar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();

        setTitle("Forecast - Entidades");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Disponibles"));

        jTablaDisponibles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "id_entidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaDisponibles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaDisponiblesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaDisponibles);
        if (jTablaDisponibles.getColumnModel().getColumnCount() > 0) {
            jTablaDisponibles.getColumnModel().getColumn(1).setMinWidth(0);
            jTablaDisponibles.getColumnModel().getColumn(1).setPreferredWidth(0);
            jTablaDisponibles.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        jLabel22.setText("Buscar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtBuscaDisponible))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBuscaDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Asignadas"));

        jTablaAsignadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "id_entidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaAsignadas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaAsignadasMouseClicked(evt);
            }
        });
        jScrollAsignadas.setViewportView(jTablaAsignadas);
        if (jTablaAsignadas.getColumnModel().getColumnCount() > 0) {
            jTablaAsignadas.getColumnModel().getColumn(1).setMinWidth(0);
            jTablaAsignadas.getColumnModel().getColumn(1).setPreferredWidth(0);
            jTablaAsignadas.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        jLabel23.setText("Buscar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtBuscaAsignadas))
                    .addComponent(jScrollAsignadas, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBuscaAsignadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollAsignadas, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/guardar.png"))); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.setBorderPainted(false);
        jBtnGuardar.setContentAreaFilled(false);
        jBtnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnGuardar.setFocusPainted(false);
        jBtnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnGuardar)
                        .addGap(268, 268, 268)
                        .addComponent(jBtnSalir)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnSalir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTablaDisponiblesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaDisponiblesMouseClicked
        if (evt.getClickCount() == 2) {
            int fila = jTablaDisponibles.convertRowIndexToModel(jTablaDisponibles.getSelectedRow());

            String nombre = modeloDisponibles.getValueAt(fila, 0).toString();
            int id_entidad = (int) modeloDisponibles.getValueAt(fila, 1);
            
            Object[] nuevo = {nombre, id_entidad};
            if (Utiles.existeEnModelo(modeloAsignadas, 0, nombre)){
                JOptionPane.showMessageDialog(this, "Esta entidad ya ha sido seleccionada", "Atención", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            modeloAsignadas.addRow(nuevo);
            Rectangle r = this.jTablaAsignadas.getCellRect(modeloAsignadas.getRowCount() - 1, 0, true);
            this.jScrollAsignadas.getViewport().scrollRectToVisible(r);
            jTablaDisponibles.clearSelection();
        }
    }//GEN-LAST:event_jTablaDisponiblesMouseClicked

    private void jTablaAsignadasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaAsignadasMouseClicked
        if (evt.getClickCount() == 2) {
            int fila = jTablaAsignadas.convertRowIndexToModel(jTablaAsignadas.getSelectedRow());
            modeloAsignadas.removeRow(fila);
        }
    }//GEN-LAST:event_jTablaAsignadasMouseClicked

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ForecastEntidad.this.avisoEspera = new AvisoEspera(null, false, AvisoEspera.GUARDANDO);
                ForecastEntidad.this.avisoEspera.setVisible(true);
                Thread performer = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ForecastEntidad.this.guardar();
                        ForecastEntidad.this.consulta();
                        ForecastEntidad.this.avisoEspera.setVisible(false);
                    };
                }, "Performer");
                performer.start();
            }
        });
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollAsignadas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablaAsignadas;
    private javax.swing.JTable jTablaDisponibles;
    private javax.swing.JTextField jTxtBuscaAsignadas;
    private javax.swing.JTextField jTxtBuscaDisponible;
    // End of variables declaration//GEN-END:variables
}
