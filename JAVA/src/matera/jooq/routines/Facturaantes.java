/**
 * This class is generated by jOOQ
 */
package matera.jooq.routines;


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
public class Facturaantes extends AbstractRoutine<Byte> {

	private static final long serialVersionUID = 529667780;

	/**
	 * The parameter <code>matera.facturaAntes.RETURN_VALUE</code>.
	 */
	public static final Parameter<Byte> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.TINYINT, false);

	/**
	 * The parameter <code>matera.facturaAntes.id_presupuesto</code>.
	 */
	public static final Parameter<Integer> ID_PRESUPUESTO = createParameter("id_presupuesto", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.facturaAntes.fecha</code>.
	 */
	public static final Parameter<Date> FECHA = createParameter("fecha", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * Create a new routine call instance
	 */
	public Facturaantes() {
		super("facturaAntes", Matera.MATERA, org.jooq.impl.SQLDataType.TINYINT);

		setReturnParameter(RETURN_VALUE);
		addInParameter(ID_PRESUPUESTO);
		addInParameter(FECHA);
	}

	/**
	 * Set the <code>id_presupuesto</code> parameter IN value to the routine
	 */
	public void setIdPresupuesto(Integer value) {
		setValue(ID_PRESUPUESTO, value);
	}

	/**
	 * Set the <code>id_presupuesto</code> parameter to the function to be used with a {@link org.jooq.Select} statement
	 */
	public void setIdPresupuesto(Field<Integer> field) {
		setField(ID_PRESUPUESTO, field);
	}

	/**
	 * Set the <code>fecha</code> parameter IN value to the routine
	 */
	public void setFecha(Date value) {
		setValue(FECHA, value);
	}

	/**
	 * Set the <code>fecha</code> parameter to the function to be used with a {@link org.jooq.Select} statement
	 */
	public void setFecha(Field<Date> field) {
		setField(FECHA, field);
	}
}