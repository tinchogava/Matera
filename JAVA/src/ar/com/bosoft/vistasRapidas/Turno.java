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
import java.awt.HeadlessException;
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
public class Turno extends javax.swing.JDialog {
    Conexion conexion;
    int id_empresa,id_presupuesto;
    String empresa;
    ResultSet rsConsulta;
    DefaultTableModel modeloProductos, modeloCajas;
    
    /**
     * Creates new form Turno
     * @param parent
     * @param modal
     * @param conexion
     * @param id_empresa
     * @param empresa
     */
    public Turno(java.awt.Frame parent, boolean modal, Conexion conexion, int id_empresa, String empresa) {
        super(parent, modal);
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        
        initComponents();
        setTitle("Turno");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png")).getImage());
        setLocationRelativeTo(parent);
        
        this.modeloProductos = (DefaultTableModel) this.jTablaProducto.getModel();
        this.jTablaProducto.setModel(modeloProductos);
        this.modeloCajas = (DefaultTableModel) this.jTablaCaja.getModel();
        this.jTablaCaja.setModel(modeloCajas);
    }

    public void setId_presupuesto(String id_presupuesto){
        this.id_presupuesto = Integer.parseInt(id_presupuesto);
        this.jLblId_presupuesto.setText(String.valueOf(this.id_presupuesto));
        consulta();
    }
    
