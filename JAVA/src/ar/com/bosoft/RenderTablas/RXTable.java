/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.RenderTablas;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


/**
 * The RXTable provides some extensions to the default JTable
 *
 * 1) Select All editing - when a text related cell is placed in editing mode
 *    the text is selected. Controlled by invoking a "setSelectAll..." method.
 *
 * 2) reorderColumns - static convenience method for reodering table columns
 */
public class RXTable extends JTable{
    private boolean isSelectAllForMouseEvent = true;
    private boolean isSelectAllForActionEvent = true;
    private boolean isSelectAllForKeyEvent = true;
    
//
// Constructors
//
    /**
     * Constructs a default RXTable that is initialized with a default
     * data model, a default column model, and a default selection
     * model.
     */
    public RXTable(){
        this(null, null, null);
    }

    /**
     * Constructs a RXTable that is initialized with
     * dm as the data model, a default column model,
     * and a default selection model.
     *
     * @param dm        the data model for the table
     */
    public RXTable(TableModel dm){
        this(dm, null, null);
    }

    /**
     * Constructs a RXTable that is initialized with
     * dm as the data model, cm
     * as the column model, and a default selection model.
     *
     * @param dm        the data model for the table
     * @param cm        the column model for the table
     */
    public RXTable(TableModel dm, TableColumnModel cm){
        this(dm, cm, null);
    }

    /**
     * Constructs a RXTable that is initialized with
     * dm as the data model, cm as the
     * column model, and sm as the selection model.
     * If any of the parameters are null this method
     * will initialize the table with the corresponding default model.
     * The autoCreateColumnsFromModel flag is set to false
     * if cm is non-null, otherwise it is set to true
     * and the column model is populated with suitable
     * TableColumns for the columns in dm.
     *
     * @param dm        the data model for the table
     * @param cm        the column model for the table
     * @param sm        the row selection model for the table
     */
    public RXTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm){
        super(dm, cm, sm);
    }

    /**
     * Constructs a RXTable with numRows
     * and numColumns of empty cells using
     * DefaultTableModel.  The columns will have
     * names of the form "A", "B", "C", etc.
     *
     * @param numRows           the number of rows the table holds
     * @param numColumns        the number of columns the table holds
     */
    public RXTable(int numRows, int numColumns){
        this(new DefaultTableModel(numRows, numColumns));
    }

    /**
     * Constructs a RXTable to display the values in the
     * Vector of Vectors, rowData,
     * with column names, columnNames.  The
     * Vectors contained in rowData
     * should contain the values for that row. In other words,
     * the value of the cell at row 1, column 5 can be obtained
     * with the following code:
     *
     * ((Vector)rowData.elementAt(1)).elementAt(5);
     *
     * @param rowData           the data for the new table
     * @param columnNames       names of each column
     */
    public RXTable(Vector rowData, Vector columnNames){
        this(new DefaultTableModel(rowData, columnNames));
    }

    /**
     * Constructs a RXTable to display the values in the two dimensional array,
     * rowData, with column names, columnNames.
     * rowData is an array of rows, so the value of the cell at row 1,
     * column 5 can be obtained with the following code:
     *  rowData[1][5];
     * All rows must be of the same length as columnNames.
     * @param rowData           the data for the new table
     * @param columnNames       names of each column
     */
    public RXTable(final Object[][] rowData, final Object[] columnNames){
        super(rowData, columnNames);
    }
//
//  Overridden methods
//
	/*
	 *  Override to provide Select All editing functionality
	 */
    @Override
    public boolean editCellAt(int row, int column, EventObject e){
        boolean result = super.editCellAt(row, column, e);

        if (isSelectAllForMouseEvent
            ||  isSelectAllForActionEvent
            ||  isSelectAllForKeyEvent){
                selectAll(e);
        }

        return result;
    }
    
    /*
     * Select the text when editing on a text related cell is started
     */
    private void selectAll(EventObject e){
        final Component editor = getEditorComponent();

        if (editor == null || ! (editor instanceof JTextComponent)){
            return;
        }

        if (e == null){
            ((JTextComponent)editor).selectAll();
            return;
        }

        //  Typing in the cell was used to activate the editor

        if (e instanceof KeyEvent && isSelectAllForKeyEvent){
            ((JTextComponent)editor).selectAll();
            return;
        }

        //  F2 was used to activate the editor

        if (e instanceof ActionEvent && isSelectAllForActionEvent){
            ((JTextComponent)editor).selectAll();
            return;
        }

        //  A mouse click was used to activate the editor.
        //  Generally this is a double click and the second mouse click is
        //  passed to the editor which would remove the text selection unless
        //  we use the invokeLater()

        if (e instanceof MouseEvent && isSelectAllForMouseEvent){
            SwingUtilities.invokeLater(new Runnable(){
                @Override
                public void run(){
                        ((JTextComponent)editor).selectAll();
                }
            });
        }
    }

//
//  Newly added methods
//
    /*
     *  Sets the Select All property for for all event types
     */
    public void setSelectAllForEdit(boolean isSelectAllForEdit){
        setSelectAllForMouseEvent( isSelectAllForEdit );
        setSelectAllForActionEvent( isSelectAllForEdit );
        setSelectAllForKeyEvent( isSelectAllForEdit );
    }

    /*
     *  Set the Select All property when editing is invoked by the mouse
     */
    public void setSelectAllForMouseEvent(boolean isSelectAllForMouseEvent){
        this.isSelectAllForMouseEvent = isSelectAllForMouseEvent;
    }

    /*
     *  Set the Select All property when editing is invoked by the "F2" key
     */
    public void setSelectAllForActionEvent(boolean isSelectAllForActionEvent){
        this.isSelectAllForActionEvent = isSelectAllForActionEvent;
    }

    /*
     *  Set the Select All property when editing is invoked by
     *  typing directly into the cell
     */
    public void setSelectAllForKeyEvent(boolean isSelectAllForKeyEvent){
        this.isSelectAllForKeyEvent = isSelectAllForKeyEvent;
    }

//
//  Static, convenience methods
//
    /**
     *  Convenience method to order the table columns of a table. The columns
     *  are ordered based on the column names specified in the array. If the
     *  column name is not found then no column is moved. This means you can
     *  specify a null value to preserve the current order of a given column.
     *
 *  @param table        the table containing the columns to be sorted
 *  @param columnNames  an array containing the column names in the
 *                      order they should be displayed
     */
    public static void reorderColumns(JTable table, Object... columnNames){
        TableColumnModel model = table.getColumnModel();

        for (int newIndex = 0; newIndex < columnNames.length; newIndex++){
            try{
                Object columnName = columnNames[newIndex];
                int index = model.getColumnIndex(columnName);
                model.moveColumn(index, newIndex);
            }
            catch(IllegalArgumentException e) {}
        }
    }    
}  // End of Class RXTable