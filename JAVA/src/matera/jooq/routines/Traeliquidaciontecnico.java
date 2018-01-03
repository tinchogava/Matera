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
public class Traeliquidaciontecnico extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 1756994004;

	/**
	 * The parameter <code>matera.traeLiquidacionTecnico.nroLiquidacion</code>.
	 */
	public static final Parameter<Integer> NROLIQUIDACION = createParameter("nroLiquidacion", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.traeLiquidacionTecnico.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Traeliquidaciontecnico() {
		super("traeLiquidacionTecnico", Matera.MATERA);

		addInParameter(NROLIQUIDACION);
		addInParameter(ID_EMPRESA);
	}

	/**
	 * Set the <code>nroLiquidacion</code> parameter IN value to the routine
	 */
	public void setNroliquidacion(Integer value) {
		setValue(NROLIQUIDACION, value);
	}

	/**
	 * Set the <code>id_empresa</code> parameter IN value to the routine
	 */
	public void setIdEmpresa(Integer value) {
		setValue(ID_EMPRESA, value);
	}
}
