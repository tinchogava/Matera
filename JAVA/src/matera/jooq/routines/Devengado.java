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
public class Devengado extends AbstractRoutine<Byte> {

	private static final long serialVersionUID = 730265150;

	/**
	 * The parameter <code>matera.devengado.RETURN_VALUE</code>.
	 */
	public static final Parameter<Byte> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.TINYINT, false);

	/**
	 * The parameter <code>matera.devengado.id_presupuesto</code>.
	 */
	public static final Parameter<Integer> ID_PRESUPUESTO = createParameter("id_presupuesto", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.devengado.fechaCirugia</code>.
	 */
	public static final Parameter<Date> FECHACIRUGIA = createParameter("fechaCirugia", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * Create a new routine call instance
	 */
	public Devengado() {
		super("devengado", Matera.MATERA, org.jooq.impl.SQLDataType.TINYINT);

		setReturnParameter(RETURN_VALUE);
		addInParameter(ID_PRESUPUESTO);
		addInParameter(FECHACIRUGIA);
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
	 * Set the <code>fechaCirugia</code> parameter IN value to the routine
	 */
	public void setFechacirugia(Date value) {
		setValue(FECHACIRUGIA, value);
	}

	/**
	 * Set the <code>fechaCirugia</code> parameter to the function to be used with a {@link org.jooq.Select} statement
	 */
	public void setFechacirugia(Field<Date> field) {
		setField(FECHACIRUGIA, field);
	}
}