    private void consulta(){
        try {
            modeloProductos.getDataVector().removeAllElements();
            modeloProductos.fireTableDataChanged();
            
            modeloCajas.getDataVector().removeAllElements();
            modeloCajas.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            parametros.add(this.id_presupuesto);

            rsConsulta = conexion.procAlmacenado("traeTurno", parametros,
                empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            if (rsConsulta.first()) {
                this.jLblZona.setText(rsConsulta.getString("zona"));
                this.jLblEntidad.setText(rsConsulta.getString("entidad"));
                this.jLblLugarCx.setText(rsConsulta.getString("lugarCirugia"));
                this.jLblProfesional.setText(rsConsulta.getString("profesional"));
                this.jLblTecnico.setText(rsConsulta.getString("tecnico"));
                this.jLblPrestacion.setText(rsConsulta.getString("prestacion"));
                this.jLblPaciente.setText(rsConsulta.getString("paciente"));
                this.jLblVendedor.setText(rsConsulta.getString("vendedor"));
                this.jLblTipoOperacion.setText(rsConsulta.getString("tipoOperacion"));
                this.jLblEspecialidad.setText(rsConsulta.getString("especialidad"));
                this.jTxtObservaciones.setText(rsConsulta.getString("observaciones"));
            }else{
                JOptionPane.showMessageDialog(this, "No se han recuperado los datos del turno", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
            
            rsConsulta = conexion.procAlmacenado("traePresupuestoProd", parametros,
                empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

            if (rsConsulta.first()) {
                int cantidad = rsConsulta.getInt("cantidad");
                String codigo = rsConsulta.getString("codigo");
                String producto = rsConsulta.getString("producto");
                String proveedor = rsConsulta.getString("proveedor");

                Object[] fila = {cantidad, codigo, producto, proveedor};

                modeloProductos.addRow(fila);
            }else{
                JOptionPane.showMessageDialog(this, "No se han recuperado los productos del turno", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
            
            //consultaAsignadas
            rsConsulta = conexion.procAlmacenado("consultaAsignadas", parametros,
                empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

            if (rsConsulta.first()) {
                String codigo = rsConsulta.getString("codigo");
                String nombre = rsConsulta.getString("nombre");

                Object[] fila = {codigo, nombre};

                modeloCajas.addRow(fila);
            }else{
                JOptionPane.showMessageDialog(this, "No se han recuperado las cajas asignadas del turno", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException | HeadlessException ex) {
            Utiles.enviaError(this.empresa, ex);
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
        jScrollPane5 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();
        jBtnSalir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLblZona = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablaProducto = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTablaCaja = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLblEntidad = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLblLugarCx = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLblProfesional = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLblEspecialidad = new javax.swing.JLabel();
        jLblVendedor = new javax.swing.JLabel();
        jLblTipoOperacion = new javax.swing.JLabel();
        jLblPaciente = new javax.swing.JLabel();
        jLblPrestacion = new javax.swing.JLabel();
        jLblTecnico = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Turno");

        jLblId_presupuesto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblId_presupuesto.setText("jLabel2");

        jTxtObservaciones.setEditable(false);
        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jTxtObservaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Observaciones"));
        jScrollPane5.setViewportView(jTxtObservaciones);

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

        jLabel2.setText("Zona");

        jLblZona.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblZona.setText("jLabel2");

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Productos"));

        jTablaProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cantidad", "Código", "Producto", "Proveedor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTablaProducto);
        if (jTablaProducto.getColumnModel().getColumnCount() > 0) {
            jTablaProducto.getColumnModel().getColumn(0).setMinWidth(75);
            jTablaProducto.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTablaProducto.getColumnModel().getColumn(0).setMaxWidth(75);
            jTablaProducto.getColumnModel().getColumn(1).setMinWidth(100);
            jTablaProducto.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTablaProducto.getColumnModel().getColumn(1).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cajas asignadas"));

        jTablaCaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
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
        jScrollPane6.setViewportView(jTablaCaja);
        if (jTablaCaja.getColumnModel().getColumnCount() > 0) {
            jTablaCaja.getColumnModel().getColumn(0).setMinWidth(150);
            jTablaCaja.getColumnModel().getColumn(0).setPreferredWidth(150);
            jTablaCaja.getColumnModel().getColumn(0).setMaxWidth(150);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        jScrollPane1.setBorder(null);

        jLabel5.setText("Entidad");

        jLblEntidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblEntidad.setText("jLabel2");

        jLabel6.setText("Lugar Cx.");

        jLblLugarCx.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblLugarCx.setText("jLabel2");

        jLabel9.setText("Profesional");

        jLblProfesional.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblProfesional.setText("jLabel2");

        jLabel11.setText("Técnico");

        jLabel12.setText("Prestación");

        jLabel10.setText("Paciente");

        jLabel13.setText("Tipo Op.");

        jLabel14.setText("Vendedor");

        jLabel15.setText("Especialidad");

        jLblEspecialidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblEspecialidad.setText("jLabel2");

        jLblVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblVendedor.setText("jLabel2");

        jLblTipoOperacion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblTipoOperacion.setText("jLabel2");

        jLblPaciente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblPaciente.setText("jLabel2");

        jLblPrestacion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblPrestacion.setText("jLabel2");

        jLblTecnico.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblTecnico.setText("jLabel2");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel6)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLblLugarCx, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblPrestacion, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblTipoOperacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblEspecialidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblEntidad)
                    .addComponent(jLabel5))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblLugarCx)
                    .addComponent(jLabel6))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblProfesional)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblTecnico))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblPrestacion))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblPaciente)
                    .addComponent(jLabel10))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLblTipoOperacion))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLblVendedor))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLblEspecialidad))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLblId_presupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLblZona, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jBtnSalir))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblId_presupuesto)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLblZona))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblEntidad;
    private javax.swing.JLabel jLblEspecialidad;
    private javax.swing.JLabel jLblId_presupuesto;
    private javax.swing.JLabel jLblLugarCx;
    private javax.swing.JLabel jLblPaciente;
    private javax.swing.JLabel jLblPrestacion;
    private javax.swing.JLabel jLblProfesional;
    private javax.swing.JLabel jLblTecnico;
    private javax.swing.JLabel jLblTipoOperacion;
    private javax.swing.JLabel jLblVendedor;
    private javax.swing.JLabel jLblZona;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTablaCaja;
    private javax.swing.JTable jTablaProducto;
    private javax.swing.JTextArea jTxtObservaciones;
    // End of variables declaration//GEN-END:variables
}
