package ar.com.bosoft.clases;
// Objetos para la elaboración de menús
// Objetos para las teclas de abreviación
import java.awt.event.*;
import java.lang.reflect.*;
// Objetos de lista ordenada por clave
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

// Declaración de la clase MiMenu
public class MiMenu extends JMenuBar implements ActionListener {
    private final ArrayList vtOpc; // Matriz de opciones
    private String MetodoAccion = ""; // Nombre del método de acciones
    private final int LonOpc; // Número de filas de la matriz de opciones
    String empresa;
    // Constructor de la clase MiMenu
    public MiMenu(ArrayList vtOpcPar, String MetAccion, String empresa) {
        this.empresa = empresa;
        // Asignación de parámetros y variables de instancia
        vtOpc = vtOpcPar;
        MetodoAccion = MetAccion;
        LonOpc = vtOpc.size();
        
        /* Definición de la orientación y aspecto del menú. Necesaria
        para que opciones de acción directamente colocadas en la
        barra de menús restrinjan su tamaño al mínimo */
        //setLayout(new FlowLayout(FlowLayout.LEFT));
        
        // Definición de la lista que contendrá los objetos del menú
        Hashtable Lista = new Hashtable();
        // Encontrando los tipos de objetos y llenando la lista con ellos
        JMenu tmpMenu;
        JMenuItem tmpItem;
        Iterator i = vtOpc.iterator();
        while (i.hasNext()){
            Object[] tmp = (Object[]) i.next();
            if (TieneHijos(tmp[0].toString())){
                // Si tiene hijos, debe ser un submenú
                tmpMenu = new JMenu(tmp[0].toString());
                // El nombre del objeto es la clave de la opción
                tmpMenu.setName(tmp[0].toString());
                /* El texto que se mostrará va sin el señalador del
                mnemónico */
                tmpMenu.setText(QuitaCar(tmp[1].toString(), '_'));
                /* El mnemónico se asigna como el caracter despúes del
                señalador */
                tmpMenu.setMnemonic(
                EncuentraMnemonico(tmp[1].toString(), '_'));
                // Se agrega el objeto a la lista, ordenado por su clave
                Lista.put(tmp[0].toString(), tmpMenu);
            } else {
                // Si no, debe ser una opción de acción
                tmpItem = new JMenuItem(tmp[0].toString());
                // El nombre del objeto es la clave de la opción
                tmpItem.setName(tmp[0].toString());
                /* El texto que se mostrará va sin el señalador del
                mnemónico */
                tmpItem.setText(QuitaCar(tmp[1].toString(), '_'));
                /* El mnemónico se asigna como el caracter despúes del
                señalador */
                char Nemonico = EncuentraMnemonico(tmp[1].toString(), '_');
                tmpItem.setMnemonic(Nemonico);
                
//                // La tecla de aceleración es el mismo mnemónico
//                tmpItem.setAccelerator(KeyStroke.getKeyStroke(
//                Nemonico, ActionEvent.ALT_MASK));
                
                /* El comando de acción del objeto es también la clave
                de la opción */
                tmpItem.setActionCommand(tmp[2].toString());
                /* Este es de acciones por lo que debe ser escuchado
                por el sistema */
                tmpItem.addActionListener(this);
                // Se agrega el objeto a la lista, ordenado por su clave
                Lista.put(tmp[0].toString(), tmpItem);
            }
        }
        
        String IdPapa;
        // Variable temporal utilizada sólo para la comparación de clases
        JMenu tmpMenuPrb = new JMenu();
        /* Conectando los objetos de la lista de acuerdo con las
        jerarquías establecidas */
        i = vtOpc.iterator();
        
        while (i.hasNext()){
            Object[] tmp = (Object[]) i.next();
            if (EsPrincipal(tmp[0].toString())){
                /* Si es una opción principal, no tiene padre y se agrega a
                la barra de menús. Dependiendo del tipo de objeto se
                recupera de la lista por su clave */
                    if (Lista.get(tmp[0].toString()).getClass() == tmpMenuPrb.getClass()) {
                        add((JMenu) Lista.get(tmp[0].toString()));
                    } else {
                        add((JMenuItem) Lista.get(tmp[0].toString()));
                    }
            } else {
                /* Si no, tiene un padre. Dependiendo del tipo de objeto
                se recupera de la lista por su clave y se anexa al padre
                encontrado */
                IdPapa = tmp[0].toString().substring(0,tmp[0].toString().length() - 1);
                tmpMenu = (JMenu) Lista.get(IdPapa);
                if (Lista.get(tmp[0].toString()).getClass() == tmpMenuPrb.getClass()) {
                    tmpMenu.add((JMenu) Lista.get(tmp[0].toString()));
                } else {
                    /* Busca si el item es un separador */
                    if (Separador(tmp[1].toString())){
                        tmpMenu.addSeparator();
                    }else{
                        tmpMenu.add((JMenuItem) Lista.get(tmp[0].toString()));
                    }
                }
            }
        }
    } // Fin del constructor MiMenu
    
