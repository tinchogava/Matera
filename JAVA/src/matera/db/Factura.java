/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
/**
 *
 * @author h2o
 */
@Table("factura")
@IdName("id_factura")
@BelongsToParents({ 
@BelongsTo(foreignKeyName="id_presupuesto",parent=Presupuesto.class)
})
public class Factura extends Model {
    
    // Lista de tipos de comprobantes que no se tienen en cuenta al contabilizar los montos (RECIBOS)
    public List noFacturable = new ArrayList(Arrays.asList(4,16,17,18));
    
    public Double getMonto(){
        
        if (noFacturable.contains(this.get("id_tipoComp")))
            return 0.0;
        
        Double monto = this.getDouble("neto") + this.getDouble("percIIBB") + this.getDouble("iva");
        if (!this.get("dc").equals("D")){
            monto *= -1;
        }
        return monto;
    }
    
}
