/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.gui.renderers;

import ar.com.bosoft.clases.Utiles;
import ar.com.matera.TableModels.RemitoDetalleTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author h2o
 */

public class TrazabilidadRenderer extends DefaultTableCellRenderer {

    String columnName;
    public TrazabilidadRenderer(){
    }
    // You should override getTableCellRendererComponent
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (isSelected) return c;
        
        String colName = Utiles.formattedColumn(table.getColumnName(column));
        if (!isTrazabilidadColumn(colName) || !(table.getModel() instanceof RemitoDetalleTableModel)){
            //stay at default
            c.setForeground(Color.BLACK);
            c.setBackground(Color.WHITE);
            c.setFont(new Font("Dialog", Font.PLAIN, 12));
            return c;
        }
            
        
        RemitoDetalleTableModel m = (RemitoDetalleTableModel)table.getModel();
        Integer trazabilidad = m.getRow(row).getProducto().getTrazabilidad();
        
        Boolean paintGrey = true;
        switch(colName){
            case "lote":
            case "vencimiento":
                if (trazabilidad > Utiles.TRAZABILIDAD.SIN_TRAZABILIDAD)
                    paintGrey = false;
                break;
            case "serie":
                if (trazabilidad > Utiles.TRAZABILIDAD.LOTE)
                    paintGrey = false;
                break;                
                
        }
        if (paintGrey){
                c.setForeground(Color.BLACK);
                c.setBackground(Color.LIGHT_GRAY);
                c.setFont(new Font("Dialog", Font.BOLD, 12));
        }
        else {
            // Here you should also stay at default
            //stay at default
            c.setForeground(Color.BLACK);
            c.setBackground(new Color(204, 229, 255));
            c.setFont(new Font("Dialog", Font.PLAIN, 12));
        }
        return c;
    }
    
    private Boolean isTrazabilidadColumn(String column){
        switch(column){
            case "lote":
            case "serie":
            case "vencimiento":
                    return true;
                
            default:
                return false;
        }
    }
}