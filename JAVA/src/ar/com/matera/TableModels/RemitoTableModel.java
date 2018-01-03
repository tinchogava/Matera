/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.matera.TableModels;

import ar.com.bosoft.clases.Utiles;
import java.util.ArrayList;
import java.util.List;
import matera.TableObjects.RemitoTableObject;
import matera.jooq.tables.records.EntidadRecord;
import matera.jooq.tables.records.LogEventoRemitoRecord;
import matera.jooq.tables.records.PresupuestoRecord;
import matera.jooq.tables.records.RemitoRecord;
import matera.jooq.tables.records.ZonaRecord;


/**
 *
 * @author h2o
 */
public class RemitoTableModel extends RowTableModel<RemitoTableObject> {

    public RemitoTableModel() {
     List colNames = new ArrayList();
        
        colNames.add("turno");
        colNames.add("fecha");
        colNames.add("numero");
        colNames.add("origen");
        colNames.add("destino");
        colNames.add("entidad");
        colNames.add("remito");
        colNames.add("recibido");

        setDataAndColumnNames(new ArrayList<>(), colNames);
        setRowClass(RemitoTableObject.class);
        setModelEditable(false);
    }
    
    @Override
    public Object getValueAt(int row, int column)
    {
        try{
            RemitoTableObject rto = getRow(row);
            
            RemitoRecord r = rto.getRemito();
            PresupuestoRecord p = rto.getPresupuesto();
            ZonaRecord z = rto.getZona();
            EntidadRecord e = rto.getEntidad();
            LogEventoRemitoRecord log = rto.getEventoRemito();
            

            switch (Utiles.formattedColumn(getColumnName(column)))
            {
                case "turno": return r.getIdPresupuesto();
                case "fecha": return r.getFecha();
                case "numero": return r.getNumcomp();
                case "origen": return z.getNombre();
                case "destino": return rto.getDestino();
                case "entidad": return e.getNombre();
                case "remito":  return r.getIdRemito();
                case "recibido": 
                    if(log.getIdLogEventoTipo() == 201)
                        return "Si";
                    else
                        return "No";
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
            // NOT IMPEMENTED
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }
    }
}
