/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.managers;

import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;
import matera.jooq.Tables;
import matera.jooq.tables.records.CotizacionRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Result;

/**
 *
 * @author tinchogava
 */
public class CotizacionMgr {
   
    public static Result<CotizacionRecord> getCotizacionByFecha (DSLContext dsl, Date fechaConsumido){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaConsumido);
        Integer mes = ((calendar.get(Calendar.MONTH) - 1 + 1) % 12) + 1;
        Integer año = calendar.get(Calendar.YEAR);
        Condition condition = Tables.COTIZACION.MES.eq(mes);
        condition.and(Tables.COTIZACION.AÑO.eq(año));
        if(((mes.compareTo(6))< 0) && (año.equals(2015))){
            return dsl.select()
                    .from(Tables.COTIZACION)
                    .orderBy(Tables.COTIZACION.FECHAREAL.asc())
                    .limit(1)
                    .fetchInto(Tables.COTIZACION);
        } else if ((mes.compareTo(3) > 0) &&(año.equals(2016))){
            return dsl.select()
                    .from(Tables.COTIZACION)
                    .orderBy(Tables.COTIZACION.FECHAREAL.desc())
                    .limit(1)
                    .fetchInto(Tables.COTIZACION);
        } else {
            return dsl.select()
                    .from(Tables.COTIZACION)
                    .where(condition)
                    .orderBy(Tables.COTIZACION.FECHAREAL.desc())
                    .limit(1)
                    .fetchInto(Tables.COTIZACION);
        }
    }
}
