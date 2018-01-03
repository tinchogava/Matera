/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.matera.TableModels;

import ar.com.bosoft.clases.Utiles;
import java.util.ArrayList;
import java.util.List;
import matera.TableObjects.EmailTableObject;
import matera.jooq.tables.records.EmailreciptersRecord;
import matera.jooq.tables.records.UsuarioRecord;
import matera.jooq.tables.records.ZonaRecord;

/**
 *
 * @author tinchogava
 */
public class ABMEmailTableModel extends RowTableModel<EmailTableObject> {
    public ABMEmailTableModel() {
        List colNames = new ArrayList();
        colNames.add("usuario");
        colNames.add("sucursal");
        colNames.add("email");
        colNames.add("habilitado");
        
        setDataAndColumnNames(new ArrayList<>(), colNames);
        setRowClass(EmailTableObject.class);
        setModelEditable(true);
    }

    @Override
    public Object getValueAt(int row, int column) {
        try{
            EmailTableObject eto = getRow(row);
            
            EmailreciptersRecord err = eto.getEmailrecipters();
            UsuarioRecord ur = eto.getUsuario();
            ZonaRecord zr = eto.getZona();
           
            switch(Utiles.formattedColumn(getColumnName(column)))
            {
                case "usuario": return ur.getNombre();
                case "sucursal": return zr.getIdZona() == null ? "TODAS" : zr.getNombre();
                case "email": return err.getEmailaddress();  
                case "habilitado": return err.getEnabledBool();
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
    public void setValueAt(Object value, int row, int column) {
        try{
            EmailTableObject eto = getRow(row);
            EmailreciptersRecord err = eto.getEmailrecipters();
            String email = err.getEmailaddress();
            
            if (value.equals(err.getEmailaddress())){
                email = (String) value;
            }
                switch(Utiles.formattedColumn(getColumnName(column)))
                {
                    case "email": 
                        err.setEmailaddress(email);
                        break;
                    default:
                        throw new IndexOutOfBoundsException();     
                }
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        } 
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        switch(column){
            case 2:
                return true;
            default:
                return false;
        }
    }
    
    
    
}
