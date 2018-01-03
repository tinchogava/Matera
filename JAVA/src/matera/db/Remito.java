/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db;

import ar.com.bosoft.DataSources.ConsumidoDataSource;
import ar.com.bosoft.DataSources.RemitoDataSource;
//import ar.com.bosoft.Modelos.Consumido;
import ar.com.bosoft.Utilidades.DateUtil;
import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.data.ClasiProductoData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import matera.db.Lists.CustomLazyList;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
/**
 *
 * @author h2o
 */
@Table("remito")
@IdName("id_remito")
@BelongsToParents({ 
@BelongsTo(foreignKeyName="id_presupuesto", parent=Presupuesto.class),
@BelongsTo(foreignKeyName="id_entidad", parent=Entidad.class)
})
public class Remito extends Model {
    
    Boolean cachedRemitoDetalle = false;
    Boolean cachedConsumido = false;
    
    public Presupuesto getPresupuesto(){
        return this.parent(Presupuesto.class);
    }
    
    public Entidad getEntidad(){
        return this.parent(Entidad.class);
    }
    
    public List<RemitoDetalle> getEnviado(){
        if (!cachedRemitoDetalle)
            loadRemitoDetalle();
        return getFilteredRemitoDetalle("C");
    }
    
    public List<RemitoDetalle> getRecibido(){
        if (!cachedRemitoDetalle)
            loadRemitoDetalle();        
        return getFilteredRemitoDetalle("D");
    }
    
    private List<RemitoDetalle> getFilteredRemitoDetalle(String dc){
        List<RemitoDetalle> list;
        if (cachedRemitoDetalle){
            List<RemitoDetalle> rd = this.getAll(RemitoDetalle.class);
            list = rd.stream().filter(r -> r.get("dc").equals(dc)).collect(Collectors.toList());        
        }
        else
            list = new CustomLazyList();
        return list;
    }
    
    public LazyList<RemitoDetalle> loadRemitoDetalle(){
        LazyList<RemitoDetalle> rd = null;
        try{
            rd = this.getAll(RemitoDetalle.class).include(Producto.class, Remito.class);
        }
        catch(Exception e){
            rd = this.getAll(RemitoDetalle.class);
        }
        finally{
            this.addChildren(RemitoDetalle.class, (List) rd);
                       
        }
        return rd;
    }
    
    public void addChildren(Class c, List<Model> childrens){
        if (childrens != null && childrens.size() > 0){
            this.setChildren(c, childrens);
            if (c.equals(RemitoDetalle.class))
                cachedRemitoDetalle = true;
            else if(c.equals(Consumido.class))
                cachedConsumido = true;            
        }

    }
    
    public static LazyList<RemitoDetalle> getRemitoDetalle(List<Integer> remitosId){
        return (LazyList) RemitoDetalle.findByRemitoIds(remitosId);
    }
    
    public static LazyList<Consumido> getConsumido(List<Integer> remitosId){
        return (LazyList) Consumido.findByRemitoIds(remitosId);
    }
    
    
    
    public List<Consumido> getConsumido(){
        try{
            if (cachedConsumido){
                return this.getAll(Consumido.class);      
            }
            else
                return new CustomLazyList();            
            
        }
        catch(Exception e){
            // System.out.println(this.get("id_presupuesto") + " " + this.get("id_remito") + " " + getConsumidoFromRemitoDetalle(true).size());
            return new CustomLazyList();
        }
        
    }
    
    /*
    public void saveConsumido(){
        deleteConsumido();
        List<RemitoDetalle> consumido = getConsumidoFromRemitoDetalle(true);
        consumido.stream().forEach((remitoDetalle) -> {
            addConsumido(remitoDetalle);
        });
    }
    
    public void addConsumido(RemitoDetalle remitoDetalle){
        Consumido c = new Consumido();
        c.set("id_stockdetalle", remitoDetalle.get("id_stockdetalle"));
        c.set("id_remito", remitoDetalle.get("id_remito"));
        c.insert();        
    }
    */
    
    
    
