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
@Table("cajacomposicion")
@IdName("id_cajaComposicion")
@BelongsTo(parent = CajaDeposito.class, foreignKeyName = "id_cajaDeposito")
public class CajaComposicion extends Model {
    
    public Producto getProducto(){
        return Producto.findById(this.get("id_producto"));
    }
}
