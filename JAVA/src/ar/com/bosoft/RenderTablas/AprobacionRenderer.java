/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.bosoft.RenderTablas;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author tinchogava
 */
public class AprobacionRenderer extends  DefaultTableCellRenderer{
   private final SimpleDateFormat aprobacion = new SimpleDateFormat("HH:mm dd/MM/yyyy");
   //private final SimpleDateFormat aprobacion = new SimpleDateFormat("dd/MM/yyyy");
   private final Calendar hoy = Calendar.getInstance();
   
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        Calendar fechaAprobacion = Calendar.getInstance();
        Component cell;
        if( value instanceof Date) {
            if (column == 10){
                value = aprobacion.format(value);
                fechaAprobacion.setTime((Date) table.getValueAt(row, 10));
            }
        }
        cell = super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
         if((table.getValueAt(row, 10) != null) && 
                 hoy.get(Calendar.YEAR) == fechaAprobacion.get(Calendar.YEAR) && 
                 hoy.get(Calendar.DAY_OF_YEAR) == fechaAprobacion.get(Calendar.DAY_OF_YEAR)){
            cell.setBackground(Color.red);
            cell.setForeground(Color.white);
        } else {
            cell.setBackground(Color.white);
            cell.setForeground(Color.black);
        } 
        return cell;
    }
}