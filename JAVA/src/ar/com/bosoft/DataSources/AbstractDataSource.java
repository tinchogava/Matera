/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.clases.Utiles;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author h2o
 */
abstract class AbstractDataSource<T> {
    protected final List<T> list = new ArrayList<>();
    protected int index = -1;
    
    public void add(T o){
        this.list.add(o);
    } 
    
    public void add(List<T> l){        
        this.list.addAll(l);
    }     
    
    public boolean next() throws JRException {
        return ++index < list.size();
    }

    public Object getFieldValue(JRField jrf) throws JRException {
        return Utiles.getAttr(list.get(index), jrf.getName());
    }
    
     public boolean isEmpty(){
        return this.list.isEmpty();
    }
     
    public List<T> getList(){
        return list;
    }     
}
