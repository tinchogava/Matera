package ar.com.bosoft.RenderTablas;

import java.awt.Component;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class NegativosRojos extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,boolean hasFocus, int row, int column){
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(value instanceof Double){
            Double amount = (Double) value;
            if(amount.intValue() < 0){
                cell.setBackground(Color.RED);
                cell.setForeground(Color.WHITE);
            }else if(amount.intValue() == 0){
                cell.setBackground(Color.GREEN);
                cell.setForeground(Color.BLACK);
            }else{    
                cell.setBackground(Color.WHITE);
                cell.setForeground(Color.BLACK);
            }
        }
        return cell;
    }
}