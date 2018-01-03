/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.gui.combobox;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import java.util.Collection;
import javax.swing.JComboBox;

/**
 *
 * @author h2o
 */
public class GlazedCombo extends JComboBox<Object> {
    
    EventList<Object> objects = new SortedList<>(new BasicEventList<>());
    AutoCompleteSupport autocomplete;
    
    public GlazedCombo(){
        super();
        enableAutocomplete();
    }
    
    private void enableAutocomplete(){
        autocomplete = GlazedAutocomplete.enable(this);
    }
    
    public AutoCompleteSupport getAutocomplete(){
        return autocomplete;
    }
    
    public EventList<Object> getEventList(){
        return objects;
    }
    
    public void fillEventList(Collection<?> clctn){
        objects.addAll(clctn);
    }
    
    public void removeItemFromEventList(Object o){
        objects.remove(o);
    }
    
    public void removeItemsFromEventList(Collection<?> clctn){
        objects.removeAll(clctn);
    }
    
    public void removeAllItemsFromEventList(){
        objects.removeAll(objects);
    }
}
