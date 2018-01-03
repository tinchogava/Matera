/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.Table;
import org.javalite.activejdbc.annotations.IdName;
/**
 *
 * @author h2o
 */
@Table("producto")
@IdName("id_producto")
@BelongsToParents({ 
@BelongsTo(foreignKeyName="id_codconsumo",parent=CodigoConsumo.class),
@BelongsTo(foreignKeyName="id_clasiproducto",parent=ClasiProducto.class),
@BelongsTo(foreignKeyName="id_moneda",parent=Moneda.class),
@BelongsTo(foreignKeyName="id_proveedor",parent=Proveedor.class)
})

public class Producto extends Model {
    private Long stock = null;
    private Long adquirido = null;
    private Long consumido = null;
    private Long ajuste = null;
    
    public Long getStock(){
        if (stock != null) return stock;
        stock = getAdquiridoCount() - getConsumidoCount() + getAjusteStockCount();
        return stock;
    }
    
    public Long getAdquiridoCount(){
        if (adquirido != null) return adquirido;
        adquirido = Base.count("stockdetalle", "id_producto = ? AND id_stock > 0 AND dc = 'D'", this.getId());
        return adquirido;
    }
    
    public Long getConsumidoCount(){
        if (consumido != null) return consumido;
        consumido = Base.count("stockdetalle", "id_producto = ? AND id_remito > 0 AND dc = 'C'", this.getId()) - 
                            Base.count("stockdetalle", "id_producto = ? AND id_remito > 0 AND dc = 'D'", this.getId());
        return consumido;
    }
    
    public Long getAjusteStockCount(){
        if (ajuste != null) return ajuste;
        ajuste = Base.count("stockdetalle", "id_producto = ? AND id_ajustestock > 0 AND dc = 'D'", this.getId()) -
                        Base.count("stockdetalle", "id_producto = ? AND id_ajustestock > 0 AND dc = 'C'", this.getId());
        return ajuste;
    }
    
    public static ArrayList removeProductDuplicates(List<HashMap> list){
        ArrayList newList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()){
            HashMap map = (HashMap) it.next();
            
            Iterator newListIterator = newList.iterator();
            Boolean isUnique = true;
            while (newListIterator.hasNext()){
                HashMap m = (HashMap) newListIterator.next();
                if (map.get("codigo").equals(m.get("codigo")) &&
                        map.get("lote").equals(m.get("lote")) &&
                        map.get("serie").equals(m.get("serie")) &&
                        map.get("vencimiento").equals(m.get("vencimiento"))){
                    isUnique = false;
                    break;
                }
            }
            if (isUnique)
                newList.add(map);
            
        }
        return newList;
    }
    
    public static Integer countProductDuplicates(ArrayList list, HashMap map){
        ArrayList clonedList = (ArrayList) list.clone();
        Iterator it = clonedList.iterator();
        Integer count = 0;
        while (it.hasNext()){
            HashMap m = (HashMap) it.next();
            
                if (map.get("codigo").equals(m.get("codigo")) &&
                        map.get("lote").equals(m.get("lote")) &&
                        map.get("serie").equals(m.get("serie")) &&
                        map.get("vencimiento").equals(m.get("vencimiento"))){
                    count++;
                    it.remove();
                    
                }
        }
        return count;
    }
    
    public static ArrayList filterBy(ArrayList<Map> list, String field, Object value){
        ArrayList clonedList = (ArrayList) list.clone();
        Iterator it = clonedList.iterator();
        while (it.hasNext()){
            HashMap map = (HashMap) it.next();
            Object val = map.get(field.toLowerCase());
            if (!val.equals(value)){
                it.remove();
            }
        }
        return clonedList;        
    }
    
    public static List<HashMap> groupOccurrencesForPrinting(List<HashMap> list){
        
        Set s = new HashSet(Producto.removeProductDuplicates(list));
        Set newSet = new HashSet();
        Iterator itDetalle = s.iterator();
        
        while (itDetalle.hasNext()){
            HashMap map = (HashMap)itDetalle.next();
            Integer occurrences = Producto.countProductDuplicates(new ArrayList(list),map);
            map.put("cantidad", occurrences);
            newSet.add(map);
        }
        return new ArrayList(newSet);
    }    
}
