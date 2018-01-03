/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.buscadores.BuscaPresupuesto;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.formatosCampos.ConMascara;
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
public class AnulaCircuito extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, id_presupuesto;
    String empresa, usuario;
    
    ResultSet rsConsulta;
    DefaultTableModel modeloProductos, modeloCajas;
    
    ConMascara conMascara = new ConMascara();
    BuscaPresupuesto buscaPresupuesto;
    /**
     * Creates new form AnulaCircuito
     * @param conexion
     * @param id_empresa
     * @param empresa
     * @param usuario
     */
    public AnulaCircuito(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        
        initComponents();
        
        this.modeloProductos = (DefaultTableModel) this.jTablaProducto.getModel();
        this.jTablaProducto.setModel(modeloProductos);
        this.modeloCajas = (DefaultTableModel) this.jTablaCaja.getModel();
        this.jTablaCaja.setModel(modeloCajas);
        
        this.limpia();
    }

    private void limpia(){
        this.jFmtId_presupuesto.setText("");
        this.jLblZona.setText("");
        this.jLblEntidad.setText("");
        this.jLblLugarCx.setText("");
        this.jLblProfesional.setText("");
        this.jLblTecnico.setText("");
        this.jLblPrestacion.setText("");
        this.jLblTecnico.setText("");
        this.jLblPrestacion.setText("");
        this.jLblPaciente.setText("");
        this.jLblTipoOperacion.setText("");
        this.jLblVendedor.setText("");
        this.jLblEspecialidad.setText("");
        this.jTxtObservaciones.setText("");
        
        modeloProductos.getDataVector().removeAllElements();
        modeloProductos.fireTableDataChanged();

        modeloCajas.getDataVector().removeAllElements();
        modeloCajas.fireTableDataChanged();
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
//            }else{
//                JOptionPane.showMessageDialog(this, "No se han recuperado las cajas asignadas del turno", "Atención", JOptionPane.INFORMATION_MESSAGE);
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
        jFmtId_presupuesto = conMascara.getjFmt("######",' ',true);
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablaProducto = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTablaCaja = new javax.swing.JTable();
        jBtnSalir = new javax.swing.JButton();
        jBtnBusca = new javax.swing.JButton();
        jBtnAnular = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLblZona = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLblEntidad = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLblLugarCx = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLblProfesional = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLblTecnico = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLblPrestacion = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLblPaciente = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLblTipoOperacion = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLblVendedor = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLblEspecialidad = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();

        setTitle("Anulación de circuito de presupuesto");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jLabel1.setText("Presupuesto");

        jFmtId_presupuesto.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        jFmtId_presupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFmtId_presupuestoActionPerformed(evt);
            }
        });
        jFmtId_presupuesto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFmtId_presupuestoFocusLost(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

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

        jBtnBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/24x24/enabled/buscar.png"))); // NOI18N
        jBtnBusca.setBorderPainted(false);
        jBtnBusca.setContentAreaFilled(false);
        jBtnBusca.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnBusca.setFocusPainted(false);
        jBtnBusca.setFocusable(false);
        jBtnBusca.setRequestFocusEnabled(false);
        jBtnBusca.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/24x24/rollover/buscar.png"))); // NOI18N
        jBtnBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscaActionPerformed(evt);
            }
        });

        jBtnAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/anular.png"))); // NOI18N
        jBtnAnular.setText("Anular circuito");
        jBtnAnular.setToolTipText("");
        jBtnAnular.setBorderPainted(false);
        jBtnAnular.setContentAreaFilled(false);
        jBtnAnular.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnAnular.setFocusPainted(false);
        jBtnAnular.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnAnular.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/anular.png"))); // NOI18N
        jBtnAnular.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAnularActionPerformed(evt);
            }
        });

        jLabel2.setText("Zona");

        jLblZona.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblZona.setText("jLabel2");

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

        jLblTecnico.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblTecnico.setText("jLabel2");

        jLabel12.setText("Prestación");

        jLblPrestacion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblPrestacion.setText("jLabel2");

        jLabel10.setText("Paciente");

        jLblPaciente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblPaciente.setText("jLabel2");

        jLabel13.setText("Tipo Op.");

        jLblTipoOperacion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblTipoOperacion.setText("jLabel2");

        jLabel14.setText("Vendedor");

        jLblVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblVendedor.setText("jLabel2");

        jLabel15.setText("Especialidad");

        jLblEspecialidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblEspecialidad.setText("jLabel2");

        jTxtObservaciones.setEditable(false);
        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jTxtObservaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Observaciones"));
        jScrollPane5.setViewportView(jTxtObservaciones);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13)
                            .addComponent(jLabel10)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jFmtId_presupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLblZona, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblLugarCx, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblPrestacion, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblTipoOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(325, 325, 325)
                            .addComponent(jBtnAnular)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                            .addComponent(jBtnSalir))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane5))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(1, 1, 1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jFmtId_presupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jBtnBusca))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLblZona))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLblEntidad))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLblLugarCx))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblProfesional))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblTecnico))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblPrestacion))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLblPaciente))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLblTipoOperacion))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLblVendedor))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLblEspecialidad))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnAnular, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnSalir, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(1, 1, 1))
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

    private void jFmtId_presupuestoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFmtId_presupuestoFocusLost
        if (!this.jFmtId_presupuesto.getText().trim().isEmpty()) {
            this.id_presupuesto = Integer.parseInt(this.jFmtId_presupuesto.getText().trim());
            consulta();
        }
    }//GEN-LAST:event_jFmtId_presupuestoFocusLost

    private void jBtnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscaActionPerformed
        this.buscaPresupuesto = new BuscaPresupuesto(conexion,null,true,this.id_empresa,this.empresa, 6);
        this.buscaPresupuesto.setVisible(true);
        this.jFmtId_presupuesto.setText(buscaPresupuesto.getId_presupuesto().isEmpty() ? "" : buscaPresupuesto.getId_presupuesto());
        if (!buscaPresupuesto.getId_presupuesto().isEmpty()) {
            consulta();
        }        
    }//GEN-LAST:event_jBtnBuscaActionPerformed

    private void jBtnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAnularActionPerformed
        if (this.id_presupuesto > 0) {
            int respuesta = JOptionPane.showConfirmDialog(this, "Confirma la anulación del circuito de este presupuesto?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                ArrayList parametros = new ArrayList();
                parametros.add(this.id_presupuesto);
                parametros.add(this.usuario);
                
                conexion.procAlmacenado("anulaCircuito", parametros,
                    empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            }
        }else{
            JOptionPane.showMessageDialog(this, "Ingrese el presupuesto que desea consultar", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
            
    }//GEN-LAST:event_jBtnAnularActionPerformed

    private void jFmtId_presupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFmtId_presupuestoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFmtId_presupuestoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAnular;
    private javax.swing.JButton jBtnBusca;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JFormattedTextField jFmtId_presupuesto;
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
    private javax.swing.JLabel jLblLugarCx;
    private javax.swing.JLabel jLblPaciente;
    private javax.swing.JLabel jLblPrestacion;
    private javax.swing.JLabel jLblProfesional;
    private javax.swing.JLabel jLblTecnico;
    private javax.swing.JLabel jLblTipoOperacion;
    private javax.swing.JLabel jLblVendedor;
    private javax.swing.JLabel jLblZona;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTablaCaja;
    private javax.swing.JTable jTablaProducto;
    private javax.swing.JTextArea jTxtObservaciones;
    // End of variables declaration//GEN-END:variables
}
