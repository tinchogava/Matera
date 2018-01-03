/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.ActiveDatabase;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ForecastGrupoCRUD;
import ar.com.bosoft.crud.VendedorCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.EntidadData;
import ar.com.bosoft.data.ForecastGrupoData;
import ar.com.bosoft.data.VendedorData;
import ar.com.bosoft.data.ZonaData;
import java.awt.Rectangle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import matera.jooq.tables.records.ForecastRecord;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class ForecastCarga extends javax.swing.JInternalFrame {
    private final Conexion conexion;
    private final int id_empresa;
    private final String empresa;
    private final String usuario;
    private final String todos;
    
    ZonaCRUD zonaCRUD;
    VendedorCRUD vendedorCRUD;
    ForecastGrupoCRUD forecastGrupoCRUD;
    
    ArrayList zonaArray, entidadArray, forecastGrupoArray, vendedorArray;
    ArrayList id_entidadArray, id_vendedorArray;
    ResultSet rsConsulta;
    
    DefaultTableModel modeloDisponible, modeloAsignado;
    TableRowSorter sorter;
    
    /**
     * Creates new form ForecastCarga
     * @param conexion
     * @param id_empresa
     * @param empresa
     * @param usuario
     */
    public ForecastCarga(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        this.todos = "-- TODAS --";
        
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.vendedorCRUD = new VendedorCRUD(conexion, id_empresa, empresa);
        this.forecastGrupoCRUD = new ForecastGrupoCRUD(conexion, id_empresa, empresa);
        this.entidadArray = new ArrayList();
        this.id_entidadArray = new ArrayList();
        this.id_vendedorArray = new ArrayList();
        
        initComponents();
        
        modeloDisponible = (DefaultTableModel) this.jTablaDisponibles.getModel();
        this.jTablaDisponibles.setModel(modeloDisponible);
        sorter = new TableRowSorter(modeloDisponible);
        
        modeloAsignado = (DefaultTableModel) this.jTablaAsignados.getModel();
        this.jTablaAsignados.setModel(modeloAsignado);        
        this.jTablaAsignados.setAutoCreateRowSorter(true);
        
        llenaComboZona();
        llenaComboForecastGrupo();
        consultaEntidad();
        consultaVendedor();
        
        
        limpia();
        zonaUsuario();
        setJTexFieldChanged(jTxtBusca);
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
    
    private void consultaEntidad(){
        try{
            ArrayList parametros = new ArrayList();
            parametros.add(this.id_empresa);

            rsConsulta = conexion.procAlmacenado("consultaForecastEntidades", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsConsulta.beforeFirst();
            while (rsConsulta.next()) {
                int id_entidad = rsConsulta.getInt("id_entidad");
                String nombre = rsConsulta.getString("nombre");
                int id_zona = rsConsulta.getInt("id_zona");
                
                EntidadData e = new EntidadData();
                e.setId_entidad(id_entidad);
                e.setNombre(nombre);
                e.setId_zona(id_zona);
                
                entidadArray.add(e);                
            }
        }catch (Exception ex){
            Utiles.enviaError(empresa, ex);
        }
        
    }
    
    private void consultaVendedor(){
        this.vendedorArray = vendedorCRUD.consulta(true);
    }
    
    private void llenaComboEntidad(int id_zona){
        this.jComboEntidad.removeAllItems();
        this.id_entidadArray.clear();
        
        Iterator i = entidadArray.iterator();
        while (i.hasNext()){
            EntidadData tmp = (EntidadData) i.next();
            if (id_zona == 0) {
                this.jComboEntidad.addItem(tmp.getNombre());
                this.id_entidadArray.add(tmp.getId_entidad());
            }else if (tmp.getId_zona() == id_zona) {
                this.jComboEntidad.addItem(tmp.getNombre());
                this.id_entidadArray.add(tmp.getId_entidad());
            }
            
        }
        this.jComboEntidad.setSelectedIndex(-1);
    }
    
    private void llenaComboVendedor(int id_zona){
        this.jComboVendedor.removeAllItems();
        this.id_vendedorArray.clear();
        
        Iterator i = vendedorArray.iterator();
        while (i.hasNext()){
            VendedorData tmp = (VendedorData) i.next();
            if (tmp.getId_especialidad() == 13){
                if (id_zona == 0) {
                    this.jComboVendedor.addItem(tmp.getNombre());
                    this.id_vendedorArray.add(tmp.getId_vendedor());
                }else if (tmp.getId_zona() == id_zona) {
                    this.jComboVendedor.addItem(tmp.getNombre());
                    this.id_vendedorArray.add(tmp.getId_vendedor());
                }
            } 
        }
        this.jComboVendedor.setSelectedIndex(-1);
    }

    private void llenaComboForecastGrupo(){
        this.forecastGrupoArray = forecastGrupoCRUD.consulta(true);
        Iterator i = forecastGrupoArray.iterator();
        while (i.hasNext()){
            ForecastGrupoData tmp = (ForecastGrupoData) i.next();
            this.jComboForecastGrupo.addItem(tmp.getNombre());
        }
    }
    
    private void limpia(){
        this.jAño.setYear(Calendar.getInstance().get(Calendar.YEAR));
        if (this.jComboZona.isEnabled()) {
            this.jComboZona.setSelectedIndex(0);
        }
        this.jComboVendedor.setSelectedIndex(-1);
        this.jComboEntidad.setSelectedIndex(-1);
        
        this.jComboForecastGrupo.setSelectedIndex(-1);
        
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
    
    private void borra(){
        this.jTxtBusca.setText("");
        modeloDisponible.getDataVector().removeAllElements();
        modeloDisponible.fireTableDataChanged();
        modeloAsignado.getDataVector().removeAllElements();
        modeloAsignado.fireTableDataChanged();
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
          jTablaDisponibles.setRowSorter(sorter);
        }
    }

    private boolean validaObligatorios(){
        return this.jAño.getYear() > 0 &&
                //this.jComboEntidad.getSelectedIndex() >= 0 &&
                this.jComboVendedor.getSelectedIndex() >= 0 &&
                this.jComboForecastGrupo.getSelectedIndex() >= 0;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jAño = new com.toedter.calendar.JYearChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jComboVendedor = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jComboEntidad = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaDisponibles = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollAsignadas = new javax.swing.JScrollPane();
        jTablaAsignados = new ar.com.bosoft.RenderTablas.RXTable();
        jLabel10 = new javax.swing.JLabel();
        jComboForecastGrupo = new javax.swing.JComboBox();
        jBtnBusca = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();

        setTitle("Forecast - Carga");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jLabel1.setText("Año");

        jLabel6.setText("Zona");

        jComboZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboZonaActionPerformed(evt);
            }
        });

        jLabel7.setText("Vendedor");

        jComboVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboVendedorActionPerformed(evt);
            }
        });

        jLabel3.setText("Entidad");

        jComboEntidad.setEnabled(false);
        jComboEntidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboEntidadMouseClicked(evt);
            }
        });
        jComboEntidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboEntidadActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Productos"));

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Disponibles"));

        jLabel22.setText("Buscar");

        jTablaDisponibles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "id_productoFact"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class
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
        jTablaDisponibles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaDisponiblesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaDisponibles);
        if (jTablaDisponibles.getColumnModel().getColumnCount() > 0) {
            jTablaDisponibles.getColumnModel().getColumn(1).setMinWidth(0);
            jTablaDisponibles.getColumnModel().getColumn(1).setPreferredWidth(0);
            jTablaDisponibles.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtBusca))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Asignados"));

        jTablaAsignados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Cant.", "id_productoFact"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollAsignadas.setViewportView(jTablaAsignados);
        if (jTablaAsignados.getColumnModel().getColumnCount() > 0) {
            jTablaAsignados.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTablaAsignados.getColumnModel().getColumn(1).setMaxWidth(75);
            jTablaAsignados.getColumnModel().getColumn(2).setMinWidth(0);
            jTablaAsignados.getColumnModel().getColumn(2).setPreferredWidth(0);
            jTablaAsignados.getColumnModel().getColumn(2).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollAsignadas, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollAsignadas, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel10.setText("Grupo");

        jComboForecastGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboForecastGrupoActionPerformed(evt);
            }
        });

        jBtnBusca.setText("Busca");
        jBtnBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboForecastGrupo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnBusca))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboForecastGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jBtnBusca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(3, 3, 3))
        );

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

        jBtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/guardar.png"))); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.setBorderPainted(false);
        jBtnGuardar.setContentAreaFilled(false);
        jBtnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnGuardar.setFocusPainted(false);
        jBtnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Incluir Entidad");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboZona, 0, 236, Short.MAX_VALUE)
                            .addComponent(jComboVendedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboEntidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox1))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnGuardar)
                .addGap(253, 253, 253)
                .addComponent(jBtnSalir)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnSalir)
                    .addComponent(jBtnGuardar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        int id_zona = 0;
        if (this.jComboZona.getSelectedIndex() > 0){
            ZonaData tmp = (ZonaData) zonaArray.get(this.jComboZona.getSelectedIndex() - 1);
            id_zona = tmp.getId_zona();
        }

        try {
            llenaComboVendedor(id_zona);
            llenaComboEntidad(id_zona);
        } catch (Exception e) {
        }
            
        this.borra();
    }//GEN-LAST:event_jComboZonaActionPerformed

    private void jTablaDisponiblesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaDisponiblesMouseClicked
        if (evt.getClickCount() == 2) {
            int fila = jTablaDisponibles.convertRowIndexToModel(jTablaDisponibles.getSelectedRow());

            String nombre = modeloDisponible.getValueAt(fila, 0).toString();
            Double cantidad = 1.0;
            int id_entidad = (int) modeloDisponible.getValueAt(fila, 1);

            Object[] nuevo = {nombre, cantidad, id_entidad};
            if (Utiles.existeEnModelo(modeloAsignado, 0, nombre)){
                JOptionPane.showMessageDialog(this, "Este producto ya ha sido seleccionado", "Atención", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            modeloAsignado.addRow(nuevo);
            Rectangle r = this.jTablaAsignados.getCellRect(modeloAsignado.getRowCount() - 1, 0, true);
            this.jScrollAsignadas.getViewport().scrollRectToVisible(r);
            jTablaDisponibles.clearSelection();
        }
    }//GEN-LAST:event_jTablaDisponiblesMouseClicked

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        if (rsConsulta != null) {
            try {
                rsConsulta.close();
            } catch (SQLException ex) {}
        }
        
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jComboForecastGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboForecastGrupoActionPerformed
        this.borra();
    }//GEN-LAST:event_jComboForecastGrupoActionPerformed

    private void jComboVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboVendedorActionPerformed
        this.borra();
    }//GEN-LAST:event_jComboVendedorActionPerformed

    private void jComboEntidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboEntidadActionPerformed
        this.borra();
    }//GEN-LAST:event_jComboEntidadActionPerformed

    private void jBtnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscaActionPerformed
        int id_entidad = 0;
        if (this.jAño.getYear() > 0 &&
                //this.jComboEntidad.getSelectedIndex() >= 0 &&
                this.jComboVendedor.getSelectedIndex() >= 0 &&
                this.jComboForecastGrupo.getSelectedIndex() >= 0) {
            
            try {
                this.borra();
                
                ArrayList parametros = new ArrayList();
                int year = this.jAño.getYear();
                int id_vendedor = (int) this.id_vendedorArray.get(this.jComboVendedor.getSelectedIndex());
                if(this.jComboEntidad.getSelectedIndex() >= 0){
                    id_entidad = (int) this.id_entidadArray.get(this.jComboEntidad.getSelectedIndex());
                }
                ForecastGrupoData f = (ForecastGrupoData) this.forecastGrupoArray.get(this.jComboForecastGrupo.getSelectedIndex());
                int id_forecastGrupo = f.getId_forecastGrupo();
                
                parametros.add(year);
                parametros.add(id_vendedor);
                parametros.add(id_entidad);
                parametros.add(id_forecastGrupo);
                
                rsConsulta = conexion.procAlmacenado("consultaForecastAsignados", parametros,
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                
                rsConsulta.beforeFirst();
                
                while (rsConsulta.next()) {
                    String nombre = rsConsulta.getString("nombre");
                    int id_productoFact = rsConsulta.getInt("id_productoFact");
                    double cantidad = rsConsulta.getDouble("cantidad");
                    
                    Object[] filaDisponible = {nombre, id_productoFact};
                    modeloDisponible.addRow(filaDisponible);
                    
                    if (cantidad > 0) {
                        Object[] filaAsignado = {nombre, cantidad, id_productoFact};
                        modeloAsignado.addRow(filaAsignado);
                    }
                }
                
            } catch (SQLException ex) {
                Utiles.enviaError(empresa, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione todos los datos", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnBuscaActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        int id_entidad = 0;
        if (jTablaAsignados.isEditing())
            jTablaAsignados.getCellEditor().stopCellEditing(); 
        
        if (validaObligatorios()){
            try {
               ArrayList parametros = new ArrayList();
                int anio = this.jAño.getYear();
                int id_vendedor = (int) this.id_vendedorArray.get(this.jComboVendedor.getSelectedIndex());
                if(this.jComboEntidad.getSelectedIndex() > 0){
                    id_entidad = (int) this.id_entidadArray.get(this.jComboEntidad.getSelectedIndex());
                }
                ForecastGrupoData f = (ForecastGrupoData) this.forecastGrupoArray.get(this.jComboForecastGrupo.getSelectedIndex());
                int id_forecastGrupo = f.getId_forecastGrupo();
                
                parametros.add(anio);
                parametros.add(id_vendedor);
                parametros.add(id_entidad);
                parametros.add(id_forecastGrupo);
                parametros.add(Principal.usuarioData.getNombre());
                parametros.add(Principal.equipo);


                conexion.procAlmacenado("deleteForecast", parametros,
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                for (int i = 0; i < modeloAsignado.getRowCount(); i++) {
                    parametros.clear();
                    int id_productoFact = (int) modeloAsignado.getValueAt(i, 2);
                    Double cantidad = (Double) modeloAsignado.getValueAt(i, 1);
                    if (cantidad > 0) {
                        ForecastRecord forecastRecord = new ForecastRecord(null,
                        anio,
                        id_vendedor,
                        id_entidad,
                        id_productoFact,
                        cantidad,
                        this.usuario,
                        new Timestamp(new java.util.Date().getTime()));
                        forecastRecord.attach(ActiveDatabase.getDSLContext().configuration());
                        forecastRecord.store();
                    }
                }
                Utiles.showMessage("Guardado correctamente.");
            } catch (Exception e) {
                Utiles.showErrorMessage(e);
            }
            //this.limpia();
        }else{
            JOptionPane.showMessageDialog(this,"Complete todos los datos obligatorios (*)", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jComboEntidadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboEntidadMouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Entidad");
            Iterator it = this.entidadArray.iterator();
            while (it.hasNext()) {
                EntidadData tmp = (EntidadData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboEntidad.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboEntidadMouseClicked

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if(this.jComboEntidad.isEnabled()){
            this.jComboEntidad.setEnabled(false);
        } else {
            this.jComboEntidad.setEnabled(true);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JYearChooser jAño;
    private javax.swing.JButton jBtnBusca;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboEntidad;
    private javax.swing.JComboBox jComboForecastGrupo;
    private javax.swing.JComboBox jComboVendedor;
    private javax.swing.JComboBox jComboZona;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollAsignadas;
    private javax.swing.JScrollPane jScrollPane1;
    private ar.com.bosoft.RenderTablas.RXTable jTablaAsignados;
    private javax.swing.JTable jTablaDisponibles;
    private javax.swing.JTextField jTxtBusca;
    // End of variables declaration//GEN-END:variables
}
