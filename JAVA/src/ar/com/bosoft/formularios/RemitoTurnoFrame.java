/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 *
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.PresupuestoProdDataSource;
import ar.com.bosoft.DataSources.RemitoDataSource;
import ar.com.bosoft.Modelos.PresupuestoProd;
import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.clases.TableHeaderMouseListener;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.clases.Reporte;
import com.toedter.calendar.JDateChooser;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import ar.com.bosoft.RenderTablas.DateCellEditor;
import ar.com.matera.TableModels.ProductoTableModel;
import ar.com.matera.TableModels.RemitoDetalleTableModel;
import java.util.List;
import matera.TableObjects.RemitoDetalleTableObject;
import matera.cache.CacheClasiProducto;
import matera.gui.RemitoInternalFrame;
import matera.gui.helper.ExcelAdapter;
import matera.gui.renderers.TrazabilidadRenderer;
import matera.helpers.RemitoHelper;
import matera.jooq.Tables;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class RemitoTurnoFrame extends RemitoInternalFrame {
    Conexion conexion;
    int id_empresa, id_presupuesto, id_entidad, indiceProducto, indiceSeleccion, copias;
    String empresa, usuario, fechaCirugia, zona, lugarCirugia, entidad, profesional, matricula, paciente, tecnico, impresora, rutaArchivo;
    boolean creoRemito;

    TableRowSorter sorterProducto;
    JTableHeader header;
    TableHeaderMouseListener tableHeaderMouseListener;
    
    ResultSet rsTraePresupuestoProd;

    ConsultaConfirmados consultaConfirmados;

    /**
     * Creates new form RemitoTurnoFrame
     * @param conexion
     * @param consultaConfirmados
     */
    public RemitoTurnoFrame(Conexion conexion, ConsultaConfirmados consultaConfirmados) {
        this.conexion = conexion;
        this.id_empresa = Principal.getIdEmpresa();
        this.empresa = Principal.getEmpresaName();
        this.usuario = Principal.getUsuarioName();
        this.consultaConfirmados = consultaConfirmados;
        this.composicionCajaMap = new HashMap<>();
        this.detalleCajaRemito = new DetalleCajaRemito(null, true);

        initComponents();

        modeloProducto = new ProductoTableModel();
        modeloProducto.getPropertiesFromDefaultModel(productosTable.getModel());
        productosTable.setModel(modeloProducto);
        sorterProducto = new TableRowSorter(modeloProducto);
        productosTable.setRowSorter (sorterProducto);
        this.header = productosTable.getTableHeader();
        this.tableHeaderMouseListener = new TableHeaderMouseListener(productosTable);
        this.header.addMouseListener(this.tableHeaderMouseListener);

        modeloCajaSeleccion = (DefaultTableModel) jTablaCajaSeleccion.getModel();
        jTablaCajaSeleccion.setModel(modeloCajaSeleccion);

        modeloSeleccion = new RemitoDetalleTableModel();
        modeloSeleccion.getPropertiesFromDefaultModel(seleccionTable.getModel());
        seleccionTable.setModel(modeloSeleccion);
        seleccionTable.setDefaultRenderer(Object.class, new TrazabilidadRenderer());

        TableColumn dateCol = seleccionTable.getColumnModel().getColumn(Utiles.getColumnByName(seleccionTable, "vencimiento"));
        JDateChooser date = new JDateChooser("dd/MM/20yy", "##/##/####", '_');
        date.setMinSelectableDate(new Date());
        dateCol.setCellEditor(new DateCellEditor(date));

        setTitle("Carga de Remito de Turno Confirmado");
        loadClasiProductoCombo();
        limpia();
        consultaProducto();
        setJTexFieldChanged(jTxtBusca);
        
        ExcelAdapter adapter = new ExcelAdapter(seleccionTable);
/*
        TableColumnAdjuster tcaProducto = new TableColumnAdjuster(productosTable);
        tcaProducto.adjustColumns();
        TableColumnAdjuster tcaSeleccion = new TableColumnAdjuster(seleccionTable);
        tcaSeleccion.setDynamicAdjustment(true);
*/
    }

    public void setIdZona(Integer id_zona){
        this.id_zona = id_zona;
    }

    private void loadClasiProductoCombo(){
        this.jComboClasificacion.addItem("-- TODOS --");

        clasiProductos = CacheClasiProducto.instance().getClasiProductos();
        clasiProductos.forEach(c ->{
            this.jComboClasificacion.addItem(c.into(Tables.CLASIPRODUCTO).getNombre());
        });
    }

    public final void limpia(){
        this.jTxtSucursal.setText("");
        this.jTxtNumero.setText("");
        this.jLblId_presupuesto.setText("");
        this.jLblFechaCirugia.setText("");
        this.jLblOrigen.setText("");
        this.jLblLugarCirugia.setText("");
        this.jLblEntidad.setText("");
        this.jLblProfesional.setText("");
        this.jLblMatricula.setText("");
        this.jLblPaciente.setText("");
        this.jLblTecnico.setText("");
        this.jTxtSet.setText("");

        modeloCajaSeleccion.getDataVector().removeAllElements();
        modeloCajaSeleccion.fireTableDataChanged();
        composicionCajaMap.clear();

        this.jComboClasificacion.setSelectedIndex(-1);
        this.jTxtBusca.setText("");

        modeloSeleccion.removeAllRows();
        modeloSeleccion.fireTableDataChanged();

        this.jCheckRemito.setSelected(true);
        this.jCheckCertServ.setSelected(true);
        this.jCheckCertImpl.setSelected(true);

        this.indiceProducto = -1;
        this.indiceSeleccion = -1;
        this.copias = 1;
        this.impresora = "";
        this.rutaArchivo = "";
        this.creoRemito = false;
    }

    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }

    public void setFechaCirugia(String fechaCirugia) {
        this.fechaCirugia = fechaCirugia;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public void setLugarCirugia(String lugarCirugia) {
        this.lugarCirugia = lugarCirugia;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public void setId_entidad(int id_entidad) {
        this.id_entidad = id_entidad;
    }

    public void setId_zona(int id_zona) {
        this.id_zona = id_zona;
    }

    public void setTecnico(String tecnico){
        this.tecnico = tecnico;
    }

    public void addFila(Object[] fila){
        modeloCajaSeleccion.addRow(fila);
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
          sorterProducto.setRowFilter(null);
        } else {
          sorterProducto.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*", this.tableHeaderMouseListener.getColumna()));
          productosTable.setRowSorter(sorterProducto);
        }
    }

    public final void llena(){
        this.jLblId_presupuesto.setText(String.valueOf(this.id_presupuesto));
        this.jLblFechaCirugia.setText(this.fechaCirugia);
        this.jLblOrigen.setText(this.zona);
        this.jLblLugarCirugia.setText(this.lugarCirugia);
        this.jLblEntidad.setText(this.entidad);
        this.jLblProfesional.setText(this.profesional);
        this.jLblMatricula.setText(this.matricula);
        this.jLblPaciente.setText(this.paciente);
        this.jLblTecnico.setText(this.tecnico);
    }

    private boolean validaRemito(){
        return !this.jTxtSucursal.getText().isEmpty() &&
                !this.jTxtNumero.getText().isEmpty() &&
                !this.jTxtSet.getText().isEmpty();
    }

    private void secuencia(){
        if (seleccionTable.isEditing())
            seleccionTable.getCellEditor().stopCellEditing();
        int respuesta = JOptionPane.showConfirmDialog(this, "Confirma la carga del remito?", "Atencíon", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION){
            if (validaRemito()){
                if (!existeRemito(this.jTxtSucursal.getText() + this.jTxtNumero.getText())){
                    String numComp = this.jTxtSucursal.getText() + this.jTxtNumero.getText();
                    String cajas = "";
                    for (int i = 0; i < modeloCajaSeleccion.getRowCount(); i++) {
                        cajas += modeloCajaSeleccion.getValueAt(i, 0).toString() + (i == modeloCajaSeleccion.getRowCount() - 1 ? "" : " / ");
                    }                    
                    Integer output = insertRemito(id_presupuesto, RemitoInternalFrame.TIPO_ENTIDAD.PRESUPUESTO, id_entidad, numComp, cajas, jTxtSet.getText(), "");
                    if (output == Utiles.INSERT.ERROR){
                        Utiles.showMessage("error al guardar.");
                        return;
                    }
                    salida = new Salida(null, true);
                    salida.setVisible(true);
                    if (salida.getSiNo()) {
                        this.rutaArchivo = salida.getRutaArchivo();
                        this.impresora = salida.getImpresora();
                        this.copias = salida.getCopias();
                        imprime(salida, false);
                    }
                    this.jBtnSalirActionPerformed(null);
                }else{
                    JOptionPane.showMessageDialog(this, "El remito ya existe", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(this, "Complete datos de Remito y Numero de SET (*)", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void imprimirCertificados(Integer id, Map param){
        try{
            ArrayList parametros = new ArrayList();
            parametros.add(id);

            rsTraePresupuestoProd = conexion.procAlmacenado("traePresupuestoProd", parametros,
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

            PresupuestoProdDataSource dataCertServicio = new PresupuestoProdDataSource();

            rsTraePresupuestoProd.beforeFirst();
            while (rsTraePresupuestoProd.next()){
                int cantidad = rsTraePresupuestoProd.getInt("cantidad");
                String codigo = rsTraePresupuestoProd.getString("codigo");
                String nombre = rsTraePresupuestoProd.getString("producto");
                String obsProducto = rsTraePresupuestoProd.getString("observaciones");

                PresupuestoProd pp = new PresupuestoProd(cantidad, codigo, nombre, obsProducto, BigDecimal.ZERO);
                dataCertServicio.addPresupuestoProd(pp);
            }

            if (this.jCheckCertServ.isSelected()){
                Reporte reporte = new Reporte();
                reporte.startReport("CertServicio",param, dataCertServicio,
                        salida.getTipoSalida(),
                        salida.getRutaArchivo(),
                        salida.getImpresora(),
                        salida.getCopias());
            }

            if (this.jCheckCertImpl.isSelected()){
                Reporte reporte = new Reporte();
                reporte.startReport("CertImplante",param, null,
                        salida.getTipoSalida(),
                        salida.getRutaArchivo(),
                        salida.getImpresora(),
                        salida.getCopias());
            }
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }
    }

    private void imprime (Integer tipoSalida, boolean previa){
        salida = new Salida(null, true);
        salida.setTipoSalida(tipoSalida);
        imprime(salida, previa);
    }

    private void imprime(Salida salida, boolean previa){
        try {
            int id = Integer.parseInt(this.jLblId_presupuesto.getText());
            String e = this.jLblEntidad.getText();
            Date fCx = null;
            try {
                fCx = formatter.parse(this.jLblFechaCirugia.getText());
            } catch (Exception ex) {
                //No se ha introducido una fecha válida
            }

            String lCx = this.jLblLugarCirugia.getText();
            String tec = this.jLblTecnico.getText();
            String prof = this.jLblProfesional.getText();
            String pac = this.jLblPaciente.getText();
            String observaciones = "";
            String dirLugarCirugia = "";
            String mat = this.jLblMatricula.getText();

            Map param=new HashMap();
            param.put("fecha", new Date());
            param.put("id_presupuesto", id);
            param.put("entidad", e);
            param.put("fechaCirugia", fCx);
            param.put("lugarCirugia", lCx);
            param.put("tecnico", tec);
            param.put("profesional", prof);
            param.put("paciente", pac);
            param.put("observaciones", observaciones);
            param.put("matricula", mat);
            param.put("dirLugarCirugia", dirLugarCirugia);
            param.put("numComp", this.jTxtSucursal.getText() + this.jTxtNumero.getText());
            param.put("previa", previa);
            param.put("set", this.jTxtSet.getText());

            HashMap cajaMap = new HashMap();
            for (int i = 0; i < modeloCajaSeleccion.getRowCount(); i++) {
                cajaMap.put(Integer.parseInt(Utiles.valueAt(modeloCajaSeleccion, i, "id_cajaDeposito").toString()), Utiles.valueAt(modeloCajaSeleccion, i, "codigo"));
            }

            RemitoHelper remitoHelper = new RemitoHelper();
            remitoHelper.printByCaja(param, composicionCajaMap, Utiles.modelToMapList(modeloCajaSeleccion), salida);

            if (modeloSeleccion.getRowCount() > 0) {
                param.put("cajas","Adicionales");
                param.put("tipo", "Adicionales");
                RemitoDataSource data = new RemitoDataSource(); //limpio el DataSource

                List<RemitoDetalleTableObject> extraList = modeloSeleccion.getRowsAsList();

                extraList = remitoHelper.groupProductOccurrences(extraList);

                remitoHelper.sortByCodigo(extraList);
                remitoHelper.fillRemitoReport(data, extraList);
                remitoHelper.printReporte(param, data, salida);
            }

            if (!previa) {
                imprimirCertificados(id, param);
            }
        } catch (Exception ex) {
            Utiles.showErrorMessage(ex);
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

        jPopupMenuProductos = new javax.swing.JPopupMenu();
        jMenuItemCargar = new javax.swing.JMenuItem();
        seleccionMenu = new javax.swing.JPopupMenu();
        removeSeleccionados = new javax.swing.JMenuItem();
        jLblLugarCirugia = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLblOrigen = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablaCajaSeleccion = new javax.swing.JTable();
        jBtnComposicion = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTxtSet = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLblFechaCirugia = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jComboClasificacion = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jTxtBusca = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        productosTable = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jScrollSeleccion = new javax.swing.JScrollPane();
        seleccionTable = new ar.com.bosoft.RenderTablas.RXTable();
        seleccionTable.setSelectAllForEdit(true);
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLblTecnico = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jBtnPrevia = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLblMatricula = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jCheckRemito = new javax.swing.JCheckBox();
        jCheckCertServ = new javax.swing.JCheckBox();
        jCheckCertImpl = new javax.swing.JCheckBox();
        jLblPaciente = new javax.swing.JLabel();
        jLblId_presupuesto = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLblEntidad = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLblProfesional = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxtSucursal = new javax.swing.JTextField();
        jTxtSucursal.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(4,true));
        jTxtNumero = new javax.swing.JTextField();
        jTxtNumero.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(8,true));

        jMenuItemCargar.setText("Cargar Productos...");
        jMenuItemCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCargarActionPerformed(evt);
            }
        });
        jPopupMenuProductos.add(jMenuItemCargar);

        removeSeleccionados.setText("Remover seleccionados...");
        removeSeleccionados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeSeleccionadosActionPerformed(evt);
            }
        });
        seleccionMenu.add(removeSeleccionados);

        setTitle("Carga de Remito");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jLblLugarCirugia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblLugarCirugia.setText("...");

        jLabel7.setText("Lugar de Cx.");

        jLabel4.setText("Origen");

        jLblOrigen.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblOrigen.setText("...");

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cajas asignadas"));

        jTablaCajaSeleccion.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTablaCajaSeleccion);
        if (jTablaCajaSeleccion.getColumnModel().getColumnCount() > 0) {
            jTablaCajaSeleccion.getColumnModel().getColumn(0).setPreferredWidth(120);
            jTablaCajaSeleccion.getColumnModel().getColumn(0).setMaxWidth(200);
            jTablaCajaSeleccion.getColumnModel().getColumn(2).setMinWidth(0);
            jTablaCajaSeleccion.getColumnModel().getColumn(2).setPreferredWidth(0);
            jTablaCajaSeleccion.getColumnModel().getColumn(2).setMaxWidth(0);
        }

        jBtnComposicion.setText("Cargar composición");
        jBtnComposicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnComposicionActionPerformed(evt);
            }
        });

        jLabel3.setText("N° Set");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtSet)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnComposicion)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnComposicion)
                    .addComponent(jLabel3)
                    .addComponent(jTxtSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setText("Fecha Cx.");

        jLblFechaCirugia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblFechaCirugia.setText("...");

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Productos"));

        jComboClasificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboClasificacionActionPerformed(evt);
            }
        });

        jLabel10.setText("Busca");

        productosTable.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        productosTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "codigo", "nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        productosTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        productosTable.setComponentPopupMenu(jPopupMenuProductos);
        productosTable.getTableHeader().setReorderingAllowed(false);
        productosTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productosTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(productosTable);

        jLabel11.setText("Clasificación");

        seleccionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "codigo", "gtin", "nombre", "pm", "cantidad", "lote", "serie", "vencimiento", "tipo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        seleccionTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        seleccionTable.setComponentPopupMenu(seleccionMenu);
        seleccionTable.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        seleccionTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seleccionTableMouseClicked(evt);
            }
        });
        jScrollSeleccion.setViewportView(seleccionTable);

        jLabel6.setText("Formato de fecha");

        jLabel5.setText("dd/mm/yyyy");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtBusca)
                            .addComponent(jComboClasificacion, 0, 369, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 343, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollSeleccion)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboClasificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel5)))
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(jScrollSeleccion, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(1, 1, 1))
        );

        jLblTecnico.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblTecnico.setText("...");

        jLabel19.setText("Técnico");

        jBtnPrevia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/preview.png"))); // NOI18N
        jBtnPrevia.setText("Vista previa");
        jBtnPrevia.setBorderPainted(false);
        jBtnPrevia.setContentAreaFilled(false);
        jBtnPrevia.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        jBtnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        jLabel12.setText("Entidad");

        jLabel18.setText("Paciente");

        jLblMatricula.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblMatricula.setText("...");

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jCheckRemito.setSelected(true);
        jCheckRemito.setText("Remito");
        jCheckRemito.setContentAreaFilled(false);
        jCheckRemito.setEnabled(false);

        jCheckCertServ.setSelected(true);
        jCheckCertServ.setText("Certificado de servicio");
        jCheckCertServ.setContentAreaFilled(false);

        jCheckCertImpl.setSelected(true);
        jCheckCertImpl.setText("Certificado de implante");
        jCheckCertImpl.setContentAreaFilled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCheckCertImpl)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCheckCertServ)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(jCheckRemito)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckCertServ)
                    .addComponent(jCheckRemito))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckCertImpl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLblPaciente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblPaciente.setText("...");

        jLblId_presupuesto.setBackground(new java.awt.Color(153, 204, 255));
        jLblId_presupuesto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblId_presupuesto.setText("99999");
        jLblId_presupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLblId_presupuestoActionPerformed(evt);
            }
        });

        jLabel14.setText("Profesional");

        jLblEntidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblEntidad.setText("...");

        jLabel16.setText("Matrícula");

        jLblProfesional.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblProfesional.setText("...");

        jLabel1.setText("Remito");

        jLabel2.setText("Turno N°");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(jBtnPrevia)
                        .addGap(93, 93, 93)
                        .addComponent(jBtnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnSalir))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel7)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jTxtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLblLugarCirugia, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                                .addComponent(jLblEntidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLblProfesional, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLblMatricula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLblPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLblOrigen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLblTecnico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLblId_presupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLblFechaCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTxtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLblId_presupuesto)
                            .addComponent(jLabel2)
                            .addComponent(jLabel8)
                            .addComponent(jLblFechaCirugia))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLblOrigen))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLblLugarCirugia))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLblEntidad))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLblProfesional))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jLblMatricula))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jLblPaciente))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLblTecnico)
                            .addComponent(jLabel19)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPrevia, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jBtnSalir)
                        .addComponent(jBtnGuardar)))
                .addGap(3, 3, 3))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnComposicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnComposicionActionPerformed
        jBtnComposicion.disable();
        if (jTablaCajaSeleccion.getSelectedRow() >= 0) {
            int fila = jTablaCajaSeleccion.convertRowIndexToModel(jTablaCajaSeleccion.getSelectedRow());

            int id_caja = (int)modeloCajaSeleccion.getValueAt(fila, Utiles.getColumnByName(modeloCajaSeleccion, "id_cajadeposito"));
            String nombreCaja = modeloCajaSeleccion.getValueAt(fila, Utiles.getColumnByName(modeloCajaSeleccion, "codigo")) + " - " + modeloCajaSeleccion.getValueAt(fila, Utiles.getColumnByName(modeloCajaSeleccion, "nombre")).toString();
            getDatosComposicion(id_caja, fila, nombreCaja);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione una caja", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
        jBtnComposicion.enable();
    }//GEN-LAST:event_jBtnComposicionActionPerformed

    private void jComboClasificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboClasificacionActionPerformed
        comboClasificacionItemSelected(jComboClasificacion);
    }//GEN-LAST:event_jComboClasificacionActionPerformed

    private void productosTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productosTableMouseClicked
        tablaProductoMouseClicked( productosTable, evt);
    }//GEN-LAST:event_productosTableMouseClicked

    private void seleccionTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seleccionTableMouseClicked

    }//GEN-LAST:event_seleccionTableMouseClicked

    private void jBtnPreviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPreviaActionPerformed
        imprime(0, true);
    }//GEN-LAST:event_jBtnPreviaActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        jBtnGuardar.disable();
        secuencia();
        jBtnGuardar.enable();
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        try {
            this.consultaConfirmados.limpia();
            this.consultaConfirmados.consulta();
            this.dispose();
        } catch (Exception ex) {
            Utiles.showErrorMessage(ex);
        }
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jLblId_presupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLblId_presupuestoActionPerformed
        if (!this.jLblId_presupuesto.getText().equals("")) {
            Presupuesto presupuesto = new Presupuesto(conexion, id_empresa, empresa, usuario);
            presupuesto.setId_presupuesto(this.jLblId_presupuesto.getText().trim());
            Principal.dp.add(presupuesto);
            presupuesto.toFront();
            presupuesto.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "No hay un turno seleccionado", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jLblId_presupuestoActionPerformed

    private void jTxtSucursalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtSucursalFocusLost
        String aux = this.jTxtSucursal.getText();
        this.jTxtSucursal.setText("0000".substring(aux.length()) + aux);
    }//GEN-LAST:event_jTxtSucursalFocusLost

    private void jTxtNumeroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtNumeroFocusLost
        String aux = this.jTxtNumero.getText();
        this.jTxtNumero.setText("00000000".substring(aux.length()) + aux);
    }//GEN-LAST:event_jTxtNumeroFocusLost

    private void jMenuItemCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCargarActionPerformed
        agregarProductos(productosTable);
    }//GEN-LAST:event_jMenuItemCargarActionPerformed

    private void removeSeleccionadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSeleccionadosActionPerformed
        removeSeleccionados(seleccionTable);
    }//GEN-LAST:event_removeSeleccionadosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnComposicion;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnPrevia;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JCheckBox jCheckCertImpl;
    private javax.swing.JCheckBox jCheckCertServ;
    private javax.swing.JCheckBox jCheckRemito;
    private javax.swing.JComboBox jComboClasificacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLblEntidad;
    private javax.swing.JLabel jLblFechaCirugia;
    private javax.swing.JButton jLblId_presupuesto;
    private javax.swing.JLabel jLblLugarCirugia;
    private javax.swing.JLabel jLblMatricula;
    private javax.swing.JLabel jLblOrigen;
    private javax.swing.JLabel jLblPaciente;
    private javax.swing.JLabel jLblProfesional;
    private javax.swing.JLabel jLblTecnico;
    private javax.swing.JMenuItem jMenuItemCargar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenuProductos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollSeleccion;
    private javax.swing.JTable jTablaCajaSeleccion;
    private javax.swing.JTextField jTxtBusca;
    private javax.swing.JTextField jTxtNumero;
    private javax.swing.JTextField jTxtSet;
    private javax.swing.JTextField jTxtSucursal;
    private javax.swing.JTable productosTable;
    private javax.swing.JMenuItem removeSeleccionados;
    private javax.swing.JPopupMenu seleccionMenu;
    private ar.com.bosoft.RenderTablas.RXTable seleccionTable;
    // End of variables declaration//GEN-END:variables
}
