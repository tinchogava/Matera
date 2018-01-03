/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.RenderTablas;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class FilasResaltadas  extends DefaultTableCellRenderer{
    Color fondo = Color.YELLOW;
    Color fuente = Color.BLACK;
    int columnaVerificadora = 0;
    Object verificador = "SI";

    public void setFondo(Color fondo) {
        this.fondo = fondo;
    }

    public void setFuente(Color fuente) {
        this.fuente = fuente;
    }

    public void setColumnaVerificadora(int columnaVerificadora) {
        this.columnaVerificadora = columnaVerificadora;
    }

    public void setVerificador(Object verificador) {
        this.verificador = verificador;
    }
    
    @Override
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        if (table.getValueAt(row,columnaVerificadora).toString().equals(verificador.toString())){
            this.setForeground(fuente);
            this.setBackground(fondo);
        } else {
            this.setForeground(Color.BLACK);
            this.setBackground(Color.WHITE);
        }  
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
    }
    
}
