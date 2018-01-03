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
import matera.TableObjects.ReclamoTableObject;
import matera.jooq.tables.records.ReclamoRecord;
import matera.jooq.tables.records.ReclamoexternoRecord;
import matera.jooq.tables.records.ReclamointernoRecord;
/**
 *
 * @author tinchogava
 */
public class ReclamoTableModel extends RowTableModel<ReclamoTableObject> {

    public ReclamoTableModel() {
        List colNames = new ArrayList();
        colNames.add("reclamo");
        colNames.add("fecha");
        colNames.add("clasificacion");
        colNames.add("tipo");
        colNames.add("Destino");
        colNames.add("turno");
        
        setDataAndColumnNames(new ArrayList<>(), colNames);
        setRowClass(ProductoTableObject.class);
        setModelEditable(false);
    }

    @Override
    public Object getValueAt(int row, int column) {
        try{
            ReclamoTableObject rtd = getRow(row);
            
            ReclamoRecord reclamo = rtd.getReclamo();
            ReclamointernoRecord ri = rtd.getReclamoInterno();
            ReclamoexternoRecord re = rtd.getReclamoExterno();
            
            switch(Utiles.formattedColumn(getColumnName(column)))
            {
                case "reclamo": return reclamo.getIdReclamo();
                case "fecha": return reclamo.getFechareclamo();
                case "clasificacion": return reclamo.getTipoReclamo();
                case "tipo": 
                    if(reclamo.getIdReclamoexterno() != null)
                        return re.getNombre();
                    else
                        return ri.getNombre();
                case "destino": return reclamo.getDestinoreclamo().toUpperCase();
                case "turno": return reclamo.getIdPresupuesto();                 
                default:
                    throw new IndexOutOfBoundsException();     
            }
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
            return null;
        }
    }

}
