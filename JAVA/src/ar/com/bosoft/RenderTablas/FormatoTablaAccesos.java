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
public class FormatoTablaAccesos extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column) {
     
        //***** RECORDAR QUE LAS CELDAS SE EMPIEZAN A CONTAR DESDE 0*****// 
     
        if (table.getValueAt(row,1).toString().equals("SI")){ // SI EN CADA FILA DE LA TABLA LA CELDA 2 ES IGUAL A SI
            setForeground(Color.BLUE);
        } else {                                                    // SI ES NO ENTONCES COLOR ROJO
            setForeground(Color.red);
        }  
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
    }
}