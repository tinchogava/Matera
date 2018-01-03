/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.gui;

import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.ActiveDatabase;
import ar.com.bosoft.formularios.DetalleCajaRemito;
import ar.com.bosoft.formularios.Principal;
import ar.com.matera.TableModels.ProductoTableModel;
import ar.com.matera.TableModels.RemitoDetalleTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import matera.TableObjects.CargaProductoTableObject;
import matera.TableObjects.ProductoTableObject;
import matera.TableObjects.RemitoDetalleTableObject;
import matera.cache.CacheProductos;
import matera.helpers.LogHelper;
import matera.jooq.Tables;
import static matera.jooq.tables.Remito.REMITO;
import matera.jooq.tables.daos.RemitoDao;
import matera.jooq.tables.daos.StockdetalleDao;
import matera.jooq.tables.pojos.Stockdetalle;
import matera.jooq.tables.records.ClasiproductoRecord;
import matera.jooq.tables.records.ProfesionalRecord;
import matera.jooq.tables.records.ProveedorRecord;
import matera.jooq.tables.records.RemitoRecord;
import matera.jooq.tables.records.ZonaRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

/**
 *
 * @author h2o
 */
public abstract class RemitoInternalFrame extends javax.swing.JInternalFrame {
    protected DefaultTableModel modeloCaja, modeloCajaSeleccion;
    protected ProductoTableModel modeloProducto;
    protected RemitoDetalleTableModel modeloSeleccion;
    protected Integer id_zona;
    protected Result<Record> productos;
    
    protected Map<Integer, List<RemitoDetalleTableObject>> composicionCajaMap = new HashMap<>();
    
    protected DetalleCajaRemito detalleCajaRemito;
    
    protected SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
    protected Salida salida;
    
    protected Result<ZonaRecord> zonas;
    protected Result<ProveedorRecord> proveedores;
    protected Result<ProfesionalRecord> profesionales;
    protected Result<ClasiproductoRecord> clasiProductos;
    
    protected void setZona(Integer id_zona){
        this.id_zona = id_zona;
    }
    
    protected Integer getZona(){
        return this.id_zona;
    }
    
    protected void comboClasificacionItemSelected(JComboBox combo){
        try {
            if (combo.getSelectedIndex() >=0){
                String clasificacion = combo.getSelectedItem().toString();
                filtraProducto(clasificacion);            
            }
        } catch (Exception ex) {
            Utiles.showErrorMessage(ex);
        }
    }
    
    protected void loadSeleccion(List<CargaProductoTableObject> list){
        list.forEach(p -> {
            for (int i=0; i < p.getCantidad(); i++){
                RemitoDetalleTableObject rdo = new RemitoDetalleTableObject(p.getProducto(), p.getClasiProducto(), id_zona);
                modeloSeleccion.addRow(rdo);
            }
        });

        //Rectangle r = this.jTablaSeleccion.getCellRect(modeloSeleccion.getRowCount() - 1, 0, true);
        //this.jScrollSeleccion.getViewport().scrollRectToVisible(r);
        //this.indiceProducto = 0;    
    }    
    
    protected void consultaProducto(){
        productos = CacheProductos.instance().getHabilitados();
    }    
    
    protected void getDatosComposicion(Integer id_cajaDeposito, Integer row, String nombreCaja){
        if (composicionCajaMap.get(row) != null){
            composicionCajaMap.put( row, this.detalleCajaRemito.getDatosComposicion(composicionCajaMap.get(row), nombreCaja, id_zona));
        }
        else{
            composicionCajaMap.put( row, this.detalleCajaRemito.getDatosComposicion(id_cajaDeposito, nombreCaja, id_zona));
        }
    }
    
    protected void deleteDatosComposicion(Integer row){
        composicionCajaMap.put(row, null);
    }
    
    protected void insertRemitoDetalle(DSLContext dsl, Integer id_remito) {
        // String dc = "C";
        // Date venc = null;
        // String observaciones = "RM-R " + this.jTxtSucursal.getText() + this.jTxtNumero.getText() 
        //    + "\nTurno: " + this.jLblId_presupuesto.getText()
        //    + "\nDestino: " + this.jLblLugarCirugia.getText();

        List<Stockdetalle> stockdetalleList = new ArrayList<>();
        StockdetalleDao stockDetalleDAO = new StockdetalleDao(dsl.configuration());
        
        composicionCajaMap.forEach((k,v)->{
            if (v != null){
                v.forEach(rdo ->{
                    Stockdetalle stockdetalle = rdo.getStockdetalle().into(Stockdetalle.class);
                    stockdetalle.setIdRemito(id_remito);
                    stockdetalleList.add(stockdetalle);
                });
            } 
        });
        
        List<RemitoDetalleTableObject> rdList = modeloSeleccion.getRowsAsList();
        rdList.forEach(rd -> {
            Stockdetalle stockdetalle = rd.getStockdetalle().into(Stockdetalle.class);
            stockdetalle.setIdRemito(id_remito);
            stockdetalleList.add(stockdetalle);
        });
        
        if (stockdetalleList.isEmpty())
            throw new IndexOutOfBoundsException();
        
        stockDetalleDAO.insert(stockdetalleList);      
    }    
    
    public static class TIPO_ENTIDAD{
       public static final int PRESUPUESTO = 1, PROVEEDOR = 2, PROFESIONAL = 3, INTERSUCURSAL = 4;
    }
    
