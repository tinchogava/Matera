// PER = PendienteEntregadoRechazado

package ar.com.bosoft.RenderTablas;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
public class FormatoPER extends DefaultTableCellRenderer{
 @Override
 public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column) {
     
     //***** RECORDAR QUE LAS CELDAS SE EMPIEZAN A CONTAR DESDE 0*****//
     switch (String.valueOf(table.getValueAt(row,4)).trim()) {
         case "P":
             //PENDIENTE COLOR AMARILLO
             setBackground(Color.YELLOW);
             break;
         case "E":
             // ENTREGADO COLOR VERDE
             setBackground(Color.GREEN);
             break;
         case "R":
             //RECHAZADO COLOR ROJO
             setBackground(Color.RED);
             break;
     }
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
    }
 }