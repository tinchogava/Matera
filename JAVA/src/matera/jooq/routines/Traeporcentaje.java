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
public class Traeporcentaje extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 722881560;

	/**
	 * The parameter <code>matera.traePorcentaje.id_grupoPorcentaje</code>.
	 */
	public static final Parameter<Integer> ID_GRUPOPORCENTAJE = createParameter("id_grupoPorcentaje", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.traePorcentaje.id_especialidad</code>.
	 */
	public static final Parameter<Integer> ID_ESPECIALIDAD = createParameter("id_especialidad", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.traePorcentaje.id_zona</code>.
	 */
	public static final Parameter<Integer> ID_ZONA = createParameter("id_zona", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.traePorcentaje.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Traeporcentaje() {
		super("traePorcentaje", Matera.MATERA);

		addInParameter(ID_GRUPOPORCENTAJE);
		addInParameter(ID_ESPECIALIDAD);
		addInParameter(ID_ZONA);
		addInParameter(ID_EMPRESA);
	}

	/**
	 * Set the <code>id_grupoPorcentaje</code> parameter IN value to the routine
	 */
	public void setIdGrupoporcentaje(Integer value) {
		setValue(ID_GRUPOPORCENTAJE, value);
	}

	/**
	 * Set the <code>id_especialidad</code> parameter IN value to the routine
	 */
	public void setIdEspecialidad(Integer value) {
		setValue(ID_ESPECIALIDAD, value);
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
