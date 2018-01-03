/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.formularios;

import ar.com.bosoft.DataSources.PresupuestoProdDataSource;
import ar.com.bosoft.Modelos.PresupuestoProd;
import ar.com.bosoft.Utilidades.NumberImput;
import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.Utilidades.SeleccionImp;
import ar.com.bosoft.buscadores.BuscaPresupuesto;
import ar.com.bosoft.buscadores.BuscaProductoFact;
import ar.com.bosoft.clases.NumeroALetra;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.EntidadCRUD;
import ar.com.bosoft.crud.FormaPagoCRUD;
import ar.com.bosoft.crud.MantenimientoCRUD;
import ar.com.bosoft.crud.MotivoCRUD;
import ar.com.bosoft.crud.NotaPresuCRUD;
import ar.com.bosoft.crud.PlazoCRUD;
import ar.com.bosoft.crud.ProductoFactCRUD;
import ar.com.bosoft.crud.ProfesionalCRUD;
import ar.com.bosoft.crud.TipoOperacionCRUD;
import ar.com.bosoft.crud.VendedorCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.EntidadData;
import ar.com.bosoft.data.MantenimientoData;
import ar.com.bosoft.data.MotivoData;
import ar.com.bosoft.data.NotaPresuData;
import ar.com.bosoft.data.PlazoData;
import ar.com.bosoft.data.ProductoFactData;
import ar.com.bosoft.data.ProfesionalData;
import ar.com.bosoft.data.TipoOperacionData;
import ar.com.bosoft.data.VendedorData;
import ar.com.bosoft.data.ZonaData;
import ar.com.bosoft.formatosCampos.ConMascara;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.conexion.ActiveDatabase;
import ar.com.bosoft.crud.ProveedorCRUD;
import ar.com.bosoft.data.ProveedorData;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import matera.cache.CacheEntidad;
import matera.cache.CacheProfesional;
import matera.gui.combobox.ComboBoxMgr;
import matera.gui.dialog.PresupuestoEventosDialog;
import matera.helpers.LogHelper;
import matera.services.EmailService;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class Presupuesto extends javax.swing.JInternalFrame {
    Conexion conexion;
    int id_empresa, id_productoFact, indiceTabla, copias;
    String empresa, usuario, estado, notaDefault, impresora, rutaArchivo, id_presupuesto;
    ZonaCRUD zonaCRUD;
    EntidadCRUD entidadCRUD;
    ProfesionalCRUD profesionalCRUD;
    VendedorCRUD vendedorCRUD;
    TipoOperacionCRUD tipoOperacionCRUD;
    FormaPagoCRUD formaPagoCRUD;
    PlazoCRUD plazoCRUD;
    MantenimientoCRUD mantenimientoCRUD;
    MotivoCRUD motivoCRUD;
    NotaPresuCRUD notaPresuCRUD;
    DefaultTableModel modelo;
    ProductoFactCRUD productoFactCRUD;
    ProveedorCRUD proveedorCRUD;
    boolean hayPresupuesto;
    
    ArrayList arrayZona, arrayEntidad, arrayLugarCirugia, arrayId_entidadLugar, arrayProfesional, arrayId_profesional, 
            arrayVendedor, arrayId_vendedor, arrayTipoOperacion, arrayFormaPago, arrayPlazo, arrayMantenimiento, arrayMotivo,
            arrayProductoFact, arrayProveedor;
    
    ResultSet rsConsulta;
    
    ConMascara mascara;
    
    BuscaPresupuesto buscaPresupuesto;
    BuscaProductoFact buscaProductoFact;
    SeleccionImp seleccionImp;
    NumeroALetra numALetra;
    boolean puedeModificar = true;
    boolean withMod = false;
    boolean isEntidadFull = false;
    boolean isPro1Full = false;
    
    Salida salida = new Salida(null, true);
    
    public Presupuesto(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        this.entidadCRUD = new EntidadCRUD(conexion, id_empresa, empresa);
        this.profesionalCRUD = new ProfesionalCRUD(conexion, id_empresa, empresa);
        this.vendedorCRUD = new VendedorCRUD(conexion, id_empresa, empresa);
        this.tipoOperacionCRUD = new TipoOperacionCRUD(conexion, empresa);
        this.formaPagoCRUD = new FormaPagoCRUD(conexion, empresa);
        this.plazoCRUD = new PlazoCRUD(conexion, empresa);
        this.mantenimientoCRUD = new MantenimientoCRUD(conexion, empresa);
        this.motivoCRUD = new MotivoCRUD(conexion, empresa);
        this.notaPresuCRUD = new NotaPresuCRUD(conexion, id_empresa, empresa, usuario);
        this.productoFactCRUD = new ProductoFactCRUD(conexion, empresa);
        this.proveedorCRUD = new ProveedorCRUD(conexion, id_empresa, empresa);
        
        this.seleccionImp = new SeleccionImp(null, true);
        this.numALetra = new NumeroALetra();
        
        this.arrayId_entidadLugar = new ArrayList();
        this.arrayId_profesional = new ArrayList();
        this.arrayId_vendedor = new ArrayList();
        
        this.mascara = new ConMascara();
        
        initComponents();
        
        modelo = (DefaultTableModel) this.jTabla.getModel();
        this.jTabla.setModel(modelo);
        
        consultaProductosFact();
        consultaProveedor();
        consultaZona();
        consultaEntidad();
        consultaProfesional();
        consultaVendedor();
        consultaTipoOperacion();
        consultaFormaPago();
        consultaPlazoEntrega();
        consultaMantenimientoOferta();
        consultaMotivo();
        
        llenaComboZona();  
        llenaComboProveedor();
        llenaComboTipoOperacion();
        //llenaComboFormaPago();
        llenaComboPlazo();
        llenaComboMantenimiento();
        llenaComboMotivo(); 
        llenaNota();
        limpia();
        pintaEstado();
        this.withMod = withMod;
    }
    
    public static String getEstadoPresupuesto(String s){
        String estado = "";
        switch (s){
            case "":
                estado = "";
                break;
            case "P":
                estado = "PENDIENTE";
                break;
            case "A":
                estado = "APROBADO";

                break;
            case "S":
                estado = "SUSPENDIDO";
                break;                
            case "C":
                estado = "CONFIRMADO";
                break;
            case "R":
                estado = "RECHAZADO";
                break;
            case "N":
                estado = "ANULADO";
                break;
            case "Z":
                estado = "FINALIZADO";
                break;
        }        
        return estado;
    }    
    
    public void setModifiable(boolean b){
        withMod = b;
    }

    private void consultaZona(){
        arrayZona = zonaCRUD.consulta(true);
    }
    
    private void consultaEntidad(){
        arrayEntidad = entidadCRUD.consulta(true);
    }
    
    private void consultaProfesional(){
        arrayProfesional = profesionalCRUD.consulta(true);
    }
    
    private void consultaVendedor(){
        arrayVendedor = vendedorCRUD.consulta(true);
    }
    
    private void consultaTipoOperacion(){
        arrayTipoOperacion = tipoOperacionCRUD.consulta();
    }
    
    private void consultaFormaPago(){
        arrayFormaPago = formaPagoCRUD.consulta(true);
    }
    
    private void consultaPlazoEntrega(){
        arrayPlazo = plazoCRUD.consulta(true);
    }
    
    private void consultaMantenimientoOferta(){
        arrayMantenimiento = mantenimientoCRUD.consulta(true);
    }
    
    private void consultaMotivo(){
        arrayMotivo = motivoCRUD.consulta();
    }
    
    private void consultaProveedor(){
        arrayProveedor = this.proveedorCRUD.consulta(true);
    }
    
    private void llenaComboZona(){
        if (arrayZona.isEmpty()){
            JOptionPane.showMessageDialog(this, "No hay zonas habilitadas", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }else{
            Iterator i = arrayZona.iterator();
            while (i.hasNext()){
                ZonaData tmp = (ZonaData) i.next();
                this.jComboZona.addItem(tmp.getNombre());
            }
        }
    }
    
    private void llenaComboProveedor(){
        Iterator i = arrayProveedor.iterator();
        while (i.hasNext()) {
            ProveedorData p = (ProveedorData) i.next();
            this.jComboProveedor.addItem(p.getNombre());
        }
    }
    
    private void llenaComboEntidadLugar(int indiceComboZona){
//        this.arrayId_entidadLugar.clear();
//        this.jComboEntidad.removeAllItems();
//        this.jComboLugarCirugia.removeAllItems();
//        if (indiceComboZona >= 0){
//            ZonaData z = (ZonaData) arrayZona.get(indiceComboZona);
//            int id_zona = z.getId_zona();
//            Iterator i = arrayEntidad.iterator();
//            while (i.hasNext()){
//                EntidadData item = (EntidadData) i.next();
//                if (item.getId_zona() == id_zona){
//                    this.jComboEntidad.addItem(item.getNombre());
//                    this.jComboLugarCirugia.addItem(item.getNombre());
//                    this.arrayId_entidadLugar.add(item.getId_entidad());
//                }
//            }
//        }
//        this.jComboEntidad.setSelectedIndex(-1);
//        this.jComboLugarCirugia.setSelectedIndex(-1);
        
        this.arrayId_entidadLugar.clear();
        this.jComboEntidad.removeAllItems();
        this.jComboLugarCirugia.removeAllItems();
        Iterator i = arrayEntidad.iterator();
        while (i.hasNext()){
            EntidadData item = (EntidadData) i.next();
            this.jComboEntidad.addItem(item.getNombre());
            this.jComboLugarCirugia.addItem(item.getNombre());
            this.arrayId_entidadLugar.add(item.getId_entidad());
        }
        
        this.jComboEntidad.setSelectedIndex(-1);
        this.jComboLugarCirugia.setSelectedIndex(-1);
        
        ItemListener cambioCombo = (ItemEvent e) -> {
            if(!isEntidadFull){
                if(jComboEntidad.getSelectedIndex() > 0 && e.getStateChange() == ItemEvent.SELECTED){
                    ComboBoxMgr.fillFormaDePagoCombo(jComboFormaPago, CacheEntidad.instance()
                            .getEntidad(jComboEntidad.getSelectedItem().toString()).getIdFormapago());
                }
            }
            };

        jComboEntidad.addItemListener(cambioCombo);
        
    }
    
    private void llenaComboProfesional(int indiceComboZona){
        this.arrayId_profesional.clear();
        this.jComboProfesional1.removeAllItems();
        this.jComboProfesional2.removeAllItems();
        this.jComboProfesional3.removeAllItems();
        this.jComboProfesional4.removeAllItems();
        this.jComboProfesional5.removeAllItems();
        if (indiceComboZona >= 0){
            ZonaData z = (ZonaData) arrayZona.get(indiceComboZona);
            int id_zona = z.getId_zona();
            Iterator i = arrayProfesional.iterator();
            while (i.hasNext()){
                ProfesionalData item = (ProfesionalData) i.next();
                if (item.getId_zona() == id_zona){
                    this.jComboProfesional1.addItem(item.getNombre());
                    this.jComboProfesional2.addItem(item.getNombre());
                    this.jComboProfesional3.addItem(item.getNombre());
                    this.jComboProfesional4.addItem(item.getNombre());
                    this.jComboProfesional5.addItem(item.getNombre());
                    this.arrayId_profesional.add(item.getId_profesional());
                }
            }
        }
        this.jComboProfesional1.setSelectedIndex(-1);
        this.jComboProfesional2.setSelectedIndex(-1);
        this.jComboProfesional3.setSelectedIndex(-1);
        this.jComboProfesional4.setSelectedIndex(-1);
        this.jComboProfesional5.setSelectedIndex(-1);
        
        ItemListener cambiaPro1 = (ItemEvent e) -> {
            if(!isPro1Full){
                if(jComboProfesional1.getSelectedIndex() > 0 && e.getStateChange() == ItemEvent.SELECTED
                        && this.jComboZona.getSelectedIndex() == 1){
                    ComboBoxMgr.fillVendedorCombo(jComboVendedor, this.jComboZona.getSelectedIndex(),CacheProfesional.instance()
                            .getProfesional(jComboProfesional1.getSelectedItem().toString()));
                }
            }
        };

        jComboProfesional1.addItemListener(cambiaPro1);
    }
    
    private void llenaComboVendedor(int indiceComboZona){
        this.arrayId_vendedor.clear();
        this.jComboVendedor.removeAllItems();
        if (indiceComboZona >= 0){
            ZonaData z = (ZonaData) arrayZona.get(indiceComboZona);
            int id_zona = z.getId_zona();
            Iterator i = arrayVendedor.iterator();
            while (i.hasNext()){
                VendedorData item = (VendedorData) i.next();
                if (item.getId_zona() == id_zona &&
                        item.getId_especialidad() == 13){
                    this.jComboVendedor.addItem(item.getNombre());
                    this.arrayId_vendedor.add(item.getId_vendedor());
                }
            }
        }
        this.jComboVendedor.setSelectedIndex(-1);
    }
    
    private void llenaComboTipoOperacion(){
        if (arrayTipoOperacion.isEmpty()){
            JOptionPane.showMessageDialog(this, "No hay tipos de operación habilitados", "Atención", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Iterator i = arrayTipoOperacion.iterator();
        while (i.hasNext()){
            TipoOperacionData tmp = (TipoOperacionData) i.next();
            this.jComboTipoOperacion.addItem(tmp.getNombre());
        }
    }
    /*
    private void llenaComboFormaPago(){
        Iterator i = arrayFormaPago.iterator();
        while (i.hasNext()){
            FormaPagoData tmp = (FormaPagoData) i.next();
            this.jComboFormaPago.addItem(tmp.getNombre());
        }
        this.jComboFormaPago.addItem("-- Otro --");
    }
    */
    private void llenaComboPlazo(){
        Iterator i = arrayPlazo.iterator();
        while (i.hasNext()){
            PlazoData tmp = (PlazoData) i.next();
            this.jComboPlazo.addItem(tmp.getNombre());
        }
        this.jComboPlazo.addItem("-- Otro --");
    }
    
    private void llenaComboMantenimiento(){
        Iterator i = arrayMantenimiento.iterator();
        while (i.hasNext()){
            MantenimientoData tmp = (MantenimientoData) i.next();
            this.jComboMantenimiento.addItem(tmp.getNombre());
        }
        this.jComboMantenimiento.addItem("-- Otro --");
    }
    
    private void llenaComboMotivo(){
        Iterator i = arrayMotivo.iterator();
        while (i.hasNext()){
            MotivoData tmp = (MotivoData) i.next();
            this.jComboMotivo.addItem(tmp.getNombre());
        }
        this.jComboMotivo.addItem("-- Otro --");
    }
    
    private void llenaNota(){
        NotaPresuData tmp = notaPresuCRUD.consulta();
        this.notaDefault = tmp.getNota();
    }
    
    private void consultaProductosFact(){
        arrayProductoFact = productoFactCRUD.consulta(true, 1);
        if (arrayProductoFact.isEmpty()){
            JOptionPane.showMessageDialog(this, "No hay productos habilitados", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void limpia(){
        this.jTxtId_presupuesto.setText("");
        this.estado = "";
        this.rutaArchivo = "";
        this.impresora = "";
        this.indiceTabla = -1;
        this.copias = 1;
        this.isEntidadFull = false;
        this.isPro1Full = false;
        
        this.jFecha.setDate(new Date());
        this.jComboZona.setSelectedIndex(arrayZona.isEmpty() ? -1 : 0);
        this.jComboEntidad.setSelectedIndex(-1);
        this.jComboLugarCirugia.setSelectedIndex(-1);
        this.jCheckAcompañamiento.setSelected(false);
        
        this.jFechaApertura.setDate(null);
        this.jFmtHoraApertura.setText("");
        this.jTxtExpediente.setText("");
        this.jTxtContratacion.setText("");
        
        this.jTxtPaciente.setText("");
        this.jCheckVip.setSelected(false);
        this.jTxtDni.setText("");
        this.jTxtDireccion.setText("");
        this.jTxtTelefono.setText("");
        
        this.jFmtCantidad.setValue(1);
        this.jTxtCodigo.setText("");
        this.jLblNombre.setText("");
        this.jComboProveedor.setSelectedIndex(-1);
        this.jTxtObsProducto.setText("");
        this.jFmtPxUnit.setValue(0.00);
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
        this.jFmtTotal.setValue(0.00);
        
        this.jComboProfesional1.setSelectedIndex(-1);
        this.jFmtRm1.setValue(0.00);
        this.jComboProfesional2.setSelectedIndex(-1);
        this.jFmtRm2.setValue(0.00);
        this.jComboProfesional3.setSelectedIndex(-1);
        this.jFmtRm3.setValue(0.00);
        this.jComboProfesional4.setSelectedIndex(-1);
        this.jFmtRm4.setValue(0.00);
        this.jComboProfesional5.setSelectedIndex(-1);
        this.jFmtRm5.setValue(0.00);
        this.jComboVendedor.setSelectedIndex(-1);
        this.jComboTipoOperacion.setSelectedIndex(-1);
        
        //this.jComboFormaPago.setSelectedIndex(-1);
        this.jTxtFormaPagoOtro.setText("");
        this.jComboPlazo.setSelectedIndex(-1);
        this.jTxtPlazoOtro.setText("");
        this.jComboMantenimiento.setSelectedIndex(-1);
        this.jTxtMantenimientoOtro.setText("");
        this.jTxtExtra.setText("");
        
        this.jTxtObservaciones.setText("");
        
        this.jComboMotivo.setEnabled(false);
        this.jTxtMotivo.setEnabled(false);
        this.jComboMotivo.setSelectedIndex(-1);
        this.jTxtMotivo.setText("");
        
        this.jFmtCostoVenta.setText("");
        
        this.puedeModificar = true;
        
        this.id_presupuesto = "";
        
        pintaEstado();
        
    }
    
    private void pintaEstado(){
        this.jLblEstado.setForeground(Color.BLACK);
        this.jLblEstado.setOpaque(true);
        switch (this.estado){
            case "":
                this.jLblEstado.setText("");
                this.jLblEstado.setOpaque(false);
                break;
            case "P":
                this.jLblEstado.setText("PENDIENTE");
                this.jLblEstado.setBackground(Color.CYAN);
                break;
            case "A":
                this.jLblEstado.setText("APROBADO");
                this.jLblEstado.setBackground(Color.GREEN);
                break;
            case "S":
                this.jLblEstado.setText("SUSPENDIDO");
                this.jLblEstado.setBackground(Color.YELLOW);
                break;                
            case "C":
                this.jLblEstado.setText("CONFIRMADO");
                this.jLblEstado.setBackground(Color.PINK);
                break;
            case "R":
                this.jLblEstado.setText("RECHAZADO");
                this.jLblEstado.setBackground(Color.RED);
                break;
            case "N":
                this.jLblEstado.setText("ANULADO");
                this.jLblEstado.setBackground(Color.LIGHT_GRAY);
                break;
            case "Z":
                this.jLblEstado.setText("FINALIZADO");
                this.jLblEstado.setBackground(Color.BLUE);
                this.jLblEstado.setForeground(Color.WHITE);
                break;
        }
        this.habilita();
        this.jBtnBusca.setEnabled(true);
        this.jBtnAtras.setEnabled(true);
        this.jBtnAdelante.setEnabled(true);
    }
    
    public void setId_presupuesto(String id_presupuesto){
        this.limpia();
        this.jTxtId_presupuesto.setText(id_presupuesto);
        traePresupuesto();
        this.puedeModificar = false;
        this.habilita();
    }
    
    private void habilita(){
        if (!withMod){
            this.jBtnBusca.setEnabled(puedeModificar);
            this.jBtnAtras.setEnabled(puedeModificar);
            this.jBtnAdelante.setEnabled(puedeModificar);
            this.jFecha.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboZona.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboEntidad.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboLugarCirugia.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jFechaApertura.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jFmtHoraApertura.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtExpediente.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtContratacion.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jCheckAcompañamiento.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtPaciente.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jCheckVip.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtDni.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtTelefono.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtDireccion.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jFmtCantidad.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtCodigo.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboProveedor.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtObsProducto.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jFmtPxUnit.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jMenuItemElimina.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jMenuItemModifica.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboProfesional1.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboProfesional2.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboProfesional3.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboProfesional4.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboProfesional5.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jFmtRm1.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jFmtRm2.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jFmtRm3.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jFmtRm4.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jFmtRm5.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboVendedor.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboTipoOperacion.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboFormaPago.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtFormaPagoOtro.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboPlazo.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtPlazoOtro.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboMantenimiento.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtMantenimientoOtro.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtExtra.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jBtnAprobar.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jBtnRechazar.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jBtnGuardar.setEnabled(puedeModificar);
            this.jBtnAnular.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jBtnRecuperar.setEnabled(!puedeModificar ? false : this.estado.matches("N|R"));
            this.jBtnLimpiar.setEnabled(puedeModificar);
            this.jTxtObservaciones.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jFmtCostoVenta.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            if(Principal.usuarioData.getId_usuario() == 14 || Principal.usuarioData.getId_usuario()  == 9)
                this.jFmtCostoVenta.setEnabled(true);
            this.jComboMotivo.setEnabled(false);
            this.jTxtMotivo.setEnabled(false);            
            /*
            this.jBtnBusca.setEnabled(puedeModificar);
            this.jBtnAtras.setEnabled(puedeModificar);
            this.jBtnAdelante.setEnabled(puedeModificar);
            this.jFecha.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jComboZona.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jComboEntidad.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jComboLugarCirugia.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jFechaApertura.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jFmtHoraApertura.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jTxtExpediente.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jTxtContratacion.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jCheckAcompañamiento.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jTxtPaciente.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jCheckVip.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jTxtDni.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jTxtTelefono.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jTxtDireccion.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jFmtCantidad.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jTxtCodigo.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jComboProveedor.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jTxtObsProducto.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jFmtPxUnit.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jMenuItemElimina.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jMenuItemModifica.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jComboProfesional1.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jComboProfesional2.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jComboProfesional3.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jComboProfesional4.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jComboProfesional5.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jFmtRm1.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jFmtRm2.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jFmtRm3.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jFmtRm4.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jFmtRm5.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jComboVendedor.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jComboTipoOperacion.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jComboFormaPago.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jTxtFormaPagoOtro.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jComboPlazo.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jTxtPlazoOtro.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jComboMantenimiento.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jTxtMantenimientoOtro.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jTxtExtra.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jBtnAprobar.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jBtnRechazar.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jBtnGuardar.setEnabled(puedeModificar);
            this.jBtnAnular.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jBtnRecuperar.setEnabled(!puedeModificar ? false : (this.estado.equals("N") || this.estado.equals("R")));
            this.jBtnLimpiar.setEnabled(puedeModificar);
            this.jTxtObservaciones.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jFmtCostoVenta.setEnabled(!puedeModificar ? false : (this.estado.equals("") || this.estado.equals("P")));
            this.jComboMotivo.setEnabled(false);
            this.jTxtMotivo.setEnabled(false);
            */
        }
        else{
            this.jBtnBusca.setEnabled(puedeModificar);
            this.jBtnAtras.setEnabled(puedeModificar);
            this.jBtnAdelante.setEnabled(puedeModificar);
            this.jFecha.setEnabled(!puedeModificar ? false : (this.estado.matches("^$|P|A|S|C|Z")));
            this.jComboZona.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboEntidad.setEnabled(!puedeModificar ? false : (this.estado.matches("^$|P|A|S|C|Z")));
            this.jComboLugarCirugia.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jFechaApertura.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jFmtHoraApertura.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtExpediente.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtContratacion.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jCheckAcompañamiento.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtPaciente.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S|C"));
            this.jCheckVip.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtDni.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S|C"));
            this.jTxtTelefono.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S|C"));
            this.jTxtDireccion.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S|C"));
            this.jFmtCantidad.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jTxtCodigo.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jTxtObsProducto.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jFmtPxUnit.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jMenuItemElimina.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jMenuItemModifica.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jComboProfesional1.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jComboProfesional2.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jComboProfesional3.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jComboProfesional4.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jComboProfesional5.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jFmtRm1.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jFmtRm2.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jFmtRm3.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jFmtRm4.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jFmtRm5.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jComboVendedor.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboTipoOperacion.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S|C|Z"));
            this.jComboFormaPago.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtFormaPagoOtro.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboPlazo.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtPlazoOtro.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jComboMantenimiento.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtMantenimientoOtro.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jTxtExtra.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jBtnAprobar.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|S"));
            this.jBtnRechazar.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jBtnGuardar.setEnabled(puedeModificar);
            this.jBtnAnular.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P|A|S"));
            this.jBtnRecuperar.setEnabled(!puedeModificar ? false : this.estado.matches("N|R"));
            this.jBtnLimpiar.setEnabled(puedeModificar);
            this.jTxtObservaciones.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            this.jFmtCostoVenta.setEnabled(!puedeModificar ? false : this.estado.matches("^$|P"));
            if(Principal.usuarioData.getId_usuario() == 14 || Principal.usuarioData.getId_usuario()  == 9)
                this.jFmtCostoVenta.setEnabled(true);
            this.jComboMotivo.setEnabled(false);
            this.jTxtMotivo.setEnabled(false);
        }
    }
    
    private boolean validaProducto(){
        return Integer.parseInt(this.jFmtCantidad.getValue().toString()) > 0 &&
                !this.jTxtCodigo.getText().isEmpty() &&
                Double.parseDouble(this.jFmtPxUnit.getValue().toString()) > 0;
    }
    
    private void cargaProducto(){
        RoundingMode round = RoundingMode.HALF_UP;
        int cantidad = Integer.parseInt(this.jFmtCantidad.getValue().toString());
        String codigo = this.jTxtCodigo.getText();
        String nombre = this.jLblNombre.getText();
        String proveedor = this.jComboProveedor.getSelectedItem().toString();
        String obsProducto = this.jTxtObsProducto.getText();
        Double pxUnit = Double.parseDouble(this.jFmtPxUnit.getValue().toString());
        
        BigDecimal c = new BigDecimal(cantidad);
        BigDecimal px = new BigDecimal(pxUnit);
        BigDecimal rmProd = new BigDecimal(0.00);
        BigDecimal subtotal = c.multiply(px).setScale(2, round);
        
        int id_proveedor = 0;
        if (this.jComboProveedor.getSelectedIndex() >= 0) {
            ProveedorData p = (ProveedorData) arrayProveedor.get(this.jComboProveedor.getSelectedIndex());
            id_proveedor = p.getId_proveedor();
        }
        
        Object[] fila  = {cantidad, codigo, nombre, proveedor, obsProducto,
                            pxUnit, rmProd, subtotal, this.id_productoFact, id_proveedor};
        if (indiceTabla >= 0) {
            modelo.removeRow(indiceTabla);
        }
        
        modelo.addRow(fila);
        calculaTotal();
        
        this.jFmtCantidad.setValue(1);
        this.jTxtCodigo.setText("");
        this.jLblNombre.setText("");
        this.jComboProveedor.setSelectedIndex(-1);
        this.jTxtObsProducto.setText("");
        this.jFmtPxUnit.setValue(0.00);
        this.jTxtCodigo.setText("");
        this.indiceTabla = -1;
    }
    
    private void calculaTotal(){
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < modelo.getRowCount(); i++){
            total = total.add(new BigDecimal(Double.parseDouble(modelo.getValueAt(i, modelo.findColumn("Subtotal ($)")).toString())));
        }
        this.jFmtTotal.setValue(total);
    }
    
    private boolean validaObligatorios(){
        boolean b1 = this.jFecha.getDate() != null &&
                this.jComboZona.getSelectedIndex() >= 0 &&
                this.jComboEntidad.getSelectedIndex() >= 0 &&
                Double.parseDouble(this.jFmtTotal.getValue().toString()) > 0 &&
                this.jComboVendedor.getSelectedIndex() >= 0 &&
                this.jComboTipoOperacion.getSelectedIndex() >= 0 &&
                !this.jFmtCostoVenta.getText().isEmpty();
        
        //boolean b2 =  this.jComboFormaPago.getSelectedIndex() == arrayFormaPago.size() - 1 ? this.jTxtFormaPagoOtro.getText().isEmpty() : true;
        boolean b3 =  this.jComboPlazo.getSelectedIndex() == arrayPlazo.size() - 1 ? this.jTxtPlazoOtro.getText().isEmpty() : true;
        boolean b4 =  this.jComboMantenimiento.getSelectedIndex() == arrayMantenimiento.size() - 1 ? this.jTxtMantenimientoOtro.getText().isEmpty() : true;
        //boolean b5 = this.estado.equals("N") ? (this.jComboMotivo.getSelectedIndex() >= 0) : true;
        return b1 && b3 && b4;
    }
    
    private void confirmacion(String tipo){
        String mensaje = "Está seguro de ";
        switch (tipo){
            case "A":
                mensaje += "aprobar ";
                break;
            case "R":
                mensaje += "rechazar ";
                break;
            case "N":
                mensaje += "anular ";
                break;
            default:
                mensaje += "recuperar ";
                break;
        }
        mensaje += "este presupuesto?";
        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION){
            this.estado = tipo;
            this.jComboMotivo.setEnabled(tipo.equals("N"));
            this.jComboMotivo.setSelectedIndex(tipo.equals("N") ? 0 : -1);
            this.jTxtMotivo.setEnabled(tipo.equals("N"));
            JOptionPane.showMessageDialog(this, "Recuerde guardar el presupuesto para aplicar los cambios", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private String traePresupuesto(){
        String result = "";
        try{
            int id = Integer.parseInt(this.jTxtId_presupuesto.getText().trim());
            ArrayList parametros = new ArrayList();
            parametros.add(id);
            rsConsulta = conexion.procAlmacenado("traePresupuesto", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            limpia();
            this.jTxtId_presupuesto.setText(String.valueOf(id));
            rsConsulta.beforeFirst();
            if (rsConsulta.next()){
                result = rsConsulta.getString("id_presupuesto");
                this.jTxtId_presupuesto.setText(rsConsulta.getString("id_presupuesto"));
                this.estado = rsConsulta.getString("estado");
                this.jFecha.setDate(rsConsulta.getDate("fecha"));
                
                this.jComboZona.setSelectedItem(rsConsulta.getString("zona"));
                this.jComboZonaActionPerformed(null);
                Integer fp = rsConsulta.getInt("id_formaPago");
                ComboBoxMgr.fillFormaDePagoCombo(this.jComboFormaPago, fp);
                isEntidadFull = true;
                isPro1Full = true;
                this.jComboEntidad.setSelectedItem(rsConsulta.getString("entidad"));
                this.jComboLugarCirugia.setSelectedItem(rsConsulta.getString("lugar"));
                this.jCheckAcompañamiento.setSelected(rsConsulta.getString("acompañamiento").equals("S"));
                this.jFechaApertura.setDate(rsConsulta.getDate("fechaApertura"));
                this.jFmtHoraApertura.setText(rsConsulta.getString("horaApertura"));
                this.jTxtExpediente.setText(rsConsulta.getString("expediente"));
                this.jTxtContratacion.setText(rsConsulta.getString("contratacion"));
                this.jTxtPaciente.setText(rsConsulta.getString("paciente"));
                this.jCheckVip.setSelected(rsConsulta.getString("vip").equals("S"));
                this.jTxtDni.setText(rsConsulta.getString("dni"));
                this.jTxtDireccion.setText(rsConsulta.getString("direccion"));
                this.jTxtTelefono.setText(rsConsulta.getString("telefono"));
                this.jFmtTotal.setValue(rsConsulta.getDouble("total"));
                this.jComboProfesional1.setSelectedItem(rsConsulta.getString("profesional1"));
                this.jFmtRm1.setValue(rsConsulta.getDouble("rm1"));
                this.jComboProfesional2.setSelectedItem(rsConsulta.getString("profesional2"));
                this.jFmtRm2.setValue(rsConsulta.getDouble("rm2"));
                this.jComboProfesional3.setSelectedItem(rsConsulta.getString("profesional3"));
                this.jFmtRm3.setValue(rsConsulta.getDouble("rm3"));
                this.jComboProfesional4.setSelectedItem(rsConsulta.getString("profesional4"));
                this.jFmtRm4.setValue(rsConsulta.getDouble("rm4"));
                this.jComboProfesional5.setSelectedItem(rsConsulta.getString("profesional5"));
                this.jFmtRm5.setValue(rsConsulta.getDouble("rm5"));
                this.jComboVendedor.setSelectedItem(rsConsulta.getString("vendedor"));
                this.jComboTipoOperacion.setSelectedItem(rsConsulta.getString("tipoOperacion"));
                //this.jComboFormaPago.setSelectedItem(rsConsulta.getString("formaPago").isEmpty() ? "-- Otro --" : rsConsulta.getString("formaPago"));
                this.jTxtFormaPagoOtro.setText(rsConsulta.getString("formaPagoOtro"));
                this.jComboPlazo.setSelectedItem(rsConsulta.getString("plazo").isEmpty() ? "-- Otro --" : rsConsulta.getString("plazo"));
                this.jTxtPlazoOtro.setText(rsConsulta.getString("plazoOtro"));
                this.jComboMantenimiento.setSelectedItem(rsConsulta.getString("mantenimiento").isEmpty() ? "-- Otro --" : rsConsulta.getString("mantenimiento"));
                this.jTxtMantenimientoOtro.setText(rsConsulta.getString("mantenimientoOtro"));
                this.jTxtExtra.setText(rsConsulta.getString("notaExtra"));
                this.jTxtObservaciones.setText(rsConsulta.getString("observaciones"));
                this.jFmtCostoVenta.setText(rsConsulta.getString("costo_venta"));
                if (this.estado.equals("N")){
                    this.jComboMotivo.setSelectedItem(rsConsulta.getString("id_motivo"));
                    this.jTxtMotivo.setText(rsConsulta.getString("motivo"));
                }
                
                rsConsulta = conexion.procAlmacenado("traePresupuestoProd", parametros, 
                    this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                rsConsulta.beforeFirst();
                while(rsConsulta.next()){
                    int cantidad = rsConsulta.getInt("cantidad");
                    String codigo = rsConsulta.getString("codigo");
                    String nombre = rsConsulta.getString("producto");
                    String proveedor = rsConsulta.getString("proveedor");
                    String obsProducto = rsConsulta.getString("observaciones");
                    Double pxUnit = rsConsulta.getDouble("pxUnit");
                    int id_prod = rsConsulta.getInt("id_productoFact");
                    int id_proveedor = rsConsulta.getInt("id_proveedor");
                    Double rmProd = rsConsulta.getDouble("rm");
                    
                    BigDecimal c = new BigDecimal(cantidad);
                    BigDecimal p = new BigDecimal(pxUnit);
                    BigDecimal s = c.multiply(p).setScale(2, RoundingMode.HALF_UP);
                    BigDecimal rm = new BigDecimal(rmProd);
                    
                    Object[] fila = {cantidad, codigo, nombre, proveedor, obsProducto, 
                                        pxUnit, rm, s, id_prod, id_proveedor};
                    modelo.addRow(fila);
                }
                calculaTotal();
            }else{
                JOptionPane.showMessageDialog(this, "No existe el presupuesto", "Atención", JOptionPane.INFORMATION_MESSAGE);
                limpia();
                result = "NOT FOUND";
            }
            this.pintaEstado();
        }catch (HeadlessException | NumberFormatException | SQLException ex){
            Utiles.enviaError(this.empresa,ex);
        }
        return result;
    }
    
    private void imprimir(int salida){
        int reg = modelo.getRowCount();
        BigDecimal total = new BigDecimal(this.jFmtTotal.getValue().toString()).setScale(2, RoundingMode.HALF_UP);
        if (reg > 0){
            Map param=new HashMap();
            param.put("id_presupuesto", this.jTxtId_presupuesto.getText().trim());
            param.put("fecha", this.jFecha.getDate());
            param.put("entidad", this.jComboEntidad.getSelectedIndex() >= 0 ? this.jComboEntidad.getSelectedItem().toString() : "");
            param.put("expediente", this.jTxtExpediente.getText());
            param.put("contratacion", this.jTxtContratacion.getText());
            param.put("fechaApertura", this.jFechaApertura.getDate());
            param.put("horaApertura", this.jFmtHoraApertura.getText());
            param.put("paciente", this.jTxtPaciente.getText());
            param.put("dni", this.jTxtDni.getText());
            param.put("direccion", this.jTxtDireccion.getText());
            param.put("telefono", this.jTxtTelefono.getText());
            param.put("letras", numALetra.Convertir(total.toString(), true));
            param.put("profesional", this.jComboProfesional1.getSelectedIndex() >= 0 ? this.jComboProfesional1.getSelectedItem().toString() : "");
            param.put("lugarCirugia", this.jComboLugarCirugia.getSelectedIndex() >= 0 ? this.jComboLugarCirugia.getSelectedItem().toString() : "");
            param.put("formaPago", jComboFormaPago.getSelectedItem().toString());
            param.put("plazo", this.jComboPlazo.getSelectedItem().toString().equals("-- Otro --") ?
                        this.jTxtPlazoOtro.getText() : this.jComboPlazo.getSelectedItem().toString());
            param.put("mantenimiento", this.jComboMantenimiento.getSelectedItem().toString().equals("-- Otro --") ?
                        this.jTxtMantenimientoOtro.getText() : this.jComboMantenimiento.getSelectedItem().toString());
            param.put("notaPresu", this.notaDefault);
            param.put("notaExtra", this.jTxtExtra.getText().trim());
            param.put("cantProductos", this.modelo.getRowCount());
            param.put("costo_venta", Double.parseDouble(this.jFmtCostoVenta.getText()));
            PresupuestoProdDataSource data = new PresupuestoProdDataSource();
            
            for (int i = 0; i < modelo.getRowCount(); i++){
                int cantidad = Integer.parseInt(modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "cantidad")).toString());
                String codigo = modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "codigo")).toString();
                String nombre = modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "nombre")).toString();
                String obsProducto = modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "observaciones")).toString();
                BigDecimal pxUnit = new BigDecimal(Double.parseDouble(modelo.getValueAt(i, Utiles.getColumnByName(jTabla, "P. Unit. ($)")).toString())).setScale(2, RoundingMode.HALF_UP);
                
                PresupuestoProd pp = new PresupuestoProd(cantidad, codigo, nombre, obsProducto, pxUnit);
                data.addPresupuestoProd(pp);
            }
            
            Reporte reporte = new Reporte();
            reporte.startReport("Presupuesto",param, data, salida, rutaArchivo, impresora, copias);
        }else{
            JOptionPane.showMessageDialog(this, "No hay resultados para mostrar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuEliminaModifica = new javax.swing.JPopupMenu();
        jMenuItemModifica = new javax.swing.JMenuItem();
        jMenuItemElimina = new javax.swing.JMenuItem();
        jMenuDetalle = new javax.swing.JMenuItem();
        jMenuCopiarPegar = new javax.swing.JPopupMenu();
        jMenuCopiar = new javax.swing.JMenuItem();
        jMenuPegar = new javax.swing.JMenuItem();
        jBtnBusca = new javax.swing.JButton();
        jBtnAtras = new javax.swing.JButton();
        jTxtId_presupuesto = this.mascara.getjFmt("######", ' ', true);
        jFecha = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jFecha.getJCalendar().setTodayButtonVisible(true); 
        this.jFecha.getJCalendar().setTodayButtonText("Hoy"); 
        this.jFecha.getJCalendar().setWeekOfYearVisible(false);
        this.jFecha.getCalendarButton().setVisible(false);
        jComboZona = new javax.swing.JComboBox();
        jBtnAdelante = new javax.swing.JButton();
        jBtnAprobar = new javax.swing.JButton();
        jBtnRechazar = new javax.swing.JButton();
        jBtnAnular = new javax.swing.JButton();
        jBtnRecuperar = new javax.swing.JButton();
        jComboMotivo = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTxtMotivo = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTxtObservaciones = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jBtnScr = new javax.swing.JButton();
        jBtnImp = new javax.swing.JButton();
        jBtnPdf = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jBtnLimpiar = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jLblEstado = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jComboEntidad = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jComboLugarCirugia = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        jFechaApertura = new com.toedter.calendar.JDateChooser("dd/MM/20yy", "##/##/####", '_');
        this.jFechaApertura.getJCalendar().setTodayButtonVisible(true); 
        this.jFechaApertura.getJCalendar().setTodayButtonText("Hoy"); 
        this.jFechaApertura.getJCalendar().setWeekOfYearVisible(false);
        jLabel23 = new javax.swing.JLabel();
        try{
            jFmtHoraApertura = ar.com.bosoft.formatosCampos.ConMascara.class.newInstance().getjFmt("##:##",' ',true);
        }catch (Exception ex){
            Utiles.enviaError(this.empresa,ex);
        }
        jLabel24 = new javax.swing.JLabel();
        jTxtExpediente = new javax.swing.JTextField();
        jTxtExpediente.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel25 = new javax.swing.JLabel();
        jTxtContratacion = new javax.swing.JTextField();
        jTxtContratacion.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jCheckAcompañamiento = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jTxtPaciente = new javax.swing.JTextField();
        jTxtPaciente.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel4 = new javax.swing.JLabel();
        jTxtDni = new javax.swing.JTextField();
        jTxtDni.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(15,true));
        jLabel6 = new javax.swing.JLabel();
        jTxtTelefono = new javax.swing.JTextField();
        jTxtTelefono.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jCheckVip = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jTxtDireccion = new javax.swing.JTextField();
        jTxtDireccion.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(100,false));
        jFmtCantidad = new javax.swing.JFormattedTextField();
        jTxtCodigo = new javax.swing.JTextField();
        jTxtCodigo.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLblNombre = new javax.swing.JLabel();
        jPanelObsProducto = new javax.swing.JPanel();
        jScrollPaneObsProducto = new javax.swing.JScrollPane();
        jTxtObsProducto = new javax.swing.JTextArea();
        jFmtPxUnit = new ar.com.bosoft.formatosCampos.Decimal3(true);
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTabla = new javax.swing.JTable();
        jFmtTotal = new ar.com.bosoft.formatosCampos.Moneda(true);
        ;
        jLabel21 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboProfesional1 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jFmtRm1 = new ar.com.bosoft.formatosCampos.Decimal(true); ;
        jLabel9 = new javax.swing.JLabel();
        jComboProfesional2 = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jFmtRm2 = new ar.com.bosoft.formatosCampos.Decimal(true);
        jLabel11 = new javax.swing.JLabel();
        jComboProfesional3 = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jComboVendedor = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jComboTipoOperacion = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jComboFormaPago = new javax.swing.JComboBox();
        jTxtFormaPagoOtro = new javax.swing.JTextField();
        jTxtFormaPagoOtro.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel15 = new javax.swing.JLabel();
        jComboPlazo = new javax.swing.JComboBox();
        jTxtPlazoOtro = new javax.swing.JTextField();
        jTxtPlazoOtro.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jLabel18 = new javax.swing.JLabel();
        jComboMantenimiento = new javax.swing.JComboBox();
        jTxtMantenimientoOtro = new javax.swing.JTextField();
        jTxtMantenimientoOtro.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,true));
        jScrollPane1 = new javax.swing.JScrollPane();
        jTxtExtra = new javax.swing.JTextArea();
        jComboProveedor = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        jButtonHistorial = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jComboProfesional4 = new javax.swing.JComboBox();
        jLabel27 = new javax.swing.JLabel();
        jFmtRm4 = new ar.com.bosoft.formatosCampos.Decimal(true);
        jLabel28 = new javax.swing.JLabel();
        jComboProfesional5 = new javax.swing.JComboBox();
        jLabel29 = new javax.swing.JLabel();
        jFmtRm5 = new ar.com.bosoft.formatosCampos.Decimal(true);
        jFmtRm3 = new javax.swing.JFormattedTextField();
        jFmtCostoVenta = new javax.swing.JFormattedTextField();

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

        jMenuDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/16x16/enabled/eye_icon&16.png"))); // NOI18N
        jMenuDetalle.setText("Ver detalle");
        jMenuDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuDetalleActionPerformed(evt);
            }
        });
        jMenuEliminaModifica.add(jMenuDetalle);

        jMenuCopiar.setText("Copiar");
        jMenuCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuCopiarActionPerformed(evt);
            }
        });
        jMenuCopiarPegar.add(jMenuCopiar);

        jMenuPegar.setText("Pegar");
        jMenuPegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuPegarActionPerformed(evt);
            }
        });
        jMenuCopiarPegar.add(jMenuPegar);

        setTitle("Presupuestos");
        setAutoscrolls(true);

        jBtnBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/24x24/enabled/buscar.png"))); // NOI18N
        jBtnBusca.setBorderPainted(false);
        jBtnBusca.setContentAreaFilled(false);
        jBtnBusca.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnBusca.setFocusPainted(false);
        jBtnBusca.setFocusable(false);
        jBtnBusca.setRequestFocusEnabled(false);
        jBtnBusca.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/24x24/rollover/buscar.png"))); // NOI18N
        jBtnBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscaActionPerformed(evt);
            }
        });

        jBtnAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/atras.png"))); // NOI18N
        jBtnAtras.setBorderPainted(false);
        jBtnAtras.setContentAreaFilled(false);
        jBtnAtras.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnAtras.setFocusPainted(false);
        jBtnAtras.setFocusable(false);
        jBtnAtras.setRequestFocusEnabled(false);
        jBtnAtras.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/atras.png"))); // NOI18N
        jBtnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAtrasActionPerformed(evt);
            }
        });

        jTxtId_presupuesto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtId_presupuesto.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        jTxtId_presupuesto.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jTxtId_presupuesto.setNextFocusableComponent(jComboZona);
        jTxtId_presupuesto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtId_presupuestoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtId_presupuestoFocusLost(evt);
            }
        });
        jTxtId_presupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtId_presupuestoActionPerformed(evt);
            }
        });

        jFecha.setNextFocusableComponent(jComboZona);
        jFecha.setRequestFocusEnabled(false);

        jComboZona.setNextFocusableComponent(jComboEntidad);
        jComboZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboZonaActionPerformed(evt);
            }
        });

        jBtnAdelante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/adelante.png"))); // NOI18N
        jBtnAdelante.setBorderPainted(false);
        jBtnAdelante.setContentAreaFilled(false);
        jBtnAdelante.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnAdelante.setFocusPainted(false);
        jBtnAdelante.setFocusable(false);
        jBtnAdelante.setRequestFocusEnabled(false);
        jBtnAdelante.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/adelante.png"))); // NOI18N
        jBtnAdelante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAdelanteActionPerformed(evt);
            }
        });

        jBtnAprobar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/finalizar.png"))); // NOI18N
        jBtnAprobar.setText("Aprobar");
        jBtnAprobar.setToolTipText("");
        jBtnAprobar.setBorderPainted(false);
        jBtnAprobar.setContentAreaFilled(false);
        jBtnAprobar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnAprobar.setFocusPainted(false);
        jBtnAprobar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnAprobar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/finalizar.png"))); // NOI18N
        jBtnAprobar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnAprobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAprobarActionPerformed(evt);
            }
        });

        jBtnRechazar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/cancelar.png"))); // NOI18N
        jBtnRechazar.setText("Rechazar");
        jBtnRechazar.setToolTipText("");
        jBtnRechazar.setBorderPainted(false);
        jBtnRechazar.setContentAreaFilled(false);
        jBtnRechazar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnRechazar.setFocusPainted(false);
        jBtnRechazar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnRechazar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/cancelar.png"))); // NOI18N
        jBtnRechazar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnRechazar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnRechazarActionPerformed(evt);
            }
        });

        jBtnAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/anular.png"))); // NOI18N
        jBtnAnular.setText("Anular");
        jBtnAnular.setToolTipText("");
        jBtnAnular.setBorderPainted(false);
        jBtnAnular.setContentAreaFilled(false);
        jBtnAnular.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnAnular.setFocusPainted(false);
        jBtnAnular.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnAnular.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/anular.png"))); // NOI18N
        jBtnAnular.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAnularActionPerformed(evt);
            }
        });

        jBtnRecuperar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/recuperar.png"))); // NOI18N
        jBtnRecuperar.setText("Recuperar");
        jBtnRecuperar.setToolTipText("");
        jBtnRecuperar.setBorderPainted(false);
        jBtnRecuperar.setContentAreaFilled(false);
        jBtnRecuperar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnRecuperar.setFocusPainted(false);
        jBtnRecuperar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnRecuperar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/recuperar.png"))); // NOI18N
        jBtnRecuperar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnRecuperar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnRecuperarActionPerformed(evt);
            }
        });

        jComboMotivo.setEnabled(false);

        jTxtMotivo.setColumns(20);
        jTxtMotivo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTxtMotivo.setRows(5);
        jTxtMotivo.setBorder(javax.swing.BorderFactory.createTitledBorder("Motivo"));
        jTxtMotivo.setEnabled(false);
        jScrollPane2.setViewportView(jTxtMotivo);

        jTxtObservaciones.setColumns(20);
        jTxtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTxtObservaciones.setRows(5);
        jTxtObservaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Observaciones"));
        jScrollPane3.setViewportView(jTxtObservaciones);

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Salida"));

        jBtnScr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/enabled/pantalla.png"))); // NOI18N
        jBtnScr.setBorder(null);
        jBtnScr.setBorderPainted(false);
        jBtnScr.setContentAreaFilled(false);
        jBtnScr.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        jBtnImp.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        jBtnPdf.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnPdf.setFocusPainted(false);
        jBtnPdf.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/pdf.png"))); // NOI18N
        jBtnPdf.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/48x48/rollover/pdf.png"))); // NOI18N
        jBtnPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPdfActionPerformed(evt);
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
                .addComponent(jBtnPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnScr)
                    .addComponent(jBtnImp)
                    .addComponent(jBtnPdf))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jBtnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/limpiar.png"))); // NOI18N
        jBtnLimpiar.setText("Limpiar");
        jBtnLimpiar.setBorderPainted(false);
        jBtnLimpiar.setContentAreaFilled(false);
        jBtnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnLimpiar.setFocusPainted(false);
        jBtnLimpiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnLimpiar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/limpiar.png"))); // NOI18N
        jBtnLimpiar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/limpiar.png"))); // NOI18N
        jBtnLimpiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLimpiarActionPerformed(evt);
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

        jLblEstado.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLblEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblEstado.setText("APROBADO");

        jLabel1.setText("* Fecha");

        jLabel16.setText("* Zona");

        jLabel37.setText("* Entidad");

        jComboEntidad.setNextFocusableComponent(jComboLugarCirugia);
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
        jComboEntidad.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboEntidadPropertyChange(evt);
            }
        });
        jComboEntidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboEntidadKeyReleased(evt);
            }
        });

        jLabel17.setText("Lugar de Cirugía");

        jComboLugarCirugia.setNextFocusableComponent(jFechaApertura);
        jComboLugarCirugia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboLugarCirugiaMouseClicked(evt);
            }
        });
        jComboLugarCirugia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboLugarCirugiaKeyReleased(evt);
            }
        });

        jLabel22.setText("Fecha Apertura");

        jFechaApertura.setNextFocusableComponent(jFmtHoraApertura);

        jLabel23.setText("Hora");

        jFmtHoraApertura.setNextFocusableComponent(jTxtExpediente);

        jLabel24.setText("Expediente N°");

        jTxtExpediente.setNextFocusableComponent(jTxtContratacion);

        jLabel25.setText("Contratación Directa");

        jTxtContratacion.setNextFocusableComponent(jCheckAcompañamiento);

        jCheckAcompañamiento.setText("Acompañamiento");
        jCheckAcompañamiento.setContentAreaFilled(false);
        jCheckAcompañamiento.setNextFocusableComponent(jTxtPaciente);

        jLabel3.setText(" Paciente");

        jTxtPaciente.setNextFocusableComponent(jTxtDni);

        jLabel4.setText("DNI");

        jTxtDni.setNextFocusableComponent(jTxtTelefono);

        jLabel6.setText("Teléfono");

        jTxtTelefono.setNextFocusableComponent(jCheckVip);

        jCheckVip.setText("VIP");
        jCheckVip.setContentAreaFilled(false);
        jCheckVip.setNextFocusableComponent(jTxtDireccion);

        jLabel5.setText("Dirección");

        jTxtDireccion.setNextFocusableComponent(jFmtCantidad);

        jFmtCantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmtCantidad.setNextFocusableComponent(jTxtCodigo);
        jFmtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFmtCantidadActionPerformed(evt);
            }
        });
        jFmtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFmtCantidadKeyReleased(evt);
            }
        });

        jTxtCodigo.setNextFocusableComponent(jComboProveedor);
        jTxtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtCodigoFocusLost(evt);
            }
        });
        jTxtCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTxtCodigoMouseClicked(evt);
            }
        });
        jTxtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtCodigoActionPerformed(evt);
            }
        });
        jTxtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtCodigoKeyReleased(evt);
            }
        });

        jTxtObsProducto.setColumns(20);
        jTxtObsProducto.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtObsProducto.setLineWrap(true);
        jTxtObsProducto.setRows(15);
        jTxtObsProducto.setWrapStyleWord(true);
        jTxtObsProducto.setComponentPopupMenu(jMenuCopiarPegar);
        jTxtObsProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtObsProductoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtObsProductoFocusLost(evt);
            }
        });
        jTxtObsProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtObsProductoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtObsProductoKeyReleased(evt);
            }
        });
        jScrollPaneObsProducto.setViewportView(jTxtObsProducto);

        jFmtPxUnit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmtPxUnit.setNextFocusableComponent(jFmtCantidad);
        jFmtPxUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFmtPxUnitActionPerformed(evt);
            }
        });
        jFmtPxUnit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFmtPxUnitFocusLost(evt);
            }
        });
        jFmtPxUnit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFmtPxUnitKeyReleased(evt);
            }
        });

        jLabel2.setText("F5 - Carga");

        javax.swing.GroupLayout jPanelObsProductoLayout = new javax.swing.GroupLayout(jPanelObsProducto);
        jPanelObsProducto.setLayout(jPanelObsProductoLayout);
        jPanelObsProductoLayout.setHorizontalGroup(
            jPanelObsProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelObsProductoLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jScrollPaneObsProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFmtPxUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelObsProductoLayout.setVerticalGroup(
            jPanelObsProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelObsProductoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanelObsProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelObsProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFmtPxUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(jScrollPaneObsProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cantidad", "codigo", "nombre", "Proveedor", "observaciones", "P. Unit. ($)", "RM ($)", "Subtotal ($)", "id_productoFact", "id_proveedor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabla.setComponentPopupMenu(jMenuEliminaModifica);
        jTabla.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTablaPropertyChange(evt);
            }
        });
        jTabla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTablaKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(jTabla);
        if (jTabla.getColumnModel().getColumnCount() > 0) {
            jTabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTabla.getColumnModel().getColumn(0).setMaxWidth(75);
            jTabla.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(1).setMaxWidth(150);
            jTabla.getColumnModel().getColumn(4).setPreferredWidth(200);
            jTabla.getColumnModel().getColumn(4).setMaxWidth(350);
            jTabla.getColumnModel().getColumn(5).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(5).setMaxWidth(120);
            jTabla.getColumnModel().getColumn(6).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(6).setMaxWidth(120);
            jTabla.getColumnModel().getColumn(7).setPreferredWidth(75);
            jTabla.getColumnModel().getColumn(7).setMaxWidth(120);
            jTabla.getColumnModel().getColumn(8).setMinWidth(0);
            jTabla.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(8).setMaxWidth(0);
            jTabla.getColumnModel().getColumn(9).setMinWidth(0);
            jTabla.getColumnModel().getColumn(9).setPreferredWidth(0);
            jTabla.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        jFmtTotal.setEditable(false);
        jFmtTotal.setBackground(new java.awt.Color(153, 204, 255));
        jFmtTotal.setBorder(null);
        jFmtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmtTotal.setFocusable(false);
        jFmtTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("* Total");

        jLabel7.setText("Profesional (1)");

        jComboProfesional1.setNextFocusableComponent(jFmtRm1);
        jComboProfesional1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProfesional1MouseClicked(evt);
            }
        });
        jComboProfesional1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboProfesional1ActionPerformed(evt);
            }
        });
        jComboProfesional1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboProfesional1KeyReleased(evt);
            }
        });

        jLabel8.setText("RM (1)");

        jFmtRm1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFmtRm1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmtRm1.setNextFocusableComponent(jComboProfesional2);
        jFmtRm1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jFmtRm1MouseClicked(evt);
            }
        });
        jFmtRm1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFmtRm1ActionPerformed(evt);
            }
        });

        jLabel9.setText("Profesional (2)");

        jComboProfesional2.setNextFocusableComponent(jFmtRm2);
        jComboProfesional2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProfesional2MouseClicked(evt);
            }
        });
        jComboProfesional2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboProfesional2ActionPerformed(evt);
            }
        });
        jComboProfesional2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboProfesional2KeyReleased(evt);
            }
        });

        jLabel10.setText("RM (2)");

        jFmtRm2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFmtRm2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmtRm2.setNextFocusableComponent(jComboProfesional3);
        jFmtRm2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jFmtRm2MouseClicked(evt);
            }
        });
        jFmtRm2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFmtRm2ActionPerformed(evt);
            }
        });

        jLabel11.setText("Profesional (3)");

        jComboProfesional3.setNextFocusableComponent(jFmtRm3);
        jComboProfesional3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProfesional3MouseClicked(evt);
            }
        });
        jComboProfesional3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboProfesional3KeyReleased(evt);
            }
        });

        jLabel12.setText("RM (3)");

        jLabel13.setText("* Agente");
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jComboVendedor.setNextFocusableComponent(jComboTipoOperacion);
        jComboVendedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboVendedorMouseClicked(evt);
            }
        });

        jLabel19.setText("* Tipo Op.");

        jComboTipoOperacion.setNextFocusableComponent(jComboFormaPago);

        jLabel14.setText("* Forma de Pago");

        jComboFormaPago.setNextFocusableComponent(jTxtFormaPagoOtro);
        jComboFormaPago.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboFormaPagoPropertyChange(evt);
            }
        });

        jTxtFormaPagoOtro.setEnabled(false);
        jTxtFormaPagoOtro.setNextFocusableComponent(jComboPlazo);

        jLabel15.setText("* Plazo de entrega");

        jComboPlazo.setNextFocusableComponent(jTxtPlazoOtro);
        jComboPlazo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboPlazoPropertyChange(evt);
            }
        });

        jTxtPlazoOtro.setEnabled(false);
        jTxtPlazoOtro.setNextFocusableComponent(jComboMantenimiento);
        jTxtPlazoOtro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtPlazoOtroActionPerformed(evt);
            }
        });

        jLabel18.setText("* Manten. de Oferta");

        jComboMantenimiento.setNextFocusableComponent(jTxtMantenimientoOtro);
        jComboMantenimiento.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboMantenimientoPropertyChange(evt);
            }
        });

        jTxtMantenimientoOtro.setEnabled(false);

        jTxtExtra.setColumns(20);
        jTxtExtra.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTxtExtra.setLineWrap(true);
        jTxtExtra.setRows(5);
        jTxtExtra.setWrapStyleWord(true);
        jTxtExtra.setBorder(javax.swing.BorderFactory.createTitledBorder("Nota extra"));
        jScrollPane1.setViewportView(jTxtExtra);

        jComboProveedor.setNextFocusableComponent(jTxtObsProducto);
        jComboProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProveedorMouseClicked(evt);
            }
        });
        jComboProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboProveedorKeyReleased(evt);
            }
        });

        jLabel20.setText("* Costo de Venta");

        jButtonHistorial.setText("Historial");
        jButtonHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHistorialActionPerformed(evt);
            }
        });

        jLabel26.setText("Profesional (4)");

        jComboProfesional4.setNextFocusableComponent(jFmtRm3);
        jComboProfesional4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProfesional4MouseClicked(evt);
            }
        });
        jComboProfesional4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboProfesional4KeyReleased(evt);
            }
        });

        jLabel27.setText("RM (4)");

        jFmtRm4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFmtRm4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmtRm4.setNextFocusableComponent(jComboVendedor);
        jFmtRm4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jFmtRm4MouseClicked(evt);
            }
        });

        jLabel28.setText("Profesional (5)");

        jComboProfesional5.setNextFocusableComponent(jFmtRm3);
        jComboProfesional5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProfesional5MouseClicked(evt);
            }
        });
        jComboProfesional5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboProfesional5KeyReleased(evt);
            }
        });

        jLabel29.setText("RM (5)");

        jFmtRm5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFmtRm5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmtRm5.setNextFocusableComponent(jComboVendedor);
        jFmtRm5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jFmtRm5MouseClicked(evt);
            }
        });

        jFmtRm3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFmtRm3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jFmtRm3MouseClicked(evt);
            }
        });

        jFmtCostoVenta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jBtnAprobar)
                        .addGap(104, 104, 104)
                        .addComponent(jBtnRechazar)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jBtnGuardar)))
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboMotivo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jBtnAnular)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnRecuperar)
                        .addGap(37, 37, 37)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jBtnLimpiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonHistorial))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBtnSalir)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel16)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBtnBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel22)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel19)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel18)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jFmtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboProveedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelObsProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtDireccion, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFmtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jTxtPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel4))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel37))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jFechaApertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel23)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jFmtHoraApertura, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel24)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jTxtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel6))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jComboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel17))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jTxtExpediente, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel25)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jTxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jCheckVip))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTxtContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jComboLugarCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jCheckAcompañamiento))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jFecha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTxtId_presupuesto, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jBtnAdelante, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jComboTipoOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jFmtCostoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jComboProfesional1, 0, 221, Short.MAX_VALUE)
                                            .addComponent(jComboProfesional2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jFmtRm2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel26)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jComboProfesional4, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jFmtRm1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel11)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jComboProfesional3, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jFmtRm3))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel27)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jFmtRm4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel28)
                                            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jComboProfesional5, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jFmtRm5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 74, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtFormaPagoOtro, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboPlazo, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtPlazoOtro, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboMantenimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtMantenimientoOtro, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jTxtId_presupuesto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnAdelante)
                    .addComponent(jBtnAtras)
                    .addComponent(jBtnBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblEstado))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)
                    .addComponent(jComboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jComboLugarCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel22)
                    .addComponent(jFechaApertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(jFmtHoraApertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jTxtExpediente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(jTxtContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckAcompañamiento))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTxtPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTxtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckVip))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFmtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelObsProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFmtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboProfesional1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jFmtRm1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jComboProfesional3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel28)
                            .addComponent(jComboProfesional5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFmtRm3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jComboProfesional2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jFmtRm2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26)
                            .addComponent(jComboProfesional4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27)
                            .addComponent(jFmtRm4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29)
                            .addComponent(jFmtRm5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jComboTipoOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(jLabel13)
                            .addComponent(jComboVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFmtCostoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(jComboFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTxtFormaPagoOtro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(jComboPlazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTxtPlazoOtro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(jComboMantenimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTxtMantenimientoOtro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jBtnLimpiar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jBtnRecuperar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jBtnGuardar)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jBtnAprobar)
                                                .addComponent(jBtnAnular))))
                                    .addComponent(jBtnRechazar))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jComboMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jButtonHistorial))
                        .addGap(1, 1, 1))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnSalir))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboEntidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboEntidadKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboEntidad.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jComboEntidadKeyReleased

    private void jComboLugarCirugiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboLugarCirugiaKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboLugarCirugia.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jComboLugarCirugiaKeyReleased

    private void jBtnScrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScrActionPerformed
        //        if (producto()){
                        imprimir(0);
            //        }
    }//GEN-LAST:event_jBtnScrActionPerformed

    private void jBtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImpActionPerformed
        //        if(producto()){
                        seleccionImp.setVisible(true);
                        if (seleccionImp.getSino()){
                                this.impresora = seleccionImp.getImpresora();
                                this.copias = seleccionImp.getCopias();
                                imprimir(1);
                            }
            //        }
    }//GEN-LAST:event_jBtnImpActionPerformed

    private void jBtnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPdfActionPerformed
        //        if (producto()){
                        //Crear un objeto FileChooser
                        JFileChooser fc = new JFileChooser();
                        //Mostrar la ventana para abrir archivo y recoger la respuesta
                        int respuesta = fc.showSaveDialog(null);
                        //Comprobar si se ha pulsado Aceptar
                        if (respuesta == JFileChooser.APPROVE_OPTION){
                                //Crear un objeto File con el archivo elegido
                                File archivoElegido = fc.getSelectedFile();
                                try {
                                        this.rutaArchivo = archivoElegido.getCanonicalPath() + ".pdf";
                                        imprimir(2);
                                    } catch (IOException ex) {
                                        JOptionPane.showMessageDialog(this,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
                                    }
                            }
            //        }
    }//GEN-LAST:event_jBtnPdfActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
        limpia();
        this.jFecha.requestFocus();
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

    private void jTxtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtCodigoFocusLost
        this.id_productoFact = 0;
        String busca = this.jTxtCodigo.getText();
        if (!busca.isEmpty()){
            Iterator i = arrayProductoFact.iterator();
            while (i.hasNext()){
                ProductoFactData tmp = (ProductoFactData) i.next();
                if (tmp.getCodigo().equals(busca)) {
                    this.jLblNombre.setText(tmp.getNombre());
                    this.id_productoFact = tmp.getId_productoFact();
//                    this.jTxtObsProducto.setText("");
//                    this.jFmtPxUnit.setValue(0.00);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "El producto no existe o está inhabilitado", "Atención", JOptionPane.INFORMATION_MESSAGE);
            this.jTxtCodigo.requestFocus();
        }
    }//GEN-LAST:event_jTxtCodigoFocusLost

    private void jFmtCantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFmtCantidadKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_F5){
            if (validaProducto()){
                cargaProducto();
            }else{
                JOptionPane.showMessageDialog(this, "Faltan datos del producto", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jFmtCantidadKeyReleased

    private void jTxtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCodigoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_F5){
            if (validaProducto()){
                cargaProducto();
            }else{
                JOptionPane.showMessageDialog(this, "Faltan datos del producto", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jTxtCodigoKeyReleased

    private void jFmtPxUnitKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFmtPxUnitKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_F5){
            if (validaProducto()){
                cargaProducto();
            }else{
                JOptionPane.showMessageDialog(this, "Faltan datos del producto", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jFmtPxUnitKeyReleased

    private void jComboProfesional2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboProfesional2KeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboProfesional2.setSelectedIndex(-1);
            this.jFmtRm2.setValue(0.00);
        }
    }//GEN-LAST:event_jComboProfesional2KeyReleased

    private void jComboProfesional3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboProfesional3KeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboProfesional3.setSelectedIndex(-1);
            this.jFmtRm3.setValue(0.00);
        }
    }//GEN-LAST:event_jComboProfesional3KeyReleased

    private void jComboFormaPagoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboFormaPagoPropertyChange
        this.jTxtFormaPagoOtro.setEnabled(true);
    }//GEN-LAST:event_jComboFormaPagoPropertyChange

    private void jComboPlazoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboPlazoPropertyChange
        this.jTxtPlazoOtro.setEnabled(this.jComboPlazo.getSelectedIndex() == arrayPlazo.size());
    }//GEN-LAST:event_jComboPlazoPropertyChange

    private void jComboMantenimientoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboMantenimientoPropertyChange
        this.jTxtMantenimientoOtro.setEnabled(this.jComboMantenimiento.getSelectedIndex() == arrayMantenimiento.size());
    }//GEN-LAST:event_jComboMantenimientoPropertyChange

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        try{
            if (validaObligatorios()){
                try {
                    int id_presupuesto = 0;
                    if (!this.jTxtId_presupuesto.getText().trim().isEmpty()){
                        id_presupuesto = Integer.parseInt(this.jTxtId_presupuesto.getText().trim());
                    }

                    // LOG PRESUPUESTO EVENT
                    if (id_presupuesto > 0){
                        matera.jooq.tables.pojos.Presupuesto presupuesto  = new matera.jooq.tables.daos.PresupuestoDao(
                                ActiveDatabase.getDSLContext().configuration()).findById(id_presupuesto);
                        if (!presupuesto.getEstado().equals(estado)){
                            switch(estado){
                                case "A":
                                    LogHelper.logPresupuestoEvent(id_presupuesto, Utiles.LOG_EVENTO.APRUEBA_PRESUPUESTO);
                                    break;
                                case "C":
                                    LogHelper.logPresupuestoEvent(id_presupuesto, Utiles.LOG_EVENTO.CONFIRMA_PRESUPUESTO);
                                    break;
                                case "S":
                                    LogHelper.logPresupuestoEvent(id_presupuesto, Utiles.LOG_EVENTO.SUSPENDE_PRESUPUESTO);
                                    break;
                                case "R":
                                    LogHelper.logPresupuestoEvent(id_presupuesto, Utiles.LOG_EVENTO.RECHAZA_PRESUPUESTO);
                                    break;
                                case "N":
                                    LogHelper.logPresupuestoEvent(id_presupuesto, Utiles.LOG_EVENTO.ANULA_PRESUPUESTO);
                                    break;                                
                            }
                        }
                        else{
                            LogHelper.logPresupuestoEvent(id_presupuesto, Utiles.LOG_EVENTO.ACTUALIZA_PRESUPUESTO);
                        }

                    }

                    Long fecha = this.jFecha.getDate().getTime();

                    ZonaData z = (ZonaData) arrayZona.get(this.jComboZona.getSelectedIndex());
                    int id_zona = z.getId_zona();

                    int id_entidad = (int) arrayId_entidadLugar.get(this.jComboEntidad.getSelectedIndex());

                    int id_lugarCirugia = 0;
                    if (this.jComboLugarCirugia.getSelectedIndex() >= 0) {
                        id_lugarCirugia = (int) arrayId_entidadLugar.get(this.jComboLugarCirugia.getSelectedIndex());
                    }

                    String acompañamiento = this.jCheckAcompañamiento.isSelected() ? "S" : "N";
                    Long fechaApertura = (this.jFechaApertura.getDate() == null ? 0 : this.jFechaApertura.getDate().getTime());
                    String horaApertura = this.jFmtHoraApertura.getText().replace(":", "");
                    String expediente = this.jTxtExpediente.getText();
                    String contratacion = this.jTxtContratacion.getText();
                    String paciente = this.jTxtPaciente.getText();
                    String vip = this.jCheckVip.isSelected() ? "S" : "N";
                    String dni = this.jTxtDni.getText();
                    String direccion = this.jTxtDireccion.getText();
                    String telefono = this.jTxtTelefono.getText();
                    Double total = Double.parseDouble(this.jFmtTotal.getValue().toString());                    

                    int id_profesional1 = 0;
                    Double rm1 = 0.00;
                    if (this.jComboProfesional1.getSelectedIndex() >= 0){
                        id_profesional1 = (int) arrayId_profesional.get(this.jComboProfesional1.getSelectedIndex());
                        rm1 = Double.parseDouble(this.jFmtRm1.getValue().toString());
                    }

                    int id_profesional2 = 0;
                    Double rm2 = 0.00;
                    if (this.jComboProfesional2.getSelectedIndex() >= 0){
                        id_profesional2 = (int) arrayId_profesional.get(this.jComboProfesional2.getSelectedIndex());
                        rm2 = Double.parseDouble(this.jFmtRm2.getValue().toString());
                    }

                    int id_profesional3 = 0;
                    Double rm3 = 0.00;
                    if (this.jComboProfesional3.getSelectedIndex() >= 0){
                        id_profesional3 = (int) arrayId_profesional.get(this.jComboProfesional3.getSelectedIndex());
                        rm3 = Double.parseDouble(this.jFmtRm3.getValue().toString());
                    }
                    
                    int id_profesional4 = 0;
                    Double rm4 = 0.00;
                    if (this.jComboProfesional4.getSelectedIndex() >= 0){
                        id_profesional4 = (int) arrayId_profesional.get(this.jComboProfesional4.getSelectedIndex());
                        rm4 = Double.parseDouble(this.jFmtRm4.getValue().toString());
                    }
                    
                    int id_profesional5 = 0;
                    Double rm5 = 0.00;
                    if (this.jComboProfesional5.getSelectedIndex() >= 0){
                        id_profesional5 = (int) arrayId_profesional.get(this.jComboProfesional5.getSelectedIndex());
                        rm5 = Double.parseDouble(this.jFmtRm5.getValue().toString());
                    }

                    int id_vendedor = 0;                
                    if (this.jComboVendedor.getSelectedIndex() >= 0){
                        id_vendedor = (int) arrayId_vendedor.get(this.jComboVendedor.getSelectedIndex());
                    }

                    int id_tipoOperacion = 0;
                    if (this.jComboVendedor.getSelectedIndex() >= 0){
                        TipoOperacionData to = (TipoOperacionData) arrayTipoOperacion.get(this.jComboTipoOperacion.getSelectedIndex());
                        id_tipoOperacion = to.getId_tipoOperacion();
                    }

                    int id_formaPago = 0;
                    String formaPagoOtro = this.jTxtFormaPagoOtro.getText();
                    if (this.jComboFormaPago.getSelectedIndex() != arrayFormaPago.size()){
                        id_formaPago = ComboBoxMgr.getSelectedId(jComboFormaPago);
                        formaPagoOtro = "";
                    }

                    int id_plazo = 0;
                    String plazoOtro = this.jTxtPlazoOtro.getText();
                    if (this.jComboPlazo.getSelectedIndex() != arrayPlazo.size()){
                        PlazoData pl = (PlazoData) arrayPlazo.get(this.jComboPlazo.getSelectedIndex());
                        id_plazo = pl.getId_plazo();
                        plazoOtro = "";
                    }

                    int id_mantenimiento = 0;
                    String mantenimientoOtro = this.jTxtMantenimientoOtro.getText();
                    if (this.jComboMantenimiento.getSelectedIndex() != arrayMantenimiento.size()){
                        MantenimientoData m = (MantenimientoData) arrayMantenimiento.get(this.jComboMantenimiento.getSelectedIndex());
                        id_mantenimiento = m.getId_mantenimiento();
                        mantenimientoOtro = "";
                    }

                    String notaExtra = this.jTxtExtra.getText().trim();

                    String notaPresu = this.notaDefault;
                    String observaciones = this.jTxtObservaciones.getText().trim();

                    Long fechaAprobacion = (long)0;
                    if (this.estado.equals("A")) {
                        fechaAprobacion = new Date().getTime();
                    }

                    int id_motivo = 0;
                    String motivo = "";
                    if (this.estado.equals("N")){
                        MotivoData mo = (MotivoData) arrayMotivo.get(this.jComboMotivo.getSelectedIndex());
                        id_motivo = mo.getId_motivo();
                        motivo = this.jTxtMotivo.getText().trim();
                    }                    

                    ArrayList parametros = new ArrayList();
                    parametros.add(id_presupuesto);
                    parametros.add(estado.isEmpty() ? "P" : estado);
                    parametros.add(fecha);
                    parametros.add(id_zona);
                    parametros.add(id_entidad);
                    parametros.add(id_lugarCirugia);
                    parametros.add(acompañamiento);
                    parametros.add(fechaApertura);
                    parametros.add(horaApertura);
                    parametros.add(expediente);
                    parametros.add(contratacion);
                    parametros.add(paciente);
                    parametros.add(vip);
                    parametros.add(dni);
                    parametros.add(direccion);
                    parametros.add(telefono);
                    parametros.add(total);
                    parametros.add(id_profesional1);
                    parametros.add(rm1);
                    parametros.add(id_profesional2);
                    parametros.add(rm2);
                    parametros.add(id_profesional3);
                    parametros.add(rm3);
                    parametros.add(id_profesional4);
                    parametros.add(rm4);
                    parametros.add(id_profesional5);
                    parametros.add(rm5);
                    parametros.add(id_vendedor);
                    parametros.add(id_tipoOperacion);
                    parametros.add(id_formaPago);
                    parametros.add(formaPagoOtro);
                    parametros.add(id_plazo);
                    parametros.add(plazoOtro);
                    parametros.add(id_mantenimiento);
                    parametros.add(mantenimientoOtro);
                    parametros.add(notaPresu);
                    parametros.add(observaciones);
                    parametros.add(id_motivo);
                    parametros.add(motivo);
                    parametros.add(notaExtra);

                    parametros.add(this.id_empresa);
                    parametros.add(this.usuario);
                    parametros.add(fechaAprobacion);
                    parametros.add(Principal.equipo);

                    parametros.add(Double.parseDouble(this.jFmtCostoVenta.getText()));

                    conexion.procAlmacenado("insertPresupuesto", parametros,
                            this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                    parametros.clear();
                    parametros.add(this.id_empresa);

                    if (id_presupuesto == 0) {
                        rsConsulta = conexion.funcAlmacenada("proximoPresupuesto", parametros,
                                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());

                        rsConsulta.first();
                        id_presupuesto = rsConsulta.getInt(1);
                        this.jTxtId_presupuesto.setText(String.valueOf(id_presupuesto));
                        LogHelper.logPresupuestoEvent(id_presupuesto, Utiles.LOG_EVENTO.CREA_PRESUPUESTO);
                    }
                    String contenido = "";
                    for (int i = 0; i < modelo.getRowCount(); i++){
                        parametros.clear();

                        int cantidad = Integer.parseInt(modelo.getValueAt(i, 0).toString());
                        int id_prod = Integer.parseInt(modelo.getValueAt(i, 7).toString());
                        String obsProducto = modelo.getValueAt(i, 4).toString();
                        Double pxUnit = Double.parseDouble(modelo.getValueAt(i, 5).toString());
                        Double rmProd = Double.parseDouble(modelo.getValueAt(i, 6).toString());
                        int id_proveedor = (int)modelo.getValueAt(i, 8);
                        contenido = contenido +  modelo.getValueAt(i, 0).toString() + "  " 
                                + modelo.getValueAt(i, 1).toString() + "  " +
                                modelo.getValueAt(i, 2).toString() + "\r\n";

                        boolean primero = (i == 0);

                        parametros.add(this.id_empresa);
                        parametros.add(id_presupuesto);
                        parametros.add(cantidad);
                        parametros.add(id_prod);
                        parametros.add(obsProducto);
                        parametros.add(pxUnit);
                        parametros.add(rmProd);
                        parametros.add(primero);
                        parametros.add(id_proveedor);

                        conexion.procAlmacenado("insertPresupuestoProd", parametros,
                                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
                    }
                    
                    if(estado.equals("A")){
                        EmailService.sendEmail(id_presupuesto, id_zona, paciente, jComboEntidad.getSelectedItem().toString(), 
                                jComboProfesional1.getSelectedItem().toString(), contenido);
                    }
                    int imprime = JOptionPane.showConfirmDialog(this, "Desea imprimir el presupuesto actual?", "MASA", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (imprime == JOptionPane.YES_OPTION){
                        salida.setVisible(true);
                        this.rutaArchivo = salida.getRutaArchivo();
                        this.impresora = salida.getImpresora();
                        this.copias = salida.getCopias();
                        imprimir(salida.getTipoSalida());
                    }
                    limpia();
                    this.jFecha.requestFocus();
                } catch (SQLException ex) {
                    Utiles.enviaError(empresa, ex);
                }
            }else{
                JOptionPane.showMessageDialog(this,"Complete todos los datos obligatorios (*)", "Información",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            Utiles.showErrorMessage(e);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscaActionPerformed
        this.buscaPresupuesto = new BuscaPresupuesto(conexion,null,true,this.id_empresa,this.empresa, 6);
        this.buscaPresupuesto.setVisible(true);
        this.jTxtId_presupuesto.setText(buscaPresupuesto.getId_presupuesto().isEmpty() ? "" : buscaPresupuesto.getId_presupuesto());
        if (!this.jTxtId_presupuesto.getText().trim().equals("")) {
            traePresupuesto();
        }
    }//GEN-LAST:event_jBtnBuscaActionPerformed

    private void jBtnAprobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAprobarActionPerformed
        confirmacion("A");
    }//GEN-LAST:event_jBtnAprobarActionPerformed

    private void jBtnRechazarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnRechazarActionPerformed
        confirmacion("R");
    }//GEN-LAST:event_jBtnRechazarActionPerformed

    private void jBtnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAnularActionPerformed
        confirmacion("N");
    }//GEN-LAST:event_jBtnAnularActionPerformed

    private void jBtnRecuperarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnRecuperarActionPerformed
        confirmacion("");
    }//GEN-LAST:event_jBtnRecuperarActionPerformed

    private void jBtnAdelanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAdelanteActionPerformed
        int id_actual;
        if (this.jTxtId_presupuesto.getText().trim().isEmpty()){
            id_actual = 0;
        }else{
            id_actual = Integer.parseInt(this.jTxtId_presupuesto.getText().trim());
        }
        id_actual++;
        this.jTxtId_presupuesto.setText(String.valueOf(id_actual));
        traePresupuesto();
    }//GEN-LAST:event_jBtnAdelanteActionPerformed

    private void jBtnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAtrasActionPerformed
        int id_actual;
        if (this.jTxtId_presupuesto.getText().trim().isEmpty()){
            id_actual = 1;
        }else{
            id_actual = Integer.parseInt(this.jTxtId_presupuesto.getText().trim());
        }
        id_actual--;
        if (id_actual > 0){
            this.jTxtId_presupuesto.setText(String.valueOf(id_actual));
            traePresupuesto();
        }
    }//GEN-LAST:event_jBtnAtrasActionPerformed

    private void jComboZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboZonaActionPerformed
        int indiceZona = this.jComboZona.getSelectedIndex();
        llenaComboEntidadLugar(0);
        llenaComboVendedor(indiceZona);
        llenaComboProfesional(indiceZona);
    }//GEN-LAST:event_jComboZonaActionPerformed

    private void jMenuItemModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemModificaActionPerformed
        try {
            indiceTabla = jTabla.getSelectedRow();
            if (indiceTabla >= 0){
                this.jFmtCantidad.setValue((int)modelo.getValueAt(indiceTabla, 0));
                this.jTxtCodigo.setText(modelo.getValueAt(indiceTabla, 1).toString());
                this.jLblNombre.setText(modelo.getValueAt(indiceTabla, 2).toString());
                this.jComboProveedor.setSelectedIndex(-1);
                this.jComboProveedor.setSelectedItem(modelo.getValueAt(indiceTabla, 3).toString());
                this.jTxtObsProducto.setText(modelo.getValueAt(indiceTabla, 4).toString());
                this.jFmtPxUnit.setValue((double)modelo.getValueAt(indiceTabla, 5));
                this.id_productoFact = (int)modelo.getValueAt(indiceTabla, 7);
            }else{
                JOptionPane.showMessageDialog(this,"No ha seleccionado ninguna fila", "Información",JOptionPane.INFORMATION_MESSAGE);
            }   
        } catch (HeadlessException | NumberFormatException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
    }//GEN-LAST:event_jMenuItemModificaActionPerformed

    private void jMenuItemEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEliminaActionPerformed
        indiceTabla = jTabla.getSelectedRow();
        if (indiceTabla >= 0){
            modelo.removeRow(indiceTabla);
            this.indiceTabla = -1;
            calculaTotal();
        }else{
            JOptionPane.showMessageDialog(this,"No ha seleccionado ninguna fila", "Información",JOptionPane.INFORMATION_MESSAGE);
        }   
    }//GEN-LAST:event_jMenuItemEliminaActionPerformed

    private void jTxtCodigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTxtCodigoMouseClicked
        if (evt.getClickCount() == 2) {
            this.buscaProductoFact = new BuscaProductoFact(conexion, null, true, id_empresa, empresa);
            this.buscaProductoFact.setVisible(true);
            this.jTxtCodigo.setText(this.buscaProductoFact.getCodigo());
            this.jTxtObsProducto.setText(this.buscaProductoFact.getDescripcion());
            this.jFmtPxUnit.setText(this.buscaProductoFact.getPrecio());
            
            this.jTxtCodigoFocusLost(null);
        }
    }//GEN-LAST:event_jTxtCodigoMouseClicked

    private void jTxtPlazoOtroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtPlazoOtroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtPlazoOtroActionPerformed

    private void jFmtPxUnitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFmtPxUnitFocusLost
        if (validaProducto()){
            cargaProducto();
        }
    }//GEN-LAST:event_jFmtPxUnitFocusLost

    private void jTxtObsProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtObsProductoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_F5){
            if (validaProducto()){
                cargaProducto();
            }else{
                JOptionPane.showMessageDialog(this, "Faltan datos del producto", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jTxtObsProductoKeyReleased

    private void jTxtObsProductoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtObsProductoFocusGained
        this.jPanelObsProducto.setSize(this.jPanelObsProducto.getWidth(), 150);
        this.jScrollPaneObsProducto.setSize(this.jScrollPaneObsProducto.getWidth(), 150);
        this.jTxtObsProducto.setSize(this.jTxtObsProducto.getWidth(), 150);
        this.jTxtObsProducto.setCaretPosition(this.jTxtObsProducto.getText().trim().length());
    }//GEN-LAST:event_jTxtObsProductoFocusGained

    private void jTxtObsProductoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtObsProductoFocusLost
        this.jPanelObsProducto.setSize(this.jPanelObsProducto.getWidth(), 36);
        this.jScrollPaneObsProducto.setSize(this.jScrollPaneObsProducto.getWidth(), 36);
        this.jTxtObsProducto.setSize(this.jTxtObsProducto.getWidth(), 36);
    }//GEN-LAST:event_jTxtObsProductoFocusLost

    private void jTxtObsProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtObsProductoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            this.jTxtObsProducto.transferFocus();
        }
    }//GEN-LAST:event_jTxtObsProductoKeyPressed

    private void jMenuCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuCopiarActionPerformed
        String textoCopiado = this.jTxtObsProducto.getSelectedText();
        if (!textoCopiado.isEmpty()) {
            Utiles.copiarAlPortapapeles(textoCopiado);
        }
    }//GEN-LAST:event_jMenuCopiarActionPerformed

    private void jMenuPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuPegarActionPerformed
        String textoEnPortapapeles = Utiles.pegarPortapapeles();
        if (!textoEnPortapapeles.isEmpty()) {
            this.jTxtObsProducto.setText(this.jTxtObsProducto.getText() + textoEnPortapapeles);
            this.jTxtObsProducto.setCaretPosition(this.jTxtObsProducto.getText().trim().length());
        }
    }//GEN-LAST:event_jMenuPegarActionPerformed

    private void jMenuDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuDetalleActionPerformed
        int indiceTabla = jTabla.getSelectedRow();
        if (indiceTabla >= 0){
            String titulo = modelo.getValueAt(indiceTabla, 1).toString() + "   " + modelo.getValueAt(indiceTabla, 2).toString();
            String message = modelo.getValueAt(indiceTabla, 4).toString();
            JOptionPane.showMessageDialog(this, message, titulo, JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this,"No ha seleccionado ninguna fila", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jMenuDetalleActionPerformed

    private void jComboProfesional1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboProfesional1KeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboProfesional1.setSelectedIndex(-1);
            //this.jFmtRm1.setValue(0.00);
        }
    }//GEN-LAST:event_jComboProfesional1KeyReleased

    private void jTxtId_presupuestoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtId_presupuestoFocusLost
        if (!this.jTxtId_presupuesto.getText().replaceAll("\\s","").equals("")) {
            if(!this.jTxtId_presupuesto.getText().equals(this.id_presupuesto))
                id_presupuesto = traePresupuesto();
        }else{
            limpia();
            this.jFecha.requestFocus();
        }
    }//GEN-LAST:event_jTxtId_presupuestoFocusLost

    private void jTxtId_presupuestoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtId_presupuestoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtId_presupuestoFocusGained

    private void jTxtId_presupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtId_presupuestoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtId_presupuestoActionPerformed

    private void jComboEntidadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboEntidadMouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Entidad");
            Iterator it = this.arrayEntidad.iterator();
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

    private void jComboLugarCirugiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboLugarCirugiaMouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Entidad");
            Iterator it = this.arrayEntidad.iterator();
            while (it.hasNext()) {
                EntidadData tmp = (EntidadData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboLugarCirugia.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboLugarCirugiaMouseClicked

    private void jComboProfesional1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProfesional1MouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Profesional");
            Iterator it = this.arrayProfesional.iterator();
            while (it.hasNext()) {
                ProfesionalData tmp = (ProfesionalData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboProfesional1.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboProfesional1MouseClicked

    private void jComboProfesional2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProfesional2MouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.setTitle("Profesional");
            Iterator it = this.arrayProfesional.iterator();
            while (it.hasNext()) {
                ProfesionalData tmp = (ProfesionalData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboProfesional2.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboProfesional2MouseClicked

    private void jComboProfesional3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProfesional3MouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Profesional");
            Iterator it = this.arrayProfesional.iterator();
            while (it.hasNext()) {
                ProfesionalData tmp = (ProfesionalData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboProfesional3.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboProfesional3MouseClicked

    private void jComboVendedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboVendedorMouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Agente");
            Iterator it = this.arrayVendedor.iterator();
            while (it.hasNext()) {
                VendedorData tmp = (VendedorData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboVendedor.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboVendedorMouseClicked

    private void jComboProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProveedorMouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Proveedor");
            Iterator it = this.arrayProveedor.iterator();
            while (it.hasNext()) {
                ProveedorData tmp = (ProveedorData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboProveedor.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboProveedorMouseClicked

    private void jComboProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboProveedorKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            this.jComboProveedor.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jComboProveedorKeyReleased

    private void jFmtPxUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFmtPxUnitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFmtPxUnitActionPerformed

    private void jButtonHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHistorialActionPerformed
        Integer id_presupuesto = Integer.parseInt(this.jTxtId_presupuesto.getText().trim());
        PresupuestoEventosDialog eventos = new PresupuestoEventosDialog(null, true, id_presupuesto);
        eventos.setVisible(true);
    }//GEN-LAST:event_jButtonHistorialActionPerformed

    private void jComboProfesional2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboProfesional2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboProfesional2ActionPerformed

    private void jFmtRm2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFmtRm2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFmtRm2ActionPerformed

    private void jComboProfesional1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboProfesional1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboProfesional1ActionPerformed

    private void jFmtRm1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFmtRm1ActionPerformed
   
    }//GEN-LAST:event_jFmtRm1ActionPerformed

    private void jFmtRm1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFmtRm1MouseClicked
   if (evt.isMetaDown()){
            NumberImput numberImput = new NumberImput(JOptionPane.getFrameForComponent(this), true);
            numberImput.setTitle("Nuevo valor de RM 1");
            numberImput.setVisible(true);
            if (numberImput.getValue() != 0.00) {
                jFmtRm1.setValue(numberImput.getValue());
            }
        }
    }//GEN-LAST:event_jFmtRm1MouseClicked

    private void jFmtRm2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFmtRm2MouseClicked
       if (evt.isMetaDown()){           
            NumberImput numberImput = new NumberImput(JOptionPane.getFrameForComponent(this), true);
            numberImput.setTitle("Nuevo valor de RM 2");
            numberImput.setVisible(true);
            if (numberImput.getValue() != 0.00) {
                jFmtRm2.setValue(numberImput.getValue());
            }
        }
    }//GEN-LAST:event_jFmtRm2MouseClicked

    private void jComboProfesional4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProfesional4MouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Profesional 4");
            Iterator it = this.arrayProfesional.iterator();
            while (it.hasNext()) {
                ProfesionalData tmp = (ProfesionalData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboProfesional4.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboProfesional4MouseClicked

    private void jComboProfesional4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboProfesional4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboProfesional4ActionPerformed

    private void jComboProfesional4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboProfesional4KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboProfesional4KeyReleased

    private void jFmtRm4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFmtRm4MouseClicked
        if (evt.isMetaDown()){           
            NumberImput numberImput = new NumberImput(JOptionPane.getFrameForComponent(this), true);
            numberImput.setTitle("Nuevo valor de RM 4");
            numberImput.setVisible(true);
            if (numberImput.getValue() != 0.00) {
                jFmtRm4.setValue(numberImput.getValue());
            }
        }
    }//GEN-LAST:event_jFmtRm4MouseClicked

    private void jFmtRm4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFmtRm4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFmtRm4ActionPerformed

    private void jComboProfesional5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProfesional5MouseClicked
        if (evt.isMetaDown()) {
            Principal.general1.limpia();
            Principal.general1.setTitle("Profesional 5");
            Iterator it = this.arrayProfesional.iterator();
            while (it.hasNext()) {
                ProfesionalData tmp = (ProfesionalData) it.next();
                Object[] fila = {tmp.getNombre()};
                Principal.general1.add(fila);
            }
            Principal.general1.setVisible(true);
            if (!Principal.general1.getResultado().isEmpty()) {
                this.jComboProfesional5.setSelectedItem(Principal.general1.getResultado());
            }
        }
    }//GEN-LAST:event_jComboProfesional5MouseClicked

    private void jComboProfesional5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboProfesional5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboProfesional5ActionPerformed

    private void jComboProfesional5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboProfesional5KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboProfesional5KeyReleased

    private void jFmtRm5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFmtRm5MouseClicked
        if (evt.isMetaDown()){           
            NumberImput numberImput = new NumberImput(JOptionPane.getFrameForComponent(this), true);
            numberImput.setTitle("Nuevo valor de RM 5");
            numberImput.setVisible(true);
            if (numberImput.getValue() != 0.00) {
                jFmtRm5.setValue(numberImput.getValue());
            }
        }
    }//GEN-LAST:event_jFmtRm5MouseClicked

    private void jFmtRm5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFmtRm5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFmtRm5ActionPerformed

    private void jFmtRm3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFmtRm3MouseClicked
        if (evt.isMetaDown()){
            NumberImput numberImput = new NumberImput(JOptionPane.getFrameForComponent(this), true);
            numberImput.setTitle("Nuevo valor de RM 3");
            numberImput.setVisible(true);
            if (numberImput.getValue() != 0.00) {
                jFmtRm3.setValue(numberImput.getValue());
            }
        }
    }//GEN-LAST:event_jFmtRm3MouseClicked

    private void jComboEntidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboEntidadActionPerformed
        
    }//GEN-LAST:event_jComboEntidadActionPerformed

    private void jComboEntidadPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboEntidadPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboEntidadPropertyChange

    private void jTxtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtCodigoActionPerformed

    private void jFmtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFmtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFmtCantidadActionPerformed

    private void jTablaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTablaPropertyChange
       Double rmSum = 0.00;
        for(int i = 0; i < jTabla.getRowCount(); i ++){
            rmSum = rmSum + Double.parseDouble(modelo.getValueAt(i, modelo.findColumn("RM ($)")).toString());
        }
        this.jFmtRm1.setText(Double.toString(rmSum));
    }//GEN-LAST:event_jTablaPropertyChange

    private void jTablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTablaKeyPressed
      
        /*Double rmSum = 0.00;
        for(int i = 0; i < jTabla.getRowCount(); i ++){
            rmSum = rmSum + Double.parseDouble(modelo.getValueAt(i, 6).toString());
        }
        this.jFmtRm1.setText(Double.toString(rmSum));
        */
    }//GEN-LAST:event_jTablaKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAdelante;
    private javax.swing.JButton jBtnAnular;
    private javax.swing.JButton jBtnAprobar;
    private javax.swing.JButton jBtnAtras;
    private javax.swing.JButton jBtnBusca;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnImp;
    private javax.swing.JButton jBtnLimpiar;
    private javax.swing.JButton jBtnPdf;
    private javax.swing.JButton jBtnRechazar;
    private javax.swing.JButton jBtnRecuperar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnScr;
    private javax.swing.JButton jButtonHistorial;
    private javax.swing.JCheckBox jCheckAcompañamiento;
    private javax.swing.JCheckBox jCheckVip;
    private javax.swing.JComboBox jComboEntidad;
    private javax.swing.JComboBox jComboFormaPago;
    private javax.swing.JComboBox jComboLugarCirugia;
    private javax.swing.JComboBox jComboMantenimiento;
    private javax.swing.JComboBox jComboMotivo;
    private javax.swing.JComboBox jComboPlazo;
    private javax.swing.JComboBox jComboProfesional1;
    private javax.swing.JComboBox jComboProfesional2;
    private javax.swing.JComboBox jComboProfesional3;
    private javax.swing.JComboBox jComboProfesional4;
    private javax.swing.JComboBox jComboProfesional5;
    private javax.swing.JComboBox jComboProveedor;
    private javax.swing.JComboBox jComboTipoOperacion;
    private javax.swing.JComboBox jComboVendedor;
    private javax.swing.JComboBox jComboZona;
    private com.toedter.calendar.JDateChooser jFecha;
    private com.toedter.calendar.JDateChooser jFechaApertura;
    private javax.swing.JFormattedTextField jFmtCantidad;
    private javax.swing.JFormattedTextField jFmtCostoVenta;
    private javax.swing.JFormattedTextField jFmtHoraApertura;
    private javax.swing.JFormattedTextField jFmtPxUnit;
    private javax.swing.JFormattedTextField jFmtRm1;
    private javax.swing.JFormattedTextField jFmtRm2;
    private javax.swing.JFormattedTextField jFmtRm3;
    private javax.swing.JFormattedTextField jFmtRm4;
    private javax.swing.JFormattedTextField jFmtRm5;
    private javax.swing.JFormattedTextField jFmtTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblEstado;
    private javax.swing.JLabel jLblNombre;
    private javax.swing.JMenuItem jMenuCopiar;
    private javax.swing.JPopupMenu jMenuCopiarPegar;
    private javax.swing.JMenuItem jMenuDetalle;
    private javax.swing.JPopupMenu jMenuEliminaModifica;
    private javax.swing.JMenuItem jMenuItemElimina;
    private javax.swing.JMenuItem jMenuItemModifica;
    private javax.swing.JMenuItem jMenuPegar;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelObsProducto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPaneObsProducto;
    private javax.swing.JTable jTabla;
    private javax.swing.JTextField jTxtCodigo;
    private javax.swing.JTextField jTxtContratacion;
    private javax.swing.JTextField jTxtDireccion;
    private javax.swing.JTextField jTxtDni;
    private javax.swing.JTextField jTxtExpediente;
    private javax.swing.JTextArea jTxtExtra;
    private javax.swing.JTextField jTxtFormaPagoOtro;
    private javax.swing.JFormattedTextField jTxtId_presupuesto;
    private javax.swing.JTextField jTxtMantenimientoOtro;
    private javax.swing.JTextArea jTxtMotivo;
    private javax.swing.JTextArea jTxtObsProducto;
    private javax.swing.JTextArea jTxtObservaciones;
    private javax.swing.JTextField jTxtPaciente;
    private javax.swing.JTextField jTxtPlazoOtro;
    private javax.swing.JTextField jTxtTelefono;
    // End of variables declaration//GEN-END:variables
}
