/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.gui.renderers;

//import java.awt.Component;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.JTable;
//import javax.swing.JTextField;



/**
 *
 * @author h2o
 */

public class DateStringRenderer extends DefaultTableCellRenderer {
    DateFormat formatter;
    public DateStringRenderer() { super(); }

    @Override
    public void setValue(Object value) {
        if (formatter==null) {
            formatter = DateFormat.getDateInstance();
        }
        setText((value == null) ? "" : getFormattedDate(value.toString()));
    }
    
    public String getFormattedDate(String value){
        SimpleDateFormat stringFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = stringFormat.parse(value);
            SimpleDateFormat dateF = new SimpleDateFormat("dd/MM/yyyy");
            value = dateF.format(date);
        } catch (ParseException ex) {
            Logger.getLogger(DateStringRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }
}
