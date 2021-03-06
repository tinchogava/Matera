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
public class Reciberemito extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = -2128690742;

	/**
	 * The parameter <code>matera.recibeRemito.id_remito</code>.
	 */
	public static final Parameter<Integer> ID_REMITO = createParameter("id_remito", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.recibeRemito.usuario</code>.
	 */
	public static final Parameter<String> USUARIO = createParameter("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.recibeRemito.equipo</code>.
	 */
	public static final Parameter<String> EQUIPO = createParameter("equipo", org.jooq.impl.SQLDataType.VARCHAR.length(255), false);

	/**
	 * Create a new routine call instance
	 */
	public Reciberemito() {
		super("recibeRemito", Matera.MATERA);

		addInParameter(ID_REMITO);
		addInParameter(USUARIO);
		addInParameter(EQUIPO);
	}

	/**
	 * Set the <code>id_remito</code> parameter IN value to the routine
	 */
	public void setIdRemito(Integer value) {
		setValue(ID_REMITO, value);
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
}
