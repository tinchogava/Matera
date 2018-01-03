package ar.com.bosoft.buscadores;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ProductoCRUD;
import ar.com.bosoft.data.ProductoData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;



public class BuscaProducto extends javax.swing.JDialog {
    String empresa, codigo, gtin, nombre, pm, clasiProducto;
    int id_empresa, id_producto, id_moneda;
    Double costo;
    ProductoCRUD productoCRUD;
    ArrayList arrayProducto;
    DefaultTableModel modelo;
    TableRowSorter sorter;
    Conexion conexion;
    
    public BuscaProducto(Conexion conexion, java.awt.Frame parent, boolean modal, int id_empresa, String empresa) {
        super(parent, modal);
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.productoCRUD = new ProductoCRUD(conexion, id_empresa, empresa);
        this.codigo = "";
        this.gtin = "";
        this.nombre = "";
        this.pm = "";
        this.clasiProducto = "";
        this.id_producto = 0;
        this.id_moneda = 0;
        this.costo = 0.00;
        
        initComponents();
        
        this.modelo = (DefaultTableModel) this.jTabla.getModel();
        this.jTabla.setModel(modelo);
        sorter = new TableRowSorter(modelo);
        jTabla.setRowSorter (sorter);
        
        
        setTitle("Busca Producto");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png")).getImage());
        setLocationRelativeTo(parent);
        consulta();
        setJTexFieldChanged(jTxtBusca);
    }

    private void consulta(){
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
        arrayProducto = productoCRUD.consulta(0, true);
        Iterator i = arrayProducto.iterator();
        while (i.hasNext()){
            ProductoData tmp = (ProductoData) i.next();
            HashMap map = new HashMap();
            map.put("codigo", tmp.getCodigo());
            map.put("gtin", tmp.getGtin());
            map.put("nombre", tmp.getNombre());
            map.put("pm", tmp.getPm());
            map.put("clasiproducto", tmp.getClasiProducto());
            map.put("id_producto", tmp.getId_producto());
            map.put("costo", tmp.getCosto());
            map.put("id_moneda", tmp.getId_moneda());

            modelo.addRow(Utiles.fillTableObject(modelo, map));
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
    
    public String getCodigo(){
        return this.codigo;
    }
    
    public String getGtin(){
        return this.gtin;
    }    

    public String getNombre() {
        return nombre;
    }

    public String getPm() {
        return pm;
    }

    public String getClasiProducto() {
        return clasiProducto;
    }

    public int getId_producto() {
        return id_producto;
    }

    public int getId_moneda() {
        return id_moneda;
    }

    public Double getCosto() {
        return costo;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jTxtBusca = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla = new javax.swing.JTable();
        jBtnAcepta = new javax.swing.JButton();
        jBtnCancela = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setFocusable(false);

        jLabel22.setText("Buscar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "gtin", "Nombre", "PM", "clasiProducto", "id_producto", "costo", "id_moneda"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
            jTabla.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTabla.getColumnModel().getColumn(0).setMaxWidth(200);
            jTabla.getColumnModel().getColumn(3).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(3).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(4).setMinWidth(0);
            jTabla.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(4).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(5).setMinWidth(0);
            jTabla.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(5).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(6).setMinWidth(0);
            jTabla.getColumnModel().getColumn(6).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(6).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(7).setMinWidth(0);
            jTabla.getColumnModel().getColumn(7).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(7).setMaxWidth(0);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBtnAcepta)
                        .addGap(182, 182, 182)
                        .addComponent(jBtnCancela))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnCancela, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnAcepta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnAceptaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAceptaActionPerformed
        int indice = jTabla.convertRowIndexToModel (jTabla.getSelectedRow());
        if (indice >= 0){
            this.codigo = modelo.getValueAt(indice, Utiles.getColumnByName(modelo, "codigo")).toString();
            this.gtin = modelo.getValueAt(indice, Utiles.getColumnByName(modelo, "gtin")).toString();
            this.nombre = modelo.getValueAt(indice, Utiles.getColumnByName(modelo, "nombre")).toString();
            this.pm = modelo.getValueAt(indice, Utiles.getColumnByName(modelo, "pm")).toString();
            this.id_producto = (int)modelo.getValueAt(indice, Utiles.getColumnByName(modelo, "id_producto"));
            this.costo = Double.parseDouble(modelo.getValueAt(indice, Utiles.getColumnByName(modelo, "costo")).toString());
            this.id_moneda = (int)modelo.getValueAt(indice, Utiles.getColumnByName(modelo, "id_moneda"));
            this.clasiProducto = Utiles.valueAt(modelo, indice, "clasiProducto").toString();
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }        
    }//GEN-LAST:event_jBtnAceptaActionPerformed

    private void jBtnCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelaActionPerformed
        this.codigo = "";
        this.dispose();
    }//GEN-LAST:event_jBtnCancelaActionPerformed

    private void jTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaMouseClicked
        if (evt.getClickCount() == 2) {
            this.jBtnAceptaActionPerformed(null);
        }
    }//GEN-LAST:event_jTablaMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAcepta;
    private javax.swing.JButton jBtnCancela;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTabla;
    private javax.swing.JTextField jTxtBusca;
    // End of variables declaration//GEN-END:variables
}
