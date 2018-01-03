package ar.com.bosoft.buscadores;

import ar.com.bosoft.clases.TableHeaderMouseListener;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.formularios.Presupuesto;
import ar.com.bosoft.formularios.Principal;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import jxl.Workbook;
import jxl.write.Number;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class BuscaPresupuesto extends javax.swing.JDialog {
    String empresa,id_presupuesto;
    int id_empresa,meses;
    ZonaCRUD zonaCRUD;
    DefaultTableModel modelo;
    ArrayList arrayZona;
    TableRowSorter sorter;
    JTableHeader header;
    TableHeaderMouseListener tableHeaderMoudseListener;
    Conexion conexion;
    ResultSet rs;
    
    public BuscaPresupuesto(Conexion conexion, java.awt.Frame parent, boolean modal, int id_empresa, String empresa, int meses) {
        super(parent, modal);
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.id_presupuesto = "";
        this.meses = meses;
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        
        initComponents();
        
        this.modelo = (DefaultTableModel) this.jTabla.getModel();
        this.jTabla.setModel(modelo);
        sorter = new TableRowSorter(modelo);
        jTabla.setRowSorter (sorter);
        
        this.header = jTabla.getTableHeader();
        this.tableHeaderMoudseListener = new TableHeaderMouseListener(jTabla);
        this.header.addMouseListener(this.tableHeaderMoudseListener);
        
        setTitle("Busca Presupuesto");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png")).getImage());
        setLocationRelativeTo(parent);
        this.jSpinnerMeses.setValue(meses);
        consultaZona();
        llenaComboZona(); 
        zonaUsuario();
        consulta();
        setJTexFieldChanged(jTxtBusca);
    }

    private void consultaZona(){
        arrayZona = zonaCRUD.consulta(true);
    }
    
    private void llenaComboZona(){
        this.jComboZona.addItem("-- TODAS --");
        Iterator i = arrayZona.iterator();
        while (i.hasNext()){
            ZonaData tmp = (ZonaData) i.next();
            this.jComboZona.addItem(tmp.getNombre());
        }
    }
    
    private void zonaUsuario(){
        int id_zonaUsuario = Principal.usuarioData.getId_zonaSistema();
        if (id_zonaUsuario > 0) {
            if (this.arrayZona != null){
                Iterator it = arrayZona.iterator();
                while (it.hasNext()){
                    ZonaData tmp = (ZonaData) it.next();
                    if (tmp.getId_zona() == id_zonaUsuario) {
                        this.jComboZona.setSelectedItem(tmp.getNombre());
                    }
                }
            }
        }
        this.jComboZona.setEnabled(id_zonaUsuario == 0);
    }
    
    private void consulta(){
        ArrayList parametros = new ArrayList();
        parametros.add(this.id_empresa);
        parametros.add(meses);
        rs = conexion.procAlmacenado("listaPresupuesto", parametros,
                empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        filtra();
    }
    
    private void filtra(){
        try {
            modelo.getDataVector().removeAllElements();
            modelo.fireTableDataChanged();
            ArrayList l = Utiles.resultSetToArrayList(rs);
            for (Object o : l){
                HashMap map = (HashMap) o;
                String zona = this.jComboZona.getSelectedItem().toString().trim();
                map.put("estado",Presupuesto.getEstadoPresupuesto(map.get("estado").toString()));
                if (this.jComboZona.getSelectedIndex() == 0) {
                    modelo.addRow(Utiles.fillTableObject(modelo, map));
                } else if (zona.equals(map.get("zona"))) {
                    modelo.addRow(Utiles.fillTableObject(modelo, map));
                }                
            }
        } catch (SQLException ex) {
            Utiles.enviaError(empresa, ex);
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
          sorter.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*",this.tableHeaderMoudseListener.getColumna()));
          jTabla.setRowSorter(sorter);
        }
    }
    
    public String getId_presupuesto(){
        return this.id_presupuesto;
    }
    
    private void acepta(){
        int indice = jTabla.convertRowIndexToModel (jTabla.getSelectedRow());
        this.id_presupuesto = modelo.getValueAt(indice, 0).toString();
        this.dispose();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu = new javax.swing.JPopupMenu();
        jExcel = new javax.swing.JMenuItem();
        jPanel3 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla = new javax.swing.JTable();
        jBtnAcepta = new javax.swing.JButton();
        jBtnCancela = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSpinnerMeses = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jBtnRecarga = new javax.swing.JButton();

        jExcel.setText("Exportar a Excel");
        jExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jExcelActionPerformed(evt);
            }
        });
        jPopupMenu.add(jExcel);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setFocusable(false);

        jLabel22.setText("Buscar");

        jLabel16.setText("Zona");

        jComboZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboZonaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel22)
                        .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "numero", "Fecha", "estado", "Entidad", "lugar", "Profesional", "Paciente", "DNI", "Monto", "operacion", "Zona", "rm1", "rm2", "rm3", "costo_venta"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTabla.setComponentPopupMenu(jPopupMenu);
        jTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTabla);
        if (jTabla.getColumnModel().getColumnCount() > 0) {
            jTabla.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(1).setMaxWidth(100);
            jTabla.getColumnModel().getColumn(6).setPreferredWidth(150);
            jTabla.getColumnModel().getColumn(6).setMaxWidth(250);
            jTabla.getColumnModel().getColumn(7).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(7).setMaxWidth(100);
            jTabla.getColumnModel().getColumn(8).setPreferredWidth(100);
            jTabla.getColumnModel().getColumn(8).setMaxWidth(200);
        }

        jBtnAcepta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/finalizar.png"))); // NOI18N
        jBtnAcepta.setText("Aceptar");
        jBtnAcepta.setBorderPainted(false);
        jBtnAcepta.setContentAreaFilled(false);
        jBtnAcepta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnAcepta.setFocusPainted(false);
        jBtnAcepta.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/finalizar.png"))); // NOI18N
        jBtnAcepta.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/finalizar.png"))); // NOI18N
        jBtnAcepta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAceptaActionPerformed(evt);
            }
        });

        jBtnCancela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/cancelar.png"))); // NOI18N
        jBtnCancela.setText("Cancelar");
        jBtnCancela.setBorderPainted(false);
        jBtnCancela.setContentAreaFilled(false);
        jBtnCancela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnCancela.setFocusPainted(false);
        jBtnCancela.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/cancelar.png"))); // NOI18N
        jBtnCancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelaActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Últimos");

        jSpinnerMeses.setModel(new javax.swing.SpinnerNumberModel(1, 1, 48, 1));

        jLabel2.setText("meses");

        jBtnRecarga.setText("Recargar");
        jBtnRecarga.setBorderPainted(false);
        jBtnRecarga.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnRecarga.setFocusPainted(false);
        jBtnRecarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnRecargaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinnerMeses, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnRecarga)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jSpinnerMeses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jBtnRecarga))
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
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 281, Short.MAX_VALUE)
                        .addComponent(jBtnAcepta)
                        .addGap(277, 277, 277)
                        .addComponent(jBtnCancela))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnAcepta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnCancela, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnAceptaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAceptaActionPerformed
        if (jTabla.getSelectedRowCount() > 0 && jTabla.convertRowIndexToModel (jTabla.getSelectedRow()) >= 0) {
            acepta();
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione un presupuesto", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }        
    }//GEN-LAST:event_jBtnAceptaActionPerformed

    private void jBtnCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelaActionPerformed
        this.id_presupuesto = "";
        this.dispose();
    }//GEN-LAST:event_jBtnCancelaActionPerformed

    private void jBtnRecargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnRecargaActionPerformed
        this.meses = (Integer)this.jSpinnerMeses.getValue();
        consulta();
    }//GEN-LAST:event_jBtnRecargaActionPerformed

    private void jTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaMouseClicked
        if (evt.getClickCount() == 2){
            acepta();
        }
    }//GEN-LAST:event_jTablaMouseClicked

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        try {
            filtra();
        } catch (Exception e) {
            //Solo para inicializar el combo
        }

    }//GEN-LAST:event_jComboZonaActionPerformed

    private void jExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jExcelActionPerformed
        if (modelo.getRowCount() > 0) {
            //Crear un objeto FileChooser
            JFileChooser fc = new JFileChooser();
            //Mostrar la ventana para abrir archivo y recoger la respuesta
            int respuesta = fc.showSaveDialog(null);
            //Comprobar si se ha pulsado Aceptar
            if (respuesta == JFileChooser.APPROVE_OPTION){
                try {
                    //Crear un objeto File con el archivo elegido
                    File file = new File(fc.getSelectedFile().getCanonicalPath() + ".xls");
                    WritableWorkbook workbook1 = Workbook.createWorkbook(file);
                    WritableSheet sheet1 = workbook1.createSheet("Hoja", 0);
                    for (int i = 0; i < modelo.getColumnCount(); i++) {
                        //if (i > 13) {
                        //    break;
                        //}
                        Label column = new Label(i, 0, modelo.getColumnName(i));
                        sheet1.addCell(column);
                    }
                    int excelRow = 0;
                    int[] selectedRows = jTabla.getSelectedRows();
                    for (int i = 0; i < modelo.getRowCount(); i++) {
                        if (selectedRows.length > 0){
                            if (!ArrayUtils.contains(selectedRows, i)){
                                continue;
                            }
                        }
                        for (int j = 0; j < modelo.getColumnCount(); j++) {

                            //if (j > 13) {
                            //    break;
                            //}
                            int rowIndex = jTabla.convertRowIndexToModel(i);
                            int colIndex = j;
                            String val = (modelo.getValueAt(rowIndex, colIndex) == null ? "" : modelo.getValueAt(rowIndex, colIndex).toString());
                            if (NumberUtils.isNumber(val)){
                                sheet1.addCell(new Number(colIndex, excelRow + 1, Float.parseFloat(val)));
                            }
                            else {
                                sheet1.addCell(new Label(colIndex, excelRow + 1, val));                            
                            }
                        }
                        excelRow++;
                    }
                    workbook1.write();
                    workbook1.close();
                    JOptionPane.showMessageDialog(this,"Exportación exitosa!", "Información",JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException | WriteException ex) {
                    JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }else{
            JOptionPane.showMessageDialog(this,"No hay resultados para mostrar", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jExcelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAcepta;
    private javax.swing.JButton jBtnCancela;
    private javax.swing.JButton jBtnRecarga;
    private javax.swing.JComboBox jComboZona;
    private javax.swing.JMenuItem jExcel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerMeses;
    private javax.swing.JTable jTabla;
    private javax.swing.JTextField jTxtBusca;
    // End of variables declaration//GEN-END:variables
}
