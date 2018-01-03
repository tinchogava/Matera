/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.RemitoDataSource;
import ar.com.bosoft.Modelos.Remito;
import ar.com.bosoft.RenderTablas.DateRenderer;
import ar.com.bosoft.buscadores.BuscaProducto;
import ar.com.bosoft.clases.TableHeaderMouseListener;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ClasiProductoCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import ar.com.dialogos.AvisoEspera;
import java.awt.Rectangle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import matera.cache.CacheClasiProducto;
import matera.jooq.tables.records.ClasiproductoRecord;
import org.jooq.Result;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class IntersucursalRecibeDevolucion extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, id_zonaDestino, id_remito;
    String empresa, usuario, todos, numero, origen, destino;
    
    DefaultTableModel modeloConsulta, modeloCaja, modeloProducto;
    TableRowSorter sorterConsulta, sorterCaja, sorterProducto;
    JTableHeader headerConsulta;
    TableHeaderMouseListener thmlConsulta;
    TableCellRenderer tableCellRenderer;
    
    ZonaCRUD zonaCRUD;
    ClasiProductoCRUD clasiProductoCRUD;
    
    ArrayList arrayOrigen, arrayDestino;
    
    Result<ClasiproductoRecord> cprResult;
    
    ResultSet rsConsulta, rsCaja, rsProducto;

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
    BuscaProducto buscaProducto;
    
    AvisoEspera avisoEspera;
    
    /**
     * Creates new form IntersucursalControl
     * @param conexion
     * @param id_empresa
     * @param empresa
     * @param usuario
     */
    public IntersucursalRecibeDevolucion(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        this.todos = "-- TODOS --";
        
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.clasiProductoCRUD = new ClasiProductoCRUD(conexion, empresa);
        
        this.arrayOrigen = new ArrayList();
        this.arrayDestino = new ArrayList();
        cprResult = CacheClasiProducto.instance().getClasiProductos();
        
        initComponents();
        
        this.modeloConsulta = (DefaultTableModel) this.jTablaConsulta.getModel();
        jTablaConsulta.setModel(modeloConsulta);        
        jTablaConsulta.setRowSorter (new TableRowSorter(modeloConsulta));
        sorterConsulta = new TableRowSorter(modeloConsulta);
        this.headerConsulta = jTablaConsulta.getTableHeader();
        this.thmlConsulta = new TableHeaderMouseListener(jTablaConsulta);
        this.headerConsulta.addMouseListener(this.thmlConsulta);
        tableCellRenderer = new DateRenderer();
        jTablaConsulta.getColumnModel().getColumn(0).setCellRenderer(tableCellRenderer);
        
        this.modeloCaja = (DefaultTableModel) this.jTablaCaja.getModel();
        jTablaCaja.setModel(modeloCaja);        
        jTablaCaja.setRowSorter (new TableRowSorter(modeloCaja));
        sorterCaja = new TableRowSorter(modeloCaja);
        
        this.modeloProducto = (DefaultTableModel) this.jTablaProductos.getModel();
        jTablaProductos.setModel(modeloProducto);        
        jTablaProductos.setRowSorter (new TableRowSorter(modeloProducto));
        sorterProducto = new TableRowSorter(modeloProducto);
        
        setJTexFieldChangedConsulta(this.jTxtBuscaConsulta);
        
        llenaComboOrigen();
        limpia();
    }

    private void setJTexFieldChangedConsulta(JTextField txt){
        DocumentListener documentListener = new DocumentListener() {
 
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                printItConsulta(documentEvent);
            }
 
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                printItConsulta(documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                printItConsulta(documentEvent);
            }
        };
        txt.getDocument().addDocumentListener(documentListener); 
    }
 
    private void printItConsulta(DocumentEvent documentEvent) {
        DocumentEvent.EventType type = documentEvent.getType();
 
        if (type.equals(DocumentEvent.EventType.CHANGE)){
 
        }else if (type.equals(DocumentEvent.EventType.INSERT)){
            JTxtBuscaChangedConsulta();
        }else if (type.equals(DocumentEvent.EventType.REMOVE)){
            JTxtBuscaChangedConsulta();
        }
    }
 
    private void JTxtBuscaChangedConsulta(){
        String text = jTxtBuscaConsulta.getText().trim().replaceAll("'", "´");
        if (text.length() == 0) {
          sorterConsulta.setRowFilter(null);
        } else {
            sorterConsulta.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*",this.thmlConsulta.getColumna()));
            jTablaConsulta.setRowSorter(sorterConsulta);
        }
    }

    private void llenaComboOrigen(){
        this.jComboOrigen.addItem(this.todos);
        
        this.arrayOrigen = zonaCRUD.consulta(true);
        Iterator it = arrayOrigen.iterator();
        while (it.hasNext()) {
            ZonaData tmp = (ZonaData) it.next();
            this.jComboOrigen.addItem(tmp.getNombre());
        }
    }

    private void llenaComboDestino(int id_zona){
        this.jComboDestino.removeAllItems();
        this.jComboDestino.addItem(this.todos);
        this.arrayDestino.clear();
        
        Iterator it = arrayOrigen.iterator();
        while (it.hasNext()) {
            ZonaData tmp = (ZonaData)it.next();
            if (tmp.getId_zona() != id_zona) {
                this.jComboDestino.addItem(tmp.getNombre());
                this.arrayDestino.add(tmp);
            }
        }
    }
    
    private void limpia(){
        this.jComboOrigen.setSelectedItem(this.todos);
        this.jComboDestino.setSelectedItem(this.todos);
        this.jTxtBuscaConsulta.setText("");
        
        modeloConsulta.getDataVector().removeAllElements();
        modeloConsulta.fireTableDataChanged();
        
        modeloCaja.getDataVector().removeAllElements();
        modeloCaja.fireTableDataChanged();
        
        modeloProducto.getDataVector().removeAllElements();
        modeloProducto.fireTableDataChanged();
        
        this.jTxtObservaciones.setText("");
        
        this.id_zonaDestino = 0;
    }
    
    private void llenaCajas(){
        try {
            modeloCaja.getDataVector().removeAllElements();
            modeloCaja.fireTableDataChanged();
            
            if (rsCaja.first()) {
                rsCaja.beforeFirst();
                while (rsCaja.next()) {
                    String codigo = rsCaja.getString("codigo");
                    String nombre = rsCaja.getString("nombre");
                    int id_cajaDeposito = rsCaja.getInt("id_cajaDeposito");
                    
                    Object[] fila = {codigo, nombre, id_cajaDeposito};
                    modeloCaja.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            Utiles.enviaError(empresa, ex);
        }
    }
    
    private void llenaProductos() {
        try {
            modeloProducto.getDataVector().removeAllElements();
            modeloProducto.fireTableDataChanged();
            
            if (rsProducto != null) {
                rsProducto.beforeFirst();
                while (rsProducto.next()) {
                    String codigo = rsProducto.getString("codigo");
                    String gtin = rsProducto.getString("gtin");
                    String nombre = rsProducto.getString("nombre");
                    int enviado = rsProducto.getInt("cantidad");
                    String pm = rsProducto.getString("pm");
                    String lote = rsProducto.getString("lote");
                    String serie = rsProducto.getString("serie");
                    String vencimiento = rsProducto.getString("vencimiento");
                    String clasiProducto = rsProducto.getString("clasiProducto");
                    int id_producto = rsProducto.getInt("id_producto");
                    double costo = rsProducto.getDouble("costo");
                    int id_moneda = rsProducto.getInt("id_moneda");

                    Object[] producto = {codigo, gtin, nombre, enviado, enviado, pm, 
                                            lote, serie, vencimiento, clasiProducto, id_producto, costo, 
                                            id_moneda};

                    modeloProducto.addRow(producto);
                }   
            }
        } catch (Exception ex) {
            Utiles.enviaError(this.empresa, ex);
        }
    }
    
    private void secuencia(){
        int respuesta = JOptionPane.showConfirmDialog(this, "Confirma la recepción del remito?", "Atencíon", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    IntersucursalRecibeDevolucion.this.avisoEspera = new AvisoEspera(null, false, 2);
                    IntersucursalRecibeDevolucion.this.avisoEspera.setVisible(true);
                    Thread performer = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            IntersucursalRecibeDevolucion.this.recibeIntersucursal();
                            IntersucursalRecibeDevolucion.this.insertRemitoDetalle();
                            IntersucursalRecibeDevolucion.this.avisoEspera.setVisible(false);
                            IntersucursalRecibeDevolucion.this.limpia();
                        }
                    }, "Performer");
                    performer.start();
                }
            });
