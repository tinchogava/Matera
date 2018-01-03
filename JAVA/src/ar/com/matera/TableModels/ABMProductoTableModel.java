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
import matera.jooq.tables.records.ClasiproductoRecord;
import matera.jooq.tables.records.MonedaRecord;
import matera.jooq.tables.records.ProductoRecord;

/**
 *
 * @author h2o
 */
public class ABMProductoTableModel extends RowTableModel<ProductoTableObject> {

    public ABMProductoTableModel() {

        List colNames = new ArrayList();
        colNames.add("codigo");
        colNames.add("gtin");
        colNames.add("nombre");
        colNames.add("costo");
        colNames.add("moneda");
        colNames.add("clasificacion");
        colNames.add("pm");
        colNames.add("trazabilidad");
        colNames.add("habilitado");
        
        setDataAndColumnNames(new ArrayList<>(), colNames);
        setRowClass(ProductoTableObject.class);
        setModelEditable(false);
        
    }

    @Override
    public Object getValueAt(int row, int column)
    {
        try{
            ProductoTableObject rd = getRow(row);

            ProductoRecord p = rd.getProducto();
            ClasiproductoRecord c = rd.getClasiProducto();
            MonedaRecord m = rd.getMoneda();

            switch (Utiles.formattedColumn(getColumnName(column)))
            {
                case "codigo": return p.getCodigo();
                case "gtin": return p.getGtin();
                case "nombre": return p.getNombre();
                case "costo": return p.getCosto();
                case "moneda": return m.getNombre();
                case "clasificacion": return c.getNombre();
                case "pm": return p.getPm();
                case "trazabilidad": return getTrazabilidadString(p.getTrazabilidad());
                case "habilitado": return p.getHabilita();
                default: 
                    throw new IndexOutOfBoundsException();
            }
        }
        catch(Exception e){
            //Utiles.showErrorMessage(e);
            return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int column)
    {
        // NOT IMPLEMENTED
    }
    
    public ProductoTableObject findProductoByCodigo (String codigo){
        try{
            return this.getRowsAsList().stream().filter(p->p.getProducto().getCodigo().equals(codigo)).findFirst().get();
        }
        catch(Exception e){
        }
        return null;
    }
    
    public String getTrazabilidadString(Integer trazabilidad){
        switch(trazabilidad){
            case Utiles.TRAZABILIDAD.SIN_TRAZABILIDAD: return "sin trazabilidad";
            case Utiles.TRAZABILIDAD.LOTE: return "Lote";
            case Utiles.TRAZABILIDAD.LOTE_SERIE: return "Lote + Serie";
        }
        return "";
    }
}
