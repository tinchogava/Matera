/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.FormaPagoCRUD;
import ar.com.bosoft.crud.TipoCompCRUD;
import ar.com.bosoft.data.FormaPagoData;
import ar.com.bosoft.data.TipoCompData;
import ar.com.bosoft.formatosCampos.ConMascara;
import java.awt.HeadlessException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class CargaFacturaPresu extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa;
    String empresa, usuario;
    
    ResultSet rsConsulta;
    TipoCompCRUD tipoCompCRUD;
    FormaPagoCRUD formaPagoCRUD;
    
    ArrayList tipoCompArray, formaPagoArray;
    
    ConMascara mascara;
    
    public CargaFacturaPresu(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        
        this.tipoCompCRUD = new TipoCompCRUD(conexion, empresa);
        this.formaPagoCRUD = new FormaPagoCRUD(conexion, empresa);
        
        this.mascara = new ConMascara();
        
        initComponents();
        
        llenaComboTipoComp();
        llenaComboFormaPago();
        limpia();
    }

    private void llenaComboTipoComp(){
        tipoCompArray = tipoCompCRUD.consulta();
        if (!tipoCompArray.isEmpty()){
            Iterator i = tipoCompArray.iterator();
            while (i.hasNext()){
                TipoCompData t = (TipoCompData) i.next();
                this.jComboTipoComp.addItem(t.getAbreviatura());
            }
        }else{
            JOptionPane.showMessageDialog(this, "No hay tipos de comprobantes habilitados", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void llenaComboFormaPago(){
        formaPagoArray = formaPagoCRUD.consulta(true);
        if (!formaPagoArray.isEmpty()){
            Iterator i = formaPagoArray.iterator();
            while (i.hasNext()){
                FormaPagoData f = (FormaPagoData) i.next();
                this.jComboFormaPago.addItem(f.getNombre());
            }
        }else{
            JOptionPane.showMessageDialog(this, "No hay formas de pago habilitadas", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void limpia(){
        this.jLblId_presupuesto.setText("");
        this.jFecha.setDate(new Date());
        this.jComboTipoComp.setSelectedIndex(tipoCompArray.isEmpty() ? -1 : 0);
        this.jTxtSucursal.setText("");
        this.jTxtNumero.setText("");
        this.jVence.setDate(null);
        this.jComboFormaPago.setSelectedIndex(formaPagoArray.isEmpty() ? -1 : 0);
        this.jFmtSubtotal.setValue(0.00);
        this.jFmtBonificacion.setValue(0.00);
        this.jLblNeto.setText("0,00");
        this.jFmtPercIIBB.setValue(0.00);
        this.jLblPercIIBB.setText("0,00");
        this.jComboAliIva.setSelectedIndex(0);
        this.jLblIva.setText("0,00");
        this.jLblTotal.setText("0,00");
        this.jTxtObservaciones.setText("");
    }
    
    private void actualizaValores(){
        RoundingMode RM = RoundingMode.HALF_UP;
        int escala = 2;
        BigDecimal cien = new BigDecimal(100);
        
        BigDecimal subtotal = new BigDecimal(Double.parseDouble(this.jFmtSubtotal.getValue().toString()));
        BigDecimal bonificacion = new BigDecimal(Double.parseDouble(this.jFmtBonificacion.getValue().toString()));

        BigDecimal neto = subtotal.subtract(bonificacion).setScale(escala, RM);

        BigDecimal aliPerc = new BigDecimal(Double.parseDouble(this.jFmtPercIIBB.getValue().toString()));
        aliPerc = aliPerc.divide(cien, 4, RM);
        BigDecimal perc = neto.multiply(aliPerc).setScale(escala, RM);

        BigDecimal aliIva = new BigDecimal(Double.parseDouble(this.jComboAliIva.getSelectedItem().toString().replace(',', '.')));
        aliIva = aliIva.divide(cien, 4, RM);
        BigDecimal iva = neto.multiply(aliIva).setScale(escala, RM);
        
        BigDecimal total = neto.add(perc).setScale(escala, RM);
        total = total.add(iva).setScale(escala, RM);
        
        this.jLblNeto.setText(String.format("%.2f", neto));
        this.jLblPercIIBB.setText(String.format("%.2f", perc));
        this.jLblIva.setText(String.format("%.2f", iva));
        this.jLblTotal.setText(String.format("%.2f", total));
    }
    
    private String validaObligatorios(){
        String respuesta = "";
        
        if (this.jLblId_presupuesto.getText().trim().isEmpty() ||
                this.jFecha.getDate() == null ||
                this.jComboTipoComp.getSelectedIndex() < 0 ||
                this.jTxtSucursal.getText().isEmpty() ||
                this.jTxtNumero.getText().isEmpty() ||
                this.jComboFormaPago.getSelectedIndex() < 0 ||
                Double.parseDouble(this.jLblTotal.getText().replace(".", "").replace(',', '.')) <= 0){
            respuesta = "Complete todos los datos obligatorios (*)";
            return respuesta;
        }
        
        if (this.jVence.getDate() != null){
            if (this.jVence.getDate().before(this.jFecha.getDate())){
                respuesta = "Hay un error en las fechas";
                return respuesta;
            }
        }
        
        return respuesta;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBtnSalir = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jFecha = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jFecha.getJCalendar().setTodayButtonVisible(true); 
        this.jFecha.getJCalendar().setTodayButtonText("Hoy"); 
        this.jFecha.getJCalendar().setWeekOfYearVisible(false);
        jLabel1 = new javax.swing.JLabel();
        jComboTipoComp = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jTxtSucursal = new javax.swing.JTextField();
        jTxtSucursal.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(4,true));
        jTxtNumero = new javax.swing.JTextField();
        jTxtNumero.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(8,true));
        jLabel3 = new javax.swing.JLabel();
        jVence = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jVence.getJCalendar().setTodayButtonVisible(true); 
        this.jVence.getJCalendar().setTodayButtonText("Hoy"); 
        this.jVence.getJCalendar().setWeekOfYearVisible(false);
        jLabel6 = new javax.swing.JLabel();
        jComboFormaPago = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jFmtSubtotal = new ar.com.bosoft.formatosCampos.Decimal(true);
        ;
        jLabel9 = new javax.swing.JLabel();
        jFmtBonificacion = new ar.com.bosoft.formatosCampos.Decimal(true);  ;
        jLabel10 = new javax.swing.JLabel();
        jLblNeto = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jFmtPercIIBB = new ar.com.bosoft.formatosCampos.Decimal(true);  ;
        jLabel15 = new javax.swing.JLabel();
        jLblPercIIBB = new javax.swing.JLabel();
        jLblIva = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLblTotal = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();
        jComboAliIva = new javax.swing.JComboBox();
        jLblId_presupuesto = this.mascara.getjFmt("######", ' ', true);
        jBtnVer = new javax.swing.JButton();

        setTitle("Carga comprobantes a presupuesto");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

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

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos del comprobante"));

        jLabel5.setText("* Fecha");

        jLabel1.setText("* Tipo");

        jLabel2.setText("* Número");

        jTxtSucursal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtSucursalFocusLost(evt);
            }
        });

        jTxtNumero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtNumeroFocusLost(evt);
            }
        });

        jLabel3.setText("Vencimiento");

        jLabel6.setText("* Forma de pago");

        jLabel7.setText("* Subtotal");

        jFmtSubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmtSubtotal.setNextFocusableComponent(jFmtBonificacion);
        jFmtSubtotal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFmtSubtotalFocusLost(evt);
            }
        });

        jLabel9.setText("Bonificación");

        jFmtBonificacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmtBonificacion.setNextFocusableComponent(jFmtPercIIBB);
        jFmtBonificacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFmtBonificacionFocusLost(evt);
            }
        });

        jLabel10.setText("Neto");

        jLblNeto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLblNeto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblNeto.setText("0,00");

        jLabel13.setText("Perc. IIBB (%)");

        jFmtPercIIBB.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmtPercIIBB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFmtPercIIBBFocusLost(evt);
            }
        });

        jLabel15.setText("IVA (%)");

        jLblPercIIBB.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLblPercIIBB.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblPercIIBB.setText("0,00");

        jLblIva.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLblIva.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblIva.setText("0,00");

        jLabel21.setText("Total");

        jLblTotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblTotal.setText("0,00");

        jLabel100.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel100.setText("* Presupuesto");

        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jTxtObservaciones.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Observaciones"));
        jScrollPane2.setViewportView(jTxtObservaciones);

        jComboAliIva.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "21", "10,5", "0,0" }));
        jComboAliIva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboAliIvaActionPerformed(evt);
            }
        });

        jLblId_presupuesto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jLblId_presupuesto.setText("...");
        jLblId_presupuesto.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);

        jBtnVer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/16x16/enabled/eye_icon&16.png"))); // NOI18N
        jBtnVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnVerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel15)
                    .addComponent(jLabel13)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel100))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFmtSubtotal)
                            .addComponent(jFmtBonificacion)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jFmtPercIIBB, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLblPercIIBB, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLblNeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jComboAliIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLblIva, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 703, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboTipoComp, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jVence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLblId_presupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBtnVer)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel100)
                    .addComponent(jLblId_presupuesto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnVer, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1)
                    .addComponent(jComboTipoComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel3)
                        .addComponent(jTxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTxtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jVence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jFmtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jFmtBonificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLblNeto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jFmtPercIIBB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblPercIIBB))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jLblIva)
                            .addComponent(jComboAliIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jLblTotal)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnGuardar)
                        .addGap(314, 314, 314)
                        .addComponent(jBtnSalir))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jBtnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBtnGuardar))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        try {
            if (this.rsConsulta != null){
                this.rsConsulta.close();
            }
            this.dispose();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios().isEmpty()){
            try{
                ArrayList parametros = new ArrayList();

                int id_presupuesto = Integer.parseInt(this.jLblId_presupuesto.getText().trim());
                parametros.add(id_presupuesto);
                rsConsulta = conexion.procAlmacenado("estadoPresupuesto", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                
                if (rsConsulta.first()) {
                    String estado = rsConsulta.getString("estado");
                    switch (estado){
                        case "P":
                            JOptionPane.showMessageDialog(this, "El presupuesto se encuentra pendiente", "Atención", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        case "N":
                            JOptionPane.showMessageDialog(this, "El presupuesto se encuentra anulado", "Atención", JOptionPane.INFORMATION_MESSAGE);
                            return;
                    }
                }else{
                    JOptionPane.showMessageDialog(this, "No se encontró el presupuesto", "Atención", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                Long fecha = this.jFecha.getDate().getTime();

                TipoCompData t = (TipoCompData) tipoCompArray.get(this.jComboTipoComp.getSelectedIndex());
                int id_tipoComp = t.getId_tipoComp();

                String numComp = this.jTxtSucursal.getText() + this.jTxtNumero.getText();
                Long vencimiento = (this.jVence.getDate() != null ? this.jVence.getDate().getTime() : (long) 0);

                FormaPagoData f = (FormaPagoData) formaPagoArray.get(this.jComboFormaPago.getSelectedIndex());
                int id_formaPago = f.getId_formaPago();

                String dc = "D";
                if (id_tipoComp == 3 ||
                        id_tipoComp == 7 ||
                        id_tipoComp == 10 ||
                        id_tipoComp == 13 ||
                        id_tipoComp == 25){
                    dc = "C";
                }

                Double bonificacion = Double.parseDouble(this.jFmtBonificacion.getValue().toString());
                Double neto = Double.parseDouble(this.jLblNeto.getText().replace(".", "").replace(',', '.'));
                Double aliPercIIBB = Double.parseDouble(this.jFmtPercIIBB.getValue().toString());
                Double percIIBB = Double.parseDouble(this.jLblPercIIBB.getText().replace(".", "").replace(',', '.'));
                Double aliIva = Double.parseDouble(this.jComboAliIva.getSelectedItem().toString().replace(',', '.'));
                Double iva = Double.parseDouble(this.jLblIva.getText().replace(".", "").replace(',', '.'));
                String observaciones = this.jTxtObservaciones.getText().trim();

                parametros.clear();
                parametros.add(id_tipoComp);
                parametros.add(numComp);
                parametros.add(this.id_empresa);
                
                rsConsulta = conexion.procAlmacenado("existeFactura", parametros, 
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                
                if (rsConsulta.first()) {
                    int turno = rsConsulta.getInt("id_presupuesto");
                    JOptionPane.showMessageDialog(this, "Este comprobante ya ha sido cargado " + 
                                                            (turno == id_presupuesto ? "a este presupuesto" : 
                                                                                            "al presupuesto " + turno), "Atención", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                parametros.clear();
                parametros.add(fecha);
                parametros.add(id_presupuesto);
                parametros.add(id_tipoComp);
                parametros.add(numComp);
                parametros.add(id_tipoComp);
                parametros.add(numComp);
                parametros.add(vencimiento);
                parametros.add(id_formaPago);
                parametros.add(dc);
                parametros.add(bonificacion);
                parametros.add(neto);
                parametros.add(aliPercIIBB);
                parametros.add(percIIBB);
                parametros.add(aliIva);
                parametros.add(iva);
                parametros.add(observaciones);
                parametros.add(0);
                parametros.add("N");
                parametros.add(0);
                parametros.add(this.id_empresa);
                parametros.add(this.usuario);

                conexion.procAlmacenado("insertFactura", parametros, 
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                limpia();
            } catch (NumberFormatException | SQLException | HeadlessException ex) {
                Utiles.enviaError(this.empresa,ex);
            }   
        }else{
            JOptionPane.showMessageDialog(this, validaObligatorios(), "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jFmtSubtotalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFmtSubtotalFocusLost
        actualizaValores();
    }//GEN-LAST:event_jFmtSubtotalFocusLost

    private void jFmtBonificacionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFmtBonificacionFocusLost
        actualizaValores();
    }//GEN-LAST:event_jFmtBonificacionFocusLost

    private void jFmtPercIIBBFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFmtPercIIBBFocusLost
        actualizaValores();
    }//GEN-LAST:event_jFmtPercIIBBFocusLost

    private void jTxtSucursalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtSucursalFocusLost
        if (!this.jTxtSucursal.getText().isEmpty()){
            String aux = this.jTxtSucursal.getText();
            this.jTxtSucursal.setText("0000".substring(aux.length()) + aux);
        }
    }//GEN-LAST:event_jTxtSucursalFocusLost

    private void jTxtNumeroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtNumeroFocusLost
        if (!this.jTxtNumero.getText().isEmpty()){
            String aux = this.jTxtNumero.getText();
            this.jTxtNumero.setText("00000000".substring(aux.length()) + aux);
        }
    }//GEN-LAST:event_jTxtNumeroFocusLost

    private void jComboAliIvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboAliIvaActionPerformed
        actualizaValores();
    }//GEN-LAST:event_jComboAliIvaActionPerformed

    private void jBtnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnVerActionPerformed
        if (!this.jLblId_presupuesto.getText().trim().isEmpty()) {
            Presupuesto presupuesto = new Presupuesto(conexion, id_empresa, empresa, this.usuario);
            presupuesto.setId_presupuesto(this.jLblId_presupuesto.getText().trim());
            Principal.dp.add(presupuesto);
            presupuesto.toFront();
            presupuesto.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Ingrese el presupuesto a consultar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnVerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnVer;
    private javax.swing.JComboBox jComboAliIva;
    private javax.swing.JComboBox jComboFormaPago;
    private javax.swing.JComboBox jComboTipoComp;
    private com.toedter.calendar.JDateChooser jFecha;
    private javax.swing.JFormattedTextField jFmtBonificacion;
    private javax.swing.JFormattedTextField jFmtPercIIBB;
    private javax.swing.JFormattedTextField jFmtSubtotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JFormattedTextField jLblId_presupuesto;
    private javax.swing.JLabel jLblIva;
    private javax.swing.JLabel jLblNeto;
    private javax.swing.JLabel jLblPercIIBB;
    private javax.swing.JLabel jLblTotal;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTxtNumero;
    private javax.swing.JTextArea jTxtObservaciones;
    private javax.swing.JTextField jTxtSucursal;
    private com.toedter.calendar.JDateChooser jVence;
    // End of variables declaration//GEN-END:variables
}
