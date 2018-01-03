/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.matera.TableModels;

import ar.com.bosoft.clases.Utiles;
import java.util.Map;
import matera.TableObjects.PendienteFacturacionTO;
import matera.jooq.tables.records.PresupuestoControlfacturacionRecord;

/**
 *
 * @author h2o
 */
public class PendienteFacturacionTM extends RowTableModel<PendienteFacturacionTO> {
    @Override
    public Object getValueAt(int row, int column)
    {
        try{
            PendienteFacturacionTO rto = getRow(row);
            
            Map rs = rto.getRs();
            PresupuestoControlfacturacionRecord control = rto.getControl();                  

            switch (Utiles.formattedColumn(getColumnName(column)))
            {
                case "turno": 
                    return rs.get("id_presupuesto");
                case "fecha cx.": 
                    return rs.get("fechacirugia");
                case "entidad": 
                    return rs.get("entidad");
                case "profesional": 
                    return rs.get("profesional");
                case "paciente":
                    return rs.get("paciente");
                case "$ presu.":
                    return rs.get("montopresupuesto");
                case "$ pend.":
                    return rs.get("montofacturado");
                case "lugarcx":
                    return rs.get("lugarcx");
                case "circuito":
                    return control.getCircuito() > 0;
                case "orden":
                    return control.getOrden() > 0;
                case "protocolo":
                    return control.getProtocolo() > 0;
                case "rx":
                    return control.getRx() > 0;
                case "remito":
                    return control.getRemito() > 0;
                case "firma":
                    return control.getFirma() > 0;
                case "sticker":
                    return control.getSticker() > 0;
                case "completo":
                    return control.getCompleto()> 0;    
                    
                default: 
                    throw new IndexOutOfBoundsException();
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int column)
    {
        try{
            
            PendienteFacturacionTO rto = getRow(row);
            PresupuestoControlfacturacionRecord control = rto.getControl();
            
            Integer v = 0;
            if (value.equals(true))
                v = 1;

            switch (Utiles.formattedColumn(getColumnName(column)))
            {
                case "circuito":
                    control.setCircuito(v);
                    break;
                    
                case "orden":
                    control.setOrden(v);
                    break;
                    
                case "protocolo":
                    control.setProtocolo(v);
                    break;
                    
                case "rx":
                    control.setRx(v);
                    break;
                    
                case "remito":
                    control.setRemito(v);
                    break;
                    
                case "firma":
                    control.setFirma(v);
                    break;
                    
                case "sticker":
                    control.setSticker(v);
                    break;
                case "completo":
                    control.setCompleto(v);
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
