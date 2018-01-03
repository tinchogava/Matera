/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.helpers;

import ar.com.bosoft.DataSources.RemitoDataSource;
import ar.com.bosoft.Utilidades.Salida;
import ar.com.bosoft.clases.Reporte;
import ar.com.bosoft.clases.Utiles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import matera.TableObjects.RemitoDetalleTableObject;
import matera.cache.CacheClasiProducto;

/**
 *
 * @author h2o
 */

public class RemitoHelper {
    
        public static ArrayList removeProductDuplicates(List<RemitoDetalleTableObject> list){
            ArrayList<RemitoDetalleTableObject> newList = new ArrayList<>();
            Iterator it = list.iterator();
            while (it.hasNext()){
                RemitoDetalleTableObject rdo = (RemitoDetalleTableObject) it.next();

                Iterator newListIterator = newList.iterator();
                Boolean isUnique = true;
                while (newListIterator.hasNext()){
                    RemitoDetalleTableObject m = (RemitoDetalleTableObject) newListIterator.next();
                    if(rdo.getProducto().getCodigo().equals(m.getProducto().getCodigo())
                            && rdo.getStockdetalle().getLote().equals(m.getStockdetalle().getLote())
                            && rdo.getStockdetalle().getSerie().equals(m.getStockdetalle().getSerie())){
                        isUnique = false;
                        break;
                    }
                }
                if (isUnique)
                    newList.add(rdo);

            }
            return newList;
        }

            /**
             * Imprime X reportes por caja donde X depende del tipo de productos diferentes que contenga la caja (CLASIPRODUCTO)
             * 
             * @param param
             * @param composicionCajas
             * @param cajas
             * @param salida
             */        
        public void printByCaja(Map param, Map<Integer, List<RemitoDetalleTableObject>> composicionCajas, List<Map> cajas, Salida salida){
        try{
            Integer row = 0;
            for (Map m : cajas){
                param.put("cajas", m.get("codigo") + " (" + m.get("nombre") + ")");
                List<RemitoDetalleTableObject> rdoList = composicionCajas.get(row);
                if (rdoList == null){
                    Utiles.showMessage("la composición de la caja " + m.get("nombre") + " no se ha cargado todavia.");
                    return;
                }
                printByClasiProducto(param, rdoList, salida);
                row++;
            }
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }        
    }
        
    public void printByClasiProducto(Map param, List<RemitoDetalleTableObject> rdoList, Salida salida){
        CacheClasiProducto.instance().getClasiProductos().forEach(clasi ->{
            RemitoDataSource data = new RemitoDataSource(); //1 remito de impresion por cada caja y tipo de producto
            param.put("tipo", clasi.getNombre());

            // FILTER BY CLASIPRODUCTO
            List <RemitoDetalleTableObject> filteredByClasi = rdoList.stream().
                    filter(rdo->{ 
                        return rdo.getClasiProducto().equals(clasi);
                                })
                    .collect(Collectors.toList());

            filteredByClasi = groupProductOccurrences(filteredByClasi);

            sortByCodigo(filteredByClasi);

            data = fillRemitoReport(data, filteredByClasi);

            if (data.listaSize() > 0) {
                printReporte(param, data, salida);
            }                    

        });    
    }
        
    public Map<String, Long> getGroupedRemitoDetalleCounts(List<RemitoDetalleTableObject> list){
        return list.stream().collect(Collectors.groupingBy(e -> 
                e.getProducto().getIdProducto().toString() 
                        + e.getStockdetalle().getLote() 
                        + e.getStockdetalle().getSerie(),
                Collectors.counting()));    
    }
    
    public Map<String, Long> getProductCounts(List<RemitoDetalleTableObject> list){
        return list.stream().collect(Collectors.groupingBy(e -> 
                e.getProducto().getCodigo(),
                Collectors.counting()));    
    }
        
    public List<RemitoDetalleTableObject> groupProductOccurrences(List<RemitoDetalleTableObject> list){
        Map<String, Long> counts = getGroupedRemitoDetalleCounts(list);
        List<RemitoDetalleTableObject> newList = new ArrayList<>();
        
        newList.addAll(list);
        
        newList = removeProductDuplicates(newList);

        newList.forEach(rdo->{
            rdo.setCantidad(counts.get(rdo.getStockdetalle().getIdProducto().toString() 
                    + rdo.getStockdetalle().getLote() 
                    + rdo.getStockdetalle().getSerie())
                    .intValue());
        });
        
        return newList;
    }
        
    public RemitoDataSource fillRemitoReport(RemitoDataSource report, List<RemitoDetalleTableObject> list){
        list.forEach(rdo->{
            report.add(new ar.com.bosoft.Modelos.Remito(
                    rdo.getCantidad(),
                    rdo.getProducto().getCodigo(),
                    rdo.getProducto().getGtin(),
                    rdo.getProducto().getNombre(),
                    rdo.getStockdetalle().getLote(),
                    rdo.getStockdetalle().getSerie(),
                    rdo.getProducto().getPm(),
                    rdo.getStockdetalle().getVencimiento()
            ));
        });
        return report;
    }
    
    public void printReporte(Map param, RemitoDataSource data, Salida salida){
        Reporte reporte = new Reporte();
        String nombreReporte = "Remito";
        reporte.startReport(nombreReporte, param, data, 
                salida.getTipoSalida(), 
                salida.getRutaArchivo(),
                salida.getImpresora(), 
                salida.getCopias());    
    }    
    
    public void sortByCodigo(List list){
        Collections.sort(list, new Comparator<RemitoDetalleTableObject>() {
            @Override
            public int compare(RemitoDetalleTableObject m1, RemitoDetalleTableObject m2) {
                return m1.getProducto().getCodigo().compareTo(m2.getProducto().getCodigo());
            }
        });    
    }      
}
