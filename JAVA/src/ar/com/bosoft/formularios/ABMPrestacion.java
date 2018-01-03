/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.formularios;

import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.OpcionCRUD;
import ar.com.bosoft.crud.PrestacionCRUD;
import ar.com.bosoft.data.OpcionData;
import ar.com.bosoft.data.PrestacionData;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class ABMPrestacion extends javax.swing.JInternalFrame {
    Conexion conexion;
    DefaultTableModel modelo;
    TableRowSorter sorter;
    PrestacionCRUD prestacionCRUD;
    OpcionCRUD opcionCRUD;
    ArrayList arrayPrestacion,arrayOpcion;
    String empresa;
    int id_empresa, id_prestacion, indiceModelo;
    
    /**
     * Creates new form AltaUsuario
     * @param conexion
     * @param empresa
     * @param id_empresa
     */
    public ABMPrestacion(Conexion conexion, int id_empresa, String empresa){
        this.conexion = conexion;
        this.empresa = empresa;
        this.id_empresa = id_empresa;
        this.prestacionCRUD = new PrestacionCRUD(conexion, id_empresa, empresa);
        this.opcionCRUD = new OpcionCRUD(conexion, empresa);
        
        initComponents();
        
        modelo = (DefaultTableModel) jTabla.getModel();
        jTabla.setModel(modelo);
        jTabla.setRowSorter (new TableRowSorter(modelo));
        sorter = new TableRowSorter(modelo);
        llenaComboHabilita();
        limpia();
        consulta();
        setJTexFieldChanged(jTxtBusca);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTxtNombre = new javax.swing.JTextField();
        jTxtNombre.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jBtnLimpiar = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jComboHabilita = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jFmtImporte = new ar.com.bosoft.formatosCampos.Decimal(true);
        ;
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();
        jLblId_Prestacion = new javax.swing.JLabel();

        setTitle("ABM Prestación técnica");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jLabel1.setText("* Nombre");

        jTxtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtNombreFocusGained(evt);
            }
        });

        jLabel5.setText("* Habilitado");

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setFocusable(false);

        jTxtBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaFocusGained(evt);
            }
        });

        jLabel6.setText("Buscar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel7.setText("(*)Datos Obligatorios");

        jBtnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/limpiar.png"))); // NOI18N
        jBtnLimpiar.setText("Limpiar");
        jBtnLimpiar.setBorderPainted(false);
        jBtnLimpiar.setContentAreaFilled(false);
        jBtnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnLimpiar.setFocusPainted(false);
        jBtnLimpiar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/limpiar.png"))); // NOI18N
        jBtnLimpiar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/limpiar.png"))); // NOI18N
        jBtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLimpiarActionPerformed(evt);
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

        jTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Importe", "Hab."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
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
        jTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTabla);
        if (jTabla.getColumnModel().getColumnCount() > 0) {
            jTabla.getColumnModel().getColumn(0).setMinWidth(50);
            jTabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTabla.getColumnModel().getColumn(0).setMaxWidth(50);
            jTabla.getColumnModel().getColumn(2).setMinWidth(75);
            jTabla.getColumnModel().getColumn(2).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(2).setMaxWidth(75);
            jTabla.getColumnModel().getColumn(3).setMinWidth(50);
            jTabla.getColumnModel().getColumn(3).setPreferredWidth(50);
            jTabla.getColumnModel().getColumn(3).setMaxWidth(50);
        }

        jLabel8.setText("Importe");

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Salida"));

        jBtnScr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/pantalla.png"))); // NOI18N
        jBtnScr.setBorder(null);
        jBtnScr.setBorderPainted(false);
        jBtnScr.setContentAreaFilled(false);
        jBtnScr.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnScr.setFocusPainted(false);
        jBtnScr.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/pantalla.png"))); // NOI18N
        jBtnScr.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/pantalla.png"))); // NOI18N
        jBtnScr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnScrActionPerformed(evt);
            }
        });

        jBtnImp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/impresora.png"))); // NOI18N
        jBtnImp.setBorder(null);
        jBtnImp.setBorderPainted(false);
        jBtnImp.setContentAreaFilled(false);
        jBtnImp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnImp.setFocusPainted(false);
        jBtnImp.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/impresora.png"))); // NOI18N
        jBtnImp.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/impresora.png"))); // NOI18N
        jBtnImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnImpActionPerformed(evt);
            }
        });

        jBtnPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/pdf.png"))); // NOI18N
        jBtnPdf.setBorder(null);
        jBtnPdf.setBorderPainted(false);
        jBtnPdf.setContentAreaFilled(false);
        jBtnPdf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnPdf.setFocusPainted(false);
        jBtnPdf.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/pdf.png"))); // NOI18N
        jBtnPdf.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/pdf.png"))); // NOI18N
        jBtnPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPdfActionPerformed(evt);
            }
        });

        jBtnXls.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/xls.png"))); // NOI18N
        jBtnXls.setBorder(null);
        jBtnXls.setBorderPainted(false);
        jBtnXls.setContentAreaFilled(false);
        jBtnXls.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnXls.setFocusPainted(false);
        jBtnXls.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/xls.png"))); // NOI18N
        jBtnXls.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/xls.png"))); // NOI18N
        jBtnXls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnXlsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jBtnScr, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnImp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnXls, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBtnScr)
            .addComponent(jBtnImp)
            .addComponent(jBtnPdf)
            .addComponent(jBtnXls)
        );

        jLblId_Prestacion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblId_Prestacion.setText("---");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jBtnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(31, 31, 31)
                                            .addComponent(jLabel1))
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(jLabel5)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jFmtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboHabilita, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addComponent(jLblId_Prestacion))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel7)
                                .addGap(76, 76, 76)
                                .addComponent(jBtnGuardar)
                                .addGap(65, 65, 65)
                                .addComponent(jBtnLimpiar)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblId_Prestacion)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFmtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboHabilita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jBtnGuardar)
                        .addComponent(jBtnLimpiar))
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jBtnSalir, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void llenaComboHabilita(){
        arrayOpcion = opcionCRUD.consulta(2);
        Iterator i = arrayOpcion.iterator();
        while (i.hasNext()){
            OpcionData tmp = (OpcionData) i.next();
            jComboHabilita.addItem(tmp.getNombre());
        }
    }
    
    private void limpia(){
        this.jLblId_Prestacion.setText("---");
        this.jTxtNombre.setText("");
        this.jFmtImporte.setValue(0.00);
        this.jComboHabilita.setSelectedItem("SI");
        
        this.jTxtBusca.setText("");
        
        this.id_prestacion = 0;
    }
    
    private void consulta(){
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
        
        arrayPrestacion = prestacionCRUD.consulta(false);
        Iterator i = arrayPrestacion.iterator();
        while (i.hasNext()){
            PrestacionData tmp = (PrestacionData) i.next();
            int id = tmp.getId_prestacion();
            String nombre = tmp.getNombre();
            Double importe = tmp.getImporte();
            String habilitado = tmp.getHabilita().equals("S") ? "SI" : "NO";
            Object [] nuevo = {id, nombre, importe, habilitado};
            modelo.addRow(nuevo);
        }
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
        return !this.jTxtNombre.getText().isEmpty();
    }
    
    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
        limpia();
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios()){
            PrestacionData nuevo = new PrestacionData();
            nuevo.setId_prestacion(this.id_prestacion);
            nuevo.setNombre(this.jTxtNombre.getText());
            nuevo.setImporte(Double.parseDouble(this.jFmtImporte.getValue().toString()));
            nuevo.setHabilita(this.jComboHabilita.getSelectedItem().toString().substring(0, 1));

            prestacionCRUD.insert(nuevo);
            
            limpia();
            consulta();
            this.jTxtNombre.requestFocus();
        }else{
            JOptionPane.showMessageDialog(this,"Complete todos los datos obligatorios (*)", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaMouseClicked
        indiceModelo = jTabla.convertRowIndexToModel (jTabla.getSelectedRow());
        PrestacionData tmp = (PrestacionData) arrayPrestacion.get(indiceModelo);
        this.id_prestacion = tmp.getId_prestacion();
        this.jLblId_Prestacion.setText(String.valueOf(this.id_prestacion));
        this.jTxtNombre.setText(tmp.getNombre());
        this.jFmtImporte.setValue(tmp.getImporte());
        this.jComboHabilita.setSelectedItem(tmp.getHabilita().equals("S") ? "SI" : "NO");
        
        this.jTxtNombre.requestFocus();
    }//GEN-LAST:event_jTablaMouseClicked

    private void jTxtNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtNombreFocusGained
        this.jTxtNombre.select(0, this.jTxtNombre.getText().length());
    }//GEN-LAST:event_jTxtNombreFocusGained

    private void jTxtBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaFocusGained
        this.jTxtBusca.select(0, this.jTxtBusca.getText().length());
    }//GEN-LAST:event_jTxtBuscaFocusGained

    private void jBtnScrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScrActionPerformed
