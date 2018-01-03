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
public class Logsistema extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 261594017;

	/**
	 * The parameter <code>matera.logSistema.usuario</code>.
	 */
	public static final Parameter<String> USUARIO = createParameter("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.logSistema.equipo</code>.
	 */
	public static final Parameter<String> EQUIPO = createParameter("equipo", org.jooq.impl.SQLDataType.VARCHAR.length(255), false);

	/**
	 * The parameter <code>matera.logSistema.log</code>.
	 */
	public static final Parameter<String> LOG = createParameter("log", org.jooq.impl.SQLDataType.CLOB, false);

	/**
	 * Create a new routine call instance
	 */
	public Logsistema() {
		super("logSistema", Matera.MATERA);

		addInParameter(USUARIO);
		addInParameter(EQUIPO);
		addInParameter(LOG);
	}

	/**
	 * Set the <code>usuario</code> parameter IN value to the routine
	 */
	public void setUsuario(String value) {
		setValue(USUARIO, value);
	}

	/**
	 * Set the <code>equipo</code> parameter IN value to the routine
	 */
	public void setEquipo(String value) {
		setValue(EQUIPO, value);
	}

	/**
	 * Set the <code>log</code> parameter IN value to the routine
	 */
	public void setLog(String value) {
		setValue(LOG, value);
	}
}