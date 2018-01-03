/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.matera.TableModels;

import ar.com.bosoft.Modelos.CostoVentas;
import ar.com.bosoft.clases.Utiles;
import matera.services.RmService;

/**
 *
 * @author h2o
 */
public class CostoVentasTM extends RowTableModel<CostoVentas> {
    @Override
    public Object getValueAt(int row, int column)
    {
        try{
            CostoVentas e = getRow(row);

            switch (Utiles.formattedColumn(getColumnName(column)))
            {
                case "turno": return e.getId_presupuesto();
                case "cantidad": return e.getCantidad();
                case "producto": return e.getProducto();
                case "costo": return e.getCostoPesos();
                case "tipo op.": return e.getCategoria();
                case "cotizacion": return e.getCotizacion();
                default: return null;
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
