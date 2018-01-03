/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.Table;
import org.javalite.activejdbc.annotations.IdName;
/**
 *
 * @author h2o
 */
@Table("cajadeposito")
@IdName("id_cajaDeposito")
@BelongsTo(parent = Caja.class, foreignKeyName = "id_caja")
public class CajaDeposito extends Model {
    public Zona getZona(){
        return Zona.findById(this.get("id_zona"));
    }
    
    public boolean isHabilitada(){
        return this.get("habilita") == "S";
    }
}
