package ar.com.bosoft.formatosCampos;

import javax.swing.text.*;

/**
 *@author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class JTxtFieldLimitado extends PlainDocument {
    private final int limite;  //Cantidad de caracteres
    private boolean aMayusculas = false;    //todo en mayusculas
    
    public JTxtFieldLimitado(int limite) {
        super();
        this.limite = limite;
    }

    public JTxtFieldLimitado(int limite, boolean soloMayusculas) {
        super();
        this.limite = limite;
        this.aMayusculas = soloMayusculas;
    }
    
    @Override
    public void insertString (int offset, String  str, AttributeSet attr) throws BadLocationException {
        if (str == null){
            return;
        }

        if ((getLength() + str.length()) <= limite) {
            if (aMayusculas){
                str = str.toUpperCase();
            }
            super.insertString(offset, str, attr);
        }
    }
}