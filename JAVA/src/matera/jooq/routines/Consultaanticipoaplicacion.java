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
public class Consultaanticipoaplicacion extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 2101958884;

	/**
	 * The parameter <code>matera.consultaAnticipoAplicacion.id_profesional</code>.
	 */
	public static final Parameter<Integer> ID_PROFESIONAL = createParameter("id_profesional", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaAnticipoAplicacion.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Consultaanticipoaplicacion() {
		super("consultaAnticipoAplicacion", Matera.MATERA);

		addInParameter(ID_PROFESIONAL);
		addInParameter(ID_EMPRESA);
	}

	/**
	 * Set the <code>id_profesional</code> parameter IN value to the routine
	 */
	public void setIdProfesional(Integer value) {
		setValue(ID_PROFESIONAL, value);
	}

	/**
	 * Set the <code>id_empresa</code> parameter IN value to the routine
	 */
	public void setIdEmpresa(Integer value) {
		setValue(ID_EMPRESA, value);
	}
}
