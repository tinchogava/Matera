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
public class Consultadepartamento extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = -2139575315;

	/**
	 * The parameter <code>matera.consultaDepartamento.id_provincia</code>.
	 */
	public static final Parameter<Integer> ID_PROVINCIA = createParameter("id_provincia", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Consultadepartamento() {
		super("consultaDepartamento", Matera.MATERA);

		addInParameter(ID_PROVINCIA);
	}

	/**
	 * Set the <code>id_provincia</code> parameter IN value to the routine
	 */
	public void setIdProvincia(Integer value) {
		setValue(ID_PROVINCIA, value);
	}
}