    protected boolean existeRemito(String numComp){
        try{
            Integer c = new RemitoDao(ActiveDatabase.getDSLContext().configuration())
                    .fetchByNumcomp(numComp)
                    .size();
            if (c > 0)
                return true;
            
        }catch (Exception ex){
            Utiles.showErrorMessage(ex);
        }
        return false;
    }    
    
    protected Integer insertRemito(Integer entityId, Integer entityType, Integer id_entidad, String numComp, String cajas, String sets, String observaciones) {
        
        if(entityId < 1) {
            Utiles.showMessage("ID de entidad incorrecto!");
            return Utiles.INSERT.ERROR;
        }
        
        try{
        
        DSLContext dsl = ActiveDatabase.getDSLContext(false);
   
        dsl.transaction(configuration ->{
            
            Integer id_presupuesto = 0, id_proveedor = 0, id_profesional = 0, id_zonadestino = 0;
            
            switch(entityType){
                case TIPO_ENTIDAD.PRESUPUESTO:
                    id_presupuesto = entityId;
                    break;
                case TIPO_ENTIDAD.PROFESIONAL:
                    id_profesional = entityId;                  
                    break;
                case TIPO_ENTIDAD.PROVEEDOR:
                    id_proveedor = entityId;
                    break;
                case TIPO_ENTIDAD.INTERSUCURSAL:
                    id_zonadestino = entityId;
                    break;
                default:
                    Utiles.showMessage("Tipo de entidad incorrecto!");
                    return;
            }             
                
                RemitoRecord remitoRecord = DSL.using(configuration).insertInto(REMITO)
                        .set(REMITO.ID_PRESUPUESTO, id_presupuesto)
                        .set(REMITO.ID_TIPOCOMP, 18)
                        .set(REMITO.SETS, sets)
                        .set(REMITO.FECHA, new java.sql.Date(new Date().getTime()))
                        .set(REMITO.NUMCOMP, numComp)
                        .set(REMITO.ID_ENTIDAD, id_entidad)
                        .set(REMITO.ID_PROVEEDOR, id_proveedor)
                        .set(REMITO.ID_DESTINO, id_profesional)
                        .set(REMITO.ID_ZONADESTINO, id_zonadestino)
                        .set(REMITO.ID_ZONA, id_zona)
                        .set(REMITO.OBSERVACIONES, observaciones)
                        .set(REMITO.CAJAS, cajas)
                        .set(REMITO.ID_EMPRESA, Principal.getIdEmpresa())
                        .set(REMITO.USUARIO, Principal.getUsuarioName())
                        .returning(REMITO.ID_REMITO)
                        .fetchOne();

                Integer idRemito = remitoRecord.getIdRemito();
                
                LogHelper.logRemitoEvent(idRemito, Utiles.LOG_EVENTO.CREA_REMITO);
                insertRemitoDetalle(DSL.using(configuration), idRemito);
        });
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
            return Utiles.INSERT.ERROR;
        }
        return Utiles.INSERT.SUCCESS;
    }
    
    protected void filtraProducto(String clasificacion){
        modeloProducto.removeAllRows();
        modeloProducto.fireTableDataChanged();
        
        if (productos != null){
            productos.forEach(r ->{
                ClasiproductoRecord clasiProductoRecord = r.into(Tables.CLASIPRODUCTO);
                if (!clasificacion.equals("-- TODOS --")){
                    if (clasificacion.equals(clasiProductoRecord.getNombre())){
                        ProductoTableObject productoTableObject = new ProductoTableObject(r.into(Tables.PRODUCTO), clasiProductoRecord, r.into(Tables.MONEDA));
                        modeloProducto.addRow(productoTableObject);
                    }
                }
                else{
                    ProductoTableObject productoTableObject = new ProductoTableObject(r.into(Tables.PRODUCTO), r.into(Tables.CLASIPRODUCTO), r.into(Tables.MONEDA));
                    modeloProducto.addRow(productoTableObject);
                }
            });        
        }
    }
    
    protected void agregarProductos(JTable table){
        
        if (!table.getModel().equals(modeloProducto))
            return;
        
        CargaProductos JDialogCargaProductos = new CargaProductos(null, true);
        List<CargaProductoTableObject> list = new ArrayList<>();
        int[] selectedRows = table.getSelectedRows();
        for (int i=0; i<selectedRows.length;i++){
            ProductoTableObject p = modeloProducto.getRow(table.convertRowIndexToModel(selectedRows[i]));
            list.add(new CargaProductoTableObject(p.getProducto(), p.getClasiProducto(), p.getMoneda(), 1));
        }
        JDialogCargaProductos.loadSeleccion(list);
        JDialogCargaProductos.setVisible(true);
        if (JDialogCargaProductos.isLoaded())
            loadSeleccion(JDialogCargaProductos.getProductos());
    }
    
    protected void removeSeleccionados(JTable table){
        
        if (!table.getModel().equals(modeloSeleccion))
            return;
        //int[] selectedRows = table.getSelectedRows();
        while (table.getSelectedRowCount() > 0){
            modeloSeleccion.removeRow(table.convertColumnIndexToModel(table.getSelectedRow()));
        }
    }
    
    protected void tablaProductoMouseClicked(JTable table, java.awt.event.MouseEvent evt){
        if (evt.getClickCount() == 2) {
            int row = table.convertRowIndexToModel(table.getSelectedRow());
            ProductoTableObject pto = modeloProducto.getRow(row);
            List<CargaProductoTableObject> list = new ArrayList();
            list.add(new CargaProductoTableObject(pto.getProducto(), pto.getClasiProducto(), pto.getMoneda(), 1));
            loadSeleccion(list);
        }    
    }
}