    public void deleteRecibido(){
        RemitoDetalle.delete("id_remito = ? AND dc = ?", this.get("id_remito"), "D");
    }
    
    public void deleteConsumido(){
        Consumido.delete("id_remito = ?", this.get("id_remito"));
    }
    
    public void deleteRecibidoAndConsumido(){
        deleteRecibido();
        deleteConsumido();
    }
    
    public void resetCache(){        
        cachedConsumido = false;
        cachedRemitoDetalle = false;
        this.setChildren(RemitoDetalle.class, null);
        this.setChildren(Consumido.class, null);
    }
    
    public List<RemitoDetalle> getConsumidoFromRemitoDetalle(Boolean resetCache){
        if (resetCache)
            resetCache();
        // WORKAROUND
        if (!this.get("recibido").equals("S")){
            LazyList<RemitoDetalle> emptyList = this.getAll(RemitoDetalle.class);
            emptyList.clear();
            return emptyList;
        }    
        
        List<RemitoDetalle> enviado = this.getEnviado();
        List<RemitoDetalle> recibido = this.getRecibido();
        List<RemitoDetalle> consumido = new CustomLazyList();
        consumido.addAll(enviado);
        Iterator<RemitoDetalle> it = recibido.iterator();
        
        while (it.hasNext()){
            consumido.remove(it.next());
        }
        return consumido;
    }
    
    public static RemitoDataSource fillRemitoReport(RemitoDataSource report, List list){        
        for (Object o : list){
            if (o instanceof HashMap){
                HashMap extraMap = (HashMap)o;
                int cantidad = Integer.parseInt(Utiles.v(extraMap.get("cantidad")));
                String codigo = Utiles.v(extraMap.get("codigo"));
                String gtin = Utiles.v(extraMap.get("gtin"));
                String nombre = Utiles.v(extraMap.get("nombre"));
                if (nombre.length() > 25)
                    nombre = nombre.substring(0, 25);
                String lote = Utiles.v(extraMap.get("lote"));
                String serie = Utiles.v(extraMap.get("serie"));
                String pm = Utiles.v(extraMap.get("pm"));
                Date venc = DateUtil.stringToDate(Utiles.v(extraMap.get("vencimiento")));
                ar.com.bosoft.Modelos.Remito nuevo = new ar.com.bosoft.Modelos.Remito(cantidad, codigo, gtin, nombre, lote, serie, pm, venc);
                report.add(nuevo);
            }               
        }
        return report;
    }
    
    public static void fillRemitoDetalleReport(RemitoDataSource data, List<RemitoDetalle> list){
        Map<String,List<RemitoDetalle>> groupedConsumido = list.stream()
              .collect(Collectors.groupingBy(r -> r.getSerial(), LinkedHashMap::new, toList()));

        for (String key : groupedConsumido.keySet()){
            RemitoDetalle rd = groupedConsumido.get(key).get(0);
            rd.set("cantidad",groupedConsumido.get(key).size());
            
            // 25 CARACTERES PARA MEJOR LECTURA DE LOS REPORTES (1 SOLA LINEA) , QUIZAS SEA MEJOR HACERLO EN EL REPORTE MISMO?
            String nombre =  rd.getProducto().getString("nombre");
            if (nombre.length() > 25)
                    nombre = nombre.substring(0, 25);
            
            ar.com.bosoft.Modelos.Remito nuevo = new ar.com.bosoft.Modelos.Remito(
                    rd.getInteger("cantidad"),
                    rd.getProducto().getString("codigo"),
                    rd.getProducto().getString("gtin"),
                    nombre,
                    rd.getString("lote"),
                    rd.getString("serie"),
                    rd.getString("pm"),
                    rd.getDate("vencimiento")); 

            data.add(nuevo);                
        }
    }
    
