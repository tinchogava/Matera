/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.BelongsTo;
/**
 *
 * @author h2o
 */
@Table("cajaasignada")
@IdName("id_cajaAsignada")
@BelongsTo(parent = Presupuesto.class, foreignKeyName = "id_presupuesto")
public class CajaAsignada extends Model {
    public CajaDeposito getCajaDeposito(){
        return CajaDeposito.findById(this.get("id_cajaDeposito"));
    }
}