    /* Método que determina si el item es un separador */
    private boolean Separador(String Nombre){
        return Nombre.trim().equals("/-/");
    }
    
    /* Método que determina, dada la clave de una opción,
    si ésta tiene subopciones */
    private boolean TieneHijos(String Item) {
        /* Cuenta el número de veces que aparece la clave dada iniciando
        otras claves. Si ésta aparece más de una vez, la opción tiene
        subopciones */
        int NVeces = 0;
        int LonItem = Item.length();
        Iterator i = vtOpc.iterator();
        while (i.hasNext()){
            Object[] tmp = (Object[]) i.next();
            if (tmp[0].toString().length() >= LonItem) {
                if (tmp[0].toString().substring(0,LonItem).equals(Item)) {
                    NVeces++;
                    if (NVeces > 1) {
                        return true;
                    }
                }
            }
        }
        return (NVeces > 1);
    } // Fin de TieneHijos
    
    /* Método que determina, dada la clave de una opción,
    si ésta pertenece a la barra de menús */
    private boolean EsPrincipal(String Item) {
        // En la barra de menús se esperan claves de un solo dígito
        return (Item.length() == 1 );
    } // Fin de EsPrincipal
    
    /* Método de propósito general que quita un caracter específico
    de una cadena */
    private String QuitaCar(String Cad, char c) {
        int Pos = Cad.indexOf(c);
        int Lon = Cad.length();
        if (Pos != -1) {
            // Si está el caracter
            if (Pos == 0) {
                return Cad.substring(1, Lon);
            } else {
                if (Pos == Lon-1) {
                    return Cad.substring(0, Lon-1);
                } else {
                    return Cad.substring(0, Pos) + Cad.substring(Pos+1, Lon);
                }
            }
        }
        return Cad;
    } // Fin de QuitaCar
    
    /* Método que retorna el caracter siguiente al señalado,
    entendiendo que dicho caracter es el mnemónico de una
    opción de menú */
    private char EncuentraMnemonico(String Cad, char c) {
        int Pos = Cad.indexOf(c);
        if (Pos >= Cad.length() - 1) {
            /* El señalador de mnemónico no debe ser el último caracter
            de la cadena */
            return 0;
        }
        return Cad.charAt(Pos+1);
    } // Fin de EncuentraMnemonico
    
    /* Método para la resolución de las acciones en respuesta a
    los eventos de MiMenu que estén siendo escuchados */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            /* Si el contexto en que se utiliza el objeto MiMenu es
            una ventana, dentro de la cual hay una barra de menús,
            extendiendo la barra MiMenu, el objeto que instancia
            la clase está en la tercera generación. Si este no es el
            caso, habrá que alterar la instrucción, referenciando
            el objeto padre en la generación correcta */
            Object objPapa = getParent().getParent().getParent();
            Class MiPapa = getParent().getParent().getParent().getClass();
            /* Estableciendo que los parámetros del método de acciones
            en la clase que llama a MiMenu son de tipo String y
            pasando como argumento a dicho método la clave de la
            opción seleccionada */
            Class[] TiposParametros = new Class[] {String.class};
            Object[] argumentos = new Object[] {e.getActionCommand()};
            /* Definiendo el método de acciones de la clase que llama
            a MiMenu y sus parámetros para luego invocarlo
            ocasionando su ejecución */
            Method target = objPapa.getClass().getMethod(MetodoAccion, TiposParametros);
            Object param[] = { e.getActionCommand() };
            target.invoke(objPapa,argumentos);
        } catch ( IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException exp ) {
            exp.getCause().printStackTrace();
            Utiles.enviaError(this.empresa,exp);
        }
    } // Fin de actionPerformed
} // Fin de la clase MiMenu