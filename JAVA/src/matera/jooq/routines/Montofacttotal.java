/**
 * This class is generated by jOOQ
 */
package matera.jooq.routines;


import java.math.BigDecimal;
import java.sql.Date;

import javax.annotation.Generated;

import matera.jooq.Matera;

import org.jooq.Field;
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
public class Montofacttotal extends AbstractRoutine<BigDecimal> {

	private static final long serialVersionUID = 1886228957;

	/**
	 * The parameter <code>matera.montoFactTotal.RETURN_VALUE</code>.
	 */
	public static final Parameter<BigDecimal> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.DECIMAL.precision(20, 2), false);

	/**
	 * The parameter <code>matera.montoFactTotal.id_entidad</code>.
	 */
	public static final Parameter<Integer> ID_ENTIDAD = createParameter("id_entidad", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.montoFactTotal.desde</code>.
	 */
	public static final Parameter<Date> DESDE = createParameter("desde", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.montoFactTotal.hasta</code>.
	 */
	public static final Parameter<Date> HASTA = createParameter("hasta", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * Create a new routine call instance
	 */
	public Montofacttotal() {
		super("montoFactTotal", Matera.MATERA, org.jooq.impl.SQLDataType.DECIMAL.precision(20, 2));

		setReturnParameter(RETURN_VALUE);
		addInParameter(ID_ENTIDAD);
		addInParameter(DESDE);
		addInParameter(HASTA);
	}

	/**
	 * Set the <code>id_entidad</code> parameter IN value to the routine
	 */
	public void setIdEntidad(Integer value) {
		setValue(ID_ENTIDAD, value);
	}

	/**
	 * Set the <code>id_entidad</code> parameter to the function to be used with a {@link org.jooq.Select} statement
	 */
	public void setIdEntidad(Field<Integer> field) {
		setField(ID_ENTIDAD, field);
	}

	/**
	 * Set the <code>desde</code> parameter IN value to the routine
	 */
	public void setDesde(Date value) {
		setValue(DESDE, value);
	}

	/**
	 * Set the <code>desde</code> parameter to the function to be used with a {@link org.jooq.Select} statement
	 */
	public void setDesde(Field<Date> field) {
		setField(DESDE, field);
	}

	/**
	 * Set the <code>hasta</code> parameter IN value to the routine
	 */
	public void setHasta(Date value) {
		setValue(HASTA, value);
	}

	/**
	 * Set the <code>hasta</code> parameter to the function to be used with a {@link org.jooq.Select} statement
	 */
	public void setHasta(Field<Date> field) {
		setField(HASTA, field);
	}
}
