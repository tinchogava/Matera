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
public class Consultaempresa extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 1040756191;

	/**
	 * The parameter <code>matera.consultaEmpresa.soloHabilitados</code>.
	 */
	public static final Parameter<Byte> SOLOHABILITADOS = createParameter("soloHabilitados", org.jooq.impl.SQLDataType.TINYINT, false);

	/**
	 * Create a new routine call instance
	 */
	public Consultaempresa() {
		super("consultaEmpresa", Matera.MATERA);

		addInParameter(SOLOHABILITADOS);
	}

	/**
	 * Set the <code>soloHabilitados</code> parameter IN value to the routine
	 */
	public void setSolohabilitados(Byte value) {
		setValue(SOLOHABILITADOS, value);
	}
}
