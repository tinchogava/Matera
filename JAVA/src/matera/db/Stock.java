/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;

/**
 *
 * @author h2o
 */
public class Stock  extends Model {
    HashMap<Integer,Integer> adquirido;
    HashMap<Integer,Integer> consumido;
    HashMap<Integer,Integer> ajuste;
    HashMap<Integer,Integer> stock;
    
    public Stock(){
        adquirido = new HashMap<>();
        consumido = new HashMap<>();
        ajuste = new HashMap<>();
        stock = new HashMap<>();
        
        getConsumido();
        getAdquirido();
        getAjusteStock();
        getStock();
    }
    
    public void clearAll(){
        adquirido.clear();
        consumido.clear();
        ajuste.clear();
        stock.clear();
    }
    
    public HashMap getStock(){
        if (!stock.isEmpty())
            return stock;
        
        List<Map> productos = Base.findAll("select id_producto, 0 AS cant from producto");
        stock = mapMe(productos);
        for (Integer key : stock.keySet()) {
            Integer compras = adquirido.get(key) != null ? adquirido.get(key) : 0;
            Integer ventas =  consumido.get(key) != null ? consumido.get(key) : 0;
            Integer ajusteStock = ajuste.get(key) != null ? ajuste.get(key) : 0;
            
            stock.put(key, compras - ventas + ajusteStock);
        }
        return stock;
    }
    
    public Integer getStock(Integer id_producto){
        return stock.get(id_producto) != null ? stock.get(id_producto) : 0;
    }    
    
    public HashMap getConsumido(){
        if (!consumido.isEmpty())
            return consumido;
        
        List<Map> enviado = Base.findAll("select id_producto, count(*) AS cant from stockdetalle where id_remito > 0 AND dc = 'C' group by id_producto");
        List<Map> recibido = Base.findAll("select id_producto, count(*) AS cant from stockdetalle where id_remito > 0 AND dc = 'D' group by id_producto");        
        
        consumido = mapMe(enviado);
        HashMap<Integer,Integer> recibidoMap = mapMe(recibido);
        
        for (Integer key : consumido.keySet()) {
            if (recibidoMap.get(key) != null)
                consumido.put(key, consumido.get(key) - recibidoMap.get(key));
        }
        
        return consumido;
    }
    
    public Integer getConsumido(Integer id_producto){
        return consumido.get(id_producto) != null ? consumido.get(id_producto) : 0;
    }    
    
    public HashMap getAdquirido(){
        if (!adquirido.isEmpty())
            return adquirido;        
        
        List<Map> l = Base.findAll("select id_producto, count(*) AS cant from stockdetalle where id_stock > 0 AND dc = 'D' group by id_producto");        
        
        adquirido = mapMe(l);
        
        return adquirido;
    }
    
    public Integer getAdquirido(Integer id_producto){
        return adquirido.get(id_producto) != null ? adquirido.get(id_producto) : 0;
    }    
    
    public HashMap getAjusteStock(){
        if (!ajuste.isEmpty())
            return ajuste;        
        List<Map> alta = Base.findAll("select id_producto, count(*) AS cant from stockdetalle where id_ajustestock > 0 AND dc = 'D' group by id_producto");
        List<Map> baja = Base.findAll("select id_producto, count(*) AS cant from stockdetalle where id_ajustestock > 0 AND dc = 'C' group by id_producto");        
        
        ajuste = mapMe(alta);
        HashMap<Integer,Integer> bajaAjuste = mapMe(baja);
        
        for (Integer key : ajuste.keySet()) {
            if (bajaAjuste.get(key) != null)
                ajuste.put(key, ajuste.get(key) - bajaAjuste.get(key));
        }
        
        return ajuste;
    }
    
    public Integer getAjusteStock(Integer id_producto){
        return ajuste.get(id_producto) != null ? ajuste.get(id_producto) : 0;
    }    
    
    public static <T> HashMap<Integer,Integer> mapMe(Collection<Map> list) {
       HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
       for (Map el : list) {
           map.put(Integer.parseInt(el.get("id_producto").toString()), Integer.parseInt(el.get("cant").toString()));
       }   
       return map;
    }     
}
