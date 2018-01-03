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
public class AgendaRenderer extends  DefaultTableCellRenderer{
   private final SimpleDateFormat agenda = new SimpleDateFormat("dd/MM/yyyy");
   private final Calendar hoy = Calendar.getInstance();
   
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus,
            int row, int column) { 
        
        Calendar fechaAgenda = Calendar.getInstance();
        Component cell;
        
        if( value instanceof Date) {
        if(column == 5){
            value = agenda.format(value);
            fechaAgenda.setTime((Date) table.getValueAt(row, 5));
            }
        }
            cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);          
        if(fechaAgenda.before(hoy)){
            cell.setBackground(Color.yellow);
            cell.setForeground(Color.black);
        } else {
            cell.setBackground(Color.white);
            cell.setForeground(Color.black);
        }
        
        return cell;
        } 
}