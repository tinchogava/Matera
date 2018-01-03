package ar.com.bosoft.RenderTablas;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class FormatoCerosBlancos extends DefaultTableCellRenderer{
    ArrayList columnas = new ArrayList();
    
    public void setColumnas(ArrayList columnas){
        this.columnas = columnas;
    }
    
    @Override
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column) {
     
        Iterator i = this.columnas.iterator();
        
        while (i.hasNext()){
            int columna = (int) i.next();
            if (Double.parseDouble(table.getValueAt(row,columna).toString()) == 0){
                setForeground(Color.WHITE);
            } else {                                                    
                setForeground(Color.BLACK);
            }  
            super.getTableCellRendererComponent(table, value, selected, focused, row, columna);
        }
        
        return this;
    }
}