/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.services;

import ar.com.bosoft.conexion.ActiveDatabase;
import ar.com.bosoft.conexion.Conexion;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import matera.jooq.Tables;
import matera.jooq.routines.Montofacturadobyprofesional;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

/**
 *
 * @author tinchogava
 */
public class RankingVentaService {
    
    public static BigDecimal getMontoFacturadoByProfesional(Integer id_profesional, java.util.Date d1, java.util.Date d2){
        java.sql.Date since = new java.sql.Date(d1.getTime());
        java.sql.Date until = new java.sql.Date(d2.getTime());
        return getMontoFacturadoByProfesional(id_profesional, since, until, ActiveDatabase.getDSLContext());
    }
    
    private static BigDecimal getMontoFacturadoByProfesional(Integer id_profesional, java.sql.Date since, java.sql.Date until, DSLContext dsl){
        Montofacturadobyprofesional procedure = new Montofacturadobyprofesional();
        procedure.setIdProfesional(id_profesional);
        procedure.setDesde(since);
        procedure.setHasta(until);
        procedure.execute(dsl.configuration());
        return procedure.getReturnValue() != null ? procedure.getReturnValue() : BigDecimal.ZERO;   
    }
    
}