//        if (producto()){
//            imprimir(0);
//        }
    }//GEN-LAST:event_jBtnScrActionPerformed

    private void jBtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImpActionPerformed
//        if(producto()){
//            seleccionImp.setVisible(true);
//            if (seleccionImp.getSino()){
//                this.impresora = seleccionImp.getImpresora();
//                imprimir(1);
//            }
//        }
    }//GEN-LAST:event_jBtnImpActionPerformed

    private void jBtnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPdfActionPerformed
//        if (producto()){
//            //Crear un objeto FileChooser
//            JFileChooser fc = new JFileChooser();
//            //Mostrar la ventana para abrir archivo y recoger la respuesta
//            int respuesta = fc.showSaveDialog(null);
//            //Comprobar si se ha pulsado Aceptar
//            if (respuesta == JFileChooser.APPROVE_OPTION){
//                //Crear un objeto File con el archivo elegido
//                File archivoElegido = fc.getSelectedFile();
//                try {
//                    this.rutaArchivo = archivoElegido.getCanonicalPath() + ".pdf";
//                    imprimir(2);
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        }
    }//GEN-LAST:event_jBtnPdfActionPerformed

    private void jBtnXlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnXlsActionPerformed
//        if (producto()){
//            //Crear un objeto FileChooser
//            JFileChooser fc = new JFileChooser();
//            //Mostrar la ventana para abrir archivo y recoger la respuesta
//            int respuesta = fc.showSaveDialog(null);
//            //Comprobar si se ha pulsado Aceptar
//            if (respuesta == JFileChooser.APPROVE_OPTION){
//                //Crear un objeto File con el archivo elegido
//                File archivoElegido = fc.getSelectedFile();
//                try {
//                    this.rutaArchivo = archivoElegido.getCanonicalPath() + ".xlsx";
//                    imprimir(3);
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        }
    }//GEN-LAST:event_jBtnXlsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnLimpiar;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jBtnXls;
    private javax.swing.JComboBox jComboHabilita;
    private javax.swing.JFormattedTextField jFmtImporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLblId_Prestacion;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTabla;
    private javax.swing.JTextField jTxtBusca;
    private javax.swing.JTextField jTxtNombre;
    // End of variables declaration//GEN-END:variables
}
