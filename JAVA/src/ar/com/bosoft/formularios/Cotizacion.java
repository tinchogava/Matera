package ar.com.bosoft.formularios;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.ActiveDatabase;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.CotizacionCRUD;
import ar.com.bosoft.crud.MonedaCRUD;
import ar.com.bosoft.data.CotizacionData;
import ar.com.bosoft.data.MonedaData;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import matera.cache.CacheCotizaciones;
import static matera.jooq.Tables.REMITO;
import static matera.jooq.Tables.STOCKDETALLE;
import org.apache.commons.lang.math.NumberUtils;
import org.jooq.DSLContext;

public class Cotizacion extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa;
    String empresa, usuario;
    MonedaCRUD monedaCRUD;
    CotizacionCRUD cotizacionCRUD;
    ArrayList monedaArray, cotizacionArray;
    DefaultTableModel modelo;
    TableRowSorter sorter;
    
    /**
     * Creates new form Cotizacion
     * @param conexion
     * @param empresa
     * @param id_empresa
     * @param usuario
     */
    public Cotizacion(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        this.monedaCRUD = new MonedaCRUD(conexion, empresa);
        this.cotizacionCRUD = new CotizacionCRUD(conexion, id_empresa, empresa, usuario);
        
        initComponents();
        modelo = (DefaultTableModel) this.jTabla.getModel();
        this.jTabla.setModel(modelo);
        jTabla.setRowSorter (new TableRowSorter(modelo));
        sorter = new TableRowSorter(modelo);
        
        llenaComboMoneda();
        consulta();
        limpia();  
        setJTexFieldChanged(jTxtBusca);
    }
    
    private void llenaComboMoneda(){
        this.monedaArray = monedaCRUD.consulta(true);
        Iterator it = monedaArray.iterator();
        while (it.hasNext()){
            MonedaData tmp = (MonedaData) it.next();
            if (tmp.getId_moneda() == 1) {
                continue;
            }
            this.jComboMoneda.addItem(tmp.getNombre());
        }
    }

    private void consulta(){
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
        
        cotizacionArray = cotizacionCRUD.consulta();
        Iterator it = cotizacionArray.iterator();
        while (it.hasNext()){
            CotizacionData tmp = (CotizacionData) it.next();
            String moneda = tmp.getMoneda();
            int mes = tmp.getMes();
            int año = tmp.getAño();
            String mesAño = String.format("%02d", mes) + "/" + String.valueOf(año);
            Double valor = tmp.getValor();
            
            Object[] fila = {moneda, mesAño, valor};
            
            modelo.addRow(fila);
        }
    }
    
    private void limpia(){
        this.jComboMoneda.setSelectedIndex(-1);
        this.jMes.setMonth((Calendar.getInstance().get(Calendar.MONTH)) - 1);
        this.jFmtCotizacion.setValue(0.000);
        this.jTxtBusca.setText("");
        //this.jCheckActualizaRemitos.setSelected(false);
    }
    
    private void setJTexFieldChanged(JTextField txt){
        DocumentListener documentListener = new DocumentListener() {
 
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                printIt(documentEvent);
            }
 
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                printIt(documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                printIt(documentEvent);
            }
        };
        txt.getDocument().addDocumentListener(documentListener); 
    }
 
    private void printIt(DocumentEvent documentEvent) {
        DocumentEvent.EventType type = documentEvent.getType();
 
        if (type.equals(DocumentEvent.EventType.CHANGE)){
 
        }else if (type.equals(DocumentEvent.EventType.INSERT)){
            JTxtBuscaChanged();
        }else if (type.equals(DocumentEvent.EventType.REMOVE)){
            JTxtBuscaChanged();
        }
    }
 
    private void JTxtBuscaChanged(){
        String text = jTxtBusca.getText().trim().replaceAll("'", "´");
        if (text.length() == 0) {
          sorter.setRowFilter(null);
        } else {
          sorter.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTabla.setRowSorter(sorter);
        }
    }

    private boolean validaObligatorios(){
        return this.jComboMoneda.getSelectedIndex() >= 0 &&
                Double.parseDouble(this.jFmtCotizacion.getValue().toString()) > 0;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenuMoneda = new javax.swing.JPopupMenu();
        jMenuItemActualizaCostos = new javax.swing.JMenuItem();
        jLabel4 = new javax.swing.JLabel();
        jComboMoneda = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        jFmtCotizacion = new ar.com.bosoft.formatosCampos.Decimal3(true);
        jMes = new com.toedter.calendar.JMonthChooser();
        jLabel25 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla = new javax.swing.JTable();
        jBtnGuardar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jYear = new com.toedter.calendar.JYearChooser();
        jLabel1 = new javax.swing.JLabel();

        jMenuItemActualizaCostos.setText("Actualizar Costo de Ventas");
        jMenuItemActualizaCostos.setToolTipText("");
        jMenuItemActualizaCostos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActualizaCostosActionPerformed(evt);
            }
        });
        jPopupMenuMoneda.add(jMenuItemActualizaCostos);

        setTitle("Cotización");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jLabel4.setText("* Moneda");

        jLabel24.setText("* Cotización");

        jLabel25.setText("* Mes");

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setFocusable(false);

        jLabel22.setText("Buscar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)))
        );

        jTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Moneda", "Mes", "Cotización"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabla.setComponentPopupMenu(jPopupMenuMoneda);
        jScrollPane1.setViewportView(jTabla);
        if (jTabla.getColumnModel().getColumnCount() > 0) {
            jTabla.getColumnModel().getColumn(1).setMinWidth(120);
            jTabla.getColumnModel().getColumn(1).setPreferredWidth(120);
            jTabla.getColumnModel().getColumn(1).setMaxWidth(120);
            jTabla.getColumnModel().getColumn(2).setMinWidth(100);
            jTabla.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTabla.getColumnModel().getColumn(2).setMaxWidth(100);
        }

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

        jLabel1.setText("Haga Click derecho en una cotización para actualizar los costos de ventas del mes");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel24)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel25))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jFmtCotizacion, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jMes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(26, 26, 26))
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1))
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBtnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnSalir)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel25)
                    .addComponent(jMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFmtCotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(7, 7, 7)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnSalir))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios()){
            MonedaData m = (MonedaData) monedaArray.get(this.jComboMoneda.getSelectedIndex());
            int id_moneda = m.getId_moneda();
            int mes = this.jMes.getMonth() + 1;
            int año = this.jYear.getYear();
            Double valor = Double.parseDouble(this.jFmtCotizacion.getValue().toString());
            String moneda = this.jComboMoneda.getSelectedItem().toString();
            
            CotizacionData nuevo = new CotizacionData(id_moneda, mes, año, valor, moneda);
            cotizacionCRUD.insert(nuevo);

            /*
            if (this.jCheckActualizaRemitos.isSelected()) {
                ArrayList parametros = new ArrayList();
                parametros.add(mes);
                parametros.add(año);
                parametros.add(id_moneda);
                parametros.add(valor);
                parametros.add(id_empresa);
                parametros.add(Principal.usuarioData.getNombre());
                parametros.add(Principal.equipo);
                
                conexion.procAlmacenado("actualizaCotizacionStockDetalle", parametros, 
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            }
            */
            CacheCotizaciones.instance().fetch();
            this.limpia();
            this.consulta();
            this.jTxtBusca.requestFocus();
        }else{
            JOptionPane.showMessageDialog(this,"Complete todos los datos obligatorios (*)", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jMenuItemActualizaCostosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemActualizaCostosActionPerformed
        try{
            Integer row = jTabla.getSelectedRow();
            if (row > -1){
                row = jTabla.convertRowIndexToModel(row);
                // Obtengo la cotizacion
                BigDecimal cotizacion = NumberUtils.createBigDecimal(Utiles.valueAt(modelo, row, "cotizacion").toString());
                int opcion = JOptionPane.showConfirmDialog(this,
                        "Está seguro de querer actualizar los costos de ventas del mes "+ Utiles.valueAt(modelo, row, "mes") 
                                + " con la cotización " 
                                + cotizacion, "Confirmación"
                        , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (opcion == JOptionPane.YES_OPTION){
                    // Parseo la columna mes/year
                    String []d = Utiles.valueAt(modelo, row, "mes").toString().split("/");
                    String mes = d[0];
                    String year = d[1];

                    Calendar c = Calendar.getInstance();
                    c.clear();
                    c.set(Integer.parseInt(year),Integer.parseInt(mes)-1,1);
                    java.sql.Date from = new java.sql.Date(c.getTime().getTime());
                    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));            
                    java.sql.Date to = new java.sql.Date(c.getTime().getTime());

                    // Busco los ids de los remitos consumidos entre el primer y ultimo dia del mes
                    DSLContext dsl = ActiveDatabase.getDSLContext();
                    List<Integer> idRemitos = dsl.select(REMITO.ID_REMITO).from(REMITO).where(REMITO.FECHACONSUMIDO.greaterOrEqual(from)).and(REMITO.FECHACONSUMIDO.lessOrEqual(to)).fetch(REMITO.ID_REMITO);

                    // Actualizo el costo en pesos y la cotizacion de los detalles de los remitos
                    dsl.update(STOCKDETALLE).set(STOCKDETALLE.COSTOPESOS,STOCKDETALLE.COSTOPESOS.divide(STOCKDETALLE.COTIZACION).multiply(cotizacion)).set(STOCKDETALLE.COTIZACION,cotizacion).where(STOCKDETALLE.ID_REMITO.in(idRemitos)).and(STOCKDETALLE.ID_MONEDA.equal(2)).execute();
                    JOptionPane.showMessageDialog(null, "Se han actualizado los costos de ventas del mes: "+Utiles.valueAt(modelo, row, "mes"), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }
    }//GEN-LAST:event_jMenuItemActualizaCostosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JComboBox jComboMoneda;
    private javax.swing.JFormattedTextField jFmtCotizacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuItem jMenuItemActualizaCostos;
    private com.toedter.calendar.JMonthChooser jMes;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenuMoneda;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTabla;
    private javax.swing.JTextField jTxtBusca;
    private com.toedter.calendar.JYearChooser jYear;
    // End of variables declaration//GEN-END:variables
}
