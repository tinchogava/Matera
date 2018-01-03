/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db;
import ar.com.bosoft.clases.Utiles;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.common.Util;
/**
 *
 * @author h2o
 */
@Table("stockdetalle")
@IdName("id_stockdetalle")
@BelongsToParents({ 
@BelongsTo(foreignKeyName="id_producto",parent=Producto.class),
@BelongsTo(foreignKeyName="id_remito", parent = Remito.class)
})

public class RemitoDetalle extends Model {
    
    String serial;
    
    public static String insertPreparedStatementString(){
        Set columns = RemitoDetalle.getMetaModel()
                .getColumnMetadata()
                .keySet()
                .stream()
                .filter(k -> !k.toLowerCase().equals(
                        RemitoDetalle
                                .getMetaModel()
                                .getIdName().toLowerCase()))
                .collect(Collectors.toSet());
        
        String [] c = new String [columns.size()];
        Arrays.fill(c, "?");
        return "INSERT INTO " + RemitoDetalle.getTableName()
                + "(" + Util.join(columns, ", ") + ")"
                + " VALUES " + "(" + Util.join(c, ", ") + ")";
    }
    
    public Producto getProducto(){
        return this.parent(Producto.class);
    }
    
    @Override
    public boolean equals(Object o){
        RemitoDetalle d = (RemitoDetalle) o;
        return this.getSerial().equals(d.getSerial());
    }
    
    public boolean equals(RemitoDetalle d){
        return this.get("id_stockdetalle") == d.get("id_stockdetalle");
    }    
    
    public static List findByIds(List ids){
        return RemitoDetalle.where("id_stockdetalle IN ('" + Util.join(ids, "', '") + "')").include(Producto.class);    
    }
    
    public static List findByRemitoIds(List ids){
        return RemitoDetalle.where("id_remito IN ('" + Util.join(ids, "', '") + "')").orderBy("id_remito").include(Producto.class).load();    
    }    
    
    public String getSerial(){
        if (serial == null)
            serial = Utiles.v(this.get("id_producto")) + Utiles.v(this.get("lote")) + Utiles.v(this.get("serie")) + Utiles.v(this.get("vencimiento"));
        return serial;
    }
    
    public static List<RemitoDetalle> filterByProductoField(List<RemitoDetalle> list, String field, Object value){
        return list.stream().filter(rd -> rd.getProducto().get(field).equals(value))
        .collect(Collectors.toList());        
    }
    
    public static List<RemitoDetalle> filterBy(List<RemitoDetalle> list, String field, Object value){
        return list.stream().filter(rd -> rd.get(field).equals(value))
        .collect(Collectors.toList());        
    }
    
    public void addParent(Model model){
        this.setCachedParent(model);
    }
}
