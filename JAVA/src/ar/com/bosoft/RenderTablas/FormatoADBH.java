// ADBH = AltaBajaDebeHaber

package ar.com.bosoft.RenderTablas;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
public class FormatoADBH extends DefaultTableCellRenderer{
    int columna = 0;
    public FormatoADBH(int columna) {
        this.columna = columna;
    }
    
    @Override
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        if(String.valueOf(table.getValueAt(row,columna)).trim().equals("Alta") 
                || String.valueOf(table.getValueAt(row,columna)).trim().equals("Debe")){ // SI EN CADA FILA DE LA TABLA LA CELDA 3 ES IGUAL A ALTA O DEBE COLOR AZUL
            setForeground(Color.blue);
        } else {                                                    // SI ES BAJA ENTONCES COLOR ROJO
            setForeground(Color.red);
        }  
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
    }
 }