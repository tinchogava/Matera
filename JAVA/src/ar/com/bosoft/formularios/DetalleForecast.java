/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.DetalleForecastDataSource;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.clases.Utiles;
import ar.com.dialogos.AvisoEspera;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import static matera.jooq.Tables.PRESUPUESTOPROD;
import static matera.jooq.Tables.PROFESIONAL;
import static matera.jooq.tables.Entidad.ENTIDAD;
import static matera.jooq.tables.Presupuesto.PRESUPUESTO;
import matera.jooq.tables.records.EntidadRecord;
import matera.jooq.tables.records.PresupuestoRecord;
import matera.jooq.tables.records.PresupuestoprodRecord;
import matera.jooq.tables.records.ProfesionalRecord;
import org.jooq.Record;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class DetalleForecast extends javax.swing.JDialog {

    String empresa, vendedor, producto, entidad;
    int year, mes, tipo;
    boolean vip;
    List<Record> records;
    
    AvisoEspera avisoEspera;
    /**
     * Creates new form DetalleForecast
     * @param parent
     * @param modal
     * @param records
     */
    public DetalleForecast(java.awt.Frame parent, boolean modal, List<Record> records) {
        super(parent, modal);
        initComponents();
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png")).getImage());
        setLocationRelativeTo(parent);
        this.records = records;
    }
    
    public void carga(String empresa, String entidad, String productoFact, String vendedor, Integer year, Integer mes, Integer aprobadosCount, Integer vipCount){
        try {
            this.jLblEntidad.setText(entidad);
            this.jLblProducto.setText(productoFact);
            this.empresa = empresa;
            this.entidad = entidad;
            this.producto = productoFact;
            this.vendedor = vendedor;
            this.year = year;
            this.mes = mes;
            
            this.jBtnAprobados.setText(aprobadosCount.toString());
            this.jBtnVip.setText(vipCount.toString());
        } catch (Exception ex) {
            Utiles.showErrorMessage(ex);
        }
    }

    private void imprime(Boolean vip){
        try {
                DetalleForecastDataSource data = new DetalleForecastDataSource();
                for(Record r : records){
                    PresupuestoRecord p = r.into(PRESUPUESTO);
                    PresupuestoprodRecord pProd = r.into(PRESUPUESTOPROD);
                    ProfesionalRecord profesional = r.into(PROFESIONAL);
                    EntidadRecord lugarCirugia = r.into(ENTIDAD);
                    
                    String medico = profesional.getNombre();
                    if (medico == null) medico = "SIN ASIGNAR";
                    
                    String entidadCirugia = lugarCirugia.getNombre();
                    if (entidadCirugia == null) entidadCirugia = "SIN ASIGNAR";
                    
                    ar.com.bosoft.Modelos.DetalleForecast nuevo = new ar.com.bosoft.Modelos.DetalleForecast(
                            p.getIdPresupuesto(),
                            p.getFechaaprobacion(),
                            p.getPaciente(),
                            p.getDireccion(),
                            p.getTelefono(),
                            pProd.getObservaciones(), 
                            p.getObservaciones(),
                            p.getTotal(),
                            pProd.getCantidad(),
                            medico,
                            entidadCirugia
                            );
                    data.add(nuevo);                
                }
                Map param=new HashMap();
                param.put("empresa", empresa);
                param.put("productoFact", producto);
                param.put("entidad", entidad);
                param.put("vendedor", vendedor);

                if (vip)
                    param.put("tipo", "VIP");
                else
                    param.put("tipo", "Aprobados");

                Reporte reporte = new Reporte();
                String nombreReporte = "DetalleForecast";
                reporte.startReport(nombreReporte, param, data, 0, "", "", 0);                

            /*
            ArrayList parametros = new ArrayList();
            parametros.add(this.id_vendedor);
            parametros.add(this.id_entidad);
            parametros.add(this.id_productoFact);
            parametros.add(this.anio);
            parametros.add(this.mes);
            parametros.add(this.tipo);
            parametros.add(this.vip);

            rsConsulta = conexion.procAlmacenado("detalleForecast", parametros,  
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());

            DetalleForecastDataSource data = new DetalleForecastDataSource();
            rsConsulta.beforeFirst();
            while (rsConsulta.next()) {
                int id_presupuesto = rsConsulta.getInt("id_presupuesto");
                Date fecha = rsConsulta.getDate("fecha");
                String paciente = rsConsulta.getString("paciente");
                String direccion = rsConsulta.getString("direccion");
                String telefono = rsConsulta.getString("telefono");
                BigDecimal total = rsConsulta.getBigDecimal("total");
                String observaciones = rsConsulta.getString("observaciones");
                String obsProducto = rsConsulta.getString("obsProducto");
                int cantidad = rsConsulta.getInt("cantidad");
                
                ar.com.bosoft.Modelos.DetalleForecast nuevo = new ar.com.bosoft.Modelos.DetalleForecast(id_presupuesto, fecha, paciente, direccion, telefono, obsProducto, observaciones, total, cantidad);
                data.add(nuevo);
            }

            Map param=new HashMap();
            param.put("empresa", this.empresa);
            param.put("productoFact", this.jLblProducto.getText());
            param.put("entidad", this.jLblEntidad.getText());
            param.put("vendedor", this.vendedor);
            
            switch (this.tipo){
                case 0: 
                    param.put("tipo", "Aprobados");
                    break;
                case 1: 
                    param.put("tipo", "Facturados");
                    break;
                case 2: 
                    param.put("tipo", "Pendientes de facturación");
                    break;
                case 3: 
                    param.put("tipo", "VIP");
                    break;                 
            }
            
            Reporte reporte = new Reporte();
            String nombreReporte = "DetalleForecast";
            reporte.startReport(nombreReporte, param, data, 0, "", "", 0);
            */
        } catch (Exception ex) {
            Utiles.showErrorMessage(ex);
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

        jLblEntidad = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jBtnAprobados = new javax.swing.JButton();
        jBtnVip = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jBtnSalir = new javax.swing.JButton();
        jLblProducto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLblEntidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLblEntidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblEntidad.setText("entidad");

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Aprobados");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("VIP");

        jBtnAprobados.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jBtnAprobados.setText("0");
        jBtnAprobados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAprobadosActionPerformed(evt);
            }
        });

        jBtnVip.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jBtnVip.setText("0");
        jBtnVip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnVipActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnAprobados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnVip, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnVip, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnAprobados, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jLblProducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblProducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblProducto.setText("producto");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLblEntidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLblProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(287, 287, 287)
                        .addComponent(jBtnSalir)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblEntidad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLblProducto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnSalir)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
      /*
        if (rsConsulta != null) {
            try {
                rsConsulta.close();
            } catch (Exception e) {
            }
        }
         */
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnAprobadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAprobadosActionPerformed
        if (!this.jBtnAprobados.getText().trim().equals("0")) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    DetalleForecast.this.avisoEspera = new AvisoEspera(null, false, AvisoEspera.IMPRIMIENDO);
                    DetalleForecast.this.avisoEspera.setVisible(true);
                    Thread performer = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DetalleForecast.this.imprime(false);                            
                            DetalleForecast.this.avisoEspera.setVisible(false);
                        }
                    }, "Performer");
                    performer.start();
                }
            });
        }else{
            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }            
    }//GEN-LAST:event_jBtnAprobadosActionPerformed

    private void jBtnVipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnVipActionPerformed
        if (!this.jBtnVip.getText().trim().equals("0")) {
            this.tipo = 3;
            this.vip = true;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    DetalleForecast.this.avisoEspera = new AvisoEspera(null, false, AvisoEspera.IMPRIMIENDO);
                    DetalleForecast.this.avisoEspera.setVisible(true);
                    Thread performer = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DetalleForecast.this.imprime(true);                            
                            DetalleForecast.this.avisoEspera.setVisible(false);
                        }
                    }, "Performer");
                    performer.start();
                }
            });
        }else{
            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }    
    }//GEN-LAST:event_jBtnVipActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAprobados;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnVip;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLblEntidad;
    private javax.swing.JLabel jLblProducto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
