/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.bosoft.RenderTablas;

/**
 *
 * @author h2o
 */

import ar.com.bosoft.clases.Utiles;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RowColorRenderer extends DefaultTableCellRenderer {
    private final Color HIGHLIGHT_COLOR = Color.green;
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
        
        //if (!isSelected){
            if (table.getValueAt(row, Utiles.getColumnByName(table, "color")) != null ) {
                if(!isSelected)
                    comp.setBackground(HIGHLIGHT_COLOR);
                else
                    comp.setBackground(Color.YELLOW);
            } else if(!isSelected) {
                comp.setBackground(Color.white);
            }
        //}
        
        return comp;
    }
}