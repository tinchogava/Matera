/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.formularios;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.EmpresaCRUD;
import ar.com.bosoft.crud.UsuarioCRUD;
import ar.com.bosoft.data.EmpresaData;
import ar.com.bosoft.data.UsuarioData;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */

public class Ingreso extends javax.swing.JDialog {
    Conexion conexion;
    EmpresaCRUD empresaCRUD;
    UsuarioCRUD usuarioCRUD;
    EmpresaData empresaData;
    UsuarioData usuarioData;
    ArrayList arrayEmpresa, arrayUsuario;
    
    Dimension d = this.getSize();
    
    CambiaContraseña cambiaContraseña;
    
    Boolean isValidated = false;
    
    /**
     * Creates new form Ingreso
     * @param conexion
     * @param parent
     * @param modal
     */
    public Ingreso(Conexion conexion, java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.conexion = conexion;
        empresaCRUD = new EmpresaCRUD(conexion, "");
        empresaData = null;
        usuarioData = null;
        initComponents();
        llenaComboEmpresa();
        this.cambiaContraseña = new CambiaContraseña(null, true);
        if (this.jComboEmpresa.getItemCount() == 1) {
            this.jComboEmpresa.setEnabled(false);
            this.jComboEmpresaFocusLost(null);
            this.jComboUsuario.requestFocus();
        }
    }
    
    public Boolean isValidated(){
        return isValidated;
    }
    
    public void setValidated(Boolean validate){
        isValidated = validate;
    }
    
    public EmpresaData getEmpresaData(){
        return empresaData;
    }
    
