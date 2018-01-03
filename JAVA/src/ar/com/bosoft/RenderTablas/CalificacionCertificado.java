/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.RenderTablas;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class CalificacionCertificado extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected,boolean focused, int row, int column){
        Component cell = super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        if(value instanceof Integer){
            Integer amount = (Integer) value;
            if(amount == 0){
                cell.setBackground(Color.RED);
                cell.setForeground(Color.RED);
            }else if (amount == 1){
                cell.setBackground(Color.YELLOW);
                cell.setForeground(Color.YELLOW);
            }else if (amount == 2){
                cell.setBackground(Color.GREEN);
                cell.setForeground(Color.GREEN);
            }else if (amount == 3){
                cell.setBackground(Color.BLUE);
                cell.setForeground(Color.BLUE);
            }else{
                cell.setBackground(Color.WHITE);
                cell.setForeground(Color.WHITE);
            }
        }
        return cell;
        
//        //Alternativas
//        if (table.getValueAt(row,0).toString().equals("MALO")){
//            setBackground(Color.RED);
//            setForeground(Color.RED);
//        }
//        
//        if (table.getValueAt(row,0).toString().equals("REGULAR")){
//            setBackground(Color.YELLOW);
//            setForeground(Color.YELLOW);
//        }
//        
//        if (table.getValueAt(row,0).toString().equals("BUENO")){
//            setBackground(Color.GREEN);
//            setForeground(Color.GREEN);
//        }
//        
//        if (table.getValueAt(row,0).toString().equals("EXCELENTE")){
//            setBackground(Color.BLUE);
//            setForeground(Color.BLUE);
//        }
//        
//        //Instrumental
//        if (table.getValueAt(row,1).toString().equals("MALO")){
//            setBackground(Color.RED);
//            setForeground(Color.RED);
//        }
//        
//        if (table.getValueAt(row,1).toString().equals("REGULAR")){
//            setBackground(Color.YELLOW);
//            setForeground(Color.YELLOW);
//        }
//        
//        if (table.getValueAt(row,1).toString().equals("BUENO")){
//            setBackground(Color.GREEN);
//            setForeground(Color.GREEN);
//        }
//        
//        if (table.getValueAt(row,1).toString().equals("EXCELENTE")){
//            setBackground(Color.BLUE);
//            setForeground(Color.BLUE);
//        }
//        
//        //Asistencia
//        if (table.getValueAt(row,2).toString().equals("MALO")){
//            setBackground(Color.RED);
//            setForeground(Color.RED);
//        }
//        
//        if (table.getValueAt(row,2).toString().equals("REGULAR")){
//            setBackground(Color.YELLOW);
//            setForeground(Color.YELLOW);
//        }
//        
//        if (table.getValueAt(row,2).toString().equals("BUENO")){
//            setBackground(Color.GREEN);
//            setForeground(Color.GREEN);
//        }
//        
//        if (table.getValueAt(row,2).toString().equals("EXCELENTE")){
//            setBackground(Color.BLUE);
//            setForeground(Color.BLUE);
//        }
//        
//        //Puntualidad
//        if (table.getValueAt(row,3).toString().equals("MALO")){
//            setBackground(Color.RED);
//            setForeground(Color.RED);
//        }
//        
//        if (table.getValueAt(row,3).toString().equals("REGULAR")){
//            setBackground(Color.YELLOW);
//            setForeground(Color.YELLOW);
//        }
//        
//        if (table.getValueAt(row,3).toString().equals("BUENO")){
//            setBackground(Color.GREEN);
//            setForeground(Color.GREEN);
//        }
//        
//        if (table.getValueAt(row,3).toString().equals("EXCELENTE")){
//            setBackground(Color.BLUE);
//            setForeground(Color.BLUE);
//        }
//        
//        
//        
//        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
//        return this;
    }
}
