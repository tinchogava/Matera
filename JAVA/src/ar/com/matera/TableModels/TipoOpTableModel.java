/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.matera.TableModels;

import ar.com.bosoft.clases.Utiles;
import java.util.ArrayList;
import java.util.List;
import matera.jooq.tables.records.TipooperacionRecord;
/**
 *
 * @author tinchogava
 */
public class TipoOpTableModel extends RowTableModel<TipooperacionRecord> {
    
    public TipoOpTableModel() {

        List colNames = new ArrayList();
        colNames.add("Código");
        colNames.add("Nombre");
        colNames.add("Habilitado");
        
        setDataAndColumnNames(new ArrayList<>(), colNames);
        setRowClass(TipooperacionRecord.class);
        
    }

    @Override
    public Object getValueAt(int row, int column) {
      try{
            TipooperacionRecord tipoOpRecord = getRow(row);
            String a = Utiles.formattedColumn(getColumnName(column));
            switch (Utiles.formattedColumn(getColumnName(column)))
            {
                case "codigo": return tipoOpRecord.getIdTipooperacion();
                case "nombre": return tipoOpRecord.getNombre();
                case "habilitado": return tipoOpRecord.getHabilita();
                default: 
                    throw new IndexOutOfBoundsException();
            }
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
            return null;
        }}
    
    @Override
    public void setValueAt(Object value, int row, int column)
    {
       try{
            TipooperacionRecord tipoOpRecord = getRow(row);

            switch (Utiles.formattedColumn(getColumnName(column)))
            {
                case "nombre": tipoOpRecord.setNombre(value.toString());
                break;
                case "habilitado": tipoOpRecord.setHabilita(value.toString());
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
