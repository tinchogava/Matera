/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;

import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.ProveedorRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author h2o
 */
public class CacheProveedores implements CacheRecords {
    
    String key = "proveedores";
    private static CacheProveedores instance = null;
    
    protected CacheProveedores() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheProveedores instance() {
        if(instance == null) {
           synchronized(CacheProveedores.class) { 
              instance = new CacheProveedores();
           }
        }
        return instance;
    }        
    
    @Override
    public Result<Record> get() {
        Result<Record> records = CacheMgr.getCache().get(key);
        if (records == null){
            fetch();
        }
        return CacheMgr.getCache().get(key);
    }

    @Override
    public Result<Record> getHabilitados() {
        return get().intoGroups(Tables.PROVEEDOR.HABILITA).get("S");
    }

    @Override
    public void fetch() {
        CacheMgr.getCache().put(key, ActiveDatabase.getDSLContext()
                    .select().from(Tables.PROVEEDOR)
                    .fetch().sortAsc(Tables.PROVEEDOR.NOMBRE));  
    }
    
    public ProveedorRecord getProveedor(Integer id){
        return get().into(Tables.PROVEEDOR).intoGroups(Tables.PROVEEDOR.ID_PROVEEDOR).get(id).get(0);
    }
    
    public ProveedorRecord getProveedor(String name){
        return get().into(Tables.PROVEEDOR).intoGroups(Tables.PROVEEDOR.NOMBRE).get(name).get(0);
    }    
    
    public Result<ProveedorRecord> getProveedores(){
        return get().into(Tables.PROVEEDOR);
    }
    
    public Result<ProveedorRecord> getProveedoresHabilitados(){
        return getHabilitados().into(Tables.PROVEEDOR);
    }
}
