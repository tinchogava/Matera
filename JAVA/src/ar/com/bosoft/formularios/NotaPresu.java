package ar.com.bosoft.formularios;

import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.NotaPresuCRUD;
import ar.com.bosoft.data.NotaPresuData;
import javax.swing.JOptionPane;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class NotaPresu extends javax.swing.JInternalFrame {
    Conexion conexion;
    NotaPresuCRUD notaPresuCRUD;
    int id_empresa;
    String empresa, usuario;
    
    public NotaPresu(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        this.notaPresuCRUD = new NotaPresuCRUD(conexion, id_empresa, empresa, usuario);
        
        initComponents();
        
        consulta();
    }

    private void consulta(){
        NotaPresuData notaPresuVO = notaPresuCRUD.consulta();
        if (notaPresuVO == null){
            this.jTxtNota.setText("");
        }else{
            this.jTxtNota.setText(notaPresuVO.getNota().trim());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTxtNota = new javax.swing.JTextArea();
        jBtnGuardar = new javax.swing.JButton();
        jBtnPreview = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();

        setTitle("Nota en presupuesto");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jTxtNota.setColumns(20);
        jTxtNota.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTxtNota.setRows(5);
        jScrollPane1.setViewportView(jTxtNota);

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

        jBtnPreview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/preview.png"))); // NOI18N
        jBtnPreview.setBorderPainted(false);
        jBtnPreview.setContentAreaFilled(false);
        jBtnPreview.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnPreview.setFocusPainted(false);
        jBtnPreview.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/preview.png"))); // NOI18N
        jBtnPreview.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/preview.png"))); // NOI18N
        jBtnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPreviewActionPerformed(evt);
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBtnPreview)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnGuardar)
                        .addGap(186, 186, 186)
                        .addComponent(jBtnSalir))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jBtnGuardar)
                            .addComponent(jBtnPreview))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnSalir))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        NotaPresuData nuevo = new NotaPresuData();
        nuevo.setNota(this.jTxtNota.getText().trim());
        notaPresuCRUD.insert(nuevo);
        JOptionPane.showMessageDialog(this, "La nota se ha guardado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPreviewActionPerformed
        JOptionPane.showMessageDialog(this, this.jTxtNota.getText().trim(), "Previsualización", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jBtnPreviewActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnPreview;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTxtNota;
    // End of variables declaration//GEN-END:variables
}
