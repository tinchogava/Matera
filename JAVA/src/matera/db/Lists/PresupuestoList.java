/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.Lists;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import matera.db.Factura;
import matera.db.Presupuesto;
import matera.db.PresupuestoProducto;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author h2o
 */
public class PresupuestoList extends LazyList<Presupuesto> implements List<Presupuesto> {
    
    LazyList<Presupuesto> presupuestos;
    
    public PresupuestoList(LazyList<Presupuesto> a){
        presupuestos = a;
    }
    
    public void filterHasFacturas(Boolean facturado){
        Iterator<Presupuesto> it = presupuestos.iterator();
        while(it.hasNext()){
            Presupuesto p = it.next();
            if (facturado && p.getAll(Factura.class).size() == 0)
                it.remove();
            else if (!facturado && p.getAll(Factura.class).size() > 0)
                it.remove();
        }
    }
    
    public void filterProducto(Integer producto){
        if (producto < 1) return;
        Iterator<Presupuesto> it = presupuestos.iterator();
        while(it.hasNext()){
            Presupuesto p = it.next();
            LazyList<PresupuestoProducto> productos = p.getAll(PresupuestoProducto.class);
            if (productos.isEmpty())
                it.remove();
            else if (!productos.get(0).get("id_productoFact").equals(producto))
                it.remove();
        }
    }
    
    @Override
    public boolean add(Presupuesto presupuesto) {
    return presupuestos.add(presupuesto);
    }


    @Override
    public void add(int index, Presupuesto presupuesto) {
    presupuestos.add(index, presupuesto);
    }


    @Override
    public boolean addAll(Collection<? extends Presupuesto> presupuestoItems) {
    return presupuestos.addAll(presupuestoItems);
    }


    @Override
    public boolean addAll(int index, Collection<? extends Presupuesto> presupuestoItems) {
    return presupuestos.addAll(index, presupuestoItems);
    }


    @Override
    public void clear() {
    presupuestos.clear();
    }


    @Override
    public boolean contains(Object presupuesto) {
    return presupuestos.contains(presupuesto);
    }


    @Override
    public boolean containsAll(Collection<?> presupuestoItems){
    return presupuestos.containsAll(presupuestoItems);
    }


    @Override
    public Presupuesto get(int index) {
    return presupuestos.get(index);
    }


    @Override
    public int indexOf(Object index) {
    return presupuestos.indexOf(index);
    }


    @Override
    public boolean isEmpty() {
    return presupuestos.isEmpty();
    }


    @Override
    public Iterator<Presupuesto> iterator() {
    return presupuestos.iterator();
    }


    @Override
    public int lastIndexOf(Object presupuesto) {
    return presupuestos.lastIndexOf(presupuesto);
    }


    @Override
    public ListIterator<Presupuesto> listIterator(){
    return presupuestos.listIterator();
    }


    @Override
    public ListIterator<Presupuesto> listIterator(int index) {
    return presupuestos.listIterator(index);
    }


    @Override
    public boolean remove(Object index) {
    return presupuestos.remove(index);
    }


    @Override
    public Presupuesto remove(int index) {
    return presupuestos.remove(index);
    }


    @Override
    public boolean removeAll(Collection<?> presupuestoItems) {
    return presupuestos.removeAll(presupuestoItems);
    }


    @Override
    public boolean retainAll(Collection<?> presupuestoItems) {
    return presupuestos.retainAll(presupuestoItems);
    }


    @Override
    public Presupuesto set(int index, Presupuesto presupuesto) {
    return presupuestos.set(index, presupuesto);
    }


    @Override
    public int size() {
    return presupuestos.size();
    }


    @Override
    public List<Presupuesto> subList(int fromIndex, int toIndex) {
    return presupuestos.subList(fromIndex, toIndex);
    }


    @Override
    public Object[] toArray() {
    return presupuestos.toArray();
    }


    @Override
    public <T> T[] toArray(T[] presupuesto) {
    return presupuestos.toArray(presupuesto);
    }
}
