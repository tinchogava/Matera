/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.gui.combobox;

import ca.odell.glazedlists.matchers.TextMatcherEditor;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import ca.odell.glazedlists.swing.EventComboBoxModel;
import java.awt.event.ItemEvent;

/**
 *
 * @author h2o
 */
public class GlazedAutocomplete {
    public static AutoCompleteSupport enable(GlazedCombo combo){
        // Use a GlazedLists EventComboBoxModel to connect the JComboBox with an EventList.
        EventComboBoxModel<Object> model = new EventComboBoxModel<>(combo.getEventList());
        combo.setModel(model);

        AutoCompleteSupport autocomplete = AutoCompleteSupport.install(combo, combo.getEventList(), new ObjectFilterator());
        // Try without the filterator to see the difference.
        //autocomplete.setStrict(true);
        autocomplete.setFilterMode(TextMatcherEditor.CONTAINS);
        combo.addItemListener((ItemEvent event) -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                Object item = event.getItem();
                if (!autocomplete.getItemList().contains(item)){
                    combo.setSelectedItem(combo.getEventList().get(0));
                    //combo.updateUI();
                }
            }
        });
        return autocomplete;
    }
}
