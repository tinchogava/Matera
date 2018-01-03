package ar.com.bosoft.formatosCampos;

/**
 *@author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;
import java.text.*;
import java.util.Locale;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class Decimal extends JFormattedTextField {
    private final NumberFormat visualFormat,editFormat;
    private final NumberFormatter visualFormatter, editFormatter;
    private final DefaultFormatterFactory formatterFactory;
    
    public Decimal(boolean autoseleccion) {
	// Formato de visualización
        visualFormat = NumberFormat.getNumberInstance();
        // Formato de edición: inglés (separador decimal: el punto)
        editFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        // Para la edición, no queremos separadores de millares
        editFormat.setGroupingUsed(false);
        // Creamos los formateadores de números
        visualFormatter = new NumberFormatter(visualFormat);
        editFormatter = new NumberFormatter(editFormat);
        editFormatter.setFormat(new DecimalFormat("#,###.00"));
        // Creamos la factoría de formateadores especificando los
        // formateadores por defecto, de visualización y de edición
        formatterFactory = new DefaultFormatterFactory(visualFormatter, visualFormatter, editFormatter);
        // El formateador de edición no admite caracteres incorrectos
        editFormatter.setAllowsInvalid(false);
        // El formateador de edición actualiza automaticamente el valor del campo
        editFormatter.setCommitsOnValidEdit(true);
        // Asignamos la factoría al campo
        setFormatterFactory(formatterFactory);
        
        if (autoseleccion){
            this.addFocusListener(new FocusAdapter(){
                @Override
                public void focusGained (FocusEvent e){
                    SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        Decimal.this.selectAll();
                    }
                    });
                }
            });
        }
    }
}