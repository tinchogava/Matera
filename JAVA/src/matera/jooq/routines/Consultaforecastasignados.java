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
public class Consultaforecastasignados extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 1973854053;

	/**
	 * The parameter <code>matera.consultaForecastAsignados.anio</code>.
	 */
	public static final Parameter<Integer> ANIO = createParameter("anio", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaForecastAsignados.id_vendedor</code>.
	 */
	public static final Parameter<Integer> ID_VENDEDOR = createParameter("id_vendedor", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaForecastAsignados.id_entidad</code>.
	 */
	public static final Parameter<Integer> ID_ENTIDAD = createParameter("id_entidad", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaForecastAsignados.id_forecastGrupo</code>.
	 */
	public static final Parameter<Integer> ID_FORECASTGRUPO = createParameter("id_forecastGrupo", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Consultaforecastasignados() {
		super("consultaForecastAsignados", Matera.MATERA);

		addInParameter(ANIO);
		addInParameter(ID_VENDEDOR);
		addInParameter(ID_ENTIDAD);
		addInParameter(ID_FORECASTGRUPO);
	}

	/**
	 * Set the <code>anio</code> parameter IN value to the routine
	 */
	public void setAnio(Integer value) {
		setValue(ANIO, value);
	}

	/**
	 * Set the <code>id_vendedor</code> parameter IN value to the routine
	 */
	public void setIdVendedor(Integer value) {
		setValue(ID_VENDEDOR, value);
	}

	/**
	 * Set the <code>id_entidad</code> parameter IN value to the routine
	 */
	public void setIdEntidad(Integer value) {
		setValue(ID_ENTIDAD, value);
	}

	/**
	 * Set the <code>id_forecastGrupo</code> parameter IN value to the routine
	 */
	public void setIdForecastgrupo(Integer value) {
		setValue(ID_FORECASTGRUPO, value);
	}
}