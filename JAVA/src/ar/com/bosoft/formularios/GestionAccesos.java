package ar.com.bosoft.formularios;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.UsuarioCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.UsuarioData;
import ar.com.bosoft.data.ZonaData;
import com.google.common.primitives.Ints;
import java.awt.Rectangle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class GestionAccesos extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, id_usuario;
    String empresa, claveAccesos;
    
    UsuarioCRUD usuarioCRUD;
    ZonaCRUD zonaCRUD;
    
    ArrayList usuarioArray, zonaArray, rubroArray, menuArray;
    
    DefaultTableModel modelo;
    
    ResultSet rsAccesos;
    
    ar.com.bosoft.RenderTablas.FormatoTablaAccesos formato = new ar.com.bosoft.RenderTablas.FormatoTablaAccesos();
    
    /**
     * Creates new form GestionAccesos
     * @param conexion
     * @param empresa
     * @param id_empresa
     * @param id_usuario
     * @param claveAccesos
     */
    public GestionAccesos(Conexion conexion, int id_empresa, String empresa, int id_usuario, String claveAccesos) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.id_usuario = id_usuario;
        this.claveAccesos = claveAccesos;
        this.menuArray = new ArrayList();
        this.rubroArray = new ArrayList();
        
        this.usuarioCRUD = new UsuarioCRUD(conexion, id_empresa, empresa);
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        
        initComponents();
        
        jTablaAccesos.setDefaultRenderer(Object.class, formato);
        modelo = (DefaultTableModel) jTablaAccesos.getModel();
        jTablaAccesos.setModel(modelo);
        
        llenaComboUsuarios();
        llenaComboZona();
        llenaComboRubro();
        limpia();
    }

    private void llenaComboUsuarios(){
        usuarioArray = usuarioCRUD.consulta(true,true);
        Iterator i = usuarioArray.iterator();
        while (i.hasNext()){
            UsuarioData u = (UsuarioData) i.next();
            jComboUsuarios.addItem(u.getNombre());
        }
    }
    
    private void llenaComboZona(){
        this.jComboZona.addItem("-- TODAS --");
        zonaArray = zonaCRUD.consulta(true);
        Iterator i = zonaArray.iterator();
        while (i.hasNext()){
            ZonaData z = (ZonaData) i.next();
            this.jComboZona.addItem(z.getNombre());
        }
    }
    
    private void llenaComboRubro(){
        this.jComboRubro.addItem("-- TODOS --");
        try {
            ArrayList parametros = new ArrayList();
            parametros.add(this.id_empresa);
            rsAccesos = conexion.procAlmacenado("traeRubros", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
            
            if (rsAccesos.first()){
                rsAccesos.beforeFirst();
                while (rsAccesos.next()){
                    String posicion = rsAccesos.getString("posicion");
                    String nombre = rsAccesos.getString("nombre");
                    
                    Object[] nuevo = {posicion, nombre};
                    rubroArray.add(nuevo);
                    
                    this.jComboRubro.addItem(nombre);
                }
            }else{
                JOptionPane.showMessageDialog(this, "No hay rubros habilitados", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionAccesos.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    private void limpia(){
        this.jComboUsuarios.setSelectedIndex(-1);
        this.jComboRubro.setSelectedIndex(0);
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
    }
    
    private void traeAccesos(){
        this.menuArray.clear();
        try{
            modelo.getDataVector().removeAllElements();
            modelo.fireTableDataChanged();
            
            ArrayList parametros = new ArrayList();
            
            UsuarioData u = (UsuarioData) usuarioArray.get(this.jComboUsuarios.getSelectedIndex());
            int id_usuarioSeleccionado = u.getId_usuario();
            
            parametros.add(id_usuarioSeleccionado);
            parametros.add(this.id_empresa);
            
            rsAccesos = conexion.procAlmacenado("traeAccesos", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
            
            rsAccesos.beforeFirst();
            int indiceArray = 0;
            while (rsAccesos.next()){
                String nombre = rsAccesos.getString("nombre");
                String habilita = rsAccesos.getString("habilita");
                int id_menu = rsAccesos.getInt("id_menu");
                String posicion = rsAccesos.getString("posicion");
                
                Object[] fila = {nombre, habilita, id_menu, posicion, indiceArray};
                
                modelo.addRow(fila);
                menuArray.add(fila);
                 indiceArray++;
            }  
            
            this.jComboZona.setSelectedIndex(0);
            if (u.getId_zonaSistema() > 0) {
                Iterator it = zonaArray.iterator();
                while (it.hasNext()) {
                    ZonaData z = (ZonaData)it.next();
                    if (z.getId_zona() == u.getId_zonaSistema()) {
                        this.jComboZona.setSelectedItem(z.getNombre());
                        break;
                    }
                }
            }
            
            this.jComboRubro.setEnabled(true);
            this.jTablaAccesos.setEnabled(true);
            this.jBtnGuardar.setEnabled(true);
            this.jComboRubro.setSelectedIndex(0);
        }catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboUsuarios = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jBtnGuardar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablaAccesos = new javax.swing.JTable();
        jProgressBar = new javax.swing.JProgressBar();
        jLabel3 = new javax.swing.JLabel();
        jPassword = new javax.swing.JPasswordField();
        jBtnBusca = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboRubro = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();

        setTitle("Gestión de Accesos");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jLabel1.setText("Usuario");

        jComboUsuarios.setEnabled(false);

        jLabel2.setText("Opciones de Sistema");

        jBtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/guardar.png"))); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnGuardar.setEnabled(false);
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
        jBtnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnSalir.setFocusPainted(false);
        jBtnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jBtnSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/salir.png"))); // NOI18N
        jBtnSalir.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/salir.png"))); // NOI18N
        jBtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSalirActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(102, 153, 255));

        jTablaAccesos.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTablaAccesos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Opción", "Hab.", "id_menu", "posicion", "indiceArray"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaAccesos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTablaAccesos.setEnabled(false);
        jTablaAccesos.getTableHeader().setReorderingAllowed(false);
        jTablaAccesos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaAccesosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTablaAccesos);
        if (jTablaAccesos.getColumnModel().getColumnCount() > 0) {
            jTablaAccesos.getColumnModel().getColumn(1).setMinWidth(50);
            jTablaAccesos.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTablaAccesos.getColumnModel().getColumn(1).setMaxWidth(50);
            jTablaAccesos.getColumnModel().getColumn(2).setMinWidth(0);
            jTablaAccesos.getColumnModel().getColumn(2).setPreferredWidth(0);
            jTablaAccesos.getColumnModel().getColumn(2).setMaxWidth(0);
            jTablaAccesos.getColumnModel().getColumn(3).setMinWidth(0);
            jTablaAccesos.getColumnModel().getColumn(3).setPreferredWidth(0);
            jTablaAccesos.getColumnModel().getColumn(3).setMaxWidth(0);
            jTablaAccesos.getColumnModel().getColumn(4).setMinWidth(0);
            jTablaAccesos.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTablaAccesos.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jProgressBar.setStringPainted(true);

        jLabel3.setText("Clave");

        jPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordFocusLost(evt);
            }
        });
        jPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordActionPerformed(evt);
            }
        });

        jBtnBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/24x24/enabled/buscar.png"))); // NOI18N
        jBtnBusca.setText("Consultar");
        jBtnBusca.setBorderPainted(false);
        jBtnBusca.setContentAreaFilled(false);
        jBtnBusca.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnBusca.setFocusPainted(false);
        jBtnBusca.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/24x24/rollover/buscar.png"))); // NOI18N
        jBtnBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscaActionPerformed(evt);
            }
        });

        jLabel4.setText("Rubro");

        jComboRubro.setEnabled(false);
        jComboRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboRubroActionPerformed(evt);
            }
        });

        jLabel16.setText("Zona");

        jComboZona.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBtnGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jComboZona, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jComboUsuarios, javax.swing.GroupLayout.Alignment.LEADING, 0, 248, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jBtnBusca)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnBusca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboRubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnGuardar)
                            .addComponent(jProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(382, 382, 382)
                        .addComponent(jBtnSalir))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        int reg = menuArray.size();
        this.jProgressBar.setMaximum(reg);
        
        UsuarioData u = (UsuarioData) usuarioArray.get(this.jComboUsuarios.getSelectedIndex());
        int id_usuarioSeleccionado = u.getId_usuario();
        
        ArrayList parametros = new ArrayList();        
        parametros.add(id_usuarioSeleccionado);
        parametros.add(this.id_empresa);

        conexion.procAlmacenado("deleteAcceso", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());

        String posicionPadre = "";
        Iterator it = menuArray.iterator();
        int i = 0;
        while (it.hasNext()) {
            parametros.clear();

            Object[] opcion = (Object[]) it.next();
            String habilita = opcion[1].toString();
            String posicion = opcion[3].toString();
            if (habilita.equals("SI")) {
                if (!posicionPadre.equals(posicion.substring(0, posicion.length() - 1))){ //Todavia no guardo el padre
                    posicionPadre = posicion.substring(0, posicion.length() - 1);

                    while (posicionPadre.length() > 0){
                        parametros.add(id_usuarioSeleccionado);
                        parametros.add(this.id_empresa);
                        parametros.add(posicionPadre);

                        conexion.procAlmacenado("insertPadre", parametros, 
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());

                        posicionPadre = posicionPadre.substring(0, posicionPadre.length() - 1);
                        parametros.clear();
                    }

                    parametros.add(id_usuarioSeleccionado);
                    parametros.add((int) opcion[2]);
                    parametros.add(this.id_empresa);

                    conexion.procAlmacenado("insertAcceso", parametros, 
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
                }
            }
            i++;
            this.jProgressBar.setValue(i);
            Rectangle progressRect = this.jProgressBar.getBounds();
            progressRect.x = 0;
            progressRect.y = 0;
            this.jProgressBar.paintImmediately( progressRect );
        }
        
        int id_zonaSistema = 0;
        if (this.jComboZona.getSelectedIndex() > 0) {
            ZonaData z = (ZonaData) this.zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
            id_zonaSistema = z.getId_zona();
        }
        
        parametros.clear();
        parametros.add(id_usuarioSeleccionado);
        parametros.add(id_zonaSistema);
        conexion.procAlmacenado("zonaSistema", parametros, 
            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        
        
        this.jProgressBar.setValue(reg);
        Rectangle progressRect = this.jProgressBar.getBounds();
        progressRect.x = 0;
        progressRect.y = 0;
        this.jProgressBar.paintImmediately( progressRect );
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordFocusLost
        if (!(this.jPassword.getText().trim().equals(""))){
            if (this.jPassword.getText().trim().equals(this.claveAccesos)){
                this.jPassword.setEnabled(false);
                this.jComboUsuarios.setEnabled(true);
                this.jComboZona.setEnabled(true);
                this.jComboUsuarios.requestFocus();
            }else{
               JOptionPane.showMessageDialog(this,"La clave no es correcta", "Información",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jPasswordFocusLost

    private void jBtnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscaActionPerformed
        if (this.jComboUsuarios.getSelectedIndex() >= 0){
            traeAccesos();
        }
    }//GEN-LAST:event_jBtnBuscaActionPerformed

    private void jTablaAccesosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaAccesosMouseClicked
        if (evt.getClickCount() == 2){
            List rows = Ints.asList(jTablaAccesos.getSelectedRows());
            for(Object r : rows){
                int row = (int) r;
                int fila = jTablaAccesos.convertRowIndexToModel(row);

                String nombre = modelo.getValueAt(fila, 0).toString();
                String habilita = modelo.getValueAt(fila, 1).toString().equals("NO") ? "SI" : "NO";
                int id_menu = (int) modelo.getValueAt(fila, 2);
                String posicion = modelo.getValueAt(fila, 3).toString();
                int indiceArray = (int) modelo.getValueAt(fila, 4);

                Object[] filaModificada = {nombre, habilita, id_menu, posicion, indiceArray};

                menuArray.set(indiceArray, filaModificada);

                modelo.setValueAt(habilita, fila, 1);                
            }
            

        }
    }//GEN-LAST:event_jTablaAccesosMouseClicked

    private void jComboRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboRubroActionPerformed
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
        
        boolean todos = this.jComboRubro.getSelectedIndex() == 0;
        
        Iterator i = menuArray.iterator();        
        while (i.hasNext()){
            Object[] fila = (Object[]) i.next();
            if (todos){
                modelo.addRow(fila);
            }else{
                Object[] rubroSeleccionado = (Object[])rubroArray.get(this.jComboRubro.getSelectedIndex() - 1);
                int posicionRubro = Integer.parseInt(rubroSeleccionado[0].toString());
                int posicionPadre = Integer.parseInt(fila[3].toString().substring(0, 1));
                if (posicionRubro == posicionPadre){
                    modelo.addRow(fila);
                }
            }
        }
    }//GEN-LAST:event_jComboRubroActionPerformed

    private void jPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnBusca;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JComboBox jComboRubro;
    private javax.swing.JComboBox jComboUsuarios;
    private javax.swing.JComboBox jComboZona;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPassword;
    private javax.swing.JProgressBar jProgressBar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTablaAccesos;
    // End of variables declaration//GEN-END:variables
}
