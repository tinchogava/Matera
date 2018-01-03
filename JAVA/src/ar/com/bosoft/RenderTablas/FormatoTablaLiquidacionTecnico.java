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
public class FormatoTablaLiquidacionTecnico extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column) {
     
        //***** RECORDAR QUE LAS CELDAS SE EMPIEZAN A CONTAR DESDE 0*****// 
     
        if (Integer.parseInt(table.getValueAt(row,8).toString()) == 0){ // SI EN CADA FILA DE LA TABLA LA CELDA 2 ES IGUAL A true
            setBackground(Color.YELLOW);
        } else {                                                    // SI ES NO ENTONCES COLOR ROJO
            setBackground(Color.GREEN);
        }  
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
    }
}