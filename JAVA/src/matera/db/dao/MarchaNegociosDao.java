/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.dao;

import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.pojos.MarchaNegocios;
import org.jooq.impl.DSL;

/**
 *
 * @author h2o
 */
public class MarchaNegociosDao extends matera.jooq.tables.daos.MarchaNegociosDao {
    public MarchaNegociosDao(){
        super(ActiveDatabase.getDSLContext().configuration());
    }
    
    public MarchaNegocios fetchByYearAndMonth(Integer year, Integer month){
        return DSL.using(configuration()).select()
                .from(Tables.MARCHA_NEGOCIOS)
                .where(Tables.MARCHA_NEGOCIOS.YEAR.eq(year)
                .and(Tables.MARCHA_NEGOCIOS.MONTH.eq(month)))
                .fetchOneInto(MarchaNegocios.class); 
    }
}