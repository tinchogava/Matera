/**
 * This class is generated by jOOQ
 */
package matera.jooq.routines;


import java.math.BigDecimal;

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
public class Aplicaanticipo extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = -1821736656;

	/**
	 * The parameter <code>matera.aplicaAnticipo.id_profesional</code>.
	 */
	public static final Parameter<Integer> ID_PROFESIONAL = createParameter("id_profesional", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.aplicaAnticipo.aplicacion</code>.
	 */
	public static final Parameter<BigDecimal> APLICACION = createParameter("aplicacion", org.jooq.impl.SQLDataType.DECIMAL.precision(7, 2), false);

	/**
	 * The parameter <code>matera.aplicaAnticipo.id_presupuesto</code>.
	 */
	public static final Parameter<Integer> ID_PRESUPUESTO = createParameter("id_presupuesto", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.aplicaAnticipo.usuario</code>.
	 */
	public static final Parameter<String> USUARIO = createParameter("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * Create a new routine call instance
	 */
	public Aplicaanticipo() {
		super("aplicaAnticipo", Matera.MATERA);

		addInParameter(ID_PROFESIONAL);
		addInParameter(APLICACION);
		addInParameter(ID_PRESUPUESTO);
		addInParameter(USUARIO);
	}

	/**
	 * Set the <code>id_profesional</code> parameter IN value to the routine
	 */
	public void setIdProfesional(Integer value) {
		setValue(ID_PROFESIONAL, value);
	}

	/**
	 * Set the <code>aplicacion</code> parameter IN value to the routine
	 */
	public void setAplicacion(BigDecimal value) {
		setValue(APLICACION, value);
	}

	/**
	 * Set the <code>id_presupuesto</code> parameter IN value to the routine
	 */
	public void setIdPresupuesto(Integer value) {
		setValue(ID_PRESUPUESTO, value);
	}

	/**
	 * Set the <code>usuario</code> parameter IN value to the routine
	 */
	public void setUsuario(String value) {
		setValue(USUARIO, value);
	}
}