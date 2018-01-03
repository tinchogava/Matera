/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.vistasRapidas;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class ComprobantesAsociados extends javax.swing.JDialog {
    Conexion conexion;
    int id_empresa;
    String empresa;
    DefaultTableModel modelo;
    ResultSet rsConsulta;
    
    /**
     * Creates new form ComprobantesAsociados
     * @param parent
     * @param modal
     * @param conexion
     * @param id_empresa
     * @param empresa
     */
    public ComprobantesAsociados(java.awt.Frame parent, boolean modal, Conexion conexion, int id_empresa, String empresa) {
        super(parent, modal);
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        
        initComponents();
        setTitle("Comprobantes asociados");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png")).getImage());
        setLocationRelativeTo(parent);
        this.modelo = (DefaultTableModel) this.jTablaComprobantes.getModel();
        this.jTablaComprobantes.setModel(modelo);        
    }

    public void setId_presupuesto(String id_presupuesto){
        this.jLblId_presupuesto.setText(id_presupuesto);
        consulta(id_presupuesto);
    }
    
    private void consulta(String id){
        try {
            ArrayList parametros = new ArrayList();
            int id_presupuesto = Integer.parseInt(id);
            parametros.add(id_presupuesto);
            parametros.add(id_empresa);
            
            rsConsulta = conexion.procAlmacenado("consultaComprobantesAsociados", parametros,
                    empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            modelo.getDataVector().removeAllElements();
            modelo.fireTableDataChanged();
            double total = 0.00;
            this.jLblTotal.setText("$ " + String.format("%.2f", total));
            
            if (rsConsulta.first()) {
                rsConsulta.beforeFirst();
                while (rsConsulta.next()) {
                    String fecha = rsConsulta.getString("fecha");
                    String tipoComp = rsConsulta.getString("tipoComp");
                    String numComp = rsConsulta.getString("numComp");
                    double debe = rsConsulta.getDouble("debe");
                    double haber = rsConsulta.getDouble("haber");
                    
                    Object[] fila = {fecha, tipoComp, numComp, debe, haber};
                    modelo.addRow(fila);
                    
                    total = total + debe - haber;
                }
                this.jLblTotal.setText("$ " + String.format("%.2f", total));
            }else{
                JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Utiles.enviaError(empresa,ex);
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

        jLabel1 = new javax.swing.JLabel();
        jLblId_presupuesto = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablaComprobantes = new javax.swing.JTable();
        jBtnSalir = new javax.swing.JButton();
        jLblTotal = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Turno");

        jLblId_presupuesto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblId_presupuesto.setText("jLabel2");

        jTablaComprobantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "", "Comprobante", "Debe", "Haber"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
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
        jScrollPane2.setViewportView(jTablaComprobantes);
        if (jTablaComprobantes.getColumnModel().getColumnCount() > 0) {
            jTablaComprobantes.getColumnModel().getColumn(0).setMinWidth(75);
            jTablaComprobantes.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTablaComprobantes.getColumnModel().getColumn(0).setMaxWidth(75);
            jTablaComprobantes.getColumnModel().getColumn(1).setMinWidth(50);
            jTablaComprobantes.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTablaComprobantes.getColumnModel().getColumn(1).setMaxWidth(50);
            jTablaComprobantes.getColumnModel().getColumn(3).setMinWidth(100);
            jTablaComprobantes.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTablaComprobantes.getColumnModel().getColumn(3).setMaxWidth(100);
            jTablaComprobantes.getColumnModel().getColumn(4).setMinWidth(100);
            jTablaComprobantes.getColumnModel().getColumn(4).setPreferredWidth(100);
            jTablaComprobantes.getColumnModel().getColumn(4).setMaxWidth(100);
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

        jLblTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblTotal.setText("jLabel2");

        jLabel2.setText("Total");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLblId_presupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jBtnSalir))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLblId_presupuesto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLblTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnSalir))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        try {
            if (rsConsulta != null) {
                rsConsulta.close();
            }
        } catch (Exception ex) {
            Utiles.enviaError(this.empresa, ex);
        }
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLblId_presupuesto;
    private javax.swing.JLabel jLblTotal;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTablaComprobantes;
    // End of variables declaration//GEN-END:variables
}
