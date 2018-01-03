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
public class EstadoTurno extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column) {
     
        switch (table.getValueAt(row,10).toString()){
            case "Z":
                setForeground(Color.BLUE);
                break;
            case "A":
                setForeground(Color.GREEN);
            case "S":
                setForeground(Color.YELLOW);                
                break;
            case "N":
                setForeground(Color.RED);
                break;
            default:
                setForeground(Color.BLACK);
                break;
        }
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
    }
}
