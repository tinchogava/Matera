/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.matera.TableModels;

import ar.com.bosoft.clases.Utiles;
import java.util.ArrayList;
import java.util.List;
import matera.TableObjects.TrackingOLPTableObject;
import matera.jooq.tables.records.LiquidacionOlpRecord;
import matera.jooq.tables.records.TrackingolpRecord;

/**
 *
 * @author tinchogava
 */
public class TrackingOLPTableModel extends RowTableModel<TrackingOLPTableObject> {

    public TrackingOLPTableModel() {
        List colNames = new ArrayList();
        colNames.add("olp");
        colNames.add("supervisor");
        colNames.add("asesor");
        colNames.add("retorno");
        colNames.add("terranova");
        colNames.add("fin");
        
        setDataAndColumnNames(new ArrayList<>(), colNames);
        setRowClass(TrackingOLPTableObject.class);
        setModelEditable(true);  
    }

    @Override
    public Object getValueAt(int row, int column) {
        
        try{
            TrackingOLPTableObject toto = getRow(row);
            
            TrackingolpRecord tor = toto.getTrackingOLP();
            LiquidacionOlpRecord lor = toto.getLiquidacionolp();
            
            switch(Utiles.formattedColumn(getColumnName(column)))
            {
                case "olp": return lor.getOlp();
                case "supervisor": return tor.step1();
                case "asesor": return tor.step2();  
                case "retorno": return tor.step3();
                case "terranova": return tor.step4();
                case "fin": return tor.step5();
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
    public Class getColumnClass(int col) {
       switch (col) {
        case 0: 
            return String.class;
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
            return Boolean.class;    
        default:
         return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        try{
            TrackingOLPTableObject toto = getRow(row);
            TrackingolpRecord tor = toto.getTrackingOLP();

            Integer v = 0;
            if (value.equals(true))
                v = 1;

            switch(Utiles.formattedColumn(getColumnName(column)))
            {
                case "supervisor": 
                    tor.setStep1(v);
                    break;
                case "asesor": 
                    tor.setStep2(v);
                    break;
                case "retorno": 
                    tor.setStep3(v);
                    break;
                case "terranova": 
                    tor.setStep4(v);
                    break;
                case "fin": 
                    tor.setStep5(v);
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

