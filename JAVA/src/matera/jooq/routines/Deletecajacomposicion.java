/**
 * This class is generated by jOOQ
 */
package matera.jooq.routines;


import javax.annotation.Generated;

import matera.jooq.Matera;

import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Deletecajacomposicion extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = -690843534;

	/**
	 * The parameter <code>matera.deleteCajaComposicion.id_cajaDeposito</code>.
	 */
	public static final Parameter<Integer> ID_CAJADEPOSITO = createParameter("id_cajaDeposito", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Deletecajacomposicion() {
		super("deleteCajaComposicion", Matera.MATERA);

		addInParameter(ID_CAJADEPOSITO);
	}

	/**
	 * Set the <code>id_cajaDeposito</code> parameter IN value to the routine
	 */
	public void setIdCajadeposito(Integer value) {
		setValue(ID_CAJADEPOSITO, value);
	}
}
