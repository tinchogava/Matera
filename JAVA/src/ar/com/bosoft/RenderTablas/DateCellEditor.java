/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.bosoft.RenderTablas;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * A CellEditor for tables, using a JDateChooser.
 * 
 * @author Kai Toedter
 * @version $LastChangedRevision: 100 $
 * @version $LastChangedDate: 2006-06-04 14:36:06 +0200 (So, 04 Jun 2006) $
 */
public class DateCellEditor extends AbstractCellEditor implements
		TableCellEditor {

	private static final long serialVersionUID = 917881575221755609L;

	private JDateChooser dateChooser = new JDateChooser("dd/MM/20yy", "##/##/####", '_');
        
        public DateCellEditor(JDateChooser date){
            dateChooser = date;
        }

        @Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {

		Date date = null;
		if (value instanceof Date)
			date = (Date) value;

		dateChooser.setDate(date);

		return dateChooser;
	}

        @Override
	public Object getCellEditorValue() {
            SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
            if (dateChooser.getDate() != null)
                return dt.format(dateChooser.getDate());
            return "";
	}
}
