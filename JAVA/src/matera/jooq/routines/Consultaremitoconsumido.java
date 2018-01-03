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
public class Consultaremitoconsumido extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 1380083272;

	/**
	 * The parameter <code>matera.consultaRemitoConsumido.id_presupuesto</code>.
	 */
	public static final Parameter<Integer> ID_PRESUPUESTO = createParameter("id_presupuesto", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaRemitoConsumido.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Consultaremitoconsumido() {
		super("consultaRemitoConsumido", Matera.MATERA);

		addInParameter(ID_PRESUPUESTO);
		addInParameter(ID_EMPRESA);
	}

	/**
	 * Set the <code>id_presupuesto</code> parameter IN value to the routine
	 */
	public void setIdPresupuesto(Integer value) {
		setValue(ID_PRESUPUESTO, value);
	}

	/**
	 * Set the <code>id_empresa</code> parameter IN value to the routine
	 */
	public void setIdEmpresa(Integer value) {
		setValue(ID_EMPRESA, value);
	}
}