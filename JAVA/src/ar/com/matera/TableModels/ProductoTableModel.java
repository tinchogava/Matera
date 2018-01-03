/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.matera.TableModels;

import ar.com.bosoft.clases.Utiles;
import java.util.ArrayList;
import java.util.List;
import matera.TableObjects.ProductoTableObject;
import matera.jooq.tables.records.ProductoRecord;

/**
 *
 * @author h2o
 */
public class ProductoTableModel extends RowTableModel<ProductoTableObject> {

    public ProductoTableModel() {

        List colNames = new ArrayList();
        colNames.add("codigo");
        colNames.add("nombre");     
        
        setDataAndColumnNames(new ArrayList<>(), colNames);
        setRowClass(ProductoTableObject.class);
        setModelEditable(false);
        
    }

    @Override
    public Object getValueAt(int row, int column)
    {
        try{
            ProductoTableObject rd = getRow(row);

            ProductoRecord producto = rd.getProducto();

            switch (Utiles.formattedColumn(getColumnName(column)))
            {
                case "codigo": return producto.getCodigo();
                case "nombre": return producto.getNombre();
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
        // NOT IMPLEMENTED
    }
}

