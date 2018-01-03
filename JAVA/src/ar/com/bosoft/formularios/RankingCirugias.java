/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ProductoFactCRUD;
import ar.com.bosoft.crud.ProfesionalCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.ProductoFactData;
import ar.com.bosoft.data.ProfesionalData;
import ar.com.bosoft.data.ZonaData;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class RankingCirugias extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, copias;
    String empresa, todos, nombreArchivo, impresora;
    ProfesionalCRUD profesionalCRUD;
    ProductoFactCRUD productoFactCRUD;
    ZonaCRUD zonaCRUD;
    
    ArrayList zonaArray, profesionalArray, id_profesionalArray, productoFactArray;
    
    DefaultTableModel modelo;
    TableRowSorter sorter;

    ResultSet rsConsulta;
    
    SeleccionImp seleccionImp;
    
    /**
     * Creates new form RankingCirugias
     * @param conexion
     * @param id_empresa
     * @param empresa
     */
    public RankingCirugias(Conexion conexion, int id_empresa, String empresa) {
        initComponents();
        
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.todos = "-- TODOS --";
        
        this.profesionalCRUD = new ProfesionalCRUD(conexion, id_empresa, empresa);
        this.productoFactCRUD = new ProductoFactCRUD(conexion, empresa);
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        
        this.zonaArray = new ArrayList();
        this.profesionalArray = new ArrayList();
        this.id_profesionalArray = new ArrayList();
        
        this.seleccionImp = new SeleccionImp(null, true);
        
        modelo = (DefaultTableModel) jTabla.getModel();
        jTabla.setModel(modelo);
        jTabla.setRowSorter (new TableRowSorter(modelo));
        sorter = new TableRowSorter(modelo);
        
        llenaComboZona();
        llenaComboProducto();
        consultaProfesional();
        
        limpia();
        zonaUsuario();
        
        this.jComboZonaActionPerformed(null);
        
        setJTexFieldChanged(jTxtBusca);
    }

    private void zonaUsuario(){
        int id_zonaUsuario = Principal.usuarioData.getId_zonaSistema();
        if (id_zonaUsuario > 0) {
            if (this.zonaArray != null){
                Iterator it = zonaArray.iterator();
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
    
    private void llenaComboZona(){
        this.jComboZona.addItem(this.todos);        
        this.zonaArray = zonaCRUD.consulta(true);
        Iterator i = zonaArray.iterator();
        while (i.hasNext()){
            ZonaData tmp = (ZonaData) i.next();
            this.jComboZona.addItem(tmp.getNombre());
        }
    }
    
    private void consultaProfesional(){
        this.profesionalArray = profesionalCRUD.consulta(true);
    }
    
    private void llenaComboProfesional(int id_zona){
        this.jComboProfesional.removeAllItems();
        this.id_profesionalArray.clear();
        this.jComboProfesional.addItem(todos);
        
        Iterator i = profesionalArray.iterator();
        while (i.hasNext()) {
            ProfesionalData tmp = (ProfesionalData) i.next();
            boolean incluye = false;
            if (id_zona == 0){
                incluye = true;
            }else{
                if (tmp.getId_zona() == id_zona){
                    incluye = true;
                }
            }
            
            if (incluye) {
                this.id_profesionalArray.add(tmp.getId_profesional());
                this.jComboProfesional.addItem(tmp.getNombre());
            }
        }
        this.jComboProfesional.setSelectedIndex(0);
    }
    
    private void llenaComboProducto(){
        this.jComboProducto.removeAllItems();
        this.id_profesionalArray.clear();
        this.jComboProducto.addItem(todos);
        this.productoFactArray = productoFactCRUD.consulta(true, 2);
        
        Iterator i = productoFactArray.iterator();
        while (i.hasNext()){
            ProductoFactData tmp = (ProductoFactData) i.next();
            this.jComboProducto.addItem(tmp.getNombre());
        }
        
        this.jComboProducto.setSelectedItem(todos);
    }
    
    private void limpia(){
        this.jDesde.setDate(null);
        this.jHasta.setDate(null);
        
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedItem(todos);
        }
        
        this.jRadioProducto.setSelected(true);
        this.jRadioProductoActionPerformed(null);
        
        this.nombreArchivo = "";
        this.impresora = "";
        this.copias = 0;
    }
    
    private void imprimir (int salida){
        Map param = new HashMap();
        param.put("empresa", this.empresa);
        param.put("desde", this.jDesde.getDate());
        param.put("hasta", this.jHasta.getDate());
        param.put("zona", this.jComboZona.getSelectedItem().toString());
        param.put("por", this.jRadioProducto.isSelected() ? 1 : 2);
        param.put("profesionalProducto", this.jRadioProducto.isSelected() ? this.jComboProducto.getSelectedItem().toString() : this.jComboProfesional.getSelectedItem().toString());
        
        Reporte reporte = new Reporte();
        reporte.startReport("RankingCirugias", param, new JRTableModelDataSource(modelo), salida, nombreArchivo, impresora, copias);
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
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupo = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jDesde = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jDesde.getJCalendar().setTodayButtonVisible(true); 
        this.jDesde.getJCalendar().setTodayButtonText("Hoy"); 
        this.jDesde.getJCalendar().setWeekOfYearVisible(false);
        jLabel2 = new javax.swing.JLabel();
        jHasta = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jHasta.getJCalendar().setTodayButtonVisible(true); 
        this.jHasta.getJCalendar().setTodayButtonText("Hoy"); 
        this.jHasta.getJCalendar().setWeekOfYearVisible(false);
        jComboProfesional = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jComboProducto = new javax.swing.JComboBox();
        jRadioProducto = new javax.swing.JRadioButton();
        jRadioProveedor = new javax.swing.JRadioButton();
        jBtnFiltra = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnXls = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla = new javax.swing.JTable();
        jBtnSalir = new javax.swing.JButton();

        setTitle("Ranking de cirugías");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jLabel1.setText("Desde");

        jLabel2.setText("Hasta");

        jComboProfesional.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProfesionalMouseClicked(evt);
            }
        });

        jLabel6.setText("Zona");

        jComboZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboZonaActionPerformed(evt);
            }
        });

        jComboProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProductoMouseClicked(evt);
            }
        });

        grupo.add(jRadioProducto);
        jRadioProducto.setSelected(true);
        jRadioProducto.setText("Producto");
        jRadioProducto.setContentAreaFilled(false);
        jRadioProducto.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jRadioProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioProductoActionPerformed(evt);
            }
        });

        grupo.add(jRadioProveedor);
        jRadioProveedor.setText("Proveedor");
        jRadioProveedor.setContentAreaFilled(false);
        jRadioProveedor.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jRadioProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioProveedorActionPerformed(evt);
            }
        });

        jBtnFiltra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/filtrar.png"))); // NOI18N
        jBtnFiltra.setText("Filtrar");
        jBtnFiltra.setBorderPainted(false);
        jBtnFiltra.setContentAreaFilled(false);
        jBtnFiltra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnFiltra.setFocusPainted(false);
        jBtnFiltra.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/filtrar.png"))); // NOI18N
        jBtnFiltra.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/filtrar.png"))); // NOI18N
        jBtnFiltra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnFiltraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioProveedor, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jRadioProducto, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboProfesional, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboZona, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboProducto, javax.swing.GroupLayout.Alignment.LEADING, 0, 216, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnFiltra)))
                .addGap(3, 3, 3))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioProducto))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboProfesional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioProveedor))
                .addGap(11, 11, 11))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBtnFiltra)
                .addContainerGap())
        );

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

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setFocusable(false);

        jTxtBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaFocusGained(evt);
            }
        });

        jLabel11.setText("Buscar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)))
        );

        jTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Profesional", "cantidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
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
        jTabla.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTabla);
        if (jTabla.getColumnModel().getColumnCount() > 0) {
            jTabla.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(0).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(3).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(3).setMaxWidth(150);
        }

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 741, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(248, 248, 248)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnSalir))))
                .addGap(3, 3, 3))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnSalir)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 3, 3))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboProfesionalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProfesionalMouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Profesional");
            Iterator it = this.profesionalArray.iterator();
            while (it.hasNext()) {
                ProfesionalData tmp = (ProfesionalData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboProfesional.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboProfesionalMouseClicked

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        int id_zona = 0;
        if (this.jComboZona.getSelectedIndex() > 0){
            ZonaData tmp = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
            id_zona = tmp.getId_zona();
        }

        llenaComboProfesional(id_zona);
    }//GEN-LAST:event_jComboZonaActionPerformed

    private void jBtnScrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScrActionPerformed
        imprimir(0);
    }//GEN-LAST:event_jBtnScrActionPerformed

    private void jBtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImpActionPerformed
        seleccionImp.setVisible(true);
        if (seleccionImp.getSino()){
            this.impresora = seleccionImp.getImpresora();
            this.copias = seleccionImp.getCopias();
            imprimir(1);
        }
    }//GEN-LAST:event_jBtnImpActionPerformed

    private void jBtnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPdfActionPerformed
        //Crear un objeto FileChooser
        JFileChooser fc = new JFileChooser();
        //Mostrar la ventana para abrir archivo y recoger la respuesta
        int respuesta = fc.showSaveDialog(null);
        //Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION){
            //Crear un objeto File con el archivo elegido
            File archivoElegido = fc.getSelectedFile();
            try {
                this.nombreArchivo = archivoElegido.getCanonicalPath() + ".pdf";
                imprimir(2);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jBtnPdfActionPerformed

    private void jBtnXlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnXlsActionPerformed
        //Crear un objeto FileChooser
        JFileChooser fc = new JFileChooser();
        //Mostrar la ventana para abrir archivo y recoger la respuesta
        int respuesta = fc.showSaveDialog(null);
        //Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION){
            //Crear un objeto File con el archivo elegido
            File archivoElegido = fc.getSelectedFile();
            try {
                this.nombreArchivo = archivoElegido.getCanonicalPath() + ".xlsx";
                imprimir(3);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jBtnXlsActionPerformed

    private void jRadioProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioProveedorActionPerformed
        this.jRadioProductoActionPerformed(evt);
    }//GEN-LAST:event_jRadioProveedorActionPerformed

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        try {
            modelo.getDataVector().removeAllElements();
            modelo.fireTableDataChanged();

            Long desde = this.jDesde.getDate() == null ? (long) 0 : this.jDesde.getDate().getTime();
            Long hasta = this.jHasta.getDate() == null ? (long) 0 : this.jHasta.getDate().getTime();

            int id_zona = 0;
            if (this.jComboZona.getSelectedIndex() > 0){
                ZonaData z = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
                id_zona = z.getId_zona();
            }
            
            int id_productoFact = 0;
            if (this.jComboProducto.getSelectedIndex() > 0){
                ProductoFactData p = (ProductoFactData) productoFactArray.get(this.jComboProducto.getSelectedIndex() - 1);
                id_productoFact = p.getId_productoFact();
            }

            int id_profesional = 0;
            if(this.jComboProfesional.getSelectedIndex() > 0){
                id_profesional = (int)id_profesionalArray.get(this.jComboProfesional.getSelectedIndex() - 1);
            }

            ArrayList parametros = new ArrayList();
            parametros.add(desde);
            parametros.add(hasta);
            parametros.add(this.jRadioProducto.isSelected() ? 1 : 2);
            parametros.add(id_productoFact);
            parametros.add(id_profesional);
            parametros.add(id_zona);
            parametros.add(this.id_empresa);

            rsConsulta = conexion.procAlmacenado("consultaRankingCirugias", parametros,
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

            if (rsConsulta.first()){
                rsConsulta.beforeFirst();
                while (rsConsulta.next()){
                    String codigo = rsConsulta.getString("codigo");
                    String producto = rsConsulta.getString("producto");
                    String profesional = rsConsulta.getString("profesional");
                    int cantidad = rsConsulta.getInt("cantidad");

                    Object[] fila = {codigo, producto, profesional, cantidad};

                    modelo.addRow(fila);
                }
            }else{
                JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jTxtBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaFocusGained
        this.jTxtBusca.select(0, this.jTxtBusca.getText().length());
    }//GEN-LAST:event_jTxtBuscaFocusGained

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jComboProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProductoMouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Producto");
            Iterator it = this.productoFactArray.iterator();
            while (it.hasNext()) {
                ProductoFactData tmp = (ProductoFactData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboProducto.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboProductoMouseClicked

    private void jRadioProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioProductoActionPerformed
        this.jComboProducto.setSelectedItem(this.todos);
        this.jComboProfesional.setSelectedItem(this.todos);
        this.jComboProducto.setEnabled(this.jRadioProducto.isSelected());
        this.jComboProfesional.setEnabled(!this.jRadioProducto.isSelected());
    }//GEN-LAST:event_jRadioProductoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup grupo;
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jBtnXls;
    private javax.swing.JComboBox jComboProducto;
    private javax.swing.JComboBox jComboProfesional;
    private javax.swing.JComboBox jComboZona;
    private com.toedter.calendar.JDateChooser jDesde;
    private com.toedter.calendar.JDateChooser jHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioProducto;
    private javax.swing.JRadioButton jRadioProveedor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTabla;
    private javax.swing.JTextField jTxtBusca;
    // End of variables declaration//GEN-END:variables
}
