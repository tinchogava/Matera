/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.matera.TableModels;

import ar.com.bosoft.clases.Utiles;
import java.util.ArrayList;
import java.util.List;
import matera.TableObjects.CargaProductoTableObject;
import matera.jooq.tables.records.ProductoRecord;

/**
 *
 * @author h2o
 */
public class CargaProductoTableModel extends RowTableModel<CargaProductoTableObject> {

    public CargaProductoTableModel() {

        List colNames = new ArrayList();
        colNames.add("id_producto");
        colNames.add("codigo");
        colNames.add("GTIN");
        colNames.add("nombre");
        colNames.add("pm");
        colNames.add("cantidad");
        
        setDataAndColumnNames(new ArrayList<>(), colNames);
        setRowClass(CargaProductoTableObject.class);
        
    }

    @Override
    public Object getValueAt(int row, int column)
    {
        try{
            CargaProductoTableObject rd = getRow(row);

            ProductoRecord producto = rd.getProducto();

            switch (getColumnName(column))
            {
                case "id_producto": return producto.getIdProducto();
                case "codigo": return producto.getCodigo();
                case "GTIN": return producto.getGtin();
                case "nombre": return producto.getNombre();
                case "pm": return producto.getPm();
                case "cantidad": return rd.getCantidad();
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
            CargaProductoTableObject rd = getRow(row);

            switch (Utiles.formattedColumn(getColumnName(column)))
            {
                case "cantidad": 
                    rd.setCantidad(Integer.parseInt(value.toString()));
                    break;
                default: 
                    throw new IndexOutOfBoundsException();
            }
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }
    }
}
