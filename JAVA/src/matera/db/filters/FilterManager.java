/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.filters;

import ar.com.bosoft.conexion.ActiveDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import matera.db.Entidad;
import matera.db.Factura;
import matera.db.Prestacion;
import matera.db.Presupuesto;
import matera.db.PresupuestoProducto;
import matera.db.Profesional;
import matera.db.Remito;
import matera.db.Tecnico;
import matera.db.TipoOperacion;
import matera.db.Vendedor;
import matera.jooq.Tables;
import static matera.jooq.Tables.ENTIDAD;
import static matera.jooq.Tables.PRESTACION;
import static matera.jooq.Tables.PRESUPUESTO;
import static matera.jooq.Tables.PRODUCTO;
import static matera.jooq.Tables.PROVEEDOR;
import static matera.jooq.Tables.REMITO;
import static matera.jooq.Tables.REMITO_CONSUMIDO;
import static matera.jooq.Tables.STOCKDETALLE;
import static matera.jooq.Tables.TECNICO;
import static matera.jooq.Tables.ZONA;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.RowListenerAdapter;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;

/**
 *
 * @author h2o
 */
public class FilterManager {
    public static ArrayList getControlEntidades(String query,Object[] params){
        ArrayList list = new ArrayList();
        String selectFields ="select"
                + " entidad.id_entidad,"
                + " entidad.nombre as entidad,"
                + " zona.nombre as zona,"
                + " count(presupuesto.id_presupuesto) as cantPresuEmitidos,"
                + " sum(ifnull(presupuesto.total, 0.00)) as montoPresuEmitidos,"
                + " sum(if(presupuesto.estado NOT IN ('R', 'N', 'P'), 1, 0)) as cantPresuAprobados,"
                + " sum(if(presupuesto.estado NOT IN ('R', 'N', 'P'), presupuesto.total, 0.00)) as montoPresuAprobados,"
                + " SUM(ifnull(IF(factura.dc = 'D', 1, (-1)) * neto + percIIBB + iva, 0)) as montoFactTotal,"
                + " count(DISTINCT factura.id_presupuesto) as cantPresuFacturados"
                ;
        String fromFields = " from presupuesto" +
                            " left join entidad on entidad.id_entidad = presupuesto.id_entidad" +
                            " left join factura on factura.id_presupuesto = presupuesto.id_presupuesto" +
                            " left join zona on zona.id_zona = presupuesto.id_zona";
        String searchQuery = selectFields + fromFields + " WHERE "+ query;
        Base.find(searchQuery, params).with(new RowListenerAdapter() {
                    @Override
                    public void onNext(Map row) {
                        list.add(row);
                    }
                });
        return list;
    }
    
    public static List<Presupuesto> getTrazabilidad(String query,Object[] params){
        LazyList<Presupuesto> presupuestos = Presupuesto.find(query, params).include(Remito.class, Entidad.class, Profesional.class, Tecnico.class, Prestacion.class);
        Presupuesto.addConsumido(presupuestos);
        
        return presupuestos;
    }
    
    public static Result<Record> getTrazabilidad(Condition condition){
        // Join two tables
        DSLContext dsl = ActiveDatabase.getDSLContext();
        SelectConditionStep step;
        step = dsl.select()
                              .from(PRESUPUESTO)
                              .join(REMITO).on(REMITO.ID_PRESUPUESTO.equal(PRESUPUESTO.ID_PRESUPUESTO))
                              .join(REMITO_CONSUMIDO).on(REMITO_CONSUMIDO.ID_REMITO.equal(REMITO.ID_REMITO))
                              .join(STOCKDETALLE).on(STOCKDETALLE.ID_STOCKDETALLE.equal(REMITO_CONSUMIDO.ID_STOCKDETALLE))
                              .join(PRODUCTO).on(PRODUCTO.ID_PRODUCTO.equal(STOCKDETALLE.ID_PRODUCTO))
                              .leftJoin(ENTIDAD).on(ENTIDAD.ID_ENTIDAD.equal(PRESUPUESTO.ID_ENTIDAD))
                              .leftJoin(ENTIDAD.as("lugarCirugia")).on(Tables.ENTIDAD.as("lugarCirugia").ID_ENTIDAD.equal(PRESUPUESTO.ID_LUGARCIRUGIA))
                              .leftJoin(ZONA).on(ZONA.ID_ZONA.eq(PRESUPUESTO.ID_ZONA))
                              .leftJoin(TECNICO).on(TECNICO.ID_TECNICO.eq(PRESUPUESTO.ID_TECNICO))
                              .leftJoin(PRESTACION).on(PRESTACION.ID_PRESTACION.eq(PRESUPUESTO.ID_PRESTACION))
                              .leftJoin(PROVEEDOR).on(PROVEEDOR.ID_PROVEEDOR.eq(PRODUCTO.ID_PROVEEDOR))
                              .leftJoin(Tables.PROFESIONAL).on(Tables.PRESUPUESTO.ID_PROFESIONAL1.eq(Tables.PROFESIONAL.ID_PROFESIONAL))
                              .where(condition)
                              ;
        
        return step.fetch();
    }    
    
    public static List<Presupuesto> getCostoVentas(String query,Object[] params, Integer filter){
        LazyList<Presupuesto> presupuestos = null;
        switch(filter){
            case 0:
                presupuestos = Presupuesto.find(query, params).include(Remito.class, Factura.class, Vendedor.class, TipoOperacion.class, PresupuestoProducto.class);
                break;
            default:
                String select = "SELECT presupuesto.*  FROM presupuesto";
                String join = " JOIN remito on remito.id_presupuesto = presupuesto.id_presupuesto AND " + query;
                String sql = select + join;
                presupuestos = Presupuesto.findBySQL( sql, params).include(Remito.class, Factura.class, Vendedor.class, TipoOperacion.class, PresupuestoProducto.class);
                break;
        }
        Presupuesto.addConsumido(presupuestos);
        return presupuestos;
    }
}
