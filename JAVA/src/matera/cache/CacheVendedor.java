/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;


import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.VendedorRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author h2o
 */
public class CacheVendedor implements CacheRecords {
    
    String key = "vendedor";
    private static CacheVendedor instance = null;
    
    protected CacheVendedor() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheVendedor instance() {
        if(instance == null) {
           synchronized(CacheVendedor.class) { 
              instance = new CacheVendedor();
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
        return get().intoGroups(Tables.VENDEDOR.HABILITA).get("S");
    }

    @Override
    public void fetch() {
        CacheMgr.getCache().put(key, ActiveDatabase.getDSLContext()
                    .select().from(Tables.VENDEDOR)
                    .where(Tables.VENDEDOR.ID_ESPECIALIDAD.eq(13))
                    .fetch().sortAsc(Tables.VENDEDOR.NOMBRE));  
    }
    
    public VendedorRecord getVendedor(Integer id){
        return get().into(Tables.VENDEDOR).intoGroups(Tables.VENDEDOR.ID_VENDEDOR).get(id).get(0);
    }
    
    public VendedorRecord getVendedor(String name){
        return get().into(Tables.VENDEDOR).intoGroups(Tables.VENDEDOR.NOMBRE).get(name).get(0);
    }    
    
    public Result<VendedorRecord> getVendedores(){
        return get().into(Tables.VENDEDOR);
    }
    
    public Result<VendedorRecord> getVendedoresHabilitados(){
        return getHabilitados().into(Tables.VENDEDOR);
    }    
    
    public Result<VendedorRecord> getVendedoresForZona(Integer id_zona){
        return getHabilitados().into(Tables.VENDEDOR).intoGroups(Tables.VENDEDOR.ID_ZONA).get(id_zona);
    }    
}

