/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.services;

import java.util.Calendar;
import java.util.List;
import matera.db.dao.RemitoDao;
import matera.jooq.tables.records.RemitoRecord;
import org.jooq.Result;

/**
 *
 * @author tinchogava
 */
public class RemitoService {
    
    public static Result<RemitoRecord> getRemitosByPresupuesto(Integer id_presupuesto){
        return new RemitoDao().getRemitosByPresupuesto(id_presupuesto);
    }
    
    public static Result<RemitoRecord> getRemitoByFechaConsumo(Calendar d1, Calendar d2, List<Integer> yaCosteados){
        java.sql.Date since = new java.sql.Date(d1.getTime().getTime());
        java.sql.Date until = new java.sql.Date(d2.getTime().getTime());
        return new RemitoDao().getRemitosByFechaConsumo(since, until, yaCosteados);
    }
}