//            recibeIntersucursal();
//            insertRemitoDetalle();
//            limpia();
        }
    }
    
    private void recibeIntersucursal(){
        ArrayList parametros = new ArrayList();
        parametros.add(this.id_remito);
        parametros.add(this.jTxtObservaciones.getText().trim());
        parametros.add(this.usuario);
        
        conexion.procAlmacenado("recibeIntersucursal", parametros, 
            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    private void insertRemitoDetalle(){
        ArrayList parametros = new ArrayList();
        String dc = "D";
        Date venc = null;
        String observaciones = "Intersucursal Recepción RM-R " + this.numero 
            + "\nOrigen: " + this.origen 
            + "\nDestino: " + this.destino;

        for (int i = 0; i < modeloProducto.getRowCount(); i++){
            parametros.clear();

            int cantidad = Integer.parseInt(modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "recibe")).toString());
            if (cantidad > 0) {
                int id_producto = Integer.parseInt(modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "id_producto")).toString());
                String lote = modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "lote")).toString();
                String serie = modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "serie")).toString();
                String pm = modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "pm")).toString();
                try{
                    venc = formatter.parse(modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "vencimiento")).toString());
                }catch (Exception e){
                    //No se ha ingresado una fecha válida
                }
                Double costo = Double.parseDouble(modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "costo")).toString());
                int id_moneda = (int) modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "id_moneda"));
                
                if (cantidad > 0){
                    parametros.add(id_remito);
                    parametros.add(id_producto);
                    parametros.add(cantidad);
                    parametros.add(dc);
                    parametros.add(lote);
                    parametros.add(serie);
                    parametros.add(pm);
                    parametros.add(venc == null ? (long)0 : venc.getTime());
                    parametros.add(costo);
                    parametros.add(id_moneda);
                    parametros.add(observaciones);
                    parametros.add(this.id_zonaDestino);
                    parametros.add(this.id_empresa);

                    conexion.procAlmacenado("insertRemitoDetalle", parametros, 
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                }
            }   
        }
    }
    
    private void imprime(int salida, boolean previa){
        Map param=new HashMap();
        
        RemitoDataSource data;
        Iterator itClasiProducto = cprResult.iterator();
        while (itClasiProducto.hasNext()){
            data = new RemitoDataSource();  //limpio el DataSource
            ClasiproductoRecord cpr = (ClasiproductoRecord) itClasiProducto.next();
            //ClasiProductoData clasiData = (ClasiProductoData) itClasiProducto.next();
            //String nombreClasi = clasiData.getNombre();
            param.put("tipo", cpr.getNombre());
            
            for (int i = 0; i < modeloProducto.getRowCount(); i++){
                int cantidad = Integer.parseInt(modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "envio")).toString());
                String codigo = modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "codigo")).toString();
                String gtin = modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "gtin")).toString();
                String nombre = modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "nombre")).toString();
                String lote = modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "lote")).toString();
                String serie = modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "serie")).toString();
                String pm = modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "pm")).toString();

                Date venc = null;
                try{
                    venc = formatter.parse(modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "vencimiento")).toString());
                }catch (Exception e){
                    //No se ha ingresado una fecha válida
                }

                String clasi = modeloProducto.getValueAt(i, Utiles.getColumnByName(jTablaProductos, "clasiProducto")).toString();
                
                if (clasi.equals(cpr.getNombre())) {
                    ar.com.bosoft.Modelos.Remito nuevo = new Remito(cantidad, codigo, gtin, nombre, lote, serie, pm, venc);

                    data.add(nuevo);
                }
            }
            if (data.listaSize() > 0) {
                Reporte reporte = new Reporte();
                String nombreReporte = "RecepcionIntersucursal";
                if (previa){
                    nombreReporte += "Previo" ;
                }
                reporte.startReport(nombreReporte, param, data, salida, "", "", 0);
            }
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

        jPopupMenu = new javax.swing.JPopupMenu();
        jAgregaProducto = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jComboOrigen = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboDestino = new javax.swing.JComboBox();
        jBtnFiltra = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaConsulta = new javax.swing.JTable();
        jTxtBuscaConsulta = new javax.swing.JTextField();
        jBtnPrevia = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTablaCaja = new javax.swing.JTable();
        jScrollSeleccion = new javax.swing.JScrollPane();
        jTablaProductos = new ar.com.bosoft.RenderTablas.RXTable();
        jTablaProductos.setSelectAllForEdit(true);
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        jAgregaProducto.setText("Agregar producto");
        jAgregaProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAgregaProductoActionPerformed(evt);
            }
        });
        jPopupMenu.add(jAgregaProducto);

        setTitle("Recepción de devoluciones intersucursal");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros"));

        jComboOrigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboOrigenActionPerformed(evt);
            }
        });

        jLabel1.setText("Envía");

        jLabel2.setText("Recibe");

        jComboDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboDestinoActionPerformed(evt);
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

        jLabel6.setText("Buscar");

        jTablaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Número", "Origen", "Destino", "id_remito", "observaciones", "id_zonaDestino"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaConsultaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaConsulta);
        if (jTablaConsulta.getColumnModel().getColumnCount() > 0) {
            jTablaConsulta.getColumnModel().getColumn(0).setMinWidth(75);
            jTablaConsulta.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTablaConsulta.getColumnModel().getColumn(0).setMaxWidth(75);
            jTablaConsulta.getColumnModel().getColumn(1).setMinWidth(100);
            jTablaConsulta.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTablaConsulta.getColumnModel().getColumn(1).setMaxWidth(100);
            jTablaConsulta.getColumnModel().getColumn(4).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(4).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(5).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(5).setMaxWidth(0);
            jTablaConsulta.getColumnModel().getColumn(6).setMinWidth(0);
            jTablaConsulta.getColumnModel().getColumn(6).setPreferredWidth(0);
            jTablaConsulta.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        jTxtBuscaConsulta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaConsultaFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBtnFiltra)
                    .addComponent(jComboOrigen, 0, 230, Short.MAX_VALUE)
                    .addComponent(jComboDestino, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtBuscaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTxtBuscaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jComboDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jBtnFiltra, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jBtnPrevia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/preview.png"))); // NOI18N
        jBtnPrevia.setText("Vista previa");
        jBtnPrevia.setBorderPainted(false);
        jBtnPrevia.setContentAreaFilled(false);
        jBtnPrevia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnPrevia.setFocusPainted(false);
        jBtnPrevia.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnPrevia.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/preview.png"))); // NOI18N
        jBtnPrevia.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/preview.png"))); // NOI18N
        jBtnPrevia.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnPrevia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPreviaActionPerformed(evt);
            }
        });

        jBtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/guardar.png"))); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.setBorderPainted(false);
        jBtnGuardar.setContentAreaFilled(false);
        jBtnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnGuardar.setFocusPainted(false);
        jBtnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
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

        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jTxtObservaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Observaciones"));
        jScrollPane4.setViewportView(jTxtObservaciones);

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTablaCaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "id_cajaDeposito"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
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
        jScrollPane7.setViewportView(jTablaCaja);
        if (jTablaCaja.getColumnModel().getColumnCount() > 0) {
            jTablaCaja.getColumnModel().getColumn(0).setPreferredWidth(95);
            jTablaCaja.getColumnModel().getColumn(0).setMaxWidth(95);
            jTablaCaja.getColumnModel().getColumn(2).setMinWidth(0);
            jTablaCaja.getColumnModel().getColumn(2).setPreferredWidth(0);
            jTablaCaja.getColumnModel().getColumn(2).setMaxWidth(0);
        }

        jTablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "GTIN", "Nombre", "Envío", "Recibe", "PM", "Lote", "serie", "vencimiento", "clasiProducto", "id_producto", "costo", "id_moneda"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true, true, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablaProductos.setComponentPopupMenu(jPopupMenu);
        jTablaProductos.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jScrollSeleccion.setViewportView(jTablaProductos);
        if (jTablaProductos.getColumnModel().getColumnCount() > 0) {
            jTablaProductos.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTablaProductos.getColumnModel().getColumn(0).setMaxWidth(150);
            jTablaProductos.getColumnModel().getColumn(3).setPreferredWidth(50);
            jTablaProductos.getColumnModel().getColumn(3).setMaxWidth(100);
            jTablaProductos.getColumnModel().getColumn(4).setPreferredWidth(50);
            jTablaProductos.getColumnModel().getColumn(4).setMaxWidth(100);
            jTablaProductos.getColumnModel().getColumn(5).setPreferredWidth(75);
            jTablaProductos.getColumnModel().getColumn(5).setMaxWidth(150);
            jTablaProductos.getColumnModel().getColumn(6).setPreferredWidth(100);
            jTablaProductos.getColumnModel().getColumn(6).setMaxWidth(200);
            jTablaProductos.getColumnModel().getColumn(7).setMinWidth(100);
            jTablaProductos.getColumnModel().getColumn(7).setPreferredWidth(100);
            jTablaProductos.getColumnModel().getColumn(7).setMaxWidth(100);
            jTablaProductos.getColumnModel().getColumn(8).setPreferredWidth(75);
            jTablaProductos.getColumnModel().getColumn(8).setMaxWidth(75);
            jTablaProductos.getColumnModel().getColumn(9).setMinWidth(0);
            jTablaProductos.getColumnModel().getColumn(9).setPreferredWidth(0);
            jTablaProductos.getColumnModel().getColumn(9).setMaxWidth(0);
            jTablaProductos.getColumnModel().getColumn(10).setMinWidth(0);
            jTablaProductos.getColumnModel().getColumn(10).setPreferredWidth(0);
            jTablaProductos.getColumnModel().getColumn(10).setMaxWidth(0);
            jTablaProductos.getColumnModel().getColumn(11).setMinWidth(0);
            jTablaProductos.getColumnModel().getColumn(11).setPreferredWidth(0);
            jTablaProductos.getColumnModel().getColumn(11).setMaxWidth(0);
            jTablaProductos.getColumnModel().getColumn(12).setMinWidth(0);
            jTablaProductos.getColumnModel().getColumn(12).setPreferredWidth(0);
            jTablaProductos.getColumnModel().getColumn(12).setMaxWidth(0);
        }

        jLabel5.setText("dd/mm/yyyy");

        jLabel7.setText("Formato de fecha");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollSeleccion, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                .addGap(1, 1, 1))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(7, 7, 7)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollSeleccion, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBtnPrevia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnGuardar)
                        .addGap(361, 361, 361)
                        .addComponent(jBtnSalir))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(3, 3, 3))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnPrevia)
                    .addComponent(jBtnSalir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboDestinoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboDestinoActionPerformed

    private void jComboOrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboOrigenActionPerformed
        try{
            int id_zona = 0;
            if (this.jComboOrigen.getSelectedIndex() > 0) {
                ZonaData tmp = (ZonaData) arrayOrigen.get(this.jComboOrigen.getSelectedIndex() - 1);
                id_zona = tmp.getId_zona();
            }
            
            llenaComboDestino(id_zona);
        }catch (Exception ex){
            //Solo para inicializar el combo.
        }
    }//GEN-LAST:event_jComboOrigenActionPerformed

    private void jBtnFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFiltraActionPerformed
        try {
            modeloConsulta.getDataVector().removeAllElements();
            modeloConsulta.fireTableDataChanged();
            
            modeloCaja.getDataVector().removeAllElements();
            modeloCaja.fireTableDataChanged();
            
            modeloProducto.getDataVector().removeAllElements();
            modeloProducto.fireTableDataChanged();
            
            this.jTxtObservaciones.setText("");
            
            int id_zona = 0;
            if (this.jComboOrigen.getSelectedIndex() > 0) {
                ZonaData z = (ZonaData) arrayOrigen.get(this.jComboOrigen.getSelectedIndex() - 1);
                id_zona = z.getId_zona();
            }
            
            int id_zonaDest = 0;
            if (this.jComboDestino.getSelectedIndex() > 0) {
                ZonaData z = (ZonaData) arrayDestino.get(this.jComboDestino.getSelectedIndex() - 1);
                id_zonaDest = z.getId_zona();
            }
            
            ArrayList parametros = new ArrayList();
            parametros.add(id_zona);
            parametros.add(id_zonaDest);
            parametros.add(id_empresa);
            
            rsConsulta = conexion.procAlmacenado("consultaIntersucursalRecibeDevolucion", parametros, 
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsConsulta.beforeFirst();
            while (rsConsulta.next()) {
                Date fecha = rsConsulta.getDate("fecha");
                String nro = rsConsulta.getString("numero");
                String orig = rsConsulta.getString("origen");
                String dest = rsConsulta.getString("destino");
                int id_r = rsConsulta.getInt("id_remito");
                String observaciones = rsConsulta.getString("observaciones");
                int id_zonaD = rsConsulta.getInt("id_zonaDestino");
                
                Object[] fila = {fecha, nro, orig, dest, id_r, observaciones, id_zonaD};
                modeloConsulta.addRow(fila);
            }            
        } catch (Exception ex) {
            Utiles.enviaError(this.empresa, ex);
        }
    }//GEN-LAST:event_jBtnFiltraActionPerformed

    private void jTxtBuscaConsultaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaConsultaFocusGained

    }//GEN-LAST:event_jTxtBuscaConsultaFocusGained

    private void jTablaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaConsultaMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                int fila = jTablaConsulta.convertRowIndexToModel(this.jTablaConsulta.getSelectedRow());
                this.numero = modeloConsulta.getValueAt(fila, 1).toString();
                this.origen = modeloConsulta.getValueAt(fila, 2).toString();
                this.destino = modeloConsulta.getValueAt(fila, 3).toString();
                this.id_remito = (int) modeloConsulta.getValueAt(fila, 4);
                this.id_zonaDestino = (int)modeloConsulta.getValueAt(fila, 6);
                
                ArrayList parametros = new ArrayList();
                parametros.add(this.id_remito);
                
                rsCaja = conexion.procAlmacenado("traeRemitoCaja", parametros, 
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                
                llenaCajas();
                
                rsProducto = conexion.procAlmacenado("traeRemitoDetalle", parametros, 
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
                llenaProductos();
                
                this.jTxtObservaciones.setText(modeloConsulta.getValueAt(fila, 5).toString());
            } catch (Exception ex) {
                Utiles.enviaError(this.empresa, ex);
            }
        }
    }//GEN-LAST:event_jTablaConsultaMouseClicked

    private void jBtnPreviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPreviaActionPerformed
        imprime(0, true);
    }//GEN-LAST:event_jBtnPreviaActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        secuencia();
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        try {
            if (this.rsConsulta != null){
                this.rsConsulta.close();
            }
            if (this.rsCaja != null){
                this.rsCaja.close();
            }
            if (this.rsProducto != null){
                this.rsProducto.close();
            }
            this.dispose();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jAgregaProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAgregaProductoActionPerformed
        this.buscaProducto = new BuscaProducto(conexion, null, true, id_empresa, empresa);
        this.buscaProducto.setVisible(true);
        
        String codigo = this.buscaProducto.getCodigo();
        String gtin = this.buscaProducto.getGtin();
        String nombre = this.buscaProducto.getNombre();
        int enviado = 1;
        String pm = this.buscaProducto.getPm();
        String lote = "";
        String serie = "";
        String vencimiento = "";
        String clasiProducto = this.buscaProducto.getClasiProducto();
        int id_producto = this.buscaProducto.getId_producto();
        double costo = this.buscaProducto.getCosto();
        int id_moneda = this.buscaProducto.getId_moneda();

        Object[] producto = {codigo, gtin, nombre, enviado, enviado, pm, 
                                lote, serie, vencimiento, clasiProducto, id_producto, costo, 
                                id_moneda};

        if (id_producto > 0) {
            modeloProducto.addRow(producto);
            Rectangle r = this.jTablaProductos.getCellRect(modeloProducto.getRowCount() - 1, 0, true);
            this.jScrollSeleccion.getViewport().scrollRectToVisible(r);
        }
    }//GEN-LAST:event_jAgregaProductoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jAgregaProducto;
    private javax.swing.JButton jBtnFiltra;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnPrevia;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JComboBox jComboDestino;
    private javax.swing.JComboBox jComboOrigen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollSeleccion;
    private javax.swing.JTable jTablaCaja;
    private javax.swing.JTable jTablaConsulta;
    private ar.com.bosoft.RenderTablas.RXTable jTablaProductos;
    private javax.swing.JTextField jTxtBuscaConsulta;
    private javax.swing.JTextArea jTxtObservaciones;
    // End of variables declaration//GEN-END:variables

}
