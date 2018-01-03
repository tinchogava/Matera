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
public class Consultaproductofact extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 311341164;

	/**
	 * The parameter <code>matera.consultaProductoFact.soloHabilitados</code>.
	 */
	public static final Parameter<Byte> SOLOHABILITADOS = createParameter("soloHabilitados", org.jooq.impl.SQLDataType.TINYINT, false);

	/**
	 * The parameter <code>matera.consultaProductoFact.ordena</code>.
	 */
	public static final Parameter<Integer> ORDENA = createParameter("ordena", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Consultaproductofact() {
		super("consultaProductoFact", Matera.MATERA);

		addInParameter(SOLOHABILITADOS);
		addInParameter(ORDENA);
	}

	/**
	 * Set the <code>soloHabilitados</code> parameter IN value to the routine
	 */
	public void setSolohabilitados(Byte value) {
		setValue(SOLOHABILITADOS, value);
	}

	/**
	 * Set the <code>ordena</code> parameter IN value to the routine
	 */
	public void setOrdena(Integer value) {
		setValue(ORDENA, value);
	}
}
