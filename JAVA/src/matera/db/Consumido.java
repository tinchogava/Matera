/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;
import org.javalite.common.Util;

@Table("remito_consumido")
@IdName("id_stockdetalle")
@BelongsToParents({
@BelongsTo(foreignKeyName="id_remito", parent = Remito.class),
@BelongsTo(foreignKeyName="id_stockdetalle", parent = RemitoDetalle.class)
})
public class Consumido extends Model {
    
    public RemitoDetalle getRemitoDetalle(){
        return this.parent(RemitoDetalle.class);
    }
    
    public static List findByRemitoIds(List ids){
        List<Consumido> consumido = Consumido.where("id_remito IN ('" + Util.join(ids, "', '") + "')").orderBy("id_remito").include(RemitoDetalle.class).load();
        HashMap<Integer,Model> map = new HashMap<>();
        Set set = new HashSet();
        consumido.stream().forEach(c -> set.add(c.getRemitoDetalle().get("id_producto")));
        List<Producto> productos = Producto.where("id_producto IN ('" + Util.join(set, "', '") + "')").load();
        productos.stream().forEach(p -> map.put(p.getInteger("id_producto"),p));
        consumido.stream().forEach(c -> c.getRemitoDetalle().addParent(map.get(c.getRemitoDetalle().getInteger("id_producto"))));
        return consumido;
    }
    
    public static List<Consumido> filterByProductoField(List<Consumido> list, String field, Object value){
        return list.stream().filter(c -> c.getRemitoDetalle().getProducto().get(field).equals(value))
        .collect(Collectors.toList());        
    }
    
    public static List<Consumido> filterByRemitoDetalleField(List<Consumido> list, String field, Object value){
        return list.stream().filter(c -> c.getRemitoDetalle().get(field).equals(value))
        .collect(Collectors.toList());        
    }    
    
    public static List<Consumido> filterBy(List<Consumido> list, String field, Object value){
        return list.stream().filter(rd -> rd.get(field).equals(value))
        .collect(Collectors.toList());        
    }     
}
