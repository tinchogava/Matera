/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.managers;

import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.ProveedorRecord;
import org.jooq.DSLContext;

/**
 *
 * @author tinchogava
 */
public class ProveedorMgr {
    public static ProveedorRecord getProveedorById(Integer id_presupuesto){
        return getProveedorById(id_presupuesto, ActiveDatabase.getDSLContext());
    }
    
    public static ProveedorRecord getProveedorById(Integer id_presupuesto, DSLContext dsl){
        return dsl.select()
                .from(Tables.PROVEEDOR)
                .where(Tables.PROVEEDOR.ID_PROVEEDOR.eq(id_presupuesto))
                .fetchOneInto(Tables.PROVEEDOR);
    }
}