    /**
     * Imprime los remitos por caja, filtrando el array de productos por caja, despues por tipo de producto y al final agrupando los productos iguales para mostrar la cantidad de cada uno.
     * @param cajaMap HashMap en formato (id_cajaDepostito, codigo)
     * @param param Map con los parametros del remito
     * @param arrayComponentes lista de productos
     * @param arrayClasi lista de tipos de producto
     * @param salida Salida que tiene los datos de ruta a archivo, tipo de salida, etc
     */
    public static void printRemitoByCaja(HashMap cajaMap, Map param, ArrayList arrayComponentes, ArrayList arrayClasi, Salida salida){
        for ( Object o : cajaMap.keySet()){

            Integer idCaja = (Integer) o;
            Caja caja = CajaDeposito.findById(idCaja).parent(Caja.class);
            param.put("cajas", cajaMap.get(idCaja) + " (" + caja.getString("nombre") + ")");
            ArrayList filteredByCaja = Producto.filterBy(arrayComponentes, "id_cajadeposito", idCaja);

            Iterator itClasiProducto = arrayClasi.iterator();
            while (itClasiProducto.hasNext()){

                RemitoDataSource data = new RemitoDataSource(); //1 remito de impresion por cada caja y tipo de producto

                ClasiProductoData clasiData = (ClasiProductoData) itClasiProducto.next(); 
                String nombreClasi = clasiData.getNombre();
                param.put("tipo", nombreClasi);

                List filteredByTipo = Producto.filterBy(filteredByCaja, "tipo", param.get("tipo"));
                filteredByTipo = Producto.groupOccurrencesForPrinting(filteredByTipo);
                
                sortByCodigo(filteredByTipo);

                data = Remito.fillRemitoReport(data, filteredByTipo);

                if (data.listaSize() > 0) {
                    printReporte(param, data, salida);
                }                    
            }   
        }    
    }
    
    public static void printReporte(Map param, RemitoDataSource data, Salida salida){
        Reporte reporte = new Reporte();
        String nombreReporte = "Remito";
        reporte.startReport(nombreReporte, param, data, 
                salida.getTipoSalida(), 
                salida.getRutaArchivo(),
                salida.getImpresora(), 
                salida.getCopias());    
    }
    
    public static void sortByCodigo(List list){
        Collections.sort(list, new Comparator<HashMap>() {
            @Override
            public int compare(HashMap m1, HashMap m2) {
                return Utiles.v(m1.get("codigo")).compareTo(Utiles.v(m2.get("codigo")));
            }
        });    
    }
    
    public static void fillConsumidoReport(ConsumidoDataSource data, List<RemitoDetalle> consumido){
        Map<String,List<RemitoDetalle>> groupedConsumido = consumido.stream()
              .collect(Collectors.groupingBy(r -> r.getSerial(), LinkedHashMap::new, toList()));

        for (String key : groupedConsumido.keySet()){
            RemitoDetalle rd = groupedConsumido.get(key).get(0);
            rd.set("cantidad",groupedConsumido.get(key).size());
            
            // 25 CARACTERES PARA MEJOR LECTURA DE LOS REPORTES (1 SOLA LINEA) , QUIZAS SEA MEJOR HACERLO EN EL REPORTE MISMO?
            String nombre =  rd.getProducto().getString("nombre");
            if (nombre.length() > 25)
                    nombre = nombre.substring(0, 25);
            
            ar.com.bosoft.Modelos.Consumido c = new ar.com.bosoft.Modelos.Consumido(
                    (Integer)rd.get("cantidad"), 
                    (Integer)rd.get("cantidad"), 
                    rd.getProducto().getString("codigo"), 
                    rd.getProducto().getString("gtin"), 
                    nombre,
                    rd.getString("lote"), 
                    rd.getString("serie"),
                    rd.getString("pm")); 

            data.add(c);                
        }
    }
}
