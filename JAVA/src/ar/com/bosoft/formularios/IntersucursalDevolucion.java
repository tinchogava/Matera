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
import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.ClasiProductoCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.ClasiProductoData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.clases.Reporte;
import ar.com.dialogos.AvisoEspera;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.swing.table.TableRowSorter;
import matera.gui.RemitoInternalFrame;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class IntersucursalDevolucion extends RemitoInternalFrame {
    Conexion conexion;
    int id_empresa, indiceProducto, indiceSeleccion, copias;
    String empresa, usuario, rutaArchivo, impresora;
    TableRowSorter sorterCaja, sorterCajaSeleccion;
    
    ZonaCRUD zonaCRUD;
    ClasiProductoCRUD clasiProductoCRUD;
    
    ArrayList arrayZona, arrayZonaDestino, arrayComponentes, arrayClasi;
    
    ResultSet rsCajaPrestada, rsCajaComposicion, rsTraeRemito, rsUltimoRemito;
    
    AvisoEspera avisoEspera;
    
    public IntersucursalDevolucion(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.clasiProductoCRUD = new ClasiProductoCRUD(conexion, empresa);
        this.arrayZona = new ArrayList();
        this.arrayZonaDestino = new ArrayList();
        this.arrayComponentes = new ArrayList();
        this.arrayClasi = new ArrayList();
        this.detalleCajaRemito = new DetalleCajaRemito(null, true);
        
        initComponents();
        
        modeloCaja = (DefaultTableModel) jTablaCaja.getModel();
        jTablaCaja.setModel(modeloCaja);
        jTablaCaja.setRowSorter (new TableRowSorter(modeloCaja));
        sorterCaja = new TableRowSorter(modeloCaja);
        
        modeloCajaSeleccion = (DefaultTableModel) jTablaCajaSeleccion.getModel();
        jTablaCajaSeleccion.setModel(modeloCajaSeleccion);
        jTablaCajaSeleccion.setRowSorter (new TableRowSorter(modeloCajaSeleccion));
        sorterCajaSeleccion = new TableRowSorter(modeloCajaSeleccion);
        
        llenaComboOrigen();
        consultaClasi();
        limpia();
        zonaUsuario();
        programaBuscaCaja(jTxtBuscaCaja);
    }

    private void zonaUsuario(){
        int id_zonaUsuario = Principal.usuarioData.getId_zonaSistema();
        if (id_zonaUsuario > 0) {
            if (this.arrayZona != null){
                Iterator it = arrayZona.iterator();
                while (it.hasNext()){
                    ZonaData tmp = (ZonaData) it.next();
                    if (tmp.getId_zona() == id_zonaUsuario) {
                        this.jComboOrigen.setSelectedItem(tmp.getNombre());
                    }
                }
            }
        }
        this.jComboOrigen.setEnabled(id_zonaUsuario == 0);
    }
    
    private void llenaComboOrigen(){
        arrayZona = zonaCRUD.consulta(true);
        
        if (arrayZona.isEmpty()){
            JOptionPane.showMessageDialog(this, "No hay zonas habilitadas", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }else{
            Iterator i = arrayZona.iterator();
            while (i.hasNext()){
                ZonaData tmp = (ZonaData) i.next();
                this.jComboOrigen.addItem(tmp.getNombre());
            }
        }
    }
    
    private void consultaClasi(){
        arrayClasi = clasiProductoCRUD.consulta(true);
    }
    
    public final void limpia(){
        this.jFecha.setDate(new Date());
        this.jTxtSucursal.setText("");
        this.jTxtNumero.setText("");
        
        if (this.jComboOrigen.isEnabled()) {
            this.jComboOrigen.setSelectedIndex(-1);
        }
        
        this.jComboDestino.setSelectedIndex(-1);
        
        this.jTxtBuscaCaja.setText("");
        modeloCaja.getDataVector().removeAllElements();
        modeloCaja.fireTableDataChanged();
        modeloCajaSeleccion.getDataVector().removeAllElements();
        modeloCajaSeleccion.fireTableDataChanged();
        
        this.jTxtObservaciones.setText("");
        
        this.indiceProducto = -1;
        this.indiceSeleccion = -1;
        
        this.rutaArchivo = "";
        this.arrayComponentes.clear();
    }
    
    private void programaBuscaCaja(JTextField txt){
        DocumentListener documentListener = new DocumentListener() { 
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                printItCaja(documentEvent);
            }
 
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                printItCaja(documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                printItCaja(documentEvent);
            }
        };
        txt.getDocument().addDocumentListener(documentListener); 
    }
 
    private void printItCaja(DocumentEvent documentEvent) {
        DocumentEvent.EventType type = documentEvent.getType();
 
        if (type.equals(DocumentEvent.EventType.INSERT) || type.equals(DocumentEvent.EventType.REMOVE)){
            JTxtBuscaCajaChanged();
        }
    }
 
    private void JTxtBuscaCajaChanged(){
        String text = jTxtBuscaCaja.getText().trim().replaceAll("'", "´");
        if (text.length() == 0) {
          sorterCaja.setRowFilter(null);
        } else {
          sorterCaja.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTablaCaja.setRowSorter(sorterCaja);
        }
    }
    
    private void llenaCajas(int id_zona){
        modeloCaja.getDataVector().removeAllElements();
        modeloCaja.fireTableDataChanged();
        modeloCajaSeleccion.getDataVector().removeAllElements();
        modeloCajaSeleccion.fireTableDataChanged();
        arrayComponentes.clear();
        
        try{
            ArrayList parametros = new ArrayList();
            parametros.add(id_zona);
            parametros.add(this.id_empresa);
            
            rsCajaPrestada = conexion.procAlmacenado("consultaCajaPrestada", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rsCajaPrestada.beforeFirst();
            while (rsCajaPrestada.next()){
                String codigo = rsCajaPrestada.getString("codigo");
                String caja = rsCajaPrestada.getString("caja");
                int id_cajaDeposito = rsCajaPrestada.getInt("id_cajaDeposito");
                int id_intersucursalCaja = rsCajaPrestada.getInt("id_intersucursalCaja");
                
                Object[] fila = {codigo, caja, id_cajaDeposito, id_intersucursalCaja};
                modeloCaja.addRow(fila);
            }
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }
    
    private void llenaComboDestino(int id_zona){
        arrayZonaDestino.clear();
        this.jComboDestino.removeAllItems();
        Iterator it = arrayZona.iterator();
        while (it.hasNext()) {
            ZonaData tmp = (ZonaData)it.next();
            if (tmp.getId_zona() != id_zona) {
                arrayZonaDestino.add(tmp);
                this.jComboDestino.addItem(tmp.getNombre());
            }
        }
        this.jComboDestino.setSelectedIndex(-1);
    }
    
    private String avisoError(){
        String error = "";
        
        if (this.jFecha.getDate() == null){
            error = "Falta cargar la fecha del remito";
            return error;
        }else if (this.jFecha.getDate().after(new Date())){
            error = "La fecha del remito no puede ser mayor a la actual";
            return error;
        }
        
        if (this.jTxtSucursal.getText().isEmpty() || this.jTxtNumero.getText().isEmpty()){
            error = "Falta cargar el número del remito";
            return error;
        }
        
        if (this.jComboOrigen.getSelectedIndex() < 0){
            error = "Falta cargar el origen del remito";
            return error;
        }
        
        if (this.jComboDestino.getSelectedIndex() < 0){
            error = "Falta cargar el destino del remito";
            return error;
        }
        
        if (modeloCajaSeleccion.getRowCount() <= 0){
            error = "No hay cajas cargadas";
        }
        
        return error;
    }
    
    private void secuencia(){
        if (avisoError().isEmpty()){
            int respuesta = JOptionPane.showConfirmDialog(this, "Confirma la carga del remito?", "Atencíon", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION){
                if (!existeRemito(this.jTxtSucursal.getText() + this.jTxtNumero.getText())){
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            IntersucursalDevolucion.this.avisoEspera = new AvisoEspera(null, false, 2);
                            IntersucursalDevolucion.this.avisoEspera.setVisible(true);
                            Thread performer = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    IntersucursalDevolucion.this.insertRemito();
                                    IntersucursalDevolucion.this.insertRemitoDetalle();
                                    IntersucursalDevolucion.this.devuelveCajaIntersucursal();
                                    IntersucursalDevolucion.this.insertIntersucursal();
                                    IntersucursalDevolucion.this.avisoEspera.setVisible(false);
                                }
                            }, "Performer");
                            performer.start();
                        }
                    });
//                    insertRemito();
//                    insertRemitoDetalle();
//                    devuelveCajaIntersucursal();
//                    insertIntersucursal();
                    salida = new Salida(null, true);
                    salida.setVisible(true);
                    if (salida.getSiNo()) {
                        this.rutaArchivo = salida.getRutaArchivo();
                        this.impresora = salida.getImpresora();
                        this.copias = salida.getCopias();
                        int s = salida.getTipoSalida();
                        imprime(s, false);
                    }
                    limpia();
                }else{
                    JOptionPane.showMessageDialog(this, "El remito ya existe", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }else{
            JOptionPane.showMessageDialog(this, avisoError(), "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    
    private void cargaDatosComposicion(Integer id_cajaDeposito){
        //this.arrayComponentes = this.detalleCajaRemito.cargaDatosComposicion(id_cajaDeposito, this.arrayComponentes, this.conexion, this.empresa);
    }
    
    private void eliminaDatosComposicion(Integer id_cajaDeposito){
        //this.arrayComponentes = this.detalleCajaRemito.eliminaDatosComposicion(id_cajaDeposito, this.arrayComponentes);
    }
    
    private void muestraDatosComposicion(Integer id_cajaDeposito, String nombreCaja){
        //this.arrayComponentes = this.detalleCajaRemito.muestraDatosComposicion(id_cajaDeposito,nombreCaja,this.arrayComponentes);
    }
    
    private void insertRemito(){
        ArrayList parametros = new ArrayList();
        
        int id_remito = 0;
        int id_presupuesto = 0;
        Long fecha = this.jFecha.getDate().getTime();
        int id_tipoComp = 18;
        String numComp = this.jTxtSucursal.getText() + this.jTxtNumero.getText();
        int id_entidad = 0;
        int id_proveedor = 0;
        int id_destino = 0;
        
        ZonaData d = (ZonaData) arrayZonaDestino.get(this.jComboDestino.getSelectedIndex());
        int id_zonaDestino = d.getId_zona();
        
        ZonaData z = (ZonaData) arrayZona.get(this.jComboOrigen.getSelectedIndex());
        id_zona = z.getId_zona();
        
        String observaciones = this.jTxtObservaciones.getText().trim();
        
        String cajas = "";
        for (int i = 0; i < modeloCajaSeleccion.getRowCount(); i++) {
            cajas += modeloCajaSeleccion.getValueAt(i, 0).toString() + (i == modeloCajaSeleccion.getRowCount() - 1 ? "" : " / ");
        }            
        
        String sets = "";
        
        parametros.add(id_remito);
        parametros.add(id_presupuesto);
        parametros.add(fecha);
        parametros.add(id_tipoComp);
        parametros.add(numComp);
        parametros.add(id_entidad);
        parametros.add(id_proveedor);
        parametros.add(id_destino);
        parametros.add(id_zonaDestino);
        parametros.add(id_zona);
        parametros.add(observaciones);
        parametros.add(cajas);
        parametros.add(sets);
        parametros.add(this.id_empresa);
        parametros.add(this.usuario);
        parametros.add(Principal.equipo);
        
        conexion.procAlmacenado("insertRemito", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    private void insertRemitoDetalle(){
        ArrayList parametros = new ArrayList();        
        
        parametros.add(this.id_empresa);

        rsUltimoRemito = conexion.funcAlmacenada("proximoRemito", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

        int id_remito = 0;
        try {
            rsUltimoRemito.next();
            id_remito = rsUltimoRemito.getInt(1);
        } catch (SQLException ex) {
            Utiles.enviaError(empresa, ex);
        }
        
        
        String dc = "C";
        Date venc = null;
        String observaciones = "Devolución Intersucursal RM-R " + this.jTxtSucursal.getText() + this.jTxtNumero.getText() 
            + "\nOrigen: " + this.jComboOrigen.getSelectedItem().toString() 
            + "\nDestino: " + this.jComboDestino.getSelectedItem().toString();

        ZonaData z = (ZonaData) arrayZona.get(this.jComboOrigen.getSelectedIndex());
        id_zona = z.getId_zona();
        
            
        Iterator it = arrayComponentes.iterator();
        while (it.hasNext()){
            try {
                parametros.clear();
                HashMap map = (HashMap) it.next();
                
                Integer cantidad = (Integer) map.get("cantidad");
                
                if (cantidad > 0) {
                    try {
                        venc = formatter.parse(map.get("vencimiento").toString());
                    } catch (Exception e) {
                        //No se ha ingresado una fecha válida
                    }
                    
                    parametros.add(id_remito);
                    parametros.add(map.get("id_producto"));
                    parametros.add(cantidad);
                    parametros.add(dc);
                    parametros.add(map.get("lote"));
                    parametros.add(map.get("serie"));
                    parametros.add(map.get("pm"));
                    parametros.add(venc == null ? (long)0 : venc.getTime());
                    parametros.add(Double.parseDouble(map.get("costo").toString()));
                    parametros.add(map.get("id_moneda"));
                    parametros.add(observaciones);
                    parametros.add(id_zona);
                    parametros.add(this.id_empresa);

                    conexion.procAlmacenado("insertRemitoDetalle", parametros, 
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                }                   
            } catch (Exception ex) {
                Utiles.enviaError(this.empresa,ex);
            }
        }
    }
    
    private void devuelveCajaIntersucursal(){
        ArrayList parametros = new ArrayList();
        for (int i = 0; i < modeloCajaSeleccion.getRowCount(); i++) {
            parametros.clear();
            
            int id_intersucursalCaja = (int)modeloCajaSeleccion.getValueAt(i, 3);
            parametros.add(id_intersucursalCaja);
            conexion.procAlmacenado("devuelveIntersucursalCaja", parametros, 
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        }
    }
    
    private void insertIntersucursal(){
        ArrayList parametros = new ArrayList();
        parametros.add(this.usuario);
        parametros.add(this.id_empresa);
        
        conexion.procAlmacenado("insertIntersucursal", parametros, 
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        for (int i = 0; i < modeloCajaSeleccion.getRowCount(); i++) {
            parametros.clear();
            
            int id_cajaDeposito = (int)modeloCajaSeleccion.getValueAt(i, 2);
            parametros.add(id_cajaDeposito);
            parametros.add("S");
            conexion.procAlmacenado("insertIntersucursalCaja", parametros, 
                        this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        }
    }
    
    private void imprime(int salida, boolean previa){
        Map param=new HashMap();
        param.put("fecha", this.jFecha.getDate());
        param.put("id_presupuesto", 0);
        param.put("entidad", "ENVÍA " + this.jComboOrigen.getSelectedItem().toString());
        param.put("lugarCirugia", "DEPOSITO " + this.jComboDestino.getSelectedItem().toString());
        param.put("profesional", "");
        param.put("paciente", "");
        param.put("telefono", "");
        param.put("dirLugarCirugia", "");
        param.put("numComp", this.jTxtSucursal.getText() + this.jTxtNumero.getText());
        param.put("tipo", "");
        param.put("observaciones", this.jTxtObservaciones.getText().trim());
        param.put("previa", previa);
        
        String cajas = "";
        for (int i = 0; i < modeloCajaSeleccion.getRowCount(); i++) {
            cajas += modeloCajaSeleccion.getValueAt(i, 0).toString() + (i == modeloCajaSeleccion.getRowCount() - 1 ? "" : " / ");
        }
        param.put("cajas", cajas);
            
        RemitoDataSource data = new RemitoDataSource();
        Iterator itClasiProducto = arrayClasi.iterator();
        while (itClasiProducto.hasNext()){
         data = new RemitoDataSource();  //Limpio el Datasource
         ClasiProductoData clasiData = (ClasiProductoData) itClasiProducto.next();
         String nombreClasi = clasiData.getNombre();
         param.put("tipo", nombreClasi);

         Iterator itDetalle = arrayComponentes.iterator();
         while (itDetalle.hasNext()){
             HashMap map = (HashMap)itDetalle.next();
             Integer cantidad = (Integer) map.get("cantidad");
             String tipo = map.get("tipo").toString();

             if (cantidad > 0 && tipo.equals(nombreClasi)) {
                 String codigo = map.get("codigo").toString();
                 String gtin = map.get("gtin").toString();
                 String nombre = map.get("nombre").toString();
                 String lote = map.get("lote").toString();
                 String serie = map.get("serie").toString();
                 String pm = map.get("pm").toString();

                 Date venc = null;
                 String vencimiento = map.get("vencimiento").toString();
                 try {
                     venc = formatter.parse(vencimiento);
                 } catch (Exception ex) {
                     //No se ha ingresado una fecha válida
                 }

                 ar.com.bosoft.Modelos.Remito nuevo = new Remito(cantidad, codigo, gtin, nombre, lote, serie, pm, venc);

                 data.add(nuevo);
             }   
         }
         if (data.listaSize() > 0) {
             Reporte reporte = new Reporte();
             String nombreReporte = "Remito";
             reporte.startReport(nombreReporte, param, data, salida, rutaArchivo, impresora, copias);
         }   
     }      
        
        if (data.listaSize() > 0) {
            Reporte reporte = new Reporte();
            String nombreReporte = "Remito";
            reporte.startReport(nombreReporte, param, data, salida, rutaArchivo, impresora, copias);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLocaleChooser1 = new com.toedter.components.JLocaleChooser();
        jLabel1 = new javax.swing.JLabel();
        jComboOrigen = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jComboDestino = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jFecha = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jFecha.getJCalendar().setTodayButtonVisible(true); 
        this.jFecha.getJCalendar().setTodayButtonText("Hoy"); 
        this.jFecha.getJCalendar().setWeekOfYearVisible(false);
        jLabel4 = new javax.swing.JLabel();
        jTxtSucursal = new javax.swing.JTextField();
        jTxtSucursal.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(4,true));
        jTxtNumero = new javax.swing.JTextField();
        jTxtNumero.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(8,true));
        jScrollPane4 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();
        jBtnSalir = new javax.swing.JButton();
        jBtnPrevia = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTablaCaja = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTablaCajaSeleccion = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jTxtBuscaCaja = new javax.swing.JTextField();
        jBtnComposicion = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();

        setTitle("Remito de devolución intersucursal");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jLabel1.setText("* Envía");

        jComboOrigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboOrigenActionPerformed(evt);
            }
        });

        jLabel2.setText("* Recibe");

        jComboDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboDestinoActionPerformed(evt);
            }
        });

        jLabel3.setText("* Fecha");

        jLabel4.setText("* Número");

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

        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObservaciones.setRows(5);
        jTxtObservaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Observaciones"));
        jScrollPane4.setViewportView(jTxtObservaciones);

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

        jPanel6.setBackground(new java.awt.Color(153, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cajas"));

        jTablaCaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "id_cajaDeposito", "id_intersucursalCaja"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
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
        jTablaCaja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaCajaMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTablaCaja);
        if (jTablaCaja.getColumnModel().getColumnCount() > 0) {
            jTablaCaja.getColumnModel().getColumn(0).setMinWidth(95);
            jTablaCaja.getColumnModel().getColumn(0).setPreferredWidth(95);
            jTablaCaja.getColumnModel().getColumn(0).setMaxWidth(95);
            jTablaCaja.getColumnModel().getColumn(2).setMinWidth(0);
            jTablaCaja.getColumnModel().getColumn(2).setPreferredWidth(0);
            jTablaCaja.getColumnModel().getColumn(2).setMaxWidth(0);
            jTablaCaja.getColumnModel().getColumn(3).setMinWidth(0);
            jTablaCaja.getColumnModel().getColumn(3).setPreferredWidth(0);
            jTablaCaja.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        jTablaCajaSeleccion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "id_cajaDeposito", "id_intersucursalCaja"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
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
        jTablaCajaSeleccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaCajaSeleccionMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jTablaCajaSeleccion);
        if (jTablaCajaSeleccion.getColumnModel().getColumnCount() > 0) {
            jTablaCajaSeleccion.getColumnModel().getColumn(0).setMinWidth(95);
            jTablaCajaSeleccion.getColumnModel().getColumn(0).setPreferredWidth(95);
            jTablaCajaSeleccion.getColumnModel().getColumn(0).setMaxWidth(95);
            jTablaCajaSeleccion.getColumnModel().getColumn(2).setMinWidth(0);
            jTablaCajaSeleccion.getColumnModel().getColumn(2).setPreferredWidth(0);
            jTablaCajaSeleccion.getColumnModel().getColumn(2).setMaxWidth(0);
            jTablaCajaSeleccion.getColumnModel().getColumn(3).setMinWidth(0);
            jTablaCajaSeleccion.getColumnModel().getColumn(3).setPreferredWidth(0);
            jTablaCajaSeleccion.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        jLabel13.setText("Busca");

        jBtnComposicion.setText("Cargar composición");
        jBtnComposicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnComposicionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtBuscaCaja))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnComposicion)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTxtBuscaCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnComposicion))
                .addGap(3, 3, 3)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnPrevia)
                        .addGap(361, 361, 361)
                        .addComponent(jBtnGuardar)
                        .addGap(323, 323, 323)
                        .addComponent(jBtnSalir))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(105, 105, 105)
                                                .addComponent(jLabel1))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jTxtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel2)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jComboOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 938, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTxtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jComboOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnPrevia)
                    .addComponent(jBtnSalir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        try {
            if (this.rsCajaPrestada != null){
                this.rsCajaPrestada.close();
            }
            if (this.rsCajaComposicion != null){
                this.rsCajaComposicion.close();
            }
            if (this.rsTraeRemito != null){
                this.rsTraeRemito.close();
            }
            if (this.rsUltimoRemito != null){
                this.rsUltimoRemito.close();
            }
            this.dispose();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jComboOrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboOrigenActionPerformed
        try{
            ZonaData tmp = (ZonaData) arrayZona.get(this.jComboOrigen.getSelectedIndex());
            llenaCajas(tmp.getId_zona());
            llenaComboDestino(tmp.getId_zona());
        }catch (Exception ex){
            //Solo para inicializar el combo. 
        }  
    }//GEN-LAST:event_jComboOrigenActionPerformed

    private void jBtnPreviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPreviaActionPerformed
        imprime(0, true);
    }//GEN-LAST:event_jBtnPreviaActionPerformed

    private void jComboDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboDestinoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboDestinoActionPerformed

    private void jTablaCajaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaCajaMouseClicked
        if (evt.getClickCount() == 2) {
            int fila = jTablaCaja.convertRowIndexToModel(jTablaCaja.getSelectedRow());
            String codigo = modeloCaja.getValueAt(fila, 0).toString();
            String nombre = modeloCaja.getValueAt(fila, 1).toString();
            int id_cajaDeposito = (int) modeloCaja.getValueAt(fila, 2);
            int id_intersucursalCaja = (int) modeloCaja.getValueAt(fila, 3);
            
            if (!Utiles.existeEnModelo(modeloCajaSeleccion, 2, id_cajaDeposito)) {
                cargaDatosComposicion(id_cajaDeposito);
                Object[] nuevo = {codigo, nombre, id_cajaDeposito, id_intersucursalCaja};
                modeloCajaSeleccion.addRow(nuevo);
            }else{
                JOptionPane.showMessageDialog(this, "Ya ha seleccionado esta caja", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }            
        }
    }//GEN-LAST:event_jTablaCajaMouseClicked

    private void jTablaCajaSeleccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaCajaSeleccionMouseClicked
        if (evt.getClickCount() == 2) {
            int fila = jTablaCajaSeleccion.convertRowIndexToModel(jTablaCajaSeleccion.getSelectedRow());
            int id_cajaDeposito = (int) modeloCajaSeleccion.getValueAt(fila, 2);
            eliminaDatosComposicion(id_cajaDeposito);
            modeloCajaSeleccion.removeRow(fila);
        }
    }//GEN-LAST:event_jTablaCajaSeleccionMouseClicked

    private void jBtnComposicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnComposicionActionPerformed
        if (jTablaCajaSeleccion.getSelectedRow() >= 0) {
            int fila = jTablaCajaSeleccion.convertRowIndexToModel(jTablaCajaSeleccion.getSelectedRow());
        
            int id_cajaDeposito = (int)modeloCajaSeleccion.getValueAt(fila, 2);
            String nombreCaja = modeloCajaSeleccion.getValueAt(fila, 0) + " - " + modeloCajaSeleccion.getValueAt(fila, 1).toString();
            muestraDatosComposicion(id_cajaDeposito, nombreCaja);
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione una caja", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnComposicionActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        secuencia();
    }//GEN-LAST:event_jBtnGuardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnComposicion;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnPrevia;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JComboBox jComboDestino;
    private javax.swing.JComboBox jComboOrigen;
    private com.toedter.calendar.JDateChooser jFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private com.toedter.components.JLocaleChooser jLocaleChooser1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTable jTablaCaja;
    private javax.swing.JTable jTablaCajaSeleccion;
    private javax.swing.JTextField jTxtBuscaCaja;
    private javax.swing.JTextField jTxtNumero;
    private javax.swing.JTextArea jTxtObservaciones;
    private javax.swing.JTextField jTxtSucursal;
    // End of variables declaration//GEN-END:variables
}
