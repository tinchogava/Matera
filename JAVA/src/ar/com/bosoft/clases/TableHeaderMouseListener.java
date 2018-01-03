/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.clases;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class TableHeaderMouseListener extends MouseAdapter{
    private final JTable table;
    int columna;
     
    public TableHeaderMouseListener(JTable table) {
        this.table = table;
    }
     
    @Override
    public void mouseClicked(MouseEvent event) {
        Point point = event.getPoint();
        this.columna = table.columnAtPoint(point);
    }
    
    public int getColumna(){
        return columna;
    }
}
