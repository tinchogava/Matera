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
public class Controlobrassociales extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 734556968;

	/**
	 * The parameter <code>matera.controlObrasSociales.desde</code>.
	 */
	public static final Parameter<Date> DESDE = createParameter("desde", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.controlObrasSociales.hasta</code>.
	 */
	public static final Parameter<Date> HASTA = createParameter("hasta", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.controlObrasSociales.id_zona</code>.
	 */
	public static final Parameter<Integer> ID_ZONA = createParameter("id_zona", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.controlObrasSociales.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Controlobrassociales() {
		super("controlObrasSociales", Matera.MATERA);

		addInParameter(DESDE);
		addInParameter(HASTA);
		addInParameter(ID_ZONA);
		addInParameter(ID_EMPRESA);
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
	 * Set the <code>id_zona</code> parameter IN value to the routine
	 */
	public void setIdZona(Integer value) {
		setValue(ID_ZONA, value);
	}

	/**
	 * Set the <code>id_empresa</code> parameter IN value to the routine
	 */
	public void setIdEmpresa(Integer value) {
		setValue(ID_EMPRESA, value);
	}
}
