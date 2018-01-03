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
import org.javalite.activejdbc.annotations.BelongsToParents;
/**
 *
 * @author h2o
 */
@Table("presupuestoprod")
@IdName("id_PresupuestoProd")
@BelongsToParents({ 
@BelongsTo(foreignKeyName="id_presupuesto",parent=Presupuesto.class)
})
public class PresupuestoProducto extends Model {
    public Double getMonto(){
        return this.getDouble("pxUnit") * this.getInteger("cantidad");
    }
}
