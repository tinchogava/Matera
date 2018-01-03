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
public class Listapresupuesto extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = -1684905085;

	/**
	 * The parameter <code>matera.listaPresupuesto.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.listaPresupuesto.meses</code>.
	 */
	public static final Parameter<Integer> MESES = createParameter("meses", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Listapresupuesto() {
		super("listaPresupuesto", Matera.MATERA);

		addInParameter(ID_EMPRESA);
		addInParameter(MESES);
	}

	/**
	 * Set the <code>id_empresa</code> parameter IN value to the routine
	 */
	public void setIdEmpresa(Integer value) {
		setValue(ID_EMPRESA, value);
	}

	/**
	 * Set the <code>meses</code> parameter IN value to the routine
	 */
	public void setMeses(Integer value) {
		setValue(MESES, value);
	}
}
