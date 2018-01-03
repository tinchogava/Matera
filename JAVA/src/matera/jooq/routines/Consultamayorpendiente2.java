/**
 * This class is generated by jOOQ
 */
package matera.jooq.routines;


import java.sql.Date;

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
public class Consultamayorpendiente2 extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = -500047125;

	/**
	 * The parameter <code>matera.consultaMayorPendiente2.ids</code>.
	 */
	public static final Parameter<String> IDS = createParameter("ids", org.jooq.impl.SQLDataType.CLOB, false);

	/**
	 * The parameter <code>matera.consultaMayorPendiente2.desde</code>.
	 */
	public static final Parameter<Date> DESDE = createParameter("desde", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.consultaMayorPendiente2.hasta</code>.
	 */
	public static final Parameter<Date> HASTA = createParameter("hasta", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.consultaMayorPendiente2.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Consultamayorpendiente2() {
		super("consultaMayorPendiente2", Matera.MATERA);

		addInParameter(IDS);
		addInParameter(DESDE);
		addInParameter(HASTA);
		addInParameter(ID_EMPRESA);
	}

	/**
	 * Set the <code>ids</code> parameter IN value to the routine
	 */
	public void setIds(String value) {
		setValue(IDS, value);
	}

	/**
	 * Set the <code>desde</code> parameter IN value to the routine
	 */
	public void setDesde(Date value) {
		setValue(DESDE, value);
	}

	/**
	 * Set the <code>hasta</code> parameter IN value to the routine
	 */
	public void setHasta(Date value) {
		setValue(HASTA, value);
	}

	/**
	 * Set the <code>id_empresa</code> parameter IN value to the routine
	 */
	public void setIdEmpresa(Integer value) {
		setValue(ID_EMPRESA, value);
	}
}
