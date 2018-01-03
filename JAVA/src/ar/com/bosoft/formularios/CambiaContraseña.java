/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.conexion.Conexion;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class CambiaContraseña extends javax.swing.JDialog {
    Conexion conexion;
    int id_usuario;
    String empresa, usuario, passwordActual;
    
    public CambiaContraseña(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Cambio de contraseña");
        setLocationRelativeTo(parent);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png")).getImage());
        initComponents();
    }

    public void setConexion(Conexion conexion){
        this.conexion = conexion;
    }
    
    public void setId_usuario(int id_usuario){
        this.id_usuario = id_usuario;
    }
    
    public void setEmpresa(String empresa){
        this.empresa = empresa;
    }
    
    public void setUsuario(String usuario){
        this.usuario = usuario;
    }
    
    public void setPasswordActual(String passwordActual){
        this.passwordActual = passwordActual;
    }
    
    private boolean validaPasswordActual(){
        return this.jPasswordActual.getText().trim().equals(this.passwordActual);
    }
    
    private boolean comparaPass(){
        return this.jPasswordNueva.getText().trim().equals(this.jPasswordNueva2.getText().trim());
    }
    
    private boolean validaObligatorios(){
        return !this.jPasswordActual.getText().isEmpty() &&
                !this.jPasswordNueva.getText().isEmpty() &&
                !this.jPasswordNueva2.getText().isEmpty();
    }
    
    public void limpiaCampos(){
        this.jLblUsuario.setText(this.usuario);
        this.jPasswordActual.setText("");
        this.jPasswordNueva.setText("");
        this.jPasswordNueva2.setText("");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLblUsuario = new javax.swing.JLabel();
        jPasswordActual = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPasswordNueva = new javax.swing.JPasswordField();
        jPasswordNueva2 = new javax.swing.JPasswordField();
        jBtnGuardar = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();
        jBtnMostrar = new javax.swing.JButton();
        jBtnMostrar1 = new javax.swing.JButton();
        jBtnMostrar2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        jLblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblUsuario.setText("...");

        jLabel2.setText("Contraseña actual");

        jLabel3.setText("Contraseña nueva");

        jLabel4.setText("Repita la nueva contraseña");

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

        jBtnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/cancelar.png"))); // NOI18N
        jBtnCancelar.setText("Cancelar");
        jBtnCancelar.setBorderPainted(false);
        jBtnCancelar.setContentAreaFilled(false);
        jBtnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnCancelar.setFocusPainted(false);
        jBtnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jBtnCancelar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/cancelar.png"))); // NOI18N
        jBtnCancelar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/salir.png"))); // NOI18N
        jBtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarActionPerformed(evt);
            }
        });

        jBtnMostrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/16x16/enabled/eye_icon&16.png"))); // NOI18N
        jBtnMostrar.setBorderPainted(false);
        jBtnMostrar.setContentAreaFilled(false);
        jBtnMostrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnMostrar.setFocusPainted(false);
        jBtnMostrar.setFocusable(false);
        jBtnMostrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBtnMostrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBtnMostrarMouseExited(evt);
            }
        });

        jBtnMostrar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/16x16/enabled/eye_icon&16.png"))); // NOI18N
        jBtnMostrar1.setBorderPainted(false);
        jBtnMostrar1.setContentAreaFilled(false);
        jBtnMostrar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnMostrar1.setFocusPainted(false);
        jBtnMostrar1.setFocusable(false);
        jBtnMostrar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBtnMostrar1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBtnMostrar1MouseExited(evt);
            }
        });

        jBtnMostrar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/16x16/enabled/eye_icon&16.png"))); // NOI18N
        jBtnMostrar2.setBorderPainted(false);
        jBtnMostrar2.setContentAreaFilled(false);
        jBtnMostrar2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnMostrar2.setFocusPainted(false);
        jBtnMostrar2.setFocusable(false);
        jBtnMostrar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBtnMostrar2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBtnMostrar2MouseExited(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPasswordActual, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPasswordNueva, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPasswordNueva2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jBtnMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jBtnMostrar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jBtnMostrar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnGuardar)
                        .addGap(72, 72, 72)
                        .addComponent(jBtnCancelar))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblUsuario)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jPasswordActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jBtnMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jPasswordNueva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jBtnMostrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jPasswordNueva2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jBtnMostrar2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jBtnGuardar)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnCancelar))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios()){
            if (validaPasswordActual()){
                if (comparaPass()){
                    String contraseña = this.jPasswordNueva.getText().trim();
                    
                    ArrayList parametros = new ArrayList();
                    parametros.add(this.id_usuario);
                    parametros.add(contraseña);
                    
                    conexion.procAlmacenado("cambiaContraseña", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    
                    JOptionPane.showMessageDialog(this, "Se ha cambiado la contraseña.\nVuelva a ejecutar el Sistema para activar los cambios.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }else{
                    JOptionPane.showMessageDialog(this,"Las contraseñas no coinciden", "Información",JOptionPane.INFORMATION_MESSAGE);
                    this.jPasswordNueva.setText("");
                    this.jPasswordNueva2.setText("");
                    this.jPasswordNueva.requestFocus();
                }
            }else{
                JOptionPane.showMessageDialog(this,"La contraseña actual es incorrecta", "Información",JOptionPane.INFORMATION_MESSAGE);
                this.jPasswordActual.setText("");
            }
        }else{
            JOptionPane.showMessageDialog(this,"Complete todos los datos obligatorios (*)", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnCancelarActionPerformed

    private void jBtnMostrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnMostrarMouseEntered
        this.jPasswordActual.setEchoChar((char) 0);
    }//GEN-LAST:event_jBtnMostrarMouseEntered

    private void jBtnMostrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnMostrarMouseExited
        this.jPasswordActual.setEchoChar('\u25cf');
    }//GEN-LAST:event_jBtnMostrarMouseExited

    private void jBtnMostrar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnMostrar1MouseEntered
        this.jPasswordNueva.setEchoChar((char) 0);
    }//GEN-LAST:event_jBtnMostrar1MouseEntered

    private void jBtnMostrar1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnMostrar1MouseExited
        this.jPasswordNueva.setEchoChar('\u25cf');
    }//GEN-LAST:event_jBtnMostrar1MouseExited

    private void jBtnMostrar2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnMostrar2MouseEntered
        this.jPasswordNueva2.setEchoChar((char) 0);
    }//GEN-LAST:event_jBtnMostrar2MouseEntered

    private void jBtnMostrar2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnMostrar2MouseExited
        this.jPasswordNueva2.setEchoChar('\u25cf');
    }//GEN-LAST:event_jBtnMostrar2MouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnMostrar;
    private javax.swing.JButton jBtnMostrar1;
    private javax.swing.JButton jBtnMostrar2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLblUsuario;
    private javax.swing.JPasswordField jPasswordActual;
    private javax.swing.JPasswordField jPasswordNueva;
    private javax.swing.JPasswordField jPasswordNueva2;
    // End of variables declaration//GEN-END:variables
}
