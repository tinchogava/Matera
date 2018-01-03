/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.gui.renderers.combobox;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import matera.jooq.tables.records.ClasiproductoRecord;

/**
 *
 * @author h2o
 */
public class ClasiProductoRenderer extends BasicComboBoxRenderer
{
    public Component getListCellRendererComponent(
        JList list, Object value, int index,
        boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index,
            isSelected, cellHasFocus);

        if (value != null)
        {
            ClasiproductoRecord item = (ClasiproductoRecord)value;
            setText( item.getNombre().toUpperCase() );
        }
/*
        if (index == -1)
        {
            setText("");
        }
*/

        return this;
    }
}