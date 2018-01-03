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
 * NO SE USA!!!
 */
public class ConfirmaTurnoRenderer extends DefaultTableCellRenderer {
    private final SimpleDateFormat aprobacion = new SimpleDateFormat("HH:mm dd/MM/yyyy");
    private final SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final Calendar hoy = Calendar.getInstance();
    
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        
        Calendar fechaAgenda = Calendar.getInstance();
        Calendar fechaAprobacion = Calendar.getInstance();
        Component cell;
        
        if( value instanceof Date) {
            if(column == 5){
                value = fechaFormat.format(value);
                fechaAgenda.setTime((Date) table.getValueAt(row, 5));
                System.out.println(fechaAgenda.getTime());      
            }else if (column == 10){
                value = fechaFormat.format(value);                   
                fechaAprobacion.setTime((Date) table.getValueAt(row, 10));
                System.out.println(fechaAprobacion.getTime());
            } 
        }
        //cell = super.getTableCellRendererComponent(table, value, isSelected,
         //       hasFocus, row, column);
         System.out.println(fechaAprobacion.getTime());
         System.out.println(hoy.getTime());
        if(fechaAprobacion.compareTo(hoy) == 0){
            setBackground(Color.red);
            setForeground(Color.black);
        } else {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        if(fechaAgenda.before(hoy)){
            setBackground(Color.yellow);
            setForeground(Color.black);
        } else {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        
        return super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
    }
}
