/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;

import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;

/**
 *
 * @author h2o
 */
public class CacheComposicionCaja implements CacheRecords {

    //static Result<Record> records = null;
    String key = "composicionCaja";
    private static CacheComposicionCaja instance = null;
    
    protected CacheComposicionCaja() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheComposicionCaja instance() {
        if(instance == null) {
           synchronized(CacheComposicionCaja.class) { 
              instance = new CacheComposicionCaja();
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
        return get().intoGroups(Tables.PRODUCTO.HABILITA).get("S");
    }

    @Override
    public void fetch() {
        
//        SelectOnConditionStep s = ActiveDatabase.getDSLContext().select().from(Tables.PRODUCTO)
//                    .join(Tables.CAJACOMPOSICION).on(Tables.CAJACOMPOSICION.ID_PRODUCTO.eq(Tables.PRODUCTO.ID_PRODUCTO))
//                    .join(Tables.CAJADEPOSITO).on(Tables.CAJADEPOSITO.ID_CAJADEPOSITO.eq(Tables.CAJACOMPOSICION.ID_CAJADEPOSITO))
//                    .join(Tables.CAJA).on(Tables.CAJA.ID_CAJA.eq(Tables.CAJADEPOSITO.ID_CAJA))
//                    .leftJoin(Tables.PROVEEDOR).on(Tables.PRODUCTO.ID_PROVEEDOR.eq(Tables.PROVEEDOR.ID_PROVEEDOR))
//                    .join(Tables.CODCONSUMO).on(Tables.CODCONSUMO.ID_CODCONSUMO.eq(Tables.PRODUCTO.ID_CODCONSUMO))
//                    .join(Tables.MONEDA).on(Tables.MONEDA.ID_MONEDA.eq(Tables.PRODUCTO.ID_MONEDA))
//                    .join(Tables.CLASIPRODUCTO).on(Tables.CLASIPRODUCTO.ID_CLASIPRODUCTO.eq(Tables.PRODUCTO.ID_CLASIPRODUCTO));
//        
//        CacheMgr.getCache().put(key,s.fetch());
    }
    
    private Result<Record> fetch(Integer id){
        SelectConditionStep s = ActiveDatabase.getDSLContext().select().from(Tables.PRODUCTO)
                    .join(Tables.CAJACOMPOSICION).on(Tables.CAJACOMPOSICION.ID_PRODUCTO.eq(Tables.PRODUCTO.ID_PRODUCTO))
                    .join(Tables.CAJADEPOSITO).on(Tables.CAJADEPOSITO.ID_CAJADEPOSITO.eq(Tables.CAJACOMPOSICION.ID_CAJADEPOSITO))
                    .join(Tables.CAJA).on(Tables.CAJA.ID_CAJA.eq(Tables.CAJADEPOSITO.ID_CAJA))
                    .leftJoin(Tables.PROVEEDOR).on(Tables.PRODUCTO.ID_PROVEEDOR.eq(Tables.PROVEEDOR.ID_PROVEEDOR))
                    .leftJoin(Tables.CODCONSUMO).on(Tables.CODCONSUMO.ID_CODCONSUMO.eq(Tables.PRODUCTO.ID_CODCONSUMO))
                    .join(Tables.MONEDA).on(Tables.MONEDA.ID_MONEDA.eq(Tables.PRODUCTO.ID_MONEDA))
                    .join(Tables.CLASIPRODUCTO).on(Tables.CLASIPRODUCTO.ID_CLASIPRODUCTO.eq(Tables.PRODUCTO.ID_CLASIPRODUCTO))
                .where(Tables.CAJADEPOSITO.ID_CAJADEPOSITO.eq(id));
        
        return s.fetch();
    }
    
    
    public Result<Record> getComposicionCaja(Integer id){
        try{
            return fetch(id)
                    .sortAsc(Tables.PRODUCTO.CODIGO);
        }
        catch(Exception e){
            return fetch(id);
        }
    }
}
