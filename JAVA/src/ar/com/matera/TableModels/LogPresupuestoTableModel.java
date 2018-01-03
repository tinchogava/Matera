/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.matera.TableModels;

import ar.com.bosoft.clases.Utiles;
import matera.db.dto.EventoPresupuestoDTO;


/**
 *
 * @author h2o
 */
public class LogPresupuestoTableModel extends RowTableModel<EventoPresupuestoDTO> {

    @Override
    public Object getValueAt(int row, int column)
    {
        try{
            EventoPresupuestoDTO e = getRow(row);

            switch (Utiles.formattedColumn(getColumnName(column)))
            {
                case "evento": return e.getTipo().getNombre();
                case "usuario": return e.getUsuario().getNombre();
                case "timestamp": return e.getEvento().getTimestamp();
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
