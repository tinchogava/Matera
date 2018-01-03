/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.clases;

import java.util.HashMap;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRRewindableDataSource;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class JRTableSorterDataSource implements JRRewindableDataSource {

    private RowSorter<? extends TableModel> sorter;

    private int currentRow = -1;

    private HashMap<String, Integer> columnNames = new HashMap<>();

    public JRTableSorterDataSource(RowSorter<? extends TableModel> sorter) {
        if (sorter == null) return; // do nothing, no sorter
        this.sorter = sorter;
        TableModel tableModel = sorter.getModel();
        if (tableModel != null) {
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                this.columnNames.put(tableModel.getColumnName(i), i);
            }
        }

    }

    @Override
    public Object getFieldValue(JRField field) throws JRException {
        String fieldName = field.getName();
        String[] f = fieldName.split("_");
        Integer columnIndex = Integer.parseInt(f[f.length - 1]);
        //Integer columnIndex = Integer.parseInt(fieldName.substring(fieldName.length() - 1));
        Object obj = null;
        try {
            obj = sorter.getModel().getValueAt(sorter.convertRowIndexToModel(currentRow), columnIndex);
        } catch (Exception e) {
        }
        return obj;
//        return sorter.getModel().getValueAt(sorter.convertRowIndexToModel(currentRow), columnIndex);
    }


    @Override
    public boolean next() throws JRException {
        if (sorter == null || sorter.getModel() == null)
            return false;
        this.currentRow++;
        return (this.currentRow < sorter.getViewRowCount());
    }

    @Override
    public void moveFirst() throws JRException {
        this.currentRow = -1;
    }

    protected int getColumnIndex(JRField field) throws JRException {
        String fieldName = field.getName();
        Integer columnIndex = this.columnNames.get(fieldName);

        if (columnIndex != null) {
            return columnIndex;
        } else if (fieldName.startsWith("COLUMN_")) {
            return Integer.parseInt(fieldName.substring(7));
        }
        throw new JRException("Unknown column name : " + fieldName);
    }

}