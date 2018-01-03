/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db;

import ar.com.bosoft.conexion.ActiveDatabase;
import java.util.Arrays;
import java.util.List;
import static matera.jooq.Tables.ENTIDAD;
import static matera.jooq.Tables.FORECASTENTIDAD;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.Table;
import org.javalite.activejdbc.annotations.IdName;
import org.jooq.DSLContext;
import org.jooq.Select;
//import static org.jooq.impl.DSL.*;
/**
 *
 * @author h2o
 */
@Table("forecastentidad")
@IdName("id_forecastEntidad")
@BelongsTo(parent = Entidad.class, foreignKeyName = "id_entidad")

public  class ForecastEntidad extends Model {
    
    public static List<Entidad> getEntidades(Integer zona){
        
        DSLContext dsl = ActiveDatabase.getDSLContext();
        Select<?> s1 = dsl.select(ENTIDAD.fields()).from(ENTIDAD).join(FORECASTENTIDAD).on(ENTIDAD.ID_ENTIDAD.equal(FORECASTENTIDAD.ID_ENTIDAD)).and(ENTIDAD.ID_ZONA.equal(zona));
        return Entidad.findBySQL(s1.getSQL(),Arrays.asList(zona).toArray());
    }
    
}
