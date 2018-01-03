/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.gui.combobox;

import ca.odell.glazedlists.TextFilterator;
import java.util.List;

/**
 *
 * @author h2o
 */
class ObjectFilterator implements TextFilterator<Object> {
        @Override
        public void getFilterStrings(List<String> baseList, Object o) {
            baseList.add(o.toString());
        }
    }
