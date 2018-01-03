/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.vistasRapidas;

import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ProfesionalCRUD;
import ar.com.bosoft.data.ProfesionalData;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class ProfesionalVista extends javax.swing.JDialog {
    Conexion conexion;
    int id_empresa,id_profesional;
    String empresa;
    ProfesionalCRUD profesionalCRUD;
    
    /**
     * Creates new form EntidadVista
     * @param conexion
     * @param id_empresa
     * @param empresa
     * @param parent
     * @param modal
     */
    public ProfesionalVista(Conexion conexion, int id_empresa, String empresa, java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.profesionalCRUD = new ProfesionalCRUD(conexion, id_empresa, empresa);
        
        initComponents();
        setTitle("Vista rapida - Profesional");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png")).getImage());
        setLocationRelativeTo(parent);
    }

    public void setId_Entidad(int id_entidad){
        this.id_profesional = id_entidad;
    }
    
    public void trae(){
        ProfesionalData tmp = profesionalCRUD.trae(id_profesional);
        this.jLblNombre.setText(tmp.getNombre());
        this.jLblDireccion.setText(tmp.getDireccion());
        this.jLblLocalidad.setText(tmp.getLocalidad());
        this.jLblDepartamento.setText(tmp.getDepartamento());
        this.jLblProvincia.setText(tmp.getProvincia());
        this.jLblMatricula.setText(tmp.getMatricula());
        this.jLblEmail.setText(tmp.getEmail());
        this.jLblTelPart.setText(tmp.getTelParticular());
        this.jLblEspecialidad.setText(tmp.getEspecialidad());
        this.jLblPerfil.setText(tmp.getPerfil());
        this.jLblAgente.setText(tmp.getVendedor());
        this.jLblTelMovil.setText(tmp.getTelMovil());
        this.jLblSubespecialidad.setText(tmp.getSubespecialidad());
        this.jLblObservaciones.setText(tmp.getObservaciones());
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLblNombre = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLblDireccion = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLblLocalidad = new javax.swing.JLabel();
        jLblDepartamento = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLblProvincia = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLblEmail = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLblTelPart = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLblEspecialidad = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLblPerfil = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLblObservaciones = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLblAgente = new javax.swing.JLabel();
        jBtnSalir = new javax.swing.JButton();
        jLblMatricula = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLblTelMovil = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLblSubespecialidad = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLblNombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblNombre.setText("Nombre Profesional");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Dirección");

        jLblDireccion.setText("...");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Localidad");

        jLblLocalidad.setText("...");

        jLblDepartamento.setText("...");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Departamento");

        jLblProvincia.setText("...");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Provincia");

        jLblEmail.setText("...");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Email");

        jLblTelPart.setText("...");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Tel. Part.");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Especialidad");

        jLblEspecialidad.setText("...");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Perfil");

        jLblPerfil.setText("...");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("Observaciones");

        jLblObservaciones.setText("...");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("Agente");

        jLblAgente.setText("...");

        jBtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/salir.png"))); // NOI18N
        jBtnSalir.setBorderPainted(false);
        jBtnSalir.setContentAreaFilled(false);
        jBtnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnSalir.setFocusPainted(false);
        jBtnSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/salir.png"))); // NOI18N
        jBtnSalir.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/salir.png"))); // NOI18N
        jBtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSalirActionPerformed(evt);
            }
        });

        jLblMatricula.setText("...");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Matrícula");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Tel. Móvil");

        jLblTelMovil.setText("...");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("Subespec.");

        jLblSubespecialidad.setText("...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel15)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLblTelMovil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLblLocalidad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLblMatricula, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLblDepartamento, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(jLblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLblEspecialidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLblProvincia, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(jLblTelPart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLblSubespecialidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLblDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLblAgente, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel18)
                            .addComponent(jLabel20))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jBtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblNombre)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLblDireccion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLblLocalidad)
                        .addComponent(jLabel7)
                        .addComponent(jLblDepartamento)
                        .addComponent(jLblProvincia)))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblMatricula)
                    .addComponent(jLabel5)
                    .addComponent(jLblEmail)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13)
                    .addComponent(jLblTelPart))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblEspecialidad)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLblTelMovil)
                    .addComponent(jLblSubespecialidad)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLblPerfil)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLblObservaciones)
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLblAgente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jBtnSalir))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblAgente;
    private javax.swing.JLabel jLblDepartamento;
    private javax.swing.JLabel jLblDireccion;
    private javax.swing.JLabel jLblEmail;
    private javax.swing.JLabel jLblEspecialidad;
    private javax.swing.JLabel jLblLocalidad;
    private javax.swing.JLabel jLblMatricula;
    private javax.swing.JLabel jLblNombre;
    private javax.swing.JLabel jLblObservaciones;
    private javax.swing.JLabel jLblPerfil;
    private javax.swing.JLabel jLblProvincia;
    private javax.swing.JLabel jLblSubespecialidad;
    private javax.swing.JLabel jLblTelMovil;
    private javax.swing.JLabel jLblTelPart;
    // End of variables declaration//GEN-END:variables
}
