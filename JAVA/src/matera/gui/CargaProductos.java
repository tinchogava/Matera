/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.gui;

import ar.com.matera.TableModels.CargaProductoTableModel;
import java.util.List;
import javax.swing.JTable;
import matera.TableObjects.CargaProductoTableObject;

/**
 *
 * @author h2o
 */
public class CargaProductos extends javax.swing.JDialog {

    private CargaProductoTableModel modeloProducto;
    Boolean loaded; // Presiono el boton Cargar?
    /**
     * Creates new form CargaProductos
     */
    public CargaProductos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        modeloProducto = new CargaProductoTableModel();
        jTableProducto.setModel(modeloProducto);
        loaded = false;
    }
    
    public JTable getTableProductos(){
        return jTableProducto;
    }
    
    public List<CargaProductoTableObject> getProductos(){
        return modeloProducto.getRowsAsList();
    }
    
    public void loadSeleccion(List<CargaProductoTableObject> list){
        list.forEach(p -> modeloProducto.addRow(p));
    }
    
    public Boolean isLoaded(){
        return loaded;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProducto = new javax.swing.JTable();
        jButtonCargar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        jScrollPane1.setViewportView(jTableProducto);
        if (jTableProducto.getColumnModel().getColumnCount() > 0) {
            jTableProducto.getColumnModel().getColumn(0).setMinWidth(0);
            jTableProducto.getColumnModel().getColumn(0).setPreferredWidth(0);
            jTableProducto.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jButtonCargar.setText("Cargar");
        jButtonCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCargarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonCargar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCargar))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCargarActionPerformed
        if(jTableProducto.isEditing())
            jTableProducto.getCellEditor().stopCellEditing();
        loaded = true;
        this.dispose();
    }//GEN-LAST:event_jButtonCargarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCargar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableProducto;
    // End of variables declaration//GEN-END:variables
}
