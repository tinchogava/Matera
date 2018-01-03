package ar.com.bosoft.formularios;

import ar.com.bosoft.RenderTablas.DateCellEditor;
import ar.com.bosoft.RenderTablas.FormatoADBH;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.ZonaData;
import com.toedter.calendar.JDateChooser;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class AjusteStock extends javax.swing.JInternalFrame {
    Conexion conexion;
    DefaultTableModel modeloConsulta, modeloAjustes;
    TableRowSorter sorterConsulta,sorterAjustes;
    ResultSet rsConsulta;
    ZonaCRUD zonaCRUD;
    ArrayList zonaArray;
    String empresa,usuario;
    int id_producto, id_empresa;
    int indiceAjustes;
    FormatoADBH formato = new FormatoADBH(4);
    
    /**
     * Creates new form AjusteStock
     * @param conexion
     * @param empresa
     * @param id_empresa
     * @param usuario
     */
    public AjusteStock(Conexion conexion, int id_empresa, String empresa, String usuario){
        this.conexion = conexion;
        this.usuario = usuario;
        this.empresa = empresa;
        this.id_empresa = id_empresa;
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        
        initComponents();
        
        this.modeloConsulta = (DefaultTableModel) this.jTablaConsulta.getModel();
        this.jTablaConsulta.setModel(modeloConsulta);        
        jTablaConsulta.setRowSorter (new TableRowSorter(modeloConsulta));
        sorterConsulta = new TableRowSorter(modeloConsulta);
        
        this.modeloAjustes = (DefaultTableModel) this.jTablaAjustes.getModel();
        this.jTablaAjustes.setModel(modeloAjustes);        
        jTablaAjustes.setRowSorter (new TableRowSorter(modeloAjustes));
        sorterAjustes = new TableRowSorter(modeloAjustes);
        
        TableColumn dateCol = jTablaAjustes.getColumnModel().getColumn(Utiles.getColumnByName(jTablaAjustes, "vencimiento"));
        JDateChooser date = new JDateChooser("dd/MM/20yy", "##/##/####", '_');
        date.setMinSelectableDate(new Date());
        dateCol.setCellEditor(new DateCellEditor(date));              
        
        llenaComboZona();
        limpia();
        zonaUsuario();
        if (!this.jComboZona.isEnabled()) {
            consulta();
        }
        setJTexFieldChanged(jTxtBusca);
    }

    private void llenaComboZona(){
        this.zonaArray = zonaCRUD.consulta(true);
        if (this.zonaArray != null){
            Iterator it = zonaArray.iterator();
            while (it.hasNext()){
                ZonaData tmp = (ZonaData) it.next();
                this.jComboZona.addItem(tmp.getNombre());
            }
        }else{
            JOptionPane.showMessageDialog(this, "No hay zonas habilitadas", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void limpia(){
        this.jTxtBusca.setText("");        
        this.jTablaConsulta.clearSelection();
        
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedIndex(-1);
        }
        
        
        limpiaPanel();
        
        modeloAjustes.getDataVector().removeAllElements();
        modeloAjustes.fireTableDataChanged();
    }
    
    private void zonaUsuario(){
        int id_zonaSistema = Principal.usuarioData.getId_zonaSistema();
        if (id_zonaSistema > 0) {
            if (this.zonaArray != null){
                Iterator it = zonaArray.iterator();
                while (it.hasNext()){
                    ZonaData tmp = (ZonaData) it.next();
                    if (tmp.getId_zona() == id_zonaSistema) {
                        this.jComboZona.setSelectedItem(tmp.getNombre());
                    }
                }
            }
        }
        this.jComboZona.setEnabled(id_zonaSistema == 0);
    }
    
    private void limpiaPanel(){
        this.jLblCodigo.setText("");
        this.jLblNombre.setText("");
        this.jLblStkActual.setText("0");
        this.jComboAltaBaja.setSelectedIndex(0);
        this.jFmtAjuste.setValue(0);
        this.jTxtLote.setText("");
        this.jTxtSerie.setText("");
        this.jTxtPm.setText("");
        this.jVence.setDate(null);
        this.jTxtAreaDetalle.setText("");
        this.id_producto = 0;
        this.indiceAjustes = -1;
    }
    
    private void consulta(){
        try {
            modeloConsulta.getDataVector().removeAllElements();
            modeloConsulta.fireTableDataChanged();
            
            ZonaData z = (ZonaData) this.zonaArray.get(this.jComboZona.getSelectedIndex());
            int id_zona = z.getId_zona();
            
            
            ArrayList parametros = new ArrayList();
            parametros.add(0);
            parametros.add(0);
            parametros.add(id_zona);
            parametros.add(this.id_empresa);
            rsConsulta = conexion.procAlmacenado("consultaStock", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
            rsConsulta.beforeFirst();
            
            while (rsConsulta.next()){
                /*
                String codigo = rsConsulta.getString("codigo");
                String nombre = rsConsulta.getString("nombre");
                int existencia = rsConsulta.getInt("existencia");
                int id = rsConsulta.getInt("id_producto");
                String pm = rsConsulta.getString("pm");
                
                Object[] fila = {codigo, nombre, existencia, id, pm};
                modeloConsulta.addRow(fila);
                */
                modeloConsulta.addRow(Utiles.fillTableObject(modeloConsulta, rsConsulta));
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
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
          sorterConsulta.setRowFilter(null);
        } else {
          sorterConsulta.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTablaConsulta.setRowSorter(sorterConsulta);
        }
    }
    
    private String validaAjuste(){
        String aviso = "";
        if (this.jLblCodigo.getText().isEmpty() ||
                Integer.parseInt(this.jFmtAjuste.getValue().toString()) <= 0 
//                ||
//                this.jTxtLote.getText().isEmpty() ||
//                this.jTxtPm.getText().isEmpty() ||
//                this.jVence.getDate() == null){
                ){
            aviso = "Complete todos los datos obligatorios (*)";
        }
        return aviso;
    }
    
    private String validaObligatorios(){
        String aviso = "";
        
        if (this.jComboZona.getSelectedIndex() < 0){
            aviso = "Falta cargar la zona";
        }
        
        if (modeloAjustes.getRowCount() == 0){
            aviso = "No hay ajustes cargados";
        }
        
        return aviso;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuEliminaModifica = new javax.swing.JPopupMenu();
        jMenuItemModifica = new javax.swing.JMenuItem();
        jMenuItemElimina = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLblCodigo = new javax.swing.JLabel();
        jLblNombre = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLblStkActual = new javax.swing.JLabel();
        jComboAltaBaja = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTxtAreaDetalle = new javax.swing.JTextArea();
        //try{
            jFmtAjuste = new javax.swing.JFormattedTextField();
            //}catch (Exception ex){}
        jLabel1 = new javax.swing.JLabel();
        jBtnCarga = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxtLote = new javax.swing.JTextField();
        jTxtLote.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel4 = new javax.swing.JLabel();
        jTxtPm = new javax.swing.JTextField();
        jTxtPm.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jVence = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jVence.getJCalendar().setTodayButtonVisible(true);
        this.jVence.getJCalendar().setTodayButtonText("Hoy");
        this.jVence.getJCalendar().setWeekOfYearVisible(false);
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTxtSerie = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTablaAjustes = new javax.swing.JTable();
        jTablaAjustes.setDefaultRenderer(Object.class, formato);
        jBtnSalir = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();

        jMenuItemModifica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/16x16/enabled/editar_popup.png"))); // NOI18N
        jMenuItemModifica.setText("Modificar");
        jMenuItemModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemModificaActionPerformed(evt);
            }
        });
        jMenuEliminaModifica.add(jMenuItemModifica);

        jMenuItemElimina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/16x16/enabled/eliminar_popup.png"))); // NOI18N
        jMenuItemElimina.setText("Eliminar");
        jMenuItemElimina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEliminaActionPerformed(evt);
            }
        });
        jMenuEliminaModifica.add(jMenuItemElimina);

        setTitle("Ajustes de Stock");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), java.awt.Color.black)); // NOI18N

        jLblCodigo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblCodigo.setMaximumSize(new java.awt.Dimension(64, 15));
        jLblCodigo.setMinimumSize(new java.awt.Dimension(64, 15));
        jLblCodigo.setPreferredSize(new java.awt.Dimension(64, 15));

        jLblNombre.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLblNombre.setMaximumSize(new java.awt.Dimension(440, 17));
        jLblNombre.setMinimumSize(new java.awt.Dimension(440, 17));
        jLblNombre.setPreferredSize(new java.awt.Dimension(440, 17));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Stock Actual");

        jLblStkActual.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblStkActual.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLblStkActual.setMaximumSize(new java.awt.Dimension(64, 22));
        jLblStkActual.setMinimumSize(new java.awt.Dimension(64, 22));
        jLblStkActual.setPreferredSize(new java.awt.Dimension(64, 22));

        jComboAltaBaja.setBackground(java.awt.Color.red);
        jComboAltaBaja.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Alta", "Baja" }));

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Detalle", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), java.awt.Color.black)); // NOI18N

        jTxtAreaDetalle.setColumns(20);
        jTxtAreaDetalle.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTxtAreaDetalle.setRows(5);
        jScrollPane2.setViewportView(jTxtAreaDetalle);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jFmtAjuste.setMaximumSize(new java.awt.Dimension(75, 22));
        jFmtAjuste.setMinimumSize(new java.awt.Dimension(75, 22));
        jFmtAjuste.setPreferredSize(new java.awt.Dimension(75, 22));

        jLabel1.setText("*");

        jBtnCarga.setBackground(new java.awt.Color(51, 153, 255));
        jBtnCarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/cargar.png"))); // NOI18N
        jBtnCarga.setText("Cargar");
        jBtnCarga.setBorderPainted(false);
        jBtnCarga.setContentAreaFilled(false);
        jBtnCarga.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnCarga.setFocusPainted(false);
        jBtnCarga.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/cargar.png"))); // NOI18N
        jBtnCarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCargaActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel23.setText("(*)Datos Obligatorios");

        jLabel2.setText("* Lote");

        jTxtLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtLoteActionPerformed(evt);
            }
        });

        jLabel4.setText("* PM");

        jLabel5.setText("* Vencimiento");

        jLabel6.setText("*Serie");

        jTxtSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtSerieActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jBtnCarga)
                        .addContainerGap())))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboAltaBaja, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFmtAjuste, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblStkActual, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtLote, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(jTxtPm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jVence, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 122, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel3)
                            .addComponent(jLblStkActual, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jComboAltaBaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFmtAjuste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTxtLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jTxtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jTxtPm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jVence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnCarga)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23))))
        );

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
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jTablaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "GTIN", "Nombre", "Existencia", "id_producto", "pm"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaConsulta.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTablaConsulta.getTableHeader().setReorderingAllowed(false);
        jTablaConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaConsultaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTablaConsulta);
        if (jTablaConsulta.getColumnModel().getColumnCount() > 0) {
            jTablaConsulta.getColumnModel().getColumn(0).setMinWidth(130);
            jTablaConsulta.getColumnModel().getColumn(0).setPreferredWidth(130);
            jTablaConsulta.getColumnModel().getColumn(0).setMaxWidth(130);
            jTablaConsulta.getColumnModel().getColumn(3).setMinWidth(100);
            jTablaConsulta.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTablaConsulta.getColumnModel().getColumn(3).setMaxWidth(100);
            jTablaConsulta.getColumnModel().getColumn(4).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(4).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(5).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        jTablaAjustes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "GTIN", "Nombre", "Lote", "Serie", "vencimiento", "PM", "Cantidad", "Ajuste", "Detalle", "id_producto", "fecha_modificacion", "long_vencimiento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Long.class, java.lang.Long.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, true, true, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaAjustes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTablaAjustes.setComponentPopupMenu(jMenuEliminaModifica);
        jTablaAjustes.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jTablaAjustes);
        if (jTablaAjustes.getColumnModel().getColumnCount() > 0) {
            jTablaAjustes.getColumnModel().getColumn(0).setMinWidth(130);
            jTablaAjustes.getColumnModel().getColumn(0).setPreferredWidth(130);
            jTablaAjustes.getColumnModel().getColumn(0).setMaxWidth(130);
            jTablaAjustes.getColumnModel().getColumn(2).setMinWidth(150);
            jTablaAjustes.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTablaAjustes.getColumnModel().getColumn(2).setMaxWidth(150);
            jTablaAjustes.getColumnModel().getColumn(3).setMinWidth(100);
            jTablaAjustes.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTablaAjustes.getColumnModel().getColumn(3).setMaxWidth(100);
            jTablaAjustes.getColumnModel().getColumn(4).setMinWidth(100);
            jTablaAjustes.getColumnModel().getColumn(4).setPreferredWidth(100);
            jTablaAjustes.getColumnModel().getColumn(4).setMaxWidth(100);
            jTablaAjustes.getColumnModel().getColumn(5).setMinWidth(75);
            jTablaAjustes.getColumnModel().getColumn(5).setPreferredWidth(75);
            jTablaAjustes.getColumnModel().getColumn(5).setMaxWidth(75);
            jTablaAjustes.getColumnModel().getColumn(6).setMinWidth(75);
            jTablaAjustes.getColumnModel().getColumn(6).setPreferredWidth(75);
            jTablaAjustes.getColumnModel().getColumn(6).setMaxWidth(75);
            jTablaAjustes.getColumnModel().getColumn(7).setMinWidth(60);
            jTablaAjustes.getColumnModel().getColumn(7).setPreferredWidth(60);
            jTablaAjustes.getColumnModel().getColumn(7).setMaxWidth(60);
            jTablaAjustes.getColumnModel().getColumn(9).setMinWidth(150);
            jTablaAjustes.getColumnModel().getColumn(9).setPreferredWidth(150);
            jTablaAjustes.getColumnModel().getColumn(9).setMaxWidth(150);
            jTablaAjustes.getColumnModel().getColumn(10).setMinWidth(0);
            jTablaAjustes.getColumnModel().getColumn(10).setPreferredWidth(0);
            jTablaAjustes.getColumnModel().getColumn(10).setMaxWidth(0);
            jTablaAjustes.getColumnModel().getColumn(11).setMinWidth(0);
            jTablaAjustes.getColumnModel().getColumn(11).setPreferredWidth(0);
            jTablaAjustes.getColumnModel().getColumn(11).setMaxWidth(0);
            jTablaAjustes.getColumnModel().getColumn(12).setMinWidth(0);
            jTablaAjustes.getColumnModel().getColumn(12).setPreferredWidth(0);
            jTablaAjustes.getColumnModel().getColumn(12).setMaxWidth(0);
        }

        jBtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/salir.png"))); // NOI18N
        jBtnSalir.setText("Salir");
        jBtnSalir.setBorderPainted(false);
        jBtnSalir.setContentAreaFilled(false);
        jBtnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnSalir.setFocusPainted(false);
        jBtnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jBtnSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/salir.png"))); // NOI18N
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
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jLabel7.setText("Zona");

        jComboZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboZonaActionPerformed(evt);
            }
        });

        jButton1.setText("Consultar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jBtnGuardar)
                                        .addGap(312, 312, 312)
                                        .addComponent(jBtnSalir))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 857, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 857, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnSalir))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTablaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsultaMouseClicked
        limpiaPanel();
        
        int indiceConsulta = jTablaConsulta.convertRowIndexToModel (jTablaConsulta.getSelectedRow());
        this.id_producto = Integer.parseInt(modeloConsulta.getValueAt(indiceConsulta, Utiles.getColumnByName(jTablaConsulta, "id_producto")).toString());
        this.jLblCodigo.setText(modeloConsulta.getValueAt(indiceConsulta, Utiles.getColumnByName(jTablaConsulta, "codigo")).toString());
        this.jLblNombre.setText(modeloConsulta.getValueAt(indiceConsulta, Utiles.getColumnByName(jTablaConsulta, "nombre")).toString());
        String actual = modeloConsulta.getValueAt(indiceConsulta, Utiles.getColumnByName(jTablaConsulta, "existencia")).toString();
        this.jLblStkActual.setText(actual);
        this.jTxtPm.setText(modeloConsulta.getValueAt(indiceConsulta, Utiles.getColumnByName(jTablaConsulta, "pm")).toString());
        this.jFmtAjuste.requestFocus();
    }//GEN-LAST:event_jTablaConsultaMouseClicked

    private void jMenuItemModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemModificaActionPerformed
        try {
            if(jTablaAjustes.getSelectedRow() < 0){
                JOptionPane.showMessageDialog(this,"No ha seleccionado ninguna fila", "Información",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            indiceAjustes = jTablaAjustes.convertRowIndexToModel(jTablaAjustes.getSelectedRow());
            
            this.jLblCodigo.setText(modeloAjustes.getValueAt(indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "codigo")).toString());
            this.jLblNombre.setText(modeloAjustes.getValueAt(indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "nombre")).toString());
            this.jLblStkActual.setText(String.format("%.2f", 0.00));
            this.jComboAltaBaja.setSelectedItem(modeloAjustes.getValueAt(indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "cantidad")).toString());
            //this.jFmtAjuste.setValue(Integer.parseInt(modeloAjustes.getValueAt(indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "fecha")).toString()));
            this.jTxtLote.setText(modeloAjustes.getValueAt(indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "lote")).toString());
            this.jTxtPm.setText(modeloAjustes.getValueAt(indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "pm")).toString());
            this.jVence.setDate(new Date(Long.parseLong(modeloAjustes.getValueAt(indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "vencimiento")).toString())));
            this.jTxtAreaDetalle.setText(modeloAjustes.getValueAt(indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "detalle")).toString());
        } catch (HeadlessException | NumberFormatException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }//GEN-LAST:event_jMenuItemModificaActionPerformed

    private void jMenuItemEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEliminaActionPerformed
        try{
            if(jTablaAjustes.getSelectedRow() < 0){
                JOptionPane.showMessageDialog(this,"No ha seleccionado ninguna fila", "Información",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            indiceAjustes = jTablaAjustes.convertRowIndexToModel(jTablaAjustes.getSelectedRow());
            
            modeloAjustes.removeRow(indiceAjustes);
            limpiaPanel();
        } catch (HeadlessException ex){
            Utiles.enviaError(this.empresa,ex);
        }  
    }//GEN-LAST:event_jMenuItemEliminaActionPerformed

    private void jBtnCargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCargaActionPerformed
        if (validaAjuste().isEmpty()){
            try{
                HashMap<Object,Object> map = new HashMap<>();
                map.put("codigo",this.jLblCodigo.getText());
                map.put("gtin", modeloConsulta.getValueAt(jTablaConsulta.getSelectedRow(), Utiles.getColumnByName(jTablaAjustes, "gtin")).toString());
                map.put("nombre", this.jLblNombre.getText());
                map.put("lote", this.jTxtLote.getText());
                map.put("serie", this.jTxtSerie.getText());
                map.put("vencimiento", new SimpleDateFormat("dd/MM/yyyy").format(this.jVence.getDate()));
                map.put("pm", this.jTxtPm.getText());
                map.put("cantidad", Integer.parseInt(this.jFmtAjuste.getValue().toString()));
                map.put("ajuste", this.jComboAltaBaja.getSelectedItem().toString());
                map.put("detalle", this.jTxtAreaDetalle.getText());
                map.put("id_producto", this.id_producto);
                Date f = new Date();
                map.put("fecha_modificacion", new SimpleDateFormat("dd/MM/yyyy").format(f));
                map.put("long_vencimiento", f.getTime());
                modeloAjustes.addRow(Utiles.fillTableObject(modeloAjustes, map));
                /*
                String codigo = this.jLblCodigo.getText();
                String gtin = modeloConsulta.getValueAt(jTablaConsulta.getSelectedRow(), Utiles.getColumnByName(jTablaAjustes, "gtin")).toString();
                String nombre = this.jLblNombre.getText();
                String altaBaja = this.jComboAltaBaja.getSelectedItem().toString();
                int cantidad = Integer.parseInt(this.jFmtAjuste.getValue().toString());
                Date f = new Date();
                String fecha_modificacion = new SimpleDateFormat("dd/MM/yyyy").format(f);
                String lote = this.jTxtLote.getText();
                String pm = this.jTxtPm.getText();
                String serie = this.jTxtSerie.getText();
                String vencimiento = new SimpleDateFormat("dd/MM/yyyy").format(this.jVence.getDate());
                String detalle = this.jTxtAreaDetalle.getText();
                Long long_vencimiento = f.getTime();
                */
                //if (indiceAjustes < 0){
                    //Object [] nuevo = {codigo, gtin, nombre, lote, serie, vencimiento, pm, cantidad, altaBaja, detalle, this.id_producto, fecha_modificacion, long_vencimiento };
                    //modeloAjustes.addRow(nuevo);
                
                    
                /*} else {
                    modeloAjustes.setValueAt(lote, indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "lote"));
                    modeloAjustes.setValueAt(serie, indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "serie"));
                    modeloAjustes.setValueAt(pm, indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "pm"));
                    modeloAjustes.setValueAt(altaBaja, indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "altaBaja"));
                    modeloAjustes.setValueAt(cantidad, indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "cantidad"));
                    modeloAjustes.setValueAt(fecha_modificacion, indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "fecha_modificacion"));
                    modeloAjustes.setValueAt(detalle, indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "detalle"));
                    modeloAjustes.setValueAt(id_producto, indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "fechaLong"));
                    modeloAjustes.setValueAt(vencimiento, indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "vencimiento"));
                    modeloAjustes.setValueAt(long_vencimiento, indiceAjustes, Utiles.getColumnByName(jTablaAjustes, "long_vencimiento"));
                }*/
                limpiaPanel();
            } catch (NumberFormatException ex){
                Utiles.enviaError(this.empresa,ex);
            }
        }else{
            JOptionPane.showMessageDialog(this,validaAjuste(), "Atención",JOptionPane.INFORMATION_MESSAGE);
        }        
    }//GEN-LAST:event_jBtnCargaActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        if (rsConsulta != null){
            try{
                rsConsulta.close();
            }catch (Exception ex){
                Utiles.enviaError(empresa, ex);
            }
        }
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (jTablaAjustes.isEditing())
            jTablaAjustes.getCellEditor().stopCellEditing();
        if (jTablaConsulta.isEditing())
            jTablaConsulta.getCellEditor().stopCellEditing();         
        if (validaObligatorios().isEmpty()){
            ArrayList parametros = new ArrayList();
            
            int id_zona = ((ZonaData)zonaArray.get(this.jComboZona.getSelectedIndex())).getId_zona();
            Long fecha = new Date().getTime();
            
            parametros.add(id_zona);
            parametros.add(fecha);
            parametros.add(this.id_empresa);
            parametros.add(this.usuario);
            
            conexion.procAlmacenado("insertAjusteStock", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
            
            parametros.clear();
            parametros.add(this.id_empresa);
            
            for (int i = 0; i < modeloAjustes.getRowCount(); i++){
                parametros.clear();
                /*                
                (id_producto INT,
                cantidad INT,
                dc VARCHAR(1),
                lote VARCHAR(45),
                serie VARCHAR(255),
                pm VARCHAR(45),
                vencimiento DATE,
                observaciones TEXT,
                id_zona INT,
                id_empresa INT)
                */
                parametros.add(Integer.parseInt(modeloAjustes.getValueAt(i, Utiles.getColumnByName(jTablaAjustes, "id_producto")).toString()));
                parametros.add(Integer.parseInt(modeloAjustes.getValueAt(i, Utiles.getColumnByName(jTablaAjustes, "cantidad")).toString()));
                parametros.add((modeloAjustes.getValueAt(i, Utiles.getColumnByName(jTablaAjustes, "ajuste")).toString().equals("Alta") ? "D" : "C"));
                parametros.add(modeloAjustes.getValueAt(i, Utiles.getColumnByName(jTablaAjustes, "lote")).toString());
                parametros.add(modeloAjustes.getValueAt(i, Utiles.getColumnByName(jTablaAjustes, "serie")).toString());
                parametros.add(modeloAjustes.getValueAt(i, Utiles.getColumnByName(jTablaAjustes, "pm")).toString());
                parametros.add(Long.parseLong(modeloAjustes.getValueAt(i, Utiles.getColumnByName(jTablaAjustes, "long_vencimiento")).toString()));
                String observaciones = "Ajuste de stock - " + modeloAjustes.getValueAt(i, Utiles.getColumnByName(jTablaAjustes, "detalle")).toString();
                parametros.add(observaciones);
                parametros.add(id_zona);
                parametros.add(this.id_empresa);

                conexion.procAlmacenado("insertAjusteStockDetalle", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
            }
            limpia();
        }else{
            JOptionPane.showMessageDialog(this, validaObligatorios(), "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        this.modeloAjustes.getDataVector().removeAllElements();
        this.modeloAjustes.fireTableDataChanged();
    }//GEN-LAST:event_jComboZonaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (this.jComboZona.getSelectedIndex() >= 0) {
            consulta();
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione una zona", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTxtSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtSerieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtSerieActionPerformed

    private void jTxtLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtLoteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtLoteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnCarga;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboAltaBaja;
    private javax.swing.JComboBox jComboZona;
    private javax.swing.JFormattedTextField jFmtAjuste;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLblCodigo;
    private javax.swing.JLabel jLblNombre;
    private javax.swing.JLabel jLblStkActual;
    private javax.swing.JPopupMenu jMenuEliminaModifica;
    private javax.swing.JMenuItem jMenuItemElimina;
    private javax.swing.JMenuItem jMenuItemModifica;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTablaAjustes;
    private javax.swing.JTable jTablaConsulta;
    private javax.swing.JTextArea jTxtAreaDetalle;
    private javax.swing.JTextField jTxtBusca;
    private javax.swing.JTextField jTxtLote;
    private javax.swing.JTextField jTxtPm;
    private javax.swing.JTextField jTxtSerie;
    private com.toedter.calendar.JDateChooser jVence;
    // End of variables declaration//GEN-END:variables
}
