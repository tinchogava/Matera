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
public class Traeremito extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = -1167597906;

	/**
	 * The parameter <code>matera.traeRemito.id_remito</code>.
	 */
	public static final Parameter<Integer> ID_REMITO = createParameter("id_remito", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.traeRemito.numComp</code>.
	 */
	public static final Parameter<String> NUMCOMP = createParameter("numComp", org.jooq.impl.SQLDataType.VARCHAR.length(12), false);

	/**
	 * The parameter <code>matera.traeRemito.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Traeremito() {
		super("traeRemito", Matera.MATERA);

		addInParameter(ID_REMITO);
		addInParameter(NUMCOMP);
		addInParameter(ID_EMPRESA);
	}

	/**
	 * Set the <code>id_remito</code> parameter IN value to the routine
	 */
	public void setIdRemito(Integer value) {
		setValue(ID_REMITO, value);
	}

	/**
	 * Set the <code>numComp</code> parameter IN value to the routine
	 */
	public void setNumcomp(String value) {
		setValue(NUMCOMP, value);
	}

	/**
	 * Set the <code>id_empresa</code> parameter IN value to the routine
	 */
	public void setIdEmpresa(Integer value) {
		setValue(ID_EMPRESA, value);
	}
}
