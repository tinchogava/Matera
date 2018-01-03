/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.vistasRapidas;

import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.MotivoCRUD;
import ar.com.bosoft.data.MotivoData;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class Motivo extends javax.swing.JDialog {
    int id_motivo;
    String motivo;
    boolean siNo;
    
    MotivoCRUD motivoCRUD;
    ArrayList motivoArray;
    

    /**
     * Creates new form Motivo
     * @param conexion
     * @param empresa
     * @param parent
     * @param modal
     */
    public Motivo(Conexion conexion, String empresa, java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        motivoCRUD = new MotivoCRUD(conexion, empresa);
        motivoArray = motivoCRUD.consulta();
        initComponents();
        llenaComboMotivo();
        limpia();
        setLocationRelativeTo(null);
    }

    private void llenaComboMotivo(){
        Iterator it = motivoArray.iterator();
        while (it.hasNext()){
            MotivoData tmp = (MotivoData) it.next();
            this.jComboMotivo.addItem(tmp.getNombre());
        }
    }
    
    private void limpia(){
        this.jComboMotivo.setSelectedIndex(-1);
        this.jTxtMotivo.setText("");
        this.siNo = false;
    }

    public int getId_motivo() {
        return id_motivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public boolean isSiNo() {
        return siNo;
    }

    private boolean validaObligatorios(){
        return this.jComboMotivo.getSelectedIndex() >= 0;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboMotivo = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTxtMotivo = new javax.swing.JTextArea();
        jBtnAceptar = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTxtMotivo.setColumns(20);
        jTxtMotivo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTxtMotivo.setRows(5);
        jTxtMotivo.setBorder(javax.swing.BorderFactory.createTitledBorder("Motivo"));
        jScrollPane2.setViewportView(jTxtMotivo);

        jBtnAceptar.setText("Aceptar");
        jBtnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAceptarActionPerformed(evt);
            }
        });

        jBtnCancelar.setText("Cancelar");
        jBtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jBtnAceptar)
                        .addGap(40, 40, 40)
                        .addComponent(jBtnCancelar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBtnAceptar, jBtnCancelar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAceptarActionPerformed
        if (validaObligatorios()) {
            MotivoData m = (MotivoData) motivoArray.get(this.jComboMotivo.getSelectedIndex());
            this.id_motivo = m.getId_motivo();
            this.motivo = this.jTxtMotivo.getText().trim();
            this.siNo = true;
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione el motivo", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnAceptarActionPerformed

    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        this.siNo = false;
        this.dispose();
    }//GEN-LAST:event_jBtnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAceptar;
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JComboBox jComboMotivo;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTxtMotivo;
    // End of variables declaration//GEN-END:variables
}
