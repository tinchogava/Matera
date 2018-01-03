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
public class Recibeintersucursal extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = -966732164;

	/**
	 * The parameter <code>matera.recibeIntersucursal.id_remito</code>.
	 */
	public static final Parameter<Integer> ID_REMITO = createParameter("id_remito", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.recibeIntersucursal.observaciones</code>.
	 */
	public static final Parameter<String> OBSERVACIONES = createParameter("observaciones", org.jooq.impl.SQLDataType.CLOB, false);

	/**
	 * The parameter <code>matera.recibeIntersucursal.usuario</code>.
	 */
	public static final Parameter<String> USUARIO = createParameter("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * Create a new routine call instance
	 */
	public Recibeintersucursal() {
		super("recibeIntersucursal", Matera.MATERA);

		addInParameter(ID_REMITO);
		addInParameter(OBSERVACIONES);
		addInParameter(USUARIO);
	}

	/**
	 * Set the <code>id_remito</code> parameter IN value to the routine
	 */
	public void setIdRemito(Integer value) {
		setValue(ID_REMITO, value);
	}

	/**
	 * Set the <code>observaciones</code> parameter IN value to the routine
	 */
	public void setObservaciones(String value) {
		setValue(OBSERVACIONES, value);
	}

	/**
	 * Set the <code>usuario</code> parameter IN value to the routine
	 */
	public void setUsuario(String value) {
		setValue(USUARIO, value);
	}
}