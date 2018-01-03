/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.bosoft.RenderTablas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.commons.lang.math.NumberUtils;

/**
 *
 * @author h2o
 */
//Custom DefaultTableCellRenderer
public class ColumnColorRenderer extends DefaultTableCellRenderer {

    String columnName;
    public ColumnColorRenderer(String column){
        columnName = column;
    }
    // You should override getTableCellRendererComponent
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Check the column name, if it is "version"
        if (table.getColumnName(column).compareToIgnoreCase(columnName) == 0) {
            // You know version column includes string
            Double val = NumberUtils.toDouble(value.toString().replace(",", ".")) / 100.0;
            
            if (val > 1.0) val = 1.0;
            
            double H = val * 0.4; // Hue (note 0.4 = Green, see huge chart below)
            double S = 1; // Saturation
            double B = 1; // Brightness


            Color color = Color.getHSBColor((float)H, (float)S, (float)B);
            
            c.setForeground(color);
            c.setBackground(Color.BLACK);
            c.setFont(new Font("Dialog", Font.BOLD, 12));
            setText(value.toString() + "%");
            setHorizontalAlignment(SwingConstants.CENTER);

        } else {
            // Here you should also stay at default
            //stay at default
            c.setForeground(Color.BLACK);
            c.setBackground(Color.WHITE);
            c.setFont(new Font("Dialog", Font.PLAIN, 12));
            if (NumberUtils.isNumber(value.toString()))
                setHorizontalAlignment(SwingConstants.CENTER);
            else
                setHorizontalAlignment(SwingConstants.LEFT);
        }
        return c;
    }
}