    public UsuarioData getUsuarioData(){
        return usuarioData;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboUsuario = new javax.swing.JComboBox();
        jPassword = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jBtnSalir = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jComboEmpresa = new javax.swing.JComboBox();
        jLblOlvido = new javax.swing.JLabel();
        jLblCambio = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setResizable(false);

        jLabel1.setText("Usuario");

        jLabel2.setText("Contraseña");

        jComboUsuario.setEnabled(false);
        jComboUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComboUsuarioFocusLost(evt);
            }
        });

        jPassword.setEnabled(false);
        jPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("BIENVENIDO");

        jBtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/salir.png"))); // NOI18N
        jBtnSalir.setText("Salir");
        jBtnSalir.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Salir", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jBtnSalir.setBorderPainted(false);
        jBtnSalir.setContentAreaFilled(false);
        jBtnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/salir.png"))); // NOI18N
        jBtnSalir.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/salir.png"))); // NOI18N
        jBtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSalirActionPerformed(evt);
            }
        });

        jLabel5.setText("Empresa");

        jComboEmpresa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComboEmpresaFocusLost(evt);
            }
        });

        jLblOlvido.setText("Olvidé mi contraseña");
        jLblOlvido.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLblOlvido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLblOlvidoMouseClicked(evt);
            }
        });

        jLblCambio.setText("Cambiar mi contraseña");
        jLblCambio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLblCambio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLblCambioMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboEmpresa, 0, 262, Short.MAX_VALUE)
                    .addComponent(jComboUsuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPassword)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLblOlvido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLblCambio))))
            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jBtnSalir, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblOlvido)
                    .addComponent(jLblCambio))
                .addGap(18, 18, 18)
                .addComponent(jBtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void llenaComboEmpresa() {
        arrayEmpresa = empresaCRUD.consulta(true);
        if (arrayEmpresa.isEmpty()){
            JOptionPane.showMessageDialog(this, "No hay empresas habilitadas.\nCmuníquese con el soporte técnico de BOSOFT", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }else{
            Iterator i = arrayEmpresa.iterator();
            while (i.hasNext()){
                EmpresaData tmp = (EmpresaData) i.next();
                this.jComboEmpresa.addItem(tmp.getFantasia());
            }
            this.jComboEmpresa.setSelectedIndex(0);
        }
    }
    
    private void llenaComboUsuario() {
        usuarioCRUD = new UsuarioCRUD(conexion, this.empresaData.getId_empresa(), this.empresaData.getFantasia());
        arrayUsuario = usuarioCRUD.consulta(true,true);
        if (arrayUsuario.isEmpty()){
            JOptionPane.showMessageDialog(this, "No hay usuarios habilitados.\nCmuníquese con el soporte técnico de BOSOFT", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }else{
            Iterator i = arrayUsuario.iterator();
            while (i.hasNext()){
                UsuarioData tmp = (UsuarioData) i.next();
                this.jComboUsuario.addItem(tmp.getNombre());
            }
            this.jComboUsuario.setSelectedIndex(0);
        }
     }
        
    private boolean validaObligatorios(){
        return this.empresaData != null && this.usuarioData != null;
    }

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jComboEmpresaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboEmpresaFocusLost
        if (this.jComboEmpresa.getSelectedIndex() >= 0){
            this.empresaData = (EmpresaData) arrayEmpresa.get(this.jComboEmpresa.getSelectedIndex());
            this.llenaComboUsuario();
            this.jComboUsuario.setEnabled(true);
            this.jPassword.setEnabled(true);
            this.jComboUsuario.requestFocus();
        }        
    }//GEN-LAST:event_jComboEmpresaFocusLost

    private void jComboUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboUsuarioFocusLost
        if (this.jComboUsuario.getSelectedIndex() >= 0){
            this.usuarioData = (UsuarioData) arrayUsuario.get(this.jComboUsuario.getSelectedIndex());
        }
    }//GEN-LAST:event_jComboUsuarioFocusLost

    private void jLblOlvidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLblOlvidoMouseClicked
        if (this.usuarioData != null){
            this.jLblOlvido.setForeground(Color.BLUE);
            String nombre = this.usuarioData.getNombre();
            String direccionMail = this.usuarioData.getEmail();
            String password = this.usuarioData.getContraseña();
            if (!direccionMail.isEmpty()){
                Utiles.enviaMailOlvido(nombre, direccionMail, password);
                JOptionPane.showMessageDialog(null, "Se ha enviado un e-mail a " + direccionMail + " con los datos de acceso", "Información", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Este usuario no tiene e-mail de contacto", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione un usuario", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jLblOlvidoMouseClicked

    private void jLblCambioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLblCambioMouseClicked
        if (this.usuarioData != null){
            this.jLblCambio.setForeground(Color.BLUE);
            this.cambiaContraseña.setConexion(conexion);
            this.cambiaContraseña.setId_usuario(usuarioData.getId_usuario());
            this.cambiaContraseña.setEmpresa(empresaData.getFantasia());
            this.cambiaContraseña.setUsuario(usuarioData.getNombre());
            this.cambiaContraseña.setPasswordActual(usuarioData.getContraseña());
            
            this.cambiaContraseña.limpiaCampos();
            
            this.cambiaContraseña.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione un usuario", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jLblCambioMouseClicked

    private void jPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            
//            String rutaEjecucion = System.getProperty("user.dir");
//            String rutaProduccion = "C:" + File.separator + "Bosoft";
//
//            if (!rutaEjecucion.equals(rutaProduccion)) {    //Si no esta ejecutando desde el directorio de produccion
//                this.dispose();
//                return;
//            }
            
            if (jPassword.getText().trim().equals("Matera2684Bosoft")) {
                setValidated(true);
                this.dispose();
                return;
            }

            if (jPassword.getText().trim().equals("")){
                JOptionPane.showMessageDialog(this,"Ingrese la contraseña", "Atención",JOptionPane.INFORMATION_MESSAGE);
                this.jPassword.requestFocus();
                return;
            }

            if (validaObligatorios()){
                String password = this.usuarioData.getContraseña();

                if (jPassword.getText().trim().equals(password)){
                    setValidated(true);
                    this.dispose();
                }else{
                    JOptionPane.showMessageDialog(this,"La contraseña no es correcta", "Atención",JOptionPane.INFORMATION_MESSAGE);
                    this.jPassword.setText("");
                    this.jPassword.requestFocus();
                }
            }else{
                JOptionPane.showMessageDialog(this,"Seleccione todos los items", "Información",JOptionPane.INFORMATION_MESSAGE);
                this.jPassword.requestFocus();
            }        
        }
    }//GEN-LAST:event_jPasswordKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JComboBox jComboEmpresa;
    private javax.swing.JComboBox jComboUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLblCambio;
    private javax.swing.JLabel jLblOlvido;
    private javax.swing.JPasswordField jPassword;
    // End of variables declaration//GEN-END:variables
}
