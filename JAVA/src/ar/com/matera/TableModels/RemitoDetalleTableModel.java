/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.matera.TableModels;

import ar.com.bosoft.Utilidades.DateUtil;
import ar.com.bosoft.clases.Utiles;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import matera.TableObjects.RemitoDetalleTableObject;
import matera.jooq.tables.records.ClasiproductoRecord;
import matera.jooq.tables.records.ProductoRecord;
import matera.jooq.tables.records.StockdetalleRecord;

/**
 *
 * @author h2o
 */
public class RemitoDetalleTableModel extends RowTableModel<RemitoDetalleTableObject> {

    public RemitoDetalleTableModel() {

        List colNames = new ArrayList();
        colNames.add("codigo");
        colNames.add("GTIN");
        colNames.add("nombre");
        colNames.add("cantidad");
        colNames.add("PM");
        colNames.add("lote");
        colNames.add("serie");
        colNames.add("vencimiento");
        colNames.add("tipo");
        
        setDataAndColumnNames(new ArrayList<>(), colNames);
        setRowClass(RemitoDetalleTableObject.class);
        
    }

    @Override
    public Object getValueAt(int row, int column)
    {
        try{
            RemitoDetalleTableObject rd = getRow(row);

            ProductoRecord producto = rd.getProducto();
            ClasiproductoRecord clasiProducto = rd.getClasiProducto();
            StockdetalleRecord stockdetalle = rd.getStockdetalle();

            switch (Utiles.formattedColumn(getColumnName(column)))
            {
                case "codigo": return producto.getCodigo();
                case "gtin": return producto.getGtin();
                case "nombre": return producto.getNombre();
                case "cantidad": return stockdetalle.getCantidad();
                case "pm": return producto.getPm();
                case "lote": return stockdetalle.getLote();
                case "serie": return stockdetalle.getSerie();
                case "vencimiento": return stockdetalle.getVencimiento();
                case "tipo": return clasiProducto.getNombre();
                default: 
                    throw new IndexOutOfBoundsException();
            }
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
            return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int column)
    {
        try{
            RemitoDetalleTableObject rd = getRow(row );
            StockdetalleRecord stockdetalle = rd.getStockdetalle();

            switch (Utiles.formattedColumn(getColumnName(column)))
            {
                case "lote": stockdetalle.setLote((String) value); break;
                case "serie": stockdetalle.setSerie((String) value); break;
                case "vencimiento":
                    Date d = DateUtil.stringToDate(value);
                    if (d != null)
                        stockdetalle.setVencimiento(new java.sql.Date(d.getTime()));
                    break;
                default: 
                    throw new IndexOutOfBoundsException();
            }
            fireTableCellUpdated(row, column);
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        RemitoDetalleTableObject rd = getRow(row );
        if (super.isCellEditable(row, column)){
            Integer trazabilidad = rd.getProducto().getTrazabilidad();
            switch (Utiles.formattedColumn(getColumnName(column)))
            {
                case "lote":
                    return trazabilidad > Utiles.TRAZABILIDAD.SIN_TRAZABILIDAD || false;
                    
                case "serie":
                    return trazabilidad > Utiles.TRAZABILIDAD.LOTE || false;
                    
                default:
                    return true;
            }
        }
        return false;

    }    
}
